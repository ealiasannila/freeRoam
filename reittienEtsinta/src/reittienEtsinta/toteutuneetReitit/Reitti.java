/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reittienEtsinta.toteutuneetReitit;

/**
 *
 * @author elias
 */
public class Reitti {

    private int[] x;
    private int[] y;
    private int[] aika;

    public Reitti(int[] x, int[] y, int[] aika) {
        this.x = x;
        this.y = y;
        this.aika = aika;
    }

    private int pisteidenEtaisyys(int x1, int y1, int x2, int y2) {
        return (int) Math.sqrt(
                Math.pow(Math.abs(y1 - y2), 2)
                + Math.pow(Math.abs(x1 - x2), 2));

    }

    private int matka(int alku, int loppu) {
        int matka = 0;
        for (int i = alku; i < loppu; i++) {
            matka += this.pisteidenEtaisyys(this.x[i], this.y[i], this.x[i+1], this.y[i+1]);
        }
        return matka;
    }
    
    private int aika(int alku, int loppu) {
        return aika[loppu]-aika[alku];
    }
    
    public int vauhti(int alku, int loppu){
        return this.matka(alku, loppu)/this.aika(alku, loppu);
    }

}
