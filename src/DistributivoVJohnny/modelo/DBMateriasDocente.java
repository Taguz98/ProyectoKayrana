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
public class DBMateriasDocente extends MateriasDocente {

    BDConect bd = new BDConect();

    public ArrayList<MateriasDocente> consultarMateriasDocente(int idMateria) {
        ArrayList<MateriasDocente> materias = new ArrayList();

        try {
            String sql = "SELECT cedula_docente, nombre_donce \n"
                    + "FROM public.\"Docentes_Instituto\", public.\"Materias_Preferentes\" \n"
                    + "WHERE cedula_docente = fk_docente AND fk_materias = " + idMateria + " \n"
                    + "AND mat_pref_elim = false AND docentes_insti_elim = false;";
            ResultSet rs = bd.sql(sql);

            while (rs.next()) {
                MateriasDocente mat = new MateriasDocente();

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

    public ArrayList<MateriasDocente> consultarDocenteCarrera(int idCarrera) {
        ArrayList<MateriasDocente> docente = new ArrayList();

        try {
            String sql = "SELECT cedula_docente, nombre_donce \n"
                    + "FROM public.\"Docentes_Instituto\", public.\"Docente_Carrera\" \n"
                    + "WHERE cedula_docente = fk_docente AND fk_carrera = " + idCarrera + " \n"
                    + "AND docentes_insti_elim = false;";
            ResultSet rs = bd.sql(sql);

            while (rs.next()) {
                MateriasDocente mat = new MateriasDocente();

                mat.setCedula(rs.getString("cedula_docente"));
                mat.setNombre(rs.getString("nombre_donce"));

                docente.add(mat);
            }
            return docente;
        } catch (SQLException | NullPointerException e) {
            System.out.println("No se pudo consultar docentes de una carrera." + e.getMessage());
            return null;
        }
    }

}
