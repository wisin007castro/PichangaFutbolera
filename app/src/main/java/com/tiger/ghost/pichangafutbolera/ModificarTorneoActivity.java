package com.tiger.ghost.pichangafutbolera;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class ModificarTorneoActivity extends AppCompatActivity {

    int contador;
    String sizelista;
    String nombre;
    String id;
    TextView textViewNombre;
    EditText editTextNombreModificado;
    Button buttonModificarTorneo;

    DatabaseReference databaseReferenceTorneos;

    List<Torneo> listaTorneo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_torneo);

        listaTorneo = new ArrayList<>();
        textViewNombre = (TextView) findViewById(R.id.textViewModNomTorneo);
        editTextNombreModificado = (EditText) findViewById(R.id.editTextNomModTorneo);
        buttonModificarTorneo = (Button) findViewById(R.id.btnModTorneo);

        Bundle bundle = getIntent().getExtras();

        if(bundle != null){
            nombre = bundle.getString("nombreTorneo");
            id = bundle.getString("IDTorneo");
        }

        databaseReferenceTorneos = FirebaseDatabase.getInstance().getReference("clubes").child(id);

        textViewNombre.setText(nombre);

        buttonModificarTorneo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReferenceTorneos.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        listaTorneo.clear();
                        for (DataSnapshot torneoSnapshot: dataSnapshot.getChildren()){
                            Torneo torneo = torneoSnapshot.getValue(Torneo.class);

                            listaTorneo.add(torneo);
                        }
                        sizelista = String.valueOf(listaTorneo.size());
                        Log.d("Firetorneo", sizelista);
                        modificar(sizelista);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    private void modificar (String Noequipos){

        String nuevoNombre = editTextNombreModificado.getText().toString().trim();

        if(!TextUtils.isEmpty(nuevoNombre)){
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("torneos").child(id);

            Torneo torneo = new Torneo(id, Noequipos, nuevoNombre);
            databaseReference.setValue(torneo);
            Toast.makeText(this, "Modificado a " + nuevoNombre, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), NuevaCopa.class);
            startActivity(intent);
        }
        else{
            Toast.makeText(this, "Debe ingresar el nombre", Toast.LENGTH_SHORT).show();
        }

    }
}
