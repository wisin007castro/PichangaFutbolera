package com.tiger.ghost.pichangafutbolera;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.tiger.ghost.pichangafutbolera.Objetos.Torneo;

import java.util.List;

public class ListaTorneos extends ArrayAdapter<Torneo> {

    private Activity context;
    private List<Torneo> listaTorneo;

    public ListaTorneos(Activity context, List<Torneo> listaTorneo){
        super(context, R.layout.list_torneos_layout, listaTorneo);
        this.context = context;
        this.listaTorneo = listaTorneo;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.list_torneos_layout, null, true);

        TextView textViewNombre = (TextView) listViewItem.findViewById(R.id.textViewNombre);
        TextView textViewNumero = (TextView) listViewItem.findViewById(R.id.textViewNumero);

        Torneo torneo = listaTorneo.get(position);

        textViewNombre.setText(torneo.getNombreTorneo());
        textViewNumero.setText("Numero de equipos: "+torneo.getNumeroEquipos());

        return listViewItem;
    }
}
