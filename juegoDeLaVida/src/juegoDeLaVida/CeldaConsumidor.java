package juegoDeLaVida;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CeldaConsumidor extends Thread {

    public Buzon buzon;
    public boolean estado;

    public int iPos;
    public int jPos;

    public int totalVecinos;
    public int totalVivos;
    private final CyclicBarrier barrierRecepcion; 
    private final CyclicBarrier barrierCalculo; 



    public CeldaConsumidor(Buzon buzon, int totalVecinos, CyclicBarrier barrierRecepcion,CyclicBarrier barrierCalculo, int iPos, int jPos) {
        this.buzon = buzon;
        this.totalVecinos = totalVecinos;
        this.totalVivos = 0;
        this.barrierRecepcion = barrierRecepcion;
        this.barrierCalculo = barrierCalculo;
        this.iPos = iPos;
        this.jPos = jPos;
    }

    private int recibirEstado(Buzon buzonEmisor){
        try {
            boolean estadoVecino = buzonEmisor.recibirEstado();
            if(estadoVecino == true){
                totalVivos++;
            }
            return 1;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public void run(){
        for(int i = 0; i < Tablero.generaciones; i++)
        {
            try {
                Tablero.barrierTurno.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        totalVivos = 0;
        int conteo = 0;
        boolean centinela = true;
        while(centinela){

            while(buzon.cola.size() == 0){
                Thread.yield();
            }

            conteo += recibirEstado(buzon);
            
            if(totalVecinos == conteo){
                centinela = false;
            }
        }
        try {
            barrierRecepcion.await();
            Tablero.CalcularEstado(iPos, jPos, totalVivos);
            barrierCalculo.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }

    }

        
    }
}
