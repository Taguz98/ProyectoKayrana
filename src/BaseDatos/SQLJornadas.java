/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BaseDatos;

import static BaseDatos.Conexion_Consultas.ejecutar;
import static BaseDatos.Conexion_Consultas.resultado;
import static BaseDatos.Conexion_Consultas.sentencia;
import Clases.Detalle_Jornada;
import Clases.HorasTrabajo;
import Clases.Jornada;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Johnny
 */
public class SQLJornadas {

    private static String sql;
    
    
    public static ArrayList<HorasTrabajo> cargarHorasTrabajo(){
        ArrayList<HorasTrabajo> horas = new ArrayList(); 
        
          sql = "SELECT * FROM public.\"Horas\";";
          
          String auxHora; 

        try {
            resultado = sentencia.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println("ERROR EN LA CONSULTA");
        } catch (NullPointerException e) {
            System.out.println("No tiene datos " + e.getMessage());
        }

        try {
            while (resultado.next()) {
                HorasTrabajo hora = new HorasTrabajo();

                hora.setId_hora_trabajo(resultado.getInt("id_hora_trabajo"));
                
                //Hago esto para pasarle del formato de 00:00:00 al 00:00
                auxHora = resultado.getString("hora_trab"); 
                auxHora = auxHora.substring(0, 5);
                
                hora.setHora_trab(auxHora);

                horas.add(hora);
            }
        } catch (SQLException e) {
            System.out.println("Error al traeer horas de trabajo en clase jornadas: " + e.getMessage());
        } catch (NullPointerException e) {
            System.out.println("No tiene datos " + e.getMessage());
        }
        
        return horas; 
    }
    

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
            System.out.println("Error al traeer jornadas en clase jornadas: " + e.getMessage());
        } catch (NullPointerException e) {
            System.out.println("No tiene datos " + e.getMessage());
        }

        return jornadaDB;

    }

    public static ArrayList<Detalle_Jornada> cargarDetalleJornada(boolean condicion) {

        ArrayList<Detalle_Jornada> detalleJornadaDB = new ArrayList();
        
        String con = ""; 
        if(condicion){
            con = "WHERE deta_jord_elim=false";
        }

        sql = "SELECT * FROM public.\"Detalle_Jornada\"  "+ con +"";

        try {
            resultado = sentencia.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println("ERROR EN LA CONSULTA");
        } catch (NullPointerException e) {
            System.out.println("No tiene datos " + e.getMessage());
        }

        try {
            while (resultado.next()) {
                Detalle_Jornada detJord = new Detalle_Jornada();

                detJord.setId_deta_jornada(resultado.getInt("id_deta_jornada"));
                detJord.setDias(resultado.getString("dias_jornada"));
                detJord.setDescripcion_jornada(resultado.getString("descripcion_jornada"));
                detJord.setFk_jornada(resultado.getInt("fk_jornada"));
                detJord.setFk_hora1(resultado.getInt("fk_hora_inicio"));
                detJord.setFk_hora2(resultado.getInt("fk_hora_fin"));
                detJord.setDeta_jord_elim(resultado.getBoolean("deta_jord_elim"));

                detalleJornadaDB.add(detJord);
            }
        } catch (SQLException e) {
            System.out.println("Error al traer detalles jornadas: " + e.getMessage());
        } catch (NullPointerException e) {
            System.out.println("No tiene datos " + e.getMessage());
        }

        return detalleJornadaDB;
    }

    public static void insertarDetalleJornada(Detalle_Jornada nuevaDeJorn) {

        sql = "INSERT INTO public.\"Detalle_Jornada\"(\n"
                + "	 dias_jornada, descripcion_jornada, fk_jornada, fk_hora_inicio, fk_hora_fin)\n"
                + "	VALUES ( '" + nuevaDeJorn.getDias() + "','"+nuevaDeJorn.getDescripcion_jornada() +
                "', "+nuevaDeJorn.getFk_jornada()+", "+nuevaDeJorn.getFk_hora1()+
                ", "+nuevaDeJorn.getFk_hora2()+");";

        ejecutar(sql);
    }

    public static void editarDetalleJornada(Detalle_Jornada nuevaDeJorn) {

        sql = "UPDATE public.\"Detalle_Jornada\"\n"
                + "	SET dias_jornada='" + nuevaDeJorn.getDias() + "', descripcion_jornada='"
                + nuevaDeJorn.getDescripcion_jornada() + "', fk_jornada=" + nuevaDeJorn.getFk_jornada()
                + ", fk_hora_inicio=" + nuevaDeJorn.getFk_hora1() + ", fk_hora_fin=" + nuevaDeJorn.getFk_hora2() + "\n"
                + "	WHERE id_deta_jornada = " + nuevaDeJorn.getId_deta_jornada() + ";";

        ejecutar(sql);
    }

    public static void eliminarDetalleJornada(int id_detalleJornada) {

        sql = "UPDATE public.\"Detalle_Jornada\"\n"
                + "	SET deta_jord_elim='" + true +"'\n"
                + "	WHERE id_deta_jornada = " + id_detalleJornada + ";";

        ejecutar(sql);
    }

}
