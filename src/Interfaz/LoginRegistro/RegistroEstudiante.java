/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaz.LoginRegistro;

import BaseDatos.Conexion_Consultas;
import Clases.Estudiante;
import Clases.Pregunta_Seguridad;
import Clases.Usuario;
import Interfaz.Kayrana.App;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JSpinner;
import javax.swing.SpinnerListModel;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

/**
 *
 * @author Johnny
 */
public class RegistroEstudiante extends javax.swing.JFrame {

    /**
     * Creates new form RegistroEstudiante
     */
    private boolean valido = true;
    private String meses[] = {"Enero", "Febrero", "Marzo", "Abril", "Mayo",
        "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre",
        "Diciembre"};

    private SpinnerNumberModel modeloDias = new SpinnerNumberModel(1, 1, 31, 1);

    private SpinnerModel modeloMeses = new SpinnerListModel(meses);

    private SpinnerNumberModel modeloAnios = new SpinnerNumberModel(1930, 1, 2002, 1);

    private static Estudiante userEstudiante;
    
    //Para mover la venta  
    int mouseX; 
    int mouseY; 

    public RegistroEstudiante() {
        initComponents();
        //Conexion_Consultas.conectar();

        //Icono de la palicacion 
               try {
            setIconImage(new ImageIcon(getClass().getResource("../img/LogoKayranaCircularV.1.png")).getImage());
        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());
        }

        ArrayList<Pregunta_Seguridad> preguntas = new ArrayList();
        preguntas = Conexion_Consultas.cargar_preguntas();

        for (int i = 0; i < preguntas.size(); i++) {
            comboPreg1.addItem(String.valueOf(preguntas.get(i).getPregunta()));
            comboPreg2.addItem(String.valueOf(preguntas.get(i).getPregunta()));
        }

        JComponent editor = new JSpinner.NumberEditor(spinnerAnio, "####");
        spinnerAnio.setEditor(editor);

        lblErrorApellidoDoc.setVisible(false);
        lblErrorCedulaDoc.setVisible(false);
        lblErrorFechaNac.setVisible(false);
        lblErrorNick.setVisible(false);
        lblErrorNombreDoc.setVisible(false);
        lblErrorRescribirContra.setVisible(false);
        lblErrorResp1.setVisible(false);
        lblErrorColegioEst.setVisible(false);
        lclErrorCorreo.setVisible(false); // cambiar c por b

