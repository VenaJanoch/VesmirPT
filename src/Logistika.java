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
	private Random r = new Random();
	private int[] pomPole;
	private Graf graf;

	
	
	
	
	
	
	public Logistika(ArrayList<Planeta> planety, Graf g) {
	

		this.planety = planety;
		this.objednavky = new ArrayList<Objednavka>();
		this.graf = g;
		
		if (getDen() % 30 == 0) {
			vytvorObjednavky();
		}
		
		
		
	}
	
	
	/**
	 * 
	 * @param start
	 */
	public void naplanujCestu(int start){
	
	}
	
	/**
	 * 
	 */
	public void vypravLod(){
		
	}
	/**
	 * 
	 * @param vzdalenostOdcentraly
	 * @return
	 */
	public double spoctiCasLetu(double vzdalenostOdcentraly){
		return vzdalenostOdcentraly/25.0;
	}
	
	/**
	 * 
	 */
	public void vytvorObjednavky(){
		double pocetBaleni = 0;
		
		for (int i = 5; i < planety.size(); i++) {
			pocetBaleni = (double)(planety.get(i).getPocetObyvatel()) *(double)((double)(100-vlastniVyroba())/100.0);
			System.out.println(pocetBaleni);
			planety.get(i).setObjednavka(new Objednavka((int)pocetBaleni));
			planety.get(i).setDobaLetu(spoctiCasLetu(planety.get(i).getVzdalenostOdcentraly()));//(new Objednavka((int)pocetBaleni, planety.get(i).getPoloha(),planety.get(i).getNazev() ));
		}
		
		
	}
	
	
	
	public int vlastniVyroba(){
		int procenta = r.nextInt(60) + 20;
		return procenta;
		
	}
	

	public void vypisStav(File vystup){
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
