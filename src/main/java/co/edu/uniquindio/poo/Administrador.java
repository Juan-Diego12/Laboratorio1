package co.edu.uniquindio.poo;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Administrador {
    @NonNull
    private String nombre;
    @NonNull
    private String numeroIdentificacion;

    public void programarSesion(SesionEntrenamiento sesion, Entrenador entrenador) {
        entrenador.agregarSesion(sesion);
        System.out.println("Sesión programada por " + nombre + " para el entrenador " + entrenador.getNombre());
    }

    public void cancelarSesion(SesionEntrenamiento sesion, Entrenador entrenador) {
        entrenador.getSesiones().remove(sesion);
        System.out.println("Sesión cancelada por " + nombre + " para el entrenador " + entrenador.getNombre());
    }

    public void cambiarEstadoSesion(SesionEntrenamiento sesion, EstadoSesion nuevoEstado) {
        sesion.setEstado(nuevoEstado);
        System.out.println("Estado de la sesión cambiado a " + nuevoEstado + " por " + nombre);
    }
}
