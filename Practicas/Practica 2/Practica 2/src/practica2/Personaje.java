/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practica2;

/**
 *
 * @author Daniel Predes
 */
//Clase hija personaje
public class Personaje {
    int id;
    String nombre;
    String arma;
    int hp;
    int ataque;
    int velocidad;
    int agilidad;
    int defensa;
    int batallas;
    int ganadas;
    int perdidas;
    
    //Constructor de la clase hija
    public Personaje(int id, String nombre, String arma, int hp, int ataque, 
            int velocidad, int agilidad, int defensa){
        
        this.id = id;
        this.nombre = nombre;
        this.arma = arma;
        this.hp = hp;
        this.ataque = ataque;
        this.velocidad = velocidad;
        this.agilidad = agilidad;
        this.defensa = defensa;
        this.batallas = 0;
        this.ganadas = 0;
        this.perdidas = 0;
    }
    
}
