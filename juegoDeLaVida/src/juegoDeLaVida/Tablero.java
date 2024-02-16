package juegoDeLaVida;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.CyclicBarrier;

public class Tablero {
    public static Buzon[][] tableroBuzones;
    public static int sizeT;
    public static int generaciones;
    public static boolean[][] tablero;
    public static CyclicBarrier barrierTurno;

    public static void main(String[] args) throws FileNotFoundException, InterruptedException {
      
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    System.out.println("Ingrese el nombre del archivo:");


    try {
        String archivoEntrada = "src\\data\\" + reader.readLine();
        BufferedReader br = new BufferedReader(new FileReader(archivoEntrada));
            
        // Leer el tamaño del tablero
        int size = Integer.parseInt(br.readLine());
        sizeT=size;
        tablero = new boolean[size][size];
        tableroBuzones= new Buzon[size][size];

        try (Scanner input = new Scanner(System.in)) {
            System.out.println("Ingrese el total de generaciones:");
            generaciones = input.nextInt();
        }

        for (int n = 0; n < size; n++) {
            String[] linea = br.readLine().split(",");
            for (int m = 0; m < size; m++) {
                // Insertar buzones en el tablero
                tablero[n][m] = Boolean.parseBoolean(linea[m]);
                }
            }
        br.close();

        CyclicBarrier barrierRecepcion = new CyclicBarrier(sizeT*sizeT, () -> {
        // Acción a ejecutar cuando todos los hilos hayan llegado a la barrera
        System.out.println("Se han recibido todos los estados en todas las celdas...");
        });

        CyclicBarrier barrierCalculo = new CyclicBarrier(sizeT*sizeT, () -> {
            // Acción a ejecutar cuando todos los hilos hayan llegado a la barrera
            System.out.println("Se han calculado todos los nuevos estados...");
            });

        CyclicBarrier barrierEnvio = new CyclicBarrier(sizeT*sizeT, () -> {
                // Acción a ejecutar cuando todos los hilos hayan llegado a la barrera
                System.out.println("Se han enviado todos los estados de todas las celdas...");
                });

        barrierTurno = new CyclicBarrier(sizeT*sizeT*2, () -> {
            // Acción a ejecutar cuando todos los hilos hayan llegado a la barrera
            System.out.println("Han llegado todas a la misma generacion...");
            });

        Thread[][] productores = new Thread[size][size];  // Matriz de Threads productores
        Thread[][] consumidores = new Thread[size][size];  // Matriz de Threads consumidores

        // Leer el estado inicial del tablero desde el archivo
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                // Insertar buzones en el tablero
                tableroBuzones[i][j] = new Buzon(i + 1);
                int vecinos = contadorVecinos(i, j, size);
                // Creación de celdas productoras y consumidoras
                CeldaProductor productor = new CeldaProductor(tablero[i][j], i, j,barrierEnvio);
                CeldaConsumidor consumidor = new CeldaConsumidor(tableroBuzones[i][j], vecinos, barrierRecepcion,barrierCalculo, i, j);

                // Almacenar los Threads productores y consumidores
                productores[i][j] = new Thread(productor);
                consumidores[i][j] = new Thread(consumidor);

                // Ejecución de los Threads
                productores[i][j].start();
                consumidores[i][j].start();
            }
        }
        // Esperar a que todos los Threads productores y consumidores terminen
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                productores[i][j].join();
                consumidores[i][j].join();
            }
        }

        // Continuar con el main
        System.out.println("Todos los Threads productores y consumidores han terminado. Continuar con el main.");
            // Imprimir la matriz cargada desde el archivo
            imprimirMatriz();
            
        } 
        catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }

    }

    // Método para imprimir la matriz
    public static void imprimirMatriz() {
        for (boolean[] fila : tablero) {
            for (boolean celda : fila) {
                System.out.print(celda ? "■ " : "□ ");
            }
            System.out.println();
            }

    }

    public static int contadorVecinos(int i, int j, int N){
        int conteo = 0;
    
        // Verifica los vecinos en la fila superior
        if (i - 1 >= 0) {
            if (j - 1 >= 0) { // superior izquierda
                conteo++;
            }
            // superior centro
            conteo++;
            if (j + 1 < N) { // superior derecha
                conteo++;
            }
        }
        
        // Verifica los vecinos en la fila central
        if (j - 1 >= 0) { // centro izquierda
            conteo++;
        }
        if (j + 1 < N) { // centro derecha
            conteo++;
        }
        
        // Verifica los vecinos en la fila inferior
        if (i + 1 < N) {
            if (j - 1 >= 0) { // inferior izquierda
                conteo++;
            }
            // inferior centro
            conteo++;
            if (j + 1 < N) { // inferior derecha
                conteo++;
            }
        }
        return conteo;
    }

    public static void CalcularEstado(int iPos, int jPos, int totalVivos){
        if (tablero[iPos][jPos] == false && totalVivos == 3){
            tablero[iPos][jPos] = true;
        }
        if (tablero[iPos][jPos] == true){
            if(totalVivos > 3 || totalVivos == 0){
                tablero[iPos][jPos] = false;
            }
            else{
                tablero[iPos][jPos] = true;
            }
        }
    }

}