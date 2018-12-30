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
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

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

            for (Paralelos pl : paralelos) {
                if (pl.getCiclo() == ciclo) {
                    Object valores[] = {pl.getNombre()};
                    mdTblParalelos.addRow(valores);
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

    //Al escoger una materias se crea un modelo para la tabla  
    private void informacionParalelo() {
        int posParFil = pnlClas.getCbParalelo().getSelectedIndex();
        int jornada = pnlClas.getCbJornada().getSelectedIndex();

        //Creamos un modelo para pasarlo a las tablas
        String titulo[] = {"Hora", "Lunes", "Martes", "Miercoles", "Jueves", "Viernes"};
        //String titulo[] = {"Hora", "Lunes", "Martes", "Miercoles", "Jueves", "Viernes"};
        String datos[][] = {};
        DefaultTableModel mdTblHorarioVacio = new DefaultTableModel(datos, titulo);
        pnlClas.getTblHorario().setModel(mdTblHorarioVacio);

        //Aqui guardaremos la informacion del paralelo 
        int horaInicio = 0;
        int horaFin = 0;
        int HORAS = 0;

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
            DefaultTableModel mdTblHorarioDatos = new DefaultTableModel(campos, titulo);
            pnlClas.getTblHorario().setModel(mdTblHorarioDatos);
            //Ampliamos las celdas  
            pnlClas.getTblHorario().setRowHeight(30); 
            
            if (horaInicio == 1) {
                HORAS = 7;
            } else if (horaInicio == 8) {
                HORAS = 14;
            }
            System.out.println("Cantidad: " + (horaFin - (horaInicio - 1)));
            String horas[] = new String[horaFin - horaInicio];
            for (int i = 0; i < (horaFin - horaInicio); i++) {
                if (HORAS < 10) {
                    horas[i] = "0" + HORAS + ":00";
                } else {
                    horas[i] = HORAS + ":00";
                }
                HORAS++;
            }

            for (int i = 0; i < horas.length; i++) {
                System.out.print(horas[i] + "  ");
                //mdTblHorarioDatos.addColumn(horas); 
                mdTblHorarioDatos.setValueAt(horas[i], i, 0);
            }

        }

    }

}
