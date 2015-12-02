import java.awt.Point;
import java.util.ArrayList;

public class Lod implements Comparable<Lod>{

	private final int maxNostnost = 5000000;

	private String nazev;

	private ObchodniCesta obC;

	private Point pozice;

	private int dobaLetu;
	private int vzdalenost;
	
	private int urazeno = 0;

	private boolean stav = true;

	private Planeta planeta;
	
	public Lod(String nazev) {

		this.nazev = nazev;
		planeta = new Planeta("centrala",0,new Point(0, 0));

	}
	/**
	 * Metoda pro porovnavani lodi 
	 * @param o
	 * @return lod ktera ma vetsi dobu letu
	 */
	@Override
	public int compareTo(Lod o) {
		
		int vzdalenost = ((Lod)o).dobaLetu;
		return this.dobaLetu - vzdalenost ;

	}
	/**
	 * Zjisti jak dlouho bude trvat let spolecne s nakladkou v vylozenim leku
	 */
	public void zjistiDobuCesty() {
		int mez = obC.getP().size();
		int vzdalenost = 0;
		int vysledek = 0;

		for (int i = 0; i < mez; i++) {
			vzdalenost = obC.getP().get(i).getVzdalenostOdcentraly();
			if (vzdalenost > vysledek) {
				vysledek = vzdalenost;
			}
		}

		vzdalenost = vysledek;
		vysledek += mez + 1;
		dobaLetu = vysledek / 24;

	}
	
	@Override
	public String toString() {
		
		zjistiDobuCesty();
		String info = "";
		
		if (stav) {
			 info = "Nazev: " + getNazev() + " celkova doba letu: " + dobaLetu + " urazeno " + getUrazeno() + " do planety:" + planeta.getNazev(); 			
		}else {
			info = "Nazev: " + getNazev() + " byla napadena a vraci se do centraly " + getObC().getP().get(0).getCentrala();
		}
		return info;
	}

	/*********************************Getry a Setry ***************************************/

	/**
	 * 
	 * @return
	 */
	public Point getPozice() {
		return pozice;
	}

	/**
	 * 
	 * @param pozice
	 */
	public void setPozice(Point pozice) {
		this.pozice = pozice;
	}

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
	public int getDobaLetu() {
		return dobaLetu;
	}

	/**
	 * @param dobaLetu
	 *            the dobaLetu to set
	 */
	public void setDobaLetu(int dobaLetu) {
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
	 * @param urazeno the urazeno to set
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
	 * @param planeta the planeta to set
	 */
	public void setPlaneta(Planeta planeta) {
		this.planeta = planeta;
	}

	

}
