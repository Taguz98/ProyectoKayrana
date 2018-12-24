/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaz.Administrador;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author Johnny
 */
public class Similitudes {

    //Para ocultar el id de todas mis tablas 
    public static void ocultarID(JTable tbl) {
        TableColumnModel columna = tbl.getColumnModel();

        columna.getColumn(0).setPreferredWidth(0);
        columna.getColumn(0).setWidth(0);
        columna.getColumn(0).setMinWidth(0);
        columna.getColumn(0).setMaxWidth(0);
    }

    public static void limpiarTbl(DefaultTableModel tbl) {
        tbl.setRowCount(0);
    }

    public static void tituloTbls(JTable tbl) {
        JTableHeader tituloTbls = tbl.getTableHeader();

        tituloTbls.setBackground(new Color(50, 60, 63));
        tituloTbls.setForeground(new Color(255, 255, 255));
        tituloTbls.setOpaque(false);
        tituloTbls.setFont(new Font("Verdana", Font.BOLD, 12));
        tituloTbls.setReorderingAllowed(false);
    }

    public static boolean btnsModoInactivo(JButton btnEditar, JButton btnEliminar) {
        //Cambiamos los botones a inactivos
        btnEditar.setBackground(new Color(118, 125, 127));
        btnEliminar.setBackground(new Color(118, 125, 127));

        btnEditar.setEnabled(false);
        btnEliminar.setEnabled(false);

        return false;
    }

    public static boolean btnsModoActivo(JButton btnEditar, JButton btnEliminar) {
        btnEditar.setBackground(new Color(99, 144, 158));
        btnEliminar.setBackground(new Color(99, 144, 158));

        btnEditar.setEnabled(true);
        btnEliminar.setEnabled(true);

        return true;
    }

    //Cambiar en color de un boton cuando entras un color azulito mas claro 
    public static void btnEntered(JButton btn) {
        btn.setBackground(new Color(21, 89, 110));
    }

    //Cambiar el color de boton cuando sales un color mas oscuro. 
    public static void btnExited(JButton btn) {
        btn.setBackground(new Color(99, 144, 158));
    }

    //Para el menu de navegacion, para cambiar los paneles 
    public static void cambioPanel(JPanel panelPadre, JPanel panelHijo) {
        panelPadre.removeAll();
        panelPadre.repaint();
        panelPadre.revalidate();

        panelPadre.add(panelHijo);
        panelPadre.repaint();
        panelPadre.revalidate();
    }

}
