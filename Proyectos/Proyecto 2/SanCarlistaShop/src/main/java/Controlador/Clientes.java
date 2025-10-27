/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Cliente;

public class Clientes {

    private static Cliente[] clientes = new Cliente[100];
    private static int contadorClientes = 0;

    public static void crearCliente(Cliente c) {
        if (buscarCliente(c.getCodigo()) == null) {
            clientes[contadorClientes++] = c;
            System.out.println("Cliente " + c.getNombre() + " registrado correctamente.");
        } else {
            System.out.println("Código de cliente duplicado.");
        }
    }

    public static Cliente buscarCliente(String codigo) {
        for (int i = 0; i < contadorClientes; i++) {
            if (clientes[i] != null && clientes[i].getCodigo().equalsIgnoreCase(codigo)) {
                return clientes[i];
            }
        }
        return null;
    }

    public static void actualizarCliente(String codigo, String nuevoNombre, String nuevaPassword) {
        Cliente c = buscarCliente(codigo);
        if (c != null) {
            c.setNombre(nuevoNombre);
            c.setPassword(nuevaPassword);
            System.out.println("Cliente actualizado correctamente.");
        } else {
            System.out.println("Cliente no encontrado.");
        }
    }

    public static void eliminarCliente(String codigo) {
        for (int i = 0; i < contadorClientes; i++) {
            if (clientes[i] != null && clientes[i].getCodigo().equalsIgnoreCase(codigo)) {
                for (int j = i; j < contadorClientes - 1; j++) {
                    clientes[j] = clientes[j + 1];
                }
                clientes[--contadorClientes] = null;
                System.out.println("Cliente eliminado");
                return;
            }
        }
        System.out.println("Cliente no encontrado.");
    }

    public static void listarClientes() {
        System.out.println("=== LISTA DE CLIENTES ===");
        for (int i = 0; i < contadorClientes; i++) {
            Cliente c = clientes[i];
            if (c != null) {
                System.out.println(c.getCodigo() + " | " + c.getNombre()+"|"+c.getgenero()+"|"+c.getCumpleaños());
            }
        }
    }

    public static Cliente[] getClientes() { return clientes; }
    public static int getCantidadClientes() { return contadorClientes; }
}

