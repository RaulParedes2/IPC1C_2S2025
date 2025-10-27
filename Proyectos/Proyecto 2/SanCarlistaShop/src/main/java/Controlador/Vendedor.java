/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Producto;
import Modelo.Vendedores;
import java.io.*;

/**
 * Controlador principal para la gestión de vendedores y productos.
 * Incluye manejo de stock separado (sin alterar la clase Producto).
 */
public class Vendedor {

    // ===============================================================
    // ARREGLOS Y CONTADORES
    // ===============================================================
    private static final int MAX_VENDEDORES = 100;
    private static final int MAX_PRODUCTOS = 200;

    private static Vendedores[] vendedores = new Vendedores[MAX_VENDEDORES];
    private static Producto[] productos = new Producto[MAX_PRODUCTOS];
    private static int[] stocks = new int[MAX_PRODUCTOS]; // Stock separado

    private static int contadorVendedores = 0;
    private static int contadorProductos = 0;

    // ===============================================================
    // GESTIÓN DE VENDEDORES
    // ===============================================================
    public static void crearVendedor(Vendedores nuevo) {
        if (nuevo == null) return;
        if (buscarVendedor(nuevo.getCodigo()) == null) {
            if (contadorVendedores < MAX_VENDEDORES) {
                vendedores[contadorVendedores++] = nuevo;
                System.out.println("[OK] Vendedor " + nuevo.getNombre() + " creado correctamente.");
            } else {
                System.out.println("[ERROR] Límite de vendedores alcanzado.");
            }
        } else {
            System.out.println("[ERROR] Código de vendedor duplicado.");
        }
    }

    public static Vendedores buscarVendedor(String codigo) {
        for (int i = 0; i < contadorVendedores; i++) {
            if (vendedores[i] != null && vendedores[i].getCodigo().equalsIgnoreCase(codigo)) {
                return vendedores[i];
            }
        }
        return null;
    }

    public static void eliminarVendedor(String codigo) {
        for (int i = 0; i < contadorVendedores; i++) {
            if (vendedores[i] != null && vendedores[i].getCodigo().equalsIgnoreCase(codigo)) {
                vendedores[i] = vendedores[contadorVendedores - 1];
                vendedores[contadorVendedores - 1] = null;
                contadorVendedores--;
                System.out.println("[OK] Vendedor eliminado correctamente.");
                return;
            }
        }
        System.out.println("[ERROR] No se encontró el vendedor.");
    }

    public static Vendedores[] getVendedores() {
        return vendedores;
    }

    public static int getCantidadVendedores() {
        return contadorVendedores;
    }

    // ===============================================================
    // ACTUALIZAR VENDEDOR
    // ===============================================================
    public static void actualizarVendedor(String codigo, String nuevoNombre, String nuevaPassword) {
        for (int i = 0; i < contadorVendedores; i++) {
            if (vendedores[i] != null && vendedores[i].getCodigo().equalsIgnoreCase(codigo)) {

                if (nuevoNombre != null && !nuevoNombre.isEmpty()) {
                    vendedores[i].setNombre(nuevoNombre);
                }

                if (nuevaPassword != null && !nuevaPassword.isEmpty()) {
                    vendedores[i].setPassword(nuevaPassword);
                }

                System.out.println("[OK] Vendedor actualizado: " + vendedores[i].getNombre());
                return;
            }
        }
        System.out.println("[ERROR] No se encontró un vendedor con el código: " + codigo);
    }

    // ===============================================================
    // GESTIÓN DE PRODUCTOS
    // ===============================================================
    public static void crearProducto(Producto nuevo) {
        if (nuevo == null) return;
        if (buscarProducto(nuevo.getCodigo()) == null) {
            if (contadorProductos < MAX_PRODUCTOS) {
                productos[contadorProductos] = nuevo;
                stocks[contadorProductos] = 0; // Inicializa stock en 0
                contadorProductos++;
                System.out.println("[OK] Producto " + nuevo.getNombre() + " agregado correctamente.");
            } else {
                System.out.println("[ERROR] Capacidad de productos alcanzada.");
            }
        } else {
            System.out.println("[ERROR] Código de producto duplicado.");
        }
    }

