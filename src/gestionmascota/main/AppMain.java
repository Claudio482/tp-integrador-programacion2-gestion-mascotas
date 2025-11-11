package gestionmascota.main;

import gestionmascota.entities.Dueno;
import gestionmascota.entities.Mascota;
import gestionmascota.entities.Microchip;
import gestionmascota.service.DuenoService;
import gestionmascota.service.MascotaService;
import gestionmascota.service.ServiceException;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class AppMain {

    public static void main(String[] args) {

        MascotaService mascotaService = new MascotaService();
        DuenoService duenoService = new DuenoService();
        Scanner sc = new Scanner(System.in);

        int opcion;
        do {
            System.out.println("====================================");
            System.out.println("      GESTION DE DUEÑOS Y MASCOTAS  ");
            System.out.println("====================================");
            System.out.println("1) Crear dueno");
            System.out.println("2) Listar duenos");
            System.out.println("3) Buscar dueno por DNI");
            System.out.println("4) Crear mascota + microchip");
            System.out.println("5) Listar mascotas");
            System.out.println("6) Buscar mascotas por nombre");
            System.out.println("7) Eliminar mascota (baja logica)");
            System.out.println("0) Salir");
            System.out.print("Opcion: ");
            opcion = leerEntero(sc);

            switch (opcion) {
                case 1 -> crearDueno(sc, duenoService);
                case 2 -> listarDuenos(duenoService);
                case 3 -> buscarDuenoPorDni(sc, duenoService);
                case 4 -> crearMascotaConMicrochip(sc, mascotaService);
                case 5 -> listarMascotas(mascotaService);
                case 6 -> buscarMascotasPorNombre(sc, mascotaService);
                case 7 -> eliminarMascota(sc, mascotaService);
                case 0 -> System.out.println("Saliendo...");
                default -> System.out.println("Opción no válida");
            }

            System.out.println();

        } while (opcion != 0);

        sc.close();
    }

    // =========================================
    // SECCIÓN DUEÑOS
    // =========================================

    private static void crearDueno(Scanner sc, DuenoService duenoService) {
        try {
            System.out.println("---- Alta de dueño ----");
            System.out.print("DNI: ");
            String dni = sc.nextLine();

            System.out.print("Nombre: ");
            String nombre = sc.nextLine();

            System.out.print("Apellido: ");
            String apellido = sc.nextLine();

            System.out.print("Telefono (opcional): ");
            String tel = sc.nextLine();

            System.out.print("Email (opcional): ");
            String email = sc.nextLine();

            System.out.print("Direccion (opcional): ");
            String dir = sc.nextLine();

            Dueno d = new Dueno();
            d.setDni(dni);
            d.setNombre(nombre);
            d.setApellido(apellido);
            d.setTelefono(tel.isBlank() ? null : tel);
            d.setEmail(email.isBlank() ? null : email);
            d.setDireccion(dir.isBlank() ? null : dir);
            d.setEliminado(false);

            duenoService.crearDueno(d);

            System.out.println(" Dueño creado con ID: " + d.getId());

        } catch (ServiceException e) {
            System.out.println(" Error: " + e.getMessage());
        }
    }

    private static void listarDuenos(DuenoService duenoService) {
        try {
            List<Dueno> lista = duenoService.listarTodos();
            System.out.println("---- Lista de dueños ----");
            if (lista.isEmpty()) {
                System.out.println("No hay dueños cargados.");
            } else {
                for (Dueno d : lista) {
                    System.out.println(d);
                }
            }
        } catch (ServiceException e) {
            System.out.println(" Error al listar dueños: " + e.getMessage());
        }
    }

    private static void buscarDuenoPorDni(Scanner sc, DuenoService duenoService) {
        System.out.print("Ingrese DNI: ");
        String dni = sc.nextLine();
        try {
            Dueno d = duenoService.obtenerPorDni(dni);
            if (d == null) {
                System.out.println("No se encontro dueño con ese DNI.");
            } else {
                System.out.println("Dueño encontrado:");
                System.out.println(d);
            }
        } catch (ServiceException e) {
            System.out.println(" Error al buscar dueño: " + e.getMessage());
        }
    }

    // =========================================
    // SECCIÓN MASCOTAS + MICROCHIP
    // =========================================

    private static void crearMascotaConMicrochip(Scanner sc, MascotaService mascotaService) {
        try {
            System.out.println("---- Nueva mascota ----");
            System.out.print("Nombre: ");
            String nombre = sc.nextLine();

            System.out.print("Especie: ");
            String especie = sc.nextLine();

            System.out.print("Raza (puede quedar vacia): ");
            String raza = sc.nextLine();

            // Colocamos una fecha de nacimiento fija 
            LocalDate fechaNac = LocalDate.now().minusYears(1);

            System.out.print("ID del dueño (use opcion 2 para ver IDs): ");
            long duenoId = leerLong(sc);

            // armamos el dueño (solo con id)
            Dueno dueno = new Dueno();
            dueno.setId(duenoId);

            // armamos la mascota
            Mascota m = new Mascota();
            m.setNombre(nombre);
            m.setEspecie(especie);
            m.setRaza(raza);
            m.setFechaNacimiento(fechaNac);
            m.setDueno(dueno);
            m.setEliminado(false);

            // preguntamos si quiere microchip
            System.out.print("Agregar microchip? (S/N): ");
            String resp = sc.nextLine().trim().toUpperCase();

            if (resp.equals("S")) {
                Microchip chip = new Microchip();
                System.out.print("Codigo de microchip: ");
                String codigo = sc.nextLine();
                chip.setCodigo(codigo);

                System.out.print("Veterinaria: ");
                String vet = sc.nextLine();
                chip.setVeterinaria(vet);

                chip.setObservaciones("Cargado desde consola");
                chip.setFechaImplantacion(LocalDate.now());
                chip.setEliminado(false);

                m.setMicrochip(chip);
            }

            // llamamos al service (esto hace la transacción)
            mascotaService.crearMascotaConMicrochip(m);

            System.out.println(" Mascota creada con exito. ID generado: " + m.getId());
            if (m.getMicrochip() != null) {
                System.out.println(" Microchip creado con éxito.");
            }

        } catch (ServiceException e) {
            System.out.println(" Error: " + e.getMessage());
        }
    }

    private static void listarMascotas(MascotaService mascotaService) {
        try {
            List<Mascota> mascotas = mascotaService.listarTodas();
            System.out.println("---- Mascotas ----");
            if (mascotas.isEmpty()) {
                System.out.println("No hay mascotas cargadas.");
            } else {
                for (Mascota mas : mascotas) {
                    System.out.println(mas);
                }
            }
        } catch (ServiceException e) {
            System.out.println(" Error al listar mascotas: " + e.getMessage());
        }
    }

    private static void buscarMascotasPorNombre(Scanner sc, MascotaService mascotaService) {
        System.out.print("Nombre (o parte): ");
        String nombre = sc.nextLine();
        try {
            List<Mascota> mascotas = mascotaService.buscarPorNombre(nombre);
            if (mascotas.isEmpty()) {
                System.out.println("No se encontraron mascotas con ese nombre.");
            } else {
                System.out.println("---- Resultados ----");
                for (Mascota m : mascotas) {
                    System.out.println(m);
                }
            }
        } catch (ServiceException e) {
            System.out.println(" Error al buscar mascotas: " + e.getMessage());
        }
    }
   private static void eliminarMascota(Scanner sc, MascotaService mascotaService) {
    System.out.println("---- Eliminar mascota ----");
    System.out.print("Ingrese ID de la mascota a eliminar: ");
    long id = leerLong(sc);

    try {
        mascotaService.eliminarMascotaYMicrochip(id);
        System.out.println(" Mascota eliminada logicamente.");
        System.out.println("   (Si tenia microchip, tambien se marco como eliminado).");
    } catch (ServiceException e) {
        System.out.println(" Error: " + e.getMessage());
    }
}
    // =========================================
    // MÉTODOS AUXILIARES DE LECTURA
    // =========================================

    private static int leerEntero(Scanner sc) {
        while (true) {
            try {
                String linea = sc.nextLine();
                return Integer.parseInt(linea.trim());
            } catch (NumberFormatException e) {
                System.out.print("Numero invalido. Ingrese de nuevo: ");
            }
        }
    }

    private static long leerLong(Scanner sc) {
        while (true) {
            try {
                String linea = sc.nextLine();
                return Long.parseLong(linea.trim());
            } catch (NumberFormatException e) {
                System.out.print("Numero invalido. Ingrese de nuevo: ");
            }
        }
    }
}