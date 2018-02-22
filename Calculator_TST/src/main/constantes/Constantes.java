package main.constantes;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.By;

/**
 * Ensemble des constantes manipulées par les tests.
 * On favoriserais en temps normal la mise à disposition des données de type URL dans un fichier properties extérieur.
 * @author levieilfa
 *
 */
public class Constantes {
	
	//////////////////////////////////////////////////// INFORMATIONS TECHNIQUES FIREFOX  ////////////////////////////////////////////////////////////////
	public static final String EMPLACEMENT_FIREFOX = "C:\\Users\\levieilfa\\AppData\\Local\\Mozilla Firefox\\firefox.exe";
	public static final String EMPLACEMENT_PROFIL_FIREFOX = "C:\\Users\\levieilfa\\AppData\\Roaming\\Mozilla\\Firefox\\Profiles\\Automate";
	public static final String EMPLACEMENT_GECKO_DRIVER =  System.setProperty("webdriver.gecko.driver", "C:\\Users\\levieilfa\\AppData\\Roaming\\Mozilla\\Firefox\\Profiles\\geckodriver64bit.exe");
	
	////////////////////////////////////////////////////INFORMATIONS TECHNIQUES CHROME  ////////////////////////////////////////////////////////////////
	public static final String EMPLACEMENT_CHROME = "C:\\work\\apps\\Chrome\\Application\\chrome.exe";
	public static final String EMPLACEMENT_CHROME_DRIVER = System.setProperty("webdriver.chrome.driver", "C:\\work\\apps\\Chrome\\Application\\chromedriver.exe");
	
	////////////////////////////////////////////////////AUTRES INFORMATIONS TECHNIQUES  ////////////////////////////////////////////////////////////////
	public static final String DATE_JOUR_YYYY_MM_DD = new SimpleDateFormat("yyyy_MM_dd").format(new Date());
	public static final int USE_FIREFOX = 1;
	public static final int USE_CHROME = 2;
	
	//////////////////////////////////////////////////// INFORMATIONS RELATIVES AUX TESTS ////////////////////////////////////////////////////////////	
	public static final String URL_PAGE = "http://tests.qa.weborama.com/calculator/";
	public static final String TITRE_PAGE = "Calculator";
	
	public static final By CHAMP_NUMERO_A = new By.ByName("numbera");
	public static final By CHAMP_NUMERO_B = new By.ByXPath(".//*[@*='Fill the number B']");
	public static final By SELECT_OPERATION = new By.ById("operation");
	public static final By VALIDER_FORMULAIRE_CALCUL = new By.ByXPath(".//form[@*='myCompute']//input[@*='submit']");
}
