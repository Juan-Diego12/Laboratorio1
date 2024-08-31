package co.edu.uniquindio.poo;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public abstract class MiembroClub {
    @NonNull
    private String nombre;
    @NonNull
    private String email;
    @NonNull
    private String numeroIdentificacion;

    public abstract boolean puedeInscribirse(Deporte deporte);
}

