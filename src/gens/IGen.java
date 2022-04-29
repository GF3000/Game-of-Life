package gens;
/**
 * Responsabilidades:
 *
 * - Representar una generación del juego de la vida en un mundo
 *    cuadrado con paredes.
 * - Saber qué celdas están vivas o muertas.
 * - Establecer si una célula está viva o muerta.
 * - Crear la siguiente generación.
 */
import stdlib.*;
  
public interface IGen 
{
 /**
  * Crea una generación sin células vivas en un mundo 
  * cuadrado de dimensiones size x size.
  *
  * public Gen(int size);
  */
 /**
  * Crea una generación con células vivas en posiciones 
  * aleatorias en un mundo cuadrado de dimensiones 
  * de tamaño aleatorio en el rango [min, max].
  *
  * public Gen (int min, int max) 
  */
 /**
  * Crea una generación con células vivas en posiciones 
  * de un mundo cuadrado. El tamaño y las posiciones son
  * leídos de una fichero de texto de nombre filename.
  * La primera línea del fichero tiene el tamaño del mundo.
  * Cada posición x y viene en una línea del fichero.
  *
  * public Gen (String filename)
  */
 /**
  * El tamaño del mundo de esta generación.
  */
  public int size ();
 /**
  * Establece si la célula en la posición (x, y) está viva
  * o muerta. Los parámetros x e y deberán ser mayores o
  * iguales que 0 y menores que this.size().
  */
  public void set (int x, int y, boolean alive);
 /**
  * Dice si la célula en la posición (x, y) está viva.
  */
  public boolean alive(int x, int y);
 /**
  * Devuelve una nueva generación aplicando las reglas del
  * juego de la vida.
  */
  public Gen next ();
 /**
  * Decide si dos generaciones son iguales.
  */
  public boolean equals (Object o);
 /**
  * Pinta esta generación en canvas.
  */
  public void pintar (Draw canvas);

  
}
