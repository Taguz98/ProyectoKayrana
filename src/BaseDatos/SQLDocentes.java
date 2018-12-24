/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BaseDatos;

import static BaseDatos.Conexion_Consultas.resultado;
import static BaseDatos.Conexion_Consultas.sentencia;
import Clases.ActividadesDocente;
import Clases.DocenteCarrera;
import Clases.Docente_Instituto;
import Clases.JornadaDocente;
import Clases.MateriasDocente;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Johnny
 */
public class SQLDocentes {

    private static String sql;

    public static Docente_Instituto consultarDocenteInsti(String ci) {
        Docente_Instituto docen = null;

        sql = "SELECT * FROM public.\"Docentes_Instituto\" WHERE  docentes_insti_elim = '" + ci + "' ;";

        try {
            resultado = sentencia.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println("ERROR EN LA CONSULTA");
        } catch (NullPointerException e) {
            System.out.println("No tiene datos " + e.getMessage());
        }

        try {
            while (resultado.next()) {
                Docente_Instituto docente = new Docente_Instituto();

                docente.setCedula_docente(resultado.getString("cedula_docente"));
                docente.setNombre_docente(resultado.getString("nombre_donce"));
                docente.setDocente_insti_elim(resultado.getBoolean("docentes_insti_elim"));

                docen = docente;

            }
        } catch (SQLException e) {
            System.out.println("Error al traeer docentes del instituto: " + e.getMessage());
        } catch (NullPointerException e) {
            System.out.println("No tiene datos " + e.getMessage());
        }

        return docen;
    }

    public static ArrayList<Docente_Instituto> cargarDocentesInstituo(boolean condi) {
        ArrayList<Docente_Instituto> docentesInsti = new ArrayList();

        String codicion = "";
        if (condi) {
            codicion = "WHERE  docentes_insti_elim = false";
        }

        sql = "SELECT * FROM public.\"Docentes_Instituto\"   " + codicion + " ;";

        try {
            resultado = sentencia.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println("ERROR EN LA CONSULTA");
        } catch (NullPointerException e) {
            System.out.println("No tiene datos " + e.getMessage());
        }

        try {
            while (resultado.next()) {
                Docente_Instituto docente = new Docente_Instituto();

                docente.setCedula_docente(resultado.getString("cedula_docente"));
                docente.setNombre_docente(resultado.getString("nombre_donce"));
                docente.setDocente_insti_elim(resultado.getBoolean("docentes_insti_elim"));

                docentesInsti.add(docente);
            }
        } catch (SQLException e) {
            System.out.println("Error al traeer docentes del instituto: " + e.getMessage());
        } catch (NullPointerException e) {
            System.out.println("No tiene datos " + e.getMessage());
        }

        return docentesInsti;
    }

    public static void insertarDocenteInsti(Docente_Instituto nuevoDoncete) {
        sql = "INSERT INTO public.\"Docentes_Instituto\"(\n"
                + "	cedula_docente, nombre_donce)\n"
                + "	VALUES ('" + nuevoDoncete.getCedula_docente() + "', '" + nuevoDoncete.getNombre_docente() + "');";

        Conexion_Consultas.ejecutar(sql);
    }

    public static void editarDocenteInsti(Docente_Instituto nuevoDoncete) {
        sql = "UPDATE public.\"Docentes_Instituto\"\n"
                + "SET nombre_donce='" + nuevoDoncete.getNombre_docente() + "'\n"
                + "WHERE cedula_docente='" + nuevoDoncete.getCedula_docente() + "';";

        Conexion_Consultas.ejecutar(sql);
    }

    public static void eliminarDocenteInsti(String cedula, boolean elim) {
        sql = "UPDATE public.\"Docentes_Instituto\"\n"
                + "	SET docentes_insti_elim = " + elim + " "
                + "	WHERE cedula_docente = '" + cedula + "';";

        Conexion_Consultas.ejecutar(sql);
    }

    public static ActividadesDocente consultarActividadDocente(String cedula, int id_actividad) {
        ActividadesDocente act = null;

        sql = "SELECT * FROM public.\"Actividades_Docente\"  WHERE fk_docente = '" + cedula + "' and fk_actividad = " + id_actividad + ";";

        try {
            resultado = sentencia.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println("ERROR EN LA CONSULTA");
        } catch (NullPointerException e) {
            System.out.println("No tiene datos " + e.getMessage());
        }

        try {
            while (resultado.next()) {
                ActividadesDocente actDocen = new ActividadesDocente();

                System.out.println("Encontramos una actividad ");

                actDocen.setId_actividad_docen(resultado.getInt("id_actividad_docente"));
                actDocen.setFk_docente(resultado.getString("fk_docente"));
                actDocen.setFk_actividad(resultado.getInt("fk_actividad"));
                actDocen.setAct_donce_elim(resultado.getBoolean("act_donce_elim"));

                act = actDocen;
            }
            
        } catch (SQLException e) {
            System.out.println("Error al traer una actividad de un docente: " + e.getMessage());
        } catch (NullPointerException e) {
            System.out.println("No tiene datos " + e.getMessage());
        }
        return act;
    }

