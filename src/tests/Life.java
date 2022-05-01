package tests;

import stdlib.*;
import gens.*;
import java.awt.Font;


/**
 * Versión del Juego de la Vida.
 * Se ha utilizado la librería Stdlib para la lectura de archivos y la GUI
 * Se ha utilizado java.awt.Font para la personalización de fuentes
 * Se han utilizado EventListeners y programación orientada a eventos.
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
    static boolean siguiente_paso;
    static long millisUltimoPaso;
    static int tiempoBucle;
    static boolean menuConfiguracion;

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
        reiniciarGraficos();
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
        double centro = Life.gen.size()*0.55;
        
        //Título
        Life.canvas.setPenColor(Draw.BOOK_RED);
        Life.canvas.filledRectangle(Life.gen.size()/2, Life.gen.size()-1.5*Life.salto_de_linea,Life.gen.size()/2+1 , 1.25*Life.salto_de_linea);
        Life.canvas.setPenColor(Draw.WHITE);
        Life.canvas.text(Life.gen.size()/2, Life.gen.size()-Life.salto_de_linea, "El Juego de la Vida - JH. Conway");
        Life.canvas.text(Life.gen.size()/2,Life. gen.size()-2*Life.salto_de_linea, "Versión de Guillermo Franco Gimeno");

        //Cuerpo de las instrucciones
        Life.canvas.text(Life.gen.size()/2, centro + 3.5*Life.salto_de_linea, "Instrucciones:");
        Life.canvas.textLeft(Life.salto_de_linea/2, centro + 2*Life.salto_de_linea, "Espacio o Flecha derecha = Evolucionar");
        Life.canvas.textLeft(Life.salto_de_linea/2, centro + Life.salto_de_linea, "Flecha izquierda = Involucionar");
        Life.canvas.textLeft(Life.salto_de_linea/2, centro , "Clickar sobre una celda = Cambiar su estado");
        Life.canvas.textLeft(Life.salto_de_linea/2, centro - Life.salto_de_linea , "R (Reiniciar) = Generación en negro");
        Life.canvas.textLeft(Life.salto_de_linea/2, centro -2*Life.salto_de_linea, "T (Texto) = Generación por archivo de texto");
        Life.canvas.textLeft(Life.salto_de_linea/2, centro -3*Life.salto_de_linea, "A (Aleatorio) = Generación aleatoria");
        Life.canvas.textLeft(Life.salto_de_linea/2, centro -4*Life.salto_de_linea, "I (Instrucciones) = Abre este menú con instrucciones");
        Life.canvas.textLeft(Life.salto_de_linea/2, centro -5*Life.salto_de_linea, "B (Bucle) = Actica / Desactiva bucle");
        Life.canvas.textLeft(Life.salto_de_linea/2, centro -6*Life.salto_de_linea, "C (Configuración) = Abre menú de configuración");
  

        Life.canvas.text(Life.gen.size()/2, salto_de_linea, "Empiece con una generación inicial (R, T, A)");

        Life.canvas.show();

    }

    /**
     * Método para generar un juego aleatorio
     */
    static void aleatorio(){

        Life.done = false;
        Life.menuConfiguracion = false;
        Gen nuevaGen = new Gen(Life.gen.size(), Life.gen.size());
        ILifeHistory nuevaLife = new LifeHistory(nuevaGen);
        reiniciarGraficos();
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
        reiniciarGraficos();
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

    /**
     * Método Bucle: 
     * Evoluciona de forma automatica cuando la variable bulcle = true;
     */
    static void bucle() {
        while (true){
            System.out.print(""); //Si no se pone no funciona
            if (loop){
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
                    if (System.currentTimeMillis() - millisUltimoPaso > tiempoBucle){
                        siguiente_paso = true;

                    }


                }

            }else{
                millisUltimoPaso = System.currentTimeMillis();
            }
        }

    }

    static void reiniciarGraficos(){

        
        salto_de_linea = (double)(Life.gen.size())/15;
        canvas.setXscale(0, gen.size());
        canvas.setYscale(0, gen.size());
        canvas.show();
    }
    /**
     * Abre pantalla de configuración
     */
    static void configuracion(){
        Life.canvas.clear(Draw.BLACK);

        Life.canvas.setFont(new Font("Mi fuente", Font.PLAIN, 30));
        double centro = Life.gen.size()/2;
        reiniciarGraficos();
        
        //Título
        Life.canvas.setPenColor(Draw.BOOK_RED);
        Life.canvas.filledRectangle(Life.gen.size()/2, Life.gen.size()-1.5*Life.salto_de_linea,Life.gen.size()/2+1 , 1.25*Life.salto_de_linea);
        Life.canvas.setPenColor(Draw.WHITE);
        Life.canvas.text(Life.gen.size()/2, Life.gen.size()-Life.salto_de_linea, "El Juego de la Vida - JH. Conway");
        Life.canvas.text(Life.gen.size()/2,Life. gen.size()-2*Life.salto_de_linea, "Configuración");

        //Cuerpo de las instrucciones
        Life.canvas.text(Life.gen.size()/2, centro + 3.5*Life.salto_de_linea, "Parámateros:");
        Life.canvas.textLeft(Life.salto_de_linea/2, centro + 2*Life.salto_de_linea, "Arriba / abajo para cambiar el tamaño");
        Life.canvas.textLeft(Life.salto_de_linea/2, centro + Life.salto_de_linea, "Tamaño = " + gen.size());
        Life.canvas.textLeft(Life.salto_de_linea/2, centro , "Izquierda / derecha para cambiar la velocidad del bucle");
        Life.canvas.textLeft(Life.salto_de_linea/2, centro - Life.salto_de_linea, "Velocidad = " + tiempoBucle);


        Life.canvas.text(Life.gen.size()/2, centro -6*Life.salto_de_linea, "Pulse I para salir");

        Life.canvas.show();


    }
    /**
     * Aumenta en 1 el tamaño de la matriz
     */
    static void aumentarTamanno(){

        gen = new Gen (gen.size()+1);
        life = new LifeHistory(gen);
        

    }
    
    /**
     * Disminuye en 1 el tamaño de la matriz
     */
    static void disminuirTamanno(){
        gen = new Gen (gen.size()-1);
        life = new LifeHistory(gen);
        
    }
    public static void main(String[] args) {

        //Parámetros controlabes
        filename = ".\\files\\bg.life";
        canvasSize = 800;
        tiempoBucle = 20;

        //Iniaciliación automática de variables
        gen = new Gen(filename);
        gen = new Gen (gen.size());
        life = new LifeHistory(gen);
        canvas = new Draw("Life´s Game - Guillermo Franco");
        listener = new Listener();
        done = false;
        loop = false;
        siguiente_paso = false;
        millisUltimoPaso = System.currentTimeMillis();
        salto_de_linea = (double)(Life.gen.size())/15;
        menuConfiguracion = false; 

        

        //Inicializacion del canvas
        canvas.enableDoubleBuffering();
        canvas.setCanvasSize(canvasSize, canvasSize);
        canvas.setXscale(0, gen.size());
        canvas.setYscale(0, gen.size());
        canvas.setPenColor(Draw.WHITE);
        canvas.addListener(listener);

        //Empieza en el menú de instrucciones
        listener.keyTyped('i');
        bucle();
        
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
        if (c == 'i'){
            Life.pantallaInstrucciones();
            Life.menuConfiguracion = false;
        }
        if (c == 'b') Life.loop = !Life.loop;
        if (c == 'c') {
            if(!Life.menuConfiguracion){
                Life.configuracion();
                Life.menuConfiguracion = true;
            }
        }
            

    }

    @Override
    public void keyPressed(int keycode) {

        if (keycode == 32 || keycode == 39){ //Flecha deracha y espacio
             
            if (!Life.menuConfiguracion)Life.siguiente();
            else {
                Life.tiempoBucle++;
                Life.configuracion();
            }
        }
        if (keycode == 37){ //Flecha izquierda
            if (!Life.menuConfiguracion) Life.anterior();
            else{
                 Life.tiempoBucle--;
                 Life.configuracion();
            }
        }
        if (keycode == 38) {
            Life.aumentarTamanno(); //Flecha arriba
            Life.configuracion();
        }
        if (keycode == 40) {
            Life.disminuirTamanno(); //Flecha arriba
            Life.configuracion();
        }
       
    }

    @Override
    public void keyReleased(int keycode) {
        // TODO Auto-generated method stub
        
    }

}