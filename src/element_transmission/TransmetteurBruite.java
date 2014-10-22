package element_transmission;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import exception.InformationNonConforme;

/**
 * Classe modélisant un transmetteur ajoutant du bruit au signal reçu par
 * l'émetteur, et envoi au récepteur un signal bruité. Le bruit se défini comme
 * un bruit blanc gaussien (centré)
 *
 */
public class TransmetteurBruite extends Transmetteur<Float, Float> {

	int nbEchantillon = 0;
	float snr = 0f;
	float puissanceSignal;
	LinkedList<Float> valeursBruit;
	static int histo[] = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };

	/**
	 * Constructeur du transmetteur bruité
	 * 
	 * @param nbEchantillon
	 * @param snr
	 */
	public TransmetteurBruite(int nbEchantillon, float snr) {
		informationEmise = new Information<Float>();
		this.nbEchantillon = nbEchantillon;
		this.snr = snr;
		valeursBruit = new LinkedList<Float>();
	}

	/**
	 * Méthode de calcul du bruit : Cette méthode calcul aléatoirement une
	 * valeur de bruit (d'origine blanc et gaussien). L'aléatoire est généré par
	 * deux coeffcients a1 et a2 que l'on retrouve dans la formule de calcul du
	 * bruit.
	 * 
	 * @return
	 */
	public float calculBruit() {

		float puissanceBruit, valeurBruit;
		Random a1 = new Random();
		Random a2 = new Random();

		puissanceBruit = (float) (puissanceSignal / (Math.pow(10, snr / 10)));

		valeurBruit = (float) (Math.sqrt(puissanceBruit)
				* Math.sqrt(-2 * Math.log(1 - a1.nextFloat())) * Math.cos(2
				* Math.PI * a2.nextFloat()));
		return valeurBruit;

	}

	/**
	 * Cette méthode permet de faire le calcul de la puissance du signal. Ceci
	 * pour être ensuite appelée par la méthode calculBruit(). La puissance du
	 * signal est calculée sur tout les échantillons du signal reçu. Evitant
	 * ainsi, lors du calcul du bruit, d'obtenir une abération lors du calcul du
	 * bruit.
	 * 
	 * @return
	 */
	public float calculPuissanceSignal() {
		float somme = 0f;

		for (int i = 0; i < informationRecue.nbElements(); i++) {
			somme += Math.pow(informationRecue.iemeElement(i).floatValue(), 2);
		}
		float moy = (1 / (float) informationRecue.nbElements()) * somme;
		return moy;
	}

	/**
	 * Sur chaque échantillon de l'information reçu par le transmetteur, cette
	 * méthode ajoute aléatoirement une valeur de bruit (d'origine blanc et
	 * gaussien) tirée de la méthode calculBruit().
	 */
	public void ajoutBruit() {
		float vBruit = 0.0f;
		for (int i = 0; i < informationRecue.nbElements(); i++) {
			vBruit = calculBruit();
			informationEmise.add(informationRecue.iemeElement(i).floatValue()
					+ vBruit);
			valeursBruit.add(vBruit);
		}
	}

	/**
	 * Méthode pour emettre l'information bruité au récepteur de la chaine de transmsission
	 */
	@Override
	public void emettre() throws InformationNonConforme {
		ajoutBruit();
		histogrammeBruit();
		super.emettre();
	}

	/**
	 * Méthode pour recevoir l'information analogique de l'emetteur de la chaine de transmission
	 */
	@Override
	public void recevoir(Information<Float> information)
			throws InformationNonConforme {
		informationRecue = information;
		puissanceSignal = calculPuissanceSignal();
	}

	/**
	 * Méthode pour générer un histogramme représentant les valeurs du bruit blanc gaussien.
	 */
	public void histogrammeBruit() {
		Iterator<Float> itr = valeursBruit.iterator();

		while (itr.hasNext()) {
			Float vb = itr.next();
			if (vb.intValue() == 0) {
				histo[5]++;
			} else if (vb.intValue() == 1) {
				histo[6]++;
			} else if (vb.intValue() == 2) {
				histo[7]++;
			} else if (vb.intValue() == 3) {
				histo[8]++;
			} else if (vb.intValue() == 4) {
				histo[9]++;
			} else if (vb.intValue() == 5) {
				histo[10]++;
			} else if (vb.intValue() == -1) {
				histo[4]++;
			} else if (vb.intValue() == -2) {
				histo[3]++;
			} else if (vb.intValue() == -3) {
				histo[2]++;
			} else if (vb.intValue() == -4) {
				histo[1]++;
			} else if (vb.intValue() == -5) {
				histo[0]++;
			}
		}
	}

	/**
	 * Méthode permettant de retourner le tableau de l'histogramme du bruit
	 * @return
	 */
	public static int[] getTableauHistogramme() {
		return histo;
	}
}