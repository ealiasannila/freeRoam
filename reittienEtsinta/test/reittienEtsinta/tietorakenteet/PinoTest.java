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
public class PinoTest {

    private Pino pino;

    public PinoTest() {
    }

    @Before
    public void setUp() {
        this.pino = new Pino(10);
    }

    /**
     * Test of lisaa method, of class Pino.
     */
    @Test
    public void testLisaa() {
        assertTrue(this.pino.tyhja());

        this.pino.lisaa(1);
        assertFalse(this.pino.tyhja());
        
    }

    /**
     * Test of ota method, of class Pino.
     */
    @Test
    public void testOta() {
        this.pino.lisaa(1);
        this.pino.lisaa(2);
        this.pino.lisaa(3);
        
        assertEquals(3, this.pino.ota());
        assertEquals(2, this.pino.ota());
        assertEquals(1, this.pino.ota());
        
        assertTrue(this.pino.tyhja());
        
    }

}
