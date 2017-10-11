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
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tiger.ghost.pichangafutbolera.Objetos.Torneo;

import java.util.ArrayList;
import java.util.List;

public class NuevaCopa extends AppCompatActivity {

    public static final String NOMBRE_TORNEO = "nombretorneo";
    public static final String ID_TORNEO = "idtorneo";

    private TextView text;
    private EditText nombreTorneo;
    private int numero;
    private Button bGuardar;
    private SeekBar seek;

    DatabaseReference databaseTorneos;

    ListView listViewTorneos;

    List<Torneo> listaTorneo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_copa);

        databaseTorneos = FirebaseDatabase.getInstance().getReference("torneos");

        text = (TextView) findViewById(R.id.txtEquipos);
        seek = (SeekBar) findViewById(R.id.seekNumero);
        nombreTorneo = (EditText) findViewById(R.id.editTextTorneo);
        bGuardar = (Button) findViewById(R.id.buttonGuardar);

        seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                numero = progress + 2;
//                Toast.makeText(NuevaCopa.this, "" + numero, Toast.LENGTH_SHORT).show();
                text.setText("Equipos: " + numero);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        listViewTorneos = (ListView) findViewById(R.id.listViewTorneos);
        listaTorneo = new ArrayList<>();

        bGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarTorneo();
                nombreTorneo.setText("");
            }
        });

        listViewTorneos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Torneo torneo = listaTorneo.get(position);

                Intent intent = new Intent(getApplicationContext(), AgregarEquipoActivity.class);

                intent.putExtra(ID_TORNEO, torneo.getTorneoId());
                intent.putExtra(NOMBRE_TORNEO, torneo.getNombreTorneo());

                startActivity(intent);
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseTorneos.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                listaTorneo.clear();

                for(DataSnapshot torneoSnapshot: dataSnapshot.getChildren()){
                    Torneo torneo = torneoSnapshot.getValue(Torneo.class);

                    listaTorneo.add(torneo);
                }

                ListaTorneos adapter = new ListaTorneos(NuevaCopa.this, listaTorneo);
                listViewTorneos.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void agregarTorneo(){
        String nombre = nombreTorneo.getText().toString().trim();
        String nEquipos = Integer.toString(numero);

        if(!TextUtils.isEmpty(nombre)){

            String id = databaseTorneos.push().getKey();

            Torneo torneo = new Torneo(id, nEquipos, nombre);
            databaseTorneos.child(id).setValue(torneo);
            Toast.makeText(NuevaCopa.this, "Nombre: "+nombre+" ,Numero de equipos:"+nEquipos, Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Debes introducir un nombre", Toast.LENGTH_SHORT).show();
        }
    }
}
