
public abstract class Application {
	
	/**
	 * Cette foncion permet de calculer et de retourner le taux d'erreur binaire
	 * entre le message envoyé à la source et le message reçut par la
	 * destination.
	 * 
	 * @param source
	 * @param destination
	 * @return
	 */
	public static float tauxErreurBinaire(Source<Boolean> source,
			Destination<Boolean> destination) {
		Information<Boolean> messEmis = source.getInformationEmise();
		Information<Boolean> messRecu = destination.getInformationRecue();
		float errBinaire = 0;
		int nbErr = 0;
		for (int i = 0; i < messEmis.nbElements(); i++) {
			System.out.println("messEmis : "+ messEmis.iemeElement(i)+ " messRe�u"+ messRecu.iemeElement(i));
			if (messEmis.iemeElement(i) != messRecu.iemeElement(i)) {
				nbErr++;
			}
		}
		errBinaire = (float) (nbErr / messEmis.nbElements());
		return errBinaire;
	}
}
