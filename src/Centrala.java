import java.awt.Point;

public class Centrala extends Objekt {
	/***
	 * @author Václav Janoch
	 * @author Filip Kupilik
	 * 
	 */

	/** Promenna pro urceni poctu planet ktere budou obsluhovany */
	private int pocetZakazniku;

	/**
	 * Konstruktor tridy
	 * 
	 * @param nazev
	 *            nazev centraly
	 * @param poloha
	 *            jeji poloha ve vesmiru
	 */
	public Centrala(String nazev, Point poloha) {
		super(nazev, poloha);
		// TODO Auto-generated constructor stub
	}

	/***************** Getry a Setry **********/

	/**
	 * @return the pocetZakazniku
	 */
	public int getPocetZakazniku() {
		return pocetZakazniku;
	}

	/**
	 * @param pocetZakazniku
	 *            the pocetZakazniku to set
	 */
	public void setPocetZakazniku(int pocetZakazniku) {
		this.pocetZakazniku = pocetZakazniku;
	}

	@Override
	Objednavka vytvorObjednavku() {
		// TODO Auto-generated method stub
		return null;
	}

}
