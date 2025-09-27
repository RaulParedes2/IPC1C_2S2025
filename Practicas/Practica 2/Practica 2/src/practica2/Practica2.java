/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practica2;

//Librerias solicitadas
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
 *
 * @author Daniel Predes
 */
public class Practica2 {
    //Declaracion de los vectores y su cantidad
    //Maximo de personajes 100
    public static Personaje[] personajes = new Personaje[100];
    //Max de peleas resgistradas 100
    public static Historial[] historial = new Historial[100];
    //El contador de personahes registradas es 0
    public static int contadorPersonajes = 0;
    //El contador de peleas es dontadorPersone 0
    public static int contadorBatallas = 0;
    //El ID principal empieza en 1
    public static int idGlobal = 1;
    
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int opcion;
        //Opciones en la consola
        do {
            MostrarMenu();
            opcion = leerEntero(sc, "Elige una opcion: ");

            switch (opcion) {
                case 1: 
                    AgregarPersonaje(sc); 
                    break;
                    
                case 2: 
                    ModificarPersonaje(sc); 
                    break;
                    
                case 3: 
                    EliminarPersonaje(sc); 
                    break;
                    
                case 4:
                    VisualizarPersonajes();
                    break;
                    
                case 5:
                    Simulacion(sc); 
                    break;
                    
                case 6:
                    HistorialBatallas(); 
                    break;
                    
                case 7: 
                    BuscarPersonaje(sc); 
                    break;
                    
                case 8: 
                    GuardarCargar(sc); 
                    break;
                    
                case 9: 
                    DatosEstudiante(); 
                    break;
                    
                case 10:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Intentelo de nuevo (1 al 10)");
            }
        } while (opcion != 10);

