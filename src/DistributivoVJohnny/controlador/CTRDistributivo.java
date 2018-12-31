/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DistributivoVJohnny.controlador;

import DistributivoVJohnny.modelo.ClaseDia;
import DistributivoVJohnny.modelo.Clases;
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
    
    private final Distributivo distri;
    //En esta variable guardaremos la poscion al seleccionar un periodo lectivo
    private int posPer;

    //Aqui se guarda todos los docentes
    private ArrayList<Docentes> docentes;
    //Aqui se guardarn todos los periodos lectivos 
    private ArrayList<PeriodoLectivos> periodos;
    //Aqui guardaremos todos los paralelos 
    private ArrayList<Paralelos> paralelos;
    //Aqui guardamos todas las materias de esta carrera.
    private ArrayList<Materias> materias;

    //Aqui guardaremos los unicos paralelos que pasen todos los filtros
    private ArrayList<Paralelos> paralelosFiltrados;
    //Aqui guardaremos las materias de un ciclo especifico
    private ArrayList<Materias> materiasFiltradas;
    //Aqui guardamos los docentes que tienen materias preferentes de un ciclo 
    private ArrayList<Docentes> docentesFiltrados;

    //Todos los paneles que usa el distributivo
    private final PnlClasificar pnlClas = new PnlClasificar();
    private final PnlDocentes pnlDocen = new PnlDocentes();
    private final PnlMaterias pnlMat = new PnlMaterias();
    private final PnlParalelos pnlPara = new PnlParalelos();
    
    public CTRDistributivo(Distributivo distri) {
        this.distri = distri;
        //Hacemos visible el frame de distributivo
        distri.setVisible(true);
    }
    
    public void iniciar() {
        //Le asignamos las funciones que pintan el panel adecuado al btn 
        distri.getBtnClasificar().addActionListener(e -> clasificar());
        distri.getBtnDocentes().addActionListener(e -> docentes());
        distri.getBtnMaterias().addActionListener(e -> materias());
        distri.getBtnParalelos().addActionListener(e -> paralelos());

        //Llamamos a la funcion que da un formato al titulo de las tablas  
        Similitudes.tituloTbls(pnlClas.getTblHorario());
        Similitudes.tituloTbls(pnlClas.getTblParalelosCiclo());
        Similitudes.tituloTbls(pnlClas.getTblParalelosJornada());
        Similitudes.tituloTbls(pnlDocen.getTblDocentes());
        Similitudes.tituloTbls(pnlDocen.getTblMaterias());
        Similitudes.tituloTbls(pnlMat.getTblDocentes());
        Similitudes.tituloTbls(pnlMat.getTblMaterias());
        Similitudes.tituloTbls(pnlPara.getTblJornadas());
        Similitudes.tituloTbls(pnlPara.getTblParalelos());

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

        //Asignamos una accion al combo ciclos  
        pnlClas.getCbCiclo().addActionListener(e -> filtrarParalelosCiclo());
        //Asignamos una accion al combo jornadas  
        pnlClas.getCbJornada().addActionListener(e -> filtrarParalelosJornada());
        //Asignamos una accion al combo paralelos filtrados  
        pnlClas.getCbParalelo().addActionListener(e -> informacionParalelo());

        //Le damos una accion a organizar horario  
        pnlClas.getBtnOrganizarHorario().addActionListener(e -> organizarHorario());
    }
    
    public void clasificar() {
        //Repintamos el panel principal con el pnl de clasificar
        Similitudes.cambioPanel(distri.getPnlPrincipal(), pnlClas);

        //Se llena el combo de jornadas con las constantes  
        pnlClas.getCbJornada().removeAllItems();
        pnlClas.getCbJornada().addItem(" ");
        pnlClas.getCbJornada().addItem("Matutina");
        pnlClas.getCbJornada().addItem("Vespertina");
        pnlClas.getCbJornada().addItem("Nocturna");
    }
    
    public void docentes() {
        //Repintamos el pnl principal con el pnl docente 
        Similitudes.cambioPanel(distri.getPnlPrincipal(), pnlDocen);
    }
    
    public void materias() {
        //Repintamos el panel principal con el panel materias  
        Similitudes.cambioPanel(distri.getPnlPrincipal(), pnlMat);
    }
    
    public void paralelos() {
        //Repintamos el panel principal con el panel paralelos  
        Similitudes.cambioPanel(distri.getPnlPrincipal(), pnlPara);
    }
    
    private void salir() {
        System.exit(0);
    }

    //Con este metodo llenare mi combo de periodos lectivo  
    public void cargarCbPeriodoLectivo() {
        //Antes de cargar el cb de periodos, limpiamos el cb
        distri.getCbPeriodoLectivo().removeAllItems();
        //Le agregamos un item vacio 
        distri.getCbPeriodoLectivo().addItem(" ");
        
        DBPeriodoLectivos per = new DBPeriodoLectivos();
        //Consultamos todos los periodos lectivos en la base de datos
        periodos = per.cargarPeriodoLectivoCarrera();
        //Si periodos no esta vacio, llenamos el combo box con el nombre de la carrera
        //La fecha en que inicia y la fecha en la que termina el periodo
        if (!periodos.isEmpty()) {
            for (int i = 0; i < periodos.size(); i++) {
                //Guardamos en un combo box todo  
                distri.getCbPeriodoLectivo().addItem(periodos.get(i).getCarrera() + " | "
                        + periodos.get(i).getFechaInicio() + " / "
                        + periodos.get(i).getFechaFin());
            }
        }
    }

    //Funcion que se ejcutara al realizar una accion en el combo box periodo lectivo 
    public void actCbPeriodoLectivo() {
        //Se captura la posicion del item en la que se realizo la accion  
        posPer = distri.getCbPeriodoLectivo().getSelectedIndex();
        //Pregintamos si la posicion es mayor a 0 para poder realizar una accion 
        //Si la posicion es mayor a 0 signifca que selecciono un periodo lectivo
        if (posPer > 0) {
            //Llamamos a las funciones que nos actualizan los paneles.
            actParalelos();
            actMaterias();
            actDocentes();
            actClasificar();
        }
    }

    //Llenaremos la tabla de paralelos 
    private void actParalelos() {
        //Le asginamos un modelo a la tabla de paralelos
        String titulo[] = {"Nombre", "Ciclo"};
        String datos[][] = {};
        DefaultTableModel mdParalelos = new DefaultTableModel(datos, titulo);
        pnlPara.getTblParalelos().setModel(mdParalelos);
        
        DBParalelos par = new DBParalelos();
        //Consultamos todos los paralelos de una carrera  
        paralelos = par.cargarParalelosCarrera(periodos.get(posPer - 1).getIdCarrera());

        //Limpiamos el combo box de paralelos 
        pnlPara.getCbParalelo().removeAllItems();
        //Agregamos un espacio vacio
        pnlPara.getCbParalelo().addItem(" ");
        //Llenamos la tabla paralelos
        for (int i = 0; i < paralelos.size(); i++) {
            Object valores[] = {paralelos.get(i).getNombre(),
                paralelos.get(i).getCiclo()};
            mdParalelos.addRow(valores);

            //Llenamos el combo box de paralelos tambien
            pnlPara.getCbParalelo().addItem(paralelos.get(i).getNombre());
        }
        
    }
    
    public void actTblJornadasParalelo() {
        //Aqui guardaremos la posicion del paralelos selecionado 
        int posPar = pnlPara.getCbParalelo().getSelectedIndex();

        //Solo consultaremos las jornadas y paralelos si  se selecciona un paralelo
        if (posPar > 0) {
            DBJornadas prJd = new DBJornadas();
            //Aqui guardamos todos los parlJornada 
            ArrayList<Jornadas> parlJornada = prJd.cargaParalelosJornada(paralelos.get(posPar - 1).getId());
            //Creamos y asignamos un modelo a la tabla. 
            String titulo[] = {"Jornadas"};
            String datos[][] = {};
            DefaultTableModel mdTblJornadas = new DefaultTableModel(datos, titulo);
            pnlPara.getTblJornadas().setModel(mdTblJornadas);
            //Llenamos la tabla de jornadas de un paralelo
            for (int i = 0; i < parlJornada.size(); i++) {
                Object valores[] = {parlJornada.get(i).getDescripcion()};
                mdTblJornadas.addRow(valores);
            }
        }
    }

    //Llenamos la tabla de materias 
    private void actMaterias() {
        //Creamos un modelo y lo asignamos a la tabla
        String titulo[] = {"Nombre", "Ciclo", "Horas/Semana"};
        String datos[][] = {};
        DefaultTableModel mdTblMaterias = new DefaultTableModel(datos, titulo);
        pnlMat.getTblMaterias().setModel(mdTblMaterias);
        
        DBMaterias mtCarrera = new DBMaterias();
        //Consultamos todas las materias de la carrera selecionada.
        materias = mtCarrera.consultarMateriasCarrera(periodos.get(posPer - 1).getIdCarrera());

        //Tambien llenamos el combo box  
        pnlMat.getCbMateria().removeAllItems();
        //Agregamos el espacio vacio  
        pnlMat.getCbMateria().addItem(" ");
        //LLenamos la tabla de materias
        for (int i = 0; i < materias.size(); i++) {
            Object valores[] = {materias.get(i).getNombre(),
                materias.get(i).getCiclo(),
                materias.get(i).getHoras()};
            
            mdTblMaterias.addRow(valores);

            //Llenamos el combo tambien 
            pnlMat.getCbMateria().addItem(materias.get(i).getNombre());
        }
    }

    //Si seleciona una materia se ejecutara este metodo para consultar que docentes tienen esta materia  
    public void actTblMateriasDocente() {
        int posMat = pnlMat.getCbMateria().getSelectedIndex();
        
        if (posMat > 0) {
            //Creamos un modelo y lo pasamos a la tabla
            String titulo[] = {"Cedula", "Nombre"};
            String datos[][] = {};
            DefaultTableModel mdTblDocentes = new DefaultTableModel(datos, titulo);
            pnlMat.getTblDocentes().setModel(mdTblDocentes);

            //Consultamos todos los docentes que tengas preferente esta materia  
            DBDocentes dbMat = new DBDocentes();
            
            ArrayList<Docentes> materiasDocen = dbMat.consultarMateriasDocente(materias.get(posMat - 1).getId());
            
            for (int i = 0; i < materiasDocen.size(); i++) {
                Object valores[] = {materiasDocen.get(i).getCedula(),
                    materiasDocen.get(i).getNombre()};
                
                mdTblDocentes.addRow(valores);
            }
        }
    }

    //Para actulizar el pnl docentes 
    public void actDocentes() {
        //Creamos un modelo y lo pasamos a la tabla
        String titulo[] = {"Cedula", "Nombre"};
        String datos[][] = {};
        DefaultTableModel mdTblDocentes = new DefaultTableModel(datos, titulo);
        pnlDocen.getTblDocentes().setModel(mdTblDocentes);

        //Consultamos los docentes de esta carrera  
        DBDocentes mtDocen = new DBDocentes();
        docentes = mtDocen.consultarDocenteCarrera(periodos.get(posPer - 1).getIdCarrera());

        //Preparamos el combo box docentes para llenarlo  
        pnlDocen.getCbDocente().removeAllItems();
        
        pnlDocen.getCbDocente().addItem(" ");
        
        for (int i = 0; i < docentes.size(); i++) {
            Object valores[] = {docentes.get(i).getCedula(),
                docentes.get(i).getNombre()};
            
            mdTblDocentes.addRow(valores);
            //Tambien llenamos el combo box 
            pnlDocen.getCbDocente().addItem(docentes.get(i).getNombre());
        }
    }

    //Para actualizar la tbl materias al dar selecionar un docente  
    public void actTblMaterias() {
        int posDocen = pnlDocen.getCbDocente().getSelectedIndex();
        
        if (posDocen > 0) {
            //Creamos un modelo y lo pasamos a la tabla
            String titulo[] = {"Materia", "Ciclo", "Horas/Semana"};
            String datos[][] = {};
            DefaultTableModel mdTblMaterias = new DefaultTableModel(datos, titulo);
            pnlDocen.getTblMaterias().setModel(mdTblMaterias);
            
            DBMaterias bdMat = new DBMaterias();
            ArrayList<Materias> mate = bdMat.consultarMateriasDocente(periodos.get(posPer - 1).getIdCarrera(),
                    docentes.get(posDocen - 1).getCedula());
            //Agregramos los datos a la tabla
            for (int i = 0; i < mate.size(); i++) {
                Object valores[] = {mate.get(i).getNombre(),
                    mate.get(i).getCiclo(), mate.get(i).getHoras()};
                
                mdTblMaterias.addRow(valores);
            }
        }
    }
    
    private void actClasificar() {
        //Borramos todos los datos de los cb 
        pnlClas.getCbCiclo().removeAllItems();
        pnlClas.getCbParalelo().removeAllItems();
        //Agregamos un campo vacio  
        pnlClas.getCbCiclo().addItem(" ");
        pnlClas.getCbParalelo().addItem(" ");

        //Vemos el numero de ciclos de esa carrera y llenamos el combo box  
        for (int i = 0; i < periodos.get(posPer - 1).getNumCiclos(); i++) {
            pnlClas.getCbCiclo().addItem((i + 1) + "");
        }
    }

    //Llenamos la tabla con el numero de ciclo que se escoja  
    private void filtrarParalelosCiclo() {
        int ciclo = pnlClas.getCbCiclo().getSelectedIndex();
        //Preguntamos si el indice no es 0 para llenarlo 
        if (ciclo != 0) {
            //Creamos un modelo y lo pasamos a la tabla  
            String titulo[] = {"Paralelo"};
            String datos[][] = {};
            DefaultTableModel mdTblParalelos = new DefaultTableModel(datos, titulo);
            pnlClas.getTblParalelosCiclo().setModel(mdTblParalelos);
            //Filtramos los paralelos de un ciclo 
            for (Paralelos pl : paralelos) {
                for (Jornadas jd : pl.getJornadas()) {
                    if (pl.getCiclo() == ciclo) {
                        Object valores[] = {pl.getNombre()};
                        mdTblParalelos.addRow(valores);
                    }
                }
            }
            //Filtramos las materias de un ciclo  
            materiasFiltradas = new ArrayList<>();
            for (Materias mt : materias) {
                if (mt.getCiclo() == ciclo) {
                    materiasFiltradas.add(mt);
                }
            }
        }
    }

    //Llenamos la tabla de paralelos jornada 
    private void filtrarParalelosJornada() {
        int jornada = pnlClas.getCbJornada().getSelectedIndex();
        int ciclo = pnlClas.getCbCiclo().getSelectedIndex();
        pnlClas.getCbParalelo().removeAllItems();
        pnlClas.getCbParalelo().addItem(" ");

        //Creamos un modelo y lo pasamos a la tabla  
        String titulo[] = {"Paralelo"};
        String datos[][] = {};
        DefaultTableModel mdTblParalelos = new DefaultTableModel(datos, titulo);
        pnlClas.getTblParalelosJornada().setModel(mdTblParalelos);
        //Limpiamos nuestro array de paralelos  
        paralelosFiltrados = new ArrayList<>();

        //Preguntamos si el indice es mayor a 0 para buscar los paralelos 
        if (jornada > 0) {
            //Llenamos nuestra tabla con los paralelos de un ciclo y una jornada selecionada
            for (int i = 0; i < paralelos.size(); i++) {
                for (int j = 0; j < paralelos.get(i).getJornadas().size(); j++) {
                    //Aqui filtramos unicamente los paralelos que tengan un ciclo y una jornada especificos
                    if (paralelos.get(i).getCiclo() == ciclo && paralelos.get(i).getJornadas().get(j).getJornada() == jornada) {
                        Object valores[] = {paralelos.get(i).getNombre()};
                        mdTblParalelos.addRow(valores);
                        paralelosFiltrados.add(paralelos.get(i));
                        //Llenamos el cb de paralelos  
                        pnlClas.getCbParalelo().addItem(paralelos.get(i).getNombre());
                    }
                }
            }
        }
    }
    
    DefaultTableModel mdTblHorarioDatos;

    //Al escoger una materias se crea un modelo para la tabla  
    private void informacionParalelo() {
        int posParFil = pnlClas.getCbParalelo().getSelectedIndex();
        int jornada = pnlClas.getCbJornada().getSelectedIndex();

        //Creamos un modelo para pasarlo a las tablas
        String titulo[] = {"Hora", "Lunes", "Martes", "Miercoles", "Jueves", "Viernes"};
        String datos[][] = {};
        DefaultTableModel mdTblHorarioVacio = new DefaultTableModel(datos, titulo);
        pnlClas.getTblHorario().setModel(mdTblHorarioVacio);

        //Aqui guardaremos la informacion del paralelo 
        int horaInicio = 0;
        int horaFin = 0;
        int horaImpri = 0;
        //Vector en el que se almacenara todas las horas de nuestro sistema  
        int HORAS[] = {7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};
        
        if (posParFil > 0) {
            System.out.println("Nombre de curso: " + paralelosFiltrados.get(posParFil - 1).getNombre());
            System.out.println("Ciclo: " + paralelosFiltrados.get(posParFil - 1).getCiclo());
            
            for (Jornadas jd : paralelosFiltrados.get(posParFil - 1).getJornadas()) {
                if (jd.getJornada() == jornada) {
                    System.out.println("Informacion:");
                    System.out.println("Hora inicio: " + jd.getHoraInicio());
                    System.out.println("Hora fin: " + jd.getHoraFin());
                    System.out.println("Jornada: " + jd.getJornada() + " | " + jd.getDescripcion());
                    horaInicio = jd.getHoraInicio();
                    horaFin = jd.getHoraFin();
                }
            }

            //Pasamos el modelo con la matriz de su horario 
            String campos[][] = new String[horaFin - horaInicio][6];
            mdTblHorarioDatos = new DefaultTableModel(campos, titulo);
            pnlClas.getTblHorario().setModel(mdTblHorarioDatos);
            //Ampliamos las altura de las celdas  
            pnlClas.getTblHorario().setRowHeight(30);
            //Aqui cogemos del vector la hora que corresponde dependiendo de la hora en la que inicia clases
            horaImpri = HORAS[horaInicio - 1];
            
            String horas[] = new String[horaFin - horaInicio];
            for (int i = 0; i < (horaFin - horaInicio); i++) {
                if (horaImpri < 10) {
                    horas[i] = "0" + horaImpri + ":00";
                } else {
                    horas[i] = horaImpri + ":00";
                }
                horaImpri++;
            }
            
            for (int i = 0; i < horas.length; i++) {
                System.out.print(horas[i] + "  ");
                //mdTblHorarioDatos.addColumn(horas); 
                mdTblHorarioDatos.setValueAt(horas[i], i, 0);
            }
            
            int horasClase = (horaFin - horaInicio) * 5;
            pnlClas.getLblHorasClase().setText(horasClase + "");
            
            int numMat = materiasFiltradas.size();
            int horasMat = 0;
            int ciclo = pnlClas.getCbCiclo().getSelectedIndex();
            //Aqui sumamos todas las horas semanales de cada materia de un ciclo
            for (Materias mt : materiasFiltradas) {
                horasMat = horasMat + mt.getHoras();
            }
            //Consutlamos los docentes que tienen preferente las materias de un ciclo 
            //y una carrera concretas  
            docentesFiltrados = new ArrayList<>();
            DBDocentes docen = new DBDocentes();
            docentesFiltrados = docen.consultarDocenteCarreraCiclo(periodos.get(posPer - 1).getIdCarrera(), ciclo);
            //Aqui sumamos el numero de profesores que tienen una materia pref de un ciclo  
            int numDocen = docentesFiltrados.size();
            
            pnlClas.getLblNumMaterias().setText(numMat + "");
            pnlClas.getLblHorasMaterias().setText(horasMat + "");
            pnlClas.getLblNumDocentes().setText(numDocen + "");
        }
        
    }

    //Aqui organizaremos el horario de nuestra materia
    public void organizarHorario() {
        int posPar = pnlClas.getCbParalelo().getSelectedIndex();
        //Si la posicion del paralelos es mayor que 0 significa que se escogio un curso 
        if (posPar > 0) {
            //Numero de materias de esta carrera en este ciclo  
            int cantMat = materiasFiltradas.size();

            //Crearemos las clases  
            ArrayList<Clases> clases = new ArrayList<>();
            int horasClase[];
            int sumHorasClase;
            for (Materias mt : materiasFiltradas) {
                Clases clase = new Clases();
                clase.setMateria(mt);
                
                sumHorasClase = 0;
                
                if (mt.getHoras() <= 4) {
                    clase.setNumDiasClase(2);
                    horasClase = new int[clase.getNumDiasClase()];
                    
                    for (int i = 0; i < horasClase.length; i++) {
                        
                        if (i == horasClase.length - 1) {
                            System.out.println("Estamos en la ultima posicion de la hora");
                            horasClase[i] = mt.getHoras() - sumHorasClase;
                        } else {
                            horasClase[i] = Math.round(mt.getHoras() / clase.getNumDiasClase());
                            sumHorasClase = sumHorasClase + horasClase[i];
                        }
                    }
                    
                    clase.setHorasClaseDia(horasClase);
                } else {
                    clase.setNumDiasClase(3);
                    horasClase = new int[clase.getNumDiasClase()];
                    
                    for (int i = 0; i < horasClase.length; i++) {
                        
                        if (i == horasClase.length - 1) {
                            //System.out.println("Estamos en la ultima posicion de la hora");
                            horasClase[i] = mt.getHoras() - sumHorasClase;
                        } else {
                            horasClase[i] = Math.round(mt.getHoras() / clase.getNumDiasClase());
                            sumHorasClase = sumHorasClase + horasClase[i];
                        }
                    }
                    
                    clase.setHorasClaseDia(horasClase);
                }
                clases.add(clase);
            }
            
            for (int i = 0; i < clases.size(); i++) {
                System.out.println("-----------");
                System.out.println("Nombre materia: " + clases.get(i).getMateria().getNombre());
                System.out.println("Ciclo materia: " + clases.get(i).getMateria().getCiclo());
                System.out.println("Num de dias: " + clases.get(i).getNumDiasClase() + "");
                System.out.println("Horas semana: " + clases.get(i).getMateria().getHoras());
                for (int j = 0; j < clases.get(i).getHorasClaseDia().length; j++) {
                    System.out.print(clases.get(i).getHorasClaseDia()[j] + " | ");
                }
                System.out.println("\n-----------");
            }
            
            ArrayList<ClaseDia> clasesDia = new ArrayList<>();
            
            int lunes = 0, martes = 0, miercoles = 0, jueves = 0, viernes = 0;
            boolean guardado;
            int diaRam;
            String auxDiaG;            
            String diaCom = "";            
            
            for (int i = 0; i < clases.size(); i++) {
                auxDiaG = "";
                for (int j = 0; j < clases.get(i).getNumDiasClase(); j++) {
                    ClaseDia clDia = new ClaseDia();
                    clDia.setMateria(clases.get(i).getMateria());
                    clDia.setNumHoras(clases.get(i).getHorasClaseDia()[j]);
                    
                    guardado = false;
                    do {
                        diaRam = (int) (Math.random() * 5) + 1; 
                    } while (auxDiaG.contains(diaRam + "") || diaCom.contains(diaRam + ""));
                    //System.out.println("Este es el dia premiado de "+diaRam);
                    if (lunes < 6 && diaRam == 1) {
                        auxDiaG = auxDiaG + 1 + "";
                        lunes = lunes + clDia.getNumHoras();                        
                        clDia.setDia(1);
                    } else if (martes < 6 && diaRam == 2) {
                        auxDiaG = auxDiaG + 2 + "";
                        martes = martes + clDia.getNumHoras();                        
                        clDia.setDia(2);
                    } else if (miercoles < 6 && diaRam == 3) {
                        auxDiaG = auxDiaG + 3 + "";
                        miercoles = miercoles + clDia.getNumHoras();                        
                        clDia.setDia(3);
                    } else if (jueves < 6 && diaRam == 4) {
                        auxDiaG = auxDiaG + 4 + "";
                        jueves = jueves + clDia.getNumHoras();                        
                        clDia.setDia(4);
                    } else if (viernes < 6 && diaRam == 5) {
                        auxDiaG = auxDiaG + 5 + "";
                        viernes = viernes + clDia.getNumHoras();                        
                        clDia.setDia(5);
                    }
                    
                    if (lunes == 6) {
                        diaCom = diaCom + 1 + "";
                    }
                    
                    if (martes == 6) {
                        diaCom = diaCom + 2 + "";
                    }
                    if (miercoles == 6) {
                        diaCom = diaCom + 3 + "";
                    }
                    if (jueves == 6) {
                        diaCom = diaCom + 4 + "";
                    }
                    if (viernes == 6) {
                        diaCom = diaCom + 5 + "";
                    }
                    
                    clasesDia.add(clDia);
                }
                
            }
            
            for (ClaseDia clDia : clasesDia) {
                System.out.println("*/*/*/*/*/*/*");
                System.out.println("Clase: " + clDia.getMateria().getNombre());
                System.out.println("Dia: " + clDia.getDia());
                System.out.println("Horas:" + clDia.getNumHoras());
                System.out.println("*/*/*/*/*/*/*");
            }
            
            System.out.println("******************");
            System.out.println("Cantidad de horas que cumplen por dia");
            System.out.println("Lunes: " + lunes + " Martes: " + martes + " Miercoles: " + miercoles + " Jueves: " + jueves + " Viernes: " + viernes);
        }
    }
    
}
