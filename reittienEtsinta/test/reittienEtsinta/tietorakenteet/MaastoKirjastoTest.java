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
 * Testaa maastokirjaston toimintaa
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

    /**
     * luo maastokirjaston ja polygonit
     */
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
     * Testaa että alueen sisällä kuljettassa tulee oikea vauhti
     */
    @Test
    public void testHaeVauhtiAlueSisa() {

        assertEquals(0.14142135623730953, this.kirjasto.haeVauhti(0, false), 0.0001);
    }
    /**
     * testaa että alueen reunaa pitkin kuljettaessa tulee suurempi alueen ja tuntemattoman vauhdeista
     */
    @Test
    public void testHaeVauhtiAlueUlko() {

        assertEquals(0.14142135623730953 * 2, this.kirjasto.haeVauhti(0, true), 0.0001);
    }
    /**
     * testaa että viivaa pitkin kuljettaessa tulee oikea vauhti
     */
    @Test
    public void testHaeVauhtiViiva() {

        assertEquals(0.14142135623730953 * 2, this.kirjasto.haeVauhti(1, true), 0.0001);
        assertEquals(0.14142135623730953 * 2, this.kirjasto.haeVauhti(1, false), 0.0001);
    }

}
