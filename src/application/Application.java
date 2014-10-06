package application;
import element_transmission.Information;


public abstract class Application {
	
	/**
	 * Cette fonction permet de calculer et de retourner le taux d'erreur binaire
	 * entre le message envoyé à la source et le message reçut par la
	 * destination.
	 * 
	 * @param source La source d'information
	 * @param destination La destination de la chaine 
	 * @return 
	 */
	public static float tauxErreurBinaire(Information<Boolean> messEmis, Information<Boolean> messRecu) {
		float errBinaire = 0;
		int nbErr = 0;
		for (int i = 0; i < messEmis.nbElements(); i++) {
			if (messEmis.iemeElement(i).booleanValue() != messRecu.iemeElement(i).booleanValue()) { // Test sur les valeurs (booleanValue) et non sur les references des objets Boolean
				//System.out.println("mssemis : "+);
				nbErr++;
			}
		}
		System.out.println("Nb err : "+ nbErr + " nbElements :"+messEmis.nbElements());
		errBinaire =  ((float)nbErr / messEmis.nbElements())*100;
		return errBinaire;
		
	}
}
