package com.tiger.ghost.pichangafutbolera;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tiger.ghost.pichangafutbolera.Objetos.FirebaseReferencias;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(FirebaseReferencias.REFERENCIA_FUTBOLERA);

        myRef.addValueEventListener(new ValueEventListener() {//actualiza cambios en tiempo real
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int valor = dataSnapshot.getValue(Integer.class);
                Log.i("DATOS", valor+"");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i("ERROR", databaseError.getMessage());
            }
        });
    }
}
