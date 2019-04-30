/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sni;

import java.awt.HeadlessException;
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
public class QPersonas {
    private Conexion con= new Conexion();
    
    public boolean agregarPersona(int idPersona, String nombre, String apellido, String telefono, String direccion, String correo, String foto, int nivelAcademico, String facebook){
        try{
            int rows_updated=0;
            PreparedStatement stmt1 = con.conectar().prepareStatement("INSERT INTO persona VALUES (?,?,?,?,?,?,?,?,?)");
            stmt1.setInt(1, idPersona);
            stmt1.setString(2, nombre);
            stmt1.setString(3, apellido);
            stmt1.setString(4, telefono);
            stmt1.setString(5, direccion);
            stmt1.setString(6, correo);
            stmt1.setString(7, foto);
            stmt1.setInt(8, nivelAcademico);
            stmt1.setString(9, facebook);
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
    
    public List<Personas> mostrarPersonas(){
        List<Personas> listaPersonas = new ArrayList<Personas>();
        try{
            Statement sentencia = null;
            ResultSet resultado = null;
            sentencia = con.conectar().createStatement();
            resultado = sentencia.executeQuery("SELECT dui, nombre, apellido, telefono, direccion, correo, foto, nivelAcademico.nivelAcademico, facebook FROM persona LEFT JOIN nivelAcademico ON persona.nivelAcademico = nivelAcademico.idNivelAcademico;");
            resultado.last();
            
            if(resultado.getRow()<=0){
                listaPersonas.clear();
                return listaPersonas;
            }else{
                resultado.beforeFirst();
                while(resultado.next()){
                    int dui = (Integer) resultado.getObject("dui");
                    String nombre = evaluarStringNulo(resultado.getObject("nombre"));
                    String apellido = evaluarStringNulo(resultado.getObject("apellido"));
                    String telefono = evaluarStringNulo(resultado.getObject("telefono"));
                    String direccion = evaluarStringNulo(resultado.getObject("direccion"));;
                    String correo = evaluarStringNulo(resultado.getObject("correo"));
                    String foto = evaluarStringNulo(resultado.getObject("foto"));
                    String nivelAcademico = evaluarStringNulo(resultado.getObject("nivelAcademico"));
                    String facebook = evaluarStringNulo(resultado.getObject("facebook"));
                    Personas per = new Personas(dui, nombre, apellido, telefono, direccion, correo, foto, nivelAcademico, facebook);
                    listaPersonas.add(per);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } finally{
            con.desconectar();
        }
        return listaPersonas;
    }
    
    public List<Personas> mostrarPersonasPor(String tipoFiltro, String filtro){
        List<Personas> listaPersonas = new ArrayList<Personas>();
        try{
            Statement sentencia = null;
            ResultSet resultado = null;
            sentencia = con.conectar().createStatement();
            resultado = sentencia.executeQuery("SELECT dui, nombre, apellido, telefono, direccion, correo, foto, nivelAcademico.nivelAcademico, facebook FROM persona LEFT JOIN nivelAcademico ON persona.nivelAcademico = nivelAcademico.idNivelAcademico WHERE "+tipoFiltro+" like '"+filtro+"%';");
            resultado.last();
            
            if(resultado.getRow()<=0){
                listaPersonas.clear();
                return listaPersonas;
            }else{
                resultado.beforeFirst();
                while(resultado.next()){
                    int dui = (Integer) resultado.getObject("dui");
                    String nombre = evaluarStringNulo(resultado.getObject("nombre"));
                    String apellido = evaluarStringNulo(resultado.getObject("apellido"));
                    String telefono = evaluarStringNulo(resultado.getObject("telefono"));
                    String direccion = evaluarStringNulo(resultado.getObject("direccion"));;
                    String correo = evaluarStringNulo(resultado.getObject("correo"));
                    String foto = evaluarStringNulo(resultado.getObject("foto"));
                    String nivelAcademico = evaluarStringNulo(resultado.getObject("nivelAcademico"));
                    String facebook = evaluarStringNulo(resultado.getObject("facebook"));
                    Personas per = new Personas(dui, nombre, apellido, telefono, direccion, correo, foto, nivelAcademico, facebook);
                    listaPersonas.add(per);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } finally{
            con.desconectar();
        }
        return listaPersonas;
    }
    
    public String evaluarStringNulo(Object dato){
        if(dato==null){
            return "n/a";
        }else{
            return dato.toString();
        }
    }
    
    public boolean modificarPersona(int dui){
        try {
            int rows_updated = 0;
            PreparedStatement stmt1 = con.conectar().prepareStatement("UPDATE municipio SET idMunicipio=?, departamento = (SELECT idDepartamento FROM departamento WHERE departamento=?), municipio=? WHERE (idMunicipio = " + 0 + ")");
            //stmt1.setInt(1, idNuevo);
            //stmt1.setString(2, departamento);
            //stmt1.setString(3, municipio);

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
    
    public boolean eliminarPersona(int dui){
        try {
            int rows_updated = 0;
            PreparedStatement stmt1 = con.conectar().prepareStatement("DELETE FROM persona where dui= " + dui);
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
