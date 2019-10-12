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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author jesus
 */
public class IngresoProductos extends javax.swing.JFrame {
  DefaultTableModel model;
   String idfila="";
   int x,y;
  
    /**
     * Creates new form IngresoProductos
     */
    public IngresoProductos() {
        initComponents();
         txtcod.setDisabledTextColor(Color.red);
        AWTUtilities.setWindowOpaque(this,false); 
        setLocationRelativeTo(ventanaadmin.escritorio);
           bloquear();
         cargar("");
         mostrartipoproducto();
    }
    void mostrartipoproducto(){
       String sql="Select * from tipoproducto";
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next())
            {
                cbotipo.addItem(rs.getString("descrip"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(IngresoProductos.class.getName()).log(Level.SEVERE, null, ex);
        }
       
   }
         void bloquear(){
    txtcod.setEnabled(false);
    txtdes.setEnabled(false);
    txtpre.setEnabled(false);
    txtstock.setEnabled(false);
    btnguardar.setEnabled(false);
    btnnuevo.setEnabled(true);
    btnactualizar.setEnabled(false);
    
    }
    void limpiar(){
    txtcod.setText("");
    txtdes.setText("");
    txtpre.setText("");
    txtstock.setText("");
    }
    void desbloquear(){
    txtcod.setEnabled(true);
    txtdes.setEnabled(true);
    txtpre.setEnabled(true);
    txtstock.setEnabled(true);
     btnactualizar.setEnabled(true);
    btnguardar.setEnabled(false);
    btnnuevo.setEnabled(false);
    }
      void desbloquearnuevo(){
    txtcod.setEnabled(true);
    txtdes.setEnabled(true);
    txtpre.setEnabled(true);
    txtstock.setEnabled(true);
     btnactualizar.setEnabled(true);
    btnguardar.setEnabled(true);
    btnnuevo.setEnabled(false);
    }
      void cargar(String valor) {
        try{
            String [] titulos={"Codigo","Descripcion","Precio","Stock","tipo producto"};
            String [] registros= new String[6];
            model=new DefaultTableModel(null,titulos);
            
            String cons="select * from producto,tipoproducto WHERE producto.id_tipo= tipoproducto.id_tipo && CONCAT (descripcion,'',precio) LIKE '%"+valor+"%'";
            Statement st= cn.createStatement();
            ResultSet rs = st.executeQuery(cons);
            while(rs.next()){
                registros[0]=rs.getString("cod_pro");
                registros[1]=rs.getString("descripcion");
                registros[2]=rs.getString("precio");
                registros[3]=rs.getString("Stock");
                 registros[4]=rs.getString("descrip");
             
                
                model.addRow(registros);      
                }
           tbproductos.setModel(model);
            tbproductos.getColumnModel().getColumn(0).setPreferredWidth(150);
            tbproductos.getColumnModel().getColumn(1).setPreferredWidth(200);
            tbproductos.getColumnModel().getColumn(2).setPreferredWidth(80);
             tbproductos.getColumnModel().getColumn(3).setPreferredWidth(50);
              tbproductos.getColumnModel().getColumn(4).setPreferredWidth(140);
            }catch(Exception e){
                System.out.println(e.getMessage());
                 }
       }
      
      void codigos(){  
        int j;
        int cont=1;
        String num="";
        String c="";
         String SQL="select max(cod_pro) from producto";
            try {
                Statement st = cn.createStatement();
                ResultSet rs=st.executeQuery(SQL);
                while(rs.next())
                {              
                     c=rs.getString(1);
                }
                if(c==null){
                    txtcod.setText("COA-MERCA0001");
                }
                    else{
            char r1=c.charAt(9);
            char r2=c.charAt(10);
            char r3=c.charAt(11);
            char r4=c.charAt(12);
            
            String r="";
            r=""+r1+r2+r3+r4;
                         j=Integer.parseInt(r);
                         GenerarCodigos gen= new GenerarCodigos();
                         gen.generar(j);
                         txtcod.setText("COA-MERCA"+gen.serie());
                    }
            } catch (SQLException ex) {
               Logger.getLogger(IngresoProductos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
      
      void Guardar(){
         String nulo="";
         if(cbotipo.getSelectedIndex() == 0){
            JOptionPane.showMessageDialog(null, "Campo Tipo es necesario");
        }else if(txtdes.getText().equals(nulo)) {
                 JOptionPane.showMessageDialog(null, "Campo Descripcion es necesario");
                 
                 }else if(txtpre.getText().equals(nulo)) {
                 JOptionPane.showMessageDialog(null, "Campo Precio es necesario");
                 
                    }else if(txtstock.getText().equals(nulo)) {
                    JOptionPane.showMessageDialog(null, "Campo Stock es necesario");
                    
                        }else  {
                                btnguardar.requestFocusInWindow();
         
                                          String cod,des,pre,stock;
                                            String sql="";
                                            cod=txtcod.getText();
                                            des=txtdes.getText();
                                            pre=txtpre.getText();
                                            stock=txtstock.getText();
                                            sql="INSERT INTO producto (cod_pro,descripcion,precio,stock,id_tipo) VALUES (?,?,?,?,?)";
                                            try {
                                                PreparedStatement pst  = cn.prepareStatement(sql);
                                                pst.setString(1, cod);
                                                pst.setString(2, des);
                                                pst.setString(3, pre);
                                                pst.setString(4, stock);
                                                pst.setInt(5, cbotipo.getSelectedIndex());

                                                int n=pst.executeUpdate();
                                                if(n>0){
                                                    JOptionPane.showMessageDialog(null, "Registro Guardado con Exito");
                                                    bloquear();
                                                }
                                                cargar("");
                                            } catch (SQLException ex) {
                                                Logger.getLogger(IngresoProductos.class.getName()).log(Level.SEVERE, null, ex);
                                            }
         }
      
      }
      
      void Actualizar(){
                    String nulo="";
         if(cbotipo.getSelectedIndex() == 0){
            JOptionPane.showMessageDialog(null, "Campo Tipo es necesario");
        }else if(txtdes.getText().equals(nulo)) {
                 JOptionPane.showMessageDialog(null, "Campo Descripcion es necesario");
                 
                 }else if(txtpre.getText().equals(nulo)) {
                 JOptionPane.showMessageDialog(null, "Campo Precio es necesario");
                 
                    }else if(txtstock.getText().equals(nulo)) {
                    JOptionPane.showMessageDialog(null, "Campo Stock es necesario");
                    
                        }else  {
            
            
             String sql="UPDATE producto SET precio = '"+txtpre.getText()+"',descripcion ='"+txtdes.getText()+"',Stock = '"+txtstock.getText()+"',id_tipo = '"+cbotipo.getSelectedIndex()+"' WHERE cod_pro = '"+txtcod.getText()+"'";
        try {
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Actualizado");
            cargar("");
            bloquear();
            limpiar();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }   
        }  
      }
      
      void Eliminar(){
               JPasswordField pwd = new JPasswordField(12);
               int fila= tbproductos.getSelectedRow();
        if(fila<0)
        {
            JOptionPane.showMessageDialog(this, "Seleccione alguna fila");
        }
        else
        {
            idfila= tbproductos.getValueAt(fila, 0).toString();
            String contra = JOptionPane.showInputDialog(null, "Escribe tu contraseña de usuario administrador"+new String(pwd.getPassword()));
            String passdb="";

            String nomusuario= ventanaadmin.lblusu.getText();
            System.out.print(nomusuario);
            String sql="SELECT * FROM usuario WHERE nick='"+nomusuario+"'";
            try {
                Statement st = cn.createStatement();
                ResultSet rs = st.executeQuery(sql);
                while(rs.next())
                {
                    passdb=rs.getString("password");

                }
                System.out.println(passdb);

            } catch (SQLException ex) {
                Logger.getLogger(usuarios.class.getName()).log(Level.SEVERE, null, ex);
            }
            if(passdb.equals(contra))
            {

                String eli="DELETE FROM producto WHERE cod_pro = '"+idfila+"'";
                PreparedStatement pst;
                try {
                    pst = cn.prepareStatement(eli);
                    int m=pst.executeUpdate();
                    if(m>0){
                        JOptionPane.showMessageDialog(this, "Elimino producto");
                         bloquear();
                        cargar("");
                         

                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, "Contraseña incorrecta", "Verificacion ***", JOptionPane.WARNING_MESSAGE);
                        JOptionPane.showMessageDialog(this, "No se pudo eliminar");
                        cargar("");
                    }

                } catch (SQLException ex) {
                    Logger.getLogger(usuarios.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
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

        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtcod = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtdes = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtpre = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtstock = new javax.swing.JTextField();
        cbotipo = new javax.swing.JComboBox();
        jLabel6 = new javax.swing.JLabel();
        btnactualizar = new javax.swing.JButton();
        btnnuevo = new javax.swing.JButton();
        btnguardar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbproductos = new javax.swing.JTable();
        btnsalir = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        txtbuscar = new javax.swing.JTextField();
        btneliminar = new javax.swing.JButton();
        lbbarra = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 255), 3));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 204, 153));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Detalle de Producto", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 18), new java.awt.Color(0, 51, 102))); // NOI18N
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("Codigo:");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 90, 40));

        txtcod.setEditable(false);
        txtcod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtcodActionPerformed(evt);
            }
        });
        jPanel1.add(txtcod, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 20, 160, 40));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setText("Descripcion:");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 110, 40));

        txtdes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtdesActionPerformed(evt);
            }
        });
        jPanel1.add(txtdes, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 90, 160, 40));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setText("Precio:");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, 80, 40));

        txtpre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtpreActionPerformed(evt);
            }
        });
        txtpre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtpreKeyTyped(evt);
            }
        });
        jPanel1.add(txtpre, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 150, 160, 40));

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel5.setText("Stock:");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, 80, 40));

        txtstock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtstockActionPerformed(evt);
            }
        });
        txtstock.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtstockKeyTyped(evt);
            }
        });
        jPanel1.add(txtstock, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 210, 160, 40));

        cbotipo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccione.." }));
        cbotipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbotipoActionPerformed(evt);
            }
        });
        jPanel1.add(cbotipo, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 280, 160, 40));

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel6.setText("tipo producto:");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 280, 120, 40));

        jPanel2.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 320, 340));

        btnactualizar.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        btnactualizar.setText("Modificar");
        btnactualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnactualizarActionPerformed(evt);
            }
        });
        btnactualizar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnactualizarKeyPressed(evt);
            }
        });
        jPanel2.add(btnactualizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 170, 100, 60));

        btnnuevo.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnnuevo.setText("Nuevo");
        btnnuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnnuevoActionPerformed(evt);
            }
        });
        btnnuevo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnnuevoKeyPressed(evt);
            }
        });
        jPanel2.add(btnnuevo, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 50, 100, 60));

        btnguardar.setBackground(new java.awt.Color(255, 102, 102));
        btnguardar.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnguardar.setText("Grabar");
        btnguardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnguardarActionPerformed(evt);
            }
        });
        btnguardar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnguardarKeyPressed(evt);
            }
        });
        jPanel2.add(btnguardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 120, 100, 60));

        tbproductos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbproductos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbproductosMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbproductos);

        jPanel2.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, 700, 180));

        btnsalir.setBackground(new java.awt.Color(51, 51, 255));
        btnsalir.setForeground(new java.awt.Color(255, 255, 255));
        btnsalir.setText("Salir");
        btnsalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsalirActionPerformed(evt);
            }
        });
        jPanel2.add(btnsalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 10, -1, 20));

        jButton1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton1.setText("Mostrar Todo");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 340, 140, 20));

        txtbuscar.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtbuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbuscarKeyReleased(evt);
            }
        });
        jPanel2.add(txtbuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 340, 140, 20));

        btneliminar.setBackground(new java.awt.Color(255, 255, 102));
        btneliminar.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btneliminar.setText("Eliminar");
        btneliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btneliminarActionPerformed(evt);
            }
        });
        jPanel2.add(btneliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 240, 100, 60));

        lbbarra.setCursor(new java.awt.Cursor(java.awt.Cursor.MOVE_CURSOR));
        lbbarra.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                lbbarraMouseDragged(evt);
            }
        });
        lbbarra.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lbbarraMousePressed(evt);
            }
        });
        jPanel2.add(lbbarra, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 620, 40));

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel10.setText("buscar :");
        jPanel2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 340, -1, -1));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 720, 560));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnnuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnuevoActionPerformed

        desbloquearnuevo();
        limpiar();
        txtcod.requestFocus();
        codigos();
    }//GEN-LAST:event_btnnuevoActionPerformed

    private void btnguardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnguardarActionPerformed
      Guardar();
     
    }//GEN-LAST:event_btnguardarActionPerformed

    private void btnactualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnactualizarActionPerformed
