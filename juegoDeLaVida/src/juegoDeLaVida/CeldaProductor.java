package juegoDeLaVida;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import java.util.concurrent.Semaphore;


public class CeldaProductor extends Thread{
    private boolean estado;

    private int iPos;
    private int jPos;
    private CyclicBarrier barrierEnvio;

    private static Semaphore semaforoImprimir = new Semaphore(1);
    private static boolean mensajeImpreso = false;

    public CeldaProductor(boolean estado, int iPos, int jPos, CyclicBarrier barrierEnvio){
        this.estado = estado;
        this.iPos = iPos;
        this.jPos = jPos;
        this.barrierEnvio = barrierEnvio;
    }

    private void enviarEstado(Buzon buzonDestinatario){
        try {
            buzonDestinatario.enviarEstado(estado);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run(){
        for(int i = 0; i < Tablero.generaciones; i++)
        {

            try {
                Tablero.barrierTurno.await();
                boolean[][] matriz = new boolean[Tablero.sizeT][Tablero.sizeT];
                for (int m = 0; m < Tablero.sizeT; m++) {
                    for  (int n = 0; n < Tablero.sizeT; n++) {
                        matriz[m][n] = Tablero.tablero[m][n];
                    }
                }
                Tablero.listado.add(matriz);
            }
            catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }finally {
                // Liberamos el semáforo
                semaforoImprimir.release();
            }
            actualizarEstado();
            
            try {
                enviarEstado(Tablero.tableroBuzones[iPos - 1][jPos]);
            } catch (Exception e) {
                // Aquí maneja la excepción específica que ocurre al tratar de obtener el vecino de arriba
            }
            // vecino abajo [i+1][j]
            try {
                enviarEstado(Tablero.tableroBuzones[iPos + 1][jPos]);
            } catch (Exception e) {
                // Aquí maneja la excepción específica que ocurre al tratar de obtener el vecino de abajo
            }
            // vecino derecho [i][j+1]
            try {
                enviarEstado(Tablero.tableroBuzones[iPos][jPos + 1]);
            } catch (Exception e) {
                // Aquí maneja la excepción específica que ocurre al tratar de obtener el vecino de la derecha
            }
            // vecino izquierdo [i][j-1]
            try {
                enviarEstado(Tablero.tableroBuzones[iPos][jPos - 1]);
            } catch (Exception e) {
                // Aquí maneja la excepción específica que ocurre al tratar de obtener el vecino de la izquierda
            }
            // vecino arriba derecha [i-1][j+1]
            try {
                enviarEstado(Tablero.tableroBuzones[iPos - 1][jPos + 1]);
            } catch (Exception e) {
                // Aquí maneja la excepción específica que ocurre al tratar de obtener el vecino de arriba derecha
            }
            // vecino arriba izquierdo [i-1][j-1]
            try {
                enviarEstado(Tablero.tableroBuzones[iPos - 1][jPos - 1]);
            } catch (Exception e) {
                // Aquí maneja la excepción específica que ocurre al tratar de obtener el vecino de arriba izquierda
            }
            // vecino abajo derecha [i+1][j+1]
            try {
                enviarEstado(Tablero.tableroBuzones[iPos + 1][jPos + 1]);
            } catch (Exception e) {
                // Aquí maneja la excepción específica que ocurre al tratar de obtener el vecino de abajo derecha
            }
            // vecino abajo izquierdo [i+1][j-1]
            try {
                enviarEstado(Tablero.tableroBuzones[iPos + 1][jPos - 1]);
            } catch (Exception e) {
                // Aquí maneja la excepción específica que ocurre al tratar de obtener el vecino de abajo izquierda
            }
            try {
                barrierEnvio.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        
        }
    
    }
    public void actualizarEstado(){
        boolean elEstado = Tablero.tablero[iPos][jPos];
        this.estado = elEstado;
    }

    
}
