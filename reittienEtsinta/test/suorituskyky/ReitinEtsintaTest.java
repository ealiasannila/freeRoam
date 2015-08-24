/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package suorituskyky;

import java.io.File;
import java.util.Random;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import reittienEtsinta.tiedostonKasittely.GeoJsonLukija;
import reittienEtsinta.tietorakenteet.Lista;
import reittienEtsinta.tietorakenteet.Maastokirjasto;
import reittienEtsinta.tietorakenteet.Polygoni;
import reittienEtsinta.tietorakenteet.Verkko;
import reittienEtsinta.tietorakenteet.Verkontekija;

/**
 *
 * @author elias
 */
public class ReitinEtsintaTest {

    private Verkko verkko;

    public ReitinEtsintaTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @Before
    public void setUp() {

    }

    private void luoVerkko(int maxsolmuja) {
        Verkontekija verkontekija = TestausApuLuokka.luePolygonit(maxsolmuja);
        verkontekija.luoVerkko();
        this.verkko = verkontekija.getVerkko();
    }

    private void reitinetsintaTest(int maxsolmuja) {
        this.luoVerkko(maxsolmuja);
        Random random = new Random();
        long aikasumma = 0;
        int n = 10;
        for (int i = 0; i < n; i++) {
            long aikaAlussa = System.currentTimeMillis();
            this.verkko.alustus(random.nextInt(this.verkko.lat.length), random.nextInt(this.verkko.lat.length));

            this.verkko.aStar();
            
            long aikaLopussa = System.currentTimeMillis();
            aikasumma += aikaLopussa - aikaAlussa;

        }
        System.out.println("Reitin etsintään kului aikaa: " + (aikasumma) / n + "ms.");

    }

    @Test
    public void reitinEtsintaTest10() {
        this.reitinetsintaTest(10);
    }

    @Test
    public void reitinEtsintaTest100() {
        this.reitinetsintaTest(100);
    }

    @Test
    public void reitinEtsintaTest1000() {
        this.reitinetsintaTest(1000);
    }

    @Test
    public void reitinEtsintaTest10000() {
        this.reitinetsintaTest(10000);
    }

}