Actualizar();
  
    }//GEN-LAST:event_btnactualizarActionPerformed

    private void btnsalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsalirActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnsalirActionPerformed

    private void txtbuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscarKeyReleased
        // TODO add your handling code here:
        cargar(txtbuscar.getText());
    }//GEN-LAST:event_txtbuscarKeyReleased

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        cargar("");
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txtcodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtcodActionPerformed
        // TODO add your handling code here:
        txtcod.transferFocus();
    }//GEN-LAST:event_txtcodActionPerformed

    private void txtdesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtdesActionPerformed
        // TODO add your handling code here:
        txtdes.transferFocus();
    }//GEN-LAST:event_txtdesActionPerformed

    private void txtpreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtpreActionPerformed
        // TODO add your handling code here:
        txtpre.transferFocus();
    }//GEN-LAST:event_txtpreActionPerformed

    private void txtstockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtstockActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtstockActionPerformed

    private void tbproductosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbproductosMouseClicked
         desbloquear();
        int r=tbproductos.getSelectedRow();
        TableModel model=tbproductos.getModel();
        txtcod.setText(model.getValueAt(r,0).toString());
        txtdes.setText(model.getValueAt(r,1).toString());
            txtpre.setText(model.getValueAt(r,2).toString());
                txtstock.setText(model.getValueAt(r,3).toString());
    }//GEN-LAST:event_tbproductosMouseClicked

    private void txtpreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtpreKeyTyped
       char car = evt.getKeyChar();
        if(txtpre.getText().length()>=9) evt.consume();
        if((car<'0' || car>'9')) evt.consume();
    }//GEN-LAST:event_txtpreKeyTyped

    private void cbotipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbotipoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbotipoActionPerformed

    private void btnnuevoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnnuevoKeyPressed
         desbloquearnuevo();
        limpiar();
        txtcod.requestFocus();
        codigos();
    }//GEN-LAST:event_btnnuevoKeyPressed

    private void btnguardarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnguardarKeyPressed
      Guardar();
    }//GEN-LAST:event_btnguardarKeyPressed

    private void btnactualizarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnactualizarKeyPressed
       Actualizar();
    }//GEN-LAST:event_btnactualizarKeyPressed

    private void txtstockKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtstockKeyTyped
       char car = evt.getKeyChar();
        if(txtpre.getText().length()>=9) evt.consume();
        if((car<'0' || car>'9')) evt.consume();
    }//GEN-LAST:event_txtstockKeyTyped

    private void btneliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneliminarActionPerformed
       Eliminar();
     
    }//GEN-LAST:event_btneliminarActionPerformed

    private void lbbarraMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbbarraMouseDragged
        this.setLocation(this.getLocation().x + evt.getX() - x, this.getLocation().y + evt.getY()- y);
    }//GEN-LAST:event_lbbarraMouseDragged

    private void lbbarraMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbbarraMousePressed
        x = evt.getX();
        y = evt.getY();
    }//GEN-LAST:event_lbbarraMousePressed

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
            java.util.logging.Logger.getLogger(IngresoProductos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(IngresoProductos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(IngresoProductos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(IngresoProductos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new IngresoProductos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnactualizar;
    private javax.swing.JButton btneliminar;
    private javax.swing.JButton btnguardar;
    private javax.swing.JButton btnnuevo;
    private javax.swing.JButton btnsalir;
    private javax.swing.JComboBox cbotipo;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lbbarra;
    private javax.swing.JTable tbproductos;
    private javax.swing.JTextField txtbuscar;
    private javax.swing.JTextField txtcod;
    private javax.swing.JTextField txtdes;
    private javax.swing.JTextField txtpre;
    private javax.swing.JTextField txtstock;
    // End of variables declaration//GEN-END:variables
conectar cc = new conectar();
Connection cn = cc.conexion();

}
