/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

import Controlador.*;
import Controlador.Bitacora;
import Controlador.Vendedor;
import Modelo.Vendedores;
import Controlador.ReportePDF;
import Modelo.*;
import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class VentanaReportes extends JFrame {

    private JTextArea txtSalida;
    private JButton btnUsuarios, btnProductos, btnPedidos, btnBitacora;
    private JButton btnMasVendidos, btnMenosVendidos, btnInventario,
            btnVentasVendedor, btnClientesActivos, btnPorCaducar, btnReporteFinanciero;

    public VentanaReportes() {
        setTitle("Reportes del Sistema");
        setSize(750, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // ====== PANEL DE TEXTO DE SALIDA ======
        txtSalida = new JTextArea();
        txtSalida.setEditable(false);
        JScrollPane scroll = new JScrollPane(txtSalida);

        // ====== PANEL SUPERIOR (BOTONES DE VISUALIZACIÓN) ======
        JPanel botonesSuperiores = new JPanel(new GridLayout(1, 4, 5, 5));
        botonesSuperiores.setBorder(BorderFactory.createTitledBorder("Visualización de Datos"));
        btnUsuarios = new JButton("Usuarios");
        btnProductos = new JButton("Productos");
        btnPedidos = new JButton("Pedidos");
        btnBitacora = new JButton("Bitácora");

        botonesSuperiores.add(btnUsuarios);
        botonesSuperiores.add(btnProductos);
        botonesSuperiores.add(btnPedidos);
        botonesSuperiores.add(btnBitacora);

        // ====== PANEL INFERIOR (REPORTES PDF) ======
        JPanel panelReportes = new JPanel(new GridLayout(4, 2, 10, 10));
        panelReportes.setBorder(BorderFactory.createTitledBorder("Generación de Reportes PDF"));
        panelReportes.setBackground(new Color(245, 247, 250));

        btnMasVendidos = crearBoton("Productos Más Vendidos", new Color(0, 153, 76));
        btnMenosVendidos = crearBoton("Productos Menos Vendidos", new Color(204, 102, 0));
        btnInventario = crearBoton("Inventario (Stock Crítico)", new Color(51, 102, 204));
        btnVentasVendedor = crearBoton("Ventas por Vendedor", new Color(102, 0, 153));
        btnClientesActivos = crearBoton("Clientes Activos", new Color(0, 153, 153));
        btnPorCaducar = crearBoton("Productos por Caducar", new Color(204, 0, 0));
        btnReporteFinanciero = crearBoton("Reporte Financiero", new Color(153, 0, 0));

        panelReportes.add(btnMasVendidos);
        panelReportes.add(btnMenosVendidos);
        panelReportes.add(btnInventario);
        panelReportes.add(btnVentasVendedor);
        panelReportes.add(btnClientesActivos);
        panelReportes.add(btnPorCaducar);
        panelReportes.add(btnReporteFinanciero);

        // ====== ESTRUCTURA PRINCIPAL ======
        add(botonesSuperiores, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(panelReportes, BorderLayout.SOUTH);

        // ====== EVENTOS DE BOTONES DE VISUALIZACIÓN ======
        btnUsuarios.addActionListener(e -> mostrarUsuarios());
        btnProductos.addActionListener(e -> mostrarProductos());
        btnPedidos.addActionListener(e -> mostrarPedidos());
        btnBitacora.addActionListener(e -> mostrarBitacora());

        // ====== EVENTOS NUEVOS (Reportes PDF con iText) ======
        btnMasVendidos.addActionListener(e -> {
            ReportePDF.generarReporteProductosMasVendidos();
            JOptionPane.showMessageDialog(this, "Reporte de Productos Más Vendidos generado correctamente.");
            Controlador.Bitacora.registrar(
                "ADMIN" ,
                "admin" ,
                "REPORTE PDF",
                "EXITOSA",
                "Productos Más Vendidos"
        );
        });

        btnMenosVendidos.addActionListener(e -> {
            ReportePDF.generarReporteProductosMenosVendidos();
            JOptionPane.showMessageDialog(this, "Reporte de Productos Menos Vendidos generado correctamente.");
            Controlador.Bitacora.registrar(
                "ADMIN" ,
                "admin" ,
                "REPORTE PDF",
                "EXITOSA",
                "Productos Menos Vendidos"
        );
        });

        btnInventario.addActionListener(e -> {
            ReportePDF.generarReporteInventario(Productos.getProductos());
            JOptionPane.showMessageDialog(this, "Reporte de Inventario generado correctamente.");
            Controlador.Bitacora.registrar(
                "ADMIN" ,
                "admin" ,
                "REPORTE PDF",
                "EXITOSA",
                "Inventario"
        );
        });

        btnVentasVendedor.addActionListener(e -> {
            ReportePDF.generarReporteVentasPorVendedor();
            JOptionPane.showMessageDialog(this, "Reporte de Ventas por Vendedor generado correctamente.");
            Controlador.Bitacora.registrar(
                "ADMIN" ,
                "admin" ,
                "REPORTE PDF",
                "EXITOSA",
                "Ventas por Vendedor"
        );
        });

        btnClientesActivos.addActionListener(e -> {
            ReportePDF.generarReporteClientesActivos(Clientes.getClientes());
            JOptionPane.showMessageDialog(this, "Reporte de Clientes Activos generado correctamente.");
            Controlador.Bitacora.registrar(
                "ADMIN" ,
                "admin" ,
                "REPORTE PDF",
                "EXITOSA",
                "Clientes Activos"
        );
        });

        btnPorCaducar.addActionListener(e -> {
            ReportePDF.generarReporteProductosPorCaducar(Productos.getProductos());
            JOptionPane.showMessageDialog(this, "Reporte de Productos por Caducar generado correctamente.");
            Controlador.Bitacora.registrar(
                "ADMIN" ,
                "admin" ,
                "REPORTE PDF",
                "EXITOSA",
                "Productos por Caducar"
        );
        });

        btnReporteFinanciero.addActionListener(e -> {
            ReportePDF.generarReporteFinanciero();
            JOptionPane.showMessageDialog(this, "Reporte Financiero generado correctamente.");
            Controlador.Bitacora.registrar(
                "ADMIN" ,
                "admin" ,
                "REPORTE PDF",
                "EXITOSA",
                "Financiero"
        );
        });
    }

    // ==== BOTONES ESTILIZADOS ====
    private JButton crearBoton(String texto, Color color) {
        JButton boton = new JButton(texto);
        boton.setFocusPainted(false);
        boton.setBackground(color);
        boton.setForeground(Color.WHITE);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));

        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setBackground(color.darker());
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setBackground(color);
            }
        });
        return boton;
    }

    // ==== VISUALIZACIONES DE TEXTO ====
    private void mostrarUsuarios() {
    txtSalida.setText("=== REPORTE DE USUARIOS ===\n");

    File vendedoresFile = new File("src/main/resources/data/vendedores.csv");
    File clientesFile = new File("src/main/resources/data/clientes.csv");

    try {
        if (vendedoresFile.exists()) {
            txtSalida.append("--- VENDEDORES ---\n");
            try (BufferedReader br = new BufferedReader(new FileReader(vendedoresFile))) {
                String linea;
                while ((linea = br.readLine()) != null) {
                    if (linea.startsWith("Codigo")) continue; // saltar encabezado
                    String[] partes = linea.split(",");
                    if (partes.length >= 2)
                        txtSalida.append("[" + partes[0] + "] " + partes[1] + "\n");
                }
            }
        }

        if (clientesFile.exists()) {
            txtSalida.append("\n--- CLIENTES ---\n");
            try (BufferedReader br = new BufferedReader(new FileReader(clientesFile))) {
                String linea;
                while ((linea = br.readLine()) != null) {
                    if (linea.startsWith("Codigo")) continue;
                    String[] partes = linea.split(",");
                    if (partes.length >= 2)
                        txtSalida.append("[" + partes[0] + "] " + partes[1] + "\n");
                }
            }
        }

    } catch (IOException e) {
        txtSalida.append("Error al cargar usuarios: " + e.getMessage());
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
    txtSalida.setText("=== HISTORIAL DE COMPRAS ===\n");
    File archivo = new File("src/main/resources/data/historial_compras.csv");

    if (!archivo.exists()) {
        txtSalida.append("No hay historial de compras disponible.\n");
        return;
    }

    try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
        String linea;
        while ((linea = br.readLine()) != null) {
            if (linea.startsWith("Fecha")) continue;
            txtSalida.append(linea + "\n");
        }
    } catch (IOException e) {
        txtSalida.append("Error al leer historial: " + e.getMessage());
    }
}
    
    
//=======================================================================
    
    private void mostrarBitacora() {
    JDialog dialogo = new JDialog(this, "Consulta de Bitácora", true);
    dialogo.setSize(800, 600);
    dialogo.setLayout(new BorderLayout());

    JTextArea area = new JTextArea();
    area.setEditable(false);
    JScrollPane scroll = new JScrollPane(area);

    JPanel filtros = new JPanel(new GridLayout(3, 4, 5, 5));
    filtros.setBorder(BorderFactory.createTitledBorder("Filtros de búsqueda"));

    JTextField txtTipo = new JTextField();
    txtTipo.setBorder(BorderFactory.createTitledBorder("Tipo de Usuario"));
    JTextField txtOperacion = new JTextField();
    txtOperacion.setBorder(BorderFactory.createTitledBorder("Operación"));
    JTextField txtCodigo = new JTextField();
    txtCodigo.setBorder(BorderFactory.createTitledBorder("Código Usuario"));
    JTextField txtDesde = new JTextField();
    txtDesde.setBorder(BorderFactory.createTitledBorder("Desde (DD/MM/YYYY)"));
    JTextField txtHasta = new JTextField();
    txtHasta.setBorder(BorderFactory.createTitledBorder("Hasta (DD/MM/YYYY)"));

    JButton btnFiltrar = new JButton("Filtrar");
    JButton btnLimpiar = new JButton("Limpiar");
    JButton btnExportCSV = new JButton("Exportar CSV");
    JButton btnExportPDF = new JButton("Exportar PDF");

    filtros.add(txtTipo);
    filtros.add(txtOperacion);
    filtros.add(txtCodigo);
    filtros.add(txtDesde);
    filtros.add(txtHasta);
    filtros.add(btnFiltrar);
    filtros.add(btnLimpiar);
    filtros.add(btnExportCSV);
    filtros.add(btnExportPDF);

    dialogo.add(filtros, BorderLayout.NORTH);
    dialogo.add(scroll, BorderLayout.CENTER);

    // === ACCIONES ===
    btnFiltrar.addActionListener(e -> {
        area.setText("");
        String[] resultados = Bitacora.filtrar(
                txtTipo.getText().trim(),
                txtOperacion.getText().trim(),
                txtCodigo.getText().trim(),
                txtDesde.getText().trim(),
                txtHasta.getText().trim()
        );
        for (int i = 0; i < resultados.length; i++) {
            if (resultados[i] != null)
                area.append((i + 1) + ". " + resultados[i] + "\n");
        }
    });

    btnLimpiar.addActionListener(e -> {
        txtTipo.setText("");
        txtOperacion.setText("");
        txtCodigo.setText("");
        txtDesde.setText("");
        txtHasta.setText("");
        area.setText("");
    });

    btnExportCSV.addActionListener(e -> {
        Bitacora.exportarCSV("src/main/resources/data/bitacora.csv");
        JOptionPane.showMessageDialog(dialogo, "Bitácora exportada a src/main/resources/data/bitacora.csv");
        Controlador.Bitacora.registrar(
                "ADMIN" ,
                "admin" ,
                "REPORTE CSV",
                "EXITOSA",
                "Bitácora exportada"
        );
        
    });

    btnExportPDF.addActionListener(e -> {
        Bitacora.exportarPDF("src/data/bitacora.pdf");
        JOptionPane.showMessageDialog(dialogo, "Bitácora exportada a src/main/resources/data/bitacora.pdf");
        Controlador.Bitacora.registrar(
                "ADMIN" ,
                "admin" ,
                "REPORTE PDF",
                "EXITOSA",
                "Bitácora exportada"
        );
    });

    dialogo.setVisible(true);
}
}
