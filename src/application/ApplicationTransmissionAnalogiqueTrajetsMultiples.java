package application;

import sonde.SondeAnalogique;
import sonde.SondeLogique;
import element_transmission.CanalTrajetsMultiples;
import element_transmission.DestinationFinale;
import element_transmission.EmetteurNumeriqueAnalogique;
import element_transmission.RecepteurAnalogiqueNumerique;
import element_transmission.Source;
import element_transmission.TypeCodage;
import exception.InformationNonConforme;

public class ApplicationTransmissionAnalogiqueTrajetsMultiples extends
Application {

	public void execution(Source<Boolean> src, float min, float max,
			int nbEchantillon, TypeCodage codage, boolean sonde,
			int nbTrajetIndirect, int decaTempo[], float amplRel[]) {

		// Instanciation des composants de la chaine de transmission
		EmetteurNumeriqueAnalogique ena = new EmetteurNumeriqueAnalogique(min,
				max, nbEchantillon, codage);
		CanalTrajetsMultiples ctm = new CanalTrajetsMultiples(nbTrajetIndirect,
				decaTempo, amplRel);
		RecepteurAnalogiqueNumerique ran = new RecepteurAnalogiqueNumerique(
				min, max, nbEchantillon, src.getInformationGeneree().nbElements(), codage);
		DestinationFinale<Boolean> dstFinale = new DestinationFinale<Boolean>();

		// Instanciation des sondes
		SondeLogique sondeSource = new SondeLogique(
				"Signal emis par la source", "Temps", "Valeur logique");
		SondeAnalogique sondeCanal = new SondeAnalogique(
				"Signal emis par le canal", "Temps", "Amplitude");
		SondeAnalogique sondeEmetteur = new SondeAnalogique(
				"Signal emis par l'emetteur", "Temps", "Amplitude");
		SondeLogique sondeRecepteur = new SondeLogique(
				"Signal emis par le recepteur", "Temps", "Valeur logique");

		SondeAnalogique histogramme = new SondeAnalogique(
				"Histogramme de répartition du bruit", "Valeur",
				"Nombre d'echantillons");

		src.connecter(ena);
		ena.connecter(ctm);
		ctm.connecter(ran);
		ran.connecter(dstFinale);

		if (sonde) {

			// Connecter les sondes a chaque composant
			src.connecter(sondeSource);
			ena.connecter(sondeEmetteur);

			ctm.connecter(sondeCanal);
			ran.connecter(sondeRecepteur);
		}

		try {
			src.emettre();
			ena.emettre();
			ctm.emettre();
			ran.emettre();
		} catch (InformationNonConforme e) {
			e.printStackTrace();
		}

		// appel de la fonction de calcul du taux d'erreur binaire
		System.out.println("\nType de codage :" + codage);
		tauxErreurBinaire(src.getInformationEmise(),
				dstFinale.getInformationRecue());
		System.out.println("Le taux d'erreur binaire est égal à " + teb + "%");

	}
}
