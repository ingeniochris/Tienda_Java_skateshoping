/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classConectar;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author jesus
 */
public class conectar {
    Connection conect = null;
   public Connection conexion()
    {
      try {
           Class.forName("com.mysql.jdbc.Driver");
           //Class.forName("oracle.jdbc.driver.OracleDriver");
           conect = DriverManager.getConnection("jdbc:mysql://localhost/huarache2","khristian","12345678");
            //conect = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "HUARACHE", "12345678");
           //JOptionPane.showMessageDialog(null, "conectado");
    
        } catch (SQLException | ClassNotFoundException ex){
            JOptionPane.showMessageDialog(null,"Error "+ ex);
        }
        return conect;
     
}
}
