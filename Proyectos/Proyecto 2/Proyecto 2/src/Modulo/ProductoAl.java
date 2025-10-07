/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modulo;

import java.time.LocalDate;
/**
 *
 * @author Daniel Predes
 */
public class ProductoAl extends Producto {
    private LocalDate fechaCaducidad;
    
    public ProductoAl(String codigo, String nombre, LocalDate fechaCaducidad, int stock){
        super(codigo,nombre,"Alimento",stock);
        this.fechaCaducidad=fechaCaducidad;
        
    }
    
    @Override
    public String getDetalle(){
        return "Caduca el "+fechaCaducidad;
    }
}
