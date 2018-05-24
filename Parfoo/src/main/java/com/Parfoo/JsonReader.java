package com.Parfoo;

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
