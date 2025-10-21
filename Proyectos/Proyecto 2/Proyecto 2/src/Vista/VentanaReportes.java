/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package Vista;

import Controlador.*;
import Controlador.Bitacora;
import Modelo.*;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class VentanaReportes extends JFrame {

    private JTextArea txtSalida;
    private JButton btnUsuarios, btnProductos, btnPedidos, btnBitacora, btnExportarPDF;

    public VentanaReportes() {
        setTitle("Reportes del Sistema");
        setSize(650, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        txtSalida = new JTextArea();
        txtSalida.setEditable(false);
        JScrollPane scroll = new JScrollPane(txtSalida);

        btnUsuarios = new JButton("Usuarios");
        btnProductos = new JButton("Productos");
        btnPedidos = new JButton("Pedidos");
        btnBitacora = new JButton("Bitácora");
        btnExportarPDF = new JButton("Exportar a PDF");

        JPanel botones = new JPanel(new GridLayout(1, 5, 5, 5));
        botones.add(btnUsuarios);
        botones.add(btnProductos);
        botones.add(btnPedidos);
        botones.add(btnBitacora);
        botones.add(btnExportarPDF);

        add(botones, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);

        btnUsuarios.addActionListener(e -> mostrarUsuarios());
        btnProductos.addActionListener(e -> mostrarProductos());
        btnPedidos.addActionListener(e -> mostrarPedidos());
        btnBitacora.addActionListener(e -> mostrarBitacora());
        btnExportarPDF.addActionListener(e -> exportarPDF());
    }

    private void mostrarUsuarios() {
        txtSalida.setText("=== REPORTE DE USUARIOS ===\n");
        Vendedores[] vendedores = Vendedor.getVendedores();
        Cliente[] clientes = Clientes.getClientes();

        for (int i = 0; i < Vendedor.getCantidadVendedores(); i++) {
            txtSalida.append("[VENDEDOR] " + vendedores[i].getCodigo() + " - " + vendedores[i].getNombre() + "\n");
        }
        for (int i = 0; i < Clientes.getCantidadClientes(); i++) {
            txtSalida.append("[CLIENTE] " + clientes[i].getCodigo() + " - " + clientes[i].getNombre() + "\n");
        }
    }

    private void mostrarProductos() {
        txtSalida.setText("=== REPORTE DE PRODUCTOS ===\n");
        Producto[] productos = Productos.getProductos();

        for (int i = 0; i < Productos.getCantidadProductos(); i++) {
            txtSalida.append(productos[i].toString() + "\n");
        }
    }

    private void mostrarPedidos() {
        txtSalida.setText("=== REPORTE DE PEDIDOS ===\n");
        Pedido[] pedidos = Pedidos.getPedidos();

        for (int i = 0; i < Pedidos.getCantidadPedidos(); i++) {
            txtSalida.append(pedidos[i].toString() + "\n");
        }
    }

    private void mostrarBitacora() {
        txtSalida.setText("=== BITÁCORA DEL SISTEMA ===\n");
        String[] registros = Bitacora.getRegistros();
        int total = Bitacora.getCantidadRegistros();

        if (total == 0) {
            txtSalida.append("No hay registros aún.");
            return;
        }

        for (int i = 0; i < total; i++) {
            txtSalida.append((i + 1) + ". " + registros[i] + "\n");
        }
    }

    private void exportarPDF() {
        try {
            ReportePDF.generar(txtSalida.getText());
            JOptionPane.showMessageDialog(this, "Reporte exportado correctamente (Reporte.pdf)");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error al generar PDF: " + ex.getMessage());
        }
    }
}

