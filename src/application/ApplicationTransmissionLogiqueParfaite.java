package application;

import sonde.SondeLogique;
import element_transmission.DestinationFinale;
import element_transmission.Source;
import element_transmission.TransmetteurParfait;
import exception.InformationNonConforme;

/**
 * 
 * Cette classe correspond à l'application développée pour le livrable numéro 1
 * La chaine de transmission déduite de cette application est composée de : une
 * source, un transmetteur parfait et une destination. Des sondes sont connectés
 * à ces trois éléments de la chaine pour observer : le signal émit par la
 * source et le transmetteur, puis le signal reçu par la destination. Ainsi le
 * taux d'erreur binaire peut être défini en comparant le message envoyé du
 * message reçu.
 *
 *
 */
public class ApplicationTransmissionLogiqueParfaite extends Application {

	/**
	 * Méthode déroulant l'application de transmission logique parfaite
	 * 
	 * @param src
	 *            Suite de symbole binaire
	 * @param sonde
	 *            Utilisation des sondes ou non
	 */
	public void execution(Source<Boolean> src, boolean sonde) {

		// Instanciation d'un objet TransmetteurParfait et DestinationFinale
		TransmetteurParfait<Boolean, Boolean> trParfait = new TransmetteurParfait<Boolean, Boolean>();
		DestinationFinale<Boolean> dstFinale = new DestinationFinale<Boolean>();

		// Instanciation des deux sondes logiques
		SondeLogique sondeSource = new SondeLogique("Signal emis par la source", "Temps", "Valeur logique");
		SondeLogique sondeTransmetteur = new SondeLogique(
				"Signal emis par le transmetteur", "Temps", "Valeur logique");

		// Verifier que l'utilisateur a bien saisi deux arguments et qu'il a
		// choisi d'utiliser les sondes
		if (sonde) {
			// Connecter le transmetteur parfait a la source
			src.connecter(trParfait);
			// Connecter la destination finale au transmetteur parfait
			trParfait.connecter(dstFinale);
			// Connecter les sondes a chaque composant
			src.connecter(sondeSource);
			trParfait.connecter(sondeTransmetteur);
		}
		// Si ce n'est pas le cas
		else {
			// Connecter seulement les elements de la chaine de transmission
			// sans sondes
			src.connecter(trParfait);
			trParfait.connecter(dstFinale);
		}

		try {
			// Emission de la source
			src.emettre();
			// Emission du transmetteur parfait
			trParfait.emettre();
		} catch (InformationNonConforme e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// appel de la fonction de calcul du taux d'erreur binaire
		tauxErreurBinaire(src.getInformationEmise(),
				dstFinale.getInformationRecue());
		System.out.println("\nLe taux d'erreur binaire est égal à "
				+ teb + "%");
	}
}
