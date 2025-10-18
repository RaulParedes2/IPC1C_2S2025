/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

import Controlador.*;
import Modelo.*;
import javax.swing.*;
import java.awt.*;

public class VentanaProductos extends JFrame {

    private JTextField txtCodigo, txtNombre, txtStock;
    private JButton btnAgregar, btnListar, btnEliminar;
    private JTextArea txtSalida;

    public VentanaProductos() {
        setTitle("Gestión de Productos");
        setSize(500, 400);
        setLocationRelativeTo(null);

        txtCodigo = new JTextField();
        txtNombre = new JTextField();
        txtStock = new JTextField();

        txtCodigo.setBorder(BorderFactory.createTitledBorder("Código"));
        txtNombre.setBorder(BorderFactory.createTitledBorder("Nombre"));
        txtStock.setBorder(BorderFactory.createTitledBorder("Stock"));

        btnAgregar = new JButton("Agregar");
        btnListar = new JButton("Listar");
        btnEliminar = new JButton("Eliminar");

        txtSalida = new JTextArea();
        txtSalida.setEditable(false);
        JScrollPane scroll = new JScrollPane(txtSalida);

        JPanel inputPanel = new JPanel(new GridLayout(3, 2));
        inputPanel.add(txtCodigo);
        inputPanel.add(txtNombre);
        inputPanel.add(txtStock);
        inputPanel.add(btnAgregar);
        inputPanel.add(btnListar);
        inputPanel.add(btnEliminar);

        btnAgregar.addActionListener(e -> {
            String c = txtCodigo.getText();
            String n = txtNombre.getText();
            int s = Integer.parseInt(txtStock.getText());
            Productos.crearProducto(new ProductoGe(c, n, "Genérico", s));
        });

        btnListar.addActionListener(e -> {
            txtSalida.setText("");
            Producto[] lista = Productos.getProductos();
            for (int i = 0; i < Productos.getCantidadProductos(); i++) {
                txtSalida.append(lista[i] + "\n");
            }
        });

        btnEliminar.addActionListener(e -> {
            Productos.eliminarProducto(txtCodigo.getText());
        });

        add(inputPanel, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
    }
}

