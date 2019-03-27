/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sni;

import java.awt.HeadlessException;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author vladi
 */
public class QDepartamentos {
    private Conexion con= new Conexion();
    public Departamentos depa=new Departamentos();
    
    public boolean agregarDepartamento(int idDpto,String departamento){
        try{
            int rows_updated=0;
            PreparedStatement stmt1 = con.conectar().prepareStatement("INSERT INTO departamento (idDepartamento,departamento) VALUES (?,?)");
            stmt1.setInt(1, idDpto);
            stmt1.setString(2, departamento);
            rows_updated = stmt1.executeUpdate();
            
            if (rows_updated == 1) {
                con.desconectar();
                return true;
            } else {
                con.desconectar();
                return false;
            }
            
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error detectado:\n" + e.getMessage(), "Ha ocurrido un error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    public List<Departamentos> mostrarDepartamentos(){
        List<Departamentos> listaDepartamentos = new ArrayList<Departamentos>();
        try{
            Statement sentencia = null;
            ResultSet resultado = null;
            sentencia = con.conectar().createStatement();
            resultado = sentencia.executeQuery("SELECT * FROM departamento");
            resultado.last();
            
            if(resultado.getRow()<=0){
                listaDepartamentos.clear();
                return listaDepartamentos;
            }else{
                resultado.beforeFirst();
                while(resultado.next()){
                    int id = (Integer) resultado.getObject("idDepartamento");
                    String departamento = resultado.getObject("departamento").toString();
                    Departamentos dp = new Departamentos(id,departamento);
                    listaDepartamentos.add(dp);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } finally{
            con.desconectar();
        }
        return listaDepartamentos;
    }
    
    public boolean modificarDepartamento(int idActual, int idNuevo, String departamento){
        try {
            int rows_updated = 0;
            PreparedStatement stmt1 = con.conectar().prepareStatement("UPDATE departamento SET idDepartamento =?, departamento = ? WHERE (idDepartamento = " + idActual + ")");
            stmt1.setInt(1, idNuevo);
            stmt1.setString(2, departamento);

            rows_updated = stmt1.executeUpdate();
            if (rows_updated == 1) {
                con.desconectar();
                return true;
            } else {
                con.desconectar();
                return false;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            return false;
        } finally{
            con.desconectar();
        }
    }
    
    public boolean eliminarDepartamento(int id){
        try {
            int rows_updated = 0;
            PreparedStatement stmt1 = con.conectar().prepareStatement("DELETE FROM departamento where idDepartamento= " + id);
            rows_updated = stmt1.executeUpdate();
            if (rows_updated == 1) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return false;
        } finally{
            con.desconectar();
        }
    }
    
}
