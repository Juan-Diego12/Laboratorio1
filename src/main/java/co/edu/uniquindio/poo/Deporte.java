package co.edu.uniquindio.poo;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

@Data
@RequiredArgsConstructor
public class Deporte implements Serializable{
    @NonNull
    private String nombre;
    @NonNull
    private String descripcion;
    @NonNull
    private NivelDificultad nivelDificultad;

    private List<Entrenador> entrenadores = new ArrayList<>();

    private static final long serialVersionUID = 1L;

    public void agregarEntrenador(Entrenador entrenador) {
        entrenadores.add(entrenador);
    }

    @Override
    public String toString() {
    return this.nombre; 

    public Deporte(String nombre){
        this.nombre = nombre;
    }
}

}
