package element_transmission;

import exception.InformationNonConforme;

/**
 * Cette classe permet de mettre en place une logique de transduction pour la
 * réception d'un symbole (composé de trois éléments) : 000->0 001->1 010->0
 * 011->0 100->1 101->1 110->0 111->1
 * 
 * (comme observé sur la table ci dessus, cette classe gère également les
 * erreurs sur un bit)
 * 
 */
public class TransducteurReception extends Transmetteur<Boolean, Boolean> {

	/**
	 * Cette Méthode permet de faire la conversion suivante (entre
	 * InformationReçue et InformationEmise) : 000->0 001->1 010->0 011->0
	 * 100->1 101->1 110->0 111->1
	 */
	private void transduction() {
		int tab = 0;
		for (int i = 0; i < informationRecue.nbElements(); i++) {
			tab = 0;
			if (informationRecue.iemeElement(i++))
				tab += 100;
			if (informationRecue.iemeElement(i++))
				tab += 10;
			if (informationRecue.iemeElement(i))
				tab += 1;

			switch (tab) {
			case 000:
				informationEmise.add(false);
				break;
			case 001:
				informationEmise.add(true);
				break;
			case 010:
				informationEmise.add(false);
				break;
			case 011:
				informationEmise.add(false);
				break;
			case 100:
				informationEmise.add(true);
				break;
			case 101:
				informationEmise.add(true);
				break;
			case 110:
				informationEmise.add(false);
				break;
			case 111:
				informationEmise.add(true);
				break;
			}
		}
	}

	/**
	 * Méthode de reception des informations émises après conversion analogique
	 * numérique
	 */
	@Override
	public void recevoir(Information<Boolean> information)
			throws InformationNonConforme {

		informationRecue = information;
	}

	/**
	 * Méthode d'émission des informations à la destination
	 */
	@Override
	public void emettre() throws InformationNonConforme {

		transduction();
		super.emettre();
	}

}