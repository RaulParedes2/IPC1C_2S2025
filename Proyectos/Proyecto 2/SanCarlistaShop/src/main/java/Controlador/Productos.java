/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.*;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Controlador de gestión de productos y stock Autor: Daniel Predes +
 * actualización ChatGPT
 */
public class Productos {

    private static final String RUTA_PRODUCTOS = "src/main/resources/data/productos.csv";
    private static final String RUTA_STOCK = "src/main/resources/data/stock.csv";
    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private static Producto[] productos = new Producto[100];
    private static int contadorProductos = 0;

    // ============================================================
    // BLOQUE ESTÁTICO (Carga inicial de archivos)
    // ============================================================
    static {
        cargarProductos();
        cargarStock();
    }

    // ============================================================
    // 1. CARGAR ARCHIVOS CSV
    // ============================================================
    public static void cargarProductos() {
        try (BufferedReader br = new BufferedReader(new FileReader(RUTA_PRODUCTOS))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.startsWith("Codigo")) {
                    continue;
                }
                String[] partes = linea.split(",");
                if (partes.length < 4) {
                    continue;
                }

                String codigo = partes[0].trim();
                String nombre = partes[1].trim();
                String categoria = partes[2].trim();
                String atributo = partes[3].trim();

                Producto p = null;
                switch (categoria.toLowerCase()) {
                    case "alimento":
                        LocalDate fechaCad = LocalDate.parse(atributo, FORMATO_FECHA);
                        p = new ProductoAl(codigo, nombre, fechaCad, atributo);
                        break;
                    case "tecnologia":
                        p = new ProductoTec(codigo, nombre, 12, atributo);
                        break;
                    default:
                        p = new ProductoGe(codigo, nombre, categoria, atributo);
                        break;
                }
                productos[contadorProductos++] = p;
            }
            System.out.println("[INFO] Productos cargados correctamente.");
        } catch (IOException e) {
            System.err.println("[ERROR] No se pudo leer productos.csv: " + e.getMessage());
        }
    }

    public static void cargarStock() {
        try (BufferedReader br = new BufferedReader(new FileReader(RUTA_STOCK))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.startsWith("Codigo")) {
                    continue;
                }

                String[] partes = linea.split(",");
                if (partes.length < 3) {
                    continue;
                }

                String codigo = partes[0].trim();
                int stock = Integer.parseInt(partes[1].trim());
                double precio = Double.parseDouble(partes[2].trim());

                Producto p = buscarProducto(codigo);
                if (p != null) {
                    p.setStock(stock);
                    p.setPrecio(precio);
                }
            }
            System.out.println("[INFO] Stock y precios cargados correctamente.");
        } catch (IOException e) {
            System.err.println("[ERROR] No se pudo leer stock.csv: " + e.getMessage());
        }
    }

    // ============================================================
    // 2. GUARDAR ARCHIVOS CSV
    // ============================================================
    public static void guardarProductos() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(RUTA_PRODUCTOS))) {
            for (int i = 0; i < contadorProductos; i++) {
                Producto p = productos[i];
                pw.println(p.getCodigo() + "," + p.getNombre() + "," + p.getCategoria() + "," + p.getAtributo());
            }
            System.out.println("[INFO] productos.csv guardado correctamente.");
        } catch (IOException e) {
            System.err.println("[ERROR] No se pudo guardar productos.csv: " + e.getMessage());
        }
    }

    public static void guardarStock() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(RUTA_STOCK))) {
            pw.println("Codigo,Stock,Precio"); // Agregar encabezado para mantener formato CSV

            for (int i = 0; i < contadorProductos; i++) {
                Producto p = productos[i];
                if (p != null) {
                    //  Evita precios nulos o reiniciados
                    double precioSeguro = p.getPrecio() > 0 ? p.getPrecio() : 0.01;
                    pw.println(p.getCodigo() + "," + p.getStock() + "," + precioSeguro);
                }
            }
            System.out.println("[INFO] stock.csv guardado correctamente.");
        } catch (IOException e) {
            System.err.println("[ERROR] No se pudo guardar stock.csv: " + e.getMessage());
        }
    }

    // ============================================================
    // 3. CRUD BÁSICO DE PRODUCTOS
    // ============================================================
    public static void crearProducto(Producto p) {
        if (buscarProducto(p.getCodigo()) == null) {
            productos[contadorProductos++] = p;
            guardarProductos();
            guardarStock();
        } else {
            System.out.println("[ADVERTENCIA] Código duplicado: " + p.getCodigo());
        }
    }

    public static Producto buscarProducto(String codigo) {
        for (int i = 0; i < contadorProductos; i++) {
            Producto p = productos[i];
            if (p != null && p.getCodigo().equalsIgnoreCase(codigo)) {
                return p;
            }
        }
        return null;
    }

    public static void eliminarProducto(String codigo) {
        for (int i = 0; i < contadorProductos; i++) {
            if (productos[i] != null && productos[i].getCodigo().equalsIgnoreCase(codigo)) {
                for (int j = i; j < contadorProductos - 1; j++) {
                    productos[j] = productos[j + 1];
                }
                productos[--contadorProductos] = null;
                guardarProductos();
                guardarStock();
                System.out.println("[INFO] Producto eliminado: " + codigo);
                return;
            }
        }
        System.out.println("[ERROR] Producto no encontrado: " + codigo);
    }

    // ============================================================
    // 4. LISTAR PRODUCTOS (para consola o debug)
    // ============================================================
    public static void listarProductos() {
        System.out.println("=== LISTADO DE PRODUCTOS ===");
        for (int i = 0; i < contadorProductos; i++) {
            Producto p = productos[i];
            if (p != null) {
                System.out.println(p.getCodigo() + " | " + p.getNombre() + " | "
                        + p.getCategoria() + " | " + p.getAtributo()
                        + " | Precio: Q." + p.getPrecio() + " | Stock: " + p.getStock());
            }
        }
    }

    // ============================================================
    // 5. GETTERS
    // ============================================================
    public static Producto[] getProductos() {
        return productos;
    }

    public static int getCantidadProductos() {
        return contadorProductos;
    }
}