        //txtCedula.setText(Registro.cedula);
    }

    public void cedulaEstudiante(String cedula) {
        System.out.println("Cedula de estudiante "+cedula);
        txtCedula.setText(cedula);
    }

    String nickName = "SN";

    public Usuario registrar() throws ParseException {

        valido = true;

        char sexo;
        if (String.valueOf(comboSexo.getSelectedItem()).equals("Masculino")) {
            sexo = 'M';
        } else {
            sexo = 'F';
        }

        Object dia = spinnerDia.getModel().getValue();

        int mes = 0;
        while (!(meses[mes].equals(spinnerMes.getModel().getValue()))) {
            mes++;
        }

        Object anio = spinnerAnio.getModel().getValue();

        SimpleDateFormat formatoSimple = new SimpleDateFormat("yyyy-MM-dd");
        String fechaEnCadena = anio + "-" + (mes + 1) + "-" + dia;

        Date fechaNacimiento = formatoSimple.parse(fechaEnCadena);

        String colegio = txtColegio.getText();

        if (!colegio.matches("[A-Za-z0-9\\s]+")) {
            lblErrorColegioEst.setVisible(true);
            valido = false;
        }

        String nick = txtNickUsuario.getText();

        if (!nick.matches("[A-Za-z\\s]+")) {
            lblErrorNick.setVisible(true);
            valido = false;
        } else {
            nickName = nick;
        }

        String cedula = txtCedula.getText();

        if (!cedula.matches("[0-9]{10}")) {
            lblErrorCedulaDoc.setVisible(true);
            valido = false;
        }

        String nombre = txtNombre.getText();

        if (!nombre.matches("[A-Za-z\\s]+")) {
            lblErrorNombreDoc.setVisible(true);
            valido = false;
        }

        String apellido = txtApellido.getText();

        if (!apellido.matches("[A-Za-z\\s]+")) {
            lblErrorApellidoDoc.setVisible(true);
            valido = false;
        }

        String correo = txtCorreo.getText();

        if (!correo.matches("[A-Za-z0-9]+@[a-z]+\\.+[a-z]+$")) {
            lclErrorCorreo.setVisible(true);
            valido = false;
        }

        String contrasena = txtContrasena.getText();
        String contrasena_ = txtReingresoContraDoc.getText();

        if (!contrasena.matches("[A-Za-z0-9]+")) {
            lblErrorRescribirContra.setVisible(true);
            valido = false;
        }

        if (!contrasena.equals(contrasena_)) {
            lblErrorRescribirContra.setVisible(true);
            valido = false;
        }

        String respuesta1 = txtRespPreg1.getText();
        String respuesta2 = txtRespPreg2.getText();

        Estudiante nuevoEstudiante = new Estudiante(colegio, cedula, nick, contrasena, correo, sexo,
                respuesta1, respuesta2, fechaNacimiento, nombre, apellido, 2, comboPreg1.getSelectedIndex() + 1,
                comboPreg1.getSelectedIndex() + 1, true);

        if (valido) {
            this.userEstudiante = nuevoEstudiante;
        }

        return nuevoEstudiante;

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblLapiz = new javax.swing.JLabel();
        panelDocente = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtApellido = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txtColegio = new javax.swing.JTextField();
        btnRegistrarEst = new javax.swing.JButton();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel15 = new javax.swing.JLabel();
        txtContrasena = new javax.swing.JTextField();
        txtReingresoContraDoc = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        lblErrorNombreDoc = new javax.swing.JLabel();
        lblErrorColegioEst = new javax.swing.JLabel();
        txtConsejoDoc = new javax.swing.JTextArea();
        lblErrorApellidoDoc = new javax.swing.JLabel();
        lblErrorRescribirContra = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txtCedula = new javax.swing.JTextField();
        lblErrorCedulaDoc = new javax.swing.JLabel();
        panelUsuario = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtNickUsuario = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtCorreo = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtRespPreg2 = new javax.swing.JTextField();
        comboPreg2 = new javax.swing.JComboBox<>();
        comboSexo = new javax.swing.JComboBox<>();
        comboPreg1 = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtRespPreg1 = new javax.swing.JTextField();
        lblErrorFechaNac = new javax.swing.JLabel();
        lblErrorResp1 = new javax.swing.JLabel();
        lblErrorNick = new javax.swing.JLabel();
        lclErrorCorreo = new javax.swing.JLabel();
        spinnerDia = new JSpinner(modeloDias);
        spinnerMes = new JSpinner(modeloMeses);
        spinnerAnio = new JSpinner(modeloAnios);
        jPanel1 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        lblDocente = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        btnCancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(900, 550));
        setUndecorated(true);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblLapiz.setBackground(new java.awt.Color(231, 235, 230));
        lblLapiz.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblLapiz.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Registro/LapizCortado.png"))); // NOI18N
        lblLapiz.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblLapiz.setOpaque(true);
        getContentPane().add(lblLapiz, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 50, 60, 500));

        panelDocente.setBackground(new java.awt.Color(231, 235, 230));
        panelDocente.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel11.setFont(new java.awt.Font("Trebuchet MS", 1, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(20, 110, 172));
        jLabel11.setText("Nombre:");
        panelDocente.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 90, -1, -1));

        txtNombre.setFont(new java.awt.Font("Trebuchet MS", 0, 18)); // NOI18N
        txtNombre.setOpaque(false);
        panelDocente.add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 90, 230, -1));

        jLabel12.setFont(new java.awt.Font("Trebuchet MS", 1, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(20, 110, 172));
        jLabel12.setText("Apellido:");
        panelDocente.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 150, -1, -1));

        txtApellido.setFont(new java.awt.Font("Trebuchet MS", 0, 18)); // NOI18N
        txtApellido.setOpaque(false);
        panelDocente.add(txtApellido, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 150, 230, -1));

        jLabel14.setFont(new java.awt.Font("Trebuchet MS", 1, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(20, 110, 172));
        jLabel14.setText("Colegio:");
        panelDocente.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 210, -1, -1));

        txtColegio.setFont(new java.awt.Font("Trebuchet MS", 0, 18)); // NOI18N
        txtColegio.setOpaque(false);
        panelDocente.add(txtColegio, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 210, 230, -1));

        btnRegistrarEst.setFont(new java.awt.Font("Trebuchet MS", 1, 18)); // NOI18N
        btnRegistrarEst.setForeground(new java.awt.Color(255, 160, 0));
        btnRegistrarEst.setText("R E G I S T R A R ");
        btnRegistrarEst.setBorder(null);
        btnRegistrarEst.setBorderPainted(false);
        btnRegistrarEst.setContentAreaFilled(false);
        btnRegistrarEst.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnRegistrarEst.setFocusPainted(false);
        btnRegistrarEst.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnRegistrarEstMouseClicked(evt);
            }
        });
        btnRegistrarEst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarEstActionPerformed(evt);
            }
        });
        panelDocente.add(btnRegistrarEst, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 460, -1, 30));
        panelDocente.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 490, 150, 10));

        jLabel15.setFont(new java.awt.Font("Trebuchet MS", 1, 18)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(20, 110, 172));
        jLabel15.setText("Contraseña:");
        panelDocente.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 270, -1, -1));

        txtContrasena.setFont(new java.awt.Font("Trebuchet MS", 0, 18)); // NOI18N
        txtContrasena.setOpaque(false);
        panelDocente.add(txtContrasena, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 300, 230, -1));

        txtReingresoContraDoc.setFont(new java.awt.Font("Trebuchet MS", 0, 18)); // NOI18N
        txtReingresoContraDoc.setOpaque(false);
        panelDocente.add(txtReingresoContraDoc, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 400, 230, -1));

        jLabel16.setFont(new java.awt.Font("Trebuchet MS", 1, 18)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(20, 110, 172));
        jLabel16.setText("Vuelva a ingresar su contraseña:");
        panelDocente.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 370, -1, -1));

        lblErrorNombreDoc.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        lblErrorNombreDoc.setForeground(new java.awt.Color(173, 46, 25));
        lblErrorNombreDoc.setText("Solo debe ingresar letras.");
        panelDocente.add(lblErrorNombreDoc, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 120, -1, -1));

        lblErrorColegioEst.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        lblErrorColegioEst.setForeground(new java.awt.Color(173, 46, 25));
        lblErrorColegioEst.setText("Solo debe ingresar letras.");
        panelDocente.add(lblErrorColegioEst, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 240, -1, -1));

        txtConsejoDoc.setColumns(2);
        txtConsejoDoc.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        txtConsejoDoc.setForeground(new java.awt.Color(20, 110, 172));
        txtConsejoDoc.setRows(5);
        txtConsejoDoc.setText("La contraseña deberia tener una letra \nmayuscula y por lo menos un numero\n");
        txtConsejoDoc.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        txtConsejoDoc.setFocusable(false);
        txtConsejoDoc.setOpaque(false);
        panelDocente.add(txtConsejoDoc, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 330, 250, 40));

        lblErrorApellidoDoc.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        lblErrorApellidoDoc.setForeground(new java.awt.Color(173, 46, 25));
        lblErrorApellidoDoc.setText("Solo debe ingresar letras.");
        panelDocente.add(lblErrorApellidoDoc, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 180, -1, -1));

        lblErrorRescribirContra.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        lblErrorRescribirContra.setForeground(new java.awt.Color(173, 46, 25));
        lblErrorRescribirContra.setText("Sus contraseñas no coinciden.");
        panelDocente.add(lblErrorRescribirContra, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 430, -1, -1));

        jLabel13.setFont(new java.awt.Font("Trebuchet MS", 1, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(20, 110, 172));
        jLabel13.setText("Cedula:");
        panelDocente.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 30, -1, -1));

        txtCedula.setEditable(false);
        txtCedula.setFont(new java.awt.Font("Trebuchet MS", 0, 18)); // NOI18N
        txtCedula.setEnabled(false);
        txtCedula.setOpaque(false);
        panelDocente.add(txtCedula, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 30, 230, -1));

        lblErrorCedulaDoc.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        lblErrorCedulaDoc.setForeground(new java.awt.Color(173, 46, 25));
        lblErrorCedulaDoc.setText("Solo debe ingresar 10 numeros.");
        panelDocente.add(lblErrorCedulaDoc, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 60, -1, -1));

        getContentPane().add(panelDocente, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 50, 420, 500));

        panelUsuario.setBackground(new java.awt.Color(231, 235, 230));
        panelUsuario.setPreferredSize(new java.awt.Dimension(425, 550));
        panelUsuario.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setFont(new java.awt.Font("Trebuchet MS", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(20, 110, 172));
        jLabel4.setText("Nick name:");
        panelUsuario.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 30, -1, -1));

        txtNickUsuario.setFont(new java.awt.Font("Trebuchet MS", 0, 18)); // NOI18N
        txtNickUsuario.setToolTipText("Puede ingresar letras y numeros, pero no caracteres especiales.");
        txtNickUsuario.setOpaque(false);
        panelUsuario.add(txtNickUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 30, 230, -1));

        jLabel6.setFont(new java.awt.Font("Trebuchet MS", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(20, 110, 172));
        jLabel6.setText("Correo:");
        panelUsuario.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 90, -1, -1));

        txtCorreo.setFont(new java.awt.Font("Trebuchet MS", 0, 18)); // NOI18N
        txtCorreo.setToolTipText("Solo puede ingresar un correo, gmail o hotmail.");
        txtCorreo.setOpaque(false);
        panelUsuario.add(txtCorreo, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 90, 230, -1));

        jLabel7.setFont(new java.awt.Font("Trebuchet MS", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(20, 110, 172));
        jLabel7.setText("Fecha Nacimiento:");
        panelUsuario.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 150, -1, -1));

        txtRespPreg2.setFont(new java.awt.Font("Trebuchet MS", 0, 18)); // NOI18N
        txtRespPreg2.setOpaque(false);
        panelUsuario.add(txtRespPreg2, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 460, 300, -1));

        comboPreg2.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N
        comboPreg2.setBorder(null);
        comboPreg2.setOpaque(false);
        panelUsuario.add(comboPreg2, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 420, 300, 30));

        comboSexo.setBackground(new java.awt.Color(91, 53, 67));
        comboSexo.setFont(new java.awt.Font("Trebuchet MS", 0, 18)); // NOI18N
        comboSexo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Masculino", "Femenino" }));
        comboSexo.setBorder(null);
        panelUsuario.add(comboSexo, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 250, 130, -1));

        comboPreg1.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N
        comboPreg1.setBorder(null);
        comboPreg1.setOpaque(false);
        comboPreg1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboPreg1ActionPerformed(evt);
            }
        });
        panelUsuario.add(comboPreg1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 320, 300, 30));

        jLabel9.setFont(new java.awt.Font("Trebuchet MS", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(20, 110, 172));
        jLabel9.setText("Sexo:");
        panelUsuario.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 250, -1, -1));

        jLabel10.setFont(new java.awt.Font("Trebuchet MS", 1, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(20, 110, 172));
        jLabel10.setText("Responda las siguentes preguntas");
        panelUsuario.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 290, -1, -1));

        txtRespPreg1.setFont(new java.awt.Font("Trebuchet MS", 0, 18)); // NOI18N
        txtRespPreg1.setOpaque(false);
        panelUsuario.add(txtRespPreg1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 360, 300, -1));

        lblErrorFechaNac.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        lblErrorFechaNac.setForeground(new java.awt.Color(173, 46, 25));
        lblErrorFechaNac.setText("Ingrese una fecha correcta.");
        panelUsuario.add(lblErrorFechaNac, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 220, -1, -1));

        lblErrorResp1.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        lblErrorResp1.setForeground(new java.awt.Color(173, 46, 25));
        lblErrorResp1.setText("Solo debe ingresar letras.");
        panelUsuario.add(lblErrorResp1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 390, -1, -1));

        lblErrorNick.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        lblErrorNick.setForeground(new java.awt.Color(173, 46, 25));
        lblErrorNick.setText("Solomente ingrese numero y letras.");
        panelUsuario.add(lblErrorNick, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 60, -1, -1));

        lclErrorCorreo.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        lclErrorCorreo.setForeground(new java.awt.Color(173, 46, 25));
        lclErrorCorreo.setText("ejemplo@gmail.com");
        panelUsuario.add(lclErrorCorreo, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 120, -1, -1));

        spinnerDia.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        panelUsuario.add(spinnerDia, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 180, 50, 30));

        spinnerMes.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        panelUsuario.add(spinnerMes, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 180, 100, 30));

        spinnerAnio.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        panelUsuario.add(spinnerAnio, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 180, 70, 30));

        getContentPane().add(panelUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, -1, 500));

        jPanel1.setBackground(new java.awt.Color(99, 46, 52));
        jPanel1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jPanel1MouseDragged(evt);
            }
        });
        jPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jPanel1MousePressed(evt);
            }
        });
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setFont(new java.awt.Font("Trebuchet MS", 1, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(163, 162, 157));
        jLabel5.setText("R E G I S T R O");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 10, 170, 30));

        lblDocente.setFont(new java.awt.Font("Trebuchet MS", 1, 24)); // NOI18N
        lblDocente.setForeground(new java.awt.Color(163, 162, 157));
        lblDocente.setText("E S T U D I A N T E");
        jPanel1.add(lblDocente, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 10, -1, 30));
        jPanel1.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 40, 180, 10));
        jPanel1.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 40, 220, 10));

        btnCancelar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnCancelar.setForeground(new java.awt.Color(255, 255, 255));
        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Registro/icons8_Cancel_26px.png"))); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.setBorder(null);
        btnCancelar.setBorderPainted(false);
        btnCancelar.setContentAreaFilled(false);
        btnCancelar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCancelar.setFocusPainted(false);
        btnCancelar.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btnCancelar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Registro/icons8_Cancel_26px_1.png"))); // NOI18N
        btnCancelar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCancelarMouseClicked(evt);
            }
        });
        jPanel1.add(btnCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 0, 100, 50));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 900, 50));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnRegistrarEstMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRegistrarEstMouseClicked

        if (valido) {
            App app = new App();

            app.setUserEstudiante(userEstudiante);
            app.bienvenida(nickName);
            app.setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_btnRegistrarEstMouseClicked

    private void btnCancelarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelarMouseClicked
        
        Conexion_Consultas.cerrarConexion();
        
        Login login = new Login();
        login.setVisible(true);
        this.dispose();

    }//GEN-LAST:event_btnCancelarMouseClicked

    private void comboPreg1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboPreg1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboPreg1ActionPerformed

    private void btnRegistrarEstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarEstActionPerformed
        // TODO add your handling code here:
        try {
            Usuario nuevoUsuario = registrar();

            if (valido) {
                Conexion_Consultas.insertar_usuario(nuevoUsuario);
            }

        } catch (ParseException ex) {
            Logger.getLogger(Registro.class.getName()).log(Level.SEVERE, null, ex);
        }


    }//GEN-LAST:event_btnRegistrarEstActionPerformed

    private void jPanel1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MousePressed
        mouseX = evt.getX(); 
        mouseY = evt.getY(); 
    }//GEN-LAST:event_jPanel1MousePressed

    private void jPanel1MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseDragged
        int x = evt.getXOnScreen(); 
        int y = evt.getYOnScreen(); 
        
        this.setLocation(x - mouseX, y - mouseY); 
    }//GEN-LAST:event_jPanel1MouseDragged

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
            java.util.logging.Logger.getLogger(RegistroEstudiante.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RegistroEstudiante.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RegistroEstudiante.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RegistroEstudiante.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RegistroEstudiante().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnRegistrarEst;
    private javax.swing.JComboBox<String> comboPreg1;
    private javax.swing.JComboBox<String> comboPreg2;
    private javax.swing.JComboBox<String> comboSexo;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JLabel lblDocente;
    private javax.swing.JLabel lblErrorApellidoDoc;
    private javax.swing.JLabel lblErrorCedulaDoc;
    private javax.swing.JLabel lblErrorColegioEst;
    private javax.swing.JLabel lblErrorFechaNac;
    private javax.swing.JLabel lblErrorNick;
    private javax.swing.JLabel lblErrorNombreDoc;
    private javax.swing.JLabel lblErrorRescribirContra;
    private javax.swing.JLabel lblErrorResp1;
    private javax.swing.JLabel lblLapiz;
    private javax.swing.JLabel lclErrorCorreo;
    private javax.swing.JPanel panelDocente;
    private javax.swing.JPanel panelUsuario;
    private javax.swing.JSpinner spinnerAnio;
    private javax.swing.JSpinner spinnerDia;
    private javax.swing.JSpinner spinnerMes;
    private javax.swing.JTextField txtApellido;
    private javax.swing.JTextField txtCedula;
    private javax.swing.JTextField txtColegio;
    private javax.swing.JTextArea txtConsejoDoc;
    private javax.swing.JTextField txtContrasena;
    private javax.swing.JTextField txtCorreo;
    private javax.swing.JTextField txtNickUsuario;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtReingresoContraDoc;
    private javax.swing.JTextField txtRespPreg1;
    private javax.swing.JTextField txtRespPreg2;
    // End of variables declaration//GEN-END:variables
}
