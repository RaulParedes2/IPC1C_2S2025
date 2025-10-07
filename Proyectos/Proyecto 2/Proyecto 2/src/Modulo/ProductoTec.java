/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modulo;

/**
 *
 * @author Daniel Predes
 */
public class ProductoTec extends Producto {
    private int mesesGarantia;
    
    public ProductoTec(String codigo, String nombre, int mesesGarantia, int stock){
        super(codigo, nombre,"Tecnologia",stock);
        this.mesesGarantia=mesesGarantia;
    }
    
    @Override
    public String getDetalle(){
        return "Garantia"+mesesGarantia+"meses";
    }
}
