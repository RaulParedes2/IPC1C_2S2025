/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Pedido;
import Modelo.Producto;
import Modelo.Cliente;
import Modelo.Vendedores;

public class Pedidos {

    private static Pedido[] pedidos = new Pedido[100];
    private static int contadorPedidos = 0;

    public static void crearPedido(Pedido p) {
        pedidos[contadorPedidos++] = p;
        System.out.println("Pedido creado para " + p.getCliente().getNombre());
    }

    public static void listarPedidos() {
        System.out.println("=== LISTA DE PEDIDOS ===");
        for (int i = 0; i < contadorPedidos; i++) {
            Pedido p = pedidos[i];
            if (p != null) System.out.println((i + 1) + ". " + p);
        }
    }

    public static void confirmarPedido(String codigo, Vendedores v) {
        for (int i = 0; i < contadorPedidos; i++) {
            Pedido p = pedidos[i];
            if (p != null && p.getCodigo().equalsIgnoreCase(codigo)) {
                p.confirmar();
                v.confirmarVenta();
                System.out.println("Pedido " + codigo + " confirmado por " + v.getNombre());
                return;
            }
        }
        System.out.println("Pedido no encontrado.");
    }

    public static void eliminarPedido(String codigo) {
        for (int i = 0; i < contadorPedidos; i++) {
            if (pedidos[i] != null && pedidos[i].getCodigo().equalsIgnoreCase(codigo)) {
                for (int j = i; j < contadorPedidos - 1; j++) {
                    pedidos[j] = pedidos[j + 1];
                }
                pedidos[--contadorPedidos] = null;
                System.out.println("Pedido eliminado y lista reordenada.");
                return;
            }
        }
        System.out.println("Pedido no encontrado.");
    }

    public static Pedido[] getPedidos() { return pedidos; }
    public static int getCantidadPedidos() { return contadorPedidos; }
}

