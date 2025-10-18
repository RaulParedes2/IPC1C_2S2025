/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

import Modelo.*;
import javax.swing.*;
import java.awt.*;

public class MenuVendedor extends JFrame {

    private Vendedores vendedor;

    public MenuVendedor(Vendedores v) {
        this.vendedor = v;
        setTitle("Panel del Vendedor - " + v.getNombre());
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JButton btnClientes = new JButton("Gestionar Clientes");
        JButton btnStock = new JButton("Gestionar Stock");
        JButton btnPedidos = new JButton("Gestionar Pedidos");
        JButton btnSalir = new JButton("Cerrar Sesión");

        btnClientes.addActionListener(e -> new VentanaClientes().setVisible(true));
        btnSalir.addActionListener(e -> { dispose(); new LoginFrame().setVisible(true); });

        setLayout(new GridLayout(4, 1, 10, 10));
        add(btnClientes);
        add(btnStock);
        add(btnPedidos);
        add(btnSalir);
    }
}

