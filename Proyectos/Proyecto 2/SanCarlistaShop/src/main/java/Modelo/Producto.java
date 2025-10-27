/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public abstract class Producto implements Serializable {

    private String codigo;
    private String nombre;
    private String categoria;
    private String atributo;
    private double precio;
    private int stock;
    private int cantidadVendida;
    private LocalDate fechaCaducidad;

    public Producto(String codigo, String nombre, String categoria, String atributo) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.categoria = categoria;
        this.atributo = atributo;
    }

    public abstract String getDetalle();

    // ======= Getters y Setters =======
    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getAtributo() {
        return atributo;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setAtributo(String atributo) {
        this.atributo = atributo;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getCantidadVendida() {
        return cantidadVendida;
    }

    public void setCantidadVendida(int cantidadVendida) {
        this.cantidadVendida = cantidadVendida;
    }

    public LocalDate getFechaCaducidad() {
        return fechaCaducidad;
    }

    public void setFechaCaducidad(LocalDate fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    // Diferencia en días hasta la caducidad (solo para Alimentos)
    public long getDiasParaCaducar() {
        if (fechaCaducidad == null) {
            return Long.MAX_VALUE;
        }
        return ChronoUnit.DAYS.between(LocalDate.now(), fechaCaducidad);
    }

    @Override
    public String toString() {
        return "[" + codigo + "] " + nombre
                + " | " + categoria
                + " | " + atributo
                + " | Precio: Q" + String.format("%.2f", precio)
                + " | Stock: " + stock;
    }
}
