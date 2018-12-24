/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaz.Administrador;

import BaseDatos.Conexion_Consultas;
import BaseDatos.SQLCarreras;
import BaseDatos.SQLMateria;
import Clases.Carrera;
import Clases.Carrera_Jornada;
import Clases.Jornada;
import Clases.Materia;
import Clases.MateriaCarrera;
import Clases.MateriasCiclo;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

//Importamos nuestras validaciones creadas en una clase  
import Clases.Validaciones;
import java.util.ArrayList;
import javax.swing.ImageIcon;

/**
 *
 * @author Johnny
 */
public class CarrerasVtn extends javax.swing.JFrame {

    //Creamos nuestro modelo de tabla 
    DefaultTableModel modeloTabla;
    //Le damos su titulo 
    String tituloTabla[] = {"id", "Carrera", "Modalidad", "Titulo", "Ciclos", "Jornadas"};
    String datos[][] = {};

    //Modelo para materias 
    DefaultTableModel modeloMaterias;
    String tituloMaterias[] = {"id", "Carrera", "Ciclo", "Materia", "Horas"};
    String datosMaterias[][] = {};

    //Para poder mover nuestra ventana 
    int mousex;
    int mousey;

    //Guardamos la posicion de la fila que seleciono 
    int filaSelecionada;
    //Guardamos la posicion de la fila selecionada al momento de clickear en editar
    int filaEditar;

    //Variable para saber si estoy editando o no  
    boolean editando = false;

    //Creamos nuestra variable para validar 
    Validaciones valido = new Validaciones();

    //Variable para saber si los botones estan habilitados o no  
    boolean btnAvilitados = false;

    //Creamos un array con las carreras 
    ArrayList<Carrera> carreras = new ArrayList();
    ArrayList<Carrera> carrerasF = new ArrayList();

    String carrera_copia;
    int id_editar;

    ArrayList<Jornada> jornadas = new ArrayList();

    ArrayList<Carrera_Jornada> jornadasCarrera = new ArrayList();
    ArrayList<Carrera_Jornada> jornadasCarreraF = new ArrayList();

    ArrayList<Materia> materiasDB = new ArrayList();
    //ArrayList<Materia> materiasDBF = new ArrayList();

    //Para saber cual debo eliminar 
    Carrera_Jornada jrdEliminar = new Carrera_Jornada();

    //Cargaremos las matericas que tendra una carrera en un ciclo  
    ArrayList<MateriaCarrera> materiasCarrera = new ArrayList();

    //Cargamos las materias que tendra un ciclo 
    ArrayList<MateriasCiclo> materiasCicloF = new ArrayList();

    //Para saber si ya existe esat carrera materia  
    boolean existeCarreraMateria = false;

    public CarrerasVtn() {
        initComponents();

        Conexion_Consultas.conectar();
        
         //Icono de la palicacion 
                try {
            setIconImage(new ImageIcon(getClass().getResource("../img/LogoKayranaCircularV.1.png")).getImage());
        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());
        }

        //Cargamos todas las materias que tenga una carrera
        if (!SQLCarreras.cargarMateriasCarrera().isEmpty()) {
            materiasCarrera = SQLCarreras.cargarMateriasCarrera();
        }

        actualizarComboMaterias();

        jornadas = Conexion_Consultas.cargarJornadas();

        //Deshabilitamos los botones  
        btnAvilitados = Similitudes.btnsModoInactivo(btnEditar, btnEliminar);
        btnAvilitados = Similitudes.btnsModoInactivo(btnEditarMateria, btnEliminarMateria);

        //Inicializamos nuestra tabla 
        modeloTabla = new DefaultTableModel(datos, tituloTabla);

        modeloMaterias = new DefaultTableModel(datosMaterias, tituloMaterias);

        cargarTblCarrera();

        //Pasamos el modelo a la tabla  
        tblCarreras.setModel(modeloTabla);

        tblMaterias.setModel(modeloMaterias);
        //Modificamos el tamaño de las columnas
        TableColumnModel columModel = tblCarreras.getColumnModel();

        Similitudes.ocultarID(tblCarreras);

        columModel.getColumn(1).setPreferredWidth(290);
        columModel.getColumn(2).setPreferredWidth(90);
        columModel.getColumn(3).setPreferredWidth(300);
        columModel.getColumn(4).setPreferredWidth(60);
        columModel.getColumn(5).setPreferredWidth(70);

        TableColumnModel columMaterias = tblMaterias.getColumnModel();

        Similitudes.ocultarID(tblMaterias);

        columMaterias.getColumn(1).setPreferredWidth(350);
        columMaterias.getColumn(2).setPreferredWidth(50);
        columMaterias.getColumn(3).setPreferredWidth(350);
        columMaterias.getColumn(4).setPreferredWidth(60);

        Similitudes.tituloTbls(tblCarreras);

        Similitudes.tituloTbls(tblMaterias);

