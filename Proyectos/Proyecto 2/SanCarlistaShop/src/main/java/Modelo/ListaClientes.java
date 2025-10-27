/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

public class ListaClientes {
    private static Cliente[] clientes = new Cliente[200];
    private static int cantidad = 0;

    public static void agregarCliente(Cliente c) {
        if (cantidad < clientes.length) {
            clientes[cantidad++] = c;
        }
    }

    public static Cliente[] getClientes() {
        return clientes;
    }

    public static int getCantidadClientes() {
        return cantidad;
    }

    public static Cliente buscarCliente(String codigo) {
        for (int i = 0; i < cantidad; i++) {
            if (clientes[i] != null && clientes[i].getCodigo().equalsIgnoreCase(codigo)) {
                return clientes[i];
            }
        }
        return null;
    }
}

