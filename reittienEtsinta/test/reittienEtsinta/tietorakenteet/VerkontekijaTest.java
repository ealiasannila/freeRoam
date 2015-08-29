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

        Maastokirjasto maastoKirjasto = new Maastokirjasto(1,4);
        maastoKirjasto.lisaaVauhti(0, 1);
        maastoKirjasto.lisaaVauhti(1, 1);

        Verkko verkko = new Verkko(8, maastoKirjasto);
        verkontekija = new Verkontekija(polygonilista, 0, 3, 0, 3, 8);

        this.verkontekija.luoVerkko(verkko);
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
                verkko.toString());
    }

    @Test
    public void testLuoVerkkoAlueSisakulma() { //sen muotoinen verkko, missä luodaan kaari polygonin solmusta toiseen tuntemattoman alueen läpi
        Lista<Polygoni> polygonilista = new Lista<Polygoni>(2);
        Polygoni poly1 = new AluePolygoni(8);
        poly1.lisaaPiste(0, 1, 0);
        poly1.lisaaPiste(0, 2, 1);
        poly1.lisaaPiste(1, 3, 2);
        poly1.lisaaPiste(2, 2, 3);
        poly1.lisaaPiste(1, 2, 4);
        poly1.lisaaPiste(1, 1, 5);
        poly1.lisaaPiste(2, 1, 6);
        poly1.lisaaPiste(1, 0, 7);

        polygonilista.lisaa(poly1);

        Maastokirjasto maastoKirjasto = new Maastokirjasto(1,4);
        maastoKirjasto.lisaaVauhti(0, 2);
        maastoKirjasto.lisaaVauhti(1, 1);

        Verkko verkko = new Verkko(8, maastoKirjasto);
        verkontekija = new Verkontekija(polygonilista, 0, 3, 0, 3, 8);

        this.verkontekija.luoVerkko(verkko);
        assertEquals(
                  "[   ][ 0 ][ 1 ][ 2 ][ 3 ][ 4 ][ 5 ][ 6 ][ 7 ]\n"
                + "[ 0 ][max][0.5][1.1][max][0.7][0.5][max][0.7]\n"
                + "[ 1 ][0.5][max][0.7][max][0.5][0.7][max][1.1]\n"
                + "[ 2 ][1.1][0.7][max][0.7][0.5][max][max][max]\n"
                + "[ 3 ][max][max][0.7][max][0.5][1.4][1.0][max]\n"
                + "[ 4 ][0.7][0.5][0.5][0.5][max][0.5][1.4][max]\n"
                + "[ 5 ][0.5][0.7][max][1.4][0.5][max][0.5][0.5]\n"
                + "[ 6 ][max][max][max][1.0][1.4][0.5][max][0.7]\n"
                + "[ 7 ][0.7][1.1][max][max][max][0.5][0.7][max]\n",
                verkko.toString());
    }

    @Test
    public void testLuoVerkkoViiva() {
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

        Maastokirjasto maastoKirjasto = new Maastokirjasto(1,4);
        maastoKirjasto.lisaaVauhti(0, 2);
        maastoKirjasto.lisaaVauhti(1, 1);

        Verkko verkko = new Verkko(8, maastoKirjasto);
        verkontekija = new Verkontekija(polygonilista, 0, 3, 0, 3, 8);

        this.verkontekija.luoVerkko(verkko);

        assertEquals(
                  "[   ][ 0 ][ 1 ][ 2 ][ 3 ][ 4 ][ 5 ][ 6 ][ 7 ]\n"
                + "[ 0 ][max][0.5][1.4][1.0][1.4][2.2][max][2.2]\n"
                + "[ 1 ][0.5][max][0.5][1.4][2.2][2.8][max][3.2]\n"
                + "[ 2 ][1.4][0.5][max][0.5][max][max][max][3.6]\n"
                + "[ 3 ][1.0][1.4][0.5][max][2.2][3.2][3.6][2.8]\n"
                + "[ 4 ][1.4][2.2][max][2.2][max][0.5][1.4][1.0]\n"
                + "[ 5 ][2.2][2.8][max][3.2][0.5][max][0.5][1.4]\n"
                + "[ 6 ][max][max][max][3.6][1.4][0.5][max][0.5]\n"
                + "[ 7 ][2.2][3.2][3.6][2.8][1.0][1.4][0.5][max]\n",
                verkko.toString());
    }

    /**
     * Test of getVerkko method, of class Verkontekija.
     */
    @Test
    public void testGetVerkko() {
    }

}