    public static ArrayList<ActividadesDocente> cargarActividadesDocente(boolean condicion) {
        ArrayList<ActividadesDocente> actividadesDocente = new ArrayList();

        String con = "";
        if (condicion) {
            con = "WHERE act_donce_elim = false";
        }

        sql = "SELECT * FROM public.\"Actividades_Docente\"   " + con + " ;";

        try {
            resultado = sentencia.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println("ERROR EN LA CONSULTA");
        } catch (NullPointerException e) {
            System.out.println("No tiene datos " + e.getMessage());
        }

        try {
            while (resultado.next()) {
                ActividadesDocente actDocen = new ActividadesDocente();

                actDocen.setId_actividad_docen(resultado.getInt("id_actividad_docente"));
                actDocen.setFk_docente(resultado.getString("fk_docente"));
                actDocen.setFk_actividad(resultado.getInt("fk_actividad"));
                actDocen.setAct_donce_elim(resultado.getBoolean("act_donce_elim"));

                actividadesDocente.add(actDocen);
            }
        } catch (SQLException e) {
            System.out.println("Error al traeer actividades de un docente: " + e.getMessage());
        } catch (NullPointerException e) {
            System.out.println("No tiene datos " + e.getMessage());
        }

        return actividadesDocente;
    }

    public static void insertarActividadesDocentes(ActividadesDocente nuevoActDocen) {
        sql = "INSERT INTO public.\"Actividades_Docente\"(\n"
                + "	 fk_actividad, fk_docente, act_donce_elim)\n"
                + "	VALUES ( " + nuevoActDocen.getFk_actividad() + ",'" + nuevoActDocen.getFk_docente() + "' , false);";

        Conexion_Consultas.ejecutar(sql);
    }

    public static void editarActividadesDocentes(ActividadesDocente nuevoActDocen) {
        sql = "UPDATE public.\"Actividades_Docente\"\n"
                + "SET fk_actividad = " + nuevoActDocen.getFk_actividad()
                + ", fk_docente = '" + nuevoActDocen.getFk_docente() + "' \n"
                + "WHERE id_actividad_docente=" + nuevoActDocen.getId_actividad_docen() + ";";

        Conexion_Consultas.ejecutar(sql);
    }

    public static void eliminarActividadesDocentes(int id_act_docen, boolean elim) {

        sql = "UPDATE public.\"Actividades_Docente\"\n"
                + "SET act_donce_elim = " + elim + " \n"
                + "WHERE id_actividad_docente=" + id_act_docen + ";";
        Conexion_Consultas.ejecutar(sql);
    }

    public static JornadaDocente consultarJornadaDocen(String cedula, int id_det_jord) {
        JornadaDocente jdr = null;

        sql = "SELECT * FROM public.\"Jornada_Docente\" WHERE fk_docente_instituto = '" + cedula + "'  and  fk_deta_jornada = " + id_det_jord + " ;";

        try {
            resultado = sentencia.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println("ERROR EN LA CONSULTA");
        } catch (NullPointerException e) {
            System.out.println("No tiene datos " + e.getMessage());
        }

        try {
            while (resultado.next()) {
                JornadaDocente jrdDocen = new JornadaDocente();

                jrdDocen.setId_jornada_docen(resultado.getInt("id_jornada_docen"));
                jrdDocen.setFk_docen_insti(resultado.getString("fk_docente_instituto"));
                jrdDocen.setFk_deta_jornd(resultado.getInt("fk_deta_jornada"));
                jrdDocen.setDocen_jord_elim(resultado.getBoolean("docen_jornada_elim"));

                jdr = jrdDocen;
            }
        } catch (SQLException e) {
            System.out.println("Error al traeer jornadas de un docente: " + e.getMessage());
        } catch (NullPointerException e) {
            System.out.println("No tiene datos " + e.getMessage());
        }

        return jdr;
    }

