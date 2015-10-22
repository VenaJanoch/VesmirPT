import java.util.Scanner;

public class Vesmir {

	private static String souborPlaneta;
	private static String souborMatice;
	private static String souborCentrala;
	private static Rozdeleni r;
	private static Graf g;
	
	public static int behProgramu(String args[]) {
		
		
		if (args.length > 12) {
			System.out.println("Zadano prilis parametru pro spusteni");
			System.exit(1);
		}

		if (args.length > 4 && args.length < 12) {
			System.out.println("Zadany spatne parametry pro spusteni");
			System.exit(1);
		}
		
		if (args[0].equals(String.valueOf(1))) {
			return 1;
		} else if (args[0].equals(String.valueOf(2))) {
			return 2;
		} else {
			System.out.println("Spatne zadany parametr pro vstrup/vystup");
			System.exit(1);

		}

		return 0;
	}

	public static void nactiData(String args[]) {

		souborPlaneta = args[1];
		souborMatice = args[3];
		souborCentrala = args[2];
		
		r = new Rozdeleni(souborPlaneta, souborCentrala);
		g = new Graf(souborMatice, r.getPlanety());
		
		

	}
	
	public static void ulozData(String args[]){
		
		r = new Rozdeleni(Integer.parseInt(args[4]), Integer.parseInt(args[5]),
				Double.valueOf(args[6]),Integer.parseInt(args[7]), 
				Integer.valueOf(args[8]), Integer.valueOf(args[9]), Integer.valueOf(args[10]), Integer.valueOf(args[11]));
		
		g = new Graf(r.getPlanety());

		g.ulozGraf(args[3]);
		r.vypisPlanety(args[1]);
		r.vypisCentraly(args[2]);
		
	}

	public static void main(String[] args) {

		
		if (behProgramu(args) == 1) {
			nactiData(args);
		}else {
			ulozData(args);
		}

		
	}

}
