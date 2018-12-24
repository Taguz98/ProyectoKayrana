/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BaseDatos;

import static BaseDatos.Conexion_Consultas.ejecutar;
import static BaseDatos.Conexion_Consultas.resultado;
import static BaseDatos.Conexion_Consultas.sentencia;
import Clases.Carrera;
import Clases.Carrera_Jornada;
import Clases.MateriaCarrera;
import Clases.MateriasCiclo;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Johnny
 */
public class SQLCarreras {

    private static String sql = "";

    public static ArrayList<Carrera> cargarCarrerasDB(boolean condicion) {
        ArrayList<Carrera> carreraDB = new ArrayList();

        String con = "";
        if (condicion) {
            con = "WHERE carrera_elim=false";
        }

        sql = "SELECT * FROM public.\"Carrera\"  " + con + ";";

        try {
            resultado = sentencia.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println("ERROR EN LA CONSULTA");
        } catch (NullPointerException e) {
            System.out.println("No tiene datos " + e.getMessage());
        }

        try {
            while (resultado.next()) {
                Carrera carr = new Carrera();

                carr.setId_carrera(resultado.getInt("id_carrera"));
                carr.setNombre_Carrera(resultado.getString("nombre_carrera"));
                carr.setModalidad_Carrera(resultado.getString("modalidad_carrera"));
                carr.setTitulo_carrera(resultado.getString("titulo_carrera"));
                carr.setNum_ciclos_carrera(resultado.getInt("num_ciclos_carrera"));
                carr.setCarrera_elim(resultado.getBoolean("carrera_elim"));

                carreraDB.add(carr);
            }
        } catch (SQLException e) {
            System.out.println("Error al traeer materias: " + e.getMessage());
        } catch (NullPointerException e) {
            System.out.println("No tiene datos " + e.getMessage());
        }

        return carreraDB;
    }

    public static void insertarCarrera(Carrera nuevaCarrera) {
        sql = "INSERT INTO public.\"Carrera\"(\n"
                + "	 nombre_carrera, modalidad_carrera, titulo_carrera, num_ciclos_carrera, carrera_elim)\n"
                + "VALUES ( '" + nuevaCarrera.getNombre_Carrera() + "','" + nuevaCarrera.getModalidad_Carrera()
                + "','" + nuevaCarrera.getTitulo_carrera() + "','" + nuevaCarrera.getNum_ciclos_carrera()
                + "','" + nuevaCarrera.isCarrera_elim() + "');";

        ejecutar(sql);
    }

    public static void editarCarrera(Carrera nuevaCarrera) {
        sql = "UPDATE public.\"Carrera\"\n"
                + "SET nombre_carrera= '" + nuevaCarrera.getNombre_Carrera() + "', modalidad_carrera='"
                + nuevaCarrera.getModalidad_Carrera() + "', titulo_carrera='" + nuevaCarrera.getTitulo_carrera()
                + "', num_ciclos_carrera='" + nuevaCarrera.getNum_ciclos_carrera() + "'"
                + "WHERE id_carrera=" + nuevaCarrera.getId_carrera() + ";";

        ejecutar(sql);
    }

    public static void eliminarCarrera(int id_carrera, boolean elim) {
        sql = "UPDATE public.\"Carrera\"\n"
                + "SET carrera_elim= '" + elim + "'"
                + "WHERE id_carrera=" + id_carrera + ";";

        ejecutar(sql);
    }

    //Hasta aqui copie  
    public static ArrayList<Carrera_Jornada> cargarJornadasCarrera(boolean condicion) {
        ArrayList<Carrera_Jornada> jornadasCDB = new ArrayList();

        String con = "";
        if (condicion) {
            con = "WHERE jorn_carrera_elim=false";
        }

        sql = "SELECT * FROM public.\"Carrera_Jornadas\"   " + con + " ;";

        try {
            resultado = sentencia.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println("ERROR EN LA CONSULTA");
        } catch (NullPointerException e) {
            System.out.println("No tiene datos " + e.getMessage());
        }

        try {
            while (resultado.next()) {
                Carrera_Jornada jord = new Carrera_Jornada();

                jord.setId_carrera_jornada(resultado.getInt("id_carrera_jornada"));
                jord.setFk_carrera(resultado.getInt("fk_carrera"));
                jord.setFk_jornada(resultado.getInt("fk_jornada"));
                jord.setJorn_carrera_elim(resultado.getBoolean("jorn_carrera_elim"));

                jornadasCDB.add(jord);
            }
        } catch (SQLException e) {
            System.out.println("Error al traer carreras_jornada: " + e.getMessage());
        } catch (NullPointerException e) {
            System.out.println("No tiene datos " + e.getMessage());
        }

        return jornadasCDB;
    }

    public static ArrayList<Carrera_Jornada> cargarJornadasCarrera(int fk_carrera) {
        ArrayList<Carrera_Jornada> jornadasCDB = new ArrayList();

        sql = "SELECT * FROM public.\"Carrera_Jornadas\"  WHERE jorn_carrera_elim=false  and fk_carrera= " + fk_carrera + " ;";

        try {
            resultado = sentencia.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println("ERROR EN LA CONSULTA");
        } catch (NullPointerException e) {
            System.out.println("No tiene datos " + e.getMessage());
        }

        try {
            while (resultado.next()) {
                Carrera_Jornada jord = new Carrera_Jornada();

                jord.setId_carrera_jornada(resultado.getInt("id_carrera_jornada"));
                jord.setFk_carrera(resultado.getInt("fk_carrera"));
                jord.setFk_jornada(resultado.getInt("fk_jornada"));
                jord.setJorn_carrera_elim(resultado.getBoolean("jorn_carrera_elim"));

                jornadasCDB.add(jord);
            }
        } catch (SQLException e) {
            System.out.println("Error al traer carreras_jornada: " + e.getMessage());
        } catch (NullPointerException e) {
            System.out.println("No tiene datos " + e.getMessage());
        }

        return jornadasCDB;
    }

