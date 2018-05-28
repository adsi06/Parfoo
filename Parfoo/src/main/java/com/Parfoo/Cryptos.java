package com.Parfoo;

/**
 * En la clase Cryptos se piden los datos al API de binance para las distintas
 * criptomonedas y que regrese la información para que se pueda utilizar
 */

import java.io.IOException;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
public class Cryptos 
{
	
	public static String URLprice="https://api.binance.com/api/v1/ticker/price?symbol=";
	public static String URLticker="https://api.binance.com/api/v1/ticker/24hr?symbol=";
	private final static Logger LOG = Logger.getLogger(JsonReader.class);
	public Criptomoneda cryp = new Criptomoneda();
	
	/**
	 * Se pide el precio de la criptomoneda
	 * Se regresa en un double
	 * @param symbol
	 * @return
	 */
	public static double precio(String symbol)  {

		double ret=0;
		JsonReader jr = new JsonReader();
  		JSONObject json;
		try {
			json = jr.readJsonFromUrl(URLprice+symbol);
			Criptomoneda cripto = jr.setPrice(json);
			ret=cripto.getPrice();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			LOG.error(e.toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			ret=0;
		}
  		return ret; 
	}
	
	/**
	 * Se pide el cambio en el precio de la criptomoneda
	 * Se regresa en un double
	 * @param symbol
	 * @return
	 */
	public static double percentage(String symbol)  {

		double ret=0;
		JsonReader jr = new JsonReader();
  		JSONObject json;
		try {
			json = jr.readJsonFromUrl(URLprice+symbol);
			Criptomoneda cripto = jr.setPrice(json);
			ret=cripto.getPriceChangePercent();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			LOG.error(e.toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			ret=0;
		}
  		return ret; 
	}
	
	/**
	 * Se piden los datos más importantes de la criptomoneda
	 * Se regresan en un array
	 * @param symbol
	 * @return
	 */
	public static double[] ticker(String symbol){
		double[] tick=new double[5];
		JsonReader jr = new JsonReader();
  		JSONObject json;
		try {
			json = jr.readJsonFromUrl(URLticker+symbol);
			Criptomoneda cripto = jr.setCriptoDataTicker(json);
			tick[0]=(double)cripto.getCount();
			tick[1]=cripto.getHighPrice();
			tick[2]=cripto.getLowPrice();
			tick[3]=cripto.getOpenPrice();
			tick[4]=cripto.getPriceChangePercent();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			LOG.error(e.toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			LOG.error(e.toString());
		}
  		return tick; 
	}

}
