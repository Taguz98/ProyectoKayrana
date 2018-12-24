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
public class HorasTrabajo {
    
    private int id_hora_trabajo; 
    private String hora_trab;
    
    public HorasTrabajo(){
        
    }

    public HorasTrabajo(int id_hora_trabajo, String hora_trab) {
        this.id_hora_trabajo = id_hora_trabajo;
        this.hora_trab = hora_trab;
    }

    public int getId_hora_trabajo() {
        return id_hora_trabajo;
    }

    public void setId_hora_trabajo(int id_hora_trabajo) {
        this.id_hora_trabajo = id_hora_trabajo;
    }

    public String getHora_trab() {
        return hora_trab;
    }

    public void setHora_trab(String hora_trab) {
        this.hora_trab = hora_trab;
    }
   
}
