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

        //========================================================
        //=========Botones========================================
        //========================================================
        JButton btnModificar = new JButton("Modificar Cantidad");
        JButton btnCrear = new JButton("Crear Pedido");
        JButton btnVer = new JButton("Ver Mis Pedidos");
        JButton btnCancelar = new JButton("Cancelar Pedido");

        txtSalida = new JTextArea();
        txtSalida.setEditable(false);
        JScrollPane scroll = new JScrollPane(txtSalida);

        //========================================================
        //==========Panel=========================================
        //========================================================
        JPanel topPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        topPanel.add(txtCodigoPedido);
        topPanel.add(txtCodigoProducto);
        topPanel.add(txtCantidad);
        topPanel.add(btnModificar);
        topPanel.add(btnCrear);
        topPanel.add(btnVer);
        topPanel.add(btnCancelar);

        add(topPanel, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);

        // ===========================================================
        // Crear pedido
        // ===========================================================
        btnCrear.addActionListener(e -> {
            String codPedido = txtCodigoPedido.getText().trim();
            String codProducto = txtCodigoProducto.getText().trim();
            String cantidadTxt = txtCantidad.getText().trim();
            int cantidad;

            if (codPedido.isEmpty() || codProducto.isEmpty() || cantidadTxt.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, llena todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (Pedidos.existePedido(codPedido)) {
                JOptionPane.showMessageDialog(this,
                        "Ya existe un pedido con el código " + codPedido + ".",
                        "Código duplicado", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                cantidad = Integer.parseInt(txtCantidad.getText().trim());
                if (cantidad <= 0) {
                    JOptionPane.showMessageDialog(this, "La cantidad debe ser mayor que cero.");
                    return;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Cantidad inválida.");
                return;
            }

            Producto p = Productos.buscarProducto(codProducto);
            if (p == null) {
                JOptionPane.showMessageDialog(this, "Producto no encontrado.");
                return;
            }

            if (!StockUtils.hayStock(codProducto, cantidad)) {
                JOptionPane.showMessageDialog(this,
                        "No hay suficiente stock disponible para el producto " + codProducto,
                        "Stock insuficiente", JOptionPane.WARNING_MESSAGE);
                Controlador.Bitacora.registrar(
                        "CLIENTE: " + cliente.getNombre(),
                        "CODIGO: " + cliente.getCodigo(),
                        "CREAR PEDIDO",
                        "FALLIDO",
                        "PEDIDO CREADO: " + txtCodigoPedido.getText() + "|" + txtCantidad.getText()
                );
                return;
            }

            Pedido pedido = new Pedido(codPedido, cliente, p, cantidad);
            Pedidos.crearPedido(pedido);

            JOptionPane.showMessageDialog(this,
                    "Pedido creado exitosamente.\n"
                    + "Producto: " + p.getNombre() + "\n"
                    + "Precio unitario: Q" + String.format("%.2f", p.getPrecio()) + "\n"
                    + "Total: Q" + String.format("%.2f", (p.getPrecio() * cantidad)));

            Controlador.Bitacora.registrar(
                    "CLIENTE: " + cliente.getNombre(),
                    "CODIGO: " + cliente.getCodigo(),
                    "CREAR PEDIDO",
                    "EXITOSA",
                    "PEDIDO CREADO: " + txtCodigoPedido.getText() + "|" + txtCantidad.getText()
            );

            limpiarCampos();
        });

        // ===========================================================
        // Modificar cantidad
        // ===========================================================
        btnModificar.addActionListener(e -> {
            String cod = txtCodigoPedido.getText().trim();
            String nuevaCantTxt = txtCantidad.getText().trim();

            if (cod.isEmpty() || nuevaCantTxt.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Debes ingresar el código del pedido y la nueva cantidad.");
                return;
            }

            int nuevaCantidad;
            try {
                nuevaCantidad = Integer.parseInt(nuevaCantTxt);
                if (nuevaCantidad <= 0) {
                    JOptionPane.showMessageDialog(this, "La cantidad debe ser mayor que cero.");
                    return;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Cantidad inválida.");

                return;
            }

            Pedido[] lista = Pedidos.getPedidos();
            int total = Pedidos.getCantidadPedidos();
            boolean modificado = false;

            for (int i = 0; i < total; i++) {
                Pedido p = lista[i];
                if (p != null && p.getCodigo().equalsIgnoreCase(cod)
                        && p.getCliente().getCodigo().equals(cliente.getCodigo())) {

                    if (!StockUtils.hayStock(p.getProducto().getCodigo(), nuevaCantidad)) {
                        JOptionPane.showMessageDialog(this,
                                "No hay suficiente stock disponible para esta cantidad.",
                                "Stock insuficiente", JOptionPane.WARNING_MESSAGE);
                        Controlador.Bitacora.registrar(
                                "CLIENTE: " + cliente.getNombre(),
                                "CODIGO: " + cliente.getCodigo(),
                                "MODIFICAR CANTIDAD",
                                "FALLIDO",
                                "CANTIDAD MODIFICADA: " + txtCodigoPedido.getText() + "|" + txtCantidad.getText()
                        );
                        return;
                    }

                    p.setCantidad(nuevaCantidad);
                    JOptionPane.showMessageDialog(this, "Cantidad modificada correctamente.\n"
                            + "Nuevo total: Q" + String.format("%.2f", p.getProducto().getPrecio() * nuevaCantidad));
                    modificado = true;
                    Controlador.Bitacora.registrar(
                                "CLIENTE: " + cliente.getNombre(),
                                "CODIGO: " + cliente.getCodigo(),
                                "MODIFICAR CANTIDAD",
                                "EXITOSA",
                                "CANTIDAD MODIFICADA: " + txtCodigoPedido.getText() + "|" + txtCantidad.getText()
                        );
                    break;
                }
            }

            if (!modificado) {
                JOptionPane.showMessageDialog(this, "Pedido no encontrado o no pertenece al cliente actual.");
            }

            limpiarCampos();
        });

        // ===========================================================
        // Ver pedidos del cliente (con totales)
        // ===========================================================
        btnVer.addActionListener(e -> {
            txtSalida.setText("=== MIS PEDIDOS ===\n");
            Pedido[] lista = Pedidos.getPedidos();
            int total = Pedidos.getCantidadPedidos();
            boolean encontrado = false;
            double totalGeneral = 0;

            for (int i = 0; i < total; i++) {
                Pedido p = lista[i];
                if (p != null && p.getCliente().getCodigo().equals(cliente.getCodigo())) {
                    double totalPedido = p.getProducto().getPrecio() * p.getCantidad();
                    totalGeneral += totalPedido;
                    txtSalida.append(p.getCodigo() + " | "
                            + p.getProducto().getNombre()
                            + " | Cantidad: " + p.getCantidad()
                            + " | Precio unitario: Q" + String.format("%.2f", p.getProducto().getPrecio())
                            + " | Total: Q" + String.format("%.2f", totalPedido) + "\n");
                    encontrado = true;
                }
            }

            if (!encontrado) {
                txtSalida.append("No tienes pedidos registrados.");
            } else {
                txtSalida.append("\n=== TOTAL GENERAL: Q" + String.format("%.2f", totalGeneral) + " ===");
            }
        });

        // ===========================================================
        // Cancelar pedido
        // ===========================================================
        btnCancelar.addActionListener(e -> {
            String cod = txtCodigoPedido.getText().trim();
            if (cod.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ingresa el código del pedido a cancelar.");
                Controlador.Bitacora.registrar(
                                "CLIENTE: " + cliente.getNombre(),
                                "CODIGO: " + cliente.getCodigo(),
                                "CANCELAR PEDIDO",
                                "FALLIDO",
                                "PEDIDO CANCELADO: " + txtCodigoPedido.getText() + "|" + txtCantidad.getText()
                        );
                return;
            }

            Pedidos.eliminarPedido(cod);
            JOptionPane.showMessageDialog(this, "Pedido cancelado correctamente.");
            Controlador.Bitacora.registrar(
                                "CLIENTE: " + cliente.getNombre(),
                                "CODIGO: " + cliente.getCodigo(),
                                "CANCELAR PEDIDO",
                                "EXITOSA",
                                "PEDIDO CANCELADO: " + txtCodigoPedido.getText() + "|" + txtCantidad.getText()
                        );
            limpiarCampos();
        });
    }

    private void limpiarCampos() {
        txtCodigoPedido.setText("");
        txtCodigoProducto.setText("");
        txtCantidad.setText("");
    }
}
