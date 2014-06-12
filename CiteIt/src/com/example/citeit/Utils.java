package com.example.citeit;

import java.io.FileReader;
import java.util.Properties;

import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;

/**
 * 
 *  Diese Klasse enth�lt nur statische Methoden.
 */
public class Utils {
	
    /** Name des URL-Parameters, mit dem der API-Key in die URL eingebaut werden muss,
     * z.B. "....?query=geld&key=abcd1234&limit=10" (mit "key" als URL-Parameter-Name und "abcd1234"
     * als API-Key 
     */
	public static final String API_KEY_PARAMETER_NAME = "key";
	
	/** Schl�ssel, unter dem der API-Key in der Properties-Datei abgelegt ist */
	private static final String KEY = "secret_api_key";

	/** Dateiname, unter dem die Properties-Datei mit dem API-Key
	 *  im Hauptverzeichnis des Projektes zu finden ist.
	 */
	private static final String FILE_NAME = "api_key.properties";
	
	/** 
	 * Klassenvariable, um die eingelesene Property-Datei
	 * mit dem API-Key zu cachen.
	 */
	private static Properties _properties = null;
	
	
	/**  
	 * Diese Methode kapselt den Zugriff auf den API-Key f�r den Freebase-Zugriff.
	 * API-Key holen/verwalten: https://cloud.google.com/console
	 * 
	 * Wenn der API-Key "abcd1234" ist, dann sollte die Properties-Datei eine Zeile
	 * wie folgt haben:
	 * <pre>secret_api_key=abcd1234</pre>
	 * Diese Properties-Datei muss im Hauptverzeichnis des Projektes liegen (in
	 * dem Verzeichnis, in dem z.B. die Datei ".project" liegt oder das das
	 * Unterverzeichnis "src" enth�lt).	 
	 * F�r eine Android-App z.B. k�nnte es sinnvoll sein, den
	 * API-Key in dieser Methode hardcodiert zur�ckzugeben. 
	 * 
	 * @return API-Key oder leerer String, wenn beim Laden eine
	 *         Exception aufgetreten ist.
	 */
	public static String holeApiKey() {
				
		try {
			
			if (_properties == null) {
			
				_properties  = new Properties();
				FileReader fileReader = new FileReader(FILE_NAME); 
				//_properties.load(fileReader);
				fileReader.close();
				
			}
		
			
			if (!_properties.containsKey(KEY)) {
				
				System.err.println("Kein API-Key in der Datei \"" + FILE_NAME + "\" gefunden.");
				return "";
				
			} else
				return (String)_properties.get(KEY);
						
			
		}
		catch (Exception ex) {
			System.err.println("Exception beim Laden des API-Keys aufgetreten: " + ex);
			ex.printStackTrace();
			return "";
		}
	}

	
	/**
	 * Erzeugt ein Factory-Objekt f�r die HTTP-Requets.
	 * 
	 * @return Request-Factory zum Durchf�hren eines HTTP-Requests.
	 */
	public static HttpRequestFactory holeHttpRequestFactory() {
		HttpTransport httpTransport = new NetHttpTransport();
	    return httpTransport.createRequestFactory();
	}
	
	
	/**
	 * Test-Methode: Gibt API-Key auf STDOUT aus.
	 * 
	 * @param args Wird nicht ausgewertet.
	 */
	public static void main(String[] args) {
		
		System.out.println("API-Key: \"" + holeApiKey() + "\"");
		
	}
	
};