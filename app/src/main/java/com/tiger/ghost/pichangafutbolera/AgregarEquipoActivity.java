package com.tiger.ghost.pichangafutbolera;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tiger.ghost.pichangafutbolera.Objetos.Club;

import java.util.ArrayList;
import java.util.List;

public class AgregarEquipoActivity extends AppCompatActivity {

    String idTorneo;
    String nombre;
    TextView textViewNombreTorneo;
    EditText editTextNombreClub;
    Button buttonAgregarClub;
    Button buttonModElimTorneo;

    ListView listViewClubs;
    DatabaseReference databaseClubs;

    List<Club> listaClub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_equipo);

        textViewNombreTorneo = (TextView) findViewById(R.id.textViewClub);
        editTextNombreClub = (EditText) findViewById(R.id.editTextClub);
        listViewClubs = (ListView) findViewById(R.id.listViewClubes);
        buttonAgregarClub = (Button) findViewById(R.id.buttonAgregar);
        buttonModElimTorneo = (Button) findViewById(R.id.btnModElimTorneo);

        listaClub = new ArrayList<>();

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            idTorneo = bundle.getString("IdTorneo");
            nombre = bundle.getString("NombreTorneo");
        }

        textViewNombreTorneo.setText(nombre);
        databaseClubs = FirebaseDatabase.getInstance().getReference("clubes").child(idTorneo);

        buttonAgregarClub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarClub();
                editTextNombreClub.setText("");
            }
        });

        buttonModElimTorneo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(), ModificarTorneoActivity.class);

                intent1.putExtra("nombreTorneo", nombre);
                intent1.putExtra("IDTorneo", idTorneo);

                startActivity(intent1);
            }
        });

        listViewClubs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Club club = listaClub.get(position);

                Intent intentClub = new Intent(getApplicationContext(), ModificarEquipoActivity.class);

                intentClub.putExtra("IdTorneo", idTorneo);
                intentClub.putExtra("NombreTorneo", nombre);
                intentClub.putExtra("nombreClub", club.getNombreclub());
                intentClub.putExtra("IdClub", club.getIdClub());
                intentClub.putExtra("puntosClub", club.getPuntos());

                startActivity(intentClub);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseClubs.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listaClub.clear();
                for(DataSnapshot clubSnapshot: dataSnapshot.getChildren()){
                    Club club = clubSnapshot.getValue(Club.class);
                    listaClub.add(club);
                }
                ListaClubes clubesListAdapter = new ListaClubes(AgregarEquipoActivity.this, listaClub);
                listViewClubs.setAdapter(clubesListAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void guardarClub(){
        int puntos = 0;
        String nombreClub = editTextNombreClub.getText().toString().trim();
        String ruta = "";

        if(!TextUtils.isEmpty(nombreClub)){
            String id = databaseClubs.push().getKey();

            Club club = new Club(id, nombreClub, puntos, ruta);
            databaseClubs.child(id).setValue(club);

            Toast.makeText(this, "Agregado Correctamente", Toast.LENGTH_SHORT).show();

        }else{
            Toast.makeText(this, "Debe ingresar un nombre", Toast.LENGTH_SHORT).show();
        }
    }
}
