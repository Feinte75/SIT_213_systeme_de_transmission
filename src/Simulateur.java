/**
 * Cette classe permettra de traiter essentiellement la gestion des paramètres
 * décris dans le fichier "commande_unique" sur moodle. Il appelera également
 * les différentes applications (suivant les différents séances/livrables).
 * 
 * @author rodolphemartinez
 *
 */
public class Simulateur {

	public static void main(String[] args) {

		// valeurs arguments du simulateur par défaut
		// -etape
		String etape = "1";
		// -mess
		String message = "100";
		// -s
		boolean sonde = false;
		// -form
		TypeCodage forme = TypeCodage.NRZ;
		// -nbEch
		int nbEch = 10;
		// -ampl
		float amplMin = 0.0f;
		float amplMax = 1.0f;
		// -snr
		float rsb = 0.7f;
		// -ti
		int iEmeTrajet = 1;
		int decaTempo = 0;
		float amplRel = 0.0f;
		// -transducteur
		boolean transducteur = false;
		
		/********************************************/
		// traitement des arguments
		int i = 0;

		while (i < args.length) { // args[i].length() -> taille de l'argument, args.length -> taille du tableau d'argument
			switch (args[i]) {
			case "-etape":
				if(args[++i].matches("[123]||[45][ab]")) etape = args[i];
				break;
			case "-mess":
				message = args[++i];
				break;
			case "-s":
				sonde = true;
				break;
			case "-form":
				switch(args[++i]){
				case "RZ": 
					forme = TypeCodage.NRZ;
					break;
				case "NRZ":
					forme = TypeCodage.NRZ;
					break;
				case "NRZT":
					forme = TypeCodage.NRZT;
					break;
				}
				break;
			case "-nbech":
				nbEch = Integer.parseInt(args[++i]);
				break;
			case "-ampl":
				amplMin = (float) (Float.parseFloat(args[++i]));
				amplMax = (float) (Float.parseFloat(args[++i]));
				break;
			case "-snr":
				rsb = (float) (Float.parseFloat(args[++i]));
				break;
			case "-ti":
				iEmeTrajet = Integer.parseInt(args[i]);
				decaTempo = Integer.parseInt(args[i]);
				amplRel = (float) (Float.parseFloat(args[i]));
				break;
			case "-transducteur":
				transducteur = true;
				break;
			}
			i++;
		}
		
		System.out.println("Param�tres : -etape =" + etape +" , -mess =" + message + " , -s =" + sonde + " , -form =" + forme + " , -nbEch =" + nbEch +
				" , -ampl =" + amplMin + " et " + amplMax);
		
		if(etape.equals("1")){
			ApplicationTransmissionLogiqueParfaite app1 = new ApplicationTransmissionLogiqueParfaite();
			app1.execution(message, sonde);
		}
		else if (etape.equals("2")){
			ApplicationTransmissionAnalogiqueParfaite app2 = new ApplicationTransmissionAnalogiqueParfaite();
			app2.execution(message, amplMin, amplMax, nbEch, forme, sonde);
		}
		else System.out.println("Etape non cod�e pour le moment");
		
		
		
	}
}