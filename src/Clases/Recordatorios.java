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
public class Recordatorios {
    
    private int id_recordatorio; 
    private Date fecha_esc_record; 
    private String recordatorio; 
    private Date fecha_noti; 
    
    private String fk_usuario; 
    private boolean recordatorio_elim; 
    
    public Recordatorios(){
        
    }

    public Recordatorios(int id_recordatorio, Date fecha_esc_record, String recordatorio, Date fecha_noti, String fk_usuario, boolean recordatorio_elim) {
        this.id_recordatorio = id_recordatorio;
        this.fecha_esc_record = fecha_esc_record;
        this.recordatorio = recordatorio;
        this.fecha_noti = fecha_noti;
        this.fk_usuario = fk_usuario;
        this.recordatorio_elim = recordatorio_elim;
    }

    public int getId_recordatorio() {
        return id_recordatorio;
    }

    public void setId_recordatorio(int id_recordatorio) {
        this.id_recordatorio = id_recordatorio;
    }

    public Date getFecha_esc_record() {
        return fecha_esc_record;
    }

    public void setFecha_esc_record(Date fecha_esc_record) {
        this.fecha_esc_record = fecha_esc_record;
    }

    public String getRecordatorio() {
        return recordatorio;
    }

    public void setRecordatorio(String recordatorio) {
        this.recordatorio = recordatorio;
    }

    public Date getFecha_noti() {
        return fecha_noti;
    }

    public void setFecha_noti(Date fecha_noti) {
        this.fecha_noti = fecha_noti;
    }

    public String getFk_usuario() {
        return fk_usuario;
    }

    public void setFk_usuario(String fk_usuario) {
        this.fk_usuario = fk_usuario;
    }

    public boolean isRecordatorio_elim() {
        return recordatorio_elim;
    }

    public void setRecordatorio_elim(boolean recordatorio_elim) {
        this.recordatorio_elim = recordatorio_elim;
    }
    
}
