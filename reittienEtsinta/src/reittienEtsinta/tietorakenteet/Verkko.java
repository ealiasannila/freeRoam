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
import java.util.Stack;
import reittienEtsinta.Apumetodit;
import reittienEtsinta.KuvanLukija;
import reittienEtsinta.toteutuneetReitit.MaastoKirjasto;
import reittienEtsinta.toteutuneetReitit.Reitti;

/**
 *
 * @author elias
 */
public class Verkko {

    private VerkkoSolmu[][] solmut;
    private MaastoKirjasto maastoKirjasto;
    private KuvanLukija kuvanLukija;

    public Verkko(int koko, MaastoKirjasto maastokirjasto, KuvanLukija kuvanLukija) {

        this.maastoKirjasto = maastokirjasto;
        this.kuvanLukija = kuvanLukija;
        this.solmut = new VerkkoSolmu[koko][koko];

    }

    private void alustus(int lahtoX, int lahtoY, int maaliX, int maaliY) {

        for (int y = 0; y < this.solmut.length; y++) {
            for (int x = 0; x < this.solmut.length; x++) {
                this.solmut[y][x] = new VerkkoSolmu(x, y, this.kuvanLukija.getMaasto(x, y), Apumetodit.pisteidenEtaisyys(lahtoX, lahtoY, maaliX, maaliY));
            }
        }

        this.solmut[lahtoY][lahtoX].setAlkuun(0);

    }

    private boolean loysaa(int solmuX, int solmuY, int naapuriX, int naapuriY) {

        VerkkoSolmu solmu = solmut[solmuY][solmuX];
        VerkkoSolmu naapuri = solmut[naapuriY][naapuriX];
        if (solmu.getAlkuun() == Double.MAX_VALUE || this.maastoKirjasto.haeVauhti(naapuri.getMaasto()) < 0.0001) { //jos vauhti on liian pieni ei solmuun voi mennä
            return false;
        }

        double kaarenVauhti = 1 / (this.maastoKirjasto.haeVauhti(solmu.getMaasto()) + this.maastoKirjasto.haeVauhti(naapuri.getMaasto())) / 2;

        //liikutaan vinottain
        if (solmuX != naapuriX && solmuY != naapuriY) {
            kaarenVauhti *= Math.sqrt(2.0);
        }

        if (naapuri.getAlkuun() > solmu.getAlkuun() + kaarenVauhti) {
            naapuri.setAlkuun(solmu.getAlkuun() + kaarenVauhti);

            naapuri.setPolkuX(solmuX);
            naapuri.setPolkuY(solmuY);
            return true;
        }
        return false;
    }

    public boolean aStar(int lahtoX, int lahtoY, int maaliX, int maaliY) {
        alustus(lahtoX, lahtoY, maaliX, maaliY);

        MinimiKeko keko = new MinimiKeko(solmut.length * solmut.length + 1);

        for (int y = 0; y < solmut.length; y++) {
            for (int x = 0; x < solmut.length; x++) {
                keko.lisaa(solmut[y][x]);
            }
        }

        while (!keko.tyhja()) {
            VerkkoSolmu solmu = keko.otaPienin();
            int solmuX = solmu.getX();
            int solmuY = solmu.getY();

            for (int nY = -1; nY <= 1; nY++) {
                for (int nX = -1; nX <= 1; nX++) {
                    if (nY == 0 && nX == 0) {
                        continue;
                    }
                    int naapuriX = solmuX + nX;
                    int naapuriY = solmuY + nY;

                    if (naapuriX < 0 || naapuriX >= solmut.length || naapuriY < 0 || naapuriY >= solmut.length) {
                        continue;
                    }

                    if (loysaa(solmuX, solmuY, naapuriX, naapuriY)) {
                        keko.paivita(solmut[naapuriY][naapuriX]);
                    }

                }

            }
        }

        return true;
    }

    public Reitti lyhyinReitti(int lahtoX, int lahtoY, int maaliX, int maaliY) {
        Stack<Integer> pinoX = new Stack<>();
        Stack<Integer> pinoY = new Stack<>();

        int x = this.solmut[maaliY][maaliX].getPolkuX();
        int y = this.solmut[maaliY][maaliX].getPolkuY();

        while (x != -1 && y != -1) {
            //System.out.println("--");
            pinoX.push(x);
            pinoY.push(y);

            
            x = this.solmut[y][x].getPolkuX();
            y = this.solmut[y][x].getPolkuY();
            //System.out.println(x);
            //System.out.println(y);
            
        }
        
        int[] reittiX = new int[pinoX.size()];
        int[] reittiY = new int[pinoY.size()];
        int[] aika = new int[pinoY.size()];

        for (int i = 0; i < reittiX.length; i++) {
            reittiX[i] = pinoX.pop();
            reittiY[i] = pinoY.pop();
            aika[i] = (int) this.solmut[reittiY[i]][reittiX[i]].getAlkuun();
        }
        return new Reitti(reittiX, reittiY, aika);

    }

    public VerkkoSolmu getSolmu(int x, int y) {
        return this.solmut[y][x];
    }

    public VerkkoSolmu[][] getSolmut() {
        return this.solmut;
    }

    public String toString() {
        String tuloste = "";
        for (int i = 0; i < this.solmut.length; i++) {
            for (int j = 0; j < this.solmut.length; j++) {
                    tuloste += String.format("[%02d:%02d->%02d:%02d]", solmut[i][j].getX(),solmut[i][j].getY(),solmut[i][j].getPolkuX(),solmut[i][j].getPolkuY());
               
            }
            tuloste += "\n";
        }
        return tuloste;
    }

}
