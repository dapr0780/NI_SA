/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sni;

import java.sql.Date;

/**
 *
 * @author vladi
 */
public class MiembroElectoral {
    int dui;
    String nombre;
    String apellido;
    String telefono;
    String direccion;
    String correo;
    int jrv;
    String cargo;
    String centroVotacion;
    String dirCentroVot;
    String departamentoVot;
    String municipioVot;
    Date fechaRegistro;
    String lugarCapacitacion;
    String dirCentroCap;
    String departamentoCap;
    String municipioCap;
    Date fechaCapacitacion;
    String foto;

    public MiembroElectoral() {
    }

    public MiembroElectoral(int dui, String nombre, String apellido, String telefono, String direccion, String correo, int jrv, String cargo, String centroVotacion, String dirCentroVot, String departamentoVot, String municipioVot, Date fechaRegistro, String lugarCapacitacion, String dirCentroCap, String departamentoCap, String municipioCap, Date fechaCapacitacion, String foto) {
        this.dui = dui;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.direccion = direccion;
        this.correo = correo;
        this.jrv = jrv;
        this.cargo = cargo;
        this.centroVotacion = centroVotacion;
        this.dirCentroVot = dirCentroVot;
        this.departamentoVot = departamentoVot;
        this.municipioVot = municipioVot;
        this.fechaRegistro = fechaRegistro;
        this.lugarCapacitacion = lugarCapacitacion;
        this.dirCentroCap = dirCentroCap;
        this.departamentoCap = departamentoCap;
        this.municipioCap = municipioCap;
        this.fechaCapacitacion = fechaCapacitacion;
        this.foto = foto;
    }

    public MiembroElectoral(int dui, String nombre, String apellido, String telefono, String direccion, String correo, int jrv, String cargo, String centroVotacion, String dirCentroVot, String departamentoVot, String municipioVot, Date fechaRegistro, String foto) {
        this.dui = dui;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.direccion = direccion;
        this.correo = correo;
        this.jrv = jrv;
        this.cargo = cargo;
        this.centroVotacion = centroVotacion;
        this.dirCentroVot = dirCentroVot;
        this.departamentoVot = departamentoVot;
        this.municipioVot = municipioVot;
        this.fechaRegistro = fechaRegistro;
        this.foto = foto;
    }

    public MiembroElectoral(int dui, String nombre, String apellido, String telefono, String direccion, String correo, int jrv, String cargo, String foto) {
        this.dui = dui;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.direccion = direccion;
        this.correo = correo;
        this.jrv = jrv;
        this.cargo = cargo;
        this.foto = foto;
    }

    public int getDui() {
        return dui;
    }

    public void setDui(int dui) {
        this.dui = dui;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public int getJrv() {
        return jrv;
    }

    public void setJrv(int jrv) {
        this.jrv = jrv;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getCentroVotacion() {
        return centroVotacion;
    }

    public void setCentroVotacion(String centroVotacion) {
        this.centroVotacion = centroVotacion;
    }

    public String getDirCentroVot() {
        return dirCentroVot;
    }

    public void setDirCentroVot(String dirCentroVot) {
        this.dirCentroVot = dirCentroVot;
    }

    public String getDepartamentoVot() {
        return departamentoVot;
    }

    public void setDepartamentoVot(String departamentoVot) {
        this.departamentoVot = departamentoVot;
    }

    public String getMunicipioVot() {
        return municipioVot;
    }

    public void setMunicipioVot(String municipioVot) {
        this.municipioVot = municipioVot;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getLugarCapacitacion() {
        return lugarCapacitacion;
    }

    public void setLugarCapacitacion(String lugarCapacitacion) {
        this.lugarCapacitacion = lugarCapacitacion;
    }

    public String getDirCentroCap() {
        return dirCentroCap;
    }

    public void setDirCentroCap(String dirCentroCap) {
        this.dirCentroCap = dirCentroCap;
    }

    public String getDepartamentoCap() {
        return departamentoCap;
    }

    public void setDepartamentoCap(String departamentoCap) {
        this.departamentoCap = departamentoCap;
    }

    public String getMunicipioCap() {
        return municipioCap;
    }

    public void setMunicipioCap(String municipioCap) {
        this.municipioCap = municipioCap;
    }

    public Date getFechaCapacitacion() {
        return fechaCapacitacion;
    }

    public void setFechaCapacitacion(Date fechaCapacitacion) {
        this.fechaCapacitacion = fechaCapacitacion;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
    
}
