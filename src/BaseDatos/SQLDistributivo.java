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
public class SQLDistributivo {

    private static String sql = "";

    public static ArrayList<ArrayList<String>> cargarMateriasCarrera(int id_carrera) {
        ArrayList<ArrayList<String>> datos = new ArrayList();

        sql = "SELECT id_mat_ciclo, fk_materia, fk_mat_carrera, horas_materia,"
                + " mat_carrera_elim, nombre_materia, ciclo_mat_carrera\n"
                + "	FROM public.\"Materias_Ciclo\", public.\"Materias_Carrera\", public.\"Materias\"\n"
                + "	WHERE fk_mat_carrera = \"Materias_Carrera\".id_mat_carrera \n"
                + "	and \"Materias_Carrera\".fk_carrera = " + id_carrera + " \n"
                + "	and \"Materias\".id_materia = fk_materia"
                + "     and mat_carrera_elim = false"
                + "      order by ciclo_mat_carrera;";

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

                valores.add(resultado.getString("id_mat_ciclo"));
                valores.add(resultado.getString("ciclo_mat_carrera"));
                valores.add(resultado.getString("nombre_materia"));
                valores.add(resultado.getString("horas_materia"));

                datos.add(valores);
            }
        } catch (SQLException e) {
            System.out.println("Error al traeer materias: " + e.getMessage());
        } catch (NullPointerException e) {
            System.out.println("No tiene datos " + e.getMessage());
        }

        return datos;
    }

    public static ArrayList<ArrayList<String>> cargarMateriasDocenteCarrera(int id_carrera) {
        ArrayList<ArrayList<String>> datos = new ArrayList();

        sql = "SELECT id_mat_pref, cedula_docente, nombre_donce, nombre_materia, ciclo_mat_carrera, horas_materia\n"
                + "	FROM public.\"Materias_Preferentes\", public.\"Docentes_Instituto\", public.\"Materias\", \n"
                + "	public.\"Materias_Carrera\", public.\"Materias_Ciclo\"\n"
                + "	WHERE \"Docentes_Instituto\".cedula_docente = fk_docente\n"
                + "	and \"Materias_Preferentes\".fk_materias = \"Materias_Ciclo\".fk_materia\n"
                + "	and \"Materias\".id_materia = \"Materias_Ciclo\".fk_materia\n"
                + "	and \"Materias_Ciclo\".fk_mat_carrera = \"Materias_Carrera\".id_mat_carrera\n"
                + "	and \"Materias_Carrera\".fk_carrera = " + id_carrera + "\n"
                + "	and \"Materias_Ciclo\".mat_carrera_elim = false\n"
                + "	and mat_pref_elim = false;";

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

                valores.add(resultado.getString("id_mat_pref"));
                valores.add(resultado.getString("cedula_docente"));
                valores.add(resultado.getString("nombre_donce"));
                valores.add(resultado.getString("nombre_materia"));
                valores.add(resultado.getString("ciclo_mat_carrera"));
                valores.add(resultado.getString("horas_materia"));

                datos.add(valores);
            }
        } catch (SQLException e) {
            System.out.println("Error al traeer materias: " + e.getMessage());
        } catch (NullPointerException e) {
            System.out.println("No tiene datos " + e.getMessage());
        }

        return datos;
    }

    public static ArrayList<ArrayList<String>> cargarParalelosCarrera(int id_carrera) {
        ArrayList<ArrayList<String>> datos = new ArrayList();

        sql = "SELECT id_paral_jornada, nombre_paralelo, descripcion_jornada\n"
                + "	FROM public.\"Paralelos_Jornada\", public.\"Paralelos\", public.\"Detalle_Jornada\"\n"
                + "	WHERE \"Paralelos\".id_paralelo = fk_paralelo\n"
                + "	and \"Paralelos\".fk_carrera = " + id_carrera + "\n"
                + "	and \"Detalle_Jornada\".id_deta_jornada =fk_deta_jornada\n"
                + "	and parl_jornada_elim = false;";

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

                valores.add(resultado.getString("id_paral_jornada"));
                valores.add(resultado.getString("nombre_paralelo"));
                valores.add(resultado.getString("descripcion_jornada"));
                datos.add(valores);
            }
        } catch (SQLException e) {
            System.out.println("Error al traeer materias: " + e.getMessage());
        } catch (NullPointerException e) {
            System.out.println("No tiene datos " + e.getMessage());
        }

        return datos;
    }

    public static ArrayList<ArrayList<String>> cargarParalelosCarreraCiclo(int id_carrera, int ciclo) {
        ArrayList<ArrayList<String>> datos = new ArrayList();

        sql = "SELECT id_paral_jornada, nombre_paralelo, descripcion_jornada, dias_jornada, fk_hora_inicio, fk_hora_fin\n"
                + "	FROM public.\"Paralelos_Jornada\", public.\"Paralelos\", public.\"Detalle_Jornada\"\n"
                + "	WHERE \"Paralelos\".id_paralelo = fk_paralelo\n"
                + "	and \"Paralelos\".fk_carrera = " + id_carrera + "\n"
                + "	and \"Paralelos\".ciclo_paralelo = " + ciclo + "\n"
                + "	and \"Detalle_Jornada\".id_deta_jornada =fk_deta_jornada\n"
                + "	and parl_jornada_elim = false;";

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

                valores.add(resultado.getString("id_paral_jornada"));
                valores.add(resultado.getString("nombre_paralelo"));
                valores.add(resultado.getString("descripcion_jornada"));
                valores.add(resultado.getString("dias_jornada"));
                valores.add(resultado.getString("fk_hora_inicio"));
                valores.add(resultado.getString("fk_hora_fin"));

                datos.add(valores);
            }
        } catch (SQLException e) {
            System.out.println("Error al traeer materias: " + e.getMessage());
        } catch (NullPointerException e) {
            System.out.println("No tiene datos " + e.getMessage());
        }

        return datos;
    }

    public static ArrayList<ArrayList<String>> cargarParalelosCarreraCicloJorada(int id_carrera, int ciclo, int fk_jorn) {
        ArrayList<ArrayList<String>> datos = new ArrayList();

        sql = "SELECT id_paral_jornada, nombre_paralelo, descripcion_jornada, dias_jornada, fk_hora_inicio, fk_hora_fin\n"
                + "	FROM public.\"Paralelos_Jornada\", public.\"Paralelos\", public.\"Detalle_Jornada\"\n"
                + "	WHERE \"Paralelos\".id_paralelo = fk_paralelo\n"
                + "	and \"Paralelos\".fk_carrera = " + id_carrera + "\n"
                + "	and \"Paralelos\".ciclo_paralelo = " + ciclo + "\n"
                + "	and \"Detalle_Jornada\".id_deta_jornada =fk_deta_jornada\n"
                + "     and \"Detalle_Jornada\".fk_jornada = " + fk_jorn
                + "	and parl_jornada_elim = false;";

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

                valores.add(resultado.getString("id_paral_jornada"));
                valores.add(resultado.getString("nombre_paralelo"));
                valores.add(resultado.getString("descripcion_jornada"));
                valores.add(resultado.getString("dias_jornada"));
                valores.add(resultado.getString("fk_hora_inicio"));
                valores.add(resultado.getString("fk_hora_fin"));

                datos.add(valores);
            }
        } catch (SQLException e) {
            System.out.println("Error al traeer materias: " + e.getMessage());
        } catch (NullPointerException e) {
            System.out.println("No tiene datos " + e.getMessage());
        }

        return datos;
    }

    public static ArrayList<ArrayList<String>> cargarMateriasCarreraCiclo(int id_carrera, int ciclo) {
        ArrayList<ArrayList<String>> datos = new ArrayList();

        sql = "SELECT fk_materia, nombre_materia, horas_materia\n"
                + "	FROM public.\"Materias_Carrera\", public.\"Materias\", public.\"Materias_Ciclo\"\n"
                + "	WHERE ciclo_mat_carrera = " + ciclo + "\n"
                + "	and fk_carrera = " + id_carrera + "\n"
                + "	and \"Materias_Ciclo\".fk_mat_carrera = id_mat_carrera\n"
                + "	and \"Materias\".id_materia = \"Materias_Ciclo\".fk_materia\n"
                + "	and \"Materias_Ciclo\".mat_carrera_elim = false;";

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

                valores.add(resultado.getString("fk_materia"));
                valores.add(resultado.getString("nombre_materia"));
                valores.add(resultado.getString("horas_materia"));

                datos.add(valores);
            }
        } catch (SQLException e) {
            System.out.println("Error al traeer materias: " + e.getMessage());
        } catch (NullPointerException e) {
            System.out.println("No tiene datos " + e.getMessage());
        }

        return datos;
    }

    public static ArrayList<ArrayList<String>> cargarMateriasPrefDocente(String cedula) {
        ArrayList<ArrayList<String>> datos = new ArrayList();

        sql = "SELECT fk_materias, nombre_donce, nombre_materia\n"
                + "	FROM public.\"Materias_Preferentes\", public.\"Docentes_Instituto\", public.\"Materias\"\n"
                + "	WHERE \"Materias_Preferentes\".fk_docente = '"+cedula+"'\n"
                + "	and \"Docentes_Instituto\".cedula_docente = '"+cedula+"'\n"
                + "	and \"Materias\".id_materia = fk_materias\n"
                + "	and mat_pref_elim = false;";

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

                valores.add(resultado.getString("fk_materias"));
                valores.add(resultado.getString("nombre_donce"));
                valores.add(resultado.getString("nombre_materia"));

                datos.add(valores);
            }
        } catch (SQLException e) {
            System.out.println("Error al traeer materias: " + e.getMessage());
        } catch (NullPointerException e) {
            System.out.println("No tiene datos " + e.getMessage());
        }

        return datos;
    }

}
