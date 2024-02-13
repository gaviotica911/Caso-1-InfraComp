package juegoDeLaVida;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ArrayBlockingQueue;

public class Buzon {
    
    private BlockingQueue<Boolean> cola;
    private int capacidad;


    public Buzon(int capacidad) {
        this.capacidad = capacidad;
        this.cola = new ArrayBlockingQueue<Boolean>(capacidad);//revisar el tamaño porsi
    }

    public synchronized void recibirEstado(boolean estado) throws InterruptedException{ //lo recibo de
        
        while(cola.remainingCapacity()==0){
            wait();
        }
        cola.add(estado);
        notify();
    }


    public synchronized boolean enviarEstado() throws InterruptedException{
        while(cola.size()==0){
            wait();//yield por la espera semiactiva
        }
        boolean estado = cola.take();

        notify();
        return estado;
    }


}
