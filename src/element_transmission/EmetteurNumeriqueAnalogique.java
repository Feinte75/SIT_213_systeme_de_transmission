package element_transmission;

import exception.InformationNonConforme;

/**
 * 
 * Cette classe permet de simuler le composant "Emetteur Numerique Analogique"
 * 
 */
public class EmetteurNumeriqueAnalogique extends Transmetteur<Boolean, Float>
		implements Convertisseur {

	private Float min, max;
	private int nbEchantillon;
	private TypeCodage type;

	/**
	 * Constructeur de la classe EmetteurNumeriqueAnalogique
	 * 
	 * @param min
	 *            Valeur min du signal
	 * @param max
	 *            Valeur max du signal
	 * @param nbEchantillon
	 *            Nombre d'�chantillons par symbole
	 * @param type
	 *            Type du codage utilis� lors de la conversion
	 */
	public EmetteurNumeriqueAnalogique(float min, float max, int nbEchantillon,
			TypeCodage type) {
		super();
		informationEmise = new Information<Float>();
		this.min = new Float(min);
		this.max = new Float(max);
		this.nbEchantillon = nbEchantillon;
		this.type = type;
	}

	/**
	 * M�thode d'appel des types de conversion num�rique analogique
	 */
	@Override
	public void conversion() {

		switch (type) {

		case RZ:
			codageRZ();
			break;
		case NRZ:
			codageNRZ();
			break;
		case NRZT:
			codageNRZT();
			break;
		}
	}

	/**
	 * Codage retour � zero. Signal � min ou max pendant 1/2 symbole puis 0
	 * ensuite
	 */
	public void codageRZ() {

		for (int i = 0; i < informationRecue.nbElements(); i++) {

			for (int j = 0; j < nbEchantillon; j++) {

				if (informationRecue.iemeElement(i)) {
					if (j < nbEchantillon / 2)
						informationEmise.add(max);
					else if (j >= nbEchantillon / 2)
						informationEmise.add(0f);
				} else {
					if (j < nbEchantillon / 2)
						informationEmise.add(min);
					else if (j >= nbEchantillon / 2)
						informationEmise.add(0f);
				}
			}
		}
	}

	/**
	 * Codage Non Retour � Z�ro. Signal � max pour un "1" et � min pour un
	 * "0"
	 */
	public void codageNRZ() {

		for (int i = 0; i < informationRecue.nbElements(); i++) {

			for (int j = 0; j < nbEchantillon; j++) {

				if (informationRecue.iemeElement(i))
					informationEmise.add(max);
				else
					informationEmise.add(min);
			}
		}
	}

	/**
	 * Codage Non Retour � Z�ro Triangulaire. Pour un "1", pente montante de
	 * min à max durant 1/3 de la période, plateau à max pendant 1 autre tier
	 * puis pente descendante jusqu'à min pendant le dernier tier de la
	 * période
	 */
	public void codageNRZT() {

		float penteMontante = 0.0f;
		float penteDescendante = 0.0f;

		for (int i = 0; i < informationRecue.nbElements(); i++) {

			if (informationRecue.iemeElement(i)) { // cas d'un "1"
				// Pente montante
				for (int j = 0; j < nbEchantillon / 3; j++) {
					penteMontante = ((max - min) * 3 / nbEchantillon) * j + min;
					informationEmise.add(penteMontante);
				}

				// Plateau
				for (int j = nbEchantillon / 3; j < 2 * nbEchantillon / 3; j++) {
					informationEmise.add(max);
				}

				// Pente descendante
				for (int j = 2 * nbEchantillon / 3; j < nbEchantillon; j++) {
					penteDescendante = ((min - max) * 3 / nbEchantillon)
							* (j - 2 * nbEchantillon / 3) + max;
					informationEmise.add(penteDescendante);
				}
			} else
				// Cas d'un "0"
				for (int j = 0; j < nbEchantillon; j++) {
					informationEmise.add(min);
				}
		}
	}

	/**
	 * M�thode d'emission de l'information
	 */
	@Override
	public void emettre() throws InformationNonConforme {

		conversion();
		super.emettre();
	}

	/**
	 * M�thode de r�ception de l'information
	 */
	@Override
	public void recevoir(Information<Boolean> information)
			throws InformationNonConforme {

		informationRecue = information;
	}

}
