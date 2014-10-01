


/**
 * Classe Abstraite d'un composant destination r�alisant un affichage
 * 
 * @author prou
 */
public abstract class Sonde<T> extends Destination<T> {

	/**
	 * nom de la fen�tre d'affichage
	 */
	protected String nom;

	/**
	 * 
	 * @param nom
	 *            le nom de la fen�tre d'affichage
	 */
	public Sonde(String nom) {
		this.nom = nom;
	}

	/**
	 * pour recevoir et afficher l'information transmise par la source qui nous
	 * est connect�e
	 * 
	 * @param information
	 *            l'information � recevoir
	 */
	public abstract void recevoir(Information<T> information);
}