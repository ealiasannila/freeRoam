/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reittienEtsinta;

import reittienEtsinta.tietorakenteet.MinimiKeko;
import java.util.Arrays;
import java.util.Random;
import reittienEtsinta.tietorakenteet.Verkko;
import reittienEtsinta.tietorakenteet.VerkkoSolmu;
import reittienEtsinta.toteutuneetReitit.MaastoKirjasto;
import reittienEtsinta.toteutuneetReitit.Reitti;

/**
 *
 * @author elias
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        KuvanLukija kuvanLukija = new KuvanLukija("aineisto/testialue_200.jpg", "aineisto/testialue_200_reitti.jpg");
        MaastoKirjasto maastoKirjasto = new MaastoKirjasto();
        Verkko verkko = new Verkko(200, maastoKirjasto, kuvanLukija);
        maastoKirjasto.lisaaReitti(kuvanLukija.getMaasto(0, 50), 0, 1, new Reitti(new int[]{0, 0}, new int[]{0, 1}, new int[]{0, 1}));
        //maastoKirjasto.lisaaReitti(kuvanLukija.getMaasto(50, 50), 0, 1, new Reitti(new int[]{0, 0}, new int[]{0, 1}, new int[]{0, 100}));

        maastoKirjasto.lisaaReitti(kuvanLukija.getMaasto(70, 0), 0, 1, new Reitti(new int[]{0, 0}, new int[]{0, 1}, new int[]{0, 2}));

        verkko.aStar(0, 50, 187, 178);
        kuvanLukija.piirraReitti(verkko.lyhyinReitti(0, 50, 187, 178));

    }

}
