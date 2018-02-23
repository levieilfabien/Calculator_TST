package main.outils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Classe permettant la manipulation des fichier de properties des projets de test.
 * Si un projet ne dispose pas du fichier de properties des erreurs seront remontées.
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
	 * @param nom le nom du fichier de properties contenu à la racine du projet de test.
	 */
	public PropertiesOutil(String nom) {
		fichierProperties = nom;
	}
	
	/**
	 * Fonction pour l'initialisation d'une constante.
	 * ATTENTION : Cette fonction bypass la gestion d'erreur, à n'utiliser qu'en cas de certitude de la disponibilité de la propriété.
	 * @param clef la clef dans le fichier properties.
	 * @return la valeur associée à la clef ou null si la valeur n'est pas trouvée.
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
	 * Récupère les infos du fichier de propriétés.
	 * @param clef la clef à rechercher dans le fichier de propriétés.
	 * @return la chain associée à la clef passée en paramètre.
	 * @throws Exception en cas d'erreur (notament absence du fichier de propriété dans le même répertoire que le fichier jar)
	 */
	public static String getInfo(final String clef) throws Exception {
		return getInfo(PropertiesOutil.class, clef);
	}
	
	/**
	 * Récupère les infos du fichier de propriétés.
	 * @param clef la clef à rechercher dans le fichier de propriétés.
	 * @return la chain associée à la clef passée en paramètre.
	 * @throws Exception en cas d'erreur (notament absence du fichier de propriété dans le même répertoire que le fichier jar)
	 */
	public static String getInfo(Class classe, final String clef) throws Exception {
		try {
			// Accède au fichier de properties et extrait le libelle associé au code demandé.
		    Properties properties = new Properties();
		    String retour = null;
		    InputStream fip = null;
		    // On récupère le fichier de propriété sous forme de "stream".
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
			// Si le fichier n'est pas trouvé on lance une erreur.
			throw new Exception("Vérifiez la présence du fichier de propriétées. " + fichierProperties);
		}
	}
	
	/**
	 * Permet d'obtenir le chemin vers le fichier de properties.
	 * @param classe une classe de référence du projet de test pour retrouver l'arborescence des resources
	 * @return le chemin vers le fichier de properties.
	 */
	private static InputStream getPropertiesAsStream(Class classe) {
		InputStream retour = null;
		try {		
			// On tente de récuperer la resource directement (racine du jar/repertoire de resource déclaré)
			retour = classe.getClassLoader().getResourceAsStream(fichierProperties);
		} catch (NullPointerException ex) {
			// Si elle n'est pas disponible dans le repertoire de resource par défaut, on regarde dans le main.
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
