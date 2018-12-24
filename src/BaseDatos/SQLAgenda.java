/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BaseDatos;

import static BaseDatos.Conexion_Consultas.resultado;
import static BaseDatos.Conexion_Consultas.sentencia;
import Clases.Apuntes;
import Clases.Recordatorios;
import Clases.Tareas;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Johnny
 */
public class SQLAgenda {

    private static String sql;

    public static ArrayList<Apuntes> cargarApuntes() {
        ArrayList<Apuntes> apuntes = new ArrayList();

        sql = "SELECT * FROM public.\"Apuntes\";";

        try {
            resultado = sentencia.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println("ERROR EN LA CONSULTA" + e);
        } catch (NullPointerException e) {
            System.out.println("No tiene datos " + e.getMessage());
        }

        try {
            while (resultado.next()) {
                Apuntes apt = new Apuntes();

                apt.setId_apunte(resultado.getInt("id_apunte"));
                apt.setFecha_esc_apunte(resultado.getTimestamp("fecha_esc_nota"));
                apt.setFk_usuario(resultado.getString("fk_usuario"));
                apt.setApunte(resultado.getString("apunte"));
                apt.setApunte_elim(resultado.getBoolean("apunte_elim"));

                apuntes.add(apt);
            }
        } catch (SQLException e) {
            System.out.println("Error al los apuntes: " + e.getMessage());
        } catch (NullPointerException e) {
            System.out.println("No tiene datos " + e.getMessage());
        }

        return apuntes;
    }

    public static ArrayList<Apuntes> cargarApuntesUsuario(String cedula) {
        ArrayList<Apuntes> apuntes = new ArrayList();

        sql = "SELECT * FROM public.\"Apuntes\" WHERE fk_usuario='" + cedula + "' and apunte_elim = false ;";

        try {
            resultado = sentencia.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println("ERROR EN LA CONSULTA" + e);
        } catch (NullPointerException e) {
            System.out.println("No tiene datos " + e.getMessage());
        }

        try {
            while (resultado.next()) {
                Apuntes apt = new Apuntes();

                apt.setId_apunte(resultado.getInt("id_apunte"));
                apt.setFecha_esc_apunte(resultado.getTimestamp("fecha_esc_nota"));
                apt.setFk_usuario(resultado.getString("fk_usuario"));
                apt.setApunte(resultado.getString("apunte"));
                apt.setApunte_elim(resultado.getBoolean("apunte_elim"));

                apuntes.add(apt);
                
            }
        } catch (SQLException e) {
            System.out.println("Error al los apuntes: " + e.getMessage());
        } catch (NullPointerException e) {
            System.out.println("No tiene datos " + e.getMessage());
        }

        return apuntes;
    }

    public static void insertarApunte(Apuntes nuevoApunte) {
        sql = "INSERT INTO public.\"Apuntes\"(\n"
                + "fecha_esc_nota, apunte, fk_usuario)\n"
                + "VALUES ( '" + nuevoApunte.getFecha_esc_apunte()
                + "','" + nuevoApunte.getApunte()
                + "','" + nuevoApunte.getFk_usuario() + "');";

        Conexion_Consultas.ejecutar(sql);
    }

    public static void editarApunte(Apuntes nuevoApunte) {
        sql = "UPDATE public.\"Apuntes\"\n"
                + "	SET apunte='" + nuevoApunte.getApunte() + "'\n"
                + "	WHERE id_apunte=" + nuevoApunte.getId_apunte() + ";";

        Conexion_Consultas.ejecutar(sql);
    }

    public static void eliminarApunte(int id_apunte) {
        sql = "UPDATE public.\"Apuntes\"\n"
                + "	SET apunte_elim=true\n"
                + "	WHERE id_apunte=" + id_apunte + ";";

        Conexion_Consultas.ejecutar(sql);
    }

