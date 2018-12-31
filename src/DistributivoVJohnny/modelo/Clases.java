/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DistributivoVJohnny.modelo;

/**
 *
 * @author Usuario
 */
public class Clases {
    private Materias materia; 
    private int numDiasClase; 
    private int horasClaseDia[];  
    
    public Clases(){
        
    }

    public Materias getMateria() {
        return materia;
    }

    public void setMateria(Materias materia) {
        this.materia = materia;
    }

    public int getNumDiasClase() {
        return numDiasClase;
    }

    public void setNumDiasClase(int numDiasClase) {
        this.numDiasClase = numDiasClase;
    }

    public int[] getHorasClaseDia() {
        return horasClaseDia;
    }

    public void setHorasClaseDia(int[] horasClaseDia) {
        this.horasClaseDia = horasClaseDia;
    }
    
}
