/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Interfaz;
import javax.swing.*;
import java.awt.*;
import practica2.Practica2G;
/**
 *
 * @author Daniel Predes
 */
public class Interfaz extends JFrame{
    
    //Interfaz del menu y sus botones
    
    public Interfaz() {
        super("Menú");
    setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
//Nombre de los botenes del menu
        JButton btnAgregar = new JButton("Agregar Personaje");
        JButton btnModificar = new JButton("Modificar Personaje");
        JButton btnEliminar = new JButton("Eliminar Personaje");
        JButton btnVisualizar = new JButton("Visualizar Personajes");
        JButton btnSimulacion = new JButton("Simulación");
        JButton btnHistorial = new JButton("Historial de Batallas");
        JButton btnBuscar = new JButton("Buscar Personaje");
        JButton btnCargarGuardar = new JButton("Cargar/Guardar");
        JButton btnDatos = new JButton("Datos del Estudiante");
        JButton btnSalir = new JButton("Volver");
        
        //Agregar los botenes al panel del menu

        JPanel panel = new JPanel(new GridLayout(10, 2, 15, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.add(btnAgregar);
        panel.add(btnModificar);
        panel.add(btnEliminar);
        panel.add(btnVisualizar);
        panel.add(btnSimulacion);
        panel.add(btnHistorial);
        panel.add(btnBuscar);
        panel.add(btnCargarGuardar);
        panel.add(btnDatos);
        panel.add(btnSalir);

        setContentPane(panel);
        pack();
        setLocationRelativeTo(null);
        
        //Llamada de los constructures de la logica de los botnes
        btnAgregar.addActionListener(e -> Practica2G.AgregarPersonajeConDialog(this));
        btnModificar.addActionListener(e -> Practica2G.ModificarPersonajeConDialog(this));
        btnEliminar.addActionListener(e -> Practica2G.EliminarPersonajeConDialog(this));
        btnVisualizar.addActionListener(e -> Practica2G.VisualizarConDialog(this));
        btnSimulacion.addActionListener(e -> Practica2G.SimulacionConDialog(this));
        btnHistorial.addActionListener(e -> Practica2G.HistorialConDialog(this));
        btnCargarGuardar.addActionListener(e -> Practica2G.GuardarCargarConDialog(this));
        btnBuscar.addActionListener(e -> Practica2G.BuscarPersonajeConDialog(this));
        btnDatos.addActionListener(e -> Practica2G.DatosConDialog(this));
        btnSalir.addActionListener(e -> {
            new Inicio().setVisible(true);
            dispose();
        });
    }
}
