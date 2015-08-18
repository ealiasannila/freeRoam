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
public class PolygoniListaTest {

    private PolygoniLista lista;
    private Polygoni poly1;
    private Polygoni poly2;

    public PolygoniListaTest() {
    }

    @Before
    public void setUp() {
        lista = new PolygoniLista(2);
        this.poly1 = new Polygoni(1);
        this.poly2 = new Polygoni(2);

    }

    /**
     * Test of lisaa method, of class PolygoniLista.
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
     * Test of ota method, of class PolygoniLista.
     */
    @Test
    public void testOta() {

        lista.lisaa(poly1);
        lista.lisaa(poly2);
        assertEquals(lista.ota(1), poly2);
        assertFalse(lista.ota(1) == poly1);
    }

    /**
     * Test of koko method, of class PolygoniLista.
     */
    @Test
    public void testKoko() {

        lista.lisaa(poly1);
        lista.lisaa(poly2);
        lista.lisaa(poly2);
        assertEquals(lista.koko(), 3);
    }

}
