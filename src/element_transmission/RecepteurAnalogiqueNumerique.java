package element_transmission;

import exception.InformationNonConforme;

/**
 * 
 * Classe représentant un élément de la chaine de transmission générale. Le
 * récepteur se situe entre le transmetteur et la destination. Ce recepteur
 * permet de convertire le signal analogique reçu depuis le transmetteur en
 * signal numérique pour le transmettre à la destination.
 *
 */
public class RecepteurAnalogiqueNumerique extends Transmetteur<Float, Boolean>
		implements Convertisseur {

	private Float min, max;
	private int nbEchantillon;
	private int nbBitsEnvoyes;
	private TypeCodage type;

	/**
	 * Constructeur de la classe RecepteurAnalogiqueNumerique
	 * 
	 * @param min
	 *            Minimum du signal
	 * @param max
	 *            Maximum du signal
	 * @param nbEchantillon
	 *            Nombre d'échantillon
	 * @param type
	 *            Type de codage
	 */
	public RecepteurAnalogiqueNumerique(float min, float max,
			int nbEchantillon, int nbBitsEnvoyes, TypeCodage type) {
		super();
		informationEmise = new Information<Boolean>();
		this.min = new Float(min);
		this.max = new Float(max);
		this.nbEchantillon = nbEchantillon;
		this.nbBitsEnvoyes = nbBitsEnvoyes;
		this.type = type;
	}

	/**
	 * Méthode de conversion d'un String reçu (pour le type de codage) en une
	 * méthode associée : decodageRZ(), decodageNRZ() ou decodageNRZT()
	 */
	@Override
	public void conversion() {

		switch (type) {

		case RZ:
			decodageRZ();
			break;
		case NRZ:
			decodageNRZ();
			break;
		case NRZT:
			decodageNRZT();
			break;
		}
	}

	/**
	 * Decodage Non Retour à Zéro Triangulaire. Instant de décision tout les
	 * nbEchantillon/2 (milieu du symbole)
	 */
	private void decodageNRZT() {

		for (int i = nbEchantillon / 2; i < nbBitsEnvoyes * nbEchantillon; i += nbEchantillon) {

			if (informationRecue.iemeElement(i).floatValue() >= (max + min) / 2) {
				informationEmise.add(new Boolean(true));
			} else {
				informationEmise.add(new Boolean(false));
			}
		}
	}

	/**
	 * Decodage Non Retour à Zéro. Moyenne sur l'ensemble des valeurs des
	 * echantillons puis determination de l'etat du bit par un seuillage si la
	 * moyenne est > à (min + max) / 2 on detecte le bit comme etant true sinon
	 * false
	 */
	private void decodageNRZ() {

		float somme = 0;
		for (int i = 0; i < nbBitsEnvoyes * nbEchantillon; i += nbEchantillon) {

			for (int j = i; j < i + nbEchantillon; j++) {

				somme += informationRecue.iemeElement(j).floatValue();
			}
			somme /= nbEchantillon;
			if (somme >= (min + max) / 2)
				informationEmise.add(new Boolean(true));
			else
				informationEmise.add(new Boolean(false));
			somme = 0;
		}
	}

	/**
	 * Decodage Retour à Zéro. Instant de décision tout les nbEchantillon/4
	 * (quart du symbole)
	 */
	private void decodageRZ() {

		for (int i = nbEchantillon / 4; i <  nbBitsEnvoyes * nbEchantillon; i += nbEchantillon) {

			if (informationRecue.iemeElement(i).floatValue() >= (max + min) / 2) {
				informationEmise.add(new Boolean(true));
			} else {
				informationEmise.add(new Boolean(false));
			}
		}
	}

	/**
	 * Méthode pour emettre le signal analogique converti en numérique à la
	 * destination (dernier élément de la chaine).
	 */
	@Override
	public void emettre() throws InformationNonConforme {

		conversion();
		super.emettre();
	}

	/**
	 * Méthode pour recevoir les informations analogique du Transmetteur
	 */
	@Override
	public void recevoir(Information<Float> information)
			throws InformationNonConforme {
		informationRecue = information;
	}
}
