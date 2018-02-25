package main.constantes;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.By;

import main.outils.PropertiesOutil;

/**
 * Ensemble des constantes manipulées par les tests.
 * On favoriserais en temps normal la mise à disposition des données de type URL dans un fichier properties extérieur.
 * @author levieilfa
 *
 */
public class Constantes {
	
	//////////////////////////////////////////////////// INFORMATIONS TECHNIQUES FIREFOX  ////////////////////////////////////////////////////////////////
	public static final String EMPLACEMENT_FIREFOX = PropertiesOutil.getInfoConstante("EMPLACEMENT_FIREFOX");
	public static final String EMPLACEMENT_PROFIL_FIREFOX = PropertiesOutil.getInfoConstante("EMPLACEMENT_PROFIL_FIREFOX");
	public static final String EMPLACEMENT_GECKO_DRIVER =  System.setProperty("webdriver.gecko.driver", PropertiesOutil.getInfoConstante("EMPLACEMENT_GECKO_DRIVER"));
	
	////////////////////////////////////////////////////INFORMATIONS TECHNIQUES CHROME  ////////////////////////////////////////////////////////////////
	public static final String EMPLACEMENT_CHROME = PropertiesOutil.getInfoConstante("EMPLACEMENT_CHROME");
	public static final String EMPLACEMENT_CHROME_DRIVER = System.setProperty("webdriver.chrome.driver", PropertiesOutil.getInfoConstante("EMPLACEMENT_CHROME_DRIVER"));
	
	////////////////////////////////////////////////////AUTRES INFORMATIONS TECHNIQUES  ////////////////////////////////////////////////////////////////
	public static final String DATE_JOUR_YYYY_MM_DD = new SimpleDateFormat("yyyy_MM_dd").format(new Date());
	public static final int USE_FIREFOX = 1;
	public static final int USE_CHROME = 2;
	
	//////////////////////////////////////////////////// INFORMATIONS RELATIVES AUX TESTS ////////////////////////////////////////////////////////////	
	public static final String URL_PAGE_CALCULATOR = PropertiesOutil.getInfoConstante("URL_PAGE_CALCULATOR");
	public static final String TITRE_PAGE_CALCULATOR = "Calculator";
	public static final String URL_PAGE_PHOTOMANAGER = PropertiesOutil.getInfoConstante("URL_PAGE_DRAGDROP");
	public static final String TITRE_PAGE_PHOTOMANAGER = "Drag and Drop Demo Sites | Testing Site - GlobalSQA";
	
	// Les différents types d'opération
	public static final String OPERATION_PAR_DEFAUT = "";
	public static final String OPERATION_SOMME = "sum";
	public static final String OPERATION_SOUSTRACTION = "substract";
	public static final String OPERATION_MULTIPLICATION = "multiply";
	public static final String OPERATION_DIVISION = "divide";
	
	// Chaines de caractères présentes dans l'IHM
	public static final String PREFIXE_DERNIER_RESULTAT = "Last result";
	
	// Les locateurs des éléments manipulés par les tests sur le calculateur
	public static final By CHAMP_NUMERO_A = new By.ByName("numbera");
	public static final By CHAMP_NUMERO_B = new By.ByXPath(".//*[@*='Fill the number B']");
	public static final By SELECT_OPERATION = new By.ById("operation");
	public static final By VALIDER_FORMULAIRE_CALCUL = new By.ByXPath(".//form[@*='myCompute']//input[@*='submit']");
	public static final By DERNIER_RESULTAT = new By.ByXPath(".//fieldset[1]//p");
	
	// Les locateurs des éléments manipulés par les tests sur le photomanager et de la zone 'trash'
	public static final By PHOTOS = new By.ByXPath(".//html/body/div[1]/ul//img");
	public static final By ZONE_TRASH = new By.ById("trash");
}