    public static ArrayList<Tareas> cargarTareas() {
        ArrayList<Tareas> tareas = new ArrayList();

        sql = "SELECT * FROM public.\"Tareas\";";

        try {
            resultado = sentencia.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println("ERROR EN LA CONSULTA" + e);
        } catch (NullPointerException e) {
            System.out.println("No tiene datos " + e.getMessage());
        }

        try {
            while (resultado.next()) {
                Tareas tarea = new Tareas();

                tarea.setId_tarea(resultado.getInt("id_tarea"));
                tarea.setFk_usuario(resultado.getString("fk_usuario"));
                tarea.setFecha_esc_tarea(resultado.getTimestamp("fecha_esc_tarea"));
                tarea.setFecha_presentacion(resultado.getTimestamp("fecha_presentacion"));
                tarea.setTarea(resultado.getString("tarea"));
                tarea.setTarea_completa(resultado.getBoolean("tarea_completa"));
                tarea.setTarea_elim(resultado.getBoolean("tarea_elim"));

                tareas.add(tarea);
            }
        } catch (SQLException e) {
            System.out.println("Error al los Tareas: " + e.getMessage());
        } catch (NullPointerException e) {
            System.out.println("No tiene datos " + e.getMessage());
        }

        return tareas;
    }

    public static ArrayList<Tareas> cargarTareasUsuario(String cedula) {
        ArrayList<Tareas> tareas = new ArrayList();

        sql = "SELECT * FROM \"Tareas\"  WHERE \"fk_usuario\"='"+cedula+"' and tarea_elim = false ORDER BY \"fecha_presentacion\"";

        try {
            resultado = sentencia.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println("ERROR EN LA CONSULTA" + e);
        } catch (NullPointerException e) {
            System.out.println("No tiene datos " + e.getMessage());
        }

        try {
            while (resultado.next()) {
                Tareas tarea = new Tareas();

                tarea.setId_tarea(resultado.getInt("id_tarea"));
                tarea.setFk_usuario(resultado.getString("fk_usuario"));
                tarea.setFecha_esc_tarea(resultado.getTimestamp("fecha_esc_tarea"));
                tarea.setFecha_presentacion(resultado.getTimestamp("fecha_presentacion"));
                tarea.setTarea(resultado.getString("tarea"));
                tarea.setTarea_completa(resultado.getBoolean("tarea_completa"));
                tarea.setTarea_elim(resultado.getBoolean("tarea_elim"));

                tareas.add(tarea);
            }
        } catch (SQLException e) {
            System.out.println("Error al los Tareas: " + e.getMessage());
        } catch (NullPointerException e) {
            System.out.println("No tiene datos " + e.getMessage());
        }

        return tareas;
    }

    public static void insertarTareas(Tareas nuevaTarea) {
        sql = "INSERT INTO public.\"Tareas\"(\n"
                + " fecha_esc_tarea, tarea, fecha_presentacion, tarea_completa, fk_usuario)\n"
                + "VALUES ( '" + nuevaTarea.getFecha_esc_tarea()
                + "','" + nuevaTarea.getTarea() + "','" + nuevaTarea.getFecha_presentacion()
                + "','false','" + nuevaTarea.getFk_usuario() + "');";

        Conexion_Consultas.ejecutar(sql);
    }

    public static void editarTareas(Tareas nueTarea) {
        sql = "UPDATE public.\"Tareas\"\n"
                + "SET tarea='" + nueTarea.getTarea() + "', fecha_presentacion='" + nueTarea.getFecha_presentacion() + "'\n"
                + "WHERE id_tarea='" + nueTarea.getId_tarea() + "';";

        Conexion_Consultas.ejecutar(sql);
    }

    public static void estadoTareas(int id_tarea, boolean estado) {
        
        sql = "UPDATE public.\"Tareas\"\n"
                + "SET tarea_completa='" + estado + "'\n"
                + "WHERE id_tarea=" + id_tarea + ";";

        Conexion_Consultas.ejecutar(sql);
    }

    public static void eliminarTarea(int id_tarea) {
        sql = "UPDATE public.\"Tareas\"\n"
                + "SET tarea_elim = true \n"
                + "WHERE id_tarea=" + id_tarea + ";";

        Conexion_Consultas.ejecutar(sql);
    }

