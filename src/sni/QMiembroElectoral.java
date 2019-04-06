/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sni;

import java.awt.HeadlessException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author vladi
 */
public class QMiembroElectoral {
    private Conexion con= new Conexion();
    
    public boolean agregarPersona(int dui, int jrv, int lugarCapacitacion, int cargo){
        try{
            int rows_updated=0;
            PreparedStatement stmt1 = con.conectar().prepareStatement("INSERT INTO miembroelectoral VALUES (?,?,?,NOW(),?)");
            stmt1.setInt(1, dui);
            stmt1.setInt(2, jrv);
            stmt1.setInt(3, lugarCapacitacion);
            stmt1.setInt(4, cargo);
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
    
    public List<MiembroElectoral> mostrarPersonas(){
        List<MiembroElectoral> listaMiembrosElec = new ArrayList<MiembroElectoral>();
        try{
            Statement sentencia = null;
            ResultSet resultado = null;
            sentencia = con.conectar().createStatement();
            resultado = sentencia.executeQuery("SELECT "
                    + "miembroelectoral.dui, persona.nombre, persona.apellido, "
                    + "persona.telefono, persona.direccion, persona.correo, "
                    + "jrv.idjrv, cargo.cargo, centrovotacion.nombre, "
                    + "centrovotacion.direccion, departamento.departamento, "
                    + "municipio.municipio, miembroelectoral.fecha, "
                    + "centrovotacion.nombre, centrovotacion.direccion, "
                    + "departamento.departamento, municipio.municipio, "
                    + "capacitacion.fecha, persona.foto FROM miembroelectoral "
                    + "INNER JOIN persona ON persona.dui=miembroelectoral.dui "
                    + "INNER JOIN cargo ON miembroelectoral.cargo=cargo.idcargo "
                    + "INNER JOIN jrv ON miembroelectoral.jrv=jrv.idjrv "
                    + "INNER JOIN centrovotacion ON "
                    + "jrv.centrovotacion=centrovotacion.idcentrovotacion AND "
                    + "miembroelectoral.lugarcapacitacion=centrovotacion.idcentrovotacion "
                    + "INNER JOIN municipio ON centrovotacion.municipio=municipio.idmunicipio "
                    + "INNER JOIN departamento ON municipio.departamento=departamento.iddepartamento "
                    + "INNER JOIN capacitacion ON miembroelectoral.dui=capacitacion.dui;");
            resultado.last();
            
            if(resultado.getRow()<=0){
                listaMiembrosElec.clear();
                return listaMiembrosElec;
            }else{
                resultado.beforeFirst();
                while(resultado.next()){
                    int dui = (Integer) resultado.getObject(1);
                    String nombre = resultado.getObject(2).toString(); 
                    String apellido = resultado.getObject(3).toString();
                    String telefono = evaluarStringNulo(resultado.getObject(4).toString());
                    String direccion = evaluarStringNulo(resultado.getObject(5).toString());
                    String correo = evaluarStringNulo(resultado.getObject(6).toString());
                    int jrv = (Integer)resultado.getObject(7);
                    String cargo = evaluarStringNulo(resultado.getObject(8).toString());
                    String centroVotacion = evaluarStringNulo(resultado.getObject(9).toString());
                    String dirCentroVot = evaluarStringNulo(resultado.getObject(10).toString());
                    String departamentoVot = evaluarStringNulo(resultado.getObject(11).toString());
                    String municipioVot = evaluarStringNulo(resultado.getObject(12).toString());
                    Date fechaRegistro = Date.valueOf(evaluarStringNulo(resultado.getObject(13).toString()));
                    String lugarCapacitacion = resultado.getObject(14).toString();
                    String dirCentroCap = resultado.getObject(15).toString();
                    String departamentoCap = resultado.getObject(16).toString();
                    String municipioCap = resultado.getObject(17).toString();
                    Date fechaCapacitacion = Date.valueOf(resultado.getObject(18).toString());
                    String foto = evaluarStringNulo(resultado.getObject(19).toString());
                    MiembroElectoral me = new MiembroElectoral();
                    listaMiembrosElec.add(me);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } finally{
            con.desconectar();
        }
        return listaMiembrosElec;
    }
    
    public String evaluarStringNulo(Object dato){
        if(dato==null){
            return "n/a";
        }else{
            return dato.toString();
        }
    }
    
    public boolean modificarMiembroElectoral(int idActual, int idNuevo, String departamento, String municipio){
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
    
    public boolean eliminarMiembroElectoral(int dui){
        try {
            int rows_updated = 0;
            PreparedStatement stmt1 = con.conectar().prepareStatement("DELETE FROM MiembroElectoral where idMunicipio= " + dui);
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
