package element_transmission;

import java.util.Random;

import exception.InformationNonConforme;

public class TransmetteurBruite extends Transmetteur<Float, Float> {

	int nbEchantillon = 0;
	float snr = 0f;
	float puissanceSignal;

	public TransmetteurBruite(int nbEchantillon, float snr) {
		informationEmise = new Information<Float>();
		this.nbEchantillon = nbEchantillon;
		this.snr = snr;
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
		for (int i = 0; i < informationRecue.nbElements(); i++) {
			informationEmise.add(informationRecue.iemeElement(i)
					+ calculBruit());
		}
	}

	@Override
	public void emettre() throws InformationNonConforme {
		ajoutBruit();
		super.emettre();
	}

	@Override
	public void recevoir(Information<Float> information)
			throws InformationNonConforme {
		informationRecue = information;
		puissanceSignal = calculPuissanceSignal();
	}

}