/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package suorituskyky;

import java.io.File;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import reittienEtsinta.tiedostonKasittely.GeoJsonLukija;
import reittienEtsinta.tietorakenteet.Lista;
import reittienEtsinta.tietorakenteet.Maastokirjasto;
import reittienEtsinta.tietorakenteet.Polygoni;
import reittienEtsinta.tietorakenteet.Reitti;
import reittienEtsinta.tietorakenteet.Verkko;
import reittienEtsinta.tietorakenteet.Verkontekija;

/**
 *
 * @author elias
 */
public class VerkonGenerointiTest {

    private Verkontekija verkontekija;

    public VerkonGenerointiTest() {
    }

    @BeforeClass
    public static void setUpClass() {

    }

    @Before
    public void setUp() {

    }

    @Test
    public void verkonGenerointiTest10() {
        this.verkonGenerointiAikaTest(10);
    }

    @Test
    public void verkonGenerointiTest100() {
        this.verkonGenerointiAikaTest(100);
    }

    @Test
    public void verkonGenerointiTest1000() {
        this.verkonGenerointiAikaTest(1000);
    }

    @Test
    public void verkonGenerointiTest10000() {
        this.verkonGenerointiAikaTest(10000);
    }

    @Test
    public void verkonGenerointiTest100000() {
        this.verkonGenerointiAikaTest(100000);
    }

    @Test
    public void verkonGenerointiTest1000000() { //ei toimi koska testiverkossa on vain noin 300 000 solmua.
        this.verkonGenerointiAikaTest(1000000);
    }

    private void verkonGenerointiAikaTest(int maxsolmuja) {
        TestausApuLuokka testiapu = new TestausApuLuokka();
        this.verkontekija = testiapu.luePolygonit(maxsolmuja);

        long aikasumma = 0;
        int n = 10;
        for (int i = 0; i < n; i++) {
            long aikaAlussa = System.currentTimeMillis();
            verkontekija.luoVerkko(testiapu.getVerkko());
            long aikaLopussa = System.currentTimeMillis();
            aikasumma += (aikaLopussa - aikaAlussa);
        }
        System.out.println("Verkon generointiin kului aikaa: " + (aikasumma) / n + "ms.");

    }

}
