/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaz.Administrador;

import BaseDatos.Conexion_Consultas;
import BaseDatos.SQLActividades;
import BaseDatos.SQLCarreras;
import BaseDatos.SQLDocentes;
import BaseDatos.SQLJornadas;
import BaseDatos.SQLMateria;
import Clases.Actividad;
import Clases.ActividadesDocente;
import Clases.Carrera;
import Clases.Detalle_Jornada;
import Clases.DocenteCarrera;
import Clases.Docente_Instituto;
import Clases.JornadaDocente;
import Clases.Materia;
import Clases.MateriasDocente;
import Clases.Validaciones;
import Validacion.Validacion;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author Johnny
 */
public class DocentesVtn extends javax.swing.JFrame {

    static final int limite = 10;
    Validacion validar = new Validacion();
    //Creamos nuestro modelo de tabla 
    DefaultTableModel modeloTabla;
    //Le damos su titulo 
    String tituloTabla[] = {"Cedula", "Nombre"};
    String datos[][] = {};

    //Creamos un modelo para tabla de docente actividad 
    DefaultTableModel modeloTablaActividad;
    String tituloActividad[] = {"id", "Docente", "Actividad"};
    String datosActividad[][] = {};

    //Creamos un modelo para materias docente  
    DefaultTableModel modeloTblMateria;
    String tituloMaterias[] = {"id", "Docente", "Actividad"};
    String datosMateria[][] = {};

    //Creamos un modelo para carreras docente  
    DefaultTableModel modeloTblCarrera;
    String tituloCarreras[] = {"id", "Docente", "Carrera"};
    String datosCarrera[][] = {};

    //Creamos un modelo para jornadas docente  
    DefaultTableModel modeloTblJornada;
    String tituloJornada[] = {"id", "Docente", "Jornada"};
    String datosJornada[][] = {};

    //Para mover la ventana 
    int mousex;
    int mousey;

    //Para saber si los botones estan avilitados  
    boolean btnAvilitados = false;

    //Guardamos la posicion de la fila que seleciono 
    int filaSelecionada;
    //Guardamos la posicion de la fila selecionada al momento de clickear en editar
    int filaEditar;

    //Variable para saber si estoy editando o no  
    boolean editando = false;

    //Creamos nuestra variable para validar 
    Validaciones valido = new Validaciones();

    //Agregamos docentes  
    ArrayList<Docente_Instituto> docentesInsti = new ArrayList();
    ArrayList<Docente_Instituto> docentesInstiF = new ArrayList();

    //Agregamos actividades 
    ArrayList<Actividad> actividadesDB = new ArrayList();

    ArrayList<ActividadesDocente> actividadesDocente = new ArrayList();
    ArrayList<ActividadesDocente> actividadesDocenteT = new ArrayList();

    //Agregamos materias 
    ArrayList<Materia> materiasDB = new ArrayList();

    ArrayList<MateriasDocente> materiasDocent = new ArrayList();
    ArrayList<MateriasDocente> materiasDocentT = new ArrayList();

    //Agregamos carreras 
    ArrayList<Carrera> carrerasDB = new ArrayList();

    ArrayList<DocenteCarrera> carrerasDocente = new ArrayList();
    ArrayList<DocenteCarrera> carrerasDocenteT = new ArrayList();

    //Agregamos jornadas 
    ArrayList<Detalle_Jornada> jornadasDB = new ArrayList();

    ArrayList<JornadaDocente> jornadasDocente = new ArrayList();
    ArrayList<JornadaDocente> jornadasDocenteT = new ArrayList();

    public DocentesVtn() {
        initComponents();

        //Nos conectamos 
        Conexion_Consultas.conectar();

        cargarCombos();

        Similitudes.btnsModoInactivo(btnEditar, btnEliminar);

        Similitudes.btnsModoInactivo(btnEditarActividad, btnEliminarActividad);

        Similitudes.btnsModoInactivo(btnEditarMateria, btnEliminarMateria);

        Similitudes.btnsModoInactivo(btnEditarCarrera, btnEliminarCarrera);

        Similitudes.btnsModoInactivo(btnEditarJornada, btnEliminarJornada);

        //Inicializamos nuestra tabla 
        modeloTabla = new DefaultTableModel(datos, tituloTabla);

        modeloTablaActividad = new DefaultTableModel(datosActividad, tituloActividad);

        modeloTblMateria = new DefaultTableModel(datosMateria, tituloMaterias);

        modeloTblCarrera = new DefaultTableModel(datosCarrera, tituloCarreras);

        modeloTblJornada = new DefaultTableModel(datosJornada, tituloJornada);

        //Pasamos el modelo a la tabla 
        actualizarDocen();

        tblDocentes.setModel(modeloTabla);

        actualizarTblActiDocen();

        tblActividadesDocen.setModel(modeloTablaActividad);

        actualizarTblMatDocen();

        tblMaterias.setModel(modeloTblMateria);

        actualizarTblCarrDocen();

        tblCarrera.setModel(modeloTblCarrera);

        actualizarTblJordDocen();

        tblJornada.setModel(modeloTblJornada);

        //Modificamos todos los tamanos de nuestras columnas de las diferentes tablas 
        //tamColumnas(tblDocentes, 100, 250);

        TableColumnModel columnasDocente = tblDocentes.getColumnModel();
        columnasDocente.getColumn(0).setPreferredWidth(100);
        columnasDocente.getColumn(1).setPreferredWidth(250);
        
         
        tamColumnas(tblActividadesDocen, 100, 250);

        tamColumnas(tblMaterias, 100, 250);

        tamColumnas(tblCarrera, 100, 250);

        tamColumnas(tblJornada, 150, 200);

        //Modificamos los titulos de todas nuestras tablas 
        Similitudes.tituloTbls(tblDocentes);

        Similitudes.tituloTbls(tblActividadesDocen);

        Similitudes.tituloTbls(tblMaterias);

        Similitudes.tituloTbls(tblCarrera);

        Similitudes.tituloTbls(tblJornada);

        //Ocultamos todos los errores 
        lblErrorFechaInicio.setVisible(false);
        lblErrorFechaFin.setVisible(false);
        lblErrorNuevoDocente.setVisible(false);

        lblErrorActividad.setVisible(false);

        lblErrorMateria.setVisible(false);

        lblErrorCarrera.setVisible(false);

        lblErrorJornada.setVisible(false);

    }

    private void actualizarTblActiDocen() {
        modeloTablaActividad.setRowCount(0);

        if (!SQLDocentes.cargarActividadesDocente(true).isEmpty()) {
            actividadesDocente = SQLDocentes.cargarActividadesDocente(true);
            for (int i = 0; i < actividadesDocente.size(); i++) {
                Object valores[] = {actividadesDocente.get(i).getId_actividad_docen(),
                    nomDocente(actividadesDocente.get(i).getFk_docente()),
                    nomActividad(actividadesDocente.get(i).getFk_actividad())};

                modeloTablaActividad.addRow(valores);
            }
        }
    }

    private void actualizarTblMatDocen() {
        modeloTblMateria.setRowCount(0);

        if (!SQLDocentes.cargarMateriasDocente(true).isEmpty()) {
            materiasDocent = SQLDocentes.cargarMateriasDocente(true);
            System.out.println("Este es el tamaño de materias docente "+materiasDocent.size());
            
            for (int i = 0; i < materiasDocent.size(); i++) {
                if (!materiasDocent.get(i).isMat_pref_elim()) {
                    Object valores[] = {materiasDocent.get(i).getId_mat_pref(),
                        nomDocente(materiasDocent.get(i).getFk_docente()),
                        nomMateria(materiasDocent.get(i).getFk_materia())};

                    modeloTblMateria.addRow(valores);
                }
            }
        }
    }

    private void actualizarTblCarrDocen() {
        modeloTblCarrera.setRowCount(0);

        if (!SQLDocentes.cargarCarreraDocente(true).isEmpty()) {
            carrerasDocente = SQLDocentes.cargarCarreraDocente(true);

            for (int i = 0; i < carrerasDocente.size(); i++) {
                if (!carrerasDocente.get(i).isDocente_ints_elim()) {
                    Object[] valores = {carrerasDocente.get(i).getId_docen_carrera(),
                        nomDocente(carrerasDocente.get(i).getFk_docente_inst()),
                        nomCarrera(carrerasDocente.get(i).getFk_carrera())};

                    modeloTblCarrera.addRow(valores);
                }
            }
        }
    }

    private void actualizarTblJordDocen() {
        modeloTblJornada.setRowCount(0);

        if (!SQLDocentes.cargarJornadaDocente(true).isEmpty()) {
            jornadasDocente = SQLDocentes.cargarJornadaDocente(true);
            for (int i = 0; i < jornadasDocente.size(); i++) {
                if (!jornadasDocente.get(i).isDocen_jord_elim()) {
                    Object[] valores = {jornadasDocente.get(i).getId_jornada_docen(),
                        nomDocente(jornadasDocente.get(i).getFk_docen_insti()),
                        nomJornada(jornadasDocente.get(i).getFk_deta_jornd())};

                    modeloTblJornada.addRow(valores);
                }
            }
        }
    }

    private void cargarCombos() {
        //Iniciamos todos los combo box 
        if (!SQLActividades.cargarActividades(true).isEmpty()) {
            actividadesDB = SQLActividades.cargarActividades(true);

            //Cargamos los combos de este panel
            for (int i = 0; i < actividadesDB.size(); i++) {
                comboActividad.addItem(actividadesDB.get(i).getNombre_actividad());
            }
        }

        if (!SQLMateria.cargarMaterias(true).isEmpty()) {
            materiasDB = SQLMateria.cargarMaterias(true);
            for (int i = 0; i < materiasDB.size(); i++) {
                comboMateria.addItem(materiasDB.get(i).getNombre_materia());
            }
        }

        if (!SQLCarreras.cargarCarrerasDB(true).isEmpty()) {
            carrerasDB = SQLCarreras.cargarCarrerasDB(true);
            for (int i = 0; i < carrerasDB.size(); i++) {
                comboCarrera.addItem(carrerasDB.get(i).getNombre_Carrera());
            }
        }

        if (!SQLJornadas.cargarDetalleJornada(true).isEmpty()) {
            jornadasDB = SQLJornadas.cargarDetalleJornada(true);
            for (int i = 0; i < jornadasDB.size(); i++) {
                comboJornada.addItem(jornadasDB.get(i).getDescripcion_jornada());
            }
        }
    }

