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
 * Testaa verkko luokkaa
 * @author elias
 */
public class VerkkoTest {

    private Verkko verkko;
    private Maastokirjasto kirjasto;

    public VerkkoTest() {
    }

    /**
     * luodaan verkko
     */
    @Before
    public void setUp() {
        kirjasto = new Maastokirjasto(3, 4);
        kirjasto.lisaaVauhti(0, 1);
        kirjasto.lisaaVauhti(1, 2);
        this.verkko = new Verkko(4, kirjasto);

        this.verkko.lisaaKaari(0, 1, 1, 0, 0, 0, 1, true);
        this.verkko.lisaaKaari(1, 2, 0, 0, 1, 1, 1, true);

    }

    /**
     * tarkistaa tuliko kaarien lisäys oikein
     */
    @Test
    public void testLisaaKaari() {
        //System.out.println(verkko.toString());
        assertEquals((
                  "[   ][ 0 ][ 1 ][ 2 ][ 3 ]\n"
                + "[ 0 ][max][0.5][max][max]\n"
                + "[ 1 ][0.5][max][1.0][max]\n"
                + "[ 2 ][max][1.0][max][max]\n"
                + "[ 3 ][max][max][max][max]\n"),
                verkko.toString()
        );

    }

    /**
     * Testaa tuliko AStarin ajamisen jälkeen alkuun ja polku taulukoihin oikeat arvot
     */
    @Test
    public void testAStar() {
        this.verkko.alustus(2, 0);
        verkko.aStar();
        assertEquals("[1, 2, -1, -1]", Arrays.toString(verkko.getPolku()));
        assertEquals("[1.5, 1.0, 0.0, 1.7976931348623157E308]", Arrays.toString(verkko.getAlkuun()));
    }

}
