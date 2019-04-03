/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sni;

/**
 *
 * @author vladi
 */
public class Municipios {
    private int idMunicipio;
    private int idDepartamento;
    private String departamento;
    private String municipio;

    public Municipios() {
    }

    public Municipios(int idMunicipio, int idDepartamento, String municipio) {
        this.idMunicipio = idMunicipio;
        this.idDepartamento = idDepartamento;
        this.municipio = municipio;
    }

    public Municipios(int idMunicipio, String departamento, String municipio) {
        this.idMunicipio = idMunicipio;
        this.departamento = departamento;
        this.municipio = municipio;
    }

    public Municipios(int idMunicipio, int idDepartamento, String departamento, String municipio) {
        this.idMunicipio = idMunicipio;
        this.idDepartamento = idDepartamento;
        this.departamento = departamento;
        this.municipio = municipio;
    }

    public int getIdMunicipio() {
        return idMunicipio;
    }

    public void setIdMunicipio(int idMunicipio) {
        this.idMunicipio = idMunicipio;
    }

    public int getIdDepartamento() {
        return idDepartamento;
    }

    public void setIdDepartamento(int idDepartamento) {
        this.idDepartamento = idDepartamento;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    
    
}
