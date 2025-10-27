/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

public class ListaVendedores {
    private static Vendedores[] vendedores = new Vendedores[100];
    private static int cantidad = 0;

    public static void agregarVendedor(Vendedores v) {
        if (cantidad < vendedores.length) {
            vendedores[cantidad++] = v;
        }
    }

    public static Vendedores[] getVendedores() {
        return vendedores;
    }

    public static int getCantidadVendedores() {
        return cantidad;
    }

    public static Vendedores buscarVendedor(String codigo) {
        for (int i = 0; i < cantidad; i++) {
            if (vendedores[i] != null && vendedores[i].getCodigo().equalsIgnoreCase(codigo)) {
                return vendedores[i];
            }
        }
        return null;
    }
}
