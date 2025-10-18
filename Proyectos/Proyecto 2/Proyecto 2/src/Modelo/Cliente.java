/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.time.LocalDate;
/**
 *
 * @author Daniel Predes
 */
public class Cliente extends Usuario{
    private String cumpleaños;
    
    public Cliente(String codigo, String nombre, String genero, String password, String cumpleaños){
        super(codigo, nombre, genero, password);
        this.cumpleaños=cumpleaños;
    }
    public String getCumpleaños(){
        return cumpleaños;
    }
    
    public void setCumpleaños(String cumpleaños) { 
        this.cumpleaños = cumpleaños; }

    @Override
    public String getRol(){
        return "CLIENTE";
    }
}
