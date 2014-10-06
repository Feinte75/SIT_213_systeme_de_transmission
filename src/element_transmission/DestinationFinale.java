package element_transmission;
import exception.InformationNonConforme;



/**
 * Classe qui modelise une destination finale de la chaine de transmission
 * @author user
 *
 */

public class DestinationFinale<T> extends Destination<T> {
	/**
	 * Pour recevoir l'information envoyee par le composant precedent 
	 */
	@Override
	public void recevoir(Information<T> information)
			throws InformationNonConforme {

		informationRecue = information;
	}

}
