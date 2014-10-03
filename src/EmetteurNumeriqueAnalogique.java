import java.lang.Float;

public class EmetteurNumeriqueAnalogique extends
Transmetteur<Boolean, Float> implements Convertisseur {

	private Float min, max;
	private int nbEchantillon;
	private TypeCodage type;

	public EmetteurNumeriqueAnalogique(float min, float max, int nbEchantillon,
			TypeCodage type) {
		super();
		informationEmise = new Information<Float>();
		this.min = new Float(min);
		this.max = new Float(max);
		this.nbEchantillon = nbEchantillon;
		this.type = type;
	}

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

	public void codageRZ() {

		for(int i = 0; i < informationRecue.nbElements(); i++){

			for(int j = 0; j < nbEchantillon; j++){
				
				if(informationRecue.iemeElement(j)){
					if (j < nbEchantillon / 2) informationEmise.add(max);
					else if (j > nbEchantillon / 2) informationEmise.add(0f);
				}
				else{
					if (j < nbEchantillon / 2) informationEmise.add(min);
					else if (j > nbEchantillon / 2) informationEmise.add(0f);
				}
			}
		}
	}

	public void codageNRZ() {

		for(int i = 0; i < informationRecue.nbElements(); i++){
			System.out.println("info reçue : "+ informationRecue.nbElements());
			for(int j = 0; j < nbEchantillon; j++){
				System.out.println("i : "+i+"  j : "+j);
				if(informationRecue.iemeElement(i)) 
					informationEmise.add(max);
				else informationEmise.add(min);
			}
		}
	}

	public void codageNRZT() {
			// TODO completer
	}

	@Override
	public void emettre() throws InformationNonConforme {
		
		conversion();
		super.emettre();
	}

	@Override
	public void recevoir(Information<Boolean> information)
			throws InformationNonConforme {

		informationRecue = information;
	}

}
