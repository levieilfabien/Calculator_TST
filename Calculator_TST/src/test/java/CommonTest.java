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
 * Fonctions communues utilisées par les différents tests.
 * @author levieilfa
 *
 */
public class CommonTest {

	/**
	 * Id de serialisation.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Ce test à pour objectif de vérifier si la saisie de données non numérique ou incorrectement formatée est bien gérée par l'application.
	 * Si la chaine des valeur n'est pas correcte, on affiche "fill the numbers".
	 * Si l'opération n'est pas selectionnée on affiche "fill the operation select please";
	 * @throws Exception en cas d'erreur.
	 */
	@Test
	public void saisiesTest() throws Exception {	

		// Si l'emplacement du driver chrome est bon, alors on lance le navigateur
		if (Constantes.EMPLACEMENT_CHROME_DRIVER == null) {
			// Initialisation du driver et accès à la page
			ChromeDriver driver =  (ChromeDriver) obtenirDriver(Constantes.USE_CHROME);
			driver.get(Constantes.URL_PAGE);
			
			try {
				///////////////////////////////////////////////////
				// Cas aux limites
				///////////////////////////////////////////////////
				
				// Pas de valorisation de l'opération
				Assert.assertTrue("Ne pas renseigner l'opération : on attendait 'fill the operation select please'", saisieFormulaire(driver, "3", "4", null, "fill the operation select please"));
				// Somme contenant une somme
				Assert.assertTrue("Opérande B contenant un signe addition", saisieFormulaire(driver, "4", "5+3", Constantes.OPERATION_SOMME, "fill the numbers"));
				// Somme avec des caractères interdits (ils sont ignorés)
				Assert.assertTrue("Opérande A contenant des lettres", saisieFormulaire(driver, "3UNCHIFFR", "4", Constantes.OPERATION_SOMME, "Last result is : 7"));
				// Somme avec un exposant mal écrit
				Assert.assertTrue("Opérande A contenant des lettres", saisieFormulaire(driver, "3E", "4", Constantes.OPERATION_SOMME, "fill the numbers"));
				// Valorisation de l'opération à la valeur par défaut -- Please select an operation --
				Assert.assertTrue("Renseigner l'opération par défaut : on attendait 'fill the operation select please'", saisieFormulaire(driver, "3", "4", Constantes.OPERATION_PAR_DEFAUT, "fill the operation select please"));
				// Somme avec un grand nombre de chiffres (ici on remarque qu'une troncature (due à la nature des doubles, à lieue)
				Assert.assertTrue("Résultat disposant d'un nombre de chiffres très important", saisieFormulaire(driver, "0,123456789", "123456789123456789", Constantes.OPERATION_SOMME, "123456789123456789.123456789"));
				

			} catch (AssertionError err) {
				System.out.println("Echec du test sur la vérification suivante : " + err.getMessage());
				err.printStackTrace();
				throw err;
			}
		} else {
			System.out.println("Le fichier gecko ou chrome driver n'est pas disponible ou l'emplacement est mal définie dans les constantes");
		}
	}
	
	/**
	 * Ce test à pour objectif de vérifier le bon fonctionnement de la somme.
	 * @throws Exception en cas d'erreur.
	 */
	@Test
	public void multiplicationTest() throws Exception {	
		// Si l'emplacement du driver chrome est bon, alors on lance le navigateur
		if (Constantes.EMPLACEMENT_CHROME_DRIVER == null) {
			// Initialisation du driver et accès à la page
			ChromeDriver driver =  (ChromeDriver) obtenirDriver(Constantes.USE_CHROME);
			driver.get(Constantes.URL_PAGE);
			
			try {
				///////////////////////////////////////////////////
				// 1] Multiplication "Passantes" :
				///////////////////////////////////////////////////
				
				// Avec nombre négatifs
				Assert.assertTrue("Multiplication de nombres négatifs", saisieFormulaire(driver, "-2", "-500", Constantes.OPERATION_MULTIPLICATION));
				// Avec un caractère "+"
				Assert.assertTrue("Multiplication de nombres signés", saisieFormulaire(driver, "+123", "-154", Constantes.OPERATION_MULTIPLICATION));
				// Avec des "."
				Assert.assertTrue("Multiplication de nombres à virgules 1/2", saisieFormulaire(driver, "0.123456", "12345", Constantes.OPERATION_MULTIPLICATION));
				// Avec des ","
				Assert.assertTrue("Multiplication de nombres à virgules 2/2", saisieFormulaire(driver, "0,123456", "56779", Constantes.OPERATION_MULTIPLICATION));
				
				///////////////////////////////////////////////////
				// 2] Cas aux limites
				///////////////////////////////////////////////////
				// Multiplication par 0
				Assert.assertTrue("Multiplication par 0", saisieFormulaire(driver, "123", "0", Constantes.OPERATION_MULTIPLICATION));
				// Multiplication de nombre avec Exposant
				Assert.assertTrue("Multiplication avec exposant", saisieFormulaire(driver, "3E4", "6", Constantes.OPERATION_MULTIPLICATION));
				// Multiplication avec un grand nombre de chiffres (ici on remarque qu'une troncature (due à la nature des doubles, à lieue)
				Assert.assertTrue("Multiplication de nombreux long", saisieFormulaire(driver, "123", "123456789123456789", Constantes.OPERATION_MULTIPLICATION));
				
			} catch (AssertionError err) {
				System.out.println("Echec du test sur la vérification suivante : " + err.getMessage());
				err.printStackTrace();
				throw err;
			}
		} else {
			System.out.println("Le fichier gecko ou chrome driver n'est pas disponible ou l'emplacement est mal définie dans les constantes");
		}
	}
	
