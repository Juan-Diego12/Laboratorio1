package co.edu.uniquindio.poo;

public class Adulto extends MiembroClub {

    public Adulto(String nombre, String email, String numeroIdentificacion) {
        super(nombre, email, numeroIdentificacion);
    }

    @Override
    public boolean puedeInscribirse(Deporte deporte) {
        return true;
    }
}
