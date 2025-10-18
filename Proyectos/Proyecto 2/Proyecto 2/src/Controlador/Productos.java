/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;
import Modelo.Producto;
import Modelo.ProductoAl;
import Modelo.ProductoGe;
import Modelo.ProductoTec;
/**
 *
 * @author Daniel Predes
 */
public class Productos {
    
    private static Producto[] productos = new Producto[100];
    private static int contadorProductos = 0;
    
    //---------------------------------------------------------------------------
    //==Crear Producto==
    //-----------------------------------------------------------------------
    
    public static void crearProducto(Producto p) {
        if (buscarProducto(p.getCodigo()) == null) {
            productos[contadorProductos++] = p;
            System.out.println("Producto " + p.getNombre() + " agregado correctamente.");
        } else {
            System.out.println("Código de producto duplicado.");
        }
    }
 //---------------------------------------------------------------------------
    //==Buscar Producto==
//--------------------------------------------------------------------------------    
    public static Producto buscarProducto(String codigo) {
        for (int i = 0; i < contadorProductos; i++) {
            if (productos[i] != null && productos[i].getCodigo().equalsIgnoreCase(codigo)) {
                return productos[i];
            }
        }
        return null;
    }
    //---------------------------------------------------------------------------
    //==Elimanr Producto==
    //------------------------------------------------------------------------
    public static void eliminarProducto(String codigo) {
        for (int i = 0; i < contadorProductos; i++) {
            if (productos[i] != null && productos[i].getCodigo().equalsIgnoreCase(codigo)) {
                for (int j = i; j < contadorProductos - 1; j++) {       //ordenar la lista
                    productos[j] = productos[j + 1];
                }
                productos[--contadorProductos] = null;
                System.out.println("Producto eliminado y lista reordenada.");
                return;
            }
        }
        System.out.println("Producto no encontrado.");
    }
    //----------------------------------------------------------------------------
    //Modificacion de producto
    //----------------------------------------------------------------------------
    
    public static void actualizarProducto(String codigo, String nuevoNombre, String nuevoAtributo) {
        Producto p = buscarProducto(codigo);
        if (p == null) {
            System.out.println("No se encontró producto con código " + codigo);
            return;
        }

        p.setNombre(nuevoNombre);
        
        if (p instanceof ProductoTec) {
            ((ProductoTec) p).setMesesGarantia(Integer.parseInt(nuevoAtributo));
        } else if (p instanceof ProductoAl) {
            ((ProductoAl) p).setFechaCaducidad(java.time.LocalDate.parse(nuevoAtributo));
        } else if (p instanceof ProductoGe) {
            ((ProductoGe) p).setMaterial(nuevoAtributo);
        }

        System.out.println("Producto actualizado correctamente.");
    }
    //--------------------------------------------------------------------
    //Lista
    //--------------------------------------------------------------------
    public static void listarProductos() {
        System.out.println("=== LISTADO DE PRODUCTOS ===");
        for (int i = 0; i < contadorProductos; i++) {
            Producto p = productos[i];
            if (p != null) {
                System.out.println(p.getCodigo() + " | " + p.getNombre() + " | " +
                                   p.getCategoria() + " | Stock: " + p.getStock() +
                                   " | " + p.getDetalle());
            }
        }
    }
    
    //--------------------------------------------------------------------
    //Agregar en Stock
    //--------------------------------------------------------------------
    public static void agregarStock(String codigo, int cantidad) {
        Producto p = buscarProducto(codigo);
        if (p != null) {
            p.agregarStock(cantidad);
            System.out.println("Se agregaron " + cantidad + " unidades al producto " + p.getNombre());
        } else {
            System.out.println("Producto no encontrado.");
        }
    }
    
    //----------------------------------------------------------------------
    
     public static Producto[] getProductos() {
        return productos;
    }
     
     //-------------------------------------------------------------------
     
     public static int getCantidadProductos() {
        return contadorProductos;
    }
     
     
}
