package element_transmission;
import exception.InformationNonConforme;



/**
 * Classe qui modelise une source fixe dont l'infomation emise est choisie par l'utilisateur
 * @author user
 *
 */

public class SourceFixe extends Source<Boolean> {
	
	public SourceFixe(String arg) {

		informationGeneree = new Information<Boolean>();
		informationEmise = informationGeneree;

		//Parcourir l'argument saisi par l'utilisateur
		for (int i = 0; i < arg.length(); i++) {
			//Si le caractere en cours est un 0
			if (arg.charAt(i) == '0')
				//Ajouter false a la liste informationGeneree
				informationGeneree.add(false);
			//Si le caractere en cours est un 1
			else if (arg.charAt(i) == '1')
				//Ajouter true a la liste informationGeneree
				informationGeneree.add(true);
		}

	}
	/**
	 * pour emettre l'information generee par la source
	 */
	@Override
	public void emettre() throws InformationNonConforme {
		//Parcourir la liste des destinations de la source
		java.util.Iterator<DestinationInterface<Boolean>> itr = destinationsConnectees.iterator();
		while (itr.hasNext()) {
			DestinationInterface<Boolean> di = itr.next();
			//Appeler la methode recevoir pour chaque destination
			di.recevoir(informationEmise);
		}

	}

}
