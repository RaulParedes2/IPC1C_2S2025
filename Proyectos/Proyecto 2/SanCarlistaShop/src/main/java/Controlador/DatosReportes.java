/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Clase que centraliza la lectura de todos los archivos CSV
 * utilizados por los reportes PDF y el historial de compras.
 * No genera datos aleatorios, todos provienen de los CSV reales.
 */
public class DatosReportes {

    private static final int MAX = 500;
    private static final String RUTA_BASE = "src/main/resources/reporte";
    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    // ==========================================================
    // 🔹 STOCK: stock.csv (codigo,cantidad)
    // ==========================================================
    public static String[][] leerStock() {
        String[][] datos = new String[MAX][2];
        int contador = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(RUTA_BASE + "stock.csv"))) {
            String linea;
            while ((linea = br.readLine()) != null && contador < MAX) {
                if (linea.trim().isEmpty() || linea.startsWith("Código")) continue;
                String[] partes = linea.split(",");
                if (partes.length >= 2) {
                    datos[contador][0] = partes[0].trim(); // código
                    datos[contador][1] = partes[1].trim(); // cantidad
                    contador++;
                }
            }
        } catch (IOException e) {
            System.out.println("[ERROR] No se pudo leer stock.csv: " + e.getMessage());
        }

        return datos;
    }

    // ==========================================================
    // 🔹 PRODUCTOS: productos.csv (codigo,nombre,categoria,atributo,fechaCaducidad)
    // ==========================================================
    public static String[][] leerProductos() {
        String[][] datos = new String[MAX][5];
        int contador = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(RUTA_BASE + "productos.csv"))) {
            String linea;
            while ((linea = br.readLine()) != null && contador < MAX) {
                if (linea.trim().isEmpty() || linea.startsWith("Código")) continue;
                String[] partes = linea.split(",");
                for (int i = 0; i < partes.length && i < 5; i++) {
                    datos[contador][i] = partes[i].trim();
                }
                contador++;
            }
        } catch (IOException e) {
            System.out.println("[ERROR] No se pudo leer productos.csv: " + e.getMessage());
        }

        return datos;
    }

    // ==========================================================
    // 🔹 CLIENTES: clientes.csv (codigo,nombre,genero,password)
    // ==========================================================
    public static String[][] leerClientes() {
        String[][] datos = new String[MAX][4];
        int contador = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(RUTA_BASE + "clientes.csv"))) {
            String linea;
            while ((linea = br.readLine()) != null && contador < MAX) {
                if (linea.trim().isEmpty() || linea.startsWith("Código")) continue;
                String[] partes = linea.split(",");
                for (int i = 0; i < partes.length && i < 4; i++) {
                    datos[contador][i] = partes[i].trim();
                }
                contador++;
            }
        } catch (IOException e) {
            System.out.println("[ERROR] No se pudo leer clientes.csv: " + e.getMessage());
        }

        return datos;
    }

    // ==========================================================
    // 🔹 HISTORIAL COMPRAS:
    // historial_compras.csv
    // (fecha,codigoCliente,codigoProducto,nombreProducto,cantidad,vendedor,precioUnitario,total)
    // ==========================================================
    public static String[][] leerHistorialCompras() {
        String[][] datos = new String[MAX][8];
        int contador = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(RUTA_BASE + "historial_compras.csv"))) {
            String linea;
            while ((linea = br.readLine()) != null && contador < MAX) {
                if (linea.trim().isEmpty() || linea.startsWith("Fecha")) continue;
                String[] partes = linea.split(",");
                for (int i = 0; i < partes.length && i < 8; i++) {
                    datos[contador][i] = partes[i].trim();
                }
                contador++;
            }
        } catch (IOException e) {
            System.out.println("[ERROR] No se pudo leer historial_compras.csv: " + e.getMessage());
        }

        return datos;
    }

    // ==========================================================
    // 🔹 REPORTE FINANCIERO: Calcula total de ventas reales
    // ==========================================================
    public static double calcularTotalVentas() {
        double total = 0.0;
        String[][] historial = leerHistorialCompras();

        for (String[] fila : historial) {
            if (fila[7] == null) break;
            try {
                total += Double.parseDouble(fila[7]);
            } catch (NumberFormatException e) {
                // ignorar líneas mal formateadas
            }
        }
        return total;
    }

    // ==========================================================
    // 🔹 FECHA DE CADUCIDAD: Se obtiene del CSV del producto
    // ==========================================================
    public static LocalDate obtenerFechaCaducidad(String codigoProducto) {
        String[][] productos = leerProductos();

        for (String[] fila : productos) {
            if (fila[0] == null) break;
            if (fila[0].equalsIgnoreCase(codigoProducto) && fila.length >= 5) {
                try {
                    return LocalDate.parse(fila[4], FORMATO);
                } catch (Exception e) {
                    System.out.println("[ADVERTENCIA] Fecha inválida para producto " + codigoProducto);
                }
            }
        }

        return null; // Si no hay fecha válida
    }

    // ==========================================================
    // 🔹 Obtener fecha más reciente de compra (última venta)
    // ==========================================================
    public static LocalDate obtenerUltimaCompra() {
        String[][] historial = leerHistorialCompras();
        LocalDate ultima = null;

        for (String[] fila : historial) {
            if (fila[0] == null) break;
            try {
                LocalDate fecha = LocalDate.parse(fila[0], FORMATO);
                if (ultima == null || fecha.isAfter(ultima)) {
                    ultima = fecha;
                }
            } catch (Exception ignored) {}
        }

        return ultima;
    }
}
