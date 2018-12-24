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
public class Detalle_Jornada {
    private int id_deta_jornada; 
    private String dias; 
    private String descripcion_jornada; 
    
    private int fk_jornada; 
    private int fk_hora1; 
    private int fk_hora2; 
    
    private boolean deta_jord_elim; 
    
    public Detalle_Jornada(){
        
    }

    public Detalle_Jornada(int id_deta_jornada, String dias, String descripcion_jornada, int fk_jornada, int fk_hora1, int fk_hora2, boolean deta_jord_elim) {
        this.id_deta_jornada = id_deta_jornada;
        this.dias = dias;
        this.descripcion_jornada = descripcion_jornada;
        this.fk_jornada = fk_jornada;
        this.fk_hora1 = fk_hora1;
        this.fk_hora2 = fk_hora2;
        
        this.deta_jord_elim = deta_jord_elim; 
    }

    public int getId_deta_jornada() {
        return id_deta_jornada;
    }

    public void setId_deta_jornada(int id_deta_jornada) {
        this.id_deta_jornada = id_deta_jornada;
    }

    public String getDias() {
        return dias;
    }

    public void setDias(String dias) {
        this.dias = dias;
    }

    public String getDescripcion_jornada() {
        return descripcion_jornada;
    }

    public void setDescripcion_jornada(String descripcion_jornada) {
        this.descripcion_jornada = descripcion_jornada;
    }

    public int getFk_jornada() {
        return fk_jornada;
    }

    public void setFk_jornada(int fk_jornada) {
        this.fk_jornada = fk_jornada;
    }

    public int getFk_hora1() {
        return fk_hora1;
    }

    public void setFk_hora1(int fk_hora1) {
        this.fk_hora1 = fk_hora1;
    }

    public int getFk_hora2() {
        return fk_hora2;
    }

    public void setFk_hora2(int fk_hora2) {
        this.fk_hora2 = fk_hora2;
    }

    public boolean isDeta_jord_elim() {
        return deta_jord_elim;
    }

    public void setDeta_jord_elim(boolean deta_jord_elim) {
        this.deta_jord_elim = deta_jord_elim;
    }
    
}
