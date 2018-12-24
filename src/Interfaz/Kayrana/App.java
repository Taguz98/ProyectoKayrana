/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaz.Kayrana;

import BaseDatos.Conexion_Consultas;
import BaseDatos.SQLAgenda;
import Clases.Apuntes;
import Clases.Docente;
import Clases.Estudiante;
import Clases.Recordatorios;
import Clases.Tareas;
import Interfaz.Administrador.Similitudes;
import java.awt.Color;
import java.awt.Frame;

//Importamos nuestro login 
import Interfaz.LoginRegistro.Login;
import ds.desktop.notify.DesktopNotify;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimerTask;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SpinnerDateModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author Johnny
 */
public class App extends javax.swing.JFrame {

    //Para mover mi pantalla a la hora de moverlo. 
    int xMause;
    int yMause;
    Timer miTimer = new Timer();
    //Creamos nuestro modelo de tabla 
    DefaultTableModel modelTblApuntes;
    //Le damos su titulo 
    String tituloApuntes[] = {"id", "Apuntes"};
    String datosApuntes[][] = {};

    //Modelo para la tabla de notas  
    DefaultTableModel modelTblTareas;

    String tituloTareas[] = {"id", "Tareas"};
    String datosTareas[][] = {};

    //Modelo para la tabla de recordatorios 
    DefaultTableModel modelTblRecord;

    String tituloRecord[] = {"id", "Tareas"};
    String datosRecord[][] = {};

    //Para saber si mis botones estan avilitados o no 
    boolean btnAvilitados = false;

    //Para saber en la posicion que selecione en mi tabla 
    private int filaSelecionada;

    //Para saber si le di click a editar 
    private boolean editando = false;

    //Usuarios 
    static Docente userDocente;
    static Estudiante userEstudiante;

    //Cargamos todas las cosas de un usuario, bueno las vamos a guardar en estos arrya  
    ArrayList<Apuntes> apuntesUsuario = new ArrayList();

    ArrayList<Tareas> tareasUsuario = new ArrayList();

    ArrayList<Recordatorios> recordatoriosUsuario = new ArrayList();
    
    //Para controlar  que no se repitan los recordatorios 
    ArrayList<Integer> controlTareas = new ArrayList(); 
    ArrayList<Integer> controlRecordatorios = new ArrayList(); 

    private static String cedula_usuario = "";

    //Las usaremos para contar la cantidad de escritos en la agenda  
    int cantApuntes = 0;
    int cantTareas = 0;
    int cantRecordatorios = 0;

    int cantApuntesDistributivo = 0;

    //Para ocultarlo en barra de tareas 
    private ImageIcon imageicon;
    private TrayIcon trayicon;
    private SystemTray systemtray;

    public final void instaciarTray() {
        trayicon = new TrayIcon(imageicon.getImage(), "Kayrana", popupMenuOculto);
        trayicon.setImageAutoSize(true);
        systemtray = SystemTray.getSystemTray();
    }

    public App() {
        initComponents();

        imageicon = new ImageIcon(this.getClass().getResource("/img/LogoKayranaCircularV.1.png"));

        //Icono de la palicacion 
        try {
            //setIconImage(new ImageIcon(getClass().getResource("/img/LogoV.2P.png")).getImage());
            this.setIconImage(imageicon.getImage());
        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());
        }

        popupAbrir.setLabel("Abrir");
        popupSalir.setLabel("Salir");

        instaciarTray();

        //Le cambiamos el estilod de escribir para un txt que salte la linea automaticamnete  
        saltoLineasTxt(txtApunteIngresar);
        saltoLineasTxt(txtIngresarTareas);
        saltoLineasTxt(txtIngresarRecordatorio);

        //Le pasamos el modelo a la tabla de apuntes  
        modelTblApuntes = new DefaultTableModel(datosApuntes, tituloApuntes);

        tblApuntes.setModel(modelTblApuntes);
        //Le pasamos el modelo a la tabla de tareas 
        modelTblTareas = new DefaultTableModel(datosTareas, tituloTareas);

        tblTareas.setModel(modelTblTareas);
        //Le pasamos el modelo a la tabla de recordatorios 
        modelTblRecord = new DefaultTableModel(datosRecord, tituloRecord);

        //System.out.println("la cedula del usuario ingresado " + cedula_usuario);
        tblRecordatorios.setModel(modelTblRecord);
        //Cambiamos el color del titulo de una tbl  
        Similitudes.tituloTbls(tblApuntes);
        Similitudes.tituloTbls(tblTareas);
        Similitudes.tituloTbls(tblRecordatorios);
        //Cambiamos el tamaño de las columnas 
        tamColumnas(tblApuntes);
        tamColumnas(tblTareas);
        tamColumnas(tblRecordatorios);

        //Ponemos los btn en inactivos  
        btnsAgendaInactivo();

        //Esto solo sera visible para el usuario tipo docente 
        lblNotasDistributivo.setVisible(false);
        lblCantidadNotasDistributivo.setVisible(false);

        notificarRecordatorios();

