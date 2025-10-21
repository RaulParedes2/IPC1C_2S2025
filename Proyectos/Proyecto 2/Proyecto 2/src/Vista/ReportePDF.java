/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

import java.io.FileWriter;
import java.io.IOException;

public class ReportePDF {

    public static void generar(String contenido) throws IOException {
        FileWriter writer = new FileWriter("Reporte.txt");
        writer.write("Sancarlista Shop - Reporte General\n\n");
        writer.write(contenido);
        writer.close();
    }
}

