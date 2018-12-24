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
public class Materia {
   
    private int id_materia; 
    private String nombre_materia; 
    private boolean materia_elim; 
    
    public Materia(){
        
    }

    public Materia(String nombre_materia, boolean materia_elim) {
        this.nombre_materia = nombre_materia;
        this.materia_elim = materia_elim;
    }

    public int getId_materia() {
        return id_materia;
    }

    public void setId_materia(int id_materia) {
        this.id_materia = id_materia;
    }
    

    public String getNombre_materia() {
        return nombre_materia;
    }

    public void setNombre_materia(String nombre_materia) {
        this.nombre_materia = nombre_materia;
    }

    public boolean isMateria_elim() {
        return materia_elim;
    }

    public void setMateria_elim(boolean materia_elim) {
        this.materia_elim = materia_elim;
    }
    
    
}
