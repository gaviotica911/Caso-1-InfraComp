package juegoDeLaVida;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Tablero {
    public static Celda[][] tableroCeldas;
    public static int sizeT;
    public static int generaciones;

    public static void main(String[] args) throws FileNotFoundException {
      
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Ingrese el nombre del archivo:");

        try (Scanner input = new Scanner(System.in)) {
            System.out.println("Ingrese el total de generaciones:");
            generaciones = input.nextInt();
        }
        try {
            String archivoEntrada = "juegoDeLaVida/src/data/" + reader.readLine();
            BufferedReader br = new BufferedReader(new FileReader(archivoEntrada));
            
            // Leer el tamaño del tablero
            int size = Integer.parseInt(br.readLine());
            sizeT=size;
            boolean[][] tablero = new boolean[size][size];
            tableroCeldas= new Celda[size][size];

            // Leer el estado inicial del tablero desde el archivo
            for (int i = 0; i < size; i++) {
                String[] linea = br.readLine().split(",");
                for (int j = 0; j < size; j++) {
                    tablero[i][j] = Boolean.parseBoolean(linea[j]);
                    tableroCeldas[i][j]= new Celda(new Buzon(i+1), tablero[i][j], i, j);
                }
            }

            // Imprimir la matriz cargada desde el archivo
            System.out.println("Tablero:");
            imprimirMatriz(tablero);

            br.close();
            
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }

        //empezar juego
        for (int i = 0; i < sizeT; i++) {
            for (int j = 0; j < sizeT; j++) {
                Celda celdaActual = tableroCeldas[i][j];
                celdaActual.start();
            }
        }
    }

    // Método para imprimir la matriz
    public static void imprimirMatriz(boolean[][] matriz) {
        for (boolean[] fila : matriz) {
            for (boolean celda : fila) {
                System.out.print(celda ? "■ " : "□ ");
            }
            System.out.println();
        }
    }

    

}

