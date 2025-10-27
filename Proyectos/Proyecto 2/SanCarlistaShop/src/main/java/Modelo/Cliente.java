/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.time.LocalDate;
/**
 *
 * @author Daniel Predes
 */
public class Cliente extends Usuario{
    private LocalDate cumpleaños;
    
    private LocalDate ultimaCompra;
    private int comprasUltimoMes;
    private double montoTotalUltimoMes;
    private String productoFavorito;
    private String clasificacion;
    
    public Cliente(String codigo, String nombre, String genero, String password, LocalDate cumpleaños){
        super(codigo, nombre, genero, password);
        this.cumpleaños=cumpleaños;
        this.ultimaCompra = LocalDate.now().minusDays((int)(Math.random() * 30));
        this.comprasUltimoMes = (int)(Math.random() * 10);
        this.montoTotalUltimoMes = Math.random() * 1000;
        this.productoFavorito = "Desconocido";
        this.clasificacion = calcularClasificacion();
    }
    public LocalDate getCumpleaños(){
        return cumpleaños;
    }
    
    public void setCumpleaños(LocalDate cumpleaños) { 
        this.cumpleaños = cumpleaños; }

    
    public String getUltimaCompra() { return ultimaCompra.toString(); }
    public int getComprasUltimoMes() { return comprasUltimoMes; }
    public double getMontoTotalUltimoMes() { return montoTotalUltimoMes; }
    public String getProductoFavorito() { return productoFavorito; }
    public String getClasificacion() { return clasificacion; }

    private String calcularClasificacion() {
        if (comprasUltimoMes >= 8) return "Frecuente";
        if (comprasUltimoMes >= 3) return "Ocasional";
        return "Nuevo";
    }
    
    @Override
    public String getRol(){
        return "CLIENTE";
    }
}
