/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practica2;

import static practica2.Practica2.cargarDesdeArchivo;
import static practica2.Practica2.guardarEnArchivo;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.*;
import java.awt.*;
/**
 *
 * @author Daniel Predes
 */
//parte logica de la interfaz grafica
public class Practica2G {
    //-------------------------------------------------------------------------
    //==Agregar Personaje==
    //-------------------------------------------------------------------------
    public static void AgregarPersonajeConDialog(Component parent) {
        try {   
            String nombre = JOptionPane.showInputDialog(parent, "Nombre del personaje:");
            if (nombre == null || nombre.trim().isEmpty()) { // si el Nombre esta vacio no resgistrar personaje
                return;
            }

            String arma = JOptionPane.showInputDialog(parent, "Arma del personaje:");
            if (arma == null || arma.trim().isEmpty()) {
                return;// si el Arma esta vacia no registrar personaje
            }
            
            //No permite la entrada de datos vacios, fuera de rango o caracteres
            int hp = leerRangoDialog("HP (100-500):", 100, 500);
            int ataque = leerRangoDialog("Ataque (10-100):", 10, 100);
            int velocidad = leerRangoDialog("Velocidad (1-10):", 1, 10);
            int agilidad = leerRangoDialog("Agilidad (1-10):", 1, 10);
            int defensa = leerRangoDialog("Defensa (1-50):", 1, 50);

            Practica2.personajes[Practica2.contadorPersonajes++]//Agergar personajes al contadors de persoajes
                    = new Personaje(Practica2.idGlobal++, nombre, arma, hp, ataque, velocidad, agilidad, defensa);

            JOptionPane.showMessageDialog(parent, "Personaje agregado exitosamente.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(parent, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    //------------------------------------------------------------------------
    //==Modificar Personaje
    //------------------------------------------------------------------------

    public static void ModificarPersonajeConDialog(Component parent) {
        String entrada = JOptionPane.showInputDialog(parent, "Ingrese nombre o ID del personaje:");//Ingrso de nombres o ID
        if (entrada == null || entrada.trim().isEmpty()) {
            return;
        }

        Personaje p = Practica2.buscarPorIdONombre(entrada.trim());
        if (p == null) {
            JOptionPane.showMessageDialog(parent, "No se encontro el personaje.");//De los contrario 
            return;
        }
        //La modificacion empieza apartir del arma
        String arma = JOptionPane.showInputDialog(parent, "Nueva arma (" + p.arma + "):");
        if (arma != null && !arma.trim().isEmpty()) {
            p.arma = arma;
        }
        //Seguido de los demas
        p.hp = leerRangoDialog("HP (100-500):", 100, 500);
        p.ataque = leerRangoDialog("Ataque (10-100):", 10, 100);
        p.velocidad = leerRangoDialog("Velocidad (1-10):", 1, 10);
        p.agilidad = leerRangoDialog("Agilidad (1-10):", 1, 10);
        p.defensa = leerRangoDialog("Defensa (1-50):", 1, 50);

        JOptionPane.showMessageDialog(parent, "Personaje modificado.");
    }
    //------------------------------------------------------------------------
    //==Eliminar persomaje==
    //------------------------------------------------------------------------

    public static void EliminarPersonajeConDialog(Component parent) {
        String entrada = JOptionPane.showInputDialog(parent, "Ingrese nombre o ID:");// Ingreso de Nombre o ID
        if (entrada == null || entrada.trim().isEmpty()) {
            return;
        }

        int index = Practica2.buscarIndice(entrada.trim());
        if (index == -1) {
            JOptionPane.showMessageDialog(parent, "No se encontro el personaje.");//De los contrario
            return;
        }
        //Mensaje de confirmacion de eliminacion
        int confirm = JOptionPane.showConfirmDialog(parent, "¿Seguro que desea eliminarlo?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            for (int i = index; i < Practica2.contadorPersonajes - 1; i++) {
                Practica2.personajes[i] = Practica2.personajes[i + 1];
            }
            Practica2.contadorPersonajes--;
            JOptionPane.showMessageDialog(parent, "Personaje eliminado.");
        }
    }

    //------------------------------------------------------------------------
    //==Visualizar Personajes==
    //------------------------------------------------------------------------
    public static void VisualizarConDialog(Component parent) {
        if (Practica2.contadorPersonajes == 0) {
            JOptionPane.showMessageDialog(parent, "No hay personajes registrados.");
            return;
        }
        StringBuilder sb = new StringBuilder("=== Personajes Registrados ===\n");
        for (int i = 0; i < Practica2.contadorPersonajes; i++) {
            Personaje p = Practica2.personajes[i];
            sb.append(String.format("[%d] %s | HP:%d Atk:%d Def:%d Vel:%d Agi:%d\n",
                    p.id, p.nombre, p.hp, p.ataque, p.defensa, p.velocidad, p.agilidad));
        }
        JOptionPane.showMessageDialog(parent, sb.toString());
    }

    //-------------------------------------------------------------------------
    //==Simulacion==
    //-------------------------------------------------------------------------
    public static void SimulacionConDialog(Component parent) {
        final JFrame simulacionFrame = new JFrame("Simulación");

        //Validacion de dos personajes minimo
        if (Practica2.contadorPersonajes < 2) {
            JOptionPane.showMessageDialog(parent, "Se necesitan al menos 2 personajes.");
            return;
        }

        //Seleccion de personaje
        String id1 = JOptionPane.showInputDialog(parent, "ID del primer personaje:");
        String id2 = JOptionPane.showInputDialog(parent, "ID del segundo personaje:");
        if (id1 == null || id2 == null) {
            return;
        }

        Personaje p1 = Practica2.buscarPorIdONombre(id1.trim());
        Personaje p2 = Practica2.buscarPorIdONombre(id2.trim());

        if (p1 == null || p2 == null || p1 == p2) {
            JOptionPane.showMessageDialog(parent, "Personajes invalidos.");
            return;
        }

        //Validacion de vida del personaje
        if (p1.hp <= 0 || p2.hp <= 0) {
            JOptionPane.showMessageDialog(parent, "Uno o ambos personajes no estan vivos.");
            return;
        }
        //Pelea
          p1.batallas++; 
          p2.batallas++;
         
          Thread h1 = new Thread(() -> Practica2.pelear(p1, p2)); Thread h2 = new
          Thread(() -> Practica2.pelear(p2, p1));
         
          h1.start(); h2.start();
         
         try { h1.join(); h2.join(); } catch (InterruptedException e) {}
         
          String ganador = (p1.hp > 0) ? p1.nombre : p2.nombre; if (p1.hp > 0)
          { p1.ganadas++; p2.perdidas++; } else { p2.ganadas++; p1.perdidas++;
          }
         
         String fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm:ss"));
         Practica2.historial[Practica2.contadorBatallas++] = new Historial(Practica2.contadorBatallas, 
                 fecha, p1.nombre, p2.nombre, ganador);
         
         
         
          JOptionPane.showMessageDialog(parent, "Ganador: " + ganador);
           

    }

    //--------------------------------------------------------------------------
    //==Historial de batallas==
    //--------------------------------------------------------------------------
    public static void HistorialConDialog(Component parent) {
        if (Practica2.contadorBatallas == 0) {
            JOptionPane.showMessageDialog(parent, "No hay batallas registradas.");
            return;
        }
        StringBuilder sb = new StringBuilder("=== Historial de Batallas ===\n");
        for (int i = 0; i < Practica2.contadorBatallas; i++) {
            Historial h = Practica2.historial[i];
            sb.append(String.format("#%d | %s | %s vs %s | Ganador: %s\n",
                    h.id, h.fecha, h.participante1, h.participante2, h.ganador));
        }
        JOptionPane.showMessageDialog(parent, sb.toString());
    }

    //--------------------------------------------------------------------------
    //==Buscar Personaje==
    //--------------------------------------------------------------------------
    public static void BuscarPersonajeConDialog(Component parent) {
        String entrada = JOptionPane.showInputDialog(parent, "Ingrese nombre o ID del personaje:");
        if (entrada == null || entrada.trim().isEmpty()) {
            return;
        }

        Personaje p = Practica2.buscarPorIdONombre(entrada.trim());
        if (p == null) {
            JOptionPane.showMessageDialog(parent, "No se encontro el personaje.");
            return;
        }

        String info = String.format("[%d] %s | Arma: %s | HP: %d | Atk: %d | Def: %d | Vel: %d | Agi: %d | Batallas: %d | Ganadas: %d | Perdidas: %d",
                p.id, p.nombre, p.arma, p.hp, p.ataque, p.defensa, p.velocidad, p.agilidad, p.batallas, p.ganadas, p.perdidas);
        JOptionPane.showMessageDialog(parent, info);
    }

    //-------------------------------------------------------------------------
    //Cargar y Gurdar
    //-------------------------------------------------------------------------
    public static void GuardarCargarConDialog(Component parent) {
        String[] opciones = {"Guardar estado", "Cargar estado"};
        int opcion = JOptionPane.showOptionDialog(parent, "¿Qué deseas hacer?", "Guardar/Cargar",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);

        if (opcion == 0) {
            guardarEnArchivo();
            JOptionPane.showMessageDialog(parent, "Datos guardados en arena.txt");
        } else if (opcion == 1) {
            cargarDesdeArchivo();
            JOptionPane.showMessageDialog(parent, "Datos cargados desde arena.txt");
        }
    }

    //--------------------------------------------------------------------------
    //==Datos de Estudiante==
    //--------------------------------------------------------------------------
    public static void DatosConDialog(Component parent) {
        JOptionPane.showMessageDialog(parent,
                "Nombre: Raul Jose Daniel Paredes Gonzalez\nCarnet: 202400554\nCurso: IPC1",
                "Datos del estudiante", JOptionPane.INFORMATION_MESSAGE);
    }

    //--------------------------------------------------------------------------
    //==Mensajes
    //--------------------------------------------------------------------------
    private static int leerRangoDialog(String mensaje, int min, int max) {
        while (true) {
            String input = JOptionPane.showInputDialog(mensaje);
            if (input == null) {
                throw new RuntimeException("Operación cancelada.");//en caso de cancelar la operacion en curso
            }
            try {
                int valor = Integer.parseInt(input.trim());
                if (valor >= min && valor <= max) {
                    return valor;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Debe ingresar un número válido.");//Ingreso de caracteres
            }
        }
    }
}
