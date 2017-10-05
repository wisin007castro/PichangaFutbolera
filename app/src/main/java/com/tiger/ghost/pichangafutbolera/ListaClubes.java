package com.tiger.ghost.pichangafutbolera;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.tiger.ghost.pichangafutbolera.Objetos.Club;

import java.util.List;

public class ListaClubes extends ArrayAdapter<Club>{

    private Activity context;
    private List<Club> listaClub;

    public ListaClubes(Activity context, List<Club> listaClub){
        super(context, R.layout.list_clubs_layout, listaClub);
        this.context = context;
        this.listaClub = listaClub;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.list_clubs_layout, null, true);

        TextView textViewNombre = (TextView) listViewItem.findViewById(R.id.textViewNombreClub);
        TextView textViewPuntos = (TextView) listViewItem.findViewById(R.id.textViewPuntos);

        Club club = listaClub.get(position);

        textViewNombre.setText(club.getNombreclub());
        textViewPuntos.setText("Puntos del Club: " + club.getPuntos());

        return listViewItem;
    }
}
