/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaz.Administrador;


import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

//Importamos mi clase de validaciones 
import Clases.Validaciones;
import java.util.ArrayList;
import Clases.Materia;
import BaseDatos.Conexion_Consultas;
import BaseDatos.SQLMateria;

/**
 *
 * @author Johnny
 */
public class MateriasVtn extends javax.swing.JFrame {

    //Variables para mover la ventana  
    private int mousex;
    private int mousey;

    //Creamos una variable para darle un modelo a nuestra tabla 
    private DefaultTableModel modeloTabla;
    private String titulo[] = {"id", "Materias"};
    private String datos[][] = {};

    //Posicion de de la fila en la que dimos click  
    private int posFila;
    //Posicion de la fila al editar  
    private int filaEditar;

    //Para saber si le di click en editar 
    private boolean editando = false;

    //Constructor para las validaciones 
    Validaciones valido = new Validaciones();

    //Variable que usare para saber si di click en la tabla  
    private boolean btnAvilitados = false;

    //Cargamos todas las materias que tenemos en nuestra base de datos. 
    ArrayList<Materia> materias = new ArrayList();
    ArrayList<Materia> materiasF = new ArrayList();

    public MateriasVtn() {
        initComponents();
        //Conectarnos a la base de datos  
        Conexion_Consultas.conectar();
        
        //Le pasamos el modelo a nuestra tabla 
        modeloTabla = new DefaultTableModel(datos, titulo);

        tblMaterias.setModel(modeloTabla);

        actualizarTblMaterias();
        
        //Ocultamos el id de nuestra tabla
        Similitudes.ocultarID(tblMaterias);

        //Modificamos el Header de nuestra tabla  
        Similitudes.tituloTbls(tblMaterias);

        //Ocultamos todos los errores 
        lblErrorMateria.setVisible(false);
        lblErrorNuevaMateria.setVisible(false);

        //Deasbilitamos los botones  
        btnAvilitados = Similitudes.btnsModoInactivo(btnEditar, btnEliminar);
    }

    private void actualizarTblMaterias() {
        modeloTabla.setRowCount(0); 
        //Cargamos toda la informacion de materias en la tabla  
        if (!SQLMateria.cargarMaterias(false).isEmpty()) {
            materias = SQLMateria.cargarMaterias(false);
        }

        if (!SQLMateria.cargarMaterias(true).isEmpty()) {
            materiasF = SQLMateria.cargarMaterias(true);
            for (int i = 0; i < materiasF.size(); i++) {
                Object valores[] = {materiasF.get(i).getId_materia(), materiasF.get(i).getNombre_materia()};
                modeloTabla.addRow(valores);
            }
        }
    }

