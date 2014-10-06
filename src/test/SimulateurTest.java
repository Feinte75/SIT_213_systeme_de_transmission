package test;

import static org.junit.Assert.fail;
import ihm.Simulateur;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import exception.InformationNonConforme;
import application.ApplicationTransmissionAnalogiqueParfaite;

public class SimulateurTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {

	}

	@Test
	public void test() {
		// tous les parametres valides n°1
		String args1[] = { "-etape", "2", "-mess", "9", "-s", "-form", "NRZ",
				"-nbEch", "1", "-ampl", "1", "2", "-snr", "1", "-ti", "3", "1",
				"1", "-transducteur" };
		try {
			Simulateur.main(args1);
		} catch (InformationNonConforme e) {
			fail();
		}

		// tous les parametres valides n°2
		String args2[] = { "-etape", "5b", "-mess", "1011101011010101",
				"-form", "NRZT" };
		try {
			Simulateur.main(args2);
		} catch (InformationNonConforme e) {
			fail();
		}

		// tous les parametres valides n°3
		/*
		 * String args3[] = { "-form", "RZ" }; try { Simulateur.main(args3); }
		 * catch (InformationNonConforme e) {fail();}
		 */

		// tous les parametres valides n°4
		String args4[] = { "-form", "nrz" };
		try {
			Simulateur.main(args4);
		} catch (InformationNonConforme e) {
			fail();
		}

		// -etape different de 1 2 3 4a 4b 5a 5b
		String args5[] = { "-etape", "6" };
		try {
			Simulateur.main(args5);
			fail();
		} catch (InformationNonConforme e) {
		}

		// -mess >7 avec chiffres autres que 0 et 1
		String args6[] = { "-mess", "1234567890" };
		try {
			Simulateur.main(args6);
			fail();
		} catch (InformationNonConforme e) {
		}

		// -form autre que RZ/NRZ/NRTZ
		String args7[] = { "-form", "bidule" };
		try {
			Simulateur.main(args7);
			fail();
		} catch (InformationNonConforme e) {
		}

		// -nbEch < 0
		String args8[] = { "-nbEch", "-1" };
		try {
			Simulateur.main(args8);
			fail();
		} catch (InformationNonConforme e) {
		}

		// -ampl min > max
		String args9[] = { "-ampl", "2", "1" };
		try {
			Simulateur.main(args9);
			fail();
		} catch (InformationNonConforme e) {
		}

		// -ti iEmeTrajet pas compris entre 0 et 5
		String args10[] = { "-ti", "6", "1", "1" };
		try {
			Simulateur.main(args10);
			fail();
		} catch (InformationNonConforme e) {
		}
	}
}
