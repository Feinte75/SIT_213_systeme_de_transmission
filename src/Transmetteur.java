


import java.util.Iterator;
import java.util.LinkedList;


/**
 * Classe Abstraite d'un composant transmetteur d'informations dont les �l�ments
 * sont de type R en entr�e et de type E en sortie; l'entr�e du transmetteur
 * impl�mente l'interface DestinationInterface, la sortie du transmetteur
 * impl�mente l'interface SourceInterface
 * 
 * @author prou
 */
public abstract class Transmetteur<R, E> implements DestinationInterface<R>,
SourceInterface<E> {

	/**
	 * la liste des composants destination connect�s en sortie du transmetteur
	 */
	protected LinkedList<DestinationInterface<E>> destinationsConnectees;

	/**
	 * l'information re�ue en entr�e du transmetteur
	 */
	protected Information<R> informationRecue;

	/**
	 * l'information �mise en sortie du transmetteur
	 */
	protected Information<E> informationEmise;

	/**
	 * un constructeur factorisant les initialisations communes aux r�alisations
	 * de la classe abstraite Transmetteur
	 */
	public Transmetteur() {
		destinationsConnectees = new LinkedList<DestinationInterface<E>>();
		informationRecue = null;
		informationEmise = null;
	}

	/**
	 * pour obtenir la derni�re information re�ue en entr�e du transmetteur
	 * 
	 * @return une information
	 */
	public Information<R> getInformationRecue() {
		return this.informationRecue;
	}

	/**
	 * pour obtenir la derni�re information �mise en sortie du transmetteur
	 * 
	 * @return une information
	 */
	public Information<E> getInformationEmise() {
		return this.informationEmise;
	}

	/**
	 * pour connecter une destination � la sortie du transmetteur
	 * 
	 * @param destination
	 *            la destination � connecter
	 */
	public void connecter(DestinationInterface<E> destination) {
		destinationsConnectees.add(destination);
	}

	/**
	 * pour d�connecter une destination de la la sortie du transmetteur
	 * 
	 * @param destination
	 *            la destination � connecter
	 */
	public void deconnecter(DestinationInterface<E> destination) {
		destinationsConnectees.remove(destination);
	}

	/**
	 * pour recevoir une information de la source qui est connect�e � l'entr�e
	 * du transmetteur; Cette m�thode devra, en fin d'ex�cution, appeller la
	 * m�thode �mettre.
	 * 
	 * @param information
	 *            l'information � recevoir
	 */
	public abstract void recevoir(Information<R> information)
			throws InformationNonConforme;

	/**
	 * pour �mettre l'information contenue dans l'entr�e du transmetteur
	 */
	public void emettre() throws InformationNonConforme{
		
		//Parcourir la liste des destinations du transmetteur
		Iterator<DestinationInterface<E>> itr = destinationsConnectees.iterator();
		while (itr.hasNext()) {
			//Appeler la methode recevoir pour chaque destination
			DestinationInterface<E> di = itr.next();
			di.recevoir(informationEmise);
		}

	}
}