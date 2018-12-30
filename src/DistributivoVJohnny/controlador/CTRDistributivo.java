/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DistributivoVJohnny.controlador;

import DistributivoVJohnny.modelo.DBMaterias;
import DistributivoVJohnny.modelo.DBDocentes;
import DistributivoVJohnny.modelo.DBParalelos;
import DistributivoVJohnny.modelo.DBJornadas;
import DistributivoVJohnny.modelo.DBPeriodoLectivos;
import DistributivoVJohnny.modelo.Materias;
import DistributivoVJohnny.modelo.Docentes;
import DistributivoVJohnny.modelo.Paralelos;
import DistributivoVJohnny.modelo.Jornadas;
import DistributivoVJohnny.modelo.PeriodoLectivos;
import DistributivoVJohnny.vista.Distributivo;
import DistributivoVJohnny.vista.PnlClasificar;
import DistributivoVJohnny.vista.PnlDocentes;
import DistributivoVJohnny.vista.PnlMaterias;
import DistributivoVJohnny.vista.PnlParalelos;
import Interfaz.Administrador.Similitudes;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Usuario
 */
public class CTRDistributivo {

    public Distributivo distri;

    public CTRDistributivo(Distributivo distri) {
        this.distri = distri;

        //Hacemos visible el frame de distributivo
        distri.setVisible(true);
    }

    public void iniciar() {

        //Le asignamos una accion a cada btn  
        distri.getBtnClasificar().addActionListener(e -> clasificar());
        distri.getBtnDocentes().addActionListener(e -> docentes());
        distri.getBtnMaterias().addActionListener(e -> materias());
        distri.getBtnParalelos().addActionListener(e -> paralelos());

        //Asignamos una accion al btn salir  
        distri.getBtnSalir().addActionListener(e -> salir());

        //Cargamos el cb periodos lectivo  
        cargarCbPeriodoLectivo();

        //Asignamos una accion al combo box periodo lectivo  
        distri.getCbPeriodoLectivo().addActionListener(e -> actCbPeriodoLectivo());
        
        //Asignamos una accion al combo box paralelos  
        pnlPara.getCbParalelo().addActionListener(e -> actTblJornadasParalelo()); 
        
        //Asiganamos una accion al combo materias  
        pnlMat.getCbMateria().addActionListener(e -> actTblMateriasDocente()); 
        
        //Asignamos una accion al combo docentes  
        pnlDocen.getCbDocente().addActionListener(e -> actTblMaterias()); 
    }

    PnlClasificar pnlClas = new PnlClasificar();

    public void clasificar() {
        //Repintamos el panel principal con el pnl de clasificar
        Similitudes.cambioPanel(distri.getPnlPrincipal(), pnlClas);
    }

    PnlDocentes pnlDocen = new PnlDocentes();

    public void docentes() {
        //Repintamos el pnl principal con el pnl docente 
        Similitudes.cambioPanel(distri.getPnlPrincipal(), pnlDocen);
    }

    PnlMaterias pnlMat = new PnlMaterias();

    public void materias() {
        //Repintamos el panel principal con el panel materias  
        Similitudes.cambioPanel(distri.getPnlPrincipal(), pnlMat);
    }

    PnlParalelos pnlPara = new PnlParalelos();

    public void paralelos() {
        //Repintamos el panel principal con el panel paralelos  
        Similitudes.cambioPanel(distri.getPnlPrincipal(), pnlPara);
    }

    private void salir() {
        System.exit(0);
    }
    //Aqui se guardarn todos los periodos lectivos 
    ArrayList<PeriodoLectivos> periodos;

