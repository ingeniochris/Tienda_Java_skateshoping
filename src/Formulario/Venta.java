/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Formulario;

import classConectar.conectar;
import com.sun.awt.AWTUtilities;
import java.awt.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author jesus
 */
public class Venta extends javax.swing.JFrame {

    /**
     * Creates new form Venta
     */
    public Venta() {
        initComponents();
        AWTUtilities.setWindowOpaque(this,false);
        setResizable(false); 
        setLocationRelativeTo(ventanaadmin.escritorio);
      
         
         txtfecha.setText(fechaact());
        txtnumbol.setDisabledTextColor(Color.red);
        txtcod.setDisabledTextColor(Color.blue);
        
        txtdni.setDisabledTextColor(Color.blue);
        txtnom.setDisabledTextColor(Color.blue);
        hora();
        numeros();
    }
         
     void hora(){
         Calendar cal = Calendar.getInstance(); 
         String hora;
         
         hora=cal.get(Calendar.HOUR_OF_DAY)+":"+cal.get(Calendar.MINUTE)+":"+cal.get(Calendar.SECOND);
         
         this.labhora.setText(hora);
    }
     public static String fechaact(){
    Date fecha= new Date();
    SimpleDateFormat formatofecha= new SimpleDateFormat("dd/MM/YYYY");
    return formatofecha.format(fecha);
}  
    
    
    
    void descontarstock(String codi,String can)
    {
       int des = Integer.parseInt(can);
       String cap="";
       int desfinal;
       String consul="SELECT * FROM producto WHERE  cod_pro='"+codi+"'";
                try {
                    Statement st= cn.createStatement();
                    ResultSet rs= st.executeQuery(consul);
                    while(rs.next())
                    {
                        cap= rs.getString(4);
                    }


                } catch (Exception e) {
                }
        desfinal=Integer.parseInt(cap)-des;
        String modi="UPDATE producto SET Stock='"+desfinal+"' WHERE cod_pro = '"+codi+"'";
            try {
                PreparedStatement pst = cn.prepareStatement(modi);
                pst.executeUpdate();
            } catch (Exception e) {
            }
    }
         
