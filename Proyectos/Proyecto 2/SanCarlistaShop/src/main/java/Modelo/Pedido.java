/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.time.LocalDate;
public class Pedido {
    private String codigo;
    private Cliente cliente;
    private Producto producto;
    private int cantidad;
    private boolean confirmado;
    private LocalDate fecha;

    public Pedido(String codigo, Cliente cliente, Producto producto, int cantidad) {
        this.codigo = codigo;
        this.cliente = cliente;
        this.producto = producto;
        this.cantidad = cantidad;
        this.confirmado = false;
        this.fecha=LocalDate.now();
    }
    
    public LocalDate getFecha(){return fecha;}
    public String getCodigo() { return codigo; }
    public Cliente getCliente() { return cliente; }
    public Producto getProducto() { return producto; }
    public int getCantidad() { return cantidad; }
    public boolean isConfirmado() { return confirmado; }

    public void confirmar() {
        this.confirmado = true;
    }
    
    public void setCantidad(int cantidad) {
    this.cantidad = cantidad;
}


    @Override
    public String toString() {
        return "Pedido[" + codigo + "] Cliente: " + cliente.getNombre() +
               " | Producto: " + producto.getNombre() +
               " | Cantidad: " + cantidad +
                "|Fecha: "+fecha+
               " | Estado: " + (confirmado ? "Confirmado" : "Pendiente");
    }
}
