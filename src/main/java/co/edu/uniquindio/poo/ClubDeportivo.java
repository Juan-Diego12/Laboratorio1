package co.edu.uniquindio.poo;

import java.util.ArrayList;
import java.util.List;

public class ClubDeportivo {

    private List<MiembroClub> miembros;
    private List<Deporte> deportes;

    public ClubDeportivo() {
        miembros = new ArrayList<>();
        deportes = new ArrayList<>();
    }

    public void registrarMiembro(MiembroClub miembro) {
        miembros.add(miembro);
    }

    public void registrarDeporte(Deporte deporte){
        deportes.add(deporte);
    }

    public List<Deporte> getDeportes() {
        return deportes;
    }

    public Deporte obtenerDeportePorNombre(String nombre) {
        return deportes.stream().filter(d -> d.getNombre().equals(nombre)).findFirst().orElse(null);
    }

    public MiembroClub getUltimoMiembroRegistrado() {
        if (miembros.isEmpty()) {
            return null;
        }
        return miembros.get(miembros.size() - 1);
    }
}
