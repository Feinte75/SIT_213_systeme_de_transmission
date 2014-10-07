package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import element_transmission.EmetteurNumeriqueAnalogique;
import element_transmission.Information;
import element_transmission.SourceFixe;
import element_transmission.TypeCodage;
import exception.InformationNonConforme;

public class EmetteurNumeriqueAnalogiqueTest {

	private SourceFixe src;
	private EmetteurNumeriqueAnalogique ena;
	
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		src = new SourceFixe("10101100");
	}

	@Test
	public void testCodageNRZ() {
		float min = -3;
		float max = 7;
		ena = new EmetteurNumeriqueAnalogique(min, max, 2, TypeCodage.NRZ);
		src.connecter(ena);
		
		try {
			src.emettre();
			ena.emettre();
		} catch (InformationNonConforme e) {
			
		}
		
		Information<Float> infoAttendu = new Information<Float>();
		infoAttendu.add(max);
		infoAttendu.add(max);
		infoAttendu.add(min);
		infoAttendu.add(min);
		infoAttendu.add(max);
		infoAttendu.add(max);
		infoAttendu.add(min);
		infoAttendu.add(min);
		infoAttendu.add(max);
		infoAttendu.add(max);
		infoAttendu.add(max);
		infoAttendu.add(max);
		infoAttendu.add(min);
		infoAttendu.add(min);
		infoAttendu.add(min);
		infoAttendu.add(min);

		Information<Float> infoEmise = ena.getInformationEmise();
		assertTrue(infoAttendu.equals(infoEmise));
	}

	@Test
	public void testCodageNRZT() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testCodageRZ() {
		/*float min = -3;
		float max = 7;
		ena = new EmetteurNumeriqueAnalogique(min, max, 2, TypeCodage.RZ);
		src.connecter(ena);
		
		try {
			src.emettre();
			ena.emettre();
		} catch (InformationNonConforme e) {
			
		}
		
		Information<Float> infoAttendu = new Information<Float>();
		infoAttendu.add(max);
		infoAttendu.add(0f);
		infoAttendu.add(min);
		infoAttendu.add(0f);
		infoAttendu.add(max);
		infoAttendu.add(max);
		infoAttendu.add(min);
		infoAttendu.add(min);
		infoAttendu.add(max);
		infoAttendu.add(max);
		infoAttendu.add(max);
		infoAttendu.add(max);
		infoAttendu.add(min);
		infoAttendu.add(min);
		infoAttendu.add(min);
		infoAttendu.add(min);

		Information<Float> infoEmise = ena.getInformationEmise();
		assertTrue(infoAttendu.equals(infoEmise));*/
	}
}
