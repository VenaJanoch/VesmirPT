
public class Umrti {
	
	/***
	 * @author Václav Janoch
	 * @author Filip Kupilik
	 * 
	 */

	/** Statisticke promenne tridy */
	public static long CelkemUmrtnost;
	private static long prvniC;
	private static long druheC;
	private static long tretiC;

	/** Promenne potrebne pro praci */
	private String planeta;
	private int[] pocetObyvatel;
	private int pocatecnyStavObyvatel;
	private int pomPocetObyvatel = pocatecnyStavObyvatel;

	/**
	 * Konstruktor tridy nastavi jmeno planety pro kteru je statistika vedena a
	 * pocatecny stav obyvatel na planete
	 * 
	 * @param planeta
	 *            Planeta pro kterou je statistika vedena
	 * @param pocatecnyStavObyvatel
	 *            pocatecny stav obyvatel na planete
	 */
	public Umrti(String planeta, int pocatecnyStavObyvatel) {
		this.setPlaneta(planeta);
		this.pocetObyvatel = new int[12];
		this.setPocatecnyStavObyvatel(pocatecnyStavObyvatel);
	}

	/**
	 * Prepsana metoda toString pro vypis tridy
	 */
	@Override
	public String toString() {

		String vypisObyvatel = "";

		for (int i = 0; i < pocetObyvatel.length; i++) {
			int mesic = i + 1;
			vypisObyvatel += " Mesic " + mesic + ". pocet obyvatel " + pocetObyvatel[i] + ".  \n";
		}
		String vypis = "Planeta: " + planeta + "vyvoj obyvatel\n" + vypisObyvatel;
		return vypis;
	}

	/**
	 * Metoda ktera urci umrti za prvni ctyri mesice
	 */
	public void prvniCtvrtleti() {
		int pocet = 0;

		for (int i = 0; i < 4; i++) {
			pocet += pomPocetObyvatel - (pomPocetObyvatel - pocetObyvatel[i]);
			pomPocetObyvatel = pocetObyvatel[i];
		}
		CelkemUmrtnost = pocet;
		prvniC += pocet;
	}

	/**
	 * Metoda ktera urci umrti za druhe ctyri mesice
	 */
	public void druheCtvrtleti() {
		int pocet = 0;

		for (int i = 4; i < 7; i++) {
			pocet += pomPocetObyvatel - (pomPocetObyvatel - pocetObyvatel[i]);
			pomPocetObyvatel = pocetObyvatel[i];
		}
		CelkemUmrtnost = pocet;
		druheC += pocet;
	}

	/**
	 * Metoda ktera urci umrti za posledni ctyri mesice
	 */
	public void tretiCtvrtleti() {
		int pocet = 0;

		for (int i = 8; i < 11; i++) {
			pocet += pomPocetObyvatel - (pomPocetObyvatel - pocetObyvatel[i]);
			pomPocetObyvatel = pocetObyvatel[i];
		}
		CelkemUmrtnost = pocet;
		tretiC += pocet;
	}
	/****************************** Getry a Setry ************/

	/**
	 * @return the planeta
	 */
	public String getPlaneta() {
		return planeta;
	}

	/**
	 * @param planeta
	 *            the planeta to set
	 */
	public void setPlaneta(String planeta) {
		this.planeta = planeta;
	}

	/**
	 * @return the pocetObyvatel
	 */
	public int[] getPocetObyvatel() {
		return pocetObyvatel;
	}

	/**
	 * @param pocetObyvatel
	 *            the pocetObyvatel to set
	 */
	public void setPocetObyvatel(int[] pocetObyvatel) {
		this.pocetObyvatel = pocetObyvatel;
	}

	/**
	 * @return the pocatecnyStavObyvatel
	 */
	public int getPocatecnyStavObyvatel() {
		return pocatecnyStavObyvatel;
	}

	/**
	 * @param pocatecnyStavObyvatel
	 *            the pocatecnyStavObyvatel to set
	 */
	public void setPocatecnyStavObyvatel(int pocatecnyStavObyvatel) {
		this.pocatecnyStavObyvatel = pocatecnyStavObyvatel;
	}

	/**
	 * @return the celkemUmrtnost
	 */
	public static long getCelkemUmrtnost() {
		return CelkemUmrtnost;
	}

	/**
	 * @param celkemUmrtnost
	 *            the celkemUmrtnost to set
	 */
	public static void setCelkemUmrtnost(long celkemUmrtnost) {
		CelkemUmrtnost = celkemUmrtnost;
	}

	/**
	 * @return the prvniC
	 */
	public static long getPrvniC() {
		return prvniC;
	}

	/**
	 * @param prvniC
	 *            the prvniC to set
	 */
	public static void setPrvniC(long prvniC) {
		Umrti.prvniC = prvniC;
	}

	/**
	 * @return the druheC
	 */
	public static long getDruheC() {
		return druheC;
	}

	/**
	 * @param druheC
	 *            the druheC to set
	 */
	public static void setDruheC(long druheC) {
		Umrti.druheC = druheC;
	}

	/**
	 * @return the tretiC
	 */
	public static long getTretiC() {
		return tretiC;
	}

	/**
	 * @param tretiC
	 *            the tretiC to set
	 */
	public static void setTretiC(long tretiC) {
		Umrti.tretiC = tretiC;
	}

}
