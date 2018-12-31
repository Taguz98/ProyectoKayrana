/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DistributivoVJohnny.controlador;

import DistributivoVJohnny.modelo.Docentes;
import DistributivoVJohnny.modelo.Materias;
import DistributivoVJohnny.modelo.Paralelos;
import java.util.ArrayList;

/**
 *
 * @author Usuario
 */
public class CTRHorario {
    
    private final ArrayList<Paralelos> paralelos; 
    private final ArrayList<Materias> materias; 
    private final ArrayList<Docentes> docentes;

    public CTRHorario(ArrayList<Paralelos> paralelos, ArrayList<Materias> materias, ArrayList<Docentes> docentes) {
        this.paralelos = paralelos;
        this.materias = materias;
        this.docentes = docentes;
    }
    
    
    
}
