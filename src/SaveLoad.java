import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class SaveLoad {
	
	Rozdeleni o;
	Graf g = new Graf(o.getPlanety());

	public SaveLoad() {
	
		o = new Rozdeleni(5000, 3000000, 0.2,
				100000, 10000000, 800,800);

	}
	
	public void ulozGraf(int matice[][], String nazevSouboru){
	
	PrintWriter pw;
	try {
		pw = new PrintWriter(new BufferedWriter(
				new FileWriter(nazevSouboru)));
	
		for (int i = 0; i < matice.length; i++) {
			for (int j = 0; j < matice.length; j++) {
				pw.print(matice[i][j] + ",");
			}
			pw.println();
		}
		
		pw.close();
	
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		System.out.println("Soubor nebyl nalezen");
		System.exit(1);
	}
	
		
	}
	
	
}
