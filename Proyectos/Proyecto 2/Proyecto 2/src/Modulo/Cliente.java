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
public class Cliente extends Usuario{
    private LocalDate cumpleaños;
    
    public Cliente(String codigo, String nombre, String genero, String password){
        super(codigo, nombre, genero, password);
        this.cumpleaños=cumpleaños;
    }
    public LocalDate getCumpleaños(){
        return cumpleaños;
    }
    @Override
    public String getRol(){
        return "CLIENTE";
    }
}
