package com.Parfoo;

/**
 * action -> 0 = recomendacion; 1 = buy/sell
 * type -> 0 = buy; 1 -> sell;
 *
 */
public class Recomendacion {
	private int type;
	private int action;
	
	/**
	 * 
	 * @param type buy == 0; sell == 1
	 * @param action recomendacion == 0; peform buy/sell == 1
	 */
	public Recomendacion(int type, int action) {
		super();
		this.type = type;
		this.action = action;
	}

	public int getType() {
		return type;
	}

	public int getAction() {
		return action;
	}

	@Override
	public String toString() {
		if(this.type == 0) {
			if(this.action == 0)
				return "Recomendation: Buy on Low";
			else
				return "Recomendation: Sell on High";
		} else{
			if(this.action == 0)
				return "Action: Buy on Low";
			else
				return "Action: Sell on High";
		}
	}
}