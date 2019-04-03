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
public class QMunicipios {
    private Conexion con= new Conexion();
    public Municipios mun=new Municipios();
    
    public boolean agregarMunicipio(int idMun, String departamento, String municipio){
        try{
            int rows_updated=0;
            PreparedStatement stmt1 = con.conectar().prepareStatement("INSERT INTO municipio VALUES (?,(SELECT idDepartamento FROM departamento WHERE departamento=?),?)");
            stmt1.setInt(1, idMun);
            stmt1.setString(2, departamento);
            stmt1.setString(3, municipio);
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
    
    public List<Municipios> mostrarMunicipios(){
        List<Municipios> listaMunicipios = new ArrayList<Municipios>();
        try{
            Statement sentencia = null;
            ResultSet resultado = null;
            sentencia = con.conectar().createStatement();
            resultado = sentencia.executeQuery("SELECT * FROM municipio");
            resultado.last();
            
            if(resultado.getRow()<=0){
                listaMunicipios.clear();
                return listaMunicipios;
            }else{
                resultado.beforeFirst();
                while(resultado.next()){
                    int id = (Integer) resultado.getObject("idMunicipio");
                    int idDepartamento = (Integer) resultado.getObject("departamento");
                    String municipio = resultado.getObject("municipio").toString();
                    Municipios mn = new Municipios(id,idDepartamento,municipio);
                    listaMunicipios.add(mn);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } finally{
            con.desconectar();
        }
        return listaMunicipios;
    }
    
    public List<Municipios> mostrarMunicipiosDepto(){
        List<Municipios> listaMunicipios = new ArrayList<Municipios>();
        try{
            Statement sentencia = null;
            ResultSet resultado = null;
            sentencia = con.conectar().createStatement();
            resultado = sentencia.executeQuery(
                    "SELECT idMunicipio, municipio, departamento.departamento "
                            + "FROM municipio "
                            + "INNER JOIN departamento "
                            + "ON municipio.departamento=departamento.iddepartamento;");
            resultado.last();
            
            if(resultado.getRow()<=0){
                listaMunicipios.clear();
                return listaMunicipios;
            }else{
                resultado.beforeFirst();
                while(resultado.next()){
                    int id = (Integer) resultado.getObject("idMunicipio");
                    String municipio = resultado.getObject("municipio").toString();
                    String departamento = resultado.getObject("departamento").toString();
                    Municipios mn = new Municipios(id,departamento,municipio);
                    listaMunicipios.add(mn);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } finally{
            con.desconectar();
        }
        return listaMunicipios;
    }
    
    public boolean modificarMunicipio(int idActual, int idNuevo, String departamento, String municipio){
        try {
            int rows_updated = 0;
            PreparedStatement stmt1 = con.conectar().prepareStatement("UPDATE municipio SET idMunicipio=?, departamento = (SELECT idDepartamento FROM departamento WHERE departamento=?), municipio=? WHERE (idMunicipio = " + idActual + ")");
            stmt1.setInt(1, idNuevo);
            stmt1.setString(2, departamento);
            stmt1.setString(3, municipio);

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
    
    public boolean eliminarMunicipio(int id){
        try {
            int rows_updated = 0;
            PreparedStatement stmt1 = con.conectar().prepareStatement("DELETE FROM municipio where idMunicipio= " + id);
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