    public static ArrayList<Recordatorios> cargarRecordatorios() {
        ArrayList<Recordatorios> recordatorios = new ArrayList();

        sql = "SELECT * FROM public.\"Recordatorios\";";

        try {
            resultado = sentencia.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println("ERROR EN LA CONSULTA" + e);
        } catch (NullPointerException e) {
            System.out.println("No tiene datos " + e.getMessage());
        }

        try {
            while (resultado.next()) {
                Recordatorios record = new Recordatorios();

                record.setId_recordatorio(resultado.getInt("id_recordatorio"));
                record.setFk_usuario(resultado.getString("fk_usuario"));
                record.setFecha_esc_record(resultado.getTimestamp("fecha_esc_record"));
                record.setFecha_noti(resultado.getTimestamp("fecha_notificacion"));
                record.setRecordatorio(resultado.getString("recordatorio"));
                record.setRecordatorio_elim(resultado.getBoolean("recordatorio_elim"));

                recordatorios.add(record);
            }
        } catch (SQLException e) {
            System.out.println("Error al los recordatorios: " + e.getMessage());
        } catch (NullPointerException e) {
            System.out.println("No tiene datos " + e.getMessage());
        }

        return recordatorios;
    }

    public static ArrayList<Recordatorios> cargarRecordatoriosUsuarios(String cedula) {
        ArrayList<Recordatorios> recordatorios = new ArrayList();

        sql = "SELECT * FROM \"Recordatorios\"  WHERE \"fk_usuario\"='"+cedula+"' and recordatorio_elim = false  ORDER BY \"fecha_notificacion\"";

        try {
            resultado = sentencia.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println("ERROR EN LA CONSULTA" + e);
        } catch (NullPointerException e) {
            System.out.println("No tiene datos " + e.getMessage());
        }

        try {
            while (resultado.next()) {
                Recordatorios record = new Recordatorios();

                record.setId_recordatorio(resultado.getInt("id_recordatorio"));
                record.setFk_usuario(resultado.getString("fk_usuario"));
                record.setFecha_esc_record(resultado.getTimestamp("fecha_esc_record"));
                record.setFecha_noti(resultado.getTimestamp("fecha_notificacion"));
                record.setRecordatorio(resultado.getString("recordatorio"));
                record.setRecordatorio_elim(resultado.getBoolean("recordatorio_elim"));

                recordatorios.add(record);
            }
        } catch (SQLException e) {
            System.out.println("Error al los recordatorios de un usuario: " + e.getMessage());
        } catch (NullPointerException e) {
            System.out.println("No tiene datos " + e.getMessage());
        }
        return recordatorios;
    }

    public static void insertarRecordatorio(Recordatorios nuevoRecord) {
        sql = "INSERT INTO public.\"Recordatorios\"(\n"
                + "fecha_esc_record, recordatorio, fecha_notificacion, fk_usuario)\n"
                + "VALUES ('" + nuevoRecord.getFecha_esc_record()
                + "','" + nuevoRecord.getRecordatorio()
                + "','" + nuevoRecord.getFecha_noti()
                + "','" + nuevoRecord.getFk_usuario() + "');";

        Conexion_Consultas.ejecutar(sql);
    }

    public static void editarRecordatorio(Recordatorios nuevoRecord) {
        sql = "UPDATE public.\"Recordatorios\"\n"
                + "SET recordatorio='" + nuevoRecord.getRecordatorio()
                + "', fecha_notificacion='" + nuevoRecord.getFecha_noti() + "'\n"
                + "WHERE id_recordatorio=" + nuevoRecord.getId_recordatorio() + ";";

        Conexion_Consultas.ejecutar(sql);
    }

    public static void eliminarRecordatorio(int id_recordatorio) {
        sql = "UPDATE public.\"Recordatorios\"\n"
                + "SET recordatorio_elim = true "
                + " WHERE id_recordatorio = " + id_recordatorio + ";";

        Conexion_Consultas.ejecutar(sql);
    }

}
