/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reittienEtsinta.tietorakenteet;

import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author elias
 */
public class VerkkoPolygoniTest {

    private VerkkoPolygoni verkko;
    private MaastoKirjastoPolygoni kirjasto;

    public VerkkoPolygoniTest() {
    }

    @Before
    public void setUp() {
        int[] maastorajat = new int[3];
        maastorajat[0] = 10;
        maastorajat[1] = 20;
        maastorajat[2] = 30;
        kirjasto = new MaastoKirjastoPolygoni(maastorajat);
        this.verkko = new VerkkoPolygoni(4, 0, kirjasto);
        
        this.verkko.lisaaKaari(0, 1, 0, 0, 0, 1);
        this.verkko.lisaaKaari(1, 2, 0, 1, 1, 1);
        
    }

    /**
     * Test of lisaaKaari method, of class VerkkoPolygoni.
     */
    @Test
    public void testLisaaKaari() {
        assertEquals(("[   ][ 0 ][ 1 ][ 2 ][ 3 ]\n"
                + "[ 0 ][0.0][100000.0][0.0][0.0]\n"
                + "[ 1 ][100000.0][0.0][100000.0][0.0]\n"
                + "[ 2 ][0.0][100000.0][0.0][0.0]\n"
                + "[ 3 ][0.0][0.0][0.0][0.0]\n"),
        verkko.toString()
    

    );
    
    }

    /**
     * Test of aStar method, of class VerkkoPolygoni.
     */
    @Test
    public void testAStar() {
        verkko.aStar(2);
        assertEquals("[1, 2, -1, -1]",Arrays.toString(verkko.getPolku()));
    }

    /**
     * Test of lyhyinReitti method, of class VerkkoPolygoni.
     */
    @Test
    public void testLyhyinReitti() {
    }

    /**
     * Test of getVerkko method, of class VerkkoPolygoni.
     */
    @Test
    public void testGetVerkko() {
    }

    /**
     * Test of toString method, of class VerkkoPolygoni.
     */
    @Test
    public void testToString() {
    }

}
