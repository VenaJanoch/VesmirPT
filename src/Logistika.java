import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

import javax.swing.plaf.synth.SynthSeparatorUI;

import com.sun.org.apache.bcel.internal.classfile.PMGClass;

import javafx.print.Collation;

public class Logistika {

	/***
	 * @author V�clav Janoch
	 * @author Filip Kupilik
	 * 
	 */

	/** Konstanty tridy logistika **/
	private final int maxNaklad = 5000000;
	private final int rychlostLodi = 25;

	/** staticke promenne tridy Logistika */
	private static int pomNazev = 10;

	/**
	 * Promenne potrebne pro praci s daty
	 */
	private int pocetCest;
	private int pocetCestCelkem;
	private Objednavka obj;
	private ArrayList<Planeta> planety;
	private ArrayList<Objednavka> objednavky;
	private Graf graf;
	private Dijkstra d;

	private int[] centrala0;
	private int[] centrala1;
	private int[] centrala2;
	private int[] centrala3;
	private int[] centrala4;

	int truee;
	private ArrayList<Cesta> cesta;
	private ArrayList<Planeta> planetyCentrala0 = new ArrayList<Planeta>();
	private ArrayList<Planeta> planetyCentrala1 = new ArrayList<Planeta>();
	private ArrayList<Planeta> planetyCentrala2 = new ArrayList<Planeta>();
	private ArrayList<Planeta> planetyCentrala3 = new ArrayList<Planeta>();
	private ArrayList<Planeta> planetyCentrala4 = new ArrayList<Planeta>();

	private ArrayList<Planeta> planetyPodleVzdalenosti = new ArrayList<Planeta>();

	private ArrayList<Lod> seznamLodiVolne = new ArrayList<Lod>();
	private ArrayList<Lod> seznamLodiZabrane = new ArrayList<Lod>();
	private ArrayList<ObchodniCesta> cestaO = new ArrayList<ObchodniCesta>();

	/**
	 * Pomocne promenne
	 */
	private int pocet;
	private int mesic = 0;

	/**
	 * Instance tridi Random pro praci s nahodnymi cisly
	 */
	Random r = new Random();

	/**
	 * Konstruktor tridy Nacte pripravene planet a graf vesmiru
	 * 
	 * @param planety
	 *            pripravene planety
	 * @param g
	 *            instatnce tridy graf pro praci s planetami
	 */
	public Logistika(ArrayList<Planeta> planety, Graf g) {

		this.planety = planety;
		this.objednavky = new ArrayList<Objednavka>();
		this.setGraf(g);
		this.d = new Dijkstra(g);
		this.cesta = new ArrayList<Cesta>();
		vytvorTrasy();

	}

	/**
	 * Ridi dopravu zbozi na jednotlive planety podle jejich dodavek
	 * 
	 * @param cas
	 *            Instance tridy cas pro rizeni chodu simulace
	 */
	public void dopravZbozi(Cas cas) {

		int pocetUrazenychMil;
		Lod pom;
		int index;
		int indexDodavky;

		Collections.sort(seznamLodiZabrane);

		for (int i = 0; i < seznamLodiZabrane.size(); i++) {

			index = seznamLodiZabrane.get(i).getObC().getIndexUseky();
			indexDodavky = seznamLodiZabrane.get(i).getObC().getIndexDodavky();

			pom = seznamLodiZabrane.get(i);
			pocetUrazenychMil = pom.getUrazeno();

			// pokud uz nejsou zadne planety k doruceni

			if (index < 0) {
				if (cas.getDen() != planety.get(Integer.parseInt(pom.getObC().getP().get(index + 1).getNazev()))
						.getDatum()) {

					if (pom.getUrazeno() - 25 < 0) {

						pom.setUrazeno(0);

						seznamLodiVolne.add(pom);
						seznamLodiZabrane.remove(i);

					} else {

						pom.setUrazeno(pocetUrazenychMil - rychlostLodi);
						pom.setPlaneta(planety.get(Integer.parseInt(pom.getObC().getP().get(index + 1).getCentrala())));
					}
				}
			} else {

				int index2 = Integer.parseInt(pom.getObC().getP().get(index).getNazev());

				if (planety.get(index2).getUseky().get(planety.get(index2).getIndex()).znicNaklad()) {
					pom = znic(index2, pom, cas, index);

					seznamLodiVolne.add(pom);
					seznamLodiZabrane.remove(i);

				} else {

					if (zjistiVzdalenostDoCile(pom.getUrazeno(),
							pom.getObC().getP().get(index).getVzdalenostOdcentraly()) <= 25) {

						pom = mensiVzdalenost(indexDodavky, index, index2, cas, pom, pocetUrazenychMil);

					} else {

						planety.get(index2).setLod(pom);
						planety.get(index2).setVzdalenostOdPlanety(pom.getUrazeno());

						pom.setUrazeno(pocetUrazenychMil + rychlostLodi);
						pom.setPlaneta(planety.get(index2));
					}

					seznamLodiZabrane.add(i, pom);
					seznamLodiZabrane.remove(i);
				}

			}

		}
	}

