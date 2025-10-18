/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ProductoAl extends Producto {
    private LocalDate fechaCaducidad;

    public ProductoAl(String codigo, String nombre, LocalDate fechaCaducidad, int stock) {
        super(codigo, nombre, "Alimento", stock);
        this.fechaCaducidad = fechaCaducidad;
    }

    public LocalDate getFechaCaducidad() { return fechaCaducidad; }
    public void setFechaCaducidad(LocalDate fechaCaducidad) { this.fechaCaducidad = fechaCaducidad; }

    @Override
    public String getDetalle() {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return "Caduca el: " + fechaCaducidad.format(formato);
    }
}