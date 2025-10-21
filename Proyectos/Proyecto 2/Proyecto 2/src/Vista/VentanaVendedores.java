/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

import Controlador.*;
import Modelo.*;
import javax.swing.*;
import java.awt.*;
import java.io.*;

public class VentanaVendedores extends JFrame {

    private JTextField txtCodigo, txtNombre, txtGenero, txtPassword;
    private JComboBox<String> comboGenero;
    private JButton btnAgregar, btnActualizar, btnEliminar, btnListar, btnTop, btnCargarCSV, btnGuardarCSV;
    private JTextArea txtSalida;

    public VentanaVendedores() {
        setTitle("Gestión de Vendedores");
        setSize(700, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // ====== Campos de texto ======
        txtCodigo = new JTextField();
        txtNombre = new JTextField();
        txtGenero = new JTextField();
        txtPassword = new JTextField();

        txtCodigo.setBorder(BorderFactory.createTitledBorder("Código"));
        txtNombre.setBorder(BorderFactory.createTitledBorder("Nombre"));
        txtPassword.setBorder(BorderFactory.createTitledBorder("Contraseña"));
        
        comboGenero = new JComboBox<>(new String[]{"M", "F"});
        comboGenero.setBorder(BorderFactory.createTitledBorder("Género"));

        // ====== Botones ======
        btnAgregar = new JButton("Agregar");
        btnActualizar = new JButton("Actualizar");
        btnEliminar = new JButton("Eliminar");
        btnListar = new JButton("Listar");
        btnTop = new JButton("Top 3 Vendedores");
        btnCargarCSV = new JButton("Cargar CSV");
        btnGuardarCSV = new JButton("Guardar CSV");

        // ====== Área de salida ======
        txtSalida = new JTextArea();
        txtSalida.setEditable(false);
        JScrollPane scroll = new JScrollPane(txtSalida);
        
        JPanel panelCampos = new JPanel(new GridLayout(4, 2, 5, 5));
        panelCampos.add(txtCodigo);
        panelCampos.add(txtNombre);
        panelCampos.add(comboGenero);
        panelCampos.add(txtPassword);

        JPanel panelBotones = new JPanel(new GridLayout(2, 4, 5, 5));
        panelBotones.add(btnAgregar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnListar);
        panelBotones.add(btnTop);
        panelBotones.add(btnCargarCSV);
        panelBotones.add(btnGuardarCSV);

        add(panelCampos, BorderLayout.NORTH);
        add(panelBotones, BorderLayout.CENTER);
        add(scroll, BorderLayout.SOUTH);

        // ====== Panel superior ======
        /*JPanel topPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        topPanel.add(txtCodigo);
        topPanel.add(txtNombre);
        topPanel.add(txtGenero);
        topPanel.add(txtPassword);
        topPanel.add(btnAgregar);
        topPanel.add(btnActualizar);
        topPanel.add(btnEliminar);
        topPanel.add(btnListar);

        add(topPanel, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);*/

        // ====== Acciones de los botones ======
        btnAgregar.addActionListener(e -> agregarVendedor());
        btnActualizar.addActionListener(e -> actualizarVendedor());
        btnEliminar.addActionListener(e -> eliminarVendedor());
        btnListar.addActionListener(e -> listarVendedores());
        btnTop.addActionListener(e -> mostrarTop3());
        btnCargarCSV.addActionListener(e -> cargarCSV());
        btnGuardarCSV.addActionListener(e -> guardarCSV());
    }
        //=====Agregar Vendedor======
        private void agregarVendedor() {
        String codigo = txtCodigo.getText().trim();
        String nombre = txtNombre.getText().trim();
        String genero = comboGenero.getSelectedItem().toString();
        String password = txtPassword.getText().trim();

        if (codigo.isEmpty() || nombre.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, llena todos los campos.");
            return;
        }
        
        if (Vendedor.buscarVendedor(codigo) != null) {
            JOptionPane.showMessageDialog(this, "Código de vendedor duplicado.");
            return;
        }
        

        Vendedores v = new Vendedores(codigo, nombre, genero, password);
        Vendedor.crearVendedor(v);
        JOptionPane.showMessageDialog(this, "Vendedor agregado correctamente.");
        limpiarCampos();
    }
        //===== Actualizacion de Datos o modificacion======
    private void actualizarVendedor() {
        String codigo = txtCodigo.getText().trim();
        String nuevoNombre = txtNombre.getText().trim();
        String nuevaPassword = txtPassword.getText().trim();

        if (codigo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingresa el código del vendedor a actualizar.");
            return;
        }

        Vendedor.actualizarVendedor(codigo, nuevoNombre, nuevaPassword);
        JOptionPane.showMessageDialog(this, "Datos del vendedor actualizados.");
        limpiarCampos();
    }
        //==== Eliminar vendedor ========
    private void eliminarVendedor() {
        String codigo = txtCodigo.getText().trim();

        if (codigo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingresa el código del vendedor a eliminar.");
            return;
        }

        Vendedor.eliminarVendedor(codigo);
        JOptionPane.showMessageDialog(this, "Vendedor eliminado (lista reordenada).");
        limpiarCampos();
    }
    //======Listado de Vendedores registrados======
    private void listarVendedores() {
        txtSalida.setText("");
        Vendedores[] lista =Vendedor.getVendedores();
        int total = Vendedor.getCantidadVendedores();

        if (total == 0) {
            txtSalida.setText("No hay vendedores registrados.");
            return;
        }
        

        txtSalida.append("=== LISTA DE VENDEDORES ===\n");
        for (int i = 0; i < total; i++) {
            Vendedores v = lista[i];
            txtSalida.append(v.getCodigo() + " | " + v.getNombre() +
                             " | Género: " + v.getgenero() +
                             " | Ventas: " +  v.getVentasCofirmadas() + "\n");
        }
    }
    
    //==== Top de los vendedores=====
    
    private void mostrarTop3() {
        txtSalida.setText("=== TOP 3 VENDEDORES ===\n");

        Vendedores[] lista = Vendedor.getVendedores();
        int total = Vendedor.getCantidadVendedores();

        if (total == 0) {
            txtSalida.append("No hay vendedores registrados.\n");
            return;
        }

        // Copia temporal
        Vendedores[] copia = new Vendedores[total];
        for (int i = 0; i < total; i++) copia[i] = lista[i];

        // Ordenamiento burbuja descendente por ventas
        for (int i = 0; i < total - 1; i++) {
            for (int j = 0; j < total - 1 - i; j++) {
                if (copia[j].getVentasCofirmadas() < copia[j + 1].getVentasCofirmadas()) {
                    Vendedores temp = copia[j];
                    copia[j] = copia[j + 1];
                    copia[j + 1] = temp;
                }
            }
        }

        int limite = Math.min(3, total);
        for (int i = 0; i < limite; i++) {
            txtSalida.append((i + 1) + ". " + copia[i].getNombre() +
                             " - Ventas: " + copia[i].getVentasCofirmadas() + "\n");
        }
    }
    
    //==== Boton de cargar desde CSV ======
    
    private void cargarCSV() {
        File archivo = new File("src/data/vendedores.ser");
    int cargados = 0, errores = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length != 4) {
                    errores++;
                    continue;
                }

                String codigo = partes[0].trim();
                String nombre = partes[1].trim();
                String genero = partes[2].trim();
                String password = partes[3].trim();

                if (Vendedor.buscarVendedor(codigo) != null) {
                    errores++;
                    continue;
                }

                Vendedores v = new Vendedores(codigo, nombre, genero, password);
                Vendedor.crearVendedor(v);
                cargados++;
            }

            JOptionPane.showMessageDialog(this,
                "Carga completada:\n " + cargados + " vendedores cargados\n " + errores + " con error o duplicado");

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error al leer archivo: " + ex.getMessage());
        }
    }
    
    //====== Gurdar los datos con CSV========
     private void guardarCSV() {
        File archivo = new File("src/data/vendedores.ser");

        try (PrintWriter pw = new PrintWriter(new FileWriter(archivo))) {
            Vendedores[] lista = Vendedor.getVendedores();
            int total = Vendedor.getCantidadVendedores();

            for (int i = 0; i < total; i++) {
                Vendedores v = lista[i];
                pw.println(v.getCodigo() + "," + v.getNombre() + "," +
                           v.getgenero() + "," + v.getPassword());
            }

            JOptionPane.showMessageDialog(this, "Archivo CSV guardado correctamente.");

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error al guardar: " + ex.getMessage());
        }
    }



    private void limpiarCampos() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtGenero.setText("");
        txtPassword.setText("");
    }
}
