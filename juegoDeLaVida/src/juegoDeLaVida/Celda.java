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




    @Override
    public void run(){

        //crear barrera

        // Envío del estado a vecinos de manera individual
        // Vecino derecho

                
                //manejar await
                //calcularlo
                //estado=estadoParcial;
                //imprimir el nuevo tableroa ver como se ve 
            }
        }
    


    
    
