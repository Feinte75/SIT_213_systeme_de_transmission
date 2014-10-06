package element_transmission;
import ihm.TypeCodage;
import exception.InformationNonConforme;


public class RecepteurAnalogiqueNumerique extends Transmetteur<Float, Boolean> implements Convertisseur{

	private Float min, max;
	private int nbEchantillon;
	private TypeCodage type;

	public RecepteurAnalogiqueNumerique(float min, float max, int nbEchantillon, TypeCodage type) {
		super();
		informationEmise = new Information<Boolean>();
		this.min = new Float(min);
		this.max = new Float(max);
		this.nbEchantillon = nbEchantillon;
		this.type = type;
	}

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

	private void decodageNRZT() {

		for(int i=nbEchantillon/2; i<informationRecue.nbElements();i+= nbEchantillon){

				if(informationRecue.iemeElement(i/2).floatValue()==max){
					informationEmise.add(new Boolean(true));
				}
				else {
					informationEmise.add(new Boolean(false));
				}
		}

	}

	private void decodageNRZ() {

		float somme = 0;
		for(int i = 0; i < informationRecue.nbElements(); i+= nbEchantillon){

			for(int j = i; j < i + nbEchantillon; j++){

				somme += informationRecue.iemeElement(j).floatValue();
			}
			somme /= nbEchantillon;
			if(somme >= (min + max)/2 ) informationEmise.add(new Boolean(true));
			else informationEmise.add(new Boolean(false));
			somme = 0;
		}

	}

	private void decodageRZ() {

		for(int i=nbEchantillon/4; i<informationRecue.nbElements();i+= nbEchantillon){

			if(informationRecue.iemeElement(i).floatValue()==max){
				informationEmise.add(new Boolean(true));
			}
			else {
				informationEmise.add(new Boolean(false));
			}
	}


	}

	@Override
	public void emettre() throws InformationNonConforme {

		conversion();
		super.emettre();
	}

	@Override
	public void recevoir(Information<Float> information)
			throws InformationNonConforme {
		informationRecue = information;

	}


}
