package co.edu.uniquindio.poo;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class SesionEntrenamiento {
    @NonNull
    private LocalDateTime fecha;
    @NonNull
    private int duracion;
    @NonNull
    private EstadoSesion estado;
    @NonNull
    private Deporte deporte;
    @NonNull
    private Entrenador entrenador;
}
