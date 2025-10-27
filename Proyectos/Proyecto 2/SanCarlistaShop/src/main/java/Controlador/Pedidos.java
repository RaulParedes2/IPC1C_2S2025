/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Pedido;
import Modelo.Producto;
import Modelo.Vendedores;
import java.io.*;
import javax.swing.JOptionPane;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Pedidos {

    private static Pedido[] pedidos = new Pedido[100];
    private static int contadorPedidos = 0;
    private static final String RUTA_STOCK = "src/main/resources/data/stock.csv"; // 

    public static void crearPedido(Pedido p) {
        pedidos[contadorPedidos++] = p;
        System.out.println("Pedido creado para " + p.getCliente().getNombre());
    }

    // ================================================================
    // Verificar si existe un pedido con el mismo código
    // ================================================================
    public static boolean existePedido(String codigo) {
        for (int i = 0; i < contadorPedidos; i++) {
            if (pedidos[i] != null && pedidos[i].getCodigo().equalsIgnoreCase(codigo)) {
                return true;
            }
        }
        return false;
    }

    public static boolean confirmarPedido(String codigo, Vendedores v) {
    for (int i = 0; i < contadorPedidos; i++) {
        Pedido p = pedidos[i];

        if (p != null && p.getCodigo().equalsIgnoreCase(codigo)) {

            Producto prod = p.getProducto();
            int cantidad = p.getCantidad();

            // 🔹 Leer stock desde src/data/stock.csv
            int stockDisponible = obtenerStockDeArchivo(prod.getCodigo());

            if (stockDisponible < cantidad) {
                JOptionPane.showMessageDialog(null,
                        "No hay stock suficiente para el pedido " + codigo +
                        ". Pedido sigue pendiente.",
                        "Stock insuficiente", JOptionPane.WARNING_MESSAGE);
                return false;
            }

            // 🔹 Actualizar stock en el archivo
            actualizarStockEnArchivo(prod.getCodigo(), stockDisponible - cantidad);

            // 🔹 Confirmar pedido y sumar venta al vendedor
            p.confirmar();
            v.confirmarVenta();

            // 🔹 Registrar movimiento en historial de stock
            HistorialStock.agregarMovimiento(
                    "Venta de " + cantidad + "x " + prod.getNombre() +
                    " por " + v.getNombre()
            );

            // 🔹 Registrar la venta en historial_compras.csv (con precio y total)
            registrarVentaEnHistorialCSV(p, v);

            // 🔹 Eliminar pedido confirmado de la lista
            eliminarPedido(codigo);

            JOptionPane.showMessageDialog(null,
                    "Pedido " + codigo + " confirmado y registrado correctamente.",
                    "Pedido Confirmado", JOptionPane.INFORMATION_MESSAGE);
            return true;
        }
    }

    JOptionPane.showMessageDialog(null, "Pedido no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
    return false;
}

    // ================================================================
//  REGISTRAR VENTA EN HISTORIAL (archivo CSV) con precio y total
// ================================================================
    private static void registrarVentaEnHistorialCSV(Pedido p, Vendedores v) {
    File archivo = new File("src/main/resources/data/historial_compras.csv");
    boolean nuevoArchivo = !archivo.exists();

    try (PrintWriter pw = new PrintWriter(new FileWriter(archivo, true))) {
        LocalDateTime ahora = LocalDateTime.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        if (nuevoArchivo) {
            pw.println("Fecha,CódigoCliente,CódigoProducto,NombreProducto,Cantidad,Vendedor,PrecioUnitario,Total");
        }

        double precioUnitario = p.getProducto().getPrecio();
        double total = precioUnitario * p.getCantidad();

        pw.println(
            formato.format(ahora) + "," +
            p.getCliente().getCodigo() + "," +
            p.getProducto().getCodigo() + "," +
            p.getProducto().getNombre() + "," +
            p.getCantidad() + "," +
            v.getNombre() + "," +
            String.format("%.2f", precioUnitario) + "," +
            String.format("%.2f", total)
        );

    } catch (IOException e) {
        System.err.println("Error al registrar historial de compras: " + e.getMessage());
    }
}

    // ============================================================
// 🔹 Obtener stock actual desde src/data/stock.csv (3 columnas)
// ============================================================
    private static int obtenerStockDeArchivo(String codigoProducto) {
        String ruta = "src/main/resources/data/stock.csv";
        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.startsWith("Código")) {
                    continue;
                }

                String[] partes = linea.split(",");
                // El archivo tiene 3 columnas: Código,Stock,Precio
                if (partes.length >= 2 && partes[0].trim().equalsIgnoreCase(codigoProducto)) {
                    return Integer.parseInt(partes[1].trim()); // <- Stock está en la segunda posición
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer stock.csv: " + e.getMessage());
        }
        return 0;
    }

// ============================================================
// 🔹 Actualizar stock en src/data/stock.csv (manteniendo el precio)
// ============================================================
    private static void actualizarStockEnArchivo(String codigoProducto, int nuevoStock) {
        String ruta = "src/main/resources/data/stock.csv";
        StringBuilder nuevoContenido = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.startsWith("Código")) {
                    nuevoContenido.append(linea).append("\n");
                    continue;
                }

                String[] partes = linea.split(",");
                if (partes.length >= 3 && partes[0].trim().equalsIgnoreCase(codigoProducto)) {
                    String precio = partes[2].trim(); // conservar el precio
                    nuevoContenido.append(codigoProducto)
                            .append(",")
                            .append(nuevoStock)
                            .append(",")
                            .append(precio)
                            .append("\n");
                } else {
                    nuevoContenido.append(linea).append("\n");
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer stock.csv: " + e.getMessage());
            return;
        }

        try (PrintWriter pw = new PrintWriter(new FileWriter(ruta))) {
            pw.write(nuevoContenido.toString());
        } catch (IOException e) {
            System.out.println("Error al actualizar stock.csv: " + e.getMessage());
        }
    }


    // ================================================================
    // Eliminar pedido confirmado
    // ================================================================
    public static void eliminarPedido(String codigo) {
        for (int i = 0; i < contadorPedidos; i++) {
            if (pedidos[i] != null && pedidos[i].getCodigo().equalsIgnoreCase(codigo)) {
                for (int j = i; j < contadorPedidos - 1; j++) {
                    pedidos[j] = pedidos[j + 1];
                }
                pedidos[--contadorPedidos] = null;
                return;
            }
        }
    }

    public static Pedido[] getPedidos() {
        return pedidos;
    }

    public static int getCantidadPedidos() {
        return contadorPedidos;
    }
}
