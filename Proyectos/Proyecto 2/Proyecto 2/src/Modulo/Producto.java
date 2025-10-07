/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modulo;

import java.io.Serializable;
/**
 *
 * @author Daniel Predes
 */
public abstract class Producto implements Serializable {
    protected String codigo;
    protected String nombre;
    protected String categoria;
    protected int stock;
    
    public Producto(String codigo, String nombre, String categoria, int stock){
        this.codigo=codigo;
        this.nombre=nombre;
        this.categoria=categoria;
        this.stock= stock;
    }
    public abstract String getDetalle();
    
    public void agregarStock(int cantidad){
        this.stock += cantidad;
    }
    @Override
    public String toString(){
        return nombre+"["+categoria+"]-Stock: "+stock;
    }
}
