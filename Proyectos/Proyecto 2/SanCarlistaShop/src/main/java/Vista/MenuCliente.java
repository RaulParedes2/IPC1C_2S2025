/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

import Modelo.*;
import javax.swing.*;
import java.awt.*;

public class MenuCliente extends JFrame {

    private Cliente cliente;

    public MenuCliente(Cliente c) {
        this.cliente = c;

        setTitle("Panel del Cliente - " + cliente.getNombre());
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JButton btnVerProductos = new JButton("Ver Productos");
        JButton btnMisPedidos = new JButton("Mis Pedidos");
        JButton btnHistorial = new JButton("Ver Historial de Compras");
        JButton btnSalir = new JButton("Cerrar Sesión");

        btnVerProductos.addActionListener(e -> new VentanaCatalogo(cliente).setVisible(true));
        btnMisPedidos.addActionListener(e -> new VentanaPedidosCliente(cliente).setVisible(true));
        btnHistorial.addActionListener(e -> {
            new VentanaHistorialCompras(cliente).setVisible(true);});
        btnSalir.addActionListener(e -> {
            dispose();
            new LoginFrame().setVisible(true);});

        setLayout(new GridLayout(4, 1, 10, 10));
        add(btnVerProductos);
        add(btnMisPedidos);
        add(btnHistorial);
        add(btnSalir);
    }
}
