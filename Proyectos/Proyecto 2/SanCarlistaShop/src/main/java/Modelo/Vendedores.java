/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author Daniel Predes
 */
public class Vendedores extends Usuario{
    private int ventasConfirmadas;
    private double totalVentas;
    private String productoMasVendido;
    
    public Vendedores(String codigo, String nombre, String genero, String password){
        super(codigo, nombre, genero, password);
        this.ventasConfirmadas=0;
        this.totalVentas = 0.0;
        this.productoMasVendido = "N/A";
    }
    
    
    public void confirmarVenta(){
        
        ventasConfirmadas++;
    }
    
    public void registrarVenta(double monto, String producto) {
        this.totalVentas += monto;
        this.productoMasVendido = producto;
        confirmarVenta();
    }

    public int getPedidosConfirmados() { return ventasConfirmadas; }
    public double getTotalVentas() { return totalVentas; }
    public String getProductoMasVendido() { return productoMasVendido; }
    
    // Promedio diario simulado (ejemplo básico)
    public double getPromedioVentasDiarias() {
        if (ventasConfirmadas == 0) return 0.0;
        return totalVentas / ventasConfirmadas;
    }
    
   
    public int getVentasCofirmadas(){
        return ventasConfirmadas;
    }
  
   
    //----------------------------------------------------------------------
    @Override
    public String getRol(){
        return "VENDEDOR";
    }
}
