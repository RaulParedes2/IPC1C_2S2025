/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

import Controlador.*;
import Modelo.*;
import javax.swing.*;
import java.awt.*;

public class VentanaPedidos extends JFrame {

    private Vendedores vendedor;
    private JTextField txtCodigoPedido, txtCodigoCliente, txtCodigoProducto, txtCantidad;
    private JTextArea txtSalida;

    public VentanaPedidos(Vendedores vendedor) {
        this.vendedor = vendedor;
        setTitle("Gestión de Pedidos - " + vendedor.getNombre());
        setSize(550, 500);
        setLocationRelativeTo(null);

        txtCodigoPedido = new JTextField();
        txtCodigoCliente = new JTextField();
        txtCodigoProducto = new JTextField();
        txtCantidad = new JTextField();

        txtCodigoPedido.setBorder(BorderFactory.createTitledBorder("Código del Pedido"));
        txtCodigoCliente.setBorder(BorderFactory.createTitledBorder("Código del Cliente"));
        txtCodigoProducto.setBorder(BorderFactory.createTitledBorder("Código del Producto"));
        txtCantidad.setBorder(BorderFactory.createTitledBorder("Cantidad"));

        JButton btnCrear = new JButton("Crear Pedido");
        JButton btnConfirmar = new JButton("Confirmar Pedido");
        JButton btnEliminar = new JButton("Eliminar Pedido");
        JButton btnListar = new JButton("Listar Pedidos");

        txtSalida = new JTextArea();
        txtSalida.setEditable(false);
        JScrollPane scroll = new JScrollPane(txtSalida);

        // ===========================================================
        // 🔹 Crear pedido
        // ===========================================================
        btnCrear.addActionListener(e -> {
            Cliente c = Clientes.buscarCliente(txtCodigoCliente.getText());
            Producto p = Productos.buscarProducto(txtCodigoProducto.getText());
            int cantidad = Integer.parseInt(txtCantidad.getText());

            if (c != null && p != null) {
                Pedido pedido = new Pedido(txtCodigoPedido.getText(), c, p, cantidad);
                Pedidos.crearPedido(pedido);
                JOptionPane.showMessageDialog(this, "Pedido creado correctamente.");
            } else {
                JOptionPane.showMessageDialog(this, "Cliente o producto no encontrado.");
            }
        });

        // ===========================================================
        // 🔹 Confirmar pedido
        // ===========================================================
        btnConfirmar.addActionListener(e -> {
            Pedidos.confirmarPedido(txtCodigoPedido.getText(), vendedor);
        });

        // ===========================================================
        // 🔹 Eliminar pedido
        // ===========================================================
        btnEliminar.addActionListener(e -> {
            Pedidos.eliminarPedido(txtCodigoPedido.getText());
        });

        // ===========================================================
        // 🔹 Listar pedidos
        // ===========================================================
        btnListar.addActionListener(e -> {
            txtSalida.setText("");
            Pedido[] lista = Pedidos.getPedidos();
            for (int i = 0; i < Pedidos.getCantidadPedidos(); i++) {
                txtSalida.append(lista[i].toString() + "\n");
            }
        });

        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        inputPanel.add(txtCodigoPedido);
        inputPanel.add(txtCodigoCliente);
        inputPanel.add(txtCodigoProducto);
        inputPanel.add(txtCantidad);
        inputPanel.add(btnCrear);
        inputPanel.add(btnConfirmar);
        inputPanel.add(btnEliminar);
        inputPanel.add(btnListar);

        add(inputPanel, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
    }
}

