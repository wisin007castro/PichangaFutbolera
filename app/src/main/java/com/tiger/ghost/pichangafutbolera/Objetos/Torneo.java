package com.tiger.ghost.pichangafutbolera.Objetos;

/**
 * Created by ghost on 3/10/2017.
 */

public class Torneo {
    String torneoId;
    String numeroEquipos;
    String nombreTorneo;
    String rutaTorneo;

    public Torneo(){

    }

    public Torneo(String torneoId, String numeroEquipos, String nombreTorneo, String rutaTorneo) {
        this.torneoId = torneoId;
        this.numeroEquipos = numeroEquipos;
        this.nombreTorneo = nombreTorneo;
        this.rutaTorneo = rutaTorneo;
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

    public String getRutaTorneo() {
        return rutaTorneo;
    }
}
