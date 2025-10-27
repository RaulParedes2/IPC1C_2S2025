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
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class VentanaProductos extends JFrame {

    private JTextField txtCodigo, txtNombre, txtAtributo, txtPrecio, txtStock;
    private JComboBox<String> comboCategoria;
    private JButton btnAgregar, btnModificar, btnEliminar, btnListar, btnCargarCSV, btnGuardarCSV, btnVerDetalle;
    private JTextArea txtSalida;

    public VentanaProductos() {
        setTitle("Gestión de Productos");
        setSize(950, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //====== Campos=======
        txtCodigo = new JTextField();
        txtNombre = new JTextField();
        txtAtributo = new JTextField();
        txtPrecio = new JTextField();
        txtStock = new JTextField();

        txtCodigo.setBorder(BorderFactory.createTitledBorder("Código (Único)"));
        txtNombre.setBorder(BorderFactory.createTitledBorder("Nombre del Producto"));
        txtAtributo.setBorder(BorderFactory.createTitledBorder("Atributo específico"));
        txtPrecio.setBorder(BorderFactory.createTitledBorder("Precio (Q)"));
        txtStock.setBorder(BorderFactory.createTitledBorder("Stock Disponible"));

        comboCategoria = new JComboBox<>(new String[]{"Tecnología", "Alimento", "General"});
        comboCategoria.setBorder(BorderFactory.createTitledBorder("Categoría"));

        //===Botones=====
        btnAgregar = new JButton("Agregar");
        btnModificar = new JButton("Modificar");
        btnEliminar = new JButton("Eliminar");
        btnListar = new JButton("Listar");
        btnVerDetalle = new JButton("Ver Detalle");
        btnCargarCSV = new JButton("Cargar CSV");
        btnGuardarCSV = new JButton("Guardar CSV");

        txtSalida = new JTextArea();
        txtSalida.setEditable(false);
        JScrollPane scroll = new JScrollPane(txtSalida);

        // Panel de campos
        JPanel panelCampos = new JPanel(new GridLayout(3, 3, 5, 5));
        panelCampos.add(txtCodigo);
        panelCampos.add(txtNombre);
        panelCampos.add(comboCategoria);
        panelCampos.add(txtAtributo);
        panelCampos.add(txtPrecio);
        panelCampos.add(txtStock);

        // Panel de botones
        JPanel panelBotones = new JPanel(new GridLayout(1, 7, 5, 5));
        panelBotones.add(btnAgregar);
        panelBotones.add(btnModificar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnListar);
        panelBotones.add(btnVerDetalle);
        panelBotones.add(btnCargarCSV);
        panelBotones.add(btnGuardarCSV);

        add(panelCampos, BorderLayout.NORTH);
        add(panelBotones, BorderLayout.CENTER);
        add(scroll, BorderLayout.SOUTH);

        //==== Eventos===========
        btnAgregar.addActionListener(e -> agregarProducto());
        btnModificar.addActionListener(e -> modificarProducto());
        btnEliminar.addActionListener(e -> eliminarProducto());
        btnListar.addActionListener(e -> listarProductos());
        btnVerDetalle.addActionListener(e -> verDetalleProducto());
        btnCargarCSV.addActionListener(e -> Productos.cargarStock());
        btnGuardarCSV.addActionListener(e -> Productos.guardarStock());
    }

    // ===============================================================
    // AGREGAR PRODUCTO
    // ===============================================================
    private void agregarProducto() {
        String codigo = txtCodigo.getText().trim();
        String nombre = txtNombre.getText().trim();
        String categoria = comboCategoria.getSelectedItem().toString();
        String atributo = txtAtributo.getText().trim();
        String precioStr = txtPrecio.getText().trim();
        String stockStr = txtStock.getText().trim();

        if (codigo.isEmpty() || nombre.isEmpty() || atributo.isEmpty()
                || precioStr.isEmpty() || stockStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, completa todos los campos.");
            return;
        }

        // Validaciones numéricas
        double precio;
        int stock;
        try {
            precio = Double.parseDouble(precioStr);
            stock = Integer.parseInt(stockStr);
            if (precio < 0 || stock < 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Precio y stock deben ser números positivos.");
            return;
        }

        if (Productos.buscarProducto(codigo) != null) {
            JOptionPane.showMessageDialog(this, "Código de producto duplicado.");
            return;
        }

        Producto nuevo = switch (categoria.toLowerCase()) {
            case "alimento" -> {
                try {
                    DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    LocalDate fechaCaducidad = LocalDate.parse(atributo, formato);
                    yield new ProductoAl(codigo, nombre, fechaCaducidad, atributo);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Fecha inválida. Usa el formato dd/MM/yyyy.");
                    yield null;
                }
            }
            case "tecnología", "tecnologia" ->
                new ProductoTec(codigo, nombre, 12, atributo);
            case "general" ->
                new ProductoGe(codigo, nombre, categoria, atributo);
            default ->
                null;
        };

        if (nuevo != null) {
            nuevo.setPrecio(precio);
            nuevo.setStock(stock);
            Productos.crearProducto(nuevo);
            Productos.guardarStock();
            JOptionPane.showMessageDialog(this, "Producto agregado correctamente.");
            limpiarCampos();
        }

        Bitacora.registrar(
                "ADMIN",
                "admin",
                "CREAR_PRODUCTO",
                "EXITOSA",
                "Producto agregado: " + nuevo.getCodigo()
        );
    }

    // ===============================================================
    // MODIFICAR PRODUCTO
    // ===============================================================
    private void modificarProducto() {
        String codigo = txtCodigo.getText().trim();
        Producto p = Productos.buscarProducto(codigo);

        if (p == null) {
            JOptionPane.showMessageDialog(this, "Producto no encontrado.");
            return;
        }

        if (!txtNombre.getText().trim().isEmpty()) {
            p.setNombre(txtNombre.getText().trim());
        }
        if (!txtAtributo.getText().trim().isEmpty()) {
            p.setAtributo(txtAtributo.getText().trim());
        }
        if (!txtPrecio.getText().trim().isEmpty()) {
            p.setPrecio(Double.parseDouble(txtPrecio.getText().trim()));
        }
        if (!txtStock.getText().trim().isEmpty()) {
            p.setStock(Integer.parseInt(txtStock.getText().trim()));
        }

        Productos.guardarStock();
        JOptionPane.showMessageDialog(this, "Producto modificado correctamente.");
        limpiarCampos();
        
        Bitacora.registrar(
                "ADMIN",
                "admin",
                "MODIFICAR_PRODUCTO",
                "EXITOSA",
                "Producto MODIFICADO: " + txtCodigo.getText()
        );
    }

    // ===============================================================
    // ELIMINAR PRODUCTO
    // ===============================================================
    private void eliminarProducto() {
        String codigo = txtCodigo.getText().trim();
        Producto p = Productos.buscarProducto(codigo);

        if (p == null) {
            JOptionPane.showMessageDialog(this, "Producto no encontrado.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Eliminar producto " + p.getNombre() + "?",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            Productos.eliminarProducto(codigo);
            Productos.guardarStock();
            JOptionPane.showMessageDialog(this, "Producto eliminado.");
            
            Bitacora.registrar(
                "ADMIN",
                "admin",
                "PRODUCTO ELIMINADO",
                "FALLIDO",
                "Producto ELIMINADO: " + txtCodigo.getText()
        );
        }
        
        Bitacora.registrar(
                "ADMIN",
                "admin",
                "PRODUCTO ELIMINADO",
                "EXITOSA",
                "Producto ELIMINADO: " + txtCodigo.getText()
        );
    }

    // ===============================================================
    // LISTAR PRODUCTOS
    // ===============================================================
    private void listarProductos() {
        txtSalida.setText("=== LISTA DE PRODUCTOS ===\n");
        Producto[] lista = Productos.getProductos();
        int total = Productos.getCantidadProductos();

        for (int i = 0; i < total; i++) {
            Producto p = lista[i];
            txtSalida.append(p.getCodigo() + " | " + p.getNombre()
                    + " | " + p.getCategoria()
                    + " | " + p.getAtributo()
                    + " | Precio: Q." + p.getPrecio()
                    + " | Stock: " + p.getStock() + "\n");
        }
    }

    // ===============================================================
    // VER DETALLE
    // ===============================================================
    private void verDetalleProducto() {
        String codigo = txtCodigo.getText().trim();
        Producto p = Productos.buscarProducto(codigo);

        if (p == null) {
            JOptionPane.showMessageDialog(this, "Producto no encontrado.");
            return;
        }

        String detalle = switch (p.getCategoria().toLowerCase()) {
            case "alimento" ->
                "Caduca el: " + p.getAtributo();
            case "tecnología", "tecnologia" ->
                "Garantía: " + p.getAtributo() + " meses";
            case "general" ->
                "Material: " + p.getAtributo();
            default ->
                "Sin detalles adicionales.";
        };

        JOptionPane.showMessageDialog(this,
                detalle + "\nPrecio: Q." + p.getPrecio() + "\nStock: " + p.getStock(),
                "Detalle del Producto", JOptionPane.INFORMATION_MESSAGE);
    }

    private void limpiarCampos() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtAtributo.setText("");
        txtPrecio.setText("");
        txtStock.setText("");
    }
}
//En listar no esta aguradando como debe
//No esta cargando 
