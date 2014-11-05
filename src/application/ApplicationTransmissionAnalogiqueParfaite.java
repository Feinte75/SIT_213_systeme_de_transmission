package application;

import sonde.SondeAnalogique;
import sonde.SondeLogique;
import element_transmission.DestinationFinale;
import element_transmission.EmetteurNumeriqueAnalogique;
import element_transmission.RecepteurAnalogiqueNumerique;
import element_transmission.Source;
import element_transmission.TransmetteurParfait;
import element_transmission.TypeCodage;
import exception.InformationNonConforme;

/**
 * Cette classe permet de mettre en oeuvre le livrable n°2 du projet
 * L'application simule une transmission analogique parfaite L'information
 * binaire générée est envoyée dans un emetteur analogique qui va effectuer une
 * conversion numerique/analogique. L'information analogique est ensuite envoyé
 * dans un transmetteur parfait qui envoi l'information vers un recepteur
 * analogique qui se charge de la conversion analogique/numerique.
 * 
 */
public class ApplicationTransmissionAnalogiqueParfaite extends Application {

	/**
	 * Méthode déroulant l'application de transmission analogique parfaite
	 * 
	 * @param src
	 *            Suite de symbole binaire
	 * @param min
	 *            Minimum du signal
	 * @param max
	 *            Maximum du signal
	 * @param nbEchantillon
	 *            Nombre d'échantillon
	 * @param codage
	 *            Type de codage utilisé
	 * @param sonde
	 *            Utilisation des sondes ou non
	 */
	public void execution(Source<Boolean> src, float min, float max,
			int nbEchantillon, TypeCodage codage, boolean sonde) {

		EmetteurNumeriqueAnalogique ena = new EmetteurNumeriqueAnalogique(min,
				max, nbEchantillon, codage);
		TransmetteurParfait<Float, Float> trParfait = new TransmetteurParfait<Float, Float>();
		RecepteurAnalogiqueNumerique ran = new RecepteurAnalogiqueNumerique(
				min, max, nbEchantillon, src.getInformationGeneree().nbElements(), codage);
		DestinationFinale<Boolean> dstFinale = new DestinationFinale<Boolean>();

		SondeLogique sondeSource = new SondeLogique(
				"Signal emis par la source", "Temps", "Valeur logique");
		SondeAnalogique sondeEmetteur = new SondeAnalogique(
				"Signal emis par l'emetteur", "Temps", "Amplitude");
		SondeAnalogique sondeTransmetteur = new SondeAnalogique(
				"Signal emis par le transmetteur", "Temps", "Amplitude");
		SondeLogique sondeRecepteur = new SondeLogique(
				"Signal emis par le recepteur", "Temps", "Valeur logique");

		src.connecter(ena);
		ena.connecter(trParfait);
		trParfait.connecter(ran);
		ran.connecter(dstFinale);

		// Connecter les sondes a chaque composant
		if (sonde) {
			src.connecter(sondeSource);
			ena.connecter(sondeEmetteur);

			trParfait.connecter(sondeTransmetteur);
			ran.connecter(sondeRecepteur);
		}

		try {
			src.emettre();
			ena.emettre();
			trParfait.emettre();
			ran.emettre();
		} catch (InformationNonConforme e) {
			e.printStackTrace();
		}

		// appel de la fonction de calcul du taux d'erreur binaire
		tauxErreurBinaire(src.getInformationEmise(),
				dstFinale.getInformationRecue());
		System.out
				.println("\nLe taux d'erreur binaire est égal à " + teb + "%");
	}
}
