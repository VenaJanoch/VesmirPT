import java.util.ArrayList;

public class Graf {

	private ArrayList<Planeta> vrcholy;
	private int matice[][];
	private int velikost;

	public Graf(ArrayList<Planeta> vrcholy) {
		setVrcholy(vrcholy);
		setVelikost(vrcholy.size());
		setMatice(new int[velikost][velikost]);
		
		for (int i = 0; i < velikost; i++) {
			
			najdiSousedy(vrcholy.get(i));
			
		}
		
	}

	void najdiSousedy(Planeta p){
	
		int z = 5;
		ArrayList<Planeta> pom = new ArrayList<Planeta>();
		while (pom.size() != 5) {
			
			
			for (int i = 0; i < velikost; i++) {
				
				int vzdalenost = (int)(p.getPoloha().distance(vrcholy.get(i).getPoloha()));
				
				
				if (vzdalenost > 0 && vzdalenost < z && (pom.contains(vrcholy.get(i)) != true) && pom.size() != 5) {
					pom.add((vrcholy.get(i)));
					vlozHranu(p, vrcholy.get(i), vzdalenost);
				}
			
			}
			
			z *=2; 
			
		}
		p.setPartneri(pom);
	}
	
	
	void vlozHranu(Planeta odkud, Planeta kam, int hodnota) {

		for (int i = 0; i < velikost; i++) {
			if (vrcholy.get(i).getNazev().equals(odkud.getNazev())) {

				for (int j = 0; j < velikost; j++) {
					if (vrcholy.get(i).getNazev().equals(odkud.getNazev())) {
						matice[i][j] = hodnota;
						matice[j][i] = hodnota;
					}
				}
			}
		}
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
}
