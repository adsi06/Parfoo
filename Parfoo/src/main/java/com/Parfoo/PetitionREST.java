package com.Parfoo;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.apache.log4j.Logger;

public class PetitionREST {
	/**
	 * Para identificar el protocolo de red que ayuda a identificar
	 * al usuario
	 */
	private final String USER_AGENT = "Mozilla/5.0";
	/**
	 * Para obtener un manifiesto sobre lo que la clase está haciendo
	 */
	private final static Logger LOGGER = Logger.getLogger(Algoritmo.class);
	/**
	 * Contiene el HTML de respuesta de la página a la que se le hizo
	 * una petición
	 */
	private StringBuffer buffer;
	
	public String getBuffer() {
		return buffer.toString();
	}

	/**
	 * 
	 * @param ip IP a la que se conectará para mandar información,
	 * junto con el formato habitual de un GET, ie:
	 * http://ip?param1=val1&param2=val2...
	 * @return TRUE - El proceso GET fue satisfactorio. FALSE - EOC
	 */
	public boolean sendGET(String ip) {
		boolean response = false;
		try {
			URL url = new URL(ip);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("User-Agent", this.USER_AGENT);
			response = connection.getResponseCode() == 200;
			BufferedReader input = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			String line = input.readLine();
			buffer = new StringBuffer();
			while (line != null) {
				buffer.append(line);
				line = input.readLine();
			}
			input.close();
		} catch (Exception e) {
			response = false;
			e.printStackTrace();
		}
		return response;
	}
	
	/**
	 * 
	 * @param ip IP a la que se conectará para mandar información,
	 * junto con el formato habitual de un POST, ie:
	 * http://ip
	 * @param parameters Formato de todos los parámetros, que recibirá la IP
	 * eg: param1=val1&param2=val2...
	 * @return TRUE - El proceso GET fue satisfactorio. FALSE - EOC
	 */
	public boolean sendPOST(String ip, String parameters) {
		boolean response = false;
		try {
			URL url = new URL(ip);
			HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("User-Agent", this.USER_AGENT);
			connection.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
			connection.setDoOutput(true);
			DataOutputStream writable = new DataOutputStream(connection.getOutputStream());
			writable.writeBytes(parameters);
			writable.flush();
			writable.close();
			response = connection.getResponseCode() == 200;
			BufferedReader input = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			String line = input.readLine();
			buffer = new StringBuffer();
			while (!line.isEmpty()) {
				buffer.append(line);
				line = input.readLine();
			}
			input.close();
		} catch (Exception e) {
			response = false;
			e.printStackTrace();
		}
		return response;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PetitionREST p = new PetitionREST();
		if (p.sendGET("http://www.google.com/search?q=tesla")) {
			System.out.println("Petición correcta");
		} else {
			System.out.println("Error en la petición");
		}
		
		if (p.sendPOST("https://chart.googleapis.com/chart",
				"cht=lc&chtt=MyChart&chs=600x200&chxt=x,y&chd=t:40,20,50,20,100")) {
			System.out.println("Petición correcta");
		} else {
			System.out.println("Error en la petición");
		}
		
	}

}
