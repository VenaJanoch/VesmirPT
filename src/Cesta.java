import java.util.ArrayList;

public class Cesta {
	
	private ArrayList<Integer> planety;
	private int vzdalenos;
	
	
	public Cesta(ArrayList<Integer> planety, int vdalenost) {
	this.planety = planety;
	this.vzdalenos =vdalenost;
	
	
	}
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

}
