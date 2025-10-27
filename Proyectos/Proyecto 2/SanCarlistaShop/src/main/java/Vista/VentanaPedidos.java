/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

import Controlador.*;
import Modelo.*;
import javax.swing.*;
import java.awt.*;

public class VentanaPedidos extends JFrame {

    private Vendedores vendedor;
    private JTextField txtCodigoPedido;
    private JTextArea txtSalida;

    public VentanaPedidos(Vendedores vendedor) {
        this.vendedor = vendedor;
        setTitle("Pedidos Pendientes - " + vendedor.getNombre());
        setSize(550, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // ====== Componentes ======
        txtCodigoPedido = new JTextField();
        txtCodigoPedido.setBorder(BorderFactory.createTitledBorder("Código del Pedido"));

        JButton btnConfirmar = new JButton("Confirmar Pedido");
        JButton btnListar = new JButton("Actualizar Lista");

        txtSalida = new JTextArea();
        txtSalida.setEditable(false);
        JScrollPane scroll = new JScrollPane(txtSalida);

        // ====== Eventos ======
        btnConfirmar.addActionListener(e -> confirmarPedido());
        btnListar.addActionListener(e -> listarPedidos());

        // ====== Layout ======
        JPanel panelSuperior = new JPanel(new GridLayout(2, 1, 5, 5));
        panelSuperior.add(txtCodigoPedido);
        panelSuperior.add(btnConfirmar);

        JPanel panelBotones = new JPanel();
        panelBotones.add(btnListar);

        add(panelSuperior, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        listarPedidos(); // Mostrar al iniciar
    }

    // ===========================================================
    // Listar pedidos pendientes
    // ===========================================================
    private void listarPedidos() {
        txtSalida.setText("=== PEDIDOS PENDIENTES ===\n");
        Pedido[] lista = Pedidos.getPedidos();
        for (int i = 0; i < Pedidos.getCantidadPedidos(); i++) {
            Pedido p = lista[i];
            if (p != null && !p.isConfirmado()) {
                txtSalida.append(p.toString() + "\n");  
            }
        }
    }

    // ===========================================================
    // Confirmar pedido
    // ===========================================================
    private void confirmarPedido() {
        String codigo = txtCodigoPedido.getText().trim();
        if (codigo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingresa el código del pedido.");
            return;
        }

        Pedidos.confirmarPedido(codigo, vendedor);
        Controlador.Bitacora.registrar(
                "VEMDEDOR: " +vendedor.getNombre(),
                "CODIGO: " + vendedor.getCodigo(),
                "CONFIRMAR VENTA",
                "EXITOSA",
                ""
        );

        listarPedidos(); // Actualiza la lista
        limpiarCampos();
    }
    
    private void limpiarCampos() {
        txtCodigoPedido.setText("");
       
        
    }
}

//borrar celdas