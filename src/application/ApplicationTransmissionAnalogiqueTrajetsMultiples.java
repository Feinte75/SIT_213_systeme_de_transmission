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
			int nbEchantillon, TypeCodage codage, boolean sonde, int nbTrajetIndirect, int decaTempo[], float amplRel[]) {

		// Instanciation des composants de la chaine de transmission
		EmetteurNumeriqueAnalogique ena = new EmetteurNumeriqueAnalogique(min,
				max, nbEchantillon, codage);
		CanalTrajetsMultiples ctm = new CanalTrajetsMultiples(nbTrajetIndirect, decaTempo, amplRel);
		RecepteurAnalogiqueNumerique ran = new RecepteurAnalogiqueNumerique(
				min, max, nbEchantillon, src.getInformationGeneree().nbElements() ,codage);
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

		// Connecter le transmetteur bruite a la source
		src.connecter(ena);
		// Connecter la destination finale au transmetteur bruite
		ena.connecter(ctm);
		ctm.connecter(ran);
		ran.connecter(dstFinale);

		// Verifier que l'utilisateur a bien saisi deux arguments et qu'il a
		// choisi d'utiliser les sondes
		if (sonde) {

			// Connecter les sondes a chaque composant
			src.connecter(sondeSource);
			ena.connecter(sondeEmetteur);

			ctm.connecter(sondeCanal);
			ran.connecter(sondeRecepteur);
		}

		try {
			// Emission de la source et de l'emetteur
			src.emettre();
			ena.emettre();
			// Emission du transmetteur bruite et du recepteur vers la
			// destination finale
			ctm.emettre();
			ran.emettre();
		} catch (InformationNonConforme e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//histogramme.recevoir(trBruite.getHistogramme());

		// appel de la fonction de calcul du taux d'erreur binaire
		System.out.println("\nType de codage :" + codage);
		tauxErreurBinaire(src.getInformationEmise(),
				dstFinale.getInformationRecue());
		System.out
		.println("Le taux d'erreur binaire est égal à " + teb + "%");

	}
}
