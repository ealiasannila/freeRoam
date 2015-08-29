package reittienEtsinta.tietorakenteet;

import org.json.JSONArray;
import org.json.JSONObject;
import reittienEtsinta.Apumetodit;

/**
 * Verkko jossa lyhyimpien reittien etsintä tapahtuu. Verkko muodostetaan siten,
 * että eri polygoineista muodostuvien alueiden välillä voi liikkua vain
 * polygonin solmusta toiseen, eikä kaaret saa leikata toista polygonia, tai
 * polygonia itseään.
 *
 * @author elias
 */
public class Verkko {

    public double[] lat;
    public double[] lon;
    private double[] alkuun;
    private double[] loppuun;
    private int[] polku;
    private Lista<double[]>[] vl;

    private int maalisolmu;
    private int lahtosolmu;

    private Maastokirjasto maastokirjasto;

    //ei tarvitse kertoa erikseen mistä minne, tai vieruslistoja naapurit tiedätään muutoinkin
    public Verkko(int solmujenMaara, Maastokirjasto maastokirjasto) {

        this.alkuun = new double[solmujenMaara];
        this.loppuun = new double[solmujenMaara];
        this.lat = new double[solmujenMaara];
        this.lon = new double[solmujenMaara];
        this.polku = new int[solmujenMaara];
        this.vl = new Lista[solmujenMaara];

        this.maastokirjasto = maastokirjasto;

        for (int i = 0; i < vl.length; i++) {
            vl[i] = new Lista<double[]>(10);
        }
       
    }

    /**
     * lisää kaaren kun verkontekijä luokassa on ensin testattu, ettei se
     * leikkaa mitään polygonia
     *
     * @param solmu
     * @param kohde
     * @param lats
     * @param lons
     * @param latk
     * @param lonk
     */
    public void lisaaKaari(int solmu, int kohde, int maasto, double lats, double lons, double latk, double lonk, boolean ulko) {
       
        if (this.loppuun[solmu] < 0.001) {
            this.lat[solmu] = lats;
            this.lon[solmu] = lons;

        }
        if (this.loppuun[kohde] < 0.001) {
            this.lat[kohde] = latk;
            this.lon[kohde] = lonk;
        }

        double etaisyys = Apumetodit.pisteidenEtaisyys(lats, lons, latk, lonk);
        double aika = etaisyys / this.maastokirjasto.haeVauhti(maasto, ulko);
        
        this.vl[solmu].lisaa(new double[]{kohde, aika});
        this.vl[kohde].lisaa(new double[]{solmu, aika});

    }

    private double arvioiEtaisyys(int solmu) {
        return Apumetodit.pisteidenEtaisyys(lat[solmu], lon[solmu], lat[this.maalisolmu], lon[this.maalisolmu]);
    }

    /**
     * Alustaa A* käyttämät alkuun loppuun ja polku taulukot uutta reitin
     * etsintää varten. Ei alusta vierusmatriisia
     *
     * @param lahtosolmu
     * @param maalisolmu
     */
    public void alustus(int lahtosolmu, int maalisolmu) {
        this.maalisolmu = maalisolmu;
        this.lahtosolmu = lahtosolmu;
        for (int i = 0; i < this.alkuun.length; i++) {
            this.alkuun[i] = Double.MAX_VALUE;
            this.polku[i] = -1;
            this.loppuun[i] = this.arvioiEtaisyys(i);

        }
        this.alkuun[lahtosolmu] = 0;

    }

    
    /**
     * päivitää alkuun arviota ja polkua
     * @param solmu
     * @param naapuri
     * @param naapurinindeksi
     * @return 
     */
    private boolean loysaa(int solmu, int naapuri, int naapurinindeksi) {
        if (this.alkuun[solmu] == Double.MAX_VALUE) {
            return false;
        }
        
        double kaari = this.vl[solmu].ota(naapurinindeksi)[1];
        if (this.alkuun[naapuri] > this.alkuun[solmu] + kaari) {
            this.alkuun[naapuri] = this.alkuun[solmu] + kaari;
            this.polku[naapuri] = solmu;
            return true;
        }

        return false;
    }

