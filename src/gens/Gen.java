package gens;

import stdlib.Draw;
import stdlib.In;

/**
 * Implementación de IGen proporciona para la elaboración de la práctica de Programación 2
 * Se ha modificado la interfaz IGen para incluir algunas funciones extras
 * Se utiliza la librería Stdlib para la lectura de archivos y pintar sobre un lienzo
 * FUTURAS MEJORAS:
 * +La generación debe será toroidal conectando los bordes entre sí
 * @author Guillermo Franco Gimeno
 * @version 29/04/2022
 */

public class Gen implements IGen {
    
    int tamanno;
    public boolean[][] matriz;
    public Gen (int size){
        tamanno = size;
        matriz = new boolean [tamanno][tamanno];
        for (int i = 0; i<tamanno; i++){
            for (int j = 0; j<tamanno; j++){
                matriz[i][j] = false;
            }
        }
    }

    public Gen (String filename){
        In archivo = new In(filename);
        tamanno = Integer.parseInt(archivo.readLine());
        matriz = new boolean[tamanno][tamanno];
        for (int i = 0; i<tamanno; i++){
            for (int j = 0; j<tamanno; j++){
                matriz[i][j] = false;
            }
        }
        String linea;
        while((linea=archivo.readLine())!=null){

            String[] arrAux = linea.split(" ");

            int[] intArrAux = new int [2];
            intArrAux[0] = Integer.parseInt(arrAux[0]);
            intArrAux[1] = Integer.parseInt(arrAux[1]);

            this.set(intArrAux[0], intArrAux[1], true);
        }


        

    }


    public Gen (int min, int max){
        tamanno = auxi.Auxi.aleatorio(min, max);
        matriz = new boolean [tamanno][tamanno];
        for (int i = 0; i<tamanno; i++){
            for (int j = 0; j<tamanno; j++){
                int moneda = auxi.Auxi.aleatorio(2);
                if (moneda == 1) matriz[i][j] = true;
                else matriz[i][j] = false;
            }
        }
    }

    public int size(){
        return tamanno;
    }
    public void set (int x, int y, boolean alive){
        if (x >= 0 && x < tamanno && y >= 0 && y < tamanno ) matriz[x][y] = alive;
    }
    public boolean equals (Object o){
      
        Gen aux = (Gen) o;
        if (aux.tamanno != this.tamanno) return false;
        
        for (int i = 0; i< tamanno; i++){
            for (int j = 0; j< tamanno; j++){
                if(aux.matriz[i][j] != this.matriz[i][j]) return false;
            }
        }
        return true;
    }

    public boolean alive(int x, int y) {
        return matriz[x][y];
    }
    /**
     * Devuelve si la celda existe
     * @param i fila de la matriz
     * @param j columna de la matriz
     * @return Existe celda o no 
     */
    private boolean existeCelda(int i, int j){
        if (i >= 0 && j >= 0 && i < size() && j< size()) return true;
        else return false;
    }
    
    
    public Gen next() {
        Gen futura = new Gen(tamanno);
        int contador = 0;
        //Iteración por cada celda (i,j)
        for (int i = 0; i<tamanno;i++){
            for (int j = 0; j<tamanno; j++){

                contador = 0;
                //Iteración por cada vecina(x,y) de (i,j)
                for (int x = i-1; x <= i+1; x ++){
                    for (int y = j-1; y <= j+1; y ++){
                        //if (no soy la celda && existeCelda (vecina) && vecina está viva)
                        if (!(x == i && y == j) && existeCelda(x, y) && matriz[x][y]) contador++ ;
                    }
                }
              
                if (contador == 3) futura.set(i,j,true);
                else if (contador ==2) futura.set(i, j, matriz[i][j]); 

            }
        }

        return futura;
    }

    /**
     * Método que pinta por consola la generación
     */
    public void print (){
        System.out.println("\n----------------|NEW GEN|----------------\n");
        
        for (int j = tamanno-1; j >= 0 ; j--){
                
            for (int i = 0; i< tamanno; i++){
                if (matriz[i][j]) System.out.print("X");
                else System.out.print(" ");
            }
            System.out.print(":" + j + "\n");
        }
    }
    public void pintar(Draw canvas) {

        canvas.clear(canvas.BLACK);
        for (int i = 0; i< tamanno; i++){
            for (int j = 0; j< tamanno; j++){
                if (matriz[j][i]) canvas.filledSquare(j+0.5, i+0.5, 0.47);
            }
        }
        
        
    }
    
    /**
     * Método que devuelve el número de celdas de vivas
     * @return Celdas vivas
     */

    public int contarVivas(){

        int vivas = 0;

        for (int i = 0; i< tamanno; i++){
            for (int j = 0; j< tamanno; j++){ 
                if (matriz[i][j]) vivas++;
            }
        }

        return vivas;
    }


}
