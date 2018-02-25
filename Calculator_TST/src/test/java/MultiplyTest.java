package test.java;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeDriver;

import main.constantes.Constantes;

/**
 * Test cas relatif aux multiplication
 * @author levieilfa
 *
 */
public class MultiplyTest extends CommonTest {

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
			driver.get(Constantes.URL_PAGE_CALCULATOR);
			
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
}
