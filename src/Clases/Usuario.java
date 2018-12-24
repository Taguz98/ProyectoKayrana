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
public abstract class Usuario {
    
    protected String cedulaUsuario;
    
    protected String nickUsuario;
    
    protected String contrasenaUsuario;
    
    protected String correoUsuario;
    
    protected char sexoUsuario;
    
    protected String respSeg1;
    
    protected String respSeg2;
    
    protected Date fecNacUsuario;
    
    protected String nombreUsuario;
    
    protected String apellidoUsuario;
    
    protected int idTipoUsuario;
    
    protected int idPregSeg1;
 
    protected int idPregSeg2;
    
    protected boolean cuentActiva;
    
    public Usuario(){
        
    }

    public Usuario(String cedulaUsuario, String nickUsuario, String contrasenaUsuario, String correoUsuario, char sexoUsuario, String respSeg1, String respSeg2, Date fecNacUsuario, String nombreUsuario, String apellidoUsuario, int idTipoUsuario, int idPregSeg1, int idPregSeg2, boolean cuentActiva) {
        this.cedulaUsuario = cedulaUsuario;
        this.nickUsuario = nickUsuario;
        this.contrasenaUsuario = contrasenaUsuario;
        this.correoUsuario = correoUsuario;
        this.sexoUsuario = sexoUsuario;
        this.respSeg1 = respSeg1;
        this.respSeg2 = respSeg2;
        this.fecNacUsuario = fecNacUsuario;
        this.nombreUsuario = nombreUsuario;
        this.apellidoUsuario = apellidoUsuario;
        this.idTipoUsuario = idTipoUsuario;
        this.idPregSeg1 = idPregSeg1;
        this.idPregSeg2 = idPregSeg2;
        this.cuentActiva = cuentActiva;
    }

    public String getCedulaUsuario() {
        return cedulaUsuario;
    }

    public void setCedulaUsuario(String cedulaUsuario) {
        this.cedulaUsuario = cedulaUsuario;
    }

    public String getNickUsuario() {
        return nickUsuario;
    }

    public void setNickUsuario(String nickUsuario) {
        this.nickUsuario = nickUsuario;
    }

    public String getContrasenaUsuario() {
        return contrasenaUsuario;
    }

    public void setContrasenaUsuario(String contrasenaUsuario) {
        this.contrasenaUsuario = contrasenaUsuario;
    }

    public String getCorreoUsuario() {
        return correoUsuario;
    }

    public void setCorreoUsuario(String correoUsuario) {
        this.correoUsuario = correoUsuario;
    }

    public char getSexoUsuario() {
        return sexoUsuario;
    }

    public void setSexoUsuario(char sexoUsuario) {
        this.sexoUsuario = sexoUsuario;
    }

    public String getRespSeg1() {
        return respSeg1;
    }

    public void setRespSeg1(String respSeg1) {
        this.respSeg1 = respSeg1;
    }

    public String getRespSeg2() {
        return respSeg2;
    }

    public void setRespSeg2(String respSeg2) {
        this.respSeg2 = respSeg2;
    }

    public Date getFecNacUsuario() {
        return fecNacUsuario;
    }

    public void setFecNacUsuario(Date fecNacUsuario) {
        this.fecNacUsuario = fecNacUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getApellidoUsuario() {
        return apellidoUsuario;
    }

    public void setApellidoUsuario(String apellidoUsuario) {
        this.apellidoUsuario = apellidoUsuario;
    }

    public int getIdTipoUsuario() {
        return idTipoUsuario;
    }

    public void setIdTipoUsuario(int idTipoUsuario) {
        this.idTipoUsuario = idTipoUsuario;
    }

    public int getIdPregSeg1() {
        return idPregSeg1;
    }

    public void setIdPregSeg1(int idPregSeg1) {
        this.idPregSeg1 = idPregSeg1;
    }

    public int getIdPregSeg2() {
        return idPregSeg2;
    }

    public void setIdPregSeg2(int idPregSeg2) {
        this.idPregSeg2 = idPregSeg2;
    }

    public boolean isCuentActiva() {
        return cuentActiva;
    }

    public void setCuentActiva(boolean cuentActiva) {
        this.cuentActiva = cuentActiva;
    }

    

    

   
    
    
    

    
}
