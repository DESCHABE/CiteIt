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
 * Diese Klasse sucht unter Verwendung der Search-API von Freebase
 * nach einer Person.
 * 
 * Doku zur Freebase-Search-API:
 * https://developers.google.com/freebase/v1/search-overview (enth�lt Java-Code-Beispiel).
 * https://developers.google.com/freebase/v1/search (Tabelle mit allen Parametern).
 * 
 * Es wird nach einem Freebase-Topic vom Typ "/people/person" gesucht:
 * http://schemas.freebaseapps.com/type?id=/people/person
 * http://www.freebase.com/people/person
 * 
 * Wenn bekannt ist, dass die gesuchte Person nicht mehr lebt, dann wird
 * der Sub-Typ "/people/deceased_person" verwendet:
 * http://schemas.freebaseapps.com/type?id=/people/deceased_person
 * http://www.freebase.com/people/deceased_person
 */
public class FreebaseSearchForPerson {

	/** 
	 * Starte die Suche nach einer Person (genauer: ein Freebase-Topic, das eine Person repr�sentiert)
	 * anhand eines Suchstrings.
	 * 
	 * @param suchString   Suchstring, z.B. "Einstein" oder "Albert Einstein".
	 *  
	 * @param istSchonTot  Flag gibt an, ob die gesuchte Person schon verstorben ist. Wenn nicht bekannt,
	 *                     dann <tt>false</tt> angeben.
	 *                     
	 * @param phrasenSuche Flag gibt an, ob eine Phrasen-Suche durchgef�hrt werden soll, mehrere
	 *                     W�rter in <i>suchString</i> also genau in der angegebenen Reihenfolge 
	 *                     vorkommen m�ssen.
	 *                     
	 * @return Vektor mit den Ergebnis-Datens�tzen (kann leer aber nicht <tt>null</tt> sein).
	 */
	public static Vector<PersonTopicWrapper> suchePerson(String suchString, boolean istSchonTot, boolean phrasenSuche) throws Exception {
				
		GenericUrl url = erstelleURL(suchString, istSchonTot, phrasenSuche);

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
	 * Parsen der kompletten HTTP-Response im JSON-Format. 
	 * 
	 * @param jsonResultString Komplette HTTP-Response als ein String.
	 * 
	 * @return Vector mit den einzelnen gefundenen Ergebnis-Datens�tzen
	 * 
	 * @throws ParseException Fehler beim Parsen des JSON-Formats aufgetreten
	 */
	protected static Vector<PersonTopicWrapper> parseJSONResults(String jsonResultString) throws ParseException {
		
	    JSONParser parser = new JSONParser();	    
	    JSONObject wholeResultObj = (JSONObject)parser.parse(jsonResultString);
	    String statusString = (String)wholeResultObj.get("status"); // Sollte sein: "200 OK"
	    
	    if (!statusString.contains("200")) {
	    	System.err.println("HTTP-Request f�r Search-API-Aufruf war nicht erfolgreich: " + statusString);
	    	return new Vector<PersonTopicWrapper>();
	    }

	    // Array mit den einzelnen Ergebnissen holen
	    JSONArray resultArray = (JSONArray)wholeResultObj.get("result");
	    int anzErgebnisse = resultArray.size();
	    System.out.println("Anzahl Ergebnis-Datens�tze: " + anzErgebnisse);
	    Vector<PersonTopicWrapper> ergebnisVector = new Vector<PersonTopicWrapper>(anzErgebnisse);
	    
	    // Gesamtanzahl der Treffer auswerten (auch wenn nicht alle abgefragt werden)
	    Long numberOfHits = (Long)wholeResultObj.get("hits");
	    PersonTopicWrapper.setAnzahlTrefferGesamt( (int)numberOfHits.longValue() );
	    
	    
	    // �ber einzelne Ergebnisse iterieren	    
	    PersonTopicWrapper personWrapper = null;
	    for (Object resultObject: resultArray) {
	    	JSONObject resultJSONObj = (JSONObject) resultObject;
	    	
	    	String name = (String)resultJSONObj.get("name");
	    	String id   = (String)resultJSONObj.get("id"  );
	    	String mid  = (String)resultJSONObj.get("mid" );
	    		    	
	    	personWrapper = new PersonTopicWrapper(mid);
	    	personWrapper.setID  ( id   );
	    	personWrapper.setName( name );
	    	
	    	// Zus�tzliche mit Parameter "output" angeforderte Properties abfragen 
	    	JSONObject outputObject = (JSONObject)resultJSONObj.get("output");
	    	if (outputObject == null) {
	    		System.out.println("Warnung: Ergebnis-Datensatz f�r \"" + name + "\" enth�lt kein output-Objekt.");
	    		continue;
	    	}
	    		    	
	    	// Geschlecht der Person holen
	    	JSONObject geschlechtObj = (JSONObject) outputObject.get(IFreebaseKonstanten.PROPERTY_ID_GENDER);
	    	if (geschlechtObj != null) {
	    		JSONArray geschlechtArray = (JSONArray) geschlechtObj.get(IFreebaseKonstanten.PROPERTY_ID_GENDER);
	    		if (geschlechtArray != null) {
	    			JSONObject geschlechtArrayItem = (JSONObject) geschlechtArray.get(0);
	    			String geschlechtString = (String) geschlechtArrayItem.get("name"); 
	    			if (geschlechtString.equalsIgnoreCase("Male"))
	    				personWrapper.setGeschlecht(PersonTopicWrapper.Geschlecht.MANN);
	    			else if (geschlechtString.equalsIgnoreCase("Female"))
	    				personWrapper.setGeschlecht(PersonTopicWrapper.Geschlecht.FRAU);	    				    	
	    		}
	    	}
	    	
	    	// Geburts-Datum holen
	    	JSONObject gebdatObj = (JSONObject) outputObject.get(IFreebaseKonstanten.PROPERTY_ID_GEBURTSTAG);
	    	if (gebdatObj != null) {
	    		JSONArray gebdatArray = (JSONArray) gebdatObj.get(IFreebaseKonstanten.PROPERTY_ID_GEBURTSTAG);
	    		if (gebdatArray != null) {
	    			String gebdatStr = (String) gebdatArray.get(0);
	    			personWrapper.setDatumGeburt(gebdatStr);
	    		}
	    	}
	    
	    	// Todes-Datum holen
	    	JSONObject toddatObj = (JSONObject) outputObject.get(IFreebaseKonstanten.PROPERTY_ID_TODESTAG);
	    	if (toddatObj != null) {
	    		JSONArray toddatArray = (JSONArray) toddatObj.get(IFreebaseKonstanten.PROPERTY_ID_TODESTAG);
	    		if (toddatArray != null) {
	    			String toddatStr = (String) toddatArray.get(0);
	    			personWrapper.setTodesDatum(toddatStr);
	    		}
	    	}
	    	
	    	// Bild holen (h�chstens ein Bild holen)
	    	JSONObject imgObj = (JSONObject) outputObject.get(IFreebaseKonstanten.PROPERTY_ID_IMAGE);
	    	if (imgObj != null) {
	    		JSONArray imgArray = (JSONArray) imgObj.get(IFreebaseKonstanten.PROPERTY_ID_IMAGE);
	    		if (imgArray != null && imgArray.size() > 0) {
	    			JSONObject bildObj = (JSONObject)imgArray.get(0);
	    			String bildMID  = (String)bildObj.get("mid" );
	    			//String bildName = (String)bildObj.get("name");
	    			String bildURL  = IFreebaseKonstanten.BASIS_URL_IMAGE_API + bildMID;
	    			personWrapper.setBildURL(bildURL);
	    		}
	    	}
	    	 
	    	// Beschreibungs-Text holen
	    	JSONObject descObj = (JSONObject) outputObject.get(IFreebaseKonstanten.PROPERTY_ID_DESCRIPTION);
	    	if (descObj != null) {
	    		JSONArray descArray = (JSONArray) descObj.get(IFreebaseKonstanten.PROPERTY_ID_DESCRIPTION);
	    		if (descArray != null && descArray.size() > 0) {
	    			String descString = (String)descArray.get(0);
	    			personWrapper.setBeschreibung(descString);
	    		}
	    	}
	    		    	
	    	ergebnisVector.add(personWrapper);
	    	
	    	System.out.println();	    	
	    }
	    
	    return ergebnisVector;
	}
	
	
	/**
	 * Erstellen der URL f�r die Such-Anfrage. H�ngt mehrere Parameter als Key-Value-Pairs an die Basis-URL an:
	 * <pre>http://<basis_url>?<key1>=<value1>&<key2>=<value2>....</pre>.
	 * Auftauchende Sonderzeichen werden "encoded" (z.B. Leerzeichen durch "%20" ersetzt,
	 * Klammer "(" durch "%28", Klammer ")" durch "%29", Anf�hrungszeichen durch "%22").
	 * 
	 * Eine von dieser Methode erzeugte URL kann auch direkt mit einem Browser (z.B. Firefox, weil der direkt
	 * JSON ausgeben kann) ge�ffnet werden.
	 * 
	 * Beispiele f�r erzeugte URLs:
	 * <pre>
	 * https://www.googleapis.com/freebase/v1/search?filter=%28all%20type:/people/person%20name{phrase}:%22Albert%20Einstein%22%20%29&output=%28%20/people/person/date_of_birth%20/people/deceased_person/date_of_death%20/people/person/gender%20/common/topic/image%20/common/topic/description%20%29&indent=true&limit=10&key=xxxx...
	 * Ergebnis siehe Datei "ExampleResult_SearchAPI_Personen.json".
	 * </pre>
	 * Die genannten Ergebnis-Dateien befinden sich im Basis-Verzeichnis des vorliegenden Eclipse-Projektes.
	 * 
	 * @param suchString   Suchstring, z.B. "Einstein" oder "Albert Einstein".
	 *  
	 * @param istSchonTot  Flag gibt an, ob die gesuchte Person schon verstorben ist.
	 * 
	 * @param phrasenSuche Flag gibt an, ob eine Phrasen-Suche durchgef�hrt werden soll, mehrere
	 * 
	 *                     W�rter in <i>suchString</i> also genau in der angegebenen Reihenfolge 
	 *                     vorkommen m�ssen.  
	 *                     
	 * @return URL f�r die Suchanfrage. 
	 */
	protected static GenericUrl erstelleURL(String suchString, boolean istSchonTot, boolean phrasenSuche) {
		
		GenericUrl url = new GenericUrl(IFreebaseKonstanten.BASIS_URL_SEARCH_API);
		
		// ID des Typs des gesuchten Topics.
		// Wenn wir wissen, dass die gesuchte Person schon tot ist,
		// k�nnen wir zur Einschr�nkung der Suche statt "person"
		// den Sub-Typ "deceased_person" verwenden.
		// Beide diese Typen geh�ren zur Dom�ne "/people".
		String typeString = "";
		if (istSchonTot)
			typeString = IFreebaseKonstanten.TYPE_ID_DECEASED_PERSON;
		else
			typeString = IFreebaseKonstanten.TYPE_ID_PERSON;
		
		
		// Den Wert f�r den URL-Parameter "filter" definieren:
		// Allgemein wird ein Filter bei der Search-API dazu genutzt,
		// um die Ergebnismenge (Menge der zur�ckgelieferten Topics) 
		// einzuschr�nken.
		// Schr�nkt ein auf den Typ "person" oder "deceased_person"
		// und filtert den Namen
		// Beispiel (all type:/people/person name:"Albert Einstein")
		// Der Suchbegriff k�nnte auch f�r den Parameter "query" verwendet
		// werden, aber dann wird nicht nur im "Namen" gesucht, die Suche
		// w�rde also unsch�rfer (liefert z.B. Orte zur�ck, in deren
		// Beschreibung Einstein erw�hnt wird).
		StringBuffer filterValueStringBuffer = new StringBuffer();
		filterValueStringBuffer.append("(all "); // Operator "all": alle der folgenden Bedinungen m�ssen erf�llt werden
		filterValueStringBuffer.append("type:").append(typeString);
		filterValueStringBuffer.append(" ");
		if (phrasenSuche)
			filterValueStringBuffer.append("name{phrase}:\"" + suchString + "\"");
		else
			filterValueStringBuffer.append("name:\"" + suchString + "\"");
		filterValueStringBuffer.append(" )");
		
		
		// Den Wert f�r den ULR-Parameter "output" definieren.
		// Mit diesem Parameter werden zus�tzlich in die
		// Antwort der Suchfrage aufzunehmende Properties spezifiziert
		StringBuffer outputValueStringBuffer = new StringBuffer();
		outputValueStringBuffer.append("( "); // Der Wert des Output-Parameters muss immer in runden Klammern sein
		outputValueStringBuffer.append(IFreebaseKonstanten.PROPERTY_ID_GEBURTSTAG ); // Property "Geburtsdatum" auch mit ausgeben
		outputValueStringBuffer.append(" ");
		outputValueStringBuffer.append(IFreebaseKonstanten.PROPERTY_ID_TODESTAG   ); // Bei nicht mehr lebenden Personen auch das Todesdatum ausgeben (ist leer, wenn Person noch nicht gestorben)
		outputValueStringBuffer.append(" ");
		outputValueStringBuffer.append(IFreebaseKonstanten.PROPERTY_ID_GENDER     );
		outputValueStringBuffer.append(" ");		
		outputValueStringBuffer.append(IFreebaseKonstanten.PROPERTY_ID_IMAGE      ); // MIDs von Bildern der Person zur�ckliefern
		outputValueStringBuffer.append(" ");
		outputValueStringBuffer.append(IFreebaseKonstanten.PROPERTY_ID_DESCRIPTION);
		outputValueStringBuffer.append(" )");
		
		
		// *** Parameter hinzuf�gen ***
		
		url.put("filter", filterValueStringBuffer.toString());
		
		url.put("output", outputValueStringBuffer.toString());
		
		// Damit JSON-Output gut lesbar ist
		url.put("indent", "true"); // h�ngt "&indent=true" an URL an 
		
		// Nicht mehr als 10 Ergebnis-Datens�tze
		url.put("limit", 1);

		// Noch den API-Key hinzuf�gen
		//url.put(Utils.API_KEY_PARAMETER_NAME, Utils.holeApiKey());
		
		
		return url;
	}
	
	
	/**
	 * Methode zum Durchf�hren einiger Tests: Gibt URLs einiger Suchanfragen
	 * auf STDOUT aus.
	 * 
	 * @param args Wird nicht ausgewertet
	 */
	public static void main(String[] args) {
		GenericUrl url = null;
				
		// Suche nach "Albert Einstein" mit allen vier m�glichen Parameter-Kombinationen
		
		url = FreebaseSearchForPerson.erstelleURL("Albert Einstein", true,  false ); 
		System.out.println("URL: " + url + "\n");
		
		url = FreebaseSearchForPerson.erstelleURL("Albert Einstein", false, false );
		System.out.println("URL: " + url + "\n");
		
		url = FreebaseSearchForPerson.erstelleURL("Albert Einstein", true,  true  );
		System.out.println("URL: " + url + "\n");
		
		url = FreebaseSearchForPerson.erstelleURL("Albert Einstein", false, true  );
		System.out.println("URL: " + url + "\n");
		
	}
};

