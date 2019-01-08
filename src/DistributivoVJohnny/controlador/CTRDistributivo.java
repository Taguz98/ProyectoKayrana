/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DistributivoVJohnny.controlador;

import DistributivoVJohnny.modelo.Clase;
import DistributivoVJohnny.modelo.ClaseDia;
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
import DistributivoVJohnny.vista.PnlInformacion;
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
    private final PnlInformacion pnlInfo = new PnlInformacion();

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
        distri.getBtnInformacion().addActionListener(e -> informacion());

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
        Similitudes.tituloTbls(pnlInfo.getTblDocentesSelecionados());
        Similitudes.tituloTbls(pnlInfo.getTblHorarioOrdenado());

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
        //Le damos una accion a ordenar horario  
        pnlClas.getBtnOrdenarHorario().addActionListener(e -> ordenarHorario());
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

    //Aqui guardamos  todas las materias por dia 
    ArrayList<Clase> clasesLunes;
    ArrayList<Clase> clasesMartes;
    ArrayList<Clase> clasesMiercoles;
    ArrayList<Clase> clasesJueves;
    ArrayList<Clase> clasesViernes;

    ArrayList<ClaseDia> clasesDia;

    //Aqui organizaremos el horario de nuestra materia
    public void organizarHorario() {
        clasesLunes = new ArrayList<>();
        clasesMartes = new ArrayList<>();
        clasesMiercoles = new ArrayList<>();
        clasesJueves = new ArrayList<>();
        clasesViernes = new ArrayList<>();

        clasesDia = new ArrayList<>();

        int posPar = pnlClas.getCbParalelo().getSelectedIndex();
        //Si la posicion del paralelos es mayor que 0 significa que se escogio un curso 
        if (posPar > 0) {
            //Numero de materias de esta carrera en este ciclo  

            //Todas las clases que tendra en una semana  
            ArrayList<Clase> clasesSemana = new ArrayList<>();
            System.out.println("\n#################################");
            for (Materias mt : materiasFiltradas) {
                mt.setHorasMax();
                System.out.println("Horas totales: " + mt.getHoras() + " Hora mayor: " + mt.getHorasMax() + "  |  " + mt.getNombre());
                for (int i = 0; i < mt.getHoras(); i++) {
                    Clase c = new Clase();
                    c.setMateria(mt);
                    c.setHoras(1);
                    clasesSemana.add(c);
                }
            }
            System.out.println("#################################");

            int posClase;

            for (int i = 1; i < mdTblHorarioDatos.getColumnCount(); i++) {
                for (int j = 0; j < mdTblHorarioDatos.getRowCount(); j++) {

                    posClase = (int) (Math.random() * clasesSemana.size());

                    mdTblHorarioDatos.setValueAt(clasesSemana.get(posClase).getMateria().getNombre(), j, i);

                    switch (i) {
                        case 1:
                            clasesLunes.add(clasesSemana.get(posClase));
                            break;
                        case 2:
                            clasesMartes.add(clasesSemana.get(posClase));
                            break;
                        case 3:
                            clasesMiercoles.add(clasesSemana.get(posClase));
                            break;
                        case 4:
                            clasesJueves.add(clasesSemana.get(posClase));
                            break;
                        case 5:
                            clasesViernes.add(clasesSemana.get(posClase));
                            break;
                        default:
                            System.out.println("No se encontro posicion para esta materia");
                            System.out.println("Materia: " + clasesSemana.get(posClase).getMateria().getNombre());
                            break;
                    }

                    clasesSemana.remove(posClase);

                }
            }

        }
    }

    //Ordenar la tabla 
    private void ordenarHorario() {

        System.out.println("----------------------------------------------");
        System.out.println("Ordenamos el dia lunes:");

        Clase c;
        int fila = 0;

        do {
            if (clasesLunes.size() > 0) {
                System.out.println(clasesLunes.get(0).getMateria().getNombre());
                c = clasesLunes.get(0);
                clasesLunes.remove(0);

                ClaseDia clDia = new ClaseDia();
                clDia.setDia(1);
                clDia.setMateria(c.getMateria());
                clDia.setNumHoras(c.getHoras());

                mdTblHorarioDatos.setValueAt(c.getMateria().getNombre(), fila, 1);
                fila++;

                for (int i = 0; i < clasesLunes.size(); i++) {
                    if (clasesLunes.get(i).getMateria().equals(c.getMateria())) {
                        System.out.println(clasesLunes.get(i).getMateria().getNombre());

                        mdTblHorarioDatos.setValueAt(clasesLunes.get(i).getMateria().getNombre(), fila, 1);
                        fila++;

                        clasesLunes.remove(i);
                        clDia.setNumHoras(clDia.getNumHoras() + 1);
                        /*
                        if (clDia.getNumHoras() > clDia.getMateria().getHorasMax()) {
                            organizarHorario(); 
                        }*/

                    }
                }

                clasesDia.add(clDia);
            }

        } while (clasesLunes.size() > 0);

        fila = 0;

        System.out.println("");
        System.out.println("Ordenamos martes: ");
        do {
            if (clasesMartes.size() > 0) {
                System.out.println(clasesMartes.get(0).getMateria().getNombre());
                c = clasesMartes.get(0);
                clasesMartes.remove(0);

                ClaseDia clDia = new ClaseDia();
                clDia.setDia(2);
                clDia.setMateria(c.getMateria());
                clDia.setNumHoras(c.getHoras());

                mdTblHorarioDatos.setValueAt(c.getMateria().getNombre(), fila, 2);
                fila++;

                for (int i = 0; i < clasesMartes.size(); i++) {
                    if (clasesMartes.get(i).getMateria().equals(c.getMateria())) {
                        System.out.println(clasesMartes.get(i).getMateria().getNombre());

                        mdTblHorarioDatos.setValueAt(clasesMartes.get(i).getMateria().getNombre(), fila, 2);
                        fila++;

                        clasesMartes.remove(i);
                        clDia.setNumHoras(clDia.getNumHoras() + 1);
                        /*
                        if (clDia.getNumHoras() > clDia.getMateria().getHorasMax()) {
                            organizarHorario();
                        }*/
                    }
                }

                clasesDia.add(clDia);
            }

        } while (clasesMartes.size() > 0);

        System.out.println("");
        System.out.println("Ordenamos miercoles:");

        fila = 0;

        do {
            if (clasesMiercoles.size() > 0) {
                System.out.println(clasesMiercoles.get(0).getMateria().getNombre());
                c = clasesMiercoles.get(0);
                clasesMiercoles.remove(0);

                ClaseDia clDia = new ClaseDia();
                clDia.setDia(3);
                clDia.setMateria(c.getMateria());
                clDia.setNumHoras(c.getHoras());

                mdTblHorarioDatos.setValueAt(c.getMateria().getNombre(), fila, 3);
                fila++;

                for (int i = 0; i < clasesMiercoles.size(); i++) {
                    if (clasesMiercoles.get(i).getMateria().equals(c.getMateria())) {
                        System.out.println(clasesMiercoles.get(i).getMateria().getNombre());

                        mdTblHorarioDatos.setValueAt(clasesMiercoles.get(i).getMateria().getNombre(), fila, 3);
                        fila++;

                        clasesMiercoles.remove(i);

                        clDia.setNumHoras(clDia.getNumHoras() + 1);
                        /*
                        if (clDia.getNumHoras() > clDia.getMateria().getHorasMax()) {
                            organizarHorario();
                        }*/
                    }
                }

                clasesDia.add(clDia);
            }

        } while (clasesMiercoles.size() > 0);

        System.out.println("");
        System.out.println("Ordenamos jueves:");
        fila = 0;

        do {
            if (clasesJueves.size() > 0) {
                System.out.println(clasesJueves.get(0).getMateria().getNombre());
                c = clasesJueves.get(0);
                clasesJueves.remove(0);

                ClaseDia clDia = new ClaseDia();
                clDia.setDia(4);
                clDia.setMateria(c.getMateria());
                clDia.setNumHoras(c.getHoras());

                mdTblHorarioDatos.setValueAt(c.getMateria().getNombre(), fila, 4);
                fila++;

                for (int i = 0; i < clasesJueves.size(); i++) {
                    if (clasesJueves.get(i).getMateria().equals(c.getMateria())) {
                        System.out.println(clasesJueves.get(i).getMateria().getNombre());

                        mdTblHorarioDatos.setValueAt(clasesJueves.get(i).getMateria().getNombre(), fila, 4);
                        fila++;

                        clasesJueves.remove(i);

                        clDia.setNumHoras(clDia.getNumHoras() + 1);
                        /*
                        if (clDia.getNumHoras() > clDia.getMateria().getHorasMax()) {
                            organizarHorario();
                        }*/
                    }
                }

                clasesDia.add(clDia);
            }

        } while (clasesJueves.size() > 0);

        fila = 0;
        System.out.println("");
        System.out.println("Ordenamos Viernes:");

        do {
            if (clasesViernes.size() > 0) {
                System.out.println(clasesViernes.get(0).getMateria().getNombre());
                c = clasesViernes.get(0);
                clasesViernes.remove(0);

                ClaseDia clDia = new ClaseDia();
                clDia.setDia(5);
                clDia.setMateria(c.getMateria());
                clDia.setNumHoras(c.getHoras());

                mdTblHorarioDatos.setValueAt(c.getMateria().getNombre(), fila, 5);
                fila++;

                for (int i = 0; i < clasesViernes.size(); i++) {
                    if (clasesViernes.get(i).getMateria().equals(c.getMateria())) {
                        System.out.println(clasesViernes.get(i).getMateria().getNombre());

                        mdTblHorarioDatos.setValueAt(clasesViernes.get(i).getMateria().getNombre(), fila, 5);
                        fila++;

                        clasesViernes.remove(i);

                        clDia.setNumHoras(clDia.getNumHoras() + 1);
                        /*
                        if (clDia.getNumHoras() > clDia.getMateria().getHorasMax()) {
                            organizarHorario();
                        }*/
                    }
                }

                clasesDia.add(clDia);
            }

        } while (clasesViernes.size() > 0);

        System.out.println("");
        System.out.println("Ahora veremos cuantas clases se crearon:");
        System.out.println(clasesDia.size());
        System.out.println("Ahora veremos cuantas horas se cumplen");
        System.out.println("------------------");
        System.out.println("------------------");
        int horasCumplidas = 0;
        for (ClaseDia clDia : clasesDia) {
            horasCumplidas = horasCumplidas + clDia.getNumHoras();
            if (clDia.getDia() == 1) {
                System.out.println("Materia: " + clDia.getMateria().getNombre() + " ");
                System.out.println("Horas: " + clDia.getNumHoras());
                System.out.println("Dia: " + clDia.getDia());
            }
        }
        System.out.println("------------------");
        System.out.println(horasCumplidas);

    }

    public void informacion() {
        Similitudes.cambioPanel(distri.getPnlPrincipal(), pnlInfo);

        //Seteamos la tabla  
        String titulo[] = {"Cedula", "Docente", "# Materias"};
        String datos[][] = {};
        DefaultTableModel mdTblDocenFil = new DefaultTableModel(datos, titulo);
        if (distri.getCbPeriodoLectivo().getSelectedIndex() > 0 && docentesFiltrados != null) {

            pnlInfo.getTblDocentesSelecionados().setModel(mdTblDocenFil);

            for (Docentes dc : docentesFiltrados) {
                Object valores[] = {dc.getCedula(),
                    dc.getNombre(), dc.getMateriasPref().size()};
                mdTblDocenFil.addRow(valores);
            }
            pnlInfo.getTblHorarioOrdenado().setModel(mdTblHorarioDatos);

            ArrayList<Docentes> docenGanador = new ArrayList<>();

            int numMat = materiasFiltradas.size();

            int posDoc;

            do {
                posDoc = (int) (Math.random() * docentesFiltrados.size());
                if (!docenGanador.contains(docentesFiltrados.get(posDoc))) {
                    for (Materias mt : docentesFiltrados.get(posDoc).getMateriasPref()) {
                        for(Docentes dc: docenGanador){ 
                            for(Materias m: dc.getMateriasEleg()){
                                if(!mt.getNombre().equals(m.getNombre())){
                                    System.out.println("Se puede poner esta materia YEII!!!");
                                    docentesFiltrados.get(posDoc).getMateriasEleg().add(m); 
                                    docenGanador.add(docentesFiltrados.get(posDoc));
                                    
                                }
                            }
                        }
                    }
                }

            } while (docenGanador.size() <= numMat);

        }

    }

}
