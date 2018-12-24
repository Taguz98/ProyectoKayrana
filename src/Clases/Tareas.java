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
public class Tareas {
    
    private int id_tarea; 
    private Date fecha_esc_tarea; 
    private String tarea; 
    private Date fecha_presentacion; 
    private boolean tarea_completa; 
    
    private String fk_usuario; 
    private boolean tarea_elim; 
    
    public Tareas(){
        
    }

    public Tareas(int id_tarea, Date fecha_esc_tarea, String tarea, Date fecha_presentacion, boolean tarea_completa, String fk_usuario, boolean tarea_elim) {
        this.id_tarea = id_tarea;
        this.fecha_esc_tarea = fecha_esc_tarea;
        this.tarea = tarea;
        this.fecha_presentacion = fecha_presentacion;
        this.tarea_completa = tarea_completa;
        this.fk_usuario = fk_usuario;
        this.tarea_elim = tarea_elim;
    }

    public int getId_tarea() {
        return id_tarea;
    }

    public void setId_tarea(int id_tarea) {
        this.id_tarea = id_tarea;
    }

    public Date getFecha_esc_tarea() {
        return fecha_esc_tarea;
    }

    public void setFecha_esc_tarea(Date fecha_esc_tarea) {
        this.fecha_esc_tarea = fecha_esc_tarea;
    }

    public String getTarea() {
        return tarea;
    }

    public void setTarea(String tarea) {
        this.tarea = tarea;
    }

    public Date getFecha_presentacion() {
        return fecha_presentacion;
    }

    public void setFecha_presentacion(Date fecha_presentacion) {
        this.fecha_presentacion = fecha_presentacion;
    }

    public boolean isTarea_completa() {
        return tarea_completa;
    }

    public void setTarea_completa(boolean tarea_completa) {
        this.tarea_completa = tarea_completa;
    }

    public String getFk_usuario() {
        return fk_usuario;
    }

    public void setFk_usuario(String fk_usuario) {
        this.fk_usuario = fk_usuario;
    }

    public boolean isTarea_elim() {
        return tarea_elim;
    }

    public void setTarea_elim(boolean tarea_elim) {
        this.tarea_elim = tarea_elim;
    }
        
}
