package com.Parfoo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

/*
 * El algoritmo fue desarrollado bajo los siguientes supuestos:
 *      Un individuo nunca comprará cryptos cuando el precio está subiendo
 *      Un individuo nunca venderá cryptos cuando el precio está bajando
 * 
 * Antes de probar en ambiente de producción se utilizan números random para
 * simular variaciones en el precio a lo largo del tiempo
 * 
 * Mejoras:
 *      Solo se toman en cuenta cambios en el precio consecutivos. Lo cual, en 
 *      la vida real, prodría llevar a perdidas. Se debe "recordar" el precio 
 *      al que se comprá para decidir si en un decremento en el precio vale la pena vender.
 *
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
     * @param buyLowPercentage Porcentaje de disminución de precio para venta
     * @param controlledBuyPercentage Porcentaje de utilización de dinero para venta controlada
     */
    public Algoritmo(double initMoney, double initCrypto, double sellHighPercentage, double buyLowPercentage, double controlledBuyPercentage){
        this.pseudoCalls = new ArrayList<>();
        this.currentMoney = initMoney;
        this.currentCrypto = initCrypto;
        this.SELL_ON_HIGH_PERCENTAGE = sellHighPercentage;
        this.BUY_ON_LOW_PERCENTAGE = buyLowPercentage;
        this.CONTROLLED_BUY_PERCENTAGE = controlledBuyPercentage;
        //fill(); //Para pruebas
    }
    
    /*
    * Método que añade a un ArrayList numeros al azar multiplicados por una constante.
    * Son simulaciones de cambios en el precio a lo largo del tiempo.
    * Para simular peticiones al WebService
    */
    private void fill(){
        for (int i = 0; i < 100; i++) {
            this.pseudoCalls.add(Math.random() * 2500);
        }
    }
    
    /**
     * @param symbol Simbolo de la cryptomoneda a analizar
     * @param amoutSellPercentage Porcentaje de cryptos a vender en una transacción
     * @param cryptoBuyCuantity Cantidad de cryptos a comprar en una transacción
     * @param iterations Número de segundos a correr el algoritmo
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
     *                     Si NO lo tiene seguimos a la próxima iteración del algoritmo
     *                     en caso contrario checamos si tiene dinero suficiente para comprar la cantidad deseada:
     *                         Si la tiene compramos y actualizamos cantidades de dinero y de crypto
     *                         en caso contrario calculamos la cantidad de compra controlada (Solo se compra con un porcentaje %, dado por el usuario, del dinero total)
     *                         checamos si tiene dinero suficiente para comprar la cantidad deseada:
     *                             Si la tiene compramos y actualizamos cantidades de dinero y de crypto
     *                             en caso contrario seguimos a la próxima iteración del algoritmo
     * 
     * Fin del algoritmo cuando el usuario así lo decida
     */
    public void buySellAlgorithm(String symbol, double amoutSellPercentage, double cryptoBuyCuantity, int iterations){
        //Iterator<Double> it = this.pseudoCalls.iterator();
    		double difference, percentage, sufficientMoney, currentPrice, prevPrice = Cryptos.getPrecio(symbol);
        int i = 0;
                
        while(i++ < iterations){
        	
			try {
				TimeUnit.SECONDS.sleep(1); //Se espera 1 segundo entre cada iteración del algoritmo
			} catch (InterruptedException e) {
				LOGGER.error(e.getMessage().toString());
			} 
			
            currentPrice = Cryptos.getPrecio(symbol);
            difference = currentPrice - prevPrice;
            
            System.out.println("Second i = " + i);
            System.out.println("Price: " + currentPrice + " MN: " + this.currentMoney + " CR: " + this.currentCrypto + "\n");
            LOGGER.info("Price: " + currentPrice + " MN: " + this.currentMoney + " CR: " + this.currentCrypto);
            
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
        }
    }
    
    public static void main(String[] args) {
        Algoritmo a = new Algoritmo(53450, 5, 1, 0.6, .15);
        a.buySellAlgorithm("BTCUSDT", .15, .5, 20);
    }
}