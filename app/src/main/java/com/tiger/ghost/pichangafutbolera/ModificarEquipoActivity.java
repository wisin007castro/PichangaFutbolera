package com.tiger.ghost.pichangafutbolera;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tiger.ghost.pichangafutbolera.Objetos.Club;

public class ModificarEquipoActivity extends AppCompatActivity {

    String idtorneo;
    String nombreTorneo;
    String id;
    String nombre;
    int puntos;

    TextView textViewModificarNombreClub;
    EditText editTextModificarClub;
    Button btnModificarClub;
    Button btnEliminarClub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_equipo);

        textViewModificarNombreClub = (TextView) findViewById(R.id.textViewNombreModEquipo);
        editTextModificarClub = (EditText) findViewById(R.id.editTextModificaEquipo);
        btnModificarClub = (Button) findViewById(R.id.buttonModificaClub);
        btnEliminarClub = (Button) findViewById(R.id.btnEliminarClub);

        Bundle bundle = getIntent().getExtras();

        if(bundle != null){
            idtorneo = bundle.getString("IdTorneo");
            nombreTorneo = bundle.getString("NombreTorneo");
            id = bundle.getString("IdClub");
            nombre = bundle.getString("nombreClub");
            puntos = bundle.getInt("puntosClub");
        }

        textViewModificarNombreClub.setText(nombre);

        btnModificarClub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modificar();
            }
        });

        btnEliminarClub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminar();
            }
        });
    }

    private void modificar(){

        String nuevoNombre = editTextModificarClub.getText().toString().trim();
        String nuevaRuta = "";

        if(!TextUtils.isEmpty(nuevoNombre)){
            DatabaseReference drModificar = FirebaseDatabase.getInstance().getReference("clubes").child(idtorneo);

            Club club = new Club(id, nuevoNombre, puntos, nuevaRuta);
            drModificar.child(id).setValue(club);
            Toast.makeText(this, "Club cambiado a " + nuevoNombre, Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(getApplicationContext(), AgregarEquipoActivity.class);
            intent.putExtra("NombreTorneo", nombreTorneo);
            intent.putExtra("IdTorneo", idtorneo);

            startActivity(intent);
        }
        else{
            Toast.makeText(this, "Debe ingresar el nuevo nombre", Toast.LENGTH_SHORT).show();
        }
    }

    private void eliminar(){
        DatabaseReference drEliminar = FirebaseDatabase.getInstance().getReference("clubes").child(idtorneo);

        drEliminar.child(id).removeValue();
        Toast.makeText(this, "Club eliminado Correctamente", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(getApplicationContext(), AgregarEquipoActivity.class);
        intent.putExtra("NombreTorneo", nombreTorneo);
        intent.putExtra("IdTorneo", idtorneo);

        startActivity(intent);
    }
}
