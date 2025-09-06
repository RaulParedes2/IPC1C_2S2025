/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package proyecto.pkg1;
//Libreriar importadas, Scanner, LocalDateTime, DateTimeFormatter

import java.util.Scanner;

//Librerias para el registro de las ventas
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

//Librerias para generar reportes
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.time.format.FormatStyle;

/**
 *
 * @author Daniel Predes
 */
public class Proyecto1 {

    //------------------------------------------------------------------------
    //Declaracion del tamaño de los vectores
    //-----------------------------------------------------------------------
    static String[] Nombres = new String[100];//Numero Max de nombres
    static String[] Categoria = new String[100];//Numero Max 
    static double[] Precio = new double[100];//Numero Max de Precios, con el tipo de dato double(por los decimales)
    static int[] Cantidad = new int[100];//Numero Max 
    static String[] Codigos = new String[100];//Numero Max 
    static int totalProductos = 0; //El invemtario empieza en 0

    //------------------------------------------------------------------------
    //Declaracion del tamaño de la bitacora
    //------------------------------------------------------------------------
    static String[] Historial = new String[300];
    static int totalHistorial = 0;
    static final String Archivo_Ventas = "Ventas.txt";
    //------------------------------------------------------------------------

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int opcion; //Variable opcion decalrada como entero

