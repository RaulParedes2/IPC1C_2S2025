/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author Daniel Predes
 */
public class ProductoGe extends Producto {
    private String material;
    
    public ProductoGe(String codigo, String nombre, String material, int stock){
        super(codigo, nombre, "General", stock);
        this.material=material;
    }
    public String getMaterial() { return material; }
    public void setMaterial(String material) { this.material = material; }

    
    @Override
    public String getDetalle(){
        return "Material "+material;
    }
}