    /**
     * laskee lyhyimmät etäisyydet maalisolmuun lähtien lähtösolmusta.
     * Heuristiikkafunktiona suora etäisyys * minimivauhti, lopettaa etsinnän
     * kun reitti löytyy.
     *
     * @param lahtoSolmu
     * @return
     */
    public boolean aStar() {

        MinimiKeko keko = new MinimiKeko(this.alkuun.length) {

            @Override
            double arvio(int i) {
                if (alkuun[i] == Double.MAX_VALUE) {
                    return Double.MAX_VALUE * 0.00001;
                }
                return alkuun[i] + loppuun[i] * 0.00001; //0.00001 = minimivauhti jota voidaan kulkea
            }
        };

        for (int i = 0; i < this.alkuun.length; i++) {
            keko.lisaa(i);
        }

        while (!keko.tyhja()) {
            int solmu = keko.otaPienin();
            if (solmu == this.maalisolmu) {
                return true;
            }
            for (int i = 0; i < this.vl[solmu].koko(); i++) {
                int naapuri = (int) this.vl[solmu].ota(i)[0];
                if (loysaa(solmu, naapuri, i)) {
                    keko.paivita(naapuri);
                }
            }
        }
        if (this.alkuun[this.maalisolmu] == Double.MAX_VALUE) { //reittiä ei löytynyt
            return false;
        }

        return true;
    }

    //testaus käyttää
    protected int[] getPolku() {
        return polku;
    }
    
    //testaus käyttää
    protected double[] getAlkuun() {
        return alkuun;
    }
    
    

    /**
     * palauttaa aStar metodin etsimän lyhyimmän reitin lähtö ja maalisolmun
     * välillä ReittiPolygoni muodossa
     *
     */
    public Reitti lyhyinReitti() {
        if (this.lahtosolmu == this.maalisolmu) {
            return null;
        }
        Pino<Integer> pino = new Pino(this.alkuun.length);
        int seuraava = this.polku[this.maalisolmu];

        while (seuraava != lahtosolmu) {
            pino.lisaa(seuraava);
            seuraava = this.polku[seuraava];
        }

        double[] reittilat = new double[pino.koko() + 2];
        double[] reittilon = new double[pino.koko() + 2];
        int[] reittiaika = new int[pino.koko() + 2];

        this.asetaReittiPiste(reittilat, reittilon, reittiaika, 0, this.lahtosolmu);
        int i = 1;
        while (!pino.tyhja()) {
            int solmu = pino.ota();
            this.asetaReittiPiste(reittilat, reittilon, reittiaika, i, solmu);
            i++;

        }
        this.asetaReittiPiste(reittilat, reittilon, reittiaika, i, this.maalisolmu);

        return new Reitti(reittilon, reittilat, reittiaika);

    }

    private void asetaReittiPiste(double[] reittilat, double[] reittilon, int[] reittiaika, int i, int solmu) {
        reittilat[i] = this.lat[solmu];
        reittilon[i] = this.lon[solmu];
        reittiaika[i] = (int) this.alkuun[solmu];
    }

    /**
     * hakee koordinaatteja lähimmän solmun. Käyttöliittymä käyttää
     * @param lat
     * @param lon
     * @return 
     */
    public int haeLahinSolmu(double lat, double lon) {
        double etaisyys = Double.MAX_VALUE;
        int solmu = -1;
        for (int i = 0; i < this.lat.length; i++) {
            double uusi = Apumetodit.pisteidenEtaisyys(lat, lon, this.lat[i], this.lon[i]);
            if (etaisyys > uusi) {
                etaisyys = uusi;
                solmu = i;
            }
        }
        return solmu;
    }

    /**
     * palauttaa kahden solmun välisen kaaren painon
     * @param alku
     * @param loppu
     * @return 
     */
    public double haeKaari(int alku, int loppu) {
        for (int i = 0; i < this.vl[alku].koko(); i++) {
            if (((int) this.vl[alku].ota(i)[0]) == loppu) {
                return this.vl[alku].ota(i)[1];
            }
        }
        return -1.0;
    }

    public String toString() {

        StringBuilder builder = new StringBuilder();
        StringBuilder koord = new StringBuilder();

        koord.append("[   ]");
        for (int i = 0; i < this.vl.length; i++) {
            koord.append("[ " + i + " ]");
        }
        koord.append("\n");
        builder.append(koord);
        for (int i = 0; i < this.vl.length; i++) {
            builder.append("[ " + i + " ]");
            for (int j = 0; j < this.vl.length; j++) {
                boolean jloytyi = false;
                for (int k = 0; k < this.vl[i].koko(); k++) {
                    if ((int) this.vl[i].ota(k)[0] == j) {
                        String s = String.format("%.1f", this.vl[i].ota(k)[1]);
                        builder.append("[" + s + "]");
                        jloytyi = true;
                        break;
                    }
                }
                if (!jloytyi) {
                    builder.append("[max]");

                }

            }

            builder.append("\n");
        }
        return builder.toString();
    }

}
