import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

import com.sun.org.apache.bcel.internal.classfile.PMGClass;

import javafx.print.Collation;

public class Logistika {

	private final int maxNaklad = 5000000;
	private static int pomNazev = 10;
	Random r = new Random();

	private int den = 0;
	private int pocetCest;
	private int pocetCestCelkem;

	private Objednavka obj;
	private ArrayList<Planeta> planety;
	private ArrayList<Objednavka> objednavky;
	private Graf graf;
	private Dijkstra2 d;

	private int[] centrala0;
	private int[] centrala1;
	private int[] centrala2;
	private int[] centrala3;
	private int[] centrala4;
	// int truee;
	private ArrayList<Cesta> cesta;
	private ArrayList<Planeta> planetyCentrala0 = new ArrayList<Planeta>();
	private ArrayList<Planeta> planetyCentrala1 = new ArrayList<Planeta>();
	private ArrayList<Planeta> planetyCentrala2 = new ArrayList<Planeta>();
	private ArrayList<Planeta> planetyCentrala3 = new ArrayList<Planeta>();
	private ArrayList<Planeta> planetyCentrala4 = new ArrayList<Planeta>();
	
	private ArrayList<Lod> seznamLodiVolne = new ArrayList<Lod>();
	private ArrayList<Lod> seznamLodiZabrane = new ArrayList<Lod>();
	private ArrayList<ObchodniCesta> cestaO = new ArrayList<ObchodniCesta>();
	
	public Logistika(ArrayList<Planeta> planety, Graf g) {

		this.planety = planety;
		this.objednavky = new ArrayList<Objednavka>();
		this.setGraf(g);
		this.d = new Dijkstra2(g);
		this.cesta = new ArrayList<Cesta>();
		vytvorTrasy();

		if (getDen() % 30 == 0) {
			prijmiObjednavky();
		}

		/*for (int i = 0; i < cesta.size(); i++) {
			
			vypravLod(cesta.get(i));
		}*/
		
		//System.out.println(seznamLodiZabrane.size());
		
	}

	public ObchodniCesta sectiObjednavkyNaTrase(Cesta cesta) {

		int celkem = 0;
		int pomIndex = 0;
		int lastIndex = 0;
		ArrayList<Planeta> pomPlanety = new ArrayList<Planeta>();

		for (int i = (cesta.getPlanety().size() - 1); i > 0; i--) {
			pomIndex = cesta.getPlanety().get(i);

			if (celkem < maxNaklad && planety.get(pomIndex).isObsluhovan() != true) {

				lastIndex = pomIndex;
				celkem += planety.get(pomIndex).getObjednavka().getPocetLeku();
				planety.get(pomIndex).setObsluhovan(true);
				pomPlanety.add(planety.get(pomIndex));

			} else {
				break;
			}

		}
		// System.out.println(lastIndex + " last index");
		if (celkem > maxNaklad) {
			celkem -= planety.get(lastIndex).getObjednavka().getPocetLeku();
			planety.get(lastIndex).setObsluhovan(false);
			pomPlanety.remove(pomPlanety.size() - 1);

		}
		return new ObchodniCesta(pomPlanety, celkem);
	}

	/**
	 * Vypravy lod z centraly do cile s potrebnym poctem leku
	 */
	public void vypravLod(Cesta c) {
		
		Lod lod;
		int index = 0;
		if (seznamLodiVolne.isEmpty()) {
			vytvorLod();
		}
			index = seznamLodiVolne.size()-1;
			
			lod = seznamLodiVolne.get(index);
			lod.setObC(sectiObjednavkyNaTrase(c));
			
			seznamLodiZabrane.add(lod);
			seznamLodiVolne.remove(index);
	
	}

	public void vytvorLod() {
		seznamLodiVolne.add(new Lod("X" + pomNazev));
		pomNazev++;

	}

	/**
	 * najde cesty v grafu;
	 * 
	 * @param d
	 */
	public void najdiVzdalenostiodCentaly() {

		centrala0 = d.dijkstra(0);
		centrala1 = d.dijkstra(1);
		centrala2 = d.dijkstra(2);
		centrala3 = d.dijkstra(3);
		centrala4 = d.dijkstra(4);

		najdiCentralu();

	}

	/**
	 * Najde kazde planete jednu nejblizsi centralu
	 * 
	 */
	public void najdiCentralu() {

		for (int i = 5; i < planety.size(); i++) {

			if (centrala0[i] < centrala1[i] && centrala0[i] < centrala2[i] && centrala0[i] < centrala3[i]
					&& centrala0[i] < centrala4[i]) {

				planety.get(i).setCentrala("0");
				planety.get(i).setVzdalenostOdcentraly(centrala0[i]);
				planetyCentrala0.add(planety.get(i));

			} else if (centrala1[i] < centrala0[i] && centrala1[i] < centrala2[i] && centrala1[i] < centrala3[i]
					&& centrala1[i] < centrala4[i]) {

				planety.get(i).setCentrala("1");
				planety.get(i).setVzdalenostOdcentraly(centrala1[i]);
				planetyCentrala1.add(planety.get(i));

			} else if (centrala2[i] < centrala0[i] && centrala2[i] < centrala1[i] && centrala2[i] < centrala3[i]
					&& centrala2[i] < centrala4[i]) {

				planety.get(i).setCentrala("2");
				planety.get(i).setVzdalenostOdcentraly(centrala2[i]);
				planetyCentrala2.add(planety.get(i));
			} else if (centrala3[i] < centrala0[i] && centrala3[i] < centrala1[i] && centrala3[i] < centrala2[i]
					&& centrala3[i] < centrala4[i]) {

				planety.get(i).setCentrala("3");
				planety.get(i).setVzdalenostOdcentraly(centrala3[i]);
				planetyCentrala3.add(planety.get(i));
			} else if (centrala4[i] < centrala0[i] && centrala4[i] < centrala1[i] && centrala4[i] < centrala2[i]
					&& centrala4[i] < centrala3[i]) {

				planety.get(i).setCentrala("4");
				planety.get(i).setVzdalenostOdcentraly(centrala4[i]);
				planetyCentrala4.add(planety.get(i));
			} else {
				planety.get(i).setCentrala("4");
				planety.get(i).setVzdalenostOdcentraly(centrala2[i]);
				planetyCentrala4.add(planety.get(i));

			}

		}
	}

