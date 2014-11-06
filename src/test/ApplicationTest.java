package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import application.ApplicationTransmissionAnalogiqueBruitee;
import application.ApplicationTransmissionAnalogiqueParfaite;
import application.ApplicationTransmissionLogiqueParfaite;
import application.ApplicationTransmissionLogiqueParfaiteAvecTransducteur;
import element_transmission.SourceAleatoire;
import element_transmission.SourceFixe;
import element_transmission.TypeCodage;

public class ApplicationTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	/**
	 * Test de bout en bout de l'application transmission logique parfaite
	 */
	@Test
	public void testApplicationTransmissionLogiqueParfaite() {

		ApplicationTransmissionLogiqueParfaite app = new ApplicationTransmissionLogiqueParfaite();
		app.execution(new SourceAleatoire("10"), false);
		assertEquals(app.getTeb(), 0, 10);

		app.execution(new SourceFixe("1000110"), false);
		assertEquals(app.getTeb(), 0, 10);
	}

	/**
	 * Test de bout en bout de l'application transmission analogique parfaite
	 * Focus sur les differents codage
	 */
	@Test
	public void testApplicationTransmissionAnalogiqueParfaite() {
		
		ApplicationTransmissionAnalogiqueParfaite app = new ApplicationTransmissionAnalogiqueParfaite();
		
		// Test codage RZ
		// Test min negatif et max positif
		app.execution(new SourceAleatoire("10"), -5, 5, 40, TypeCodage.RZ, false);
		assertEquals(app.getTeb(), 0, 10);

		// Test min et max positif
		app.execution(new SourceAleatoire("10"), 5, 10, 40, TypeCodage.RZ, false);
		assertEquals(app.getTeb(), 0, 10);

		// Test min et max negatif
		app.execution(new SourceAleatoire("10"), -10, -5, 40, TypeCodage.RZ, false);
		assertEquals(app.getTeb(), 0, 10);

		// Test codage NRZ
		// Test min negatif et max positif
		app.execution(new SourceAleatoire("10"), -5, 5, 40, TypeCodage.NRZ, false);
		assertEquals(app.getTeb(), 0, 10);

		// Test min et max positif
		app.execution(new SourceAleatoire("10"), 5, 10, 40, TypeCodage.NRZ, false);
		assertEquals(app.getTeb(), 0, 10);

		// Test min et max negatif
		app.execution(new SourceAleatoire("10"), -10, -5, 40, TypeCodage.NRZ, false);
		assertEquals(app.getTeb(), 0, 10);

		// Test codage NRZT
		// Test min negatif et max positif
		app.execution(new SourceAleatoire("10"), -5, 5, 40, TypeCodage.NRZT, false);
		assertEquals(app.getTeb(), 0, 10);

		// Test min et max positif
		app.execution(new SourceAleatoire("10"), 5, 10, 40, TypeCodage.NRZT, false);
		assertEquals(app.getTeb(), 0, 10);

		// Test min et max negatif
		app.execution(new SourceAleatoire("10"), -10, -5, 40, TypeCodage.NRZT, false);
		assertEquals(app.getTeb(), 0, 10);
	}
	
	/**
	 * Test de bout en bout de l'application transmission analogique bruitée
	 * Le test de la generation est validé par l'histogramme. Le caractère aléatoire du bruit
	 * ne permet pas de valider les tests à chaque fois. La methodologie appliqué est la repetition 
	 * un certains nombre de fois de l'execution puis l'assertion par rapport à la moyenne du teb.
	 */
	@Test
	public void testApplicationTransmissionAnalogiqueBruitee(){
		ApplicationTransmissionAnalogiqueBruitee app = new ApplicationTransmissionAnalogiqueBruitee();
		
		float teb;
		
		// Test SNR élevé
		teb = 0;
		for(int i = 0; i < 50; i++){
			app.execution(new SourceAleatoire("10"), 0f, 5f, 40, TypeCodage.NRZT, false, 20f);
			teb += app.getTeb();
		}
		teb /= 50;
		assertTrue(teb < 10f);
		
		teb = 0;
		for(int i = 0; i < 50; i++){
			app.execution(new SourceAleatoire("10"), 0f, 5f, 40, TypeCodage.NRZ, false, 20f);
			teb += app.getTeb();
		}
		teb /= 50;
		assertTrue(teb < 10f);
		
		teb = 0;
		for(int i = 0; i < 50; i++){
			app.execution(new SourceAleatoire("10"), 0f, 5f, 40, TypeCodage.RZ, false, 20f);
			teb += app.getTeb();
		}
		teb /= 50;
		assertTrue(teb < 10f);
		
		// Test SNR faible
		teb = 0;
		for(int i = 0; i < 50; i++){
			app.execution(new SourceAleatoire("10"), 0f, 5f, 40, TypeCodage.NRZT, false, 0.7f);
			teb += app.getTeb();
		}
		teb /= 50;
		assertTrue(teb > 10f);
		
		teb = 0;
		for(int i = 0; i < 50; i++){
			app.execution(new SourceAleatoire("10"), 0f, 5f, 40, TypeCodage.NRZ, false, -20f);
			teb += app.getTeb();
		}
		teb /= 50;
		assertTrue(teb > 10f);
		
		teb = 0;
		for(int i = 0; i < 50; i++){
			app.execution(new SourceAleatoire("10"), 0f, 5f, 40, TypeCodage.RZ, false, 0.7f);
			teb += app.getTeb();
		}
		teb /= 50;
		assertTrue(teb > 10f);
		
	}
	
	@Test
	public void testApplicationTransmissionLogiqueParfaiteAvecTransducteur(){
		
		ApplicationTransmissionLogiqueParfaiteAvecTransducteur app = new ApplicationTransmissionLogiqueParfaiteAvecTransducteur();
		
		
	}
	
	@Test
	public void testApplicationTransmissionAnalogiqueBruiteeTrajetsMultiples(){
		
	}
	
	@Test
	public void testApplicationTransmissionAnalogiqueTrajetsMultiples(){
		
	}
	

}
