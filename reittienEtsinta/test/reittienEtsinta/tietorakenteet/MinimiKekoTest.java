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
            VerkkoSolmu solmu = new VerkkoSolmu(i, -1, random.nextInt(100), 0, 0);
            solmu.setLoppuun(random.nextInt(100));

            minkek.lisaa(solmu);
        }

    }

    /**
     * Test of paivita method, of class MinimiKeko.
     */
    @Test
    public void testPaivita() {
        MinimiKeko uusiKeko = new MinimiKeko(100);
        VerkkoSolmu paivitettava = new VerkkoSolmu(1, -1, 5, 0, 0);
        uusiKeko.lisaa(paivitettava);
        uusiKeko.lisaa(new VerkkoSolmu(2, -1, 2, 0, 0));
        uusiKeko.lisaa(new VerkkoSolmu(3, -1, 4, 0, 0));
        uusiKeko.lisaa(new VerkkoSolmu(4, -1, 2, 0, 0));
        uusiKeko.lisaa(new VerkkoSolmu(3, -1, 10, 0, 0));

        paivitettava.setAlkuun(1);
        
        uusiKeko.paivita(paivitettava);
        
        
        assertEquals("id: 1 a: 1, id: 2 a: 2, id: 3 a: 4, id: 4 a: 2, id: 3 a: 10, ", uusiKeko.toString());

    }

    /**
     * Test of lisaa method, of class MinimiKeko.
     */
    @Test
    public void testLisaa() {
        MinimiKeko uusiKeko = new MinimiKeko(100);
        uusiKeko.lisaa(new VerkkoSolmu(1, -1, 5, 0, 0));
        uusiKeko.lisaa(new VerkkoSolmu(2, -1, 2, 0, 0));
        uusiKeko.lisaa(new VerkkoSolmu(3, -1, 4, 0, 0));
        uusiKeko.lisaa(new VerkkoSolmu(4, -1, 1, 0, 0));
        uusiKeko.lisaa(new VerkkoSolmu(3, -1, 10, 0, 0));

        assertEquals("id: 4 a: 1, id: 2 a: 2, id: 3 a: 4, id: 1 a: 5, id: 3 a: 10, ", uusiKeko.toString());

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
        long edellinen = -1;
        while (!this.minkek.tyhja()) {
            long eka = minkek.otaPienin().getArvio();
            if (edellinen != -1) {
                assertTrue(edellinen <= eka);
            }
            edellinen = eka;

        }
    }

}
