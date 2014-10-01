

/**
 * Classe qui modelise un transmetteur parfait
 * @author user
 *
 */

public class TransmetteurParfait extends Transmetteur<Boolean, Boolean> {

	public TransmetteurParfait() {
	
	}
	/**
	 * Pour recevoir l'information envoyee par le composant precedent 
	 */
	@Override
	public void recevoir(Information<Boolean> information)
			throws InformationNonConforme {

		informationRecue = information;
		informationEmise = informationRecue;
		
	}
	/**
	 * pour emettre l'information recue par le transmetteur
	 */
	@Override
	public void emettre() throws InformationNonConforme {

		
		//Parcourir la liste des destinations du transmetteur
		java.util.Iterator<DestinationInterface<Boolean>> itr = destinationsConnectees.iterator();
		while (itr.hasNext()) {
			//Appeler la methode recevoir pour chaque destination
			DestinationInterface<Boolean> di = itr.next();
			di.recevoir(informationEmise);
		}

	}

}
