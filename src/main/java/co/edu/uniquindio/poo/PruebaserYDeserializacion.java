package co.edu.uniquindio.poo;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.ArrayList;

public class PruebaserYDeserializacion {

<<<<<<< HEAD
    public static void main(String[] args) {
        // Inicializamos las listas de deportes y entrenadores
        ArrayList<Deporte> deportes = new ArrayList<>();
        ArrayList<Entrenador> entrenadores = new ArrayList<>();
=======
    

    public static void main(String[] args) {
        // Suponemos que deportesObservableList está inicializado
        List<Deporte> deportes = new ArrayList<Deporte>();
        List<Entrenador> entrenadores = new ArrayList<Entrenador>();
    
        deportes.add( new Deporte("futbol"));
        deportes.add( new Deporte("basket"));
        deportes.add( new Deporte("natación"));
    
        entrenadores.add( new Entrenador("Samuel"));
        entrenadores.add( new Entrenador("Santiago"));
        entrenadores.add( new Entrenador("Daniel"));
>>>>>>> 0d81bbd35e2af78de453d94bcabcd0e85eb31054
        
        Deporte futbol = new Deporte("Fútbol", "Un deporte de equipo con una pelota", NivelDificultad.MEDIO);

        deportes.add(new Deporte("Fútbol", "abc", NivelDificultad.MEDIO));
        deportes.add(new Deporte("Natación", "abc", NivelDificultad.MEDIO));
        deportes.add(new Deporte("Baloncesto", "abc", NivelDificultad.MEDIO));

        entrenadores.add(new Entrenador("Samuel", futbol));
        entrenadores.add(new Entrenador("Santiago", futbol));
        entrenadores.add(new Entrenador("Daniel", futbol));

        // Lista Observable inicializada
        ObservableList<String> deportesObservableList = FXCollections.observableArrayList("Fútbol", "Baloncesto", "Natación");

        // Variables para las listas deserializadas
        ObservableList<String> nuevaLista;
        ArrayList<Deporte> nuevaListaDeportes;
        ArrayList<Entrenador> nuevaListaEntrenadores;

        // Serialización y deserialización binaria para entrenadores
        try {
            // Serialización de la lista de entrenadores en formato binario
            Utilidades.getInstance().serializarObjeto("EntrenadorLista.dat", entrenadores);
            System.out.println("Lista de entrenadores serializada en binario: " + entrenadores);

            // Deserialización de la lista de entrenadores desde formato binario
            nuevaListaEntrenadores = (ArrayList<Entrenador>) Utilidades.getInstance().deserializarObjeto("EntrenadorLista.dat");
            if (nuevaListaEntrenadores != null && !nuevaListaEntrenadores.isEmpty()) {
                System.out.println("Lista de entrenadores deserializada desde binario: " + nuevaListaEntrenadores);
            } else {
                System.out.println("La lista de entrenadores deserializada está vacía.");
            }
        } catch (Exception e) {
            System.out.println("Error en la serialización/deserialización binaria de entrenadores: " + e.getMessage());
        }

        // Serialización y deserialización XML para deportes
        try {
            // Serialización de la lista de deportes en XML
            Utilidades.getInstance().serializarObjetoXML("DeporteLista.xml", deportes);
            System.out.println("Lista de deportes serializada en XML: " + deportes);

            // Deserialización de la lista de deportes desde XML
            nuevaListaDeportes = (ArrayList<Deporte>) Utilidades.getInstance().deserializarObjetoXML("DeporteLista.xml");
            if (nuevaListaDeportes != null && !nuevaListaDeportes.isEmpty()) {
                System.out.println("Lista de deportes deserializada desde XML: " + nuevaListaDeportes);
            } else {
                System.out.println("La lista de deportes deserializada está vacía.");
            }
        } catch (Exception e) {
            System.out.println("Error en la serialización/deserialización XML de deportes: " + e.getMessage());
        }
    }
}