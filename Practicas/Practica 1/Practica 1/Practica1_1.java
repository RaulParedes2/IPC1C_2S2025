/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package practica.pkg1.pkg1;

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
    
    public Personaje(int id, String Nombre, String Arma, String[] Habilidad, int Niveldepoder)
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
    
    public Pelea(int idP1, String NombreP1,int idP2, String NombreP2, String Fechahora )
    {
    this.idP1=idP1;
    this.NombreP1=NombreP1;
    this.idP2=idP2;
    this.NombreP2=NombreP2;
    this.Fechahora=Fechahora;
    }
} 

public class Practica1_1 {
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
        
       //Menu (interactivo)
       
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
                MostrarDatos(sc);
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
                System.out.println("--------------------------------------------------------------------");
                System.out.println("Saliendo...");
                System.out.println("--------------------------------------------------------------------");
                break;
            default:
                System.out.println("--------------------------------------------------------------------");
                System.out.println("Intentelo de nuevo(1 al 9)");
                System.out.println("--------------------------------------------------------------------");
             }
        System.out.println();
       }
       while(opcion !=9);
       sc.close();
    }
    //Menu(solo datos)
    
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
     
}
    static int leerEntero(Scanner sc, String mensaje){
        System.out.print(mensaje);
        while (!sc.hasNextInt()){
        System.out.println("--------------------------------------------------------------------");
        System.out.println("ERROR 'Ingrese un numero'");
        System.out.println("--------------------------------------------------------------------");
        sc.next();
        }
        return sc.nextInt();
    }
        //limite de personajes
        
    static void AgregarPersonaje (Scanner sc){
        if (numeroPersonajes >= Maximo_Personajes){
            System.out.println("--------------------------------------------------------------------");
            System.out.println("Haz alcanzado el Maximo de personajes");
            System.out.println("--------------------------------------------------------------------");
            return;
        }
        //Nombre del Personaje
        
        sc.nextLine();
        
        System.out.print("Nombre del Perosaje:");
       
        String Nombre = sc.nextLine();
        
        for (int i = 0; i <numeroPersonajes; i++ ){
            if (personajes[i].Nombre.equalsIgnoreCase(Nombre)){
                System.out.println("--------------------------------------------------------------------");
                System.out.println("Utilice otro nombre");
                System.out.println("--------------------------------------------------------------------");
                return;
            }
        }
        //Nombre del Arma
        
        System.out.print("Arma:");
        String Arma =sc.nextLine();
        
        //Habilidades del Personaje
        
        String [] Habilidad = new String[5];
        
        System.out.println("Ingrese solo 5 Habilidades(Para finalizar deje vacio)");
       
        for (int i=0; i<5; i++){
            System.out.print("Hablidad_"+(i+1)+":");
            String hab = sc.nextLine();
            if(hab.isEmpty()) break;
            Habilidad[i]=hab;
        }
        //Nivel de poder del personaje de 1 a 100
        
        int Niveldepoder;
        do{
           Niveldepoder =leerEntero(sc, "Nivel de poder (1 a 100):"); 
        }
        while (Niveldepoder<1 || Niveldepoder>100);
        
        personajes[numeroPersonajes] = new Personaje(idActual++, Nombre, Arma, Habilidad, Niveldepoder);
        numeroPersonajes++;
        System.out.println("--------------------------------------------------------------------");
        System.out.println("Nuevo Personaje agregado");
        System.out.println("--------------------------------------------------------------------");
    }
    //Modificacion del Personaje
    
    static void Modificarpersonaje(Scanner sc){
        int id =leerEntero(sc, "Ingrese el ID del Personaje:");
        Personaje P = buscarPorID(id);
        if(P==null){
            System.out.println("--------------------------------------------------------------------");
            System.out.println("Personaje no Encontrado");
            System.out.println("--------------------------------------------------------------------");
             return;
        }
        //Cambiar Nombre del Personaje
        
       sc.nextLine();
       System.out.println("--------------------------------------------------------------------");
       System.out.println("Modificando Personaje:" + P.Nombre);
       System.out.println("--------------------------------------------------------------------");
       
       //Cambiar el Nombre del Arma
       System.out.println("--------------------------------------------------------------------");
       System.out.print("Nueva Arma(Actual: "+P.Arma+"): ");
       System.out.println("--------------------------------------------------------------------");
       String Arma = sc.nextLine();
       if(!Arma.isEmpty()) P.Arma = Arma;
       
       //Modificacion de las Habilidades del Personaje
       
       System.out.println("Modificar Hablididades(Deje vacio para Mantener):");
       for(int i= 0; i < 5 ; i++){
           System.out.println("--------------------------------------------------------------------");
           System.out.print("Habilidad: " + (i + 1) + " (actual: " + (P.Habilidad[i] != null ? P.Habilidad[i] : "-") + "): ");
          
           String hab=sc.nextLine();
           if(!hab.isEmpty()) P.Habilidad[i]=hab;
       }
      //Modificcion del Nivel del personaje
    
       int Niveldepoder;
       do{
           Niveldepoder =leerEntero(sc, "Nivel de Poder(1-100, Actual: "+P.Niveldepoder+"):");
       }
       while (Niveldepoder<1 || Niveldepoder>100);
       P.Niveldepoder=Niveldepoder;
       System.out.println("--------------------------------------------------------------------");
       System.out.println("Personaje Modificado");
       System.out.println("--------------------------------------------------------------------");
    }
       //Eliminar un personaje por su id
    
    static void  Eliminarpersonaje (Scanner sc){
        int id = leerEntero(sc, "Ingrese el ID del Personaje");
        for( int i=0; i<numeroPersonajes; i++){
            if(personajes[i].id==id){
                for(int j=i; j< numeroPersonajes-1;j++){
                    personajes[j]= personajes[j+1];
                }
                personajes[numeroPersonajes-1]=null;
                numeroPersonajes--;
                System.out.println("--------------------------------------------------------------------");
                System.out.println("Personaje Eliminado");
                System.out.println("--------------------------------------------------------------------");
                return;
            }
        }
        System.out.println("--------------------------------------------------------------------");
        System.out.println("No se encoontro el Personaje");
        System.out.println("--------------------------------------------------------------------");
    }
    //Mostrar datos del personaje
    
    static void MostrarDatos(Scanner sc){
        int id=leerEntero(sc,"Ingrese el ID del Personaje: ");
       Personaje P= buscarPorID(id);
       if(P==null){
           System.out.println("--------------------------------------------------------------------");
           System.out.println("No se encontro el Personaje");
           System.out.println("--------------------------------------------------------------------");
           return;
       }
       System.out.println("--------------------------------------------------------------------");
       System.out.println("ID:"+P.id);
       System.out.println("Nombre:"+P.Nombre);
       System.out.println("Arma:"+P.Arma);
       System.out.println("Habilidad");
       System.out.println("--------------------------------------------------------------------");
       for(String h: P.Habilidad){
           if (h !=null) System.out.println("-"+h);
       }
       System.out.println("Nivel de Poder"+P.Niveldepoder);
    }
    //Lista de los personajes
    
    static void Listapersonajes(){
        if(numeroPersonajes==0){
            System.out.println("--------------------------------------------------------------------");
            System.out.println("No hay personajes registrados");
            System.out.println("--------------------------------------------------------------------");
            return;
        }
        System.out.println("Lista de los Personajes:");
        for(int i =0; i<numeroPersonajes; i++){
            System.out.println("--------------------------------------------------------------------");
            System.out.println("ID: "+personajes[i].id + "|Nombre: " + personajes[i].Nombre + "|Nivel de Poder: " + personajes[i].Niveldepoder);
        }   System.out.println("--------------------------------------------------------------------");
        
    }
    //Pelea de los Personajes
    
    static void Peleando (Scanner sc){
        if(numeroPersonajes<2){
            System.out.println("--------------------------------------------------------------------");
            System.out.println("Debe de Haber almenos 2 Personajes");
            System.out.println("--------------------------------------------------------------------");
            return;
        }
        int id1=leerEntero(sc, "Ingrese el ID del Primer Personaje: ");
        int id2=leerEntero(sc, "Ingrese el ID del Segundo Personaje: ");
        
        if(id1==id2){
            System.out.println("--------------------------------------------------------------------");
            System.out.println("No se puede realizar el probelma con el mismo");    
            System.out.println("--------------------------------------------------------------------");
            return;
        }
        Personaje P1= buscarPorID(id1);
        Personaje P2= buscarPorID(id2);
        
        if(P1== null || P2== null){
        System.out.println("--------------------------------------------------------------------");
        System.out.println("Uno de los Persoanjes NO EXISTEN");
        System.out.println("--------------------------------------------------------------------");
        return;
    }
        String Fechahora = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
        peleas[numeroPeleas] = new Pelea(P1.id, P1.Nombre, P2.id, P2.Nombre, Fechahora);
        numeroPeleas++;
        System.out.println("--------------------------------------------------------------------");
        System.out.println("Pelea registrada: " + P1.Nombre + " vs " + P2.Nombre + " en " + Fechahora);
        System.out.println("--------------------------------------------------------------------");
    }
    static void HistorialPeleas(){
        if(numeroPeleas==0){
            System.out.println("--------------------------------------------------------------------");
            System.out.println("No Hay Registro de Peleas...");
            System.out.println("--------------------------------------------------------------------");
            return;
        }
        
        System.out.println("Historial de Peleas");
        
        for(int i= 0; i<numeroPeleas; i++){
            Pelea Pl= peleas[i];
            System.out.println("--------------------------------------------------------------------");
            System.out.println(Pl.Fechahora + " -> " + Pl.NombreP1 + " vs " + Pl.NombreP2);
            System.out.println("--------------------------------------------------------------------");
        }
    }
    static void DatosEstudiante(){
        System.out.println("--------------------------------------------------------------------");
        System.out.println("Nombre: Raul Jose Daniel Paredes Gonzalez");
        System.out.println("Carnet:202400554");
        System.out.println("Curso: IPC1");
        System.out.println("--------------------------------------------------------------------");
    }
    static Personaje buscarPorID(int id){
        for(int i=0; i<numeroPersonajes; i++){
            if(personajes[i].id == id) return personajes[i];
        }
     return null;   
    }
}
