/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Formulario;

import classConectar.conectar;
import com.sun.awt.AWTUtilities;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author jesus
 */
public class Productobuscar extends javax.swing.JFrame {
DefaultTableModel tabla;
    /**
     * Creates new form Productobuscar
     */
    public Productobuscar() {
        initComponents();
        setLocation(500, 70);
        AWTUtilities.setWindowOpaque(this,false);
        cargarlistaproductos("");
    }
    
       String comparar(String cod)
    {
            String cant="";
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM producto WHERE cod_pro='"+cod+"'");
            while(rs.next())
            {
                cant=rs.getString(4);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Productobuscar.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cant;
        
    }
       
        void cargarlistaproductos(String dato){
        String [] Titulo = {"Codigo","Descripcion","Precio","Stock"};
        tabla=new DefaultTableModel(null,Titulo);
            String []Registro= new String[4];
            String mostrar="SELECT * FROM producto WHERE CONCAT (cod_pro,'',descripcion) LIKE '%"+dato+"%'"; 
            Statement st;
                try {
                    st = cn.createStatement();
                    ResultSet rs =st.executeQuery(mostrar);
                    while(rs.next())
                    {
                        Registro[0]=rs.getString("cod_pro");
                        Registro[1]=rs.getString("Descripcion");
                        Registro[2]=rs.getString("precio");
                        Registro[3]=rs.getString("Stock");
                        tabla.addRow(Registro);
                    }
                    tbprod.setModel(tabla);
                } catch (SQLException ex) {
                    Logger.getLogger(Productobuscar.class.getName()).log(Level.SEVERE, null, ex);
                }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        tbprod = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtprod = new javax.swing.JTextField();
        btnmostrar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setPreferredSize(new java.awt.Dimension(676, 301));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tbprod.setBackground(java.awt.Color.black);
        tbprod.setForeground(new java.awt.Color(255, 255, 255));
        tbprod.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbprodMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbprod);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 630, 250));

        jButton1.setBackground(java.awt.Color.black);
        jButton1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Terminar de agregar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 10, 170, 40));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
        jPanel1.setPreferredSize(new java.awt.Dimension(380, 200));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel1.setText("Buscar Productos");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, -1, -1));

        txtprod.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtprodKeyReleased(evt);
            }
        });
        jPanel1.add(txtprod, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 40, 170, 30));

        btnmostrar.setBackground(java.awt.Color.black);
        btnmostrar.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnmostrar.setForeground(new java.awt.Color(255, 255, 255));
        btnmostrar.setText("Mostrar todo");
        btnmostrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnmostrarActionPerformed(evt);
            }
        });
        jPanel1.add(btnmostrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 50, 150, 20));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 670, 340));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tbprodMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbprodMouseClicked
        try {
            DefaultTableModel tabladet = (DefaultTableModel)
            Venta.tbdetbol.getModel();
            String[]  dato=new String[5];

            int  fila = tbprod.getSelectedRow();

            String codins=tbprod.getValueAt(fila, 0).toString();
            String desins=tbprod.getValueAt(fila, 1).toString();
            String preins=tbprod.getValueAt(fila, 2).toString();
            int c=0;
            int j=0;
            String  cant=JOptionPane.showInputDialog("Cuantos productos de este tipo desea ");
            if((cant.equals("")) || (cant.equals("0")))
            {
                JOptionPane.showMessageDialog(this, "Debe ingresar algun valor mayor que 0");
            }
            else
            {
                int canting=Integer.parseInt(cant);
                int comp=Integer.parseInt(comparar(codins));
                if(canting>comp)
                {
                    JOptionPane.showMessageDialog(this,"Ud. no cuenta con el stock apropiado");
                }
                else
                {
                    for(int i=0;i<Venta.tbdetbol.getRowCount();i++)
                    {
                        Object com=Venta.tbdetbol.getValueAt(i,0);
                        if(codins.equals(com))
                        {
                            j=i;
                            Venta.tbdetbol.setValueAt(cant, i, 3);
                            c=c+1;
                            

                        }

                    }
                    if(c==0)
                    {

                        dato[0]=codins;
                        dato[1]=desins;
                        dato[2]=preins;
                        dato[3]=cant;

                        tabladet.addRow(dato);

                        Venta.tbdetbol.setModel(tabladet);
                         cargarlistaproductos("");
                         
                    }
                }

            }

        } catch (Exception e) {
        }
    }//GEN-LAST:event_tbprodMouseClicked

    private void txtprodKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtprodKeyReleased
        // TODO add your handling code here:
        cargarlistaproductos(txtprod.getText());
    }//GEN-LAST:event_txtprodKeyReleased

    private void btnmostrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnmostrarActionPerformed
        // TODO add your handling code here:
        cargarlistaproductos("");
    }//GEN-LAST:event_btnmostrarActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

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
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Productobuscar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Productobuscar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Productobuscar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Productobuscar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Productobuscar().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnmostrar;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tbprod;
    private javax.swing.JTextField txtprod;
    // End of variables declaration//GEN-END:variables
conectar cc= new conectar();
Connection cn = cc.conexion();
}
