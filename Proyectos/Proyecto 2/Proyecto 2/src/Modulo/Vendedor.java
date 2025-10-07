/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modulo;

/**
 *
 * @author Daniel Predes
 */
public class Vendedor extends Usuario{
    private int ventasConfirmadas;
    
    public Vendedor(String codigo, String nombre, String genero, String password){
        super(codigo, nombre, genero, password);
        this.ventasConfirmadas=0;
    }
    public void confirmarVenta(){
        ventasConfirmadas++;
    }
    public int getVentasCofirmadas(){
        return ventasConfirmadas;
    }
    
    @Override
    public String getRol(){
        return "VENDEDOR";
    }
}
