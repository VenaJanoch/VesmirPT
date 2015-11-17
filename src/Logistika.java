import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Logistika {

	private int den = 0;
	private Objednavka obj;
	private ArrayList<Planeta> planety;
	private ArrayList<Objednavka> objednavky;
	private Graf graf;

	public Logistika(ArrayList<Planeta> planety, Graf g) {

		this.planety = planety;
		this.objednavky = new ArrayList<Objednavka>();
		this.graf = g;
		
		if (getDen() % 30 == 0) {
			prijmiObjednavky();
		}
		
		

	}

	/**
	 * 
	 * @param start
	 */
	public void naplanujCestu(int start) {

	}

	/**
	 * 
	 */
	public void vypravLod() {

	}

	

	/**
	 * 
	 */
	public void prijmiObjednavky() {
		
		for (int i = 0; i < planety.size(); i++) {
				objednavky.add(planety.get(i).vytrorObjednavku());
		}

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

}
