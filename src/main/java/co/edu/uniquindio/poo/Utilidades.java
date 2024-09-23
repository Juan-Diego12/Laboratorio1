package co.edu.uniquindio.poo;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Utilidades {
private static  Utilidades instance;

    //Singleton
    public static Utilidades getInstance() {
        if (instance == null) {
            instance = new Utilidades();
        }
        return instance;
    }

    //Serializar un objeto en archivo binario
    public static void serializarObjeto(String nombre, Object objeto) throws IOException{
        ObjectOutputStream salida;

        salida = new  ObjectOutputStream(new FileOutputStream(nombre));
        salida.writeObject(objeto);
        salida.close();
    }

    //Deserializar un objeto en un archivo binario
    public static Object deserializarObjeto( String nombre) throws Exception{
        Object objeto;
        ObjectInputStream entrada;

        entrada = new ObjectInputStream( new FileInputStream(nombre));
        objeto = entrada.readObject();
        entrada.close();

        return objeto;
    }

    //Serializar un objeto en un archivo de XML
    public static void serializarObjetoXML(String nombre, Object objeto) throws IOException{

        XMLEncoder codificador;

        codificador = new XMLEncoder( new FileOutputStream(nombre));
        codificador.writeObject(objeto);
        codificador.close();
    }

    //Deserializar un objeto en un archivo  XML
    public static Object deserializarObjetoXML (String nombre) throws IOException{
        XMLDecoder decodificador;
        Object objeto;

        decodificador = new XMLDecoder( new FileInputStream(nombre));
        objeto = decodificador.readObject();
        decodificador.close();

        return objeto;
    }
}
