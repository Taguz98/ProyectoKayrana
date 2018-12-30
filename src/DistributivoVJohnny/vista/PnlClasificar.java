/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DistributivoVJohnny.vista;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTable;

/**
 *
 * @author Usuario
 */
public class PnlClasificar extends javax.swing.JPanel {

    /**
     * Creates new form pnlClasificar
     */
    public PnlClasificar() {
        initComponents();
    }

    public JComboBox<String> getCbCiclo() {
        return cbCiclo;
    }

    public JComboBox<String> getCbJornada() {
        return cbJornada;
    }

    public JComboBox<String> getCbParalelo() {
        return cbParalelo;
    }

    public JTable getTblHorario() {
        return tblHorario;
    }

    public JTable getTblParalelosCiclo() {
        return tblParalelosCiclo;
    }

    public JTable getTblParalelosJornada() {
        return tblParalelosJornada;
    }

    public JLabel getLblHorasClase() {
        return lblHorasClase;
    }

    public void setLblHorasClase(JLabel lblHorasClase) {
        this.lblHorasClase = lblHorasClase;
    }

    public JLabel getLblNumDocentes() {
        return lblNumDocentes;
    }

    public void setLblNumDocentes(JLabel lblNumDocentes) {
        this.lblNumDocentes = lblNumDocentes;
    }

    public JLabel getLblNumMaterias() {
        return lblNumMaterias;
    }

    public void setLblNumMaterias(JLabel lblNumMaterias) {
        this.lblNumMaterias = lblNumMaterias;
    }
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel3 = new javax.swing.JLabel();
        cbCiclo = new javax.swing.JComboBox<>();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblParalelosCiclo = new javax.swing.JTable();
        cbJornada = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblHorario = new javax.swing.JTable();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblParalelosJornada = new javax.swing.JTable();
        cbParalelo = new javax.swing.JComboBox<>();
        lblHorasClase = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        lblNumMaterias = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        lblNumDocentes = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel3.setText("<html> Elija un ciclo:</html>");
        add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 100, 20));

        cbCiclo.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cbCiclo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        add(cbCiclo, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 100, 30));

        tblParalelosCiclo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null},
                {null},
                {null},
                {null}
            },
            new String [] {
                "Paralelos"
            }
        ));
        jScrollPane3.setViewportView(tblParalelosCiclo);

        add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 10, 190, 100));

        cbJornada.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cbJornada.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        add(cbJornada, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 40, 130, 30));

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel4.setText("<html> Elija una jornada:</html>");
        add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 10, 130, 20));

        tblHorario.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Hora", "Lunes", "Martes", "Miercoles", "Jueves", "Viernes"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane4.setViewportView(tblHorario);

        add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 200, 660, 209));
        add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 120, 700, 10));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel2.setText("Tabla para armar el horario de los docentes:");
        add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, 320, 30));

        tblParalelosJornada.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null},
                {null},
                {null},
                {null}
            },
            new String [] {
                "Paralelos"
            }
        ));
        jScrollPane5.setViewportView(tblParalelosJornada);

        add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 10, 190, 100));

        cbParalelo.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cbParalelo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        add(cbParalelo, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 130, 190, 30));

        lblHorasClase.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        lblHorasClase.setText("0");
        add(lblHorasClase, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 160, 40, 30));

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel7.setText("Horas clase:");
        add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, 90, 30));

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel8.setText("# materias:");
        add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 160, 90, 30));

        lblNumMaterias.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        lblNumMaterias.setText("0");
        add(lblNumMaterias, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 160, 40, 30));

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel10.setText("# docentes:");
        add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 160, 90, 30));

        lblNumDocentes.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        lblNumDocentes.setText("0");
        add(lblNumDocentes, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 160, 40, 30));

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel9.setText("<html> Elija un paralelo:</html>");
        add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 130, 130, 30));
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cbCiclo;
    private javax.swing.JComboBox<String> cbJornada;
    private javax.swing.JComboBox<String> cbParalelo;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblHorasClase;
    private javax.swing.JLabel lblNumDocentes;
    private javax.swing.JLabel lblNumMaterias;
    private javax.swing.JTable tblHorario;
    private javax.swing.JTable tblParalelosCiclo;
    private javax.swing.JTable tblParalelosJornada;
    // End of variables declaration//GEN-END:variables
}