        //Ocultar tarea complea  
        btnTareaTerminado.setVisible(false);

    }

    private void notificarRecordatorios() {

        for (int i = 0; i < recordatoriosUsuario.size(); i++) {

            if (!recordatoriosUsuario.get(i).isRecordatorio_elim()) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd hh:mm:ss");
                String dateInString = String.valueOf(recordatoriosUsuario.get(i).getFecha_noti());
                Date date = null;

                try {
                    date = sdf.parse(dateInString);
                } catch (ParseException ex) {
                    Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
                }
                final String recordatorio = recordatoriosUsuario.get(i).getRecordatorio();

                if (!controlRecordatorios.contains(recordatoriosUsuario.get(i).getId_recordatorio())) {
                    controlRecordatorios.add(recordatoriosUsuario.get(i).getId_recordatorio()); 
                    mostrarNotificacion(recordatorio, date, "Recordatorio");
                }
                
                if (date.before(fechaHoraActual())) {
                    SQLAgenda.eliminarRecordatorio(recordatoriosUsuario.get(i).getId_recordatorio());
                }
            }
        }
    }

    public void notificarTareas() {

        System.out.println("Tamaño de mis tareas  " + tareasUsuario.size());

        for (int i = 0; i < tareasUsuario.size(); i++) {

            if (!tareasUsuario.get(i).isTarea_elim() && !tareasUsuario.get(i).isTarea_completa()) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd hh:mm:ss");
                String dateInString = String.valueOf(tareasUsuario.get(i).getFecha_presentacion());
                Date date = null;

                try {
                    date = sdf.parse(dateInString);
                } catch (ParseException ex) {
                    Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
                }
                final String tarea = tareasUsuario.get(i).getTarea();
                
                if (!controlTareas.contains(tareasUsuario.get(i).getId_tarea())) {
                    controlTareas.add(tareasUsuario.get(i).getId_tarea()); 
                    mostrarNotificacion(tarea, date, "Tarea");
                }
            }

        }
    }

    public void mostrarNotificacion(String mensaje, Date date, String tipo) {

        TimerTask miNotificacion = new TimerTask() {

            @Override
            public void run() {

                if (tipo.equals("Recordatorio")) {
                    DesktopNotify.showDesktopMessage(tipo, mensaje, DesktopNotify.TIP, 10000L);

                } else {
                    DesktopNotify.showDesktopMessage(tipo, mensaje, DesktopNotify.INFORMATION, 10000L);

                }

            }

        };

        if (tipo.equals("Recordatorio")) {
            miTimer.schedule(miNotificacion, date);
        } else if (new Date().before(date)) {
            miTimer.schedule(miNotificacion, new Date());

        }
    }

    String inicioHTML = "<html>"
            + "<style>"
            + "h4 {"
            + "color: #632E34;"
            + "padding: 0px; "
            + "margin: 5px 5px 1px 1px;"
            + "}"
            + ".titulo{"
            + "border-bottom: 1px solid #632E34;"
            + "}"
            + ".hora{"
            + "padding: 0px 0px 3px 1px;"
            + "color: #421E22;"
            + "}"
            + ".fecha{"
            + "padding: 0px 0px 3px 1px;"
            + "color: #421E22;"
            + "}"
            + "body{"
            + "padding: 0px 5px 5px 5px;"
            + "}"
            + "p{"
            + "font-size: 11px;"
            + "}"
            + "</style>"
            + "<body>";

    String finHTML = "</body>"
            + "</html>";

    public String cuerpo(Date fecha, Date hora, String escrito) {
        String cuerpoHTML = "<div class=\"titulo\">"
                + "<h4> "
                + formatoFecha(fecha) + "<div class=\"hora\">" + formatoHora(hora) + "</div>"
                + "</h4>"
                + "</div>"
                + "<p>"
                + escrito
                + "</p>";

        return cuerpoHTML;
    }

    public String cuerpoDosFechas(Date fechaEscribe, Date fechaNotifi, String escrito) {
        String cuerpoHTML = "<div class=\"titulo\">"
                + "<h4> "
                + formatoFechaHora(fechaEscribe) + "<div class=\"fecha\">" + formatoFechaHora(fechaNotifi) + "</div>"
                + "</h4>"
                + "</div>"
                + "<p>"
                + escrito
                + "</p>";

        return cuerpoHTML;
    }

    //Todo esto mostraremos al inicio de nuestra agenda  
    public void inicioApunte() {
        String apunte = inicioHTML;
        //System.out.println("Actualizaremos el inicio de apuntes de "+cedula_usuario);
        for (int i = apuntesUsuario.size() - 1; i >= 0; i--) {
            if (!apuntesUsuario.get(i).isApunte_elim()) {
                if (apunte.length() < 950) {
                    apunte = apunte + cuerpo(apuntesUsuario.get(i).getFecha_esc_apunte(),
                            apuntesUsuario.get(i).getFecha_esc_apunte(), apuntesUsuario.get(i).getApunte());
                    //System.out.println("Fecha en apunte "+apuntesUsuario.get(i).getFecha_esc_apunte());
                } else {
                    apunte = apunte + "<h4>. . .</h4>";
                    break;
                }
            }
        }

        apunte = apunte + finHTML;
        lblApuntesMostrar.setText(apunte);
    }

    public void inicioTarea() {
        String tarea = inicioHTML;
        //System.out.println("Actualizaremos el inicio de tareas de "+cedula_usuario);
        for (int i = tareasUsuario.size() - 1; i >= 0; i--) {
            if (!tareasUsuario.get(i).isTarea_elim() && !tareasUsuario.get(i).isTarea_completa()) {
                if (tarea.length() < 950) {
                    tarea = tarea + cuerpoDosFechas(tareasUsuario.get(i).getFecha_esc_tarea(),
                            tareasUsuario.get(i).getFecha_presentacion(),
                            tareasUsuario.get(i).getTarea());
                    //System.out.println("Fecha en tarea "+tareasUsuario.get(i).getFecha_esc_tarea() + " "+tareasUsuario.get(i).getFecha_presentacion());
                } else {
                    tarea = tarea + "<h4>. . .</h4>";
                    break;
                }
            }
        }

        tarea = tarea + finHTML;
        lblTareasMostrar.setText(tarea);
    }

    public void inicioRecordatorio() {
        String recordatorio = inicioHTML;
        //System.out.println("Actualizaremos el inicio de recordatorios de"+cedula_usuario);
        for (int i = recordatoriosUsuario.size() - 1; i >= 0; i--) {
            if (!recordatoriosUsuario.get(i).isRecordatorio_elim()) {
                if (recordatorio.length() < 950) {
                    recordatorio = recordatorio + cuerpoDosFechas(recordatoriosUsuario.get(i).getFecha_noti(),
                            recordatoriosUsuario.get(i).getFecha_esc_record(), recordatoriosUsuario.get(i).getRecordatorio());
                    //System.out.println("Fecha en recordatorio "+recordatoriosUsuario.get(i).getFecha_esc_record()+" "+recordatoriosUsuario.get(i).getFecha_noti());
                } else {
                    recordatorio = recordatorio + "<h4>. . .</h4>";
                    break;
                }
            }
        }

        recordatorio = recordatorio + finHTML;
        lblRecordatoriosMostrar.setText(recordatorio);
    }

    public void actualizarInicioAgenda() {
        System.out.println("Actualizaremos el inicio de " + cedula_usuario);
        inicioApunte();
        inicioRecordatorio();
        inicioTarea();
    }

    //Buscamos la posicion en el array de notas usuario 
    public int posApunte(int id) {
        int pos = 0;
        for (int i = 0; i < apuntesUsuario.size(); i++) {
            if (apuntesUsuario.get(i).getId_apunte() == id) {
                pos = i;
                break;
            }
        }
        return pos;
    }

    public void actualizarPerfil() {
        lblCantidadNotasDistributivo.setText(cantApuntesDistributivo + "");
        lblCantidadRecord.setText(cantRecordatorios + "");
        lblCantidadTareas.setText(cantTareas + "");
        lblCantidadNotas.setText(cantApuntes + "");
    }

    //Agregamos las cosas a la agenda de un usaurio: 
    public void actualizarAgenda() {
        cantApuntes = 0;
        cantTareas = 0;
        cantRecordatorios = 0;

        cantApuntesDistributivo = 0;

        actualizarTblApuntes();

        actualizarTblTareas();

        notificarTareas();

        actualizarTblRecordatorios();

        notificarRecordatorios();

    }

    public void actualizarTblApuntes() {
        modelTblApuntes.setRowCount(0);
        cantApuntes = 0;

        if (!SQLAgenda.cargarApuntesUsuario(cedula_usuario).isEmpty()) {
            apuntesUsuario = SQLAgenda.cargarApuntesUsuario(cedula_usuario);
            for (int i = 0; i < apuntesUsuario.size(); i++) {

                Object[] apt = {apuntesUsuario.get(i).getId_apunte(),
                    apuntesUsuario.get(i).getApunte()};
                modelTblApuntes.addRow(apt);
                cantApuntes++;

            }
        }
    }

    public void actualizarTblTareas() {
        modelTblTareas.setRowCount(0);
        cantTareas = 0;

        if (!SQLAgenda.cargarTareasUsuario(cedula_usuario).isEmpty()) {
            tareasUsuario = SQLAgenda.cargarTareasUsuario(cedula_usuario);
            for (int i = 0; i < tareasUsuario.size(); i++) {
                Object[] tr = {tareasUsuario.get(i).getId_tarea(),
                    tareasUsuario.get(i).getTarea()};
                modelTblTareas.addRow(tr);
                cantTareas++;
            }
        }
    }

    public void actualizarTblRecordatorios() {
        modelTblRecord.setRowCount(0);
        cantRecordatorios = 0;

        if (!SQLAgenda.cargarRecordatoriosUsuarios(cedula_usuario).isEmpty()) {
            recordatoriosUsuario = SQLAgenda.cargarRecordatoriosUsuarios(cedula_usuario);
            for (int i = 0; i < recordatoriosUsuario.size(); i++) {
                Object[] rec = {recordatoriosUsuario.get(i).getId_recordatorio(),
                    recordatoriosUsuario.get(i).getRecordatorio()};
                modelTblRecord.addRow(rec);
                cantRecordatorios++;
            }
        }
    }

    //Aqui guardamos el usuario que ingresara 
    public void setUserDocente(Docente usuarioDocente) {
        userDocente = usuarioDocente;
        //Cambiamos algunas cosas de la aplicacion
        btnHorario.setText("Distributivo");
        btnIngresarHorario.setText("Ingresar Distributivo");
        btnIngresarCurso.setText("Ingresar materias");

        lblNombreApellido.setText(userDocente.getNombreUsuario() + " " + userDocente.getApellidoUsuario());
        lblPerfilCarrera.setText(userDocente.getNickUsuario());
        lblCorreo.setText(userDocente.getCorreoUsuario());

        App.cedula_usuario = userDocente.getCedulaUsuario();

        //Activamos los campos de su perfil 
        lblNotasDistributivo.setVisible(true);
        lblCantidadNotasDistributivo.setVisible(true);
        
        bienvenida(userDocente.getNickUsuario()); 
        
        actualizarAgenda();
        actualizarPerfil();
    }

    public void setUserEstudiante(Estudiante usuarioEstudiante) {
        userEstudiante = usuarioEstudiante;

        btnHorario.setText("Horario");
        lblNombreApellido.setText(userEstudiante.getNombreUsuario() + " " + userEstudiante.getApellidoUsuario());
        lblPerfilCarrera.setText(userEstudiante.getNickUsuario());
        lblCorreo.setText(userEstudiante.getCorreoUsuario());

        System.out.println("Estoy iniciando sesion como estudiante esta es mi cedula");
        System.out.println(userEstudiante.getCedulaUsuario());

        App.cedula_usuario = userEstudiante.getCedulaUsuario();
        
        bienvenida(userEstudiante.getNickUsuario()); 

        actualizarAgenda();
        actualizarPerfil();
    }

    public Date fechaHoraActual() {
        Date fecha = new Date();
        //System.out.println(fecha);
        //System.out.println();
        //DateFormat horaFormato = new SimpleDateFormat("HH:mm:ss");
        //System.out.println(horaFormato.format(fecha));
        //DateFormat fechaFormato = new SimpleDateFormat("dd/MM/yyy");
        //System.out.println(fechaFormato.format(fecha));
        //DateFormat horaFechaForm = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        //System.out.println(horaFechaForm.format(fecha));

        return fecha;
    }

    public void limpiarAgenda() {

        btnsAgendaInactivo();
        limpiarTextosAgenda(apunteSelec, fechaApunte);
        limpiarTextosAgenda(tareaSelec, fechaTarea);
        limpiarTextosAgenda(recordatorioSelec, fechaRecordatorio);
        limpiarTextosAgenda(fechaTareaCompletar, fechaRecordatorioTermina);

        txtApunteIngresar.setText("");
        txtIngresarTareas.setText("");
        txtIngresarRecordatorio.setText("");
    }

    //Limpiar los textos en agenda  
    public void limpiarTextosAgenda(JLabel lbl1, JLabel lbl2) {
        lbl1.setText("");
        lbl2.setText("");
    }

    //Poner todos los botones en modo inactivo  
    public final void btnsAgendaInactivo() {
        Similitudes.btnsModoInactivo(btnModificarApunte, btnEliminarApunte);
        Similitudes.btnsModoInactivo(btnModificarTarea, btnEliminarTarea);
        btnTareaTerminado.setEnabled(false);
        btnTareaTerminado.setBackground(new Color(118, 125, 127));
        Similitudes.btnsModoInactivo(btnModificarRecordatorio, btnEliminarRecordatorio);
    }

    //Actualizamos la hora de nuestro spinner
    public void actHoraSpinners(JSpinner spin) {
        Date horaForm = new Date();
        SpinnerDateModel mf = new SpinnerDateModel(horaForm, null, null, Calendar.HOUR_OF_DAY);
        spin.setModel(mf);

        JSpinner.DateEditor horaSpin = new JSpinner.DateEditor(spin, "HH:mm:ss");
        spin.setEditor(horaSpin);
    }

    public final void saltoLineasTxt(JTextArea txtArea) {
        txtArea.setLineWrap(true);
        txtArea.setWrapStyleWord(true);
    }

    //Funcion para cambiar el tamano de nuestras columnas  
    public final void tamColumnas(JTable tabla) {
        TableColumnModel columna = tabla.getColumnModel();

        columna.getColumn(0).setPreferredWidth(0);
        columna.getColumn(0).setWidth(0);
        columna.getColumn(0).setMinWidth(0);
        columna.getColumn(0).setMaxWidth(0);

        tabla.setRowHeight(25);
        //columna.getColumn(1).setPreferredWidth(anchura1);
    }

    public void agendaSelecionado(JLabel lbl, String agenda) {
        String completo = "<html>"
                + "<style>"
                + "body{"
                + "padding: 0px 5px 5px 5px;"
                + "}"
                + "</style>"
                + "<body>"
                + "<p>"
                + agenda
                + "</p>"
                + "</body>"
                + "</html>";

        lbl.setText(completo);
    }

    public void fechaAgenda(JLabel lbl, String fecha, String hora) {
        String titulo = "<html>"
                + "<style>"
                + "h4 {"
                + "color: #F97E52;"
                + "padding: 0px; "
                + "margin: 5px 5px 1px 1px;"
                + "}"
                + ".titulo{"
                + "border-bottom: 1px solid #632E34;"
                + "}"
                + ".hora{"
                + "padding: 0px 0px 3px 1px;"
                + "color: #FA8C64;"
                + "font-weight: bold; "
                + "}"
                + "body{"
                + "padding: 0px 5px 5px 5px;"
                + "}"
                + "</style>"
                + "<body>"
                + "<div class=\"titulo\">"
                + "<h4> "
                + fecha + "<div class=\"hora\">" + hora + "</div>"
                + "</h4>"
                + "</body>"
                + "</html>";

        lbl.setText(titulo);
    }

    public void bienvenida(String nick) {
        lblNombreUsuario.setText(nick);
    }

    public void cambiarColores(JLabel lblSelec, JLabel lbl1, JLabel lbl2, JLabel lbl3) {

        lblSelec.setBackground(new Color(99, 46, 52));

        lbl1.setBackground(new Color(201, 211, 200));
        lbl2.setBackground(new Color(201, 211, 200));
        lbl3.setBackground(new Color(201, 211, 200));
    }

    public void colorDefecto(JLabel lbl1, JLabel lbl2) {
        lbl1.setBackground(new Color(237, 240, 236));
        lbl2.setBackground(new Color(237, 240, 236));
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

                Similitudes.btnsModoActivo(btnEditar, btnEliminar);
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

    //Buscar en agenda 
    //Buscar apunte  
    public Apuntes buscarApunte(int id) {
        Apuntes ap = null;
        for (int i = 0; i < apuntesUsuario.size(); i++) {
            if (apuntesUsuario.get(i).getId_apunte() == id) {
                ap = apuntesUsuario.get(i);
                break;
            }
        }
        return ap;
    }

    //Buscar recordatorio
    public Recordatorios buscarRecord(int id) {
        Recordatorios ret = null;
        for (int i = 0; i < recordatoriosUsuario.size(); i++) {
            if (recordatoriosUsuario.get(i).getId_recordatorio() == id) {
                ret = recordatoriosUsuario.get(i);
                break;
            }
        }
        return ret;
    }

    //Buscar tarea 
    public Tareas buscarTarea(int id) {
        Tareas tr = null;
        for (int i = 0; i < tareasUsuario.size(); i++) {
            if (tareasUsuario.get(i).getId_tarea() == id) {
                tr = tareasUsuario.get(i);
                break;
            }
        }

        return tr;
    }

    public String formatoHora(Date completo) {
        String hora = "";
        DateFormat horaFechaForm = new SimpleDateFormat("HH:mm:ss");
        try {
            hora = horaFechaForm.format(completo);
        } catch (Exception e) {
            System.out.println("Se me murio en hora");
        }

        return hora;
    }

    public String formatoFecha(Date completo) {
        String fecha = "";
        DateFormat FechaForm = new SimpleDateFormat("dd/MM/yyyy");
        try {
            fecha = FechaForm.format(completo);
        } catch (Exception e) {
            System.out.println("Se me murio en fecha  ");
        }

        return fecha;
    }

    public String formatoFechaHora(Date completo) {
        String fechaCompleta = "";
        DateFormat FechaForm = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        try {
            fechaCompleta = FechaForm.format(completo);
        } catch (Exception e) {
            System.out.println("Se me murio en fecha  ");
        }

        return fechaCompleta;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        popupMenuOculto = new java.awt.PopupMenu();
        popupAbrir = new java.awt.MenuItem();
        popupSalir = new java.awt.MenuItem();
        panelHeader = new javax.swing.JPanel();
        btnMinimizar = new javax.swing.JButton();
        btnCerrar = new javax.swing.JButton();
        Kayrana = new javax.swing.JLabel();
        panelMenu = new javax.swing.JPanel();
        btnEscribir = new javax.swing.JButton();
        lblEscribir = new javax.swing.JLabel();
        btnHorario = new javax.swing.JButton();
        lblHorario = new javax.swing.JLabel();
        btnSalirCuenta = new javax.swing.JButton();
        lblIngresar = new javax.swing.JLabel();
        btnIngresar = new javax.swing.JButton();
        btnInicio = new javax.swing.JButton();
        lblInicio = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        lblNombreUsuario = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        panelCuerpo = new javax.swing.JPanel();
        panelInicio = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        lblNombreDesarrolladores = new javax.swing.JLabel();
        lblInformacion = new javax.swing.JLabel();
        lblFondoInicio = new javax.swing.JLabel();
        panelAgenda = new javax.swing.JPanel();
        btnNotas = new javax.swing.JButton();
        lblBarraNotas = new javax.swing.JLabel();
        btnTareas = new javax.swing.JButton();
        lblBarraTareas = new javax.swing.JLabel();
        btnRecord = new javax.swing.JButton();
        lblBarraRecordatorios = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        panelCuerpoAgenda = new javax.swing.JPanel();
        panelInicioAgenda = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        lblApuntesMostrar = new javax.swing.JLabel();
        lblRecordatoriosMostrar = new javax.swing.JLabel();
        lblTareasMostrar = new javax.swing.JLabel();
        lblApuntes = new javax.swing.JLabel();
        lblTareas = new javax.swing.JLabel();
        lblRecordatorios = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        panelApuntes = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        panelMenuNotas = new javax.swing.JPanel();
        lblNotasColor = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblApuntes = new JTable(){
            @Override 
            public boolean isCellEditable(int rowIndex, int colIndex){
                return false; 
            }
        };
        jScrollPane2 = new javax.swing.JScrollPane();
        txtApunteIngresar = new javax.swing.JTextArea();
        btnGuardarApunte = new javax.swing.JButton();
        apunteSelec = new javax.swing.JLabel();
        btnEliminarApunte = new javax.swing.JButton();
        btnModificarApunte = new javax.swing.JButton();
        fechaApunte = new javax.swing.JLabel();
        jLabel70 = new javax.swing.JLabel();
        jLabel71 = new javax.swing.JLabel();
        panelTareas = new javax.swing.JPanel();
        panelMenuTareas = new javax.swing.JPanel();
        lblTareasColor = new javax.swing.JLabel();
        jLabel64 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblTareas = new JTable(){
            @Override 
            public boolean isCellEditable(int rowIndex, int colIndex){
                return false; 
            }
        };
        jScrollPane4 = new javax.swing.JScrollPane();
        txtIngresarTareas = new javax.swing.JTextArea();
        btnGuardarTarea = new javax.swing.JButton();
        tareaSelec = new javax.swing.JLabel();
        btnTareaTerminado = new javax.swing.JButton();
        btnModificarTarea = new javax.swing.JButton();
        fechaTareaCompletar = new javax.swing.JLabel();
        fechaTarea = new javax.swing.JLabel();
        btnEliminarTarea = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jLabel66 = new javax.swing.JLabel();
        jLabel72 = new javax.swing.JLabel();
        dateChooserTarea = new com.toedter.calendar.JDateChooser();
        jLabel76 = new javax.swing.JLabel();
        jLabel77 = new javax.swing.JLabel();
        Date fechaForm = new Date();
        SpinnerDateModel mf =
        new SpinnerDateModel(fechaForm, null, null, Calendar.HOUR_OF_DAY);
        horaTarea = new javax.swing.JSpinner(mf);
        panelRecordatorio = new javax.swing.JPanel();
        panelMenuRecordatorio = new javax.swing.JPanel();
        lblRecordatorioColor = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblRecordatorios = new JTable(){
            @Override 
            public boolean isCellEditable(int rowIndex, int colIndex){
                return false; 
            }
        };
        recordatorioSelec = new javax.swing.JLabel();
        fechaRecordatorio = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        txtIngresarRecordatorio = new javax.swing.JTextArea();
        btnGuardarRecordatorio = new javax.swing.JButton();
        jLabel65 = new javax.swing.JLabel();
        btnModificarRecordatorio = new javax.swing.JButton();
        btnEliminarRecordatorio = new javax.swing.JButton();
        fechaRecordatorioTermina = new javax.swing.JLabel();
        dateChooserRecordatorio = new com.toedter.calendar.JDateChooser();
        Date date = new Date();
        SpinnerDateModel sm =
        new SpinnerDateModel(date, null, null, Calendar.HOUR_OF_DAY);
        horaRecordatorio = new javax.swing.JSpinner(sm);
        jLabel68 = new javax.swing.JLabel();
        jLabel69 = new javax.swing.JLabel();
        jLabel73 = new javax.swing.JLabel();
        jLabel74 = new javax.swing.JLabel();
        jLabel75 = new javax.swing.JLabel();
        fondoMenuEscribir = new javax.swing.JLabel();
        panelHorario = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        lblFondoHorario1 = new javax.swing.JLabel();
        panelCalendario = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        jLabel67 = new javax.swing.JLabel();
        lblFondoHorario = new javax.swing.JLabel();
        panelPerfil = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        btnIngresarHorario = new javax.swing.JButton();
        btnIngresarCurso = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel2 = new javax.swing.JPanel();
        lblCorreo = new javax.swing.JLabel();
        btnAjustes = new javax.swing.JButton();
        lblNombreApellido = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        lblPerfilCarrera = new javax.swing.JLabel();
        lblCantidadRecord = new javax.swing.JLabel();
        jLabel63 = new javax.swing.JLabel();
        jLabel78 = new javax.swing.JLabel();
        lblCantidadNotas = new javax.swing.JLabel();
        lblCantidadTareas = new javax.swing.JLabel();
        lblNotasDistributivo = new javax.swing.JLabel();
        lblCantidadNotasDistributivo = new javax.swing.JLabel();
        jLabel61 = new javax.swing.JLabel();

        popupMenuOculto.setFont(new java.awt.Font("Trebuchet MS", 0, 14)); // NOI18N
        popupMenuOculto.setLabel("Kayrana");

        popupAbrir.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N
        popupAbrir.setLabel("Abrir");
        popupAbrir.setName("");
        popupAbrir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popupAbrirActionPerformed(evt);
            }
        });
        popupMenuOculto.add(popupAbrir);

        popupSalir.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N
        popupSalir.setLabel("Salir");
        popupSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popupSalirActionPerformed(evt);
            }
        });
        popupMenuOculto.add(popupSalir);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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

        btnMinimizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_Subtract_20px.png"))); // NOI18N
        btnMinimizar.setBorderPainted(false);
        btnMinimizar.setContentAreaFilled(false);
        btnMinimizar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnMinimizar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_Subtract_20px_1.png"))); // NOI18N
        btnMinimizar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnMinimizarMouseClicked(evt);
            }
        });
        panelHeader.add(btnMinimizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(1020, 10, 40, 18));

        btnCerrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_Delete_20px_1.png"))); // NOI18N
        btnCerrar.setBorderPainted(false);
        btnCerrar.setContentAreaFilled(false);
        btnCerrar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCerrar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_Delete_20px_2.png"))); // NOI18N
        btnCerrar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCerrarMouseClicked(evt);
            }
        });
        panelHeader.add(btnCerrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(1057, 0, 41, -1));

        Kayrana.setBackground(new java.awt.Color(255, 255, 255));
        Kayrana.setFont(new java.awt.Font("Trebuchet MS", 0, 18)); // NOI18N
        Kayrana.setForeground(new java.awt.Color(255, 255, 255));
        Kayrana.setText("Kayrana");
        panelHeader.add(Kayrana, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 1, 90, 30));

        getContentPane().add(panelHeader, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1100, 30));

        panelMenu.setBackground(new java.awt.Color(21, 89, 110));
        panelMenu.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnEscribir.setFont(new java.awt.Font("Trebuchet MS", 1, 20)); // NOI18N
        btnEscribir.setForeground(new java.awt.Color(255, 255, 255));
        btnEscribir.setText("Agenda");
        btnEscribir.setBorder(null);
        btnEscribir.setBorderPainted(false);
        btnEscribir.setContentAreaFilled(false);
        btnEscribir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEscribir.setFocusPainted(false);
        btnEscribir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEscribirActionPerformed(evt);
            }
        });
        panelMenu.add(btnEscribir, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 0, 140, 50));

        lblEscribir.setBackground(new java.awt.Color(201, 211, 200));
        lblEscribir.setOpaque(true);
        panelMenu.add(lblEscribir, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 40, 140, 10));

        btnHorario.setFont(new java.awt.Font("Trebuchet MS", 1, 20)); // NOI18N
        btnHorario.setForeground(new java.awt.Color(255, 255, 255));
        btnHorario.setText("Horario");
        btnHorario.setBorder(null);
        btnHorario.setBorderPainted(false);
        btnHorario.setContentAreaFilled(false);
        btnHorario.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnHorario.setFocusPainted(false);
        btnHorario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHorarioActionPerformed(evt);
            }
        });
        panelMenu.add(btnHorario, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 0, 140, 50));

        lblHorario.setBackground(new java.awt.Color(201, 211, 200));
        lblHorario.setOpaque(true);
        panelMenu.add(lblHorario, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 40, 140, 10));

        btnSalirCuenta.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        btnSalirCuenta.setForeground(new java.awt.Color(255, 255, 255));
        btnSalirCuenta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Menu/icons8_Logout_Rounded_Left_20px.png"))); // NOI18N
        btnSalirCuenta.setText("S A L I R");
        btnSalirCuenta.setBorder(null);
        btnSalirCuenta.setBorderPainted(false);
        btnSalirCuenta.setContentAreaFilled(false);
        btnSalirCuenta.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSalirCuenta.setFocusPainted(false);
        btnSalirCuenta.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Menu/icons8_Logout_Rounded_Left_20px_5.png"))); // NOI18N
        btnSalirCuenta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSalirCuentaMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnSalirCuentaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnSalirCuentaMouseExited(evt);
            }
        });
        panelMenu.add(btnSalirCuenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(1010, 20, 90, 30));

        lblIngresar.setBackground(new java.awt.Color(201, 211, 200));
        lblIngresar.setOpaque(true);
        panelMenu.add(lblIngresar, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 40, 140, 10));

        btnIngresar.setFont(new java.awt.Font("Trebuchet MS", 1, 20)); // NOI18N
        btnIngresar.setForeground(new java.awt.Color(255, 255, 255));
        btnIngresar.setText("Perfil");
        btnIngresar.setBorder(null);
        btnIngresar.setBorderPainted(false);
        btnIngresar.setContentAreaFilled(false);
        btnIngresar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnIngresar.setFocusPainted(false);
        btnIngresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIngresarActionPerformed(evt);
            }
        });
        panelMenu.add(btnIngresar, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 0, 140, 50));

        btnInicio.setFont(new java.awt.Font("Trebuchet MS", 1, 20)); // NOI18N
        btnInicio.setForeground(new java.awt.Color(255, 255, 255));
        btnInicio.setText("Inicio");
        btnInicio.setBorder(null);
        btnInicio.setBorderPainted(false);
        btnInicio.setContentAreaFilled(false);
        btnInicio.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnInicio.setFocusPainted(false);
        btnInicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInicioActionPerformed(evt);
            }
        });
        panelMenu.add(btnInicio, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 0, 140, 50));

        lblInicio.setBackground(new java.awt.Color(99, 46, 52));
        lblInicio.setOpaque(true);
        panelMenu.add(lblInicio, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 40, 140, 10));

        jLabel2.setBackground(new java.awt.Color(237, 240, 236));
        jLabel2.setOpaque(true);
        panelMenu.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 1100, 10));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Menu/LogoLapiz.png"))); // NOI18N
        panelMenu.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 60, 50));

        lblNombreUsuario.setFont(new java.awt.Font("Trebuchet MS", 0, 14)); // NOI18N
        lblNombreUsuario.setForeground(new java.awt.Color(204, 204, 204));
        lblNombreUsuario.setText("The Shadow Warrior");
        panelMenu.add(lblNombreUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 10, 150, 40));

        jLabel5.setFont(new java.awt.Font("Trebuchet MS", 1, 16)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Bienvenido:");
        panelMenu.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 10, 100, 40));

        getContentPane().add(panelMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 1100, -1));

        panelCuerpo.setBackground(new java.awt.Color(255, 255, 255));
        panelCuerpo.setMaximumSize(new java.awt.Dimension(770, 580));
        panelCuerpo.setMinimumSize(new java.awt.Dimension(770, 580));
        panelCuerpo.setPreferredSize(new java.awt.Dimension(770, 580));
        panelCuerpo.setLayout(new java.awt.CardLayout());

        panelInicio.setBackground(new java.awt.Color(219, 225, 218));
        panelInicio.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setBackground(new java.awt.Color(111, 111, 110));
        jLabel4.setFont(new java.awt.Font("MS PGothic", 2, 35)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(111, 111, 110));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Menu/logo.png"))); // NOI18N
        panelInicio.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 290, 220));

        lblNombreDesarrolladores.setFont(new java.awt.Font("Trebuchet MS", 2, 11)); // NOI18N
        lblNombreDesarrolladores.setForeground(new java.awt.Color(255, 255, 255));
        lblNombreDesarrolladores.setText("Desarrolladores:   Byron Anchundia             Paola Medina             Andres Ullauri             Johnny Garcia");
        panelInicio.add(lblNombreDesarrolladores, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 540, 540, 20));

        lblInformacion.setBackground(new java.awt.Color(9, 28, 32));
        lblInformacion.setForeground(new java.awt.Color(255, 255, 255));
        lblInformacion.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblInformacion.setText("<html>  <h2> --   &#169;  </h2>   </html> ");
        lblInformacion.setIconTextGap(5);
        lblInformacion.setOpaque(true);
        panelInicio.add(lblInformacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 540, 1100, 20));

        lblFondoInicio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Inicio/Instituto.png"))); // NOI18N
        panelInicio.add(lblFondoInicio, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1100, 560));

        panelCuerpo.add(panelInicio, "card2");

        panelAgenda.setBackground(new java.awt.Color(21, 89, 110));
        panelAgenda.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnNotas.setBackground(new java.awt.Color(40, 39, 38));
        btnNotas.setFont(new java.awt.Font("Trebuchet MS", 1, 36)); // NOI18N
        btnNotas.setForeground(new java.awt.Color(255, 255, 255));
        btnNotas.setText("A P U N T E S");
        btnNotas.setBorderPainted(false);
        btnNotas.setContentAreaFilled(false);
        btnNotas.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnNotas.setFocusPainted(false);
        btnNotas.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btnNotas.setIconTextGap(15);
        btnNotas.setInheritsPopupMenu(true);
        btnNotas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnNotasMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnNotasMouseExited(evt);
            }
        });
        btnNotas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNotasActionPerformed(evt);
            }
        });
        panelAgenda.add(btnNotas, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 310, 100));

        lblBarraNotas.setBackground(new java.awt.Color(237, 240, 236));
        lblBarraNotas.setOpaque(true);
        panelAgenda.add(lblBarraNotas, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 100, 310, 10));

        btnTareas.setBackground(new java.awt.Color(40, 39, 38));
        btnTareas.setFont(new java.awt.Font("Trebuchet MS", 1, 24)); // NOI18N
        btnTareas.setForeground(new java.awt.Color(255, 255, 255));
        btnTareas.setText("T A R E A S");
        btnTareas.setBorderPainted(false);
        btnTareas.setContentAreaFilled(false);
        btnTareas.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnTareas.setFocusPainted(false);
        btnTareas.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btnTareas.setIconTextGap(15);
        btnTareas.setInheritsPopupMenu(true);
        btnTareas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnTareasMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnTareasMouseExited(evt);
            }
        });
        btnTareas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTareasActionPerformed(evt);
            }
        });
        panelAgenda.add(btnTareas, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 110, 310, 100));

        lblBarraTareas.setBackground(new java.awt.Color(237, 240, 236));
        lblBarraTareas.setOpaque(true);
        panelAgenda.add(lblBarraTareas, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 210, 310, 10));

        btnRecord.setBackground(new java.awt.Color(40, 39, 38));
        btnRecord.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        btnRecord.setForeground(new java.awt.Color(255, 255, 255));
        btnRecord.setText("R E C O R D A T O R I O S ");
        btnRecord.setBorderPainted(false);
        btnRecord.setContentAreaFilled(false);
        btnRecord.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnRecord.setFocusPainted(false);
        btnRecord.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btnRecord.setIconTextGap(15);
        btnRecord.setInheritsPopupMenu(true);
        btnRecord.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btnRecord.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnRecordMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnRecordMouseExited(evt);
            }
        });
        btnRecord.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRecordActionPerformed(evt);
            }
        });
        panelAgenda.add(btnRecord, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 220, 310, 100));

        lblBarraRecordatorios.setBackground(new java.awt.Color(237, 240, 236));
        lblBarraRecordatorios.setOpaque(true);
        panelAgenda.add(lblBarraRecordatorios, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 320, 310, 10));

        jLabel10.setBackground(new java.awt.Color(40, 39, 38));
        jLabel10.setFont(new java.awt.Font("Trebuchet MS", 1, 24)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/logo.png"))); // NOI18N
        jLabel10.setText("A G E N D A");
        jLabel10.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel10.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel10.setIconTextGap(10);
        jLabel10.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jLabel10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel10MouseClicked(evt);
            }
        });
        panelAgenda.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 330, 310, 230));

        panelCuerpoAgenda.setBackground(new java.awt.Color(237, 240, 236));
        panelCuerpoAgenda.setLayout(new java.awt.CardLayout());

        panelInicioAgenda.setBackground(new java.awt.Color(40, 39, 38));
        panelInicioAgenda.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel8.setFont(new java.awt.Font("Trebuchet MS", 0, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(99, 46, 52));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("R E C O R D A T O R I O S");
        panelInicioAgenda.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 30, 210, 40));

        jLabel12.setFont(new java.awt.Font("Trebuchet MS", 0, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(99, 46, 52));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("T A R E A S");
        panelInicioAgenda.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(296, 30, 200, 40));

        jLabel11.setFont(new java.awt.Font("Trebuchet MS", 0, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(99, 46, 52));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("A P U N T E S");
        panelInicioAgenda.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(43, 29, 190, 40));

        lblApuntesMostrar.setBackground(new java.awt.Color(249, 249, 249));
        lblApuntesMostrar.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        lblApuntesMostrar.setOpaque(true);
        panelInicioAgenda.add(lblApuntesMostrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 70, 190, 460));

        lblRecordatoriosMostrar.setBackground(new java.awt.Color(249, 249, 249));
        lblRecordatoriosMostrar.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        lblRecordatoriosMostrar.setOpaque(true);
        panelInicioAgenda.add(lblRecordatoriosMostrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 70, 190, 460));

        lblTareasMostrar.setBackground(new java.awt.Color(249, 249, 249));
        lblTareasMostrar.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        lblTareasMostrar.setOpaque(true);
        panelInicioAgenda.add(lblTareasMostrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 70, 190, 460));

        lblApuntes.setBackground(new java.awt.Color(237, 240, 236));
        lblApuntes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Agenda/FondoNoti.png"))); // NOI18N
        lblApuntes.setToolTipText("");
        panelInicioAgenda.add(lblApuntes, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, 210, 520));

        lblTareas.setBackground(new java.awt.Color(237, 240, 236));
        lblTareas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Agenda/FondoNoti.png"))); // NOI18N
        lblTareas.setToolTipText("");
        panelInicioAgenda.add(lblTareas, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 20, 210, 520));

        lblRecordatorios.setBackground(new java.awt.Color(237, 240, 236));
        lblRecordatorios.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lblRecordatorios.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Agenda/FondoNoti.png"))); // NOI18N
        lblRecordatorios.setToolTipText("");
        panelInicioAgenda.add(lblRecordatorios, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 20, 210, 520));

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Agenda/fonodInicioAgenda.png"))); // NOI18N
        jLabel9.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jLabel9.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        panelInicioAgenda.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 790, 560));

        panelCuerpoAgenda.add(panelInicioAgenda, "card2");

        panelApuntes.setBackground(new java.awt.Color(40, 39, 38));
        panelApuntes.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel13.setBackground(new java.awt.Color(249, 126, 82));
        jLabel13.setOpaque(true);
        panelApuntes.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 150, 790, 10));

        panelMenuNotas.setBackground(new java.awt.Color(237, 240, 236));
        panelMenuNotas.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblNotasColor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Agenda/NotaColor.png"))); // NOI18N
        panelMenuNotas.add(lblNotasColor, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 250, 40));

        panelApuntes.add(panelMenuNotas, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 790, 40));

        jScrollPane1.setBackground(new java.awt.Color(51, 51, 51));

        tblApuntes.setBackground(new java.awt.Color(51, 51, 51));
        tblApuntes.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N
        tblApuntes.setForeground(new java.awt.Color(255, 255, 255));
        tblApuntes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null}
            },
            new String [] {
                "Notas"
            }
        ));
        tblApuntes.setGridColor(new java.awt.Color(51, 51, 51));
        tblApuntes.setSelectionBackground(new java.awt.Color(21, 89, 110));
        tblApuntes.setSelectionForeground(new java.awt.Color(204, 204, 204));
        tblApuntes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblApuntesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblApuntes);

        panelApuntes.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 270, 790, 290));

        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        txtApunteIngresar.setBackground(new java.awt.Color(229, 232, 228));
        txtApunteIngresar.setColumns(20);
        txtApunteIngresar.setFont(new java.awt.Font("Trebuchet MS", 0, 14)); // NOI18N
        txtApunteIngresar.setRows(1);
        jScrollPane2.setViewportView(txtApunteIngresar);

        panelApuntes.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 680, 60));

        btnGuardarApunte.setBackground(new java.awt.Color(102, 102, 102));
        btnGuardarApunte.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Agenda/icons8_Ok_45px_2.png"))); // NOI18N
        btnGuardarApunte.setBorderPainted(false);
        btnGuardarApunte.setContentAreaFilled(false);
        btnGuardarApunte.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGuardarApunte.setFocusPainted(false);
        btnGuardarApunte.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Agenda/icons8_Ok_45px_3.png"))); // NOI18N
        btnGuardarApunte.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnGuardarApunteMouseClicked(evt);
            }
        });
        panelApuntes.add(btnGuardarApunte, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 80, 80, 60));

        apunteSelec.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N
        apunteSelec.setForeground(new java.awt.Color(204, 204, 204));
        apunteSelec.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        panelApuntes.add(apunteSelec, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 170, 580, 90));

        btnEliminarApunte.setBackground(new java.awt.Color(118, 125, 127));
        btnEliminarApunte.setForeground(new java.awt.Color(255, 255, 255));
        btnEliminarApunte.setText("Eliminar");
        btnEliminarApunte.setBorderPainted(false);
        btnEliminarApunte.setContentAreaFilled(false);
        btnEliminarApunte.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEliminarApunte.setFocusPainted(false);
        btnEliminarApunte.setOpaque(true);
        btnEliminarApunte.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEliminarApunteMouseClicked(evt);
            }
        });
        panelApuntes.add(btnEliminarApunte, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 230, 80, -1));

        btnModificarApunte.setBackground(new java.awt.Color(118, 125, 127));
        btnModificarApunte.setForeground(new java.awt.Color(255, 255, 255));
        btnModificarApunte.setText("Modificar");
        btnModificarApunte.setBorderPainted(false);
        btnModificarApunte.setContentAreaFilled(false);
        btnModificarApunte.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnModificarApunte.setFocusPainted(false);
        btnModificarApunte.setOpaque(true);
        btnModificarApunte.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnModificarApunteMouseClicked(evt);
            }
        });
        panelApuntes.add(btnModificarApunte, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 180, 80, -1));

        fechaApunte.setForeground(new java.awt.Color(255, 255, 255));
        fechaApunte.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        panelApuntes.add(fechaApunte, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 180, 90, 50));

        jLabel70.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel70.setForeground(new java.awt.Color(204, 204, 204));
        jLabel70.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel70.setText("Escrito");
        panelApuntes.add(jLabel70, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 240, 90, -1));

        jLabel71.setFont(new java.awt.Font("Trebuchet MS", 1, 20)); // NOI18N
        jLabel71.setForeground(new java.awt.Color(21, 89, 110));
        jLabel71.setText("Ingresar Apunte:");
        panelApuntes.add(jLabel71, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 180, -1));

        panelCuerpoAgenda.add(panelApuntes, "card5");

        panelTareas.setBackground(new java.awt.Color(40, 39, 38));
        panelTareas.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelMenuTareas.setBackground(new java.awt.Color(237, 240, 236));
        panelMenuTareas.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblTareasColor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Agenda/TareaColor.png"))); // NOI18N
        panelMenuTareas.add(lblTareasColor, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, -10, 240, 50));

        panelTareas.add(panelMenuTareas, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 790, 40));

        jLabel64.setBackground(new java.awt.Color(250, 193, 0));
        jLabel64.setOpaque(true);
        panelTareas.add(jLabel64, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 160, 790, 10));

        jScrollPane3.setBackground(new java.awt.Color(51, 51, 51));

        tblTareas.setBackground(new java.awt.Color(51, 51, 51));
        tblTareas.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N
        tblTareas.setForeground(new java.awt.Color(255, 255, 255));
        tblTareas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null}
            },
            new String [] {
                "Tareas"
            }
        ));
        tblTareas.setGridColor(new java.awt.Color(51, 51, 51));
        tblTareas.setSelectionBackground(new java.awt.Color(21, 89, 110));
        tblTareas.setSelectionForeground(new java.awt.Color(204, 204, 204));
        tblTareas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblTareasMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblTareas);

        panelTareas.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 270, 790, 290));

        jScrollPane4.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        txtIngresarTareas.setBackground(new java.awt.Color(229, 232, 228));
        txtIngresarTareas.setColumns(20);
        txtIngresarTareas.setFont(new java.awt.Font("Trebuchet MS", 0, 14)); // NOI18N
        txtIngresarTareas.setRows(1);
        txtIngresarTareas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtIngresarTareasMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(txtIngresarTareas);

        panelTareas.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 680, 60));

        btnGuardarTarea.setBackground(new java.awt.Color(102, 102, 102));
        btnGuardarTarea.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Agenda/icons8_Ok_45px_2.png"))); // NOI18N
        btnGuardarTarea.setBorderPainted(false);
        btnGuardarTarea.setContentAreaFilled(false);
        btnGuardarTarea.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGuardarTarea.setFocusPainted(false);
        btnGuardarTarea.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Agenda/icons8_Ok_45px_3.png"))); // NOI18N
        btnGuardarTarea.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnGuardarTareaMouseClicked(evt);
            }
        });
        panelTareas.add(btnGuardarTarea, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 90, 80, 60));

        tareaSelec.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N
        tareaSelec.setForeground(new java.awt.Color(204, 204, 204));
        tareaSelec.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        panelTareas.add(tareaSelec, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 180, 480, 80));

        btnTareaTerminado.setBackground(new java.awt.Color(118, 125, 127));
        btnTareaTerminado.setForeground(new java.awt.Color(255, 255, 255));
        btnTareaTerminado.setText("Completa");
        btnTareaTerminado.setBorderPainted(false);
        btnTareaTerminado.setContentAreaFilled(false);
        btnTareaTerminado.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnTareaTerminado.setFocusPainted(false);
        btnTareaTerminado.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btnTareaTerminado.setOpaque(true);
        btnTareaTerminado.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnTareaTerminadoMouseClicked(evt);
            }
        });
        panelTareas.add(btnTareaTerminado, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 180, 80, -1));

        btnModificarTarea.setBackground(new java.awt.Color(118, 125, 127));
        btnModificarTarea.setForeground(new java.awt.Color(255, 255, 255));
        btnModificarTarea.setText("Modificar");
        btnModificarTarea.setBorderPainted(false);
        btnModificarTarea.setContentAreaFilled(false);
        btnModificarTarea.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnModificarTarea.setFocusPainted(false);
        btnModificarTarea.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btnModificarTarea.setOpaque(true);
        btnModificarTarea.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnModificarTareaMouseClicked(evt);
            }
        });
        panelTareas.add(btnModificarTarea, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 210, 80, -1));

        fechaTareaCompletar.setForeground(new java.awt.Color(255, 255, 255));
        fechaTareaCompletar.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        panelTareas.add(fechaTareaCompletar, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 190, 80, 50));

        fechaTarea.setForeground(new java.awt.Color(255, 255, 255));
        fechaTarea.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        panelTareas.add(fechaTarea, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, 80, 50));

        btnEliminarTarea.setBackground(new java.awt.Color(118, 125, 127));
        btnEliminarTarea.setForeground(new java.awt.Color(255, 255, 255));
        btnEliminarTarea.setText("Eliminar");
        btnEliminarTarea.setBorderPainted(false);
        btnEliminarTarea.setContentAreaFilled(false);
        btnEliminarTarea.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEliminarTarea.setFocusPainted(false);
        btnEliminarTarea.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btnEliminarTarea.setOpaque(true);
        btnEliminarTarea.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEliminarTareaMouseClicked(evt);
            }
        });
        panelTareas.add(btnEliminarTarea, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 240, 80, -1));

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(204, 204, 204));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Completar");
        panelTareas.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 240, 80, -1));

        jLabel66.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel66.setForeground(new java.awt.Color(204, 204, 204));
        jLabel66.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel66.setText("Escrito");
        panelTareas.add(jLabel66, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 240, 80, -1));

        jLabel72.setFont(new java.awt.Font("Trebuchet MS", 1, 20)); // NOI18N
        jLabel72.setForeground(new java.awt.Color(21, 89, 110));
        jLabel72.setText("Ingresar Tarea:");
        panelTareas.add(jLabel72, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 160, 30));

        dateChooserTarea.setFont(new java.awt.Font("Trebuchet MS", 0, 14)); // NOI18N
        panelTareas.add(dateChooserTarea, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 50, 120, 30));

        jLabel76.setFont(new java.awt.Font("Trebuchet MS", 1, 20)); // NOI18N
        jLabel76.setForeground(new java.awt.Color(21, 89, 110));
        jLabel76.setText("Fecha:");
        panelTareas.add(jLabel76, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 50, 80, 30));

        jLabel77.setFont(new java.awt.Font("Trebuchet MS", 1, 20)); // NOI18N
        jLabel77.setForeground(new java.awt.Color(21, 89, 110));
        jLabel77.setText("Hora:");
        panelTareas.add(jLabel77, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 50, 60, 30));

        horaTarea.setFont(new java.awt.Font("Trebuchet MS", 0, 14)); // NOI18N
        panelTareas.add(horaTarea, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 50, 120, 30));
        JSpinner.DateEditor def = new JSpinner.DateEditor(horaTarea, "HH:mm:ss");
        horaTarea.setEditor(def);

        panelCuerpoAgenda.add(panelTareas, "card4");

        panelRecordatorio.setBackground(new java.awt.Color(40, 39, 38));
        panelRecordatorio.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N
        panelRecordatorio.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelMenuRecordatorio.setBackground(new java.awt.Color(237, 240, 236));
        panelMenuRecordatorio.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblRecordatorioColor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Agenda/RecordatorioColor.png"))); // NOI18N
        panelMenuRecordatorio.add(lblRecordatorioColor, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, -10, 240, 50));

        panelRecordatorio.add(panelMenuRecordatorio, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 790, 40));

        jScrollPane5.setBackground(new java.awt.Color(51, 51, 51));

        tblRecordatorios.setBackground(new java.awt.Color(51, 51, 51));
        tblRecordatorios.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N
        tblRecordatorios.setForeground(new java.awt.Color(255, 255, 255));
        tblRecordatorios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Notas"
            }
        ));
        tblRecordatorios.setGridColor(new java.awt.Color(51, 51, 51));
        tblRecordatorios.setSelectionBackground(new java.awt.Color(21, 89, 110));
        tblRecordatorios.setSelectionForeground(new java.awt.Color(204, 204, 204));
        tblRecordatorios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblRecordatoriosMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tblRecordatorios);

        panelRecordatorio.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 260, 790, 300));

        recordatorioSelec.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N
        recordatorioSelec.setForeground(new java.awt.Color(204, 204, 204));
        recordatorioSelec.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        panelRecordatorio.add(recordatorioSelec, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 180, 500, 70));

        fechaRecordatorio.setForeground(new java.awt.Color(255, 255, 255));
        fechaRecordatorio.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        panelRecordatorio.add(fechaRecordatorio, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 180, 80, 50));

        jScrollPane6.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        txtIngresarRecordatorio.setBackground(new java.awt.Color(229, 232, 228));
        txtIngresarRecordatorio.setColumns(20);
        txtIngresarRecordatorio.setFont(new java.awt.Font("Trebuchet MS", 0, 14)); // NOI18N
        txtIngresarRecordatorio.setRows(1);
        jScrollPane6.setViewportView(txtIngresarRecordatorio);

        panelRecordatorio.add(jScrollPane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 680, 60));

        btnGuardarRecordatorio.setBackground(new java.awt.Color(102, 102, 102));
        btnGuardarRecordatorio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Agenda/icons8_Ok_45px_2.png"))); // NOI18N
        btnGuardarRecordatorio.setBorderPainted(false);
        btnGuardarRecordatorio.setContentAreaFilled(false);
        btnGuardarRecordatorio.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGuardarRecordatorio.setFocusPainted(false);
        btnGuardarRecordatorio.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Agenda/icons8_Ok_45px_3.png"))); // NOI18N
        btnGuardarRecordatorio.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnGuardarRecordatorioMouseClicked(evt);
            }
        });
        panelRecordatorio.add(btnGuardarRecordatorio, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 90, 80, 60));

        jLabel65.setBackground(new java.awt.Color(200, 55, 58));
        jLabel65.setOpaque(true);
        panelRecordatorio.add(jLabel65, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 160, 790, 10));

        btnModificarRecordatorio.setBackground(new java.awt.Color(118, 125, 127));
        btnModificarRecordatorio.setForeground(new java.awt.Color(255, 255, 255));
        btnModificarRecordatorio.setText("Modificar");
        btnModificarRecordatorio.setBorderPainted(false);
        btnModificarRecordatorio.setContentAreaFilled(false);
        btnModificarRecordatorio.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnModificarRecordatorio.setFocusPainted(false);
        btnModificarRecordatorio.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btnModificarRecordatorio.setOpaque(true);
        btnModificarRecordatorio.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnModificarRecordatorioMouseClicked(evt);
            }
        });
        panelRecordatorio.add(btnModificarRecordatorio, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 180, 80, -1));

        btnEliminarRecordatorio.setBackground(new java.awt.Color(118, 125, 127));
        btnEliminarRecordatorio.setForeground(new java.awt.Color(255, 255, 255));
        btnEliminarRecordatorio.setText("Eliminar");
        btnEliminarRecordatorio.setBorderPainted(false);
        btnEliminarRecordatorio.setContentAreaFilled(false);
        btnEliminarRecordatorio.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEliminarRecordatorio.setFocusPainted(false);
        btnEliminarRecordatorio.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btnEliminarRecordatorio.setOpaque(true);
        btnEliminarRecordatorio.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEliminarRecordatorioMouseClicked(evt);
            }
        });
        panelRecordatorio.add(btnEliminarRecordatorio, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 220, 80, -1));

        fechaRecordatorioTermina.setForeground(new java.awt.Color(255, 255, 255));
        fechaRecordatorioTermina.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        panelRecordatorio.add(fechaRecordatorioTermina, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 180, 80, 50));

        dateChooserRecordatorio.setFont(new java.awt.Font("Trebuchet MS", 0, 14)); // NOI18N
        panelRecordatorio.add(dateChooserRecordatorio, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 50, 120, 30));

        horaRecordatorio.setFont(new java.awt.Font("Trebuchet MS", 0, 14)); // NOI18N
        panelRecordatorio.add(horaRecordatorio, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 50, 120, 30));
        JSpinner.DateEditor de = new JSpinner.DateEditor(horaRecordatorio, "HH:mm:ss");
        horaRecordatorio.setEditor(de);

        jLabel68.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel68.setForeground(new java.awt.Color(204, 204, 204));
        jLabel68.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel68.setText("Escrito");
        panelRecordatorio.add(jLabel68, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 230, 80, -1));

        jLabel69.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel69.setForeground(new java.awt.Color(204, 204, 204));
        jLabel69.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel69.setText("Recordar");
        panelRecordatorio.add(jLabel69, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 230, 80, -1));

        jLabel73.setFont(new java.awt.Font("Trebuchet MS", 1, 20)); // NOI18N
        jLabel73.setForeground(new java.awt.Color(21, 89, 110));
        jLabel73.setText("Hora:");
        panelRecordatorio.add(jLabel73, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 50, 60, 30));

        jLabel74.setFont(new java.awt.Font("Trebuchet MS", 1, 20)); // NOI18N
        jLabel74.setForeground(new java.awt.Color(21, 89, 110));
        jLabel74.setText("Ingresar Recordatorio:");
        panelRecordatorio.add(jLabel74, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 230, 30));

        jLabel75.setFont(new java.awt.Font("Trebuchet MS", 1, 20)); // NOI18N
        jLabel75.setForeground(new java.awt.Color(21, 89, 110));
        jLabel75.setText("Fecha:");
        panelRecordatorio.add(jLabel75, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 50, 80, 30));

        panelCuerpoAgenda.add(panelRecordatorio, "card3");

        panelAgenda.add(panelCuerpoAgenda, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 0, 790, 560));

        fondoMenuEscribir.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        fondoMenuEscribir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Inicio/fondoEscribir1.jpg"))); // NOI18N
        fondoMenuEscribir.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        panelAgenda.add(fondoMenuEscribir, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 310, 560));

        panelCuerpo.add(panelAgenda, "card3");

        panelHorario.setBackground(new java.awt.Color(40, 39, 38));
        panelHorario.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(0, 153, 153));
        jPanel1.setForeground(new java.awt.Color(102, 102, 102));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblFondoHorario1.setForeground(new java.awt.Color(255, 255, 255));
        lblFondoHorario1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Horario/fondo2.jpg"))); // NOI18N
        lblFondoHorario1.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jPanel1.add(lblFondoHorario1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 1100, 290));

        panelHorario.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1100, 30));

        panelCalendario.setBackground(new java.awt.Color(49, 49, 48));
        panelCalendario.setForeground(new java.awt.Color(255, 255, 255));
        panelCalendario.setLayout(new java.awt.GridLayout(6, 6, 1, 1));

        jLabel6.setBackground(new java.awt.Color(74, 74, 73));
        jLabel6.setFont(new java.awt.Font("Trebuchet MS", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("<html> <body> <h1> <center> MDS-02 TDS </center> </h1>    <h4> <em> Calle Andres </em> </h4> </body></html>");
        jLabel6.setOpaque(true);
        panelCalendario.add(jLabel6);

        jLabel14.setBackground(new java.awt.Color(111, 111, 110));
        jLabel14.setFont(new java.awt.Font("Trebuchet MS", 0, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("<html> <body> <h1> <center> POO-02 TDS </center> </h1>   <h4> <em>  Herrera Jessica  </em> </h4> </body></html>");
        jLabel14.setOpaque(true);
        panelCalendario.add(jLabel14);

        jLabel15.setBackground(new java.awt.Color(74, 74, 73));
        jLabel15.setFont(new java.awt.Font("Trebuchet MS", 0, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("<html> <body> <h1> <center> BDS-02 TDS </center> </h1>   <h4> <em>Mejia Hector</em> </h4> </body></html>");
        jLabel15.setOpaque(true);
        panelCalendario.add(jLabel15);

        jLabel19.setBackground(new java.awt.Color(111, 111, 110));
        jLabel19.setFont(new java.awt.Font("Trebuchet MS", 0, 14)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setText("<html> <body> <h1> <center> POO-02 TDS </center> </h1>   <h4> <em>  Herrera Jessica  </em> </h4> </body></html>");
        jLabel19.setOpaque(true);
        panelCalendario.add(jLabel19);

        jLabel16.setBackground(new java.awt.Color(74, 74, 73));
        jLabel16.setFont(new java.awt.Font("Trebuchet MS", 0, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("<html> <body> <h1> <center> POO-02 TDS </center> </h1>   <h4> <em>  Herrera Jessica  </em> </h4> </body></html>");
        jLabel16.setOpaque(true);
        panelCalendario.add(jLabel16);

        jLabel26.setBackground(new java.awt.Color(111, 111, 110));
        jLabel26.setFont(new java.awt.Font("Trebuchet MS", 0, 14)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(255, 255, 255));
        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel26.setOpaque(true);
        panelCalendario.add(jLabel26);

        jLabel25.setBackground(new java.awt.Color(74, 74, 73));
        jLabel25.setFont(new java.awt.Font("Trebuchet MS", 0, 14)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(255, 255, 255));
        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel25.setText("<html> <body> <h1> <center> POO-02 TDS </center> </h1>   <h4> <em>  Herrera Jessica  </em> </h4> </body></html>");
        jLabel25.setOpaque(true);
        panelCalendario.add(jLabel25);

        jLabel24.setBackground(new java.awt.Color(111, 111, 110));
        jLabel24.setFont(new java.awt.Font("Trebuchet MS", 0, 14)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(255, 255, 255));
        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel24.setText("<html> <body> <h1> <center> POO-02 TDS </center> </h1>   <h4> <em>  Herrera Jessica  </em> </h4> </body></html>");
        jLabel24.setOpaque(true);
        panelCalendario.add(jLabel24);

        jLabel23.setBackground(new java.awt.Color(74, 74, 73));
        jLabel23.setFont(new java.awt.Font("Trebuchet MS", 0, 14)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 255, 255));
        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel23.setText("<html> <body> <h1> <center> BDS-02 TDS </center> </h1>   <h4> <em>Mejia Hector</em> </h4> </body></html>");
        jLabel23.setOpaque(true);
        panelCalendario.add(jLabel23);

        jLabel22.setBackground(new java.awt.Color(111, 111, 110));
        jLabel22.setFont(new java.awt.Font("Trebuchet MS", 0, 14)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel22.setText("<html> <body> <h1> <center> AGT-02 TDS </center> </h1>   <h4> <em>Riofrio Santiago</em> </h4> </body></html>");
        jLabel22.setOpaque(true);
        panelCalendario.add(jLabel22);

        jLabel34.setBackground(new java.awt.Color(74, 74, 73));
        jLabel34.setFont(new java.awt.Font("Trebuchet MS", 0, 14)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(255, 255, 255));
        jLabel34.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel34.setText("<html> <body> <h1> <center> POO-02 TDS </center> </h1>   <h4> <em>  Herrera Jessica  </em> </h4> </body></html>");
        jLabel34.setOpaque(true);
        panelCalendario.add(jLabel34);

        jLabel33.setBackground(new java.awt.Color(111, 111, 110));
        jLabel33.setFont(new java.awt.Font("Trebuchet MS", 0, 14)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(255, 255, 255));
        jLabel33.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel33.setOpaque(true);
        panelCalendario.add(jLabel33);

        jLabel28.setBackground(new java.awt.Color(74, 74, 73));
        jLabel28.setFont(new java.awt.Font("Trebuchet MS", 0, 14)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(255, 255, 255));
        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel28.setText("<html> <body> <h1> <center> POO-02 TDS </center> </h1>   <h4> <em>  Herrera Jessica  </em> </h4> </body></html>");
        jLabel28.setOpaque(true);
        panelCalendario.add(jLabel28);

        jLabel42.setBackground(new java.awt.Color(111, 111, 110));
        jLabel42.setFont(new java.awt.Font("Trebuchet MS", 0, 14)); // NOI18N
        jLabel42.setForeground(new java.awt.Color(255, 255, 255));
        jLabel42.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel42.setText("<html> <body> <h1> <center> AGT-02 TDS </center> </h1>   <h4> <em>Riofrio Santiago</em> </h4> </body></html>");
        jLabel42.setOpaque(true);
        panelCalendario.add(jLabel42);

        jLabel43.setBackground(new java.awt.Color(74, 74, 73));
        jLabel43.setFont(new java.awt.Font("Trebuchet MS", 0, 14)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(255, 255, 255));
        jLabel43.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel43.setText("<html> <body> <h1> <center> BDS-02 TDS </center> </h1>   <h4> <em>Mejia Hector</em> </h4> </body></html>");
        jLabel43.setOpaque(true);
        panelCalendario.add(jLabel43);

        jLabel38.setBackground(new java.awt.Color(111, 111, 110));
        jLabel38.setFont(new java.awt.Font("Trebuchet MS", 0, 14)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(255, 255, 255));
        jLabel38.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel38.setText("<html> <body> <h1> <center> AGT-02 TDS </center> </h1>   <h4> <em>Riofrio Santiago</em> </h4> </body></html>");
        jLabel38.setOpaque(true);
        panelCalendario.add(jLabel38);

        jLabel27.setBackground(new java.awt.Color(74, 74, 73));
        jLabel27.setFont(new java.awt.Font("Trebuchet MS", 0, 14)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 255, 255));
        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel27.setText("<html> <body> <h1> <center> LGC-02 TDS </center> </h1>   <h4> <em>Loja Jaime</em> </h4> </body></html>");
        jLabel27.setOpaque(true);
        panelCalendario.add(jLabel27);

        jLabel37.setBackground(new java.awt.Color(111, 111, 110));
        jLabel37.setFont(new java.awt.Font("Trebuchet MS", 0, 14)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(255, 255, 255));
        jLabel37.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel37.setOpaque(true);
        panelCalendario.add(jLabel37);

        jLabel36.setBackground(new java.awt.Color(74, 74, 73));
        jLabel36.setFont(new java.awt.Font("Trebuchet MS", 0, 14)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(255, 255, 255));
        jLabel36.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel36.setText("<html> <body> <h1> <center> BDS-02 TDS </center> </h1>   <h4> <em>Mejia Hector</em> </h4> </body></html>");
        jLabel36.setOpaque(true);
        panelCalendario.add(jLabel36);

        jLabel41.setBackground(new java.awt.Color(111, 111, 110));
        jLabel41.setFont(new java.awt.Font("Trebuchet MS", 0, 14)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(255, 255, 255));
        jLabel41.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel41.setText("<html> <body> <h1> <center> AGT-02 TDS </center> </h1>   <h4> <em>Riofrio Santiago</em> </h4> </body></html>");
        jLabel41.setOpaque(true);
        panelCalendario.add(jLabel41);

        jLabel39.setBackground(new java.awt.Color(74, 74, 73));
        jLabel39.setFont(new java.awt.Font("Trebuchet MS", 0, 14)); // NOI18N
        jLabel39.setForeground(new java.awt.Color(255, 255, 255));
        jLabel39.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel39.setText("<html> <body> <h1> <center> LGC-02 TDS </center> </h1>   <h4> <em>Loja Jaime</em> </h4> </body></html>");
        jLabel39.setOpaque(true);
        panelCalendario.add(jLabel39);

        jLabel21.setBackground(new java.awt.Color(111, 111, 110));
        jLabel21.setFont(new java.awt.Font("Trebuchet MS", 0, 14)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("<html> <body> <h1> <center> BDS-02 TDS </center> </h1>   <h4> <em>Mejia Hector</em> </h4> </body></html>");
        jLabel21.setOpaque(true);
        panelCalendario.add(jLabel21);

        jLabel30.setBackground(new java.awt.Color(74, 74, 73));
        jLabel30.setFont(new java.awt.Font("Trebuchet MS", 0, 14)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(255, 255, 255));
        jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel30.setText("<html> <body> <h1> <center> LGC-02 TDS </center> </h1>   <h4> <em>Loja Jaime</em> </h4> </body></html>");
        jLabel30.setOpaque(true);
        panelCalendario.add(jLabel30);

        jLabel20.setBackground(new java.awt.Color(111, 111, 110));
        jLabel20.setFont(new java.awt.Font("Trebuchet MS", 0, 14)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel20.setOpaque(true);
        panelCalendario.add(jLabel20);

        jLabel17.setBackground(new java.awt.Color(74, 74, 73));
        jLabel17.setFont(new java.awt.Font("Trebuchet MS", 0, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("<html> <body> <h1> <center> BDS-02 TDS </center> </h1>   <h4> <em>Mejia Hector</em> </h4> </body></html>");
        jLabel17.setOpaque(true);
        panelCalendario.add(jLabel17);

        jLabel40.setBackground(new java.awt.Color(111, 111, 110));
        jLabel40.setFont(new java.awt.Font("Trebuchet MS", 0, 14)); // NOI18N
        jLabel40.setForeground(new java.awt.Color(255, 255, 255));
        jLabel40.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel40.setText("<html> <body> <h1> <center> ING-02 TDS </center> </h1>   <h4> <em>Rodrigez Yamila</em> </h4> </body></html>");
        jLabel40.setOpaque(true);
        panelCalendario.add(jLabel40);

        jLabel35.setBackground(new java.awt.Color(74, 74, 73));
        jLabel35.setFont(new java.awt.Font("Trebuchet MS", 0, 14)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(255, 255, 255));
        jLabel35.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel35.setText("<html> <body> <h1> <center> MDS-02 TDS </center> </h1>    <h4> <em> Calle Andres </em> </h4> </body></html>");
        jLabel35.setOpaque(true);
        panelCalendario.add(jLabel35);

        jLabel31.setBackground(new java.awt.Color(111, 111, 110));
        jLabel31.setFont(new java.awt.Font("Trebuchet MS", 0, 14)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(255, 255, 255));
        jLabel31.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel31.setText("<html> <body> <h1> <center> ING-02 TDS </center> </h1>   <h4> <em>Rodrigez Yamila</em> </h4> </body></html>");
        jLabel31.setOpaque(true);
        panelCalendario.add(jLabel31);

        jLabel18.setBackground(new java.awt.Color(74, 74, 73));
        jLabel18.setFont(new java.awt.Font("Trebuchet MS", 0, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText("<html> <body> <h1> <center> ING-02 TDS </center> </h1>   <h4> <em>Rodrigez Yamila</em> </h4> </body></html>");
        jLabel18.setOpaque(true);
        panelCalendario.add(jLabel18);

        jLabel48.setBackground(new java.awt.Color(111, 111, 110));
        jLabel48.setFont(new java.awt.Font("Trebuchet MS", 0, 14)); // NOI18N
        jLabel48.setForeground(new java.awt.Color(255, 255, 255));
        jLabel48.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel48.setOpaque(true);
        panelCalendario.add(jLabel48);

        jLabel46.setBackground(new java.awt.Color(74, 74, 73));
        jLabel46.setFont(new java.awt.Font("Trebuchet MS", 0, 14)); // NOI18N
        jLabel46.setForeground(new java.awt.Color(255, 255, 255));
        jLabel46.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel46.setText("<html> <body> <h1> <center> BDS-02 TDS </center> </h1>   <h4> <em>Mejia Hector</em> </h4> </body></html>");
        jLabel46.setOpaque(true);
        panelCalendario.add(jLabel46);

        jLabel45.setBackground(new java.awt.Color(111, 111, 110));
        jLabel45.setFont(new java.awt.Font("Trebuchet MS", 0, 14)); // NOI18N
        jLabel45.setForeground(new java.awt.Color(255, 255, 255));
        jLabel45.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel45.setText("<html> <body> <h1> <center> ING-02 TDS </center> </h1>   <h4> <em>Rodrigez Yamila</em> </h4> </body></html>");
        jLabel45.setOpaque(true);
        panelCalendario.add(jLabel45);

        jLabel44.setBackground(new java.awt.Color(74, 74, 73));
        jLabel44.setFont(new java.awt.Font("Trebuchet MS", 0, 14)); // NOI18N
        jLabel44.setForeground(new java.awt.Color(255, 255, 255));
        jLabel44.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel44.setText("<html> <body> <h1> <center> MDS-02 TDS </center> </h1>    <h4> <em> Calle Andres </em> </h4> </body></html>");
        jLabel44.setOpaque(true);
        panelCalendario.add(jLabel44);

        jLabel29.setBackground(new java.awt.Color(111, 111, 110));
        jLabel29.setFont(new java.awt.Font("Trebuchet MS", 0, 14)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(255, 255, 255));
        jLabel29.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel29.setText("<html> <body> <h1> <center> MDS-02 TDS </center> </h1>    <h4> <em> Calle Andres </em> </h4> </body></html>");
        jLabel29.setOpaque(true);
        panelCalendario.add(jLabel29);

        jLabel32.setBackground(new java.awt.Color(74, 74, 73));
        jLabel32.setFont(new java.awt.Font("Trebuchet MS", 0, 14)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(255, 255, 255));
        jLabel32.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel32.setText("<html> <body> <h1> <center> ING-02 TDS </center> </h1>   <h4> <em>Rodrigez Yamila</em> </h4> </body></html>");
        jLabel32.setOpaque(true);
        panelCalendario.add(jLabel32);

        jLabel47.setBackground(new java.awt.Color(111, 111, 110));
        jLabel47.setFont(new java.awt.Font("Trebuchet MS", 0, 14)); // NOI18N
        jLabel47.setForeground(new java.awt.Color(255, 255, 255));
        jLabel47.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel47.setOpaque(true);
        panelCalendario.add(jLabel47);

        panelHorario.add(panelCalendario, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 70, 990, 490));

        jLabel49.setFont(new java.awt.Font("Trebuchet MS", 1, 24)); // NOI18N
        jLabel49.setForeground(new java.awt.Color(255, 255, 255));
        jLabel49.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel49.setText("Sabado");
        panelHorario.add(jLabel49, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 30, 150, 40));

        jLabel51.setFont(new java.awt.Font("Trebuchet MS", 1, 24)); // NOI18N
        jLabel51.setForeground(new java.awt.Color(255, 255, 255));
        jLabel51.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel51.setText("Martes");
        panelHorario.add(jLabel51, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 30, 160, 40));

        jLabel52.setFont(new java.awt.Font("Trebuchet MS", 1, 24)); // NOI18N
        jLabel52.setForeground(new java.awt.Color(255, 255, 255));
        jLabel52.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel52.setText("Miercoles");
        panelHorario.add(jLabel52, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 30, 160, 40));

        jLabel53.setFont(new java.awt.Font("Trebuchet MS", 1, 24)); // NOI18N
        jLabel53.setForeground(new java.awt.Color(255, 255, 255));
        jLabel53.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel53.setText("Jueves");
        panelHorario.add(jLabel53, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 30, 150, 40));

        jLabel54.setFont(new java.awt.Font("Trebuchet MS", 1, 24)); // NOI18N
        jLabel54.setForeground(new java.awt.Color(255, 255, 255));
        jLabel54.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel54.setText("Viernes");
        panelHorario.add(jLabel54, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 30, 160, 40));

        jLabel55.setFont(new java.awt.Font("Trebuchet MS", 1, 24)); // NOI18N
        jLabel55.setForeground(new java.awt.Color(255, 255, 255));
        jLabel55.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel55.setText("Lunes");
        panelHorario.add(jLabel55, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 30, 160, 40));

        jLabel56.setFont(new java.awt.Font("Trebuchet MS", 1, 24)); // NOI18N
        jLabel56.setForeground(new java.awt.Color(255, 255, 255));
        jLabel56.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel56.setText("7:00");
        panelHorario.add(jLabel56, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 80, 110, 60));

        jLabel57.setFont(new java.awt.Font("Trebuchet MS", 1, 24)); // NOI18N
        jLabel57.setForeground(new java.awt.Color(255, 255, 255));
        jLabel57.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel57.setText("8:00");
        panelHorario.add(jLabel57, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 170, 110, 60));

        jLabel58.setFont(new java.awt.Font("Trebuchet MS", 1, 24)); // NOI18N
        jLabel58.setForeground(new java.awt.Color(255, 255, 255));
        jLabel58.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel58.setText("9:00");
        panelHorario.add(jLabel58, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 250, 110, 60));

        jLabel59.setFont(new java.awt.Font("Trebuchet MS", 1, 24)); // NOI18N
        jLabel59.setForeground(new java.awt.Color(255, 255, 255));
        jLabel59.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel59.setText("10:00");
        panelHorario.add(jLabel59, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 330, 110, 60));

        jLabel60.setFont(new java.awt.Font("Trebuchet MS", 1, 24)); // NOI18N
        jLabel60.setForeground(new java.awt.Color(255, 255, 255));
        jLabel60.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel60.setText("11:00");
        panelHorario.add(jLabel60, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 410, 110, 60));

        jLabel67.setFont(new java.awt.Font("Trebuchet MS", 1, 24)); // NOI18N
        jLabel67.setForeground(new java.awt.Color(255, 255, 255));
        jLabel67.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel67.setText("12:00");
        panelHorario.add(jLabel67, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 490, 110, 60));

        lblFondoHorario.setForeground(new java.awt.Color(255, 255, 255));
        lblFondoHorario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Horario/fondo2.jpg"))); // NOI18N
        lblFondoHorario.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        panelHorario.add(lblFondoHorario, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 1100, 530));

        panelCuerpo.add(panelHorario, "card4");

        panelPerfil.setBackground(new java.awt.Color(231, 235, 230));
        panelPerfil.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel6.setBackground(new java.awt.Color(40, 39, 38));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnIngresarHorario.setFont(new java.awt.Font("Trebuchet MS", 1, 24)); // NOI18N
        btnIngresarHorario.setForeground(new java.awt.Color(204, 204, 204));
        btnIngresarHorario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Perfil/icons8_Event_104px_4.png"))); // NOI18N
        btnIngresarHorario.setText("Ingresar Horario");
        btnIngresarHorario.setBorderPainted(false);
        btnIngresarHorario.setContentAreaFilled(false);
        btnIngresarHorario.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnIngresarHorario.setFocusPainted(false);
        btnIngresarHorario.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnIngresarHorario.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Perfil/icons8_Event_104px_2.png"))); // NOI18N
        btnIngresarHorario.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanel6.add(btnIngresarHorario, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 50, 280, 160));

        btnIngresarCurso.setFont(new java.awt.Font("Trebuchet MS", 1, 24)); // NOI18N
        btnIngresarCurso.setForeground(new java.awt.Color(204, 204, 204));
        btnIngresarCurso.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Perfil/icons8_Class_104px_7.png"))); // NOI18N
        btnIngresarCurso.setText("Ingresar Curso");
        btnIngresarCurso.setBorderPainted(false);
        btnIngresarCurso.setContentAreaFilled(false);
        btnIngresarCurso.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnIngresarCurso.setFocusPainted(false);
        btnIngresarCurso.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnIngresarCurso.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Perfil/icons8_Class_104px_10.png"))); // NOI18N
        btnIngresarCurso.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnIngresarCurso.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnIngresarCursoMouseClicked(evt);
            }
        });
        jPanel6.add(btnIngresarCurso, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 50, 280, 160));

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Perfil/FondoPerfil.png"))); // NOI18N
        jLabel3.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jLabel3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanel6.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 0, 500, 390));
        jPanel6.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 370, 1100, 20));

        panelPerfil.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 170, 1100, 390));

        jPanel2.setBackground(new java.awt.Color(21, 89, 110));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblCorreo.setFont(new java.awt.Font("Trebuchet MS", 0, 22)); // NOI18N
        lblCorreo.setForeground(new java.awt.Color(255, 255, 255));
        lblCorreo.setText("<html> Curso 1 <br> Curso 2 </html>");
        lblCorreo.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jPanel2.add(lblCorreo, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 100, 390, -1));

        btnAjustes.setFont(new java.awt.Font("Trebuchet MS", 1, 20)); // NOI18N
        btnAjustes.setForeground(new java.awt.Color(255, 255, 255));
        btnAjustes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Perfil/icons8_Services_52px_1.png"))); // NOI18N
        btnAjustes.setBorder(null);
        btnAjustes.setBorderPainted(false);
        btnAjustes.setContentAreaFilled(false);
        btnAjustes.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAjustes.setFocusPainted(false);
        btnAjustes.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Perfil/icons8_Services_52px_2.png"))); // NOI18N
        btnAjustes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAjustesMouseClicked(evt);
            }
        });
        jPanel2.add(btnAjustes, new org.netbeans.lib.awtextra.AbsoluteConstraints(1020, 100, 80, 70));

        lblNombreApellido.setFont(new java.awt.Font("Trebuchet MS", 0, 24)); // NOI18N
        lblNombreApellido.setForeground(new java.awt.Color(255, 255, 255));
        lblNombreApellido.setText("<html> <h1> Aqui va un nombre de usuario</h1> </html>");
        jPanel2.add(lblNombreApellido, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 20, 390, 40));

        jLabel50.setFont(new java.awt.Font("Trebuchet MS", 0, 22)); // NOI18N
        jLabel50.setForeground(new java.awt.Color(255, 255, 255));
        jLabel50.setText("Recordatorios:");
        jPanel2.add(jLabel50, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 120, 150, 40));

        lblPerfilCarrera.setFont(new java.awt.Font("Trebuchet MS", 0, 22)); // NOI18N
        lblPerfilCarrera.setForeground(new java.awt.Color(255, 255, 255));
        lblPerfilCarrera.setText("Carrera");
        jPanel2.add(lblPerfilCarrera, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 60, 390, 40));

        lblCantidadRecord.setFont(new java.awt.Font("Trebuchet MS", 0, 22)); // NOI18N
        lblCantidadRecord.setForeground(new java.awt.Color(255, 255, 255));
        jPanel2.add(lblCantidadRecord, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 120, 60, 40));

        jLabel63.setFont(new java.awt.Font("Trebuchet MS", 0, 22)); // NOI18N
        jLabel63.setForeground(new java.awt.Color(255, 255, 255));
        jLabel63.setText("Tareas:");
        jPanel2.add(jLabel63, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 80, 80, 40));

        jLabel78.setFont(new java.awt.Font("Trebuchet MS", 0, 22)); // NOI18N
        jLabel78.setForeground(new java.awt.Color(255, 255, 255));
        jLabel78.setText("Apuntes:");
        jPanel2.add(jLabel78, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 40, 100, 40));

        lblCantidadNotas.setFont(new java.awt.Font("Trebuchet MS", 0, 22)); // NOI18N
        lblCantidadNotas.setForeground(new java.awt.Color(255, 255, 255));
        lblCantidadNotas.setText("0");
        jPanel2.add(lblCantidadNotas, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 40, 60, 40));

        lblCantidadTareas.setFont(new java.awt.Font("Trebuchet MS", 0, 22)); // NOI18N
        lblCantidadTareas.setForeground(new java.awt.Color(255, 255, 255));
        jPanel2.add(lblCantidadTareas, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 80, 60, 40));

        lblNotasDistributivo.setFont(new java.awt.Font("Trebuchet MS", 0, 22)); // NOI18N
        lblNotasDistributivo.setForeground(new java.awt.Color(255, 255, 255));
        lblNotasDistributivo.setText("Notas distributivo:");
        jPanel2.add(lblNotasDistributivo, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 40, 200, 40));

        lblCantidadNotasDistributivo.setFont(new java.awt.Font("Trebuchet MS", 0, 22)); // NOI18N
        lblCantidadNotasDistributivo.setForeground(new java.awt.Color(255, 255, 255));
        lblCantidadNotasDistributivo.setText("0");
        jPanel2.add(lblCantidadNotasDistributivo, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 40, 80, 40));

        jLabel61.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel61.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Inicio/fondo4.jpg"))); // NOI18N
        jLabel61.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jPanel2.add(jLabel61, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 170));

        panelPerfil.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1100, 170));

        panelCuerpo.add(panelPerfil, "card5");

        getContentPane().add(panelCuerpo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 1100, 560));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnMinimizarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMinimizarMouseClicked
        this.setState(Frame.ICONIFIED);
    }//GEN-LAST:event_btnMinimizarMouseClicked

    private void btnCerrarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCerrarMouseClicked
        try {
            if (SystemTray.isSupported()) {
                systemtray.add(trayicon);
                this.setVisible(false);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }//GEN-LAST:event_btnCerrarMouseClicked

    private void panelHeaderMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelHeaderMouseDragged
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();

        this.setLocation(x - xMause, y - yMause);
    }//GEN-LAST:event_panelHeaderMouseDragged

    private void panelHeaderMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelHeaderMousePressed
        xMause = evt.getX();
        yMause = evt.getY();
    }//GEN-LAST:event_panelHeaderMousePressed

    private void btnEscribirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEscribirActionPerformed
        actHoraSpinners(horaTarea);
        actHoraSpinners(horaRecordatorio);

        actualizarInicioAgenda();

        limpiarAgenda();
        Similitudes.cambioPanel(panelCuerpo, panelAgenda);
        cambiarColores(lblEscribir, lblInicio, lblIngresar, lblHorario);
    }//GEN-LAST:event_btnEscribirActionPerformed

    private void btnHorarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHorarioActionPerformed
        limpiarAgenda();
        Similitudes.cambioPanel(panelCuerpo, panelHorario);
        cambiarColores(lblHorario, lblInicio, lblEscribir, lblIngresar);
    }//GEN-LAST:event_btnHorarioActionPerformed

    private void btnSalirCuentaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSalirCuentaMouseClicked
        Conexion_Consultas.cerrarConexion();
        limpiarAgenda();
        userDocente = null;
        userEstudiante = null;

        Login login = new Login();
        login.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnSalirCuentaMouseClicked

    private void btnSalirCuentaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSalirCuentaMouseEntered
        btnSalirCuenta.setForeground(new Color(99, 46, 52));
    }//GEN-LAST:event_btnSalirCuentaMouseEntered

    private void btnSalirCuentaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSalirCuentaMouseExited
        btnSalirCuenta.setForeground(new Color(237, 240, 236));
    }//GEN-LAST:event_btnSalirCuentaMouseExited

    private void btnIngresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIngresarActionPerformed
        limpiarAgenda();
        actualizarPerfil();
        Similitudes.cambioPanel(panelCuerpo, panelPerfil);
        cambiarColores(lblIngresar, lblInicio, lblEscribir, lblHorario);
    }//GEN-LAST:event_btnIngresarActionPerformed

    private void btnInicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInicioActionPerformed

        Similitudes.cambioPanel(panelCuerpo, panelInicio);
        cambiarColores(lblInicio, lblIngresar, lblEscribir, lblHorario);
        limpiarAgenda();
    }//GEN-LAST:event_btnInicioActionPerformed

    private void btnNotasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNotasActionPerformed
        limpiarAgenda();
        Similitudes.cambioPanel(panelCuerpoAgenda, panelApuntes);
        lblBarraNotas.setBackground(new Color(249, 126, 82));
        colorDefecto(lblBarraTareas, lblBarraRecordatorios);

    }//GEN-LAST:event_btnNotasActionPerformed

    private void btnNotasMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNotasMouseEntered
        btnNotas.setOpaque(true);
    }//GEN-LAST:event_btnNotasMouseEntered

    private void btnNotasMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNotasMouseExited
        btnNotas.setOpaque(false);
    }//GEN-LAST:event_btnNotasMouseExited

    private void btnTareasMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTareasMouseEntered
        btnTareas.setOpaque(true);
    }//GEN-LAST:event_btnTareasMouseEntered

    private void btnTareasMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTareasMouseExited
        btnTareas.setOpaque(false);
    }//GEN-LAST:event_btnTareasMouseExited

    private void btnRecordMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRecordMouseEntered
        btnRecord.setOpaque(true);
    }//GEN-LAST:event_btnRecordMouseEntered

    private void btnRecordMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRecordMouseExited
        btnRecord.setOpaque(false);
    }//GEN-LAST:event_btnRecordMouseExited

    private void btnTareasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTareasActionPerformed

        Similitudes.cambioPanel(panelCuerpoAgenda, panelTareas);
        limpiarAgenda();
        lblBarraTareas.setBackground(new Color(250, 193, 0));
        actHoraSpinners(horaTarea);
        colorDefecto(lblBarraNotas, lblBarraRecordatorios);
    }//GEN-LAST:event_btnTareasActionPerformed

    private void btnRecordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRecordActionPerformed
        Similitudes.cambioPanel(panelCuerpoAgenda, panelRecordatorio);
        limpiarAgenda();
        actHoraSpinners(horaRecordatorio);
        lblBarraRecordatorios.setBackground(new Color(200, 55, 58));
        colorDefecto(lblBarraNotas, lblBarraTareas);
    }//GEN-LAST:event_btnRecordActionPerformed

    private void jLabel10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel10MouseClicked
        limpiarAgenda();
        Similitudes.cambioPanel(panelCuerpoAgenda, panelInicioAgenda);

        actualizarInicioAgenda();

        colorDefecto(lblBarraNotas, lblBarraRecordatorios);
        lblBarraTareas.setBackground(new Color(237, 240, 236));
    }//GEN-LAST:event_jLabel10MouseClicked

    private void tblApuntesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblApuntesMouseClicked

        clickTabla(tblApuntes, btnModificarApunte, btnEliminarApunte);
        if (filaSelecionada >= 0) {
            Apuntes ap = buscarApunte(Integer.parseInt(tblApuntes.getValueAt(filaSelecionada, 0).toString()));

            agendaSelecionado(apunteSelec, ap.getApunte());
            fechaAgenda(fechaApunte, formatoFecha(ap.getFecha_esc_apunte()), formatoHora(ap.getFecha_esc_apunte()));
        }

    }//GEN-LAST:event_tblApuntesMouseClicked

    private void tblTareasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblTareasMouseClicked

        clickTabla(tblTareas, btnModificarTarea, btnEliminarTarea);

        if (filaSelecionada >= 0) {

            Tareas tr = buscarTarea(Integer.parseInt(tblTareas.getValueAt(filaSelecionada, 0).toString()));

            System.out.println("Id de mi tarea " + tblTareas.getValueAt(filaSelecionada, 0).toString() + "Posicion " + buscarTarea(Integer.parseInt(tblTareas.getValueAt(filaSelecionada, 0).toString())));
            boolean com = tr.isTarea_completa();

            if (com) {
                btnTareaTerminado.setBackground(new Color(21, 89, 110));
                btnTareaTerminado.setText("Completa");
            } else {
                btnTareaTerminado.setBackground(new Color(139, 0, 0));
                btnTareaTerminado.setText("Pendiente");
            }
            btnTareaTerminado.setVisible(true);

            agendaSelecionado(tareaSelec, tr.getTarea());
            fechaAgenda(fechaTarea, formatoFecha(tr.getFecha_esc_tarea()), formatoHora(tr.getFecha_esc_tarea()));

            fechaAgenda(fechaTareaCompletar, formatoFecha(tr.getFecha_presentacion()), formatoHora(tr.getFecha_presentacion()));
        }

        btnTareaTerminado.setEnabled(true);
    }//GEN-LAST:event_tblTareasMouseClicked

    private void tblRecordatoriosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblRecordatoriosMouseClicked
        clickTabla(tblRecordatorios, btnModificarRecordatorio, btnEliminarRecordatorio);
        if (filaSelecionada >= 0) {
            Recordatorios rec = buscarRecord(Integer.parseInt(tblRecordatorios.getValueAt(filaSelecionada, 0).toString()));

            agendaSelecionado(recordatorioSelec, rec.getRecordatorio());
            fechaAgenda(fechaRecordatorio, formatoFecha(rec.getFecha_esc_record()), formatoHora(rec.getFecha_esc_record()));

            fechaAgenda(fechaRecordatorioTermina, formatoFecha(rec.getFecha_noti()), formatoHora(rec.getFecha_noti()));
        }
    }//GEN-LAST:event_tblRecordatoriosMouseClicked

    private void btnGuardarApunteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarApunteMouseClicked
        boolean guardar = true;
        int id_apunte = 0;

        id_apunte++;

        String apunte = txtApunteIngresar.getText();
        if (apunte.length() == 0 || apunte.trim().equals("")) {
            guardar = false;
        }

        Date fecha = fechaHoraActual();

        if (guardar) {

            if (!editando) {

                Apuntes apun = new Apuntes();
                apun.setId_apunte(id_apunte);
                apun.setApunte(apunte);
                apun.setFecha_esc_apunte(fecha);
                apun.setFk_usuario(cedula_usuario);

                SQLAgenda.insertarApunte(apun);

                cantApuntes++;
            }

            if (editando) {
                //modelTblApuntes.addRow(apt);
                Apuntes apun = new Apuntes();
                apun.setId_apunte(Integer.parseInt(tblApuntes.getValueAt(filaSelecionada, 0).toString()));
                apun.setApunte(apunte);
                apun.setFecha_esc_apunte(fecha);

                SQLAgenda.editarApunte(apun);
                //Modificamos nuestro array 

                editando = false;
            }

            actualizarTblApuntes();

            limpiarAgenda();
        }


    }//GEN-LAST:event_btnGuardarApunteMouseClicked

    private void btnModificarApunteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnModificarApunteMouseClicked

        txtApunteIngresar.setText(tblApuntes.getValueAt(filaSelecionada, 1).toString());

        editando = true;

        Similitudes.btnsModoInactivo(btnModificarApunte, btnEliminarApunte);
    }//GEN-LAST:event_btnModificarApunteMouseClicked

    private void btnEliminarApunteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarApunteMouseClicked
        if (filaSelecionada >= 0) {

            /*System.out.println("Todos los objetos de mi array antes de eliminar");
            for (int i = 0; i < apuntesUsuario.size(); i++) {
                System.out.println("- "+apuntesUsuario.get(i).getApunte());
            }
            System.out.println("Tamano de mi array antes de "+apuntesUsuario.size());*/
            //Aqui elmino mi apunte  
            //apuntesUsuario.remove(posApunte(Integer.parseInt(tblApuntes.getValueAt(filaSelecionada, 0).toString())));
            /*
            for (int i = 0; i < apuntesUsuario.size(); i++) {
                System.out.println("* "+apuntesUsuario.get(i).getApunte());
            }*/
            SQLAgenda.eliminarApunte(Integer.parseInt(tblApuntes.getValueAt(filaSelecionada, 0).toString()));
            modelTblApuntes.removeRow(filaSelecionada);

            Similitudes.btnsModoInactivo(btnModificarApunte, btnEliminarApunte);

            cantApuntes--;
        }
    }//GEN-LAST:event_btnEliminarApunteMouseClicked

    private void btnGuardarTareaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarTareaMouseClicked
        boolean guardar = true;
        int id_tarea = 0;

        String tarea = txtIngresarTareas.getText();
        if (tarea.length() == 0 || tarea.trim().equals("")) {
            guardar = false;
        }

        Date fecha = fechaHoraActual();

        String fec = "";
        String hor = "";
        String fechaPresentar = "";

        DateFormat FechaForm = new SimpleDateFormat("dd/MM/yyyy");
        try {
            fec = FechaForm.format(dateChooserTarea.getDate());
        } catch (Exception e) {
            System.out.println("Se me murio en fecha  ");
            guardar = false;
        }

        DateFormat horaFechaForm = new SimpleDateFormat("HH:mm:ss");
        try {
            hor = horaFechaForm.format(horaTarea.getValue());
        } catch (Exception e) {
            System.out.println("Se me murio en hora");
            guardar = false;
        }

        fechaPresentar = fec + " " + hor;

        DateFormat completo = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        Date fePre = fechaHoraActual();

        try {
            fePre = completo.parse(fechaPresentar);
        } catch (ParseException ex) {
            System.out.println("Se me murio y no transformo a fecha al guardar tarea");
        }

        if (guardar) {

            if (!editando) {

                Tareas tr = new Tareas();
                tr.setId_tarea(id_tarea);
                tr.setTarea(tarea);
                tr.setFecha_esc_tarea(fecha);
                tr.setFecha_presentacion(fePre);
                tr.setFk_usuario(cedula_usuario);

                SQLAgenda.insertarTareas(tr);

                notificarTareas();
            }

            if (editando) {

                Tareas tr = new Tareas();
                tr.setId_tarea(Integer.parseInt(tblTareas.getValueAt(filaSelecionada, 0).toString()));
                tr.setTarea(tarea);
                tr.setFecha_esc_tarea(fecha);
                tr.setFecha_presentacion(fePre);
                tr.setFk_usuario(cedula_usuario);

                SQLAgenda.editarTareas(tr);

                editando = false;
            }

            actualizarTblTareas();

            notificarTareas();
            limpiarAgenda();
        }
    }//GEN-LAST:event_btnGuardarTareaMouseClicked

    private void btnModificarTareaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnModificarTareaMouseClicked
        txtIngresarTareas.setText(tblTareas.getValueAt(filaSelecionada, 1).toString());

        editando = true;

        Similitudes.btnsModoInactivo(btnModificarTarea, btnEliminarTarea);
        btnTareaTerminado.setBackground(new Color(118, 125, 127));
        btnTareaTerminado.setEnabled(false);
    }//GEN-LAST:event_btnModificarTareaMouseClicked

    private void btnTareaTerminadoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTareaTerminadoMouseClicked

        if (btnTareaTerminado.isEnabled()) {
            Tareas tr = buscarTarea(Integer.parseInt(tblTareas.getValueAt(filaSelecionada, 0).toString()));
            boolean com = tr.isTarea_completa();

            System.out.println("La tarea estaba " + com);
            if (com) {
                SQLAgenda.estadoTareas(Integer.parseInt(tblTareas.getValueAt(filaSelecionada, 0).toString()), false);
                tr.setTarea_completa(false);
            } else {
                SQLAgenda.estadoTareas(Integer.parseInt(tblTareas.getValueAt(filaSelecionada, 0).toString()), true);
                tr.setTarea_completa(true);
            }

            //Eliminamos de nuestro array 
            //tareasUsuario.remove(posTarea(Integer.parseInt(tblTareas.getValueAt(filaSelecionada, 0).toString())));
            Similitudes.btnsModoInactivo(btnEliminarApunte, btnModificarApunte);
            btnTareaTerminado.setBackground(new Color(118, 125, 127));
            btnTareaTerminado.setEnabled(false);
        }


    }//GEN-LAST:event_btnTareaTerminadoMouseClicked

    private void btnEliminarTareaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarTareaMouseClicked
        if (filaSelecionada >= 0) {
            SQLAgenda.eliminarTarea(Integer.parseInt(tblTareas.getValueAt(filaSelecionada, 0).toString()));

            modelTblTareas.removeRow(filaSelecionada);

            Similitudes.btnsModoInactivo(btnModificarTarea, btnEliminarTarea);
            btnTareaTerminado.setBackground(new Color(118, 125, 127));
            btnTareaTerminado.setEnabled(false);
        }
    }//GEN-LAST:event_btnEliminarTareaMouseClicked

    private void btnGuardarRecordatorioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarRecordatorioMouseClicked
        boolean guardar = true;
        int id_record = 0;

        id_record++;

        String recordatorio = txtIngresarRecordatorio.getText();
        if (recordatorio.length() == 0 || recordatorio.trim().equals("")) {
            guardar = false;
        }

        Date fecha = fechaHoraActual();

        String fec = "";
        String hor = "";
        String fechaRecordar = "";

        DateFormat FechaForm = new SimpleDateFormat("dd/MM/yyyy");
        try {
            fec = FechaForm.format(dateChooserRecordatorio.getDate());
        } catch (Exception e) {
            System.out.println("Se me murio en fecha  ");
            guardar = false;
        }

        DateFormat horaFechaForm = new SimpleDateFormat("HH:mm:ss");
        try {
            hor = horaFechaForm.format(horaRecordatorio.getValue());
        } catch (Exception e) {
            System.out.println("Se me murio en hora");
            guardar = false;
        }

        fechaRecordar = fec + " " + hor;

        DateFormat completo = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        //Si no se tarnformo bien nos guardarara la fecha actual 
        Date feReco = fechaHoraActual();

        try {
            feReco = completo.parse(fechaRecordar);
        } catch (ParseException ex) {
            System.out.println("Se me murio y no transformo a fecha al guardar recordatorio.");
        }

        if (guardar) {

            if (!editando) {
                Recordatorios re = new Recordatorios();

                re.setId_recordatorio(id_record);
                re.setRecordatorio(recordatorio);
                re.setFecha_esc_record(fecha);
                re.setFecha_noti(feReco);
                re.setFk_usuario(cedula_usuario);

                SQLAgenda.insertarRecordatorio(re);

                recordatoriosUsuario = SQLAgenda.cargarRecordatoriosUsuarios(cedula_usuario);
            }

            if (editando) {

                Recordatorios re = new Recordatorios();

                re.setId_recordatorio(Integer.parseInt(tblRecordatorios.getValueAt(filaSelecionada, 0).toString()));
                re.setRecordatorio(recordatorio);
                re.setFecha_esc_record(fecha);
                re.setFecha_noti(feReco);
                re.setFk_usuario(cedula_usuario);

                SQLAgenda.editarRecordatorio(re);
                recordatoriosUsuario = SQLAgenda.cargarRecordatoriosUsuarios(cedula_usuario);

                editando = false;
            }

            actualizarTblRecordatorios();
            notificarRecordatorios();

            limpiarAgenda();
        }
    }//GEN-LAST:event_btnGuardarRecordatorioMouseClicked

    private void btnModificarRecordatorioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnModificarRecordatorioMouseClicked
        txtIngresarRecordatorio.setText(tblRecordatorios.getValueAt(filaSelecionada, 1).toString());

        editando = true;

        Similitudes.btnsModoInactivo(btnModificarRecordatorio, btnEliminarRecordatorio);
    }//GEN-LAST:event_btnModificarRecordatorioMouseClicked

    private void btnEliminarRecordatorioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarRecordatorioMouseClicked
        if (filaSelecionada >= 0) {
            //Eliminamos de nuestro array  
            SQLAgenda.eliminarRecordatorio(Integer.parseInt(tblRecordatorios.getValueAt(filaSelecionada, 0).toString()));
            modelTblRecord.removeRow(filaSelecionada);
            Similitudes.btnsModoInactivo(btnModificarRecordatorio, btnEliminarRecordatorio);
            cantRecordatorios--;
        }
    }//GEN-LAST:event_btnEliminarRecordatorioMouseClicked

    private void btnAjustesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAjustesMouseClicked
        ConfiguracionUsuario cof = new ConfiguracionUsuario();
        if (userDocente != null) {
            cof.setDocente(userDocente);
        } else if (userEstudiante != null) {
            cof.setEstudinate(userEstudiante);
        } else {
            System.out.println("No tenemos usuarios en app.");
        }

        cof.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnAjustesMouseClicked

    private void txtIngresarTareasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtIngresarTareasMouseClicked
        btnTareaTerminado.setVisible(false);
    }//GEN-LAST:event_txtIngresarTareasMouseClicked

    private void popupAbrirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popupAbrirActionPerformed
        systemtray.remove(trayicon);
        this.setVisible(true);
    }//GEN-LAST:event_popupAbrirActionPerformed

    private void popupSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popupSalirActionPerformed
        System.exit(0);
    }//GEN-LAST:event_popupSalirActionPerformed

    private void btnIngresarCursoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnIngresarCursoMouseClicked
        if (btnIngresarCurso.getText().equalsIgnoreCase("Ingresar Curso")) {
            IngresarParalelo cursos = new IngresarParalelo();
            cursos.setEstudiante(userEstudiante); 
            cursos.setVisible(true);
            this.dispose();
        }

    }//GEN-LAST:event_btnIngresarCursoMouseClicked

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
            java.util.logging.Logger.getLogger(App.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(App.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(App.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(App.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new App().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Kayrana;
    private javax.swing.JLabel apunteSelec;
    private javax.swing.JButton btnAjustes;
    private javax.swing.JButton btnCerrar;
    private javax.swing.JButton btnEliminarApunte;
    private javax.swing.JButton btnEliminarRecordatorio;
    private javax.swing.JButton btnEliminarTarea;
    private javax.swing.JButton btnEscribir;
    private javax.swing.JButton btnGuardarApunte;
    private javax.swing.JButton btnGuardarRecordatorio;
    private javax.swing.JButton btnGuardarTarea;
    private javax.swing.JButton btnHorario;
    private javax.swing.JButton btnIngresar;
    private javax.swing.JButton btnIngresarCurso;
    private javax.swing.JButton btnIngresarHorario;
    private javax.swing.JButton btnInicio;
    private javax.swing.JButton btnMinimizar;
    private javax.swing.JButton btnModificarApunte;
    private javax.swing.JButton btnModificarRecordatorio;
    private javax.swing.JButton btnModificarTarea;
    private javax.swing.JButton btnNotas;
    private javax.swing.JButton btnRecord;
    private javax.swing.JButton btnSalirCuenta;
    private javax.swing.JButton btnTareaTerminado;
    private javax.swing.JButton btnTareas;
    private javax.swing.ButtonGroup buttonGroup1;
    private com.toedter.calendar.JDateChooser dateChooserRecordatorio;
    private com.toedter.calendar.JDateChooser dateChooserTarea;
    private javax.swing.JLabel fechaApunte;
    private javax.swing.JLabel fechaRecordatorio;
    private javax.swing.JLabel fechaRecordatorioTermina;
    private javax.swing.JLabel fechaTarea;
    private javax.swing.JLabel fechaTareaCompletar;
    private javax.swing.JLabel fondoMenuEscribir;
    private javax.swing.JSpinner horaRecordatorio;
    private javax.swing.JSpinner horaTarea;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel76;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblApuntes;
    private javax.swing.JLabel lblApuntesMostrar;
    private javax.swing.JLabel lblBarraNotas;
    private javax.swing.JLabel lblBarraRecordatorios;
    private javax.swing.JLabel lblBarraTareas;
    private javax.swing.JLabel lblCantidadNotas;
    private javax.swing.JLabel lblCantidadNotasDistributivo;
    private javax.swing.JLabel lblCantidadRecord;
    private javax.swing.JLabel lblCantidadTareas;
    private javax.swing.JLabel lblCorreo;
    private javax.swing.JLabel lblEscribir;
    private javax.swing.JLabel lblFondoHorario;
    private javax.swing.JLabel lblFondoHorario1;
    private javax.swing.JLabel lblFondoInicio;
    private javax.swing.JLabel lblHorario;
    private javax.swing.JLabel lblInformacion;
    private javax.swing.JLabel lblIngresar;
    private javax.swing.JLabel lblInicio;
    private javax.swing.JLabel lblNombreApellido;
    private javax.swing.JLabel lblNombreDesarrolladores;
    private javax.swing.JLabel lblNombreUsuario;
    private javax.swing.JLabel lblNotasColor;
    private javax.swing.JLabel lblNotasDistributivo;
    private javax.swing.JLabel lblPerfilCarrera;
    private javax.swing.JLabel lblRecordatorioColor;
    private javax.swing.JLabel lblRecordatorios;
    private javax.swing.JLabel lblRecordatoriosMostrar;
    private javax.swing.JLabel lblTareas;
    private javax.swing.JLabel lblTareasColor;
    private javax.swing.JLabel lblTareasMostrar;
    private javax.swing.JPanel panelAgenda;
    private javax.swing.JPanel panelApuntes;
    private javax.swing.JPanel panelCalendario;
    private javax.swing.JPanel panelCuerpo;
    private javax.swing.JPanel panelCuerpoAgenda;
    private javax.swing.JPanel panelHeader;
    private javax.swing.JPanel panelHorario;
    private javax.swing.JPanel panelInicio;
    private javax.swing.JPanel panelInicioAgenda;
    private javax.swing.JPanel panelMenu;
    private javax.swing.JPanel panelMenuNotas;
    private javax.swing.JPanel panelMenuRecordatorio;
    private javax.swing.JPanel panelMenuTareas;
    private javax.swing.JPanel panelPerfil;
    private javax.swing.JPanel panelRecordatorio;
    private javax.swing.JPanel panelTareas;
    private java.awt.MenuItem popupAbrir;
    private java.awt.PopupMenu popupMenuOculto;
    private java.awt.MenuItem popupSalir;
    private javax.swing.JLabel recordatorioSelec;
    private javax.swing.JLabel tareaSelec;
    private javax.swing.JTable tblApuntes;
    private javax.swing.JTable tblRecordatorios;
    private javax.swing.JTable tblTareas;
    private javax.swing.JTextArea txtApunteIngresar;
    private javax.swing.JTextArea txtIngresarRecordatorio;
    private javax.swing.JTextArea txtIngresarTareas;
    // End of variables declaration//GEN-END:variables
}
