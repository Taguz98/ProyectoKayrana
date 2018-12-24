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
public class DocenteCarrera {
    
    private int id_docen_carrera; 
    private int fk_carrera; 
    private String fk_docente_inst; 
    
    private boolean docente_carrera_elim; 
    
    public DocenteCarrera(){
        
    }

    public DocenteCarrera(int id_docen_carrera, int fk_carrera, String fk_docente_inst, boolean docente_ints_elim) {
        this.id_docen_carrera = id_docen_carrera;
        this.fk_carrera = fk_carrera;
        this.fk_docente_inst = fk_docente_inst;
        this.docente_carrera_elim = docente_ints_elim;
    }

    public int getId_docen_carrera() {
        return id_docen_carrera;
    }

    public void setId_docen_carrera(int id_docen_carrera) {
        this.id_docen_carrera = id_docen_carrera;
    }

    public int getFk_carrera() {
        return fk_carrera;
    }

    public void setFk_carrera(int fk_carrera) {
        this.fk_carrera = fk_carrera;
    }

    public String getFk_docente_inst() {
        return fk_docente_inst;
    }

    public void setFk_docente_inst(String fk_docente_inst) {
        this.fk_docente_inst = fk_docente_inst;
    }

    public boolean isDocente_ints_elim() {
        return docente_carrera_elim;
    }

    public void setDocente_ints_elim(boolean docente_ints_elim) {
        this.docente_carrera_elim = docente_ints_elim;
    }
}
