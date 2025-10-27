/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

import Controlador.*;
import Modelo.*;
import javax.swing.*;
import javax.swing.JOptionPane;

import java.awt.*;
import java.io.*;

import org.jfree.chart.*;
import org.jfree.data.category.DefaultCategoryDataset;

public class VentanaVendedores extends JFrame {

    private JPanel jPanelGrafica;
    private JTextField txtCodigo, txtNombre, txtGenero, txtPassword;
    private JComboBox<String> comboGenero;
    private JButton btnAgregar, btnActualizar, btnEliminar, btnListar, btnTop, btnCargarCSV, btnGuardarCSV;
    private JTextArea txtSalida;

    public VentanaVendedores() {
    setTitle("Gestión de Vendedores");
    setSize(800, 650);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setLayout(new BorderLayout(10, 10));

    // ====== Panel de gráfica ======
    jPanelGrafica = new JPanel(new BorderLayout());
    jPanelGrafica.setPreferredSize(new Dimension(600, 300));

    // ====== Campos ======
    txtCodigo = new JTextField();
    txtNombre = new JTextField();
    txtPassword = new JTextField();
    comboGenero = new JComboBox<>(new String[]{"M", "F"});

    txtCodigo.setBorder(BorderFactory.createTitledBorder("Código"));
    txtNombre.setBorder(BorderFactory.createTitledBorder("Nombre"));
    txtPassword.setBorder(BorderFactory.createTitledBorder("Contraseña"));
    comboGenero.setBorder(BorderFactory.createTitledBorder("Género"));

    JPanel panelCampos = new JPanel(new GridLayout(2, 4, 5, 5));
    panelCampos.add(txtCodigo);
    panelCampos.add(txtNombre);
    panelCampos.add(comboGenero);
    panelCampos.add(txtPassword);

    // ====== Botones ======
    btnAgregar = new JButton("Agregar");
    btnActualizar = new JButton("Actualizar");
    btnEliminar = new JButton("Eliminar");
    btnListar = new JButton("Listar");
    btnTop = new JButton("Top 3 Vendedores");
    btnCargarCSV = new JButton("Cargar CSV");
    btnGuardarCSV = new JButton("Guardar CSV");

    JPanel panelBotones = new JPanel(new GridLayout(2, 4, 5, 5));
    panelBotones.add(btnAgregar);
    panelBotones.add(btnActualizar);
    panelBotones.add(btnEliminar);
    panelBotones.add(btnListar);
    panelBotones.add(btnTop);
    panelBotones.add(btnCargarCSV);
    panelBotones.add(btnGuardarCSV);

    // ====== Área de salida ======
    txtSalida = new JTextArea();
    txtSalida.setEditable(false);
    JScrollPane scroll = new JScrollPane(txtSalida);

    // ====== Distribución ======
    add(panelCampos, BorderLayout.NORTH);
    add(jPanelGrafica, BorderLayout.CENTER); // ahora la gráfica se muestra aquí
    add(panelBotones, BorderLayout.SOUTH);
    add(scroll, BorderLayout.EAST);

    // Acciones
    btnAgregar.addActionListener(e -> agregarVendedor());
    btnActualizar.addActionListener(e -> actualizarVendedor());
    btnEliminar.addActionListener(e -> eliminarVendedor());
    btnListar.addActionListener(e -> listarVendedores());
    btnTop.addActionListener(e -> mostrarGraficaTop3EnPanel());
    btnCargarCSV.addActionListener(e -> cargarCSV());
    btnGuardarCSV.addActionListener(e -> guardarCSV());

    // Carga inicial de la gráfica
    mostrarGraficaTop3EnPanel();
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
        
        Controlador.Bitacora.registrar(
                "ADMIN" ,
                "admin" ,
                "AGREGAR",
                "EXITOSA",
                "VENDEDOR AGREGADO: " +txtCodigo.getText()+txtNombre.getText()
        );
        limpiarCampos();
        mostrarGraficaTop3EnPanel();
    }   
        //================================================
        //===== Actualizacion de Datos o modificacion======
        //================================================
        private void actualizarVendedor() {
        String codigo = txtCodigo.getText().trim();
        String nuevoNombre = txtNombre.getText().trim();
        String nuevaPassword = txtPassword.getText().trim();

        if (codigo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingresa el código del vendedor a actualizar.");
            return;
        }
        
        if (codigo.isEmpty() || nuevoNombre.isEmpty() ||nuevaPassword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, llena todos los campos.");
            return;
        }

        Vendedor.actualizarVendedor(codigo, nuevoNombre, nuevaPassword);
        JOptionPane.showMessageDialog(this, "Datos del vendedor actualizados.");
        Controlador.Bitacora.registrar(
                "ADMIN" ,
                "admin" ,
                "MODIFICAR",
                "EXITOSA",
                "VENDEDOR MODIFICADO: " +txtCodigo.getText()+txtNombre.getText()
        );
        limpiarCampos();
        
        mostrarGraficaTop3EnPanel();
    }
        //=======================================
        //==== Eliminar vendedor ========
        //========================================
         private void eliminarVendedor() {
        String codigo = txtCodigo.getText().trim();

        if (codigo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingresa el código del vendedor a eliminar.");
            return;
        }
        
        // Mostrar cuadro de confirmación
    int confirmacion = JOptionPane.showConfirmDialog(
            this,
            "¿Estás seguro de eliminar al vendedor con código: " + codigo + "?",
            "Confirmar eliminación",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
    );

    // Si el usuario presiona "Sí"
    if (confirmacion == JOptionPane.YES_OPTION) {
        Vendedor.eliminarVendedor(codigo);
        JOptionPane.showMessageDialog(this, "Vendedor eliminado correctamente (lista reordenada).");
        Controlador.Bitacora.registrar(
                "ADMIN" ,
                "admin" ,
                "ELIMINAR",
                "EXITOSA",
                "VENDEDOR ELIMINADO: " +txtCodigo.getText()+txtNombre.getText()
        );
        limpiarCampos();
    } else {
        JOptionPane.showMessageDialog(this, "Operación cancelada.");
        Controlador.Bitacora.registrar(
                "ADMIN" ,
                "admin" ,
                "ELIMINAR",
                "FALLIDO",
                "VENDEDOR ELIMINADO: " +txtCodigo.getText()+txtNombre.getText()
        );
    }
    mostrarGraficaTop3EnPanel();
}

