/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.io.Serializable;
/**
 *
 * @author Daniel Predes
 */
public abstract class Usuario implements Serializable {
    protected String codigo;
    protected String nombre;
    protected String genero;
    protected String password;
    
    public Usuario(String codigo, String nombre, String genero, String password){
        this.codigo=codigo;
        this.nombre=nombre;
        this.genero=genero;
        this.password=password;
    }
    
    public abstract String getRol();
    
    public String getCodigo(){
        return codigo;
    }
    public String getNombre(){
        return nombre;
    }
    public String getgenero(){
        return genero;
    }
    public String getPassword(){
        return password;
    }
    
    public void setNombre(String nombre){
        this.nombre=nombre;
    }
    public void setPassword(String password){
        this.password=password;
    }
    
    @Override
    public String toString(){
        return getRol()+"-"+nombre+"("+codigo+")";
    }
}
