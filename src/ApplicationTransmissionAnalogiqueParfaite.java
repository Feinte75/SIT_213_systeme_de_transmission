
public class ApplicationTransmissionAnalogiqueParfaite {

	/**
	 * Verifier si la sonde est demandee ou pas par l'utilisateur
	 * @param arg
	 * 			valeur de l'argument saisi par l'utilisateur
	 * @return true si la valeur de l'argument est 1
	 */
	private static boolean isSondeDemandee(String arg) {
		return arg.equals("1");
	}
	/**
	 * Verifier si l'utilisateur a choisi une source fixe
	 * @param arg
	 * 			valeur de l'argument saisi par l'utilisateur
	 * @return true si la taille de l'argument est supperieure ou egale à 7
	 */
	private static boolean isSourceFixe(String arg) {
		return arg.length() >= 7;
	}
	/**
	 * Verifier si l'utilisateur a choisi une source aleatoire
	 * @param arg
	 * 			valeur de l'argument saisi par l'utilisateur
	 * @return true si la taille de l'argument est comprise entre 0 est 6 inclus
	 */
	private static boolean isSourceAleatoire(String arg) {
		return (0 < arg.length() && arg.length() <= 6);
	}

	public void execution(String mess, float min, float max, int nbEchantillon, TypeCodage codage, boolean sonde){
		
		// TODO : Prise en compte de la variable sonde
		
		Source<Boolean> src = null;

		//Verifier si l'utilisateur a choisi une source fixe
		if (isSourceFixe(mess)) {
			//Generer la suite de bits correspondante a l'argument saisi par l'utilisateur
			src = new SourceFixe(mess);
		} 
		//Verifier si l'utilisateur a choisi une source aleatoire
		else if (isSourceAleatoire(mess)) {
			//Generer une suite de bits aleatoire dont la taille est la valeur decimale l'argument saisi par l'utilisateur
			src = new SourceAleatoire(mess);
		}

		//Instanciation d'un objet TransmetteurParfait et DestinationFinale
		EmetteurNumeriqueAnalogique ena = new EmetteurNumeriqueAnalogique(min, max, nbEchantillon, codage);
		TransmetteurParfait<Float, Float> trParfait = new TransmetteurParfait<Float, Float>();
		RecepteurAnalogiqueNumerique ran = new RecepteurAnalogiqueNumerique(min, max, nbEchantillon, codage);
		DestinationFinale dstFinale = new DestinationFinale();

		//Instanciation des deux sondes logiques
		SondeLogique sondeSource = new SondeLogique("Signal emis par la source");
		SondeAnalogique sondeTransmetteur = new SondeAnalogique("Signal emis par le transmetteur");
		SondeAnalogique sondeEmetteur = new SondeAnalogique("Signal emis par l'emetteur");
		SondeLogique sondeRecepteur = new SondeLogique("Signal emis par le recepteur");

		//Verifier que l'utilisateur a bien saisi deux arguments et qu'il a choisi d'utiliser les sondes
		if (true) {
			//Connecter le transmetteur parfait a la source
			src.connecter(ena);
			//Connecter la destination finale au transmetteur parfait
			ena.connecter(trParfait);
			trParfait.connecter(ran);
			ran.connecter(dstFinale);
			//Connecter les sondes a chaque composant
			src.connecter(sondeSource);
			ena.connecter(sondeEmetteur);
			
			trParfait.connecter(sondeTransmetteur);
			ran.connecter(sondeRecepteur);
		}
		
		try {
			//Emission de la source
			src.emettre();
			ena.emettre();
			//Emission du transmetteur parfait
			trParfait.emettre();
			ran.emettre();
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
		Information messEmis = source.informationEmise;
		Information messRecu = destination.informationRecue;
		float errBinaire = 0;
		int nbErr = 0;
		for (int i = 0; i < messEmis.nbElements(); i++) {
			if (messEmis.iemeElement(i) != messRecu.iemeElement(i)) {
				nbErr++;
			}
		}
		errBinaire = (float) (1.0 * nbErr / messEmis.nbElements());
		return errBinaire;
	}
}
