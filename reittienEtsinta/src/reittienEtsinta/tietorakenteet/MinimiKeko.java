/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reittienEtsinta.tietorakenteet;

import java.util.Arrays;

/**
 *
 * @author elias
 */
public class MinimiKeko {

    private VerkkoSolmu[] keko;
    private int keonKoko;

    public MinimiKeko(int koko) {
        this.keko = new VerkkoSolmu[koko + 1];
        this.keonKoko = 0;
    }

    private int vanhempi(int i) {
        return i / 2;
    }

    private int vasenLapsi(int i) {
        return 2 * i;
    }

    private int oikeaLapsi(int i) {
        return 2 * i + 1;
    }

    private void heapify(int i) {
        int v = this.vasenLapsi(i);
        int o = this.oikeaLapsi(i);
        int pienempi;

        if (o <= this.keonKoko) {
            if (this.keko[v].getArvio() < this.keko[o].getArvio()) {
                pienempi = v;
            } else {
                pienempi = o;
            }

            if (this.keko[i].getArvio() > this.keko[pienempi].getArvio()) {
                this.vaihda(i, pienempi);
                this.heapify(pienempi);
            }
        } else if (v == this.keonKoko && this.keko[i].getArvio() > this.keko[v].getArvio()) {
            this.vaihda(i, v);
        }
    }

    public void paivita(VerkkoSolmu solmu) {
        int i = solmu.getKekoI();
        while (i > 1 && this.keko[this.vanhempi(i)].getArvio() > this.keko[i].getArvio()) {
            this.vaihda(i, this.vanhempi(i));
            i = this.vanhempi(i);
        }
    }

    private void vaihda(int a, int b) {
        VerkkoSolmu tmp = this.keko[a];
        this.keko[a] = this.keko[b];
        this.keko[a].setKekoI(a);
        this.keko[b] = tmp;
        this.keko[b].setKekoI(b);
    }

    public void lisaa(VerkkoSolmu solmu) {
        this.keonKoko++;
        int i = this.keonKoko;
        while (i > 1 && this.keko[this.vanhempi(i)].getArvio() > solmu.getArvio()) {
            this.keko[i] = keko[this.vanhempi(i)];
            this.keko[i].setKekoI(i);
            i = this.vanhempi(i);
        }
        this.keko[i] = solmu;
        solmu.setKekoI(i);

    }

    public boolean tyhja() {
        return this.keonKoko == 0;
    }

    public VerkkoSolmu otaPienin() {

        VerkkoSolmu eka = this.keko[1];
        this.keko[1] = this.keko[this.keonKoko];
        this.keko[1].setKekoI(1);

        this.keonKoko--;
        this.heapify(1);
        return eka;
    }

    public String toString() {
        String out = "";
        for (int i = 1; i <= this.keonKoko; i++) {
            out += "id: " + this.keko[i].getId() + " a: " + this.keko[i].getArvio() + ", ";
        }
        return out;
    }
}
