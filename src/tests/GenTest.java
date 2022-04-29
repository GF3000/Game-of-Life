package tests;
/**
 * Responsabilidades:
 *
 * - Probar la clase Gen mediante un GUI sencillo.
 *
 */
import stdlib.*;
import gens.*;

public class GenTest 
{
  private IGen gen;
  private Draw canvas;
  boolean done;
  
  public GenTest () 
  {
    //gen = new Gen(32, 64);
    gen = new Gen(".\\files\\bg.life");  
    //gen = carga();
    canvas = new Draw("Life´s Game - Guillermo Franco");
    canvas.enableDoubleBuffering();
    canvas.setCanvasSize(800, 800);
    canvas.setXscale(0, gen.size());
    canvas.setYscale(0, gen.size());
    canvas.setPenColor(canvas.WHITE);
    done = false;
  }
  public IGen carga ()
  {
    IGen gen = new Gen(16);
    gen.set(0, 0, true);
    gen.set(0, 4, true);
    gen.set(1, 0, true);

    gen.set(7, 7, true);
    gen.set(7, 8, true);
    gen.set(7, 9, true);

    gen.set(1, 2, true);
    gen.set(15, 0, true);
    gen.set(15, 15, true);
    return gen;
  }
  public void animacion ()
  {
    while (!done)  
    {
      canvas.clear(canvas.BLACK);
      gen.pintar(canvas);
      ((Gen)gen).print(); //Linea añadida por Guillermo Franco Gimeno
      gen = gen.next();
      canvas.show();
      canvas.pause( 80); 
 
      do {

      } while (!canvas.isKeyPressed(32));
    }
  }
  public static final void main (String[] args) 
  {
    new GenTest().animacion();
  }
}

