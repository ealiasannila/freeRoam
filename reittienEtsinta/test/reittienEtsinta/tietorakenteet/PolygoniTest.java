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
public class PolygoniTest {

    private Polygoni polygoni;
    private Polygoni polygoni2;

    public PolygoniTest() {
    }

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
     * Test of pisteenEtaisyys method, of class Polygoni.
     */
    @Test
    public void testPisteenEtaisyys1() {
        assertEquals(polygoni.pisteenEtaisyys(0, 2), 1, 0.0001);
    }

    @Test
    public void testPisteenEtaisyys2() {
        assertEquals(polygoni.pisteenEtaisyys(0, 1), 0, 0.0001);
    }

    /**
     * Test of getPisteita method, of class Polygoni.
     */
    @Test
    public void testGetPisteita() {
        assertEquals(4, this.polygoni.getPisteita());
    }

    /**
     * Test of getLatmin method, of class Polygoni.
     */
    @Test
    public void testGetLatmin() {
        assertEquals(0, this.polygoni.getLatmin(), 0.00001);
    }

    /**
     * Test of getLatmax method, of class Polygoni.
     */
    @Test
    public void testGetLatmax() {
        assertEquals(1, this.polygoni.getLatmax(), 0.00001);
    }

    /**
     * Test of getLonmin method, of class Polygoni.
     */
    @Test
    public void testGetLonmin() {

        assertEquals(0, this.polygoni.getLonmin(), 0.00001);
    }

    /**
     * Test of getLonmax method, of class Polygoni.
     */
    @Test
    public void testGetLonmax() {

        assertEquals(1, this.polygoni.getLonmax(), 0.00001);
    }

    /**
     * Test of viivaLeikkaaPolygonin method, of class Polygoni.
     */
    @Test
    public void testJanaLeikkaaAluePolygoninLahtoJaMaaliUlkona() {
        assertTrue(this.polygoni.janaLeikkaaPolygonin(1.2, 0.5, 0.5, 1.2));
    }

    @Test
    public void testJanaLeikkaaAluePolygoninLahtoJaMaaliUlkonaEiLeikkaa() {
        assertFalse(this.polygoni.janaLeikkaaPolygonin(1.5, 1.5, 0.5, 1.5));
    }

    @Test
    public void testJanaLeikkaaAluePolygoninLahtoJaMaaliSisalla() {
        assertTrue(this.polygoni.janaLeikkaaPolygonin(0.5, 0.5, 0.5, 1.5));
    }

    @Test
    public void testJanaLeikkaaAluePolygoninLahtoJaMaaliSisallaKaartaPitkin() {
        assertTrue(this.polygoni.janaLeikkaaPolygonin(0.5, 1, 0.6, 1));

    }

    @Test
    public void testJanaLeikkaaAluePolygoninLahtoJaMaaliUlkonKaartaPitkin() {
        assertTrue(this.polygoni.janaLeikkaaPolygonin(-0.5, 1, 1.5, 1));
    }

    @Test
    public void testJanaLeikkaaAluePolygoninLahtoJaMaaliSisallaEiLeikkaa() {
        assertFalse(this.polygoni.janaLeikkaaPolygonin(0.5, 0.5, 0.7, 0.7));
    }

   

    @Test
    public void testJanaLeikkaaPolygoninPysahtyySolmuun() {
        assertFalse(this.polygoni2.janaLeikkaaPolygonin(0.0, 1.0, 1.0, 1.0));

    }
}
