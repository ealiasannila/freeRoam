/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reittienEtsinta.toteutuneetReitit;

import raster.Reitti;
import raster.MaastoKirjasto;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import raster.KuvanLukija;

/**
 *
 * @author elias
 */
public class MaastoKirjastoTest {

    private MaastoKirjasto kirjasto;
    private KuvanLukija kuvanLukija;

    public MaastoKirjastoTest() {
    }

    
    @Before
    public void setUp() {
        kuvanLukija = new KuvanLukija("aineisto/valkoinen_testi_200.jpg", "aineisto/valkoinen_testi_200_reitti.jpg");
        kirjasto = new MaastoKirjasto();
    }

    /**
     * Test of lisaaVauhti method, of class MaastoKirjasto.
     */
    @Test
    public void testLisaaReitti() { //mitenkähän tätäkin nyt testais täähän on käytännössä sama kuin alla...
        this.kirjasto.lisaaReitti(new Reitti(new int[]{0, 0}, new int[]{0, 5}, new int[]{0, 1}), this.kuvanLukija);
        assertTrue(Math.abs(5.0 - this.kirjasto.haeVauhti(this.kuvanLukija.getMaasto(0, 0))) <0.001);
        assertTrue(Math.abs(5.0 - this.kirjasto.haeVauhti(this.kuvanLukija.getMaasto(0, 0))) >  -0.001);
    }

    /**
     * Test of haeVauhti method, of class MaastoKirjasto.
     */
    @Test
    public void testHaeVauhti() {
        this.kirjasto.lisaaReitti(new Reitti(new int[]{0, 0}, new int[]{0, 1}, new int[]{0, 1}),this.kuvanLukija);
        this.kirjasto.lisaaReitti(new Reitti(new int[]{0, 0}, new int[]{0, 2}, new int[]{0, 1}),this.kuvanLukija);
        this.kirjasto.lisaaReitti(new Reitti(new int[]{0, 0}, new int[]{0, 3}, new int[]{0, 1}),this.kuvanLukija);
        this.kirjasto.lisaaReitti(new Reitti(new int[]{0, 0}, new int[]{0, 4}, new int[]{0, 1}),this.kuvanLukija);
        this.kirjasto.lisaaReitti(new Reitti(new int[]{0, 0}, new int[]{0, 5}, new int[]{0, 1}),this.kuvanLukija);

        assertTrue(Math.abs(3.0 - this.kirjasto.haeVauhti(this.kuvanLukija.getMaasto(0, 0)))<0.001);
        assertTrue(Math.abs(3.0 - this.kirjasto.haeVauhti(this.kuvanLukija.getMaasto(0, 0)))> -0.001);

    }

}
