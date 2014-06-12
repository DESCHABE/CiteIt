package com.example.citeit;

/**
 * Ein Objekt dieser Klasse repr�sentiert ein Topic
 * vom Type "/people/person" oder des Sub-Typs "/people/deceased_person".
 *
 */
public class PersonTopicWrapper {

	/** Aufz�hlungs-Typ f�r das Geschlecht einer Person */
	public enum Geschlecht { MANN, FRAU, UNBEKANNT };
	
	/** 
	 *  Klassenvariable, in der die Gesamtanzahl der Treffer
	 *  f�r die Suchanfrage enthalten ist, auch wenn
	 *  wegen Verwendung des Parameters "limit" nicht
	 *  alle ausgegeben werden.
	 */
	protected static int _sAnzahlTrefferGesamt = 0;	
	
	/** Machine-ID */
	protected String _mid = "";
	
	/** Menschen-Lesbare ID */
	protected String _id = "";
	
	/** Name des Topics/der Person */
	protected String _name = "";
	
	/** Enth�lt URL auf ein Bild der Person (optional) */
	protected String _bildUrl = "";
	
	/** Geburts-Datum */
	protected String _datumGeburt = "";
	
	/** Todes-Datum (enth�lt leeren String f�r noch lebende Personen) */
	protected String _datumTod = "";
	
	/** Geschlecht der Person, Default-Wert ist "UNBEKANNT" */
	protected Geschlecht _geschlecht = Geschlecht.UNBEKANNT;
	
