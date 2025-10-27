/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

import Controlador.Vendedor;
import Modelo.*;
import Controlador.Clientes;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.text.SimpleDateFormat;

public class VentanaClientes extends JFrame {

    private JComboBox<String> comboGenero;
    private JFormattedTextField txtCumpleaños;
    private JTextField txtCodigo, txtNombre, txtPassword, txtGenero;
    private JButton btnAgregar, btnListar, btnGuardarCSV, btnCargarCSV, btnEliminar, btnActualizar;
    private JTextArea txtSalida;
    private Vendedores vendedor;

    public VentanaClientes(Vendedores vendedor) {
        setTitle("Gestión de Clientes");
        setSize(700, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        this.vendedor = vendedor;

        // ====== Campos de texto ======
        txtCodigo = new JTextField();
        txtNombre = new JTextField();
        txtGenero = new JTextField();
        txtPassword = new JTextField();
        txtCumpleaños = new JFormattedTextField(new SimpleDateFormat("dd/MM/yyyy"));

        comboGenero = new JComboBox<>(new String[]{"M", "F"});
        comboGenero.setBorder(BorderFactory.createTitledBorder("Género"));

        txtCodigo.setBorder(BorderFactory.createTitledBorder("Código"));
        txtNombre.setBorder(BorderFactory.createTitledBorder("Nombre"));
        comboGenero.setBorder(BorderFactory.createTitledBorder("Género"));
        txtPassword.setBorder(BorderFactory.createTitledBorder("Contraseña"));
        txtCumpleaños.setBorder(BorderFactory.createTitledBorder("Cumpleaños (dd/MM/yyyy)"));

        //===========Botones===============
        btnGuardarCSV = new JButton("Guardar Clientes");
        btnCargarCSV = new JButton("Cargar Clientes");
        btnEliminar = new JButton("Eliminar Cliente");
        btnActualizar = new JButton("Actualizar Cliente");
        btnAgregar = new JButton("Registrar Cliente");
        btnListar = new JButton("Listar Clientes");

        // ====== Área de salida ======
        txtSalida = new JTextArea();
        txtSalida.setEditable(false);
        JScrollPane scroll = new JScrollPane(txtSalida);

        JPanel panelCampos = new JPanel(new GridLayout(4, 2, 5, 5));
        panelCampos.add(txtCodigo);
        panelCampos.add(txtNombre);
        panelCampos.add(comboGenero);
        panelCampos.add(txtCumpleaños);
        panelCampos.add(txtPassword);

        JPanel panelBotones = new JPanel(new GridLayout(2, 4, 5, 5));
        panelBotones.add(btnAgregar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnListar);
        panelBotones.add(btnCargarCSV);
        panelBotones.add(btnGuardarCSV);

        add(panelCampos, BorderLayout.NORTH);
        add(panelBotones, BorderLayout.CENTER);
        add(scroll, BorderLayout.SOUTH);

        // ====== Acciones de los botones ======
        btnAgregar.addActionListener(e -> agregarCliente());
        btnActualizar.addActionListener(e -> actualizarCliente());
        btnEliminar.addActionListener(e -> eliminarCliente());
        btnListar.addActionListener(e -> listarCliente());
        btnCargarCSV.addActionListener(e -> cargarCSV());
        btnGuardarCSV.addActionListener(e -> guardarCSV());
    }
    //=====Agregar Cliente======

    private void agregarCliente() {
        String codigo = txtCodigo.getText().trim();
        String nombre = txtNombre.getText().trim();
        String genero = comboGenero.getSelectedItem().toString();
        String fechaTexto = txtCumpleaños.getText().trim();
        String password = txtPassword.getText().trim();

        if (fechaTexto.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El campo de cumpleaños está vacío.");
            return;
        }

        if (codigo.isEmpty() || nombre.isEmpty() || fechaTexto.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, llena todos los campos.");
            return;
        }
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate cumpleaños;
        try {
            cumpleaños = LocalDate.parse(fechaTexto, formatoFecha);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Formato de fecha inválido. Usa dd/MM/yyyy.\nEjemplo: 23/10/2025");
            return;
        }

        if (Clientes.buscarCliente(codigo) != null) {
            JOptionPane.showMessageDialog(this, "Código de cliente duplicado.");
            return;
        }

        Cliente c = new Cliente(codigo, nombre, genero, password, cumpleaños);
        Clientes.crearCliente(c);
        JOptionPane.showMessageDialog(this, "Cliente agregado correctamente.");

        Controlador.Bitacora.registrar(
                "VEMDEDOR: " +vendedor.getNombre(),
                "CODIGO: " + vendedor.getCodigo(),
                "AGREGAR_CLIENTE",
                "EXITOSA",
                "CLIENTE AGERGADO: " + txtCodigo.getText()+"|"+txtNombre.getText()
        );
        limpiarCampos();
    }

    //================================================
    //===== Actualizacion de Datos o modificacion======
    //================================================
    private void actualizarCliente() {
        String codigo = txtCodigo.getText().trim();
        String nuevoNombre = txtNombre.getText().trim();
        String nuevaPassword = txtPassword.getText().trim();
        String fechaTexto = txtCumpleaños.getText().trim();

        if (codigo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingresa el código del cliente a actualizar.");
            return;
        }

        if (codigo.isEmpty() || nuevoNombre.isEmpty() || fechaTexto.isEmpty() || nuevaPassword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, llena todos los campos.");
            return;
        }

        if (fechaTexto.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El campo de cumpleaños está vacío.");
            return;
        }

        Clientes.actualizarCliente(codigo, nuevoNombre, nuevaPassword);
        JOptionPane.showMessageDialog(this, "Datos del cliente actualizados.");
        
        Controlador.Bitacora.registrar(
                "VEMDEDOR: " +vendedor.getNombre(),
                "CODIGO: " + vendedor.getCodigo(),
                "MODIFICAR_CLIENTE",
                "EXITOSA",
                "CLIENTE MODIFICADO: " + txtCodigo.getText()+"|"+txtNombre.getText()
        );
        limpiarCampos();

    }
    //=======================================
    //==== Eliminar vendedor ========
    //========================================

    private void eliminarCliente() {
        String codigo = txtCodigo.getText().trim();

        if (codigo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingresa el código del Cliente a eliminar.");
            return;
        }

        // Mostrar cuadro de confirmación
        int confirmacion = JOptionPane.showConfirmDialog(
                this,
                "¿Estás seguro de eliminar al Cliente con código: " + codigo + "?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        // Si el usuario presiona "Sí"
        if (confirmacion == JOptionPane.YES_OPTION) {
            Clientes.eliminarCliente(codigo);
            JOptionPane.showMessageDialog(this, "Cliente eliminado correctamente (lista reordenada).");
            
            Controlador.Bitacora.registrar(
                "VEMDEDOR: " +vendedor.getNombre(),
                "CODIGO: " + vendedor.getCodigo(),
                "AGREGAR_ELIMINAR",
                "EXITOSA",
                "CLIENTE ELIMINADO: " + txtCodigo.getText()+"|"+txtNombre.getText()
        );
            limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(this, "Operación cancelada.");
            Controlador.Bitacora.registrar(
                "VEMDEDOR: " +vendedor.getNombre(),
                "CODIGO: " + vendedor.getCodigo(),
                "AGREGAR_ELIMINAR",
                "FALLIDO",
                "CLIENTE ELIMINADO: " + txtCodigo.getText()+"|"+txtNombre.getText()
        );
        }

    }

    //=============================================
    //======Listado de clientes registrados======
    //============================================
    private void listarCliente() {
        txtSalida.setText("");
        Cliente[] lista = Clientes.getClientes();
        int total = Clientes.getCantidadClientes();

        if (total == 0) {
            txtSalida.setText("No hay vendedores registrados.");
            return;
        }
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        txtSalida.append("=== LISTA DE VENDEDORES ===\n");
        for (int i = 0; i < total; i++) {
            Cliente c = lista[i];
            String cumpleañosFormateado = c.getCumpleaños().format(formatoFecha);
            txtSalida.append(c.getCodigo() + " | " + c.getNombre()
                    + " | Género: " + c.getgenero()
                    + " | Cumpleaños: " + cumpleañosFormateado + "\n");
        }
    }

    //=====================================
    //==== Boton de cargar desde CSV ======
    //=====================================
    private void cargarCSV() {
        File archivo = new File("src/main/resources/data/clientes.csv");
        int cargados = 0, errores = 0;
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length != 5) {
                    errores++;
                    continue;
                }

                String codigo = partes[0].trim();
                String nombre = partes[1].trim();
                String genero = partes[2].trim();
                String password = partes[3].trim();
                String cumpleañosTexto = partes[4].trim();

                if (Clientes.buscarCliente(codigo) != null) {
                    errores++;
                    continue;
                }

                try {
                    LocalDate cumpleaños = LocalDate.parse(cumpleañosTexto, formatoFecha);
                    Cliente c = new Cliente(codigo, nombre, genero, password, cumpleaños);
                    Clientes.crearCliente(c);
                    cargados++;
                } catch (Exception ex) {
                    errores++;
                }
            }

            JOptionPane.showMessageDialog(this,
                    "Carga completada:\n " + cargados + " vendedores cargados\n " + errores + " con error o duplicado");
            Controlador.Bitacora.registrar(
                "VEMDEDOR: " +vendedor.getNombre(),
                "CODIGO: " + vendedor.getCodigo(),
                "CARGAR",
                "EXITOSA",
                ""
        );

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error al leer archivo: " + ex.getMessage());
        }
    }

    //====== Gurdar los datos con CSV========
    private void guardarCSV() {
        File archivo = new File("src/main/resources/data/clientes.csv");
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        try (PrintWriter pw = new PrintWriter(new FileWriter(archivo))) {
            Cliente[] lista = Clientes.getClientes();
            int total = Clientes.getCantidadClientes();

            for (int i = 0; i < total; i++) {
                Cliente c = lista[i];
                pw.println(c.getCodigo() + ","
                        + c.getNombre() + ","
                        + c.getgenero() + ","
                        + c.getPassword() + ","
                        + c.getCumpleaños().format(formatoFecha));
            }

            JOptionPane.showMessageDialog(this, "Archivo CSV guardado correctamente.");
            Controlador.Bitacora.registrar(
                "VEMDEDOR: " +vendedor.getNombre(),
                "CODIGO: " + vendedor.getCodigo(),
                "GUARDAR",
                "EXITOSA",
                ""
        );

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error al guardar: " + ex.getMessage());
        }
    }

    private void limpiarCampos() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtGenero.setText("");
        txtPassword.setText("");
        txtCumpleaños.setText("");
    }
}
