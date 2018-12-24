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
public class MateriaCarrera {
    
    private int id_mat_carrera; 
    private int ciclo_mat_carrera;  
    private int fk_carrera;  
    
    public MateriaCarrera(){
        
    }

    public MateriaCarrera(int id_mat_carrera, int ciclo_mat_carrera, int fk_carrera) {
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
    
    
    
}
