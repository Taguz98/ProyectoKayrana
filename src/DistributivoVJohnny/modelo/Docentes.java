/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DistributivoVJohnny.modelo;

import java.util.ArrayList;

/**
 *
 * @author Usuario
 */
public class Docentes {
    
    private String cedula; 
    private String nombre; 
    private ArrayList<Materias> materiasPref;
    
    public Docentes(){
        
    } 

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ArrayList<Materias> getMateriasPref() {
        return materiasPref;
    }

    public void setMateriasPref(ArrayList<Materias> materiasPref) {
        this.materiasPref = materiasPref;
    }
    
}
