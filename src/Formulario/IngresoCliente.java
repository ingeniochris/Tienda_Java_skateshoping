/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Formulario;

import classConectar.conectar;
import com.sun.awt.AWTUtilities;
import java.awt.Color;
import java.awt.event.KeyEvent;
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
public class IngresoCliente extends javax.swing.JFrame {
 DefaultTableModel model;
   String idfila="";
   int x,y;
    /**
     * Creates new form IngresoCliente
     */
    public IngresoCliente() {
        initComponents();
        txtcod.setDisabledTextColor(Color.red);
        AWTUtilities.setWindowOpaque(this,false);
        setResizable(false); 
        setLocationRelativeTo(ventanaadmin.escritorio);
         bloquear();
        cargar("");
    }
    
        void bloquear(){
    txtcod.setEnabled(false);
    txtnom.setEnabled(false);
    txtape.setEnabled(false);
    txtemail.setEnabled(false);
    txttel.setEnabled(false);
    txtdni.setEnabled(false);
     txtape_ma.setEnabled(false);
    cbosexo.setEnabled(false);
    btnguardar.setEnabled(false);
    btnnuevo.setEnabled(true);
    btnactualizar.setEnabled(false);
    
    }
    void limpiar(){
    txtcod.setText("");
    txtnom.setText("");
    txtdni.setText("");
    txtemail.setText("");
    txttel.setText("");
    txtape.setText("");
     txtape_ma.setText("");
    
    }
    void desbloquear(){
    txtcod.setEnabled(true);
    txtnom.setEnabled(true);
    txtape.setEnabled(true);
    txtape_ma.setEnabled(true);
    txtemail.setEnabled(true);
    txttel.setEnabled(true);
    txtdni.setEnabled(true);
    cbosexo.setEnabled(true);
    btnguardar.setEnabled(true);
    btnnuevo.setEnabled(false);
    btnactualizar.setEnabled(false);
    }
    void desbloquear1(){
    txtcod.setEnabled(true);
    txtnom.setEnabled(true);
    txtape.setEnabled(true);
     txtape_ma.setEnabled(true);
    txtemail.setEnabled(true);
    txttel.setEnabled(true);
    txtdni.setEnabled(true);
    cbosexo.setEnabled(true);
    btnguardar.setEnabled(false);
    btnnuevo.setEnabled(false);
    btnactualizar.setEnabled(true);
    }
    
