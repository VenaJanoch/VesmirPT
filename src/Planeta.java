import java.awt.Point;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Planeta implements Comparable<Planeta> {

	/***
	 * @author Václav Janoch
	 * @author Filip Kupilik
	 * 
	 */

	/** Promenne pro uschovani pocatecnich informaci o planete */
	private String nazev;
	private int pocetObyvatel;
	private Point poloha;
	private List<Planeta> partneri;
	private List<Usek> useky;
	private String centrala;

	/** Promenne potrebne pro objednavku */
	private int pocetBaleni = 0;
	private int vzdalenostOdcentraly;
	private double dobaLetu;
	private boolean nizkyPocet = false;
	private int procenta;
	private Objednavka objednavka;

	/** Promenne potrebne pro statistiku */
	private List<StatistikaPlaneta> statistika;
	private StatistikaPlaneta s;
	private Umrti umrti;
	private boolean obsluhovan;
	private boolean vykladan;
	private boolean rucne;
	private int datum;
	private int vzdalenostOdPlanety;

	/** Promenne potrebne pro praci */
	private int index;
	private int indexStatistika;
	private Cesta p;
	private Lod lod;

	/** Instance trid */
	private Random r;
	private PrintWriter pw;

	/**
	 * Konstruktor tridy Nastavi nazev Planety pocatecny pocet obyvatel a jeji
	 * polohu
	 * 
	 * @param nazev
	 *            Nazev planety
	 * @param pocetObyvatel
	 *            pocatecny pocet obyvatel
	 * @param poloha
	 *            poloha planety ve vesmiru
	 */

	public Planeta(String nazev, int pocetObyvatel, Point poloha) {

		setNazev(nazev);
		setPocetObyvatel(pocetObyvatel);
		setPoloha(poloha);
		s = new StatistikaPlaneta();
		this.setStatistika(new ArrayList<StatistikaPlaneta>());
		this.setUmrti(new Umrti(getNazev(), getPocetObyvatel()));

		r = new Random();
	}

	/**
	 * Prepsana metoda compareTo pro porovnani jednotlivych planet podle
	 * vzdalenosti od centraly
	 */
	@Override
	public int compareTo(Planeta p) {
		int vzdalenost = ((Planeta) p).vzdalenostOdcentraly;
		return vzdalenost - this.vzdalenostOdcentraly;

	}

	/**
	 * Prepsana metoda toString pro vypis tridy
	 */
	@Override
	public String toString() {

		return jePopulace();
	}

	/**
	 * Upravy populaci na planete pokud nedorazila lod s leky
	 * 
	 * @param pocetLekuProPlanetu
	 *            pocet leku ktere byli na palube lodi
	 */
	public void upravPopulaci(int pocetLekuProPlanetu) {

		setPocetObyvatel(domaciProdukce());

	}

	/**
	 * Metoda pro urceni leku ktere si planeta vyrobi sama
	 * 
	 * @return int velikost vlastni vyroby
	 */
	public int domaciProdukce() {

		double pom = ((double) (getPocetObyvatel()) * (double) ((double) (procenta) / 100.0));
		return (int) pom;
	}

	/**
	 * Metoda pro urceni kolik leku je potreba dovest od spolecnosti
	 * 
	 * @return int potrebny pocet leku
	 */
	public int svetovaProdukce() {
		double pomSvetova = ((double) (getPocetObyvatel()) * (double) ((double) (100 - procenta) / 100.0));

		return (int) pomSvetova;
	}

	/**
	 * Upravy populaci pokud lod dorazila, ale nebyl na ni dostatek leku pro
	 * celou planetu
	 * 
	 * @param pocetLekuProPlanetu
	 */
	public void upravPopulaciLekyDorazily(int pocetLekuProPlanetu) {

		if ((domaciProdukce() + pocetLekuProPlanetu) != (domaciProdukce() + svetovaProdukce())) {

			setPocetObyvatel(domaciProdukce() + pocetLekuProPlanetu);
		}


	}

	/**
	 * Pomocna metoda pro vypis tridy pokud ma planeta dostatek obyvatel
	 * vypisuje se jeji aktualni stav pokud ne tak se planeta zacne ignorovat
	 * 
	 * @return popis planety
	 */
	public String jePopulace() {
		
		if (getPocetObyvatel() < 40000) {
			setNizkyPocet(true);
			return "Planeta " + nazev + " je pod normou obyvatel a je ignorovana";
		}
		String popis = "Planeta " + nazev + " pocet obyvatel " + pocetObyvatel + " je zasobovana z " + getCentrala()
				+ " centraly, poctem leku " + objednavka.getPocetLeku() + " za " + spoctiCasLetu();

		return popis;
	}

	/**
	 * vytvori svoji objednavku
	 * 
	 * @return pocet leku
	 */

	public Objednavka vytrorObjednavku() {
		
		umrti.getPocetObyvatel().add(getPocetObyvatel());
		if (isRucne()) {
			vlastniVyroba();
			Objednavka o = new Objednavka(pocetBaleni, getCentrala(), getNazev(), (int) dobaLetu);
			setObjednavka(o);
			return o;
		} else {
			vlastniVyroba();
			double pom = ((double) (getPocetObyvatel()) * (double) ((double) (100 - procenta) / 100.0));
			pocetBaleni = (int) pom;

			Objednavka o = new Objednavka(pocetBaleni, getCentrala(), getNazev(), (int) dobaLetu);
			setObjednavka(o);
			return o;
		}
	}

	/**
	 * urci pocet potrebny pocet procent vlastni potreby
	 * 
	 * @return procenta
	 */
	public int vlastniVyroba() {

		procenta = r.nextInt(60) + 20;
		return procenta;

	}

	/**
	 * Spocita cisty cas ledu z centraly do planety
	 * 
	 * @param vzdalenostOdcentraly
	 * @return
	 */
	public double spoctiCasLetu() {
		double doba = (vzdalenostOdcentraly - getVzdalenostOdPlanety()) / 25.0;
		dobaLetu = doba;
		return doba;
	}

	/**
	 * Metoda pro vypis statistiky o planete do souboru
	 */
	public void vypisStatistiku() {

		try {
			pw = new PrintWriter(new BufferedWriter(new FileWriter("planety/" + getNazev() + ".txt")));

			pw.println("Planeta: " + getNazev() + " pocatecny pocet obyvatel: " + umrti.getPocatecnyStavObyvatel()
					+ " zasobovana centralou: " + getCentrala());
			
		
			for (int i = 0; i < statistika.size(); i++) {
				pw.println(statistika.get(i));
			}
			pw.println("Celkovy pocet vyrobenych a prevezenych leku je: " + s.getCelkovyPocetLeku());
		

			vypisUmrti(pw);

			pw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Spatne zapsany vystupni soubor");
			System.exit(1);
		}

	}

	/**
	 * Pomocna metoda pro vypis statistiky vypise vyvoj obyvatel na planete
	 * 
	 * @param pw
	 */
	public void vypisUmrti(PrintWriter pw) {

		pw.println(umrti);
		umrti.prvniCtvrtleti();
		umrti.druheCtvrtleti();
		umrti.tretiCtvrtleti();

	}

	/**
	 * Vypise celkovy souhrn vyvoje obyvatel za simulaci
	 * 
	 * @param planety
	 */
	public void vypisUmrtiCele(List<Planeta> planety) {

		try {
			pw = new PrintWriter(new BufferedWriter(new FileWriter("umrti/umrti.cvs")));
			pw.println("Celkovy pocet umrti za prvni ctyri: " + planety.get(0).getUmrti().getPrvniC());
			pw.println("Celkovy pocet umrti za druhe ctyri: " + planety.get(0).getUmrti().getDruheC());
			pw.println("Celkovy pocet umrti za treti ctyri: " + planety.get(0).getUmrti().getTretiC());
			pw.println("Celkovy pocet umrti za simulaci: " + planety.get(0).getUmrti().getCelkemUmrtnost());
			pw.close();
		} catch (IOException e) {
			System.out.println("Nenalezen soubor umrti.cvs");
			e.printStackTrace();
			System.exit(1);
		}

	}

	/************************ Getry a Setry ********************/

	/**
	 * @return the procenta
	 */
	public int getProcenta() {
		return procenta;
	}

	/**
	 * @param procenta
	 *            the procenta to set
	 */
	public void setProcenta(int procenta) {
		this.procenta = procenta;
	}

	public String getNazev() {
		return nazev;
	}

	/**
	 * @return the rucne
	 */
	public boolean isRucne() {
		return rucne;
	}

	/**
	 * @param rucne
	 *            the rucne to set
	 */
	public void setRucne(boolean rucne) {
		this.rucne = rucne;
	}

	/**
	 * @return the pocetBaleni
	 */
	public int getPocetBaleni() {
		return pocetBaleni;
	}

	/**
	 * @param pocetBaleni
	 *            the pocetBaleni to set
	 */
	public void setPocetBaleni(int pocetBaleni) {
		this.pocetBaleni = pocetBaleni;
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

	public List<Planeta> getPartneri() {
		return partneri;
	}

	public void setPartneri(List<Planeta> partneri) {
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
	public Cesta getCesta() {
		return p;
	}

	/**
	 * @param cesta
	 *            the p to set
	 */
	public void setCesta(Cesta cesta) {
		this.p = cesta;
	}

	/**
	 * @return the obsluhovan
	 */
	public boolean isObsluhovan() {
		return obsluhovan;
	}

	/**
	 * @param obsluhovan
	 *            the obsluhovan to set
	 */
	public void setObsluhovan(boolean obsluhovan) {
		this.obsluhovan = obsluhovan;
	}

	/**
	 * @return the vykladan
	 */
	public boolean isVykladan() {
		return vykladan;
	}

	/**
	 * @param vykladan
	 *            the vykladan to set
	 */
	public void setVykladan(boolean vykladan) {
		this.vykladan = vykladan;
	}

	/**
	 * @return the datum
	 */
	public int getDatum() {
		return datum;
	}

	/**
	 * @param datum
	 *            the datum to set
	 */
	public void setDatum(int datum) {
		this.datum = datum;
	}

	/**
	 * @return the vzdalenostOdPlanety
	 */
	public int getVzdalenostOdPlanety() {
		return vzdalenostOdPlanety;
	}

	/**
	 * @param vzdalenostOdPlanety
	 *            the vzdalenostOdPlanety to set
	 */
	public void setVzdalenostOdPlanety(int vzdalenostOdPlanety) {
		this.vzdalenostOdPlanety = vzdalenostOdPlanety;
	}

	/**
	 * @return the useky
	 */
	public List<Usek> getUseky() {
		return useky;
	}

	/**
	 * @param useky
	 *            the useky to set
	 */
	public void setUseky(List<Usek> useky) {
		this.useky = useky;
	}

	/**
	 * @return the index
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * @param index
	 *            the index to set
	 */
	public void setIndex(int index) {
		this.index = index;
	}

	/**
	 * @return the nizkyPocet
	 */
	public boolean isNizkyPocet() {
		return nizkyPocet;
	}

	/**
	 * @param nizkyPocet
	 *            the nizkyPocet to set
	 */
	public void setNizkyPocet(boolean nizkyPocet) {
		this.nizkyPocet = nizkyPocet;
	}

	/**
	 * @return the lod
	 */
	public Lod getLod() {
		return lod;
	}

	/**
	 * @param lod
	 *            the lod to set
	 */
	public void setLod(Lod lod) {
		this.lod = lod;
	}

	/**
	 * @return the statistika
	 */
	public List<StatistikaPlaneta> getStatistika() {
		return statistika;
	}

	/**
	 * @param statistika
	 *            the statistika to set
	 */
	public void setStatistika(List<StatistikaPlaneta> statistika) {
		this.statistika = statistika;
	}

	/**
	 * @return the indexStatistika
	 */
	public int getIndexStatistika() {
		return indexStatistika;
	}

	/**
	 * @param indexStatistika
	 *            the indexStatistika to set
	 */
	public void setIndexStatistika(int indexStatistika) {
		this.indexStatistika = indexStatistika;
	}

	/**
	 * @return the umrti
	 */
	public Umrti getUmrti() {
		return umrti;
	}

	/**
	 * @param umrti
	 *            the umrti to set
	 */
	public void setUmrti(Umrti umrti) {
		this.umrti = umrti;
	}

	/**
	 * @return the s
	 */
	public StatistikaPlaneta getS() {
		return s;
	}

	/**
	 * @param s the s to set
	 */
	public void setS(StatistikaPlaneta s) {
		this.s = s;
	}

	/**
	 * @return the r
	 */
	public Random getR() {
		return r;
	}

	/**
	 * @param r the r to set
	 */
	public void setR(Random r) {
		this.r = r;
	}
	

}
