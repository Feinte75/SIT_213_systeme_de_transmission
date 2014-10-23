package sonde;
import ihm.VueCourbe;
import element_transmission.Information;




/**
 * Classe r�alisant l'affichage d'information compos�e d'�l�ments bool�ens
 * 
 * @author prou
 */
public class SondeLogique extends Sonde<Boolean> {

	private String xName, yName;
	
	public SondeLogique(String nom, String xName, String yName) {
		super(nom);
		this.xName = xName;
		this.yName = yName;
	}

	public void recevoir(Information<Boolean> information) {
		informationRecue = information;
		if (information.iemeElement(0) instanceof Boolean) {
			int nbElements = information.nbElements();
			boolean[] table = new boolean[nbElements];
			for (int i = 0; i < nbElements; i++) {
				table[i] = information.iemeElement(i);
			}
			new VueCourbe(table, nom, xName, yName);
		} else
			System.out.println(nom + " : " + information);
	}

}