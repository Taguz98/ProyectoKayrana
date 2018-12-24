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
public class Validaciones {

    public Validaciones() {

    }
    
    public boolean esContrasena(String entrada){
        boolean  esContra = true; 
        if(!entrada.matches("[A-Za-z0-9]+")){
            esContra = false; 
        }
        
        return esContra;
    }

    public boolean esNumero(String entrada) {
        boolean esNumero = true;
        entrada = entrada.trim();
        if (!entrada.matches("[0-9]+")) {
            esNumero = false;
        }

        return esNumero;
    }

    public boolean esLetras(String entrada) {
        boolean esLetras = true;
        if (!entrada.matches("[A-Za-záéíóúAÉÍÓÚÑñ\\s]+")) {
            esLetras = false;
        }
        return esLetras;
    }

    public boolean esDia(String entrada) {
        boolean esDias = true;
        entrada = entrada.toUpperCase();

        if (!entrada.contains("LUNES") || !entrada.contains("MARTES")
                || !entrada.contains("MIERCOLES") || !entrada.contains("JUEVES")
                || !entrada.contains("VIERNES") || !entrada.contains("SABADO")) {

            esDias = false;
        }

        return esDias;
    }
    
    public boolean esFecha(String entrada){
        boolean esFecha = true; 
        entrada = entrada.trim(); 
        
        if(!entrada.matches("[0-9]{2}/[0-9]{2}/[0-9]{4}$")){
            esFecha = false; 
        }      
        return esFecha; 
    }
    
    public boolean esAnio(String entrada){
        boolean esAnio = true; 
        entrada = entrada.trim(); 
        if(!entrada.matches("[0-9]{4}")){
            esAnio = false; 
        }
        return esAnio; 
    }
    
    public boolean esCedula(String entrada){
        boolean esCedula = true; 
        entrada = entrada.trim(); 
        
        if(!entrada.matches("[0-9]{10}")){
            esCedula = false;
        }
        return esCedula; 
    }
    
    public boolean esCorreo(String entrada){
        boolean esCorreo = true;
        if (!entrada.matches("[A-Za-z0-9]+@[a-z]+\\.+[a-z]+$")) {
            esCorreo = false;
        }
        return esCorreo; 
    }

}
