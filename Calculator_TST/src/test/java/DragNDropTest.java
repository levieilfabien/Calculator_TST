package test.java;

import java.util.List;

import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import main.constantes.Constantes;

/**
 * Classe de test pour le second exercice "dragNdrop"
 * @author levieilfa
 *
 */
public class DragNDropTest extends CommonTest {

	/**
	 * Test de drag and drop avec le navigateur Chrome
	 * @throws Exception en cas d'erreur.
	 */
	@Test
	public void dragDropChromeTest() throws Exception {	

		// Si l'emplacement du driver chrome est bon, alors on lance le navigateur
		if (Constantes.EMPLACEMENT_CHROME_DRIVER == null) {
			// Initialisation du driver et accès à la page
			ChromeDriver driver =  (ChromeDriver) obtenirDriver(Constantes.USE_CHROME);
			driver.get(Constantes.URL_PAGE_PHOTOMANAGER);
			// La troisième frame contient les informations attendues
			WebDriver driverFrame = driver.switchTo().frame(3);
			
			// Obtenir les différents éléments "photo"
			List<WebElement> elementsPhotos = driverFrame.findElements(Constantes.PHOTOS);
			// Effectuer le drag and drop des photos sur la zone "poubelle"
			for (WebElement photo : elementsPhotos) {
				// On attend que la photo soit disponible pour la manipuler
				new WebDriverWait(driverFrame, 10).until(ExpectedConditions.elementToBeClickable(photo));
				// On décrit l'action
				Actions builder = new Actions(driver);
				// On compile l'action et on l'execute.
				builder.dragAndDrop(photo, driverFrame.findElement(Constantes.ZONE_TRASH)).perform();
			}
		}
	}
	
	/**
	 * Test de drag and drop avec le navigateur Firefox
	 * @throws Exception en cas d'erreur.
	 */
	@Test
	public void dragDropFirefoxTest() throws Exception {	

		// Si l'emplacement du driver chrome est bon, alors on lance le navigateur
		if (Constantes.EMPLACEMENT_GECKO_DRIVER == null) {
			// Initialisation du driver et accès à la page
			FirefoxDriver driver =  (FirefoxDriver) obtenirDriver(Constantes.USE_FIREFOX);
			driver.get(Constantes.URL_PAGE_PHOTOMANAGER);
			// La seconde frame contient les informations attendues
			WebDriver driverFrame = driver.switchTo().frame(2);
			// Obtenir les différents éléments "photo"
			List<WebElement> elementsPhotos = driverFrame.findElements(Constantes.PHOTOS);
			// Effectuer le drag and drop des photos sur la zone "poubelle"
			for (WebElement photo : elementsPhotos) {
				new WebDriverWait(driverFrame, 10).until(ExpectedConditions.elementToBeClickable(photo));
				// On décrit l'action
				Actions builder = new Actions(driver);
				// On compile l'action et on l'execute.
				builder.dragAndDrop(photo, driverFrame.findElement(Constantes.ZONE_TRASH)).perform();	
			}
		}
	}
}
