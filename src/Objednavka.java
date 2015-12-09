
public class Objednavka {

	/***
	 * @author Václav Janoch
	 * @author Filip Kupilik
	 * 
	 */

	/** Promenne potrebne pro praci */
	private int pocetLeku;
	private String centrala;
	private String idPlanety;
	private int dobaLetu;

	/**
	 * Konstruktor tridy nastavi pocet leku kolik je potreba, centralu od kud se
	 * leky povezou jmeno planety dobu letu do planety
	 * 
	 * @param pocetLeku
	 *            potrebny pocet leku pro planetu
	 * @param centrala
	 *            centrala planety
	 * @param jmeno
	 *            jmeno planety
	 * @param dobaLetu
	 *            doba letu do planety
	 */
	public Objednavka(int pocetLeku, String centrala, String jmeno, int dobaLetu) {
		setPocetLeku(pocetLeku);
		setCentrala(centrala);
		setIdPlanety(jmeno);
		setDobaLetu(dobaLetu);
	}

	/******************* Getry a Setry ******************/
	/**
	 * @return the pocetLeku
	 */
	public int getPocetLeku() {
		return pocetLeku;
	}

	/**
	 * @param pocetLeku
	 *            the pocetLeku to set
	 */
	public void setPocetLeku(int pocetLeku) {
		this.pocetLeku = pocetLeku;
	}

	/**
	 * @return the idPlanety
	 */
	public String getIdPlanety() {
		return idPlanety;
	}

	/**
	 * @param idPlanety
	 *            the idPlanety to set
	 */
	public void setIdPlanety(String idPlanety) {
		this.idPlanety = idPlanety;
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
	public int getDobaLetu() {
		return dobaLetu;
	}

	/**
	 * @param dobaLetu
	 *            the dobaLetu to set
	 */
	public void setDobaLetu(int dobaLetu) {
		this.dobaLetu = dobaLetu;
	}
}
