import java.util.ArrayList;

public class Cesta {

	private ArrayList<Integer> planety;
	private int vychozyBod;
	private int cilovyBod;
	private int vzdalenos;
	private int celkovyPocetLeku;
	private boolean nebezpecna;

	/**
	 * Konstruktor tridy
	 * Ulozi planety pres ktere poleti lod, celkouvou vzdalenost a celkovy pocet leku na trase
	 * 
	 * @param planety
	 * @param vdalenost
	 * @param celkovyPocetLeku
	 */
	public Cesta(ArrayList<Integer> planety, boolean nebezpecna) {
		this.planety = planety;
		this.setNebezpecna(nebezpecna);


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
	 * @return the celkovyPocetLeku
	 */
	public int getCelkovyPocetLeku() {
		return celkovyPocetLeku;
	}

	/**
	 * @param celkovyPocetLeku the celkovyPocetLeku to set
	 */
	public void setCelkovyPocetLeku(int celkovyPocetLeku) {
		this.celkovyPocetLeku = celkovyPocetLeku;
	}

	
	/**
	 * @return the cilovyBod
	 */
	public int getCilovyBod() {
		return cilovyBod;
	}

	/**
	 * @param cilovyBod the cilovyBod to set
	 */
	public void setCilovyBod(int cilovyBod) {
		this.cilovyBod = cilovyBod;
	}

	/**
	 * @return the vychozyBod
	 */
	public int getVychozyBod() {
		return vychozyBod;
	}

	/**
	 * @param vychozyBod the vychozyBod to set
	 */
	public void setVychozyBod(int vychozyBod) {
		this.vychozyBod = vychozyBod;
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

}
