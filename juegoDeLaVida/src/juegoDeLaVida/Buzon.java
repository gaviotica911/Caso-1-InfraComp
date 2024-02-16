package juegoDeLaVida;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ArrayBlockingQueue;

public class Buzon {
    
    private BlockingQueue<Boolean> cola;
    private int capacidad;


    public Buzon(int capacidad) {
        this.capacidad = capacidad;
        this.cola = new ArrayBlockingQueue<Boolean>(capacidad);
    }

    public synchronized void enviarEstado(boolean estado) throws InterruptedException{
        
        while(cola.size()==capacidad){
            //System.out.println("estoy lleno");
            wait();
        }
        cola.add(estado);
        //System.out.println("Estado enviado correctamente");
        notify();
    }


    public synchronized boolean recibirEstado() throws InterruptedException{
        while(cola.size()==0){
            wait();//yield por la espera semiactiva
        }
        boolean estado = cola.take();
        //System.out.println("Estado recibido correctamente");
        notify();
        return estado;
    }

}
