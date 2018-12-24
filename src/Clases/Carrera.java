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
public class Carrera {
    private int id_carrera; 
    private String nombre_Carrera; 
    private String modalidad_Carrera; 
    private String titulo_carrera; 
    private int num_ciclos_carrera; 
    private boolean carrera_elim; 
    
    private ArrayList<Jornada> jornada = new ArrayList(); 
    
    private ArrayList<Materia> materias = new ArrayList(); 
    
    public Carrera(){
        
    }

    public Carrera(int id_carrera, String nombre_Carrera, String modalidad_Carrera, String titulo_carrera, int num_ciclos_carrera, boolean carrera_elim, ArrayList<Jornada> jornada) {
        this.id_carrera = id_carrera;
        this.nombre_Carrera = nombre_Carrera;
        this.modalidad_Carrera = modalidad_Carrera;
        this.titulo_carrera = titulo_carrera;
        this.num_ciclos_carrera = num_ciclos_carrera;
        this.carrera_elim = carrera_elim;
        this.jornada = jornada;
    }

    public int getId_carrera() {
        return id_carrera;
    }

    public void setId_carrera(int id_carrera) {
        this.id_carrera = id_carrera;
    }

    public String getNombre_Carrera() {
        return nombre_Carrera;
    }

    public void setNombre_Carrera(String nombre_Carrera) {
        this.nombre_Carrera = nombre_Carrera;
    }

    public String getModalidad_Carrera() {
        return modalidad_Carrera;
    }

    public void setModalidad_Carrera(String modalidad_Carrera) {
        this.modalidad_Carrera = modalidad_Carrera;
    }

    public String getTitulo_carrera() {
        return titulo_carrera;
    }

    public void setTitulo_carrera(String titulo_carrera) {
        this.titulo_carrera = titulo_carrera;
    }

    public int getNum_ciclos_carrera() {
        return num_ciclos_carrera;
    }

    public void setNum_ciclos_carrera(int num_ciclos_carrera) {
        this.num_ciclos_carrera = num_ciclos_carrera;
    }

    public boolean isCarrera_elim() {
        return carrera_elim;
    }

    public void setCarrera_elim(boolean carrera_elim) {
        this.carrera_elim = carrera_elim;
    }
    
    public ArrayList<Jornada> getJornada() {
        return jornada;
    }

    public void setJornada(ArrayList<Jornada> jornada) {
        this.jornada = jornada;
    }
    
    public void agregarJornada(Jornada jdr){
        this.jornada.add(jdr); 
    }
    
}
