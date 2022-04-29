package tests;
import gens.*;
import stdlib.*;


/**
 * Primera versión del tester del Juego de la Vida
 * Incluye control por teclado, bucle y contro por ratón
 * No implementa las funciones con Programación Orientada a Eventos
 * La versión "Life.java" es un tester mejorado
 * @author Guillermo Franco Gimeno
 * @version 25/04/2022
 */

@Deprecated
public class LifeHistoryTest {

   

    public static void main(String[] args) {

        IGen gen;
        boolean done;
        boolean bucle;
        int T_TECLAS = 100;

        
        //gen = new Gen(".\\files\\bg.life");  
        gen = new Gen(32, 64);
        done = false;
        bucle = false;
        ILifeHistory life = new LifeHistory(gen);
        Draw canvas = new Draw("Life´s Game - Guillermo Franco");
        canvas.enableDoubleBuffering();
        canvas.setCanvasSize(800, 800);
        canvas.setXscale(0, gen.size());
        canvas.setYscale(0, gen.size());
        canvas.setPenColor(canvas.WHITE);




        canvas.clear(canvas.BLACK);
        life.current().pintar(canvas);
        canvas.show();

        while (true)  
      {
        
        //Activar o descativar Bucle
        while (canvas.isKeyPressed(66)) {
            System.out.println("Bucle");
            bucle = !bucle;
            canvas.pause(T_TECLAS);
        }

        //Modificar Celdas con el raton
        while (canvas.isMousePressed()){
            life.current().set((int)canvas.mouseX(), (int)canvas.mouseY(), !life.current().alive((int)canvas.mouseX(), (int)canvas.mouseY()));
            life.current().pintar(canvas);
            canvas.show();
            System.out.println("Click (" + (int)canvas.mouseX() + "," + (int)canvas.mouseY() +  ")");
            canvas.pause(T_TECLAS);
        }

        //Contenido del bucle
        while (bucle){
            if (canvas.isKeyPressed(66) || done){
                bucle = !bucle;
                System.out.println("Fin Bucle");
                canvas.pause(T_TECLAS);
            }
            canvas.clear(canvas.BLACK);
            life.evolve();
            life.current().pintar(canvas);
            canvas.show();
            done = life.endOfGame();
            canvas.pause(30);
        }
        
        //Siguiente
        while (canvas.isKeyPressed(39)) {
        System.out.println("Siguiente");
        canvas.setPenColor(canvas.WHITE);
        life.evolve();
        if (!done) {
            canvas.clear(canvas.BLACK);
            life.current().pintar(canvas);
        }
        canvas.show();
        canvas.pause( T_TECLAS);
        done = life.endOfGame(); 
        } 

        //Anterior
        while (canvas.isKeyPressed(37) ) {
            System.out.println("Anterior");
            canvas.setPenColor(canvas.WHITE);
            life.undo();

            if (!done) {
                canvas.clear(canvas.BLACK);
                life.current().pintar(canvas);
            }
            canvas.show();
            canvas.pause( T_TECLAS); 
            done = life.endOfGame();
        }

        //Escape
        while (canvas.isKeyPressed(27) ) {
            done = !done;
            canvas.pause(T_TECLAS);
        }

        //Finalizado
        while (done){
            System.out.println("Fin Juego");
            if (canvas.isKeyPressed(27)){
                done = !done;
                canvas.pause(T_TECLAS);
            }
            canvas.setPenColor(canvas.BOOK_RED);
            canvas.clear(canvas.BLACK);
            life.current().pintar(canvas);
            canvas.setPenColor(canvas.WHITE);
            canvas.textLeft(0.5, gen.size()-1, "Alive = " +  life.current().contarVivas());
            canvas.textLeft(0.5, gen.size()-2, "Generations = " +  life.generations());

            canvas.show();
        }

        //Default
        canvas.setPenColor(canvas.WHITE);
        canvas.clear(canvas.BLACK);
        life.current().pintar(canvas);
        canvas.show();
        


      }

        


      
    }

    public void bucle(){
        
    }
    
}