    public boolean nuevaMateria(String materia) {
        boolean datosNuevos = true;
        String materiaComparar;

        try {
            for (int i = 0; i < materias.size(); i++) {
                materiaComparar = materias.get(i).getNombre_materia();
                if (materia.equalsIgnoreCase(materiaComparar)) {
                    datosNuevos = false;
                    SQLMateria.eliminarMateria(Integer.parseInt(tblMaterias.getValueAt(posFila, 0).toString()), false);
                    actualizarTblMaterias();
                    break;
                }
            }

        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return datosNuevos;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblMaterias = new JTable(){
            @Override 
            public boolean isCellEditable(int rowIndex, int colIndex){
                return false; 
            }
        };
        lblErrorMateria = new javax.swing.JLabel();
        txtMateria = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        lblErrorNuevaMateria = new javax.swing.JLabel();
        btnEliminar = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        btnMenu = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(810, 500));
        setUndecorated(true);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(231, 235, 230));
        jPanel1.setPreferredSize(new java.awt.Dimension(340, 450));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblMaterias.setBackground(new java.awt.Color(231, 235, 230));
        tblMaterias.setFont(new java.awt.Font("Trebuchet MS", 0, 14)); // NOI18N
        tblMaterias.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null}
            },
            new String [] {
                "Materia"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblMaterias.setSelectionBackground(new java.awt.Color(21, 89, 110));
        tblMaterias.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblMateriasMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblMaterias);
        if (tblMaterias.getColumnModel().getColumnCount() > 0) {
            tblMaterias.getColumnModel().getColumn(0).setResizable(false);
        }

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 130, 390, 330));

        lblErrorMateria.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        lblErrorMateria.setForeground(new java.awt.Color(173, 46, 25));
        lblErrorMateria.setText("No puede ingresar caracteres especiales.");
        jPanel1.add(lblErrorMateria, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 70, 300, 20));

        txtMateria.setBackground(new java.awt.Color(51, 51, 51));
        txtMateria.setFont(new java.awt.Font("Trebuchet MS", 0, 16)); // NOI18N
        txtMateria.setOpaque(false);
        jPanel1.add(txtMateria, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 40, 340, -1));

        jLabel2.setFont(new java.awt.Font("Trebuchet MS", 1, 20)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(21, 89, 110));
        jLabel2.setText("Materia: ");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 10, -1, -1));

        lblErrorNuevaMateria.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        lblErrorNuevaMateria.setForeground(new java.awt.Color(173, 46, 25));
        lblErrorNuevaMateria.setText("Ya ingreso esta materia. ");
        jPanel1.add(lblErrorNuevaMateria, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 10, 210, 30));

        btnEliminar.setBackground(new java.awt.Color(118, 125, 127));
        btnEliminar.setFont(new java.awt.Font("Trebuchet MS", 1, 18)); // NOI18N
        btnEliminar.setForeground(new java.awt.Color(255, 255, 255));
        btnEliminar.setText("Eliminar");
        btnEliminar.setToolTipText("");
        btnEliminar.setBorderPainted(false);
        btnEliminar.setContentAreaFilled(false);
        btnEliminar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEliminar.setFocusPainted(false);
        btnEliminar.setOpaque(true);
        btnEliminar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEliminarMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnEliminarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnEliminarMouseExited(evt);
            }
        });
        jPanel1.add(btnEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 90, 110, -1));

        btnGuardar.setBackground(new java.awt.Color(99, 144, 158));
        btnGuardar.setFont(new java.awt.Font("Trebuchet MS", 1, 18)); // NOI18N
        btnGuardar.setForeground(new java.awt.Color(255, 255, 255));
        btnGuardar.setText("Guardar");
        btnGuardar.setToolTipText("");
        btnGuardar.setBorderPainted(false);
        btnGuardar.setContentAreaFilled(false);
        btnGuardar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGuardar.setFocusPainted(false);
        btnGuardar.setOpaque(true);
        btnGuardar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnGuardarMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnGuardarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnGuardarMouseExited(evt);
            }
        });
        jPanel1.add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 90, 110, -1));

        btnEditar.setBackground(new java.awt.Color(118, 125, 127));
        btnEditar.setFont(new java.awt.Font("Trebuchet MS", 1, 18)); // NOI18N
        btnEditar.setForeground(new java.awt.Color(255, 255, 255));
        btnEditar.setText("Editar");
        btnEditar.setToolTipText("");
        btnEditar.setBorderPainted(false);
        btnEditar.setContentAreaFilled(false);
        btnEditar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEditar.setFocusPainted(false);
        btnEditar.setOpaque(true);
        btnEditar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEditarMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnEditarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnEditarMouseExited(evt);
            }
        });
        jPanel1.add(btnEditar, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 90, 100, -1));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Administracion/FondoLapiz3.png"))); // NOI18N
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 810, 460));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 40, 810, 460));

        jPanel2.setBackground(new java.awt.Color(9, 28, 32));
        jPanel2.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jPanel2MouseDragged(evt);
            }
        });
        jPanel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jPanel2MousePressed(evt);
            }
        });
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnMenu.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        btnMenu.setForeground(new java.awt.Color(255, 255, 255));
        btnMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Administracion/icons8_Login_26px_1.png"))); // NOI18N
        btnMenu.setToolTipText("Volver al menu");
        btnMenu.setBorderPainted(false);
        btnMenu.setContentAreaFilled(false);
        btnMenu.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnMenu.setFocusPainted(false);
        btnMenu.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btnMenu.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btnMenu.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Administracion/icons8_Login_26px_2.png"))); // NOI18N
        btnMenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnMenuMouseClicked(evt);
            }
        });
        jPanel2.add(btnMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 0, 40, 40));

        jLabel4.setFont(new java.awt.Font("Trebuchet MS", 1, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Ingresar Materias");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 0, 230, 40));

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Administracion/BanerKay.png"))); // NOI18N
        jLabel11.setToolTipText("");
        jPanel2.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 810, 40));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnEliminarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarMouseClicked
        try {
            //Eliminamos la fila que este selecionada  
            if (posFila >= 0 && btnAvilitados) {
                SQLMateria.eliminarMateria(Integer.parseInt(tblMaterias.getValueAt(posFila, 0).toString()), true);

                modeloTabla.removeRow(posFila);
            }

            //Cambiamos los botones a inactivo
 
            btnAvilitados = Similitudes.btnsModoInactivo(btnEditar, btnEliminar);
            editando = false;

        } catch (IndexOutOfBoundsException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }//GEN-LAST:event_btnEliminarMouseClicked

    private void btnGuardarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarMouseClicked
        boolean guardar = true;

        int id_materia = 0;

        if (materias.size() > 0) {
            id_materia = materias.get(materias.size() - 1).getId_materia();
        }

        id_materia++;

        String materia = txtMateria.getText();
        if (!valido.esLetras(materia)) {
            lblErrorMateria.setVisible(true);
            guardar = false;
        }

        if (!nuevaMateria(materia)) {
            lblErrorNuevaMateria.setVisible(true);
            guardar = false;
        }
        
        if (guardar) {
            if (!editando) {

                //Instanciamos la clase materias 
                Materia clase_materia = new Materia();
                clase_materia.setId_materia(id_materia);
                clase_materia.setNombre_materia(materia);
                clase_materia.setMateria_elim(false);


                SQLMateria.insertarMateria(clase_materia);
            }

            if (editando) {
                modeloTabla.setValueAt(materia, filaEditar, 1);

                Materia clase_materia = new Materia();
                clase_materia.setId_materia(Integer.parseInt(tblMaterias.getValueAt(posFila, 0).toString()));
                clase_materia.setNombre_materia(materia);
                clase_materia.setMateria_elim(false);

                SQLMateria.editarMateria(clase_materia);

                editando = false;
            }

            txtMateria.setText("");
            actualizarTblMaterias(); 
            
            //Cambiamos el estado de los botones nuevamente 
            btnAvilitados = Similitudes.btnsModoInactivo(btnEditar, btnEliminar);

            //Ocultanos nuevamente los errores  
            lblErrorMateria.setVisible(false);
            lblErrorNuevaMateria.setVisible(false);

        }


    }//GEN-LAST:event_btnGuardarMouseClicked

    private void btnEditarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditarMouseClicked
        try {
            if (posFila >= 0 && btnAvilitados) {
                filaEditar = posFila;
                txtMateria.setText(tblMaterias.getValueAt(posFila, 1).toString());

                //Si selecionamos editar deasvilitamos los botones editar y eliminar 
                btnAvilitados = Similitudes.btnsModoInactivo(btnEditar, btnEliminar);

                editando = true;
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }//GEN-LAST:event_btnEditarMouseClicked

    private void btnMenuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMenuMouseClicked
        AdministracionVtn admin = new AdministracionVtn();
        admin.setVisible(true);
        this.dispose();

        Conexion_Consultas.cerrarConexion();
    }//GEN-LAST:event_btnMenuMouseClicked

    private void jPanel2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel2MousePressed
        mousex = evt.getX();
        mousey = evt.getY();
    }//GEN-LAST:event_jPanel2MousePressed

    private void jPanel2MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel2MouseDragged
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();

        this.setLocation(x - mousex, y - mousey);
    }//GEN-LAST:event_jPanel2MouseDragged

    private void tblMateriasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblMateriasMouseClicked
        try {
            posFila = tblMaterias.getSelectedRow();

            //Activamos nuestros botones 
            if (posFila >= 0) { 
                btnAvilitados = Similitudes.btnsModoActivo(btnEditar, btnEliminar);
            }

        } catch (IndexOutOfBoundsException e) {
            System.out.println("Error: " + e.getMessage());
        }

    }//GEN-LAST:event_tblMateriasMouseClicked

    private void btnGuardarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarMouseEntered
        Similitudes.btnEntered(btnGuardar); 
    }//GEN-LAST:event_btnGuardarMouseEntered

    private void btnGuardarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarMouseExited
        Similitudes.btnExited(btnGuardar); 
    }//GEN-LAST:event_btnGuardarMouseExited

    private void btnEditarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditarMouseEntered
        if (btnAvilitados) {
            Similitudes.btnEntered(btnEditar); 
        }
    }//GEN-LAST:event_btnEditarMouseEntered

    private void btnEditarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditarMouseExited
        if (btnAvilitados) {
            Similitudes.btnEntered(btnEditar);
        }
    }//GEN-LAST:event_btnEditarMouseExited

    private void btnEliminarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarMouseEntered
        if (btnAvilitados) {
            Similitudes.btnEntered(btnEliminar);
        }
    }//GEN-LAST:event_btnEliminarMouseEntered

    private void btnEliminarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarMouseExited
        if (btnAvilitados) {
            Similitudes.btnExited(btnEliminar); 
        }
    }//GEN-LAST:event_btnEliminarMouseExited

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MateriasVtn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MateriasVtn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MateriasVtn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MateriasVtn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MateriasVtn().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnMenu;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblErrorMateria;
    private javax.swing.JLabel lblErrorNuevaMateria;
    private javax.swing.JTable tblMaterias;
    private javax.swing.JTextField txtMateria;
    // End of variables declaration//GEN-END:variables
}
