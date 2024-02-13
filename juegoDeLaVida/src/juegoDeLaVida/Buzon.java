package juegoDeLaVida;

import java.util.ArrayList;

public class Buzon {
    private ArrayList<Boolean> cola;
    private int capacidad;


    public Buzon(int capacidad) {
        this.capacidad = capacidad;
        this.cola = new ArrayList<Boolean>();//revisar el tamaño porsi
    }

    public synchronized void recibirEstado(boolean estado) throws InterruptedException{ //lo recibo de
        
        while(cola.size()==capacidad){
            wait();
        }
        cola.add(estado);
        notify();
    }


    public synchronized boolean enviarEstado() throws InterruptedException{
        while(cola.size()==0){
            wait();//yield por la espera semiactiva
        }
        boolean estado = cola.get(0);
        cola.remove(0);
        notify();
        return estado;
    }


}
