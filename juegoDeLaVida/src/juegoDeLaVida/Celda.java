package juegoDeLaVida;

//import java.util.ArrayList;


public class Celda extends Thread {

    private Buzon buzon;
    private boolean estado;
   // private ArrayList<Buzon> listaBuzonesVecinos= new ArrayList<Buzon>() ;
    private int iPos;
    private int jPos; 

    private int vecinosVivos=0;
    private int vecinosMuertos=0;
    

    public Celda(Buzon buzon, boolean estado, int iPos, int jPos) {
        this.buzon = buzon;
        this.estado = estado;
        //this.listaBuzonesVecinos= listaBuzonesVecinos;
        this.iPos = iPos;
        this.jPos = jPos;

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

    @Override
    public void run(){

        //crear barrera

        // Envío del estado a vecinos de manera individual
        // Vecino derecho
        for(int i=0; i<Tablero.sizeT; i++){
            for(int j=0; j<Tablero.sizeT; j++){
               
                if (jPos < Tablero.sizeT - 1) {
                    enviarEstado(Tablero.tableroCeldas[iPos][jPos + 1].buzon);
                    boolean estadoParcial=recibirEstado(Tablero.tableroCeldas[i][j].buzon);
                    if(estadoParcial){
                        vecinosVivos++;
                    }else{
                        vecinosMuertos++;
                    }
                }

                // Vecino izquierdo
                if (jPos > 0) {
                    enviarEstado(Tablero.tableroCeldas[iPos][jPos - 1].buzon);
                    
                }

                // Vecino arriba
                if (iPos > 0) {
                    enviarEstado(Tablero.tableroCeldas[iPos-1][jPos ].buzon);
                    
                }

                // Vecino abajo
                if (iPos < Tablero.sizeT - 1) {
                    enviarEstado(Tablero.tableroCeldas[iPos+1][jPos ].buzon);
                    
                }

                // Vecino diagonal arriba derecha
                if (iPos > 0 && jPos < Tablero.sizeT - 1) {
                    enviarEstado(Tablero.tableroCeldas[iPos-1][jPos+1 ].buzon);
                    
                }

                // Vecino diagonal arriba izquierda
                if (iPos > 0 && jPos > 0) {
                    enviarEstado( Tablero.tableroCeldas[iPos - 1][jPos - 1 ].buzon);
                
                }

                // Vecino diagonal abajo derecha
                if (iPos < Tablero.sizeT - 1 && jPos < Tablero.sizeT - 1) {
                    enviarEstado( Tablero.tableroCeldas[iPos + 1][jPos + 1 ].buzon);
                
                }

                // Vecino diagonal abajo izquierda
                if (iPos < Tablero.sizeT - 1 && jPos > 0) {
                    enviarEstado(Tablero.tableroCeldas[iPos + 1][jPos - 1 ].buzon);
                    
                }

                
                //manejar await
                //calcularlo
                //estado=estadoParcial;
                //imprimir el nuevo tableroa ver como se ve 
            }
        }

       


    }


    
    
}
