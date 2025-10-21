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

public class VentanaProductos extends JFrame {

    private JTextField txtCodigo, txtNombre, txtStock, txtAtributo;
    private JComboBox<String> comboCategoria;
    private JButton btnAgregar,btnModificar, btnEliminar, btnListar, btnCargarCSV, btnGuardarCSV, btnVerDetalle;
    private JTextArea txtSalida;

    public VentanaProductos() {
        setTitle("Gestión de Productos");
        setSize(750, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
       
        //====== Campos=======
        
        txtCodigo = new JTextField();
        txtNombre = new JTextField();
        txtStock = new JTextField();

        txtCodigo.setBorder(BorderFactory.createTitledBorder("Código (Unico)"));
        txtNombre.setBorder(BorderFactory.createTitledBorder("Nombre del Producto"));
        txtStock.setBorder(BorderFactory.createTitledBorder("Stock"));
        
        comboCategoria = new JComboBox<>(new String[]{"Tecnología", "Alimento", "General"});
        comboCategoria.setBorder(BorderFactory.createTitledBorder("Categoría"));

        txtAtributo = new JTextField();
        txtAtributo.setBorder(BorderFactory.createTitledBorder("Atributo específico"));

        //===Botones=====
        btnAgregar = new JButton("Agregar");
        btnModificar = new JButton("Modificar");
        btnEliminar=new JButton("Eliminar");
        btnListar = new JButton("Listar");
        btnCargarCSV = new JButton("Cargar Producto");
        btnGuardarCSV= new JButton("Guardar Producto");
        btnVerDetalle=new JButton("Ver detalles");
        
        

        txtSalida = new JTextArea();
        txtSalida.setEditable(false);
        JScrollPane scroll = new JScrollPane(txtSalida);

        /*JPanel inputPanel = new JPanel(new GridLayout(3, 2));
        inputPanel.add(txtCodigo);
        inputPanel.add(txtNombre);
        inputPanel.add(txtStock);
        inputPanel.add(btnAgregar);
        inputPanel.add(btnListar);
        inputPanel.add(btnEliminar);*/
        
        JPanel panelCampos = new JPanel(new GridLayout(5, 2, 5, 5));
        panelCampos.add(txtCodigo);
        panelCampos.add(txtNombre);
        panelCampos.add(comboCategoria);
        panelCampos.add(txtStock);
        panelCampos.add(txtAtributo);

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
        btnCargarCSV.addActionListener(e -> cargarCSV());
        btnGuardarCSV.addActionListener(e -> guardarCSV());
    }
    
    private void agregarProducto() {
    String codigo = txtCodigo.getText().trim();
    String nombre = txtNombre.getText().trim();
    String categoria = comboCategoria.getSelectedItem().toString();
    String atributo = txtAtributo.getText().trim();
    String stockStr = txtStock.getText().trim();

    if (codigo.isEmpty() || nombre.isEmpty() || stockStr.isEmpty() || atributo.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Por favor, completa todos los campos.");
        return;
    }

    // Validar duplicado
    if (Productos.buscarProducto(codigo) != null) {
        JOptionPane.showMessageDialog(this, "Código de producto duplicado.");
        return;
    }

    // Validar stock
    int stock;
    try {
        stock = Integer.parseInt(stockStr);
        if (stock < 0) {
            JOptionPane.showMessageDialog(this, "El stock no puede ser negativo.");
            return;
        }
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "El stock debe ser un número entero.");
        return;
    }

    Producto nuevo = null;

    // Crear producto según categoría seleccionada
    switch (categoria.toLowerCase()) {
        case "alimento":
            try {
                // Se usa el campo atributo para ingresar la fecha (formato dd/MM/yyyy)
                java.time.format.DateTimeFormatter formato = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy");
                java.time.LocalDate fechaCaducidad = java.time.LocalDate.parse(atributo, formato);
                nuevo = new ProductoAl(codigo, nombre, fechaCaducidad, atributo, stock);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Fecha de caducidad inválida. Usa el formato dd/MM/yyyy.");
                return;
            }
            break;

        case "general":
            nuevo = new ProductoGe(codigo, nombre, atributo, "Material", stock);
            break;

        case "tecnología":
        case "tecnologia":
            try {
                int meses = Integer.parseInt(atributo); // atributo = meses de garantía
                nuevo = new ProductoTec(codigo, nombre, meses, "Garantía", stock);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Los meses de garantía deben ser un número entero.");
                return;
            }
            break;

        default:
            JOptionPane.showMessageDialog(this, "Categoría no reconocida.");
            return;
    }

    // Guardar el producto
    Productos.crearProducto(nuevo);
    JOptionPane.showMessageDialog(this, "Producto agregado correctamente.");
    limpiarCampos();
    }
    
