import java.awt.Point;
import java.util.ArrayList;

public class Lod {
	
	private final int maxNostnost = 5000000;
	private String nazev;
	private int aktualniPocetLeku;
	private ObchodniCesta obC;
	private Point pozice;
	private boolean stav = true;
	
	public Lod(String nazev) {
	
		this.nazev = nazev;
		
		}

	
	/********************************* Getry a Setry ***************************************/
	public int getAktualniPocetLeku() {
		return aktualniPocetLeku;
	}
	/**
	 * 
	 * @param aktualniPocetLeku
	 */
	public void setAktualniPocetLeku(int aktualniPocetLeku) {
		this.aktualniPocetLeku = aktualniPocetLeku;
	}
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
	 * @param nazev the nazev to set
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
	 * @param obC the obC to set
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
	 * @param stav the stav to set
	 */
	public void setStav(boolean stav) {
		this.stav = stav;
	}


	}
