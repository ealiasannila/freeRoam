/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reittienEtsinta;

import java.util.Scanner;
import org.json.JSONObject;
import reittienEtsinta.tiedostonKasittely.GeoJsonKirjoittaja;
import reittienEtsinta.tietorakenteet.ReittiPolygoni;
import reittienEtsinta.tietorakenteet.VerkkoPolygoni;

/**
 *
 * @author elias
 */
public class Kayttoliittyma {

    private VerkkoPolygoni verkko;

    public Kayttoliittyma(VerkkoPolygoni verkko) {
        this.verkko = verkko;
    }

    public void kaynnista() {
        Scanner lukija = new Scanner(System.in);
        System.out.println("Komennot: \n koord <lon> <lat> <lon> <lat> <tiedostonimi> \n hae <lahtosolmu> <maalisolmu> <tiedostonimi> \n lopeta");
        while (true) {
            String komento = lukija.nextLine();
            if (komento.substring(0, 5).equals("koord")) {
                Scanner komentotulkki = new Scanner(komento);

                double lonA = Double.parseDouble(komentotulkki.findInLine("[0-9]{6}"));
                double latA = Double.parseDouble(komentotulkki.findInLine("[0-9]{7}"));
                double lonM = Double.parseDouble(komentotulkki.findInLine("[0-9]{6}"));
                double latM = Double.parseDouble(komentotulkki.findInLine("[0-9]{7}"));

                this.haeReittiKoordinaateilla(latA, lonA, latM, lonM);

                String polku = komentotulkki.next();
                this.tallennaReitti(polku);
            }
            if (komento.substring(0, 3).equals("hae")) {
                Scanner komentotulkki = new Scanner(komento);
                int alku = Integer.parseInt(komentotulkki.findInLine("[0-9]{1,4}"));
                int maali = Integer.parseInt(komentotulkki.findInLine("[0-9]{1,4}"));

                this.haeReitti(alku, maali);
                String polku = komentotulkki.next();
                this.tallennaReitti(polku);

            } else if (komento.equals("lopeta")) {
                return;
            } else {
                System.out.println("tuntematon komento");
            }
        }

    }

    private void tallennaReitti(String polku) {
        System.out.println("Puretaan reitti");
        ReittiPolygoni lyhyinReitti = verkko.lyhyinReitti();

        System.out.println("Tallennetaan reitti tiedostoon " + polku);
        GeoJsonKirjoittaja.kirjoita(polku, GeoJsonKirjoittaja.muunnaJson(lyhyinReitti));
        System.out.println("valmis");
    }

    private void haeReittiKoordinaateilla(double latA, double lonA, double latM, double lonM) {
        int alku = verkko.haeLahinSolmu(latA, lonA);
        int loppu = verkko.haeLahinSolmu(latM, lonM);
        this.haeReitti(alku, loppu);
    }

    private void haeReitti(int lahtosolmu, int maalisolmu) {
        System.out.println("Muodostetaan reitti solmusta " + lahtosolmu + " solmuun " + maalisolmu);
        this.verkko.alustus(lahtosolmu, maalisolmu);
        this.verkko.aStar();
    }

}
