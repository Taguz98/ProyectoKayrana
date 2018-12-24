/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BaseDatos;

import static BaseDatos.Conexion_Consultas.ejecutar;
import static BaseDatos.Conexion_Consultas.resultado;
import static BaseDatos.Conexion_Consultas.sentencia;
import Clases.Materia;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Johnny
 */
public class SQLMateria {

    private static String sql;

    public static ArrayList<Materia> cargarMaterias(boolean condicion) {

        ArrayList<Materia> materiasDB = new ArrayList();
        String con = "";
        
        if(condicion){
            con = "WHERE materia_elim=false";
        }
        
        sql = "SELECT * FROM public.\"Materias\"  " + con + " ;";

        try {
            resultado = sentencia.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println("ERROR EN LA CONSULTA");
        } catch (NullPointerException e) {
            System.out.println("No tiene datos " + e.getMessage());
        }

        try {
            while (resultado.next()) {
                Materia mat = new Materia();

                mat.setId_materia(resultado.getInt("id_materia"));
                mat.setNombre_materia(resultado.getString("nombre_materia"));
                mat.setMateria_elim(resultado.getBoolean("materia_elim"));

                materiasDB.add(mat);
            }
        } catch (SQLException e) {
            System.out.println("Error al traeer materias: " + e.getMessage());
        } catch (NullPointerException e) {
            System.out.println("No tiene datos " + e.getMessage());
        }

        return materiasDB;
    }

    public static void insertarMateria(Materia nuevaMateria) {

        sql = "INSERT INTO public.\"Materias\"(\n"
                + " nombre_materia)\n"
                + "VALUES ( '" + nuevaMateria.getNombre_materia() + "');";

        ejecutar(sql);

        //actualizarBD();
    }

    public static void editarMateria(Materia editarMateria) {

        sql = "UPDATE public.\"Materias\"\n"
                + "SET nombre_materia='" + editarMateria.getNombre_materia() + "'"
                + "WHERE id_materia=" + editarMateria.getId_materia() + ";";

        ejecutar(sql);

        //actualizarBD();
    }

    public static void eliminarMateria(int id_materia, boolean elim) {

        sql = "UPDATE public.\"Materias\"\n"
                + "SET materia_elim='" + elim + "'"
                + "WHERE id_materia=" + id_materia + ";";

        ejecutar(sql);

        //actualizarBD();
    }
}
