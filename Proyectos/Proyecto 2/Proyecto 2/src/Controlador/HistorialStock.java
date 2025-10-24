/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

/**
 *
 * @author Daniel Predes
 */
public class HistorialStock {
     private static String[] movimientos = new String[500]; // capacidad fija
    private static int contador = 0;
    
    
    public static void agregarMovimiento(String registro) {
        if (contador < movimientos.length) {
            movimientos[contador++] = registro;
        } else {
            System.out.println("Historial lleno. No se puede registrar más movimientos.");
        }
    }
    
    public static String[] getMovimientos() {
        return movimientos;
    }

    public static int getCantidadMovimientos() {
        return contador;
    }
}

