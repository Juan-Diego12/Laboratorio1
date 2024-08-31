package co.edu.uniquindio.poo;

import java.time.LocalDateTime;

public class MainTest {
    public static void main(String[] args) {
        // Crear un deporte y un entrenador
        Deporte futbol = new Deporte("Fútbol", "Un deporte de equipo con una pelota", NivelDificultad.MEDIO);
        Entrenador entrenador = new Entrenador("Juan Pérez", futbol);
        futbol.agregarEntrenador(entrenador);

        // Crear un administrador
        Administrador admin = new Administrador("Luis Gómez", "ADM001");

        // Programar una sesión de entrenamiento
        SesionEntrenamiento sesion1 = new SesionEntrenamiento(
            LocalDateTime.now(),
            90,
            EstadoSesion.PROGRAMADA,
            futbol,
            entrenador
        );

        // El administrador programa la sesión
        admin.programarSesion(sesion1, entrenador);

        // Mostrar detalles del entrenador y sus sesiones
        System.out.println("\nEntrenador: " + entrenador.getNombre());
        System.out.println("Especialidad: " + entrenador.getEspecialidad().getNombre());
        System.out.println("Sesiones:");
        for (SesionEntrenamiento sesion : entrenador.getSesiones()) {
            System.out.println("- Fecha: " + sesion.getFecha() + ", Duración: " + sesion.getDuracion() +
                               " minutos, Estado: " + sesion.getEstado());
        }

        // Cambiar el estado de la sesión a "COMPLETADA"
        admin.cambiarEstadoSesion(sesion1, EstadoSesion.COMPLETADA);

        // Mostrar el estado actualizado de la sesión
        System.out.println("\nEstado actualizado de la sesión: " + sesion1.getEstado());

        // Crear deportes adicionales
        Deporte natacion = new Deporte("Natación", "Un deporte de resistencia", NivelDificultad.MEDIO);
        Deporte boxeo = new Deporte("Boxeo", "Un deporte de combate", NivelDificultad.ALTO);

        // Crear miembros del club
        MiembroClub juvenil = new Juvenil("Dmitriy", "dmitriy@example.com", "12345");
        MiembroClub adulto = new Adulto("Ana Pérez", "ana.perez@example.com", "67890");

        // Verificar si los miembros pueden inscribirse en ciertos deportes
        System.out.println("\n¿Juvenil puede inscribirse en fútbol? " + juvenil.puedeInscribirse(futbol));
        System.out.println("¿Juvenil puede inscribirse en natación? " + juvenil.puedeInscribirse(natacion));
        System.out.println("¿Juvenil puede inscribirse en boxeo? " + juvenil.puedeInscribirse(boxeo));
        System.out.println("\n¿Adulto puede inscribirse en fútbol? " + adulto.puedeInscribirse(futbol));
        System.out.println("¿Adulto puede inscribirse en natación? " + adulto.puedeInscribirse(natacion));
        System.out.println("¿Adulto puede inscribirse en boxeo? " + adulto.puedeInscribirse(boxeo));

        // Prueba de cancelación de sesión por parte del administrador
        admin.cancelarSesion(sesion1, entrenador);
        System.out.println("\nSesión cancelada. Total de sesiones del entrenador: " + entrenador.getSesiones().size());
    }
}
