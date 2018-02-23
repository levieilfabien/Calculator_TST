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

import org.junit.Assert;
import main.constantes.Constantes;
import main.outils.PropertiesOutil;

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

	/**
	 * Ce test � pour objectif de v�rifier si la saisie de donn�es non num�rique ou incorrectement format�e est bien g�r�e par l'application.
	 * Si la chaine des valeur n'est pas correcte, on affiche "fill the numbers".
	 * Si l'op�ration n'est pas selectionn�e on affiche "fill the operation select please";
	 * @throws Exception en cas d'erreur.
	 */
	@Test
	public void saisiesTest() throws Exception {	

		// Si l'emplacement du driver chrome est bon, alors on lance le navigateur
		if (Constantes.EMPLACEMENT_CHROME_DRIVER == null) {
			// Initialisation du driver et acc�s � la page
			ChromeDriver driver =  (ChromeDriver) obtenirDriver(Constantes.USE_CHROME);
			driver.get(Constantes.URL_PAGE);
			
			try {
				///////////////////////////////////////////////////
				// Cas aux limites
				///////////////////////////////////////////////////
				
				// Pas de valorisation de l'op�ration
				Assert.assertTrue("Ne pas renseigner l'op�ration : on attendait 'fill the operation select please'", saisieFormulaire(driver, "3", "4", null, "fill the operation select please"));
				// Somme contenant une somme
				Assert.assertTrue("Op�rande B contenant un signe addition", saisieFormulaire(driver, "4", "5+3", Constantes.OPERATION_SOMME, "fill the numbers"));
				// Somme avec des caract�res interdits (ils sont ignor�s)
				Assert.assertTrue("Op�rande A contenant des lettres", saisieFormulaire(driver, "3UNCHIFFR", "4", Constantes.OPERATION_SOMME, "Last result is : 7"));
				// Somme avec un exposant mal �crit
				Assert.assertTrue("Op�rande A contenant des lettres", saisieFormulaire(driver, "3E", "4", Constantes.OPERATION_SOMME, "fill the numbers"));
				// Valorisation de l'op�ration � la valeur par d�faut -- Please select an operation --
				Assert.assertTrue("Renseigner l'op�ration par d�faut : on attendait 'fill the operation select please'", saisieFormulaire(driver, "3", "4", Constantes.OPERATION_PAR_DEFAUT, "fill the operation select please"));
				// Somme avec un grand nombre de chiffres (ici on remarque qu'une troncature (due � la nature des doubles, � lieue)
				Assert.assertTrue("R�sultat disposant d'un nombre de chiffres tr�s important", saisieFormulaire(driver, "0,123456789", "123456789123456789", Constantes.OPERATION_SOMME, "123456789123456789.123456789"));
				

			} catch (AssertionError err) {
				System.out.println("Echec du test sur la v�rification suivante : " + err.getMessage());
				err.printStackTrace();
				throw err;
			}
		} else {
			System.out.println("Le fichier gecko ou chrome driver n'est pas disponible ou l'emplacement est mal d�finie dans les constantes");
		}
	}
	
	/**
	 * Ce test � pour objectif de v�rifier le bon fonctionnement de la somme.
	 * @throws Exception en cas d'erreur.
	 */
	@Test
	public void multiplicationTest() throws Exception {	
		// Si l'emplacement du driver chrome est bon, alors on lance le navigateur
		if (Constantes.EMPLACEMENT_CHROME_DRIVER == null) {
			// Initialisation du driver et acc�s � la page
			ChromeDriver driver =  (ChromeDriver) obtenirDriver(Constantes.USE_CHROME);
			driver.get(Constantes.URL_PAGE);
			
			try {
				///////////////////////////////////////////////////
				// 1] Multiplication "Passantes" :
				///////////////////////////////////////////////////
				
				// Avec nombre n�gatifs
				Assert.assertTrue("Multiplication de nombres n�gatifs", saisieFormulaire(driver, "-2", "-500", Constantes.OPERATION_MULTIPLICATION));
				// Avec un caract�re "+"
				Assert.assertTrue("Multiplication de nombres sign�s", saisieFormulaire(driver, "+123", "-154", Constantes.OPERATION_MULTIPLICATION));
				// Avec des "."
				Assert.assertTrue("Multiplication de nombres � virgules 1/2", saisieFormulaire(driver, "0.123456", "12345", Constantes.OPERATION_MULTIPLICATION));
				// Avec des ","
				Assert.assertTrue("Multiplication de nombres � virgules 2/2", saisieFormulaire(driver, "0,123456", "56779", Constantes.OPERATION_MULTIPLICATION));
				
				///////////////////////////////////////////////////
				// 2] Cas aux limites
				///////////////////////////////////////////////////
				// Multiplication par 0
				Assert.assertTrue("Multiplication par 0", saisieFormulaire(driver, "123", "0", Constantes.OPERATION_MULTIPLICATION));
				// Multiplication de nombre avec Exposant
				Assert.assertTrue("Multiplication avec exposant", saisieFormulaire(driver, "3E4", "6", Constantes.OPERATION_MULTIPLICATION));
				// Multiplication avec un grand nombre de chiffres (ici on remarque qu'une troncature (due � la nature des doubles, � lieue)
				Assert.assertTrue("Multiplication de nombreux long", saisieFormulaire(driver, "123", "123456789123456789", Constantes.OPERATION_MULTIPLICATION));
				
			} catch (AssertionError err) {
				System.out.println("Echec du test sur la v�rification suivante : " + err.getMessage());
				err.printStackTrace();
				throw err;
			}
		} else {
			System.out.println("Le fichier gecko ou chrome driver n'est pas disponible ou l'emplacement est mal d�finie dans les constantes");
		}
	}
	
	/**
	 * Ce test � pour objectif de v�rifier le bon fonctionnement de la somme.
	 * @throws Exception en cas d'erreur.
	 */
	@Test
	public void divisionTest() throws Exception {	
		// Si l'emplacement du driver chrome est bon, alors on lance le navigateur
		if (Constantes.EMPLACEMENT_CHROME_DRIVER == null) {
			// Initialisation du driver et acc�s � la page
			ChromeDriver driver =  (ChromeDriver) obtenirDriver(Constantes.USE_CHROME);
			driver.get(Constantes.URL_PAGE);
			
			try {
				///////////////////////////////////////////////////
				// 1] Cas standards
				///////////////////////////////////////////////////
				
				// Avec nombre n�gatifs
				Assert.assertTrue("Division de nombres n�gatifs", saisieFormulaire(driver, "-2", "-500", Constantes.OPERATION_DIVISION));
				// Avec un caract�re "+"
				Assert.assertTrue("Division de nombres sign�s", saisieFormulaire(driver, "+123", "-154", Constantes.OPERATION_DIVISION));
				// Avec des "."
				Assert.assertTrue("Division de nombres � virgules 1/2", saisieFormulaire(driver, "10.50", "3.5", Constantes.OPERATION_DIVISION));
				// Avec des ","
				Assert.assertTrue("Division de nombres � virgules 2/2", saisieFormulaire(driver, "100,345", "10,5", Constantes.OPERATION_DIVISION));
				
				///////////////////////////////////////////////////
				// 2] Cas aux limites
				///////////////////////////////////////////////////
				// Division par 0
				Assert.assertTrue("Division par 0", saisieFormulaire(driver, "123", "0", Constantes.OPERATION_DIVISION, "divide by 0 is forbidden"));
				// Division de 0
				Assert.assertTrue("Division de 0", saisieFormulaire(driver, "0", "5", Constantes.OPERATION_DIVISION));
				// Division de nombre avec Exposant
				Assert.assertTrue("Division avec exposant", saisieFormulaire(driver, "3E4", "6", Constantes.OPERATION_DIVISION));
				// Multiplication avec un grand nombre de chiffres (ici on remarque qu'une troncature (due � la nature des doubles, � lieue)
				Assert.assertTrue("Division de nombreux long", saisieFormulaire(driver, "123", "123456789123456789", Constantes.OPERATION_DIVISION));
				
			} catch (AssertionError err) {
				System.out.println("Echec du test sur la v�rification suivante : " + err.getMessage());
				err.printStackTrace();
				throw err;
			}
		} else {
			System.out.println("Le fichier gecko ou chrome driver n'est pas disponible ou l'emplacement est mal d�finie dans les constantes");
		}
	}
	
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
			FirefoxBinary ffBinary = new FirefoxBinary(new File(Constantes.EMPLACEMENT_FIREFOX));
			FirefoxProfile profile = configurerProfilFirefox();

			// Initialisation du driver
			retour = new FirefoxDriver(profile);
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
