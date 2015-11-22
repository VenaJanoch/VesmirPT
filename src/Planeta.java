import java.awt.Point;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

public class Planeta implements Comparable<Planeta>{

	private String nazev;
	private int pocetObyvatel;
	private Point poloha;
	
	private ArrayList<Planeta> partneri;
	private String centrala;
	private int vzdalenostOdcentraly;
	private double dobaLetu;
	
	private Objednavka objednavka;
	private Cesta p; 
	private boolean obsluhovan;
	
	private Random r = new Random();
	


	public Planeta(String nazev, int pocetObyvatel, Point poloha) {

		setNazev(nazev);
		setPocetObyvatel(pocetObyvatel);
		setPoloha(poloha);

	}
	

	@Override
	public int compareTo(Planeta p) {
		int vzdalenost = ((Planeta)p).vzdalenostOdcentraly;
		return vzdalenost - this.vzdalenostOdcentraly;

	}
	
	@Override
	public String toString() {
		String popis = "Planeta " + nazev + " je zasobovana z " + getCentrala() + " centraly, poctem leku "
				+ objednavka.getPocetLeku() + " za " + dobaLetu + " dnu ";

		return popis;
	}
	
	/**
	 * vytvori svoji objednavku 
	 * @return pocet leku
	 */
	
	public Objednavka vytrorObjednavku(){
		double pocetBaleni = 0;

	pocetBaleni = (double) (getPocetObyvatel())
			* (double) ((double) (100 - vlastniVyroba()) / 100.0);
			Objednavka o = new Objednavka((int)pocetBaleni,getCentrala(),getNazev(),spoctiCasLetu());
	setObjednavka(o);
		return o;
	}
	/**
	 * urci pocet potrebny pocet procent vlastni potreby
	 * @return procenta
	 */
	public int vlastniVyroba() {
		int procenta = r.nextInt(60) + 20;
		return procenta;

	}
	
	/**
	 * 
	 * @param vzdalenostOdcentraly
	 * @return
	 */
	public int spoctiCasLetu() {
		double doba = vzdalenostOdcentraly / 25.0;
		dobaLetu = doba + 1;
		return (int)doba +1;
	}

	public String getNazev() {
		return nazev;
	}

	public void setNazev(String nazev) {
		this.nazev = nazev;
	}

	public int getPocetObyvatel() {
		return pocetObyvatel;
	}

	public void setPocetObyvatel(int pocetObyvatel) {
		this.pocetObyvatel = pocetObyvatel;
	}

	public Point getPoloha() {
		return poloha;
	}

	public void setPoloha(Point poloha) {
		this.poloha = poloha;
	}

	public ArrayList<Planeta> getPartneri() {
		return partneri;
	}

	public void setPartneri(ArrayList<Planeta> partneri) {
		this.partneri = partneri;
	}

	/**
	 * @return the centrala
	 */
	public String getCentrala() {
		return centrala;
	}

	/**
	 * @param centrala
	 *            the centrala to set
	 */
	public void setCentrala(String centrala) {
		this.centrala = centrala;
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
	 * @return the objednavka
	 */
	public Objednavka getObjednavka() {
		return objednavka;
	}

	/**
	 * @param objednavka
	 *            the objednavka to set
	 */
	public void setObjednavka(Objednavka objednavka) {
		this.objednavka = objednavka;
	}

	/**
	 * @return the vzdalenostOdcentraly
	 */
	public int getVzdalenostOdcentraly() {
		return vzdalenostOdcentraly;
	}

	/**
	 * @param vzdalenostOdcentraly
	 *            the vzdalenostOdcentraly to set
	 */
	public void setVzdalenostOdcentraly(int vzdalenostOdcentraly) {
		this.vzdalenostOdcentraly = vzdalenostOdcentraly;
	}


	/**
	 * @return the p
	 */
	public Cesta getP() {
		return p;
	}


	/**
	 * @param cesta the p to set
	 */
	public void setP(Cesta cesta) {
		this.p = cesta;
	}


	/**
	 * @return the obsluhovan
	 */
	public boolean isObsluhovan() {
		return obsluhovan;
	}


	/**
	 * @param obsluhovan the obsluhovan to set
	 */
	public void setObsluhovan(boolean obsluhovan) {
		this.obsluhovan = obsluhovan;
	}



	

}
