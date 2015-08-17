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
public class MaastoKirjastoPolygoniTest {
    private MaastoKirjastoPolygoni kirjasto;
    private ReittiPolygoni reitti;
    private Polygoni[] polygonit;
    
    public MaastoKirjastoPolygoniTest() {
    }
    
    @Before
    public void setUp() {
        int[] maastorajat = new int[3];
        maastorajat[0] = 10;
        maastorajat[1] = 20;
        maastorajat[2] = 30;
        kirjasto = new MaastoKirjastoPolygoni(maastorajat);
        reitti = new ReittiPolygoni(new double[]{0.1,0.2}, new double[]{0.1, 0.2}, new int[] {0, 1});
        this.polygonit = new AluePolygoni[1];
        this.polygonit[0] = new AluePolygoni(4);
        this.polygonit[0].lisaaPiste(0, 0, 1);
        this.polygonit[0].lisaaPiste(0, 1, 2);
        this.polygonit[0].lisaaPiste(1, 1, 3);
        this.polygonit[0].lisaaPiste(1, 0, 4);
        
    }

    

    /**
     * Test of haeVauhti method, of class MaastoKirjastoPolygoni.
     */
    @Test
    public void testHaeVauhti() {
        this.kirjasto.lisaaReitti(reitti, polygonit);
        System.out.println(this.kirjasto.haeVauhti(1, 2));
        assertTrue(0.14142135623730953-this.kirjasto.haeVauhti(1, 2)<0.0001);
        assertTrue(0.14142135623730953-this.kirjasto.haeVauhti(1, 2)>-0.0001);
    }

    /**
     * Test of lisaaReitti method, of class MaastoKirjastoPolygoni.
     */
    @Test
    public void testLisaaReitti() {
    }

    /**
     * Test of toString method, of class MaastoKirjastoPolygoni.
     */
    @Test
    public void testToString() {
    }
    
}
