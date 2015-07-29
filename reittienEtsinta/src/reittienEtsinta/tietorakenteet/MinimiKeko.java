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

    public int[] keko;
    private int keonKoko;

    public MinimiKeko(int koko) {
        this.keko = new int[koko+1];
        this.keonKoko = 0;
    }

    private int parentInd(int i) {
        return i / 2;
    }

    private int leftChildInd(int i) {
        return 2 * i;
    }

    private int rightChildInd(int i) {
        return 2 * i + 1;
    }

    private void heapify(int i) {
        int l = this.leftChildInd(i);
        int r = this.rightChildInd(i);
        int pienempi;

        if (r <= this.keonKoko) {
            if (this.keko[l] < this.keko[r]) {
                pienempi = l;
            } else {
                pienempi = r;
            }

            if (this.keko[i] > this.keko[pienempi]) {
                this.vaihda(i, pienempi);
                this.heapify(pienempi);
            }
        } else if (l == this.keonKoko && this.keko[i] > this.keko[l]) {
            this.vaihda(i, l);
        }
    }

    private void vaihda(int a, int b) {
        int tmp = this.keko[a];
        this.keko[a] = this.keko[b];
        this.keko[b] = tmp;
    }

    public void lisaa(int solmu) {
        this.keonKoko++;
        int i = this.keonKoko;
        while (i > 1 && this.keko[this.parentInd(i)] > solmu) {
            this.keko[i] = keko[this.parentInd(i)];
            i = this.parentInd(i);
        }
        this.keko[i] = solmu;

    }

    public void paivita(int i, int uusiArvo) {
        if (uusiArvo < this.keko[i]) {
            this.keko[i] = uusiArvo;
            while (i > 1 && this.keko[this.parentInd(i)] > this.keko[i]) {
                this.vaihda(i, this.parentInd(i));
                i = this.parentInd(i);
            }
        }

    }

    public boolean tyhja() {
        return this.keonKoko == 0;
    }

    public int otaPienin() {

        int eka = this.keko[1];
        this.keko[1] = this.keko[this.keonKoko];
        this.keonKoko--;
        this.heapify(1);
        return eka;
    }
}
