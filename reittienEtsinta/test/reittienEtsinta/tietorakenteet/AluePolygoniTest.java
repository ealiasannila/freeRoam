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
public class AluePolygoniTest {

    private AluePolygoni polygoni;

    public AluePolygoniTest() {
    }

    @Before
    public void setUp() {
        this.polygoni = new AluePolygoni(4);
        this.polygoni.lisaaPiste(0, 0, 1);
        this.polygoni.lisaaPiste(1, 0, 2);
        this.polygoni.lisaaPiste(1, 1, 3);
        this.polygoni.lisaaPiste(0, 1, 4);

    }

    /**
     * Testaa onko piste polygonin sisällä
     */
    @Test
    public void testPisteSisallaOn() {
        assertTrue(this.polygoni.pisteSisalla(0.5, 0.5));
    }

    @Test
    public void testPisteSisallaEi() {
        assertFalse(this.polygoni.pisteSisalla(1.5, 1.5));
    }

    @Test
    public void testPisteSisallaItaRajalla() {
        assertTrue(this.polygoni.pisteSisalla(0.5, 1.0));
    }

    @Test
    public void testPisteSisallaLansiRajalla() {
        assertTrue(this.polygoni.pisteSisalla(0.5, 0));
    }

    @Test
    public void testPisteSisallaPohjoisRajalla() {
        assertTrue(this.polygoni.pisteSisalla(1, 0.5));
    }

    @Test
    public void testPisteSisallaLounaisKulmassa() {
        assertTrue(this.polygoni.pisteSisalla(0.0, 0.0));
    }
     @Test
    public void testPisteSisallaKoilisKulmassa() {
        assertTrue(this.polygoni.pisteSisalla(1.0, 1.0));
    }
     @Test
    public void testPisteSisallaKaakkoisKulmassa() {
        assertTrue(this.polygoni.pisteSisalla(0.0, 1.0));
    }
     @Test
    public void testPisteSisallaLuoteisKulmassa() {
        assertTrue(this.polygoni.pisteSisalla(1.0, 0.0));
    }

}
