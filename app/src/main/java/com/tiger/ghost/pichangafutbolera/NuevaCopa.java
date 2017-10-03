package com.tiger.ghost.pichangafutbolera;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class NuevaCopa extends AppCompatActivity {

    private TextView text;
    private int numero;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_copa);
        text = (TextView) findViewById(R.id.txtEquipos);
        SeekBar s = (SeekBar) findViewById(R.id.seek);

        s.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                numero = progress + 2;
                Toast.makeText(NuevaCopa.this, "" + numero, Toast.LENGTH_SHORT).show();
                text.setText("Equipos: " + numero);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }
}