    public static Producto buscarProducto(String codigo) {
        for (int i = 0; i < contadorProductos; i++) {
            if (productos[i] != null && productos[i].getCodigo().equalsIgnoreCase(codigo)) {
                return productos[i];
            }
        }
        return null;
    }

    public static void eliminarProducto(String codigo) {
        for (int i = 0; i < contadorProductos; i++) {
            if (productos[i] != null && productos[i].getCodigo().equalsIgnoreCase(codigo)) {
                productos[i] = productos[contadorProductos - 1];
                stocks[i] = stocks[contadorProductos - 1];
                productos[contadorProductos - 1] = null;
                stocks[contadorProductos - 1] = 0;
                contadorProductos--;
                System.out.println("[OK] Producto eliminado correctamente.");
                return;
            }
        }
        System.out.println("[ERROR] No se encontró el producto.");
    }

    public static Producto[] getProductos() {
        return productos;
    }

    public static int getCantidadProductos() {
        return contadorProductos;
    }

    // ===============================================================
    // GESTIÓN DE STOCK
    // ===============================================================

    public static boolean agregarStock(String codigo, int cantidad) {
        if (cantidad <= 0) return false;
        for (int i = 0; i < contadorProductos; i++) {
            if (productos[i] != null && productos[i].getCodigo().equalsIgnoreCase(codigo)) {
                stocks[i] += cantidad;
                return true;
            }
        }
        return false;
    }

    public static boolean setStock(String codigo, int cantidad) {
        if (cantidad < 0) return false;
        for (int i = 0; i < contadorProductos; i++) {
            if (productos[i] != null && productos[i].getCodigo().equalsIgnoreCase(codigo)) {
                stocks[i] = cantidad;
                return true;
            }
        }
        return false;
    }

    public static int getStock(String codigo) {
        for (int i = 0; i < contadorProductos; i++) {
            if (productos[i] != null && productos[i].getCodigo().equalsIgnoreCase(codigo)) {
                return stocks[i];
            }
        }
        return -1;
    }

    public static int getStockByIndex(int index) {
        if (index >= 0 && index < contadorProductos) {
            return stocks[index];
        }
        return -1;
    }

    // ===============================================================
    // GUARDAR Y CARGAR CSV DE INVENTARIO
    // ===============================================================

    public static void guardarInventarioCSV(String ruta) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ruta))) {
            pw.println("Código,Nombre,Categoría,Atributo,Stock");
            for (int i = 0; i < contadorProductos; i++) {
                Producto p = productos[i];
                pw.println(p.getCodigo() + "," + p.getNombre() + "," + p.getCategoria() + "," +
                           p.getAtributo() + "," + stocks[i]);
            }
            System.out.println("[OK] Inventario guardado en " + ruta);
        } catch (IOException e) {
            System.out.println("[ERROR] Error al guardar inventario: " + e.getMessage());
        }
    }

    public static void cargarStockDesdeCSV(String ruta) {
        File archivo = new File(ruta);
        if (!archivo.exists()) {
            System.out.println("[INFO] No se encontró archivo de stock (" + ruta + ")");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            int cargados = 0, errores = 0;
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty() || linea.startsWith("Código")) continue;
                String[] partes = linea.split(",");
                if (partes.length < 2) {
                    errores++;
                    continue;
                }

                String codigo = partes[0].trim();
                int cantidad;
                try {
                    cantidad = Integer.parseInt(partes[1].trim());
                } catch (NumberFormatException ex) {
                    errores++;
                    continue;
                }

                if (setStock(codigo, cantidad)) {
                    cargados++;
                } else {
                    errores++;
                }
            }
            System.out.println("[OK] Stock cargado (" + cargados + " correctos, " + errores + " errores).");
        } catch (IOException e) {
            System.out.println("[ERROR] Error al leer stock: " + e.getMessage());
        }
    }

    // ===============================================================
    // LIMPIEZA GENERAL
    // ===============================================================
    public static void limpiarProductos() {
        for (int i = 0; i < contadorProductos; i++) {
            productos[i] = null;
            stocks[i] = 0;
        }
        contadorProductos = 0;
    }

    public static void limpiarVendedores() {
        for (int i = 0; i < contadorVendedores; i++) {
            vendedores[i] = null;
        }
        contadorVendedores = 0;
    }
}