    //=============================================
    //======Listado de Vendedores registrados======
    //============================================
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
    //==================================
    //==== Top de los vendedores=====
    //==================================
    
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
    
    private void mostrarGraficaTop3EnPanel() {
    Vendedores[] lista = Vendedor.getVendedores();
    int total = Vendedor.getCantidadVendedores();

    if (total == 0) {
        jPanelGrafica.removeAll();
        jPanelGrafica.repaint();
        return;
    }

    // Copiar y ordenar descendente
    Vendedores[] copia = new Vendedores[total];
    for (int i = 0; i < total; i++) copia[i] = lista[i];
    for (int i = 0; i < total - 1; i++) {
        for (int j = 0; j < total - 1 - i; j++) {
            if (copia[j].getVentasCofirmadas() < copia[j + 1].getVentasCofirmadas()) {
                Vendedores temp = copia[j];
                copia[j] = copia[j + 1];
                copia[j + 1] = temp;
            }
        }
    }

    // Crear dataset
    DefaultCategoryDataset dataset = new DefaultCategoryDataset();
    int limite = Math.min(3, total);
    for (int i = 0; i < limite; i++) {
        dataset.addValue(copia[i].getVentasCofirmadas(), "Ventas Confirmadas", copia[i].getNombre());
    }

    // Crear gráfico
    JFreeChart chart = ChartFactory.createBarChart(
            "Top 3 Vendedores", "Vendedor", "Ventas", dataset
    );

    // Mostrar en el panel
    ChartPanel chartPanel = new ChartPanel(chart);
    jPanelGrafica.removeAll();
    jPanelGrafica.setLayout(new java.awt.BorderLayout());
    jPanelGrafica.add(chartPanel, java.awt.BorderLayout.CENTER);
    jPanelGrafica.validate();
}
    //=====================================
    //==== Boton de cargar desde CSV ======
    //=====================================
    
    private void cargarCSV() {
        File archivo = new File("src/main/resources/data/vendedores.csv");
    int cargados = 0, errores = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length < 4|| partes.length >5) {
                    errores++;
                    continue;
                }

                String codigo = partes[0].trim();
                String nombre = partes[1].trim();
                String genero = partes[2].trim();
                String password = partes[3].trim();
                int ventas=0;
                
                if(partes.length==5){
                    try{
                        ventas= Integer.parseInt(partes[4].trim());
                        if(ventas<0) ventas=0;
                    }catch(NumberFormatException e){
                        ventas=0;
                    }
                }
                
                if (Vendedor.buscarVendedor(codigo) != null) {
                    errores++;
                    continue;
                }

                Vendedores v = new Vendedores(codigo, nombre, genero, password);
                for(int i=0; i<ventas;i++) v.confirmarVenta();
                Vendedor.crearVendedor(v);
                Vendedor.crearVendedor(v);
                cargados++;
            }

            JOptionPane.showMessageDialog(this,
                "Carga completada:\n " + cargados + " vendedores cargados\n " + errores + " con error o duplicado");
            Controlador.Bitacora.registrar(
                "ADMIN",
                "admin",
                "CARGAR",
                "EXITOSA",
                ""
            );

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error al leer archivo: " + ex.getMessage());
            Controlador.Bitacora.registrar(
                "ADMIN",
                "admin",
                "CARGAR",
                "FALLIDA",
                ""
            );
        }
    }
    
    //====== Gurdar los datos con CSV========
     private void guardarCSV() {
        File archivo = new File("src/main/resources/data/vendedores.csv");

        try (PrintWriter pw = new PrintWriter(new FileWriter(archivo))) {
            Vendedores[] lista = Vendedor.getVendedores();
            int total = Vendedor.getCantidadVendedores();

            for (int i = 0; i < total; i++) {
                Vendedores v = lista[i];
                pw.println(v.getCodigo() + "," + v.getNombre() + "," +
                           v.getgenero() + "," + v.getPassword()+","+v.getVentasCofirmadas());
            }

            JOptionPane.showMessageDialog(this, "Archivo CSV guardado correctamente.");
            Controlador.Bitacora.registrar(
                "ADMIN",
                "admin",
                "GUARDAR",
                "EXITOSA",
                ""
            );

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error al guardar: " + ex.getMessage());
            Controlador.Bitacora.registrar(
                "ADMIN",
                "admin",
                "GUARDAR",
                "FALLIDA",
                ""
            );
        }
    }



    private void limpiarCampos() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtGenero.setText("");
        txtPassword.setText("");
    }
}
