/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaz.Administrador;

import BaseDatos.Conexion_Consultas;
import BaseDatos.SQLCarreras;
import BaseDatos.SQLJornadas;
import BaseDatos.SQLParalelos;
import Clases.Carrera;
import Clases.Detalle_Jornada;
import Clases.HorasTrabajo;
import Clases.Paralelo;
import Clases.ParaleloJornada;
import Clases.Validaciones;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author Johnny
 */
public class ParalelosVtn extends javax.swing.JFrame {

    //Creamos nuestro modelo de tabla 
    DefaultTableModel modeloTabla;
    //Le damos su titulo 
    String tituloTabla[] = {"id", "Carrera", "Ciclo", "Paralelo"};
    String datos[][] = {};

    //Modelo de la tabla apra jornadas de un curso  
    DefaultTableModel modeloTblJornada;
    String tituloJornada[] = {"id", "Paralelo", "Jornada"};
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

    //Arraycon las carreras  
    ArrayList<Carrera> carreras = new ArrayList();

    //Array para jornadas 
    ArrayList<Detalle_Jornada> jornadas = new ArrayList();
    ArrayList<Detalle_Jornada> jornadasF = new ArrayList();

    //Guardamos los paralelos que ya tenga en mi abse de datos 
    ArrayList<Paralelo> paralelosDB = new ArrayList();

    //Creamos  nuestro array paralos apralelos jornada  
    ArrayList<ParaleloJornada> parlJornadas = new ArrayList();

    public ParalelosVtn() {
        initComponents();

        //Nos conectamos a las base de datos 
        Conexion_Consultas.conectar();

        //Deshabilitamos los botones  
        btnEditar.setEnabled(false);
        btnEliminar.setEnabled(false);

        btnEditarJornada.setEnabled(false);
        btnEliminarJornada.setEnabled(false);

        //Cargamos todas las carreras que no esten eliminadas  
        if (!SQLCarreras.cargarCarrerasDB(true).isEmpty()) {
            carreras = SQLCarreras.cargarCarrerasDB(true);
            for (int i = 0; i < carreras.size(); i++) {
                //Tambien de paso cargamos nuestro combo  
                comboCarrera.addItem(carreras.get(i).getNombre_Carrera());
            }
        }

        if (!SQLJornadas.cargarDetalleJornada(false).isEmpty()) {
            jornadas = SQLJornadas.cargarDetalleJornada(false);
        }

        actualizarComboJornadas();

        //Inicializamos nuestra tabla 
        modeloTabla = new DefaultTableModel(datos, tituloTabla);

        modeloTblJornada = new DefaultTableModel(datosJornada, tituloJornada);

        actualizarTblParalelos();
        //Pasamos el modelo a la tabla 
        tblParalelos.setModel(modeloTabla);
        
        actualizarTblJornadas(); 
        tblJornada.setModel(modeloTblJornada);

        //Modificamos el tamaño de las columnas
        TableColumnModel columModel = tblParalelos.getColumnModel();

        Similitudes.ocultarID(tblParalelos);

        columModel.getColumn(1).setPreferredWidth(280);
        columModel.getColumn(2).setPreferredWidth(60);
        columModel.getColumn(3).setPreferredWidth(60);

        TableColumnModel columnaJornada = tblJornada.getColumnModel();

        Similitudes.ocultarID(tblJornada);

        columnaJornada.getColumn(1).setPreferredWidth(100);
        columnaJornada.getColumn(2).setPreferredWidth(250);

        Similitudes.tituloTbls(tblParalelos);
        Similitudes.tituloTbls(tblJornada);
        //Ocultamos todos los errores 
        lblErrorParalelo.setVisible(false);

        lblErrorJornada.setVisible(false);
    }

