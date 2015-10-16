import java.awt.Point;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;



public class Rozdeleni {

	private int pocetObyvatel;
	private String nazev;
	private int pocetPlanetCelkem;
	private int prumer;
	private double odchylka;
	private int dolniMez;
	private int horniMez;
	private int velikostVesmiruX;
	private int velikostVesmiruY;
	private ArrayList<Planeta> planety;

	public int getVelikostVesmiruY() {
		return velikostVesmiruY;
	}


	public void setVelikostVesmiruY(int velikostVesmiruY) {
		this.velikostVesmiruY = velikostVesmiruY;
	}
	
	public int getPocetObyvatel() {
		return pocetObyvatel;
	}


	public void setPocetObyvatel(int pocetObyvatel) {
		this.pocetObyvatel = pocetObyvatel;
	}


	public String getNazev() {
		return nazev;
	}


	public void setNazev(String nazev) {
		this.nazev = nazev;
	}


	public int getPocetPlanetCelkem() {
		return pocetPlanetCelkem;
	}


	public void setPocetPlanetCelkem(int pocetPlanetCelkem) {
		this.pocetPlanetCelkem = pocetPlanetCelkem;
	}


	public int getPrumer() {
		return prumer;
	}


	public void setPrumer(int prumer) {
		this.prumer = prumer;
	}


	public double getOdchylka() {
		return odchylka;
	}


	public void setOdchylka(double odchylka) {
		this.odchylka = odchylka;
	}


	public int getDolniMez() {
		return dolniMez;
	}


	public void setDolniMez(int dolniMez) {
		this.dolniMez = dolniMez;
	}


	public int getHorniMez() {
		return horniMez;
	}


	public void setHorniMez(int horniMez) {
		this.horniMez = horniMez;
	}


	public int getVelikostVesmiruX() {
		return velikostVesmiruX;
	}


	public void setVelikostVesmiruX(int velikostVesmiruX) {
		this.velikostVesmiruX = velikostVesmiruX;
	}


	public ArrayList<Planeta> getPlanety() {
		return planety;
	}


	public void setPlanety(ArrayList<Planeta> planety) {
		this.planety = planety;
	}


	public Rozdeleni(int pocetPlanet, int prumer, double odchylka, int dolniMez, int horniMez, int velikstVesmiruX, int velikostVesmiruY) {
		
		this.setPocetPlanetCelkem(pocetPlanet);
		this.setPrumer(prumer);
		this.setOdchylka(odchylka);
		this.setDolniMez(dolniMez);
		this.setHorniMez(horniMez);
		this.setVelikostVesmiruX(velikstVesmiruX);
		this.setVelikostVesmiruY(velikostVesmiruY);
		
		planety = new ArrayList<Planeta>();
		
		prirazeniObyvatel();
	}
	
	
	public void prirazeniObyvatel(){
		int pocetPlanet = 0;
		Random r = new Random();
		Point pozice = new Point(0, 0);

		while(pocetPlanet != getPocetPlanetCelkem()){
			 
			pocetObyvatel =(int)( getPrumer()* (getOdchylka() * r.nextGaussian()));
			
			if (pocetObyvatel > getDolniMez() && pocetObyvatel < getHorniMez()) {
		
				setNazev(String.valueOf(pocetPlanet + 1));
			
				pozice = new Point(r.nextInt(getVelikostVesmiruX()), r.nextInt(getVelikostVesmiruY()));
				for (int i = 0; i < planety.size(); i++) {
					
					if (pozice.distance(planety.get(i).getPoloha()) < 2) {
					
						pozice = new Point(r.nextInt(800), r.nextInt(800));
					}
				}	
				
				planety.add(new Planeta(nazev,pocetObyvatel,pozice));
				pocetPlanet++;
			}
			
		}
	}
	


		
}
