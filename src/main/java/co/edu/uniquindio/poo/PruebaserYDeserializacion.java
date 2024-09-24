package co.edu.uniquindio.poo;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PruebaserYDeserializacion {

    

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
        

        // Serialización y deserialización binaria
        try {
            Utilidades.getInstance().serializarObjeto("Deporte1.dat", deportes);
            System.out.println("Lista serializada: " + deportes);

            nuevaLista1 = (ArrayList<Deporte>) Utilidades.getInstance().deserializarObjeto("Deporte1.dat");
            System.out.println("Lista deserializada: " + nuevaLista);
        } catch (Exception e) {
            System.out.println("Error en la serialización/deserialización binaria: " + e.getMessage());
        }

        // Serialización y deserialización XML
        try {
            Utilidades.getInstance().serializarObjetoXML("Entrenador1.xml", entrenadores);
            System.out.println("Lista serializada en XML: " + entrenadores);

            nuevaLista2 = (ArrayList<Entrenador>) Utilidades.getInstance().deserializarObjetoXML("Entrenador1.xml");
            System.out.println("Lista deserializada desde XML: " + nuevaLista);
        } catch (Exception e) {
            System.out.println("Error en la serialización/deserialización XML: " + e.getMessage());
        }
    }
}
