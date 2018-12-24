/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BaseDatos;

import static BaseDatos.Conexion_Consultas.ejecutar;
import static BaseDatos.Conexion_Consultas.resultado;
import static BaseDatos.Conexion_Consultas.sentencia;
import Clases.Actividad;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Johnny
 */
public class SQLActividades {
        
        private static String sql = ""; 
    
     public static ArrayList<Actividad> cargarActividades(boolean condicion) {
        ArrayList<Actividad> actividadesDB = new ArrayList();
        
        String con = ""; 
        
        if(condicion){
            con = "WHERE actividad_elim = false"; 
        }
        
        sql = "SELECT * FROM public.\"Actividades\"  "+con+" ;";

        try {
            resultado = sentencia.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println("ERROR EN LA CONSULTA");
        } catch (NullPointerException e) {
            System.out.println("No tiene datos " + e.getMessage());
        }

        try {
            while (resultado.next()) {
                Actividad act = new Actividad();

                act.setId_actividad(resultado.getInt("id_actividad"));
                act.setNombre_actividad(resultado.getString("nombre_actividad"));
                act.setActividad_elim(resultado.getBoolean("actividad_elim"));

                actividadesDB.add(act);
            }
        } catch (SQLException e) {
            System.out.println("Error al traeer materias: " + e.getMessage());
        } catch (NullPointerException e) {
            System.out.println("No tiene datos " + e.getMessage());
        }

        return actividadesDB;
    }

    public static void insertarActividad(Actividad nuevaActividad) {
        sql = "INSERT INTO public.\"Actividades\"(\n"
                + " nombre_actividad)\n"
                + "VALUES ( '" + nuevaActividad.getNombre_actividad() + "');";

        ejecutar(sql);
    }

    public static void editarActividad(Actividad editarActividad) {
        sql = "UPDATE public.\"Actividades\"\n"
                + "SET nombre_actividad='" + editarActividad.getNombre_actividad() + "'"
                + "WHERE id_actividad=" + editarActividad.getId_actividad() + ";";

        ejecutar(sql);
    }

    public static void eliminarActividad(int id_actividad, boolean elim) {
        sql = "UPDATE public.\"Actividades\"\n"
                + "SET actividad_elim='" + elim + "'"
                + "WHERE id_actividad=" + id_actividad + ";";

        ejecutar(sql);
    }

}
