package com.Parfoo;

import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;

public class CryptoTest {

	private final static Logger LOG = Logger.getLogger(CryptoTest.class);
	private static String[] symbol={"ETHBTC","ETHBT"};
	
	@BeforeClass
	public static void beforeUrl(){
		for(int i=0; i<symbol.length;i++)
			LOG.info(symbol[i]);
	}
	
	@Test
	public void testCryptoTicker(){
		for(int i=0; i<symbol.length; i++){
			for(int j=0; j<Cryptos.ticker(symbol[i]).length; j++) {
				LOG.info(Cryptos.ticker(symbol[i])[j]);
			}
		}
	}
	
	public void testCryptoPrice() {
		for(int i=0; i<symbol.length; i++){
			LOG.info(Cryptos.getPrecio(symbol[i]));
		}
	}
	

}
