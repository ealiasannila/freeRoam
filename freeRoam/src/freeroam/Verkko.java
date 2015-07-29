/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package freeroam;

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

    int[] polku;
    long[] alkuun;
    long[] loppuun;
    int[][] vm;
    int[] solmuX;
    int[] solmuY;

    public Verkko(int[] mista, int[] minne, int[] painot, int n, int[] solmuX, int[] solmuY) {
        luoVm(mista, minne, painot, n);
        this.solmuX = solmuX;
        this.solmuY = solmuY;
    }

    private void luoVm(int[] mista, int[] minne, int[] paino, int n) {
        this.vm = new int[n][n];
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
                Math.pow(Math.abs(this.solmuY[solmu] - this.solmuY[maali]), 2)
                + Math.pow(Math.abs(this.solmuX[solmu] - this.solmuX[maali]), 2));

    }

    private void alustus(int lahtoSolmu, int maaliSolmu) {
        this.polku = new int[this.vm.length];
        this.alkuun = new long[this.vm.length];
        this.loppuun = new long[this.vm.length];

        for (int i = 0; i < this.polku.length; i++) {
            this.alkuun[i] = Long.MAX_VALUE;
            this.polku[i] = -1;
            this.loppuun[i] = this.arvioiEtaisyys(i, maaliSolmu);
        }
        this.alkuun[lahtoSolmu] = 0;

    }

    private boolean loysaa(int solmu, int naapuri) {
        if (alkuun[solmu] == Long.MAX_VALUE) {
            return false;
        }
        if (alkuun[naapuri] > alkuun[solmu] + vm[solmu][naapuri]) {
            alkuun[naapuri] = alkuun[solmu] + vm[solmu][naapuri];
            polku[naapuri] = solmu;
            return true;
        }
        return false;
    }

    public boolean aStar(int lahtoSolmu, int maaliSolmu) {
        alustus(lahtoSolmu, maaliSolmu);

        PriorityQueue<Integer> keko = new PriorityQueue(vm.length, new Comparator<Integer>() {
            @Override
            public int compare(Integer a, Integer b) {
                if (alkuun[a] + loppuun[a] > alkuun[b] + loppuun[b]) {
                    return 1;
                } else if (alkuun[a] + loppuun[a] < alkuun[b] + loppuun[b]) {
                    return -1;
                } else {
                    return 0;
                }

            }
        });

        for (int i = 0; i < vm.length; i++) {
            keko.add(i);
        }

        while (!keko.isEmpty()) {
            int solmu = keko.poll();

            for (int naapuri = 0; naapuri < vm.length; naapuri++) {
                
                if(solmu == maaliSolmu){    //voiko jo pysäyttää?
                    return true;
                }
                
                if (vm[solmu][naapuri] != 0) {
                    if (loysaa(solmu, naapuri)) {
                        keko.remove(naapuri);
                        keko.add(naapuri);
                    }

                }
            }
        }
        if (alkuun[maaliSolmu] == Long.MAX_VALUE) { //reittiä ei löytynyt
            return false;
        }

        return true;
    }

}
