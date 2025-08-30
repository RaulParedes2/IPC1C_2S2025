/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package proyecto.pkg1;
//Libreriar importadas, Scanner, LocalDateTime, DateTimeFormatter
import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
/**
 *
 * @author Daniel Predes
 */
public class Proyecto1 {
    //Declaracion del tamaño de los vectores
    static String[]Nombres= new String[100];//Numero Max de nombres
    static String[]Categoria= new String[100];//Numero Max 
    static double[] Precio= new double[100];//Numero Max de Precios, con el tipo de dato double(por los decimales)
    static int[] Cantidad = new int[100];//Numero Max 
    static  String[] Codigos = new String[100];//Numero Max 
    static int totalProductos = 0; //El invemtario empieza en 0
    
    public static void main(String[] args) {
       Scanner sc = new Scanner(System.in);
       int opcion; //Variable opcion decalrada como entero
       do{
           MostrarMenu();
           opcion = leerEntero (sc, "Elige una opcion");
           
           // Seleccion de opciones(solo con numero)
           switch (opcion){
               
               case 1: 
                   AgregarProducto(sc);
                   break;
                   
               case 2: 
                   BuscarProducto();
                   break;
                   
               case 3:
                    EliminarProducto(sc);
                    break;
                    
               case 4:
                   RegistrarVenta(sc);
                   break;
                   
               case 5:
                   GenerarReporte(sc);
                   
               case 6:
                   VerDatosEstudante();
                   break;
                   
               case 7:
                   Bitacora();
                   break;
                   
                   //La opcion 8 Escribira Salinedo
               case 8:
                   System.out.println("------------------------------------------------------");
                   System.out.println("Saliendo...");
                   System.out.println("------------------------------------------------------");
                   break;
                   
                   /*En caso contrario de no escribir en el rango de 1 al 8: 
                   Escribira Intentelo de Nuevo */
               default:
                   System.out.println("------------------------------------------------------");
                   System.out.println("Intentelo de Nuevo(1 al 8)");
                   System.out.println("------------------------------------------------------");
  
           }
       }
       
       // En caso de seleccionar la Opcion 8 se cerrrara el programa
       while(opcion !=8);
       sc.close();
    }
    
    //Menu que se mostrara en la consola 
    static void MostrarMenu(){
        System.out.println("==MENU==");
        System.out.println("1. Agergar Produto");
        System.out.println("2. Buscar Producto");
        System.out.println("3. Eliminar Producto");
        System.out.println("4. Registrar Venta");
        System.out.println("5. Generar Reporte");
        System.out.println("6. Ver Datos del Estudiante");
        System.out.println("7. Bitacora");
        System.out.println("8. SALIR");
            }
    
    // En caso de no escribir un numero Saldra el seguiente mensaje ERROR
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
    
    static void AgregarProducto(Scanner sc){
        try{
           if(totalProductos >= 100){
               System.out.println("-------------------------------------------------------------");
               System.out.println("'Inventario Lleno'");
               System.out.println("-------------------------------------------------------------");
               return;
           }
           sc.nextLine();
            System.out.println("Nombre del Producto: ");
            String nombre = sc.nextLine();
            
            System.out.println("Categoria(Camisa, Pantalon, Accesorios, etc): ");
            String categoria = sc.nextLine();
            
            System.out.println(" Ingrese el codigo: ");
            String codigo = sc.nextLine();
            
            //Codigo Unico
            for(int i=0; i<totalProductos; i++){
                if(Codigos[i].equals(codigo)){
                    System.out.println("--------------------------------------------------------------------");
                    System.out.println("'ERROR'(codigo ya existente)");
                    System.out.println("--------------------------------------------------------------------");
                    return;
                }
            }
            System.out.println("Precio del producto(Quetzalez):");
            double precio = sc.nextDouble();
            
            //Ingreso de solo valores positivos
            if(precio <=0){
                System.out.println("--------------------------------------------------------------------");
                System.out.println("'ERROR'(Solo numeros Positivos)");
                System.out.println("--------------------------------------------------------------------");
                return;
            }
            System.out.println("Ingrese la cantidad en Stock: ");
            int cantidad= sc.nextInt();
            
            //Ingreso de valores positivos
            if(cantidad < 0 ){
                System.out.println("--------------------------------------------------------------------");
                System.out.println("'ERROR'(Solo Numeros positivos)");
                System.out.println("--------------------------------------------------------------------");
                return;
            }
            //Guardar en el inventario(vectores)
            Nombres[totalProductos]= nombre;
            Categoria[totalProductos]= categoria;
            Precio[totalProductos]= precio;
            Cantidad[totalProductos]= cantidad;
            Codigos[totalProductos]= codigo;
            
            totalProductos++;
            System.out.println("Producto Agregado");
        }   
        
        catch(Exception e){
            System.out.println("--------------------------------------------------------------------");
            System.out.println("'ERROR'(Al agregar el producto): " + e.getMessage());           
            System.out.println("--------------------------------------------------------------------");
            sc.nextLine();
        }
    }
    static void VerDatosEstudante(){
        System.out.println("--------------------------------------------------------------------");
        System.out.println("Nombre: Raul Jose Daniel Paredes Gonzalez");
        System.out.println("Carnet:202400554");
        System.out.println("Curso: IPC1");
        System.out.println("--------------------------------------------------------------------");
    }
}
