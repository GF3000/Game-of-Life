package gens;
import auxi.*;

/**
 * Implementación de la interfaz ILifeHistory proporcionada para la elaboración de la práctica de Programación 2
 * Se utiliza la clase Node del paquete auxi 
 * ERRORES ENCONTRADOS:
 * +Hay que mejorar endOfGame(), a veces el juego entra en un bucle que no es capaz de ser detectado
 * @author Guillermo Franco Gimeno
 * @version 25/04/3022
 */

public class LifeHistory implements ILifeHistory {

    Node<Gen> ultimo = null; 


    
    public LifeHistory (IGen gen) {
        ultimo = new Node (gen, null);
        }

    @Override
    public void evolve() {
        Gen siguiente = ultimo.element.next();
        ultimo = new Node (siguiente, ultimo);
    }

    @Override
    public void undo() {
        if (ultimo.next != null) ultimo = ultimo.next;   
        
    }   



    @Override

    public Gen current() {
        
        return ultimo.element;
    }

    @Override
    public Gen former() {
        return ultimo.next.element;
    }

    @Override
    public int generations() {
        int contador = 0;
        Node <Gen> siguiente = ultimo;
        while (siguiente!= null){
            siguiente = siguiente.next;
            contador++;
        }
        return contador;
    }
    /**
     * El juego acabará si una generación es igual a la anterior o a la anterior de la anterior pues hay estructuras que se repiten ciclicamente
     * @return Juego acabado
     */
    @Override
    public boolean endOfGame() {
        
        if (ultimo.next.next == null) return false;
        return ultimo.element.equals(ultimo.next.element) || ultimo.element.equals(ultimo.next.next.element);
    }
    
}
