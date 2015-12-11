import java.awt.Point;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Lod implements Comparable<Lod> {

	/***
	 * @author Václav Janoch
	 * @author Filip Kupilik
	 * 
	 */

	/** Konstanta pro urceni maximalni hmotnosti nakladu na lodi */
	private final int maxNostnost = 5000000;

	/** Staticka promenna tridy*/
	private static int prepadeniCelkem;

	/** Promenne potrebne pro praci */
	private String nazev;
	private ObchodniCesta obC;
	private double dobaLetu;
	private int vzdalenost;
	private int urazeno = 0;
	private boolean stav = true;
	private Planeta planeta;
	private StatistikaLod statistika;
	private int index = 0;
	private int prepadeni;

	/** Instanace tridy PrintWrite pro vypis do souboru */
	PrintWriter pw;

	/**
	 * Konstruktor tridy Ulozi nazev lode
	 * 
	 * @param nazev
	 *            nazev lode
	 */
	public Lod(String nazev) {

		this.nazev = nazev;
		planeta = new Planeta("centrala", 0, new Point(0, 0));
		statistika = new StatistikaLod(nazev);
	}

	/**
	 * Metoda pro porovnavani lodi
	 * 
	 * @param o
	 *            Lod se kterou se bude porovnavat
	 * @return lod ktera ma vetsi dobu letu
	 */
	@Override
	public int compareTo(Lod o) {

		int vzdalenost =(int)((Lod) o).dobaLetu;
		return (int)this.dobaLetu - vzdalenost;

	}

	/**
	 * Zjisti jak dlouho bude trvat let spolecne s nakladkou v vylozenim leku
	 */
	public void zjistiDobuCesty() {
		setPrepadeniCelkem(getPrepadeniCelkem() + prepadeni);
		int mez = obC.getP().size();
		double vzdalenost = 0;
		double vysledek = 0;

		for (int i = 0; i < mez; i++) {
			vzdalenost = obC.getP().get(i).getVzdalenostOdcentraly();
			if (vzdalenost > vysledek) {
				vysledek = vzdalenost;
			}
		}

		vzdalenost = vysledek;
		vysledek += mez + 1;
		dobaLetu = 2.0*(vysledek / 25.0);
	
	}

	/**
	 * Prepsana metoda toString tridy pro jeji vypsani
	 */
	@Override
	public String toString() {

		zjistiDobuCesty();
		String info = "";

		if (stav) {
			info = "Nazev: " + getNazev() + " celkova doba letu: " + dobaLetu + " urazeno " + getUrazeno()
					+ " do planety:" + planeta.getNazev();
		} else {
			info = "Nazev: " + getNazev() + " byla napadena a vraci se do centraly "
					+ getObC().getP().get(0).getCentrala();
		}
		return info;
	}

	/**
	 * Metoda pro vypis statistiky lodi do souboru
	 */
	public void vypisLod() {
		try {

			pw = new PrintWriter(new BufferedWriter(new FileWriter("lode/" + getNazev() + ".txt")));

			pw.println(statistika);
			statistika.zvysPocetLeku();
			statistika.zvysPrepadeni();

			pw.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Vypise do statistik celkove statistiky vsechlodi
	 */
	public void vypisLodCelkem() {

		try {
			pw = new PrintWriter(new BufferedWriter(new FileWriter("lode/statistikaLodiCelkem.txt")));
			pw.println("Celkovy pocet prevezenych leku: " + statistika.getPocetLekuCelkem());
			pw.println("Celkovy pocet prepadeni: " + prepadeniCelkem);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		pw.close();
	}
	/************************* Getry a Setry *************************/

	/**
	 * @return the nazev
	 */
	public String getNazev() {
		return nazev;
	}

	/**
	 * @param nazev
	 *            the nazev to set
	 */
	public void setNazev(String nazev) {
		this.nazev = nazev;
	}

	/**
	 * @return the maxnostnost
	 */
	public int getMaxnostnost() {
		return maxNostnost;
	}

	/**
	 * @return the obC
	 */
	public ObchodniCesta getObC() {
		return obC;
	}

	/**
	 * @param obC
	 *            the obC to set
	 */
	public void setObC(ObchodniCesta obC) {
		this.obC = obC;
	}

	/**
	 * @return the stav
	 */
	public boolean isStav() {
		return stav;
	}

	/**
	 * @param stav
	 *            the stav to set
	 */
	public void setStav(boolean stav) {
		this.stav = stav;
	}

	/**
	 * @return the dobaLetu
	 */
	public double getDobaLetu() {
		return dobaLetu;
	}

	/**
	 * @param dobaLetu
	 *            the dobaLetu to set
	 */
	public void setDobaLetu(double dobaLetu) {
		this.dobaLetu = dobaLetu;
	}

	/**
	 * @return the vzdalenost
	 */
	public int getVzdalenost() {
		return vzdalenost;
	}

	/**
	 * @param vzdalenost
	 *            the vzdalenost to set
	 */
	public void setVzdalenost(int vzdalenost) {
		this.vzdalenost = vzdalenost;
	}

	/**
	 * @return the urazeno
	 */
	public int getUrazeno() {
		return urazeno;
	}

	/**
	 * @param urazeno
	 *            the urazeno to set
	 */
	public void setUrazeno(int urazeno) {
		this.urazeno = urazeno;
	}

	/**
	 * @return the planeta
	 */
	public Planeta getPlaneta() {
		return planeta;
	}

	/**
	 * @param planeta
	 *            the planeta to set
	 */
	public void setPlaneta(Planeta planeta) {
		this.planeta = planeta;
	}

	/**
	 * @return the statistika
	 */
	public StatistikaLod getStatistika() {
		return statistika;
	}

	/**
	 * @param statistika
	 *            the statistika to set
	 */
	public void setStatistika(StatistikaLod statistika) {
		this.statistika = statistika;
	}

	/**
	 * @return the index
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * @param index
	 *            the index to set
	 */
	public void setIndex(int index) {
		this.index = index;
	}

	/**
	 * @return the prepadeni
	 */
	public int getPrepadeni() {
		return prepadeni;
	}

	/**
	 * @param prepadeni
	 *            the prepadeni to set
	 */
	public void setPrepadeni(int prepadeni) {
		this.prepadeni = prepadeni;
	}

	/**
	 * @return the prepadeniCelkem
	 */
	public static int getPrepadeniCelkem() {
		return prepadeniCelkem;
	}

	/**
	 * @param prepadeniCelkem the prepadeniCelkem to set
	 */
	public static void setPrepadeniCelkem(int prepadeniCelkem) {
		Lod.prepadeniCelkem = prepadeniCelkem;
	}

}
