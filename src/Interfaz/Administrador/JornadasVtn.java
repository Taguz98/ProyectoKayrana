/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaz.Administrador;

import BaseDatos.Conexion_Consultas;
import BaseDatos.SQLJornadas;
import Clases.Validaciones;
import Clases.Detalle_Jornada;
import Clases.HorasTrabajo;
import Clases.Jornada;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author Johnny
 */
public class JornadasVtn extends javax.swing.JFrame {

    //Creamos nuestro modelo de tabla 
    DefaultTableModel modeloTabla;
    //Le damos su titulo 
    String tituloTabla[] = {"id", "Dias", "Descripcion", "Jornada", "Inicia", "Termina"};
    String datos[][] = {};

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

    //Array en donde guardaremos todos nuestros datos de jornadas  
    ArrayList<Detalle_Jornada> jornadasDetalle = new ArrayList();
    ArrayList<Detalle_Jornada> jornadasDetalleF = new ArrayList();

    //Array para guardar todas las jornadas 
    ArrayList<Jornada> jornadas = new ArrayList();

    //Cargamos todas las horas que tengamos en nuestro sistema 
    ArrayList<HorasTrabajo> horas = new ArrayList();
    //Array donde guardaremos todos los detalles jornada para agregarlos a nuestra tabla 

    public JornadasVtn() {
        initComponents();

        //Abrimos la conexion 
        Conexion_Consultas.conectar();

        //Deshabilitamos los botones  
        btnEditar.setEnabled(false);
        btnEliminar.setEnabled(false);

        //Inicializamos nuestra tabla 
        modeloTabla = new DefaultTableModel(datos, tituloTabla);

        if (!SQLJornadas.cargarJornadas().isEmpty()) {
            jornadas = SQLJornadas.cargarJornadas();
            for (int i = 0; i < jornadas.size(); i++) {
                comboJornada.addItem(jornadas.get(i).getJornada());
            }
        }

        if (!SQLJornadas.cargarHorasTrabajo().isEmpty()) {
            horas = SQLJornadas.cargarHorasTrabajo();

            for (int i = 0; i < horas.size(); i++) {
                comboHoraInicio.addItem(horas.get(i).getHora_trab());
            }
        }

        //Actualizamos nuestra tabla
        actualizarTabla();

        //Pasamos el modelo a la tabla  
        tblJornadas.setModel(modeloTabla);

        //Modificamos el tamaño de las columnas
        TableColumnModel columModel = tblJornadas.getColumnModel();

        //Ocultamos el id de nuestra tabla
        Similitudes.ocultarID(tblJornadas);
        //columModel.getColumn(0).setResizable(false);

        columModel.getColumn(1).setPreferredWidth(150);
        columModel.getColumn(2).setPreferredWidth(280);
        columModel.getColumn(3).setPreferredWidth(100);
        columModel.getColumn(4).setPreferredWidth(80);
        columModel.getColumn(5).setPreferredWidth(80);

        //Modificamos el header nuestra tabla  
        Similitudes.tituloTbls(tblJornadas);

        //Ocultamos todos los errores 
        lblErrorDescripcion.setVisible(false);
        lblErrorJornada.setVisible(false);

        txtDescripcion.setEnabled(false);

    }

    private void actualizarTabla() {
        modeloTabla.setRowCount(0); 
        
        jornadasDetalle = SQLJornadas.cargarDetalleJornada(false);
        if (!SQLJornadas.cargarDetalleJornada(true).isEmpty()) {
            jornadasDetalleF = SQLJornadas.cargarDetalleJornada(true);

            for (int i = 0; i < jornadasDetalleF.size(); i++) {

                Object[] valores = {jornadasDetalleF.get(i).getId_deta_jornada(),
                    jornadasDetalleF.get(i).getDias(),
                    jornadasDetalleF.get(i).getDescripcion_jornada(),
                    jornadas.get(jornadasDetalleF.get(i).getFk_jornada() - 1).getJornada(),
                    horas.get(jornadasDetalleF.get(i).getFk_hora1() - 1).getHora_trab(),
                    horas.get(jornadasDetalleF.get(i).getFk_hora2() - 1).getHora_trab()};

                modeloTabla.addRow(valores);

            }
        }
    }

