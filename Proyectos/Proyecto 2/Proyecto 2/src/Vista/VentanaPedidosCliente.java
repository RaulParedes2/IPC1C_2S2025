/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

import Controlador.*;
import Modelo.*;
import javax.swing.*;
import java.awt.*;

public class VentanaPedidosCliente extends JFrame {

    private Cliente cliente;
    private JTextField txtCodigoPedido, txtCodigoProducto, txtCantidad;
    private JTextArea txtSalida;

    public VentanaPedidosCliente(Cliente c) {
        this.cliente = c;

        setTitle("Mis Pedidos - " + cliente.getNombre());
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        txtCodigoPedido = new JTextField();
        txtCodigoProducto = new JTextField();
        txtCantidad = new JTextField();

        txtCodigoPedido.setBorder(BorderFactory.createTitledBorder("Código del Pedido"));
        txtCodigoProducto.setBorder(BorderFactory.createTitledBorder("Código del Producto"));
        txtCantidad.setBorder(BorderFactory.createTitledBorder("Cantidad"));

        JButton btnCrear = new JButton("Crear Pedido");
        JButton btnVer = new JButton("Ver Mis Pedidos");
        JButton btnCancelar = new JButton("Cancelar Pedido");

        txtSalida = new JTextArea();
        txtSalida.setEditable(false);
        JScrollPane scroll = new JScrollPane(txtSalida);

        JPanel topPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        topPanel.add(txtCodigoPedido);
        topPanel.add(txtCodigoProducto);
        topPanel.add(txtCantidad);
        topPanel.add(btnCrear);
        topPanel.add(btnVer);
        topPanel.add(btnCancelar);

        add(topPanel, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);

        // ===========================================================
        // 🔹 Crear pedido
        // ===========================================================
        btnCrear.addActionListener(e -> {
            String codPedido = txtCodigoPedido.getText().trim();
            String codProducto = txtCodigoProducto.getText().trim();
            int cantidad;

            try {
                cantidad = Integer.parseInt(txtCantidad.getText().trim());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Cantidad inválida.");
                return;
            }

            Producto p = Productos.buscarProducto(codProducto);
            if (p == null) {
                JOptionPane.showMessageDialog(this, "Producto no encontrado.");
                return;
            }

            Pedido pedido = new Pedido(codPedido, cliente, p, cantidad);
            Pedidos.crearPedido(pedido);

            JOptionPane.showMessageDialog(this, "Pedido creado exitosamente.");
            limpiarCampos();
        });

        // ===========================================================
        // 🔹 Ver pedidos del cliente
        // ===========================================================
        btnVer.addActionListener(e -> {
            txtSalida.setText("=== MIS PEDIDOS ===\n");
            Pedido[] lista = Pedidos.getPedidos();
            int total = Pedidos.getCantidadPedidos();
            boolean encontrado = false;

            for (int i = 0; i < total; i++) {
                Pedido p = lista[i];
                if (p.getCliente().getCodigo().equals(cliente.getCodigo())) {
                    txtSalida.append(p.toString() + "\n");
                    encontrado = true;
                }
            }

            if (!encontrado) {
                txtSalida.append("No tienes pedidos registrados.");
            }
        });

        // ===========================================================
        // 🔹 Cancelar pedido
        // ===========================================================
        btnCancelar.addActionListener(e -> {
            String cod = txtCodigoPedido.getText().trim();
            if (cod.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ingresa el código del pedido a cancelar.");
                return;
            }

            Pedidos.eliminarPedido(cod);
            JOptionPane.showMessageDialog(this, "Pedido cancelado y lista reordenada.");
            limpiarCampos();
        });
    }

    private void limpiarCampos() {
        txtCodigoPedido.setText("");
        txtCodigoProducto.setText("");
        txtCantidad.setText("");
    }
}

