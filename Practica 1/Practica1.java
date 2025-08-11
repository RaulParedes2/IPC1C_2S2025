/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package practica.pkg1;

import java.util.*;
/**
 *
 * @author Daniel Predes
 */
class personaje{
    String nombre;
    String Arma;
    List<String> habilidad;
    int niveldepoder;
    
    public personaje(String nombre, String Arma, List<String> habilidad, int niveldepoder){
    this.nombre=nombre;
    this.Arma=Arma;
    this.habilidad=habilidad;
    this.niveldepoder=niveldepoder;
    }
}

public class Practica1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);//Escribir
        int opcion;
       do{ 
        System.out.println("Menu de gestiones de Personajes");
        System.out.println("1. Agregar personaje");
        System.out.println("2. MOdificar personaje");
        System.out.println("3. Eliminar Personaje");
        System.out.println("4. Ver datos del personaje");
        System.out.println("5. Ver lista de personajes");
        System.out.println("6. Realizar pelea de personajes");
        System.out.println("7. Ver historial de pelea");
        System.out.println("8. Ver datos del estudiante");
        System.out.println("9. Salir");
        System.out.println("Elige una opcion:");
        
        opcion =sc.nextInt();
        sc.nextLine();
       
        switch(opcion){
            case 1: 
                System.out.println("Agregando Personaje...");
                break;
            case 2:
                System.out.println("Modificando personaje...");
                break;
            case 3:
                System.out.println("Eliminando personaje...");
                break;
            case 4:
                System.out.println("Mostrando datos...");
                break;
            case 5: 
                System.out.println("Lstado de personajes");
                break;
            case 6: 
                System.out.println("Peleando...");
                break;
            case 7:
                System.out.println("Historial de peleas");
                break;
            case 8:
                System.out.println("Datos del estudiante");
                break;
            case 9:
                System.out.println("Saliendo...");
                break;
            default:
                System.out.println("Intentelo de nuevo");
             }
        System.out.println();
       }
       while(opcion !=9);
       sc.close();
    }
    
}
