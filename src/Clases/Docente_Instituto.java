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
public class Docente_Instituto {
    private String cedula_docente; 
    private String nombre_docente; 
    private boolean docente_insti_elim; 
    
    private ArrayList<Actividad> actividades_docente; 
    private ArrayList<Detalle_Jornada> jornada_docente; 
    private ArrayList<Materia> materias_preferentes; 
    private ArrayList<Carrera> docente_carreras; 
    
    public Docente_Instituto(){
        
    }

    public Docente_Instituto(String cedula_docente, String nombre_docente, boolean docente_insti_elim) {
        this.cedula_docente = cedula_docente;
        this.nombre_docente = nombre_docente;
        this.docente_insti_elim = docente_insti_elim;
    }

    public String getCedula_docente() {
        return cedula_docente;
    }

    public void setCedula_docente(String cedula_docente) {
        this.cedula_docente = cedula_docente;
    }

    public String getNombre_docente() {
        return nombre_docente;
    }

    public void setNombre_docente(String nombre_docente) {
        this.nombre_docente = nombre_docente;
    }

    public boolean isDocente_insti_elim() {
        return docente_insti_elim;
    }

    public void setDocente_insti_elim(boolean docente_insti_elim) {
        this.docente_insti_elim = docente_insti_elim;
    }

    public ArrayList<Actividad> getActividades_docente() {
        return actividades_docente;
    }

    public void setActividades_docente(ArrayList<Actividad> actividades_docente) {
        this.actividades_docente = actividades_docente;
    }

    public ArrayList<Detalle_Jornada> getJornada_docente() {
        return jornada_docente;
    }

    public void setJornada_docente(ArrayList<Detalle_Jornada> jornada_docente) {
        this.jornada_docente = jornada_docente;
    }

    public ArrayList<Materia> getMaterias_preferentes() {
        return materias_preferentes;
    }

    public void setMaterias_preferentes(ArrayList<Materia> materias_preferentes) {
        this.materias_preferentes = materias_preferentes;
    }

    public ArrayList<Carrera> getDocente_carreras() {
        return docente_carreras;
    }

    public void setDocente_carreras(ArrayList<Carrera> docente_carreras) {
        this.docente_carreras = docente_carreras;
    }
 
}
