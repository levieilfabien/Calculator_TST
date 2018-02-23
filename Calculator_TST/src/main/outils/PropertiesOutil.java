package main.outils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Classe permettant la manipulation des fichier de properties des projets de test.
 * Si un projet ne dispose pas du fichier de properties des erreurs seront remont�es.
 * @author levieilfa
 *
 */
public class PropertiesOutil {
	
	/**
	 * Le nom du fichier properties.
	 */
	public static String fichierProperties = "selenium.properties";
	
	/**
	 * Constructeur de l'outil fixant le nom du properties.
	 * @param nom le nom du fichier de properties contenu � la racine du projet de test.
	 */
	public PropertiesOutil(String nom) {
		fichierProperties = nom;
	}
	
	/**
	 * Fonction pour l'initialisation d'une constante.
	 * ATTENTION : Cette fonction bypass la gestion d'erreur, � n'utiliser qu'en cas de certitude de la disponibilit� de la propri�t�.
	 * @param clef la clef dans le fichier properties.
	 * @return la valeur associ�e � la clef ou null si la valeur n'est pas trouv�e.
	 */
	public static String getInfoConstante(final String clef) {
		try {
			return getInfo(clef);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	/**
	 * R�cup�re les infos du fichier de propri�t�s.
	 * @param clef la clef � rechercher dans le fichier de propri�t�s.
	 * @return la chain associ�e � la clef pass�e en param�tre.
	 * @throws Exception en cas d'erreur (notament absence du fichier de propri�t� dans le m�me r�pertoire que le fichier jar)
	 */
	public static String getInfo(final String clef) throws Exception {
		return getInfo(PropertiesOutil.class, clef);
	}
	
	/**
	 * R�cup�re les infos du fichier de propri�t�s.
	 * @param clef la clef � rechercher dans le fichier de propri�t�s.
	 * @return la chain associ�e � la clef pass�e en param�tre.
	 * @throws Exception en cas d'erreur (notament absence du fichier de propri�t� dans le m�me r�pertoire que le fichier jar)
	 */
	public static String getInfo(Class classe, final String clef) throws Exception {
		try {
			// Acc�de au fichier de properties et extrait le libelle associ� au code demand�.
		    Properties properties = new Properties();
		    String retour = null;
		    InputStream fip = null;
		    // On r�cup�re le fichier de propri�t� sous forme de "stream".
		    fip = getPropertiesAsStream(classe);

		    // Si on a bien ouvert le flux vers l'objet on extrait la properties.
		    if (fip != null) {
		    	properties.load(fip);
		    	retour = properties.getProperty(clef);
		    	System.out.println(clef + " = " + retour);	
				return properties.getProperty(clef);
		    } else {
		    	return null;
		    }
		} catch (IOException e) {
			e.printStackTrace();
			// Si le fichier n'est pas trouv� on lance une erreur.
			throw new Exception("V�rifiez la pr�sence du fichier de propri�t�es. " + fichierProperties);
		}
	}
	
	/**
	 * Permet d'obtenir le chemin vers le fichier de properties.
	 * @param classe une classe de r�f�rence du projet de test pour retrouver l'arborescence des resources
	 * @return le chemin vers le fichier de properties.
	 */
	private static InputStream getPropertiesAsStream(Class classe) {
		InputStream retour = null;
		try {		
			// On tente de r�cuperer la resource directement (racine du jar/repertoire de resource d�clar�)
			retour = classe.getClassLoader().getResourceAsStream(fichierProperties);
		} catch (NullPointerException ex) {
			// Si elle n'est pas disponible dans le repertoire de resource par d�faut, on regarde dans le main.
			retour = classe.getResourceAsStream("/main/resources/" + fichierProperties);
		}
		return retour;
	}
	
	/**
	 * Permet d'obtenir le chemin vers le repertoire de properties.
	 * @return le chemin vers le fichier de properties.
	 */
	public static String getRepertoireProjet() {
		return new File("").getAbsolutePath();
	}
	
}
