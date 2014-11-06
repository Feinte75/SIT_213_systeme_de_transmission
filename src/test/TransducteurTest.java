package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import element_transmission.Source;
import element_transmission.SourceFixe;
import element_transmission.TransducteurEmission;
import element_transmission.TransducteurReception;
import exception.InformationNonConforme;

public class TransducteurTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testTransductionEmission() {
		
		Source<Boolean> src = new SourceFixe("1");
		TransducteurEmission te = new TransducteurEmission();
		src.connecter(te);
		try {
			src.emettre();
			te.emettre();
		} catch (InformationNonConforme e) {

			e.printStackTrace();
		}
		
		// Verification nb Elements (pour 1 bit en entrée, 3 en sortie)
		assertTrue(te.getInformationEmise().nbElements() == 3);		
		
		// Verification de la bonne sequence de bits en sortie (1 0 1)
		int i = 0;
		if(te.getInformationEmise().iemeElement(i++).booleanValue() == false)fail();
		if(te.getInformationEmise().iemeElement(i++).booleanValue() == true)fail();
		if(te.getInformationEmise().iemeElement(i).booleanValue() == false)fail();
		
		src = new SourceFixe("0");
		te = new TransducteurEmission();
		src.connecter(te);
		
		try {
			src.emettre();
			te.emettre();
		} catch (InformationNonConforme e) {

			e.printStackTrace();
		}
		
		// Verification nb Elements (pour 1 bit en entrée, 3 en sortie)
		assertTrue(te.getInformationEmise().nbElements() == 3);		
		
		// Verification de la bonne sequence de bits en sortie ( 0 1 0 )
		i = 0;
		if(te.getInformationEmise().iemeElement(i++).booleanValue() == true)fail();
		if(te.getInformationEmise().iemeElement(i++).booleanValue() == false)fail();
		if(te.getInformationEmise().iemeElement(i).booleanValue() == true)fail();
	}
	
	@Test
	public void testTransductionReception() {
		
		// Partie bit emis = TRUE
		
		Source<Boolean> src = new SourceFixe("101");
		TransducteurReception tr = new TransducteurReception();
		src.connecter(tr);
		try {
			src.emettre();
			tr.emettre();
		} catch (InformationNonConforme e) {

			e.printStackTrace();
		}
		
		// Verification nb Elements (pour 3 bit en entrée, 1 en sortie)
		assertTrue(tr.getInformationEmise().nbElements() == 1);		
		
		// Verification du bit en sortie
		assertTrue(tr.getInformationEmise().iemeElement(0).booleanValue());

		src = new SourceFixe("001");
		tr = new TransducteurReception();
		src.connecter(tr);
		try {
			src.emettre();
			tr.emettre();
		} catch (InformationNonConforme e) {

			e.printStackTrace();
		}
		
		// Verification nb Elements (pour 3 bit en entrée, 1 en sortie)
		assertTrue(tr.getInformationEmise().nbElements() == 1);		
		
		// Verification du bit en sortie
		assertTrue(tr.getInformationEmise().iemeElement(0).booleanValue());

		
		src = new SourceFixe("100");
		tr = new TransducteurReception();
		src.connecter(tr);
		try {
			src.emettre();
			tr.emettre();
		} catch (InformationNonConforme e) {

			e.printStackTrace();
		}
		
		// Verification nb Elements (pour 3 bit en entrée, 1 en sortie)
		assertTrue(tr.getInformationEmise().nbElements() == 1);		
		
		// Verification du bit en sortie
		assertTrue(tr.getInformationEmise().iemeElement(0).booleanValue());

		src = new SourceFixe("111");
		tr = new TransducteurReception();
		src.connecter(tr);
		try {
			src.emettre();
			tr.emettre();
		} catch (InformationNonConforme e) {

			e.printStackTrace();
		}
		
		// Verification nb Elements (pour 3 bit en entrée, 1 en sortie)
		assertTrue(tr.getInformationEmise().nbElements() == 1);		
		
		// Verification du bit en sortie
		assertTrue(tr.getInformationEmise().iemeElement(0).booleanValue());

		
		// Partie bit emis = FALSE
		
		src = new SourceFixe("010");
		tr = new TransducteurReception();
		src.connecter(tr);
		
		try {
			src.emettre();
			tr.emettre();
		} catch (InformationNonConforme e) {

			e.printStackTrace();
		}
		
		// Verification nb Elements (pour 3 bit en entrée, 1 en sortie)
		assertTrue(tr.getInformationEmise().nbElements() == 1);		
		
		// Verification du bit en sortie
		assertTrue(!tr.getInformationEmise().iemeElement(0).booleanValue());
		
		src = new SourceFixe("110");
		tr = new TransducteurReception();
		src.connecter(tr);
		
		try {
			src.emettre();
			tr.emettre();
		} catch (InformationNonConforme e) {

			e.printStackTrace();
		}
		
		// Verification nb Elements (pour 3 bit en entrée, 1 en sortie)
		assertTrue(tr.getInformationEmise().nbElements() == 1);		
		
		// Verification du bit en sortie
		assertTrue(!tr.getInformationEmise().iemeElement(0).booleanValue());
		
		src = new SourceFixe("011");
		tr = new TransducteurReception();
		src.connecter(tr);
		
		try {
			src.emettre();
			tr.emettre();
		} catch (InformationNonConforme e) {

			e.printStackTrace();
		}
		
		// Verification nb Elements (pour 3 bit en entrée, 1 en sortie)
		assertTrue(tr.getInformationEmise().nbElements() == 1);		
		
		// Verification du bit en sortie
		assertTrue(!tr.getInformationEmise().iemeElement(0).booleanValue());
		
		src = new SourceFixe("000");
		tr = new TransducteurReception();
		src.connecter(tr);
		
		try {
			src.emettre();
			tr.emettre();
		} catch (InformationNonConforme e) {

			e.printStackTrace();
		}
		
		// Verification nb Elements (pour 3 bit en entrée, 1 en sortie)
		assertTrue(tr.getInformationEmise().nbElements() == 1);		
		
		// Verification du bit en sortie
		assertTrue(!tr.getInformationEmise().iemeElement(0).booleanValue());
	}

}
