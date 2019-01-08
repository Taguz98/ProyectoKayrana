/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DistributivoVJohnny.modelo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Usuario
 */
public class DBMaterias extends Materias {

    BDConect bd = new BDConect();

    public ArrayList<Materias> consultarMateriasCarrera(int carrera) {
        ArrayList<Materias> materias = new ArrayList();

        try {
            String sql = "SELECT nombre_materia, horas_materia, ciclo_mat_carrera, id_materia\n"
                    + "FROM public.\"Materias_Carrera\", public.\"Materias_Ciclo\", public.\"Materias\" \n"
                    + "WHERE materia_elim = false AND mat_carrera_elim = false\n"
                    + "AND fk_carrera = " + carrera + " AND fk_mat_carrera = id_mat_carrera\n"
                    + "AND id_materia = fk_materia\n"
                    + "ORDER BY ciclo_mat_carrera;  ";

            ResultSet rs = bd.sql(sql);

            while (rs.next()) {

                Materias mat = new Materias();

                mat.setCiclo(rs.getInt("ciclo_mat_carrera"));
                mat.setHoras(rs.getInt("horas_materia"));
                mat.setId(rs.getInt("id_materia"));
                mat.setNombre(rs.getString("nombre_materia"));

                materias.add(mat);
            }
            bd.st.close();
            return materias;
        } catch (SQLException e) {
            System.out.println("Se produjo un error al consultar materias. " + e.getMessage());
            return null;
        }

    }

    public ArrayList<Materias> consultarMateriasDocente(int idCarrera, String cedula) {
        ArrayList<Materias> materias = new ArrayList();

        try {
            String sql = "SELECT nombre_materia, horas_materia, ciclo_mat_carrera, id_materia\n"
                    + "FROM public.\"Materias_Carrera\" , public.\"Materias_Ciclo\", public.\"Materias\", \n"
                    + "public.\"Materias_Preferentes\" \n"
                    + "WHERE materia_elim = false AND mat_carrera_elim = false\n"
                    + "AND fk_carrera = " + idCarrera + " AND fk_mat_carrera = id_mat_carrera\n"
                    + "AND id_materia = fk_materia AND fk_docente = '" + cedula + "'\n"
                    + "AND fk_materia = fk_materias\n"
                    + "ORDER BY ciclo_mat_carrera; ";

            ResultSet rs = bd.sql(sql);

            while (rs.next()) {

                Materias mat = new Materias();

                mat.setCiclo(rs.getInt("ciclo_mat_carrera"));
                mat.setHoras(rs.getInt("horas_materia"));
                mat.setId(rs.getInt("id_materia"));
                mat.setNombre(rs.getString("nombre_materia"));

                materias.add(mat);
            }
            //bd.st.close();
            return materias;
        } catch (SQLException e) {
            System.out.println("Se produjo un error al consultar materias. " + e.getMessage());
            return null;
        }
    }
    
    //Consultamos las materias de un docente en un ciclo determinado
    public ArrayList<Materias> consultarMateriasDocenteCiclo(int idCarrera, String cedula, int ciclo) {
        ArrayList<Materias> materias = new ArrayList();

        try {
            String sql = "SELECT nombre_materia, horas_materia, ciclo_mat_carrera, id_materia\n"
                    + "FROM public.\"Materias_Carrera\", public.\"Materias_Ciclo\", public.\"Materias\",\n"
                    + "public.\"Materias_Preferentes\"\n"
                    + "WHERE materia_elim = false AND mat_carrera_elim = false\n"
                    + "AND fk_carrera = "+idCarrera+" AND fk_mat_carrera = id_mat_carrera\n"
                    + "AND id_materia = fk_materia AND fk_docente = '"+cedula+" '\n"
                    + "AND fk_materia = fk_materias AND ciclo_mat_carrera = "+ciclo+";";

            ResultSet rs = bd.sql(sql);

            while (rs.next()) {

                Materias mat = new Materias();

                mat.setCiclo(rs.getInt("ciclo_mat_carrera"));
                mat.setHoras(rs.getInt("horas_materia"));
                mat.setId(rs.getInt("id_materia"));
                mat.setNombre(rs.getString("nombre_materia"));
                
                System.out.println("Este docente consultamos: "+cedula);
                System.out.println(mat.getNombre()+" "+mat.getCiclo());

                materias.add(mat);
            }
            //bd.st.close();
            return materias;
        } catch (SQLException e) {
            System.out.println("Se produjo un error al consultar materias de un docente en un ciclo. " + e.getMessage());
            return null;
        }
    }

}
