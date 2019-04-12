/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sni;

import java.math.BigDecimal;

/**
 *
 * @author vladi
 */
public class Afiliado {
    int dui;
    String nombre;
    String apellido;
    String telefono;
    String direccion;
    String correo;
    String facebook;
    String foto;
    BigDecimal totalDonaciones;

    public Afiliado() {
    }

    public Afiliado(int dui, String nombre, String apellido, String telefono, String direccion, String correo, String facebook, String foto) {
        this.dui = dui;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.direccion = direccion;
        this.correo = correo;
        this.facebook = facebook;
        this.foto = foto;
    }

    public Afiliado(int dui, String nombre, String apellido, String telefono, String direccion, String correo, String facebook, String foto, BigDecimal totalDonaciones) {
        this.dui = dui;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.direccion = direccion;
        this.correo = correo;
        this.facebook = facebook;
        this.foto = foto;
        this.totalDonaciones = totalDonaciones;
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

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public BigDecimal getTotalDonaciones() {
        return totalDonaciones;
    }

    public void setTotalDonaciones(BigDecimal totalDonaciones) {
        this.totalDonaciones = totalDonaciones;
    }

    
    
}
