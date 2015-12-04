
public class Usek {
	
	private int pocatek;
	private int cil;
	private int vzdalenost;
	private boolean nebezpecny;
	private int pocetPruletu;
	private int prulet = 1;
	
	public Usek(int pocatek, int cil, int vzdalenost, boolean nebezpecny) {
		this.setPocatek(pocatek);
		this.setCil(cil);
		this.setVzdalenost(vzdalenost);
		this.setPocatek(pocatek);
	}
	
	
	public boolean znicNaklad(){
		
		if ((pocetPruletu/prulet)%(pocetPruletu*0.1) != 0) {
			
			return false;
		}else {
			prulet = 0;
			return true;
		}
		
		
	}
	/************************************* Getry a Setry ***********************************/
	
	
	
	
	/**
	 * @return the pocatek
	 */
	public int getPocatek() {
		return pocatek;
	}

	/**
	 * @return the prulet
	 */
	public int getPrulet() {
		return prulet;
	}


	/**
	 * @param prulet the prulet to set
	 */
	public void setPrulet(int prulet) {
		this.prulet = prulet;
	}


	/**
	 * @param pocatek the pocatek to set
	 */
	public void setPocatek(int pocatek) {
		this.pocatek = pocatek;
	}

	/**
	 * @return the cil
	 */
	public int getCil() {
		return cil;
	}

	/**
	 * @param cil the cil to set
	 */
	public void setCil(int cil) {
		this.cil = cil;
	}

	/**
	 * @return the vzdalenost
	 */
	public int getVzdalenost() {
		return vzdalenost;
	}

	/**
	 * @param vzdalenost the vzdalenost to set
	 */
	public void setVzdalenost(int vzdalenost) {
		this.vzdalenost = vzdalenost;
	}

	/**
	 * @return the nebezpecny
	 */
	public boolean isNebezpecny() {
		return nebezpecny;
	}

	/**
	 * @param nebezpecny the nebezpecny to set
	 */
	public void setNebezpecny(boolean nebezpecny) {
		this.nebezpecny = nebezpecny;
	}

	

	/**
	 * @return the pocetPruletu
	 */
	public int getPocetPruletu() {
		return pocetPruletu;
	}

	/**
	 * @param pocetPruletu the pocetPruletu to set
	 */
	public void setPocetPruletu(int pocetPruletu) {
		this.pocetPruletu = pocetPruletu;
	}

}

