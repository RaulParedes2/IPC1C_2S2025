/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Interfaz;

import javax.swing.*;
import java.awt.*;
/**
 *
 * @author Daniel Predes
 */
public class Inicio extends JFrame {
    //Menu o pantalla de inicio
    private final JButton btnAbrir = new JButton("Abrir Menu");
    private final JButton btnSalir = new JButton("Salir");

    //Nombre de la pantalla principal
    public Inicio() {
        super("Practica 2");

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        JLabel titulo = new JLabel("Practica 2", SwingConstants.CENTER);
        titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 18f));

        JPanel centro = new JPanel(new GridLayout(2, 1, 10, 10));
        centro.add(btnAbrir);//Boton del Menu
        centro.add(btnSalir);//Boton de salir

        JPanel root = new JPanel(new BorderLayout(10, 10));
        root.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        root.add(titulo, BorderLayout.NORTH);
        root.add(new JLabel("Simulación de batallas con personajes.", SwingConstants.CENTER), BorderLayout.CENTER);//Titulo
        root.add(centro, BorderLayout.SOUTH);

        setContentPane(root);
        pack();
        setLocationRelativeTo(null);
        
        //Botones 
        btnAbrir.addActionListener(e -> {
            new Interfaz().setVisible(true);
            dispose();
        });

        btnSalir.addActionListener(e -> System.exit(0));

        getRootPane().setDefaultButton(btnAbrir);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Inicio().setVisible(true));
    }
    
}
