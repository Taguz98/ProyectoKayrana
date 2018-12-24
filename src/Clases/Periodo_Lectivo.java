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
public class Periodo_Lectivo {
    private int id_per_lect; 
    private String fecha_ini_per_lect; 
    private String fecha_fin_epr_lect; 
    private String anio_per_lect; 
    private int fk_carrera; 
    
    private boolean per_lect_activo; 
    
    public Periodo_Lectivo(){
        
    }

    public Periodo_Lectivo(int id_per_lect, String fecha_ini_per_lect, String fecha_fin_epr_lect, String anio_per_lect, boolean per_lect_activo, int fk_carrera) {
        this.id_per_lect = id_per_lect;
        this.fecha_ini_per_lect = fecha_ini_per_lect;
        this.fecha_fin_epr_lect = fecha_fin_epr_lect;
        this.anio_per_lect = anio_per_lect;
        this.per_lect_activo = per_lect_activo;
        this.fk_carrera = fk_carrera; 
    }

    public int getId_per_lect() {
        return id_per_lect;
    }

    public void setId_per_lect(int id_per_lect) {
        this.id_per_lect = id_per_lect;
    }

    public String getFecha_ini_per_lect() {
        return fecha_ini_per_lect;
    }

    public void setFecha_ini_per_lect(String fecha_ini_per_lect) {
        this.fecha_ini_per_lect = fecha_ini_per_lect;
    }

    public String getFecha_fin_epr_lect() {
        return fecha_fin_epr_lect;
    }

    public void setFecha_fin_epr_lect(String fecha_fin_epr_lect) {
        this.fecha_fin_epr_lect = fecha_fin_epr_lect;
    }

    public String getAnio_per_lect() {
        return anio_per_lect;
    }

    public void setAnio_per_lect(String anio_per_lect) {
        this.anio_per_lect = anio_per_lect;
    }

    public boolean isPer_lect_activo() {
        return per_lect_activo;
    }

    public void setPer_lect_activo(boolean per_lect_activo) {
        this.per_lect_activo = per_lect_activo;
    }

    public int getFk_carrera() {
        return fk_carrera;
    }

    public void setFk_carrera(int fk_carrera) {
        this.fk_carrera = fk_carrera;
    }
    
    
    
}
