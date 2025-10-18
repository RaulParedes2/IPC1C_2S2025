/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Usuario;
import java.io.*;

/**
 *
 * @author Daniel Predes
 */
public class Autenticacion {
 private static Usuario usuarioActual=null;
 private static Usuario[] usuarios= new Usuario[100];//Arreglod de cantidad perimitida
 private static int contadorUsuarios=0;
 
 //Agregar Usuario
 public static void registrarUsuario(Usuario usuario){
     if(buscarPorCodigo(usuario.getCodigo())==null){
         usuarios[contadorUsuarios++]=usuario;
         System.out.println("[ok] Usuario"+usuario.getCodigo()+"Registrado Existosamente");
     }
     else{
         System.out.println("[ERROR] Codigo de usuario duplicado");
     }
 }
 
 //Buscar al usuario por codigo
 
 public static Usuario buscarPorCodigo(String codigo){
     for(int i=0; i< contadorUsuarios; i++){
         if(usuarios[i].getCodigo().equalsIgnoreCase(codigo)){
             return usuarios[i];
         }
     }
     return null;
 }
 
    //iniciar secion
 
 public static boolean inicarSecion(String codigo, String password){
     Usuario u= buscarPorCodigo(codigo);
     if(u != null && u.getPassword().equals(password)){
         usuarioActual=u;
         System.out.println("Bienbenido"+u.getNombre()+"("+u.getRol()+")");
         return true;
     }
     System.out.println("Error-Datos incorrectos");
     return false;
 }
 
 //Cerrar sesion del usuario
 public static void cerrarSecion(){
     if(usuarioActual!=null){
         System.out.println("[Cerrar]"+usuarioActual.getNombre()+"ha cerrado secion.");
         usuarioActual=null;
         
     }
     else{
         System.out.println("Secion inactiva");
     }
 }
    //Obtener el usuario actual
 public static Usuario getUsuarioActual(){
     return usuarioActual;
 }
 
 public static Usuario[] getusuarios(){
        return usuarios;
 }
 
 //Guaredar cambios bajo Serializacion
 
 public static void guardarDatos() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("data/usuarios.ser"))) {
            out.writeObject(usuarios);
            out.writeInt(contadorUsuarios);
        } catch (IOException e) {
            System.out.println("[ERROR] No se pudo guardar usuarios: " + e.getMessage());
        }
    }
 
 // Cargar usuarios
    public static void cargarDatos() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("data/usuarios.ser"))) {
            usuarios = (Usuario[]) in.readObject();
            contadorUsuarios = in.readInt();
        } catch (Exception e) {
            System.out.println("[INFO] No se encontraron usuarios previos, se inicia vacío.");
        }
    }
}