	/**
	 * Metoda pro praci s lodi pri napadeni piraty
	 * 
	 * @param index2
	 *            umisteni v Arraylistu planety
	 * @param pom
	 *            lod se kterou pracujeme
	 * @param c
	 *            intstance tridy cas pro rizeni simulace
	 * @param index
	 *            index pro zjisteni ktera planeta je na rade s
	 * @return pom Vraci upravenou lod
	 */
	public Lod znic(int index2, Lod pom, Cas c, int index) {

		planety.get(index2).getUseky().get(planety.get(index2).getIndex()).setPrulet(1);
		planety.get(index2).upravPopulaci(pom.getObC().getP().get(index).getObjednavka().getPocetLeku());
		planety.get(index2).setIndex(planety.get(index2).getIndex() - 1);
		planety.get(index2).setObsluhovan(false);
		planety.get(index2).getStatistika().add(new StatistikaPlaneta(0, pom, c.getMesic()));
		// planety.get(index2).getStatistika().get((c.getMesic()-1)).zvysPocet();
		pom.setPrepadeni(pom.getPrepadeni() + 1);
		pom.setStav(false);

		return pom;
	}

	/**
	 * Metoda pro praci s lodi pokud se blizi k planete Snizi rychlost lodi
	 * pokud je to potreba Nastavi vykladani zbozi a z brzdi lod
	 * 
	 * @param indexDodavky
	 *            pocet leku k doruceni
	 * @param index
	 *            planety v systemu dodavek
	 * @param index2
	 *            poloha planety v arraylistu planety
	 * @param c
	 *            instance tridy cas pro rizeni simulace
	 * @param pom
	 *            Lod na ktere se budou provadet upravy
	 * @param pocetUrazenychMil
	 *            vzdalenost kolik uz toho lod urazila
	 * @return Upravenou lod
	 */
	public Lod mensiVzdalenost(int indexDodavky, int index, int index2, Cas c, Lod pom, int pocetUrazenychMil) {
		int datum = planety.get(Integer.parseInt(pom.getObC().getP().get(index).getNazev())).getDatum();
		int leky = 0;
		if (c.getDen() == (datum + 1)) {

			leky = pom.getObC().getP().get(index).getObjednavka().getPocetLeku();
			planety.get(index2).setDatum(0);
			planety.get(index2).setObsluhovan(false);
			planety.get(index2).setVzdalenostOdPlanety(pom.getUrazeno());
			planety.get(index2).upravPopulaciLekyDorazily(leky);
			planety.get(index2).getStatistika().add(new StatistikaPlaneta(leky, pom, c.getMesic()));
			planety.get(index2).getStatistika().get(planety.get(index2).getIndexStatistika()).zvysPocet();
			planety.get(index2).setIndexStatistika(planety.get(index2).getIndexStatistika() + 1);
			pom.getObC().setIndexUseky(index - 1);
			pom.getObC().setIndexDodavky(indexDodavky - 1);

		} else {
			nastavDatumAVykladku(pom, index, pocetUrazenychMil, index2, c.getDen());

			planety.get(index2).setVzdalenostOdPlanety(pom.getUrazeno());

		}

		return pom;
	}

	/**
	 * 
	 * Nastavi datum vykladky nakladu pro urcenou planetu
	 * 
	 * @param pom
	 *            Lod ktera veze na planedu leky
	 * @param j
	 * @param pocetUrazenychMil
	 *            vzdalenost kterou lod urazila od startu
	 * @param index
	 * @param den
	 *            nastavi datum vykladu pro dalsi praci s nakladem
	 */
	public void nastavDatumAVykladku(Lod pom, int j, int pocetUrazenychMil, int index, int den) {
		pom.setUrazeno(pocetUrazenychMil
				+ zjistiVzdalenostDoCile(pom.getUrazeno(), pom.getObC().getP().get(j).getVzdalenostOdcentraly()));
		planety.get(index).setVykladan(true);
		planety.get(index).setDatum(den);

	}

