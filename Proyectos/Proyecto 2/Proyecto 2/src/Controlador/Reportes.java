/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Producto;
/**
 *
 * @author Daniel Predes
 */
public class Reportes {
    
    public static void productosMasVendidos(Producto[] productos) {
        System.out.println("=== Reporte de Productos Más Vendidos ===");
        for (Producto p : productos) {
            if (p != null)
                System.out.println(p);
        }
    }
}
