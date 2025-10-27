/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

import Modelo.*;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class VentanaHistorialCompras extends JFrame {

    private Cliente cliente;
    private JTextArea txtHistorial;
    private JButton btnFiltrar;

    public VentanaHistorialCompras(Cliente c) {
        this.cliente = c;

        setTitle("Historial de Compras - " + cliente.getNombre());
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        txtHistorial = new JTextArea();
        txtHistorial.setEditable(false);
        JScrollPane scroll = new JScrollPane(txtHistorial);

        btnFiltrar = new JButton("Filtrar por Fecha (dd/mm/yyyy)");
        btnFiltrar.addActionListener(e -> filtrarPorFecha());

        add(scroll, BorderLayout.CENTER);
        add(btnFiltrar, BorderLayout.SOUTH);

        mostrarHistorial(); // Muestra todo al iniciar
    }

    // ===========================================================
    // 🔹 Mostrar historial completo
    // ===========================================================
    private void mostrarHistorial() {
        txtHistorial.setText("=== HISTORIAL DE COMPRAS ===\n");

        File archivo = new File("src/main/resources/data/historial_compras.csv");
        if (!archivo.exists()) {
            txtHistorial.append("No hay historial de compras disponible.\n");
            return;
        }

        double totalGeneral = 0.0;

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            boolean encontrado = false;

            while ((linea = br.readLine()) != null) {
                if (linea.startsWith("Fecha")) continue; // Saltar encabezado

                String[] partes = linea.split(",");
                // Nuevo formato esperado: Fecha,Cliente,ProductoCod,ProductoNombre,Cantidad,Vendedor,PrecioUnitario,Total
                if (partes.length < 6) continue;

                String fecha = partes[0];
                String codigoCliente = partes[1];
                String codigoProducto = partes[2];
                String nombreProducto = partes[3];
                String cantidad = partes[4];
                String vendedor = partes[5];

                double precioUnitario = 0.0;
                double totalCompra = 0.0;

                if (partes.length >= 8) { // Si el archivo tiene columnas de precios
                    try {
                        precioUnitario = Double.parseDouble(partes[6]);
                        totalCompra = Double.parseDouble(partes[7]);
                    } catch (NumberFormatException ex) {
                        // Si no hay datos válidos, se dejan en 0
                    }
                }

                if (codigoCliente.equals(cliente.getCodigo())) {
                    txtHistorial.append("Fecha: " + fecha +
                            " | Producto: " + nombreProducto +
                            " | Código: " + codigoProducto +
                            " | Cantidad: " + cantidad +
                            " | Precio: Q" + String.format("%.2f", precioUnitario) +
                            " | Total: Q" + String.format("%.2f", totalCompra) +
                            " | Vendedor: " + vendedor + "\n");
                    totalGeneral += totalCompra;
                    encontrado = true;
                }
            }

            if (!encontrado) {
                txtHistorial.append("No tienes compras registradas.\n");
            } else {
                txtHistorial.append("\n=== TOTAL GENERAL DE COMPRAS: Q" + String.format("%.2f", totalGeneral) + " ===\n");
            }

        } catch (IOException e) {
            txtHistorial.append("Error al leer historial: " + e.getMessage());
        }
    }

    // ===========================================================
    // 🔹 Filtrar por fecha en formato dd/MM/yyyy
    // ===========================================================
    private void filtrarPorFecha() {
        String fechaFiltro = JOptionPane.showInputDialog(this, "Ingresa la fecha (dd/mm/yyyy):");

        if (fechaFiltro == null || fechaFiltro.trim().isEmpty()) return;

        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        try {
            LocalDate fechaIngresada = LocalDate.parse(fechaFiltro, formato);
            String fechaFormateada = fechaIngresada.format(formato);

            txtHistorial.setText("=== COMPRAS DEL " + fechaFormateada + " ===\n");

            File archivo = new File("src/main/resources/data/historial_compras.csv");
            if (!archivo.exists()) {
                txtHistorial.append("No hay historial de compras disponible.\n");
                return;
            }

            double totalGeneral = 0.0;
            boolean encontrado = false;

            try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
                String linea;

                while ((linea = br.readLine()) != null) {
                    if (linea.startsWith("Fecha")) continue;

                    String[] partes = linea.split(",");
                    if (partes.length < 6) continue;

                    String fecha = partes[0];
                    String codigoCliente = partes[1];
                    String codigoProducto = partes[2];
                    String nombreProducto = partes[3];
                    String cantidad = partes[4];
                    String vendedor = partes[5];

                    double precioUnitario = 0.0;
                    double totalCompra = 0.0;

                    if (partes.length >= 8) {
                        try {
                            precioUnitario = Double.parseDouble(partes[6]);
                            totalCompra = Double.parseDouble(partes[7]);
                        } catch (NumberFormatException ex) {
                            // Ignorar error
                        }
                    }

                    if (fecha.equals(fechaFormateada) && codigoCliente.equals(cliente.getCodigo())) {
                        txtHistorial.append("Fecha: " + fecha +
                                " | Producto: " + nombreProducto +
                                " | Código: " + codigoProducto +
                                " | Cantidad: " + cantidad +
                                " | Precio: Q" + String.format("%.2f", precioUnitario) +
                                " | Total: Q" + String.format("%.2f", totalCompra) +
                                " | Vendedor: " + vendedor + "\n");
                        totalGeneral += totalCompra;
                        encontrado = true;
                    }
                }

                if (!encontrado) {
                    txtHistorial.append("No se encontraron compras para esa fecha.\n");
                } else {
                    txtHistorial.append("\n=== TOTAL DE ESE DÍA: Q" + String.format("%.2f", totalGeneral) + " ===\n");
                }

            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Formato de fecha inválido. Usa dd/mm/yyyy", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}