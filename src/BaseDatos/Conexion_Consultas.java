/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BaseDatos;

import Clases.Docente;
import Clases.Estudiante;
import Clases.Jornada;
import Clases.Pregunta_Seguridad;
import Clases.Usuario;
import Interfaz.LoginRegistro.Login;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author AndresSebastian
 */
public class Conexion_Consultas {

    static Connection conexion = null;
    static java.sql.Statement sentencia;
    static ResultSet resultado;
    
    private static String sql;

    public static void conectar() {

        //String url = "jdbc:postgresql://localhost:5432/Kayrana";
        //String url = "jdbc:postgresql://localhost:5433/ProyectoKayrana";
        //String url = "jdbc:postgresql://localhost:5432/ProyectoKayrana";
        //String url = "jdbc:postgresql://localhost:5432/ProyectoKay";
        
        //String url = "jdbc:postgresql://localhost:5433/ProyectoKayranaBDF1";
        
        //String url = "jdbc:postgresql://localhost:5433/KayranaDB"; 
        
        String url = "jdbc:postgresql://localhost:5432/KayranaDB"; 
        
        String usuario = "postgres";

        //String contrasena = "qwerty79";
        String contrasena = "Holapostgres";

        try {
            Class.forName("org.postgresql.Driver");
            conexion = DriverManager.getConnection(url, usuario, contrasena);
            sentencia = conexion.createStatement();

            System.out.println("Nos conectamos");

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("ERROR DE CONEXION " + e.getMessage());

        }
    }

    public static void cerrarConexion() {

        try {
            conexion.close();
            sentencia.close();
            resultado.close();

            System.out.println("Se a cerrado la conexion");
        } catch (SQLException e) {
            System.out.println("Erro al cerrar conexion: " + e.getMessage());
        }

    }

    public static ArrayList<Pregunta_Seguridad> cargar_preguntas() {

        ArrayList<Pregunta_Seguridad> misPreguntas = new ArrayList<>();
        String q = "SELECT * FROM \"Preguntas_Seguridad\"";

        try {
            resultado = sentencia.executeQuery(q);
        } catch (SQLException e) {
            System.out.println("ERROR EN LA CONSULTA");
        }

        try {
            while (resultado.next()) {
                misPreguntas.add(new Pregunta_Seguridad(resultado.getInt("id_preg_seg"), resultado.getString("pregunta")));

            }
        } catch (SQLException e) {

        }
        return misPreguntas;
    }

    public static void insertar_usuario(Usuario nuevo) {

        String sql_usuario = null, sql_tipo = null;

        SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");

        if (nuevo instanceof Docente) {
            sql_usuario = "INSERT INTO public.\"Usuario\"(\n"
                    + "	cedula_usuario, nick_usuario, clave_usuario,"
                    + " correo_usuario, sexo_usuario, resp_seg1, "
                    + "resp_seg2, fech_nac_usuario, nombre, apellido, fk_tipo_usuario,"
                    + " fk_preg_seg1, fk_preg_seg2, cuenta_activa)"
                    + " VALUES ('" + nuevo.getCedulaUsuario() + "','"
                    + nuevo.getNickUsuario() + "','"
                    + nuevo.getContrasenaUsuario() + "','" + nuevo.getCorreoUsuario()
                    + "','" + String.valueOf(nuevo.getSexoUsuario())
                    + "','" + nuevo.getRespSeg1() + "','" + nuevo.getRespSeg2()
                    + "','" + formateador.format(nuevo.getFecNacUsuario())
                    + "','" + nuevo.getNombreUsuario() + "','" + nuevo.getApellidoUsuario()
                    + "'," + 1 + "," + nuevo.getIdPregSeg1()
                    + "," + nuevo.getIdPregSeg1() + "," + nuevo.isCuentActiva() + ")";

            sql_tipo = "INSERT INTO \"Docente\"(\"titulo_docente\",\"fk_usuario\")  VALUES ('" + ((Docente) nuevo).getTituloDocente()
                    + "','" + nuevo.getCedulaUsuario() + "'" + ")";

        } else if (nuevo instanceof Estudiante) {
            sql_usuario = "INSERT INTO public.\"Usuario\"(\n"
                    + "	cedula_usuario, nick_usuario, clave_usuario,"
                    + " correo_usuario, sexo_usuario, resp_seg1, "
                    + "resp_seg2, fech_nac_usuario, nombre, apellido, fk_tipo_usuario,"
                    + " fk_preg_seg1, fk_preg_seg2, cuenta_activa)"
                    + " VALUES ('" + nuevo.getCedulaUsuario() + "','" + nuevo.getNickUsuario() + "','"
                    + nuevo.getContrasenaUsuario() + "','" + nuevo.getCorreoUsuario() + "','" + String.valueOf(nuevo.getSexoUsuario())
                    + "','" + nuevo.getRespSeg1() + "','" + nuevo.getRespSeg2() + "','" + formateador.format(nuevo.getFecNacUsuario())
                    + "','" + nuevo.getNombreUsuario() + "','" + nuevo.getApellidoUsuario() + "'," + 2 + "," + nuevo.getIdPregSeg1()
                    + "," + nuevo.getIdPregSeg1() + "," + nuevo.isCuentActiva() + ")";

            sql_tipo = "INSERT INTO \"Estudiante\"(\"colegio_estudiante\",\"fk_usuario\") VALUES ('"
                    + ((Estudiante) nuevo).getColegioEstudiante() + "','" + nuevo.getCedulaUsuario() + "'" + ")";

        }

        ejecutar(sql_usuario);

        ejecutar(sql_tipo);
    }

