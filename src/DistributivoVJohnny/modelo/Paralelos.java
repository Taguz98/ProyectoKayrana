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
public class Paralelos {
    
    private int id; 
    private String nombre; 
    private int ciclo; 
    private ArrayList<Jornadas> jornadas; 
    
    public Paralelos(){ 
        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCiclo() {
        return ciclo;
    }

    public void setCiclo(int ciclo) {
        this.ciclo = ciclo;
    }

    public ArrayList<Jornadas> getJornadas() {
        return jornadas;
    }

    public void setJornadas(ArrayList<Jornadas> jornadas) {
        this.jornadas = jornadas;
    }
}
