/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

/**
 *
 * @author Johnny
 */
public class Carrera_Jornada {
    
    private int id_carrera_jornada;  
    private int fk_carrera; 
    private int fk_jornada; 
    private boolean jorn_carrera_elim; 
    
    public Carrera_Jornada(){
        
    }

    public Carrera_Jornada(int id_carrera_jornada, int fk_carrera, int fk_jornada, boolean jorn_carrera_elim) {
        this.id_carrera_jornada = id_carrera_jornada;
        this.fk_carrera = fk_carrera;
        this.fk_jornada = fk_jornada;
        this.jorn_carrera_elim = jorn_carrera_elim;
    }

    public int getId_carrera_jornada() {
        return id_carrera_jornada;
    }

    public void setId_carrera_jornada(int id_carrera_jornada) {
        this.id_carrera_jornada = id_carrera_jornada;
    }

    public int getFk_carrera() {
        return fk_carrera;
    }

    public void setFk_carrera(int fk_carrera) {
        this.fk_carrera = fk_carrera;
    }

    public int getFk_jornada() {
        return fk_jornada;
    }

    public void setFk_jornada(int fk_jornada) {
        this.fk_jornada = fk_jornada;
    }

    public boolean isJorn_carrera_elim() {
        return jorn_carrera_elim;
    }

    public void setJorn_carrera_elim(boolean jorn_carrera_elim) {
        this.jorn_carrera_elim = jorn_carrera_elim;
    }
    
    
    
}
