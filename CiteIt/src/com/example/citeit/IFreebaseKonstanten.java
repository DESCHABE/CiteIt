package com.example.citeit;

/**
 * In diesem Interface sind Konstanten gesammelt, die
 * Freebase-spezifisch sind, z.B. die Basis-URLs f�r
 * die einzelnen APIs oder die IDs f�r die Typen/Properties.
 */
public interface IFreebaseKonstanten {

	/** Basis-URL der Search-API von Freebase (f�r Freitext-Suche). */
	public final static String BASIS_URL_SEARCH_API = "https://www.googleapis.com/freebase/v1/search";
	
	/** Basis-URL f�r Anfragen an die Topic-API von Freebase (Infos zu einem Topic abfragen, von dem man schon die mid/id hat). */
	public static final String BASIS_URL_TOPIC_API = "https://www.googleapis.com/freebase/v1/topic";
	
	/** Basis-URL der Image-API (an diese URL muss einfach die MID des Bildes angeh�ngt werden). */
	public final static String BASIS_URL_IMAGE_API = "https://usercontent.googleapis.com/freebase/v1/image";

	
	/** Type-ID f�r "Person", siehe auch http://schemas.freebaseapps.com/type?id=/people/person */  
	public final static String TYPE_ID_PERSON = "/people/person";
	
	/** Type-ID f�r "verstorbene Person", siehe auch http://schemas.freebaseapps.com/type?id=/people/deceased_person */
	public final static String TYPE_ID_DECEASED_PERSON = "/people/deceased_person";
	
	/** Type-ID f�r ein Zitat, siehe auch http://schemas.freebaseapps.com/type?id=/media_common/quotation */ 
	public final static String TYPE_ID_QUOTATION = "/media_common/quotation";
	
	
	/** Property-ID f�r "Geburtstag" einer Person, siehe auch http://schemas.freebaseapps.com/property?id=/people/person/date_of_birth */
	public final static String PROPERTY_ID_GEBURTSTAG = "/people/person/date_of_birth";
	
	/** Property-ID f�r "Todestag" einer Person, siehe auch http://schemas.freebaseapps.com/property?id=/people/deceased_person/date_of_death */
	public final static String PROPERTY_ID_TODESTAG = "/people/deceased_person/date_of_death";
	
	/** Property-ID f�r das Geschlecht einer Person, siehe auch http://schemas.freebaseapps.com/property?id=/people/person/gender */
	public final static String PROPERTY_ID_GENDER = "/people/person/gender";
	
	/** Property-ID f�r "Description" einer Person (geerbt von "/common/topic"), siehe auch http://schemas.freebaseapps.com/property?id=/common/topic/description */
	public final static String PROPERTY_ID_DESCRIPTION = "/common/topic/description";
	
	/** Property-ID f�r "Image" einer Person (geerbt von "/common/topic"), siehe auch http://schemas.freebaseapps.com/property?id=/common/topic/image */
	public final static String PROPERTY_ID_IMAGE = "/common/topic/image";

	/** Property-ID die Zitate einer Person, siehe auch http://www.freebase.com/people/person/quotations */
	public final static String PROPERTY_ID_QUOTATIONS = "/people/person/quotations";
	
	/** Property-ID f�r den Autor eines Zitats, siehe auch http://www.freebase.com/media_common/quotation/author */
	public final static String PROPERTY_ID_AUTHOR_OF_QUOTATION = "/media_common/quotation/author";

	
	
};