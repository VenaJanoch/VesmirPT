import java.util.List;

public class Cesta {
	/***
	 * @author Václav Janoch
	 * @author Filip Kupilik
	 * 
	 */
	/** Promenne potrebne pro praci */
	private List<Integer> planety;
	private List<Usek> useky;
	private int vzdalenos;
	private boolean nebezpecna;

	/**
	 * Konstruktor tridy Ulozi planety pres ktere poleti lod, celkouvou
	 * vzdalenost a celkovy pocet leku na trase
	 * 
	 * @param planety
	 * @param vdalenost
	 * @param celkovyPocetLeku
	 */
	public Cesta(List<Usek> useky, List<Integer> planety) {
		this.planety = planety;
		this.setUseky(useky);

	}

	/******************** Getry a Setry **********************/
	public int getVzdalenos() {
		return vzdalenos;
	}

	public void setVzdalenos(int vzdalenos) {
		this.vzdalenos = vzdalenos;
	}

	public List<Integer> getPlanety() {
		return planety;
	}

	public void setPlanety(List<Integer> planety) {
		this.planety = planety;
	}

	/**
	 * @return the nebezpecna
	 */
	public boolean isNebezpecna() {
		return nebezpecna;
	}

	/**
	 * @param nebezpecna
	 *            the nebezpecna to set
	 */
	public void setNebezpecna(boolean nebezpecna) {
		this.nebezpecna = nebezpecna;
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

}
