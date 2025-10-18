/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

import Controlador.*;
import Modelo.*;
import javax.swing.*;
import java.awt.*;

public class VentanaClientes extends JFrame {

    private JTextField txtCodigo, txtNombre, txtGenero, txtPassword, txtCumpleaños;
    private JButton btnAgregar, btnListar;
    private JTextArea txtSalida;

    public VentanaClientes() {
        setTitle("Gestión de Clientes");
        setSize(500, 400);
        setLocationRelativeTo(null);

        txtCodigo = new JTextField();
        txtNombre = new JTextField();
        txtGenero = new JTextField();
        txtPassword = new JTextField();
        txtCumpleaños = new JTextField();

        txtCodigo.setBorder(BorderFactory.createTitledBorder("Código"));
        txtNombre.setBorder(BorderFactory.createTitledBorder("Nombre"));
        txtGenero.setBorder(BorderFactory.createTitledBorder("Género"));
        txtPassword.setBorder(BorderFactory.createTitledBorder("Contraseña"));
        txtCumpleaños.setBorder(BorderFactory.createTitledBorder("Cumpleaños"));

        btnAgregar = new JButton("Registrar Cliente");
        btnListar = new JButton("Listar Clientes");
        txtSalida = new JTextArea();
        txtSalida.setEditable(false);

        btnAgregar.addActionListener(e -> {
            Cliente c = new Cliente(
                    txtCodigo.getText(),
                    txtNombre.getText(),
                    txtGenero.getText(),
                    txtPassword.getText(),
                    txtCumpleaños.getText()
            );
            Clientes.crearCliente(c);
        });

        btnListar.addActionListener(e -> {
            txtSalida.setText("");
            Cliente[] lista = Clientes.getClientes();
            for (int i = 0; i < Clientes.getCantidadClientes(); i++) {
                txtSalida.append(lista[i] + "\n");
            }
        });

        JPanel inputPanel = new JPanel(new GridLayout(6, 2));
        inputPanel.add(txtCodigo);
        inputPanel.add(txtNombre);
        inputPanel.add(txtGenero);
        inputPanel.add(txtPassword);
        inputPanel.add(txtCumpleaños);
        inputPanel.add(btnAgregar);
        inputPanel.add(btnListar);

        add(inputPanel, BorderLayout.NORTH);
        add(new JScrollPane(txtSalida), BorderLayout.CENTER);
    }
}

