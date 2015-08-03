/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reittienEtsinta.tietorakenteet;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

/**
 *
 * @author elias
 */
public class Verkko {

    private int[][] vm;
    private VerkkoSolmu[] solmut;
    private int[] solmuX;
    private int[] solmuY;

    public Verkko(int[] mista, int[] minne, int[] painot, int solmujenMaara, int[] solmuX, int[] solmuY) {
        luoVm(mista, minne, painot, solmujenMaara);
        this.solmut = new VerkkoSolmu[vm.length];
        this.solmuX = solmuX;
        this.solmuY = solmuY;
    }

    private void luoVm(int[] mista, int[] minne, int[] paino, int solmujenMaara) {
        this.vm = new int[solmujenMaara][solmujenMaara];
        for (int i = 0; i < mista.length; i++) {
            vm[mista[i]][minne[i]] = paino[i];
            vm[minne[i]][mista[i]] = paino[i];
        }
        for (int i = 0; i < vm.length; i++) {
            System.out.println(Arrays.toString(vm[i]));
        }
        System.out.println("---");

    }

    private long arvioiEtaisyys(int solmu, int maali) {

        return (long) Math.sqrt(
                Math.pow(Math.abs(this.solmut[solmu].getY() - this.solmut[maali].getY()), 2)
                + Math.pow(Math.abs(this.solmut[solmu].getX() - this.solmut[maali].getX()), 2));

    }

    private void alustus(int lahtoSolmu, int maaliSolmu) {

        for (int i = 0; i < this.vm.length; i++) {
            this.solmut[i] = new VerkkoSolmu(i, -1, Long.MAX_VALUE, this.solmuX[i], this.solmuY[i]);

        }
        for (int i = 0; i < this.vm.length; i++) {
            this.solmut[i].setLoppuun(this.arvioiEtaisyys(i, maaliSolmu));

        }
        this.solmut[lahtoSolmu].setAlkuun(0);

    }

    private boolean loysaa(int solmu, int naapuri) {
        if (solmut[solmu].getAlkuun() == Long.MAX_VALUE) {
            return false;
        }
        if (solmut[naapuri].getAlkuun() > solmut[solmu].getAlkuun() + vm[solmu][naapuri]) {
            solmut[naapuri].setAlkuun(solmut[solmu].getAlkuun() + vm[solmu][naapuri]);
            solmut[naapuri].setPolku(solmu);
            return true;
        }
        return false;
    }

    public boolean aStar(int lahtoSolmu, int maaliSolmu) {
        alustus(lahtoSolmu, maaliSolmu);

        MinimiKeko keko = new MinimiKeko(vm.length);

        for (int i = 0; i < vm.length; i++) {
            keko.lisaa(solmut[i]);
        }

        while (!keko.tyhja()) { 
            System.out.println(keko);

            int solmu = keko.otaPienin().getId();
            System.out.println(solmu);
            
            for (int naapuri = 0; naapuri < vm.length; naapuri++) {

                if (solmu == maaliSolmu) {    //voiko jo pysäyttää?
                    return true;
                }

                if (vm[solmu][naapuri] != 0) {
                    if (loysaa(solmu, naapuri)) {
                        keko.paivita(solmut[naapuri]); 
                    }

                }
            }
        }
        if (solmut[maaliSolmu].getAlkuun() == Long.MAX_VALUE) { //reittiä ei löytynyt
            return false;
        }

        return true;
    }

    public VerkkoSolmu[] getSolmut() {
        return this.solmut;
    }

}