	/**
	 * Ce test à pour objectif de vérifier le bon fonctionnement de la somme.
	 * @throws Exception en cas d'erreur.
	 */
	@Test
	public void divisionTest() throws Exception {	
		// Si l'emplacement du driver chrome est bon, alors on lance le navigateur
		if (Constantes.EMPLACEMENT_CHROME_DRIVER == null) {
			// Initialisation du driver et accès à la page
			ChromeDriver driver =  (ChromeDriver) obtenirDriver(Constantes.USE_CHROME);
			driver.get(Constantes.URL_PAGE);
			
			try {
				///////////////////////////////////////////////////
				// 1] Cas standards
				///////////////////////////////////////////////////
				
				// Avec nombre négatifs
				Assert.assertTrue("Division de nombres négatifs", saisieFormulaire(driver, "-2", "-500", Constantes.OPERATION_DIVISION));
				// Avec un caractère "+"
				Assert.assertTrue("Division de nombres signés", saisieFormulaire(driver, "+123", "-154", Constantes.OPERATION_DIVISION));
				// Avec des "."
				Assert.assertTrue("Division de nombres à virgules 1/2", saisieFormulaire(driver, "10.50", "3.5", Constantes.OPERATION_DIVISION));
				// Avec des ","
				Assert.assertTrue("Division de nombres à virgules 2/2", saisieFormulaire(driver, "100,345", "10,5", Constantes.OPERATION_DIVISION));
				
				///////////////////////////////////////////////////
				// 2] Cas aux limites
				///////////////////////////////////////////////////
				// Division par 0
				Assert.assertTrue("Division par 0", saisieFormulaire(driver, "123", "0", Constantes.OPERATION_DIVISION, "divide by 0 is forbidden"));
				// Division de 0
				Assert.assertTrue("Division de 0", saisieFormulaire(driver, "0", "5", Constantes.OPERATION_DIVISION));
				// Division de nombre avec Exposant
				Assert.assertTrue("Division avec exposant", saisieFormulaire(driver, "3E4", "6", Constantes.OPERATION_DIVISION));
				// Multiplication avec un grand nombre de chiffres (ici on remarque qu'une troncature (due à la nature des doubles, à lieue)
				Assert.assertTrue("Division de nombreux long", saisieFormulaire(driver, "123", "123456789123456789", Constantes.OPERATION_DIVISION));
				
			} catch (AssertionError err) {
				System.out.println("Echec du test sur la vérification suivante : " + err.getMessage());
				err.printStackTrace();
				throw err;
			}
		} else {
			System.out.println("Le fichier gecko ou chrome driver n'est pas disponible ou l'emplacement est mal définie dans les constantes");
		}
	}
	
//	/**
//	 * Ce test à pour objectif de vérifier le bon fonctionnement de la somme.
//	 * @throws Exception en cas d'erreur.
//	 */
//	@Test
//	public void sommeTest() throws Exception {	
//		// Si l'emplacement du driver chrome est bon, alors on lance le navigateur
//		if (Constantes.EMPLACEMENT_CHROME_DRIVER == null) {
//			// Initialisation du driver et accès à la page
//			ChromeDriver driver =  (ChromeDriver) obtenirDriver(Constantes.USE_CHROME);
//			driver.get(Constantes.URL_PAGE);
//			
//			try {
//				///////////////////////////////////////////////////
//				// Sommes
//				///////////////////////////////////////////////////
//				
//				// Somme avec nombre négatifs
//				Assert.assertTrue(saisieFormulaire(driver, "1", "-1", Constantes.OPERATION_SOMME));
//				// Somme avec un caractère "+"
//				Assert.assertTrue(saisieFormulaire(driver, "+123", "-154", Constantes.OPERATION_SOMME));
//				// Somme avec des "."
//				Assert.assertTrue(saisieFormulaire(driver, "0.123456", "12345", Constantes.OPERATION_SOMME));
//				// Somme avec des ","
//				Assert.assertTrue(saisieFormulaire(driver, "0,123456", "56779", Constantes.OPERATION_SOMME));
//
//			} catch (AssertionError err) {
//				System.out.println("Echec du test sur la vérification suivante : " + err.getMessage());
//				err.printStackTrace();
//				throw err;
//			}
//		} else {
//			System.out.println("Le fichier gecko ou chrome driver n'est pas disponible ou l'emplacement est mal définie dans les constantes");
//		}
//	}
	