	/**
	 * Najde a ulozi vsechny cesty v grafu do listu
	 */
	/**
	 * pomocna metoda pro cesty
	 * 
	 * @param s
	 * @param p
	 */
	public void dijkstraCesta(int s, ArrayList<Planeta> p) {

		d.dijkstra(s);
		for (int i = 0; i < p.size(); i++) {
			cesta.add(new Cesta(d.printPath(0, Integer.parseInt(p.get(i).getNazev())), nebezpecnaCesta()));
			p.get(i).setP(cesta.get(cesta.size() - 1));
		}
	}

	public void najdiCesty() {

		dijkstraCesta(0, planetyCentrala0);
		dijkstraCesta(1, planetyCentrala1);
		dijkstraCesta(2, planetyCentrala2);
		dijkstraCesta(3, planetyCentrala3);
		dijkstraCesta(4, planetyCentrala4);
	}

	/**
	 * Rozhodne zda se bude jednat o nebezpecnou cestu
	 * 
	 * @return
	 */
	public boolean nebezpecnaCesta() {

		zjistiCelkovyPocetCest();

		if (getPocetCest() % (getPocetCestCelkem() / (getPocetCestCelkem() * 0.2)) == 0) {
			return true;
		}

		pocetCest++;
		return false;
	}

	/**
	 * Zjisti celkovy pocet cest v Galaxii
	 */
	public void zjistiCelkovyPocetCest() {
		int pocet = planetyCentrala0.size() + planetyCentrala1.size() + planetyCentrala2.size()
				+ planetyCentrala3.size() + planetyCentrala4.size();
		setPocetCestCelkem(pocet);
	}

	/**
	 * Prijme objednavky na dany mesic
	 */
	public void prijmiObjednavky() {

		for (int i = 0; i < planety.size(); i++) {
			objednavky.add(planety.get(i).vytrorObjednavku());
		}

	}

	/**
	 * Seradi planety podle jednotlivych vzdalenosti v jejich liste central
	 */
	public void seradPlanetyPodleVzdalenosti() {
		Collections.sort(planetyCentrala0);
		Collections.sort(planetyCentrala1);
		Collections.sort(planetyCentrala2);
		Collections.sort(planetyCentrala3);
		Collections.sort(planetyCentrala4);

	}

	/**
	 * 
	 * @param start
	 */
	public void naplanujCestu(int start) {

	}

	/**
	 * Vytvori potrebne prostredi pro lode proste nevim co jak to jinak nazvat
	 * kurva uz :D
	 */
	public void vytvorTrasy() {
		najdiVzdalenostiodCentaly();
		najdiCesty();
		seradPlanetyPodleVzdalenosti();

	}

	public void vypisStav(File vystup) {
		PrintWriter pw;

		try {
			pw = new PrintWriter(vystup);

			for (int i = 5; i < planety.size(); i++) {
				System.out.println(planety.get(i));
				pw.println(planety.get(i));
			}

			pw.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/*********************************************
	 * Getry a Setry
	 ****************/
	/**
	 *
	 * @return
	 */
	public int getDen() {
		return den;
	}

	public void setDen(int den) {
		this.den = den;
	}

	public Objednavka getObj() {
		return obj;
	}

	public void setObj(Objednavka obj) {
		this.obj = obj;
	}

	public ArrayList<Planeta> getPlanety() {
		return planety;
	}

	public void setPlanety(ArrayList<Planeta> planety) {
		this.planety = planety;
	}

	/**
	 * @return the graf
	 */
	public Graf getGraf() {
		return graf;
	}

	/**
	 * @param graf
	 *            the graf to set
	 */
	public void setGraf(Graf graf) {
		this.graf = graf;
	}

	/**
	 * @return the pocetCest
	 */
	public int getPocetCest() {
		return pocetCest;
	}

	/**
	 * @param pocetCest
	 *            the pocetCest to set
	 */
	public void setPocetCest(int pocetCest) {
		this.pocetCest = pocetCest;
	}

	/**
	 * @return the pocetCestCelkem
	 */
	public int getPocetCestCelkem() {
		return pocetCestCelkem;
	}

	/**
	 * @param pocetCestCelkem
	 *            the pocetCestCelkem to set
	 */
	public void setPocetCestCelkem(int pocetCestCelkem) {
		this.pocetCestCelkem = pocetCestCelkem;
	}

}
