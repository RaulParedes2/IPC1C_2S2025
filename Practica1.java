/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package practica.pkg1;

import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
/**
 *
 * @author Daniel Predes
 */
class Personaje{
    String Nombre;
    String Arma;
    String[] Habilidad = new String[5];
    int Niveldepoder;
    int id;
    
    public Personaje(String Nombre, String Arma, String[] Habilidad, int Niveldepoder, int id)
    {
    this.id=id;
    this.Nombre=Nombre;
    this.Arma=Arma;
    this.Habilidad=Habilidad;
    this.Niveldepoder=Niveldepoder;
    }
}
class Pelea{
    int idP1;
    int idP2;
    String NombreP1;
    String NombreP2;
    String Fechahora;
    
    public Pelea(String NombreP1, String NombreP2, String Fechahora, int idP1, int idP2)
    {
    this.idP1=idP1;
    this.idP2=idP2;
    this.NombreP1=NombreP1;
    this.NombreP2=NombreP2;
    this.Fechahora=Fechahora;
    }
} 

public class Practica1 {
    static final int Maximo_Personajes=10;
    static final int Maximo_Pelea= 100;
    static Personaje[] personajes = new Personaje[Maximo_Personajes];
    static Pelea[] peleas = new Pelea[Maximo_Pelea];
    static int numeroPersonajes= 0;
    static int numeroPeleas= 0;
    static int idActual= 1;
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);//Escribir
        int opcion;
       do{ 
        MostrarMenu();
        opcion = leerEntero(sc, "Elige una opcion:");
       //solicitud
        switch(opcion){
            case 1: 
                AgregarPersonaje(sc);
                break;
            case 2:
                Modificarpersonaje(sc);
                break;
            case 3:
              Eliminarpersonaje(sc);
                break;
            case 4:
                MostrarDatos();
                break;
            case 5: 
               Listapersonajes();
                break;
            case 6: 
                Peleando(sc);
                break;
            case 7:
                HistorialPeleas();
                break;
            case 8:
                DatosEstudiante();
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
    static void MostrarMenu(){
    System.out.println("Menu");
        System.out.println("1. Agregar personaje");
        System.out.println("2. Modificar personaje");
        System.out.println("3. Eliminar Personaje");
        System.out.println("4. Ver datos del personaje");
        System.out.println("5. Ver lista de personajes");
        System.out.println("6. Realizar pelea de personajes");
        System.out.println("7. Ver historial de pelea");
        System.out.println("8. Ver datos del estudiante");
        System.out.println("9. Salir");
        System.out.println("Elige una opcion:");
}
    static int leerEntero(Scanner sc, String mensaje){
        System.out.print(mensaje);
        while (!sc.hasNextInt()){
        System.out.println("Ingrese un numero(numeros validos del 1 al 9)");
        sc.next();
        }
        return sc.nextInt();
    }
    static void AgregarPersonaje (Scanner sc){
        if (numeroPersonajes >= Maximo_Personajes){
            System.out.println("Haz alcanzado el Maximo de personajes");
            return;
        }
        sc.nextLine();
        System.out.print("Nombre del Perosaje");
        String Nombre = sc.nextLine();
        
        for (int i = 0; i>numeroPersonajes; i++ ){
            if (personajes[i].Nombre.equalsIgnoreCase(Nombre)){
                System.out.println("Utilice otro nombre");
                return;
            }
        }
        
    }
}
