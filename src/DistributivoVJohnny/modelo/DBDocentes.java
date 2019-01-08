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
public class DBDocentes extends Docentes {

    BDConect bd = new BDConect();

    public ArrayList<Docentes> consultarMateriasDocente(int idMateria) {
        ArrayList<Docentes> materias = new ArrayList();

        try {
            String sql = "SELECT cedula_docente, nombre_donce \n"
                    + "FROM public.\"Docentes_Instituto\", public.\"Materias_Preferentes\" \n"
                    + "WHERE cedula_docente = fk_docente AND fk_materias = " + idMateria + " \n"
                    + "AND mat_pref_elim = false AND docentes_insti_elim = false;";
            ResultSet rs = bd.sql(sql);

            while (rs.next()) {
                Docentes mat = new Docentes();

                mat.setCedula(rs.getString("cedula_docente"));
                mat.setNombre(rs.getString("nombre_donce"));

                materias.add(mat);
            }
            return materias;
        } catch (SQLException e) {
            System.out.println("No se pudo consultar docentes de una materia." + e.getMessage());
            return null;
        }
    }

    public ArrayList<Docentes> consultarDocenteCarrera(int idCarrera) {
        ArrayList<Docentes> docente = new ArrayList();

        try {
            String sql = "SELECT cedula_docente, nombre_donce \n"
                    + "FROM public.\"Docentes_Instituto\", public.\"Docente_Carrera\" \n"
                    + "WHERE cedula_docente = fk_docente AND fk_carrera = " + idCarrera + " \n"
                    + "AND docentes_insti_elim = false;";
            ResultSet rs = bd.sql(sql);

            while (rs.next()) {
                Docentes dc = new Docentes();

                dc.setCedula(rs.getString("cedula_docente"));
                dc.setNombre(rs.getString("nombre_donce"));
                //Consultamos todas las materias preferentes de un docente  
                DBMaterias mt = new DBMaterias();
                dc.setMateriasPref(mt.consultarMateriasDocente(idCarrera, dc.getCedula()));

                docente.add(dc);
            }
            return docente;
        } catch (SQLException | NullPointerException e) {
            System.out.println("No se pudo consultar docentes de una carrera." + e.getMessage());
            return null;
        }
    }
    
    //Aqui consultamos los docentes de una carrera en un ciclo determinado 
    public ArrayList<Docentes> consultarDocenteCarreraCiclo(int idCarrera, int ciclo) {
        ArrayList<Docentes> docentes = new ArrayList();

        try {
            String sql = "SELECT cedula_docente, nombre_donce\n"
                    + "FROM public.\"Docentes_Instituto\", public.\"Materias_Preferentes\",\n"
                    + "public.\"Materias_Carrera\", public.\"Materias_Ciclo\",\n"
                    + "public.\"Docente_Carrera\"\n"
                    + "WHERE \"Materias_Carrera\".fk_carrera = "+idCarrera+" AND ciclo_mat_carrera = "+ciclo+"\n"
                    + "AND fk_mat_carrera = id_mat_carrera AND fk_materias = fk_materia\n"
                    + "AND cedula_docente = \"Materias_Preferentes\".fk_docente \n"
                    + "AND cedula_docente = \"Docente_Carrera\".fk_docente \n"
                    + "AND docentes_insti_elim = false AND \"Docente_Carrera\".fk_carrera = "+idCarrera+"\n"
                    + "GROUP BY cedula_docente;";
            ResultSet rs = bd.sql(sql);

            while (rs.next()) {
                Docentes dc = new Docentes();

                dc.setCedula(rs.getString("cedula_docente"));
                dc.setNombre(rs.getString("nombre_donce"));
                //Consultamos todas las materias preferentes de un docente  
                DBMaterias mt = new DBMaterias();
                //dc.setMateriasPref(mt.consultarMateriasDocenteCiclo(idCarrera, dc.getCedula(), ciclo));
                dc.setMateriasPref(mt.consultarMateriasDocente(idCarrera, dc.getCedula()));
                
                docentes.add(dc);
            }
            return docentes;
        } catch (SQLException | NullPointerException e) {
            System.out.println("No se pudo consultar docentes de una carrera." + e.getMessage());
            return null;
        }
    }

}
