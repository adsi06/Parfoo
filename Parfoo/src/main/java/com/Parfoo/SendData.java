package com.Parfoo;

import org.apache.log4j.Logger;

public class SendData {
	private final static Logger LOG = Logger.getLogger(SendData.class);
	private String link;
	private Algoritmo a;
	private Recomendacion rec;
	private PetitionREST rest;
	private double data[];
	private String parameters[] = {"count", "highPrice", "lowPrice", "openPrice", "priceChangePercent"};
	
	public SendData(String link) {
		this.link = link;
		this.rest = new PetitionREST();
	}
	
	public void setParameters(String symbol) {
		data = Cryptos.ticker(symbol);
		a = new Algoritmo(symbol, 1000, 5, 1.0, 0.9, 0.7);
		rec = a.buySellAlgorithm(0.2, 0.5, data[4]);
	}
	
	public String parseParameters() {
		String params = "";
		for (int i = 0; i < data.length; i++) {
			params += this.parameters[i] + "=" + this.data[i] + "&";
		}
		params += "buy=" + this.rec.toString();
		LOG.info(params);
		return params;
	}
	
	public void sendGet(String symbol) {
		this.setParameters(symbol);
		boolean resp = this.rest.sendGET(this.link + "?" + parseParameters());
		System.out.println(resp);
	}
	
	public void sendPost(String symbol) {
		this.setParameters(symbol);
		System.out.println(this.link);
		System.out.println(parseParameters());
		boolean resp = this.rest.sendPOST(this.link, parseParameters());
		System.out.println(resp);
	}
	
	public static void main(String[] args) {
		SendData data = new SendData("http://localhost:1337/data");
		for (int i = 0; i < 200; i++) {
			data.sendPost("BTCUSDT");	
		}
	}
}
