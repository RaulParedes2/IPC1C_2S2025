/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.io.*;
import java.util.*;

public class Stock {
    private static final String RUTA = "src/main/resources/data/stock.csv";

    // 🔹 Devuelve el stock actual de un producto por su código
    public static int getStock(String codigo) {
        try (BufferedReader br = new BufferedReader(new FileReader(RUTA))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length >= 2 && datos[0].equalsIgnoreCase(codigo)) {
                    return Integer.parseInt(datos[1].trim());
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer stock.csv: " + e.getMessage());
        }
        return 0;
    }

    // 🔹 Devuelve un mapa con todos los códigos y sus stocks
    public static Map<String, Integer> getTodoStock() {
        Map<String, Integer> stock = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(RUTA))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length >= 2) {
                    stock.put(datos[0].trim(), Integer.parseInt(datos[1].trim()));
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer stock.csv: " + e.getMessage());
        }
        return stock;
    }

    // 🔹 Actualiza el stock en el archivo
    public static void actualizarStock(String codigo, int nuevoStock) {
        Map<String, Integer> stock = getTodoStock();
        stock.put(codigo, nuevoStock);

        try (PrintWriter pw = new PrintWriter(new FileWriter(RUTA))) {
            for (Map.Entry<String, Integer> entry : stock.entrySet()) {
                pw.println(entry.getKey() + "," + entry.getValue());
            }
        } catch (IOException e) {
            System.err.println("Error al escribir en stock.csv: " + e.getMessage());
        }
    }
}
