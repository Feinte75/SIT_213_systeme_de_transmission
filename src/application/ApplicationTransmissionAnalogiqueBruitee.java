package application;

import element_transmission.TypeCodage;
import sonde.SondeAnalogique;
import sonde.SondeLogique;
import element_transmission.DestinationFinale;
import element_transmission.EmetteurNumeriqueAnalogique;
import element_transmission.RecepteurAnalogiqueNumerique;
import element_transmission.Source;
import element_transmission.TransmetteurBruite;
import exception.InformationNonConforme;

/**
 * Cette classe permet simuler la chaine de transmission dans le cas o� le
 * signat est burit� par le transmetteur
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
				min, max, nbEchantillon, codage);
		DestinationFinale<Boolean> dstFinale = new DestinationFinale<Boolean>();

		// Instanciation des sondes
		SondeLogique sondeSource = new SondeLogique("Signal emis par la source", "Temps", "Valeur logique");
		SondeAnalogique sondeTransmetteur = new SondeAnalogique(
				"Signal emis par le transmetteur : Type codage "+codage, "Temps", "Amplitude");
		SondeAnalogique sondeEmetteur = new SondeAnalogique(
				"Signal emis par l'emetteur : Type codage "+codage, "Temps", "Amplitude");
		SondeLogique sondeRecepteur = new SondeLogique(
				"Signal emis par le recepteur: Type codage "+codage, "Temps", "Valeur logique");
		
		SondeAnalogique histogramme = new SondeAnalogique(
				"Histogramme de répartition du bruit : Type codage "+codage, "Valeur", "Nombre d'echantillons");


		// Connecter le transmetteur bruite a la source
		src.connecter(ena);
		// Connecter la destination finale au transmetteur bruite
		ena.connecter(trBruite);
		trBruite.connecter(ran);
		ran.connecter(dstFinale);
		
		// Verifier que l'utilisateur a bien saisi deux arguments et qu'il a
		// choisi d'utiliser les sondes
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
			// Emission du transmetteur bruite et du recepteur vers la destination finale
			trBruite.emettre();
			ran.emettre();
		} catch (InformationNonConforme e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		histogramme.recevoir(trBruite.getHistogramme());
		System.out.println("*************************************************************************************************************");
		System.out.println("*************************************** Type de codage : "+codage+" *****************************************");
		System.out.println("*************************************************************************************************************");
		System.out.println("Info generee par source          :"
				+ src.getInformationGeneree());
//		System.out.println("Info emise par source            :"
//				+ src.getInformationEmise());
//		System.out.println("Info recue par l'emetteur        :"
//				+ ena.getInformationRecue());
//		System.out.println("Info emise par l'emetteur        :"
//				+ ena.getInformationEmise());
//		System.out.println("Info recue par le transmetteur   :"
//				+ trBruite.getInformationRecue());
//		System.out.println("Info emise par le transmetteur   :"
//				+ trBruite.getInformationEmise());
//		System.out.println("Info recue par le recepteur      :"
//				+ ran.getInformationRecue());
		System.out.println("Info emise par le recepteur      :"
				+ ran.getInformationEmise());
		System.out.println("Info recue par destination finale:"
				+ dstFinale.getInformationRecue());

		// appel de la fonction de calcul du taux d'erreur binaire
		tauxErreurBinaire(src.getInformationEmise(),
				dstFinale.getInformationRecue());
		System.out.println("\nLe taux d'erreur binaire est égal à "
				+ teb + "%");
		System.out.println("***********************************************************************************************************************");
	}
}