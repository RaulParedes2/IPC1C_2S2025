/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

import Controlador.*;
import Modelo.*;
import javax.swing.*;
import java.awt.*;

public class VentanaStock extends JFrame {

    private Vendedores vendedor;
    private JTextField txtCodigoProducto, txtCantidad;
    private JTextArea txtSalida;

    public VentanaStock(Vendedores vendedor) {
        this.vendedor = vendedor;

        setTitle("Gestión de Stock - " + vendedor.getNombre());
        setSize(500, 400);
        setLocationRelativeTo(null);

        txtCodigoProducto = new JTextField();
        txtCantidad = new JTextField();
        txtCodigoProducto.setBorder(BorderFactory.createTitledBorder("Código del Producto"));
        txtCantidad.setBorder(BorderFactory.createTitledBorder("Cantidad a Agregar"));

        JButton btnAgregar = new JButton("Agregar Stock");
        JButton btnVer = new JButton("Ver Inventario");

        txtSalida = new JTextArea();
        txtSalida.setEditable(false);
        JScrollPane scroll = new JScrollPane(txtSalida);

        btnAgregar.addActionListener(e -> {
            String codigo = txtCodigoProducto.getText();
            int cantidad = Integer.parseInt(txtCantidad.getText());
            Producto p = Productos.buscarProducto(codigo);
            if (p != null) {
                p.agregarStock(cantidad);
                vendedor.confirmarVenta(); // opcional, si se quiere contar ingreso como venta
                JOptionPane.showMessageDialog(this, "Stock agregado correctamente.");
            } else {
                JOptionPane.showMessageDialog(this, "Producto no encontrado.");
            }
        });

        btnVer.addActionListener(e -> {
            txtSalida.setText("");
            Producto[] lista = Productos.getProductos();
            for (int i = 0; i < Productos.getCantidadProductos(); i++) {
                txtSalida.append(lista[i].toString() + "\n");
            }
        });

        JPanel topPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        topPanel.add(txtCodigoProducto);
        topPanel.add(txtCantidad);
        topPanel.add(btnAgregar);
        topPanel.add(btnVer);

        add(topPanel, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
    }
}

