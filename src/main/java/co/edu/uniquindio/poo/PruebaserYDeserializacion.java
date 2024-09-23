public class PruebaserYDeserializacion {


    Public static void main (String[]args){
    
        try{
            Utilidades.getInstance().serializarObjetoBinario("Deporte1.dat", deportesObservableList);
            //JOptionPane.showMessageDialog(null, deportesObservableList);
            system.out.println(deportesObservableList);
            nuevaLista = (ObservableList<String>) Utilidades.getInstance().deserializarObjetoBinario("Deporte1.dat");
            system.out.println(nuevaLista);
        }catch(Exception e){
            system.out.println("error");
        }

        try{
            Utilidades.getInstance().serializarObjetoXML("Deporte1.xml", deportesObservableList);
            system.out.println(deportesObservableList);
            nuevaLista = (ObservableList<String>) Utilidades.getInstance().deserializarObjetoXML("Deporte1.xml");
            system.out.println(nuevaLista);
        }catch(Exception e){
            system.out.println("error");
        }
    }

}
