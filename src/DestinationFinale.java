

/**
 * Classe qui modelise une destination finale de la chaine de transmission
 * @author user
 *
 */

public class DestinationFinale extends Destination<Boolean> {
	/**
	 * Pour recevoir l'information envoyee par le composant precedent 
	 */
	@Override
	public void recevoir(Information<Boolean> information)
			throws InformationNonConforme {

		informationRecue = information;
	}

}
