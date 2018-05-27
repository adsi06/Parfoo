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
	private final static Logger LOG = Logger.getLogger(PetitionREST.class);
	/**
	 * Contiene el HTML de respuesta de la página a la que se le hizo
	 * una petición
	 */
	private StringBuffer buffer;
	
	/**
	 * Getter de la respuesta del HTML
	 * @return La cadena con el HTML de respuesta de la página con la que
	 * se hizo la petición
	 */
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
		LOG.info("Checando nueva petición GET");
		boolean response = false;
		try {
			URL url = new URL(ip);
			LOG.info("Inicializando la conexión...");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			LOG.info("Configurando las propiedades del requerimiento...");
			connection.setRequestProperty("User-Agent", this.USER_AGENT);
			LOG.info("Obteniendo el código de respuesta...");
			int responseCode = connection.getResponseCode();
			response = responseCode == 200;
			LOG.info("El código de respuesta fue: " + responseCode);
			BufferedReader input = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			String line = input.readLine();
			buffer = new StringBuffer();
			LOG.info("Generando el HTML de respuesta...");
			while (line != null) {
				buffer.append(line);
				line = input.readLine();
			}
			input.close();
			LOG.info("Fin del HTML de respuesta");
		} catch (Exception e) {
			response = false;
			LOG.info("Error en la petición");
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
		LOG.info("Checando nueva petición POST");
		boolean response = false;
		try {
			URL url = new URL(ip);
			LOG.info("Inicializando la conexión...");
			HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			LOG.info("Configurando las propiedades del requerimiento...");
			connection.setRequestProperty("User-Agent", this.USER_AGENT);
			connection.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
			connection.setDoOutput(true);
			DataOutputStream writable = new DataOutputStream(connection.getOutputStream());
			writable.writeBytes(parameters);
			writable.flush();
			writable.close();
			LOG.info("Obteniendo el código de respuesta...");
			int responseCode = connection.getResponseCode();
			response = responseCode == 200;
			BufferedReader input = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			String line = input.readLine();
			buffer = new StringBuffer();
			LOG.info("Generando el HTML de respuesta...");
			while (!line.isEmpty()) {
				buffer.append(line);
				line = input.readLine();
			}
			input.close();
			LOG.info("Fin del HTML de respuesta");
		} catch (Exception e) {
			response = false;
			LOG.info("Error en la petición");
			e.printStackTrace();
		}
		return response;
	}
}