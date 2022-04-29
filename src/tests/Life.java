package tests;

import stdlib.*;
import gens.*;
import java.awt.Font;

import javax.swing.plaf.basic.BasicOptionPaneUI.ButtonActionListener;

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
    static ILifeHistory life;
    static Listener listener;
    static int canvasSize;
    static boolean done;
    static String filename;
    static boolean loop;
    static double salto_de_linea;

    /**
     * 
     * @param tiempo Tiempo en ms que tardará en devolver true
     * @return Devuelve true si ha pasado el tiempo
     */
    public static boolean esperar(int tiempo){
        boolean listo = false;

        return listo;
    }
    /**
     * Método que se ejecuta cuando el juego ha terminado
     */

    static void finDelJuego(){
        
        Life.canvas.clear();
        Life.canvas.setPenColor(Draw.RED);
        Life.life.current().pintar(Life.canvas);
        Life.canvas.setPenColor(Draw.WHITE);
        Life.canvas.textLeft(0.5, Life.gen.size()-0.5*salto_de_linea, "Alive = " +  Life.life.current().contarVivas());
        Life.canvas.textLeft(0.5, Life.gen.size()-1.5*salto_de_linea, "Generations = " +  Life.life.generations());
        Life.canvas.show(); 
        loop = false;

    }
    /**
     * Crea una nueva generación según el fichero "bg.life"
     */
    static void generacionPorTexto(){

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
    static void pantallaInstrucciones(){

        Life.canvas.clear(Draw.BLACK);

        Life.canvas.setFont(new Font("Mi fuente", Font.PLAIN, 30));
        int centro = Life.gen.size()/2;
        
        //Título
        Life.canvas.setPenColor(Draw.BOOK_RED);
        Life.canvas.filledRectangle(Life.gen.size()/2, Life.gen.size()-1.5*Life.salto_de_linea,Life.gen.size()/2+1 , 1.25*Life.salto_de_linea);
        Life.canvas.setPenColor(Draw.WHITE);
        Life.canvas.text(Life.gen.size()/2, Life.gen.size()-Life.salto_de_linea, "El Juego de la Vida - JH. Conway");
        Life.canvas.text(Life.gen.size()/2,Life. gen.size()-2*Life.salto_de_linea, "Versión de Guillermo Franco Gimeno");

        //Cuerpo de las instrucciones
        Life.canvas.text(Life.gen.size()/2, centro + 3.5*Life.salto_de_linea, "Instrucciones:");
        Life.canvas.textLeft(1, centro + 2*Life.salto_de_linea, "Espacio o Flecha derecha = Evolucionar");
        Life.canvas.textLeft(1, centro + Life.salto_de_linea, "Flecha izquierda = Involucionar");
        Life.canvas.textLeft(1, centro , "Clickar sobre una celda = Cambiar su estado");
        Life.canvas.textLeft(1, centro - Life.salto_de_linea , "R (Reiniciar) = Generación en negro");
        Life.canvas.textLeft(1, centro -2*Life.salto_de_linea, "T (Texto) = Generación por archivo de texto");
        Life.canvas.textLeft(1, centro -3*Life.salto_de_linea, "A (Aleatorio) = Generación aleatoria");
        Life.canvas.textLeft(1, centro -4*Life.salto_de_linea, "I (Instrucciones) = Abre este menú con instrucciones");
        Life.canvas.text(Life.gen.size()/2, centro -6*Life.salto_de_linea, "Empiece con una generación inicial (R, T, A)");

        Life.canvas.show();

    }

    /**
     * Método para generar un juego aleatorio
     */
    static void aleatorio(){

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
     * Método para vaciar el canvas
     */
    static void reiniciar(){

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
     * Método usado para evolucionar y  pintar la siguiente generación
     */
    static void siguiente(){

        if (!Life.done){
        Life.canvas.setPenColor(Draw.WHITE);
        Life.life.evolve();
        Life.canvas.clear();
        Life.life.current().pintar(Life.canvas);
        Life.canvas.show();
        Life.done = Life.life.endOfGame();
        }else{
            Life.finDelJuego();
        }

    }

    /**
     * Método usado para involucionar al estado anterior
     */
    static void anterior(){

        Life.canvas.setPenColor(Draw.WHITE);
        Life.life.undo();
        Life.canvas.clear();
        Life.life.current().pintar(Life.canvas);
        Life.canvas.show();
        Life.done = Life.life.endOfGame();

    }
    public static void main(String[] args) {


        //Iniaciliación de variables
        filename = ".\\files\\acorn.life";
        gen = new Gen(filename);
        gen = new Gen (gen.size());
        life = new LifeHistory(gen);
        canvas = new Draw("Life´s Game - Guillermo Franco");
        listener = new Listener();
        canvasSize = 800;
        done = false;
        loop = false;
        boolean siguiente_paso = false;
        long millisUltimoPaso = System.currentTimeMillis();
        salto_de_linea = (double)(Life.gen.size())/15; 
        
        //Inicializacion del canvas
        canvas.enableDoubleBuffering();
        canvas.setCanvasSize(canvasSize, canvasSize);
        canvas.setXscale(0, gen.size());
        canvas.setYscale(0, gen.size());
        canvas.setPenColor(Draw.WHITE);
        canvas.addListener(listener);

        //Empieza en el menú de instrucciones
        listener.keyTyped('i');

        while (true){
            System.out.print("");
            if (loop){
                //System.out.println(millisUltimoPaso);
                if (siguiente_paso){
                    siguiente_paso = false;
                    if (!Life.done){
                        canvas.setPenColor(Draw.WHITE);
                        life.evolve();
                        canvas.clear();
                        life.current().pintar(Life.canvas);
                        canvas.show();
                        done = life.endOfGame();
                        }else{
                            finDelJuego();
                        }
                        millisUltimoPaso = System.currentTimeMillis();

                }else{
                    if (System.currentTimeMillis() - millisUltimoPaso > 5){
                        siguiente_paso = true;

                    }


                }

            }else{
                millisUltimoPaso = System.currentTimeMillis();
            }
        }

    }
    
    
}

class Listener implements DrawListener{

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

        if (c == 'r') Life.reiniciar();
        if (c == 'a') Life.aleatorio();
        if (c == 't') Life.generacionPorTexto();
        if (c == 'i') Life.pantallaInstrucciones();
        if (c == 'b'){
            //System.out.println(Life.loop);
            Life.loop = !Life.loop;
            }    

    }

    @Override
    public void keyPressed(int keycode) {

        if (keycode == 32 || keycode == 39) Life.siguiente(); //Flecha deracha y espacio
        if (keycode == 37) Life.anterior(); //Flecha izquierda
       
    }

    @Override
    public void keyReleased(int keycode) {
        // TODO Auto-generated method stub
        
    }

}