import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Logistika {

	/***
	 * @author Václav Janoch
	 * @author Filip Kupilik
	 * 
	 */

	/** Konstanty tridy logistika **/
	private final int maxNaklad = 5000000;
	private final int rychlostLodi = 25;

	/** staticke promenne tridy Logistika */
	private static int pomNazev = 10;

	/**
	 * Promenne potrebne pro praci s daty
	 */
	private Objednavka obj;
	private List<Objednavka> objednavky;
	
	private List<Planeta> planetyPodleVzdalenosti = new ArrayList<Planeta>();
	private List<Planeta> planety;
	private List<Lod> seznamLodiVolne = new ArrayList<Lod>();
	private List<Lod> seznamLodiZabrane = new ArrayList<Lod>();
	private List<ObchodniCesta> cestaO = new ArrayList<ObchodniCesta>();

	/**
	 * Pomocne promenne
	 */
	
	private int mesic = 0;

	/**
	 * Instance tridi Random pro praci s nahodnymi cisly
	 */
	Random r = new Random();

	/**
	 * Konstruktor tridy Nacte pripravene planet a graf vesmiru
	 * 
	 * @param list
	 *            pripravene planety
	 * @param g
	 *            instatnce tridy graf pro praci s planetami
	 */
	public Logistika(List<Planeta> planety) {

		this.objednavky = new ArrayList<Objednavka>();
		this.planety = planety;

	}

	

	/**
	 * Ridi dopravu zbozi na jednotlive planety podle jejich dodavek
	 * 
	 * @param cas
	 *            Instance tridy cas pro rizeni chodu simulace
	 */
	public void dopravZbozi(Cas cas) {

		int pocetUrazenychMil;
		Lod pom;
		int index;
		int indexDodavky;

		Collections.sort(seznamLodiZabrane);

		for (int i = 0; i < seznamLodiZabrane.size(); i++) {

			index = seznamLodiZabrane.get(i).getObC().getIndexUseky();
			indexDodavky = seznamLodiZabrane.get(i).getObC().getIndexDodavky();

			pom = seznamLodiZabrane.get(i);
			pocetUrazenychMil = pom.getUrazeno();

			// pokud uz nejsou zadne planety k doruceni

			if (index < 0) {
				if (cas.getDen() != planety.get(Integer.parseInt(pom.getObC().getP().get(index + 1).getNazev()))
						.getDatum()) {

					if (pom.getUrazeno() - 25 < 0) {

						pom.setUrazeno(0);

						seznamLodiVolne.add(pom);
						seznamLodiZabrane.remove(i);

					} else {

						pom.setUrazeno(pocetUrazenychMil - rychlostLodi);
						pom.setPlaneta(planety.get(Integer.parseInt(pom.getObC().getP().get(index + 1).getCentrala())));
					}
				}
			} else {

				int index2 = Integer.parseInt(pom.getObC().getP().get(index).getNazev());

				if (planety.get(index2).getUseky().get(planety.get(index2).getIndex()).znicNaklad()) {
					pom = znic(index2, pom, cas, index);

					seznamLodiVolne.add(pom);
					seznamLodiZabrane.remove(i);

				} else {

					if (zjistiVzdalenostDoCile(pom.getUrazeno(),
							pom.getObC().getP().get(index).getVzdalenostOdcentraly()) <= 25) {

						pom = mensiVzdalenost(indexDodavky, index, index2, cas, pom, pocetUrazenychMil);

					} else {

						planety.get(index2).setLod(pom);
						planety.get(index2).setVzdalenostOdPlanety(pom.getUrazeno());

						pom.setUrazeno(pocetUrazenychMil + rychlostLodi);
						pom.setPlaneta(planety.get(index2));
					}

					seznamLodiZabrane.add(i, pom);
					seznamLodiZabrane.remove(i);
				}

			}

		}
	}

	/**
	 * Metoda pro praci s lodi pri napadeni piraty
	 * 
	 * @param index2
	 *            umisteni v Arraylistu planety
	 * @param pom
	 *            lod se kterou pracujeme
	 * @param c
	 *            intstance tridy cas pro rizeni simulace
	 * @param index
	 *            index pro zjisteni ktera planeta je na rade s
	 * @return pom Vraci upravenou lod
	 */
	public Lod znic(int index2, Lod pom, Cas c, int index) {

		planety.get(index2).getUseky().get(planety.get(index2).getIndex()).setPrulet(1);
		planety.get(index2).upravPopulaci(pom.getObC().getP().get(index).getObjednavka().getPocetLeku());
		planety.get(index2).setIndex(planety.get(index2).getIndex() - 1);
		planety.get(index2).setObsluhovan(false);
		planety.get(index2).getStatistika().add(new StatistikaPlaneta(0, pom, c.getMesic()));
		// planety.get(index2).getStatistika().get((c.getMesic()-1)).zvysPocet();
		pom.setPrepadeni(pom.getPrepadeni() + 1);
		pom.setStav(false);

		return pom;
	}

	/**
	 * Metoda pro praci s lodi pokud se blizi k planete Snizi rychlost lodi
	 * pokud je to potreba Nastavi vykladani zbozi a z brzdi lod
	 * 
	 * @param indexDodavky
	 *            pocet leku k doruceni
	 * @param index
	 *            planety v systemu dodavek
	 * @param index2
	 *            poloha planety v arraylistu planety
	 * @param c
	 *            instance tridy cas pro rizeni simulace
	 * @param pom
	 *            Lod na ktere se budou provadet upravy
	 * @param pocetUrazenychMil
	 *            vzdalenost kolik uz toho lod urazila
	 * @return Upravenou lod
	 */
	public Lod mensiVzdalenost(int indexDodavky, int index, int index2, Cas c, Lod pom, int pocetUrazenychMil) {
		int datum = planety.get(Integer.parseInt(pom.getObC().getP().get(index).getNazev())).getDatum();
		int leky = 0;
		if (c.getDen() == (datum + 1)) {

			leky = pom.getObC().getP().get(index).getObjednavka().getPocetLeku();
			planety.get(index2).setDatum(0);
			planety.get(index2).setObsluhovan(false);
			planety.get(index2).setVzdalenostOdPlanety(pom.getUrazeno());
			planety.get(index2).upravPopulaciLekyDorazily(leky);
			planety.get(index2).getStatistika().add(new StatistikaPlaneta(leky, pom, c.getMesic()));
			planety.get(index2).getStatistika().get(planety.get(index2).getIndexStatistika()).zvysPocet();
			planety.get(index2).setIndexStatistika(planety.get(index2).getIndexStatistika() + 1);
			pom.getObC().setIndexUseky(index - 1);
			pom.getObC().setIndexDodavky(indexDodavky - 1);

		} else {
			nastavDatumAVykladku(pom, index, pocetUrazenychMil, index2, c.getDen());

			planety.get(index2).setVzdalenostOdPlanety(pom.getUrazeno());

		}

		return pom;
	}

	/**
	 * 
	 * Nastavi datum vykladky nakladu pro urcenou planetu
	 * 
	 * @param pom
	 *            Lod ktera veze na planedu leky
	 * @param j
	 * @param pocetUrazenychMil
	 *            vzdalenost kterou lod urazila od startu
	 * @param index
	 * @param den
	 *            nastavi datum vykladu pro dalsi praci s nakladem
	 */
	public void nastavDatumAVykladku(Lod pom, int j, int pocetUrazenychMil, int index, int den) {
		pom.setUrazeno(pocetUrazenychMil
				+ zjistiVzdalenostDoCile(pom.getUrazeno(), pom.getObC().getP().get(j).getVzdalenostOdcentraly()));
		planety.get(index).setVykladan(true);
		planety.get(index).setDatum(den);

	}

	/**
	 * Zjisti Vzdalenost do cile z aktualni pozice
	 * 
	 * @param urazeno
	 *            Vzdalenost kterou lod urazila
	 * @param vzdalenostOdCentraly
	 *            vzdalenost kterou ma urazit
	 * @return vysledna vzdalenost
	 */
	public int zjistiVzdalenostDoCile(int urazeno, int vzdalenostOdCentraly) {

		return vzdalenostOdCentraly - urazeno;
	}

	/**
	 * Vytvori jednotlive dodavky pro planety a vypravy lode
	 */
	public void vypravLode() {
		for (int i = 0; i < planetyPodleVzdalenosti.size(); i++) {
			sectiObjednavky(planetyPodleVzdalenosti.get(i));
			planetyPodleVzdalenosti.get(i).setIndex(planetyPodleVzdalenosti.get(i).getUseky().size() - 1);

		}

		for (int i = 0; i < seznamLodiZabrane.size(); i++) {
			seznamLodiVolne.add(seznamLodiZabrane.get(i));
		}
		seznamLodiZabrane.clear();

		for (int i = 0; i < cestaO.size(); i++) {

			cestaO.get(i).setIndexUseky(cestaO.get(i).getP().size() - 1);
			cestaO.get(i).setIndexDodavky(cestaO.get(i).getD().size() - 1);
			vypravLod(cestaO.get(i));
		}

		cestaO.clear();

		mesic++;

	}

	/**
	 * Vytvori obchodni cestu s dodavkami leku na jednotlive leky
	 * 
	 * @param planeta
	 *            Startovni planeta pro ze ktere se zacinaji pocitat dodavky
	 */

	public void sectiObjednavky(Planeta planeta) {

		int celkem = 0;
		int pomIndex = 1;
		int index = 0;
		int lastIndex2 = 0;

		ArrayList<Dodavka> dodavky = new ArrayList<Dodavka>();
		ArrayList<Planeta> pomPlanety = new ArrayList<Planeta>();

		if (Integer.parseInt(planeta.getNazev()) > 5) {

			pomIndex = planeta.getCesta().getPlanety().size() - 1;

			while (celkem < maxNaklad) {

				if (!planeta.isObsluhovan()) {

					if (planeta.getObjednavka().getPocetLeku() > maxNaklad) {
						celkem += maxNaklad - 1;
						planeta.setObsluhovan(true);
						dodavky.add(new Dodavka(planeta.getObjednavka().getPocetLeku(), planeta));

					} else {

						celkem += planeta.getObjednavka().getPocetLeku();
						planeta.setObsluhovan(true);
						dodavky.add(new Dodavka(planeta.getObjednavka().getPocetLeku(), planeta));

					}
				}

				pomIndex -= 1;
				if (pomIndex < 0) {
					break;
				}

				index = planeta.getCesta().getPlanety().get(pomIndex);

				if (jeVolna(planeta.getCesta(), pomIndex)) {

					if (!planety.get(index).isObsluhovan()) {
						int objednavka = planety.get(index).getObjednavka().getPocetLeku();

						celkem += objednavka;

						planety.get(index).setObsluhovan(true);
						pomPlanety.add(planety.get(index));
						dodavky.add(new Dodavka(objednavka, planety.get(index)));
						lastIndex2 = index;

					}
				} else {
					break;
				}

			}

			pomPlanety.add(0, planeta);

			if (celkem > maxNaklad) {
				int indexPom = dodavky.size() - 1;
				dodavky.get(indexPom).setP(planety.get(lastIndex2));
				dodavky.get(indexPom).setPocetLeku(velikost(lastIndex2, celkem));
				celkem = maxNaklad;
			}
		}
		if (celkem > 0) {

			cestaO.add(new ObchodniCesta(pomPlanety, dodavky, planeta.getCesta(), celkem));

		}
	}

	/**
	 * Upravy naklad by se vesel na lod a nastavi planete zbyvajici pocet leku
	 * 
	 * @param lastIndex2
	 *            umisteni planety v Arraylistu
	 * @param celkem
	 *            akturalni pocet leku na palube lodi
	 * @return velikost poctu leku ktere se museji jeste dodat
	 */
	public int velikost(int lastIndex2, int celkem) {

		int pom = celkem - maxNaklad;
		planety.get(lastIndex2).setObsluhovan(false);
		planety.get(lastIndex2).getObjednavka().setPocetLeku(pom);

		return pom;

	}

	/**
	 * Zjisti zda je planeta volna
	 * 
	 * @param c
	 *            Cesta z centraly do Planety
	 * @param index
	 * @return true pokud je planeta volna
	 */
	public boolean jeVolna(Cesta c, int index) {

		return (!planety.get(c.getPlanety().get(index)).isObsluhovan()
				&& !planety.get(c.getPlanety().get(index)).isNizkyPocet());
	}

	/**
	 * Pozada o vytvoreni lodi pokud neni k dispozici nebo poskytne lod pro
	 * dodani leku
	 */

	public void vypravLod(ObchodniCesta c) {

		Lod lod;
		int index = 0;
		if (seznamLodiVolne.isEmpty()) {
			vytvorLod();
		}

		index = seznamLodiVolne.size() - 1;
		lod = seznamLodiVolne.get(index);
		lod.setObC(c);
		lod.zjistiDobuCesty();

		if (lod.getDobaLetu() > 25) {
			zrusObjednavku(lod.getObC().getP(), lod);
		} else {

			lod.getStatistika().getCesta().add(c);
			lod.getStatistika().getPocetLeku()[lod.getIndex()] = c.getPocetLekuNaCestu();
			lod.setIndex(mesic);

			seznamLodiZabrane.add(lod);
			// System.out.println(seznamLodiVolne.size());
			seznamLodiVolne.remove(index);
			// System.out.println(seznamLodiVolne.size());
		}

	}

	/**
	 * Zrusi objednavku pokud je planeta mimo dosah lodi za pul mesice
	 * 
	 * @param zmenPlanety
	 *            planeta ktere se zmena tyka
	 */
	public void zrusObjednavku(List<Planeta> zmenPlanety, Lod pom) {
		for (int i = 0; i < zmenPlanety.size(); i++) {
			int index = Integer.parseInt(zmenPlanety.get(i).getNazev());
			planety.get(index).upravPopulaci(planety.get(index).getObjednavka().getPocetLeku());
			planety.get(index).setObsluhovan(false);
			planety.get(index).getStatistika().add(new StatistikaPlaneta(0, pom, 3));

		}

	}

	/**
	 * Vytvori Lod novou lod s patricnym nazvem
	 */
	public void vytvorLod() {
		seznamLodiVolne.add(new Lod("X-" + pomNazev));
		pomNazev++;

	}


	/**
	 * Prijme objednavky na dany mesic
	 */
	public void prijmiObjednavky() {

		for (int i = 0; i < planety.size(); i++) {
			if (planety.get(i).getPocetObyvatel() > 40000 || Integer.parseInt(planety.get(i).getNazev()) < 5) {

				objednavky.add(planety.get(i).vytrorObjednavku());
			} 
		}

	}

	

	/*********************************************
	 * Getry a Setry
	 ****************/

	public Objednavka getObj() {
		return obj;
	}

	public void setObj(Objednavka obj) {
		this.obj = obj;
	}

	public List<Planeta> getPlanety() {
		return planety;
	}

	public void setPlanety(List<Planeta> planety) {
		this.planety = planety;
	}

	/**
	 * @return the graf
	 */
	
	/**
	 * @return the cestaO
	 */
	public List<ObchodniCesta> getCestaO() {
		return cestaO;
	}

	/**
	 * @param cestaO
	 *            the cestaO to set
	 */
	public void setCestaO(List<ObchodniCesta> cestaO) {
		this.cestaO = cestaO;
	}

	/**
	 * @return the seznamLodiVolne
	 */
	public List<Lod> getSeznamLodiVolne() {
		return seznamLodiVolne;
	}

	/**
	 * @param seznamLodiVolne
	 *            the seznamLodiVolne to set
	 */
	public void setSeznamLodiVolne(List<Lod> seznamLodiVolne) {
		this.seznamLodiVolne = seznamLodiVolne;
	}

	/**
	 * @return the seznamLodiZabrane
	 */
	public List<Lod> getSeznamLodiZabrane() {
		return seznamLodiZabrane;
	}

	/**
	 * @param seznamLodiZabrane
	 *            the seznamLodiZabrane to set
	 */
	public void setSeznamLodiZabrane(List<Lod> seznamLodiZabrane) {
		this.seznamLodiZabrane = seznamLodiZabrane;
	}

	/**
	 * @return the planetyPodleVzdalenosti
	 */
	public List<Planeta> getPlanetyPodleVzdalenosti() {
		return planetyPodleVzdalenosti;
	}

	/**
	 * @param list
	 *            the planetyPodleVzdalenosti to set
	 */
	public void setPlanetyPodleVzdalenosti(List<Planeta> list) {
		this.planetyPodleVzdalenosti = list;
	}
	
	/**
	 * @return the objednavky
	 */
	public List<Objednavka> getObjednavky() {
		return objednavky;
	}

	/**
	 * @param objednavky the objednavky to set
	 */
	public void setObjednavky(List<Objednavka> objednavky) {
		this.objednavky = objednavky;
	}

		

}
