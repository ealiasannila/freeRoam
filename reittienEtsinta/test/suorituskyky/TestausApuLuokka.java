/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package suorituskyky;

import java.io.File;
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
public class TestausApuLuokka {
    
    private int solmuja;
    private Verkko verkko;
    
    public Verkontekija luePolygonit(int maxsolmuja) {
        File polygonKansio = new File("aineisto/pkseutu/");
        if (!polygonKansio.isDirectory()) {
            System.out.println("anna kansio");
            return null;
        }
        File[] polygonTiedostot = polygonKansio.listFiles();

        GeoJsonLukija lukija = new GeoJsonLukija();
        int maastoja;

        Lista<Polygoni> polygonilista = new Lista(128);

        for (maastoja = 0; maastoja < polygonTiedostot.length; maastoja++) {

            lukija.luePolygonit(polygonTiedostot[maastoja], maastoja, polygonilista, maxsolmuja); 
        }

        System.out.println("polygonit luettu, " + polygonilista.koko() + " polygonia");
        this.solmuja = lukija.getPisteita();
        System.out.println("Solmuja: " + solmuja);

        Maastokirjasto maastokirjasto = new Maastokirjasto(maastoja);
        for (int i = 0; i <= maastoja; i++) {
            maastokirjasto.lisaaVauhti(i, 1);
        }

        this.verkko = new Verkko(solmuja, maastokirjasto);
        return new Verkontekija(polygonilista, lukija.getLatmin(), lukija.getLatmax(),
                lukija.getLonmin(), lukija.getLonmax(), solmuja);
    }

    public int getSolmuja() {
        return solmuja;
    }

    public Verkko getVerkko() {
        return verkko;
    }

    
    
}
