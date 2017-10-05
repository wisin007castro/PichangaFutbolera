package com.tiger.ghost.pichangafutbolera.Objetos;

/**
 * Created by ghost on 3/10/2017.
 */

public class Torneo {
    String torneoId;
    String numeroEquipos;
    String nombreTorneo;

    public Torneo(){

    }

    public Torneo(String torneoId, String numeroEquipos, String nombreTorneo) {
        this.torneoId = torneoId;
        this.numeroEquipos = numeroEquipos;
        this.nombreTorneo = nombreTorneo;
    }

    public String getTorneoId() {
        return torneoId;
    }

    public String getNumeroEquipos() {
        return numeroEquipos;
    }

    public String getNombreTorneo() {
        return nombreTorneo;
    }
}
