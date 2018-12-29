/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DistributivoVJohnny.modelo;

import java.time.LocalDate;

/**
 *
 * @author Usuario
 */
public class PeriodoLectivoCarrera {
    
    private int id; 
    private String carrera; 
    private LocalDate fechaInicio; 
    private LocalDate fechaFin; 
    private String anio; 
    private int numCiclos; 
    private int idCarrera; 
    
    public PeriodoLectivoCarrera(){
        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }  

    public int getNumCiclos() {
        return numCiclos;
    }

    public void setNumCiclos(int numCiclos) {
        this.numCiclos = numCiclos;
    }

    public int getIdCarrera() {
        return idCarrera;
    }

    public void setIdCarrera(int idCarrera) {
        this.idCarrera = idCarrera;
    }
    
}
