/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reittienEtsinta.tietorakenteet;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author elias
 */
public class MinimiKekoPolygonTest {

    private int[] kekoindeksit;
    private double[] alkuun;
    private double[] loppuun;

    private MinimiKekoPolygon keko;

    public MinimiKekoPolygonTest() {
    }

    @Before
    public void setUp() {
        int n = 10;
        this.alkuun = new double[n];
        this.loppuun = new double[n];
        this.kekoindeksit = new int[n];

        for (int i = 0; i < n-1; i++) {
            this.loppuun[i] = n - i;
        }
        this.loppuun[n-1] = n-2;

        this.keko = new MinimiKekoPolygon(alkuun, loppuun, kekoindeksit);

    }

    /**
     * Test of paivita method, of class MinimiKekoPolygon.
     */
    @Test
    public void testPaivita() {
        for (int i = 0; i < this.alkuun.length; i++) {
            this.keko.lisaa(i);
        }
        this.loppuun[3] = 0.001;
        this.keko.paivita(3);
        
        assertEquals(3,this.keko.otaPienin());
    }

    /**
     * Test of lisaa method, of class MinimiKekoPolygon.
     */
    @Test
    public void testLisaa() {
        for (int i = 0; i < this.alkuun.length; i++) {
            this.keko.lisaa(i);
        }

        int edellinen = this.keko.otaPienin();
        for (int i = 0; i < 9; i++) {
            int pienin = this.keko.otaPienin();
            assertTrue(this.alkuun[pienin] + this.loppuun[pienin] >= this.alkuun[edellinen] + this.loppuun[edellinen]);
            edellinen = pienin;
        }

    }

    /**
     * Test of tyhja method, of class MinimiKekoPolygon.
     */
    @Test
    public void testTyhja() {
    }

    /**
     * Test of otaPienin method, of class MinimiKekoPolygon.
     */
    @Test
    public void testOtaPienin() {
    }

    /**
     * Test of toString method, of class MinimiKekoPolygon.
     */
    @Test
    public void testToString() {
    }

}