	/** Beschreibungs-Text (Property "/common/topic/description") */
	protected String _beschreibung = "";

	
	/**
	 * Einziger Konstruktor dieser Klasse verlangt MID, weil diese immer
	 * vorhanden sein muss.
	 * 
	 * @param mid Machine-ID des Topics
	 */
	public PersonTopicWrapper(String mid) {
		_mid = mid;
	}
	
	
	/**
	 * Getter-Methode f�r maschinen-lesbare ID des Topics.
	 * 
	 * @return Machine-ID, z.B. "/m/0jcx" f�r Topic "Albert Einstein".
	 */
	public String getMID() {
		return _mid;
	}
	
	
	/**
	 * Setter-Methode f�r die menschen-lesbare ID des Topics.
	 * 
	 * @param id ID (menschen-lesbar) der Person, z.B. "en/albert_einstein" f�r Topic "Albert Einstein". 
	 */
	public void setID(String id) {
		_id = id;
	}
	
	
	/**
	 * Getter-Methode f�r die menschen-lesbare ID des Topics.
	 * 
	 * @return id ID (menschen-lesbar) der Person, z.B. "en/albert_einstein" f�r Topic "Albert Einstein".
	 */
	public String getID() {
		return _id;
	}
	
	
	/**
	 * Setter-Methode f�r Name-Property.
	 * 
	 * @param name Name des Topics/der Person.
	 */
	public void setName(String name) {
		_name = name;
	}
	
	
	/**
	 * Getter-Methode f�r das Name-Property.
	 * 
	 * @return Name-Property der Person.
	 */
	public String getName() {
		return _name;
	}
	
	
	/**
	 * Setter-Methode f�r Bild-URL; es ist aber nicht f�r alle Personen
	 * wenigstens ein Bilder vorhanden.
	 * 
	 * @param bildURL vollst�ndige URL auf ein Bild der Person
	 */
	public void setBildURL(String bildURL) {
		_bildUrl = bildURL;
	}
	
	
	/**
	 * Getter-Methode f�r die URL zu einem Bild der Person.
	 *   
	 * @return URL eines Bildes von der Person.
	 */
	public String getBildURL() {
		return _bildUrl;
	}
	
	
	/**
	 * Setter-Methode f�r Geburtsdatum der Person.
	 * 
	 * @param geburtsdatum Geburts-Datum
	 */
	public void setDatumGeburt(String geburtsdatum) {
		_datumGeburt = geburtsdatum;
	}
	
	
	/**
	 * Getter-Methode f�r Geburtsdatum der Person.
	 * 
	 * @return Geburts-Datum der Person, z.B. "1955-05-19" (19 Mai 1955)
	 */
	public String getDatumGeburt() {
		return _datumGeburt;
	}
	
	
	/**
	 * Setter-Methode f�r Todes-Datum der Person.
	 * 
	 * @param todesdatum Todes-Datum der Person
	 */
	public void setTodesDatum(String todesdatum) {
		_datumTod = todesdatum;
	}
	
	
	/**
	 * Getter-Methode f�r Todesdatum der Person (falls schon verstorben).
	 * 
	 * @return Geburts-Datum der Person, z.B. "1999-12-30" (30 Dezember 1999).
	 */	
	public String getTodesDatum() {
		return _datumTod;
	}
	
	
	/**
	 * Setter-Methode f�r das Geschlechter der Person.
	 * 
	 * @param geschlecht Geschlecht (M�nnlich/Weiblich) der Person.
	 */
	public void setGeschlecht(Geschlecht geschlecht) {
		_geschlecht = geschlecht;
	}
	
	
	/**
	 * Getter-Methode f�r Geschlecht der Person.
	 * 
	 * @return Geschlecht (M�nnlich/Weiblich/Unbekannt).
	 */
	public Geschlecht getGeschlecht() {
		return _geschlecht;
	}
	
	
	/**
	 * Setzten Beschreibungstext aus Property "/common/topic/description".
	 * 
	 * @param beschreibungsText Beschreibung der Person
	 */
	public void setBeschreibung(String beschreibungsText) {
		_beschreibung = beschreibungsText;
	}
	
	
	/**
	 * Getter-Methode f�r Beschreibungstext.
	 * 
	 * @return Ganzer Beschreibungs-Text.
	 */
	public String getBeschreibungVoll() {
		return _beschreibung;
	}
	
	
	/**
	 * Methode um h�chstens die ersten 150 Zeichen des Beschreibungstextes
	 * zu bekommen.
	 * 
	 * @return Beschreibungstext, h�chstens die ersten 150 Zeichen;
	 *         liefert leeren String, wenn die Member-Variable nicht
	 *         gef�llt ist. Wenn der Beschreibungs-Text abgeschnitten 
	 *         wurde, dann endet er mit "...".
	 */
	public String getBeschreibungShort() {
		final int MAX_LEN = 150;
		
		if (_beschreibung == null ) return "";			
		
		String beschreib = _beschreibung.trim();
		int   anzZeichen = beschreib.length();
		
		if (anzZeichen == 0) return "";
		
		if (anzZeichen > MAX_LEN)
			return beschreib.substring(0, MAX_LEN) + "...";
		else
			return beschreib;		
	}
	
	
	/**
	 * Erstellt URL f�r Anfrage bei Topic-API unter Verwendung der MID 
	 * (ID w�rde auch gehen, aber die ist nicht immer gef�llt).
	 * Beispiel-URL f�r "Albert Einstein": 
	 * https://www.googleapis.com/freebase/v1/topic/m/0jcx
	 * 
	 * @return URL URL f�r Anfrage an die Topic-API
	 */
	public String buildUrlForTopicAPI() {
		if (_mid != null && _mid.trim().length() > 0)
			return IFreebaseKonstanten.BASIS_URL_TOPIC_API + _mid;
		else
			return "";
	}
	
	
	/**
	 * Schreibt alle gef�llten Felder auf die Standard-Ausgabe.
	 */
	public void schreibeAufStdout() {
		StringBuffer sb = new StringBuffer();
		
		sb.append("Person: ").append(_name).append("\n");
		sb.append("  MID: ").append(_mid).append("\n");
		
		if (_geschlecht != Geschlecht.UNBEKANNT)
			sb.append("  Geschlecht: ").append(_geschlecht).append("\n");
		
		if (_id != null && _id.trim().length() > 0)
			sb.append("  ID : ").append(_id ).append("\n");
		
		if (_datumGeburt != null && _datumGeburt.trim().length() > 0)
			sb.append("  Geburts-Datum: ").append(_datumGeburt).append("\n");

		if (_datumTod != null && _datumTod.trim().length() > 0)
			sb.append("  Todes-Datum: ").append(_datumTod).append("\n");

		if (_bildUrl != null && _bildUrl.trim().length() > 0)
			sb.append("  URL Bild: ").append(_bildUrl).append("\n");
		
		sb.append("  URL Topic-API: ").append(buildUrlForTopicAPI()).append("\n");
		
		if (_beschreibung != null && _beschreibung.trim().length() > 0)
		  sb.append("  Beschreibung: \"").append(getBeschreibungShort()).append("\"\n");
		
		System.out.println(sb.toString());
	}
	
	
	/**
	 * Setter-Methode f�r Anzahl der Treffer bei Suche nach
	 * Personen (auch wenn nicht alle in der JSON-Datei enthalten
	 * sind, v.a. wegen Beschr�nkung durch Parameter "limit").
	 * 
	 * @param anzTrefferGesamt Anzahl der Gesamt-Treffer f�r eine
	 *                         Suchanfrage, muss >= 0 sein.
	 */
	public static void setAnzahlTrefferGesamt(int anzTrefferGesamt) {
		_sAnzahlTrefferGesamt = anzTrefferGesamt;
	}
	
	
	/**
	 * Getter-Methode f�r statisches Attribut mit der Gesamtanzahl
	 * der Treffer (i.d.R. gr��er als die Anzahl der Personen,
	 * die zur Anzeige abgefragt werden).
	 * 
	 * @return Anzahl Gesamttreffer der letzten Suchanfrage.
	 */
	public static int getAnzahlTrefferGesamt() {
		return _sAnzahlTrefferGesamt;
	}
	
	
	/**
	 * Liefert kurze String-Repr�sentation des Objektes zur�ck.
	 * 
	 * @return String mit Name der Person.
	 */
	@Override
	public String toString() {
		return "Person \"" + _name + "\"";
	}
	
};