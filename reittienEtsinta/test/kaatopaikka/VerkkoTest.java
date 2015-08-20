/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kaatopaikka;

import kaatopaikka.Verkko;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import kaatopaikka.KuvanLukija;
import kaatopaikka.MaastoKirjasto;
import kaatopaikka.Reitti;

/**
 *
 * @author elias
 */
public class VerkkoTest {

    private Verkko verkko;

    public VerkkoTest() {
    }

    @Before
    public void setUp() {
        MaastoKirjasto kirjasto = new MaastoKirjasto();

        KuvanLukija kuvanLukija = new KuvanLukija("aineisto/valkoinen_testi_200.jpg", "aineisto/valkoinen_testi_200_reitti.jpg");
        kirjasto.lisaaReitti(new Reitti(new int[]{0, 0}, new int[]{0, 1}, new int[]{0, 1}), kuvanLukija);

        verkko = new Verkko(3, kirjasto, kuvanLukija);
    }

    /**
     * Test of aStar method, of class Verkko.
     */
    @Test
    public void testAStar() {
        verkko.aStar(0, 0, 2, 2);
        for (int i = 0; i < 3; i++) {
            System.out.println(Arrays.toString(verkko.getSolmut()[i]));
        }
        assertTrue(0.7071067811865476 - verkko.getSolmu(2, 2).getAlkuun() < 0.0001);
        assertTrue(0.7071067811865476 - verkko.getSolmu(2, 2).getAlkuun() > -0.0001);

    }

    @Test
    public void arvioTest() {
        verkko.aStar(0, 0, 2, 2);
        assertTrue(2.8284271247461903 - verkko.getSolmu(0, 0).getArvio() < 0.0001);
        assertTrue(2.8284271247461903 - verkko.getSolmu(0, 0).getArvio() > -0.0001);

    }

}
