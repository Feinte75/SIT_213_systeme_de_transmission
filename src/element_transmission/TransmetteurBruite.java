package element_transmission;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import exception.InformationNonConforme;

public class TransmetteurBruite extends Transmetteur<Float, Float> {

	int nbEchantillon = 0;
	float snr = 0f;
	float puissanceSignal;
	LinkedList<Float> valeursBruit;
	static int histo[] = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };

	public TransmetteurBruite(int nbEchantillon, float snr) {
		informationEmise = new Information<Float>();
		this.nbEchantillon = nbEchantillon;
		this.snr = snr;
		valeursBruit = new LinkedList<Float>();
	}

	public float calculBruit() {

		float puissanceBruit, valeurBruit;
		Random a1 = new Random();
		Random a2 = new Random();

		puissanceBruit = (float) (puissanceSignal / (Math.pow(10, snr / 10)));

		valeurBruit = (float) (Math.sqrt(puissanceBruit
				* (-2 * Math.log(1 - a1.nextFloat()))) * Math.cos(2 * Math.PI
				* a2.nextFloat()));
		return valeurBruit;

	}

	public float calculPuissanceSignal() {
		float somme = 0f;

		for (int i = 0; i < informationRecue.nbElements(); i++) {
			somme += Math.pow(informationRecue.iemeElement(i), 2);
		}
		float moy = (1 / (float) nbEchantillon) * somme;
		return moy;
	}

	public void ajoutBruit() {
		float vBruit = 0.0f;
		for (int i = 0; i < informationRecue.nbElements(); i++) {
			vBruit = calculBruit();
			informationEmise.add(informationRecue.iemeElement(i) + vBruit);
			valeursBruit.add(vBruit);
		}
	}

	@Override
	public void emettre() throws InformationNonConforme {
		ajoutBruit();
		histogrammeBruit();
		super.emettre();
	}

	@Override
	public void recevoir(Information<Float> information)
			throws InformationNonConforme {
		informationRecue = information;
		puissanceSignal = calculPuissanceSignal();
	}

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
	
	public static int[] getTableauHistogramme(){
		return histo;
	}
}