/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

import Controlador.*;
import Modelo.*;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class VentanaStock extends JFrame {

    private Vendedores vendedor;
    private JTextField txtCodigoProducto, txtCantidad;
    private JTextArea txtSalida;
    private JButton btnAgregar, btnCargarCSV, btnGuardarCSV, btnExportarHistorial, btnVerInventario;

    public VentanaStock(Vendedores vendedor) {
        this.vendedor = vendedor;

        setTitle("Gestión de Stock - " + vendedor.getNombre());
        setSize(750, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // ==========================================
        // CAMPOS
        // ==========================================
        txtCodigoProducto = new JTextField();
        txtCantidad = new JTextField();

        txtCodigoProducto.setBorder(BorderFactory.createTitledBorder("Código del Producto"));
        txtCantidad.setBorder(BorderFactory.createTitledBorder("Cantidad a Agregar"));

        // ==========================================
        // BOTONES
        // ==========================================
        btnAgregar = new JButton("Agregar Stock");
        btnCargarCSV = new JButton("Cargar Stock CSV");
        btnGuardarCSV = new JButton("Guardar Stock CSV");
        btnExportarHistorial = new JButton("Exportar Historial");
        btnVerInventario = new JButton("Ver Inventario");

        // ==========================================
        // ÁREA DE SALIDA
        // ==========================================
        txtSalida = new JTextArea();
        txtSalida.setEditable(false);
        JScrollPane scroll = new JScrollPane(txtSalida);

        // ==========================================
        // PANEL SUPERIOR (FORMULARIO)
        // ==========================================
        JPanel panelCampos = new JPanel(new GridLayout(2, 2, 5, 5));
        panelCampos.add(txtCodigoProducto);
        panelCampos.add(txtCantidad);
        panelCampos.add(btnAgregar);
        panelCampos.add(btnVerInventario);

        // ==========================================
        // PANEL INFERIOR (BOTONES)
        // ==========================================
        JPanel panelBotones = new JPanel(new GridLayout(1, 3, 5, 5));
        panelBotones.add(btnCargarCSV);
        panelBotones.add(btnGuardarCSV);
        panelBotones.add(btnExportarHistorial);

        add(panelCampos, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        // ==========================================
        // EVENTOS
        // ==========================================
        btnAgregar.addActionListener(e -> agregarStock());
        btnCargarCSV.addActionListener(e -> cargarStockCSV());
        btnGuardarCSV.addActionListener(e -> guardarStockCSV());
        btnExportarHistorial.addActionListener(e -> exportarHistorialCSV());
        btnVerInventario.addActionListener(e -> mostrarInventario());
    }

    // ===============================================================
    // AGREGAR STOCK MANUALMENTE (ACTUALIZA stock.csv)
    // ===============================================================
    private void agregarStock() {
        String codigo = txtCodigoProducto.getText().trim();
        String cantidadTxt = txtCantidad.getText().trim();

        if (codigo.isEmpty() || cantidadTxt.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, llena todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int cantidad = Integer.parseInt(cantidadTxt);
            if (cantidad <= 0) {
                JOptionPane.showMessageDialog(this, "La cantidad debe ser mayor que cero.", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Buscar producto en la base
            Producto producto = Productos.buscarProducto(codigo);
            if (producto == null) {
                JOptionPane.showMessageDialog(this, "El producto con código '" + codigo + "' no existe.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Sumar stock existente + cantidad agregada
            int nuevoStock = producto.getStock() + cantidad;
            producto.setStock(nuevoStock);

            // Guardar cambios en el archivo stock.csv
            Productos.guardarStock();
            Controlador.Bitacora.registrar(
                "VEMDEDOR: " +vendedor.getNombre(),
                "CODIGO: " + vendedor.getCodigo(),
                "AGREGAR STOCK",
                "EXITOSA",
                "PRODUCTO: " +txtCodigoProducto.getText()+txtCantidad.getText()
        );

            // Registrar en el historial
            registrarMovimiento(codigo, producto.getNombre(), cantidad, vendedor.getNombre());

            JOptionPane.showMessageDialog(this,
                    "Stock actualizado para " + producto.getNombre()
                    + "\nNuevo stock: " + nuevoStock);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Cantidad inválida. Ingresa un número entero.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        limpiarCampos();
    }

    // ===============================================================
    // REGISTRAR MOVIMIENTO EN HISTORIAL
    // ===============================================================
    private void registrarMovimiento(String codigoProducto, String nombreProducto, int cantidad, String usuario) {
        LocalDateTime ahora = LocalDateTime.now();
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm:ss");

        String registro = formatoFecha.format(ahora) + ","
                + formatoHora.format(ahora) + ","
                + codigoProducto + ","
                + nombreProducto + ","
                + cantidad + ","
                + usuario;

        HistorialStock.agregarMovimiento(registro);
    }

    // ===============================================================
    // EXPORTAR HISTORIAL (FORMATO: FECHA,HORA,CODIGO,PRODUCTO,CANTIDAD,USUARIO)
    // ===============================================================
    private void exportarHistorialCSV() {
        File archivo = new File("src/main/resources/data/historial_stock.csv");

        try (PrintWriter pw = new PrintWriter(new FileWriter(archivo))) {
            pw.println("Fecha,Hora,Código,Producto,Cantidad,Usuario");

            String[] lista = HistorialStock.getMovimientos();
            int total = HistorialStock.getCantidadMovimientos();

            for (int i = 0; i < total; i++) {
                pw.println(lista[i]);
            }

            JOptionPane.showMessageDialog(this, " Historial exportado correctamente a 'src/main/resources/data/historial_stock.csv'");
            Productos.guardarStock();
            Controlador.Bitacora.registrar(
                "VEMDEDOR: " +vendedor.getNombre(),
                "CODIGO: " + vendedor.getCodigo(),
                "EXPORTAR HISTORIAL",
                "EXITOSA",
                " "
        );
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error al guardar historial: " + ex.getMessage());
            Controlador.Bitacora.registrar(
                "VEMDEDOR: " +vendedor.getNombre(),
                "CODIGO: " + vendedor.getCodigo(),
                "EXPORTAR HISTORIAL",
                "FALLIDA",
                " "
        );
        }
    }

    // ===============================================================
// CARGAR STOCK CSV (FORMATO: CÓDIGO,STOCK[,PRECIO])
// ===============================================================
    private void cargarStockCSV() {
        File archivo = new File("src/main/resources/data/stock.csv");
        int actualizados = 0, errores = 0;

        if (!archivo.exists()) {
            JOptionPane.showMessageDialog(this, "No se encontró el archivo: src/main/resources/data/stock.csv");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.startsWith("Código")) {
                    continue;
                }

                String[] partes = linea.split(",");
                if (partes.length < 2) {
                    errores++;
                    continue;
                }

                String codigo = partes[0].trim();
                int cantidad;
                try {
                    cantidad = Integer.parseInt(partes[1].trim());
                } catch (NumberFormatException e) {
                    errores++;
                    continue;
                }

                Producto producto = Vendedor.buscarProducto(codigo);
                if (producto != null) {
                    Vendedor.agregarStock(codigo, cantidad);
                    registrarMovimiento(codigo, producto.getNombre(), cantidad, vendedor.getNombre());
                    actualizados++;
                } else {
                    errores++;
                }
            }

            JOptionPane.showMessageDialog(this,
                    " Carga completada desde stock.csv\n"
                    + "Productos actualizados: " + actualizados + "\n"
                    + "Errores: " + errores);
            
            Controlador.Bitacora.registrar(
                "VEMDEDOR: " +vendedor.getNombre(),
                "CODIGO: " + vendedor.getCodigo(),
                "CARGAR CSV",
                "EXITOSA",
                " "
        );

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, " Error al leer archivo: " + e.getMessage());
            Controlador.Bitacora.registrar(
                "VEMDEDOR: " +vendedor.getNombre(),
                "CODIGO: " + vendedor.getCodigo(),
                "CARGAR CSV",
                "FALLIDA",
                " "
        );
        }
    }
    // ===============================================================
// GUARDAR STOCK CSV (FORMATO: CÓDIGO,STOCK,PRECIO)
// ===============================================================

    private void guardarStockCSV() {
        File archivo = new File("src/main/resources/data/stock.csv");

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            // 🧠 Leer el archivo actual para conservar los precios
            java.util.Map<String, Double> preciosActuales = new java.util.HashMap<>();
            String linea;

            while ((linea = br.readLine()) != null) {
                if (linea.startsWith("Codigo") || linea.startsWith("Código")) {
                    continue;
                }

                String[] partes = linea.split(",");
                if (partes.length >= 3) {
                    String codigo = partes[0].trim();
                    double precio = Double.parseDouble(partes[2].trim());
                    preciosActuales.put(codigo, precio);
                }
            }

            // 📝 Guardar con encabezado y mantener precios
            try (PrintWriter pw = new PrintWriter(new FileWriter(archivo))) {
                pw.println("Codigo,Stock,Precio");

                Producto[] lista = Productos.getProductos();
                int total = Productos.getCantidadProductos();

                for (int i = 0; i < total; i++) {
                    Producto p = lista[i];
                    if (p != null) {
                        int stock = p.getStock();
                        double precio = preciosActuales.getOrDefault(p.getCodigo(), p.getPrecio());
                        pw.println(p.getCodigo() + "," + stock + "," + precio);
                    }
                }
            }

            JOptionPane.showMessageDialog(this, "Stock guardado correctamente en src/main/resources/data/stock.csv");
            Controlador.Bitacora.registrar(
                "VEMDEDOR: " +vendedor.getNombre(),
                "CODIGO: " + vendedor.getCodigo(),
                "GUARDAR CSV",
                "EXITOSA",
                " "
        );

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, " Error al guardar archivo: " + e.getMessage());
            Controlador.Bitacora.registrar(
                "VEMDEDOR: " +vendedor.getNombre(),
                "CODIGO: " + vendedor.getCodigo(),
                "GUARDAR CSV",
                "FALLIDA",
                " "
        );
        }
    }

    // ===============================================================
    // MOSTRAR INVENTARIO EN PANTALLA (LEE: CÓDIGO,STOCK,PRECIO)
    // ===============================================================
    private void mostrarInventario() {
        txtSalida.setText("===== INVENTARIO ACTUAL =====\n");

        String ruta = "src/main/resources/data/stock.csv";
        String linea;

        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            while ((linea = br.readLine()) != null) {
                if (linea.startsWith("Código")) {
                    continue; // Saltar encabezado
                }
                String[] partes = linea.split(",");
                if (partes.length >= 3) {
                    String codigo = partes[0].trim();
                    String stock = partes[1].trim();
                    String precio = partes[2].trim();

                    txtSalida.append(
                            "Código: " + codigo
                            + " | Stock: " + stock
                            + " | Precio: Q" + precio + "\n"
                    );
                } else if (partes.length == 2) { // Soporte por si el CSV viejo no tiene precio
                    String codigo = partes[0].trim();
                    String stock = partes[1].trim();
                    txtSalida.append(
                            "Código: " + codigo
                            + " | Stock: " + stock
                            + " | Precio: (no definido)\n"
                    );
                }
            }

        } catch (IOException e) {
            txtSalida.append("Error al leer el inventario: " + e.getMessage() + "\n");
        }
    }

    private void limpiarCampos() {
        txtCodigoProducto.setText("");
        txtCantidad.setText("");
    }
}
