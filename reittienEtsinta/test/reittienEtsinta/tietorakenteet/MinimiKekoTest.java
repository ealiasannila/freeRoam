/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reittienEtsinta.tietorakenteet;

import java.util.Random;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author elias
 */
public class MinimiKekoTest {

    MinimiKeko minkek;

    public MinimiKekoTest() {
    }

    @Before
    public void setUp() {
        minkek = new MinimiKeko(100);

        Random random = new Random();

        for (int i = 0; i < 100; i++) {
            VerkkoSolmu solmu = new VerkkoSolmu(0, 0, 0, random.nextInt(100));
            solmu.setAlkuun(random.nextInt(100));

            minkek.lisaa(solmu);
        }

    }

    /**
     * Test of paivita method, of class MinimiKeko.
     */
    @Test
    public void testPaivita() {
        MinimiKeko uusiKeko = new MinimiKeko(100);
        VerkkoSolmu paivitettava = new VerkkoSolmu(0, 0, 0, 0);
        paivitettava.setAlkuun(5.0);
        
        VerkkoSolmu solmu = new VerkkoSolmu(0, 0, 0, 0);
        solmu.setAlkuun(2);
        uusiKeko.lisaa(solmu);
        solmu = new VerkkoSolmu(0, 0, 0, 0);
        solmu.setAlkuun(4);
        uusiKeko.lisaa(solmu);
        solmu = new VerkkoSolmu(0, 0, 0, 0);
        solmu.setAlkuun(2);
        uusiKeko.lisaa(solmu);
        solmu = new VerkkoSolmu(0, 0, 0, 0);
        solmu.setAlkuun(10);
        uusiKeko.lisaa(solmu);
       
        paivitettava.setAlkuun(1);
       
        uusiKeko.paivita(paivitettava);
        
        assertEquals(" a: 2.0,  a: 4.0,  a: 2.0,  a: 10.0, ", uusiKeko.toString());

    }

    /**
     * Test of lisaa method, of class MinimiKeko.
     */
    @Test
    public void testLisaa() {
        MinimiKeko uusiKeko = new MinimiKeko(100);
        
        VerkkoSolmu solmu = new VerkkoSolmu(0, 0, 0, 0);
        solmu.setAlkuun(2);
        uusiKeko.lisaa(solmu);
        solmu = new VerkkoSolmu(0, 0, 0, 0);
        solmu.setAlkuun(4);
        uusiKeko.lisaa(solmu);
        solmu = new VerkkoSolmu(0, 0, 0, 0);
        solmu.setAlkuun(2);
        uusiKeko.lisaa(solmu);
        solmu = new VerkkoSolmu(0, 0, 0, 0);
        solmu.setAlkuun(10);
        uusiKeko.lisaa(solmu);

        assertEquals(" a: 2.0,  a: 4.0,  a: 2.0,  a: 10.0, ", uusiKeko.toString());
    }

    /**
     * Test of tyhja method, of class MinimiKeko.
     */
    @Test
    public void testTyhjaKunTyhja() {
        for (int i = 0; i < 100; i++) {
            this.minkek.otaPienin();
        }
        assertTrue(this.minkek.tyhja());
    }

    @Test
    public void testTyhjaKunEiTyhja() {
        for (int i = 0; i < 99; i++) {
            this.minkek.otaPienin();
        }
        assertFalse(this.minkek.tyhja());
    }

    /**
     * Test of otaPienin method, of class MinimiKeko.
     */
    @Test
    public void testOtaPienin() {
        double edellinen = -1.0;
        while (!this.minkek.tyhja()) {
            double eka = minkek.otaPienin().getArvio();
            if (edellinen != -1.0) {
                assertTrue(edellinen <= eka);
            }
            edellinen = eka;

        }
    }

}
