package element_transmission;

import exception.InformationNonConforme;

public class CanalTrajetsMultiples extends Transmetteur<Float, Float> {

	int nbTrajetIndirect;
	int decaTempo[];
	float amplRel[];

	public CanalTrajetsMultiples(int nbTrajetIndirect, int decaTempo[],
			float amplRel[]) {
		this.nbTrajetIndirect = nbTrajetIndirect;
		this.decaTempo = decaTempo;
		this.amplRel = amplRel;
	}

	@Override
	public void recevoir(Information<Float> information)
			throws InformationNonConforme {

		informationRecue = information;
		generationTrajetsMultiples(informationRecue);
	}

	public void generationTrajetsMultiples(Information<Float> information){

		float signauxRetarde[][] = new float[nbTrajetIndirect][];
		float signalFinal[];
		int decaTempoMax = 0;
		informationEmise = new Information<Float>();

		
		for(int i = 0; i < nbTrajetIndirect; i++){
			if (decaTempo[i] > decaTempoMax)decaTempoMax = decaTempo[i]; 
		}
		
		signalFinal = new float[information.nbElements() + decaTempoMax];
		
		// Generation de nbTrajetIndirect signaux 
		for(int i = 0; i< nbTrajetIndirect; i++){

			// Instanciation du ieme trajet avec une taille de nbElement + decalage
			signauxRetarde[i] = new float[information.nbElements() + decaTempo[i]];

			// Parcours trajet et calcul du signal emi
			for(int j = 0; j < signauxRetarde[i].length; j ++){
			
				if(j < decaTempo[i]) signauxRetarde[i][j] = 0f;
				else signauxRetarde[i][j] = amplRel[i] * information.iemeElement(j - decaTempo[i]).floatValue();
		
				signalFinal[j] += signauxRetarde[i][j]; // Somme des trajets retardés
			}
		}
		
		// Generation information émise en faisant la somme des trajets retardés au signal initial
		for(int i = 0; i < information.nbElements() + decaTempoMax; i++){
			if(i < information.nbElements()) informationEmise.add(signalFinal[i] + information.iemeElement(i)); 
			else informationEmise.add(signalFinal[i]); 
		}

	}

}