    //Con este metodo llenare mi combo de periodos lectivo  
    public void cargarCbPeriodoLectivo() {
        distri.getCbPeriodoLectivo().removeAllItems();

        distri.getCbPeriodoLectivo().addItem(" ");

        DBPeriodoLectivos per = new DBPeriodoLectivos();

        periodos = per.cargarPeriodoLectivoCarrera();

        if (!periodos.isEmpty()) {
            //System.out.println("Imprimiremos todos los periodos lectivos");
            for (int i = 0; i < periodos.size(); i++) {
                /*
                System.out.println(periodos.get(i).getCarrera() + "\n"
                        + periodos.get(i).getFechaInicio() + "\n"
                        + periodos.get(i).getFechaFin() + "\n"
                        + periodos.get(i).getNumCiclos() + "");*/

                //Guardamos en un combo box todo  
                distri.getCbPeriodoLectivo().addItem(periodos.get(i).getCarrera() + " | "
                        + periodos.get(i).getFechaInicio() + " / "
                        + periodos.get(i).getFechaFin());
            }
        }
    }

    private int pos;

    //Funcion que se ejcutara al realizar una accion en el combo box periodo lectivo 
    public void actCbPeriodoLectivo() {
        System.out.println("Realizamos una accion en el cobo box");

        pos = distri.getCbPeriodoLectivo().getSelectedIndex();

        System.out.println("Esta es la posicion del item seleccionado " + pos);
        //Pregintamos si la posicion es mayor a 0 para poder realizar una accion 
        if (pos > 0) {
            System.out.println("Esta es la carrera: " + periodos.get(pos - 1).getCarrera());

            actParalelos();
            actMaterias();
            actDocentes();
        }
    }

    //Aqui guardaremos todos los paralelos 
    ArrayList<Paralelos> paralelos;

    //Llenaremos la tabla de paralelos 
    private void actParalelos() {
        //Llenamos la tabla paralelos

        String titulo[] = {"Nombre", "Ciclo"};
        String datos[][] = {};

        DefaultTableModel mdParalelos = new DefaultTableModel(datos, titulo);

        pnlPara.getTblParalelos().setModel(mdParalelos);

        DBParalelos par = new DBParalelos();

        //Consultamos todos los paralelos de una carrera  
        paralelos = par.cargarParalelosCarrera(periodos.get(pos - 1).getIdCarrera());

        //Limpiamos el combo box de paralelos 
        pnlPara.getCbParalelo().removeAllItems();
        //Agregamos un espacio vacio
        pnlPara.getCbParalelo().addItem(" ");

        for (int i = 0; i < paralelos.size(); i++) {
            Object valores[] = {paralelos.get(i).getNombre(),
                paralelos.get(i).getCiclo()};
            mdParalelos.addRow(valores);

            //Llenamos el combo box de paralelos tambien
            pnlPara.getCbParalelo().addItem(paralelos.get(i).getNombre());
        }

    }

    public void actTblJornadasParalelo() {
        //System.out.println("Realizamos una accion en combo paralelo");
        
        //Aqui guardaremos la posicion del paralelos selecionado 
        int posPar = pnlPara.getCbParalelo().getSelectedIndex();

        //Solo consultaremos las jronadas y paralelos si  se selecciona un paralelo
        if (posPar > 0) {
            
            //System.out.println("Este es el id "+paralelos.get(posPar - 1).getId());

            DBJornadas prJd = new DBJornadas();
            //Aqui guardamos todos los parlJornada 
            ArrayList<Jornadas> parlJornada = prJd.cargaParalelosJornada(paralelos.get(posPar - 1).getId());

            String titulo[] = {"Jornadas"};
            String datos[][] = {};

            DefaultTableModel mdTblJornadas = new DefaultTableModel(datos, titulo);
            
            pnlPara.getTblJornadas().setModel(mdTblJornadas); 
            
            for (int i = 0; i < parlJornada.size(); i++) {
                
                Object valores [] = {parlJornada.get(i).getDescripcion()}; 
                
                mdTblJornadas.addRow(valores); 
            }
        }

    }
    
    ArrayList<Materias> materias;
    
