/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

/**
 *
 * @author AndresSebastian
 */
public class Pregunta_Seguridad {
    
    private int idPregSeg;
    
    private String pregunta;
    

    public Pregunta_Seguridad(int idPregSeg, String pregunta) {
        this.idPregSeg = idPregSeg;
        this.pregunta = pregunta;
    }

    public int getIdPregSeg() {
        return idPregSeg;
    }

    public void setIdPregSeg(int idPregSeg) {
        this.idPregSeg = idPregSeg;
    }

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }
  
            
}
