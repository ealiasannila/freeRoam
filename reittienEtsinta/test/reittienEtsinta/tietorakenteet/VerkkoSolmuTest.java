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
public class VerkkoSolmuTest {

    private VerkkoSolmu solmu;

    public VerkkoSolmuTest() {
    }

    @Before
    public void setUp() {
        this.solmu = new VerkkoSolmu(0, 0, 0, 10.0);
    }

    @Test
    public void testGetArvio() {
        assertTrue(Double.MAX_VALUE - this.solmu.getArvio() < 0.001);
        this.solmu.setAlkuun(10.0);
        assertTrue(20.0 - this.solmu.getArvio() < 0.001);

    }

}