	/**
	 * Zjisti Vzdalenost do cile z aktualni pozice
	 * 
	 * @param urazeno
	 *            Vzdalenost kterou lod urazila
	 * @param vzdalenostOdCentraly
	 *            vzdalenost kterou ma urazit
	 * @return vysledna vzdalenost
	 */
	public int zjistiVzdalenostDoCile(int urazeno, int vzdalenostOdCentraly) {

		return vzdalenostOdCentraly - urazeno;
	}

	/**
	 * Vytvori jednotlive dodavky pro planety a vypravy lode
	 */
	public void vypravLode() {
		for (int i = 0; i < planetyPodleVzdalenosti.size(); i++) {
			sectiObjednavky(planetyPodleVzdalenosti.get(i));
			planetyPodleVzdalenosti.get(i).setIndex(planetyPodleVzdalenosti.get(i).getUseky().size() - 1);

		}
	
		for (int i = 0; i < seznamLodiZabrane.size(); i++) {
			seznamLodiVolne.add(seznamLodiZabrane.get(i));
		}
		seznamLodiZabrane.clear();
		
		for (int i = 0; i < cestaO.size(); i++) {

			cestaO.get(i).setIndexUseky(cestaO.get(i).getP().size() - 1);
			cestaO.get(i).setIndexDodavky(cestaO.get(i).getD().size() - 1);
			vypravLod(cestaO.get(i));
		}

		cestaO.clear();

		mesic++;

	}

	/**
	 * Vytvori obchodni cestu s dodavkami leku na jednotlive leky
	 * 
	 * @param planeta
	 *            Startovni planeta pro ze ktere se zacinaji pocitat dodavky
	 */

	public void sectiObjednavky(Planeta planeta) {

		int celkem = 0;
		int pomIndex = 1;
		int index = 0;
		int lastIndex2 = 0;

		ArrayList<Dodavka> dodavky = new ArrayList<Dodavka>();
		ArrayList<Planeta> pomPlanety = new ArrayList<Planeta>();

		if (Integer.parseInt(planeta.getNazev()) > 5) {

			pomIndex = planeta.getCesta().getPlanety().size() - 1;

			while (celkem < maxNaklad) {

				if (planeta.isObsluhovan() != true) {

					if (planeta.getObjednavka().getPocetLeku() > maxNaklad) {
						celkem += maxNaklad - 1;
						planeta.setObsluhovan(true);
						dodavky.add(new Dodavka(planeta.getObjednavka().getPocetLeku(), planeta));

					} else {

						celkem += planeta.getObjednavka().getPocetLeku();
						planeta.setObsluhovan(true);
						dodavky.add(new Dodavka(planeta.getObjednavka().getPocetLeku(), planeta));

					}
				}

				pomIndex -= 1;
				if (pomIndex < 0) {
					break;
				}

				index = planeta.getCesta().getPlanety().get(pomIndex);

				if (jeVolna(planeta.getCesta(), pomIndex)) {

					if (planety.get(index).isObsluhovan() != true) {
						int objednavka = planety.get(index).getObjednavka().getPocetLeku();

						celkem += objednavka;

						planety.get(index).setObsluhovan(true);
						pomPlanety.add(planety.get(index));
						dodavky.add(new Dodavka(objednavka, planety.get(index)));
						lastIndex2 = index;

					}
				} else {
					break;
				}

			}

			pomPlanety.add(0, planeta);

			if (celkem > maxNaklad) {
				int indexPom = dodavky.size() - 1;
				dodavky.get(indexPom).setP(planety.get(lastIndex2));
				dodavky.get(indexPom).setPocetLeku(velikost(lastIndex2, celkem));
				celkem = maxNaklad;
			}
		}
		if (celkem > 0) {

			cestaO.add(new ObchodniCesta(pomPlanety, dodavky, planeta.getCesta(), celkem));

		}
	}

	/**
	 * Upravy naklad by se vesel na lod a nastavi planete zbyvajici pocet leku
	 * 
	 * @param lastIndex2
	 *            umisteni planety v Arraylistu
	 * @param celkem
	 *            akturalni pocet leku na palube lodi
	 * @return velikost poctu leku ktere se museji jeste dodat
	 */
	public int velikost(int lastIndex2, int celkem) {

		int pom = celkem - maxNaklad;
		planety.get(lastIndex2).setObsluhovan(false);
		planety.get(lastIndex2).getObjednavka().setPocetLeku(pom);

		return pom;

	}