             void numeros()
    {
        
        
        String c="";
        String SQL="select max(num_bol) from venta";
        //String SQL="select count(*) from boleta";
    //String SQL="SELECT MAX(cod_emp) AS cod_emp FROM empleado";
     //String SQL="SELECT @@identity AS ID";
        try {
            Statement st = cn.createStatement();
            ResultSet rs=st.executeQuery(SQL);
            if(rs.next())
            {              
                 c=rs.getString(1);
            }
            if(c==null){
                txtnumbol.setText("00000001");
            }
            else{
            int j=Integer.parseInt(c);
            GenerarNumero gen= new GenerarNumero();
            gen.generar(j);
             txtnumbol.setText(gen.serie());
            }

        } catch (SQLException ex) {
           Logger.getLogger(Venta.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
             
    void calcular()
    {
        String pre;
        String can;
        double igv=0;
        double total=0;
        double subtotal=0;
        double precio;
        int cantidad;
        double imp=1.5;
        
            /*can=Integer.parseInt(cant);
            imp=pre*can;
            dato[4]=Float.toString(imp);*/
        
        for(int i=0;i<tbdetbol.getRowCount();i++)
        {
            pre=tbdetbol.getValueAt(i, 2).toString();
            can=tbdetbol.getValueAt(i, 3).toString();
            precio=Double.parseDouble(pre);
            cantidad=Integer.parseInt(can);
            imp=precio*cantidad;
            subtotal=subtotal+imp;
            
            tbdetbol.setValueAt(Math.rint(imp*100)/100, i, 4);
            
        }
       
        txttotal.setText(""+Math.rint(subtotal*100)/100);
        
            
    }
                
    void venta(){
    String InsertarSQL="INSERT INTO venta(num_bol,cod_cli,pre_tot,fecha) VALUES (?,?,?,?)";
    String numbol=txtnumbol.getText();
    String codcli=txtcod.getText();
    
    String total=txttotal.getText();
    String fecha=txtfecha.getText();
    try {
            PreparedStatement pst = cn.prepareStatement(InsertarSQL);
            pst.setString(1,numbol);
            pst.setString(2,codcli);
            pst.setString(3,total);
            pst.setString(4,fecha);
           
            int n= pst.executeUpdate();
            if(n>0)
            {
                JOptionPane.showMessageDialog(null,"Los datos se guardaron correctamente");
            }
           
        } catch (SQLException ex) {
            Logger.getLogger(Venta.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
                    
   void detalleventa(){
        for(int i=0;i<tbdetbol.getRowCount();i++)
        {
        String InsertarSQL="INSERT INTO detalleventa(num_bol,cod_pro,des_pro,cant_pro,pre_unit,pre_venta) VALUES (?,?,?,?,?,?)";
        String numbol=txtnumbol.getText();
        String codpro=tbdetbol.getValueAt(i, 0).toString();
        String despro=tbdetbol.getValueAt(i, 1).toString();
        String cantpro=tbdetbol.getValueAt(i, 3).toString();
        String preunit=tbdetbol.getValueAt(i, 2).toString();
        String importe=tbdetbol.getValueAt(i, 4).toString();
    
        try {
            PreparedStatement pst = cn.prepareStatement(InsertarSQL);
            pst.setString(1,numbol);
            pst.setString(2,codpro);
            pst.setString(3,despro);
            pst.setString(4,cantpro);
            pst.setString(5,preunit);
            pst.setString(6,importe);
          
             pst.executeUpdate();
          
           
        } catch (SQLException ex) {
            Logger.getLogger(Venta.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    }
   
   
   void GuardarVenta(){
    if((txtcod.getText().equals(""))||(txttotal.getText().equals(""))){
            JOptionPane.showMessageDialog(this, "Ingrese cliente, Ingresa Producto ó Genera total");
        }
        else{
            String capcod="",capcan="";
            for(int i=0;i<Venta.tbdetbol.getRowCount();i++)
            {
                capcod=Venta.tbdetbol.getValueAt(i, 0).toString();
                capcan=Venta.tbdetbol.getValueAt(i, 3).toString();
                descontarstock(capcod, capcan);

            }
            venta();
            detalleventa();
            txtcod.setText("");
            txtnom.setText("");
            txtdni.setText("");
            txttotal.setText("");

            DefaultTableModel modelo = (DefaultTableModel) tbdetbol.getModel();
            int a =tbdetbol.getRowCount()-1;
            int i;
            for(i=a;i>=0;i--)
            {
                modelo.removeRow(i);
            }
            numeros();
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

        jPanel3 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        txtnom = new javax.swing.JTextField();
        txtdni = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtcod = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        btnclientes = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        btnproductos = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        labhora = new javax.swing.JLabel();
        txtfecha = new javax.swing.JLabel();
        txtnumbol = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbdetbol = new javax.swing.JTable();
        txttotal = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        areaventa = new javax.swing.JPanel();
        btneli = new javax.swing.JButton();
        btnven = new javax.swing.JButton();
        btnsalir = new javax.swing.JButton();
        btncalcular = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Venta"));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel9.setText("Cliente");
        jPanel3.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 80, 30));

        txtnom.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtnom.setEnabled(false);
        jPanel3.add(txtnom, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 30, 270, 30));

        txtdni.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtdni.setEnabled(false);
        txtdni.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtdniActionPerformed(evt);
            }
        });
        jPanel3.add(txtdni, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 70, 110, 30));

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel10.setText("RFC:");
        jPanel3.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 70, 30));

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel11.setText("Cod.Cliente");
        jPanel3.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 100, 30));

