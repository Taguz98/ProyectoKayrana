/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BaseDatos;

import static BaseDatos.Conexion_Consultas.resultado;
import static BaseDatos.Conexion_Consultas.sentencia;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Johnny
 */
public class SQLEstudiante {

    static String sql;

    public static ArrayList<ArrayList<String>> cargarParalelosEstudiante(int fk_est) {
        ArrayList<ArrayList<String>> datos = new ArrayList();

        sql = "SELECT id_parl_est, nombre_paralelo, descripcion_jornada\n"
                + "	FROM public.\"Paralelos_Estudiantes\", public.\"Paralelos_Jornada\", public.\"Paralelos\",  public.\"Detalle_Jornada\"\n"
                + "	WHERE \"Paralelos_Estudiantes\".fk_estudiante = " + fk_est + " \n"
                + "	and \"Paralelos_Jornada\".id_paral_jornada = fk_paralelo_jrd\n"
                + "	and \"Paralelos\".id_paralelo = \"Paralelos_Jornada\".fk_paralelo\n"
                + "	and \"Detalle_Jornada\".id_deta_jornada = \"Paralelos_Jornada\".fk_deta_jornada ";

        try {
            resultado = sentencia.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println("ERROR EN LA CONSULTA");
        } catch (NullPointerException e) {
            System.out.println("No tiene datos " + e.getMessage());
        }

        try {
            while (resultado.next()) {
                ArrayList<String> valores = new ArrayList();

                valores.add(resultado.getString("id_parl_est"));
                valores.add(resultado.getString("nombre_paralelo"));
                valores.add(resultado.getString("descripcion_jornada"));

                datos.add(valores);

            }
        } catch (SQLException e) {
            System.out.println("Error al traer paralelos: " + e.getMessage());
        } catch (NullPointerException e) {
            System.out.println("No tiene paralelos " + e.getMessage());
        }

        return datos;
    }

    //Con esto consultamos un paralelo que tenga un estudiante 
    public static ArrayList<ArrayList<String>> consultarParaleloEst(int fk_est, int fk_paralelo) {
        ArrayList<ArrayList<String>> datos = new ArrayList();

        sql = "SELECT *\n"
                + "	FROM public.\"Paralelos_Estudiantes\"\n"
                + "	WHERE fk_estudiante = "+fk_est+"\n"
                + "	and fk_paralelo_jrd = "+fk_paralelo+";";

        try {
            resultado = sentencia.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println("ERROR EN LA CONSULTA");
        } catch (NullPointerException e) {
            System.out.println("No tiene datos " + e.getMessage());
        }

        try {
            while (resultado.next()) {
                ArrayList<String> valores = new ArrayList();

                valores.add(resultado.getString("id_parl_est"));
                
                datos.add(valores);

            }
        } catch (SQLException e) {
            System.out.println("Error al traer paralelos: " + e.getMessage());
        } catch (NullPointerException e) {
            System.out.println("No tiene paralelos " + e.getMessage());
        }

        return datos;
    }

    public static void ingresarParaleloEst(int fk_est, int fk_parl) {
        sql = "INSERT INTO public.\"Paralelos_Estudiantes\"(\n"
                + "	fk_estudiante, fk_paralelo_jrd, parl_est_elim)\n"
                + "	VALUES ( " + fk_est + ", " + fk_parl + ", false);";

        Conexion_Consultas.ejecutar(sql);
    }

    public static void modificarParaleloEst(int id_parl_est, int fk_parl) {
        sql = "UPDATE public.\"Paralelos_Estudiantes\"\n"
                + "	SET  fk_paralelo_jrd= " + fk_parl + ", parl_est_elim= false\n"
                + "	WHERE id_parl_est = " + id_parl_est + " ;";

        Conexion_Consultas.ejecutar(sql);
    }

    public static void eliminarParaleloEst(int id_parl_est, boolean elim) {
        sql = "UPDATE public.\"Paralelos_Estudiantes\"\n"
                + "	SET parl_est_elim= " + elim + "\n"
                + "	WHERE id_parl_est = " + id_parl_est + " ;";

        Conexion_Consultas.ejecutar(sql);
    }
}
