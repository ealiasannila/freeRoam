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
        kirjasto = new MaastoKirjastoPolygoni(3);
        this.verkko = new VerkkoPolygoni(4, kirjasto);

        this.verkko.lisaaKaari(0, 1, 1, 0, 0, 0, 1, true);
        this.verkko.lisaaKaari(1, 2, 1, 0, 1, 1, 1, true);

    }

    /**
     * Test of lisaaKaari method, of class VerkkoPolygoni.
     */
    @Test
    public void testLisaaKaari() {
        //System.out.println(verkko.toString());
        assertEquals(("[   ][ 0 ][ 1 ][ 2 ][ 3 ]\n"
                + "[ 0 ][max][100000.0][max][max]\n"
                + "[ 1 ][100000.0][max][100000.0][max]\n"
                + "[ 2 ][max][100000.0][max][max]\n"
                + "[ 3 ][max][max][max][max]\n"),
                verkko.toString()
        );

    }

    /**
     * Test of aStar method, of class VerkkoPolygoni.
     */
    @Test
    public void testAStar() {
        this.verkko.alustus(2, 0);
        verkko.aStar();
        assertEquals("[1, 2, -1, -1]", Arrays.toString(verkko.getPolku()));
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
