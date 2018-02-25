package test.java;

import java.io.File;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;

import main.constantes.Constantes;

/**
 * Fonctions communues utilis�es par les diff�rents tests.
 * @author levieilfa
 *
 */
public class CommonTest {

	/**
	 * Id de serialisation.
	 */
	private static final long serialVersionUID = 1L;

//	/**
//	 * Ce test � pour objectif de v�rifier le bon fonctionnement de la somme.
//	 * @throws Exception en cas d'erreur.
//	 */
//	@Test
//	public void sommeTest() throws Exception {	
//		// Si l'emplacement du driver chrome est bon, alors on lance le navigateur
//		if (Constantes.EMPLACEMENT_CHROME_DRIVER == null) {
//			// Initialisation du driver et acc�s � la page
//			ChromeDriver driver =  (ChromeDriver) obtenirDriver(Constantes.USE_CHROME);
//			driver.get(Constantes.URL_PAGE);
//			
//			try {
//				///////////////////////////////////////////////////
//				// Sommes
//				///////////////////////////////////////////////////
//				
//				// Somme avec nombre n�gatifs
//				Assert.assertTrue(saisieFormulaire(driver, "1", "-1", Constantes.OPERATION_SOMME));
//				// Somme avec un caract�re "+"
//				Assert.assertTrue(saisieFormulaire(driver, "+123", "-154", Constantes.OPERATION_SOMME));
//				// Somme avec des "."
//				Assert.assertTrue(saisieFormulaire(driver, "0.123456", "12345", Constantes.OPERATION_SOMME));
//				// Somme avec des ","
//				Assert.assertTrue(saisieFormulaire(driver, "0,123456", "56779", Constantes.OPERATION_SOMME));
//
//			} catch (AssertionError err) {
//				System.out.println("Echec du test sur la v�rification suivante : " + err.getMessage());
//				err.printStackTrace();
//				throw err;
//			}
//		} else {
//			System.out.println("Le fichier gecko ou chrome driver n'est pas disponible ou l'emplacement est mal d�finie dans les constantes");
//		}
//	}
	
	/**
	 * Permet de saisir et de valider le formulaire de calcul.
	 * @param driver le driver selenium
	 * @param valeurA la valeur � saisir dans le champ A
	 * @param valeurB la valeur � saisir dans le champ B
	 * @param operation l'op�ration choisie (null pour ne pas choisir d'op�ration)
	 */
	public boolean saisieFormulaire(RemoteWebDriver driver, String valeurA, String valeurB, String operation) {
		return saisieFormulaire(driver, valeurA, valeurB, operation, null);
	}
	
	/**
	 * Permet de saisir et de valider le formulaire de calcul en connaissant la valeur � afficher.
	 * @param driver le driver selenium
	 * @param valeurA la valeur � saisir dans le champ A
	 * @param valeurB la valeur � saisir dans le champ B
	 * @param operation l'op�ration choisie (null pour ne pas choisir d'op�ration)
	 */
	public boolean saisieFormulaire(RemoteWebDriver driver, String valeurA, String valeurB, String operation, String valeurAttendue) {
		boolean retour = true;
		
		// Saisie du formulaire
		viderEtSaisir(driver, Constantes.CHAMP_NUMERO_A, valeurA);
		viderEtSaisir(driver, Constantes.CHAMP_NUMERO_B, valeurB);
		
		// Choix de l'op�ration
		Select selectionOperation = new Select(driver.findElement(Constantes.SELECT_OPERATION));
		if (operation != null) {
			selectionOperation.selectByValue(operation);
		}
		
		// Validation du formulaire
		driver.findElement(Constantes.VALIDER_FORMULAIRE_CALCUL).click();
		
		String resultatObtenu = driver.findElement(Constantes.DERNIER_RESULTAT).getText();
		
		if (valeurAttendue == null) {
			// V�rification du r�sultat du calcul : cette v�rification est facultative et ne doit pas renvoyer d'exception
			try {
				// On calcule le r�sultat attendu de la calculatrice
				Double resultatAttendu = null;
				Double valeurANumerique = Double.parseDouble(valeurA.replace(",", "."));
				Double valeurBNumerique = Double.parseDouble(valeurB.replace(",", "."));
				switch(operation) {
				case Constantes.OPERATION_SOMME : resultatAttendu = valeurANumerique + valeurBNumerique; break;
				case Constantes.OPERATION_SOUSTRACTION : resultatAttendu = valeurANumerique - valeurBNumerique; break;
				case Constantes.OPERATION_MULTIPLICATION : resultatAttendu = valeurANumerique * valeurBNumerique; break;
				case Constantes.OPERATION_DIVISION : resultatAttendu = valeurANumerique / valeurBNumerique; break;
				default : return false;
				}
				
				// On analyse le r�sultat affich� par la calculatrice et on compare avec l'attendu
				if (resultatObtenu.contains(Constantes.PREFIXE_DERNIER_RESULTAT)) {
					resultatObtenu = resultatObtenu.substring(resultatObtenu.lastIndexOf(" ") + 1);
					retour = (resultatAttendu == Double.parseDouble(resultatObtenu));
				} else {
					retour = false;
				}
				
				System.out.println("Attendu : " + resultatAttendu + "| Obtenu : " + resultatObtenu);
				
			} catch (ClassCastException ex) {
				// Impossible de convertir les donn�es en param�tre, peu �tre test t'on des valeurs non num�rique. Ne repr�sente en aucun cas une erreur, mais on renvoie faux.
				retour = false;
			} catch (NullPointerException ex) {
				// Le resultat attendu n'as pas pu �tre valoris�, et est donc null, par d�faut on renvoie faux.
				retour = false;
			} catch (NumberFormatException ex) {
				// Impossible de convertir les donn�es en param�tre, on test des valeurs non num�riques.
				retour = false;
			}
		} else {
			// On analyse le r�sultat affich� par la calculatrice et on compare avec l'attendu
			if (resultatObtenu != null) {
				retour = resultatObtenu.equals(valeurAttendue);
			} else {
				retour = (valeurAttendue == null);
			}
		}
		
		return retour;
	}
	