	/**
	 * Zjisti zda je planeta volna
	 * 
	 * @param c
	 *            Cesta z centraly do Planety
	 * @param index
	 * @return true pokud je planeta volna
	 */
	public boolean jeVolna(Cesta c, int index) {

		if (!planety.get(c.getPlanety().get(index)).isObsluhovan()
				&& !planety.get(c.getPlanety().get(index)).isNizkyPocet()) {
			return true;
		}

		return false;
	}

	/**
	 * Pozada o vytvoreni lodi pokud neni k dispozici nebo poskytne lod pro
	 * dodani leku
	 */

	public void vypravLod(ObchodniCesta c) {

		Lod lod;
		int index = 0;
		if (seznamLodiVolne.isEmpty()) {
			vytvorLod();
		}

		index = seznamLodiVolne.size() - 1;
		lod = seznamLodiVolne.get(index);
		lod.setObC(c);
		lod.zjistiDobuCesty();

		if (lod.getDobaLetu() > 25) {
			zrusObjednavku(lod.getObC().getP(), lod);
		}else {
			
			lod.getStatistika().getCesta().add(c);
			lod.getStatistika().getPocetLeku()[lod.getIndex()] = c.getPocetLekuNaCestu();
			lod.setIndex(mesic);
			
			seznamLodiZabrane.add(lod);
			// System.out.println(seznamLodiVolne.size());
			seznamLodiVolne.remove(index);
			// System.out.println(seznamLodiVolne.size());
		}
		
	}

	/**
	 * Zrusi objednavku pokud je planeta mimo dosah lodi za pul mesice
	 * @param zmenPlanety planeta ktere se zmena tyka
	 */
	public void zrusObjednavku(ArrayList<Planeta> zmenPlanety, Lod pom) {
		for (int i = 0; i < zmenPlanety.size(); i++) {
			int index = Integer.parseInt(zmenPlanety.get(i).getNazev());
			planety.get(index).upravPopulaci(planety.get(index).getObjednavka().getPocetLeku());
			planety.get(index).setObsluhovan(false);
			planety.get(index).getStatistika().add(new StatistikaPlaneta(0, pom, 3));
			
			
		}

	}

	/**
	 * Vytvori Lod novou lod s patricnym nazvem
	 */
	public void vytvorLod() {
		seznamLodiVolne.add(new Lod("X-" + pomNazev));
		pomNazev++;

	}

	/**
	 * Vytvori jednotlive useky mezi planetami pro praci s cestami
	 */
	public void vytvorUseky() {
		zjistiCelkovyPocetCest();
		ArrayList<Usek> useky;
		for (int i = 0; i < getGraf().getMatice().length; i++) {
			useky = new ArrayList<Usek>();
			for (int j = 0; j < getGraf().getMatice().length; j++) {
				if (getGraf().getMatice()[i][j] != 0) {

					useky.add(new Usek(i, j, getGraf().getMatice()[i][j], nebezpecnaCesta()));

				}

			}
			planety.get(i).setUseky(useky);
		}

	}

	/**
	 * Projde graf Dijkstrou a zjisti nejkradsi cesty do planet
	 * 
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
	 * pomocna metoda pro cesty
	 * 
	 * @param s
	 * @param p
	 */
	public void cestaPresPlanety(int s, ArrayList<Planeta> p) {

		ArrayList<Usek> pomUseky;
		ArrayList<Integer> pomPlanety;
		d.dijkstra(s);
		for (int i = 0; i < p.size(); i++) {
			pomPlanety = d.printPath(s, Integer.parseInt(p.get(i).getNazev()));
			pomUseky = najdiUseky(pomPlanety);

			cesta.add(new Cesta(pomUseky, pomPlanety));
			p.get(i).setCesta(cesta.get(cesta.size() - 1));
		}
	}