        ocultarErrores();
        panelSelecionMaterias.setVisible(false);
    }

    private void ocultarErrores() {
        //Ocultamos todos los errores 
        lblErrorCarrera.setVisible(false);
        lblErrorModalidad.setVisible(false);
        lblErrorTitulo.setVisible(false);
        lblErrorCiclos.setVisible(false);
        lblErrorNuevaCarrera.setVisible(false);
        lblErrorJornada.setVisible(false);

        lblErrorHorasClase1.setVisible(false);
        lblErrorMaterias.setVisible(false);

    }

    private void cargarTblCarrera() {
        if (!SQLCarreras.cargarJornadasCarrera(false).isEmpty()) {
            jornadasCarrera = SQLCarreras.cargarJornadasCarrera(false);
        }

        if (!SQLCarreras.cargarCarrerasDB(true).isEmpty()) {
            modeloTabla.setRowCount(0);

            carrerasF = SQLCarreras.cargarCarrerasDB(true);

            //carreras = Conexion_Consultas.cargarCarrerasDB();
            String carrera;
            String modalidad;
            String titulo;
            int ciclos;
            int numJornadas = 0;
            int id = 0;

            String jornadas = "";

            for (int i = 0; i < carrerasF.size(); i++) {

                id = carrerasF.get(i).getId_carrera();
                carrera = carrerasF.get(i).getNombre_Carrera();
                modalidad = carrerasF.get(i).getModalidad_Carrera();
                titulo = carrerasF.get(i).getTitulo_carrera();
                ciclos = carrerasF.get(i).getNum_ciclos_carrera();
                try {
                    jornadasCarreraF = SQLCarreras.cargarJornadasCarrera(carrerasF.get(i).getId_carrera());

                    for (int j = 0; j < jornadasCarreraF.size(); j++) {
                        if (jornadasCarreraF.get(j).getFk_jornada() == 1) {
                            numJornadas++;
                            jornadas = "M";
                        } else if (jornadasCarreraF.get(j).getFk_jornada() == 2) {
                            if (numJornadas > 0) {
                                jornadas = jornadas + "-V";
                            } else {
                                jornadas = jornadas + "V";
                            }
                            numJornadas++;
                        } else if (jornadasCarreraF.get(j).getFk_carrera() == 3) {
                            if (numJornadas > 1) {
                                jornadas = jornadas + "-N";
                            } else {
                                jornadas = jornadas + "N";
                            }
                        }

                    }
                } catch (NullPointerException e) {
                    System.out.println("El array no tiene datos " + e.getMessage());
                }

                Object valores[] = {id, carrera, modalidad, titulo, ciclos, jornadas};
                modeloTabla.addRow(valores);

            }
        }
    }

    private void actualizarComboMaterias() {
        //Cargamos toda la informacion de materias en la tabla  
        if (!SQLMateria.cargarMaterias(true).isEmpty()) {
            comboMaterias.removeAll();
            materiasDB = SQLMateria.cargarMaterias(true);
            for (int i = 0; i < materiasDB.size(); i++) {
                comboMaterias.addItem(materiasDB.get(i).getNombre_materia());
            }
        }

    }

    public void actualizarComboCarreras() {
        if (!SQLCarreras.cargarCarrerasDB(true).isEmpty()) {
            carrerasF = SQLCarreras.cargarCarrerasDB(true);
        }
        //Cargamos nuestro combo carreras 
        comboCarrera.removeAll();
        for (int i = 0; i < carrerasF.size(); i++) {
            comboCarrera.addItem(carrerasF.get(i).getNombre_Carrera());
        }
    }

    public boolean nuevaCarrera(String carrera) {
        boolean datosNuevos = true;
        String carreraComparar;

        try {
            for (int i = 0; i < carreras.size(); i++) {
                carreraComparar = carreras.get(i).getNombre_Carrera();

                if (carrera.equalsIgnoreCase(carreraComparar)) {
                    SQLCarreras.eliminarCarrera(carreras.get(i).getId_carrera(), false);
                    datosNuevos = false;
                    cargarTblCarrera();
                    break;
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return datosNuevos;
    }

    public boolean nuevaMateria(String carrera, String materia) {
        boolean datosNuevos = true;
        int posMat = comboMaterias.getSelectedIndex();

        int idMateria = materiasDB.get(posMat).getId_materia();
        System.out.println("Esta es la id de la materia " + idMateria);

        int fk_mat_carrera = fkMatCarrera();

        try {
            materiasCicloF = SQLCarreras.cargarMateriasCiclo(true, fk_mat_carrera);
            for (int i = 0; i < materiasCicloF.size(); i++) {
                if (materiasCicloF.get(i).getFk_materia() == idMateria) {
                    SQLCarreras.eliminarMateriasCiclo(materiasCicloF.get(i).getId_mat_ciclo(), false);
                    System.out.println("Entramos en el error de nueva materia");
                    cargarTblMateriasCarrera();
                    datosNuevos = false;
                    break;
                }

            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return datosNuevos;
    }

    public boolean existeJornadaCarrera(int id_carrera, int fkJornada) {
        boolean existe = false;
        jornadasCarreraF = SQLCarreras.cargarJornadasCarrera(id_carrera);
        for (int i = 0; i < jornadasCarreraF.size(); i++) {
            if (jornadasCarreraF.get(i).getFk_jornada() == jornadas.get(fkJornada).getId_jornada()) {
                jrdEliminar = jornadasCarreraF.get(i);
                existe = true;
                break;
            }
        }
        return existe;
    }

    public int fkMatCarrera() {
        materiasCarrera = SQLCarreras.cargarMateriasCarrera();

        int pos = comboCarrera.getSelectedIndex();

        int ciclo = Integer.parseInt(comboCiclos.getSelectedItem().toString());
        int fk_mat_carrera = 0;

        if (materiasCarrera.size() > 0) {
            for (int i = 0; i < materiasCarrera.size(); i++) {
                if (materiasCarrera.get(i).getFk_carrera() == carrerasF.get(pos).getId_carrera()
                        && materiasCarrera.get(i).getCiclo_mat_carrera() == ciclo) {
                    fk_mat_carrera = materiasCarrera.get(i).getId_mat_carrera();
                    System.out.println("Encontramos el id de materia carrera  " + fk_mat_carrera);
                    break;
                }
            }
        }
        return fk_mat_carrera;
    }

    public void cargarTblMateriasCarrera() {
        Similitudes.limpiarTbl(modeloMaterias);

        int pos = comboCarrera.getSelectedIndex();
        int ciclo = Integer.parseInt(comboCiclos.getSelectedItem().toString());

        int fk_mat_carrera = fkMatCarrera();

        String carrera = carrerasF.get(pos).getNombre_Carrera();
        String materia = "Sin materia";

        if (fk_mat_carrera > 0) {

            materiasCicloF = SQLCarreras.cargarMateriasCiclo(true, fk_mat_carrera);

            for (int i = 0; i < materiasCicloF.size(); i++) {
                for (int j = 0; j < materiasDB.size(); j++) {
                    if (materiasCicloF.get(i).getFk_materia() == materiasDB.get(j).getId_materia()) {
                        materia = materiasDB.get(j).getNombre_materia();
                        Object valores[] = {materiasCicloF.get(i).getId_mat_ciclo(),
                            carrera, ciclo, materia, materiasCicloF.get(i).getHoras_materia()};

                        modeloMaterias.addRow(valores);
                    }
                }
            }
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
        panelCarreras = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblCarreras = new JTable(){
            @Override 
            public boolean isCellEditable(int rowIndex, int colIndex){
                return false; 
            }
        };
        jLabel1 = new javax.swing.JLabel();
        txtCarrera = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtTitulo = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtModalidad = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtCiclos = new javax.swing.JTextField();
        btnGuardar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        lblErrorCiclos = new javax.swing.JLabel();
        lblErrorCarrera = new javax.swing.JLabel();
        lblErrorTitulo = new javax.swing.JLabel();
        lblErrorModalidad = new javax.swing.JLabel();
        lblErrorNuevaCarrera = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        lblErrorJornada = new javax.swing.JLabel();
        boxNocturna = new javax.swing.JCheckBox();
        boxMatutina = new javax.swing.JCheckBox();
        boxVespertina = new javax.swing.JCheckBox();
        jLabel5 = new javax.swing.JLabel();
        panelMaterias = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        comboCiclos = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        comboCarrera = new javax.swing.JComboBox<>();
        panelSelecionMaterias = new javax.swing.JPanel();
        btnGuardarMateria = new javax.swing.JButton();
        btnEditarMateria = new javax.swing.JButton();
        btnEliminarMateria = new javax.swing.JButton();
        comboMaterias = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblMaterias = new JTable(){
            @Override 
            public boolean isCellEditable(int rowIndex, int colIndex){
                return false; 
            }
        };
        jLabel11 = new javax.swing.JLabel();
        txtHorasClase = new javax.swing.JTextField();
        lblErrorMaterias = new javax.swing.JLabel();
        lblErrorHorasClase1 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        btnFiltrarMaterias = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        panelHeader = new javax.swing.JPanel();
        btnMenu = new javax.swing.JButton();
        lblIngresarMaterias = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        lblIngresarCarrera = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(810, 500));
        setUndecorated(true);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelCuerpo.setBackground(new java.awt.Color(231, 235, 230));
        panelCuerpo.setLayout(new java.awt.CardLayout());

        panelCarreras.setBackground(new java.awt.Color(231, 235, 230));
        panelCarreras.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane1.setBackground(new java.awt.Color(231, 235, 230));
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        tblCarreras.setBackground(new java.awt.Color(231, 235, 230));
        tblCarreras.setFont(new java.awt.Font("Trebuchet MS", 0, 14)); // NOI18N
        tblCarreras.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null}
            },
            new String [] {
                "Carrera", "Modalidad", "Titulo", "Ciclos", "Jornadas"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Byte.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblCarreras.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tblCarreras.setOpaque(false);
        tblCarreras.setSelectionBackground(new java.awt.Color(21, 89, 110));
        tblCarreras.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblCarreras.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblCarrerasMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblCarreras);
        if (tblCarreras.getColumnModel().getColumnCount() > 0) {
            tblCarreras.getColumnModel().getColumn(0).setResizable(false);
            tblCarreras.getColumnModel().getColumn(0).setPreferredWidth(325);
            tblCarreras.getColumnModel().getColumn(1).setResizable(false);
            tblCarreras.getColumnModel().getColumn(1).setPreferredWidth(100);
            tblCarreras.getColumnModel().getColumn(2).setResizable(false);
            tblCarreras.getColumnModel().getColumn(2).setPreferredWidth(300);
            tblCarreras.getColumnModel().getColumn(3).setResizable(false);
            tblCarreras.getColumnModel().getColumn(3).setPreferredWidth(50);
        }

        panelCarreras.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 210, 810, 250));

        jLabel1.setFont(new java.awt.Font("Trebuchet MS", 1, 20)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(21, 89, 110));
        jLabel1.setText("Carrera: ");
        panelCarreras.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));

        txtCarrera.setBackground(new java.awt.Color(99, 144, 158));
        txtCarrera.setFont(new java.awt.Font("Trebuchet MS", 0, 18)); // NOI18N
        txtCarrera.setOpaque(false);
        panelCarreras.add(txtCarrera, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 20, 370, -1));

        jLabel2.setFont(new java.awt.Font("Trebuchet MS", 1, 20)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(21, 89, 110));
        jLabel2.setText("Titulo:");
        panelCarreras.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, -1, -1));

        txtTitulo.setBackground(new java.awt.Color(99, 144, 158));
        txtTitulo.setFont(new java.awt.Font("Trebuchet MS", 0, 18)); // NOI18N
        txtTitulo.setOpaque(false);
        panelCarreras.add(txtTitulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 70, 370, -1));

        jLabel3.setFont(new java.awt.Font("Trebuchet MS", 1, 20)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(21, 89, 110));
        jLabel3.setText("Modalidad:");
        panelCarreras.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 120, -1, -1));

        txtModalidad.setBackground(new java.awt.Color(99, 144, 158));
        txtModalidad.setFont(new java.awt.Font("Trebuchet MS", 0, 18)); // NOI18N
        txtModalidad.setOpaque(false);
        panelCarreras.add(txtModalidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 120, 180, -1));

        jLabel4.setFont(new java.awt.Font("Trebuchet MS", 1, 20)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(21, 89, 110));
        jLabel4.setText("Ciclos:");
        panelCarreras.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, -1, -1));

        txtCiclos.setBackground(new java.awt.Color(99, 144, 158));
        txtCiclos.setFont(new java.awt.Font("Trebuchet MS", 0, 18)); // NOI18N
        txtCiclos.setOpaque(false);
        panelCarreras.add(txtCiclos, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 120, 40, -1));

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
        panelCarreras.add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 170, 110, -1));

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
        panelCarreras.add(btnEditar, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 170, 110, -1));

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
        panelCarreras.add(btnEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 170, 110, -1));

        lblErrorCiclos.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        lblErrorCiclos.setForeground(new java.awt.Color(115, 30, 16));
        lblErrorCiclos.setText("Solo debe ingresar numeros.");
        panelCarreras.add(lblErrorCiclos, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 150, 190, -1));

        lblErrorCarrera.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        lblErrorCarrera.setForeground(new java.awt.Color(115, 30, 16));
        lblErrorCarrera.setText("Solo puede ingresar letras. ");
        panelCarreras.add(lblErrorCarrera, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 50, 340, -1));

        lblErrorTitulo.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        lblErrorTitulo.setForeground(new java.awt.Color(115, 30, 16));
        lblErrorTitulo.setText("Solo puede ingresar letras. ");
        panelCarreras.add(lblErrorTitulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 100, 340, -1));

        lblErrorModalidad.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        lblErrorModalidad.setForeground(new java.awt.Color(115, 30, 16));
        lblErrorModalidad.setText("Solo puede ingresar letras. ");
        panelCarreras.add(lblErrorModalidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 150, 190, -1));

        lblErrorNuevaCarrera.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        lblErrorNuevaCarrera.setForeground(new java.awt.Color(115, 30, 16));
        lblErrorNuevaCarrera.setText("Ya registro esta carrera. ");
        panelCarreras.add(lblErrorNuevaCarrera, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 0, 270, -1));

        jLabel8.setFont(new java.awt.Font("Trebuchet MS", 1, 20)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(21, 89, 110));
        jLabel8.setText("Jornada: ");
        panelCarreras.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 20, 100, -1));

        lblErrorJornada.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        lblErrorJornada.setForeground(new java.awt.Color(115, 30, 16));
        lblErrorJornada.setText("<html> Por lo menos debe selecionar una jornada. </html>");
        panelCarreras.add(lblErrorJornada, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 80, 200, 40));

        boxNocturna.setFont(new java.awt.Font("Trebuchet MS", 0, 16)); // NOI18N
        boxNocturna.setText("Nocturna");
        boxNocturna.setOpaque(false);
        panelCarreras.add(boxNocturna, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 60, -1, -1));

        boxMatutina.setFont(new java.awt.Font("Trebuchet MS", 0, 16)); // NOI18N
        boxMatutina.setText("Matutina");
        boxMatutina.setOpaque(false);
        panelCarreras.add(boxMatutina, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 20, -1, -1));

        boxVespertina.setFont(new java.awt.Font("Trebuchet MS", 0, 16)); // NOI18N
        boxVespertina.setText("Vespertina");
        boxVespertina.setOpaque(false);
        panelCarreras.add(boxVespertina, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 40, -1, -1));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Administracion/FondoLapiz3.png"))); // NOI18N
        panelCarreras.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 810, 460));

        panelCuerpo.add(panelCarreras, "card19");

        panelMaterias.setBackground(new java.awt.Color(231, 235, 230));
        panelMaterias.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel7.setFont(new java.awt.Font("Trebuchet MS", 1, 20)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(21, 89, 110));
        jLabel7.setText("Ciclo:");
        panelMaterias.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 20, -1, -1));

        comboCiclos.setFont(new java.awt.Font("Trebuchet MS", 0, 18)); // NOI18N
        comboCiclos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboCiclosActionPerformed(evt);
            }
        });
        panelMaterias.add(comboCiclos, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 20, 50, -1));

        jLabel9.setFont(new java.awt.Font("Trebuchet MS", 1, 20)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(21, 89, 110));
        jLabel9.setText("Carrera:");
        panelMaterias.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, -1, -1));

        comboCarrera.setFont(new java.awt.Font("Trebuchet MS", 0, 18)); // NOI18N
        comboCarrera.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboCarreraActionPerformed(evt);
            }
        });
        panelMaterias.add(comboCarrera, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 20, 390, -1));

        panelSelecionMaterias.setBackground(new java.awt.Color(231, 235, 230));
        panelSelecionMaterias.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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
        panelSelecionMaterias.add(btnGuardarMateria, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 50, 110, -1));

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
        panelSelecionMaterias.add(btnEditarMateria, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 50, 110, -1));

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
        panelSelecionMaterias.add(btnEliminarMateria, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 50, 110, -1));

        comboMaterias.setFont(new java.awt.Font("Trebuchet MS", 0, 18)); // NOI18N
        panelSelecionMaterias.add(comboMaterias, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 10, 390, -1));

        jLabel10.setFont(new java.awt.Font("Trebuchet MS", 1, 20)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(21, 89, 110));
        jLabel10.setText("Horas clase:");
        panelSelecionMaterias.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 10, -1, -1));

        jScrollPane2.setBackground(new java.awt.Color(231, 235, 230));
        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        tblMaterias.setBackground(new java.awt.Color(231, 235, 230));
        tblMaterias.setFont(new java.awt.Font("Trebuchet MS", 0, 14)); // NOI18N
        tblMaterias.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null}
            },
            new String [] {
                "Carrera", "Ciclo", "Materia", "Horas Clase"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblMaterias.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tblMaterias.setOpaque(false);
        tblMaterias.setSelectionBackground(new java.awt.Color(21, 89, 110));
        tblMaterias.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblMaterias.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblMateriasMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblMaterias);
        if (tblMaterias.getColumnModel().getColumnCount() > 0) {
            tblMaterias.getColumnModel().getColumn(0).setResizable(false);
            tblMaterias.getColumnModel().getColumn(0).setPreferredWidth(325);
            tblMaterias.getColumnModel().getColumn(1).setResizable(false);
            tblMaterias.getColumnModel().getColumn(1).setPreferredWidth(100);
            tblMaterias.getColumnModel().getColumn(2).setResizable(false);
            tblMaterias.getColumnModel().getColumn(2).setPreferredWidth(300);
        }

        panelSelecionMaterias.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 100, 810, 290));

        jLabel11.setFont(new java.awt.Font("Trebuchet MS", 1, 20)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(21, 89, 110));
        jLabel11.setText("Materia:");
        panelSelecionMaterias.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, -1, -1));

        txtHorasClase.setBackground(new java.awt.Color(99, 144, 158));
        txtHorasClase.setFont(new java.awt.Font("Trebuchet MS", 0, 18)); // NOI18N
        txtHorasClase.setOpaque(false);
        panelSelecionMaterias.add(txtHorasClase, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 10, 50, -1));

        lblErrorMaterias.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        lblErrorMaterias.setForeground(new java.awt.Color(115, 30, 16));
        lblErrorMaterias.setText("<html>Esta carrera, ya tiene asignada esta materia.</html>");
        panelSelecionMaterias.add(lblErrorMaterias, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 80, 310, 20));

        lblErrorHorasClase1.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        lblErrorHorasClase1.setForeground(new java.awt.Color(115, 30, 16));
        lblErrorHorasClase1.setText("<html>Solo debe ingresar numeros.</html>");
        panelSelecionMaterias.add(lblErrorHorasClase1, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 10, 80, 50));

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Administracion/FondoLapiz3.png"))); // NOI18N
        panelSelecionMaterias.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -10, -1, 400));

        panelMaterias.add(panelSelecionMaterias, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, -1, 390));

        btnFiltrarMaterias.setBackground(new java.awt.Color(99, 144, 158));
        btnFiltrarMaterias.setFont(new java.awt.Font("Trebuchet MS", 1, 18)); // NOI18N
        btnFiltrarMaterias.setForeground(new java.awt.Color(255, 255, 255));
        btnFiltrarMaterias.setText("Filtrar");
        btnFiltrarMaterias.setToolTipText("");
        btnFiltrarMaterias.setBorderPainted(false);
        btnFiltrarMaterias.setContentAreaFilled(false);
        btnFiltrarMaterias.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnFiltrarMaterias.setFocusPainted(false);
        btnFiltrarMaterias.setOpaque(true);
        btnFiltrarMaterias.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnFiltrarMateriasMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnFiltrarMateriasMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnFiltrarMateriasMouseExited(evt);
            }
        });
        panelMaterias.add(btnFiltrarMaterias, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 20, 110, -1));
        panelMaterias.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 810, 10));

        panelCuerpo.add(panelMaterias, "card20");

        getContentPane().add(panelCuerpo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 40, -1, 460));

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

        lblIngresarMaterias.setBackground(new java.awt.Color(9, 28, 31));
        lblIngresarMaterias.setFont(new java.awt.Font("Trebuchet MS", 1, 24)); // NOI18N
        lblIngresarMaterias.setForeground(new java.awt.Color(255, 255, 255));
        lblIngresarMaterias.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblIngresarMaterias.setText("Clasificar Materias");
        lblIngresarMaterias.setOpaque(true);
        lblIngresarMaterias.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblIngresarMateriasMouseClicked(evt);
            }
        });
        panelHeader.add(lblIngresarMaterias, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 0, 220, 40));

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Administracion/BanerKay.png"))); // NOI18N
        jLabel12.setToolTipText("");
        panelHeader.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        lblIngresarCarrera.setBackground(new java.awt.Color(231, 235, 230));
        lblIngresarCarrera.setFont(new java.awt.Font("Trebuchet MS", 1, 24)); // NOI18N
        lblIngresarCarrera.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblIngresarCarrera.setText("Ingresar Carreras");
        lblIngresarCarrera.setOpaque(true);
        lblIngresarCarrera.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblIngresarCarreraMouseClicked(evt);
            }
        });
        panelHeader.add(lblIngresarCarrera, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 260, 40));

        getContentPane().add(panelHeader, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 810, 40));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnMenuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMenuMouseClicked
        AdministracionVtn admin = new AdministracionVtn();
        admin.setVisible(true);
        this.dispose();

        Conexion_Consultas.cerrarConexion();
    }//GEN-LAST:event_btnMenuMouseClicked

    private void btnGuardarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarMouseClicked
        boolean guardar = true;

        int id_carrera = 0;

        if (carreras.size() > 0) {
            id_carrera = carreras.get(carreras.size() - 1).getId_carrera();
        }

        id_carrera++;

        String carrera = txtCarrera.getText();
        if (!valido.esLetras(carrera)) {
            lblErrorCarrera.setVisible(true);
            guardar = false;
        } else if (!editando && !nuevaCarrera(carrera)) { //Aqui validamos que esta carrera no se encuentre ya registrada
            lblErrorNuevaCarrera.setVisible(true);
            guardar = false;
        }

        String modalidad = txtModalidad.getText();
        if (!valido.esLetras(modalidad)) {
            lblErrorModalidad.setVisible(true);
            guardar = false;
        }

        String titulo = txtTitulo.getText();
        if (!valido.esLetras(titulo)) {
            lblErrorTitulo.setVisible(true);
            guardar = false;
        }

        String ciclosString = txtCiclos.getText();
        if (!valido.esNumero(ciclosString)) {
            lblErrorCiclos.setVisible(true);
            guardar = false;
        }

        int ciclos = 0;

        if (valido.esNumero(ciclosString)) {
            ciclos = Integer.parseInt(txtCiclos.getText());
        }

        if (!boxMatutina.isSelected() && !boxVespertina.isSelected() && !boxNocturna.isSelected()) {
            lblErrorJornada.setVisible(true);
            guardar = false;
        }

        Carrera clase_carrera = new Carrera();

        Carrera_Jornada jrdMatutina = new Carrera_Jornada();
        Carrera_Jornada jrdVespertina = new Carrera_Jornada();
        Carrera_Jornada jrdNocturna = new Carrera_Jornada();

        //Si no encontraron ningun error se guarda 
        if (guardar) {
            //Editamos nuestras jornadas
            if (!editando) {

                clase_carrera.setId_carrera(id_carrera);
                clase_carrera.setNombre_Carrera(carrera);
                clase_carrera.setModalidad_Carrera(modalidad);
                clase_carrera.setTitulo_carrera(titulo);
                clase_carrera.setNum_ciclos_carrera(ciclos);
                clase_carrera.setCarrera_elim(false);

                SQLCarreras.insertarCarrera(clase_carrera);

                //Ahora agregamos las jornadas que tiene esta carrera
                if (boxMatutina.isSelected()) {

                    jrdMatutina.setFk_jornada(jornadas.get(0).getId_jornada());
                    jrdMatutina.setFk_carrera(id_carrera);

                    if (!existeJornadaCarrera(id_carrera, 0)) {
                        SQLCarreras.insertarJornadasCarrera(jrdMatutina);
                        clase_carrera.agregarJornada(jornadas.get(0));
                    }

                }

                if (boxVespertina.isSelected()) {

                    jrdVespertina.setFk_jornada(jornadas.get(1).getId_jornada());
                    jrdVespertina.setFk_carrera(id_carrera);

                    if (!existeJornadaCarrera(id_carrera, 1)) {
                        SQLCarreras.insertarJornadasCarrera(jrdVespertina);
                        clase_carrera.agregarJornada(jornadas.get(1));
                    }

                }

                if (boxNocturna.isSelected()) {

                    jrdNocturna.setFk_jornada(jornadas.get(2).getId_jornada());
                    jrdNocturna.setFk_carrera(id_carrera);

                    if (!existeJornadaCarrera(id_carrera, 2)) {
                        SQLCarreras.insertarJornadasCarrera(jrdNocturna);
                        clase_carrera.agregarJornada(jornadas.get(2));
                    }
                }

            }

            if (editando) {

                id_carrera = Integer.parseInt(tblCarreras.getValueAt(filaEditar, 0).toString());

                clase_carrera.setId_carrera(id_carrera);
                clase_carrera.setNombre_Carrera(carrera);
                clase_carrera.setModalidad_Carrera(modalidad);
                clase_carrera.setTitulo_carrera(titulo);
                clase_carrera.setNum_ciclos_carrera(ciclos);
                clase_carrera.setCarrera_elim(false);

                SQLCarreras.editarCarrera(clase_carrera);

                if (!boxMatutina.isSelected()) {
                    if (existeJornadaCarrera(id_carrera, 0)) {
                        SQLCarreras.eliminarJornadasCarrera(jrdEliminar);
                    }
                } else {
                    jrdMatutina.setFk_jornada(jornadas.get(0).getId_jornada());
                    jrdMatutina.setFk_carrera(id_carrera);

                    if (!existeJornadaCarrera(id_carrera, 0)) {
                        SQLCarreras.insertarJornadasCarrera(jrdMatutina);
                        clase_carrera.agregarJornada(jornadas.get(0));
                    }
                }

                if (!boxVespertina.isSelected()) {
                    if (existeJornadaCarrera(id_carrera, 1)) {
                        SQLCarreras.eliminarJornadasCarrera(jrdEliminar);
                    }
                } else {
                    jrdVespertina.setFk_jornada(jornadas.get(1).getId_jornada());
                    jrdVespertina.setFk_carrera(id_carrera);

                    if (!existeJornadaCarrera(id_carrera, 1)) {
                        SQLCarreras.insertarJornadasCarrera(jrdVespertina);
                        clase_carrera.agregarJornada(jornadas.get(1));
                    }
                }

                if (!boxNocturna.isSelected()) {
                    if (existeJornadaCarrera(id_carrera, 2)) {
                        SQLCarreras.eliminarJornadasCarrera(jrdEliminar);
                    }
                } else {
                    jrdNocturna.setFk_jornada(jornadas.get(2).getId_jornada());
                    jrdNocturna.setFk_carrera(id_carrera);

                    if (!existeJornadaCarrera(id_carrera, 2)) {
                        SQLCarreras.insertarJornadasCarrera(jrdNocturna);
                        clase_carrera.agregarJornada(jornadas.get(2));
                    }
                }

                editando = false;
            }

            txtCarrera.setText("");
            txtModalidad.setText("");
            txtTitulo.setText("");
            txtCiclos.setText("");

            boxMatutina.setSelected(false);
            boxVespertina.setSelected(false);
            boxNocturna.setSelected(false);

            //Cambiamos el estado de los botones nuevamente 
            btnAvilitados = Similitudes.btnsModoInactivo(btnEditar, btnEliminar);

            cargarTblCarrera();

            ocultarErrores();
        }
    }//GEN-LAST:event_btnGuardarMouseClicked

    private void tblCarrerasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblCarrerasMouseClicked

        try {
            filaSelecionada = tblCarreras.getSelectedRow();
            //Activamos nuestros botones 
            if (filaSelecionada >= 0) {
                btnAvilitados = Similitudes.btnsModoActivo(btnEditar, btnEliminar);
            }

        } catch (IndexOutOfBoundsException e) {
            System.out.println("Error: " + e.getMessage());
        }


    }//GEN-LAST:event_tblCarrerasMouseClicked

    private void btnEditarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditarMouseClicked

        try {
            if (filaSelecionada >= 0 && btnAvilitados) {
                filaEditar = filaSelecionada;

                jornadasCarreraF = SQLCarreras.cargarJornadasCarrera(Integer.parseInt(tblCarreras.getValueAt(filaSelecionada, 0).toString()));

                for (int j = 0; j < jornadasCarreraF.size(); j++) {
                    if (jornadasCarreraF.get(j).getFk_jornada() == 1) {
                        boxMatutina.setSelected(true);
                    } else if (jornadasCarreraF.get(j).getFk_jornada() == 2) {
                        boxVespertina.setSelected(true);
                    } else if (jornadasCarreraF.get(j).getFk_carrera() == 3) {
                        boxNocturna.setSelected(true);
                    }
                }

                txtCarrera.setText(tblCarreras.getValueAt(filaSelecionada, 1).toString());
                txtModalidad.setText(tblCarreras.getValueAt(filaSelecionada, 2).toString());
                txtTitulo.setText(tblCarreras.getValueAt(filaSelecionada, 3).toString());
                txtCiclos.setText(tblCarreras.getValueAt(filaSelecionada, 4).toString());

                //Si selecionamos editar deshabilitamos los botones editar y eliminar
                btnAvilitados = Similitudes.btnsModoInactivo(btnEditar, btnEliminar);

                editando = true;
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Error: " + e.getMessage());
        }


    }//GEN-LAST:event_btnEditarMouseClicked

    private void btnEliminarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarMouseClicked

        try {
            //Eliminamos la fila que este selecionada  
            if (filaSelecionada >= 0 && btnAvilitados) {
                SQLCarreras.eliminarCarrera(Integer.parseInt(tblCarreras.getValueAt(filaSelecionada, 0).toString()), true);

                modeloTabla.removeRow(filaSelecionada);
            }

            //Cambiamos los botones a inactivos
            btnAvilitados = Similitudes.btnsModoInactivo(btnEditar, btnEliminar);
            editando = false;
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Error: " + e.getMessage());
        }


    }//GEN-LAST:event_btnEliminarMouseClicked

    private void panelHeaderMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelHeaderMousePressed
        mousex = evt.getX();
        mousey = evt.getY();
    }//GEN-LAST:event_panelHeaderMousePressed

    private void panelHeaderMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelHeaderMouseDragged
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();

        this.setLocation(x - mousex, y - mousey);
    }//GEN-LAST:event_panelHeaderMouseDragged

    private void btnGuardarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarMouseEntered
        Similitudes.btnEntered(btnGuardar);
    }//GEN-LAST:event_btnGuardarMouseEntered

    private void btnGuardarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarMouseExited
        Similitudes.btnExited(btnGuardar);
    }//GEN-LAST:event_btnGuardarMouseExited

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

    private void btnGuardarMateriaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarMateriaMouseClicked
        boolean guardar = true;
        int id_materia_carrera = 0;
        int id_materia_ciclo = 0;
        
        existeCarreraMateria = false; 

        int pos = comboCarrera.getSelectedIndex();

        String ciclo = comboCiclos.getSelectedItem().toString();

        //Cargamos todas las materias que tenga una carrera
        materiasCarrera = SQLCarreras.cargarMateriasCarrera();

        if (materiasCarrera.size() > 0) {
            for (int i = 0; i < materiasCarrera.size(); i++) {

                if (materiasCarrera.get(i).getFk_carrera() == carrerasF.get(pos).getId_carrera()
                        && materiasCarrera.get(i).getCiclo_mat_carrera() == Integer.parseInt(ciclo)) {
                    id_materia_carrera = materiasCarrera.get(i).getId_mat_carrera();
                    existeCarreraMateria = true;
                    break;
                }
            }

            if (!existeCarreraMateria) {
                id_materia_carrera = materiasCarrera.get(materiasCarrera.size() - 1).getId_mat_carrera();
                id_materia_carrera++;
                System.out.println("Tamaño de mi materias carrera " + materiasCarrera.size());
                System.out.println("Este es la id materia carrera " + id_materia_carrera);
            }
        }

        if (materiasCicloF.size() > 0) {
            id_materia_ciclo = materiasCicloF.get(materiasCicloF.size() - 1).getId_mat_ciclo();
        }

        id_materia_ciclo++;

        String carrera = comboCarrera.getSelectedItem().toString();

        String materias = comboMaterias.getSelectedItem().toString();

        String numeroHoras = txtHorasClase.getText();
        if (!valido.esNumero(numeroHoras)) {
            guardar = false;
            lblErrorHorasClase1.setVisible(true);
        }

        int posMateria = comboMaterias.getSelectedIndex();

        if (!editando && !nuevaMateria(carrera, materias)) {
            lblErrorMaterias.setVisible(true);
            guardar = false;
        }

        //Si no encontraron ningun error se guarda 
        if (guardar) {

            if (!existeCarreraMateria) {

                MateriaCarrera nueva = new MateriaCarrera();
                nueva.setId_mat_carrera(id_materia_carrera);
                nueva.setCiclo_mat_carrera(Integer.parseInt(ciclo));

                nueva.setFk_carrera(carrerasF.get(pos).getId_carrera());

                SQLCarreras.insertarMateriasCarrera(nueva);
            }

            if (id_materia_carrera == 0) {
                id_materia_carrera++;
            }

            if (!editando) {
                //Se lo agregamos al modelo 

                MateriasCiclo matCiclo = new MateriasCiclo();

                matCiclo.setId_mat_ciclo(id_materia_ciclo);
                matCiclo.setFk_mat_carrera(id_materia_carrera);
                matCiclo.setFk_materia(materiasDB.get(posMateria).getId_materia());
                matCiclo.setHoras_materia(Integer.parseInt(numeroHoras));

                SQLCarreras.insertarMateriasCiclo(matCiclo);
            }

            if (editando) {

                MateriasCiclo matCiclo = new MateriasCiclo();

                matCiclo.setId_mat_ciclo(Integer.parseInt(tblMaterias.getValueAt(filaEditar, 0).toString()));
                matCiclo.setFk_mat_carrera(id_materia_carrera);
                matCiclo.setFk_materia(materiasDB.get(posMateria).getId_materia());
                matCiclo.setHoras_materia(Integer.parseInt(numeroHoras));

                SQLCarreras.editarMateriasCiclo(matCiclo);

                editando = false;
            }

            txtHorasClase.setText("");
            comboMaterias.setSelectedIndex(0);

            //Cambiamos el estado de los botones nuevamente 
            btnAvilitados = Similitudes.btnsModoInactivo(btnEditarMateria, btnEliminarMateria);

            cargarTblMateriasCarrera();

            ocultarErrores();
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

                comboMaterias.setSelectedItem(tblMaterias.getValueAt(filaSelecionada, 3).toString());
                txtHorasClase.setText(tblMaterias.getValueAt(filaSelecionada, 4).toString());

                //Si selecionamos editar deshabilitamos los botones editar y eliminar
                btnAvilitados = Similitudes.btnsModoInactivo(btnEditarMateria, btnEliminarMateria);
                editando = true;
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Error al editar materias: " + e.getMessage());
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
        try {
            //Eliminamos la fila que este selecionada  
            if (filaSelecionada >= 0 && btnAvilitados) {
                System.out.println("Esta es la id de lo que eliminare "+tblMaterias.getValueAt(filaSelecionada, 0).toString());
                SQLCarreras.eliminarMateriasCiclo(Integer.parseInt(tblMaterias.getValueAt(filaSelecionada, 0).toString()), true);
                
                modeloMaterias.removeRow(filaSelecionada);
            }

            //Cambiamos los botones a inactivos
            btnAvilitados = Similitudes.btnsModoInactivo(btnEditarMateria, btnEliminarMateria);
            editando = false;
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }//GEN-LAST:event_btnEliminarMateriaMouseClicked

    private void btnEliminarMateriaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarMateriaMouseEntered
        if (btnAvilitados) {
            Similitudes.btnEntered(btnEliminarMateria);
        }
    }//GEN-LAST:event_btnEliminarMateriaMouseEntered

    private void btnEliminarMateriaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarMateriaMouseExited
        if (btnAvilitados) {
            Similitudes.btnExited(btnEliminarMateria);
        }
    }//GEN-LAST:event_btnEliminarMateriaMouseExited

    private void btnFiltrarMateriasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnFiltrarMateriasMouseClicked
        cargarTblMateriasCarrera();
        panelSelecionMaterias.setVisible(true);
    }//GEN-LAST:event_btnFiltrarMateriasMouseClicked

    private void btnFiltrarMateriasMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnFiltrarMateriasMouseEntered
        Similitudes.btnEntered(btnFiltrarMaterias);
    }//GEN-LAST:event_btnFiltrarMateriasMouseEntered

    private void btnFiltrarMateriasMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnFiltrarMateriasMouseExited
        Similitudes.btnExited(btnFiltrarMaterias);
    }//GEN-LAST:event_btnFiltrarMateriasMouseExited

    private void tblMateriasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblMateriasMouseClicked
        try {
            filaSelecionada = tblMaterias.getSelectedRow();
            //Activamos nuestros botones 
            if (filaSelecionada >= 0) {
                btnAvilitados = Similitudes.btnsModoActivo(btnEditarMateria, btnEliminarMateria);
            }

        } catch (IndexOutOfBoundsException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }//GEN-LAST:event_tblMateriasMouseClicked

    private void lblIngresarCarreraMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblIngresarCarreraMouseClicked

        Similitudes.cambioPanel(panelCuerpo, panelCarreras);

        lblIngresarCarrera.setBackground(new Color(231, 235, 230));
        lblIngresarMaterias.setBackground(new Color(9, 28, 31));

        lblIngresarCarrera.setForeground(new Color(0, 0, 0));
        lblIngresarMaterias.setForeground(new Color(255, 255, 255));

        panelSelecionMaterias.setVisible(false);

        btnAvilitados = Similitudes.btnsModoInactivo(btnEditar, btnEliminar);

    }//GEN-LAST:event_lblIngresarCarreraMouseClicked

    private void lblIngresarMateriasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblIngresarMateriasMouseClicked
        actualizarComboCarreras();

        Similitudes.cambioPanel(panelCuerpo, panelMaterias);

        lblIngresarMaterias.setBackground(new Color(231, 235, 230));
        lblIngresarCarrera.setBackground(new Color(9, 28, 31));

        lblIngresarMaterias.setForeground(new Color(0, 0, 0));
        lblIngresarCarrera.setForeground(new Color(255, 255, 255));

        //Ponemos en oculto neustra tabla de materias apa que le den filtar de nuevo y no existan errores 
        panelSelecionMaterias.setVisible(false);
        btnAvilitados = Similitudes.btnsModoInactivo(btnEditarMateria, btnEliminarMateria);
        //Le volvemos a false 
        existeCarreraMateria = false;
    }//GEN-LAST:event_lblIngresarMateriasMouseClicked

    private void comboCarreraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboCarreraActionPerformed
        //String carrera = comboCarrera.getSelectedItem().toString();
        int pos = comboCarrera.getSelectedIndex();

        int numCiclos = carrerasF.get(pos).getNum_ciclos_carrera();

        comboCiclos.removeAllItems();

        for (int i = 0; i < numCiclos; i++) {
            comboCiclos.addItem(String.valueOf(i + 1));
        }

        panelSelecionMaterias.setVisible(false);
        btnAvilitados = Similitudes.btnsModoInactivo(btnEditarMateria, btnEliminarMateria);


    }//GEN-LAST:event_comboCarreraActionPerformed

    private void comboCiclosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboCiclosActionPerformed
        panelSelecionMaterias.setVisible(false);

        btnAvilitados = Similitudes.btnsModoInactivo(btnEditarMateria, btnEliminarMateria);
    }//GEN-LAST:event_comboCiclosActionPerformed

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
            java.util.logging.Logger.getLogger(CarrerasVtn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CarrerasVtn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CarrerasVtn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CarrerasVtn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CarrerasVtn().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox boxMatutina;
    private javax.swing.JCheckBox boxNocturna;
    private javax.swing.JCheckBox boxVespertina;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEditarMateria;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnEliminarMateria;
    private javax.swing.JButton btnFiltrarMaterias;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnGuardarMateria;
    private javax.swing.JButton btnMenu;
    private javax.swing.JComboBox<String> comboCarrera;
    private javax.swing.JComboBox<String> comboCiclos;
    private javax.swing.JComboBox<String> comboMaterias;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblErrorCarrera;
    private javax.swing.JLabel lblErrorCiclos;
    private javax.swing.JLabel lblErrorHorasClase1;
    private javax.swing.JLabel lblErrorJornada;
    private javax.swing.JLabel lblErrorMaterias;
    private javax.swing.JLabel lblErrorModalidad;
    private javax.swing.JLabel lblErrorNuevaCarrera;
    private javax.swing.JLabel lblErrorTitulo;
    private javax.swing.JLabel lblIngresarCarrera;
    private javax.swing.JLabel lblIngresarMaterias;
    private javax.swing.JPanel panelCarreras;
    private javax.swing.JPanel panelCuerpo;
    private javax.swing.JPanel panelHeader;
    private javax.swing.JPanel panelMaterias;
    private javax.swing.JPanel panelSelecionMaterias;
    private javax.swing.JTable tblCarreras;
    private javax.swing.JTable tblMaterias;
    private javax.swing.JTextField txtCarrera;
    private javax.swing.JTextField txtCiclos;
    private javax.swing.JTextField txtHorasClase;
    private javax.swing.JTextField txtModalidad;
    private javax.swing.JTextField txtTitulo;
    // End of variables declaration//GEN-END:variables
}
