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
 * Testaa polygoni luokkaa
 *
 * @author elias
 */
public class PolygoniTest {

    private Polygoni polygoni;
    private Polygoni polygoni2;

    public PolygoniTest() {
    }

    /**
     * luodaan polygonit
     */
    @Before
    public void setUp() {

        this.polygoni = new AluePolygoni(4);
        this.polygoni.lisaaPiste(0, 0, 1);
        this.polygoni.lisaaPiste(1, 0, 2);
        this.polygoni.lisaaPiste(1, 1, 3);
        this.polygoni.lisaaPiste(0, 1, 4);

        this.polygoni2 = new Polygoni(3);
        this.polygoni2.lisaaPiste(0, 0, 0);
        this.polygoni2.lisaaPiste(0, 1, 0);
        this.polygoni2.lisaaPiste(0, 2, 0);

    }

    /**
     * Testataaan pisteen etäisyyttä
     */
    @Test
    public void testPisteenEtaisyys() {
        assertEquals(polygoni.pisteenEtaisyys(0, 2), 1, 0.0001);
    }

    @Test
    public void testPisteenEtaisyysPisteViivalla() {
        assertEquals(polygoni.pisteenEtaisyys(0, 1), 0, 0.0001);
    }

    /**
     * Testaa että polygonin pisteitä muuttuja on saanut oikean arvon pisteitä
     * lisättäessä
     */
    @Test
    public void testGetPisteita() {
        assertEquals(4, this.polygoni.getPisteita());
    }

    /**
     * Testaa polygonin boundingboxia
     */
    @Test
    public void testGetLatmin() {
        assertEquals(0, this.polygoni.getLatmin(), 0.00001);
    }

    /**
     * Testaa polygonin boundingboxia
     */
    @Test
    public void testGetLatmax() {
        assertEquals(1, this.polygoni.getLatmax(), 0.00001);
    }

    /**
     * Testaa polygonin boundingboxia
     */
    @Test
    public void testGetLonmin() {

        assertEquals(0, this.polygoni.getLonmin(), 0.00001);
    }

    /**
     * Testaa polygonin boundingboxia
     */
    @Test
    public void testGetLonmax() {

        assertEquals(1, this.polygoni.getLonmax(), 0.00001);
    }

    /**
     * Tilanne jossa janan lähtö ja loppupisteet ovat polygonin ulkopuolella,
     * mutta jana leikkaa polygonin kulman
     */
    @Test
    public void testJanaLeikkaaAluePolygoninLahtoJaMaaliUlkona() {
        assertTrue(this.polygoni.janaLeikkaaPolygonin(1.2, 0.5, 0.5, 1.2));
    }

    /**
     * tilanne jossa jane ei leikka polygonia
     */
    @Test
    public void testJanaLeikkaaAluePolygoninLahtoJaMaaliUlkonaEiLeikkaa() {
        assertFalse(this.polygoni.janaLeikkaaPolygonin(1.5, 1.5, 0.5, 1.5));
    }

    /**
     * tilanne jossa jana lähtee polygonin sisältä
     */
    @Test
    public void testJanaLeikkaaAluePolygoninLahtoSisalla() {
        assertTrue(this.polygoni.janaLeikkaaPolygonin(0.5, 0.5, 0.5, 1.5));
    }

    /**
     * Tilanne jossa jana sijaitsee kokonaan polygonin kaarella
     */
    @Test
    public void testJanaLeikkaaAluePolygoninLahtoJaMaaliSisallaKaartaPitkin() {
        assertTrue(this.polygoni.janaLeikkaaPolygonin(0.5, 1, 0.6, 1));

    }

    /**
     * tilanne jossa polygonin kaari sijaitsee kokonaan janalla
     */
    @Test
    public void testJanaLeikkaaAluePolygoninLahtoJaMaaliUlkonKaartaPitkin() {
        assertTrue(this.polygoni.janaLeikkaaPolygonin(-0.5, 1, 1.5, 1));
    }

    /**
     * tilanne jossa jana on kokonaan polygonin sisällä
     */
    @Test
    public void testJanaLeikkaaAluePolygoninLahtoJaMaaliSisallaEiLeikkaa() {
        assertFalse(this.polygoni.janaLeikkaaPolygonin(0.5, 0.5, 0.7, 0.7));
    }

    /**
     * tilanne jossa jana kohtaa polygonin solmussa. Tätä ei haluta tulkita leikkaukseksi
     */
    @Test
    public void testJanaLeikkaaPolygoninPysahtyySolmuun() {
        assertFalse(this.polygoni2.janaLeikkaaPolygonin(0.0, 1.0, 1.0, 1.0));

    }
}