    //====================Modificar=================
        private void modificarProducto() {
        String codigo = txtCodigo.getText().trim();
        if (codigo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese el código del producto a modificar.");
            return;
        }

        Producto existente = Productos.buscarProducto(codigo);
        if (existente == null) {
            JOptionPane.showMessageDialog(this, " No se encontró ningún producto con ese código.");
            return;
        }

        String nuevoNombre = txtNombre.getText().trim();
        String nuevaCategoria = comboCategoria.getSelectedItem().toString();
        String nuevoAtributo = txtAtributo.getText().trim();
        String nuevoStockStr = txtStock.getText().trim();

        if (nuevoNombre.isEmpty() || nuevoAtributo.isEmpty() || nuevoStockStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Completa todos los campos antes de modificar.");
            return;
        }

        int nuevoStock;
        try {
            nuevoStock = Integer.parseInt(nuevoStockStr);
            if (nuevoStock < 0) {
                JOptionPane.showMessageDialog(this, "El stock no puede ser negativo.");
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El stock debe ser un número entero.");
            return;
        }

        // Actualizar los datos
        existente.setNombre(nuevoNombre);
        existente.setCategoria(nuevaCategoria);
        existente.setAtributo(nuevoAtributo);
        existente.setStock(nuevoStock);

        JOptionPane.showMessageDialog(this, " Producto modificado correctamente.");
        limpiarCampos();
    }

    // ===============================================================
    // ELIMINAR PRODUCTO
    // ===============================================================
    private void eliminarProducto() {
        String codigo = txtCodigo.getText().trim();
        if (codigo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingresa el código del producto a eliminar.");
            return;
        }

        Productos.eliminarProducto(codigo);
        JOptionPane.showMessageDialog(this, " Producto eliminado y lista reordenada.");
        limpiarCampos();
    }

    // ===============================================================
    // LISTAR
    // ===============================================================
    private void listarProductos() {
        txtSalida.setText("=== LISTA DE PRODUCTOS ===\n");
        Producto[] lista = Productos.getProductos();
        int total = Productos.getCantidadProductos();

        if (total == 0) {
            txtSalida.append("No hay productos registrados.\n");
            return;
        }

        for (int i = 0; i < total; i++) {
            Producto p = lista[i];
            txtSalida.append(p.getCodigo() + " | " + p.getNombre() + " | " +
                    p.getCategoria() + " | " + p.getAtributo() + " | Stock: " + p.getStock() + "\n");
        }
    }

    // ===============================================================
    // VER DETALLE
    // ===============================================================
    private void verDetalleProducto() {
        String codigo = txtCodigo.getText().trim();
        if (codigo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingresa el código del producto.");
            return;
        }

        Producto p = Productos.buscarProducto(codigo);
        if (p == null) {
            JOptionPane.showMessageDialog(this, " No se encontró el producto.");
            return;
        }

        String detalle = switch (p.getCategoria()) {
            case "Tecnología" -> "Garantía (meses): " + p.getAtributo();
            case "Alimento" -> "Fecha de caducidad: " + p.getAtributo();
            case "General" -> "Material: " + p.getAtributo();
            default -> "Sin detalle específico.";
        };

        JOptionPane.showMessageDialog(this,
                "Código: " + p.getCodigo() +
                        "\nNombre: " + p.getNombre() +
                        "\nCategoría: " + p.getCategoria() +
                        "\n" + detalle +
                        "\nStock: " + p.getStock());
    }

    // ===============================================================
    // CARGAR CSV
    // ===============================================================
   private void cargarCSV() {
    File archivo = new File("src/data/productos.csv");
    int cargados = 0, errores = 0;

    try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
        String linea;
        while ((linea = br.readLine()) != null) {
            String[] partes = linea.split(",");

            // Estructura esperada del CSV: codigo,nombre,categoria,atributo,stock
            if (partes.length !=5) {
                errores++;
                continue;
            }

            String codigo = partes[0].trim();
            String nombre = partes[1].trim();
            String categoria = partes[2].trim();
            String atributo = partes[3].trim();
            int stock;

            try {
                stock = Integer.parseInt(partes[4].trim());
                if (stock < 0) stock = 0;
            } catch (NumberFormatException e) {
                errores++;
                continue;
            }

            if (Productos.buscarProducto(codigo) != null) {
                errores++;
                continue;
            }
            Producto nuevo = null;

            switch (categoria.toLowerCase()) {
                case "alimento":
                    try {
                        java.time.format.DateTimeFormatter formato = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        java.time.LocalDate fechaCaducidad = java.time.LocalDate.parse(atributo, formato);
                        nuevo = new ProductoAl(codigo, nombre, fechaCaducidad, atributo, stock);
                    } catch (Exception ex) {
                        errores++;
                        continue;
                    }
                    break;

                case "general":
                    nuevo = new ProductoGe(codigo, nombre, atributo, "Material", stock);
                    break;

                case "tecnologia":
                case "tecnología":
                    try {
                        int mesesGarantia = Integer.parseInt(atributo);
                        nuevo = new ProductoTec(codigo, nombre, mesesGarantia, "Garantía", stock);
                    } catch (NumberFormatException ex) {
                        errores++;
                        continue;
                    }
                    break;

                default:
                    errores++;
                    continue;
            }

            if (nuevo != null) {
                Productos.crearProducto(nuevo);
                cargados++;
            }
        }

        JOptionPane.showMessageDialog(this,
                "Carga completa desde 'src/data/productos.csv'\n" +
                        cargados + " productos cargados\n " + errores + " errores o duplicados.");

    } catch (IOException e) {
        JOptionPane.showMessageDialog(this, "Error al leer archivo: " + e.getMessage());
    }
}

    // ===============================================================
    // GUARDAR CSV
    // ===============================================================
    private void guardarCSV() {
        File archivo = new File("src/data/productos.csv");

        try (PrintWriter pw = new PrintWriter(new FileWriter(archivo))) {
            Producto[] lista = Productos.getProductos();
            int total = Productos.getCantidadProductos();

            for (int i = 0; i < total; i++) {
                Producto p = lista[i];
                pw.println(p.getCodigo() + "," + p.getNombre() + "," +
                        p.getCategoria() + "," + p.getAtributo()+","+p.getStock());
            }

            JOptionPane.showMessageDialog(this, "Archivo guardado en 'src/data/productos.csv'");

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al guardar: " + e.getMessage());
        }
    }

    private void limpiarCampos() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtStock.setText("");
        txtAtributo.setText("");
    }

}

