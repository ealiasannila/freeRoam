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
 * Testaa listan toimintaa
 * @author elias
 */
public class ListaTest {

    private Lista lista;
    private Polygoni poly1;
    private Polygoni poly2;

    public ListaTest() {
    }

    @Before
    public void setUp() {
        lista = new Lista(2);
        this.poly1 = new Polygoni(1);
        this.poly2 = new Polygoni(2);

    }

    /**
     * Testaa lisäystä
     */
    @Test
    public void testLisaa() {

        lista.lisaa(poly1);
        lista.lisaa(poly2);
        lista.lisaa(poly1);

        assertEquals(lista.ota(0), lista.ota(2));
        assertFalse(lista.ota(1) == poly1);
    }

    /**
     * Testaa ottamista
     */
    @Test
    public void testOta() {

        lista.lisaa(poly1);
        lista.lisaa(poly2);
        assertEquals(lista.ota(1), poly2);
        assertFalse(lista.ota(1) == poly1);
    }

    /**
     * Testaa kokoa
     */
    @Test
    public void testKoko() {

        lista.lisaa(poly1);
        lista.lisaa(poly2);
        lista.lisaa(poly2);
        assertEquals(lista.koko(), 3);
    }

}
