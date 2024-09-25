package co.edu.uniquindio.poo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.ArrayList;

public class PruebaserYDeserializacion {

    public static void main(String[] args) {
        // Inicializamos las listas de deportes y entrenadores
        ArrayList<Deporte> deportes = new ArrayList<>();
        ArrayList<Entrenador> entrenadores = new ArrayList<>();
        
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
            Utilidades.getInstance().serializarObjeto("Entrenador1.dat", entrenadores);
            System.out.println("Lista de entrenadores serializada en binario: " + entrenadores);

            // Deserialización de la lista de entrenadores desde formato binario
            nuevaListaEntrenadores = (ArrayList<Entrenador>) Utilidades.getInstance().deserializarObjeto("Entrenador1.dat");
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
            Utilidades.getInstance().serializarObjetoXML("Deporte1.xml", deportes);
            System.out.println("Lista de deportes serializada en XML: " + deportes);

            // Deserialización de la lista de deportes desde XML
            nuevaListaDeportes = (ArrayList<Deporte>) Utilidades.getInstance().deserializarObjetoXML("Deporte1.xml");
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