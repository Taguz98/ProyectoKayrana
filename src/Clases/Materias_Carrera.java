/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.util.ArrayList;

/**
 *
 * @author Usuario
 */
public class Materias_Carrera {
    private int id_mat_carrera; 
    private int ciclo_mat_carrera; 
    private int fk_carrera; 
    
    private ArrayList<Materia> materias_carrera; 
    
    public Materias_Carrera(){
        
    }

    public Materias_Carrera(int id_mat_carrera, int ciclo_mat_carrera, int fk_carrera) {
        this.id_mat_carrera = id_mat_carrera;
        this.ciclo_mat_carrera = ciclo_mat_carrera;
        this.fk_carrera = fk_carrera;
    }

    public int getId_mat_carrera() {
        return id_mat_carrera;
    }

    public void setId_mat_carrera(int id_mat_carrera) {
        this.id_mat_carrera = id_mat_carrera;
    }

    public int getCiclo_mat_carrera() {
        return ciclo_mat_carrera;
    }

    public void setCiclo_mat_carrera(int ciclo_mat_carrera) {
        this.ciclo_mat_carrera = ciclo_mat_carrera;
    }

    public int getFk_carrera() {
        return fk_carrera;
    }

    public void setFk_carrera(int fk_carrera) {
        this.fk_carrera = fk_carrera;
    }

    public ArrayList<Materia> getMaterias_carrera() {
        return materias_carrera;
    }

    public void setMaterias_carrera(ArrayList<Materia> materias_carrera) {
        this.materias_carrera = materias_carrera;
    }
    
    
}
