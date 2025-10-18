/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.Interfaz;

/**
 *
 * @author Daniel Predes
 */
public interface CRUD<T> {
    void crear(T obj);
    T leer(String codigo);
    void actualizar(T obj);
    void eliminar(String codigo);
}
