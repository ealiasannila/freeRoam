/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reittienEtsinta.tietorakenteet;

import java.util.Arrays;

/**
 * Minimikeko A* yhteydessä käytettäväksi. Järjestää solmut niiden Alkuun +
 * Loppuun arvion perusteella.
 *
 * @author elias
 */
public abstract class MinimiKeko {

    private int[] keko;
    private int keonKoko;

    private int[] kekoindeksit;

    public MinimiKeko(int koko) {
        this.keko = new int[koko + 1];
        this.keonKoko = 0;

         this.kekoindeksit = new int[koko];
    }

    /**
     * vanhemman indeksi keko taulukossa
     *
     * @param i
     * @return
     */
    private int vanhempi(int i) {
        return i / 2;
    }

    /**
     * vasemman lapsen indeksi keko taulukossa
     *
     * @param i
     * @return
     */
    private int vasenLapsi(int i) {
        return 2 * i;
    }

    /**
     * oikean lapsen indeksi keko taulukossa
     *
     * @param i
     * @return
     */
    private int oikeaLapsi(int i) {
        return 2 * i + 1;
    }

    /**
     * antaa arvion jonka mukaan määritellään kunkin keon alkion arvo.
     *
     * @param i
     * @return
     */
    abstract double arvio(int i);
    
    

    /**
     * Palauttaa kekoehdon voimaan, jos kekoon (tai keossa oleviin solmuihin) on
     * tehty muutoksia. Aloittaa keon muokkaamisen tietystä indeksistä.
     *
     * @param i
     */
    private void heapify(int i) {
        int v = this.vasenLapsi(i);
        int o = this.oikeaLapsi(i);
        int pienempi;

        if (o <= this.keonKoko) {
            if (this.arvio(this.keko[v]) < arvio(this.keko[o])) {
                pienempi = v;
            } else {
                pienempi = o;
            }

            if (arvio(this.keko[i]) > arvio(this.keko[pienempi])) {
                this.vaihda(i, pienempi);
                this.heapify(pienempi);
            }
        } else if (v == this.keonKoko && arvio(this.keko[i]) > arvio(this.keko[v])) {
            this.vaihda(i, v);
        }
    }

    /**
     * Palauttaa kekoehdon voimaan, kun solmun alkuun arviota on vähennetty.
     * Aloittaa muokkaamisen tietystä solmusta. --> toimisiko
     * this.heapify(solmu.getKekoI())?
     *
     * @param solmu
     */
    public void paivita(int solmu) {
        int i = this.kekoindeksit[solmu];
        while (i > 1 && arvio(this.keko[this.vanhempi(i)]) > arvio(this.keko[i])) {
            this.vaihda(i, this.vanhempi(i));
            i = this.vanhempi(i);
        }
    }

    /**
     * vaihtaa kahden solmun paikan keossa.
     *
     * @param a
     * @param b
     */
    private void vaihda(int a, int b) {
        int tmp = this.keko[a];
        this.keko[a] = this.keko[b];
        this.kekoindeksit[this.keko[a]] = a;
        this.keko[b] = tmp;
        this.kekoindeksit[this.keko[b]] = b;
    }

    /**
     * lisää kekoon uuden solmun, säilyttäen kekoehdon voimassa.
     *
     * @param solmu
     */
    public void lisaa(int solmu) {
        this.keonKoko++;
        int i = this.keonKoko;
        while (i > 1 && arvio(this.keko[this.vanhempi(i)]) > arvio(solmu)) {
            this.keko[i] = keko[this.vanhempi(i)];
            this.kekoindeksit[this.keko[i]] = i;
            i = this.vanhempi(i);
        }
        this.keko[i] = solmu;
        this.kekoindeksit[solmu] = i;

    }

    /**
     * kertoo onko keko tyhjä
     *
     * @return
     */
    public boolean tyhja() {
        return this.keonKoko == 0;
    }

    /**
     * ottaa keon ensimmäisen solmun ja palauttaa kekoehdon voimaan heapifyn
     * avulla.
     *
     * @return
     */
    public int otaPienin() {

        int eka = this.keko[1];
        this.keko[1] = this.keko[this.keonKoko];
        this.kekoindeksit[this.keko[1]] = 1;

        this.keonKoko--;
        this.heapify(1);
        return eka;
    }

    public String toString() {
        return "hei olen keko";
//    return "K: " + Arrays.toString(this.keko) + "\nL: " + Arrays.toString(this.loppuun) + "\nKi: " + Arrays.toString(this.kekoindeksit);
    }
}