    private void actualizarTblParalelos() {
        modeloTabla.setRowCount(0);
        
        comboParalelo.removeAllItems();
        //Cargamos todos los paralelos que tenga en mi base de datos 
        if (!SQLParalelos.cargarParalelos().isEmpty()) {
            paralelosDB = SQLParalelos.cargarParalelos();

            for (int i = 0; i < SQLParalelos.cargarParalelos().size(); i++) {
                if (!paralelosDB.get(i).isParalelo_elim()) {
                    Object[] valores = {paralelosDB.get(i).getId_paralelo(),
                        //Aqui esta la magia de buscar el nombre de la carrera pasandole la id de ella 
                        nomCarrera(paralelosDB.get(i).getFk_carrera()),
                        paralelosDB.get(i).getCiclo_paralelo(),
                        paralelosDB.get(i).getNombre_paralelo()
                    };
                    
                    
                    comboParalelo.addItem(paralelosDB.get(i).getNombre_paralelo());
                    modeloTabla.addRow(valores);
                }
            }
        }
    }

    public void actualizarTblJornadas() {
        //Cagramos nuestrso datos de la tabla paralelo_jornada  
        modeloTblJornada.setRowCount(0); 
        
        parlJornadas = SQLParalelos.cargarJornadaParalelos(true);
        for (int i = 0; i < parlJornadas.size(); i++) {
            if (!parlJornadas.get(i).isParl_jord_elim()) {
                Object[] valores = {parlJornadas.get(i).getId_parl_jord(),
                    nomParalelo(parlJornadas.get(i).getFk_paralelo()),
                    nomDetJornada(parlJornadas.get(i).getFk_deta_jord())
                };

                modeloTblJornada.addRow(valores);
            }
        }

    }

    private void actualizarComboJornadas() {
        comboJornada.removeAllItems();
        if (!SQLJornadas.cargarDetalleJornada(true).isEmpty()) {
            jornadasF = SQLJornadas.cargarDetalleJornada(true);
        }

        if (!jornadasF.isEmpty()) {
            for (int i = 0; i < jornadasF.size(); i++) {
                //Tambien cargamos nuestra combo de jornadas  
                comboJornada.addItem(jornadasF.get(i).getDescripcion_jornada());
            }
        }
    }

    //Funcion para buscar la posicion de carrera  
    public int posCarrera(String carreraSelec) {
        int pos = 0;

        for (int i = 0; i < carreras.size(); i++) {
            if (carreras.get(i).getNombre_Carrera().equals(carreraSelec)) {
                pos = i;
                break;
            }
        }

        return pos;
    }

    //Funcion para buscar la posicion de carrera  
    public int idCarrera(String carreraSelec) {
        int id = 0;

        for (int i = 0; i < carreras.size(); i++) {
            if (carreras.get(i).getNombre_Carrera().equals(carreraSelec)) {
                id = carreras.get(i).getId_carrera();
                break;
            }
        }

        return id;
    }

    //Buscamos el nombre de una carrera 
    public String nomCarrera(int id) {
        String nombre = "SN";

        for (int i = 0; i < carreras.size(); i++) {
            if (carreras.get(i).getId_carrera() == id) {
                nombre = carreras.get(i).getNombre_Carrera();
                break;
            }
        }
        return nombre;
    }

    //Funcion para buscar la posicion de carrera  
    public int idParalelo(String parleloSelec) {
        int id = 0;

        for (int i = 0; i < paralelosDB.size(); i++) {
            if (paralelosDB.get(i).getNombre_paralelo().equals(parleloSelec)) {
                id = paralelosDB.get(i).getId_paralelo();
                break;
            }
        }

        return id;
    }

    //Con esto buscamos el nombre de un paralelo pasando la id 
    public String nomParalelo(int id) {
        String nombre = "SN";
        for (int i = 0; i < paralelosDB.size(); i++) {
            if (paralelosDB.get(i).getId_paralelo() == id) {
                nombre = paralelosDB.get(i).getNombre_paralelo();
                break;
            }
        }
        return nombre;
    }

    //Buscamos el nombre de un detalle jornada pasandole una id 
    public String nomDetJornada(int id) {
        String nombre = "SN";
        for (int i = 0; i < jornadas.size(); i++) {
            if (jornadas.get(i).getId_deta_jornada() == id) {
                nombre = jornadas.get(i).getDescripcion_jornada();
                break;
            }
        }
        return nombre;
    }

