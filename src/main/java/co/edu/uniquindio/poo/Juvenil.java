package co.edu.uniquindio.poo;

public class Juvenil extends MiembroClub {

    public Juvenil(String nombre, String email, String numeroIdentificacion) {
        super(nombre, email, numeroIdentificacion);
    }

    @Override
    public boolean puedeInscribirse(Deporte deporte) {
        return deporte.getNivelDificultad() == NivelDificultad.BAJO ||
               deporte.getNivelDificultad() == NivelDificultad.MEDIO;
    }
}
