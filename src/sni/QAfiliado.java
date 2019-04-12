/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sni;

import java.awt.HeadlessException;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author vladi
 */
public class QAfiliado {
    private Conexion con= new Conexion();
    
    public boolean agregarAfiliado(int dui){
        try{
            int rows_updated=0;
            PreparedStatement stmt1 = con.conectar().prepareStatement("INSERT INTO afiliado VALUES (?)");
            stmt1.setInt(1, dui);
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
    
    public List<Afiliado> mostrarAfiliados(){
        List<Afiliado> listaAfiliados = new ArrayList<Afiliado>();
        try{
            Statement sentencia = null;
            ResultSet resultado = null;
            sentencia = con.conectar().createStatement();
            resultado = sentencia.executeQuery("SELECT afiliado.dui, nombre, "
                    + "apellido, telefono, direccion, correo, facebook, "
                    + "foto, SUM(monto) as 'Donado' FROM persona "
                    + "INNER JOIN afiliado ON persona.dui=afiliado.dui "
                    + "INNER JOIN factura ON factura.dui=afiliado.dui "
                    + "GROUP BY dui");
            resultado.last();
            
            if(resultado.getRow()<=0){
                listaAfiliados.clear();
                return listaAfiliados;
            }else{
                resultado.beforeFirst();
                while(resultado.next()){
                    int dui = (Integer) resultado.getObject(1);
                    String nombre = evaluarStringNulo(resultado.getObject(2));
                    String apellido = evaluarStringNulo(resultado.getObject(3));
                    String telefono = evaluarStringNulo(resultado.getObject(4));
                    String direccion = evaluarStringNulo(resultado.getObject(5));
                    String correo = evaluarStringNulo(resultado.getObject(6));
                    String facebook = evaluarStringNulo(resultado.getObject(7));
                    String foto = evaluarStringNulo(resultado.getObject(8));
                    BigDecimal totalDonaciones = (BigDecimal) resultado.getObject(9);
                    Afiliado af = new Afiliado(dui, nombre, apellido, telefono, direccion, correo, facebook, foto, totalDonaciones);
                    listaAfiliados.add(af);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } finally{
            con.desconectar();
        }
        return listaAfiliados;
    }
    
    public Afiliado mostrarAfiliados(int dui){
        Afiliado afiliado = new Afiliado();
        try{
            Statement sentencia = null;
            ResultSet resultado = null;
            sentencia = con.conectar().createStatement();
            resultado = sentencia.executeQuery("SELECT afiliado.dui, nombre, "
                    + "apellido, telefono, direccion, correo, facebook, "
                    + "foto, SUM(monto) as 'Donado' FROM persona "
                    + "INNER JOIN afiliado ON persona.dui=afiliado.dui "
                    + "INNER JOIN factura ON factura.dui=afiliado.dui "
                    + "WHERE afiliado.dui="+dui+" "
                    + "GROUP BY dui");
            resultado.last();
            
            if(resultado.getRow()<=0){
                return afiliado;
            }else{
                resultado.beforeFirst();
                while(resultado.next()){
                    dui = (Integer) resultado.getObject(1);
                    String nombre = evaluarStringNulo(resultado.getObject(2));
                    String apellido = evaluarStringNulo(resultado.getObject(3));
                    String telefono = evaluarStringNulo(resultado.getObject(4));
                    String direccion = evaluarStringNulo(resultado.getObject(5));
                    String correo = evaluarStringNulo(resultado.getObject(6));
                    String facebook = evaluarStringNulo(resultado.getObject(7));
                    String foto = evaluarStringNulo(resultado.getObject(8));
                    BigDecimal totalDonaciones = (BigDecimal) resultado.getObject(9);
                    afiliado = new Afiliado(dui, nombre, apellido, telefono, direccion, correo, facebook, foto, totalDonaciones);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } finally{
            con.desconectar();
        }
        return afiliado;
    }
    
    public String evaluarStringNulo(Object dato){
        if(dato==null){
            return "n/a";
        }else{
            return dato.toString();
        }
    }
    
    /*
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
    */
    public boolean eliminarAfiliado(int id){
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
