package com.tiger.ghost.pichangafutbolera.Objetos;


/**
 * Created by ghost on 4/10/2017.
 */

public class Club {
    private String idClub;
    private String nombreclub;
    private int puntos;
    private String rutaImagen;

    public Club (){

    }

    public Club(String idClub, String nombreclub, int puntos, String rutaImagen) {
        this.idClub = idClub;
        this.nombreclub = nombreclub;
        this.puntos = puntos;
        this.rutaImagen = rutaImagen;
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

    public String getRutaImagen() {
        return rutaImagen;
    }
}
