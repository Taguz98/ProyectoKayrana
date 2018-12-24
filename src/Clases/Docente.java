/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.util.Date;

/**
 *
 * @author AndresSebastian
 */
public class Docente extends Usuario {
    
    private int idDocente;
    
    private String tituloDocente;
    
    public Docente(){
        
    }

    public Docente(int idDocente, String tituloDocente, String cedulaUsuario, String nickUsuario, String contrasenaUsuario, String correoUsuario, char sexoUsuario, String respSeg1, String respSeg2, Date fecNacUsuario, String nombreUsuario, String apellidoUsuario, int idTipoUsuario, int idPregSeg1, int idPregSeg2, boolean cuentActiva) {
        super(cedulaUsuario, nickUsuario, contrasenaUsuario, correoUsuario, sexoUsuario, respSeg1, respSeg2, fecNacUsuario, nombreUsuario, apellidoUsuario, idTipoUsuario, idPregSeg1, idPregSeg2, cuentActiva);
        this.idDocente = idDocente;
        this.tituloDocente = tituloDocente;
    }
    
    

    public Docente(String tituloDocente, String cedulaUsuario, String nickUsuario, String contrasenaUsuario, String correoUsuario, char sexoUsuario, String respSeg1, String respSeg2, Date fecNacUsuario, String nombreUsuario, String apellidoUsuario, int idTipoUsuario, int idPregSeg1, int idPregSeg2, boolean cuentActiva) {
        super(cedulaUsuario, nickUsuario, contrasenaUsuario, correoUsuario, sexoUsuario, respSeg1, respSeg2, fecNacUsuario, nombreUsuario, apellidoUsuario, idTipoUsuario, idPregSeg1, idPregSeg2, cuentActiva);

        this.tituloDocente = tituloDocente;
    }

    

    public int getIdDocente() {
        return idDocente;
    }

    @Override
    public String getCedulaUsuario() {
        return cedulaUsuario;
    }

    @Override
    public void setCedulaUsuario(String cedulaUsuario) {
        this.cedulaUsuario = cedulaUsuario;
    }

    @Override
    public String getNickUsuario() {
        return nickUsuario;
    }

    @Override
    public void setNickUsuario(String nickUsuario) {
        this.nickUsuario = nickUsuario;
    }

    @Override
    public String getContrasenaUsuario() {
        return contrasenaUsuario;
    }

    @Override
    public void setContrasenaUsuario(String contrasenaUsuario) {
        this.contrasenaUsuario = contrasenaUsuario;
    }

    @Override
    public String getCorreoUsuario() {
        return correoUsuario;
    }

    @Override
    public void setCorreoUsuario(String correoUsuario) {
        this.correoUsuario = correoUsuario;
    }

    @Override
    public char getSexoUsuario() {
        return sexoUsuario;
    }

    @Override
    public void setSexoUsuario(char sexoUsuario) {
        this.sexoUsuario = sexoUsuario;
    }

    @Override
    public String getRespSeg1() {
        return respSeg1;
    }

    @Override
    public void setRespSeg1(String respSeg1) {
        this.respSeg1 = respSeg1;
    }

    @Override
    public String getRespSeg2() {
        return respSeg2;
    }

    @Override
    public void setRespSeg2(String respSeg2) {
        this.respSeg2 = respSeg2;
    }

    @Override
    public Date getFecNacUsuario() {
        return fecNacUsuario;
    }

    @Override
    public void setFecNacUsuario(Date fecNacUsuario) {
        this.fecNacUsuario = fecNacUsuario;
    }

    @Override
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    @Override
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    @Override
    public String getApellidoUsuario() {
        return apellidoUsuario;
    }

    @Override
    public void setApellidoUsuario(String apellidoUsuario) {
        this.apellidoUsuario = apellidoUsuario;
    }

    @Override
    public int getIdTipoUsuario() {
        return idTipoUsuario;
    }

    @Override
    public void setIdTipoUsuario(int idTipoUsuario) {
        this.idTipoUsuario = idTipoUsuario;
    }

    @Override
    public int getIdPregSeg1() {
        return idPregSeg1;
    }

    @Override
    public void setIdPregSeg1(int idPregSeg1) {
        this.idPregSeg1 = idPregSeg1;
    }

    @Override
    public int getIdPregSeg2() {
        return idPregSeg2;
    }

    @Override
    public void setIdPregSeg2(int idPregSeg2) {
        this.idPregSeg2 = idPregSeg2;
    }

    @Override
    public boolean isCuentActiva() {
        return cuentActiva;
    }

    @Override
    public void setCuentActiva(boolean cuentActiva) {
        this.cuentActiva = cuentActiva;
    }


    public String getTituloDocente() {
        return tituloDocente;
    }

    public void setTituloDocente(String tituloDocente) {
        this.tituloDocente = tituloDocente;
    }

    
}
