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
public class JornadaDocente {
    private int id_jornada_docen; 
    private int fk_deta_jornd; 
    private String fk_docen_insti; 
    
    private boolean docen_jord_elim; 
    
    public JornadaDocente(){
        
    }

    public JornadaDocente(int id_jornada_docen, int fk_deta_jornd, String fk_docen_insti, boolean docen_jord_elim) {
        this.id_jornada_docen = id_jornada_docen;
        this.fk_deta_jornd = fk_deta_jornd;
        this.fk_docen_insti = fk_docen_insti;
        this.docen_jord_elim = docen_jord_elim;
    }

    public int getId_jornada_docen() {
        return id_jornada_docen;
    }

    public void setId_jornada_docen(int id_jornada_docen) {
        this.id_jornada_docen = id_jornada_docen;
    }

    public int getFk_deta_jornd() {
        return fk_deta_jornd;
    }

    public void setFk_deta_jornd(int fk_deta_jornd) {
        this.fk_deta_jornd = fk_deta_jornd;
    }

    public String getFk_docen_insti() {
        return fk_docen_insti;
    }

    public void setFk_docen_insti(String fk_docen_insti) {
        this.fk_docen_insti = fk_docen_insti;
    }

    public boolean isDocen_jord_elim() {
        return docen_jord_elim;
    }

    public void setDocen_jord_elim(boolean docen_jord_elim) {
        this.docen_jord_elim = docen_jord_elim;
    }
    
    
    
}
