package sni;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Conexion {
    
    Connection Conexion = null;

    public Connection conectar() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        try {
            Conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/nisa?autoReconnect=true&useSSL=false", "root", "12345");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return Conexion;
    }

    public void desconectar() {
        try {
            Conexion.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

}

