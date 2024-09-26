package co.edu.uniquindio.poo;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

public class Utilidades {
    private static Utilidades instance;
    public static Utilidades getInstance() {
        if (instance == null) {
            instance = new Utilidades();
        }
        return instance;
    }

    private static final LoggerManager logger = new LoggerManager();

    public static void serializarObjetoBinario(String nombre, Object objeto) throws IOException{
        ObjectOutputStream salida;

        salida = new  ObjectOutputStream(new FileOutputStream(nombre));
        salida.writeObject(objeto);
        salida.close();
    }

    public static Object deserializarObjetoBinario( String nombre) throws Exception{
        Object objeto;
        ObjectInputStream entrada;

        entrada = new ObjectInputStream( new FileInputStream(nombre));
        objeto = entrada.readObject();
        entrada.close();

        return objeto;
    }

    public static void serializarObjetoXML(String nombre, Object objeto) throws IOException{

        XMLEncoder codificador;

        codificador = new XMLEncoder( new FileOutputStream(nombre));
        codificador.writeObject(objeto);
        codificador.close();
    }

    public static Object deserializarObjetoXML (String nombre) throws IOException{
        XMLDecoder decodificador;
        Object objeto;

        decodificador = new XMLDecoder( new FileInputStream(nombre));
        objeto = decodificador.readObject();
        decodificador.close();

        return objeto;
    }
    public static <T> void escribirMiembrosTxt(File archivo, List<T> lista, String formato) throws IOException{
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo, true))) {
            for (int i = 0; i < lista.size(); i++) {
                writer.write(lista.get(i).toString());
                writer.newLine();
                if ((i + 1) % 10 == 0 || i == lista.size() - 1) {
                    writer.newLine();
                }
                
                if ((i + 1) % 10 == 0) {
                    writer.flush();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static <T> void escribirDeportesTxt(File archivo, List<T> lista, String formato) throws IOException{
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo, true))) {
            for (int i = 0; i < lista.size(); i++) {
                writer.write(lista.get(i).toString());
                writer.newLine();
                if ((i + 1) % 10 == 0 || i == lista.size() - 1) {
                    writer.newLine();
                }
                
                if ((i + 1) % 10 == 0) {
                    writer.flush();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}