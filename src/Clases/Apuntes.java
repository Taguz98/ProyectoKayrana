/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.util.Date;

/**
 *
 * @author Johnny
 */
public class Apuntes {
    
    private int id_apunte; 
    private Date fecha_esc_apunte; 
    private String apunte; 
    
    private String fk_usuario; 
    private boolean apunte_elim; 
    
    public Apuntes(){
        
    }

    public Apuntes(int id_apunte, Date fecha_esc_apunte, String apunte, String fk_usuario, boolean apunte_elim) {
        this.id_apunte = id_apunte;
        this.fecha_esc_apunte = fecha_esc_apunte;
        this.apunte = apunte;
        this.fk_usuario = fk_usuario;
        this.apunte_elim = apunte_elim;
    }

    public int getId_apunte() {
        return id_apunte;
    }

    public void setId_apunte(int id_apunte) {
        this.id_apunte = id_apunte;
    }

    public Date getFecha_esc_apunte() {
        return fecha_esc_apunte;
    }

    public void setFecha_esc_apunte(Date fecha_esc_apunte) {
        this.fecha_esc_apunte = fecha_esc_apunte;
    }

    public String getApunte() {
        return apunte;
    }

    public void setApunte(String apunte) {
        this.apunte = apunte;
    }

    public String getFk_usuario() {
        return fk_usuario;
    }

    public void setFk_usuario(String fk_usuario) {
        this.fk_usuario = fk_usuario;
    }

    public boolean isApunte_elim() {
        return apunte_elim;
    }

    public void setApunte_elim(boolean apunte_elim) {
        this.apunte_elim = apunte_elim;
    }
    
}
