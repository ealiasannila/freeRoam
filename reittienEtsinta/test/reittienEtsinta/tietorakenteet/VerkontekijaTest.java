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
public class VerkontekijaTest {

    private Verkontekija verkontekija;

    public VerkontekijaTest() {
    }

    @Before
    public void setUp() {

    }

    /**
     * Test of luoVerkko method, of class Verkontekija.
     */
    @Test
    public void testLuoVerkkoAlue() {
        System.out.println("aluetest");
        Lista<Polygoni> polygonilista = new Lista<Polygoni>(2);
        Polygoni poly1 = new AluePolygoni(4);
        poly1.lisaaPiste(1, 1, 0);
        poly1.lisaaPiste(1, 0, 1);
        poly1.lisaaPiste(0, 0, 2);
        poly1.lisaaPiste(0, 1, 3);

        Polygoni poly2 = new AluePolygoni(4);
        poly2.lisaaPiste(2, 2, 4);
        poly2.lisaaPiste(3, 2, 5);
        poly2.lisaaPiste(3, 3, 6);
        poly2.lisaaPiste(2, 3, 7);

        polygonilista.lisaa(poly1);
        polygonilista.lisaa(poly2);

        MaastoKirjasto maastoKirjasto = new MaastoKirjasto(1);
        maastoKirjasto.lisaaVauhti(0, 1);
        maastoKirjasto.lisaaVauhti(1, 1);

        verkontekija = new Verkontekija(polygonilista, 0, 3, 0, 3, 8, maastoKirjasto);

        this.verkontekija.luoVerkko();
        //System.out.println(this.verkontekija.getVerkko().toString());
        assertEquals(
                "[   ][ 0 ][ 1 ][ 2 ][ 3 ][ 4 ][ 5 ][ 6 ][ 7 ]\n"
                + "[ 0 ][max][1.0][1.4][1.0][1.4][2.2][max][2.2]\n"
                + "[ 1 ][1.0][max][1.0][1.4][2.2][2.8][max][3.2]\n"
                + "[ 2 ][1.4][1.0][max][1.0][max][max][max][max]\n"
                + "[ 3 ][1.0][1.4][1.0][max][2.2][3.2][max][2.8]\n"
                + "[ 4 ][1.4][2.2][max][2.2][max][1.0][1.4][1.0]\n"
                + "[ 5 ][2.2][2.8][max][3.2][1.0][max][1.0][1.4]\n"
                + "[ 6 ][max][max][max][max][1.4][1.0][max][1.0]\n"
                + "[ 7 ][2.2][3.2][max][2.8][1.0][1.4][1.0][max]\n",
                this.verkontekija.getVerkko().toString());
    }

    @Test
    public void testLuoVerkkoViiva() {
        System.out.println("viivatest");
        Lista<Polygoni> polygonilista = new Lista<Polygoni>(2);
        Polygoni poly1 = new Polygoni(4);
        poly1.lisaaPiste(1, 1, 0);
        poly1.lisaaPiste(1, 0, 1);
        poly1.lisaaPiste(0, 0, 2);
        poly1.lisaaPiste(0, 1, 3);

        Polygoni poly2 = new Polygoni(4);
        poly2.lisaaPiste(2, 2, 4);
        poly2.lisaaPiste(3, 2, 5);
        poly2.lisaaPiste(3, 3, 6);
        poly2.lisaaPiste(2, 3, 7);

        polygonilista.lisaa(poly1);
        polygonilista.lisaa(poly2);

        MaastoKirjasto maastoKirjasto = new MaastoKirjasto(1);
        maastoKirjasto.lisaaVauhti(0, 2);
        maastoKirjasto.lisaaVauhti(1, 1);

        verkontekija = new Verkontekija(polygonilista, 0, 3, 0, 3, 8, maastoKirjasto);

        this.verkontekija.luoVerkko();
        System.out.println(this.verkontekija.getVerkko().toString());
        assertEquals(
                  "[   ][ 0 ][ 1 ][ 2 ][ 3 ][ 4 ][ 5 ][ 6 ][ 7 ]\n"
                + "[ 0 ][max][0.5][1.4][1.0][1.4][2.2][max][2.2]\n"
                + "[ 1 ][0.5][max][0.5][1.4][2.2][2.8][max][3.2]\n"
                + "[ 2 ][1.4][0.5][max][0.5][max][max][max][max]\n"
                + "[ 3 ][1.0][1.4][0.5][max][2.2][3.2][max][2.8]\n"
                + "[ 4 ][1.4][2.2][max][2.2][max][0.5][1.4][1.0]\n"
                + "[ 5 ][2.2][2.8][max][3.2][0.5][max][0.5][1.4]\n"
                + "[ 6 ][max][max][max][max][1.4][0.5][max][0.5]\n"
                + "[ 7 ][2.2][3.2][max][2.8][1.0][1.4][0.5][max]\n",
                this.verkontekija.getVerkko().toString());
    }

    /**
     * Test of getVerkko method, of class Verkontekija.
     */
    @Test
    public void testGetVerkko() {
    }

}
