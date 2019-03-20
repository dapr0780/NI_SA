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
public class Usuario {
    private static String usuario="";
    private static boolean loggeado=false;
    private static boolean supersu=false;

    public static String getUsuario() {
        return usuario;
    }

    public static void setUsuario(String usuario) {
        Usuario.usuario = usuario;
    }

    public static boolean isLoggeado() {
        return loggeado;
    }

    public static void setLoggeado(boolean loggeado) {
        Usuario.loggeado = loggeado;
    }

    public static boolean isSupersu() {
        return supersu;
    }

    public static void setSupersu(boolean supersu) {
        Usuario.supersu = supersu;
    }
    
    
    
}
