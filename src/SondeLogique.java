


/**
 * Classe r�alisant l'affichage d'information compos�e d'�l�ments bool�ens
 * 
 * @author prou
 */
public class SondeLogique extends Sonde<Boolean> {

	public SondeLogique(String nom) {
		super(nom);
	}

	public void recevoir(Information<Boolean> information) {
		informationRecue = information;
		if (information.iemeElement(0) instanceof Boolean) {
			int nbElements = information.nbElements();
			boolean[] table = new boolean[nbElements];
			for (int i = 0; i < nbElements; i++) {
				table[i] = information.iemeElement(i);
			}
			new VueCourbe(table, nom);
		} else
			System.out.println(nom + " : " + information);
	}

}