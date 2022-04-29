package tests;

import stdlib.*;
import gens.*;
import java.awt.Font;

/**
 * Versión del Juego de la Vida.
 * Se ha utilizado la librería Stdlib para la lectura de archivos y la GUI
 * Se ha utilizado java.awt.Font para la personalización de fuentes
 * Se han utilizado EventListeners y programación orientada a eventos.
 * FUTURAS VERSIONES:
 * +Incluir una función para que entre en bucle y avance de forma automática
 * @author Guillermo Franco Gimeno.
 * @version 29/04/2022
 */

public class Life {

    static IGen gen; //Primera generación
    static Draw canvas;
    static ILifeHistory life ;
    static Listener listener;
    static int canvasSize;
    static boolean done;
    static String filename;
    public static void main(String[] args) {


        //Iniaciliación de variables
        filename = ".\\files\\bg.life";
        gen = new Gen(filename);
        gen = new Gen (gen.size());
        life = new LifeHistory(gen);
        canvas = new Draw("Life´s Game - Guillermo Franco");
        listener = new Listener();
        canvasSize = 800;
        done = false;
        
        //Inicializacion del canvas
        canvas.enableDoubleBuffering();
        canvas.setCanvasSize(canvasSize, canvasSize);
        canvas.setXscale(0, gen.size());
        canvas.setYscale(0, gen.size());
        canvas.setPenColor(Draw.WHITE);
        canvas.addListener(listener);

        //Empieza en el menú de instrucciones
        listener.keyTyped('i');

    }
    
    
}

class Listener implements DrawListener{

    double salto_del_linea = (double)(Life.gen.size())/15; 

    /**
     * Método para vaciar el canvas
     */
    private void reiniciar(){

        Life.done = false;
        Gen nuevaGen = new Gen(Life.gen.size());
        ILifeHistory nuevaLife = new LifeHistory(nuevaGen);
        Life.gen = nuevaGen;
        Life.life = nuevaLife;
        Life.canvas.setPenColor(Draw.WHITE);
        Life.canvas.clear();
        Life.life.current().pintar(Life.canvas);
        Life.canvas.show();

    }
    /**
     * Método para generar un juego aleatorio
     */
    private void aleatorio(){

        Life.done = false;
        Gen nuevaGen = new Gen(Life.gen.size(), Life.gen.size());
        ILifeHistory nuevaLife = new LifeHistory(nuevaGen);
        Life.gen = nuevaGen;
        Life.life = nuevaLife;
        Life.canvas.setPenColor(Draw.WHITE);
        Life.canvas.clear();
        Life.life.current().pintar(Life.canvas);
        Life.canvas.show();

    }

    /**
     * Crea una nueva generación según el fichero "bg.life"
     */
    private void generacionPorTexto(){

        Life.done = false;
        Gen nuevaGen = new Gen(Life.filename);
        ILifeHistory nuevaLife = new LifeHistory(nuevaGen);
        Life.gen = nuevaGen;
        Life.life = nuevaLife;
        Life.canvas.setPenColor(Draw.WHITE);
        Life.canvas.clear();
        Life.life.current().pintar(Life.canvas);
        Life.canvas.show();

    }
    /**
     * Método que te lleva  a la pantalla de instrucciones
     */
    private void pantallaInstrucciones(){

        Life.canvas.clear(Draw.BLACK);

        Life.canvas.setFont(new Font("Mi fuente", Font.PLAIN, 30));
        int centro = Life.gen.size()/2;
        
        //Título
        Life.canvas.setPenColor(Draw.BOOK_RED);
        Life.canvas.filledRectangle(Life.gen.size()/2, Life.gen.size()-1.5*salto_del_linea,Life.gen.size()/2+1 , 1.25*salto_del_linea);
        Life.canvas.setPenColor(Draw.WHITE);
        Life.canvas.text(Life.gen.size()/2, Life.gen.size()-salto_del_linea, "El Juego de la Vida - JH. Conway");
        Life.canvas.text(Life.gen.size()/2,Life. gen.size()-2*salto_del_linea, "Versión de Guillermo Franco Gimeno");

        //Cuerpo de las instrucciones
        Life.canvas.text(Life.gen.size()/2, centro + 3.5*salto_del_linea, "Instrucciones:");
        Life.canvas.textLeft(1, centro + 2*salto_del_linea, "Espacio o Flecha derecha = Evolucionar");
        Life.canvas.textLeft(1, centro + salto_del_linea, "Flecha izquierda = Involucionar");
        Life.canvas.textLeft(1, centro , "Clickar sobre una celda = Cambiar su estado");
        Life.canvas.textLeft(1, centro - salto_del_linea , "R (Reiniciar) = Generación en negro");
        Life.canvas.textLeft(1, centro -2*salto_del_linea, "T (Texto) = Generación por archivo de texto");
        Life.canvas.textLeft(1, centro -3*salto_del_linea, "A (Aleatorio) = Generación aleatoria");
        Life.canvas.textLeft(1, centro -4*salto_del_linea, "I (Instrucciones) = Abre este menú con instrucciones");
        Life.canvas.text(Life.gen.size()/2, centro -6*salto_del_linea, "Empiece con una generación inicial (R, T, A)");

        Life.canvas.show();

    }

    
    /**
     * Método que se ejecuta cuando el juego termina
     */
    private void finDelJuego(){
        
        Life.canvas.clear();
        Life.canvas.setPenColor(Draw.RED);
        Life.life.current().pintar(Life.canvas);
        Life.canvas.setPenColor(Draw.WHITE);
        Life.canvas.textLeft(0.5, Life.gen.size()-0.5*salto_del_linea, "Alive = " +  Life.life.current().contarVivas());
        Life.canvas.textLeft(0.5, Life.gen.size()-1.5*salto_del_linea, "Generations = " +  Life.life.generations());
        Life.canvas.show(); 

    }
    /**
     * Método usado para evolucionar y  pintar la siguiente generación
     */
    private void siguiente(){

        if (!Life.done){
        Life.canvas.setPenColor(Draw.WHITE);
        Life.life.evolve();
        Life.canvas.clear();
        Life.life.current().pintar(Life.canvas);
        Life.canvas.show();
        Life.done = Life.life.endOfGame();
        }else{
            finDelJuego();
        }

    }
    /**
     * Método usado para involucionar al estado anterior
     */
    private void anterior(){

        Life.canvas.setPenColor(Draw.WHITE);
        Life.life.undo();
        Life.canvas.clear();
        Life.life.current().pintar(Life.canvas);
        Life.canvas.show();
        Life.done = Life.life.endOfGame();

    }

    @Override
    public void mousePressed(double x, double y) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseDragged(double x, double y) {
      // TODO document why this method is empty
    }

    @Override
    public void mouseReleased(double x, double y) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseClicked(double x, double y) {

        Life.canvas.setPenColor(Draw.WHITE);
        Life.life.current().set((int)x,(int) y, !Life.life.current().alive((int)x, (int)y));
        Life.done = false;
        Life.canvas.clear();
        Life.life.current().pintar(Life.canvas);
        Life.canvas.show();

    }

    @Override
    public void keyTyped(char c) {

        if (c == 'r') reiniciar();
        if (c == 'a') aleatorio();
        if (c == 't') generacionPorTexto();
        if (c == 'i') pantallaInstrucciones();    

    }

    @Override
    public void keyPressed(int keycode) {

        if (keycode == 32 || keycode == 39) siguiente(); //Flecha deracha y espacio
        if (keycode == 37) anterior(); //Flecha izquierda
       
    }

    @Override
    public void keyReleased(int keycode) {
        // TODO Auto-generated method stub
        
    }

}