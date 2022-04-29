package gens;
/**
 * Responsabilidades:
 *
 * - Tiene las responsabilidades de una modelo en MVC: mantener los
 *   datos principales de la aplicación.
 * - Mantener la historia de todas las generaciones hasta el momento
 *   (en la forma de una cadena enlazada).
 * - Avanzar y retroceder en la historia.
 * - Entender si la última generación es el fin del juego porque la
     siguiente será idéntica.
 */
public interface ILifeHistory 
{
  // private Node<Gen> history = null; // Atributo para la implementación.
  
 /**
  * Añade una nueva generación gen a la historia.
  *
  * public LifeHistory (IGen gen) 
  */
  public void evolve ();
 /**
  * "Involuciona", eliminando la generación actual
  *  y volviendo a la anterior.
  */
  public void undo ();
 /**
  * La generación actual.
  */
  public Gen current ();
 /**
  * La generación anterior.
  */
  public Gen former ();
 /**
  * Número de generaciones en la historia.
  */
  public int generations ();
 /**
  * Determina si el juego de la vida ha concluído.
  */
  public boolean endOfGame ();
}
