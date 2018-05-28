package com.Parfoo;

import org.apache.log4j.Logger;

public class SendData {
	private final static Logger LOG = Logger.getLogger(SendData.class);
	private String link;
	private PetitionREST rest;
	private double data[];
	private String parameters[] = {"count", "highPrice", "lowPrice", "openPrice", "priceChangePercent"};
	
	public SendData(String link) {
		this.link = link;
		this.rest = new PetitionREST();
	}
	
	public void setParameters(String symbol) {
		data = Cryptos.ticker(symbol);
	}
	
	public String parseParameters() {
		String params = "";
		for (int i = 0; i < data.length; i++) {
			params += this.parameters[i] + "=" + this.data[i] + "&";
		}
		params = params.substring(0, params.length() - 1);
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
