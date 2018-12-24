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
public class ActividadesDocente {

    private int id_actividad_docen;
    private int fk_actividad;
    private String fk_docente;

    private boolean act_donce_elim;

    public ActividadesDocente() {

    }

    public ActividadesDocente(int id_actividad_docen, int fk_actividad, String fk_docente, boolean act_donce_elim) {
        this.id_actividad_docen = id_actividad_docen;
        this.fk_actividad = fk_actividad;
        this.fk_docente = fk_docente;
        this.act_donce_elim = act_donce_elim;
    }

    public int getId_actividad_docen() {
        return id_actividad_docen;
    }

    public void setId_actividad_docen(int id_actividad_docen) {
        this.id_actividad_docen = id_actividad_docen;
    }

    public int getFk_actividad() {
        return fk_actividad;
    }

    public void setFk_actividad(int fk_actividad) {
        this.fk_actividad = fk_actividad;
    }

    public String getFk_docente() {
        return fk_docente;
    }

    public void setFk_docente(String fk_docente) {
        this.fk_docente = fk_docente;
    }

    public boolean isAct_donce_elim() {
        return act_donce_elim;
    }

    public void setAct_donce_elim(boolean act_donce_elim) {
        this.act_donce_elim = act_donce_elim;
    }

}
