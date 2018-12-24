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
public class MateriasCiclo {
    
    private int id_mat_ciclo; 
    private int fk_materia; 
    private int fk_mat_carrera;
    private int horas_materia; 
    
    private boolean mat_carrera_elim; 
    
    public MateriasCiclo(){
        
    }

    public MateriasCiclo(int id_mat_ciclo, int fk_materia, int fk_mat_carrera, int horas_materia, boolean mat_carrera_elim) {
        this.id_mat_ciclo = id_mat_ciclo;
        this.fk_materia = fk_materia;
        this.fk_mat_carrera = fk_mat_carrera;
        this.horas_materia = horas_materia; 
        this.mat_carrera_elim = mat_carrera_elim;
    }

    public int getId_mat_ciclo() {
        return id_mat_ciclo;
    }

    public void setId_mat_ciclo(int id_mat_ciclo) {
        this.id_mat_ciclo = id_mat_ciclo;
    }

    public int getFk_materia() {
        return fk_materia;
    }

    public void setFk_materia(int fk_materia) {
        this.fk_materia = fk_materia;
    }

    public int getFk_mat_carrera() {
        return fk_mat_carrera;
    }

    public void setFk_mat_carrera(int fk_mat_carrera) {
        this.fk_mat_carrera = fk_mat_carrera;
    }

    public boolean isMat_carrera_elim() {
        return mat_carrera_elim;
    }

    public void setMat_carrera_elim(boolean mat_carrera_elim) {
        this.mat_carrera_elim = mat_carrera_elim;
    }

    public int getHoras_materia() {
        return horas_materia;
    }

    public void setHoras_materia(int horas_materia) {
        this.horas_materia = horas_materia;
    }
    
    
}
