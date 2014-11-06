package test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

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
		
	}

	@Test
	public void testCodageNRZ() {
		float min = 0;
		float max = 4;
		
		src = new SourceFixe("1");
		
		ena = new EmetteurNumeriqueAnalogique(min, max, 4, TypeCodage.NRZ);
		src.connecter(ena);
		
		try {
			src.emettre();
			ena.emettre();
		} catch (InformationNonConforme e) {
			
		}
		
		int i = 0;
		while(i < 4){
			if(ena.getInformationEmise().iemeElement(i).floatValue() != max)fail();
			i++;
		}
		
		src = new SourceFixe("0");
		
		ena = new EmetteurNumeriqueAnalogique(min, max, 4, TypeCodage.NRZ);
		src.connecter(ena);
		
		try {
			src.emettre();
			ena.emettre();
		} catch (InformationNonConforme e) {
			
		}
		i = 0;
		while(i < 4){
			if(ena.getInformationEmise().iemeElement(i).floatValue() != min)fail();
			i++;
		}
	}

	@Test
	public void testCodageNRZT() {
		
		float min = 0;
		float max = 3;
		
		src = new SourceFixe("1");
		
		ena = new EmetteurNumeriqueAnalogique(min, max, 6, TypeCodage.NRZT);
		src.connecter(ena);
		
		try {
			src.emettre();
			ena.emettre();
		} catch (InformationNonConforme e) {
		}
		
		assertTrue(ena.getInformationEmise().nbElements() == 6);
		
		int i = 0;
		if(ena.getInformationEmise().iemeElement(i++).floatValue() != min)fail();
		if(ena.getInformationEmise().iemeElement(i++).floatValue() != max/2)fail();
		if(ena.getInformationEmise().iemeElement(i++).floatValue() != max)fail();
		if(ena.getInformationEmise().iemeElement(i++).floatValue() != max)fail();
		if(ena.getInformationEmise().iemeElement(i++).floatValue() != max)fail();
		if(ena.getInformationEmise().iemeElement(i++).floatValue() != max/2)fail();
		
		src = new SourceFixe("0");
		
		ena = new EmetteurNumeriqueAnalogique(min, max, 6, TypeCodage.NRZT);
		src.connecter(ena);
		
		try {
			src.emettre();
			ena.emettre();
		} catch (InformationNonConforme e) {
		}
		
		assertTrue(ena.getInformationEmise().nbElements() == 6);
		
		i = 0;
		while(i < 6){
			if(ena.getInformationEmise().iemeElement(i).floatValue() != min)fail();
			i++;
		}
	}
	
	@Test
	public void testCodageRZ() {
		
		float min = 0;
		float max = 4;
		
		src = new SourceFixe("1");
		
		ena = new EmetteurNumeriqueAnalogique(min, max, 4, TypeCodage.RZ);
		src.connecter(ena);
		
		try {
			src.emettre();
			ena.emettre();
		} catch (InformationNonConforme e) {
			
		}
		
		int i = 0;
		while(i < 2){
			if(ena.getInformationEmise().iemeElement(i).floatValue() != max)fail();
			i++;
		}
		while(i < 4){
			if(ena.getInformationEmise().iemeElement(i).floatValue() != min)fail();
			i++;
		}
		
		src = new SourceFixe("0");
		
		ena = new EmetteurNumeriqueAnalogique(min, max, 4, TypeCodage.RZ);
		src.connecter(ena);
		
		try {
			src.emettre();
			ena.emettre();
		} catch (InformationNonConforme e) {
			
		}
		i = 0;
		while(i < 4){
			if(ena.getInformationEmise().iemeElement(i).floatValue() != min)fail();
			i++;
		}
		
	}
}
