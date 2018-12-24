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
public class ParaleloJornada {
    private int id_parl_jord; 
    private int fk_paralelo; 
    private int fk_deta_jord; 
    private boolean parl_jord_elim; 
    
    public ParaleloJornada(){
        
    }

    public ParaleloJornada(int id_parl_jord, int fk_paralelo, int fk_deta_jord, boolean parl_jord_elim) {
        this.id_parl_jord = id_parl_jord;
        this.fk_paralelo = fk_paralelo;
        this.fk_deta_jord = fk_deta_jord;
        this.parl_jord_elim = parl_jord_elim;
    }

    public int getId_parl_jord() {
        return id_parl_jord;
    }

    public void setId_parl_jord(int id_parl_jord) {
        this.id_parl_jord = id_parl_jord;
    }

    public int getFk_paralelo() {
        return fk_paralelo;
    }

    public void setFk_paralelo(int fk_paralelo) {
        this.fk_paralelo = fk_paralelo;
    }

    public int getFk_deta_jord() {
        return fk_deta_jord;
    }

    public void setFk_deta_jord(int fk_deta_jord) {
        this.fk_deta_jord = fk_deta_jord;
    }

    public boolean isParl_jord_elim() {
        return parl_jord_elim;
    }

    public void setParl_jord_elim(boolean parl_jord_elim) {
        this.parl_jord_elim = parl_jord_elim;
    }
    
}
