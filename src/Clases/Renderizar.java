/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Johnny
 */
public class Renderizar extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean selected, boolean focused, int row, int column) {
        setBackground(Color.white);
        table.setForeground(Color.black);
        super.getTableCellRendererComponent(table, value, selected, focused, row, column);
        int columna = 0;
        if (table.getValueAt(row, columna).equals("A")) {
            this.setForeground(Color.RED);
        } else if (table.getValueAt(row, columna).equals("B")) {
            this.setForeground(Color.BLUE);
        } else if (table.getValueAt(row, columna).equals("C")) {
            this.setForeground(Color.GREEN);
        }
        return this;
    }
}
