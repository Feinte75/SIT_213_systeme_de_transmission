


/**
 * Classe r�alisant l'affichage d'information compos�e d'�l�ments r�els (float)
 * 
 * @author prou
 */
public class SondeAnalogique extends Sonde<Float> {

	public SondeAnalogique(String nom) {
		super(nom);
	}

	public void recevoir(Information<Float> information) {
		informationRecue = information;
		if (information.iemeElement(0) instanceof Float) {
			int nbElements = information.nbElements();
			float[] table = new float[nbElements];
			for (int i = 0; i < nbElements; i++) {
				table[i] = information.iemeElement(i);
			}
			new VueCourbe(table, nom);
		} else
			System.out.println(nom + " : " + information);
	}

}