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
           opcion = leerEntero (sc, "Elige una opcion: ");
           System.out.println("--------------------------------------------------------------------");
           
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
        System.out.println("--------------------------------------------------------------------");
        System.out.println("==MENU==");
        System.out.println("1. Agergar Produto");
        System.out.println("2. Buscar Producto");
        System.out.println("3. Eliminar Producto");
        System.out.println("4. Registrar Venta");
        System.out.println("5. Generar Reporte");
        System.out.println("6. Ver Datos del Estudiante");
        System.out.println("7. Bitacora");
        System.out.println("8. SALIR");
        System.out.println("--------------------------------------------------------------------");
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
    //---------------------------------------------------------------------------
    //Agregar Producto
    //--------------------------------------------------------------------------
    
    
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
            
            System.out.println("Ingrese el codigo: ");
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
            System.out.println("Precio del producto(Quetzalez): Q ");
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
    
    static void BuscarProducto(){
         Scanner sc = new Scanner(System.in);

        try{
            //Menu de la opcion de buscar Producto
            System.out.println("==BUSCAR PRODUCTO==");
            System.out.println("1. Buscar por Nombre");
            System.out.println("2. Buscar por Codigo");
            System.out.println("3. Buscar por Categoria");
            System.out.println("--------------------------------------------------------------------");
            System.out.println("Elige una opcion: ");
            
            int opcion =sc.nextInt();
            sc.nextLine();
            
            boolean encontrado = false; //Defirni la variable encontrado con el tipo de dato boolean
            
            switch(opcion){
            
                case 1:
                    System.out.println("Ingrese el Nombre del Producto");
                    String nomBuscar = sc.nextLine();
                    
                    for(int i=0; i<totalProductos; i++){
                        if(Nombres[i].equalsIgnoreCase(nomBuscar)){
                            MostrarProducto(i);
                            encontrado=true;
                        }
                    }
                    break;
                    
                case 2:
                    System.out.println("Ingrese el Codigo del Producto");
                    String CodBuscar = sc.nextLine();
                    
                    for(int i=0; i<totalProductos; i++){
                        if(Codigos[i].equalsIgnoreCase(CodBuscar)){
                            MostrarProducto(i);
                            encontrado=true;
                        }
                    }
                    break;
                    
                case 3:
                       System.out.println("Ingrese la Categoria del Producto");
                       String catBuscar = sc.nextLine();
                       
                       for(int i=0; i<totalProductos; i++){
                           if(Categoria[i].equalsIgnoreCase(catBuscar)){
                               MostrarProducto(i);
                               encontrado=true;
                           }
                       }
                       break;
                       
                default:
                    System.out.println("--------------------------------------------------------------------");
                    System.out.println("Opcion incorrecta");//Solo numeros ingresados del 1 a 3
                    System.out.println("--------------------------------------------------------------------");
        }
            if(!encontrado){
                System.out.println("--------------------------------------------------------------------");
                System.out.println("Producto NO encontrado");//En caso de que el producto sea falso
                System.out.println("--------------------------------------------------------------------");
            }
        }
        catch(Exception e){
            System.out.println("--------------------------------------------------------------------");
            System.out.println("'ERROR' al buscar1: " + e.getMessage());// EL sistema sigue funcionando
            System.out.println("--------------------------------------------------------------------");
            
        }
  
    }
    // Sistema Auxiliar para mostrar los datos
    static void MostrarProducto (int i){
        System.out.println("--------------------------------------------------------------------");
        System.out.println("Nombre: " + Nombres[i]);
        System.out.println("Categoria: " + Categoria[i]);
        System.out.println("Codigo: " + Codigos[i]);
        System.out.println("Precio: Q " + Precio[i]);
        System.out.println("Cantidad: " + Cantidad[i]);
        System.out.println("--------------------------------------------------------------------");
    }
    //-------------------------------------------------------------------------------------------
    //Eliminar Producto
    //-------------------------------------------------------------------------------------------
    
    static void EliminarProducto(Scanner sc){

        try{
            sc.nextLine();
            System.out.println("Ingrese el codigo del producto: ");
            String codEliminar = sc.nextLine();
            
            // Parte para la eliminacion del producto
            boolean encontrado = false;
            
            for(int i = 0; i <totalProductos; i++){
                if(Codigos[i].equals(codEliminar)){
                    encontrado=true;
                    
                    //Mensaje de confirmacion
                    
                    System.out.println("==Confirmacion de eliminacion si, no (s/n)==");
                    String Confirmacion=sc.nextLine();
                    
                    //Proceso de elminacion
                    
                    if(Confirmacion.equalsIgnoreCase("s")){
                        //Desplazamiento en el vector
                        for(int j = i; j<totalProductos-1; j++){
                            Nombres[j] = Nombres[j+1];
                            Categoria[j] = Categoria[j+1];
                            Precio[j] = Precio[j+1];
                            Cantidad[j] = Cantidad[j+1];
                            Codigos[j] = Codigos[j+1];
                        }
                        totalProductos--;
                        System.out.println("--------------------------------------------------------------------");
                        System.out.println("Producto 'ELIMINADO'");
                        System.out.println("--------------------------------------------------------------------");
                    }
                    else{
                        System.out.println("--------------------------------------------------------------------");
                        System.out.println("CANCELADO");
                        System.out.println("--------------------------------------------------------------------");
                    }
                    break;
                }
            }
            if (!encontrado){
                System.out.println("--------------------------------------------------------------------");
                System.out.println("El codigo No existe");
                System.out.println("--------------------------------------------------------------------");
            }
        }
        catch(Exception e){
            System.out.println("ERROR"+e.getMessage());
        }
}
    
    //-------------------------------------------------------------------------------------------
    //Registrar Venta
    //-------------------------------------------------------------------------------------------
    
    static void RegistrarVenta(Scanner sc){
        
    }
    
    //-------------------------------------------------------------------------------------------
    //Reporte
    //-------------------------------------------------------------------------------------------
    
    static void GenerarReporte(Scanner sc){
        
    }
    
    //-------------------------------------------------------------------------------------------
    //Datos del estudianre
    //-------------------------------------------------------------------------------------------
    
    static void VerDatosEstudante(){
        System.out.println("--------------------------------------------------------------------");
        System.out.println("Nombre: Raul Jose Daniel Paredes Gonzalez");
        System.out.println("Carnet:202400554");
        System.out.println("Curso: IPC1");
        System.out.println("--------------------------------------------------------------------");
    }
    
    //-------------------------------------------------------------------------------------------
    //Bitacora
    //-------------------------------------------------------------------------------------------
    static void Bitacora(){
        
    }
}