	/**
	 * Permet de saisir et de valider le formulaire de calcul.
	 * @param driver le driver selenium
	 * @param valeurA la valeur à saisir dans le champ A
	 * @param valeurB la valeur à saisir dans le champ B
	 * @param operation l'opération choisie (null pour ne pas choisir d'opération)
	 */
	public boolean saisieFormulaire(RemoteWebDriver driver, String valeurA, String valeurB, String operation) {
		return saisieFormulaire(driver, valeurA, valeurB, operation, null);
	}
	
	/**
	 * Permet de saisir et de valider le formulaire de calcul en connaissant la valeur à afficher.
	 * @param driver le driver selenium
	 * @param valeurA la valeur à saisir dans le champ A
	 * @param valeurB la valeur à saisir dans le champ B
	 * @param operation l'opération choisie (null pour ne pas choisir d'opération)
	 */
	public boolean saisieFormulaire(RemoteWebDriver driver, String valeurA, String valeurB, String operation, String valeurAttendue) {
		boolean retour = true;
		
		// Saisie du formulaire
		viderEtSaisir(driver, Constantes.CHAMP_NUMERO_A, valeurA);
		viderEtSaisir(driver, Constantes.CHAMP_NUMERO_B, valeurB);
		
		// Choix de l'opération
		Select selectionOperation = new Select(driver.findElement(Constantes.SELECT_OPERATION));
		if (operation != null) {
			selectionOperation.selectByValue(operation);
		}
		
		// Validation du formulaire
		driver.findElement(Constantes.VALIDER_FORMULAIRE_CALCUL).click();
		
		String resultatObtenu = driver.findElement(Constantes.DERNIER_RESULTAT).getText();
		
		if (valeurAttendue == null) {
			// Vérification du résultat du calcul : cette vérification est facultative et ne doit pas renvoyer d'exception
			try {
				// On calcule le résultat attendu de la calculatrice
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
				
				// On analyse le résultat affiché par la calculatrice et on compare avec l'attendu
				if (resultatObtenu.contains(Constantes.PREFIXE_DERNIER_RESULTAT)) {
					resultatObtenu = resultatObtenu.substring(resultatObtenu.lastIndexOf(" ") + 1);
					retour = (resultatAttendu == Double.parseDouble(resultatObtenu));
				} else {
					retour = false;
				}
				
				System.out.println("Attendu : " + resultatAttendu + "| Obtenu : " + resultatObtenu);
				
			} catch (ClassCastException ex) {
				// Impossible de convertir les données en paramètre, peu être test t'on des valeurs non numérique. Ne représente en aucun cas une erreur, mais on renvoie faux.
				retour = false;
			} catch (NullPointerException ex) {
				// Le resultat attendu n'as pas pu être valorisé, et est donc null, par défaut on renvoie faux.
				retour = false;
			} catch (NumberFormatException ex) {
				// Impossible de convertir les données en paramètre, on test des valeurs non numériques.
				retour = false;
			}
		} else {
			// On analyse le résultat affiché par la calculatrice et on compare avec l'attendu
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
	 * @param by le locateur de la zone à saisir
	 * @param texte le texte à saisir.
	 */
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
