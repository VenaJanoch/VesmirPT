import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class UmrtiStatistika {
	
	private PrintWriter pw;
	
	private ArrayList<Umrti> umrti;
	
	public UmrtiStatistika(ArrayList<Umrti> umrti) {
		this.umrti = umrti;
	}
	
	public void vypisUmrti(){
		try {
			pw = new PrintWriter(new BufferedWriter(new FileWriter("planety/umrtiCelkem.txt")));
			
			pw.println("Celkovy pocet umrti za prvni ctyri: " + umrti.get(0).getPrvniC());
			pw.println("Celkovy pocet umrti za druhe ctyri: " + umrti.get(0).getPrvniC());
			pw.println("Celkovy pocet umrti za treti ctyri: " + umrti.get(0).getPrvniC());
			pw.println("Celkovy pocet umrti za simulaci: " + umrti.get(0).getCelkemUmrtnost());
			
			pw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
				
	}
	
	public void vypisUmrtiCele(){
		
		
		try {
			pw = new PrintWriter(new BufferedWriter(new FileWriter("umrti/umrti.cvs")));
		
			pw.println("Celkovy pocet umrti za prvni ctyri: " + umrti.get(0).getPrvniC());
			pw.println("Celkovy pocet umrti za druhe ctyri: " + umrti.get(0).getPrvniC());
			pw.println("Celkovy pocet umrti za treti ctyri: " + umrti.get(0).getPrvniC());
			pw.println("Celkovy pocet umrti za simulaci: " + umrti.get(0).getCelkemUmrtnost());
			
			for (int i = 0; i < umrti.size(); i++) {
					pw.println(umrti.get(i));
					
			}
			
			pw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
	
	/************************ Getry a Setry ************/
	/**
	 * @return the umrti
	 */
	public ArrayList<Umrti> getUmrti() {
		return umrti;
	}
	/**
	 * @param umrti the umrti to set
	 */
	public void setUmrti(ArrayList<Umrti> umrti) {
		this.umrti = umrti;
	}
	/**
	 * @return the prvniC
	 */
	
	

}
