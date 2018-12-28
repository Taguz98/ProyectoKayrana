/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DistributivoVJohnny.controlador;

import DistributivoVJohnny.vista.Distributivo;
import DistributivoVJohnny.vista.PnlClasificar;
import DistributivoVJohnny.vista.PnlDocentes;
import DistributivoVJohnny.vista.PnlMaterias;
import DistributivoVJohnny.vista.PnlParalelos;
import Interfaz.Administrador.Similitudes;

/**
 *
 * @author Usuario
 */
public class CTRDistributivo {
    
    public Distributivo distri; 
    
    public CTRDistributivo(Distributivo distri){
        this.distri = distri; 
        
        //Hacemos visible el frame de distributivo
        distri.setVisible(true); 
    }
    
    public void iniciar(){
        
        //Le asignamos una accion a cada btn  
        distri.getBtnClasificar().addActionListener(e -> clasificar()); 
        distri.getBtnDocentes().addActionListener(e -> docentes()); 
        distri.getBtnMaterias().addActionListener(e -> materias());
        distri.getBtnParalelos().addActionListener(e -> paralelos()); 
        
        //Asignamos una accion al btn salir  
        distri.getBtnSalir().addActionListener(e -> salir()); 
    }
    
    public void clasificar(){ 
        PnlClasificar pnlClas = new PnlClasificar(); 
        
        //Repintamos el panel principal con el pnl de clasificar
        Similitudes.cambioPanel(distri.getPnlPrincipal(), pnlClas);
    }
    
    public void docentes(){ 
        PnlDocentes pnlDocen = new PnlDocentes(); 
        
        //Repintamos el pnl principal con el pnl docente 
        Similitudes.cambioPanel(distri.getPnlPrincipal(), pnlDocen);
    }
    
    public void materias(){ 
        PnlMaterias pnlMat = new PnlMaterias(); 
        
        //Repintamos el panel principal con el panel materias  
        Similitudes.cambioPanel(distri.getPnlPrincipal(), pnlMat);
    }
    
    public void paralelos(){ 
        PnlParalelos pnlPara = new PnlParalelos(); 
        
        //Repintamos el panel principal con el panel paralelos  
        Similitudes.cambioPanel(distri.getPnlPrincipal(), pnlPara);
    }
    
    private void salir(){ 
        System.exit(0); 
    }
    
    
}
