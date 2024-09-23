package co.edu.uniquindio.poo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PruebaserYDeserializacion {

    public static void main(String[] args) {
        // Suponemos que deportesObservableList está inicializado
        ObservableList<String> deportesObservableList = FXCollections.observableArrayList("Fútbol", "Baloncesto", "Natación");
        ObservableList<String> nuevaLista;

        // Serialización y deserialización binaria
        try {
            Utilidades.getInstance().serializarObjeto("Deporte1.dat", deportesObservableList);
            System.out.println("Lista original: " + deportesObservableList);

            nuevaLista = (ObservableList<String>) Utilidades.getInstance().deserializarObjeto("Deporte1.dat");
            System.out.println("Lista deserializada: " + nuevaLista);
        } catch (Exception e) {
            System.out.println("Error en la serialización/deserialización binaria: " + e.getMessage());
        }

        // Serialización y deserialización XML
        try {
            Utilidades.getInstance().serializarObjetoXML("Deporte1.xml", deportesObservableList);
            System.out.println("Lista original en XML: " + deportesObservableList);

            nuevaLista = (ObservableList<String>) Utilidades.getInstance().deserializarObjetoXML("Deporte1.xml");
            System.out.println("Lista deserializada desde XML: " + nuevaLista);
        } catch (Exception e) {
            System.out.println("Error en la serialización/deserialización XML: " + e.getMessage());
        }
    }
}
