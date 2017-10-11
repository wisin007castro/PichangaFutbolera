package com.tiger.ghost.pichangafutbolera;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
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

    TextView textViewNombreTorneo;
    EditText editTextNombreClub;
    Button buttonAgregarClub;

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

        Intent intent = getIntent();

        listaClub = new ArrayList<>();

        String id = intent.getStringExtra(NuevaCopa.ID_TORNEO);
        String nombre = intent.getStringExtra(NuevaCopa.NOMBRE_TORNEO);

        textViewNombreTorneo.setText(nombre);
        databaseClubs = FirebaseDatabase.getInstance().getReference("clubes").child(id);

        buttonAgregarClub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarClub();
                editTextNombreClub.setText("");
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


        if(!TextUtils.isEmpty(nombreClub)){
            String id = databaseClubs.push().getKey();

            Club club = new Club(id, nombreClub, puntos);
            databaseClubs.child(id).setValue(club);

            Toast.makeText(this, "Agregado Correctamente", Toast.LENGTH_SHORT).show();

        }else{
            Toast.makeText(this, "Debe ingresar un nombre", Toast.LENGTH_SHORT).show();
        }

    }
}
