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
public class DBJornadas extends Jornadas {

    //Nos conectamos a base de datos  
    BDConect bd = new BDConect();

    public ArrayList<Jornadas> cargaParalelosJornada(int idParalelo) {
        ArrayList<Jornadas> jornadas = new ArrayList();
        try {
            String sql = "SELECT id_deta_jornada, descripcion_jornada, fk_hora_inicio, \n"
                    + "fk_hora_fin, dias_jornada, fk_jornada \n"
                    + "FROM public.\"Paralelos_Jornada\", public.\"Detalle_Jornada\"\n"
                    + "WHERE fk_paralelo = "+idParalelo+" AND id_deta_jornada = fk_deta_jornada;";

            ResultSet rs = bd.sql(sql);

            while (rs.next()) {
                Jornadas jd = new Jornadas();

                jd.setDescripcion(rs.getString("descripcion_jornada"));
                jd.setId(rs.getInt("id_deta_jornada"));
                jd.setDias(rs.getString("dias_jornada"));
                jd.setHoraFin(rs.getInt("fk_hora_fin"));
                jd.setHoraInicio(rs.getInt("fk_hora_inicio")); 
                jd.setJornada(rs.getInt("fk_jornada")); 

                jornadas.add(jd);
            }
            bd.st.close();
            //Si todo salio bien retornamos el array  
            return jornadas;
        } catch (SQLException e) {
            System.out.println("No pudimos consultar las jornadas de una carrera. " + e.getMessage());
            //Si ocurren errores retornamos null 
            return null;
        }

    }

}