    //Limpiamos la tabla  
    public void limpiarTbl(DefaultTableModel tbl) {
        tbl.setRowCount(0);
    }

    //Actualizamos nuestra tabla y listas nuestra tabla princiapl 
    private void actualizarDocen() {
        if (!SQLDocentes.cargarDocentesInstituo(false).isEmpty()) {
            docentesInsti = SQLDocentes.cargarDocentesInstituo(false);
        }

        if (!SQLDocentes.cargarDocentesInstituo(true).isEmpty()) {
            docentesInstiF = SQLDocentes.cargarDocentesInstituo(true);

            //Primeros borramos los vaores anteriores antes de acutalizar 
            limpiarTbl(modeloTabla);

            comboDocenteAct.removeAllItems();
            comboDocenteCarrera.removeAllItems();
            comboDocenteJornada.removeAllItems();
            comboDocenteMat.removeAllItems();

            for (int i = 0; i < docentesInstiF.size(); i++) {
                Object[] valores = {docentesInstiF.get(i).getCedula_docente(),
                    docentesInstiF.get(i).getNombre_docente()};

                modeloTabla.addRow(valores);

                comboDocenteAct.addItem(docentesInstiF.get(i).getNombre_docente());
                comboDocenteCarrera.addItem(docentesInstiF.get(i).getNombre_docente());
                comboDocenteJornada.addItem(docentesInstiF.get(i).getNombre_docente());
                comboDocenteMat.addItem(docentesInstiF.get(i).getNombre_docente());
            }
        }
    }

    //Buscamos el nombre de un docent e 
    public String nomDocente(String ci) {
        String nombre = "SN";
        for (int i = 0; i < docentesInstiF.size(); i++) {
            if (docentesInstiF.get(i).getCedula_docente().equals(ci)) {
                nombre = docentesInstiF.get(i).getNombre_docente();
                break;
            }
        }
        return nombre;
    }

    //Buscar el id de docentes  
    public String ciDocente(String nombre) {
        String ci = "";
        for (int i = 0; i < docentesInstiF.size(); i++) {
            if (docentesInstiF.get(i).getNombre_docente().equals(nombre)) {
                ci = docentesInstiF.get(i).getCedula_docente();
                break;
            }
        }
        return ci;
    }

    //Metodos para buscar el id de los objetos pasandoles unicamente su nombre  
    public int idActividad(String actividad) {
        int id = 0;
        for (int i = 0; i < actividadesDB.size(); i++) {
            if (actividadesDB.get(i).getNombre_actividad().equals(actividad)) {
                id = actividadesDB.get(i).getId_actividad();
                break;
            }
        }
        return id;
    }

    public String nomActividad(int id) {
        String nombre = "SN";
        for (int i = 0; i < actividadesDB.size(); i++) {
            if (actividadesDB.get(i).getId_actividad() == id) {
                nombre = actividadesDB.get(i).getNombre_actividad();
                break;
            }
        }
        return nombre;
    }

    public int idMateria(String materia) {
        int id = 0;
        for (int i = 0; i < materiasDB.size(); i++) {
            if (materiasDB.get(i).getNombre_materia().equals(materia)) {
                id = materiasDB.get(i).getId_materia();
                break;
            }
        }
        return id;
    }

    public String nomMateria(int id) {
        String nombre = "SN";
        for (int i = 0; i < materiasDB.size(); i++) {
            if (materiasDB.get(i).getId_materia() == id) {
                nombre = materiasDB.get(i).getNombre_materia();
                break;
            }
        }
        return nombre;
    }

    public int idCarrera(String carrera) {
        int id = 0;
        for (int i = 0; i < carrerasDB.size(); i++) {
            if (carrerasDB.get(i).getNombre_Carrera().equals(carrera)) {
                id = carrerasDB.get(i).getId_carrera();
                break;
            }
        }
        return id;
    }

    public String nomCarrera(int id) {
        String nombre = "SN";
        for (int i = 0; i < carrerasDB.size(); i++) {
            if (carrerasDB.get(i).getId_carrera() == id) {
                nombre = carrerasDB.get(i).getNombre_Carrera();
                break;
            }
        }
        return nombre;
    }

    public int idDetJornada(String jornada) {
        int id = 0;
        for (int i = 0; i < jornadasDB.size(); i++) {
            if (jornadasDB.get(i).getDescripcion_jornada().equals(jornada)) {
                id = jornadasDB.get(i).getId_deta_jornada();
                break;
            }
        }
        return id;
    }

    public String nomJornada(int id) {
        String nombre = "SN";
        for (int i = 0; i < jornadasDB.size(); i++) {
            if (jornadasDB.get(i).getId_deta_jornada() == id) {
                nombre = jornadasDB.get(i).getDescripcion_jornada();
                break;
            }
        }
        return nombre;
    }

    //Funcion para cambiar el tamano de nuestras columnas  
    private void tamColumnas(JTable tabla, int anchura1, int anchura2) {
        TableColumnModel columna = tabla.getColumnModel();

        columna.getColumn(0).setPreferredWidth(0);
        columna.getColumn(0).setWidth(0);
        columna.getColumn(0).setMinWidth(0);
        columna.getColumn(0).setMaxWidth(0);

        columna.getColumn(1).setPreferredWidth(anchura1);
        columna.getColumn(2).setPreferredWidth(anchura2);
    }

