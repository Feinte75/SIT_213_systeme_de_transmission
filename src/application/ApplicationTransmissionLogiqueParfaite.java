package application;
import sonde.SondeLogique;
import element_transmission.DestinationFinale;
import element_transmission.Source;
import element_transmission.TransmetteurParfait;
import exception.InformationNonConforme;

/**
 * 
 * Livrable n 1
 *
 */
public class ApplicationTransmissionLogiqueParfaite extends Application{
	
	public void execution(Source<Boolean> src, boolean sonde){
		
		//Instanciation d'un objet TransmetteurParfait et DestinationFinale
		TransmetteurParfait<Boolean, Boolean> trParfait = new TransmetteurParfait<Boolean, Boolean>();
		DestinationFinale<Boolean> dstFinale = new DestinationFinale<Boolean>();

		//Instanciation des deux sondes logiques
		SondeLogique sondeSource = new SondeLogique("Signal emis par la source");
		SondeLogique sondeTransmetteur = new SondeLogique("Signal emis par le transmetteur");

		//Verifier que l'utilisateur a bien saisi deux arguments et qu'il a choisi d'utiliser les sondes
		if (sonde) {
			//Connecter le transmetteur parfait a la source
			src.connecter(trParfait);
			//Connecter la destination finale au transmetteur parfait
			trParfait.connecter(dstFinale);
			//Connecter les sondes a chaque composant
			src.connecter(sondeSource);
			trParfait.connecter(sondeTransmetteur);
		}
		//Si ce n'est pas le cas
		else {
			//Connecter seulement les elements de la chaine de transmission sans sondes
			src.connecter(trParfait);
			trParfait.connecter(dstFinale);
		}

		try {
			//Emission de la source
			src.emettre();
			//Emission du transmetteur parfait
			trParfait.emettre();
		} catch (InformationNonConforme e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("Info generee par source          :"
				+ src.getInformationGeneree());
		System.out.println("Info emise par source            :"
				+ src.getInformationEmise());
		System.out.println("Info recue par transmetteur      :"
				+ trParfait.getInformationRecue());
		System.out.println("Info emise par transmetteur      :"
				+ trParfait.getInformationEmise());
		System.out.println("Info recue par destination finale:"
				+ dstFinale.getInformationRecue());

		//appel de la fonction de calcul du taux d'erreur binaire
		float tauxErrBin = 0;
		tauxErrBin = tauxErreurBinaire(src.getInformationEmise(), dstFinale.getInformationRecue());
		System.out.println("\nLe taux d'erreur binaire est égal à "+tauxErrBin+"%");
	}
}
