package com.Parfoo;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.BeforeClass;
import org.junit.Test;

public class JsonReaderTest {
	private final static Logger LOG = Logger.getLogger(JsonReaderTest.class);
	private	static JsonReader jr = new JsonReader();
	private static String url="https://api.binance.com/api/v1/ticker/24hr?symbol=";
	private static String[] symbol={"ETHBTC","ETHBT"};
	
	@BeforeClass
	public static void beforeUrl(){
		LOG.info(url+symbol[0]);
		LOG.info(url+symbol[1]);
	}
	
	@Test
	public void testJson() {
		JSONObject json;
		for(int i=0; i<symbol.length;i++){
			try {
				json = jr.readJsonFromUrl(url+symbol[i]);
				Criptomoneda cripto = jr.setCriptoDataTicker(json);
				LOG.info(json.toString());
			} catch (IOException e) {
				LOG.error(e.toString());
				e.printStackTrace();
			} catch (JSONException e) {
				LOG.error(e.toString());
			}
		}
		
	}
}
