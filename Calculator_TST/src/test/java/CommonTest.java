package test.java;

import java.io.File;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;

import main.constantes.Constantes;

/**
 * Fonctions communues utilisées par les différents tests.
 * @author levieilfa
 *
 */
public class CommonTest {

	/**
	 * Id de serialisation.
	 */
	private static final long serialVersionUID = 1L;

	@Test
	public void accesTest() throws Exception {	
		
		//System.out.println(Constantes.EMPLACEMENT_GECKO_FIREFOX);
		if (Constantes.EMPLACEMENT_CHROME_DRIVER == null) {
			ChromeDriver driver =  (ChromeDriver) obtenirDriver(Constantes.USE_CHROME);
			driver.get(Constantes.URL_PAGE);
			
			// Saisie du formulaire
			viderEtSaisir(driver, Constantes.CHAMP_NUMERO_A, "1");
			viderEtSaisir(driver, Constantes.CHAMP_NUMERO_B, "-1");
			
			// Choix de l'opération
			Select selectionOperation = new Select(driver.findElement(Constantes.SELECT_OPERATION));
			selectionOperation.selectByValue("sum");
			
			// Validation du formulaire
			driver.findElement(Constantes.VALIDER_FORMULAIRE_CALCUL).click();
			
		} else {
			System.out.println("Le fichier gecko ou chrome driver n'est pas disponible ou l'emplacement est mal définie dans les constantes");
		}
	}
	
	public void viderEtSaisir(RemoteWebDriver driver, By by, String texte) {
			WebElement element = driver.findElement(by);
			element.clear();
			element.sendKeys(texte);
	}
	
	/**
	 * Permet d'obtenir le driver souhaité en fonction de l'implémentation choisie.
	 * @param implementation l'implméntation souhaitée (les valeurs possibles sont définies dans les constantes)
	 * @return le driver initialisé aves les informations définies dans les constantes.
	 */
	public RemoteWebDriver obtenirDriver(int implementation) {
		RemoteWebDriver retour = null;
		
		if (implementation == Constantes.USE_CHROME) {
			//Configuration du driver pour pointer sur le bon binaire
			ChromeOptions option = new ChromeOptions();
			option.setExperimentalOption("useAutomationExtension", false);
			option.setBinary(Constantes.EMPLACEMENT_CHROME);

			// Initialisation du driver
			retour = new ChromeDriver(option);;
		} else if (implementation == Constantes.USE_FIREFOX) {
			//Configuration du driver pour pointer sur le bon binaire
			FirefoxBinary ffBinary = new FirefoxBinary(new File(Constantes.EMPLACEMENT_FIREFOX));
			FirefoxProfile profile = configurerProfilFirefox();

			// Initialisation du driver
			retour = new FirefoxDriver(profile);
		}

		return retour;
	}
	
	
	/**
	 * Configuration du profil pour test adapté à Firefox.
	 * @return Le profil utilisé sur le modèle du profil "Automate"
	 */
	public static FirefoxProfile configurerProfilFirefox() {
		
		// Initialisation du profil avec le profil automate requis
		//ProfilesIni profileIni = new ProfilesIni();
		FirefoxProfile profile = new FirefoxProfile(new File(Constantes.EMPLACEMENT_PROFIL_FIREFOX));
		
		profile.setPreference("app.update.enabled", Boolean.FALSE);
		//profile.setPreference("network.negotiate-auth.trusted-uris", "https://open-workplace.intranatixis.com/nfi/front-middle/wiki-izivente/Rfrentiel/Liens%20Izivente.aspx");
		//profile.setPreference("network.automatic-ntlm-auth.trusted-uris", "https://open-workplace.intranatixis.com/nfi/front-middle/wiki-izivente/Rfrentiel/Liens%20Izivente.aspx");
		
		//Désactivation des plugins potentiellements nuisible au test
		profile.setPreference("browser.download.pluginOverrideTypes", "");
		profile.setPreference("plugin.disable_full_page_plugin_for_types", "application/pdf,application/vnd.fdf,application/vnd.adobe.xfdf,application/vnd.adobe.xdp+xml");
		
		//Ne pas lire directement les PDF dans le navigateur en cas de téléchargement
		profile.setPreference("pdfjs.disabled", Boolean.TRUE);
		profile.setPreference("pdfjs.firstRun", Boolean.FALSE);
		profile.setPreference("pdfjs.previousHandler.alwaysAskBeforeHandling", Boolean.FALSE);
		profile.setPreference("pdfjs.previousHandler.preferredAction", 4);
		profile.setPreference("pdfjs.disabled", Boolean.TRUE);
		
		//Fixer un répertoire de téléchargement à la racine
		profile.setPreference("browser.download.useDownloadDir", Boolean.TRUE);
		profile.setPreference("browser.download.manager.focusWhenStarting", Boolean.FALSE);
		profile.setPreference("browser.download.folderList", 2);
        profile.setPreference("browser.download.dir", new File(".\\").getAbsolutePath());
        
        //Désactivation de la popup de téléchargement au profit d'un téléchargement direct
        profile.setPreference("browser.helperApps.alwaysAsk.force", Boolean.FALSE);
        profile.setPreference("browser.download.manager.showWhenStarting", Boolean.FALSE);
        profile.setPreference("browser.download.manager.useWindow", Boolean.FALSE);
		profile.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/pdf,text/pdf,application/octet-stream,application/x-pdf,text/plain,text/xml");

		return profile;
	}
}
