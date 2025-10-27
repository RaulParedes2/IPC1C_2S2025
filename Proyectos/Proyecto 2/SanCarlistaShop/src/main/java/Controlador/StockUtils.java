/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import java.io.*;

public class StockUtils {

    private static final String RUTA_STOCK = "src/main/resources/data/stock.csv";

    // 🔹 Obtener el stock actual de un producto (formato: Codigo,Stock,Precio)
    public static int obtenerStock(String codigo) {
        try (BufferedReader br = new BufferedReader(new FileReader(RUTA_STOCK))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.startsWith("Codigo") || linea.startsWith("Código")) continue;

                String[] partes = linea.split(",");
                if (partes.length >= 2) {
                    String cod = partes[0].trim();
                    if (cod.equalsIgnoreCase(codigo)) {
                        return Integer.parseInt(partes[1].trim());
                    }
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error al leer stock: " + e.getMessage());
        }
        return -1; // No encontrado o error
    }

    // 🔹 Verificar si hay stock suficiente
    public static boolean hayStock(String codigo, int cantidad) {
        int stock = obtenerStock(codigo);
        return stock >= cantidad && stock > 0;
    }
}