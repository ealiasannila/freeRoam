/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reittienEtsinta.tietorakenteet;

/**
 * Pino tietorakenteen toteutus.
 *
 * @author elias
 */
public class Pino<A> {

    private Object[] pino;
    private int pinta;

    public Pino(int koko) {
        this.pino = new Object[koko];
        this.pinta = 0;
    }

    public void lisaa(A i) {
        this.pino[pinta] = i;
        this.pinta++;
    }

    public int koko(){
        return this.pinta;
    }
    
    public A ota() {
        this.pinta--;
        return (A) this.pino[pinta];
    }

    public boolean tyhja() {
        return this.pinta == 0;
    }

}