    public static ArrayList<JornadaDocente> cargarJornadaDocente(boolean act) {
        ArrayList<JornadaDocente> jornadasDocente = new ArrayList();

        String con = "";
        if (act) {
            con = " WHERE docen_jornada_elim = false";
        }
        sql = "SELECT * FROM public.\"Jornada_Docente\"  " + con + " ;";

        try {
            resultado = sentencia.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println("ERROR EN LA CONSULTA");
        } catch (NullPointerException e) {
            System.out.println("No tiene datos " + e.getMessage());
        }

        try {
            while (resultado.next()) {
                JornadaDocente jrdDocen = new JornadaDocente();

                jrdDocen.setId_jornada_docen(resultado.getInt("id_jornada_docen"));
                jrdDocen.setFk_docen_insti(resultado.getString("fk_docente_instituto"));
                jrdDocen.setFk_deta_jornd(resultado.getInt("fk_deta_jornada"));
                jrdDocen.setDocen_jord_elim(resultado.getBoolean("docen_jornada_elim"));

                jornadasDocente.add(jrdDocen);
            }
        } catch (SQLException e) {
            System.out.println("Error al traeer jornadas de un docente: " + e.getMessage());
        } catch (NullPointerException e) {
            System.out.println("No tiene datos " + e.getMessage());
        }

        return jornadasDocente;
    }

    public static void insertarJornadaDocente(JornadaDocente nuevaJdrDocen) {
        sql = "INSERT INTO public.\"Jornada_Docente\"(\n"
                + "	 fk_deta_jornada, fk_docente_instituto)\n"
                + "	VALUES ( " + nuevaJdrDocen.getFk_deta_jornd() + ", '" + nuevaJdrDocen.getFk_docen_insti() + "');";

        Conexion_Consultas.ejecutar(sql);
    }

    public static void editarJornadaDocente(JornadaDocente nuevaJdrDocen) {
        sql = "UPDATE public.\"Jornada_Docente\"\n"
                + "SET fk_deta_jornada=" + nuevaJdrDocen.getFk_deta_jornd()
                + ", fk_docente_instituto='" + nuevaJdrDocen.getFk_docen_insti() + "'\n"
                + "WHERE id_jornada_docen = " + nuevaJdrDocen.getId_jornada_docen() + ";";

        Conexion_Consultas.ejecutar(sql);
    }

    public static void eliminarJornadaDocente(int id_jord_docne, boolean elim) {
        sql = "UPDATE public.\"Jornada_Docente\"\n"
                + "SET docen_jornada_elim= " + elim + " \n"
                + "WHERE id_jornada_docen = " + id_jord_docne + ";";

        Conexion_Consultas.ejecutar(sql);
    }

    public static DocenteCarrera consultarCarreraDocen(String cedula, int id_act) {
        DocenteCarrera car = null;

        sql = "SELECT * FROM public.\"Docente_Carrera\" WHERE fk_docente = '" + cedula + "' and  fk_carrera = " + id_act + " ;";

        try {
            resultado = sentencia.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println("ERROR EN LA CONSULTA");
        } catch (NullPointerException e) {
            System.out.println("No tiene datos " + e.getMessage());
        }

        try {
            while (resultado.next()) {
                DocenteCarrera crrDocen = new DocenteCarrera();

                crrDocen.setId_docen_carrera(resultado.getInt("id_docen_carrera"));
                crrDocen.setFk_docente_inst(resultado.getString("fk_docente"));
                crrDocen.setFk_carrera(resultado.getInt("fk_carrera"));
                crrDocen.setDocente_ints_elim(resultado.getBoolean("docen_carrera_elim"));

                car = crrDocen;
            }
        } catch (SQLException e) {
            System.out.println("Error al traeer carreras de un docente: " + e.getMessage());
        } catch (NullPointerException e) {
            System.out.println("No tiene datos " + e.getMessage());
        }

        return car;
    }

    public static ArrayList<DocenteCarrera> cargarCarreraDocente(boolean act) {
        ArrayList<DocenteCarrera> carrerasDocente = new ArrayList();

        String con = "";
        if (act) {
            con = " WHERE docen_carrera_elim = false";
        }

        sql = "SELECT * FROM public.\"Docente_Carrera\" " + con + " ";

        try {
            resultado = sentencia.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println("ERROR EN LA CONSULTA");
        } catch (NullPointerException e) {
            System.out.println("No tiene datos " + e.getMessage());
        }

        try {
            while (resultado.next()) {
                DocenteCarrera crrDocen = new DocenteCarrera();

                crrDocen.setId_docen_carrera(resultado.getInt("id_docen_carrera"));
                crrDocen.setFk_docente_inst(resultado.getString("fk_docente"));
                crrDocen.setFk_carrera(resultado.getInt("fk_carrera"));
                crrDocen.setDocente_ints_elim(resultado.getBoolean("docen_carrera_elim"));

                carrerasDocente.add(crrDocen);
            }
        } catch (SQLException e) {
            System.out.println("Error al traeer carreras de un docente: " + e.getMessage());
        } catch (NullPointerException e) {
            System.out.println("No tiene datos " + e.getMessage());
        }

        return carrerasDocente;
    }

