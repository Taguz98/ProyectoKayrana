/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DistributivoVJohnny.modelo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author Usuario
 */
public class DBPeriodoLectivos extends PeriodoLectivos {

    //Nos conectamos a la base de datos  
    BDConect bd = new BDConect();

    public ArrayList<PeriodoLectivos> cargarPeriodoLectivoCarrera() {
        //El array que guardara todos los resultados que me devuelva la consulta 
        ArrayList<PeriodoLectivos> periodos = new ArrayList();

        try {
            String sql = " SELECT id_per_lect, fecha_ini_per_lect, \n"
                    + "	    fecha_fin_per_lect, nombre_carrera,\n"
                    + "	    num_ciclos_carrera, anio_per_lect,"
                    + "     id_carrera  \n"
                    + "FROM public.\"Periodo_Lectivo\", public.\"Carrera\"\n"
                    + "WHERE id_carrera = fk_carrera \n"
                    + "AND per_lect_activo = true\n"
                    + "AND carrera_elim = false;";
            
            ResultSet rs = bd.sql(sql);

            while (rs.next()) {
                //System.out.println("Entramos a llenar el array.");

                PeriodoLectivos per = new PeriodoLectivos();

                per.setAnio(rs.getString("anio_per_lect"));
                per.setCarrera(rs.getString("nombre_carrera"));

                String fechaFin = rs.getString("fecha_fin_per_lect");
                //per.setFechaFin(rs.getObject("fecha_fin_per_lect", Class<LocalDate> fecha)); 
                String fechaFinVec[] = fechaFin.split("-");
                LocalDate fin = LocalDate.of(Integer.parseInt(fechaFinVec[0]),
                        Integer.parseInt(fechaFinVec[1]), Integer.parseInt(fechaFinVec[2]));
                per.setFechaFin(fin);
                //Imprimimos la fecha luego de transformarla 
                //System.out.println("Fecha fin " + fin);

                String fechaIni = rs.getString("fecha_ini_per_lect");
                String fechaIniVec[] = fechaIni.split("-");
                LocalDate ini = LocalDate.of(Integer.parseInt(fechaIniVec[0]),
                        Integer.parseInt(fechaIniVec[1]),
                        Integer.parseInt(fechaIniVec[2]));
                per.setFechaInicio(ini);
                //System.out.println("Fecha inicio: " + ini);

                per.setId(rs.getInt("id_per_lect"));
                per.setIdCarrera(rs.getInt("id_carrera"));
                per.setNumCiclos(rs.getInt("num_ciclos_carrera"));

                //Agregamos el objeto al array  
                periodos.add(per);
            }
            
            //Cerramos el st de database  
            bd.st.close();
            
            //Si no ocurrio ningun error retornamos todos los periodos 
            return periodos;
        } catch (SQLException e) {
            System.out.println("No pudimos realizar la consulta de periodos lectivos. " + e.getMessage());
            //Si ocurre un erro se retornara nulo
            return null;
        }
    }

}
