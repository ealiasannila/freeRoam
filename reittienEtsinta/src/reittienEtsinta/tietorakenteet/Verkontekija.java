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

    private List<Polygoni>[][] naapurustot;
    private int pisteita;

    private double latmin;
    private double latmax;
    private double lonmin;
    private double lonmax;

    public Verkontekija(Polygoni[] polygonit, double latmin, double latmax, double lonmin, double lonmax, int pisteita) {
        this.pisteita = pisteita;
        int n = (int) (Math.sqrt(polygonit.length) * (0.75)); //kerroin vedetty hatusta
        this.naapurustot = new List[n][n];

    }

    private void lisaaPolygon(Polygoni poly) {
        int x = (int) (((poly.getBBlat() - this.latmin) / (this.latmax - this.latmin)) * this.naapurustot.length);
        int y = (int) (((poly.getBBlon() - this.lonmin) / (this.lonmax - this.lonmin)) * this.naapurustot.length);

        if (this.naapurustot[y][x] == null) {
            this.naapurustot[y][x] = new ArrayList<Polygoni>();
        }
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
            for (int j = -1; j < 2; j++) {
                for (int k = 0; k < this.naapurustot[naapurustoY + i][naapurustoX + j].size(); k++) {
                    Polygoni kohde = this.naapurustot[naapurustoY + i][naapurustoX + j].get(k);
                    for (int l = 0; l < kohde.getId().length; l++) {
                        this.asetaKaari(id, lat, lon, kohde.getId()[l], kohde.getLat()[l], kohde.getLon()[l], naapurustoX, naapurustoY, naapurustoY + i, naapurustoX + j);
                    }
                }

            }
        }
    }

    private void asetaKaari(int id1, double lat1, double lon1, int idk, double latk, double lonk, int naapurustoX, int naapurustoY, int kohdenaapurustoX, int kohdenaapurustoY) {
        List<Polygoni> omat = this.naapurustot[naapurustoY][naapurustoX];
        List<Polygoni> naapurin = this.naapurustot[kohdenaapurustoY][kohdenaapurustoX];

        for (int i = 0; i < omat.size(); i++) {
            if (omat.get(i).viivaLeikkaaPolygonin(lat1, lon1, latk, lonk)) {
                return;
            }

        }
        for (int i = 0; i < naapurin.size(); i++) {
            if (naapurin.get(i).viivaLeikkaaPolygonin(lat1, lon1, latk, lonk)) {
                return;
            }
        }
        if (naapurustoX != kohdenaapurustoX && naapurustoY != kohdenaapurustoY) {
            List<Polygoni> diagonaali1 = this.naapurustot[naapurustoY][kohdenaapurustoX];
            List<Polygoni> diagonaali2 = this.naapurustot[kohdenaapurustoY][naapurustoX];
            for (int i = 0; i < diagonaali1.size(); i++) {
                if (diagonaali1.get(i).viivaLeikkaaPolygonin(lat1, lon1, latk, lonk)) {
                    return;
                }

            }
            for (int i = 0; i < diagonaali2.size(); i++) {
                if (diagonaali2.get(i).viivaLeikkaaPolygonin(lat1, lon1, latk, lonk)) {
                    return;
                }
            }

        }
        
        //ASETA KAARI VERKOSSA

    }

}
