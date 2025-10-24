/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Producto;
import Modelo.Vendedores;

public class Vendedor {

    // ===============================================================
    // ARREGLOS Y CONTADORES
    // ===============================================================
    private static final int MAX_VENDEDORES = 100;
    private static final int MAX_PRODUCTOS = 200;

    private static Vendedores[] vendedores = new Vendedores[MAX_VENDEDORES];
    private static Producto[] productos = new Producto[MAX_PRODUCTOS];

    private static int contadorVendedores = 0;
    private static int contadorProductos = 0;

    // ===============================================================
    // GESTIÓN DE VENDEDORES
    // ===============================================================

    public static void crearVendedor(Vendedores nuevo) {
        if (buscarVendedor(nuevo.getCodigo()) == null) {
            vendedores[contadorVendedores++] = nuevo;
            System.out.println("[OK] Vendedor " + nuevo.getNombre() + " creado correctamente.");
        } else {
            System.out.println("Código de vendedor duplicado.");
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
    // GESTIÓN DE PRODUCTOS
    // ===============================================================

    public static void crearProducto(Producto nuevo) {
        if (buscarProducto(nuevo.getCodigo()) == null) {
            productos[contadorProductos++] = nuevo;
            System.out.println("[OK] Producto " + nuevo.getNombre() + " agregado correctamente.");
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
                productos[contadorProductos - 1] = null;
                contadorProductos--;
                System.out.println("Producto eliminado correctamente.");
                return;
            }
        }
        System.out.println("No se encontró el producto.");
    }

    public static Producto[] getProductos() {
        return productos;
    }

    public static int getCantidadProductos() {
        return contadorProductos;
    }

    // ===============================================================
    // MÉTODOS EXTRA (para guardar/cargar CSV en el futuro)
    // ===============================================================

    public static void limpiarProductos() {
        for (int i = 0; i < contadorProductos; i++) {
            productos[i] = null;
        }
        contadorProductos = 0;
    }

    public static void limpiarVendedores() {
        for (int i = 0; i < contadorVendedores; i++) {
            vendedores[i] = null;
        }
        contadorVendedores = 0;
    }
    //====================================
    //==========Actualizar Vendedor=======
    //====================================
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

}
