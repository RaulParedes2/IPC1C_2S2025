/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package Controlador;

public class Bitacora {

    private static final String[] registros = new String[200]; // capacidad fija
    private static int cantidad = 0;

    public static void registrarAccion(String mensaje) {
        if (cantidad < registros.length) {
            registros[cantidad++] = mensaje + " [" + java.time.LocalDateTime.now() + "]";
        } else {
            System.out.println("Bitácora llena, no se pueden agregar más registros.");
        }
    }

    public static String[] getRegistros() {
        return registros;
    }

    public static int getCantidadRegistros() {
        return cantidad;
    }

    public static void limpiarBitacora() {
        for (int i = 0; i < cantidad; i++) {
            registros[i] = null;
        }
        cantidad = 0;
    }
}

