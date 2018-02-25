package test.java;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeDriver;

import main.constantes.Constantes;

/**
 * Test case relatif à la division.
 * @author levieilfa
 *
 */
public class DivideTest extends CommonTest {
	
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
			driver.get(Constantes.URL_PAGE_CALCULATOR);
			
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

}
