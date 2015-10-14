import java.awt.Point;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;



public class Rozdeleni {

	private static int pocetObyvatel;
	private static String nazev;
	private static int pocetPlanetCelkem;
	private static int prumer;
	private static double odchylka;
	private static int dolniMez;
	private static int horniMez;
	private static int velikostVesmiruX;
	private static int velikostVesmiruY;
	private static ArrayList<Planeta> planety;

	public Rozdeleni(int pocetPlanet, int prumer, double odchylka, int dolniMez, int horniMez, int velikstVesmiruX, int velikostVesmiruY) {
		
		this.pocetPlanetCelkem = pocetPlanet;
		this.prumer = prumer;
		this.odchylka = odchylka;
		this.dolniMez = dolniMez;
		this.horniMez = horniMez;
		this.velikostVesmiruX = velikostVesmiruX;
		this.velikostVesmiruY = velikostVesmiruY;
		
		planety = new ArrayList<Planeta>();
		
		prirazeni();
	}
	
	
	public static void prirazeni(){
		int pocetPlanet = 0;
		Random r = new Random();
		Point pozice = new Point(0, 0);
		

		while(pocetPlanet != pocetPlanetCelkem){
			 
			pocetObyvatel =(int)(prumer * (odchylka * r.nextGaussian()));
			
			if (pocetObyvatel > dolniMez && pocetObyvatel < horniMez) {
		
				nazev = String.valueOf(pocetObyvatel);
				pozice = new Point(r.nextInt(velikostVesmiruX), r.nextInt(velikostVesmiruY));
				for (int i = 0; i < planety.size(); i++) {
					
					if (pozice.distance(planety.get(i).poloha) < 2) {
					
						pozice = new Point(r.nextInt(800), r.nextInt(800));
					}
				}	
				
				planety.add(new Planeta(nazev,pocetObyvatel,pozice));
				pocetPlanet++;
			}
			
		}
	}
	
}
