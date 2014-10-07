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
 * 
 * Livrable n 2
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
		DestinationFinale<Boolean> dstFinale = new DestinationFinale<Boolean>();

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
				+ src.getInformationGeneree());
		System.out.println("Info emise par source            :"
				+ src.getInformationEmise());
		System.out.println("Info recue par l'emetteur        :"
				+ ena.getInformationRecue());
		System.out.println("Info emise par l'emetteur        :"
				+ ena.getInformationEmise());
		System.out.println("Info recue par le transmetteur   :"
				+ trParfait.getInformationRecue());
		System.out.println("Info emise par le transmetteur   :"
				+ trParfait.getInformationEmise());
		System.out.println("Info recue par le recepteur      :"
				+ ran.getInformationRecue());
		System.out.println("Info emise par le recepteur      :"
				+ ran.getInformationEmise());
		System.out.println("Info recue par destination finale:"
				+ dstFinale.getInformationRecue());

		// appel de la fonction de calcul du taux d'erreur binaire
		float tauxErrBin = 0;
		tauxErrBin = tauxErreurBinaire(src.getInformationEmise(), dstFinale.getInformationRecue());
		System.out.println("\nLe taux d'erreur binaire est égal à "
				+ tauxErrBin + "%");
	}
}
