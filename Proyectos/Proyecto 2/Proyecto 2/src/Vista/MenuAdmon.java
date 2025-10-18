/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

import Controlador.*;
import Modelo.*;
import javax.swing.*;
import java.awt.*;

public class MenuAdmon extends JFrame {

    public MenuAdmon() {
        setTitle("Panel del Administrador");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JButton btnProductos = new JButton("Gestionar Productos");
        JButton btnVendedores = new JButton("Gestionar Vendedores");
        JButton btnSalir = new JButton("Cerrar Sesión");

        btnProductos.addActionListener(e -> new VentanaProductos().setVisible(true));
        btnVendedores.addActionListener(e -> new VentanaVendedores().setVisible(true));
        btnSalir.addActionListener(e -> { dispose(); new LoginFrame().setVisible(true); });

        setLayout(new GridLayout(3, 1, 10, 10));
        add(btnProductos);
        add(btnVendedores);
        add(btnSalir);
    }
}

