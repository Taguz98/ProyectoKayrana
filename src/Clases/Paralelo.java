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
public class Paralelo {
    private int id_paralelo; 
    private int ciclo_paralelo; 
    private String nombre_paralelo;
    private int fk_carrera; 
    private boolean paralelo_elim; 
    
    public Paralelo(){
        
    }

    public Paralelo(int id_paralelo, int ciclo_paralelo, String nombre_paralelo, int fk_carrera, boolean paralelo_elim) {
        this.id_paralelo = id_paralelo;
        this.ciclo_paralelo = ciclo_paralelo;
        this.nombre_paralelo = nombre_paralelo;
        this.fk_carrera = fk_carrera;
        this.paralelo_elim = paralelo_elim;
    }
    
    

    public int getId_paralelo() {
        return id_paralelo;
    }

    public void setId_paralelo(int id_paralelo) {
        this.id_paralelo = id_paralelo;
    }

    public int getCiclo_paralelo() {
        return ciclo_paralelo;
    }

    public void setCiclo_paralelo(int ciclo_paralelo) {
        this.ciclo_paralelo = ciclo_paralelo;
    }

    public String getNombre_paralelo() {
        return nombre_paralelo;
    }

    public void setNombre_paralelo(String nombre_paralelo) {
        this.nombre_paralelo = nombre_paralelo;
    }

    public int getFk_carrera() {
        return fk_carrera;
    }

    public void setFk_carrera(int fk_carrera) {
        this.fk_carrera = fk_carrera;
    }

    public boolean isParalelo_elim() {
        return paralelo_elim;
    }

    public void setParalelo_elim(boolean paralelo_elim) {
        this.paralelo_elim = paralelo_elim;
    }
    
}
