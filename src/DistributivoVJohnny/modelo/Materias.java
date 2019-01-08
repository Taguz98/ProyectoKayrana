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
public class Materias {

    private int id;
    private String nombre;
    private int horas;
    private int ciclo;
    private int horasMax;

    public Materias() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getHoras() {
        return horas;
    }

    public void setHoras(int horas) {
        this.horas = horas;
    }

    public int getCiclo() {
        return ciclo;
    }

    public void setCiclo(int ciclo) {
        this.ciclo = ciclo;
    }

    public int getHorasMax() {
        return horasMax;
    }

    public void setHorasMax() {
        int horasClase[];
        int sumHorasClase = 0;
        int numDiaClase;
        int horaMayor = 0;

        if (getHoras() <= 4) {
            numDiaClase = 2;
            horasClase = new int[numDiaClase];

            for (int i = 0; i < horasClase.length; i++) {

                if (i == horasClase.length - 1) {
                    horasClase[i] = getHoras() - sumHorasClase;
                } else {
                    horasClase[i] = Math.round(getHoras() / numDiaClase);
                    sumHorasClase = sumHorasClase + horasClase[i];
                }
            }

        } else {
            numDiaClase = 3;
            horasClase = new int[numDiaClase];

            for (int i = 0; i < horasClase.length; i++) {

                if (i == horasClase.length - 1) {
                    horasClase[i] = getHoras() - sumHorasClase;
                } else {
                    horasClase[i] = Math.round(getHoras() / numDiaClase);
                    sumHorasClase = sumHorasClase + horasClase[i];
                }
            }
        }

        for (int i = 0; i < horasClase.length; i++) {
            if (horasClase[i] > horaMayor) {
                horaMayor = horasClase[i];
            }
        }
        
        this.horasMax = horaMayor; 
    }

}
