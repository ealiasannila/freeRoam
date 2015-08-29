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
    public void testPisteenEtaisyys() {
        assertTrue(polygoni.pisteenEtaisyys(0, 2) - 1 < 0.0001);
        assertTrue(polygoni.pisteenEtaisyys(0, 2) - 1 > -0.0001);

        assertTrue(polygoni.pisteenEtaisyys(2, 2) - Math.sqrt(2) < 0.0001);
        assertTrue(polygoni.pisteenEtaisyys(2, 2) - Math.sqrt(2) > -0.0001);
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
        assertTrue(0 - this.polygoni.getLatmin() < 0.00001);
        assertTrue(0 - this.polygoni.getLatmin() > -0.00001);
    }

    /**
     * Test of getLatmax method, of class Polygoni.
     */
    @Test
    public void testGetLatmax() {
        assertTrue(1 - this.polygoni.getLatmax() < 0.00001);
        assertTrue(1 - this.polygoni.getLatmax() > -0.00001);
    }

    /**
     * Test of getLonmin method, of class Polygoni.
     */
    @Test
    public void testGetLonmin() {

        assertTrue(0 - this.polygoni.getLonmin() < 0.00001);
        assertTrue(0 - this.polygoni.getLonmin() > -0.00001);
    }

    /**
     * Test of getLonmax method, of class Polygoni.
     */
    @Test
    public void testGetLonmax() {

        assertTrue(1 - this.polygoni.getLonmax() < 0.00001);
        assertTrue(1 - this.polygoni.getLonmax() > -0.00001);
    }

    /**
     * Test of viivaLeikkaaPolygonin method, of class Polygoni.
     */
    @Test
    public void testViivaLeikkaaPolygonin() {
        System.out.println("1");
        assertTrue(this.polygoni.janaLeikkaaPolygonin(1.2, 0.5, 0.5, 1.2));
        System.out.println("2");
        assertFalse(this.polygoni.janaLeikkaaPolygonin(1.5, 1.5, 0.5, 1.5));
    }

    @Test
    public void testViivaLeikkaaPolygonin2() {
        assertFalse(this.polygoni2.janaLeikkaaPolygonin(0.0, 0.0, 1.0, 1.0));
        assertFalse(this.polygoni2.janaLeikkaaPolygonin(0.0, 0.0, 1.0, 0.0));

        assertFalse(this.polygoni2.janaLeikkaaPolygonin(0.0, 1.0, 1.0, 1.0));

    }

    /**
     * Test of lisaaPiste method, of class Polygoni.
     */
    @Test
    public void testLisaaPiste() {
    }

    /**
     * Test of getLat method, of class Polygoni.
     */
    @Test
    public void testGetLat() {
    }

    /**
     * Test of getLon method, of class Polygoni.
     */
    @Test
    public void testGetLon() {
    }

    /**
     * Test of getId method, of class Polygoni.
     */
    @Test
    public void testGetId() {
    }

    /**
     * Test of getBBlat method, of class Polygoni.
     */
    @Test
    public void testGetBBlat() {
    }

    /**
     * Test of getBBlon method, of class Polygoni.
     */
    @Test
    public void testGetBBlon() {
    }

    /**
     * Test of toString method, of class Polygoni.
     */
    @Test
    public void testToString() {
    }

}
