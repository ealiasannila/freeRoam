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
        this.solmu = new VerkkoSolmu(1, -1, Long.MAX_VALUE, 0, 0);
    }

    @Test
    public void testGetArvio() {
        this.solmu.setLoppuun(10);
        assertEquals(Long.MAX_VALUE, this.solmu.getArvio());
        this.solmu.setAlkuun(10);
        assertEquals(20, this.solmu.getArvio());

    }

}
