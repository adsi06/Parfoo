package com.Parfoo;

public class Criptomoneda {
	private String symbol;
	private int count;
	private double openPrice;
	private double lowPrice;
	private double highPrice;
	private double priceChangePercent;
	private double price;
	
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public double getOpenPrice() {
		return openPrice;
	}
	public void setOpenPrice(double openPrice) {
		this.openPrice = openPrice;
	}
	public double getLowPrice() {
		return lowPrice;
	}
	public void setLowPrice(double lowPrice) {
		this.lowPrice = lowPrice;
	}
	public double getHighPrice() {
		return highPrice;
	}
	public void setHighPrice(double highPrice) {
		this.highPrice = highPrice;
	}
	public double getPriceChangePercent() {
		return priceChangePercent;
	}
	public void setPriceChangePercent(double priceChangePercent) {
		this.priceChangePercent = priceChangePercent;
	}
	@Override
	public String toString() {
		return "Criptomoneda [symbol=" + symbol + ", count=" + count + ", openPrice=" + openPrice + ", lowPrice="
				+ lowPrice + ", highPrice=" + highPrice + ", priceChangePercent=" + priceChangePercent + "]";
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
}