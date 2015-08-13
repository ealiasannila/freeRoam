/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reittienEtsinta.tietorakenteet;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author elias
 */
public class Verkontekija {

    private PolygonVerkko verkko;
    private List<Polygoni>[][] naapurustot;
    private int pisteita;

    private double latmin;
    private double latmax;
    private double lonmin;
    private double lonmax;

    public Verkontekija(Polygoni[] polygonit, double latmin, double latmax, double lonmin, double lonmax, int pisteita, int maalisolmu) {
        this.pisteita = pisteita;
        int n = (int) (Math.sqrt(polygonit.length) * (0.15)); //kerroin vedetty hatusta
        n = Math.max(n, 1);


        System.out.println("n: " + n);

        this.naapurustot = new List[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                this.naapurustot[i][j] = new ArrayList<Polygoni>();

            }
        }

        this.latmax = latmax;
        this.latmin = latmin;
        this.lonmax = lonmax;
        this.lonmin = lonmin;

        this.verkko = new PolygonVerkko(pisteita, maalisolmu);

        this.lisaaPolygonit(polygonit);

    }

    private void lisaaPolygonit(Polygoni[] polygonit) {
        for (int i = 0; i < polygonit.length; i++) {
            this.lisaaPolygon(polygonit[i]);
        }
    }

    private void lisaaPolygon(Polygoni poly) {
        int x = (int) (((poly.getBBlat() - this.latmin) / (this.latmax - this.latmin)) * this.naapurustot.length);
        int y = (int) (((poly.getBBlon() - this.lonmin) / (this.lonmax - this.lonmin)) * this.naapurustot.length);
        /*
         System.out.println("bblat: " + poly.getBBlat());
         System.out.println("bblon: " + poly.getBBlon());

         System.out.println("latmin: " + this.latmin);
         System.out.println("latmax: " + this.latmax);
         System.out.println("lonmin: " + this.lonmin);
         System.out.println("lonmax: " + this.lonmax);

         System.out.println("y: " + y);
         System.out.println("x: " + x);
         */
        this.naapurustot[y][x].add(poly);

    }

    public void luoVerkko() {
        for (int i = 0; i < this.naapurustot.length; i++) {
            for (int j = 0; j < this.naapurustot[i].length; j++) {
                for (int k = 0; k < this.naapurustot[i][j].size(); k++) {
                    Polygoni polygoni = this.naapurustot[i][j].get(k);
                    for (int l = 0; l < polygoni.getId().length; l++) {
                        this.asetaKaaret(polygoni.getId()[l], polygoni.getLat()[l], polygoni.getLon()[l], j, i);
                    }
                }
            }
        }
    }

    private void asetaKaaret(int id, double lat, double lon, int naapurustoX, int naapurustoY) {
        for (int i = -1; i < 2; i++) {
            if (naapurustoY + i < 0 || naapurustoY + i >= this.naapurustot.length) {
                continue;
            }
            for (int j = -1; j < 2; j++) {
                if (naapurustoX + j < 0 || naapurustoX + j >= this.naapurustot[naapurustoY + i].length) {
                    continue;
                }

                for (int k = 0; k < this.naapurustot[naapurustoY + i][naapurustoX + j].size(); k++) {
                    Polygoni kohde = this.naapurustot[naapurustoY + i][naapurustoX + j].get(k);
                    for (int l = 0; l < kohde.getId().length; l++) {
                        if (kohde.getId()[l] != id) {
                            this.asetaKaari(id, lat, lon, kohde.getId()[l], kohde.getLat()[l], kohde.getLon()[l], naapurustoX, naapurustoY, naapurustoY + i, naapurustoX + j);
                        }
                    }
                }

            }
        }
    }

    public PolygonVerkko getVerkko() {
        return verkko;
    }

    private void asetaKaari(int id1, double lat1, double lon1, int idk, double latk, double lonk, int naapurustoX, int naapurustoY, int kohdenaapurustoX, int kohdenaapurustoY) {

   //     System.out.println("s: " + id1);
        //     System.out.println("k: " + idk);
        
        
        for (int i = -1; i <= 1; i++) {
            if (naapurustoY + i < 0 || naapurustoY + i >= this.naapurustot.length) {
                continue;
            }
            for (int j = -1; j <= 1; j++) {
                if (naapurustoX + j < 0 || naapurustoX + j >= this.naapurustot[naapurustoY + i].length) {
                    continue;
                }
                List<Polygoni> ruutu = this.naapurustot[naapurustoY + i][naapurustoX + j];

                for (int k = 0; k < ruutu.size(); k++) {
                    if (ruutu.get(k).viivaLeikkaaPolygonin(lat1, lon1, latk, lonk)) {
                        return;
                    }

                }
            }
        }
          for (int i = -1; i <= 1; i++) {
            if (kohdenaapurustoY + i < 0 || kohdenaapurustoY + i >= this.naapurustot.length) {
                continue;
            }
            for (int j = -1; j <= 1; j++) {
                if (kohdenaapurustoX + j < 0 || kohdenaapurustoX + j >= this.naapurustot[kohdenaapurustoY + i].length) {
                    continue;
                }
                List<Polygoni> ruutu = this.naapurustot[kohdenaapurustoY + i][kohdenaapurustoX + j];

                for (int k = 0; k < ruutu.size(); k++) {
                    if (ruutu.get(k).viivaLeikkaaPolygonin(lat1, lon1, latk, lonk)) {
                        return;
                    }

                }
            }
        }
        


        this.verkko.lisaaKaari(id1, idk, lat1, lon1, latk, lonk);

    }

}