        txtcod.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtcod.setEnabled(false);
        jPanel3.add(txtcod, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 110, 100, 30));

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel12.setText("Hora:");
        jPanel3.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 150, 80, 30));

        btnclientes.setText("Buscar cliente");
        btnclientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnclientesActionPerformed(evt);
            }
        });
        jPanel3.add(btnclientes, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 40, 120, -1));

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel14.setText("Productos:");
        jPanel3.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 110, -1, -1));

        btnproductos.setText("BUSCAR");
        btnproductos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnproductosActionPerformed(evt);
            }
        });
        jPanel3.add(btnproductos, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 110, 120, 30));

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel15.setText("Fecha:");
        jPanel3.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, 80, 30));

        labhora.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jPanel3.add(labhora, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 150, 150, 30));
        jPanel3.add(txtfecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 160, 110, 30));

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, 560, 210));

        txtnumbol.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        txtnumbol.setEnabled(false);
        getContentPane().add(txtnumbol, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 20, 260, 50));

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel8.setText("tOTAL:");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 460, 120, 40));

        tbdetbol.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CODIGO", "DESCRIPCION", "PRECIO UNITARIO", "CANTIDAD", "PRECIO VENTA"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tbdetbol);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 310, 610, 120));

        txttotal.setEditable(false);
        txttotal.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        txttotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txttotalActionPerformed(evt);
            }
        });
        getContentPane().add(txttotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 450, 250, 60));

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel13.setText("Nº");
        getContentPane().add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 50, 40));

        areaventa.setBackground(new java.awt.Color(255, 255, 255));
        areaventa.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 204, 0), 3));
        areaventa.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btneli.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btneli.setText("ELIMINAR");
        btneli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btneliActionPerformed(evt);
            }
        });
        areaventa.add(btneli, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 380, -1, 40));

        btnven.setBackground(new java.awt.Color(204, 0, 51));
        btnven.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnven.setForeground(new java.awt.Color(255, 255, 255));
        btnven.setText("Guardar Venta");
        btnven.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnvenActionPerformed(evt);
            }
        });
        areaventa.add(btnven, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 230, 130, 40));

        btnsalir.setBackground(new java.awt.Color(0, 0, 204));
        btnsalir.setForeground(new java.awt.Color(255, 255, 255));
        btnsalir.setText("SALIR");
        btnsalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsalirActionPerformed(evt);
            }
        });
        areaventa.add(btnsalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(641, 10, 70, 20));

        btncalcular.setBackground(new java.awt.Color(0, 255, 51));
        btncalcular.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btncalcular.setText("Genera ToTAL");
        btncalcular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncalcularActionPerformed(evt);
            }
        });
        areaventa.add(btncalcular, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 140, 120, 40));

        getContentPane().add(areaventa, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 730, 530));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtdniActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtdniActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtdniActionPerformed

    private void btnclientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnclientesActionPerformed
        
        ClientesB cli = new ClientesB();
        cli.toFront();
        cli.setVisible(true);
        
        
    }//GEN-LAST:event_btnclientesActionPerformed

    private void btnproductosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnproductosActionPerformed
      
          Productobuscar pro= new Productobuscar();
        //ventanaadmin.escritorio.add(pro);
        pro.toFront();
        pro.setVisible(true);
        
   
    }//GEN-LAST:event_btnproductosActionPerformed

    private void btncalcularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncalcularActionPerformed
        // TODO add your handling code here:
        if(tbdetbol.getRowCount()<1)
        {
            JOptionPane.showMessageDialog(this, "ingrese algun producto");
        }
        else
        {
            calcular();
        }

    }//GEN-LAST:event_btncalcularActionPerformed

    private void btneliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneliActionPerformed
        // TODO add your handling code here:
        DefaultTableModel model = (DefaultTableModel) tbdetbol.getModel();
        int fila = tbdetbol.getSelectedRow();
        if(fila>=0)
        {
            model.removeRow(fila);
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Tabla vacia o no seleccione ninguna fila");
        }

    }//GEN-LAST:event_btneliActionPerformed

    private void btnvenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnvenActionPerformed
        GuardarVenta();

    }//GEN-LAST:event_btnvenActionPerformed

    private void btnsalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsalirActionPerformed
      
        this.dispose();
        
    }//GEN-LAST:event_btnsalirActionPerformed

    private void txttotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txttotalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txttotalActionPerformed

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
            java.util.logging.Logger.getLogger(Venta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Venta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Venta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Venta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Venta().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private static javax.swing.JPanel areaventa;
    private javax.swing.JButton btncalcular;
    private javax.swing.JButton btnclientes;
    private javax.swing.JButton btneli;
    private javax.swing.JButton btnproductos;
    private javax.swing.JButton btnsalir;
    private javax.swing.JButton btnven;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel labhora;
    public static javax.swing.JTable tbdetbol;
    public static javax.swing.JTextField txtcod;
    public static javax.swing.JTextField txtdni;
    private javax.swing.JLabel txtfecha;
    public static javax.swing.JTextField txtnom;
    private javax.swing.JTextField txtnumbol;
    private javax.swing.JTextField txttotal;
    // End of variables declaration//GEN-END:variables
 conectar cc = new conectar();
    Connection cn = cc.conexion();
}
