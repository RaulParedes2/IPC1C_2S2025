/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package practica.pkg2;
import java.util.Scanner;
/**
 *
 * @author Daniel Predes
 */
public class Practica2 {
    
 public static void main(String[] args) {
     Scanner sc = new Scanner(System.in);
     
     int opcion;
     
     do{
         MostrarMenu();
         opcion = leerEntero(sc, "Elige una opcion: ");
         
         //Selecion de numero en el sistema
         
         switch(opcion){
             case 1:
                 AgregarPersonaje(sc);
                 break;
                 
             case 2:
                 ModificarPersonaje(sc);
                 break;
                 
             case 3:
                 EliminarPersonaje(sc);
                 break;
                 
             case 4: 
                 VisualizarPersonajes();
                 break;
                 
             case 5:
                 Simulacion(sc);
                 break;
                 
             case 6:
                 HistorialBatallas();
                 break;
                 
             case 7: 
                 BuscarPersonaje();
                 break;
                 
             case 8:
                 GuardarCargar(sc);
                 break;
                 
             case 9:
                 DatosEstudiante();
                 break;
                 
             case 10:
                 System.out.println("-------------------------------------------");
                 System.out.println("Saliendo...");
                 System.out.println("-------------------------------------------");
                 break;
                 
             default:
                 System.out.println("Intentelo de nuevo(1 al 10");
                 
         }
     }
     while(opcion !=10);
     sc.close();
 }
    static void MostrarMenu(){
        System.out.println("-------------------------------------------");
        System.out.println("==MENU==");
        System.out.println("1. Agregar Personajes");
        System.out.println("2. Modificar Personaje");
        System.out.println("3. Eliminar Personaje");
        System.out.println("4. Visualizar los personajes registrados");
        System.out.println("5. Simulacion de batalla");
        System.out.println("6. Ver historial de batallas");
        System.out.println("7. Buscar personaje");
        System.out.println("8. Guardar y cargar estado");
        System.out.println("9. Ver Datos del estudiante");
        System.out.println("10. Salir");
        System.out.println("-------------------------------------------");
    }
    
    //En caso de no escribir in numero en el menu de la consola
    static int leerEntero(Scanner sc, String Mensaje){
        System.out.print(Mensaje);
        while(!sc.hasNextInt()){
            System.out.println("-------------------------------------------");
            System.out.println("¡ERROR! Ingrese solo numeros");
            System.out.println("-------------------------------------------");
            sc.next();
        }
        return sc.nextInt();
    }
    //-------------------------------------------------------------------------
    //Agregar Personaje
    //-------------------------------------------------------------------------
    static void AgregarPersonaje(Scanner sc){
        
    }
    //-------------------------------------------------------------------------
    //Modificar Personaje
    //-------------------------------------------------------------------------
    static void ModificarPersonaje(Scanner sc){
        
    }
    
    //-------------------------------------------------------------------------
    //Eliminar Personaje
    //-------------------------------------------------------------------------
    static void EliminarPersonaje(Scanner sc){
        
    }
    
    //-------------------------------------------------------------------------
    //Visualizar los personajes
    //-------------------------------------------------------------------------
    static void VisualizarPersonajes(){
        
    }
    
    //-------------------------------------------------------------------------
    //Simulacion de batallas
    //-------------------------------------------------------------------------
    
    static void Simulacion(Scanner sc){
        
    }
    
    //-------------------------------------------------------------------------
    //Ver historial de batallas
    //-------------------------------------------------------------------------
    static void HistorialBatallas(){
        
    }
    
    //-------------------------------------------------------------------------
    //Bucar personaje 
    //-------------------------------------------------------------------------
    
    static void BuscarPersonaje(){
        
    }
    
    //-------------------------------------------------------------------------
    //Guardar y cargar estado del sistema
    //-------------------------------------------------------------------------
    
    static void GuardarCargar(Scanner sc){
        
    }
    
    //-------------------------------------------------------------------------
    //Ver Datos del Estudiante
    //-------------------------------------------------------------------------
    static void DatosEstudiante(){
        System.out.println("-------------------------------------------");
        System.out.println("Nombre: Raul Jose Daniel Paredes Gonzalez");
        System.out.println("Carnet:202400554");
        System.out.println("Curso: IPC1");
        System.out.println("-------------------------------------------");

    }
}