    //Llenamos la tabla de materias 
    private void actMaterias(){ 
        String titulo [] = {"Nombre", "Ciclo", "Horas/Semana"};
        String datos [][] = {}; 
        
        DefaultTableModel mdTblMaterias = new DefaultTableModel(datos, titulo); 
        
        pnlMat.getTblMaterias().setModel(mdTblMaterias); 
        
        DBMaterias mtCarrera = new DBMaterias(); 
        
        materias = mtCarrera.consultarMateriasCarrera(periodos.get(pos - 1).getIdCarrera()); 
        
        //Tambien llenamos el combo box  
        pnlMat.getCbMateria().removeAllItems(); 
        //Agregamos el espacio vacio  
        pnlMat.getCbMateria().addItem(" ");
        
        for (int i = 0; i < materias.size(); i++) {
            Object valores [] = {materias.get(i).getNombre(), 
            materias.get(i).getCiclo(), 
            materias.get(i).getHoras()};
            
            mdTblMaterias.addRow(valores); 
            
            //Llenamos el combo tambien 
            pnlMat.getCbMateria().addItem(materias.get(i).getNombre());
        }
        
    }
    
    //Si seleciona una materia se ejecutara este metodo para consulatr que docentes tienen esta materia  
    
    public void actTblMateriasDocente(){ 
        int posMat = pnlMat.getCbMateria().getSelectedIndex(); 
        
        if (posMat > 0) {
            
            String titulo [] = {"Cedula", "Nombre"}; 
            String datos [][] = {}; 
            
            DefaultTableModel mdTblDocentes = new DefaultTableModel(datos, titulo); 
            
            pnlMat.getTblDocentes().setModel(mdTblDocentes); 
            
            //Consultamos todos los docentes que tengas preferente esta materia  
            DBDocentes dbMat = new DBDocentes(); 
            
            ArrayList<Docentes> materiasDocen = dbMat.consultarMateriasDocente(materias.get(posMat - 1).getId()); 
            
            for (int i = 0; i < materiasDocen.size(); i++) {
                Object valores [] = {materiasDocen.get(i).getCedula(), 
                materiasDocen.get(i).getNombre()};
                
                mdTblDocentes.addRow(valores); 
            }   
        }
    }
    
    ArrayList<Docentes> docentes;
    
    //Para actulizar el pnl docentes 
    public void actDocentes(){ 
        
        String titulo [] = {"Cedula", "Nombre"};
        String datos [][] = {}; 
        
        DefaultTableModel mdTblDocentes = new DefaultTableModel(datos, titulo);
        
        pnlDocen.getTblDocentes().setModel(mdTblDocentes); 
        
        //Consultamos los docentes de esta carrera  
        DBDocentes mtDocen = new DBDocentes(); 
        docentes = mtDocen.consultarDocenteCarrera(periodos.get(pos - 1).getIdCarrera()); 
        
        //Preparamos el combo box docentes para llenarlo  
        pnlDocen.getCbDocente().removeAllItems(); 
        
        pnlDocen.getCbDocente().addItem(" ");
        
        for (int i = 0; i < docentes.size(); i++) {
            Object valores [] = {docentes.get(i).getCedula(), 
            docentes.get(i).getNombre()};
            
            mdTblDocentes.addRow(valores); 
            //Tambien llenamos el combo box 
            pnlDocen.getCbDocente().addItem(docentes.get(i).getNombre());
        }
    }
    
    //Para actualizar la tbl materias al dar selecionar un docente  
    
    public void actTblMaterias(){
        int posDocen = pnlDocen.getCbDocente().getSelectedIndex(); 
        
        if (posDocen > 0) {
            String titulo [] = {"Materia", "Ciclo", "Horas/Semana"}; 
            String datos [][] = {}; 
            
            DefaultTableModel mdTblMaterias = new DefaultTableModel(datos, titulo); 
            
            pnlDocen.getTblMaterias().setModel(mdTblMaterias); 
            
            DBMaterias bdMat = new DBMaterias(); 
            ArrayList<Materias> mate = bdMat.consultarMateriasDocente(periodos.get(pos - 1).getIdCarrera(), 
                    docentes.get(posDocen - 1).getCedula());  
            
            for (int i = 0; i < mate.size(); i++) {
                Object valores [] = {mate.get(i).getNombre(), 
                mate.get(i).getCiclo(), mate.get(i).getHoras()};
                
                mdTblMaterias.addRow(valores); 
            }
        }
    }
    
}
