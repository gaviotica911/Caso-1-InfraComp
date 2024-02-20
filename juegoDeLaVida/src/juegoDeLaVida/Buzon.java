package juegoDeLaVida;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ArrayBlockingQueue;

public class Buzon {
    
    public BlockingQueue<Boolean> cola;
    private int capacidad;


    public Buzon(int capacidad) {
        this.capacidad = capacidad;
        this.cola = new ArrayBlockingQueue<Boolean>(capacidad);
    }

    public synchronized void enviarEstado(boolean estado) throws InterruptedException{
        
        while(cola.size()==capacidad){
            wait();
        }
        cola.add(estado);
        notify();
    }


    public synchronized boolean recibirEstado() throws InterruptedException{

        boolean estado = cola.take();
        notify();
        return estado;
    }

}
