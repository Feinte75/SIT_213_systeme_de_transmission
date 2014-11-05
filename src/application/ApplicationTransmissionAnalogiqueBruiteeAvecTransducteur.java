package application;

import sonde.SondeAnalogique;
import sonde.SondeLogique;
import element_transmission.DestinationFinale;
import element_transmission.EmetteurNumeriqueAnalogique;
import element_transmission.RecepteurAnalogiqueNumerique;
import element_transmission.Source;
import element_transmission.TransducteurEmission;
import element_transmission.TransducteurReception;
import element_transmission.TransmetteurBruite;
import element_transmission.TypeCodage;
import exception.InformationNonConforme;

/**
 * Cette classe permet simuler la chaine de transmission dans le cas o� le
 * signat est burit� par le transmetteur et avec des transducteur (phase 5b)
 * 
 */
public class ApplicationTransmissionAnalogiqueBruiteeAvecTransducteur extends
		Application {

	/**
	 * M�thode qui englobe la cr�ation et la connexion des composants de la
	 * cha�ne de transmission dans le cas du signal bruit�
	 * 
	 * @param src
	 *            Suite de symboles binaires
	 * @param min
	 *            Valeur min du signal
	 * @param max
	 *            Valeur max du signal
	 * @param nbEchantillon
	 *            Nombre d'�chantillons par symbole
	 * @param codage
	 *            Type du codage � utiliser
	 * @param sonde
	 *            Sp�cifie l'utilisation ou pas de la sonde
	 * @param snr
	 *            Rapport signal � bruit de la simulation
	 */
	public void execution(Source<Boolean> src, float min, float max,
			int nbEchantillon, TypeCodage codage, boolean sonde, float snr) {

		// Instanciation des composants de la chaine de transmission
		TransducteurEmission transducEm = new TransducteurEmission();
		EmetteurNumeriqueAnalogique ena = new EmetteurNumeriqueAnalogique(min,
				max, nbEchantillon, codage);
		TransmetteurBruite trBruite = new TransmetteurBruite(nbEchantillon, snr);
		RecepteurAnalogiqueNumerique ran = new RecepteurAnalogiqueNumerique(
				min, max, nbEchantillon, 3 * src.getInformationGeneree()
						.nbElements(), codage); // nbBits envoyé multiplié par 3 du au transducteur
		TransducteurReception transducRe = new TransducteurReception();
		DestinationFinale<Boolean> dstFinale = new DestinationFinale<Boolean>();

		// Instanciation des sondes
		SondeLogique sondeSource = new SondeLogique(
				"Signal emis par la source", "Temps", "Valeur logique");
		SondeLogique sondeTransducEm = new SondeLogique(
				"Signal emis par le transducteur d'emission", "Temps",
				"Valeur logique");
		SondeAnalogique sondeTransmetteur = new SondeAnalogique(
				"Signal emis par le transmetteur", "Temps", "Amplitude");
		SondeAnalogique sondeEmetteur = new SondeAnalogique(
				"Signal emis par l'emetteur", "Temps", "Amplitude");
		SondeLogique sondeTransducRe = new SondeLogique(
				"Signal emis par le transducteur de reception", "Temps",
				"Valeur logique");
		SondeLogique sondeRecepteur = new SondeLogique(
				"Signal emis par le recepteur", "Temps", "Valeur logique");

		SondeAnalogique histogramme = new SondeAnalogique(
				"Histogramme de répartition du bruit", "Valeur",
				"Nombre d'echantillons");

		// Connecter le transducteurEmission a la source
		src.connecter(transducEm);
		// Connecter l'emetteur au transducteurEmission
		transducEm.connecter(ena);
		// Connecter le transmetteur à l'emetteur
		ena.connecter(trBruite);
		// Connecter le recepteur au transmetteur
		trBruite.connecter(ran);
		// Connecteur le transducteurReception au recepteur
		ran.connecter(transducRe);
		// Connecter la destination au transducteurReception
		transducRe.connecter(dstFinale);

		if (sonde) {

			// Connecter les sondes a chaque composant
			src.connecter(sondeSource);
			transducEm.connecter(sondeTransducEm);
			transducRe.connecter(sondeTransducRe);
			ena.connecter(sondeEmetteur);
			trBruite.connecter(sondeTransmetteur);
			ran.connecter(sondeRecepteur);
		}

		try {
			// Emission des composants de la chaine de transmission
			src.emettre();
			transducEm.emettre();
			ena.emettre();
			trBruite.emettre();
			ran.emettre();
			transducRe.emettre();
		} catch (InformationNonConforme e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		histogramme.recevoir(trBruite.getHistogramme());

		// appel de la fonction de calcul du taux d'erreur binaire
		System.out.println("\nType de codage :" + codage);
		tauxErreurBinaire(src.getInformationEmise(),
				dstFinale.getInformationRecue());
		System.out.println("Le taux d'erreur binaire est égal à " + teb + "%");

	}
}
