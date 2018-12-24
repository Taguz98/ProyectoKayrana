/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BaseDatos;

import static BaseDatos.Conexion_Consultas.resultado;
import static BaseDatos.Conexion_Consultas.sentencia;
import Clases.Periodo_Lectivo;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 *
 * @author Johnny
 */
public class SQLPeriodoLectivo {

    private static String sql;

    public static ArrayList<Periodo_Lectivo> cargarPeriodosLectivos(boolean con) {
        ArrayList<Periodo_Lectivo> periodoLectivo = new ArrayList();
        
        String condicion = ""; 
        
        if (con) {
            condicion = "WHERE per_lect_activo = true"; 
        }
        
        sql = "SELECT * FROM public.\"Periodo_Lectivo\"    "+condicion+" ;";

        try {
            resultado = sentencia.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println("ERROR EN LA CONSULTA");
        } catch (NullPointerException e) {
            System.out.println("No tiene datos " + e.getMessage());
        }

        try {
            while (resultado.next()) {
                Periodo_Lectivo perLect = new Periodo_Lectivo();

                //String fechaIni =  this.fechaInicio.getDateFormatString(); 
                String fechaIni = new SimpleDateFormat("dd/MM/yyyy").format(resultado.getDate("fecha_ini_per_lect"));

                //String fechaFin = this.fechaFin.getDateFormatString(); 
                String fechaFin = new SimpleDateFormat("dd/MM/yyyy").format(resultado.getDate("fecha_fin_per_lect"));

                perLect.setId_per_lect(resultado.getInt("id_per_lect"));
                //perLect.setFecha_ini_per_lect(resultado.getString("fecha_ini_per_lect"));
                //perLect.setFecha_fin_epr_lect(resultado.getString("fecha_fin_per_lect"));
                
                perLect.setFecha_ini_per_lect(fechaIni);
                perLect.setFecha_fin_epr_lect(fechaFin);
                
                perLect.setAnio_per_lect(resultado.getString("anio_per_lect"));
                perLect.setPer_lect_activo(resultado.getBoolean("per_lect_activo"));
                
                perLect.setFk_carrera(resultado.getInt("fk_carrera")); 

                periodoLectivo.add(perLect);
            }
        } catch (SQLException e) {
            System.out.println("Error al traeer periodos lectivo: " + e.getMessage());
        } catch (NullPointerException e) {
            System.out.println("No tiene datos " + e.getMessage());
        }

        return periodoLectivo;
    }

    public static void insertarPeriodoLectio(Periodo_Lectivo nuevoPeriodo) {
        sql = "INSERT INTO public.\"Periodo_Lectivo\"(\n"
                + "fecha_ini_per_lect, fecha_fin_per_lect, anio_per_lect, per_lect_activo, fk_carrera)\n"
                + "VALUES ( '" + nuevoPeriodo.getFecha_ini_per_lect()
                + "','" + nuevoPeriodo.getFecha_fin_epr_lect() + "','"
                + nuevoPeriodo.getAnio_per_lect() + "','true', "
                + nuevoPeriodo.getFk_carrera()+");";

        Conexion_Consultas.ejecutar(sql);
    }

    public static void editarPeriodoLectivo(Periodo_Lectivo nuevoPeriodo) {
        sql = "UPDATE public.\"Periodo_Lectivo\"\n"
                + "	SET fecha_ini_per_lect='" + nuevoPeriodo.getFecha_ini_per_lect()
                + "', fecha_fin_per_lect='" + nuevoPeriodo.getFecha_fin_epr_lect()
                + "', anio_per_lect='" + nuevoPeriodo.getAnio_per_lect() + "'"
                + "fk_carrera = "+nuevoPeriodo.getFk_carrera()+" \n"
                + "	WHERE id_per_lect=" + nuevoPeriodo.getId_per_lect() + ";";

        Conexion_Consultas.ejecutar(sql);
    }

    public static void eliminarPeriodoLectivo(int id_periodo_lectivo) {
        sql = "UPDATE public.\"Periodo_Lectivo\"" 
                + " SET per_lect_activo=false "
                + " WHERE id_per_lect =" + id_periodo_lectivo + ";";

        Conexion_Consultas.ejecutar(sql);
    }
}
