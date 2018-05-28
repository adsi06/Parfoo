package com.Parfoo;

/**
 * En la clase JSonReader se lee el Json que regresa el API de binance
 * Se le asignan los valores al objeto de Criptomoneda para que pueda ser utilizado
 * y sacar información
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;


public class JsonReader {
	private final static Logger LOG = Logger.getLogger(JsonReader.class);
	
	/**
	 * Se leen los datos de un json dado un url
	 * Se regresa un String con la información del Json
	 * @param url
	 * @return
	 * @throws IOException
	 * @throws JSONException
	 */
	public JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
		InputStream is = new URL(url).openStream();
		try {
			BufferedReader bfReader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			String jsonText = parseData(bfReader);
			JSONObject json = new JSONObject(jsonText);
			return json;
		} finally {
			is.close();
		}
	}
	
	private String parseData(Reader reader) throws IOException {
		String cad = "";
		int character;
		while ((character = reader.read()) != -1) {
			cad += (char) character;
		}
		return cad;
	}
  
	/**
	 * Se le asignan los datos de ticker al objeto de Criptomoneda
	 * Se regresa un objeto de tipo criptomoneda
	 * @param data
	 * @return
	 */
	public Criptomoneda setCriptoDataTicker(JSONObject data) {
		Criptomoneda cripto = new Criptomoneda();
		
		try {
			cripto.setSymbol(data.getString("symbol"));
			cripto.setCount(data.getInt("count"));
			cripto.setHighPrice(data.getDouble("highPrice"));
			cripto.setLowPrice(data.getDouble("lowPrice"));
			cripto.setOpenPrice(data.getDouble("openPrice"));
			cripto.setPriceChangePercent(data.getDouble("priceChangePercent"));
		} catch(Exception ex) {
			LOG.error(ex.toString());
		}
		return cripto;
	}
	
	/**
	 * Se le asigna el dato de precio al objeto Criptomoneda
	 * Se regresa un objeto de tipo criptomoneda
	 * @param data
	 * @return
	 */
	public Criptomoneda setPrice(JSONObject data) {
		Criptomoneda cripto = new Criptomoneda();
		try {
			cripto.setPrice(data.getDouble("price"));
		} catch (JSONException ex) {
			LOG.error(ex.toString());
		}
		return cripto;
	}
	
}
