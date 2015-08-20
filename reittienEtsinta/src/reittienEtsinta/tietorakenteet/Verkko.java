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
    private double[][] vm;

    private int maalisolmu;
    private int lahtosolmu;

    private MaastoKirjasto maastokirjasto;

    //ei tarvitse kertoa erikseen mistä minne, tai vieruslistoja naapurit tiedätään muutoinkin
    public Verkko(int solmujenMaara, MaastoKirjasto maastokirjasto) {

        this.alkuun = new double[solmujenMaara];
        this.loppuun = new double[solmujenMaara];
        this.lat = new double[solmujenMaara];
        this.lon = new double[solmujenMaara];
        this.polku = new int[solmujenMaara];
        this.vm = new double[solmujenMaara][solmujenMaara];

        this.maastokirjasto = maastokirjasto;

        for (int i = 0; i < vm.length; i++) {
            for (int j = 0; j < vm[i].length; j++) {
                vm[i][j] = Double.MAX_VALUE;
            }
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
        //lisätään kaari verkkoon ja tarpeen vaatiessa alustetaan solmut
        //System.out.println(lons);
        //System.out.println(lonk);
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

        this.vm[solmu][kohde] = aika;
        this.vm[kohde][solmu] = aika;

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

    private boolean loysaa(int solmu, int naapuri) {
        if (this.alkuun[solmu] == Double.MAX_VALUE) {
            return false;
        }
        if (this.alkuun[naapuri] > this.alkuun[solmu] + vm[solmu][naapuri]) {
            this.alkuun[naapuri] = this.alkuun[solmu] + vm[solmu][naapuri];
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
            for (int naapuri = 0; naapuri < vm.length; naapuri++) {

                if (loysaa(solmu, naapuri)) {
                    keko.paivita(naapuri);
                }

            }
        }
        if (this.alkuun[this.maalisolmu] == Double.MAX_VALUE) { //reittiä ei löytynyt
            return false;
        }

        return true;
    }

    public int[] getPolku() {
        return polku;
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

        this.asetaReittiPist(reittilat, reittilon, reittiaika, 0, this.lahtosolmu);
        int i = 1;
        while (!pino.tyhja()) {
            int solmu = pino.ota();
            this.asetaReittiPist(reittilat, reittilon, reittiaika, i, solmu);
            i++;

        }
        this.asetaReittiPist(reittilat, reittilon, reittiaika, i, this.maalisolmu);

        return new Reitti(reittilon, reittilat, reittiaika);

    }

    private void asetaReittiPist(double[] reittilat, double[] reittilon, int[] reittiaika, int i, int solmu) {
        reittilat[i] = this.lat[solmu];
        reittilon[i] = this.lon[solmu];
        reittiaika[i] = (int) this.alkuun[solmu];
    }

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

    public double haeKaari(int alku, int loppu) {
        return this.vm[alku][loppu];
    }

    /**
     * palauttaa verkon geojson muodossa, käytetään verkon visualisointiin
     * debuggauksen yhteydessä. Ei vaikuta ohjelman toimintaan
     *
     * @return
     */
    public JSONObject getVerkko() {

        JSONObject verkko = new JSONObject();
        verkko.put("type", "FeatureCollection");

        JSONObject properties = new JSONObject();
        properties.put("name", "urn:ogc:def:crs:EPSG::3047");

        JSONObject crs = new JSONObject();
        crs.put("type", "name");
        crs.put("properties", properties);

        verkko.put("crs", crs);

        JSONArray features = new JSONArray();

        for (int i = 0; i < this.vm.length; i++) {
            for (int j = 0; j < this.vm[i].length; j++) {

                if (vm[i][j] < 0.001) {
                    continue;
                }

                JSONObject feature = new JSONObject();
                feature.put("type", "Feature");
                feature.put("properties", "{ }");

                JSONObject geometry = new JSONObject();
                geometry.put("type", "LineString");

                JSONArray coordinates = new JSONArray();

                double[] lahtopiste = new double[]{this.lon[i], this.lat[i]};
                coordinates.put(new JSONArray(lahtopiste));

                double[] maalipiste = new double[]{this.lon[j], this.lat[j]};
                coordinates.put(new JSONArray(maalipiste));

                geometry.put("coordinates", coordinates);
                feature.put("geometry", geometry);
                features.put(feature);
            }
        }

        verkko.put("features", features);
        return verkko;

    }

    public String toString() {

        StringBuilder builder = new StringBuilder();
        StringBuilder koord = new StringBuilder();

        koord.append("[   ]");
        for (int i = 0; i < this.vm.length; i++) {
            koord.append("[ " + i + " ]");
        }
        koord.append("\n");
        builder.append(koord);
        for (int i = 0; i < this.vm.length; i++) {
            builder.append("[ " + i + " ]");
            for (int j = 0; j < this.vm[i].length; j++) {
                if (this.vm[i][j] == Double.MAX_VALUE) {
                    builder.append("[max]");
                } else {
                    String s = String.format("%.1f", this.vm[i][j]);
                    builder.append("[" + s + "]");
                }
            }

            builder.append("\n");
        }
        return builder.toString();
    }

}
