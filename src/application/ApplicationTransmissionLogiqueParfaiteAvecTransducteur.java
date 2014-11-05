package application;

import sonde.SondeLogique;
import element_transmission.DestinationFinale;
import element_transmission.Source;
import element_transmission.TransducteurEmission;
import element_transmission.TransducteurReception;
import element_transmission.TransmetteurParfait;
import exception.InformationNonConforme;

/**
 * 
 * Cette classe correspond à l'application développée pour le livrable de
 * l'étape 5a La chaine de transmission déduite de cette application est
 * composée de : une source, un tranducteur en émission, un transmetteur
 * parfait, un tranducteur en reception et une destination. Des sondes sont
 * connectés à ces éléments de la chaine pour observer : le signal émit par la
 * source, les tranducteurs et le transmetteur, puis le signal reçu par la
 * destination. Ainsi le taux d'erreur binaire peut être défini en comparant le
 * message envoyé du message reçu.
 *
 *
 */
public class ApplicationTransmissionLogiqueParfaiteAvecTransducteur extends
Application {

	/**
	 * Méthode déroulant l'application de transmission logique parfaite avec
	 * transducteur
	 * 
	 * @param src
	 *            Suite de symbole binaire
	 * @param sonde
	 *            Utilisation des sondes ou non
	 */
	public void execution(Source<Boolean> src, boolean sonde) {

		// Instanciation d'un objet TransducteurEmission, TransmetteurParfait,
		// TransducteurReception et DestinationFinale
		TransducteurEmission transducEm = new TransducteurEmission();
		TransmetteurParfait<Boolean, Boolean> trParfait = new TransmetteurParfait<Boolean, Boolean>();
		TransducteurReception transducRe = new TransducteurReception();
		DestinationFinale<Boolean> dstFinale = new DestinationFinale<Boolean>();

		// Instanciation des sondes logiques
		SondeLogique sondeSource = new SondeLogique(
				"Signal emis par la source", "Temps", "Valeur logique");
		SondeLogique sondeTransducEm = new SondeLogique(
				"Signal emis par le transducteur d'emission", "Temps",
				"Valeur logique");
		SondeLogique sondeTransmetteur = new SondeLogique(
				"Signal emis par le transmetteur", "Temps", "Valeur logique");
		SondeLogique sondeTransducRe = new SondeLogique(
				"Signal emis par le transducteur de reception", "Temps",
				"Valeur logique");

		// Verifier que l'utilisateur a bien saisi deux arguments et qu'il a
		// choisi d'utiliser les sondes
		if (sonde) {
			// Connecter le transducteurEmission a la source
			src.connecter(transducEm);
			// Connecter le transmetteur parfait au transducteurEmission
			transducEm.connecter(trParfait);
			// Connecter le TransducteurReception au Transmetteur Parfait
			trParfait.connecter(transducRe);
			// Connecter la destination finale au TransducteurReception
			transducRe.connecter(dstFinale);
			// Connecter les sondes a chaque composant
			src.connecter(sondeSource);
			transducEm.connecter(sondeTransducEm);
			trParfait.connecter(sondeTransmetteur);
			transducRe.connecter(sondeTransducRe);
		}
		// Si ce n'est pas le cas
		else {
			// Connecter seulement les elements de la chaine de transmission
			// sans sondes
			src.connecter(transducEm);
			transducEm.connecter(trParfait);
			trParfait.connecter(transducRe);
			transducRe.connecter(dstFinale);
		}

		try {
			// Emission de la source
			src.emettre();
			// Emission du tranducteurEmission
			transducEm.emettre();
			// Emission du transmetteur parfait
			trParfait.emettre();
			// Emission du transducteurReception
			transducRe.emettre();
		} catch (InformationNonConforme e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// appel de la fonction de calcul du taux d'erreur binaire
		tauxErreurBinaire(src.getInformationEmise(),
				dstFinale.getInformationRecue());
		System.out
		.println("\nLe taux d'erreur binaire est égal à " + teb + "%");
	}
}