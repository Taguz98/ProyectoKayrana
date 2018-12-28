/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DistributivoVJohnny.controlador;

import DistributivoVJohnny.vista.Distributivo;

/**
 *
 * @author Usuario
 */
public class distri {

    public static void main(String args[]) {
        iniciarModelo();

        //Creamos un objeto distributivo y tambien un objeto que los controles 
        Distributivo distri = new Distributivo();

        CTRDistributivo ctrDistri = new CTRDistributivo(distri);
        //Llamamos al inicar ctrDsitri para que asigne todos los btns 
        ctrDistri.iniciar();

    }

    public static void iniciarModelo() {
        System.out.println("Iniciaremos el estilo de windows");
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Distributivo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Distributivo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Distributivo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Distributivo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }

}
