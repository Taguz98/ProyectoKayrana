/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BaseDatos;

import static BaseDatos.Conexion_Consultas.ejecutar;
import static BaseDatos.Conexion_Consultas.resultado;
import static BaseDatos.Conexion_Consultas.sentencia;
import Clases.Paralelo;
import Clases.ParaleloJornada;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Johnny
 */
public class SQLParalelos {

    private static String sql;

    public static ArrayList<Paralelo> cargarParalelosCarrera(int fk_carrera) {

        ArrayList<Paralelo> paralelos = new ArrayList();

        sql = "SELECT * FROM public.\"Paralelos\"  WHERE fk_carrera = " + fk_carrera + " ;";

        try {
            resultado = sentencia.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println("ERROR EN LA CONSULTA");
        } catch (NullPointerException e) {
            System.out.println("No tiene datos " + e.getMessage());
        }

        try {
            while (resultado.next()) {
                Paralelo prl = new Paralelo();

                prl.setId_paralelo(resultado.getInt("id_paralelo"));
                prl.setCiclo_paralelo(resultado.getInt("ciclo_paralelo"));
                prl.setNombre_paralelo(resultado.getString("nombre_paralelo"));
                prl.setFk_carrera(resultado.getInt("fk_carrera"));
                prl.setParalelo_elim(resultado.getBoolean("parl_elim"));

                paralelos.add(prl);
            }
        } catch (SQLException e) {
            System.out.println("Error al traer paralelos: " + e.getMessage());
        } catch (NullPointerException e) {
            System.out.println("No tiene paralelos " + e.getMessage());
        }

        return paralelos;
    }

    public static ArrayList<Paralelo> cargarParalelos() {

        ArrayList<Paralelo> paralelos = new ArrayList();

        sql = "SELECT * FROM public.\"Paralelos\" WHERE parl_elim = false;";

        try {
            resultado = sentencia.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println("ERROR EN LA CONSULTA");
        } catch (NullPointerException e) {
            System.out.println("No tiene datos " + e.getMessage());
        }

        try {
            while (resultado.next()) {
                Paralelo prl = new Paralelo();

                prl.setId_paralelo(resultado.getInt("id_paralelo"));
                prl.setCiclo_paralelo(resultado.getInt("ciclo_paralelo"));
                prl.setNombre_paralelo(resultado.getString("nombre_paralelo"));
                prl.setFk_carrera(resultado.getInt("fk_carrera"));
                prl.setParalelo_elim(resultado.getBoolean("parl_elim"));

                paralelos.add(prl);
            }
        } catch (SQLException e) {
            System.out.println("Error al traer paralelos: " + e.getMessage());
        } catch (NullPointerException e) {
            System.out.println("No tiene paralelos " + e.getMessage());
        }

        return paralelos;
    }

    public static void insertarParalelos(Paralelo nuevoParalelo) {

        sql = "INSERT INTO public.\"Paralelos\"(\n"
                + " ciclo_paralelo, nombre_paralelo, fk_carrera)\n"
                + "	VALUES ( " + nuevoParalelo.getCiclo_paralelo() + ",'"
                + nuevoParalelo.getNombre_paralelo() + "'," + nuevoParalelo.getFk_carrera() + ");";

        ejecutar(sql);
    }

    public static void editarParalelo(Paralelo nuevoParalelo) {
        sql = "UPDATE public.\"Paralelos\"\n"
                + "SET ciclo_paralelo=" + nuevoParalelo.getCiclo_paralelo()
                + ", nombre_paralelo='" + nuevoParalelo.getNombre_paralelo()
                + "', fk_carrera=" + nuevoParalelo.getFk_carrera() + "\n"
                + "WHERE id_paralelo=" + nuevoParalelo.getId_paralelo() + ";";

        ejecutar(sql);
    }

    public static void eliminarParalelo(int id_paralelo) {

        sql = "UPDATE public.\"Paralelos\"\n"
                + "SET parl_elim = true\n"
                + "WHERE id_paralelo =" + id_paralelo + ";";

        ejecutar(sql);
    }

