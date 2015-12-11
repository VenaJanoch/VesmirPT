import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Graf {

	/***
	 * @author Václav Janoch 
	 * @author Filip Kupilik
	 * 
	 */
	
	/** Promenne potrebne pro praci */
	private List<Planeta> vrcholy;
	private int matice[][];
	private int velikost;
	private int maticeVzdalenosti[][];

	/**
	 * Konstruktor pro nacteni grafu ze souboru
	 * 
	 * @param souborMatice
	 *            soubor ve kterem je ulozeny graf
	 * @param list
	 *            jednotlive vrcholy
	 */
	public Graf(String souborMatice, List<Planeta> list) {
		setVrcholy(list);
		setVelikost(list.size());
		setMatice(new int[velikost][velikost]);
		maticeVzdalenosti = new int[velikost][velikost];

		nactiGraf(souborMatice);
		projdiGraf(1);
	}

	
	/**
	 * Kontruktor tridy pro vygenerovani grafu do souboru
	 * 
	 * @param list
	 *            jenotlive planety
	 */
	public Graf(List<Planeta> list) {

		setVrcholy(list);
		setVelikost(list.size());
		setMatice(new int[velikost][velikost]);
		maticeVzdalenosti = new int[velikost][velikost];

		projdiGraf(1);
		
	}
	
	/**
	 * Pomocna metoda pro prochazeni grafu 
	 * @param i nahodnecislo
	 */
	public void projdiGraf(int i){
		najdiVzdalenosti();
		najdiSousedy();
	}


	/**
	 * Vytvori matici vzdalenosti kde jsou ulozeny jednotlive vzdalenosti mezi
	 * planetami
	 */
	public void najdiVzdalenosti() {

		for (int i = 0; i < vrcholy.size(); i++) {
			for (int j = 0; j < vrcholy.size(); j++) {
				maticeVzdalenosti[i][j] = (int) (vrcholy.get(i).getPoloha().distance(vrcholy.get(j).getPoloha()));

			}
		}
	}

	/**
	 * Zjisti vzdalenost mezi jednotlivymi dvema planetama
	 * 
	 * @param i
	 *            pocatecni planeta
	 * @param j
	 *            cilova planeta
	 * @return
	 */
	public int delkaHrany(int i, int j) {
		return matice[i][j];
	}

	/**
	 * Najde sousedni vrcholy
	 * 
	 * @param i
	 *            vrchol pro prohledani sousedu
	 * @return pole sousedu
	 */
	public int[] sousedi(int i) {

		int[] sousedi;

		ArrayList<Integer> list = new ArrayList<Integer>();
		for (int j = 0; j < vrcholy.size(); j++) {
			if (matice[i][j] != 0) {
				list.add(j);
			}
		}
		sousedi = new int[list.size()];
		for (int j = 0; j < sousedi.length; j++) {
			sousedi[j] = list.get(j);
		}

		return sousedi;

	}

	/**
	 * Najde pet nejblizsich sousedu pro vytvoreni grafu
	 */
	public void najdiSousedy() {

		int pomVzdalenost = -1;
		int vzdalenost = 1000;
		int pomIndexX = -1;
		int pomIndexY = -1;
		int z = 0;

		for (int i = 0; i < maticeVzdalenosti.length; i++) {
			ArrayList<Planeta> pom = new ArrayList<Planeta>();

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
				vzdalenost = 1000;
				z++;

			}
			vrcholy.get(i).setPartneri(pom);
			z = 0;

		}

	}

	/**
	 * Metoda ktera nacte graf ze souboru
	 * 
	 * @param nazevSouboru
	 *            Nazev souboru kde je graf ulozeny
	 */
	public void nactiGraf(String nazevSouboru) {
		File f = new File(nazevSouboru);
		try {
			Scanner sc = new Scanner(f);

			String pomString;
			String[] parametry;
			int velikost;
			
			while (sc.hasNextLine()) {

				pomString = sc.nextLine();

				velikost = pomString.split(",").length;

				parametry = new String[velikost];

				parametry = pomString.split(",");

				vlozHranu(Integer.valueOf(parametry[0]), Integer.valueOf(parametry[1]), Integer.valueOf(parametry[2]));

			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Soubr " + nazevSouboru + " nebyl nalezen");
		}

	}

	/**
	 * Metoda pro ulozeni grafu do souboru
	 * 
	 * @param nazevSouboru
	 *            Nazev souboru kam se ma graf vygenerovat
	 */
	public void ulozGraf(String nazevSouboru) {

		PrintWriter pw;
		try {
			pw = new PrintWriter(new BufferedWriter(new FileWriter(nazevSouboru)));

			for (int i = 0; i < matice.length; i++) {
				for (int j = 0; j < matice.length; j++) {
					if (matice[i][j] != 0) {
						pw.println(matice[i][j] + "," + i + "," + j);

					}
				}
			}

			pw.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Soubor nebyl nalezen");
			System.exit(1);
		}

	}

	/**
	 * vypise graf do konzole
	 *
	 */
	public void ulozGraf() {
		for (int i = 0; i < matice.length; i++) {
			for (int j = 0; j < matice.length; j++) {
				if (j == velikost) {
					System.out.print(matice[i][j]);

				}
				System.out.print(matice[i][j] + ",");
			}
			System.out.println();
		}

	}

	/**
	 * Vlozi hrany do grafu z jedneho vrcholu do druheho
	 * 
	 * @param hodnota
	 * @param indexX
	 * @param indexY
	 */
	void vlozHranu(int hodnota, int indexX, int indexY) {

		matice[indexX][indexY] = hodnota;
		matice[indexY][indexX] = hodnota;
	}

	/****************** Getry a setry ****************/
	/*
	 * @return
	 */
	public List<Planeta> getVrcholy() {
		return vrcholy;
	}

	/**
	 * @param vrcholy
	 */
	public void setVrcholy(List<Planeta> vrcholy) {
		this.vrcholy = vrcholy;
	}

	/**
	 * 
	 * @return
	 */
	public int[][] getMatice() {
		return matice;
	}

	/**
	 * 
	 * @param matice
	 */
	public void setMatice(int[][] matice) {
		this.matice = matice;
	}

	/**
	 * 
	 * @return
	 */
	public int getVelikost() {
		return velikost;
	}

	/**
	 * 
	 * @param velikost
	 */
	public void setVelikost(int velikost) {
		this.velikost = velikost;
	}

	/**
	 * 
	 * @return
	 */
	public int[][] getMaticeVzdalenosti() {
		return maticeVzdalenosti;
	}

	/**
	 * 
	 * @param maticeVzdalenosti
	 */
	public void setMaticeVzdalenosti(int maticeVzdalenosti[][]) {
		this.maticeVzdalenosti = maticeVzdalenosti;
	}
}
