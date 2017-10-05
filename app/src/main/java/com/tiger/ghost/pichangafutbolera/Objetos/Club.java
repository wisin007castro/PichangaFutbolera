package com.tiger.ghost.pichangafutbolera.Objetos;

/**
 * Created by ghost on 4/10/2017.
 */

public class Club {
    private String idClub;
    private String nombreclub;
    private int puntos;

    public Club (){

    }

    public Club(String idClub, String nombreclub, int puntos) {
        this.idClub = idClub;
        this.nombreclub = nombreclub;
        this.puntos = puntos;
    }

    public String getIdClub() {
        return idClub;
    }

    public String getNombreclub() {
        return nombreclub;
    }

    public int getPuntos() {
        return puntos;
    }
}
