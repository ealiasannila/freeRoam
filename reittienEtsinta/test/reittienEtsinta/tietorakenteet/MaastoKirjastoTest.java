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
public class MaastoKirjastoTest {

    private Maastokirjasto kirjasto;
    private Reitti reitti;
    private Reitti reittiviiva;
    private Reitti reittiulko;
    private Lista<Polygoni> polygonit;

    public MaastoKirjastoTest() {
    }

    @Before
    public void setUp() {

        kirjasto = new Maastokirjasto(3, 0.1);
        reitti = new Reitti(new double[]{0.1, 0.2}, new double[]{0.1, 0.2}, new int[]{0, 1});
        reittiviiva = new Reitti(new double[]{1.1, 1.3}, new double[]{1.1, 1.3}, new int[]{0, 1});
        reittiulko = new Reitti(new double[]{1.5, 1.7}, new double[]{1.5, 1.7}, new int[]{0, 1});

        this.polygonit = new Lista(2);
        this.polygonit.lisaa(new AluePolygoni(4));
        this.polygonit.ota(0).lisaaPiste(0, 0, 1);
        this.polygonit.ota(0).lisaaPiste(0, 1, 2);
        this.polygonit.ota(0).lisaaPiste(1, 1, 3);
        this.polygonit.ota(0).lisaaPiste(1, 0, 4);

        polygonit.lisaa(new Polygoni(2));
        polygonit.ota(1).lisaaPiste(1.15, 1.15, 5);
        polygonit.ota(1).lisaaPiste(1.35, 1.35, 6);

        polygonit.ota(0).setMaasto(0);
        polygonit.ota(1).setMaasto(1);
        
        this.kirjasto.lisaaReitti(reitti, polygonit);
        this.kirjasto.lisaaReitti(reittiviiva, polygonit);
        this.kirjasto.lisaaReitti(reittiulko, polygonit);

    }

    /**
     * Test of haeVauhti method, of class MaastoKirjastoPolygoni.
     */
    @Test
    public void testHaeVauhtiAlue() {

        assertTrue(0.14142135623730953 - this.kirjasto.haeVauhti(0, false) < 0.0001);
        assertTrue(0.14142135623730953 - this.kirjasto.haeVauhti(0, false) > -0.0001);

        assertTrue(0.14142135623730953 * 2 - this.kirjasto.haeVauhti(0, true) < 0.0001);
        assertTrue(0.14142135623730953 * 2 - this.kirjasto.haeVauhti(0, true) > -0.0001);
    }

    @Test
    public void testHaeVauhtiViiva() {

        assertTrue(0.14142135623730953 * 2 - this.kirjasto.haeVauhti(1, true) < 0.0001);
        assertTrue(0.14142135623730953 * 2 - this.kirjasto.haeVauhti(1, true) > -0.0001);
        assertTrue(0.14142135623730953 * 2 - this.kirjasto.haeVauhti(1, false) < 0.0001);
        assertTrue(0.14142135623730953 * 2 - this.kirjasto.haeVauhti(1, false) > -0.0001);
    }

}