        void cargar(String valor){
    String mostrar="SELECT * FROM cliente WHERE CONCAT(cod_cli,nom_cli,ape_cli,ape_ma_cli,rfc_cli,tel_cli,email_cli) LIKE '%"+valor+"%'";
    String []titulos={"Codigo","Nombres","Apellido Pat","Apellido Mat","Sexo","RFC","Telefono","Email"};
    String []Registros=new String[9];
    model= new DefaultTableModel(null,titulos);
  
        try {
              Statement st = cn.createStatement();
              ResultSet rs = st.executeQuery(mostrar);
              while(rs.next())
              {
                  Registros[0]= rs.getString("cod_cli");
                  Registros[1]= rs.getString("nom_cli");
                  Registros[2]= rs.getString("ape_cli");
                  Registros[3]= rs.getString("ape_ma_cli");
                  Registros[4]= rs.getString("sexo_cli");
                  Registros[5]= rs.getString("rfc_cli");
                  Registros[6]= rs.getString("tel_cli");
                  Registros[7]= rs.getString("email_cli");
                        
                  model.addRow(Registros);
              }
              tbclientes.setModel(model);
        } catch (SQLException ex) {
            Logger.getLogger(IngresoCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
  }
        
        void codigosclientes(){
            int j;
               int cont=1;
               String num="";
               String c="";
                String SQL="select max(cod_cli) from cliente";
                    try {
                        Statement st = cn.createStatement();
                        ResultSet rs=st.executeQuery(SQL);
                        if(rs.next())
                        {              
                             c=rs.getString(1);
                        }


                        if(c==null){
                            txtcod.setText("SKATE0001");
                        }
                        else{
                            char r1=c.charAt(5);
                        char r2=c.charAt(6);
                        char r3=c.charAt(7);
                        char r4=c.charAt(8);
                        
                     
                        String r="";
                        r=""+r1+r2+r3+r4;

                             j=Integer.parseInt(r);
                             GenerarCodigos gen= new GenerarCodigos();
                             gen.generar(j);
                             txtcod.setText("SKATE"+gen.serie());
                        }

        } catch (SQLException ex) {
           Logger.getLogger(IngresoCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
        
        void Guardar(){
                 String nulo="";
        if(txtnom.getText().equals(nulo)){
            JOptionPane.showMessageDialog(null, "Campo Nombre es necesario");
        }else if (txtape.getText().equals(nulo)){
           JOptionPane.showMessageDialog(null, "Campo Apellido paterno es necesario");
        }else if (txtape_ma.getText().equals(nulo)){
           JOptionPane.showMessageDialog(null, "Campo Apellido materno es necesario");
        }else if (cbosexo.getSelectedIndex() == 0){
           JOptionPane.showMessageDialog(null, "Campo Sexo es necesario");
        }else if (txtdni.getText().equals(nulo)){
           JOptionPane.showMessageDialog(null, "Campo RFC es necesario");
        }else if (txttel.getText().equals(nulo)){
           JOptionPane.showMessageDialog(null, "Campo Telefono es necesario");
        }else if (txtemail.getText().equals(nulo)){
           JOptionPane.showMessageDialog(null, "Campo Emailes necesario");
        }else {
        
         btnguardar.requestFocusInWindow();
        
        String cod,dir,nom,ape,ape_ma,tel,sex,rfc,email;
        String sql="";
        cod=txtcod.getText();
        nom=txtnom.getText();
        ape=txtape.getText();
        ape_ma=txtape_ma.getText();
        tel=txttel.getText();
        sex=cbosexo.getSelectedItem().toString();
       
        email= txtemail.getText();
        rfc=txtdni.getText();

        sql="INSERT INTO cliente (cod_cli,nom_cli,ape_cli,ape_ma_cli,sexo_cli,rfc_cli,tel_cli,email_cli) VALUES (?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement pst  = cn.prepareStatement(sql);
            pst.setString(1, cod);
            pst.setString(2, nom);
            pst.setString(3, ape);
             pst.setString(4, ape_ma);
            pst.setString(5, sex);
            pst.setString(6, rfc);
            pst.setString(7, tel);
            pst.setString(8, email);
          
           

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
        if(txtnom.getText().equals(nulo)){
            JOptionPane.showMessageDialog(null, "Campo Nombre es necesario");
        }else if (txtape.getText().equals(nulo)){
           JOptionPane.showMessageDialog(null, "Campo Apellido paterno es necesario");
        }else if (txtape_ma.getText().equals(nulo)){
           JOptionPane.showMessageDialog(null, "Campo Apellido materno es necesario");
        }else if (cbosexo.getSelectedIndex() == 0){
           JOptionPane.showMessageDialog(null, "Campo Sexo es necesario");
        }else if (txtdni.getText().equals(nulo)){
           JOptionPane.showMessageDialog(null, "Campo RFC es necesario");
        }else if (txttel.getText().equals(nulo)){
           JOptionPane.showMessageDialog(null, "Campo Telefono es necesario");
        }else if (txtemail.getText().equals(nulo)){
           JOptionPane.showMessageDialog(null, "Campo Emailes necesario");
        }else {
        
         btnactualizar.requestFocusInWindow();
        try {
            
            PreparedStatement pst = cn.prepareStatement("UPDATE cliente SET nom_cli='"
                +txtnom.getText()+ "',ape_cli='"
                +txtape.getText()+ "',ape_ma_cli='"
                 +txtape_ma.getText()+ "',sexo_cli='"    
                +cbosexo.getSelectedItem()+ "',rfc_cli='"
                +txtdni.getText()+ "',tel_cli='"
                +txttel.getText()+ "',email_cli='"
                +txtemail.getText()+ "' WHERE cod_cli='"+txtcod.getText()+"'");
              
               
            pst.executeUpdate();
            cargar("");
            bloquear();
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
        }
        }
        
        
         void Eliminar(){
               JPasswordField pwd = new JPasswordField(12);
               int fila= tbclientes.getSelectedRow();
        if(fila<0)
        {
            JOptionPane.showMessageDialog(this, "Seleccione alguna fila");
        }
        else
        {
            idfila= tbclientes.getValueAt(fila, 0).toString();
            String contra = JOptionPane.showInputDialog(null, "Escribe tu contraseña de usuario administrador" + new String(pwd.getPassword()));
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

                String eli="DELETE FROM cliente WHERE cod_cli = '"+idfila+"'";
                PreparedStatement pst;
                try {
                    pst = cn.prepareStatement(eli);
                    int m=pst.executeUpdate();
                    if(m>0){
                        JOptionPane.showMessageDialog(this, "Elimino cliente");
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
        txtbuscar = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbclientes = new javax.swing.JTable();
        btnactualizar = new javax.swing.JButton();
        btnguardar = new javax.swing.JButton();
        btnnuevo = new javax.swing.JButton();
        btnbuscar = new javax.swing.JButton();
        btnsalir = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtcod = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtnom = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtape = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtdni = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        cbosexo = new javax.swing.JComboBox();
        txttel = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtemail = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtape_ma = new javax.swing.JTextField();
        btneliminar = new javax.swing.JButton();
        lbbarra = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 255), 3));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtbuscar.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtbuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbuscarKeyReleased(evt);
            }
        });
        jPanel2.add(txtbuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 360, 120, 20));

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel10.setText("buscar :");
        jPanel2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 360, -1, -1));

        tbclientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbclientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbclientesMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbclientes);

        jPanel2.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 390, 710, 190));

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
        jPanel2.add(btnactualizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 150, 100, 60));

        btnguardar.setBackground(new java.awt.Color(255, 0, 0));
        btnguardar.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnguardar.setForeground(new java.awt.Color(255, 255, 255));
        btnguardar.setText("Guardar");
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
        jPanel2.add(btnguardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 90, 100, 60));

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
        jPanel2.add(btnnuevo, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 30, 100, 60));

        btnbuscar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnbuscar.setText("Mostrar Todos");
        jPanel2.add(btnbuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 360, -1, 20));

        btnsalir.setBackground(new java.awt.Color(51, 0, 204));
        btnsalir.setForeground(new java.awt.Color(255, 255, 255));
        btnsalir.setText("Salir");
        btnsalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsalirActionPerformed(evt);
            }
        });
        jPanel2.add(btnsalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 10, 60, 20));

        jPanel1.setBackground(new java.awt.Color(255, 204, 153));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Detalle Cliente", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 18), new java.awt.Color(255, 153, 0))); // NOI18N
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Codigo:");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 70, 20));

        txtcod.setEditable(false);
        txtcod.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jPanel1.add(txtcod, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 30, 200, 30));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Nombres:");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 80, 20));

        txtnom.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtnom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtnomActionPerformed(evt);
            }
        });
        txtnom.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtnomKeyTyped(evt);
            }
        });
        jPanel1.add(txtnom, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 80, 130, 30));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Apellido Paterno:");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 110, 20));

        txtape.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtape.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtapeActionPerformed(evt);
            }
        });
        txtape.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtapeKeyTyped(evt);
            }
        });
        jPanel1.add(txtape, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 130, 130, 30));

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("RFC :");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 240, 50, 20));

        txtdni.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtdni.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtdniActionPerformed(evt);
            }
        });
        txtdni.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtdniKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtdniKeyTyped(evt);
            }
        });
        jPanel1.add(txtdni, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 230, 130, 30));

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Sexo:");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 140, 40, 20));

        cbosexo.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cbosexo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select", "M", "F" }));
        cbosexo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbosexoActionPerformed(evt);
            }
        });
        jPanel1.add(cbosexo, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 170, 80, 30));

        txttel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txttel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txttelActionPerformed(evt);
            }
        });
        txttel.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txttelKeyTyped(evt);
            }
        });
        jPanel1.add(txttel, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 280, 130, 30));

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("Telefono:");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 290, 80, 20));

        txtemail.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtemail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtemailActionPerformed(evt);
            }
        });
        jPanel1.add(txtemail, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 330, 190, 30));

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("Email:");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 340, 70, 20));

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel9.setText("Apellido Materno:");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 180, 120, 20));

        txtape_ma.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtape_ma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtape_maActionPerformed(evt);
            }
        });
        txtape_ma.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtape_maKeyTyped(evt);
            }
        });
        jPanel1.add(txtape_ma, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 180, 130, 30));

        jPanel2.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 360, 370));

        btneliminar.setBackground(new java.awt.Color(255, 255, 102));
        btneliminar.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btneliminar.setText("Eliminar");
        btneliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btneliminarActionPerformed(evt);
            }
        });
        jPanel2.add(btneliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 210, 100, 60));

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
        jPanel2.add(lbbarra, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 640, 30));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 730, 590));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtnomKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtnomKeyTyped
        // TODO add your handling code here:
        char car = evt.getKeyChar();
        if((car<'a' || car>'z') && (car<'A' || car>'Z') && (car!=(char)KeyEvent.VK_SPACE))
        {
            evt.consume();
        }
    }//GEN-LAST:event_txtnomKeyTyped

    private void txtapeKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtapeKeyTyped
        // TODO add your handling code here:
        char car = evt.getKeyChar();
        if((car<'a' || car>'z') && (car<'A' || car>'Z') && (car!=(char)KeyEvent.VK_SPACE))
        {
            evt.consume();
        }
    }//GEN-LAST:event_txtapeKeyTyped

    private void txtdniActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtdniActionPerformed
  String nulo="";
        if(txtdni.getText().equals(nulo)){
            JOptionPane.showMessageDialog(null, "Campo RFC es necesario");
        }else {
            txttel.requestFocusInWindow();
        }       
    }//GEN-LAST:event_txtdniActionPerformed

    private void txtdniKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtdniKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtdniKeyPressed

    private void txtdniKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtdniKeyTyped
        // TODO add your handling code here:
   
    }//GEN-LAST:event_txtdniKeyTyped

    private void txttelKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txttelKeyTyped
        // TODO add your handling code here:
        char car = evt.getKeyChar();
        if(txttel.getText().length()>=9) evt.consume();
        if((car<'0' || car>'9')) evt.consume();
    }//GEN-LAST:event_txttelKeyTyped

    private void txtape_maKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtape_maKeyTyped
          char car = evt.getKeyChar();
        if((car<'a' || car>'z') && (car<'A' || car>'Z') && (car!=(char)KeyEvent.VK_SPACE))
        {
            evt.consume();
        }
      
    }//GEN-LAST:event_txtape_maKeyTyped

    private void btnnuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnuevoActionPerformed
        desbloquear();
        limpiar();
        codigosclientes();
        txtcod.requestFocus();
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

    private void tbclientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbclientesMouseClicked
        desbloquear1();
        int r=tbclientes.getSelectedRow();
        TableModel model=tbclientes.getModel();
        txtcod.setText(model.getValueAt(r,0).toString());
        txtnom.setText(model.getValueAt(r,1).toString());
            txtape.setText(model.getValueAt(r,2).toString());
                txtape_ma.setText(model.getValueAt(r,3).toString());
                txtdni.setText(model.getValueAt(r,5).toString());
                  txttel.setText(model.getValueAt(r,6).toString());
                    txtemail.setText(model.getValueAt(r,7).toString());
                      
                
              
    }//GEN-LAST:event_tbclientesMouseClicked

    private void txtape_maActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtape_maActionPerformed
         String nulo="";
        if(txtape_ma.getText().equals(nulo)){
            JOptionPane.showMessageDialog(null, "Campo Apellido materno es necesario");
        }else {
            cbosexo.requestFocusInWindow();
        }
    }//GEN-LAST:event_txtape_maActionPerformed

    private void txtnomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtnomActionPerformed
 String nulo="";
        if(txtnom.getText().equals(nulo)){
            JOptionPane.showMessageDialog(null, "Campo Nombre es necesario");
        }else {
            txtape.requestFocusInWindow();
        }
    }//GEN-LAST:event_txtnomActionPerformed

    private void txtapeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtapeActionPerformed
        String nulo="";
        if(txtape.getText().equals(nulo)){
            JOptionPane.showMessageDialog(null, "Campo Apellido paterno es necesario");
        }else {
            txtape_ma.requestFocusInWindow();
        }
    }//GEN-LAST:event_txtapeActionPerformed

    private void cbosexoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbosexoActionPerformed
         String nulo="";
        if(cbosexo.getSelectedIndex() == 0){
            JOptionPane.showMessageDialog(null, "Campo password es necesario");
        }else {
            txtdni.requestFocusInWindow();
        }
    }//GEN-LAST:event_cbosexoActionPerformed

    private void txttelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txttelActionPerformed
          String nulo="";
        if(txttel.getText().equals(nulo)){
            JOptionPane.showMessageDialog(null, "Campo Telefono es necesario");
        }else {
            txtemail.requestFocusInWindow();
        }
    }//GEN-LAST:event_txttelActionPerformed

    private void txtemailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtemailActionPerformed
        String nulo="";
        if(txttel.getText().equals(nulo)){
            JOptionPane.showMessageDialog(null, "Campo Apellido paterno es necesario");
        }else {
            btnguardar.requestFocusInWindow();
        }
    }//GEN-LAST:event_txtemailActionPerformed

    private void btnnuevoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnnuevoKeyPressed
        desbloquear();
        limpiar();
        codigosclientes();
        txtcod.requestFocus();
    }//GEN-LAST:event_btnnuevoKeyPressed

    private void btnguardarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnguardarKeyPressed
        Guardar();
    }//GEN-LAST:event_btnguardarKeyPressed

    private void btnactualizarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnactualizarKeyPressed
        Actualizar();
    }//GEN-LAST:event_btnactualizarKeyPressed

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
            java.util.logging.Logger.getLogger(IngresoCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(IngresoCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(IngresoCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(IngresoCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new IngresoCliente().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnactualizar;
    private javax.swing.JButton btnbuscar;
    private javax.swing.JButton btneliminar;
    private javax.swing.JButton btnguardar;
    private javax.swing.JButton btnnuevo;
    private javax.swing.JButton btnsalir;
    private javax.swing.JComboBox cbosexo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lbbarra;
    private javax.swing.JTable tbclientes;
    private javax.swing.JTextField txtape;
    private javax.swing.JTextField txtape_ma;
    private javax.swing.JTextField txtbuscar;
    private javax.swing.JTextField txtcod;
    private javax.swing.JTextField txtdni;
    private javax.swing.JTextField txtemail;
    private javax.swing.JTextField txtnom;
    private javax.swing.JTextField txttel;
    // End of variables declaration//GEN-END:variables

    conectar cc = new conectar();
    Connection cn = cc.conexion();
}
