package juegoDeLaVida;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Tablero {
    public static void main(String[] args) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Ingrese el nombre del archivo:");
        try {
            String archivoEntrada = reader.readLine();

            BufferedReader br = new BufferedReader(new FileReader(archivoEntrada));
            
            // Leer el tamaño del tablero
            int size = Integer.parseInt(br.readLine());
            boolean[][] tablero = new boolean[size][size];

            // Leer el estado inicial del tablero desde el archivo
            for (int i = 0; i < size; i++) {
                String[] linea = br.readLine().split(",");
                for (int j = 0; j < size; j++) {
                    tablero[i][j] = Boolean.parseBoolean(linea[j]);
                }
            }

            // Imprimir la matriz cargada desde el archivo
            System.out.println("Matriz cargada desde " + archivoEntrada + ":");
            imprimirMatriz(tablero);

            br.close();
            reader.close();
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
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

