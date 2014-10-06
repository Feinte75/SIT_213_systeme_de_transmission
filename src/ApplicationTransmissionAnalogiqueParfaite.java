/**
 * 
 * Livrable n�2
 *
 */
public class ApplicationTransmissionAnalogiqueParfaite extends Application {


	public void execution(Source<Boolean> src, float min, float max, int nbEchantillon,
			TypeCodage codage, boolean sonde) {

		// Instanciation d'un objet TransmetteurParfait et DestinationFinale
		EmetteurNumeriqueAnalogique ena = new EmetteurNumeriqueAnalogique(min,
				max, nbEchantillon, codage);
		TransmetteurParfait<Float, Float> trParfait = new TransmetteurParfait<Float, Float>();
		RecepteurAnalogiqueNumerique ran = new RecepteurAnalogiqueNumerique(
				min, max, nbEchantillon, codage);
		DestinationFinale dstFinale = new DestinationFinale();

		// Instanciation des deux sondes logiques
		SondeLogique sondeSource = new SondeLogique("Signal emis par la source");
		SondeAnalogique sondeTransmetteur = new SondeAnalogique(
				"Signal emis par le transmetteur");
		SondeAnalogique sondeEmetteur = new SondeAnalogique(
				"Signal emis par l'emetteur");
		SondeLogique sondeRecepteur = new SondeLogique(
				"Signal emis par le recepteur");

		// Verifier que l'utilisateur a bien saisi deux arguments et qu'il a
		// choisi d'utiliser les sondes

		// Connecter le transmetteur parfait a la source
		src.connecter(ena);
		// Connecter la destination finale au transmetteur parfait
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
			// Emission de la source
			src.emettre();
			ena.emettre();
			// Emission du transmetteur parfait
			trParfait.emettre();
			ran.emettre();
		} catch (InformationNonConforme e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("Info generee par source          :"
				+ src.informationGeneree);
		System.out.println("Info emise par source            :"
				+ src.getInformationEmise());
		System.out.println("Info recue par transmetteur      :"
				+ trParfait.getInformationRecue());
		System.out.println("Info emise par transmetteur      :"
				+ trParfait.getInformationEmise());
		System.out.println("Info recue par destination finale:"
				+ dstFinale.getInformationRecue());

		// appel de la fonction de calcul du taux d'erreur binaire
		float tauxErrBin = 0;
		tauxErrBin = tauxErreurBinaire(src, dstFinale);
		System.out.println("\nLe taux d'erreur binaire est égal à "
				+ tauxErrBin + "%");
	}
}
