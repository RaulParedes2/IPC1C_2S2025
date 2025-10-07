/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modulo;

/**
 *
 * @author Daniel Predes
 */
public class Administrador extends Usuario {
    public Administrador(String codigo, String nombre, String genero, String password){
        super(codigo, nombre, genero, password);
    }
    
    @Override
    public String getRol(){
        return "ADMINISTRADOR";
    }
}
