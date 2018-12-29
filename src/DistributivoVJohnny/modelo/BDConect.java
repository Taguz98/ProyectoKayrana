/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DistributivoVJohnny.modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Usuario
 */
public class BDConect {
    
    Connection ct; 
    Statement st; 
    
    //Mis datos para la coneccion  
    private String url = "jdbc:postgresql://localhost:5432/KayranaDB";
    private String user = "postgres"; 
    private String pass = "Holapostgres";
    
    public BDConect(){ 
        try {
            //Importamos el driver 
            Class.forName("org.postgresql.Driver");
            //Nos conectamos a la base de datos.
            ct = DriverManager.getConnection(url, user, pass);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("No pudimos conectarnos en distributivo. "+e.getMessage());
        }
    }
    
    //Este metodo recibe como parametro un string sql  
    //Nos devolvera una exepcion si no se puede realizar la transaccion.
    //Nos devuelve null si se realizo correctamente
    public SQLException noQuery(String nsql){  
        try {
            st = ct.createStatement(); 
            //Ejecutamos el n=comando sql  
            st.execute(nsql); 
            //Y cerramos 
            st.close(); 
            //Si todo salio bien retornamos null 
            return null; 
            
        } catch (SQLException e) {
            System.out.println("No pudimos realizar la insersion "+e.getMessage());
            //Si ocurre un error retornamos el error 
            return e; 
        }
    }
    
    
    
    public ResultSet sql(String sql){ 
        //Creamos el resulado que sera devuelto 
        ResultSet rs; 
        try {
            st = ct.createStatement(); 
            //Ejecutamos el query y lo gaurdamos en un objeto resultSet  
            rs = st.executeQuery(sql); 
            //st.close(); 
            //Retornamos el objeto resulset 
            return rs; 
        } catch (SQLException e) {
            System.out.println("No pudimos hacer la consulta. "+e.getMessage());
            return null; 
        }
    }
    
}