    //Funicon para saber si no guardamos ya esto  
    public boolean agregarJordParl(int filas, String paralelo, String jornada, DefaultTableModel modelo) {
        boolean nuevaJordParl = true;
        String paraleloComparar;
        String jornadaComparar;

        try {
            for (int i = 0; i < filas; i++) {
                paraleloComparar = modelo.getValueAt(i, 0).toString();
                jornadaComparar = modelo.getValueAt(i, 1).toString();
                if (paralelo.equals(paraleloComparar) && jornada.equals(jornadaComparar)) {
                    nuevaJordParl = false;
                    break;
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
        }

        return nuevaJordParl;
    }

    public boolean nuevoParalelo(int filas, String nombre) {
        boolean nuevoParalelo = true;
        String nombreComparar;
        try {
            for (int i = 0; i < filas; i++) {
                nombreComparar = modeloTabla.getValueAt(i, 3).toString();
                if (nombre.equals(nombreComparar)) {
                    System.out.println("Entramos ");
                    nuevoParalelo = false;
                    break;
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
        }

        return nuevoParalelo;
    }

    public void clickEliminar(DefaultTableModel modeloTablas, JButton btnEditar, JButton btnEliminar) {
        try {
            //Eliminamos la fila que este selecionada
            if (filaSelecionada >= 0 && btnAvilitados) {

                modeloTablas.removeRow(filaSelecionada);

                btnAvilitados = Similitudes.btnsModoInactivo(btnEditar, btnEliminar);
                editando = false;
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    //Para selecionar la posicion de la tabla en la donde dimos click 
    public void clickTabla(JTable tbl, JButton btnEditar, JButton btnEliminar) {
        try {

            filaSelecionada = tbl.getSelectedRow();

            //Activamos nuestros botones
            if (filaSelecionada >= 0) {
                btnAvilitados = Similitudes.btnsModoActivo(btnEditar, btnEliminar);
                //Modificamos los botones, para que pueda ser usados
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
        panelParalelos = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblParalelos = new JTable(){
            @Override 
            public boolean isCellEditable(int rowIndex, int colIndex){
                return false; 
            }
        };
        btnGuardar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        lblErrorParalelo = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        comboCarrera = new javax.swing.JComboBox<>();
        comboCurso = new javax.swing.JComboBox<>();
        comboCiclo = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        panelJornadaParalelo = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        lblErrorJornada = new javax.swing.JLabel();
        comboParalelo = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        comboJornada = new javax.swing.JComboBox<>();
        btnGuardarJornada = new javax.swing.JButton();
        btnEditarJornada = new javax.swing.JButton();
        btnEliminarJornada = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblJornada = new JTable(){
            @Override 
            public boolean isCellEditable(int rowIndex, int colIndex){
                return false; 
            }
        };
        jLabel4 = new javax.swing.JLabel();
        panelHeader = new javax.swing.JPanel();
        btnMenu = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        btnJornadaParalelos = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(810, 500));
        setUndecorated(true);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelCuerpo.setBackground(new java.awt.Color(231, 235, 230));
        panelCuerpo.setLayout(new java.awt.CardLayout());

        panelParalelos.setBackground(new java.awt.Color(231, 235, 230));
        panelParalelos.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane1.setBackground(new java.awt.Color(231, 235, 230));
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        tblParalelos.setBackground(new java.awt.Color(231, 235, 230));
        tblParalelos.setFont(new java.awt.Font("Trebuchet MS", 0, 14)); // NOI18N
        tblParalelos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null}
            },
            new String [] {
                "Curso", "Ciclo", "Carrera"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblParalelos.setOpaque(false);
        tblParalelos.setSelectionBackground(new java.awt.Color(21, 89, 110));
        tblParalelos.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblParalelos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblParalelosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblParalelos);

        panelParalelos.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 140, 410, 320));

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
        panelParalelos.add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 90, 110, -1));

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
        panelParalelos.add(btnEditar, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 90, 110, -1));

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
        panelParalelos.add(btnEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 90, 110, -1));

        jLabel2.setFont(new java.awt.Font("Trebuchet MS", 1, 20)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(21, 89, 110));
        jLabel2.setText("Carrera:");
        panelParalelos.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 10, -1, -1));

        jLabel1.setFont(new java.awt.Font("Trebuchet MS", 1, 20)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(21, 89, 110));
        jLabel1.setText("Ciclo:");
        panelParalelos.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 50, 60, -1));

        lblErrorParalelo.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        lblErrorParalelo.setForeground(new java.awt.Color(115, 30, 16));
        lblErrorParalelo.setText("Ya registros este paralelo.");
        panelParalelos.add(lblErrorParalelo, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 120, 170, 20));

        jLabel3.setFont(new java.awt.Font("Trebuchet MS", 1, 20)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(21, 89, 110));
        jLabel3.setText("Curso:");
        panelParalelos.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 50, -1, -1));

        comboCarrera.setFont(new java.awt.Font("Trebuchet MS", 0, 18)); // NOI18N
        comboCarrera.setBorder(null);
        comboCarrera.setOpaque(false);
        comboCarrera.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboCarreraActionPerformed(evt);
            }
        });
        panelParalelos.add(comboCarrera, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 10, 280, -1));

        comboCurso.setFont(new java.awt.Font("Trebuchet MS", 0, 18)); // NOI18N
        comboCurso.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "A", "B", "C", "D", "E", "F" }));
        comboCurso.setBorder(null);
        comboCurso.setOpaque(false);
        panelParalelos.add(comboCurso, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 50, 50, -1));

        comboCiclo.setFont(new java.awt.Font("Trebuchet MS", 0, 18)); // NOI18N
        comboCiclo.setBorder(null);
        comboCiclo.setOpaque(false);
        panelParalelos.add(comboCiclo, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 50, 50, -1));

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Administracion/FondoLapiz3.png"))); // NOI18N
        panelParalelos.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 810, 460));

        panelCuerpo.add(panelParalelos, "card15");

        panelJornadaParalelo.setBackground(new java.awt.Color(231, 235, 230));
        panelJornadaParalelo.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel10.setFont(new java.awt.Font("Trebuchet MS", 1, 20)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(21, 89, 110));
        jLabel10.setText("Paralelo:");
        panelJornadaParalelo.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 10, -1, -1));

        lblErrorJornada.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        lblErrorJornada.setForeground(new java.awt.Color(115, 30, 16));
        lblErrorJornada.setText("Ya tiene asignado esta jornada.");
        panelJornadaParalelo.add(lblErrorJornada, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 10, 230, 30));

        comboParalelo.setFont(new java.awt.Font("Trebuchet MS", 0, 18)); // NOI18N
        comboParalelo.setBorder(null);
        comboParalelo.setOpaque(false);
        panelJornadaParalelo.add(comboParalelo, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 40, 160, -1));

        jLabel9.setFont(new java.awt.Font("Trebuchet MS", 1, 20)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(21, 89, 110));
        jLabel9.setText("Jornadas:");
        panelJornadaParalelo.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 70, -1, -1));

        comboJornada.setFont(new java.awt.Font("Trebuchet MS", 0, 18)); // NOI18N
        comboJornada.setBorder(null);
        comboJornada.setOpaque(false);
        panelJornadaParalelo.add(comboJornada, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 100, 350, -1));

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
        panelJornadaParalelo.add(btnGuardarJornada, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 140, 110, -1));

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
        panelJornadaParalelo.add(btnEditarJornada, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 140, 110, -1));

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
        panelJornadaParalelo.add(btnEliminarJornada, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 140, 110, -1));

        jScrollPane5.setBackground(new java.awt.Color(231, 235, 230));

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

        panelJornadaParalelo.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 180, 410, 280));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Administracion/FondoLapiz3.png"))); // NOI18N
        panelJornadaParalelo.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 810, 460));

        panelCuerpo.add(panelJornadaParalelo, "card3");

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

        jLabel5.setFont(new java.awt.Font("Trebuchet MS", 1, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Ingresar Paralelo");
        jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel5MouseClicked(evt);
            }
        });
        panelHeader.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 0, 220, 40));

        btnJornadaParalelos.setBackground(new java.awt.Color(9, 28, 32));
        btnJornadaParalelos.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        btnJornadaParalelos.setForeground(new java.awt.Color(204, 204, 204));
        btnJornadaParalelos.setText("<html>  <center> Jornada <br> Paralelos </center> </html>");
        btnJornadaParalelos.setBorderPainted(false);
        btnJornadaParalelos.setContentAreaFilled(false);
        btnJornadaParalelos.setFocusPainted(false);
        btnJornadaParalelos.setMargin(new java.awt.Insets(1, 2, 1, 2));
        btnJornadaParalelos.setOpaque(true);
        btnJornadaParalelos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnJornadaParalelosMouseClicked(evt);
            }
        });
        panelHeader.add(btnJornadaParalelos, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 10, 80, 30));

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Administracion/BanerKay.png"))); // NOI18N
        jLabel11.setToolTipText("");
        panelHeader.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        getContentPane().add(panelHeader, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 810, 40));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void tblParalelosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblParalelosMouseClicked
        clickTabla(tblParalelos, btnEditar, btnEliminar);
    }//GEN-LAST:event_tblParalelosMouseClicked

    private void btnGuardarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarMouseClicked
        boolean guardar = true;
        int id_paralelo = 0;
        if (paralelosDB.size() > 0) {
            id_paralelo = paralelosDB.get(paralelosDB.size() - 1).getId_paralelo();
        }

        id_paralelo++;

        String ciclo = comboCiclo.getSelectedItem().toString();

        String curso = comboCurso.getSelectedItem().toString();

        String carrera = comboCarrera.getSelectedItem().toString();

        //Con estos for le doy el nombre al paralelo en base a la carrera que escohja y sus ciclo y curso 
        String carreraAbreviasion = "";
        int cantLetras = 0;

        for (int i = 0; i < carrera.length(); i++) {
            if (i == 0) {
                carreraAbreviasion += carrera.charAt(i);
            }

            if (carrera.charAt(i) == ' ') {

                for (int j = i + 1; j < carrera.length(); j++) {
                    cantLetras++;
                    if (cantLetras > 4) {
                        carreraAbreviasion += carrera.charAt(i + 1);
                        break;
                    }

                    if (carrera.charAt(j) == ' ') {
                        break;
                    }
                }
                cantLetras = 0;
            }
        }

        String nombre_paralelo = carreraAbreviasion + "-" + ciclo + curso;
        nombre_paralelo = nombre_paralelo.toUpperCase();

        System.out.println(nombre_paralelo);

        //Verificamos si no fue ingresada ya  
        if (!nuevoParalelo(modeloTabla.getRowCount(), nombre_paralelo)) {
            guardar = false;
            lblErrorParalelo.setVisible(true);
        }

        //Si no encontraron ningun error se guarda
        if (guardar) {

            if (!editando) {
                Paralelo prl = new Paralelo();

                prl.setId_paralelo(id_paralelo);
                prl.setFk_carrera(idCarrera(carrera));
                prl.setCiclo_paralelo(Integer.parseInt(ciclo));
                prl.setNombre_paralelo(nombre_paralelo);

                SQLParalelos.insertarParalelos(prl);
            }

            if (editando) {

                Paralelo prl = new Paralelo();

                prl.setId_paralelo(Integer.parseInt(tblParalelos.getValueAt(filaEditar, 0).toString()));
                prl.setFk_carrera(idCarrera(carrera));
                prl.setCiclo_paralelo(Integer.parseInt(ciclo));
                prl.setNombre_paralelo(nombre_paralelo);

                SQLParalelos.editarParalelo(prl);

                editando = false;
            }

            comboCiclo.setSelectedIndex(0);
            comboCurso.setSelectedIndex(0);
            comboCarrera.setSelectedIndex(0);

            //Cambiamos el estado de los botones nuevamente
            btnAvilitados = Similitudes.btnsModoInactivo(btnEditar, btnEliminar);

            //Ocultamos todos los errores
            lblErrorParalelo.setVisible(false);

            actualizarTblParalelos();
        }
    }//GEN-LAST:event_btnGuardarMouseClicked

    private void btnGuardarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarMouseEntered
        btnGuardar.setBackground(new Color(21, 89, 110));
    }//GEN-LAST:event_btnGuardarMouseEntered

    private void btnGuardarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarMouseExited
        btnGuardar.setBackground(new Color(99, 144, 158));
    }//GEN-LAST:event_btnGuardarMouseExited

    private void btnEditarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditarMouseClicked

        try {
            if (filaSelecionada >= 0 && btnAvilitados) {
                filaEditar = filaSelecionada;

                String curso = tblParalelos.getValueAt(filaSelecionada, 3).toString();

                char letra = curso.charAt(curso.length() - 1);

                //System.out.println(letra);
                comboCarrera.setSelectedItem(tblParalelos.getValueAt(filaSelecionada, 1));
                comboCiclo.setSelectedItem(tblParalelos.getValueAt(filaSelecionada, 2).toString());
                comboCurso.setSelectedItem(letra + "");

                //Si selecionamos editar deshabilitamos los botones editar y eliminar
                btnAvilitados = Similitudes.btnsModoInactivo(btnEditar, btnEliminar);

                editando = true;
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }//GEN-LAST:event_btnEditarMouseClicked

    private void btnEditarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditarMouseEntered
        if (btnAvilitados) {
            btnEditar.setBackground(new Color(21, 89, 110));
        }
    }//GEN-LAST:event_btnEditarMouseEntered

    private void btnEditarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditarMouseExited
        if (btnAvilitados) {
            btnEditar.setBackground(new Color(99, 144, 158));
        }
    }//GEN-LAST:event_btnEditarMouseExited

    private void btnEliminarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarMouseClicked
        //Eliminamos de base de datos antes de eliminarlo de nuestra tabla
        SQLParalelos.eliminarParalelo(Integer.parseInt(tblParalelos.getValueAt(filaSelecionada, 0).toString()));

        clickEliminar(modeloTabla, btnEditar, btnEliminar);
    }//GEN-LAST:event_btnEliminarMouseClicked

    private void btnEliminarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarMouseEntered
        if (btnAvilitados) {
            btnEliminar.setBackground(new Color(21, 89, 110));
        }
    }//GEN-LAST:event_btnEliminarMouseEntered

    private void btnEliminarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarMouseExited
        if (btnAvilitados) {
            btnEliminar.setBackground(new Color(99, 144, 158));
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

    private void btnJornadaParalelosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnJornadaParalelosMouseClicked
        Similitudes.cambioPanel(panelCuerpo, panelJornadaParalelo);

        btnJornadaParalelos.setBackground(new Color(231, 235, 230));

        btnJornadaParalelos.setForeground(new Color(9, 28, 32));
    }//GEN-LAST:event_btnJornadaParalelosMouseClicked

    private void btnGuardarJornadaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarJornadaMouseClicked
        boolean guardar = true;
        int id_parl_jorn = 0;
        if (parlJornadas.size() > 0) {
            id_parl_jorn = parlJornadas.get(parlJornadas.size() - 1).getId_parl_jord();
        }

        id_parl_jorn++;

        String paralelo = comboParalelo.getSelectedItem().toString();
        String jornada = comboJornada.getSelectedItem().toString();

        if (!agregarJordParl(modeloTblJornada.getRowCount(), paralelo, jornada, modeloTblJornada)) {
            guardar = false;
            lblErrorJornada.setVisible(true);
        }

        if (guardar) {

            if (!editando) {

                //Guardamos en la base de datos 
                ParaleloJornada parlJord = new ParaleloJornada();
                parlJord.setId_parl_jord(id_parl_jorn);
                parlJord.setFk_paralelo(idParalelo(paralelo));
                parlJord.setFk_deta_jord(jornadas.get(comboJornada.getSelectedIndex()).getId_deta_jornada());


                SQLParalelos.insertarJornadaParalelo(parlJord);
            }

            if (editando) {
                
                //Editamos en la base de datos 
                ParaleloJornada parlJord = new ParaleloJornada();
                parlJord.setId_parl_jord(Integer.parseInt(tblJornada.getValueAt(filaEditar, 0).toString()));
                parlJord.setFk_paralelo(idParalelo(paralelo));
                parlJord.setFk_deta_jord(jornadas.get(comboJornada.getSelectedIndex()).getId_deta_jornada());

                SQLParalelos.editarJornadaParalelo(parlJord);

                editando = false;
            }

            comboParalelo.setSelectedIndex(0);
            comboJornada.setSelectedIndex(0);

            lblErrorJornada.setVisible(false);

            //Cambiamos el estado de los botones nuevamente
            btnAvilitados = Similitudes.btnsModoInactivo(btnEditarJornada, btnEliminarJornada);
            
            actualizarTblJornadas();
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

                comboParalelo.setSelectedItem(tblJornada.getValueAt(filaSelecionada, 1));
                comboJornada.setSelectedItem(tblJornada.getValueAt(filaSelecionada, 2));

                btnAvilitados = Similitudes.btnsModoInactivo(btnEditar, btnEliminar);

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
        SQLParalelos.eliminarJornadaParalelo(Integer.parseInt(tblJornada.getValueAt(filaSelecionada, 0).toString()));

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

    private void tblJornadaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblJornadaMouseClicked
        clickTabla(tblJornada, btnEditarJornada, btnEliminarJornada);
    }//GEN-LAST:event_tblJornadaMouseClicked

    private void jLabel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseClicked
        Similitudes.cambioPanel(panelCuerpo, panelParalelos);

        btnJornadaParalelos.setBackground(new Color(9, 28, 32));

        btnJornadaParalelos.setForeground(new Color(204, 204, 204));
    }//GEN-LAST:event_jLabel5MouseClicked

    private void comboCarreraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboCarreraActionPerformed
        //Borramos todos los items que tenga el combo de ciclos 

        //Aqui tenia el error fantasma  
        //El erro fantasera fue que debo tranfromar primero el string para poderlo pasar al combo ciclos
        comboCiclo.removeAllItems();

        String carreraSelec = comboCarrera.getSelectedItem().toString();
        //buscamos el numero de ciclos de una carrera y los vamos agregando a un combo box 

        for (int i = 0; i < carreras.get(posCarrera(carreraSelec)).getNum_ciclos_carrera(); i++) {
            comboCiclo.addItem(String.valueOf(i + 1));
        }
    }//GEN-LAST:event_comboCarreraActionPerformed

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
            java.util.logging.Logger.getLogger(ParalelosVtn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ParalelosVtn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ParalelosVtn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ParalelosVtn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ParalelosVtn().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEditarJornada;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnEliminarJornada;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnGuardarJornada;
    private javax.swing.JButton btnJornadaParalelos;
    private javax.swing.JButton btnMenu;
    private javax.swing.JComboBox<String> comboCarrera;
    private javax.swing.JComboBox<String> comboCiclo;
    private javax.swing.JComboBox<String> comboCurso;
    private javax.swing.JComboBox<String> comboJornada;
    private javax.swing.JComboBox<String> comboParalelo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JLabel lblErrorJornada;
    private javax.swing.JLabel lblErrorParalelo;
    private javax.swing.JPanel panelCuerpo;
    private javax.swing.JPanel panelHeader;
    private javax.swing.JPanel panelJornadaParalelo;
    private javax.swing.JPanel panelParalelos;
    private javax.swing.JTable tblJornada;
    private javax.swing.JTable tblParalelos;
    // End of variables declaration//GEN-END:variables
}
