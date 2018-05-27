package com.Parfoo;

import static org.junit.Assert.*;

import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class PetitionRESTTest {
	
	private final static Logger LOG = Logger.getLogger(PetitionRESTTest.class);
	private final static PetitionREST p = new PetitionREST();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		LOG.info("Inicializando la prueba");
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		LOG.info("Fin de la prueba");
	}

	@Test
	public void testGET() {
		boolean hasSuccessed;
		String url = "http://www.google.com/search?q=tesla";
		LOG.info("Iniciando prueba GET...");
		LOG.info("URL a usar -> " + url);
		hasSuccessed = p.sendGET(url);
		if (hasSuccessed) {
			LOG.info("Prueba exitosa");
		} else {
			LOG.info("No pasó la prueba");
		}
		assertTrue("Con la URL: " + url, hasSuccessed);
	}
	
	@Test
	public void testPOST() {
		boolean hasSuccessed;
		String url = "https://chart.googleapis.com/chart",
				parameters = "cht=lc&chtt=MyChart&chs=600x200"
						+ "&chxt=x,y&chd=t:40,20,50,20,100";
		LOG.info("Iniciando prueba POST...");
		LOG.info("URL a usar -> " + url);
		LOG.info("Parámetros a usar -> " + parameters);
		hasSuccessed = p.sendPOST(url, parameters);
		if (hasSuccessed) {
			LOG.info("Prueba exitosa");
		} else {
			LOG.info("No pasó la prueba");
		}
		assertTrue("Con la URL: " + url, hasSuccessed);
	}

}
