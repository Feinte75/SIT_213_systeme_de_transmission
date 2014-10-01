

/**
 * Classe r�alisant l'affichage (textuel) d'information compos�e d'�l�ments de
 * type T
 * 
 * @author prou
 */
public class SondeTextuelle<T> extends Sonde<T> {

	public SondeTextuelle(String nom) {
		super(nom);
	}

	public void recevoir(Information<T> information) {
		informationRecue = information;
		System.out.println(nom + " : " + information);
	}

}