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
    private final JButton btnAbrir = new JButton("Abrir ArenaUSAC");
    private final JButton btnSalir = new JButton("Salir");

    public Inicio() {
        super("ArenaUSAC");

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        JLabel titulo = new JLabel("ArenaUSAC", SwingConstants.CENTER);
        titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 18f));

        JPanel centro = new JPanel(new GridLayout(2, 1, 10, 10));
        centro.add(btnAbrir);
        centro.add(btnSalir);

        JPanel root = new JPanel(new BorderLayout(10, 10));
        root.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        root.add(titulo, BorderLayout.NORTH);
        root.add(new JLabel("Simulación de batallas con personajes.", SwingConstants.CENTER), BorderLayout.CENTER);
        root.add(centro, BorderLayout.SOUTH);

        setContentPane(root);
        pack();
        setLocationRelativeTo(null);

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
