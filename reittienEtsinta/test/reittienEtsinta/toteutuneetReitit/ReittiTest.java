/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reittienEtsinta.toteutuneetReitit;

import raster.Reitti;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
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
    
        this.reitti = new Reitti(new int[]{0,100, 200}, new int[]{0,100, 100}, new int[] {0, 1, 4});
        
    }
    
/**
     * Test of vauhti method, of class Reitti.
     */
    @Test
    public void testVauhti() {
        assertTrue(Math.abs(60.35533905932738 - this.reitti.vauhti(0, 2))<0.001);
    }
    
}
