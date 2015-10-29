import java.awt.Point;

public class Objednavka {

	private int pocetLeku;
	private Point pozice;
	private String idPlanety;
	
	public Objednavka(int pocetLeku){ //, Point pozice, String jmeno) {
		setPocetLeku(pocetLeku);
		//setPozice(pozice);
		//setIdPlanety(jmeno);
	}



	/**
	 * @return the pozice
	 */
	public Point getPozice() {
		return pozice;
	}

	/**
	 * @param pozice the pozice to set
	 */
	public void setPozice(Point pozice) {
		this.pozice = pozice;
	}



	/**
	 * @return the pocetLeku
	 */
	public int getPocetLeku() {
		return pocetLeku;
	}



	/**
	 * @param pocetLeku the pocetLeku to set
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
	 * @param idPlanety the idPlanety to set
	 */
	public void setIdPlanety(String idPlanety) {
		this.idPlanety = idPlanety;
	}
}
