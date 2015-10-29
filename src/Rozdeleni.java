import java.awt.Point;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

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
	private int pocetCentral;
	private ArrayList<Planeta> planety;
	private static PrintWriter pw;
	private static Scanner sc;

	private int []centrala0;
	private int []centrala1;
	private int []centrala2;
	private int []centrala3;
	private int []centrala4;
	
	private Dijkstra dijsktra;

	public Rozdeleni(String souborPlaneta, String souborCentrala) {
		
		this.planety = new ArrayList<Planeta>();
		this.dijsktra = new Dijkstra();

		nactiCentraly(souborCentrala);
		
		nactiPlanety(souborPlaneta);
		
	
	}

	public Rozdeleni(int pocetPlanet, int prumer, double odchylka, int pocetCentral, int dolniMez, int horniMez,
			int velikstVesmiruX, int velikostVesmiruY) {

		this.setPocetPlanetCelkem(pocetPlanet);
		this.setPrumer(prumer);
		this.setOdchylka(odchylka);
		this.setDolniMez(dolniMez);
		this.setHorniMez(horniMez);
		this.setVelikostVesmiruX(velikstVesmiruX);
		this.setVelikostVesmiruY(velikostVesmiruY);
		this.setPocetCentral(pocetCentral);
		this.planety = new ArrayList<Planeta>();

		prirazeniObyvatel();
	}

	public void prirazeniObyvatel() {
		int pocetPlanet = 0;
		Random r = new Random();
		Point pozice = new Point(0, 0);

		centraly();

		while (pocetPlanet != getPocetPlanetCelkem()) {

			pocetObyvatel = (int) (getPrumer() * (getOdchylka() * r.nextGaussian()));

			if (pocetObyvatel > getDolniMez() && pocetObyvatel < getHorniMez()) {

				setNazev(String.valueOf(pocetPlanet + pocetCentral));

				pozice = new Point(r.nextInt(getVelikostVesmiruX()), r.nextInt(getVelikostVesmiruY()));
				for (int i = 0; i < planety.size(); i++) {

					if (pozice.distance(planety.get(i).getPoloha()) < 2) {

						pozice = new Point(r.nextInt(800), r.nextInt(800));
					}
				}

				planety.add(new Planeta(nazev, pocetObyvatel, pozice));
				pocetPlanet++;
			}

		}
	}

	public void centraly() {
		double PrvniX = velikostVesmiruX * 0.25;
		double PrvniY = velikostVesmiruY * 0.25;
		double DruhaY = velikostVesmiruY * 0.75;
		double TretiX = velikostVesmiruX * 0.5;
		double TretiY = velikostVesmiruY * 0.5;
		double CtvrtaX = velikostVesmiruX * 0.75;
		Point[] pozice = new Point[5];
		pozice[0] = new Point((int) PrvniX, (int) PrvniY);
		pozice[1] = new Point((int) PrvniX, (int) DruhaY);
		pozice[2] = new Point((int) TretiX, (int) TretiY);
		pozice[3] = new Point((int) CtvrtaX, (int) DruhaY);
		pozice[4] = new Point((int) CtvrtaX, (int) PrvniY);
		for (int i = 0; i < pozice.length; i++) {
			planety.add(new Planeta(String.valueOf(i), 0, pozice[i]));
		}
	}

	public void vypisCentraly(String nazevSouboru) {

		try {
			pw = new PrintWriter(new BufferedWriter(new FileWriter(nazevSouboru)));
			for (int i = 0; i < getPocetCentral(); i++) {
				pw.print(planety.get(i).getNazev() + "," + planety.get(i).getPoloha().getX() + ","
						+ planety.get(i).getPoloha().getY() + "," + planety.get(i).getPocetObyvatel());
				for (int j = 0; j < planety.get(i).getPartneri().size(); j++) {
					pw.print("," + planety.get(i).getPartneri().get(j).getNazev());
				}

				pw.println();
			}

			pw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Soubor nebyl nalezen");
			System.exit(1);
		}

	}

	public void vypisPlanety(String nazevSouboru) {

		try {
			pw = new PrintWriter(new BufferedWriter(new FileWriter(nazevSouboru)));
			for (int i = pocetCentral; i < getPocetPlanetCelkem() + pocetCentral; i++) {
				pw.print(planety.get(i).getNazev() + "," + planety.get(i).getPoloha().getX() + ","
						+ planety.get(i).getPoloha().getY() + "," + planety.get(i).getPocetObyvatel());
				for (int j = 0; j < planety.get(i).getPartneri().size(); j++) {
					pw.print("," + planety.get(i).getPartneri().get(j).getNazev());
				}

				pw.println();

			}
			pw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Soubor nebyl nalezen");
			System.exit(1);
		}

	}


	public void nactiPlanety(String nazevSouboru) {
		try {
			sc = new Scanner(new File(nazevSouboru));

			String pomString;
			String[] parametry;
			int velikost;
			int pomPocetPlanet = 0;

			while (sc.hasNextLine()) {

				pomString = sc.nextLine();

				velikost = pomString.split(",").length;

				parametry = new String[velikost];

				parametry = pomString.split(",");

				double x = Double.valueOf(parametry[1]);
				double y = Double.valueOf(parametry[2]);
				Point pomPoloha = new Point((int) (x), (int) (y));
				planety.add(new Planeta(parametry[0], Integer.parseInt(parametry[3]), pomPoloha));
				pomPocetPlanet++;
			}
			setPocetPlanetCelkem(pomPocetPlanet);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Soubor: " + nazevSouboru + " nebyl nalezen");
			System.exit(1);
		}
	}

	public void nactiCentraly(String nazevSouboru) {
		try {
			sc = new Scanner(new File(nazevSouboru));

			String pomString;
			String[] parametry;
			int velikost;
			int pomPocetCentral = 0;
			while (sc.hasNextLine()) {

				pomString = sc.nextLine();

				velikost = pomString.split(",").length;

				parametry = new String[velikost];

				parametry = pomString.split(",");

				double x = Double.valueOf(parametry[1]);
				double y = Double.valueOf(parametry[2]);
				Point pomPoloha = new Point((int) (x), (int) (y));

				planety.add(new Planeta(parametry[0], Integer.valueOf(parametry[3]), pomPoloha));
				pomPocetCentral++;
			}
			setPocetCentral(pomPocetCentral);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Soubor: " + nazevSouboru + " nebyl nalezen");
			System.exit(1);
		}
	}

	public void najdiCentralu(Graf graf){
		
		try {
			centrala0 = dijsktra.dijkstra_function(graf,0);
			centrala1 = dijsktra.dijkstra_function(graf,1);
			centrala2 = dijsktra.dijkstra_function(graf,2);
			centrala3 = dijsktra.dijkstra_function(graf,3);
			centrala4 = dijsktra.dijkstra_function(graf,4);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int pom = 0;
		for (int i = 5; i < planety.size(); i++) {
						
			
			if (centrala0[i] < centrala1[i] && centrala0[i] < centrala2[i] && centrala0[i] < centrala3[i] && centrala0[i] < centrala4[i]) {
				
				planety.get(i).setCentrala("0");
				planety.get(i).setVzdalenostOdcentraly(centrala0[i]);
			
			}else if (centrala1[i] < centrala0[i] && centrala1[i] < centrala2[i] && centrala1[i] < centrala3[i] && centrala1[i] < centrala4[i]) {
				
				planety.get(i).setCentrala("1");
				planety.get(i).setVzdalenostOdcentraly(centrala1[i]);
			
			}else if (centrala2[i] < centrala0[i] && centrala2[i] < centrala1[i] && centrala2[i] < centrala3[i] && centrala2[i] < centrala4[i]) {
			
				planety.get(i).setCentrala("2");
				planety.get(i).setVzdalenostOdcentraly(centrala2[i]);
			
			}else if (centrala3[i] < centrala0[i] && centrala3[i] < centrala1[i] && centrala3[i] < centrala2[i] && centrala3[i] < centrala4[i]) {
			
				planety.get(i).setCentrala("3");
				planety.get(i).setVzdalenostOdcentraly(centrala3[i]);
			
			}else if (centrala4[i] < centrala0[i] && centrala4[i] < centrala1[i] && centrala4[i] < centrala2[i] && centrala4[i] < centrala3[i]) {
			
				planety.get(i).setCentrala("4");
				planety.get(i).setVzdalenostOdcentraly(centrala4[i]);
			}
		}
		
	}
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

	public int getPocetCentral() {
		return pocetCentral;
	}

	public void setPocetCentral(int pocetCentral) {
		this.pocetCentral = pocetCentral;
	}

}