    //Hasta aqui copie


    public static ArrayList<Jornada> cargarJornadas() {
        ArrayList<Jornada> jornadaDB = new ArrayList();

        sql = "SELECT * FROM public.\"Jornada\";";

        try {
            resultado = sentencia.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println("ERROR EN LA CONSULTA");
        } catch (NullPointerException e) {
            System.out.println("No tiene datos " + e.getMessage());
        }

        try {
            while (resultado.next()) {
                Jornada jord = new Jornada();

                jord.setId_jornada(resultado.getInt("id_jornada"));
                jord.setJornada(resultado.getString("jornada"));

                jornadaDB.add(jord);
            }
        } catch (SQLException e) {
            System.out.println("Error al traeer jornadas: " + e.getMessage());
        } catch (NullPointerException e) {
            System.out.println("No tiene datos " + e.getMessage());
        }

        return jornadaDB;

    }

    public static ArrayList<String> acceso() {

        ArrayList<String> cedulas = new ArrayList<>();
        String query = "SELECT * FROM public.\"Docentes_Instituto\" WHERE docentes_insti_elim = false;";

        try {
            resultado = sentencia.executeQuery(query);
        } catch (SQLException e) {

            System.out.println("ERROR EN LA CONSULTA " + e.getMessage());
        }

        try {

            while (resultado.next()) {

                cedulas.add(resultado.getString("cedula_docente"));

            }
        } catch (Exception e) {

        }

        return cedulas;

    }