    public static void insertarCarreraDocente(DocenteCarrera nuevaCarreraDocen) {
        sql = "INSERT INTO public.\"Docente_Carrera\"(\n"
                + " fk_carrera, fk_docente)\n"
                + "VALUES (" + nuevaCarreraDocen.getFk_carrera() + ", '" + nuevaCarreraDocen.getFk_docente_inst() + "');";

        Conexion_Consultas.ejecutar(sql);
    }

    public static void editarCarreraDocente(DocenteCarrera nuevaCarreraDocen) {
        sql = "UPDATE public.\"Docente_Carrera\"\n"
                + "SET fk_carrera=" + nuevaCarreraDocen.getFk_carrera()
                + ", fk_docente='" + nuevaCarreraDocen.getFk_docente_inst() + "'\n"
                + "WHERE id_docen_carrera =" + nuevaCarreraDocen.getId_docen_carrera() + ";";

        Conexion_Consultas.ejecutar(sql);
    }

    public static void eliminarCarreraDocente(int id_carrera_docen, boolean elim) {
        sql = "UPDATE public.\"Docente_Carrera\" \n"
                + "SET docen_carrera_elim = " + elim + " \n"
                + "WHERE id_docen_carrera = " + id_carrera_docen + " ;";

        Conexion_Consultas.ejecutar(sql);
    }

    public static MateriasDocente consultarMatDocen(String cedula, int id_materia) {
        MateriasDocente mat = null;

        sql = "SELECT * FROM public.\"Materias_Preferentes\"   WHERE fk_docente = '" + cedula + "' and  fk_materias = " + id_materia + " ;";

        try {
            resultado = sentencia.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println("ERROR EN LA CONSULTA");
        } catch (NullPointerException e) {
            System.out.println("No tiene datos " + e.getMessage());
        }

        try {
            while (resultado.next()) {
                MateriasDocente matDocen = new MateriasDocente();

                matDocen.setId_mat_pref(resultado.getInt("id_mat_pref"));
                matDocen.setFk_docente(resultado.getString("fk_docente"));
                matDocen.setFk_materia(resultado.getInt("fk_materias"));
                matDocen.setMat_pref_elim(resultado.getBoolean("mat_pref_elim"));

                mat = matDocen;
            }
        } catch (SQLException e) {
            System.out.println("Error al traeer materias de un docente: " + e.getMessage());
        } catch (NullPointerException e) {
            System.out.println("No tiene datos " + e.getMessage());
        }

        return mat;
    }

    public static ArrayList<MateriasDocente> cargarMateriasDocente(boolean act) {
        ArrayList<MateriasDocente> materiasDocente = new ArrayList();

        String con = " ";
        if (act) {
            con = "WHERE mat_pref_elim = false";
        }

        sql = "SELECT * FROM public.\"Materias_Preferentes\"   " + con + ";";

        try {
            resultado = sentencia.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println("ERROR EN LA CONSULTA");
        } catch (NullPointerException e) {
            System.out.println("No tiene datos " + e.getMessage());
        }

        try {
            while (resultado.next()) {
                MateriasDocente matDocen = new MateriasDocente();

                matDocen.setId_mat_pref(resultado.getInt("id_mat_pref"));
                matDocen.setFk_docente(resultado.getString("fk_docente"));
                matDocen.setFk_materia(resultado.getInt("fk_materias"));
                matDocen.setMat_pref_elim(resultado.getBoolean("mat_pref_elim"));

                materiasDocente.add(matDocen);
            }
        } catch (SQLException e) {
            System.out.println("Error al traeer materias de un docente: " + e.getMessage());
        } catch (NullPointerException e) {
            System.out.println("No tiene datos " + e.getMessage());
        }

        return materiasDocente;
    }

    public static void insertarMateriasDocente(MateriasDocente nuevaMatDocen) {
        sql = "INSERT INTO public.\"Materias_Preferentes\"(\n"
                + "fk_docente, fk_materias)\n"
                + "VALUES ( '" + nuevaMatDocen.getFk_docente()
                + "', " + nuevaMatDocen.getFk_materia() + ");";

        Conexion_Consultas.ejecutar(sql);
    }

    public static void editarMateriasDocente(MateriasDocente nuevaMatDocen) {
        sql = "UPDATE public.\"Materias_Preferentes\"\n"
                + "SET fk_docente='" + nuevaMatDocen.getFk_docente()
                + "', fk_materias=" + nuevaMatDocen.getFk_materia() + "\n"
                + "WHERE id_mat_pref=" + nuevaMatDocen.getId_mat_pref() + ";";

        Conexion_Consultas.ejecutar(sql);
    }

    public static void eliminarMateriasDocente(int id_mat_docen, boolean elim) {
        sql = "UPDATE public.\"Materias_Preferentes\"\n"
                + "SET  mat_pref_elim= " + elim + " \n"
                + "WHERE id_mat_pref=" + id_mat_docen + ";";

        Conexion_Consultas.ejecutar(sql);
    }
}
