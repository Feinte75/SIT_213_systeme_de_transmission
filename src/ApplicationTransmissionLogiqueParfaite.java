/**
 * 
 * Livrable n�1
 *
 */
public class ApplicationTransmissionLogiqueParfaite extends Application{
	/**
	 * Verifier si la sonde est demandee ou pas par l'utilisateur
	 * @param arg
	 * 			valeur de l'argument saisi par l'utilisateur
	 * @return true si la valeur de l'argument est 1
	 */
	private static boolean isSondeDemandee(String arg) {
		return arg.equals("1");
	}


	public void execution(Source<Boolean> src, boolean sonde){
		
		//Instanciation d'un objet TransmetteurParfait et DestinationFinale
		TransmetteurParfait<Boolean, Boolean> trParfait = new TransmetteurParfait<Boolean, Boolean>();
		DestinationFinale dstFinale = new DestinationFinale();

		//Instanciation des deux sondes logiques
		SondeLogique sondeSource = new SondeLogique("Signal emis par la source");
		SondeLogique sondeTransmetteur = new SondeLogique("Signal emis par le transmetteur");

		//Verifier que l'utilisateur a bien saisi deux arguments et qu'il a choisi d'utiliser les sondes
		if (sonde) {
			//Connecter le transmetteur parfait a la source
			src.connecter(trParfait);
			//Connecter la destination finale au transmetteur parfait
			trParfait.connecter(dstFinale);
			//Connecter les sondes a chaque composant
			src.connecter(sondeSource);
			trParfait.connecter(sondeTransmetteur);
		}
		//Si ce n'est pas le cas
		else {
			//Connecter seulement les elements de la chaine de transmission sans sondes
			src.connecter(trParfait);
			trParfait.connecter(dstFinale);
		}

		try {
			//Emission de la source
			src.emettre();
			//Emission du transmetteur parfait
			trParfait.emettre();
		} catch (InformationNonConforme e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("Info generee par source          :"
				+ src.informationGeneree);
		System.out.println("Info emise par source            :"
				+ src.getInformationEmise());
		System.out.println("Info recue par transmetteur      :"
				+ trParfait.getInformationRecue());
		System.out.println("Info emise par transmetteur      :"
				+ trParfait.getInformationEmise());
		System.out.println("Info recue par destination finale:"
				+ dstFinale.getInformationRecue());

		//appel de la fonction de calcul du taux d'erreur binaire
		float tauxErrBin = 0;
		tauxErrBin = tauxErreurBinaire(src, dstFinale);
		System.out.println("\nLe taux d'erreur binaire est égal à "+tauxErrBin+"%");
	}
}
