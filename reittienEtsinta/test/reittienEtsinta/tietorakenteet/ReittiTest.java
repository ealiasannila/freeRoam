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
public class ReittiTest {
    private Reitti reitti;
    
    public ReittiTest() {
    }
    
    @Before
    public void setUp() {
    int [] aika = new int[] {0, 1};
    double[] lat = new double[] {0.0, 0.0};
    double[] lon = new double[] {0.0, 1.0};
    this.reitti = new Reitti(lat, lon, aika);
            
    }

    /**
     * Test of getLon method, of class Reitti.
     */
    @Test
    public void testGetLon() {
    }

    /**
     * Test of getLat method, of class Reitti.
     */
    @Test
    public void testGetLat() {
    }

    /**
     * Test of getAika method, of class Reitti.
     */
    @Test
    public void testGetAika() {
    }

    /**
     * Test of vauhti method, of class Reitti.
     */
    @Test
    public void testVauhti() {
    assertEquals(1, this.reitti.vauhti(0, 1), 0.001);
    
    }

    /**
     * Test of toString method, of class Reitti.
     */
    @Test
    public void testToString() {
    }
    
}