	/**
	 * Permet de vider un champ et de saisir une nouvelle valeur pour celui ci.
	 * @param driver le driver selenium
	 * @param by le locateur de la zone � saisir
	 * @param texte le texte � saisir.
	 */
	public void viderEtSaisir(RemoteWebDriver driver, By by, String texte) {
			WebElement element = driver.findElement(by);
			element.clear();
			element.sendKeys(texte);
	}
	
	/**
	 * Permet d'obtenir le driver souhait� en fonction de l'impl�mentation choisie.
	 * @param implementation l'implm�ntation souhait�e (les valeurs possibles sont d�finies dans les constantes)
	 * @return le driver initialis� aves les informations d�finies dans les constantes.
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
			//FirefoxBinary ffBinary = new FirefoxBinary(new File(Constantes.EMPLACEMENT_FIREFOX));
			//FirefoxProfile profile = configurerProfilFirefox();

			// Initialisation du driver
			//retour = new FirefoxDriver(profile);
			retour = new FirefoxDriver();
		}

		return retour;
	}
	
	
	/**
	 * Configuration du profil pour test adapt� � Firefox.
	 * @return Le profil utilis� sur le mod�le du profil "Automate"
	 */
	public static FirefoxProfile configurerProfilFirefox() {
		
		// Initialisation du profil avec le profil automate requis
		//ProfilesIni profileIni = new ProfilesIni();
		FirefoxProfile profile = new FirefoxProfile(new File(Constantes.EMPLACEMENT_PROFIL_FIREFOX));
		
		profile.setPreference("app.update.enabled", Boolean.FALSE);
		//profile.setPreference("network.negotiate-auth.trusted-uris", "https://open-workplace.intranatixis.com/nfi/front-middle/wiki-izivente/Rfrentiel/Liens%20Izivente.aspx");
		//profile.setPreference("network.automatic-ntlm-auth.trusted-uris", "https://open-workplace.intranatixis.com/nfi/front-middle/wiki-izivente/Rfrentiel/Liens%20Izivente.aspx");
		
		//D�sactivation des plugins potentiellements nuisible au test
		profile.setPreference("browser.download.pluginOverrideTypes", "");
		profile.setPreference("plugin.disable_full_page_plugin_for_types", "application/pdf,application/vnd.fdf,application/vnd.adobe.xfdf,application/vnd.adobe.xdp+xml");
		
		//Ne pas lire directement les PDF dans le navigateur en cas de t�l�chargement
		profile.setPreference("pdfjs.disabled", Boolean.TRUE);
		profile.setPreference("pdfjs.firstRun", Boolean.FALSE);
		profile.setPreference("pdfjs.previousHandler.alwaysAskBeforeHandling", Boolean.FALSE);
		profile.setPreference("pdfjs.previousHandler.preferredAction", 4);
		profile.setPreference("pdfjs.disabled", Boolean.TRUE);
		
		//Fixer un r�pertoire de t�l�chargement � la racine
		profile.setPreference("browser.download.useDownloadDir", Boolean.TRUE);
		profile.setPreference("browser.download.manager.focusWhenStarting", Boolean.FALSE);
		profile.setPreference("browser.download.folderList", 2);
        profile.setPreference("browser.download.dir", new File(".\\").getAbsolutePath());
        
        //D�sactivation de la popup de t�l�chargement au profit d'un t�l�chargement direct
        profile.setPreference("browser.helperApps.alwaysAsk.force", Boolean.FALSE);
        profile.setPreference("browser.download.manager.showWhenStarting", Boolean.FALSE);
        profile.setPreference("browser.download.manager.useWindow", Boolean.FALSE);
		profile.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/pdf,text/pdf,application/octet-stream,application/x-pdf,text/plain,text/xml");

		return profile;
	}
}
