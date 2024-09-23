public class Utilidades {

    //Singleton
    public static Utilidades getInstance() {
        if (instance == null) {
            instance = new Utilidades();
        }
        return instance;
    }

    //Serializar un objeto en archivo binario
    public static void serializarObjetoBinario(String nombre, Object objeto) throws IOException{
        ObjetoOutputStream salida;

        salida = new  ObjectOutputStream(new FileOutputStream(nombre));
        salida.writeObject(objeto);
        salida.close();
    }

    //Deserializar un objeto en un archivo binario
    public static Object deserializarObjetoBinario( String nombre) throws IOException{
        Object objeto;
        ObjetoInputStream entrada;

        entrada = new ObjetoInputStream( new FileInputStream(nombre));
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

        decodificador = new XMLDecorder( new FileInputStream(nombre));
        objeto = decodificador.readObject();
        decodificador.close();

        return objeto;
    }
}
