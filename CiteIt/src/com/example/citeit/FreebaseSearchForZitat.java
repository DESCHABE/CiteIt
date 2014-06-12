package com.example.citeit;

import java.util.Vector;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;

/**
 * Diese Klasse hat Methoden um mit der Search-API von Freebase (f�r
 * Freitext-Suche) nach Topics vom Typ /media_common/quotation
 * zu suchen.
 *
 */
public class FreebaseSearchForZitat {

	/**
	 * Methode um nach Zitaten anhand eines bestimmten Suchbegriffs zu suchen.
	 * 
	 * @param suchbegriff Suchbegriff, der im Zitat enthalten sein soll, z.B. "money".
	 * 
	 * @return Array mit den gefundenen Zitaten.
	 */
	public static Vector<ZitatTopicWrapper> sucheZitat(String suchbegriff) throws Exception {
		
		// *** URL besorgen ***
		GenericUrl url = erstelleURL(suchbegriff);
		System.out.println("URL f�r Search-API-Aufruf: " + url);
		
		
		// *** HTTP-Request absetzen ***
		HttpRequestFactory requestFactory = Utils.holeHttpRequestFactory();
		HttpRequest  request              = requestFactory.buildGetRequest(url);
	    HttpResponse httpResponse         = request.execute();
		
	    
	    // *** Ergebnis auswerten ***
	    String responseString = httpResponse.parseAsString();
	    
	    System.out.println("L�nge HTTP-Response: " + responseString.length());
	    
	    return parseJSONResults(responseString);	
	    
	}
	
	
	/**
	 * Parsen der Antwort von der Search-API.
	 * 
	 * @param responseString Kompletter HTTP-Response-String
	 * 
	 * @return Vektor mit Liste der gefundenen Zitate (kann auch null Elemente enthalten, wenn nichts gefunden).
	 * 
	 * @throws ParseException Wenn Fehler beim Parsen aufgetreten ist.
	 */
	protected static Vector<ZitatTopicWrapper>  parseJSONResults(String responseString) throws ParseException {
		
	    JSONParser parser = new JSONParser();	    
	    JSONObject wholeResultObj = (JSONObject)parser.parse(responseString);

	    // HTTP-Status-Code auswerten
	    String statusString = (String)wholeResultObj.get("status"); // Sollte sein: "200 OK"	   
	    if (!statusString.contains("200")) {
	    	System.err.println("HTTP-Request f�r Search-API-Aufruf war nicht erfolgreich:" + statusString);
	    	return new Vector<ZitatTopicWrapper>();
	    }

	    // Gesamtanzahl der Treffer auswerten (auch wenn nicht alle abgefragt werden)
	    Long numberOfHits = (Long)wholeResultObj.get("hits");
	    ZitatTopicWrapper.setAnzahlTrefferGesamt( (int)numberOfHits.longValue() );
	    
	    JSONArray resultArray = (JSONArray)wholeResultObj.get("result");
	    int anzErgebnisse = resultArray.size();
	    System.out.println("Anzahl Ergebnis-Datens�tze: " + anzErgebnisse);
	    Vector<ZitatTopicWrapper> ergebnisVector = new Vector<ZitatTopicWrapper>(anzErgebnisse);
	    
	    
	    // �ber einzelne Ergebnisse iterieren	   
	    ZitatTopicWrapper zitatObj = null;
	    for (Object resultObject: resultArray) {
	    	JSONObject resultJSONObj = (JSONObject) resultObject;
	    
	    	String mid = (String)resultJSONObj.get("mid");
	    	zitatObj = new ZitatTopicWrapper(mid);
	    	
	    	String zitatStr = (String)resultJSONObj.get("name");
	    	zitatObj.setZitatText(zitatStr);
	    	
	    	
	    	// Zus�tzliche mit Parameter "output" angeforderte Properties abfragen 
	    	JSONObject outputObject = (JSONObject)resultJSONObj.get("output");
	    	if (outputObject != null) {
	    		
	    		JSONObject authorObj = (JSONObject)outputObject.get(IFreebaseKonstanten.PROPERTY_ID_AUTHOR_OF_QUOTATION);
	    		if (authorObj != null) {
	    			
	    			JSONArray authorArray = (JSONArray)authorObj.get(IFreebaseKonstanten.PROPERTY_ID_AUTHOR_OF_QUOTATION);
	    			if (authorArray != null && authorArray.size() > 0) {
	    				JSONObject authorItem = (JSONObject)authorArray.get(0);
	    				
	    				String authorMID = (String)authorItem.get("mid");
	    				if (authorMID != null && authorMID.trim().length() > 0) {
	    					PersonTopicWrapper person = new PersonTopicWrapper(authorMID);
	    					
	    					String authorName = (String)authorItem.get("name");
	    					if (authorName != null && authorName.trim().length() > 0) {
	    						person.setName(authorName);
	    						zitatObj.setAutor(person);
	    					}
	    					
	    				}
	    				
	    			}
	    			
	    		}
	    		
	    	}
	    	
	    	
	    	ergebnisVector.add(zitatObj);

	    }
	    
		return ergebnisVector;
	}
	
	
	/**
	 * Erstellt die URL f�r die Suchanfrage
	 * 
	 * @param suchbegriff Suchbegriff, der im Zitat enthalten sein soll, z.B. "money".
	 * 
	 * @return URL f�r den HTTP-Request mit der Suchanfrage nach den Zitaten.
	 */
	protected static GenericUrl erstelleURL(String suchbegriff) {
		
		GenericUrl url = new GenericUrl(IFreebaseKonstanten.BASIS_URL_SEARCH_API);
		
		
		// query="Suchbegriff"
		url.put("query", "\"" + suchbegriff + "\"");

		// filter=(any type:/media_common/quotation)
		// Die von der Suchanfrage gelieferten Topics m�ssen zum Typ "Quotation" geh�ren
		url.put("filter", "(any type:" + IFreebaseKonstanten.TYPE_ID_QUOTATION + ")");

		//output=(/media_common/quotation/author)
		url.put("output", "(" + IFreebaseKonstanten.PROPERTY_ID_AUTHOR_OF_QUOTATION + ")");
		
		// Damit JSON-Output gut lesbar ist
		url.put("indent", "true"); // h�ngt "&indent=true" an URL an 
		
		// Maximale Anzahl der Ergebnis-Datens�tze (Zitate), Default-Wert ist 20
		url.put("limit", 10);

		// Noch den API-Key hinzuf�gen
		url.put(Utils.API_KEY_PARAMETER_NAME, Utils.holeApiKey());
		
		return url;
	}
	
	
	/**
	 * Test-Methode, gibt die Query-URLs f�r verschiedene Suchbegriffe aus.
	 * 
	 * @param args Wird nicht ausgewertet.
	 */
	public static void main(String[] args) {
		GenericUrl url = null;
		
		String[] suchbegriffeArray = {"money", "music", "success", "tea", "Germany"};
				
		for (String suchbegriff : suchbegriffeArray) {
			url = erstelleURL(suchbegriff);
			System.out.println("URL f�r Suchbegriff \"" + suchbegriff + "\": " + url + "\n");
		}
		
		System.out.println();
	}
	
};