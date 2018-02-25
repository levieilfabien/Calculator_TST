package test.java;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeDriver;

import main.constantes.Constantes;

/**
 * Test case relatif à la saisie du formulaire et sa validation.
 * @author levieilfa
 *
 */
public class SaisieTest extends CommonTest {

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
			driver.get(Constantes.URL_PAGE_CALCULATOR);
			
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
}