    public boolean nuevaJornada(int filas, String dias, String descripcion, String jornada, String hora1, String hora2) {
        boolean nuevaJornada = true;
        String diasComparar;
        String jornadaComparar;
        String descripcionComparar;
        String hora1Comparar;
        String hora2Comparar;

        for (int i = 0; i < filas; i++) {
            diasComparar = modeloTabla.getValueAt(i, 1).toString();
            descripcionComparar = modeloTabla.getValueAt(i, 2).toString();
            jornadaComparar = modeloTabla.getValueAt(i, 3).toString();
            hora1Comparar = modeloTabla.getValueAt(i, 4).toString();
            hora2Comparar = modeloTabla.getValueAt(i, 5).toString();

            if (dias.equals(diasComparar) && hora1.equals(hora1Comparar)
                    && hora2.equals(hora2Comparar) && jornadaComparar.equals(jornada)
                    && descripcionComparar.equals(descripcion)) {
                nuevaJornada = false;
                break;
            }
        }

        return nuevaJornada;
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
        jScrollPane1 = new javax.swing.JScrollPane();
        tblJornadas = new JTable(){
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
        lblErrorJornada = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtDescripcion = new javax.swing.JTextField();
        lblErrorDescripcion = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        comboHoraFin = new javax.swing.JComboBox<>();
        comboJornada = new javax.swing.JComboBox<>();
        comboHoraInicio = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        lblAyudaDias = new javax.swing.JLabel();
        comboDiaFin = new javax.swing.JComboBox<>();
        comboDiaInicio = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        panelHeader = new javax.swing.JPanel();
        btnMenu = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(810, 500));
        setUndecorated(true);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelCuerpo.setBackground(new java.awt.Color(231, 235, 230));
        panelCuerpo.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane1.setBackground(new java.awt.Color(231, 235, 230));
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        tblJornadas.setBackground(new java.awt.Color(231, 235, 230));
        tblJornadas.setFont(new java.awt.Font("Trebuchet MS", 0, 14)); // NOI18N
        tblJornadas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null}
            },
            new String [] {
                "Dias", "Descripcion", "Jornada", "Hora Inicio", "Hora Fin"
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
        tblJornadas.setOpaque(false);
        tblJornadas.setSelectionBackground(new java.awt.Color(21, 89, 110));
        tblJornadas.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblJornadas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblJornadasMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblJornadas);

        panelCuerpo.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 240, 810, 220));

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
        panelCuerpo.add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 190, 110, -1));

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
        panelCuerpo.add(btnEditar, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 190, 110, -1));

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
        panelCuerpo.add(btnEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 190, 110, -1));

        jLabel2.setFont(new java.awt.Font("Trebuchet MS", 1, 20)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(21, 89, 110));
        jLabel2.setText("Jornada:");
        panelCuerpo.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 70, -1, -1));

        jLabel1.setFont(new java.awt.Font("Trebuchet MS", 1, 20)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(21, 89, 110));
        jLabel1.setText("Dia Fin:");
        panelCuerpo.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 10, 140, -1));

        lblErrorJornada.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        lblErrorJornada.setForeground(new java.awt.Color(115, 30, 16));
        lblErrorJornada.setText("Ya registro esta jornada");
        panelCuerpo.add(lblErrorJornada, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 220, 170, -1));

        jLabel3.setFont(new java.awt.Font("Trebuchet MS", 1, 20)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(21, 89, 110));
        jLabel3.setText("Descripcion:");
        panelCuerpo.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 140, -1, -1));

        txtDescripcion.setBackground(new java.awt.Color(99, 144, 158));
        txtDescripcion.setFont(new java.awt.Font("Trebuchet MS", 0, 18)); // NOI18N
        txtDescripcion.setOpaque(false);
        panelCuerpo.add(txtDescripcion, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 140, 260, -1));

        lblErrorDescripcion.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        lblErrorDescripcion.setForeground(new java.awt.Color(115, 30, 16));
        lblErrorDescripcion.setText("Solo puede ingresar letras. ");
        panelCuerpo.add(lblErrorDescripcion, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 170, 190, -1));

        jLabel4.setFont(new java.awt.Font("Trebuchet MS", 1, 20)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(21, 89, 110));
        jLabel4.setText("Hora fin:");
        panelCuerpo.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 70, -1, -1));

        comboHoraFin.setFont(new java.awt.Font("Trebuchet MS", 0, 18)); // NOI18N
        comboHoraFin.setBorder(null);
        comboHoraFin.setOpaque(false);
        panelCuerpo.add(comboHoraFin, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 100, 110, -1));

        comboJornada.setFont(new java.awt.Font("Trebuchet MS", 0, 18)); // NOI18N
        comboJornada.setBorder(null);
        comboJornada.setOpaque(false);
        panelCuerpo.add(comboJornada, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 100, 120, -1));

        comboHoraInicio.setFont(new java.awt.Font("Trebuchet MS", 0, 18)); // NOI18N
        comboHoraInicio.setBorder(null);
        comboHoraInicio.setOpaque(false);
        comboHoraInicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboHoraInicioActionPerformed(evt);
            }
        });
        panelCuerpo.add(comboHoraInicio, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 100, 110, -1));

        jLabel6.setFont(new java.awt.Font("Trebuchet MS", 1, 20)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(21, 89, 110));
        jLabel6.setText("Hora inicio:");
        panelCuerpo.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 70, -1, -1));

        lblAyudaDias.setFont(new java.awt.Font("Trebuchet MS", 0, 14)); // NOI18N
        lblAyudaDias.setForeground(new java.awt.Color(21, 89, 110));
        lblAyudaDias.setText("<html> Lunes-Viernes <br> Sabado </html>");
        panelCuerpo.add(lblAyudaDias, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 20, 100, 40));

        comboDiaFin.setFont(new java.awt.Font("Trebuchet MS", 0, 18)); // NOI18N
        comboDiaFin.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado" }));
        comboDiaFin.setBorder(null);
        comboDiaFin.setOpaque(false);
        panelCuerpo.add(comboDiaFin, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 40, 120, -1));

        comboDiaInicio.setFont(new java.awt.Font("Trebuchet MS", 0, 18)); // NOI18N
        comboDiaInicio.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado" }));
        comboDiaInicio.setBorder(null);
        comboDiaInicio.setOpaque(false);
        panelCuerpo.add(comboDiaInicio, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 40, 120, -1));

        jLabel7.setFont(new java.awt.Font("Trebuchet MS", 1, 20)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(21, 89, 110));
        jLabel7.setText("Dia Inicio:");
        panelCuerpo.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 10, 140, -1));

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Administracion/FondoLapiz3.png"))); // NOI18N
        panelCuerpo.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 810, 460));

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
        jLabel5.setText("Ingresar Jornada");
        panelHeader.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 0, 330, 40));

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Administracion/BanerKay.png"))); // NOI18N
        jLabel11.setToolTipText("");
        panelHeader.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        getContentPane().add(panelHeader, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 810, 40));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnMenuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMenuMouseClicked
        AdministracionVtn admin = new AdministracionVtn();
        admin.setVisible(true);
        this.dispose();

        //Cerramos coneccion 
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

    private void tblJornadasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblJornadasMouseClicked

        try {

            filaSelecionada = tblJornadas.getSelectedRow();

            //Activamos nuestros botones
            if (filaSelecionada >= 0) {
                btnAvilitados = Similitudes.btnsModoActivo(btnEditar, btnEliminar);
            }

        } catch (IndexOutOfBoundsException e) {
            System.out.println("Error: " + e.getMessage());
        }

    }//GEN-LAST:event_tblJornadasMouseClicked

    private void btnGuardarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarMouseClicked
        boolean guardar = true;

        int id_detalle_jornada = 0;

        if (jornadasDetalle.size() > 0) {
            id_detalle_jornada = jornadasDetalle.get(jornadasDetalle.size() - 1).getId_deta_jornada();
        }

        id_detalle_jornada++;

        String diaIni = comboDiaInicio.getSelectedItem().toString();
        String diaFin = comboDiaFin.getSelectedItem().toString();

        String dias = diaIni + "-" + diaFin;

        String jornada = comboJornada.getSelectedItem().toString();

        String horaInicio = comboHoraInicio.getSelectedItem().toString();

        String horaFin = comboHoraFin.getSelectedItem().toString();

        String descripcion = jornada+"  "+diaIni.substring(0, 2)
                +"/"+diaFin.substring(0,2)+"  "+horaInicio+"/"
                +horaFin;   
        txtDescripcion.setEnabled(true);
        txtDescripcion.setText(descripcion);
        txtDescripcion.setEnabled(false);
        

        if (!nuevaJornada(modeloTabla.getRowCount(), dias, descripcion, jornada, horaInicio, horaFin)) {
            guardar = false;
            lblErrorJornada.setVisible(true);
        }

        //Si no encontraron ningun error se guarda
        if (guardar) {
            //Guardamos todos los datos en un arreglo
            Object valores[] = {id_detalle_jornada, dias, descripcion, jornada, horaInicio, horaFin};

            if (!editando) {
                //Se lo agregamos al modelo
                modeloTabla.addRow(valores);

                Detalle_Jornada clase_jornada = new Detalle_Jornada();

                clase_jornada.setId_deta_jornada(id_detalle_jornada);
                System.out.println("Este es el id de mi jornada " + id_detalle_jornada);

                clase_jornada.setDias(dias);
                clase_jornada.setDescripcion_jornada(descripcion);
                //Guardamos el id... cogemos el numero del indice mas 1 ya que solo hay tres daots en la abse de datos    
                clase_jornada.setFk_jornada(comboJornada.getSelectedIndex() + 1);

                clase_jornada.setFk_hora1(comboHoraInicio.getSelectedIndex() + 1);
                //Cojo el index del primer combo y luego el de segundo combo, los sumo porque el segundo combo tendra 
                //Las horas que le siguen al primer combo
                clase_jornada.setFk_hora2((comboHoraInicio.getSelectedIndex() + 1) + (comboHoraFin.getSelectedIndex() + 1));

                SQLJornadas.insertarDetalleJornada(clase_jornada);

            }

            if (editando) {

                //Editamos en nuestra base de datos  
                Detalle_Jornada clase_jornada = new Detalle_Jornada();

                clase_jornada.setId_deta_jornada(Integer.parseInt(modeloTabla.getValueAt(filaEditar, 0).toString()));
                clase_jornada.setDias(dias);
                clase_jornada.setDescripcion_jornada(descripcion);
                //Guardamos el id... cogemos el numero del indice mas 1 ya que solo hay tres daots en la abse de datos    
                clase_jornada.setFk_jornada(comboJornada.getSelectedIndex() + 1);
                clase_jornada.setFk_hora1(comboHoraInicio.getSelectedIndex() + 1);

                clase_jornada.setFk_hora2(comboHoraInicio.getSelectedIndex() + 1 + comboHoraFin.getSelectedIndex() + 1);

                SQLJornadas.editarDetalleJornada(clase_jornada);

                editando = false;
            }

            txtDescripcion.setText("");

            comboDiaInicio.setSelectedIndex(0);
            comboDiaFin.setSelectedIndex(0);
            comboJornada.setSelectedIndex(0);
            comboHoraInicio.setSelectedIndex(0);
            comboHoraFin.setSelectedIndex(0);

            //Cambiamos el estado de los botones nuevamente 
            btnAvilitados = Similitudes.btnsModoInactivo(btnEditar, btnEliminar);

            //Ocultamos todos los errores
            lblErrorDescripcion.setVisible(false);
            lblErrorJornada.setVisible(false);
            
            actualizarTabla();
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

                String dias = tblJornadas.getValueAt(filaSelecionada, 1).toString();
                int posSeparador = 0;

                for (int i = 0; i < dias.length(); i++) {
                    if (dias.charAt(i) == '-') {
                        posSeparador = i;
                        break;
                    }
                }

                String dia1 = dias.substring(0, posSeparador);
                String dia2 = dias.substring(posSeparador + 1, dias.length());

                comboDiaInicio.setSelectedItem(dia1);
                comboDiaFin.setSelectedItem(dia2);

                txtDescripcion.setText(tblJornadas.getValueAt(filaSelecionada, 2).toString());

                comboJornada.setSelectedItem(tblJornadas.getValueAt(filaSelecionada, 3));
                comboHoraInicio.setSelectedItem(tblJornadas.getValueAt(filaSelecionada, 4));
                comboHoraFin.setSelectedItem(tblJornadas.getValueAt(filaSelecionada, 5));

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
            Similitudes.btnEntered(btnEditar);
        }
    }//GEN-LAST:event_btnEditarMouseEntered

    private void btnEditarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditarMouseExited
        if (btnAvilitados) {
            Similitudes.btnExited(btnEditar); 
        }
    }//GEN-LAST:event_btnEditarMouseExited

    private void btnEliminarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarMouseClicked

        try {
            //Eliminamos la fila que este selecionada
            if (filaSelecionada >= 0 && btnAvilitados) {
                SQLJornadas.eliminarDetalleJornada(Integer.parseInt(modeloTabla.getValueAt(filaSelecionada, 0).toString()));
                System.out.println("Eliminamos detalle jornada");
                modeloTabla.removeRow(filaSelecionada);
            }

            //Cambiamos los botones a inactivos
            btnAvilitados = Similitudes.btnsModoInactivo(btnEditar, btnEliminar);
            editando = false; 
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Error: " + e.getMessage());
        }

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

    private void comboHoraInicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboHoraInicioActionPerformed
        //Llenamos el combo de fin luego de las horas escogidas

        comboHoraFin.removeAllItems();

        for (int i = comboHoraInicio.getSelectedIndex() + 1; i < horas.size(); i++) {
            //comboHoraInicio.addItem(horas.get(i).getHora_trab());
            comboHoraFin.addItem(horas.get(i).getHora_trab());
        }
    }//GEN-LAST:event_comboHoraInicioActionPerformed

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
            java.util.logging.Logger.getLogger(JornadasVtn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JornadasVtn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JornadasVtn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JornadasVtn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JornadasVtn().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnMenu;
    private javax.swing.JComboBox<String> comboDiaFin;
    private javax.swing.JComboBox<String> comboDiaInicio;
    private javax.swing.JComboBox<String> comboHoraFin;
    private javax.swing.JComboBox<String> comboHoraInicio;
    private javax.swing.JComboBox<String> comboJornada;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblAyudaDias;
    private javax.swing.JLabel lblErrorDescripcion;
    private javax.swing.JLabel lblErrorJornada;
    private javax.swing.JPanel panelCuerpo;
    private javax.swing.JPanel panelHeader;
    private javax.swing.JTable tblJornadas;
    private javax.swing.JTextField txtDescripcion;
    // End of variables declaration//GEN-END:variables
}
