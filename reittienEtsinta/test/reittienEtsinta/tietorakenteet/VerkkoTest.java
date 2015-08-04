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
public class VerkkoTest {
    private Verkko verkko;
    
    public VerkkoTest() {
    }
    
    @Before
    public void setUp() {
        this.verkko = new Verkko(new int[]{1, 2, 4, 5, 1, 3, 1}, new int[]{2, 3, 5, 6, 6, 0, 4}, new int[]{4, 3, 1, 3,4,1,1}, 8, new int[]{3, 0, 4, 4, 3, 3, 0, 10}, new int[]{2, 0, 0, 3, 3, 4, 4, 10});
        
    }

    /**
     * Test of aStar method, of class Verkko.
     */
    @Test
    public void testAStar() {
        verkko.aStar(1, 0);
        assertEquals(8,verkko.getSolmut()[0].getAlkuun());
        assertEquals(0, verkko.getSolmut()[1].getAlkuun());
        assertEquals(7, verkko.getSolmut()[3].getAlkuun());
        assertEquals(Long.MAX_VALUE, verkko.getSolmut()[7].getAlkuun());
        
    }
    
    @Test
    public void arvioTest(){
        verkko.aStar(1, 0);
        assertEquals(3, verkko.getSolmut()[1].getArvio());
    }
    
    @Test
    public void pysahtymisTest(){
        verkko.aStar(3, 2);
        assertEquals(Long.MAX_VALUE, verkko.getSolmut()[1].getAlkuun());
    }


    
}
