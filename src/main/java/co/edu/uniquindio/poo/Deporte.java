package co.edu.uniquindio.poo;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@RequiredArgsConstructor
public class Deporte {
    @NonNull
    private String nombre;
    @NonNull
    private String descripcion;
    @NonNull
    private NivelDificultad nivelDificultad;

    private List<Entrenador> entrenadores = new ArrayList<>();

    public void agregarEntrenador(Entrenador entrenador) {
        entrenadores.add(entrenador);
    }

    @Override
    public String toString() {
    return this.nombre; 
}

}
