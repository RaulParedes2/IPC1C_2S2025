/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.*;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.HashMap;

public class ReportePDF {

    private static final String RUTA = "src/main/resources/reportes/";

    // ===============================================================
    // MÉTODO BASE PARA CREAR DOCUMENTO
    // ===============================================================
    private static Document crearDocumento(String nombre) throws IOException, DocumentException {
        File carpeta = new File(RUTA);
        if (!carpeta.exists()) carpeta.mkdirs();

        String fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("ddMMyyyy_HHmmss"));
        String archivo = RUTA + nombre + "_" + fecha + ".pdf";

        Document doc = new Document();
        PdfWriter.getInstance(doc, new FileOutputStream(archivo));
        doc.open();
        doc.add(new Paragraph("Reporte generado el " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))));
        doc.add(new Paragraph(" "));
        return doc;
    }
    
    //================================================================
    
    public static void generarReporteVentasPorVendedor() {
    try {
        String[][] historial = DatosReportes.leerHistorialCompras();
        String[][] vendedores = new String[100][3]; // Vendedor, cantidad total, monto total
        int total = 0;

        for (int i = 0; i < historial.length && historial[i][0] != null; i++) {
            String vendedor = historial[i][5];
            int cantidad = Integer.parseInt(historial[i][4]);
            double totalVenta = Double.parseDouble(historial[i][7]);

            int pos = -1;
            for (int j = 0; j < total; j++) {
                if (vendedores[j][0].equalsIgnoreCase(vendedor)) {
                    pos = j;
                    break;
                }
            }

            if (pos == -1) {
                vendedores[total][0] = vendedor;
                vendedores[total][1] = String.valueOf(cantidad);
                vendedores[total][2] = String.valueOf(totalVenta);
                total++;
            } else {
                int suma = Integer.parseInt(vendedores[pos][1]) + cantidad;
                double acum = Double.parseDouble(vendedores[pos][2]) + totalVenta;
                vendedores[pos][1] = String.valueOf(suma);
                vendedores[pos][2] = String.valueOf(acum);
            }
        }

        String nombreArchivo = "src/data/Reporte_VentasPorVendedor.pdf";
        Document doc = new Document(PageSize.A4);
        PdfWriter.getInstance(doc, new FileOutputStream(nombreArchivo));
        doc.open();

        Font titulo = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
        Font texto = new Font(Font.FontFamily.HELVETICA, 12);
        doc.add(new Paragraph("REPORTE DE VENTAS POR VENDEDOR", titulo));
        doc.add(new Paragraph("Fecha: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))));
        doc.add(Chunk.NEWLINE);

        PdfPTable tabla = new PdfPTable(3);
        tabla.setWidthPercentage(100);
        tabla.addCell("Vendedor");
        tabla.addCell("Cantidad Vendida");
        tabla.addCell("Total (Q)");

        for (int i = 0; i < total; i++) {
            tabla.addCell(vendedores[i][0]);
            tabla.addCell(vendedores[i][1]);
            tabla.addCell("Q. " + vendedores[i][2]);
        }

        doc.add(tabla);
        doc.close();

        System.out.println(" Reporte de Ventas por Vendedor generado correctamente en " + nombreArchivo);

    } catch (Exception e) {
        e.printStackTrace();
    }
}
    // ===============================================================
    // PRODUCTOS MÁS VENDIDOS
    // ===============================================================
    public static void generarReporteProductosMasVendidos() {
        try {
            Document doc = crearDocumento("Productos_Mas_Vendidos");
            doc.add(new Paragraph("=== PRODUCTOS MÁS VENDIDOS ==="));
            doc.add(new Paragraph(" "));

            Map<String, Integer> ventas = obtenerVentasPorProducto();

            PdfPTable tabla = new PdfPTable(2);
            tabla.addCell("Producto");
            tabla.addCell("Cantidad Vendida");

            for (Map.Entry<String, Integer> e : ventas.entrySet()) {
                tabla.addCell(e.getKey());
                tabla.addCell(String.valueOf(e.getValue()));
            }

            doc.add(tabla);
            doc.close();
        } catch (Exception e) {
            System.err.println("Error al generar reporte: " + e.getMessage());
        }
    }

    // ===============================================================
    // PRODUCTOS MENOS VENDIDOS
    // ===============================================================
    public static void generarReporteProductosMenosVendidos() {
        try {
            Document doc = crearDocumento("Productos_Menos_Vendidos");
            doc.add(new Paragraph("=== PRODUCTOS MENOS VENDIDOS ==="));
            doc.add(new Paragraph(" "));

            Map<String, Integer> ventas = obtenerVentasPorProducto();

            PdfPTable tabla = new PdfPTable(2);
            tabla.addCell("Producto");
            tabla.addCell("Cantidad Vendida");

            for (Map.Entry<String, Integer> e : ventas.entrySet()) {
                if (e.getValue() < 5) { // criterio: menos de 5 unidades
                    tabla.addCell(e.getKey());
                    tabla.addCell(String.valueOf(e.getValue()));
                }
            }

            doc.add(tabla);
            doc.close();
        } catch (Exception e) {
            System.err.println("Error al generar reporte: " + e.getMessage());
        }
    }

    // ===============================================================
    // INVENTARIO (STOCK CRÍTICO)
    // ===============================================================
    public static void generarReporteInventario(Producto[] productos) {
        try {
            Document doc = crearDocumento("Inventario_Critico");
            doc.add(new Paragraph("=== INVENTARIO - STOCK CRÍTICO ==="));
            doc.add(new Paragraph(" "));

            PdfPTable tabla = new PdfPTable(3);
            tabla.addCell("Código");
            tabla.addCell("Producto");
            tabla.addCell("Stock");

            File stockFile = new File("src/data/stock.csv");
            if (!stockFile.exists()) {
                doc.add(new Paragraph("No existe el archivo de stock."));
                doc.close();
                return;
            }

            try (BufferedReader br = new BufferedReader(new FileReader(stockFile))) {
                String linea;
                br.readLine(); // saltar encabezado
                while ((linea = br.readLine()) != null) {
                    String[] partes = linea.split(",");
                    if (partes.length < 2) continue;
                    int stock = Integer.parseInt(partes[1]);
                    if (stock <= 5) { // stock bajo
                        tabla.addCell(partes[0]);
                        tabla.addCell(obtenerNombreProducto(partes[0]));
                        tabla.addCell(String.valueOf(stock));
                    }
                }
            }

            doc.add(tabla);
            doc.close();
        } catch (Exception e) {
            System.err.println("Error en inventario crítico: " + e.getMessage());
        }
    }

    // ===============================================================
    // PRODUCTOS POR CADUCAR (SIMULACIÓN)
    // ===============================================================
    public static void generarReporteProductosPorCaducar(Producto[] productos) {
        try {
            Document doc = crearDocumento("Productos_Por_Caducar");
            doc.add(new Paragraph("=== PRODUCTOS PRÓXIMOS A CADUCAR ==="));
            doc.add(new Paragraph(" "));

            PdfPTable tabla = new PdfPTable(2);
            tabla.addCell("Código");
            tabla.addCell("Producto");

            File productosFile = new File("src/data/productos.csv");
            if (!productosFile.exists()) {
                doc.add(new Paragraph("No existe archivo de productos."));
                doc.close();
                return;
            }

            try (BufferedReader br = new BufferedReader(new FileReader(productosFile))) {
                String linea;
                br.readLine(); // encabezado
                while ((linea = br.readLine()) != null) {
                    String[] partes = linea.split(",");
                    if (partes.length < 4) continue;
                    String atributo = partes[3].toLowerCase();
                    if (atributo.contains("caduca") || atributo.contains("vence")) {
                        tabla.addCell(partes[0]);
                        tabla.addCell(partes[1]);
                    }
                }
            }

            doc.add(tabla);
            doc.close();
        } catch (Exception e) {
            System.err.println("Error reporte caducidad: " + e.getMessage());
        }
    }

    // ===============================================================
    // CLIENTES ACTIVOS
    // ===============================================================
    public static void generarReporteClientesActivos(Cliente[] clientes) {
        try {
            Document doc = crearDocumento("Clientes_Activos");
            doc.add(new Paragraph("=== CLIENTES ACTIVOS ==="));
            doc.add(new Paragraph(" "));

            PdfPTable tabla = new PdfPTable(3);
            tabla.addCell("Código");
            tabla.addCell("Nombre");
            tabla.addCell("Compras Realizadas");

            Map<String, Integer> compras = obtenerComprasPorCliente();

            for (Map.Entry<String, Integer> e : compras.entrySet()) {
                tabla.addCell(e.getKey());
                tabla.addCell(obtenerNombreCliente(e.getKey()));
                tabla.addCell(String.valueOf(e.getValue()));
            }

            doc.add(tabla);
            doc.close();
        } catch (Exception e) {
            System.err.println("Error reporte clientes activos: " + e.getMessage());
        }
    }

    // ===============================================================
    // REPORTE FINANCIERO (TOTAL DE VENTAS)
    // ===============================================================
    public static void generarReporteFinanciero() {
        try {
            Document doc = crearDocumento("Reporte_Financiero");
            doc.add(new Paragraph("=== REPORTE FINANCIERO GENERAL ==="));
            doc.add(new Paragraph(" "));

            double totalVentas = 0;
            File archivo = new File("src/data/historial_compras.csv");

            if (!archivo.exists()) {
                doc.add(new Paragraph("No hay historial de compras."));
                doc.close();
                return;
            }

            try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
                String linea;
                br.readLine();
                while ((linea = br.readLine()) != null) {
                    String[] partes = linea.split(",");
                    if (partes.length >= 8) {
                        try {
                            totalVentas += Double.parseDouble(partes[7]);
                        } catch (NumberFormatException ex) {
                            // ignorar
                        }
                    }
                }
            }

            doc.add(new Paragraph("Total general de ventas: Q" + String.format("%.2f", totalVentas)));
            doc.close();
        } catch (Exception e) {
            System.err.println("Error reporte financiero: " + e.getMessage());
        }
    }

    // ===============================================================
    // MÉTODOS DE APOYO
    // ===============================================================
    private static Map<String, Integer> obtenerVentasPorProducto() throws IOException {
        Map<String, Integer> ventas = new HashMap<>();
        File archivo = new File("src/data/historial_compras.csv");
        if (!archivo.exists()) return ventas;

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            br.readLine();
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length >= 5) {
                    String producto = partes[3];
                    int cantidad = Integer.parseInt(partes[4]);
                    ventas.put(producto, ventas.getOrDefault(producto, 0) + cantidad);
                }
            }
        }
        return ventas;
    }

    private static Map<String, Integer> obtenerComprasPorCliente() throws IOException {
        Map<String, Integer> compras = new HashMap<>();
        File archivo = new File("src/data/historial_compras.csv");
        if (!archivo.exists()) return compras;

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            br.readLine();
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length >= 2) {
                    String cliente = partes[1];
                    int cantidad = Integer.parseInt(partes[4]);
                    compras.put(cliente, compras.getOrDefault(cliente, 0) + cantidad);
                }
            }
        }
        return compras;
    }

    private static String obtenerNombreProducto(String codigo) throws IOException {
        File archivo = new File("src/data/productos.csv");
        if (!archivo.exists()) return "Desconocido";
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            br.readLine();
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes[0].equals(codigo)) return partes[1];
            }
        }
        return "Desconocido";
    }

    private static String obtenerNombreCliente(String codigo) throws IOException {
        File archivo = new File("src/data/clientes.csv");
        if (!archivo.exists()) return "Desconocido";
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            br.readLine();
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes[0].equals(codigo)) return partes[1];
            }
        }
        return "Desconocido";
    }
}
