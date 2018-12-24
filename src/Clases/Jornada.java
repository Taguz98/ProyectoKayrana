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
public class Jornada {
    
    private int id_jornada; 
    private String jornada; 
    
    public Jornada(){
        
    }

    public Jornada(int id_jornada, String jornada) {
        this.id_jornada = id_jornada;
        this.jornada = jornada;
    }

    public int getId_jornada() {
        return id_jornada;
    }

    public void setId_jornada(int id_jornada) {
        this.id_jornada = id_jornada;
    }

    public String getJornada() {
        return jornada;
    }

    public void setJornada(String jornada) {
        this.jornada = jornada;
    }  
    
}
