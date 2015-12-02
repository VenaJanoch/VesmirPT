import java.awt.Point;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

public class Planeta implements Comparable<Planeta>{

	private String nazev;
	private int pocetObyvatel;
	private Point poloha;
	
	private ArrayList<Planeta> partneri;
	private ArrayList<Usek> useky;
	private String centrala;
	private double pocetBaleni = 0;
	private int vzdalenostOdcentraly;
	private double dobaLetu;
	private boolean nizkyPocet = false;
	
	private Objednavka objednavka;
	private Cesta p; 
	private boolean obsluhovan;
	private boolean vykladan;
	private boolean rucne;
	private int datum;
	private int vzdalenostOdPlanety;
	private Random r = new Random();
	private int procenta;
	
	private int index;

	private Lod lod;

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
		
		return jePopulace();
	}
	
	public void upravPopulaci(int pocetLekuProPlanetu){
		
				
		//	System.out.println(getPocetObyvatel()- pocetLekuProPlanetu);
			setPocetObyvatel(getPocetObyvatel() -(getPocetObyvatel()- pocetLekuProPlanetu));
			
		
	}
	
public void upravPopulaciLekyDorazily(int pocetLekuProPlanetu){
	double pom = (double) (getPocetObyvatel())
	* (double) ((double) (100 - procenta) / 100.0);
	
	
		if (( getPocetObyvatel()- (pom + pocetLekuProPlanetu)) >5) {
		
			setPocetObyvatel(getPocetObyvatel() -(getPocetObyvatel()- pocetLekuProPlanetu));
		}
		
	}
	
	public String jePopulace(){
		if (getPocetObyvatel()< 400000) {
			setNizkyPocet(true);
			return "Planeta " + nazev + " je pod normou obyvatel a je ignorovana";
		}
		String popis = "Planeta " + nazev + " pocet obyvatel "+ pocetObyvatel + " je zasobovana z " + getCentrala() + " centraly, poctem leku "
				+ objednavka.getPocetLeku() + " za " + spoctiCasLetu() + " dnu";

		return popis;
	}
	/**
	 * vytvori svoji objednavku 
	 * @return pocet leku
	 */
	
	public Objednavka vytrorObjednavku(){

		if (isRucne()) {
			vlastniVyroba();
			Objednavka o = new Objednavka((int)pocetBaleni,getCentrala(),getNazev(),(int)dobaLetu);
			setObjednavka(o);
			return o;
		}else{
			
			pocetBaleni = (double) (getPocetObyvatel())
					* (double) ((double) (100 - vlastniVyroba()) / 100.0);
			Objednavka o = new Objednavka((int)pocetBaleni,getCentrala(),getNazev(),(int)dobaLetu);
			setObjednavka(o);
			return o;
		}
	}
	/**
	 * urci pocet potrebny pocet procent vlastni potreby
	 * @return procenta
	 */
	public int vlastniVyroba() {
		
		procenta = r.nextInt(60) + 20;
		return procenta;

	}
	
	/**
	 * 
	 * @param vzdalenostOdcentraly
	 * @return
	 */
	public double spoctiCasLetu() {
		double doba = (vzdalenostOdcentraly-getVzdalenostOdPlanety()) / 25.0;
		dobaLetu = doba;
		return doba;
	}

	/****************************************** Getry a Setry *******************************/
	
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
	 * @param rucne the rucne to set
	 */
	public void setRucne(boolean rucne) {
		this.rucne = rucne;
	}


	/**
	 * @return the pocetBaleni
	 */
	public double getPocetBaleni() {
		return pocetBaleni;
	}


	/**
	 * @param pocetBaleni the pocetBaleni to set
	 */
	public void setPocetBaleni(double pocetBaleni) {
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
	public Cesta getCesta() {
		return p;
	}


	/**
	 * @param cesta the p to set
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
	 * @param obsluhovan the obsluhovan to set
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
	 * @param vykladan the vykladan to set
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
	 * @param datum the datum to set
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
	 * @param vzdalenostOdPlanety the vzdalenostOdPlanety to set
	 */
	public void setVzdalenostOdPlanety(int vzdalenostOdPlanety) {
		this.vzdalenostOdPlanety = vzdalenostOdPlanety;
	}


	/**
	 * @return the useky
	 */
	public ArrayList<Usek> getUseky() {
		return useky;
	}


	/**
	 * @param useky the useky to set
	 */
	public void setUseky(ArrayList<Usek> useky) {
		this.useky = useky;
	}


	/**
	 * @return the index
	 */
	public int getIndex() {
		return index;
	}


	/**
	 * @param index the index to set
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
	 * @param nizkyPocet the nizkyPocet to set
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
	 * @param lod the lod to set
	 */
	public void setLod(Lod lod) {
		this.lod = lod;
	}



	

}
