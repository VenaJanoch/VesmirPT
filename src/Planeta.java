import java.awt.Point;
import java.util.ArrayList;



public class Planeta {
	
	private String nazev;
	private int pocetObyvatel;
	private Point poloha;
	private ArrayList<Planeta> partneri;

	
	public Planeta(String nazev, int pocetObyvatel, Point poloha) {
		setNazev(nazev);
		setPocetObyvatel(pocetObyvatel);
		setPoloha(poloha);
	
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
	
	
}
