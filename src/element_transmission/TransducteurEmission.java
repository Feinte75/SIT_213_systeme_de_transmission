package element_transmission;

import exception.InformationNonConforme;

/**
 * 
 * Cette classe permet de mettre en place une logique de transduction pour
 * l'émission de symbole : lorsque nous avons une information reçue : 1->101
 * 0->010
 * 
 * A terme, cette méthode nous permettra de faire de la détection d'erreur sur
 * la reception
 * 
 */
public class TransducteurEmission extends Transmetteur<Boolean, Boolean> {

	/**
	 * Méthode pemettant de faire la translation entre l'information reçue et
	 * celle émise : 1->101 0->010
	 */
	private void transduction() {
		Information<Boolean> infoAEmettre = new Information<Boolean>();
		
		for (int i = 0; i < informationRecue.nbElements(); i++) {
			if (informationRecue.iemeElement(i)) {
				infoAEmettre.add(true);
				infoAEmettre.add(false);
				infoAEmettre.add(true);
			} else {
				infoAEmettre.add(false);
				infoAEmettre.add(true);
				infoAEmettre.add(false);
			}
		}
		
		informationEmise = infoAEmettre;

	}

	/**
	 * Méthode de reception des informations émises par la source
	 */
	@Override
	public void recevoir(Information<Boolean> information)
			throws InformationNonConforme {

		informationRecue = information;
	}

	/**
	 * Méthode d'émission des informations aux éléments de la chaine de
	 * transmission suivants
	 */
	@Override
	public void emettre() throws InformationNonConforme {

		transduction();
		super.emettre();
	}

}
