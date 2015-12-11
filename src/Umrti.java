import java.util.ArrayList;
import java.util.List;

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
	private List<Integer> pocetObyvatel;

	public void setPocetObyvatel(List<Integer> pocetObyvatel) {
		this.pocetObyvatel = pocetObyvatel;
	}

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
		this.pocetObyvatel = new ArrayList<Integer>();
		this.setPocatecnyStavObyvatel(pocatecnyStavObyvatel);
	}

	/**
	 * Prepsana metoda toString pro vypis tridy
	 */
	@Override
	public String toString() {

		String vypisObyvatel = "";

		for (int i = 0; i < pocetObyvatel.size(); i++) {
			int mesic = i + 1;
			vypisObyvatel += " Mesic " + mesic + ". pocet obyvatel " + pocetObyvatel.get(i) + ".  \n";
		}
		String vypis = "Planeta: " + planeta + " vyvoj obyvatel\n" + vypisObyvatel;
		return vypis;
	}

	/**
	 * Metoda ktera urci umrti za prvni ctyri mesice
	 */
	public void prvniCtvrtleti() {
		int pocet = 0;
		
		double pocetObyvatelD = pocetObyvatel.size();
		double mez = (pocetObyvatelD*(1.0/3.0))-1;
			
			for (int i = 0; i < mez; i++) {
				pocet += pomPocetObyvatel - (pomPocetObyvatel - pocetObyvatel.get(i));
				pomPocetObyvatel = pocetObyvatel.get(i);
			}
			
		CelkemUmrtnost = pocet;
		prvniC += pocet;
	}

	/**
	 * Metoda ktera urci umrti za druhe ctyri mesice
	 */
	public void druheCtvrtleti() {
		int pocet = 0;
		
		double pocetObyvatelD = pocetObyvatel.size();
		double mez = (pocetObyvatelD*(2.0/3.0))-1;
			
		for (int i = 4; i < mez; i++) {
			pocet += pomPocetObyvatel -  (pomPocetObyvatel - pocetObyvatel.get(i));
			pomPocetObyvatel = pocetObyvatel.get(i);
		}
		CelkemUmrtnost = pocet;
		druheC += pocet;
	}

	/**
	 * Metoda ktera urci umrti za posledni ctyri mesice
	 */
	public void tretiCtvrtleti() {
		int pocet = 0;

		for (int i = 8; i < pocetObyvatel.size(); i++) {
			pocet += pomPocetObyvatel - (pomPocetObyvatel - pocetObyvatel.get(i));
			pomPocetObyvatel = pocetObyvatel.get(i);
		}
		CelkemUmrtnost = pocet;
		tretiC += pocet;
	}
	/****************************** Getry a Setry ************/

	/**
	 * @return the pocetObyvatel
	 */
	public List<Integer> getPocetObyvatel() {
		return pocetObyvatel;
	}

	/**
	 * @param pocetObyvatel the pocetObyvatel to set
	 */
	
	
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
