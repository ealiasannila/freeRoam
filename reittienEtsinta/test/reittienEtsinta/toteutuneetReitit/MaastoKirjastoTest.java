/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reittienEtsinta.toteutuneetReitit;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author elias
 */
public class MaastoKirjastoTest {

    private MaastoKirjasto kirjasto = new MaastoKirjasto();

    public MaastoKirjastoTest() {
    }

    @Before
    public void setUp() {
    }

    /**
     * Test of lisaaVauhti method, of class MaastoKirjasto.
     */
    @Test
    public void testLisaaReitti() { //mitenkähän tätäkin nyt testais...
        this.kirjasto.lisaaReitti(1, 0, 1, new Reitti(new int[]{0, 0}, new int[]{0, 5}, new int[]{0, 1}));
        assertTrue(Math.abs(5.0 - this.kirjasto.haeVauhti(1)) <0.001);
    }

    /**
     * Test of haeVauhti method, of class MaastoKirjasto.
     */
    @Test
    public void testHaeVauhti() {
        this.kirjasto.lisaaReitti(1, 0, 1, new Reitti(new int[]{0, 0}, new int[]{0, 1}, new int[]{0, 1}));
        this.kirjasto.lisaaReitti(1, 0, 1, new Reitti(new int[]{0, 0}, new int[]{0, 2}, new int[]{0, 1}));
        this.kirjasto.lisaaReitti(1, 0, 1, new Reitti(new int[]{0, 0}, new int[]{0, 3}, new int[]{0, 1}));
        this.kirjasto.lisaaReitti(1, 0, 1, new Reitti(new int[]{0, 0}, new int[]{0, 4}, new int[]{0, 1}));
        this.kirjasto.lisaaReitti(1, 0, 1, new Reitti(new int[]{0, 0}, new int[]{0, 5}, new int[]{0, 1}));

        assertTrue(Math.abs(3.0 - this.kirjasto.haeVauhti(1))<0.001);
    }

}