        do {
            MostrarMenu();
            opcion = leerEntero(sc, "Elige una opcion: ");
            Bitacora("Menu Opcion: " + opcion, (opcion >= 1 && opcion <= 8));
            System.out.println("--------------------------------------------------------------------");

            // Seleccion de opciones(solo con numero)
            switch (opcion) {

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
                    break;
                case 6:
                    VerDatosEstudante();
                    break;

                case 7:
                    Bitacora();
                    break;

                //La opcion 8 Escribira Salinedo
                case 8:

                    System.out.println("Saliendo...");
                    System.out.println("--------------------------------------------------------------------");
                    break;

                /*En caso contrario de no escribir en el rango de 1 al 8: 
                   Escribira Intentelo de Nuevo */
                default:

                    System.out.println("Intentelo de Nuevo(1 al 8)");

                    Bitacora("MENU | Fuera de rango", false);
            }
        } // En caso de seleccionar la Opcion 8 se cerrrara el programa
        while (opcion != 8);
        sc.close();
    }

    //Menu que se mostrara en la consola 
    static void MostrarMenu() {
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
    static int leerEntero(Scanner sc, String mensaje) {
        System.out.print(mensaje);
        while (!sc.hasNextInt()) {
            System.out.println("--------------------------------------------------------------------");
            System.out.println("ERROR 'Ingrese un numero'");
            System.out.println("--------------------------------------------------------------------");
            Bitacora("Entrada de validacion incorrecta | Ingreso de un Caracter", false);
            sc.next();
        }
        return sc.nextInt();
    }

    //--------------------------------------------------------------------------
    //Bitacora
    //--------------------------------------------------------------------------
    static void Bitacora(String accion, boolean exito) {
        try {
            if (totalHistorial < Historial.length) {
                LocalDateTime ahora = LocalDateTime.now();
                DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                String estado = exito ? "Correcto" : "Error";
                Historial[totalHistorial]
                        = ahora.format(formato) + "|" + accion + "|" + estado + "|Usuario: Raul Paredes";
                totalHistorial++;
            }
        } catch (Exception e) {
            System.out.println("ERROR Accion no registrada" + e.getMessage());
        }
    }

    static void Bitacora() {
        System.out.println("==BITACORA==");
        if (totalHistorial == 0) {
            System.out.println("No hay registro de Acciones");
            return;
        }
        for (int i = 0; i < totalHistorial; i++) {
            System.out.println(Historial[i]);
        }
    }

    //---------------------------------------------------------------------------
    //Agregar Producto
    //--------------------------------------------------------------------------
    static void AgregarProducto(Scanner sc) {
        try {
            if (totalProductos >= 100) {
                System.out.println("-------------------------------------------------------------");
                System.out.println("'Inventario Lleno'");
                System.out.println("-------------------------------------------------------------");
                Bitacora("Agregar Producto | Inventario LLeno ", false);
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
            for (int i = 0; i < totalProductos; i++) {
                if (Codigos[i].equals(codigo)) {
                    System.out.println("--------------------------------------------------------------------");
                    System.out.println("'ERROR'(codigo ya existente)");
                    System.out.println("--------------------------------------------------------------------");
                    Bitacora("Agregar Producto | Codigo Existente", false);
                    return;
                }
            }
            System.out.println("Precio del producto(Quetzalez): Q ");
            double precio = sc.nextDouble();

            //Ingreso de solo valores positivos en precio
            if (precio <= 0) {
                System.out.println("--------------------------------------------------------------------");
                System.out.println("'ERROR'(Solo numeros Positivos)");
                System.out.println("--------------------------------------------------------------------");
                Bitacora("Agregar Producto | Numero Negativo ", false);
                return;
            }
            System.out.println("Ingrese la cantidad en Stock: ");
            int cantidad = sc.nextInt();

            //Ingreso de valores positivos en Stock
            if (cantidad < 0) {
                System.out.println("--------------------------------------------------------------------");
                System.out.println("'ERROR'(Solo Numeros positivos)");
                System.out.println("--------------------------------------------------------------------");
                Bitacora("Agregar Producto | Numero negativo", false);
                return;
            }
            //Guardar en el inventario(vectores)
            Nombres[totalProductos] = nombre;
            Categoria[totalProductos] = categoria;
            Precio[totalProductos] = precio;
            Cantidad[totalProductos] = cantidad;
            Codigos[totalProductos] = codigo;

            totalProductos++;
            System.out.println("Producto Agregado");
            Bitacora("Agregar Producto | Agregado ", true);
        } catch (Exception e) {
            System.out.println("--------------------------------------------------------------------");
            System.out.println("'ERROR'(Al agregar el producto): " + e.getMessage());
            System.out.println("--------------------------------------------------------------------");
            Bitacora("Agregar Producto | ERROR del sistema", false);
            sc.nextLine();
        }

    }

    static void BuscarProducto() {
        Scanner sc = new Scanner(System.in);

        try {
            //Menu de la opcion de buscar Producto
            System.out.println("==BUSCAR PRODUCTO==");
            System.out.println("1. Buscar por Nombre");
            System.out.println("2. Buscar por Codigo");
            System.out.println("3. Buscar por Categoria");
            System.out.println("--------------------------------------------------------------------");
            System.out.println("Elige una opcion: ");

            int opcion = sc.nextInt();
            sc.nextLine();
            Bitacora("Menu Buscar Opcion: " + opcion, (opcion >= 1 && opcion <= 3));

            boolean encontrado = false; //Defirni la variable encontrado con el tipo de dato boolean

            switch (opcion) {

                case 1:
                    System.out.println("Ingrese el Nombre del Producto");
                    String nomBuscar = sc.nextLine();

                    for (int i = 0; i < totalProductos; i++) {
                        if (Nombres[i].equalsIgnoreCase(nomBuscar)) {
                            MostrarProducto(i);
                            encontrado = true;
                        }
                    }
                    break;

                case 2:
                    System.out.println("Ingrese el Codigo del Producto");
                    String CodBuscar = sc.nextLine();

                    for (int i = 0; i < totalProductos; i++) {
                        if (Codigos[i].equalsIgnoreCase(CodBuscar)) {
                            MostrarProducto(i);
                            encontrado = true;
                        }
                    }
                    break;

                case 3:
                    System.out.println("Ingrese la Categoria del Producto");
                    String catBuscar = sc.nextLine();

                    for (int i = 0; i < totalProductos; i++) {
                        if (Categoria[i].equalsIgnoreCase(catBuscar)) {
                            MostrarProducto(i);
                            encontrado = true;
                        }
                    }
                    break;

                default:
                    System.out.println("--------------------------------------------------------------------");
                    System.out.println("Opcion incorrecta");//Solo numeros ingresados del 1 a 3
                    System.out.println("--------------------------------------------------------------------");
                    Bitacora("Buscar Producto | Opcio fuera de rango", false);
            }
            if (!encontrado) {
                System.out.println("--------------------------------------------------------------------");
                System.out.println("Producto NO encontrado");//En caso de que el producto sea falso
                System.out.println("--------------------------------------------------------------------");
            }
            Bitacora("Buscar Producto ", false);
        } catch (Exception e) {
            System.out.println("--------------------------------------------------------------------");
            System.out.println("'ERROR' al buscar1: " + e.getMessage());// EL sistema sigue funcionando
            System.out.println("--------------------------------------------------------------------");
            Bitacora("Buscar Producto | ERROR del Sistema ", false);

        }

    }

    // Sistema Auxiliar para mostrar los datos
    static void MostrarProducto(int i) {
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

    static void EliminarProducto(Scanner sc) {

        try {
            sc.nextLine();
            System.out.println("Ingrese el codigo del producto: ");
            String codEliminar = sc.nextLine();

            // Parte para la eliminacion del producto
            boolean encontrado = false;

            for (int i = 0; i < totalProductos; i++) {
                if (Codigos[i].equals(codEliminar)) {
                    encontrado = true;

                    //Mensaje de confirmacion
                    System.out.println("==Confirmacion de eliminacion si, no (s/n)==");
                    String Confirmacion = sc.nextLine();

                    //Proceso de elminacion
                    if (Confirmacion.equalsIgnoreCase("s")) {
                        //Desplazamiento en el vector
                        for (int j = i; j < totalProductos - 1; j++) {
                            Nombres[j] = Nombres[j + 1];
                            Categoria[j] = Categoria[j + 1];
                            Precio[j] = Precio[j + 1];
                            Cantidad[j] = Cantidad[j + 1];
                            Codigos[j] = Codigos[j + 1];
                        }
                        totalProductos--;
                        System.out.println("--------------------------------------------------------------------");
                        System.out.println("Producto 'ELIMINADO'");
                        System.out.println("--------------------------------------------------------------------");
                        Bitacora("Eliminar Produto | Eliminado", true);
                    } else {
                        System.out.println("--------------------------------------------------------------------");
                        System.out.println("CANCELADO");
                        System.out.println("--------------------------------------------------------------------");
                        Bitacora("Eliminar Producto | Cancelado ", true);
                    }
                    break;
                }
            }
            if (!encontrado) {
                System.out.println("--------------------------------------------------------------------");
                System.out.println("El codigo No existe");
                System.out.println("--------------------------------------------------------------------");
                Bitacora("Eliminar Producto | Codigo Error", false);
            }
        } catch (Exception e) {
            System.out.println("ERROR" + e.getMessage());
            Bitacora("Eliminar Producto | ERROR del Sistema", false);
        }
    }

    //-------------------------------------------------------------------------------------------
    //Registrar Venta
    //-------------------------------------------------------------------------------------------
    static void RegistrarVenta(Scanner sc) {
        try {
            sc.nextLine();
            System.out.println("Ingrese El codigo del producto");
            String cod = sc.nextLine();
            boolean encontrado = false;

            for (int i = 0; i < totalProductos; i++) {
                if (Codigos[i].equalsIgnoreCase(cod)) {
                    encontrado = true;
                    System.out.println("Cantidad: ");
                    int cant = sc.nextInt();

                    if (cant <= 0 || cant > Cantidad[i]) {
                        System.out.println("--------------------------------------------------------------------");
                        System.out.println("'ERROR' Cantidad en Stock insufuciente");
                        Bitacora("Registrar Venta | Fuera de Rango (Stock)", false);
                        return;
                    }
                    Cantidad[i] -= cant;
                    double total = cant * Precio[i];
                    LocalDateTime fecha = LocalDateTime.now();

                    //Nombre del archivo
                    String registro = fecha + "|" + Codigos[i] + "|" + Nombres[i] + "| Cantidad: " + cant + "|Total: Q" + total;

                    File archivo = new File(Archivo_Ventas);

                    // Escritura del Archivo
                    try (FileWriter writer = new FileWriter(archivo, true)) {
                        writer.write(registro + "\n");
                        System.out.println("--------------------------------------------------------------------");
                        System.out.println("Venta Registrada: " + archivo.getAbsolutePath());
                    } catch (IOException e) {
                        System.out.println("'ERROR' al Escribir el Archivo");
                        Bitacora("Registro Venta | ERROR en el Sistema", false);
                        return;
                    }
                    // Lectura del Archivo
                    try (FileReader fr = new FileReader(archivo); BufferedReader br = new BufferedReader(fr)) {
                        String linea;
                        System.out.println("==Historial de venta==");

                        while ((linea = br.readLine()) != null) {
                            System.out.println(linea);
                        }

                    } catch (IOException e) {
                        System.out.println("ERROR al leer el Archivo");
                    }
                    Bitacora("Registro venta ", true);
                    return;
                }
            }
            if (!encontrado) {
                System.out.println("--------------------------------------------------------------------");
                System.out.println("Producto no Localizado");
                Bitacora("Registro Venta | No localizado", false);
            }
        } catch (Exception e) {
            System.out.println("ERROR de de la venta" + e.getMessage());
            Bitacora("Registro Venta | Fallo el sistema", false);
        }
    }

    //-------------------------------------------------------------------------------------------
    //Reporte
    //-------------------------------------------------------------------------------------------
    static void GenerarReporte(Scanner sc) {
        try {
            sc.nextLine();
            System.out.println("==Reportes==");
            System.out.println("1. Reporte Stock");
            System.out.println("2. Reporte Venta");
            int opcion = leerEntero(sc, "Elige una opcion (1 o 2): ");
            sc.nextLine();

            if (opcion != 1 && opcion != 2) {
                System.out.println("--------------------------------------------------------------------");
                System.out.println("ERROR: Solo se permite ingresar 1 o 2.");
                Bitacora("Generar Reporte", false);
                return;
            }

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd_MM_yyyy_HH_mm_ss");
            String fecha = LocalDateTime.now().format(dtf);
            String nombreArchivo = (opcion == 1) ? fecha + "-Stock.pdf" : fecha + "_Venta.pdf";

            Document doc = new Document();
            PdfWriter.getInstance(doc, new java.io.FileOutputStream(nombreArchivo));
            doc.open();

            if (opcion == 1) {
                doc.add(new Paragraph("==Reporte Stock=="));
                for (int i = 0; i < totalProductos; i++) {
                    doc.add(new Paragraph(Nombres[i] + "|" + Codigos[i] + "|" + Categoria[i]
                            + "| Q" + Precio[i] + "| Cantidad: " + Cantidad[i]));

                }
                Bitacora("Reporte Stock", true);
            } else {
                doc.add(new Paragraph("==Reporte Venta=="));
                File file = new File(Archivo_Ventas);

                if (file.exists()) {
                    Scanner lector = new Scanner(file);
                    while (lector.hasNextLine()) {
                        doc.add(new Paragraph(lector.nextLine()));
                    }
                    lector.close();
                } else {
                    doc.add(new Paragraph("No hay ventas registradas."));
                }
                Bitacora("Reporte Venta", true);
            }
            doc.close();
            System.out.println("Reporte: " + nombreArchivo);
        } catch (DocumentException | IOException e) {
            System.out.println("------------------------------------------------------");
            System.out.println("ERROR al generar el Reporte" + e.getMessage());
            Bitacora("Generar Reporte | Error del sistema ", false);
        }

    }

    //-------------------------------------------------------------------------------------------
    //Datos del estudianre
    //-------------------------------------------------------------------------------------------
    static void VerDatosEstudante() {
        System.out.println("--------------------------------------------------------------------");
        System.out.println("Nombre: Raul Jose Daniel Paredes Gonzalez");
        System.out.println("Carnet:202400554");
        System.out.println("Curso: IPC1");
        System.out.println("--------------------------------------------------------------------");
    }

}
