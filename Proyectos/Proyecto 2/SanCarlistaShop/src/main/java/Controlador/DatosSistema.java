/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Controlador.*;
import Modelo.*;
import java.io.*;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import javax.swing.JOptionPane;

/**
 * Carga y guarda los datos del sistema (productos, vendedores, clientes,
 * historial).
 */
public class DatosSistema {
    private Vendedores vendedor;
    private static final String RUTA_PRODUCTOS = "src/main/resources/data/productos.csv";
    private static final String RUTA_VENDEDORES = "src/main/resources/data/vendedores.csv";
    private static final String RUTA_CLIENTES = "ssrc/main/resources/data/clientes.csv";
    private static final String RUTA_HISTORIAL = "src/main/resources/data/historial_stock.csv";
   

    // ===========================================================
    // === MÉTODO PRINCIPAL DE CARGA DE TODO EL SISTEMA ==========
    // ===========================================================
    public static void cargarTodo() {
        
        cargarProductos();
        sincronizarProductos();
        
        cargarVendedores();
        cargarClientes();
        cargarHistorial();
        System.out.println("Datos del sistema cargados correctamente.");
    }
    
    
    // ===========================================================
    // === PRODUCTOS =============================================
    // ===========================================================
    public static void cargarProductos() {
    File archivo = new File(RUTA_PRODUCTOS);
    int cargados = 0, errores = 0;

    if (!archivo.exists()) {
        System.out.println("️ No se encontró productos.csv");
        return;
    }

    try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
        String linea;
        while ((linea = br.readLine()) != null) {
            if (linea.toLowerCase().contains("codigo")) continue; // Ignorar encabezado

            String[] partes = linea.split(",");
            if (partes.length != 4) {
                errores++;
                continue;
            }

            String codigo = partes[0].trim();
            String nombre = partes[1].trim();
            String categoria = partes[2].trim();
            String atributo = partes[3].trim();

            // Verificar duplicados
            if (Productos.buscarProducto(codigo) != null) {
                errores++;
                continue;
            }

            Producto nuevo = null;

            try {
                switch (categoria.toLowerCase()) {
                    case "tecnologia":
                    case "tecnología":
                        int garantia = Integer.parseInt(atributo);
                        nuevo = new ProductoTec(codigo, nombre, garantia, atributo);
                        break;

                    case "alimento":
                        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        LocalDate fecha = LocalDate.parse(atributo, formato);
                        nuevo = new ProductoAl(codigo, nombre, fecha, atributo);
                        break;

                    case "general":
                        nuevo = new ProductoGe(codigo, nombre,categoria, atributo);
                        break;

                    default:
                        System.out.println("️ Categoría desconocida: " + categoria);
                        errores++;
                        continue;
                }
            } catch (Exception e) {
                System.out.println("️ Error al procesar producto " + codigo + ": " + e.getMessage());
                errores++;
                continue;
            }

            if (nuevo != null) {
                Productos.crearProducto(nuevo);
                cargados++;
            }
        }

        System.out.println("Productos cargados correctamente desde " + RUTA_PRODUCTOS);
        System.out.println("   Productos cargados: " + cargados + " | Errores: " + errores);

    } catch (IOException e) {
        System.out.println("Error al leer productos.csv: " + e.getMessage());
    }
}
            // ===========================================================
            // === VENDEDORES ============================================
            // ===========================================================
    public static void cargarVendedores() {
        File archivo = new File(RUTA_VENDEDORES);
        if (!archivo.exists()) {
            System.out.println("️ No se encontró vendedores.csv");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.startsWith("Código")) {
                    continue;
                }
                String[] partes = linea.split(",");
                if (partes.length < 5) {
                    continue;
                }

                String codigo = partes[0].trim();
                String nombre = partes[1].trim();
                String genero = partes[2].trim();
                String password = partes[3].trim();
                int ventas = Integer.parseInt(partes[4].trim());

                Vendedores v = new Vendedores(codigo, nombre, genero, password);
                for (int i = 0; i < ventas; i++) {
                    v.confirmarVenta(); // mantener ventas
                }
                Vendedor.crearVendedor(v);
            }
        } catch (IOException e) {
            System.out.println(" Error al cargar vendedores: " + e.getMessage());
        }
    }

    // ===========================================================
    // === CLIENTES ==============================================
    // ===========================================================
   public static void cargarClientes() {
    File archivo = new File(RUTA_CLIENTES);
    if (!archivo.exists()) {
        System.out.println("️ No se encontró clientes.csv");
        return;
    }

    try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
        String linea;
        while ((linea = br.readLine()) != null) {
            if (linea.toLowerCase().contains("codigo")) continue;

            String[] partes = linea.split(",");
            if (partes.length < 5) continue; // debe tener 5 campos

            String codigo = partes[0].trim();
            String nombre = partes[1].trim();
            String genero = partes[2].trim();
            String password = partes[3].trim();

            try {
                DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate fecha = LocalDate.parse(partes[4].trim(), formato);

                Cliente nuevo = new Cliente(codigo, nombre, genero, password, fecha);
                Clientes.crearCliente(nuevo); // ahora se guarda
            } catch (Exception e) {
                System.out.println("️ Fecha inválida para cliente " + codigo);
            }
        }

        System.out.println(" Clientes cargados correctamente.");

    } catch (IOException e) {
        System.out.println("Error al cargar clientes: " + e.getMessage());
    }
}

    // ===========================================================
    // === HISTORIAL STOCK =======================================
    // ===========================================================
    public static void cargarHistorial() {
        File archivo = new File(RUTA_HISTORIAL);
        if (!archivo.exists()) {
            System.out.println("️ No se encontró historial_stock.csv");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            boolean encabezado = true;
            while ((linea = br.readLine()) != null) {
                if (encabezado) {
                    encabezado = false;
                    continue;
                } // saltar encabezado
                HistorialStock.agregarMovimiento(linea);
            }
        } catch (IOException e) {
            System.out.println(" Error al cargar historial: " + e.getMessage());
        }
    
    }
    
    // ===========================================================
// === SINCRONIZAR PRODUCTOS ENTRE CONTROLADORES =============
// ===========================================================
public static void sincronizarProductos() {
    Producto[] lista = Productos.getProductos();
    int total = Productos.getCantidadProductos();

    for (int i = 0; i < total; i++) {
        Producto p = lista[i];
        if (p != null && Vendedor.buscarProducto(p.getCodigo()) == null) {
            Vendedor.crearProducto(p);
        }
    }

    System.out.println(" Sincronización completa: " + total + " productos sincronizados con Vendedor.");
    }
}

//Pendiente carga de datos de los clientes