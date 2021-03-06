
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Vesmir {

	/***
	 * @author V�clav Janoch
	 * @author Filip Kupilik
	 * 
	 */

	/** Instance trid */
	private Cas c = new Cas();
	private Rozdeleni r;
	private Graf g;
	private Logistika l;
	private DopravniSit DopS;
	private Scanner sc;

	/** Promenne potrebne pro zadavani rucne */
	private int volba;
	private int volba2 = 5;
	private int planeta;
	private int lod;
	private static Lod lodO;
	private List<Integer> planetyRucne = new ArrayList<Integer>();

	/**
	 * Metoda pro rozhodovani zda pujde o generaci dat nebo chod simulace
	 * 
	 * @param args
	 *            argumety zadane do konsole
	 * @return rozhodnuti
	 */
	public int behProgramu(String args[]) {

		if (args.length > 11) {
			System.out.println("Zadano prilis parametru pro spusteni");
			System.exit(1);
		}

		if (args.length > 4 && args.length < 11) {
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

	/**
	 * Metoda pro zistani parametru pro nacteni dat ze souboru
	 * 
	 * @param args
	 *            argumenty prikazove radky
	 */
	public void nactiData(String args[]) {

		String souborPlaneta = args[1];
		String souborMatice = args[3];
		String souborCentrala = args[2];

		r = new Rozdeleni(souborPlaneta, souborCentrala);
		g = new Graf(souborMatice, r.getPlanety());
		r.setPlanety(g.getVrcholy());

	}

	/**
	 * Metoda pro ziskani parametru pro ulozeni dat
	 * 
	 * @param args
	 *            argumnety prikazove radky
	 */
	public void ulozData(String args[]) {

		r = new Rozdeleni(Integer.parseInt(args[4]), Integer.parseInt(args[5]), Integer.parseInt(args[6]),
				Integer.valueOf(args[7]), Integer.valueOf(args[8]), Integer.valueOf(args[9]),
				Integer.valueOf(args[10]));

		g = new Graf(r.getPlanety());

		g.ulozGraf(args[3]);

		r.vypisPlanety(args[1]);
		r.vypisCentraly(args[2]);

	}

	/**
	 * Metoda pro rizeni simulace podle volby uzivatele
	 */
	public void rizeni() {
		
		sc = new Scanner(System.in);
		System.out.println("Pozadujete rucni zadavani? Stisknete 1, pokud ne stisknete 2");
		switch (sc.nextInt()) {
		case 1:
			beh(true);
			break;
		case 2:
			beh();
			break;

		default:
			break;
		}

	}

	/**
	 * Metoda pro simulaci se zadavanim uzivatele
	 */
	public void beh(boolean rucne) {
		sc = new Scanner(System.in);
		DopS = new DopravniSit((ArrayList<Planeta>) r.getPlanety(), g);
		l = new Logistika(DopS.getPlanety());
		int i = 0;

		while (i != 372) {
			
			
			if (i % 31 == 1) {
				c.nastavDen((i % 31));
				System.out.println("Chcete pridat objednavku rucne? Pokud ano stisknete jednicku");
				volba = sc.nextInt();
				while (volba == 1) {
					System.out.println(c);
					System.out.println("Nejprve zadejte planetu (od 5 do 5005) a pak pocet leku k objednani");
					zadejObjednavkuRucne(sc.next());
					System.out.println("Chcete pridat dalsi objednavku rucne? Pokud ano stisnete jednicku");
					volba = sc.nextInt();
				}

				l.prijmiObjednavky();
				l.setPlanetyPodleVzdalenosti((List<Planeta>) (  (ArrayList<Planeta>) l.getPlanety()).clone());
				
				Collections.sort(l.getPlanetyPodleVzdalenosti());
				l.vypravLode();

				for (int j = 0; j < planetyRucne.size(); j++) {
					l.getPlanety().get(planetyRucne.get(j)).setRucne(false);
				}

			} else if (i%31 != 0){
				
				c.nastavDen(i);
				volby(volba2);
				l.dopravZbozi(c);
			}

			i++;
		}

	}

	/**
	 * Metoda pro simulaci bez zadavani uzivatele
	 */
	public void beh() {
		DopS = new DopravniSit((ArrayList<Planeta>) r.getPlanety(), g);
		l = new Logistika(DopS.getPlanety());
		int i = 0;

		while (i != 372) {
			
			
			if (i % 31 == 1) {
				c.nastavDen(i);
				System.out.println(c);
				l.prijmiObjednavky();
				l.setPlanetyPodleVzdalenosti((List<Planeta>) (  (ArrayList<Planeta>) l.getPlanety()).clone());
				Collections.sort(l.getPlanetyPodleVzdalenosti());
				l.vypravLode();

			} else if(i%31 != 0) {
				c.nastavDen(i);
				l.dopravZbozi(c);
				
			}

			i++;
		}

	}

	/**
	 * Metoda ktera slouzi pro zadani objednavky jednotlive planete rucne
	 * 
	 * @param argumenty
	 *            argumenty ziskane z konzole
	 */
	public void zadejObjednavkuRucne(String argumenty) {
		try {
			String[] pom = argumenty.split(",");
			int[] argumentyInt = new int[pom.length];

			for (int i = 0; i < pom.length; i++) {
				argumentyInt[i] = Integer.parseInt(pom[i]);
			}

			planetyRucne.add(argumentyInt[0]);
			l.getPlanety().get(argumentyInt[0]).setPocetBaleni(argumentyInt[1]);
			l.getPlanety().get(argumentyInt[0]).setRucne(true);

		} catch (Exception e) {
			System.out.println("Spatne zadany vstup");
			System.out.println("Vzor: 40,4000 ");
			System.exit(1);
		}
	}

	/**
	 * Metoda pro rozhodovani zda simulace pobezi se sledovani nejake planety
	 * nebo lode
	 * 
	 * @param volba
	 *            volba uzivatele ziskane z konzole
	 */
	public void volby(int volba3) {

		switch (volba) {
		case 2:
			sledujPlanetuRucne();
			break;
		case 3:
			sledujLodRucne();
			break;
		case 4:
			sledujPlanetuRucne();
			sledujLodRucne();
			break;
		case 5:
			System.out.println("Prejete si sledovat nekterou z planet po nasledujici rok? Pokud ano stisknete 2."
					+ " Pokud si prejete sledovat lod stisknete 3. Pokud oboji 4");
			volba = sc.nextInt();
			this.volba2 = volba;
			volby(volba);

			break;

		default:
			break;
		}
	}

	public void sledujPlanetuRucne() {

		if (planeta == 0) {

			System.out.println("Zadejte planetu z intervalu 5 az " + l.getPlanety().size());
			planeta = sc.nextInt();

			while (!jeSpravnePlaneta(planeta)) {
				planeta = sc.nextInt();
			}
			sledujPlanetu(planeta);
		} else {

			sledujPlanetu(planeta);
		}

	}

	public void sledujLodRucne() {

		if (lod == 0) {
			System.out.println("Zadejte lod z intervalu 0 az " + l.getSeznamLodiZabrane().size());
			lod = sc.nextInt();

			while (!jeSpravneLod(lod)) {
				lod = sc.nextInt();
			}

			sledujLod1(lod);

		}
		sledujLod(lod);

	}

	public boolean jeSpravnePlaneta(int index) {

		if (planeta < 5 || planeta > l.getPlanety().size()) {
			System.out.println("Zadana spatna planeta. Zadejte spravne jmeno");
			return false;
		}

		return true;
	}

	public boolean jeSpravneLod(int index) {
		if (index < 0 || index > l.getSeznamLodiZabrane().size()) {
			System.out.println("Zadana spatna lod. Zadejte spravne jmeno");
			return false;
		}
		return true;
	}

	public void sledujPlanetu(int planeta) {
		System.out.println(c);
		System.out.println(l.getPlanety().get(planeta));
		//System.out.println(l.getPlanety().get(planeta).getLod());
		
	}

	public void sledujLod1(int lod) {
		System.out.println(c);
		lodO = l.getSeznamLodiZabrane().get(lod);
		// System.out.println(l.getSeznamLodiZabrane().get(lod));
		System.out.println(lodO);
	}

	public void sledujLod(int lod) {
		System.out.println(c);
		System.out.println(lodO);
		System.out.println(lodO.getObC().getIndexUseky());
		System.out.println(lodO.getIndex());
	

	}

	public void statistika() {

		for (int i = 5; i < l.getPlanety().size(); i++) {
			l.getPlanety().get(i).vypisStatistiku();
		}

		l.getPlanety().get(0).vypisUmrtiCele((ArrayList<Planeta>) l.getPlanety());
		
		for (int i = 0; i < l.getSeznamLodiVolne().size(); i++) {
		 l.getSeznamLodiVolne().get(i).vypisLod();
		 }
			l.getSeznamLodiVolne().get(0).vypisLodCelkem();
		
	}

	public static void main(String[] args) {
	
		Vesmir v = new Vesmir();

		if (v.behProgramu(args) == 1) {
			v.nactiData(args);

			v.rizeni();
			v.statistika();

		} else {
			v.ulozData(args);
		}

	}

	/************ Getry a Setry *******************/
	/**
	 * @return the c
	 */
	public Cas getC() {
		return c;
	}

	/**
	 * @param c
	 *            the c to set
	 */
	public void setC(Cas c) {
		this.c = c;
	}
	
	/**
	 * @return the planetyRucne
	 */
	public List<Integer> getPlanetyRucne() {
		return planetyRucne;
	}

	/**
	 * @param planetyRucne the planetyRucne to set
	 */
	public void setPlanetyRucne(List<Integer> planetyRucne) {
		this.planetyRucne = planetyRucne;
	}

}
