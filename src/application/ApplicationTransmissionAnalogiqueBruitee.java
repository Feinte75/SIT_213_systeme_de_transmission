package application;

import sonde.SondeAnalogique;
import sonde.SondeLogique;
import element_transmission.DestinationFinale;
import element_transmission.EmetteurNumeriqueAnalogique;
import element_transmission.RecepteurAnalogiqueNumerique;
import element_transmission.Source;
import element_transmission.TransmetteurBruite;
import element_transmission.TypeCodage;
import exception.InformationNonConforme;

/**
 * Cette classe permet de mettre en oeuvre le livrable n°3 du projet
 * L'application simule une transmission analogique avec un ajout de bruit
 * Le bruit est generé au sein du transmetteur selon une loi gaussienne centrée
 * et dont la variance est determiné grace au rapport signal a bruit donné 
 * en paramètre (puissance de bruit = variance)
 * Un histogramme est generé avec les echantillons de bruit afin de vérifier
 * que le bruit suit une loi gaussienne.
 * 
 */
public class ApplicationTransmissionAnalogiqueBruitee extends Application {

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
		EmetteurNumeriqueAnalogique ena = new EmetteurNumeriqueAnalogique(min,
				max, nbEchantillon, codage);
		TransmetteurBruite trBruite = new TransmetteurBruite(nbEchantillon, snr);
		RecepteurAnalogiqueNumerique ran = new RecepteurAnalogiqueNumerique(
				min, max, nbEchantillon, src.getInformationGeneree().nbElements(), codage);
		DestinationFinale<Boolean> dstFinale = new DestinationFinale<Boolean>();

		// Instanciation des sondes
		SondeLogique sondeSource = new SondeLogique(
				"Signal emis par la source", "Temps", "Valeur logique");
		SondeAnalogique sondeTransmetteur = new SondeAnalogique(
				"Signal emis par le transmetteur", "Temps", "Amplitude");
		SondeAnalogique sondeEmetteur = new SondeAnalogique(
				"Signal emis par l'emetteur", "Temps", "Amplitude");
		SondeLogique sondeRecepteur = new SondeLogique(
				"Signal emis par le recepteur", "Temps", "Valeur logique");

		SondeAnalogique histogramme = new SondeAnalogique(
				"Histogramme de répartition du bruit", "Valeur",
				"Nombre d'echantillons");

		// Connecter l'emetteur a la source
		src.connecter(ena);
		// Connecter le transmetteur à l'emetteur
		ena.connecter(trBruite);
		// Connecter le recepteur au transmetteur
		trBruite.connecter(ran);
		// Connecter la destination au recepteur
		ran.connecter(dstFinale);

		if (sonde) {

			// Connecter les sondes a chaque composant
			src.connecter(sondeSource);
			ena.connecter(sondeEmetteur);

			trBruite.connecter(sondeTransmetteur);
			ran.connecter(sondeRecepteur);
		}

		try {
			// Emission de la source et de l'emetteur
			src.emettre();
			ena.emettre();
			// Emission du transmetteur bruite et du recepteur vers la
			// destination finale
			trBruite.emettre();
			ran.emettre();
		} catch (InformationNonConforme e) {
			e.printStackTrace();
		}
		histogramme.recevoir(trBruite.getHistogramme());

		// appel de la fonction de calcul du taux d'erreur binaire
		System.out.println("\nType de codage :" + codage);
		tauxErreurBinaire(src.getInformationEmise(),
				dstFinale.getInformationRecue());
		System.out
				.println("Le taux d'erreur binaire est égal à " + teb + "%");

	}
}
