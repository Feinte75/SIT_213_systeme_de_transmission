import java.util.LinkedList;

/**
 * 
 * @author prou
 */
public class Information<T> {

	private LinkedList<T> content;

	/**
	 * pour construire une information vide
	 */
	public Information() {
		this.content = new LinkedList<T>();
	}

	/**
	 * pour construire � partir d'un tableau de T une information
	 * 
	 * @param content
	 *            le tableau d'�l�ments pour initialiser l'information
	 *            construite
	 */
	public Information(T[] content) {
		this.content = new LinkedList<T>();
		for (int i = 0; i < content.length; i++) {
			this.content.add(content[i]);
		}
	}

	/**
	 * pour conna�tre le nombre d'�l�ments d'une information
	 * 
	 * @return le nombre d'�l�ments de l'information
	 */
	public int nbElements() {
		return this.content.size();
	}

	/**
	 * pour renvoyer un �l�ment d'une information
	 * 
	 * @return le ieme �l�ment de l'information
	 */
	public T iemeElement(int i) {
		return this.content.get(i);
	}

	/**
	 * pour ajouter un �l�ment � la fin de l'information
	 * 
	 * @param valeur
	 *            l'�l�ment � rajouter
	 */
	public void add(T valeur) {
		this.content.add(valeur);
	}

	/**
	 * pour comparer l'information courante avec une autre information
	 * 
	 * @param o
	 *            l'information avec laquelle se comparer
	 * @return "true" si les 2 informations contiennent les m�mes �l�ments aux
	 *         m�mes places; "false" dans les autres cas
	 */
	public boolean equals(Object o) {
		if (!(o instanceof Information))
			return false;
		Information information = (Information) o;
		if (this.nbElements() != information.nbElements())
			return false;
		for (int i = 0; i < this.nbElements(); i++) {
			if (!this.iemeElement(i).equals(information.iemeElement(i)))
				return false;
		}
		return true;
	}

	/**
	 * pour afficher une information
	 */
	public String toString() {
		String s = "";
		for (int i = 0; i < this.nbElements(); i++) {
			s += " " + this.iemeElement(i);
		}
		return s;
	}

}