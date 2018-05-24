package mx.com.parfoo.tablero;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.log4j.Logger;

/*
 * El algoritmo fue desarrollado bajo los siguientes supuestos:
 *      Un individuo nunca comprar� cryptos cuando el precio est� subiendo
 *      Un individuo nunca vender� cryptos cuando el precio est� bajando
 * 
 * Antes de probar en ambiente de producci�n se utilizan n�meros random para
 * simular variaciones en el precio a lo largo del tiempo
 * 
 * Mejoras:
 *      Solo se toman en cuenta cambios en el precio consecutivos. Lo cual, en 
 *      la vida real, prodr�a llevar a perdidas. Se debe "recordar" el precio 
 *      al que se compr� para decidir si en un decremento en el precio vale la pena vender.
 *
 *      Falta a�adir la conexi�n al WebService
 * 
 */
public class Algoritmo {
    private final static Logger LOGGER = Logger.getLogger(Algoritmo.class);
    private final double SELL_ON_HIGH_PERCENTAGE;
    private final double BUY_ON_LOW_PERCENTAGE;
    private final double CONTROLLED_BUY_PERCENTAGE;
    private ArrayList<Double> pseudoCalls;
    private double currentMoney, currentCrypto;
    
    /**
     * 
     * @param initMoney Cantidad de dinero inicial del usuario
     * @param initCrypto Cantidad de cryptoactivos inicial del usuario
     * @param sellHighPercentage Porcentaje de aumento de precio para venta
     * @param buyLowPercentage Porcentaje de disminuci�n de precio para venta
     * @param controlledBuyPercentage Porcentaje de utilizaci�n de dinero para venta controlada
     */
    public Algoritmo(double initMoney, double initCrypto, double sellHighPercentage, double buyLowPercentage, double controlledBuyPercentage){
        this.pseudoCalls = new ArrayList<>();
        this.currentMoney = initMoney;
        this.currentCrypto = initCrypto;
        this.SELL_ON_HIGH_PERCENTAGE = sellHighPercentage;
        this.BUY_ON_LOW_PERCENTAGE = buyLowPercentage;
        this.CONTROLLED_BUY_PERCENTAGE = controlledBuyPercentage;
        fill(); //Para pruebas
    }
    
    /*
    * M�todo que a�ade a un ArrayList numeros al azar multiplicados por una constante.
    * Son simulaciones de cambios en el precio a lo largo del tiempo.
    * Para simular peticiones al WebService
    */
    private void fill(){
        for (int i = 0; i < 100; i++) {
            this.pseudoCalls.add(Math.random() * 2500);
        }
    }
    
    /**
     * @param amoutSellPercentage Porcentaje de cryptos a vender en una transacci�n
     * @param cryptoBuyCuantity Cantidad de cryptos a comprar en una transacci�n
     * 
     * Algoritmo:
     * 
     * Mientras corra el algoritmo:
     *     Se checa la diferencia entre el precio actual y anterior:
     *         Si es positiva checamos si el % de cambio supera el porcentaje para venta dado por el usuario:
     *             Si es menor solo recomendaremos vender
     *             en caso contrario vendemos dado el moutSellPercentage y actualizamos cantidades de dinero y de crypto.
     * 
     *         Si es negativa checamos si el % de cambio supera el porcentaje para compra dado por el usuario:
     *             Si es menor solo recomendaremos comprar
     *             en caso contrario:
     *                 Checamos si el usuario tiene dinero:
     *                     Si NO lo tiene seguimos a la pr�xima iteraci�n del algoritmo
     *                     en caso contrario checamos si tiene dinero suficiente para comprar la cantidad deseada:
     *                         Si la tiene compramos y actualizamos cantidades de dinero y de crypto
     *                         en caso contrario calculamos la cantidad de compra controlada (Solo se compra con un porcentaje %, dado por el usuario, del dinero total)
     *                         checamos si tiene dinero suficiente para comprar la cantidad deseada:
     *                             Si la tiene compramos y actualizamos cantidades de dinero y de crypto
     *                             en caso contrario seguimos a la pr�xima iteraci�n del algoritmo
     * 
     * Fin del algoritmo cuando el usuario as� lo decida
     */
    public void buySellAlgorithm(double amoutSellPercentage, double cryptoBuyCuantity){
        Iterator it = this.pseudoCalls.iterator();
        double difference, percentage, sufficientMoney, currentPrice, prevPrice = pseudoCalls.get(0);
        int i = 0;
                
        while(it.hasNext()){
            currentPrice = (double) it.next();
            difference = currentPrice - prevPrice;
            
            System.out.println("Momento i = " + ++i);
            System.out.println("Price: " + currentPrice + " MN: " + this.currentMoney + " CR: " + this.currentCrypto);
            
            if(difference >= 0){//Venta en caso de alta de precio
                percentage = difference / prevPrice;
                
                if(percentage < this.SELL_ON_HIGH_PERCENTAGE){
                    System.out.println("Recomendation: Sell on High");
                } else {
                    if(this.currentCrypto > 0){//Tengo cryptos para vender
                        System.out.println("Action: Sell on High");
                        this.currentMoney += this.currentCrypto * amoutSellPercentage * currentPrice;
                        this.currentCrypto -= this.currentCrypto * amoutSellPercentage;
                    }
                }
            } else {//Venta o compra en caso de baja de precio
                percentage = (-1) * difference / prevPrice;
                
                if(percentage < this.BUY_ON_LOW_PERCENTAGE){
                    System.out.println("Recomendation: Buy on Low");
                } else {
                    if(this.currentMoney > 0){//Tengo dinero para comprar
                        sufficientMoney = this.currentMoney - cryptoBuyCuantity * currentPrice;
                        
                        if(sufficientMoney >= 0){ //Me alcanza para comprar la cantidad que quiero
                            System.out.println("Action: Buy on Low");
                            this.currentMoney -= cryptoBuyCuantity * currentPrice;
                            this.currentCrypto += cryptoBuyCuantity;
                        } else { //Compra con cantidad controlada (Se utiliza un % de activos del usuario)
                            double controlledMoney = this.currentMoney * this.CONTROLLED_BUY_PERCENTAGE; //Nueva cantidad a gastar
                            double controlledCrypto = controlledMoney / currentPrice; //Nueva cantidad a comprar
                            sufficientMoney = this.currentMoney - controlledCrypto * currentPrice;
                            
                            if(sufficientMoney >= 0){ //Me alcanza para comprar la cantidad que quiero
                                System.out.println("Action: Buy on Low Controlled");
                                this.currentMoney -= controlledCrypto * currentPrice;
                                this.currentCrypto += controlledCrypto;
                            } //Si sigue sin alcanzar entonces no se hace compra
                        }
                    }
                }
            }
            prevPrice = currentPrice;
            System.out.println("");
        }
    }
    
    public static void main(String[] args) {
        Algoritmo a = new Algoritmo(53450, 5, 1, 0.6, .15);
        a.buySellAlgorithm(.15, .5);
    }
}