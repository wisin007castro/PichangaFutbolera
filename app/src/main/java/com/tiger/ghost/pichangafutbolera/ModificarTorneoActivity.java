package com.tiger.ghost.pichangafutbolera;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.tiger.ghost.pichangafutbolera.Objetos.Torneo;

import java.util.ArrayList;
import java.util.List;

public class ModificarTorneoActivity extends AppCompatActivity {

    String sizelista;
    String nombre;
    String id;
    private Uri path;
    Uri rutaTorneo;

    private static final int GALLERY_INTENT = 1;

    TextView textViewNombre;
    EditText editTextNombreModificado;
    Button buttonModificarTorneo;
    Button buttonEliminarTorneo;
    Button btnEditImagenCopa;
    ImageView imgCopa;

    DatabaseReference databaseReferenceTorneos;
    private StorageReference mStorage;

    List<Torneo> listaTorneo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_torneo);

        listaTorneo = new ArrayList<>();
        textViewNombre = (TextView) findViewById(R.id.textViewModNomTorneo);
        editTextNombreModificado = (EditText) findViewById(R.id.editTextNomModTorneo);
        buttonModificarTorneo = (Button) findViewById(R.id.btnModTorneo);
        buttonEliminarTorneo = (Button) findViewById(R.id.buttonEliminarTorneo);
        btnEditImagenCopa = (Button) findViewById(R.id.btnEditImagenCopa);
        imgCopa = (ImageView) findViewById(R.id.imageViewCopa);

        Bundle bundle = getIntent().getExtras();

        if(bundle != null){
            nombre = bundle.getString("nombreTorneo");
            id = bundle.getString("IDTorneo");
        }

        databaseReferenceTorneos = FirebaseDatabase.getInstance().getReference("clubes").child(id);
        mStorage = FirebaseStorage.getInstance().getReference();

        textViewNombre.setText(nombre);

        btnEditImagenCopa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentg = new Intent(Intent.ACTION_PICK);

                intentg.setType("image/*");
                startActivityForResult(intentg,GALLERY_INTENT);
            }
        });

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

        buttonEliminarTorneo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder dialogo = new AlertDialog.Builder(v.getContext());
                dialogo.setTitle("ATENCION");
                dialogo.setMessage("Está por eliminar el Torneo");
                dialogo.setCancelable(false);
                dialogo.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        eliminar(id);
                    }
                });
                dialogo.setNegativeButton("Mejor no", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                AlertDialog dialog = dialogo.create();
                dialog.show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GALLERY_INTENT && resultCode == RESULT_OK){

            path = data.getData();
            imgCopa.setImageURI(path);

            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Cargando...");
            progressDialog.show();

            StorageReference filePath = mStorage.child("FotosClubs").child(path.getLastPathSegment());

            filePath.putFile(path)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(ModificarTorneoActivity.this, "Imagen Cargada", Toast.LENGTH_SHORT).show();
                            rutaTorneo = taskSnapshot.getDownloadUrl();
                            Log.d("ruta", rutaTorneo.toString());
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();
                            progressDialog.setMessage(((int) progress) + "% Cargado...");
                        }
                    });
        }
        else {
            Toast.makeText(this, "Seleccione una imagen", Toast.LENGTH_SHORT).show();
        }
    }

    private void modificar (String Noequipos){

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("torneos").child(id);
        String nuevoNombre = editTextNombreModificado.getText().toString().trim();

        if(!TextUtils.isEmpty(nuevoNombre)){

            Torneo torneo = new Torneo(id, Noequipos, nuevoNombre, rutaTorneo.toString());
            databaseReference.setValue(torneo);
//                Toast.makeText(this, "Modificado a " + nuevoNombre, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), NuevaCopa.class);
            startActivity(intent);

        }
        else{
            Toast.makeText(this, "Debe ingresar el nombre", Toast.LENGTH_SHORT).show();
        }
    }

    private void eliminar (String clave){
        DatabaseReference drTorneos = FirebaseDatabase.getInstance().getReference("torneos").child(clave);
        DatabaseReference drClubes = FirebaseDatabase.getInstance().getReference("clubes").child(clave);

        drTorneos.removeValue();
        drClubes.removeValue();

        Toast.makeText(this, "Torneo eliminado Correctamente", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(), NuevaCopa.class);
        startActivity(intent);
    }
}