    public static void insertarJornadasCarrera(Carrera_Jornada nuevaJornada) {
        sql = "INSERT INTO public.\"Carrera_Jornadas\"("
                + "fk_carrera, fk_jornada)\n"
                + "VALUES (" + nuevaJornada.getFk_carrera() + "," + nuevaJornada.getFk_jornada() + ");";

        ejecutar(sql);
    }

    public static void eliminarJornadasCarrera(Carrera_Jornada nuevaJornada) {
        sql = "UPDATE public.\"Carrera_Jornadas\"\n"
                + "	SET  jorn_carrera_elim='" + true + "'"
                + "WHERE id_carrera_jornada=" + nuevaJornada.getId_carrera_jornada() + ";";

        sql = "DELETE FROM public.\"Carrera_Jornadas\"\n"
                + "WHERE id_carrera_jornada=" + nuevaJornada.getId_carrera_jornada() + ";";

        ejecutar(sql);
    }

    public static ArrayList<MateriaCarrera> cargarMateriasCarrera() {
        ArrayList<MateriaCarrera> matCarrera = new ArrayList();

        sql = "SELECT * FROM public.\"Materias_Carrera\" ;";

        try {
            resultado = sentencia.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println("ERROR EN LA CONSULTA");
        } catch (NullPointerException e) {
            System.out.println("No tiene datos " + e.getMessage());
        }

        try {
            while (resultado.next()) {
                MateriaCarrera matCarr = new MateriaCarrera();

                matCarr.setId_mat_carrera(resultado.getInt("id_mat_carrera"));
                matCarr.setCiclo_mat_carrera(resultado.getInt("ciclo_mat_carrera"));
                matCarr.setFk_carrera(resultado.getInt("fk_carrera"));
                matCarrera.add(matCarr);
            }
        } catch (SQLException e) {
            System.out.println("Error al traeer materias: " + e.getMessage());
        } catch (NullPointerException e) {
            System.out.println("No tiene datos " + e.getMessage());
        }

        return matCarrera;
    }

    public static void insertarMateriasCarrera(MateriaCarrera nuevaMatCarrera) {
        sql = "INSERT INTO public.\"Materias_Carrera\"(\n"
                + "	ciclo_mat_carrera, fk_carrera)\n"
                + "	VALUES (" + nuevaMatCarrera.getCiclo_mat_carrera() + "," + nuevaMatCarrera.getFk_carrera() + ");";

        ejecutar(sql);
    }

    public static ArrayList<MateriasCiclo> cargarMateriasCiclo(boolean condicion, int fk_mat_carrera) {
        ArrayList<MateriasCiclo> matCiclo = new ArrayList();

        String con = "";
        if (condicion) {
            con = "WHERE mat_carrera_elim=false and fk_mat_carrera = "+fk_mat_carrera;
        }

        sql = "SELECT * FROM public.\"Materias_Ciclo\" " + con + ";";

        try {
            resultado = sentencia.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println("ERROR EN LA CONSULTA");
        } catch (NullPointerException e) {
            System.out.println("No tiene datos " + e.getMessage());
        }

        try {
            while (resultado.next()) {
                MateriasCiclo matCi = new MateriasCiclo();

                matCi.setId_mat_ciclo(resultado.getInt("id_mat_ciclo"));
                matCi.setFk_mat_carrera(resultado.getInt("fk_mat_carrera"));
                matCi.setFk_materia(resultado.getInt("fk_materia"));
                matCi.setHoras_materia(resultado.getInt("horas_materia"));
                matCi.setMat_carrera_elim(resultado.getBoolean("mat_carrera_elim"));
                matCiclo.add(matCi);
            }
        } catch (SQLException e) {
            System.out.println("Error al traer materias de la tabla ciclo: " + e.getMessage());
        } catch (NullPointerException e) {
            System.out.println("No tiene datos " + e.getMessage());
        }

        return matCiclo;
    }

    public static void insertarMateriasCiclo(MateriasCiclo nuevaMatCiclo) {
        sql = "INSERT INTO public.\"Materias_Ciclo\"(\n"
                + "	fk_materia, fk_mat_carrera, horas_materia)\n"
                + "	VALUES ( " + nuevaMatCiclo.getFk_materia() + "," + nuevaMatCiclo.getFk_mat_carrera()
                + "," + nuevaMatCiclo.getHoras_materia() + ");";

        ejecutar(sql);
    }

    public static void editarMateriasCiclo(MateriasCiclo nuevaMatCiclo) {
        sql = "UPDATE public.\"Materias_Ciclo\"\n"
                + "SET fk_mat_carrera = " + nuevaMatCiclo.getFk_mat_carrera() + ",  horas_materia = "
                + nuevaMatCiclo.getHoras_materia() +", fk_materia = " + nuevaMatCiclo.getFk_materia()+ " \n"
                + " WHERE id_mat_ciclo = " + nuevaMatCiclo.getId_mat_ciclo() + ";";

        ejecutar(sql);
    }

    public static void eliminarMateriasCiclo(int id_materia_ciclo, boolean elim) {
        sql = "UPDATE public.\"Materias_Ciclo\"\n"
                + "SET mat_carrera_elim='" + elim + "'\n"
                + "WHERE id_mat_ciclo=" + id_materia_ciclo + ";";

        ejecutar(sql);
    }
}
