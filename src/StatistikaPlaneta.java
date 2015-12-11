
public class StatistikaPlaneta {
	/***
	 * @author Václav Janoch
	 * @author Filip Kupilik
	 * 
	 */

	/** staticke promenne tridy */
	private static long celkovyPocetLeku;

	/** Promenne potrebne pro praci */
	private int pocetLeku;
	private Lod lod;
	private int mesic;
	

	/**
	 * Konstruktor tridy ulozi potrebny pocet pro dovezeni lod ktera leky
	 * prevazi a aktualni mesic
	 * 
	 * @param pocetLeku
	 *            pocet leku ktere jsou potreba dany mesic
	 * @param lod
	 * @param mesic
	 */
	public StatistikaPlaneta(int pocetLeku, Lod lod, int mesic) {
		this.setLod(lod);
		this.setPocetLeku(pocetLeku);
		this.setMesic(mesic);

	}

	/**
	 * Konstruktor tridy pokud planeta neni zasobovana zadnou lodi pro
	 * vzdalenost
	 * 
	 * @param s
	 */
	public StatistikaPlaneta() {

	}

	/**
	 * Prepsana metoda toString
	 */
	@Override
	public String toString() {

		String vypis = "V " + mesic + ". mesic byla planeta zasobovana leky v poctu: " + pocetLeku + " lodi: "
				+ lod.getNazev();
		return vypis;
	}

	/**
	 * Metoda pro zvyseni celkoveho poctu leku ktere jsou potreba
	 */
	public void zvysPocet() {
		celkovyPocetLeku += (long) pocetLeku;
	}

	/***************************************************
	 * Getry a Setry
	 *********************************/

	/**
	 * @return the pocetLeku
	 */
	public int getPocetLeku() {
		return pocetLeku;
	}

	/**
	 * @param pocetLeku
	 *            the pocetLeku to set
	 */
	public void setPocetLeku(int pocetLeku) {
		this.pocetLeku = pocetLeku;
	}

	/**
	 * @return the lod
	 */
	public Lod getLod() {
		return lod;
	}

	/**
	 * @param lod
	 *            the lod to set
	 */
	public void setLod(Lod lod) {
		this.lod = lod;
	}

	/**
	 * @return the celkovyPocetLeku
	 */
	public static long getCelkovyPocetLeku() {
		return celkovyPocetLeku;
	}

	/**
	 * @param celkovyPocetLeku
	 *            the celkovyPocetLeku to set
	 */
	public static void setCelkovyPocetLeku(long celkovyPocetLeku) {
		StatistikaPlaneta.celkovyPocetLeku = celkovyPocetLeku;
	}

	/**
	 * @return the mesic
	 */
	public int getMesic() {
		return mesic;
	}

	/**
	 * @param mesic
	 *            the mesic to set
	 */
	public void setMesic(int mesic) {
		this.mesic = mesic;
	}


}
