/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

/**
 *
 * @author Usuario
 */
public class Actividad {
    
    private int id_actividad; 
    private String nombre_actividad; 
    private boolean actividad_elim; 
    
    public Actividad(){
        
    }

    public Actividad(int id_actividad, String nombre_actividad, boolean actividad_elim) {
        this.id_actividad = id_actividad;
        this.nombre_actividad = nombre_actividad;
        this.actividad_elim = actividad_elim;
    }

    public int getId_actividad() {
        return id_actividad;
    }

    public void setId_actividad(int id_actividad) {
        this.id_actividad = id_actividad;
    }

    public String getNombre_actividad() {
        return nombre_actividad;
    }

    public void setNombre_actividad(String nombre_actividad) {
        this.nombre_actividad = nombre_actividad;
    }

    public boolean isActividad_elim() {
        return actividad_elim;
    }

    public void setActividad_elim(boolean actividad_elim) {
        this.actividad_elim = actividad_elim;
    }
    
}
