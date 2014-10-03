

/**
 * Classe qui modelise un transmetteur parfait
 * @author user
 *
 */

public class TransmetteurParfait<E, R> extends Transmetteur<E, R> {

	public TransmetteurParfait() {
	
	}
	/**
	 * Pour recevoir l'information envoyee par le composant precedent 
	 */
	@Override
	public void recevoir(Information<E> information)
			throws InformationNonConforme {

		informationRecue = information;
		informationEmise = (Information<R>) informationRecue;
		
	}
	/**
	 * pour emettre l'information recue par le transmetteur
	 */
	@Override
	public void emettre() throws InformationNonConforme {
		super.emettre();
	}

}
