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
			//System.out.println("info re�ue : "+ informationRecue.nbElements());
			for(int j = 0; j < nbEchantillon; j++){
				//System.out.println("i : "+i+"  j : "+j);
				if(informationRecue.iemeElement(i)) //equivalent a informationRecue.iemeElement(i) == true =1
					informationEmise.add(max);
				else informationEmise.add(min);
			}
		}
	}

	public void codageNRZT() {

		float penteMontante = 0.0f;
		float penteDescendante = 0.0f;

		for(int i = 0; i < informationRecue.nbElements(); i++){

			if(informationRecue.iemeElement(i)){
				for(int j = 0; j < nbEchantillon/3; j++){
					penteMontante = ((max-min)*3/nbEchantillon)*j;
					informationEmise.add(penteMontante);
				}

				for(int j = nbEchantillon/3; j < 2*nbEchantillon/3; j++){
					informationEmise.add(max);
				}

				for(int j = 2*nbEchantillon/3; j < nbEchantillon; j++){
					penteDescendante = ((min-max) * 3 / nbEchantillon) * (j- 2*nbEchantillon/3) + max;
					informationEmise.add(penteDescendante);
				}
			}
			else 
				for(int j = 0; j< nbEchantillon; j++) {
					informationEmise.add(min);
				}




			//				//si on recoit un 1
			//				if(informationRecue.iemeElement(i)){
			//					
			//					if(j<=nbEchantillon/3){
			//						penteMontante = ((max-min)*3/nbEchantillon)*j;
			//						informationEmise.add(penteMontante);
			//					}
			//					else if(nbEchantillon/3 < j && j < 2*nbEchantillon/3){
			//						informationEmise.add(max);
			//					}
			//					else if(j>=2*nbEchantillon/3 && j<nbEchantillon){
			//						penteDescendante = ((min-max)*3/nbEchantillon)*j;
			//						informationEmise.add(penteDescendante);
			//					}
			//				}
			//				//si on recoit un 0
			//				else informationEmise.add(min);

		}
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
