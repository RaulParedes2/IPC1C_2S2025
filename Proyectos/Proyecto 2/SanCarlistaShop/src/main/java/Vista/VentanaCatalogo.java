/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package Vista;

import Controlador.*;
import Modelo.*;
import javax.swing.*;
import java.awt.*;

public class VentanaCatalogo extends JFrame {

    private Cliente cliente;
    private JTextArea txtSalida;

    public VentanaCatalogo(Cliente c) {
        this.cliente = c;

        setTitle("Catálogo de Productos - " + cliente.getNombre());
        setSize(600, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        txtSalida = new JTextArea();
        txtSalida.setEditable(false);
        JScrollPane scroll = new JScrollPane(txtSalida);

        JButton btnActualizar = new JButton("Actualizar Lista");
        btnActualizar.addActionListener(e -> mostrarProductos());

        add(scroll, BorderLayout.CENTER);
        add(btnActualizar, BorderLayout.SOUTH);

        mostrarProductos();
    }

    private void mostrarProductos() {
        txtSalida.setText("=== CATÁLOGO DE PRODUCTOS ===\n");
        Producto[] lista = Productos.getProductos();
        int total = Productos.getCantidadProductos();

        if (total == 0) {
            txtSalida.append("\nNo hay productos registrados.");
            return;
        }

        for (int i = 0; i < total; i++) {
            Producto p = lista[i];
            if (p != null) {
                int stock = StockUtils.obtenerStock(p.getCodigo());
                double precio = p.getPrecio();

                if (stock > 0) {
                    txtSalida.append(p.getCodigo() + " | " + p.getNombre() +
                            " | Categoría: " + p.getCategoria() +
                            " | Precio: Q" + String.format("%.2f", precio) +
                            " | Stock disponible: " + stock + "\n");
                }
            }
        }
    }
}

