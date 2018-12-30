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
public class DBParalelos extends Paralelos {

    public DBParalelos() {

    }

    //Nos conectamos a la base de datos 
    BDConect bd = new BDConect();

    public ArrayList<Paralelos> cargarParalelosCarrera(int idParalelo) {
        //Aqui guardaremos todos los paralelos que encontremos de una carrera
        ArrayList<Paralelos> paralelos = new ArrayList();

        try {
            String sql = "SELECT id_paralelo, nombre_paralelo, ciclo_paralelo \n"
                    + "FROM public.\"Paralelos\" \n"
                    + "WHERE fk_carrera = "+idParalelo+";"; 
            
            ResultSet rs = bd.sql(sql); 
            
            while (rs.next()) {
                Paralelos par = new Paralelos(); 
                
                par.setCiclo(rs.getInt("ciclo_paralelo")); 
                par.setId(rs.getInt("id_paralelo")); 
                par.setNombre(rs.getString("nombre_paralelo")); 
                
                paralelos.add(par); 
                
            }
            
            bd.st.close();
            //Si exiten apralelos retamos el array
            return paralelos; 

        } catch (SQLException e) {
            System.out.println("No pudimos consultar los paralelos de una carrera "+e.getMessage());
            //Si se produce un erro retornamos null
            return null; 
        }

    }
}
