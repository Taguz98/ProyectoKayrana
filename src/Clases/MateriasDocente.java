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
public class MateriasDocente {
    private int id_mat_pref; 
    private String fk_docente; 
    private int fk_materia;
    private boolean mat_pref_elim; 
    
    public MateriasDocente(){
        
    }

    public MateriasDocente(int id_mat_pref, String fk_docente, int fk_materia, boolean mat_pref_elim) {
        this.id_mat_pref = id_mat_pref;
        this.fk_docente = fk_docente;
        this.fk_materia = fk_materia;
        this.mat_pref_elim = mat_pref_elim;
    }

    public int getId_mat_pref() {
        return id_mat_pref;
    }

    public void setId_mat_pref(int id_mat_pref) {
        this.id_mat_pref = id_mat_pref;
    }

    public String getFk_docente() {
        return fk_docente;
    }

    public void setFk_docente(String fk_docente) {
        this.fk_docente = fk_docente;
    }

    public int getFk_materia() {
        return fk_materia;
    }

    public void setFk_materia(int fk_materia) {
        this.fk_materia = fk_materia;
    }

    public boolean isMat_pref_elim() {
        return mat_pref_elim;
    }

    public void setMat_pref_elim(boolean mat_pref_elim) {
        this.mat_pref_elim = mat_pref_elim;
    }
    
    
}
