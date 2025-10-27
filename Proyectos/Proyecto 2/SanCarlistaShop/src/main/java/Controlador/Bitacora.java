/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package Controlador;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Bitacora {

    private static final int MAX_REGISTROS = 300;
    private static final String[] registros = new String[MAX_REGISTROS];
    private static int cantidad = 0;

    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    // ===========================================================
    // 🔹 REGISTRAR EVENTO
    // ===========================================================
    public static void registrar(String tipoUsuario, String codigoUsuario, 
                                 String operacion, String estado, String descripcion) {
        if (cantidad >= MAX_REGISTROS) return;

        String fecha = LocalDateTime.now().format(FMT);
        String registro = String.format("[%s] | %s | %s | %s | %s | %s",
                fecha, tipoUsuario, codigoUsuario, operacion, estado, descripcion);

        registros[cantidad++] = registro;
        System.out.println("[BITÁCORA] " + registro);
    }

    // ===========================================================
    // 🔹 GETTERS Y LIMPIEZA
    // ===========================================================
    public static String[] getRegistros() {
        return registros;
    }

    public static int getCantidadRegistros() {
        return cantidad;
    }

    public static void limpiar() {
        for (int i = 0; i < cantidad; i++) registros[i] = null;
        cantidad = 0;
    }

    // ===========================================================
    // 🔹 FILTROS DE CONSULTA
    // ===========================================================
    public static String[] filtrar(String tipoUsuario, String operacion, String codigo, 
                                   String fechaDesde, String fechaHasta) {

        String[] resultado = new String[MAX_REGISTROS];
        int index = 0;

        for (int i = 0; i < cantidad; i++) {
            if (registros[i] == null) continue;
            String linea = registros[i];

            boolean coincide = true;

            if (tipoUsuario != null && !tipoUsuario.isEmpty() && !linea.contains("| " + tipoUsuario + " |"))
                coincide = false;
            if (operacion != null && !operacion.isEmpty() && !linea.contains("| " + operacion + " |"))
                coincide = false;
            if (codigo != null && !codigo.isEmpty() && !linea.contains("| " + codigo + " |"))
                coincide = false;

            if (fechaDesde != null && !fechaDesde.isEmpty()) {
                String fechaLinea = linea.substring(1, 11); // formato DD/MM/YYYY
                if (compararFechas(fechaLinea, fechaDesde) < 0)
                    coincide = false;
            }

            if (fechaHasta != null && !fechaHasta.isEmpty()) {
                String fechaLinea = linea.substring(1, 11);
                if (compararFechas(fechaLinea, fechaHasta) > 0)
                    coincide = false;
            }

            if (coincide)
                resultado[index++] = linea;
        }

        return resultado;
    }

    // ===========================================================
    // 🔹 COMPARAR FECHAS (DD/MM/YYYY)
    // ===========================================================
    private static int compararFechas(String f1, String f2) {
        try {
            String[] a = f1.split("/");
            String[] b = f2.split("/");
            int y1 = Integer.parseInt(a[2]), m1 = Integer.parseInt(a[1]), d1 = Integer.parseInt(a[0]);
            int y2 = Integer.parseInt(b[2]), m2 = Integer.parseInt(b[1]), d2 = Integer.parseInt(b[0]);

            if (y1 != y2) return y1 - y2;
            if (m1 != m2) return m1 - m2;
            return d1 - d2;
        } catch (Exception e) {
            return 0;
        }
    }

    // ===========================================================
    // 🔹 EXPORTAR CSV
    // ===========================================================
    public static void exportarCSV(String ruta) {
        try (FileWriter writer = new FileWriter(ruta)) {
            writer.write("FechaHora,TipoUsuario,CodigoUsuario,Operacion,Estado,Descripcion\n");
            for (int i = 0; i < cantidad; i++) {
                if (registros[i] != null) {
                    String csv = registros[i]
                            .replace("[", "")
                            .replace("]", "")
                            .replace(" | ", ",");
                    writer.write(csv + "\n");
                }
            }
            System.out.println("Bitácora exportada correctamente a " + ruta);
        } catch (IOException e) {
            System.out.println("Error al exportar bitácora: " + e.getMessage());
        }
    }

    // ===========================================================
    // 🔹 EXPORTAR PDF
    // ===========================================================
    public static void exportarPDF(String ruta) {
        try {
            com.itextpdf.text.Document doc = new com.itextpdf.text.Document();
            com.itextpdf.text.pdf.PdfWriter.getInstance(doc, new java.io.FileOutputStream(ruta));
            doc.open();

            com.itextpdf.text.Font titulo = new com.itextpdf.text.Font(
                    com.itextpdf.text.Font.FontFamily.HELVETICA, 16, com.itextpdf.text.Font.BOLD);
            com.itextpdf.text.Paragraph encabezado = new com.itextpdf.text.Paragraph(
                    "BITÁCORA DEL SISTEMA\n\n", titulo);
            encabezado.setAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
            doc.add(encabezado);

            com.itextpdf.text.Font texto = new com.itextpdf.text.Font(
                    com.itextpdf.text.Font.FontFamily.COURIER, 10);

            for (int i = 0; i < cantidad; i++) {
                if (registros[i] != null)
                    doc.add(new com.itextpdf.text.Paragraph(registros[i], texto));
            }

            doc.close();
            System.out.println("Bitácora exportada a PDF: " + ruta);
        } catch (Exception e) {
            System.out.println("Error al generar PDF: " + e.getMessage());
        }
    }
}
