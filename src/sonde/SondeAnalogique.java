package sonde;
import ihm.VueCourbe;
import element_transmission.Information;




/**
 * Classe r�alisant l'affichage d'information compos�e d'�l�ments r�els (float)
 * 
 * @author prou
 */
public class SondeAnalogique extends Sonde<Float> {

	private String xName, yName;
	
	public SondeAnalogique(String nom, String xName, String yName) {
		super(nom);
		this.xName = xName;
		this.yName = yName;
	}

	public void recevoir(Information<Float> information) {
		informationRecue = information;
		if (information.iemeElement(0) instanceof Float) {
			int nbElements = information.nbElements();
			float[] table = new float[nbElements];
			for (int i = 0; i < nbElements; i++) {
				table[i] = information.iemeElement(i);
			}
			new VueCourbe(table, nom, xName, yName);
		} else
			System.out.println(nom + " : " + information);
	}

}