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
 * Testaa reitti luokkaa
 *
 * @author elias
 */
public class ReittiTest {

    private Reitti reitti;

    public ReittiTest() {
    }

    /**
     * luo reitin
     */
    @Before
    public void setUp() {
        int[] aika = new int[]{0, 1};
        double[] lat = new double[]{0.0, 0.0};
        double[] lon = new double[]{0.0, 1.0};
        this.reitti = new Reitti(lat, lon, aika);

    }

   
    @Test
    public void testVauhti() {
        assertEquals(1, this.reitti.vauhti(0, 1), 0.001);

    }

    @Test
    public void testMatka() {
        assertEquals(1, this.reitti.matka(0, 1), 0.001);
    }

    @Test
    public void testAika() {
        assertEquals(1, this.reitti.aika(0, 1), 0.001);
    }

    

}
