/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Vendedores;
import Modelo.Producto;
/**
 *
 * @author Daniel Predes
 */
public class Admon {
    private static Vendedores[] vendedores=new Vendedores[100];
    private static int contadorVendedores=0;
    private static Producto[] productos= new Producto[100];
    private static int contadorProductos=0;
    
    //Gestion de vendedores
    public static void crearVendedor(String codigo, String nombre, String genero, String password){
        if(buscarVendedor(codigo)==null){
             vendedores[contadorVendedores++] = new Vendedores(codigo, nombre, genero, password);
            System.out.println("Vendedor " + nombre + " creado correctamente.");
        }
        else{
            System.out.println("codigo de vendedor existente");
        }
    }
    
   public static Vendedores buscarVendedor(String codigo) {
        for (int i = 0; i < contadorVendedores; i++) {
            if (vendedores[i].getCodigo().equalsIgnoreCase(codigo)) {
                return vendedores[i];
            }
        }
        return null;
    }
    
     public static void eliminarVendedor(String codigo) {
        for (int i = 0; i < contadorVendedores; i++) {
            if (vendedores[i].getCodigo().equalsIgnoreCase(codigo)) {
                vendedores[i] = vendedores[contadorVendedores - 1];
                vendedores[contadorVendedores - 1] = null;
                contadorVendedores--;
                System.out.println("Vendedor eliminado correctamente.");
                return;
            }
        }
        System.out.println("Vendedor no encontrado.");
    }
    
  // === GESTIÓN DE PRODUCTOS ===
    public static void crearProducto(Producto p) {
        if (buscarProducto(p.getCodigo()) == null) {
            productos[contadorProductos++] = p;
            System.out.println("[OK] Producto " + p.getNombre() + " agregado correctamente.");
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

    public static Producto[] getProductos() { return productos; }

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
        System.out.println("Producto no encontrado.");
    }
}

