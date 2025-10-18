/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Vendedores;
/**
 *
 * @author Daniel Predes
 */
public class Vendedor {
    
     private static Vendedores[] vendedores = new Vendedores[100];
     private static int contadorVendedores = 0;
    
   public static void crearVendedor(Vendedores v) {
        if (buscarVendedor(v.getCodigo()) == null) {
            vendedores[contadorVendedores++] = v;
            System.out.println("Vendedor " + v.getNombre() + " creado correctamente.");
        } else {
            System.out.println("Código de vendedor duplicado.");
        }
    }
   //-------------------------------------------------------------------------------
   //Buscar vendedor
   //--------------------------------------------------------------------------------
   
    public static Vendedores buscarVendedor(String codigo) {
        for (int i = 0; i < contadorVendedores; i++) {
            if (vendedores[i] != null && vendedores[i].getCodigo().equalsIgnoreCase(codigo)) {
                return vendedores[i];
            }
        }
        return null;
    }
    //--------------------------------------------------------------------------------
    //Modificar vendedor
    //------------------------------------------------------------------------------
    
    public static void actualizarVendedor(String codigo, String nuevoNombre, String nuevaPassword) {
        Vendedores v = buscarVendedor(codigo);
        if (v != null) {
            v.setNombre(nuevoNombre);
            v.setPassword(nuevaPassword);
            System.out.println("Vendedor actualizado correctamente.");
        } else {
            System.out.println("No se encontró el vendedor con código " + codigo);
        }
    }
    //----------------------------------------------------------------------------
    //Eliminar vendedor 
    //----------------------------------------------------------------------------
    
     public static void eliminarVendedor(String codigo) {
        for (int i = 0; i < contadorVendedores; i++) {
            if (vendedores[i] != null && vendedores[i].getCodigo().equalsIgnoreCase(codigo)) {
                for (int j = i; j < contadorVendedores - 1; j++) {      //Reordenar la lista
                    vendedores[j] = vendedores[j + 1];
                }
                vendedores[--contadorVendedores] = null;
                System.out.println("Vendedor eliminado");
                return;
            }
        }
        System.out.println("Vendedor no encontrado.");
    }

     //---------------------------------------------------------------------
     
     public static void listarVendedores() {
        System.out.println("=== LISTADO DE VENDEDORES ===");
        for (int i = 0; i < contadorVendedores; i++) {
            Vendedores v = vendedores[i];
            if (v != null) {
                System.out.println(v.getCodigo() + " | " + v.getNombre() + 
                                   " | Ventas confirmadas: " + v.getVentasCofirmadas());
            }
        }
    }
     
     //------------------------------------------------------------------------
     
      public static void confirmarVenta(String codigo) {
        Vendedores v = buscarVendedor(codigo);
        if (v != null) {
            v.confirmarVenta();
            System.out.println("Venta confirmada para vendedor " + v.getNombre());
        } else {
            System.out.println("No se encontró el vendedor.");
        }
    }
      
      //------------------------------------------------------------------------
      public static Vendedores[] getVendedores() { return vendedores; }
      public static int getCantidadVendedores() { return contadorVendedores; }
}
