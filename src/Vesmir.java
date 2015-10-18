import java.util.Scanner;

public class Vesmir {
	
	private String souborPlanety;
	private String souborMatice;
	
	
	public void nactiData(String args[]) {
		
		setSouborPlanety(args[0]);
		setSouborMatice(args[1]);
	
		
	}
	
	public static void main(String[] args) {
		
		
		Rozdeleni o = new Rozdeleni(5000, 3000000, 0.2,
													100000, 10000000, 800,800);
		Graf g = new Graf(o.getPlanety());
		
		
	}

	public String getSouborPlanety() {
		return souborPlanety;
	}

	public void setSouborPlanety(String souborPlanety) {
		this.souborPlanety = souborPlanety;
	}

	public String getSouborMatice() {
		return souborMatice;
	}

	public void setSouborMatice(String souborMatice) {
		this.souborMatice = souborMatice;
	}
}