    public static ArrayList<ParaleloJornada> cargarJornadaParalelos(boolean elim) {

        ArrayList<ParaleloJornada> jdrParalelos = new ArrayList();
        String com = "";
        if (elim) {
            com = "WHERE parl_jornada_elim=false";
        }

        sql = "SELECT * FROM public.\"Paralelos_Jornada\"  " + com + " ";

        try {
            resultado = sentencia.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println("ERROR EN LA CONSULTA de jornadas paralelos");
        } catch (NullPointerException e) {
            System.out.println("No tiene datos " + e.getMessage());
        }

        try {
            while (resultado.next()) {
                ParaleloJornada prlJdr = new ParaleloJornada();

                prlJdr.setId_parl_jord(resultado.getInt("id_paral_jornada"));
                prlJdr.setFk_paralelo(resultado.getInt("fk_paralelo"));
                prlJdr.setFk_deta_jord(resultado.getInt("fk_deta_jornada"));
                prlJdr.setParl_jord_elim(resultado.getBoolean("parl_jornada_elim"));

                jdrParalelos.add(prlJdr);
            }
        } catch (SQLException e) {
            System.out.println("Error al traer las jornadas de los paralelos " + e.getMessage());
        } catch (NullPointerException e) {
            System.out.println("No tiene jornadas paralelos " + e.getMessage());
        }

        return jdrParalelos;
    }

    public static void insertarJornadaParalelo(ParaleloJornada nuevaJrdParl) {

        sql = "INSERT INTO public.\"Paralelos_Jornada\"(\n"
                + "	 fk_paralelo, fk_deta_jornada)\n"
                + "	VALUES (" + nuevaJrdParl.getFk_paralelo() + "," + nuevaJrdParl.getFk_deta_jord() + ");";

        ejecutar(sql);
    }

    public static void editarJornadaParalelo(ParaleloJornada nuevaJrdParl) {

        sql = "UPDATE public.\"Paralelos_Jornada\"\n"
                + "SET fk_paralelo=" + nuevaJrdParl.getFk_paralelo() + ", fk_deta_jornada=" + nuevaJrdParl.getFk_deta_jord() + "\n"
                + "WHERE id_paral_jornada=" + nuevaJrdParl.getId_parl_jord() + ";";

        ejecutar(sql);
    }

    public static void eliminarJornadaParalelo(int id_parl_jord) {

        sql = "UPDATE public.\"Paralelos_Jornada\"\n"
                + "	SET  parl_jornada_elim=true\n"
                + "	WHERE id_paral_jornada=" + id_parl_jord + ";";

        ejecutar(sql);
    }

    public static ArrayList<ArrayList<String>> cargarJornadaParalelosCarrera(int fk_carrera) {

        ArrayList<ArrayList<String>> valores = new ArrayList();

        sql = "SELECT id_paral_jornada, nombre_paralelo, descripcion_jornada\n"
                + "	FROM public.\"Paralelos_Jornada\", public.\"Paralelos\", public.\"Carrera\", public.\"Detalle_Jornada\"\n"
                + "	WHERE \"Paralelos\".id_paralelo = fk_paralelo \n"
                + "	and id_deta_jornada = fk_deta_jornada \n"
                + "	and fk_carrera = \"Carrera\".id_carrera \n"
                + "	and \"Carrera\".id_carrera =  " + fk_carrera + ";";

        try {
            resultado = sentencia.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println("ERROR EN LA CONSULTA de jornadas paralelos");
        } catch (NullPointerException e) {
            System.out.println("No tiene datos " + e.getMessage());
        }

        try {
            while (resultado.next()) {
                ArrayList<String> datos = new ArrayList();

                datos.add(resultado.getString("id_paral_jornada"));
                String juntar = resultado.getString("nombre_paralelo") + " " + resultado.getString("descripcion_jornada");
                datos.add(juntar);

                valores.add(datos);
            }
        } catch (SQLException e) {
            System.out.println("Error al traer las jornadas de los paralelos " + e.getMessage());
        } catch (NullPointerException e) {
            System.out.println("No tiene jornadas paralelos " + e.getMessage());
        }

        return valores;
    }

}
