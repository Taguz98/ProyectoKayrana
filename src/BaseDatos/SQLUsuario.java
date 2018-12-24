/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BaseDatos;

import static BaseDatos.Conexion_Consultas.resultado;
import static BaseDatos.Conexion_Consultas.sentencia;
import Clases.Docente;
import Clases.Estudiante;
import Clases.Validaciones;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Johnny
 */
public class SQLUsuario {

    private static String sql;
    private static String sql2;

    static DateFormat fechaCorta = new SimpleDateFormat("dd/MM/yyyy");

    public static void editarDocente(Docente user) {
        sql = "UPDATE public.\"Usuario\"\n"
                + "SET  nick_usuario='" + user.getNickUsuario()
                + "', correo_usuario='" + user.getCorreoUsuario()
                + "', sexo_usuario='" + user.getSexoUsuario()
                + "', nombre='" + user.getNombreUsuario()
                + "', apellido='" + user.getApellidoUsuario()
                + "', fech_nac_usuario='" + fechaCorta.format(user.getFecNacUsuario()) + "'\n"
                + " WHERE cedula_usuario = '" + user.getCedulaUsuario() + "';";

        sql2 = "UPDATE public.\"Docente\"\n"
                + "SET  titulo_docente='" + user.getTituloDocente() + "'\n"
                + " WHERE fk_usuario = '" + user.getCedulaUsuario() + "';";

        Conexion_Consultas.ejecutar(sql);
        Conexion_Consultas.ejecutar(sql2);
    }

    public static void editarEstudiante(Estudiante user) {
        sql = "UPDATE public.\"Usuario\"\n"
                + "	SET  nick_usuario='" + user.getNickUsuario()
                + "', correo_usuario='" + user.getCorreoUsuario()
                + "', sexo_usuario='" + user.getSexoUsuario()
                + "', nombre='" + user.getNombreUsuario()
                + "', apellido='" + user.getApellidoUsuario()
                + "', fech_nac_usuario='" + fechaCorta.format(user.getFecNacUsuario()) + "'\n"
                + " WHERE cedula_usuario = '" + user.getCedulaUsuario() + "';";

        sql2 = "UPDATE public.\"Estudiante\"\n"
                + "SET  colegio_estudiante='" + user.getColegioEstudiante() + "'\n"
                + " WHERE fk_usuario = '" + user.getCedulaUsuario() + "';";

        Conexion_Consultas.ejecutar(sql);
        Conexion_Consultas.ejecutar(sql2);
    }

    public static void editarContrasena(String ci_user, String nuevaClave) {
        sql = "UPDATE public.\"Usuario\"\n"
                + " SET  clave_usuario='" + nuevaClave + "'\n"
                + " WHERE cedula_usuario = '" + ci_user + "';";
    }

    public static void eliminarUsuario(String ci_user) {
        sql = "DELETE FROM public.\"Usuario\"\n"
                + "WHERE cedula_usuario = '" + ci_user + "';";

        Conexion_Consultas.ejecutar(sql);
    }

    public static ArrayList< ArrayList<String>> consultarUsuarios(String modo) {

        Validaciones val = new Validaciones();

        ArrayList< ArrayList<String>> valores = new ArrayList();

        //Variables en donde guardaremos los resultados obtenidos  
        String cedula;
        String nickUser;
        String claveUser;
        String edad = "1";
        String correoUser;
        String sexoUser;
        String nombre;
        String apellido;
        String tipoUsuario;
        String cuentaAct = "No";

        String atributo;
        boolean cuentaActiva;

        int fkTipoUser;

        sql = "";

        if (modo.equalsIgnoreCase("todos")) {
            sql = "SELECT * \n"
                    + "	FROM public.\"Usuario\", public.\"Estudiante\", public.\"Docente\" WHERE cedula_usuario =  \"Estudiante\".fk_usuario OR cedula_usuario = \"Docente\".fk_usuario;";

            //sql = "SELECT * \n"
            //      + "	FROM public.\"Usuario\";";
        }

        if (val.esCedula(modo)) {
            sql = "SELECT *\n"
                    + "	FROM public.\"Usuario\", public.\"Estudiante\", public.\"Docente\"\n"
                    + "	WHERE cedula_usuario = '" + modo + "';";
        }

        if (modo.equalsIgnoreCase("Estudiantes")) {
            sql = "SELECT *\n"
                    + "	FROM public.\"Usuario\", public.\"Estudiante\"\n"
                    + "	WHERE cedula_usuario = \"Estudiante\".fk_usuario;";
            
            System.out.println("Entramos a modo estudiante");
        }

        if (modo.equalsIgnoreCase("docentes")) {
            sql = "SELECT *\n"
                    + "	FROM public.\"Usuario\", public.\"Docente\"\n"
                    + "	WHERE cedula_usuario = \"Docente\".fk_usuario;";
            System.out.println("Entramos a modo docente");
        }

        Date fecha_nac = new Date();

        try {
            resultado = sentencia.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println("ERROR EN LA CONSULTA de un usuario " + e.getMessage());
        } catch (NullPointerException er) {
            System.out.println("No se encontro nada de nada " + er);
        }
        try {
            while (resultado.next()) {

                ArrayList<String> datos = new ArrayList();

                cedula = resultado.getString("cedula_usuario");
                nombre = resultado.getString("nombre");
                apellido = resultado.getString("apellido");
                sexoUser = resultado.getString("sexo_usuario");
                correoUser = resultado.getString("correo_usuario");
                nickUser = resultado.getString("nick_usuario");
                claveUser = resultado.getString("clave_usuario");

                fecha_nac = resultado.getDate("fech_nac_usuario");

                DateFormat FechaForm = new SimpleDateFormat("dd/MM/yyyy");

                try {
                    edad = FechaForm.format(fecha_nac);
                } catch (Exception e) {
                    System.out.println("Se me murio en fecha  ");
                }

                cuentaActiva = resultado.getBoolean("cuenta_activa");
                if (cuentaActiva) {
                    cuentaAct = "Si";
                }

                fkTipoUser = resultado.getInt("fk_tipo_usuario");

                if (fkTipoUser == 1) {
                    tipoUsuario = "Docente";

                    atributo = resultado.getString("titulo_docente");
                } else {
                    tipoUsuario = "Estudiante";

                    atributo = resultado.getString("colegio_estudiante");
                }

                if (sexoUser.equalsIgnoreCase("M")) {
                    sexoUser = "Masculino";
                } else {
                    sexoUser = "Femenino";
                }

                datos.add(cedula);
                datos.add(nombre);
                datos.add(apellido);
                datos.add(sexoUser);
                datos.add(correoUser);
                datos.add(edad);
                datos.add(nickUser);
                datos.add(claveUser);
                datos.add(cuentaAct);
                datos.add(tipoUsuario);
                datos.add(atributo);

                valores.add(datos);

            }
        } catch (Exception e) {
            System.out.println("No encontramos ningun usuario con estos datos: " + e);
        }
        return valores;
    }

}
