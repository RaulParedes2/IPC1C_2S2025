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
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JButton btnReportes = new JButton("Ver Reportes");
        JButton btnProductos = new JButton("Gestionar Productos");
        JButton btnVendedores = new JButton("Gestionar Vendedores");
        JButton btnDatosEstudiante = new JButton("Datos del Estudiante");
        JButton btnSalir = new JButton("Cerrar Sesión");

        btnReportes.addActionListener(e -> new VentanaReportes().setVisible(true));
        btnProductos.addActionListener(e -> new VentanaProductos().setVisible(true));
        btnVendedores.addActionListener(e -> new VentanaVendedores().setVisible(true));

        // ✅ Nuevo botón para mostrar tus datos
        btnDatosEstudiante.addActionListener(e -> {
            JOptionPane.showMessageDialog(
                this,
                "Nombre: Raul Paredes\nCarnet: 202400554\nIPC1 \"C\"",
                "Datos del Estudiante",
                JOptionPane.INFORMATION_MESSAGE
            );
        });

        btnSalir.addActionListener(e -> {
            dispose();
            new LoginFrame().setVisible(true);
        });

        setLayout(new GridLayout(5, 1, 10, 10));
        add(btnReportes);
        add(btnProductos);
        add(btnVendedores);
        add(btnDatosEstudiante); // ✅ Se agrega el nuevo botón al menú
        add(btnSalir);
    }
}


