


/**
 * Interface d'un composant ayant le comportement d'une source d'informations
 * dont les �l�ments sont de type T
 * 
 * @author prou
 */
public interface SourceInterface<T> {

	/**
	 * pour obtenir la derni�re information �mise par une source.
	 * 
	 * @return une information
	 */
	public Information<T> getInformationEmise();

	/**
	 * pour connecter une destination � la source
	 * 
	 * @param destination
	 *            la destination � connecter
	 */
	public void connecter(DestinationInterface<T> destination);

	/**
	 * pour �mettre l'information contenue dans une source
	 */
	public void emettre() throws InformationNonConforme;

}