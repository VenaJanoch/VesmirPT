import java.util.ArrayList;

public class Cesta implements Comparable<Cesta>{

	private ArrayList<Integer> planety;
	private ArrayList<Usek> useky;
	private int vzdalenos;
	private boolean nebezpecna;

	/**
	 * Konstruktor tridy
	 * Ulozi planety pres ktere poleti lod, celkouvou vzdalenost a celkovy pocet leku na trase
	 * 
	 * @param planety
	 * @param vdalenost
	 * @param celkovyPocetLeku
	 */
	public Cesta(ArrayList<Usek> useky, ArrayList<Integer> planety) {
		this.planety = planety;
		this.setUseky(useky);


	}

	@Override
	public int compareTo(Cesta c) {
		int vzdalenost = ((Cesta)c).vzdalenos;
		return vzdalenost - this.vzdalenos;

	}
	/********************Getry a Setry **********************/
		public int getVzdalenos() {
		return vzdalenos;
	}

	public void setVzdalenos(int vzdalenos) {
		this.vzdalenos = vzdalenos;
	}

	public ArrayList<Integer> getPlanety() {
		return planety;
	}

	public void setPlanety(ArrayList<Integer> planety) {
		this.planety = planety;
	}

	
	/**
	 * @return the nebezpecna
	 */
	public boolean isNebezpecna() {
		return nebezpecna;
	}

	/**
	 * @param nebezpecna the nebezpecna to set
	 */
	public void setNebezpecna(boolean nebezpecna) {
		this.nebezpecna = nebezpecna;
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

	

}