	/**
	 * Najde potrebne useky pro vytvoreni cesty
	 * 
	 * @param pomPlanety
	 *            Planety pres ktere se poleti
	 * @return Arraylist useku vraci vysledou cestu vytvorenou useky
	 */
	public ArrayList<Usek> najdiUseky(ArrayList<Integer> pomPlanety) {
		ArrayList<Usek> vyslednaCesta = new ArrayList<Usek>();
		for (int i = 0; i < pomPlanety.size(); i++) {
			if ((i + 1) < pomPlanety.size()) {

				vyslednaCesta.add(najdiUsek(pomPlanety.get(i), pomPlanety.get(i + 1)));
			}
		}

		return vyslednaCesta;
	}

	/**
	 * Najde spravny usek z jedne planety do druhe
	 * 
	 * @param planeta
	 *            Pocatecni planeta
	 * @param planetaCil
	 *            cilova planeta
	 * @return Usek vraci prislusny usek
	 */
	public Usek najdiUsek(int planeta, int planetaCil) {

		for (int i = 0; i < planety.get(planeta).getUseky().size(); i++) {
			if (planety.get(planeta).getUseky().get(i).getCil() == planetaCil) {

				planety.get(planeta).getUseky().get(i)
						.setPocetPruletu(planety.get(planeta).getUseky().get(i).getPocetPruletu() + 1);
				return planety.get(planeta).getUseky().get(i);
			}

		}
		return null;
	}

	/**
	 * Najde cesty do planet z jednotlivych central
	 */
	public void najdiCesty() {

		cestaPresPlanety(0, planetyCentrala0);
		cestaPresPlanety(1, planetyCentrala1);
		cestaPresPlanety(2, planetyCentrala2);
		cestaPresPlanety(3, planetyCentrala3);
		cestaPresPlanety(4, planetyCentrala4);
	}

	/**
	 * Rozhodne zda se bude jednat o nebezpecnou cestu
	 * 
	 * @return true pokud bude cesta oznacena jako nebezpecna
	 */
	public boolean nebezpecnaCesta() {

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

		for (int i = 0; i < 5000; i++) {
			for (int j = 0; j < 5000; j++) {
				if (getGraf().getMatice()[i][j] != 0) {
					pocet++;
				}
			}
		}
		setPocetCestCelkem(pocet);
	}

	/**
	 * Prijme objednavky na dany mesic
	 */
	public void prijmiObjednavky() {

		for (int i = 0; i < planety.size(); i++) {
			if (planety.get(i).getPocetObyvatel() > 40000 || Integer.parseInt(planety.get(i).getNazev()) < 5) {

				objednavky.add(planety.get(i).vytrorObjednavku());
			} else {

			}
		}

	}

	/**
	 * Vytvori potrebne prostredi pro putovani vesmirem
	 */
	public void vytvorTrasy() {
		vytvorUseky();
		najdiVzdalenostiodCentaly();
		najdiCesty();

	}

	/*********************************************
	 * Getry a Setry
	 ****************/

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

	/**
	 * @return the cestaO
	 */
	public ArrayList<ObchodniCesta> getCestaO() {
		return cestaO;
	}

	/**
	 * @param cestaO
	 *            the cestaO to set
	 */
	public void setCestaO(ArrayList<ObchodniCesta> cestaO) {
		this.cestaO = cestaO;
	}

	/**
	 * @return the seznamLodiVolne
	 */
	public ArrayList<Lod> getSeznamLodiVolne() {
		return seznamLodiVolne;
	}

	/**
	 * @param seznamLodiVolne
	 *            the seznamLodiVolne to set
	 */
	public void setSeznamLodiVolne(ArrayList<Lod> seznamLodiVolne) {
		this.seznamLodiVolne = seznamLodiVolne;
	}

	/**
	 * @return the seznamLodiZabrane
	 */
	public ArrayList<Lod> getSeznamLodiZabrane() {
		return seznamLodiZabrane;
	}

	/**
	 * @param seznamLodiZabrane
	 *            the seznamLodiZabrane to set
	 */
	public void setSeznamLodiZabrane(ArrayList<Lod> seznamLodiZabrane) {
		this.seznamLodiZabrane = seznamLodiZabrane;
	}

	/**
	 * @return the planetyPodleVzdalenosti
	 */
	public ArrayList<Planeta> getPlanetyPodleVzdalenosti() {
		return planetyPodleVzdalenosti;
	}

	/**
	 * @param planetyPodleVzdalenosti
	 *            the planetyPodleVzdalenosti to set
	 */
	public void setPlanetyPodleVzdalenosti(ArrayList<Planeta> planetyPodleVzdalenosti) {
		this.planetyPodleVzdalenosti = planetyPodleVzdalenosti;
	}

}
