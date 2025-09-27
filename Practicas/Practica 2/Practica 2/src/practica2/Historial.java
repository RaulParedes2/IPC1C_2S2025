/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practica2;

/**
 *
 * @author Daniel Predes
 */
//Clase hija Historial
public class Historial {
    int id;
    String fecha;
    String participante1;
    String participante2;
    String ganador;

//Constructor de la subClase o clase hija     
    public Historial(int id, String fecha, String p1, String p2, String ganador){
        this.id = id;
        this.fecha = fecha;
        this.participante1 = p1;
        this.participante2 = p2;
        this.ganador = ganador;
    }
}
