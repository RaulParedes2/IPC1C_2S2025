/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package examen.pkgfinal;

import java.time.LocalDate;
import java.util.Scanner;
import java.time.Period;


public class ExamenFinal {

    
    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        
        System.out.println("Ingrese su fecha de naciemiento");
        
        try{
        System.out.println("Dia: ");
        int dia=scanner.nextInt();
        
        System.out.println("Mes: ");
        int mes= scanner.nextInt();
        
        System.out.println("Anio: ");
        int anio = scanner.nextInt();
        
        
        LocalDate FechaNacimiento= LocalDate.of(anio,mes,dia);
        LocalDate FechaActual= LocalDate.now();
        
        Period edad= Period.between(FechaNacimiento, FechaActual);
        System.out.println("Su edad es: "+edad.getYears()+"anios");
        
        }catch(Exception e){
            System.out.println("Ingreo de Datos erronea");
        }
        
        scanner.close();
    }
    
}
