package sonde;
import element_transmission.Information;
import ihm.VueCourbe;




/**
 * Classe r�alisant l'affichage d'information compos�e d'�l�ments bool�ens avec
 * d�filement...
 * 
 * @author prou
 */
public class SondeLogiqueInfinie extends Sonde<Boolean> {

	VueCourbe vue;
	boolean[] fenetre;

	/**
	 * pour construire une sonde logique infinie
	 * 
	 * @param nom
	 *            le nom de la fen�tre d'affichage
	 * @param tailleFenetre
	 *            le nombre d'�l�ments d'information affich�e dans la fen�tre
	 */
	public SondeLogiqueInfinie(String nom, int tailleFenetre) {
		super(nom);
		vue = null;
		fenetre = new boolean[tailleFenetre];
		for (int i = 0; i < tailleFenetre; i++) {
			fenetre[i] = false;
		}

	}

	public void recevoir(Information<Boolean> information) {
		informationRecue = information;
		if (information.iemeElement(0) instanceof Boolean) {
			int nbElements = information.nbElements();
			boolean[] table = new boolean[nbElements];
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