package ihm;

import java.awt.Color;
import java.awt.Graphics;
import element_transmission.TransmetteurBruite;

import javax.swing.JFrame;

import element_transmission.TransmetteurBruite;

/**
 * <p>
 * Classe reprÃ©sentant un histogramme.
 * </p>
 * 
 * @author SÃ©bastien ESTIENNE.
 */
public class Histogramme extends JFrame {
	/**
	 * <p>
	 * Serial version UID.
	 * </p>
	 */
	private static final long serialVersionUID = 1L;

	/** Valeurs de l'histogramme. */
	private int[] valeurs;

	/** Valeur maximum. */
	private int max;

	/** Couleurs de barres de l'histogramme. */
	private Color[] couleurs;

	/** DÃ©calage en X du graphique par rapport Ã la gauche de la fenÃªtre. */
	private static final int DEC_X = 40;

	/** DÃ©calage en Y du graphique par rapport au bas de la fenÃªtre. */
	private static final int DEC_Y = 40;

	/** DÃ©calage en X du texte au-dessus des barres de l'histogramme. */
	private static final int DEC_TX = DEC_X + 5;

	/** DÃ©calage en Y du texte au-dessus des barres de l'histogramme. */
	private static final int DEC_TY = DEC_Y + 2;

	/** DÃ©calage en hauteur de la ligne permettant de crÃ©er la flÃ¨che. */
	private static final int DEC_FH = 4;

	/** DÃ©calage en longueur de la ligne permettant de crÃ©er la flÃ¨che. */
	private static final int DEC_FL = 8;

	/** Largeur d'une barre de l'histogramme. */
	private static final int LG_B = 30;

	/**
	 * IncrÃ©ment pour calculer la hauteur des barres de l'histogramme en
	 * fonction de la valeur.
	 */
	private static final int INCR = 10;

	/**
	 * <p>
	 * Constructeur pour l'histogramme.
	 * </p>
	 */
	public Histogramme() {
		super("Histogramme");

		// Initialisation des valeurs.
		this.valeurs = new int[11];
		this.valeurs = TransmetteurBruite.getTableauHistogramme();
//		System.out.println(valeurs);
//		this.valeurs[0] = 12;
//		this.valeurs[1] = 10;
//		this.valeurs[2] = 17;
//		this.valeurs[3] = 5;
//		this.valeurs[4] = 13;
//		this.valeurs[5] = 8;
		this.max = 40;

		// Initialisation des couleurs.
		this.couleurs = new Color[11];
		this.couleurs[0] = Color.BLUE;
		this.couleurs[1] = Color.BLUE;
		this.couleurs[2] = Color.BLUE;
		this.couleurs[3] = Color.BLUE;
		this.couleurs[4] = Color.BLUE;
		this.couleurs[5] = Color.BLUE;
		this.couleurs[6] = Color.BLUE;
		this.couleurs[7] = Color.BLUE;
		this.couleurs[8] = Color.BLUE;
		this.couleurs[9] = Color.BLUE;
		this.couleurs[10] = Color.BLUE;

		// PropriÃ©tÃ©s de la fenÃªtre.
		setLocation(50, 50);
		setSize(550, 500);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * <p>
	 * Dessine la fenÃªtre.
	 * </p>
	 * 
	 * @param g
	 *            Le contexte graphique utilisÃ© pour dessiner.
	 */
	@Override
	public void paint(Graphics g) {
		// DÃ©claration des variables utiles pour les calculs de l'histogramme.
		int x, y, x1, y1, x2, y2, largeur, hauteur;

		// Affichage des barres.
		int index=-5;
		for (int i = 0; i < this.valeurs.length; i++) {
			// Barre.
			x = DEC_X + i * (LG_B + 1);
			y = getHeight() - DEC_Y - this.valeurs[i] * INCR;
			largeur = LG_B;
			hauteur = this.valeurs[i] * INCR;
			g.setColor(this.couleurs[i]);
			g.fillRect(x, y, largeur, hauteur);

			g.drawString(Integer.toString(index), x, 475);
			index++;
			
			// Valeur.
			x = DEC_TX + i * (LG_B + 1);
			y = getHeight() - DEC_TY - this.valeurs[i] * INCR;
			g.setColor(Color.BLACK);
			g.drawString("" + this.valeurs[i], x, y);
		}

		// Affichage de l'axe X.
		g.setColor(Color.BLACK);
		x1 = DEC_X;
		y1 = getHeight() - DEC_Y;
		x2 = x1 + this.valeurs.length * LG_B + LG_B;
		y2 = y1;
		g.drawLine(x1, y1, x2, y2);
		g.drawLine(x2, y2, x2 - DEC_FL, y2 - DEC_FH);
		g.drawLine(x2, y2, x2 - DEC_FL, y2 + DEC_FH);
		g.drawString("niveau de bruit", x2, y2);

		// Affichage de l'axe Y.
		x1 = DEC_X;
		y1 = getHeight() - DEC_Y;
		x2 = x1;
		y2 = y1 - this.max * INCR;
		g.drawLine(x1, y1, x2, y2);
		g.drawLine(x2, y2, x2 - DEC_FH, y2 + DEC_FL);
		g.drawLine(x2, y2, x2 + DEC_FH, y2 + DEC_FL);
		g.drawString("occurence", x2, y2);
	}

	/**
	 * <p>
	 * Commence l'exÃ©cution du programme.
	 * </p>
	 * 
	 * @param args
	 *            Les paramÃ¨tres de la ligne de commande.
	 */
	public static void main(String[] args) {
		new Histogramme();
	}
}