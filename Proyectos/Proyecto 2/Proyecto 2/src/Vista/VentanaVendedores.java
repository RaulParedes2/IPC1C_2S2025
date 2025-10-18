/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

import Controlador.*;
import Modelo.*;
import javax.swing.*;
import java.awt.*;

public class VentanaVendedores extends JFrame {

    private JTextField txtCodigo, txtNombre, txtGenero, txtPassword;
    private JButton btnAgregar, btnActualizar, btnEliminar, btnListar;
    private JTextArea txtSalida;

    public VentanaVendedores() {
        setTitle("Gestión de Vendedores");
        setSize(550, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // ====== Campos de texto ======
        txtCodigo = new JTextField();
        txtNombre = new JTextField();
        txtGenero = new JTextField();
        txtPassword = new JTextField();

        txtCodigo.setBorder(BorderFactory.createTitledBorder("Código"));
        txtNombre.setBorder(BorderFactory.createTitledBorder("Nombre"));
        txtGenero.setBorder(BorderFactory.createTitledBorder("Género (M/F)"));
        txtPassword.setBorder(BorderFactory.createTitledBorder("Contraseña"));

        // ====== Botones ======
        btnAgregar = new JButton("Agregar");
        btnActualizar = new JButton("Actualizar");
        btnEliminar = new JButton("Eliminar");
        btnListar = new JButton("Listar");

        // ====== Área de salida ======
        txtSalida = new JTextArea();
        txtSalida.setEditable(false);
        JScrollPane scroll = new JScrollPane(txtSalida);

        // ====== Panel superior ======
        JPanel topPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        topPanel.add(txtCodigo);
        topPanel.add(txtNombre);
        topPanel.add(txtGenero);
        topPanel.add(txtPassword);
        topPanel.add(btnAgregar);
        topPanel.add(btnActualizar);
        topPanel.add(btnEliminar);
        topPanel.add(btnListar);

        add(topPanel, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);

        // ====== Acciones de los botones ======
        btnAgregar.addActionListener(e -> agregarVendedor());
        btnActualizar.addActionListener(e -> actualizarVendedor());
        btnEliminar.addActionListener(e -> eliminarVendedor());
        btnListar.addActionListener(e -> listarVendedores());
    }

    private void agregarVendedor() {
        String codigo = txtCodigo.getText().trim();
        String nombre = txtNombre.getText().trim();
        String genero = txtGenero.getText().trim();
        String password = txtPassword.getText().trim();

        if (codigo.isEmpty() || nombre.isEmpty() || genero.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, llena todos los campos.");
            return;
        }

        Vendedores v = new Vendedores(codigo, nombre, genero, password);
        Vendedor.crearVendedor(v);
        JOptionPane.showMessageDialog(this, "Vendedor agregado correctamente.");
        limpiarCampos();
    }

    private void actualizarVendedor() {
        String codigo = txtCodigo.getText().trim();
        String nuevoNombre = txtNombre.getText().trim();
        String nuevaPassword = txtPassword.getText().trim();

        if (codigo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingresa el código del vendedor a actualizar.");
            return;
        }

        Vendedor.actualizarVendedor(codigo, nuevoNombre, nuevaPassword);
        JOptionPane.showMessageDialog(this, "Datos del vendedor actualizados.");
        limpiarCampos();
    }

    private void eliminarVendedor() {
        String codigo = txtCodigo.getText().trim();

        if (codigo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingresa el código del vendedor a eliminar.");
            return;
        }

        Vendedor.eliminarVendedor(codigo);
        JOptionPane.showMessageDialog(this, "Vendedor eliminado (lista reordenada).");
        limpiarCampos();
    }

    private void listarVendedores() {
        txtSalida.setText("");
        Vendedores[] lista =Vendedor.getVendedores();
        int total = Vendedor.getCantidadVendedores();

        if (total == 0) {
            txtSalida.setText("No hay vendedores registrados.");
            return;
        }

        txtSalida.append("=== LISTA DE VENDEDORES ===\n");
        for (int i = 0; i < total; i++) {
            Vendedores v = lista[i];
            txtSalida.append(v.getCodigo() + " | " + v.getNombre() +
                             " | Ventas: " + v.getVentasCofirmadas() + "\n");
        }
    }

    private void limpiarCampos() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtGenero.setText("");
        txtPassword.setText("");
    }
}
