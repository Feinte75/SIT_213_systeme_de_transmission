
public class ApplicationTransmissionLogiqueParfaite {
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

	public static void main(String[] args) throws InformationNonConforme {
		//test github
		int nbArgs = args.length;
		Source<Boolean> src = null;

		//Verifier que l'utilisateur a bien saisi des arguments
		if(nbArgs != 0){
			//Verifier que les arguments saisis par l'utilisateur sont conformes
			if(!(args[0].matches("[1|0]+") || args[0].matches("[0-9]{1,6}")) )
				//Lever l'exception InformationNonConforme si l'argument n'est pas une suite de 0 et de 1
				// ou si l'argument n'est pas une suite de chiffres compris entre 0 et 9 à hauteur de 6 chiffes maximum
				throw new InformationNonConforme("Argument invalide");
		}

		//Comportement par défaut : verifier si l'utilisateur n'a pas saisi d'argument ou s'il a saisi 0 comme premier argument
		if (nbArgs == 0 || args[0].equals("0")) {
			//Generation d'une suite de bits aleatoire de longueure 100
			src = new SourceAleatoire("100");
		} 
		//Verifier si l'utilisateur a choisi une source fixe
		else if (isSourceFixe(args[0])) {
			//Generer la suite de bits correspondante a l'argument saisi par l'utilisateur
			src = new SourceFixe(args[0]);
		} 
		//Verifier si l'utilisateur a choisi une source aleatoire
		else if (isSourceAleatoire(args[0])) {
			//Generer une suite de bits aleatoire dont la taille est la valeur decimale l'argument saisi par l'utilisateur
			src = new SourceAleatoire(args[0]);
		}

		//Instanciation d'un objet TransmetteurParfait et DestinationFinale
		TransmetteurParfait trParfait = new TransmetteurParfait();
		DestinationFinale dstFinale = new DestinationFinale();

		//Instanciation des deux sondes logiques
		SondeLogique sondeSource = new SondeLogique("Signal emis par la source");
		SondeLogique sondeTransmetteur = new SondeLogique("Signal emis par le transmetteur");

		//Verifier que l'utilisateur a bien saisi deux arguments et qu'il a choisi d'utiliser les sondes
		if (nbArgs >= 2 && isSondeDemandee(args[1])) {
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

	public void execution(String mess, boolean sonde){
		
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
		TransmetteurParfait trParfait = new TransmetteurParfait();
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