        sc.close();

    }
    //-------------------------------------------------------------------------
    //== Menu de la consola(no de la interfaz grafica)==
    //-------------------------------------------------------------------------
    static void MostrarMenu() {
        System.out.println("-------------------------------------------");
        System.out.println("==MENU==");
        System.out.println("1. Agregar Personaje");
        System.out.println("2. Modificar Personaje");
        System.out.println("3. Eliminar Personaje");
        System.out.println("4. Visualizar personajes");
        System.out.println("5. Simulacion de batalla");
        System.out.println("6. Ver historial de batallas");
        System.out.println("7. Buscar personaje");
        System.out.println("8. Guardar y cargar estado");
        System.out.println("9. Ver Datos del estudiante");
        System.out.println("10. Salir");
        System.out.println("-------------------------------------------");
    }
    //En caso de escribir un caracter
    static int leerEntero(Scanner sc, String mensaje) {
        int num;
        while (true) {
            try {
                System.out.print(mensaje);
                num = Integer.parseInt(sc.nextLine().trim());
                return num;
            } catch (NumberFormatException e) {
                System.out.println("Ingrese solo numeros validos.");
            }
        }
    }
    //Leer el rango de personajes permitidos 
    static int leerRango(Scanner sc, String mensaje, int min, int max) {
        int valor = 0;
        boolean valido = false;
        while (!valido) {
            try {
                System.out.print(mensaje);
                valor = Integer.parseInt(sc.nextLine().trim());
                if (valor < min || valor > max) {
                    System.out.println("Debe estar entre " + min + " y " + max);
                } else {
                    valido = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un numero valido.");
            }
        }
        return valor;
    }
    
    //-------------------------------------------------------------------------
    //==Agregar Personaje==
    //-------------------------------------------------------------------------
    static void AgregarPersonaje(Scanner sc) {
        if (contadorPersonajes >= personajes.length) {
            //Lectura de la cantidad de personajes que se pueden registrar
            System.out.println("No se pueden agregar mas personajes.");
            return;
        }

        System.out.println("=== Agregar Personaje ===");

        String nombre;
        while (true) {
            System.out.print("Nombre: ");
            nombre = sc.nextLine().trim();
            if (nombre.isEmpty()) {
                System.out.println("El nombre no puede estar vacio.");//Mensaje de error 
                continue;
            }
            boolean repetido = false;
            for (int i = 0; i < contadorPersonajes; i++) {
                if (personajes[i].nombre.equalsIgnoreCase(nombre)) {
                    repetido = true;
                    break;
                }
            }
            if (!repetido) break;
            System.out.println("Ese nombre ya existe.");//No se puden dubplicar los nombres
        }

        String arma;
        while (true) {
            System.out.print("Arma: ");
            arma = sc.nextLine().trim();
            if (!arma.isEmpty()) break;
            System.out.println("El arma no puede estar vacia.");//Mensaje de error
        }
        //Ingreso de los valores restantes solo numeros positivos y en dentro del rango
        int hp = leerRango(sc, "HP (100-500): ", 100, 500);
        int ataque = leerRango(sc, "Ataque (10-100): ", 10, 100);
        int velocidad = leerRango(sc, "Velocidad (1-10): ", 1, 10);
        int agilidad = leerRango(sc, "Agilidad (1-10): ", 1, 10);
        int defensa = leerRango(sc, "Defensa (1-50): ", 1, 50);

        personajes[contadorPersonajes++] = new Personaje(idGlobal++, nombre, arma, hp, ataque, 
                velocidad, agilidad, defensa);//se agrega un personaje al vector
        
        System.out.println("Personaje agregado exitosamente.");
    }
    //-------------------------------------------------------------------------
    //== Modificar Personajes ==
    //-------------------------------------------------------------------------
    static void ModificarPersonaje(Scanner sc) {
        System.out.print("Ingrese el nombre o ID del personaje: ");
        String entrada = sc.nextLine().trim();

        Personaje p = buscarPorIdONombre(entrada);
        if (p == null) {
            System.out.println("No se encontro el personaje.");//Mensaje de error
            return;
        }

        System.out.println("Editando a: " + p.nombre);
        System.out.print("Nueva arma (" + p.arma + "): ");//Empiza la edicion apartir del arma
        String arma = sc.nextLine().trim();
        if (!arma.isEmpty()) p.arma = arma;

        p.hp = leerRango(sc, "HP (100-500): ", 100, 500);
        p.ataque = leerRango(sc, "Ataque (10-100): ", 10, 100);
        p.velocidad = leerRango(sc, "Velocidad (1-10): ", 1, 10);
        p.agilidad = leerRango(sc, "Agilidad (1-10): ", 1, 10);
        p.defensa = leerRango(sc, "Defensa (1-50): ", 1, 50);

        System.out.println("Personaje modificado exitosamente.");
    }
    //-------------------------------------------------------------------------
    //== Eliminar Personaje==
    //-------------------------------------------------------------------------
    static void EliminarPersonaje(Scanner sc) {
        System.out.print("Ingrese nombre o ID del personaje a eliminar: ");
        String entrada = sc.nextLine().trim();
        int index = buscarIndice(entrada);
        if (index == -1) {
            System.out.println("No se encontro el personaje.");
            return;
        }
        System.out.print("¿Seguro que desea eliminarlo? (s/n): ");//Mensaje de confirmacion
        if (sc.nextLine().trim().equalsIgnoreCase("s")) {
            for (int i = index; i < contadorPersonajes - 1; i++) {//resta del vector y desplaza los personajes
                personajes[i] = personajes[i + 1];
            }
            contadorPersonajes--;
            System.out.println("Personaje eliminado.");
        }
    }
    
    //-------------------------------------------------------------------------
    //==Visualizar Personajes==
    //-------------------------------------------------------------------------
    static void VisualizarPersonajes() {
        System.out.println("=== Personajes Registrados ===");
        
        if (contadorPersonajes == 0) {
        System.out.println("No hay personajes registrados.");
        return;
        }
        for (int i = 0; i < contadorPersonajes; i++) {
            Personaje p = personajes[i];
            System.out.printf("[%d] %s | Arma: %s | HP: %d | Atk: %d | Def: %d | Vel: %d | Agi: %d\n",
                    p.id, p.nombre, p.arma, p.hp, p.ataque, p.defensa, p.velocidad, p.agilidad);
        }
    }
    
    //-------------------------------------------------------------------------
    //==Simulacion==
    //-------------------------------------------------------------------------
    
    static void Simulacion(Scanner sc) {
        if (contadorPersonajes < 2) {
            System.out.println("Se necesitan al menos 2 personajes.");//Validacion de cantidad de personajes registrados
            return;
        }
        VisualizarPersonajes();
        System.out.print("Elija el ID del primer personaje: ");
        Personaje p1 = buscarPorIdONombre(sc.nextLine().trim());
        System.out.print("Elija el ID del segundo personaje: ");
        Personaje p2 = buscarPorIdONombre(sc.nextLine().trim());

        if (p1 == null || p2 == null || p1 == p2) {
            System.out.println("Personajes invalidos.");//Validacion que los personahes no sean iguales
            return;
        }
        
        // Verificar que ambos personajes estén vivos
    if (p1.hp <= 0 || p2.hp <= 0) {
        System.out.println("Uno o ambos personajes no están vivos. No se puede iniciar la batalla.");
        return;
    }
        
        //Inicio de la batalla pokemon
        
        p1.batallas++;
        p2.batallas++;

        System.out.println("=== Inicia la batalla ===");

        Thread h1 = new Thread(() -> pelear(p1, p2));
        Thread h2 = new Thread(() -> pelear(p2, p1));

        h1.start();
        h2.start();

        try {
            h1.join();
            h2.join();
        } catch (InterruptedException e) {}

        String ganador = (p1.hp > 0) ? p1.nombre : p2.nombre;
        if (p1.hp > 0) { p1.ganadas++; p2.perdidas++; }
        else { p2.ganadas++; p1.perdidas++; }

        String fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        historial[contadorBatallas++] = new Historial(contadorBatallas, fecha, p1.nombre, p2.nombre, ganador);

        System.out.println("Ganador: " + ganador);
    }

    static void pelear(Personaje atacante, Personaje enemigo) {
        while (atacante.hp > 0 && enemigo.hp > 0) {
            try { Thread.sleep(1000 / atacante.velocidad); } catch (InterruptedException e) {}
            if (Math.random() * 10 < enemigo.agilidad) {
                System.out.println(atacante.nombre + " ataca a " + enemigo.nombre + " - Fallo!");
            } else {
                int Atck = atacante.ataque - enemigo.defensa;
                if (Atck < 0) Atck = 0;
                enemigo.hp -= Atck;
                
                // Asegurar que el HP no baje de 0
            if (enemigo.hp < 0) enemigo.hp = 0;
            
                System.out.println(atacante.nombre + " ataca a " + enemigo.nombre + " - Atck: " + Atck + " | HP restante: " + enemigo.hp);
            }
        }
    }
    //--------------------------------------------------------------------------
    //==Historila de Peleas==
    //--------------------------------------------------------------------------
    static void HistorialBatallas() {
        System.out.println("=== Historial de Batallas ===");
        
        if (contadorBatallas == 0) {
        System.out.println("No hay batallas registradas.");
        return;
        }
        
        for (int i = 0; i < contadorBatallas; i++) {
            Historial  h = historial[i];
            System.out.printf("Batalla #%d | Fecha: %s | %s vs %s | Ganador: %s\n",
                    h.id, h.fecha, h.participante1, h.participante2, h.ganador);
        }
    }
    //-------------------------------------------------------------------------
    //==Buscar Personaje==
    //-------------------------------------------------------------------------
    static void BuscarPersonaje(Scanner sc) {
        System.out.print("Ingrese nombre o ID del personaje: ");
        String entrada = sc.nextLine().trim();
        Personaje p = buscarPorIdONombre(entrada);
        if (p == null) {
            System.out.println("No se encontro el personaje.");
            return;
        }
        System.out.printf("[%d] %s | Arma: %s | HP: %d | Atk: %d | Def: %d | Vel: %d | Agi: %d | Batallas: %d | Ganadas: %d | Perdidas: %d\n",
                p.id, p.nombre, p.arma, p.hp, p.ataque, p.defensa, p.velocidad, p.agilidad, p.batallas, p.ganadas, p.perdidas);
    }
    //--------------------------------------------------------------------------
    //== Cargar y guardar==
    //--------------------------------------------------------------------------
    
    static void GuardarCargar(Scanner sc) {
        //Menu de cargar y guardar en consola
        System.out.println("1. Guardar estado");
        System.out.println("2. Cargar estado");
        int opcion = leerEntero(sc, "Elige una opcion: ");

        if (opcion == 1) {
            guardarEnArchivo();
        } else if (opcion == 2) {
            cargarDesdeArchivo();
        } else {
            System.out.println("Opcion invalida.");
        }
    }

    static void guardarEnArchivo() {
        try (PrintWriter pw = new PrintWriter(new FileWriter("arena.txt"))) {//Se agaurda en en archivo plano txt
            pw.println("[PERSONAJES]");
            for (int i = 0; i < contadorPersonajes; i++) {
                Personaje p = personajes[i];
                pw.printf("%d;%s;%s;%d;%d;%d;%d;%d;%d;%d;%d\n",// de la forma id,nombre,hp,ataque,velocidad,agilidad,defensa, batllas, ganadas,perdidas
                        p.id, p.nombre, p.arma, p.hp, p.ataque, p.velocidad, p.agilidad, p.defensa,
                        p.batallas, p.ganadas, p.perdidas);
            }
            pw.println("[HISTORIAL]");//Historial agurda de la siguientes manera
            for (int i = 0; i < contadorBatallas; i++) {
                Historial h = historial[i];//id,fecha,Personaje1, personaje 2 y el gandors
                pw.printf("%d;%s;%s;%s;%s\n", h.id, h.fecha, h.participante1, h.participante2, h.ganador);
            }
            System.out.println("Datos guardados en arena.txt");
        } catch (IOException e) {
            System.out.println("Error al guardar: " + e.getMessage());
        }
    }

    static void cargarDesdeArchivo() {
        File archivo = new File("arena.txt");//Carga de archivo plano
        if (!archivo.exists()) {
            System.out.println("No existe archivo para cargar.");//verificacion de acrhivos
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            boolean leyendoPersonajes = false, leyendoHistorial = false;

            contadorPersonajes = 0;
            contadorBatallas = 0;
            idGlobal = 1;

            while ((linea = br.readLine()) != null) {
                if (linea.equals("[PERSONAJES]")) {
                    leyendoPersonajes = true;
                    leyendoHistorial = false;
                    continue;
                } else if (linea.equals("[HISTORIAL]")) {
                    leyendoPersonajes = false;
                    leyendoHistorial = true;
                    continue;
                }

                if (leyendoPersonajes && !linea.trim().isEmpty()) {
                    String[] datos = linea.split(";");
                    Personaje p = new Personaje(
                            Integer.parseInt(datos[0]), datos[1], datos[2],
                            Integer.parseInt(datos[3]), Integer.parseInt(datos[4]),
                            Integer.parseInt(datos[5]), Integer.parseInt(datos[6]),
                            Integer.parseInt(datos[7])
                    );
                    p.batallas = Integer.parseInt(datos[8]);
                    p.ganadas = Integer.parseInt(datos[9]);
                    p.perdidas = Integer.parseInt(datos[10]);
                    personajes[contadorPersonajes++] = p;
                    if (p.id >= idGlobal) idGlobal = p.id + 1;
                }

                if (leyendoHistorial && !linea.trim().isEmpty()) {
                    String[] datos = linea.split(";");
                    Historial h = new Historial(
                            Integer.parseInt(datos[0]), datos[1], datos[2], datos[3], datos[4]
                    );
                    historial[contadorBatallas++] = h;
                }
            }

            System.out.println("Datos cargados desde arena.txt");
        } catch (IOException e) {
            System.out.println("Error al cargar: " + e.getMessage());
        }
    }
    //--------------------------------------------------------------------------
    //==Datos del Estudiante==
    //-------------------------------------------------------------------------
    static void DatosEstudiante() {
        System.out.println("Nombre: Raul Jose Daniel Paredes Gonzalez");
        System.out.println("Carnet: 202400554");
        System.out.println("Curso: IPC1");
    }
    //--------------------------------------------------------------------------
    //==Auxilires
    //--------------------------------------------------------------------------
    static Personaje buscarPorIdONombre(String entrada) {
        try {
            int id = Integer.parseInt(entrada);
            for (int i = 0; i < contadorPersonajes; i++) {
                if (personajes[i].id == id) return personajes[i];
            }
        } catch (NumberFormatException e) {
            for (int i = 0; i < contadorPersonajes; i++) {
                if (personajes[i].nombre.equalsIgnoreCase(entrada)) return personajes[i];
            }
        }
        return null;
    }

    static int buscarIndice(String entrada) {
        try {
            int id = Integer.parseInt(entrada);
            for (int i = 0; i < contadorPersonajes; i++) {
                if (personajes[i].id == id) return i;
            }
        } catch (NumberFormatException e) {
            for (int i = 0; i < contadorPersonajes; i++) {
                if (personajes[i].nombre.equalsIgnoreCase(entrada)) return i;
            }
        }
        return -1;
        
    }
}
