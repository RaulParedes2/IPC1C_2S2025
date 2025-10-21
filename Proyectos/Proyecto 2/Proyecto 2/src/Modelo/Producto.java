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
public abstract class Producto implements Serializable {
    private String codigo;
    private String nombre;
    private String categoria;
    private String atributo;
    private int stock;
    
    public Producto(String codigo, String nombre, String categoria, String atributo, int stock){
        this.codigo=codigo;
        this.nombre=nombre;
        this.categoria=categoria;
        this.atributo=atributo;
        this.stock= stock;
    }
    public abstract String getDetalle();
    //----------------------------------------------------------------------------
     public String getCodigo(){
        return codigo;
    }
    public String getNombre(){
        return nombre;
    }
    public String getCategoria(){
        return categoria;
    }
    
    public String getAtributo(){
        return atributo;
    }
    
    public int getStock(){
        return stock;
    }
    //--------------------------------------------------------------------------
    public void setNombre(String nombre) {
        this.nombre = nombre; }
    public void setCategoria(String categoria) {
        this.categoria = categoria; }
    public void setAtributo(String atributo) {
        this.atributo = atributo; }
    public void setStock(int stock) { 
        this.stock = stock; }
    //-------------------------------------------------------------------------
    
    public void agregarStock(int cantidad){
        this.stock += cantidad;
    }
    
    public void reducirStock(int cantidad) {
        if (cantidad <= stock) {
            this.stock -= cantidad;
        } else {
            System.out.println("No hay suficiente.");
        }
    }
    //-------------------------------------------------------------------------
    @Override
    public String toString(){
        return "[" + codigo + "] " + nombre + " | " + categoria + " | Stock: " + stock;
    }
}
