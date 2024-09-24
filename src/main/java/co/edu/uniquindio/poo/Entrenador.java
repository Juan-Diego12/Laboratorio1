package co.edu.uniquindio.poo;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

@Data
@RequiredArgsConstructor
public class Entrenador implements Serializable {
    @NonNull
    private String nombre;
    @NonNull
    private Deporte especialidad;

    private List<SesionEntrenamiento> sesiones = new ArrayList<>();

    public void agregarSesion(SesionEntrenamiento sesion ){
        sesiones.add(sesion);
    }

    @Override
    public String toString() {
        return this.nombre; 
    }

    public Entrenador(){
        
    }

    public Entrenador(String nombre){
        this.nombre = nombre;

    }

    
}