    //Funcion que usaremos para verificar que no se haya ingresado ya este docente 
    public boolean nuevoDocente(String cedula) {
        boolean datosNuevos = true;

        try {
            if (SQLDocentes.consultarDocenteInsti(cedula) != null) {
                SQLDocentes.eliminarDocenteInsti(cedula, false);
                datosNuevos = false; 
            }

        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return datosNuevos;
    }

    //Funicon para saber si no guardamos ya esto  
    public boolean agregarCosasDocente(int filas, String docente, String actividad, DefaultTableModel modelo) {
        boolean nuevaActDocen = true;
        String docenComparar;
        String actividadComparar;

        try {
            for (int i = 0; i < filas; i++) {
                docenComparar = modelo.getValueAt(i, 0).toString();
                actividadComparar = modelo.getValueAt(i, 1).toString();
                if (docente.equals(docenComparar) && actividad.equals(actividadComparar)) {
                    nuevaActDocen = false;
                    break;
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
        }

        return nuevaActDocen;
    }

    //Cambiar los colores de los botones dependienod en la pestana que te encuentres.  
    //La primera variable es en la donde diste click y las otras son las que sobran  
    public void cambioColorBtns(JButton btnSelecionado, JButton btn1, JButton btn2, JButton btn3) {

        btnSelecionado.setBackground(new Color(231, 235, 230));

        btn1.setBackground(new Color(9, 28, 32));
        btn2.setBackground(new Color(9, 28, 32));
        btn3.setBackground(new Color(9, 28, 32));

        btnSelecionado.setForeground(new Color(9, 28, 32));

        btn1.setForeground(new Color(204, 204, 204));
        btn2.setForeground(new Color(204, 204, 204));
        btn3.setForeground(new Color(204, 204, 204));

        btnAvilitados = false;
    }

    //Para selecionar la posicion de la tabla en la donde dimos click 
    public void clickTabla(JTable tbl, JButton btnEditar, JButton btnEliminar) {
        try {

            filaSelecionada = tbl.getSelectedRow();

            //Activamos nuestros botones
            if (filaSelecionada >= 0) {
                btnAvilitados = true;
                //Modificamos los botones, para que pueda ser usados
                //Cambio sus colores para que se ve mejor que estan visibles
                btnEditar.setBackground(new Color(99, 144, 158));
                btnEliminar.setBackground(new Color(99, 144, 158));

                btnEditar.setEnabled(true);
                btnEliminar.setEnabled(true);
            }

        } catch (IndexOutOfBoundsException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void clickEliminar(DefaultTableModel modeloTablas, JButton btnEditar, JButton btnEliminar) {
        try {
            //Eliminamos la fila que este selecionada
            if (filaSelecionada >= 0 && btnAvilitados) {
                modeloTablas.removeRow(filaSelecionada);

                Similitudes.btnsModoInactivo(btnEditar, btnEliminar);
                editando = false;
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelCuerpo = new javax.swing.JPanel();
        panelDocente = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDocentes = new JTable(){
            @Override 
            public boolean isCellEditable(int rowIndex, int colIndex){
                return false; 
            }
        };
        jLabel1 = new javax.swing.JLabel();
        txtCedula = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtNombreCompleto = new javax.swing.JTextField();
        btnGuardar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        lblErrorFechaInicio = new javax.swing.JLabel();
        lblErrorFechaFin = new javax.swing.JLabel();
        lblErrorNuevoDocente = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        panelActividadesDocen = new javax.swing.JPanel();
        comboDocenteAct = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        comboActividad = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        btnGuardarActividad = new javax.swing.JButton();
        btnEditarActividad = new javax.swing.JButton();
        btnEliminarActividad = new javax.swing.JButton();
        lblErrorActividad = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tblActividadesDocen = new javax.swing.JTable();
        jLabel12 = new javax.swing.JLabel();
        panelMateriasDocen = new javax.swing.JPanel();
        btnGuardarMateria = new javax.swing.JButton();
        btnEditarMateria = new javax.swing.JButton();
        btnEliminarMateria = new javax.swing.JButton();
        comboMateria = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        comboDocenteMat = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        lblErrorMateria = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblMaterias = new javax.swing.JTable();
        jLabel13 = new javax.swing.JLabel();
        panelCarreraDocen = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblCarrera = new JTable(){
            @Override 
            public boolean isCellEditable(int rowIndex, int colIndex){
                return false; 
            }
        };
        btnGuardarCarrera = new javax.swing.JButton();
        btnEditarCarrera = new javax.swing.JButton();
        btnEliminarCarrera = new javax.swing.JButton();
        comboCarrera = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        comboDocenteCarrera = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        lblErrorCarrera = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        panelJornadaDocen = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblJornada = new JTable(){
            @Override 
            public boolean isCellEditable(int rowIndex, int colIndex){
                return false; 
            }
        };
        btnGuardarJornada = new javax.swing.JButton();
        btnEditarJornada = new javax.swing.JButton();
        btnEliminarJornada = new javax.swing.JButton();
        comboJornada = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        comboDocenteJornada = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        lblErrorJornada = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        panelHeader = new javax.swing.JPanel();
        btnMenu = new javax.swing.JButton();
        lblIngresarDocente = new javax.swing.JLabel();
        btnJornadaDocente = new javax.swing.JButton();
        btnActividadesDocente = new javax.swing.JButton();
        btnMateriasDocente = new javax.swing.JButton();
        btnCarreraDocente = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(810, 500));
        setUndecorated(true);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelCuerpo.setBackground(new java.awt.Color(231, 235, 230));
        panelCuerpo.setLayout(new java.awt.CardLayout());

        panelDocente.setBackground(new java.awt.Color(231, 235, 230));
        panelDocente.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane1.setBackground(new java.awt.Color(231, 235, 230));
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setPreferredSize(new java.awt.Dimension(450, 400));

        tblDocentes.setBackground(new java.awt.Color(231, 235, 230));
        tblDocentes.setFont(new java.awt.Font("Trebuchet MS", 0, 14)); // NOI18N
        tblDocentes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null}
            },
            new String [] {
                "Cedula", "Nombre "
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblDocentes.setOpaque(false);
        tblDocentes.setSelectionBackground(new java.awt.Color(21, 89, 110));
        tblDocentes.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblDocentes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDocentesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblDocentes);

        panelDocente.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 180, 450, 280));

        jLabel1.setFont(new java.awt.Font("Trebuchet MS", 1, 20)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(21, 89, 110));
        jLabel1.setText("Cedula:");
        panelDocente.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 20, 80, -1));

        txtCedula.setBackground(new java.awt.Color(99, 144, 158));
        txtCedula.setFont(new java.awt.Font("Trebuchet MS", 0, 18)); // NOI18N
        txtCedula.setOpaque(false);
        txtCedula.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCedulaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCedulaKeyTyped(evt);
            }
        });
        panelDocente.add(txtCedula, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 20, 170, -1));

        jLabel3.setFont(new java.awt.Font("Trebuchet MS", 1, 20)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(21, 89, 110));
        jLabel3.setText("Nombre y Apellido:");
        panelDocente.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 60, -1, -1));

        txtNombreCompleto.setBackground(new java.awt.Color(99, 144, 158));
        txtNombreCompleto.setFont(new java.awt.Font("Trebuchet MS", 0, 18)); // NOI18N
        txtNombreCompleto.setOpaque(false);
        txtNombreCompleto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtNombreCompletoMouseClicked(evt);
            }
        });
        txtNombreCompleto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNombreCompletoKeyPressed(evt);
            }
        });
        panelDocente.add(txtNombreCompleto, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 90, 370, -1));

        btnGuardar.setBackground(new java.awt.Color(99, 144, 158));
        btnGuardar.setFont(new java.awt.Font("Trebuchet MS", 1, 18)); // NOI18N
        btnGuardar.setForeground(new java.awt.Color(255, 255, 255));
        btnGuardar.setText("Guardar");
        btnGuardar.setToolTipText("");
        btnGuardar.setBorderPainted(false);
        btnGuardar.setContentAreaFilled(false);
        btnGuardar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGuardar.setFocusPainted(false);
        btnGuardar.setOpaque(true);
        btnGuardar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnGuardarMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnGuardarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnGuardarMouseExited(evt);
            }
        });
        panelDocente.add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 140, 110, -1));

        btnEditar.setBackground(new java.awt.Color(118, 125, 127));
        btnEditar.setFont(new java.awt.Font("Trebuchet MS", 1, 18)); // NOI18N
        btnEditar.setForeground(new java.awt.Color(255, 255, 255));
        btnEditar.setText("Editar");
        btnEditar.setToolTipText("");
        btnEditar.setBorderPainted(false);
        btnEditar.setContentAreaFilled(false);
        btnEditar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEditar.setFocusPainted(false);
        btnEditar.setOpaque(true);
        btnEditar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEditarMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnEditarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnEditarMouseExited(evt);
            }
        });
        panelDocente.add(btnEditar, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 140, 110, -1));

        btnEliminar.setBackground(new java.awt.Color(118, 125, 127));
        btnEliminar.setFont(new java.awt.Font("Trebuchet MS", 1, 18)); // NOI18N
        btnEliminar.setForeground(new java.awt.Color(255, 255, 255));
        btnEliminar.setText("Eliminar");
        btnEliminar.setToolTipText("");
        btnEliminar.setBorderPainted(false);
        btnEliminar.setContentAreaFilled(false);
        btnEliminar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEliminar.setFocusPainted(false);
        btnEliminar.setOpaque(true);
        btnEliminar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEliminarMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnEliminarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnEliminarMouseExited(evt);
            }
        });
        panelDocente.add(btnEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 140, 110, -1));

        lblErrorFechaInicio.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        lblErrorFechaInicio.setForeground(new java.awt.Color(115, 30, 16));
        lblErrorFechaInicio.setText("Ingrese una cedula valida.");
        panelDocente.add(lblErrorFechaInicio, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 40, 170, 30));

        lblErrorFechaFin.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        lblErrorFechaFin.setForeground(new java.awt.Color(115, 30, 16));
        lblErrorFechaFin.setText("Solo debe ingresar letras.");
        panelDocente.add(lblErrorFechaFin, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 120, 180, 20));

        lblErrorNuevoDocente.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        lblErrorNuevoDocente.setForeground(new java.awt.Color(115, 30, 16));
        lblErrorNuevoDocente.setText("Cedula ya registrada.");
        panelDocente.add(lblErrorNuevoDocente, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 0, 140, 20));

        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Administracion/FondoLapiz3.png"))); // NOI18N
        panelDocente.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 810, 460));

        panelCuerpo.add(panelDocente, "card13");

        panelActividadesDocen.setBackground(new java.awt.Color(231, 235, 230));
        panelActividadesDocen.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        comboDocenteAct.setFont(new java.awt.Font("Trebuchet MS", 0, 18)); // NOI18N
        comboDocenteAct.setBorder(null);
        comboDocenteAct.setOpaque(false);
        panelActividadesDocen.add(comboDocenteAct, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 40, 370, -1));

        jLabel2.setFont(new java.awt.Font("Trebuchet MS", 1, 20)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(21, 89, 110));
        jLabel2.setText("Docente:");
        panelActividadesDocen.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 10, -1, -1));

        comboActividad.setFont(new java.awt.Font("Trebuchet MS", 0, 18)); // NOI18N
        comboActividad.setBorder(null);
        comboActividad.setOpaque(false);
        panelActividadesDocen.add(comboActividad, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 100, 370, -1));

        jLabel4.setFont(new java.awt.Font("Trebuchet MS", 1, 20)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(21, 89, 110));
        jLabel4.setText("Actividad:");
        panelActividadesDocen.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 70, -1, -1));

        btnGuardarActividad.setBackground(new java.awt.Color(99, 144, 158));
        btnGuardarActividad.setFont(new java.awt.Font("Trebuchet MS", 1, 18)); // NOI18N
        btnGuardarActividad.setForeground(new java.awt.Color(255, 255, 255));
        btnGuardarActividad.setText("Guardar");
        btnGuardarActividad.setToolTipText("");
        btnGuardarActividad.setBorderPainted(false);
        btnGuardarActividad.setContentAreaFilled(false);
        btnGuardarActividad.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGuardarActividad.setFocusPainted(false);
        btnGuardarActividad.setOpaque(true);
        btnGuardarActividad.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnGuardarActividadMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnGuardarActividadMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnGuardarActividadMouseExited(evt);
            }
        });
        panelActividadesDocen.add(btnGuardarActividad, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 140, 110, -1));

        btnEditarActividad.setBackground(new java.awt.Color(118, 125, 127));
        btnEditarActividad.setFont(new java.awt.Font("Trebuchet MS", 1, 18)); // NOI18N
        btnEditarActividad.setForeground(new java.awt.Color(255, 255, 255));
        btnEditarActividad.setText("Editar");
        btnEditarActividad.setToolTipText("");
        btnEditarActividad.setBorderPainted(false);
        btnEditarActividad.setContentAreaFilled(false);
        btnEditarActividad.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEditarActividad.setFocusPainted(false);
        btnEditarActividad.setOpaque(true);
        btnEditarActividad.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEditarActividadMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnEditarActividadMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnEditarActividadMouseExited(evt);
            }
        });
        panelActividadesDocen.add(btnEditarActividad, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 140, 110, -1));

        btnEliminarActividad.setBackground(new java.awt.Color(118, 125, 127));
        btnEliminarActividad.setFont(new java.awt.Font("Trebuchet MS", 1, 18)); // NOI18N
        btnEliminarActividad.setForeground(new java.awt.Color(255, 255, 255));
        btnEliminarActividad.setText("Eliminar");
        btnEliminarActividad.setToolTipText("");
        btnEliminarActividad.setBorderPainted(false);
        btnEliminarActividad.setContentAreaFilled(false);
        btnEliminarActividad.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEliminarActividad.setFocusPainted(false);
        btnEliminarActividad.setOpaque(true);
        btnEliminarActividad.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEliminarActividadMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnEliminarActividadMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnEliminarActividadMouseExited(evt);
            }
        });
        panelActividadesDocen.add(btnEliminarActividad, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 140, 110, -1));

        lblErrorActividad.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        lblErrorActividad.setForeground(new java.awt.Color(115, 30, 16));
        lblErrorActividad.setText("El docente ya tiene esta actividad.");
        panelActividadesDocen.add(lblErrorActividad, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 10, 230, 30));

        tblActividadesDocen.setBackground(new java.awt.Color(231, 235, 230));
        tblActividadesDocen.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null}
            },
            new String [] {
                "Title 1", "Title 2"
            }
        ));
        tblActividadesDocen.setSelectionBackground(new java.awt.Color(21, 89, 110));
        tblActividadesDocen.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblActividadesDocenMouseClicked(evt);
            }
        });
        jScrollPane7.setViewportView(tblActividadesDocen);

        panelActividadesDocen.add(jScrollPane7, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 180, -1, 280));

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Administracion/FondoLapiz3.png"))); // NOI18N
        panelActividadesDocen.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 810, 460));

        panelCuerpo.add(panelActividadesDocen, "card3");

        panelMateriasDocen.setBackground(new java.awt.Color(231, 235, 230));
        panelMateriasDocen.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnGuardarMateria.setBackground(new java.awt.Color(99, 144, 158));
        btnGuardarMateria.setFont(new java.awt.Font("Trebuchet MS", 1, 18)); // NOI18N
        btnGuardarMateria.setForeground(new java.awt.Color(255, 255, 255));
        btnGuardarMateria.setText("Guardar");
        btnGuardarMateria.setToolTipText("");
        btnGuardarMateria.setBorderPainted(false);
        btnGuardarMateria.setContentAreaFilled(false);
        btnGuardarMateria.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGuardarMateria.setFocusPainted(false);
        btnGuardarMateria.setOpaque(true);
        btnGuardarMateria.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnGuardarMateriaMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnGuardarMateriaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnGuardarMateriaMouseExited(evt);
            }
        });
        panelMateriasDocen.add(btnGuardarMateria, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 140, 110, -1));

        btnEditarMateria.setBackground(new java.awt.Color(118, 125, 127));
        btnEditarMateria.setFont(new java.awt.Font("Trebuchet MS", 1, 18)); // NOI18N
        btnEditarMateria.setForeground(new java.awt.Color(255, 255, 255));
        btnEditarMateria.setText("Editar");
        btnEditarMateria.setToolTipText("");
        btnEditarMateria.setBorderPainted(false);
        btnEditarMateria.setContentAreaFilled(false);
        btnEditarMateria.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEditarMateria.setFocusPainted(false);
        btnEditarMateria.setOpaque(true);
        btnEditarMateria.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEditarMateriaMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnEditarMateriaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnEditarMateriaMouseExited(evt);
            }
        });
        panelMateriasDocen.add(btnEditarMateria, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 140, 110, -1));

        btnEliminarMateria.setBackground(new java.awt.Color(118, 125, 127));
        btnEliminarMateria.setFont(new java.awt.Font("Trebuchet MS", 1, 18)); // NOI18N
        btnEliminarMateria.setForeground(new java.awt.Color(255, 255, 255));
        btnEliminarMateria.setText("Eliminar");
        btnEliminarMateria.setToolTipText("");
        btnEliminarMateria.setBorderPainted(false);
        btnEliminarMateria.setContentAreaFilled(false);
        btnEliminarMateria.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEliminarMateria.setFocusPainted(false);
        btnEliminarMateria.setOpaque(true);
        btnEliminarMateria.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEliminarMateriaMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnEliminarMateriaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnEliminarMateriaMouseExited(evt);
            }
        });
        panelMateriasDocen.add(btnEliminarMateria, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 140, 110, -1));

        comboMateria.setFont(new java.awt.Font("Trebuchet MS", 0, 18)); // NOI18N
        comboMateria.setBorder(null);
        comboMateria.setOpaque(false);
        panelMateriasDocen.add(comboMateria, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 100, 370, -1));

        jLabel5.setFont(new java.awt.Font("Trebuchet MS", 1, 20)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(21, 89, 110));
        jLabel5.setText("Materia:");
        panelMateriasDocen.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 70, -1, -1));

        comboDocenteMat.setFont(new java.awt.Font("Trebuchet MS", 0, 18)); // NOI18N
        comboDocenteMat.setBorder(null);
        comboDocenteMat.setOpaque(false);
        panelMateriasDocen.add(comboDocenteMat, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 40, 370, -1));

        jLabel6.setFont(new java.awt.Font("Trebuchet MS", 1, 20)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(21, 89, 110));
        jLabel6.setText("Docente:");
        panelMateriasDocen.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 10, -1, -1));

        lblErrorMateria.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        lblErrorMateria.setForeground(new java.awt.Color(115, 30, 16));
        lblErrorMateria.setText("El docente ya tiene esta materia.");
        panelMateriasDocen.add(lblErrorMateria, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 10, 230, 30));

        tblMaterias.setBackground(new java.awt.Color(231, 235, 230));
        tblMaterias.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null}
            },
            new String [] {
                "Title 1", "Title 2"
            }
        ));
        tblMaterias.setSelectionBackground(new java.awt.Color(21, 89, 110));
        tblMaterias.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblMateriasMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(tblMaterias);

        panelMateriasDocen.add(jScrollPane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 180, -1, 280));

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Administracion/FondoLapiz3.png"))); // NOI18N
        panelMateriasDocen.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 810, 460));

        panelCuerpo.add(panelMateriasDocen, "card4");

        panelCarreraDocen.setBackground(new java.awt.Color(231, 235, 230));
        panelCarreraDocen.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblCarrera.setBackground(new java.awt.Color(231, 235, 230));
        tblCarrera.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Docente", "Carrera"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblCarrera.setSelectionBackground(new java.awt.Color(21, 89, 110));
        tblCarrera.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblCarreraMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tblCarrera);
        if (tblCarrera.getColumnModel().getColumnCount() > 0) {
            tblCarrera.getColumnModel().getColumn(0).setResizable(false);
        }

        panelCarreraDocen.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 180, 450, 280));

        btnGuardarCarrera.setBackground(new java.awt.Color(99, 144, 158));
        btnGuardarCarrera.setFont(new java.awt.Font("Trebuchet MS", 1, 18)); // NOI18N
        btnGuardarCarrera.setForeground(new java.awt.Color(255, 255, 255));
        btnGuardarCarrera.setText("Guardar");
        btnGuardarCarrera.setToolTipText("");
        btnGuardarCarrera.setBorderPainted(false);
        btnGuardarCarrera.setContentAreaFilled(false);
        btnGuardarCarrera.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGuardarCarrera.setFocusPainted(false);
        btnGuardarCarrera.setOpaque(true);
        btnGuardarCarrera.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnGuardarCarreraMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnGuardarCarreraMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnGuardarCarreraMouseExited(evt);
            }
        });
        panelCarreraDocen.add(btnGuardarCarrera, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 140, 110, -1));

        btnEditarCarrera.setBackground(new java.awt.Color(118, 125, 127));
        btnEditarCarrera.setFont(new java.awt.Font("Trebuchet MS", 1, 18)); // NOI18N
        btnEditarCarrera.setForeground(new java.awt.Color(255, 255, 255));
        btnEditarCarrera.setText("Editar");
        btnEditarCarrera.setToolTipText("");
        btnEditarCarrera.setBorderPainted(false);
        btnEditarCarrera.setContentAreaFilled(false);
        btnEditarCarrera.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEditarCarrera.setFocusPainted(false);
        btnEditarCarrera.setOpaque(true);
        btnEditarCarrera.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEditarCarreraMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnEditarCarreraMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnEditarCarreraMouseExited(evt);
            }
        });
        panelCarreraDocen.add(btnEditarCarrera, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 140, 110, -1));

        btnEliminarCarrera.setBackground(new java.awt.Color(118, 125, 127));
        btnEliminarCarrera.setFont(new java.awt.Font("Trebuchet MS", 1, 18)); // NOI18N
        btnEliminarCarrera.setForeground(new java.awt.Color(255, 255, 255));
        btnEliminarCarrera.setText("Eliminar");
        btnEliminarCarrera.setToolTipText("");
        btnEliminarCarrera.setBorderPainted(false);
        btnEliminarCarrera.setContentAreaFilled(false);
        btnEliminarCarrera.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEliminarCarrera.setFocusPainted(false);
        btnEliminarCarrera.setOpaque(true);
        btnEliminarCarrera.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEliminarCarreraMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnEliminarCarreraMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnEliminarCarreraMouseExited(evt);
            }
        });
        panelCarreraDocen.add(btnEliminarCarrera, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 140, 110, -1));

        comboCarrera.setFont(new java.awt.Font("Trebuchet MS", 0, 18)); // NOI18N
        comboCarrera.setBorder(null);
        comboCarrera.setOpaque(false);
        panelCarreraDocen.add(comboCarrera, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 100, 370, -1));

        jLabel7.setFont(new java.awt.Font("Trebuchet MS", 1, 20)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(21, 89, 110));
        jLabel7.setText("Carrera: ");
        panelCarreraDocen.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 70, -1, -1));

        comboDocenteCarrera.setFont(new java.awt.Font("Trebuchet MS", 0, 18)); // NOI18N
        comboDocenteCarrera.setBorder(null);
        comboDocenteCarrera.setOpaque(false);
        panelCarreraDocen.add(comboDocenteCarrera, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 40, 370, -1));

        jLabel8.setFont(new java.awt.Font("Trebuchet MS", 1, 20)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(21, 89, 110));
        jLabel8.setText("Docente:");
        panelCarreraDocen.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 10, -1, -1));

        lblErrorCarrera.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        lblErrorCarrera.setForeground(new java.awt.Color(115, 30, 16));
        lblErrorCarrera.setText("El docente ya tiene esta materia.");
        panelCarreraDocen.add(lblErrorCarrera, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 10, 230, 30));

        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Administracion/FondoLapiz3.png"))); // NOI18N
        panelCarreraDocen.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 810, 460));

        panelCuerpo.add(panelCarreraDocen, "card5");

        panelJornadaDocen.setBackground(new java.awt.Color(231, 235, 230));
        panelJornadaDocen.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblJornada.setBackground(new java.awt.Color(231, 235, 230));
        tblJornada.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Docente", "Carrera"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblJornada.setSelectionBackground(new java.awt.Color(21, 89, 110));
        tblJornada.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblJornadaMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tblJornada);
        if (tblJornada.getColumnModel().getColumnCount() > 0) {
            tblJornada.getColumnModel().getColumn(0).setResizable(false);
        }

        panelJornadaDocen.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 180, 450, 280));

        btnGuardarJornada.setBackground(new java.awt.Color(99, 144, 158));
        btnGuardarJornada.setFont(new java.awt.Font("Trebuchet MS", 1, 18)); // NOI18N
        btnGuardarJornada.setForeground(new java.awt.Color(255, 255, 255));
        btnGuardarJornada.setText("Guardar");
        btnGuardarJornada.setToolTipText("");
        btnGuardarJornada.setBorderPainted(false);
        btnGuardarJornada.setContentAreaFilled(false);
        btnGuardarJornada.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGuardarJornada.setFocusPainted(false);
        btnGuardarJornada.setOpaque(true);
        btnGuardarJornada.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnGuardarJornadaMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnGuardarJornadaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnGuardarJornadaMouseExited(evt);
            }
        });
        panelJornadaDocen.add(btnGuardarJornada, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 140, 110, -1));

        btnEditarJornada.setBackground(new java.awt.Color(118, 125, 127));
        btnEditarJornada.setFont(new java.awt.Font("Trebuchet MS", 1, 18)); // NOI18N
        btnEditarJornada.setForeground(new java.awt.Color(255, 255, 255));
        btnEditarJornada.setText("Editar");
        btnEditarJornada.setToolTipText("");
        btnEditarJornada.setBorderPainted(false);
        btnEditarJornada.setContentAreaFilled(false);
        btnEditarJornada.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEditarJornada.setFocusPainted(false);
        btnEditarJornada.setOpaque(true);
        btnEditarJornada.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEditarJornadaMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnEditarJornadaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnEditarJornadaMouseExited(evt);
            }
        });
        panelJornadaDocen.add(btnEditarJornada, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 140, 110, -1));

        btnEliminarJornada.setBackground(new java.awt.Color(118, 125, 127));
        btnEliminarJornada.setFont(new java.awt.Font("Trebuchet MS", 1, 18)); // NOI18N
        btnEliminarJornada.setForeground(new java.awt.Color(255, 255, 255));
        btnEliminarJornada.setText("Eliminar");
        btnEliminarJornada.setToolTipText("");
        btnEliminarJornada.setBorderPainted(false);
        btnEliminarJornada.setContentAreaFilled(false);
        btnEliminarJornada.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEliminarJornada.setFocusPainted(false);
        btnEliminarJornada.setOpaque(true);
        btnEliminarJornada.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEliminarJornadaMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnEliminarJornadaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnEliminarJornadaMouseExited(evt);
            }
        });
        panelJornadaDocen.add(btnEliminarJornada, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 140, 110, -1));

        comboJornada.setFont(new java.awt.Font("Trebuchet MS", 0, 18)); // NOI18N
        comboJornada.setBorder(null);
        comboJornada.setOpaque(false);
        panelJornadaDocen.add(comboJornada, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 100, 370, -1));

        jLabel9.setFont(new java.awt.Font("Trebuchet MS", 1, 20)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(21, 89, 110));
        jLabel9.setText("Jornada:");
        panelJornadaDocen.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 70, -1, -1));

        comboDocenteJornada.setFont(new java.awt.Font("Trebuchet MS", 0, 18)); // NOI18N
        comboDocenteJornada.setBorder(null);
        comboDocenteJornada.setOpaque(false);
        panelJornadaDocen.add(comboDocenteJornada, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 40, 370, -1));

        jLabel10.setFont(new java.awt.Font("Trebuchet MS", 1, 20)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(21, 89, 110));
        jLabel10.setText("Docente:");
        panelJornadaDocen.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 10, -1, -1));

        lblErrorJornada.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        lblErrorJornada.setForeground(new java.awt.Color(115, 30, 16));
        lblErrorJornada.setText("Ya tiene asignado esta jornada.");
        panelJornadaDocen.add(lblErrorJornada, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 10, 230, 30));

        jLabel20.setBackground(new java.awt.Color(153, 153, 153));
        jLabel20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Administracion/FondoLapiz3.png"))); // NOI18N
        panelJornadaDocen.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 810, 460));

        panelCuerpo.add(panelJornadaDocen, "card6");

        getContentPane().add(panelCuerpo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 40, 810, 460));

        panelHeader.setBackground(new java.awt.Color(9, 28, 32));
        panelHeader.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        panelHeader.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                panelHeaderMouseDragged(evt);
            }
        });
        panelHeader.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                panelHeaderMousePressed(evt);
            }
        });
        panelHeader.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnMenu.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        btnMenu.setForeground(new java.awt.Color(255, 255, 255));
        btnMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Administracion/icons8_Login_26px_1.png"))); // NOI18N
        btnMenu.setToolTipText("Volver al menu");
        btnMenu.setBorderPainted(false);
        btnMenu.setContentAreaFilled(false);
        btnMenu.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnMenu.setFocusPainted(false);
        btnMenu.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btnMenu.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btnMenu.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Administracion/icons8_Login_26px_2.png"))); // NOI18N
        btnMenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnMenuMouseClicked(evt);
            }
        });
        panelHeader.add(btnMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 0, 40, 40));

        lblIngresarDocente.setFont(new java.awt.Font("Trebuchet MS", 1, 24)); // NOI18N
        lblIngresarDocente.setForeground(new java.awt.Color(255, 255, 255));
        lblIngresarDocente.setText("Ingresar Docente");
        lblIngresarDocente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblIngresarDocenteMouseClicked(evt);
            }
        });
        panelHeader.add(lblIngresarDocente, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 0, 200, 40));

        btnJornadaDocente.setBackground(new java.awt.Color(9, 28, 32));
        btnJornadaDocente.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        btnJornadaDocente.setForeground(new java.awt.Color(204, 204, 204));
        btnJornadaDocente.setText("<html>  <center> Jornada <br> Docente </center> </html>");
        btnJornadaDocente.setBorderPainted(false);
        btnJornadaDocente.setContentAreaFilled(false);
        btnJornadaDocente.setFocusPainted(false);
        btnJornadaDocente.setMargin(new java.awt.Insets(1, 2, 1, 2));
        btnJornadaDocente.setOpaque(true);
        btnJornadaDocente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnJornadaDocenteMouseClicked(evt);
            }
        });
        panelHeader.add(btnJornadaDocente, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 10, 80, 30));

        btnActividadesDocente.setBackground(new java.awt.Color(9, 28, 32));
        btnActividadesDocente.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        btnActividadesDocente.setForeground(new java.awt.Color(204, 204, 204));
        btnActividadesDocente.setText("<html>  <center> Actividades <br> Docente </center> </html>");
        btnActividadesDocente.setBorderPainted(false);
        btnActividadesDocente.setContentAreaFilled(false);
        btnActividadesDocente.setFocusPainted(false);
        btnActividadesDocente.setMargin(new java.awt.Insets(1, 2, 1, 2));
        btnActividadesDocente.setOpaque(true);
        btnActividadesDocente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnActividadesDocenteMouseClicked(evt);
            }
        });
        panelHeader.add(btnActividadesDocente, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 10, 80, 30));

        btnMateriasDocente.setBackground(new java.awt.Color(9, 28, 32));
        btnMateriasDocente.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        btnMateriasDocente.setForeground(new java.awt.Color(204, 204, 204));
        btnMateriasDocente.setText("<html>   <center> Materias <br> Docente </center> </html>");
        btnMateriasDocente.setBorderPainted(false);
        btnMateriasDocente.setContentAreaFilled(false);
        btnMateriasDocente.setFocusPainted(false);
        btnMateriasDocente.setMargin(new java.awt.Insets(1, 2, 1, 2));
        btnMateriasDocente.setOpaque(true);
        btnMateriasDocente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnMateriasDocenteMouseClicked(evt);
            }
        });
        panelHeader.add(btnMateriasDocente, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 10, 80, 30));

        btnCarreraDocente.setBackground(new java.awt.Color(9, 28, 32));
        btnCarreraDocente.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        btnCarreraDocente.setForeground(new java.awt.Color(204, 204, 204));
        btnCarreraDocente.setText("<html>  <center> Carrera <br> Docente </center> </html>");
        btnCarreraDocente.setBorderPainted(false);
        btnCarreraDocente.setContentAreaFilled(false);
        btnCarreraDocente.setFocusPainted(false);
        btnCarreraDocente.setMargin(new java.awt.Insets(1, 2, 1, 2));
        btnCarreraDocente.setOpaque(true);
        btnCarreraDocente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCarreraDocenteMouseClicked(evt);
            }
        });
        panelHeader.add(btnCarreraDocente, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 10, 80, 30));

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Administracion/BanerKay.png"))); // NOI18N
        jLabel11.setToolTipText("");
        panelHeader.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        getContentPane().add(panelHeader, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 810, 40));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void tblDocentesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDocentesMouseClicked
        clickTabla(tblDocentes, btnEditar, btnEliminar);
    }//GEN-LAST:event_tblDocentesMouseClicked

    private void btnGuardarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarMouseClicked
        boolean guardar = true;

        String cedula = txtCedula.getText();
        if (!valido.esCedula(cedula)) {
            lblErrorFechaInicio.setVisible(true);
            guardar = false;
        } else if (!nuevoDocente(cedula) && !editando) {
            lblErrorNuevoDocente.setVisible(true);
            guardar = false;
        }

        String nombreCompleto = txtNombreCompleto.getText();
        if (!valido.esLetras(nombreCompleto)) {
            lblErrorFechaFin.setVisible(true);
            guardar = false;
        }

        //Si no encontraron ningun error se guarda
        if (guardar) {

            if (!editando) {
                Docente_Instituto docen = new Docente_Instituto();
                docen.setCedula_docente(cedula);
                docen.setNombre_docente(nombreCompleto);

                SQLDocentes.insertarDocenteInsti(docen);
            }

            if (editando) {

                Docente_Instituto docen = new Docente_Instituto();
                docen.setCedula_docente(cedula);
                docen.setNombre_docente(nombreCompleto);

                SQLDocentes.editarDocenteInsti(docen);
                editando = false;
            }
            actualizarDocen();
            
            //Volvemos a poner el txt cedula que pueda se editable
            txtCedula.setEditable(true);

            txtCedula.setText("");
            txtNombreCompleto.setText("");

            //Cambiamos el estado de los botones  
            Similitudes.btnsModoInactivo(btnEditar, btnEliminar);

            //Ocultamos todos los errores
            lblErrorFechaInicio.setVisible(false);
            lblErrorFechaFin.setVisible(false);
            lblErrorNuevoDocente.setVisible(false);

        }
    }//GEN-LAST:event_btnGuardarMouseClicked

    private void btnGuardarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarMouseEntered
        Similitudes.btnEntered(btnGuardar);
    }//GEN-LAST:event_btnGuardarMouseEntered

    private void btnGuardarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarMouseExited
        Similitudes.btnExited(btnGuardar);
    }//GEN-LAST:event_btnGuardarMouseExited

    private void btnEditarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditarMouseClicked

        try {
            if (filaSelecionada >= 0 && btnAvilitados) {
                filaEditar = filaSelecionada;

                txtCedula.setText(tblDocentes.getValueAt(filaSelecionada, 0).toString());
                txtNombreCompleto.setText(tblDocentes.getValueAt(filaSelecionada, 1).toString());

                //Ponemos el txtCedula en modo no editable
                txtCedula.setEditable(false);

                Similitudes.btnsModoInactivo(btnEliminar, btnEditar);

                editando = true;
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }//GEN-LAST:event_btnEditarMouseClicked

    private void btnEditarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditarMouseEntered
        if (btnAvilitados) {
            Similitudes.btnEntered(btnEditar);
        }
    }//GEN-LAST:event_btnEditarMouseEntered

    private void btnEditarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditarMouseExited
        if (btnAvilitados) {
            Similitudes.btnExited(btnEditar);
        }
    }//GEN-LAST:event_btnEditarMouseExited

    private void btnEliminarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarMouseClicked
        //Eliminamos el docente selecionado 
        SQLDocentes.eliminarDocenteInsti(tblDocentes.getValueAt(filaSelecionada, 0).toString(), true);

        clickEliminar(modeloTabla, btnEditar, btnEliminar);
    }//GEN-LAST:event_btnEliminarMouseClicked

    private void btnEliminarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarMouseEntered
        if (btnAvilitados) {
            Similitudes.btnEntered(btnEliminar);
        }
    }//GEN-LAST:event_btnEliminarMouseEntered

    private void btnEliminarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarMouseExited
        if (btnAvilitados) {
            Similitudes.btnExited(btnEliminar);
        }
    }//GEN-LAST:event_btnEliminarMouseExited

    private void btnMenuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMenuMouseClicked
        AdministracionVtn admin = new AdministracionVtn();
        admin.setVisible(true);
        this.dispose();

        Conexion_Consultas.cerrarConexion();
    }//GEN-LAST:event_btnMenuMouseClicked

    private void panelHeaderMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelHeaderMouseDragged
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();

        this.setLocation(x - mousex, y - mousey);
    }//GEN-LAST:event_panelHeaderMouseDragged

    private void panelHeaderMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelHeaderMousePressed
        mousex = evt.getX();
        mousey = evt.getY();
    }//GEN-LAST:event_panelHeaderMousePressed

    private void btnGuardarActividadMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarActividadMouseClicked
        boolean guardar = true;
        int id_act_docen = 0;
        
        /*if (actividadesDocente.size() > 0) {
            id_act_docen = actividadesDocente.get(actividadesDocente.size() - 1).getId_actividad_docen();
        }
        
        id_act_docen++;*/

        String docente = comboDocenteAct.getSelectedItem().toString();
        String actividad = comboActividad.getSelectedItem().toString();
        
        String ciDocen = docentesInstiF.get(comboDocenteAct.getSelectedIndex()).getCedula_docente(); 

        int idAc = actividadesDB.get(comboActividad.getSelectedIndex()).getId_actividad();
        
        ActividadesDocente act = SQLDocentes.consultarActividadDocente(ciDocen, idAc);
        
        if(act != null){
            SQLDocentes.eliminarActividadesDocentes(act.getId_actividad_docen(), true);
            lblErrorActividad.setVisible(true); 
            actualizarTblActiDocen();  
            guardar = false; 
        }
        
        guardar = false; 

        if (guardar) {

            if (!editando) {

                ActividadesDocente actDocen = new ActividadesDocente();
                actDocen.setId_actividad_docen(id_act_docen);
                actDocen.setFk_docente(ciDocen);
                actDocen.setFk_actividad(idAc); 
                actDocen.setAct_donce_elim(false); 

                SQLDocentes.insertarActividadesDocentes(actDocen);

            }

            if (editando) {
                
                ActividadesDocente actDocen = new ActividadesDocente();
                actDocen.setId_actividad_docen(Integer.parseInt(tblActividadesDocen.getValueAt(filaEditar, 0).toString()));
                actDocen.setFk_docente(ciDocen);
                actDocen.setFk_actividad(idAc);

                SQLDocentes.editarActividadesDocentes(actDocen);

                editando = false;
            }
            
            actualizarTblActiDocen();

            comboDocenteAct.setSelectedIndex(0);
            comboActividad.setSelectedIndex(0);

            lblErrorActividad.setVisible(false);

            //Cambiamos el estado de los botones nuevamente
            Similitudes.btnsModoInactivo(btnEditarActividad, btnEliminarActividad);
        }
    }//GEN-LAST:event_btnGuardarActividadMouseClicked

    private void btnGuardarActividadMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarActividadMouseEntered
        Similitudes.btnEntered(btnGuardarActividad);
    }//GEN-LAST:event_btnGuardarActividadMouseEntered

    private void btnGuardarActividadMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarActividadMouseExited
        Similitudes.btnExited(btnGuardarActividad);
    }//GEN-LAST:event_btnGuardarActividadMouseExited

    private void btnEditarActividadMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditarActividadMouseClicked
        try {
            if (filaSelecionada >= 0 && btnAvilitados) {
                filaEditar = filaSelecionada;

                comboDocenteAct.setSelectedItem(tblActividadesDocen.getValueAt(filaSelecionada, 1));
                comboActividad.setSelectedItem(tblActividadesDocen.getValueAt(filaSelecionada, 2));

                //Si selecionamos editar deshabilitamos los botones editar y eliminar
                Similitudes.btnsModoInactivo(btnEditarActividad, btnEliminarActividad);

                editando = true;
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }//GEN-LAST:event_btnEditarActividadMouseClicked

    private void btnEditarActividadMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditarActividadMouseEntered
        if (btnAvilitados) {
            Similitudes.btnEntered(btnEditarActividad);
        }
    }//GEN-LAST:event_btnEditarActividadMouseEntered

    private void btnEditarActividadMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditarActividadMouseExited
        if (btnAvilitados) {
            Similitudes.btnExited(btnEditarActividad);
        }
    }//GEN-LAST:event_btnEditarActividadMouseExited

    private void btnEliminarActividadMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarActividadMouseClicked
        SQLDocentes.eliminarActividadesDocentes(Integer.parseInt(tblActividadesDocen.getValueAt(filaSelecionada, 0).toString()), true);

        clickEliminar(modeloTablaActividad, btnEditarActividad, btnEliminarActividad);
    }//GEN-LAST:event_btnEliminarActividadMouseClicked

    private void btnEliminarActividadMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarActividadMouseEntered
        if (btnAvilitados) {
            Similitudes.btnEntered(btnEliminarActividad);
        }
    }//GEN-LAST:event_btnEliminarActividadMouseEntered

    private void btnEliminarActividadMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarActividadMouseExited
        if (btnAvilitados) {
            Similitudes.btnExited(btnEliminarActividad);
        }
    }//GEN-LAST:event_btnEliminarActividadMouseExited

    private void lblIngresarDocenteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblIngresarDocenteMouseClicked

        Similitudes.cambioPanel(panelCuerpo, panelDocente);

        actualizarDocen();

        btnActividadesDocente.setBackground(new Color(9, 28, 32));
        btnCarreraDocente.setBackground(new Color(9, 28, 32));
        btnJornadaDocente.setBackground(new Color(9, 28, 32));
        btnMateriasDocente.setBackground(new Color(9, 28, 32));

        btnActividadesDocente.setForeground(new Color(204, 204, 204));
        btnCarreraDocente.setForeground(new Color(204, 204, 204));
        btnJornadaDocente.setForeground(new Color(204, 204, 204));
        btnMateriasDocente.setForeground(new Color(204, 204, 204));

        btnAvilitados = false;

    }//GEN-LAST:event_lblIngresarDocenteMouseClicked

    private void btnActividadesDocenteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnActividadesDocenteMouseClicked

        Similitudes.cambioPanel(panelCuerpo, panelActividadesDocen);
        actualizarDocen();
        
        btnAvilitados = Similitudes.btnsModoInactivo(btnEditar, btnEliminar); 
        
        cambioColorBtns(btnActividadesDocente, btnCarreraDocente, btnJornadaDocente, btnMateriasDocente);

    }//GEN-LAST:event_btnActividadesDocenteMouseClicked

    private void btnMateriasDocenteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMateriasDocenteMouseClicked

        Similitudes.cambioPanel(panelCuerpo, panelMateriasDocen);
        actualizarDocen();
        
        btnAvilitados = Similitudes.btnsModoInactivo(btnEditarMateria, btnEliminarMateria); 
        
        cambioColorBtns(btnMateriasDocente, btnCarreraDocente, btnJornadaDocente, btnActividadesDocente);

    }//GEN-LAST:event_btnMateriasDocenteMouseClicked

    private void btnCarreraDocenteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCarreraDocenteMouseClicked

        Similitudes.cambioPanel(panelCuerpo, panelCarreraDocen);
        actualizarDocen();
        
        btnAvilitados = Similitudes.btnsModoInactivo(btnEditarCarrera, btnEliminarCarrera); 
        
        cambioColorBtns(btnCarreraDocente, btnMateriasDocente, btnJornadaDocente, btnActividadesDocente);

    }//GEN-LAST:event_btnCarreraDocenteMouseClicked

    private void btnJornadaDocenteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnJornadaDocenteMouseClicked
        Similitudes.cambioPanel(panelCuerpo, panelJornadaDocen);
        actualizarDocen();
        
        btnAvilitados = Similitudes.btnsModoInactivo(btnEditarJornada, btnEliminarJornada); 
        
        cambioColorBtns(btnJornadaDocente, btnMateriasDocente, btnCarreraDocente, btnActividadesDocente);
    }//GEN-LAST:event_btnJornadaDocenteMouseClicked

    private void btnGuardarMateriaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarMateriaMouseClicked
        boolean guardar = true;
        int id_materia_docen = 0;
        
        if (materiasDocent.size() > 0) {
            id_materia_docen = materiasDocent.get(materiasDocent.size() - 1).getId_mat_pref();
        }
        id_materia_docen++;

        String docente = comboDocenteMat.getSelectedItem().toString();
        String materia = comboMateria.getSelectedItem().toString();
        
        String ciDocen = docentesInstiF.get(comboDocenteMat.getSelectedIndex()).getCedula_docente(); 
        
        int idMat = materiasDB.get(comboMateria.getSelectedIndex()).getId_materia(); 
        
        MateriasDocente mat =  SQLDocentes.consultarMatDocen(ciDocen, idMat); 
        
       
        if (mat != null) {
            SQLDocentes.eliminarMateriasDocente(mat.getId_mat_pref(), false);
            lblErrorMateria.setVisible(true);
            guardar = false; 
            actualizarTblMatDocen();
        }
        

        if (guardar) {

            if (!editando) {

                MateriasDocente matDocen = new MateriasDocente();
                
                matDocen.setId_mat_pref(id_materia_docen);
                matDocen.setFk_docente(ciDocen);
                matDocen.setFk_materia(idMat);

                SQLDocentes.insertarMateriasDocente(matDocen);
            }

            if (editando) {

                MateriasDocente matDocen = new MateriasDocente();
                matDocen.setId_mat_pref(Integer.parseInt(tblMaterias.getValueAt(filaEditar, 0).toString()));
                matDocen.setFk_docente(ciDocen);
                matDocen.setFk_materia(idMat);

                SQLDocentes.editarMateriasDocente(matDocen);

                editando = false;
            }
            
            actualizarTblMatDocen();

            comboDocenteMat.setSelectedIndex(0);
            comboMateria.setSelectedIndex(0);

            lblErrorMateria.setVisible(false);

            //Cambiamos el estado de los botones nuevamente          
            Similitudes.btnsModoInactivo(btnEditarMateria, btnEliminarMateria);

        }
    }//GEN-LAST:event_btnGuardarMateriaMouseClicked

    private void btnGuardarMateriaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarMateriaMouseEntered
        Similitudes.btnEntered(btnGuardarMateria);
    }//GEN-LAST:event_btnGuardarMateriaMouseEntered

    private void btnGuardarMateriaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarMateriaMouseExited
        Similitudes.btnExited(btnGuardarMateria);
    }//GEN-LAST:event_btnGuardarMateriaMouseExited

    private void btnEditarMateriaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditarMateriaMouseClicked
        try {
            if (filaSelecionada >= 0 && btnAvilitados) {
                filaEditar = filaSelecionada;

                comboDocenteMat.setSelectedItem(tblMaterias.getValueAt(filaSelecionada, 1));
                comboMateria.setSelectedItem(tblMaterias.getValueAt(filaSelecionada, 2));

                Similitudes.btnsModoInactivo(btnEditarMateria, btnEliminarMateria);

                editando = true;
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }//GEN-LAST:event_btnEditarMateriaMouseClicked

    private void btnEditarMateriaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditarMateriaMouseEntered
        if (btnAvilitados) {
            Similitudes.btnEntered(btnEditarMateria);
        }
    }//GEN-LAST:event_btnEditarMateriaMouseEntered

    private void btnEditarMateriaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditarMateriaMouseExited
        if (btnAvilitados) {
            Similitudes.btnExited(btnEditarMateria);
        }
    }//GEN-LAST:event_btnEditarMateriaMouseExited

    private void btnEliminarMateriaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarMateriaMouseClicked
        SQLDocentes.eliminarMateriasDocente(Integer.parseInt(tblMaterias.getValueAt(filaSelecionada, 0).toString()), true);
        clickEliminar(modeloTblMateria, btnEditarMateria, btnEliminarMateria);
    }//GEN-LAST:event_btnEliminarMateriaMouseClicked

    private void btnEliminarMateriaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarMateriaMouseEntered
        if (btnAvilitados) {
            Similitudes.btnEntered(btnEliminarMateria);
        }
    }//GEN-LAST:event_btnEliminarMateriaMouseEntered

    private void btnEliminarMateriaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarMateriaMouseExited
        if (btnAvilitados) {
            Similitudes.btnEntered(btnEliminarMateria);
        }
    }//GEN-LAST:event_btnEliminarMateriaMouseExited

    private void tblCarreraMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblCarreraMouseClicked
        clickTabla(tblCarrera, btnEditarCarrera, btnEliminarCarrera);
    }//GEN-LAST:event_tblCarreraMouseClicked

    private void btnGuardarCarreraMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarCarreraMouseClicked
        boolean guardar = true;

        int id_carr_docen = 0;
        if (carrerasDocente.size() > 0) {
            id_carr_docen = carrerasDocente.get(carrerasDocente.size() - 1).getId_docen_carrera();
        }

        id_carr_docen++;

        String docente = comboDocenteCarrera.getSelectedItem().toString();
        String carrera = comboCarrera.getSelectedItem().toString();
        
        String ciDocen = docentesInstiF.get(comboDocenteCarrera.getSelectedIndex()).getCedula_docente(); 
        
        int idCar =  carrerasDB.get(comboCarrera.getSelectedIndex()).getId_carrera();
        
        DocenteCarrera car = SQLDocentes.consultarCarreraDocen(ciDocen, idCar);
        
        if(car != null){
            SQLDocentes.eliminarCarreraDocente(car.getId_docen_carrera(), false);
            guardar = false; 
            lblErrorCarrera.setVisible(true); 
            actualizarTblCarrDocen();
        }
        


        if (guardar) {

            if (!editando) {
                DocenteCarrera docenCar = new DocenteCarrera();
                
                docenCar.setId_docen_carrera(id_carr_docen);
                docenCar.setFk_carrera(idCar);
                docenCar.setFk_docente_inst(ciDocen);

                SQLDocentes.insertarCarreraDocente(docenCar);
            }

            if (editando) {

                DocenteCarrera docenCar = new DocenteCarrera();
                docenCar.setId_docen_carrera(Integer.parseInt(tblCarrera.getValueAt(filaEditar, 0).toString()));
                docenCar.setFk_carrera(idCar);
                docenCar.setFk_docente_inst(ciDocen);

                SQLDocentes.editarCarreraDocente(docenCar);

                editando = false;
            }
            
            actualizarTblCarrDocen();
            
            comboDocenteCarrera.setSelectedIndex(0);
            comboCarrera.setSelectedIndex(0);

            lblErrorCarrera.setVisible(false);

            //Cambiamos el estado de los botones nuevamente          
            Similitudes.btnsModoInactivo(btnEditarCarrera, btnEliminarCarrera);

        }
    }//GEN-LAST:event_btnGuardarCarreraMouseClicked

    private void btnGuardarCarreraMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarCarreraMouseEntered
        Similitudes.btnEntered(btnGuardarCarrera);
    }//GEN-LAST:event_btnGuardarCarreraMouseEntered

    private void btnGuardarCarreraMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarCarreraMouseExited
        Similitudes.btnExited(btnGuardarCarrera);
    }//GEN-LAST:event_btnGuardarCarreraMouseExited

    private void btnEditarCarreraMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditarCarreraMouseClicked
        try {
            if (filaSelecionada >= 0 && btnAvilitados) {
                filaEditar = filaSelecionada;

                comboDocenteCarrera.setSelectedItem(tblCarrera.getValueAt(filaSelecionada, 1));
                comboCarrera.setSelectedItem(tblCarrera.getValueAt(filaSelecionada, 2));

                Similitudes.btnsModoInactivo(btnEditarCarrera, btnEliminarCarrera);

                editando = true;
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }//GEN-LAST:event_btnEditarCarreraMouseClicked

    private void btnEditarCarreraMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditarCarreraMouseEntered
        if (btnAvilitados) {
            Similitudes.btnEntered(btnEditarCarrera);
        }

    }//GEN-LAST:event_btnEditarCarreraMouseEntered

    private void btnEditarCarreraMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditarCarreraMouseExited
        if (btnAvilitados) {
            Similitudes.btnExited(btnEditarCarrera);
        }
    }//GEN-LAST:event_btnEditarCarreraMouseExited

    private void btnEliminarCarreraMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarCarreraMouseClicked
        SQLDocentes.eliminarCarreraDocente(Integer.parseInt(tblCarrera.getValueAt(filaSelecionada, 0).toString()), true);
        clickEliminar(modeloTblCarrera, btnEditarCarrera, btnEliminarCarrera);
    }//GEN-LAST:event_btnEliminarCarreraMouseClicked

    private void btnEliminarCarreraMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarCarreraMouseEntered
        if (btnAvilitados) {
            Similitudes.btnEntered(btnEliminarCarrera);
        }
    }//GEN-LAST:event_btnEliminarCarreraMouseEntered

    private void btnEliminarCarreraMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarCarreraMouseExited
        if (btnAvilitados) {
            Similitudes.btnExited(btnEliminarCarrera);
        }
    }//GEN-LAST:event_btnEliminarCarreraMouseExited

    private void tblJornadaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblJornadaMouseClicked
        clickTabla(tblJornada, btnEditarJornada, btnEliminarJornada);
    }//GEN-LAST:event_tblJornadaMouseClicked

    private void btnGuardarJornadaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarJornadaMouseClicked
        boolean guardar = true;
        int id_jord_docen = 0;
        if (jornadasDocente.size() > 0) {
            id_jord_docen = jornadasDocente.get(jornadasDocente.size() - 1).getId_jornada_docen();
        }
        id_jord_docen++;

        String docente = comboDocenteJornada.getSelectedItem().toString();
        String jornada = comboJornada.getSelectedItem().toString();
        
        int idDetJorn = jornadasDB.get(comboJornada.getSelectedIndex()).getId_deta_jornada();
        String ciDocen = docentesInstiF.get(comboDocenteJornada.getSelectedIndex()).getCedula_docente(); 
        
        JornadaDocente jdr= SQLDocentes.consultarJornadaDocen(ciDocen, idDetJorn); 
        if(jdr != null){
            
            SQLDocentes.eliminarJornadaDocente(jdr.getId_jornada_docen(), false);
            guardar = false; 
            lblErrorJornada.setVisible(true); 
            actualizarTblJordDocen();
        }
        

        if (guardar) {

            if (!editando) {

                JornadaDocente jdrDocen = new JornadaDocente();
                
                jdrDocen.setId_jornada_docen(id_jord_docen);
                jdrDocen.setFk_docen_insti(ciDocen);
                jdrDocen.setFk_deta_jornd(idDetJorn);

                SQLDocentes.insertarJornadaDocente(jdrDocen);
            }

            if (editando) {

                JornadaDocente jdrDocen = new JornadaDocente();
                jdrDocen.setId_jornada_docen(Integer.parseInt(tblJornada.getValueAt(filaEditar, 0).toString()));
                jdrDocen.setFk_docen_insti(ciDocen);
                jdrDocen.setFk_deta_jornd(idDetJorn);

                SQLDocentes.editarJornadaDocente(jdrDocen);

                editando = false;
            }
            
            actualizarTblJordDocen();
            
            comboDocenteJornada.setSelectedIndex(0);
            comboJornada.setSelectedIndex(0);

            lblErrorJornada.setVisible(false);

            //Cambiamos el estado de los botones nuevamente          
            Similitudes.btnsModoInactivo(btnEditarJornada, btnEliminarJornada);

        }
    }//GEN-LAST:event_btnGuardarJornadaMouseClicked

    private void btnGuardarJornadaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarJornadaMouseEntered
        Similitudes.btnEntered(btnGuardarJornada);
    }//GEN-LAST:event_btnGuardarJornadaMouseEntered

    private void btnGuardarJornadaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarJornadaMouseExited
        Similitudes.btnExited(btnGuardarJornada);
    }//GEN-LAST:event_btnGuardarJornadaMouseExited

    private void btnEditarJornadaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditarJornadaMouseClicked
        try {
            if (filaSelecionada >= 0 && btnAvilitados) {
                filaEditar = filaSelecionada;

                comboDocenteJornada.setSelectedItem(tblJornada.getValueAt(filaSelecionada, 1));
                comboJornada.setSelectedItem(tblJornada.getValueAt(filaSelecionada, 2));

                Similitudes.btnsModoInactivo(btnEditarJornada, btnEliminarJornada);

                editando = true;
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }//GEN-LAST:event_btnEditarJornadaMouseClicked

    private void btnEditarJornadaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditarJornadaMouseEntered
        if (btnAvilitados) {
            Similitudes.btnEntered(btnEditarJornada);
        }
    }//GEN-LAST:event_btnEditarJornadaMouseEntered

    private void btnEditarJornadaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditarJornadaMouseExited
        if (btnAvilitados) {
            Similitudes.btnExited(btnEditarJornada);
        }
    }//GEN-LAST:event_btnEditarJornadaMouseExited

    private void btnEliminarJornadaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarJornadaMouseClicked
        SQLDocentes.eliminarJornadaDocente(Integer.parseInt(tblJornada.getValueAt(filaSelecionada, 0).toString()), true);
        clickEliminar(modeloTblJornada, btnEditarJornada, btnEliminarJornada);
    }//GEN-LAST:event_btnEliminarJornadaMouseClicked

    private void btnEliminarJornadaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarJornadaMouseEntered
        if (btnAvilitados) {
            Similitudes.btnEntered(btnEliminarJornada);
        }
    }//GEN-LAST:event_btnEliminarJornadaMouseEntered

    private void btnEliminarJornadaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarJornadaMouseExited
        if (btnAvilitados) {
            Similitudes.btnExited(btnEliminarJornada);
        }
    }//GEN-LAST:event_btnEliminarJornadaMouseExited

    private void txtCedulaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCedulaKeyTyped
        if (txtCedula.getText().length() == limite) {
            evt.consume();
        } else {
            txtNombreCompleto.setEnabled(false);
            char caracter = evt.getKeyChar();
            // Verificar si la tecla pulsada no es un digito
            if (((caracter < '0') || (caracter > '9')) && (caracter != '\b' /* corresponde a BACK_SPACE */)) {
                System.out.println("Debe ingresar solo caracteres numericos");
                evt.consume(); // ignorar el evento de teclado   
            }
        }
    }//GEN-LAST:event_txtCedulaKeyTyped

    private void txtCedulaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCedulaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (validar.ValidaCedula(txtCedula.getText())) {
                txtNombreCompleto.requestFocus();
                txtNombreCompleto.setEnabled(true);
                System.out.println("Cedula Correcta");
            } else {
                System.out.println("Cedula no valida");
                txtNombreCompleto.setText("");
            }
        }
    }//GEN-LAST:event_txtCedulaKeyPressed

    private void txtNombreCompletoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreCompletoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (txtNombreCompleto != null) {
                btnGuardar.requestFocus();
            }
        }
    }//GEN-LAST:event_txtNombreCompletoKeyPressed

    private void txtNombreCompletoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtNombreCompletoMouseClicked
        if (validar.ValidaCedula(txtCedula.getText())) {
            txtNombreCompleto.setEnabled(true);
            System.out.println("Cedula Correcta");
        } else {
            System.out.println("Cedula no valida");
        }
    }//GEN-LAST:event_txtNombreCompletoMouseClicked

    private void tblMateriasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblMateriasMouseClicked
        clickTabla(tblMaterias, btnEditarMateria, btnEliminarMateria);
    }//GEN-LAST:event_tblMateriasMouseClicked

    private void tblActividadesDocenMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblActividadesDocenMouseClicked
        clickTabla(tblActividadesDocen, btnEditarActividad, btnEliminarActividad);
    }//GEN-LAST:event_tblActividadesDocenMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(DocentesVtn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DocentesVtn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DocentesVtn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DocentesVtn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DocentesVtn().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActividadesDocente;
    private javax.swing.JButton btnCarreraDocente;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEditarActividad;
    private javax.swing.JButton btnEditarCarrera;
    private javax.swing.JButton btnEditarJornada;
    private javax.swing.JButton btnEditarMateria;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnEliminarActividad;
    private javax.swing.JButton btnEliminarCarrera;
    private javax.swing.JButton btnEliminarJornada;
    private javax.swing.JButton btnEliminarMateria;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnGuardarActividad;
    private javax.swing.JButton btnGuardarCarrera;
    private javax.swing.JButton btnGuardarJornada;
    private javax.swing.JButton btnGuardarMateria;
    private javax.swing.JButton btnJornadaDocente;
    private javax.swing.JButton btnMateriasDocente;
    private javax.swing.JButton btnMenu;
    private javax.swing.JComboBox<String> comboActividad;
    private javax.swing.JComboBox<String> comboCarrera;
    private javax.swing.JComboBox<String> comboDocenteAct;
    private javax.swing.JComboBox<String> comboDocenteCarrera;
    private javax.swing.JComboBox<String> comboDocenteJornada;
    private javax.swing.JComboBox<String> comboDocenteMat;
    private javax.swing.JComboBox<String> comboJornada;
    private javax.swing.JComboBox<String> comboMateria;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JLabel lblErrorActividad;
    private javax.swing.JLabel lblErrorCarrera;
    private javax.swing.JLabel lblErrorFechaFin;
    private javax.swing.JLabel lblErrorFechaInicio;
    private javax.swing.JLabel lblErrorJornada;
    private javax.swing.JLabel lblErrorMateria;
    private javax.swing.JLabel lblErrorNuevoDocente;
    private javax.swing.JLabel lblIngresarDocente;
    private javax.swing.JPanel panelActividadesDocen;
    private javax.swing.JPanel panelCarreraDocen;
    private javax.swing.JPanel panelCuerpo;
    private javax.swing.JPanel panelDocente;
    private javax.swing.JPanel panelHeader;
    private javax.swing.JPanel panelJornadaDocen;
    private javax.swing.JPanel panelMateriasDocen;
    private javax.swing.JTable tblActividadesDocen;
    private javax.swing.JTable tblCarrera;
    private javax.swing.JTable tblDocentes;
    private javax.swing.JTable tblJornada;
    private javax.swing.JTable tblMaterias;
    private javax.swing.JTextField txtCedula;
    private javax.swing.JTextField txtNombreCompleto;
    // End of variables declaration//GEN-END:variables
}
