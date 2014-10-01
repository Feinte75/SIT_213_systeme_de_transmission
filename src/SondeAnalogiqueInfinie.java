


/**
 * Classe r�alisant l'affichage d'information compos�e d'�l�ments r�els (float)
 * avec d�filement
 * 
 * @author prou
 */
public class SondeAnalogiqueInfinie extends Sonde<Float> {

	VueCourbe vue;
	float[] fenetre;

	/**
	 * pour construire une sonde analogique infinie
	 * 
	 * @param nom
	 *            le nom de la fen�tre d'affichage
	 * @param tailleFenetre
	 *            le nombre d'�l�ments d'information affich�e dans la fen�tre
	 */
	public SondeAnalogiqueInfinie(String nom, int tailleFenetre) {
		super(nom);
		vue = null;
		fenetre = new float[tailleFenetre];
		for (int i = 0; i < tailleFenetre; i++) {
			fenetre[i] = 0.0f;
		}
	}

	public void recevoir(Information<Float> information) {
		informationRecue = information;
		if (information.iemeElement(0) instanceof Float) {
			int nbElements = information.nbElements();
			float[] table = new float[nbElements];
			for (int i = 0; i < nbElements; i++) {
				table[i] = information.iemeElement(i);
			}
			for (int i = 0; i < fenetre.length - nbElements; i++) {
				fenetre[i] = fenetre[i + nbElements];
			}
			for (int i = 0; i < nbElements; i++) {
				fenetre[i + fenetre.length - nbElements] = table[i];
			}

			if (vue == null)
				vue = new VueCourbe(fenetre, nom);
			else
				vue.changer(fenetre);
		} else
			System.out.println(nom + " : " + information);
	}

}