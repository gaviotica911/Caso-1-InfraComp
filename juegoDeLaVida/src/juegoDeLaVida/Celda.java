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
                    System.out.println("se va a enviar el estado de la celda [" + iPos + "," + jPos + "] a la celda [" + iPos + "," + (jPos+1) + "]");
                    enviarEstado(Tablero.tableroCeldas[iPos][jPos + 1].buzon);
                    boolean estadoParcial=recibirEstado(Tablero.tableroCeldas[i][j].buzon);
                    if(estadoParcial){
                        vecinosVivos++;
                        System.out.println("El contador de vecinos vivos para la celda [" + iPos + "," + jPos + "] es: " + vecinosVivos);
                    }else{
                        vecinosMuertos++;
                        System.out.println("El contador de vecinos muertos para la celda [" + iPos + "," + jPos + "] es: " + vecinosVivos);
                    }
                }

                // Vecino izquierdo
                if (jPos > 0) {
                    System.out.println("se va a enviar el estado de la celda [" + iPos + "," + jPos + "] a la celda [" + iPos + "," + (jPos-1) + "]");
                    enviarEstado(Tablero.tableroCeldas[iPos][jPos - 1].buzon);
                    boolean estadoParcial=recibirEstado(Tablero.tableroCeldas[i][j].buzon);
                    if(estadoParcial){
                        vecinosVivos++;
                        System.out.println("El contador de vecinos vivos para la celda [" + iPos + "," + jPos + "] es: " + vecinosVivos);
                    }else{
                        vecinosMuertos++;
                        System.out.println("El contador de vecinos muertos para la celda [" + iPos + "," + jPos + "] es: " + vecinosVivos);
                    }
                }

                // Vecino arriba
                if (iPos > 0) {
                    System.out.println("se va a enviar el estado de la celda [" + iPos + "," + jPos + "] a la celda [" + (iPos-1) + "," + (jPos) + "]");
                    enviarEstado(Tablero.tableroCeldas[iPos-1][jPos ].buzon);
                    boolean estadoParcial=recibirEstado(Tablero.tableroCeldas[i][j].buzon);
                    if(estadoParcial){
                        vecinosVivos++;
                        System.out.println("El contador de vecinos vivos para la celda [" + iPos + "," + jPos + "] es: " + vecinosVivos);
                    }else{
                        vecinosMuertos++;
                        System.out.println("El contador de vecinos muertos para la celda [" + iPos + "," + jPos + "] es: " + vecinosVivos);
                    }
                    
                }

                // Vecino abajo
                if (iPos < Tablero.sizeT - 1) {
                    System.out.println("se va a enviar el estado de la celda [" + iPos + "," + jPos + "] a la celda [" + (iPos+1) + "," + (jPos) + "]");
                    enviarEstado(Tablero.tableroCeldas[iPos+1][jPos ].buzon);
                    boolean estadoParcial=recibirEstado(Tablero.tableroCeldas[i][j].buzon);
                    if(estadoParcial){
                        vecinosVivos++;
                        System.out.println("El contador de vecinos vivos para la celda [" + iPos + "," + jPos + "] es: " + vecinosVivos);
                    }else{
                        vecinosMuertos++;
                        System.out.println("El contador de vecinos muertos para la celda [" + iPos + "," + jPos + "] es: " + vecinosVivos);
                    }
                    
                }

                // Vecino diagonal arriba derecha
                if (iPos > 0 && jPos < Tablero.sizeT - 1) {
                    System.out.println("se va a enviar el estado de la celda [" + iPos + "," + jPos + "] a la celda [" + (iPos-1) + "," + (jPos+1) + "]");
                    enviarEstado(Tablero.tableroCeldas[iPos-1][jPos+1 ].buzon);
                    boolean estadoParcial=recibirEstado(Tablero.tableroCeldas[i][j].buzon);
                    if(estadoParcial){
                        vecinosVivos++;
                        System.out.println("El contador de vecinos vivos para la celda [" + iPos + "," + jPos + "] es: " + vecinosVivos);
                    }else{
                        vecinosMuertos++;
                        System.out.println("El contador de vecinos muertos para la celda [" + iPos + "," + jPos + "] es: " + vecinosVivos);
                    }
                    
                }

                // Vecino diagonal arriba izquierda
                if (iPos > 0 && jPos > 0) {
                    System.out.println("se va a enviar el estado de la celda [" + iPos + "," + jPos + "] a la celda [" + (iPos-1) + "," + (jPos-1) + "]");
                    enviarEstado( Tablero.tableroCeldas[iPos - 1][jPos - 1 ].buzon);
                    boolean estadoParcial=recibirEstado(Tablero.tableroCeldas[i][j].buzon);
                    if(estadoParcial){
                        vecinosVivos++;
                        System.out.println("El contador de vecinos vivos para la celda [" + iPos + "," + jPos + "] es: " + vecinosVivos);
                    }else{
                        vecinosMuertos++;
                        System.out.println("El contador de vecinos muertos para la celda [" + iPos + "," + jPos + "] es: " + vecinosVivos);
                    }
                
                }

                // Vecino diagonal abajo derecha
                if (iPos < Tablero.sizeT - 1 && jPos < Tablero.sizeT - 1) {
                    System.out.println("se va a enviar el estado de la celda [" + iPos + "," + jPos + "] a la celda [" + (iPos+1) + "," + (jPos+1) + "]");
                    enviarEstado( Tablero.tableroCeldas[iPos + 1][jPos + 1 ].buzon);
                    boolean estadoParcial=recibirEstado(Tablero.tableroCeldas[i][j].buzon);
                    if(estadoParcial){
                        vecinosVivos++;
                        System.out.println("El contador de vecinos vivos para la celda [" + iPos + "," + jPos + "] es: " + vecinosVivos);
                    }else{
                        vecinosMuertos++;
                        System.out.println("El contador de vecinos muertos para la celda [" + iPos + "," + jPos + "] es: " + vecinosVivos);
                    }
                
                }

                // Vecino diagonal abajo izquierda
                if (iPos < Tablero.sizeT - 1 && jPos > 0) {
                    System.out.println("se va a enviar el estado de la celda [" + iPos + "," + jPos + "] a la celda [" + (iPos+1) + "," + (jPos-1) + "]");
                    enviarEstado(Tablero.tableroCeldas[iPos + 1][jPos - 1 ].buzon);
                    boolean estadoParcial=recibirEstado(Tablero.tableroCeldas[i][j].buzon);
                    if(estadoParcial){
                        vecinosVivos++;
                        System.out.println("El contador de vecinos vivos para la celda [" + iPos + "," + jPos + "] es: " + vecinosVivos);
                    }else{
                        vecinosMuertos++;
                        System.out.println("El contador de vecinos muertos para la celda [" + iPos + "," + jPos + "] es: " + vecinosVivos);
                    }
                    
                }

                
                //manejar await
                //calcularlo
                //estado=estadoParcial;
                //imprimir el nuevo tableroa ver como se ve 
            }
        }

       


    }


    
    
}