    public static String ingreso(String cedula, String contrasena) {

        //Variables en donde guardaremos los resultados obtenidos  
        String cedula_user = "", nick_user = "", clave_user = "", correo_user = "", resp_seg1 = "", resp_seg2 = "",
                nombre_user = "", apellido_user = "", fecha_nac_user = "", titulo_user = "", colegio_user = "";
        String sexo_user = "";
        int id_estudiante = 0, id_docente = 0, fk_tipo_user = 0, fk_preg_seg1 = 0, fk_preg_seg2 = 0;
        boolean cuenta_activida = true;

        String q1 = "SELECT * FROM \"Usuario\" WHERE \"cedula_usuario\"='" + cedula + "';";

        Docente miDocente = null;
        Estudiante miEstudiante = null;

        String buscarDocente = "SELECT * FROM public.\"Docente\" WHERE \"fk_usuario\"='" + cedula + "';";

        String buscarEstudiante = "SELECT * FROM public.\"Estudiante\" WHERE \"fk_usuario\"='" + cedula + "';";

        Date fecha_nac = new Date();
        try {
            resultado = sentencia.executeQuery(q1);
        } catch (SQLException e) {
            System.out.println("ERROR EN LA CONSULTA " + e.getMessage());
        } catch (NullPointerException er) {
            System.out.println("No se encontro nada de nada " + er);
        }
        try {
            while (resultado.next()) {

                cedula_user = resultado.getString("cedula_usuario");
                nick_user = resultado.getString("nick_usuario");
                clave_user = resultado.getString("clave_usuario");
                correo_user = resultado.getString("correo_usuario");
                sexo_user = resultado.getString("sexo_usuario");
                resp_seg1 = resultado.getString("resp_seg1");
                resp_seg2 = resultado.getString("resp_seg2");
                nombre_user = resultado.getString("nombre");
                apellido_user = resultado.getString("apellido");
                fk_tipo_user = resultado.getInt("fk_tipo_usuario");
                fk_preg_seg1 = resultado.getInt("fk_preg_seg1");
                fk_preg_seg2 = resultado.getInt("fk_preg_seg2");
                cuenta_activida = resultado.getBoolean("cuenta_activa");
                fecha_nac_user = resultado.getString("fech_nac_usuario");

                fecha_nac = resultado.getDate("fech_nac_usuario");

                //Solo para pruebas 
                System.out.println("Esta es la fecha de nacimiento con modo date  " + fecha_nac);

                DateFormat FechaForm = new SimpleDateFormat("dd/MM/yyyy");
                try {
                     System.out.println(FechaForm.format(fecha_nac));
                } catch (Exception e) {
                    System.out.println("Se me murio en fecha  ");
                    
                }

                DateFormat horaFechaForm = new SimpleDateFormat("HH:mm:ss");
                try {
                    System.out.println(horaFechaForm.format(fecha_nac));
                } catch (Exception e) {
                    System.out.println("sE me murio en hora");
                   
                }
                
                System.out.println("Terminamos de consyltar la fecha de nacimiento etc");
            }
        } catch (Exception e) {
            System.out.println("No encontramos ningun usuario con estos datos: " + e);
        }

        try {
            resultado = sentencia.executeQuery(buscarDocente);
        } catch (Exception e) {
            System.out.println("Error al buscar docente: " + e);
        }

        try {
            while (resultado.next()) {

                id_docente = resultado.getInt("id_docente");
                titulo_user = resultado.getString("titulo_docente");

            }
        } catch (Exception e) {
            System.out.println("No encontramos ningun usuario con estos datos: " + e);
        }

        try {
            resultado = sentencia.executeQuery(buscarEstudiante);
        } catch (Exception e) {
            System.out.println("Error al buscar docente: " + e);
        }

        try {
            while (resultado.next()) {
                id_estudiante = resultado.getInt("id_estudiante");
                colegio_user = resultado.getString("colegio_estudiante");

            }
        } catch (Exception e) {
            System.out.println("No encontramos ningun usuario con estos datos: " + e);
        }

        Login lg = new Login();
        
        if (fk_tipo_user == 1) {
            miDocente = new Docente(id_docente, titulo_user, cedula_user, nick_user, clave_user, correo_user, sexo_user.charAt(0), resp_seg1, resp_seg2, fecha_nac, nombre_user, apellido_user, fk_tipo_user, fk_preg_seg1, fk_preg_seg2, cuenta_activida);

            lg.setUserDocente(miDocente);
              
        } else if (fk_tipo_user == 2) {
            miEstudiante = new Estudiante(id_estudiante, colegio_user, cedula_user, nick_user, clave_user, correo_user, sexo_user.charAt(0), resp_seg1, resp_seg2, fecha_nac, nombre_user, apellido_user, fk_tipo_user, fk_preg_seg1, fk_preg_seg2, cuenta_activida);;

            lg.setUserEstudiante(miEstudiante);
        }

        return nick_user;
    }

    public static void ejecutar(String q) {

        try {
            sentencia.executeUpdate(q);
        } catch (Exception e) {
            System.out.println("Error al ejecutar " + e.getMessage());
        }
    }

    public static void actualizarBD() {
        try {
            conexion.commit();
        } catch (SQLException e) {
            System.out.println("Error:  " + e.getMessage());
        }
    }

}
