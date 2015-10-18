import java.util.ArrayList;

public class Graf {

	private ArrayList<Planeta> vrcholy;
	private int matice[][];
	private int velikost;
	private int maticeVzdalenosti[][];

	public Graf(ArrayList<Planeta> vrcholy) {
		
		setVrcholy(vrcholy);
		setVelikost(vrcholy.size());
		setMatice(new int[velikost][velikost]);
		
		najdiVzdalenosti();
			
		najdiSousedy();		
		
		SaveLoad sl = new SaveLoad();
		sl.ulozGraf(matice, "MaticeGrafu.txt");
		/*
		System.out.println(vrcholy.get(4999).getPartneri().get(0).getNazev());
		System.out.println(vrcholy.get(4999).getPartneri().get(1).getNazev());
		System.out.println(vrcholy.get(4999).getPartneri().get(2).getNazev());
		System.out.println(vrcholy.get(4999).getPartneri().get(3).getNazev());
		System.out.println(vrcholy.get(4999).getPartneri().get(4).getNazev());
		
		vypisMatici(maticeVzdalenosti);
		System.out.println();
		vypisMatici(matice);	  
	 */
	}
	
	public void najdiVzdalenosti(){
		maticeVzdalenosti = new int[velikost][velikost];
		
		for (int i = 0; i < vrcholy.size(); i++) {
			for (int j = 0; j < vrcholy.size(); j++) {
				maticeVzdalenosti[i][j] = (int)(vrcholy.get(i).getPoloha().distance(vrcholy.get(j).getPoloha()));
				
			}
		}
	}

	public void najdiSousedy(){

		ArrayList<Planeta> pom = new ArrayList<Planeta>();
		
		int pomVzdalenost = -1;
		int vzdalenost = 1000;
		int pomIndexX = -1;
		int pomIndexY = -1;
		int z = 0;
		
		for (int i = 0; i < velikost; i++) {
			
		while (z != 5) {
			
				for (int j = 0; j < maticeVzdalenosti.length; j++) {
					pomVzdalenost = maticeVzdalenosti[i][j]; 
						if (pomVzdalenost < vzdalenost && pomVzdalenost > 0) {
							vzdalenost = pomVzdalenost;
							pomIndexX = i;
							pomIndexY = j;
						}
						
				}
				maticeVzdalenosti[pomIndexX][pomIndexY] = 1000;
				pom.add(vrcholy.get(pomIndexY));
				vlozHranu(vzdalenost, i, pomIndexY);
				vrcholy.get(i).setPartneri(pom);
				vzdalenost = 1000;
				z++;
			}
		z = 0; 
		}
	}
	
	
	void vlozHranu(int hodnota, int indexX, int indexY) {

						matice[indexX][indexY] = hodnota;
						matice[indexY][indexY] = hodnota;
	}
	
	

	public ArrayList<Planeta> getVrcholy() {
		return vrcholy;
	}

	public void setVrcholy(ArrayList<Planeta> vrcholy) {
		this.vrcholy = vrcholy;
	}

	public int[][] getMatice() {
		return matice;
	}

	public void setMatice(int[][] matice) {
		this.matice = matice;
	}

	public int getVelikost() {
		return velikost;
	}

	public void setVelikost(int velikost) {
		this.velikost = velikost;
	}

	public int[][] getMaticeVzdalenosti() {
		return maticeVzdalenosti;
	}

	public void setMaticeVzdalenosti(int maticeVzdalenosti[][]) {
		this.maticeVzdalenosti = maticeVzdalenosti;
	}
}
