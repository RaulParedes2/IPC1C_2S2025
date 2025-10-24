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

    private Vendedores vendedor; // tu modelo individual
    private JTextField txtCodigoProducto, txtCantidad;
    private JTextArea txtSalida;

    public VentanaStock(Vendedores vendedor) {
        this.vendedor = vendedor;

        setTitle("Gestión de Stock - " + vendedor.getNombre());
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        txtCodigoProducto = new JTextField();
        txtCantidad = new JTextField();
        txtCodigoProducto.setBorder(BorderFactory.createTitledBorder("Código del Producto"));
        txtCantidad.setBorder(BorderFactory.createTitledBorder("Cantidad a Agregar"));

        JButton btnAgregar = new JButton("Agregar Stock");
        JButton btnVer = new JButton("Ver Inventario");

        txtSalida = new JTextArea();
        txtSalida.setEditable(false);
        JScrollPane scroll = new JScrollPane(txtSalida);

        // --- Acción: Agregar stock ---
        btnAgregar.addActionListener(e -> {
            String codigo = txtCodigoProducto.getText().trim();
            String cantidadTexto = txtCantidad.getText().trim();

            if (codigo.isEmpty() || cantidadTexto.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, llena ambos campos.");
                return;
            }

            try {
                int cantidad = Integer.parseInt(cantidadTexto);
                if (cantidad <= 0) {
                    JOptionPane.showMessageDialog(this, "La cantidad debe ser mayor que 0.");
                    return;
                }

                Producto p = Vendedor.buscarProducto(codigo); // tu controlador
                if (p != null) {
                    p.agregarStock(cantidad);
                    vendedor.confirmarVenta(); // si quieres registrar venta
                    JOptionPane.showMessageDialog(this, "Stock agregado correctamente.");
                } else {
                    JOptionPane.showMessageDialog(this, "Producto no encontrado.");
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Cantidad inválida. Ingresa un número entero.");
            }
        });

        // --- Acción: Ver inventario ---
        btnVer.addActionListener(e -> {
            txtSalida.setText("");
            Producto[] lista = Vendedor.getProductos(); // del controlador
            for (int i = 0; i < Vendedor.getCantidadProductos(); i++) {
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
