package juegoDeLaVida;

public class Celda extends Thread {

    private Buzon buzon;
    private boolean estado;

    public Celda(Buzon buzon, boolean estado) {
        this.buzon = buzon;
        this.estado = estado;
    }


    private void enviarEstado(Buzon buzonDestinatario){
        try {
            buzonDestinatario.recibirEstado(estado);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private boolean recibirEstado(Buzon buzonEmisor){
        try {
            estado = buzonEmisor.enviarEstado();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return estado;
    }


    
    
}
