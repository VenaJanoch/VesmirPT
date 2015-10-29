import java.awt.Point;
import java.util.ArrayList;



public class Planeta {
	
	private String nazev;
	private int pocetObyvatel;
	private Point poloha;
	private ArrayList<Planeta> partneri;
	private String centrala;
	private int vzdalenostOdcentraly;
	private double dobaLetu;
	private Objednavka objednavka;

	
	public Planeta(String nazev, int pocetObyvatel, Point poloha) {
	
		setNazev(nazev);
		setPocetObyvatel(pocetObyvatel);
		setPoloha(poloha);
	
	}
	
	

	@Override
	public String toString() {
		String popis = "Planeta " + nazev + " je zasobovana z " + getCentrala() + " centraly, poctem leku " 
	+ objednavka.getPocetLeku() + " za " + dobaLetu + " dnu ";
		
		return popis;
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
	 * @param centrala the centrala to set
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
	 * @param dobaLetu the dobaLetu to set
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
	 * @param objednavka the objednavka to set
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
	 * @param vzdalenostOdcentraly the vzdalenostOdcentraly to set
	 */
	public void setVzdalenostOdcentraly(int vzdalenostOdcentraly) {
		this.vzdalenostOdcentraly = vzdalenostOdcentraly;
	}
	
	
}
