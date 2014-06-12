package com.example.citeit;

/**
 * Ein Objekt dieser Klasse beinhaltet ein Zitat.
 *
 */
public class ZitatTopicWrapper {

	/** Machine-Identifier dieses Topics/Zitats */
	protected String _mid = "";
	
	/** Eigentlicher Text des Zitats */
	protected String _zitatText = "";
	
	/** Optionaler Autor des Zitats als Personen-Objekt. */
	protected PersonTopicWrapper _autorDesZitats = null;
	
	/** 
	 *  Klassenvariable, in der die Gesamtanzahl der Treffer
	 *  f�r die Suchanfrage enthalten ist, auch wenn
	 *  wegen Verwendung des Parameters "limit" nicht
	 *  alle ausgegeben werden.
	 */
	protected static int _sAnzahlTrefferGesamt = 0;
	
	
	/**
	 * Einziger Konstruktor dieser Klasse erwartet MID, weil 
	 * diese immer vorhanden sein muss.
	 * 
	 * @param mid Machine-Identifier des Zitats.
	 */
	public ZitatTopicWrapper(String mid) {
		_mid = mid;
	}
	
	
	/**
	 * Getter-Methode zum Setzen des Zitats.
	 * 
	 * @param zitat Eigentlicher Text des Zitats.
	 */
	public void setZitatText(String zitat) {
		_zitatText = zitat;
	}
	
	
	/**
	 * Setter-Methode f�r den Autor des Zitates. 
	 * 
	 * @param autor Person, die das Zitat von sich gegeben hat.
	 */
	public void setAutor(PersonTopicWrapper autor) {
		_autorDesZitats = autor;
	}
	
	
	/**
	 * Setter-Methode f�r Anzahl der Treffer bei Suche nach
	 * Zitaten (auch wenn diese nicht alle angezeigt werden).
	 * 
	 * @param anzTrefferGesamt Anzahl der Gesamt-Treffer f�r eine
	 *                         Suchanfrage, muss >= 0 sein.
	 */
	public static void setAnzahlTrefferGesamt(int anzTrefferGesamt) {
		_sAnzahlTrefferGesamt = anzTrefferGesamt;
	}
	
	
	/**
	 * Getter-Methode f�r statisches Attribut mit der Gesamtanzahl
	 * der Treffer (i.d.R. gr��er als die Anzahl der Zitate,
	 * die zur Anzeige abgefragt werden).
	 * 
	 * @return Anzahl Gesamttreffer der letzten Suchanfrage
	 */
	public static int getAnzahlTrefferGesamt() {
		return _sAnzahlTrefferGesamt;
	}
	
	
	/**
	 * Liefert String mit Zitat zur�ck.
	 * 
	 * @return String mit dem Text des Zitats und ggf. dem Namen des Autors. 
	 */
	@Override
	public String toString() {
		
		StringBuffer sb = new StringBuffer();
		
		sb.append("\"").append(_zitatText).append("\"");
		if (_autorDesZitats != null)
			sb.append(" (").append( _autorDesZitats.getName() ).append(")");
		
		sb.append(".");
		
		return sb.toString();
	}
};