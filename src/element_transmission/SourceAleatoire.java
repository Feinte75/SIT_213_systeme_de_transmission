package element_transmission;



import java.util.Random;

import exception.InformationNonConforme;
/**
 * Classe qui modelise une source aletoire dont la taille de l'infomation emise est choisie par l'utilisateur
 */


public class SourceAleatoire extends Source<Boolean> {

	public SourceAleatoire(String arg) {

		informationGeneree = new Information<Boolean>();
		informationEmise = informationGeneree;
		//La taille du message genere est l'argument converti en int
		int binaryLength = Integer.parseInt(arg);
		//Generer la suite aletoire de bits
		generateRandomSymbols(binaryLength);
	}
	/**
	 * Generer de maniere aleatoire une suite de bits(0 ou 1) dans la taille est le nombre passe le parametre
	 * @param size
	 * 			taille de suite aleatoire de bits
	 */
	public void generateRandomSymbols(int size) {

		Random randGen = new Random();
		for (int i = 0; i < size; i++) {
			int rand = randGen.nextInt(2);
			//Si le bit genere est egale a 0
			if (rand == 0)
				//Ajouter false a la liste informationGeneree
				informationGeneree.add(false);
			//Si le bit genere est egale a 1
			else if (rand == 1)
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
			//Appeler la methode recevoir pour chaque destination
			DestinationInterface<Boolean> di = itr.next();
			di.recevoir(informationEmise);
		}
	}

}
