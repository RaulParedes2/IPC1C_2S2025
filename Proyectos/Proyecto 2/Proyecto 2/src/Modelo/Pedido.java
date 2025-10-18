/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.io.Serializable;

public class Pedido implements Serializable {

    private String codigo;
    private Cliente cliente;
    private Producto producto;
    private int cantidad;
    private boolean confirmado;

    public Pedido(String codigo, Cliente cliente, Producto producto, int cantidad) {
        this.codigo = codigo;
        this.cliente = cliente;
        this.producto = producto;
        this.cantidad = cantidad;
        this.confirmado = false;
    }

    public void confirmar() {
        if (!confirmado) {
            confirmado = true;
            producto.reducirStock(cantidad);
        }
    }

    public String getCodigo() { return codigo; }
    public Cliente getCliente() { return cliente; }
    public Producto getProducto() { return producto; }
    public int getCantidad() { return cantidad; }
    public boolean isConfirmado() { return confirmado; }

    @Override
    public String toString() {
        return "[" + codigo + "] " + cliente.getNombre() + " → " + producto.getNombre() +
               " x" + cantidad + " | Estado: " + (confirmado ? "Confirmado" : "Pendiente");
    }
}
