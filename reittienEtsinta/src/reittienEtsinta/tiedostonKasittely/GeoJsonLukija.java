/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reittienEtsinta.tiedostonKasittely;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import reittienEtsinta.tietorakenteet.Polygoni;

/**
 *
 * @author elias
 */
public class GeoJsonLukija {

    String data;
    int pisteita;

    public GeoJsonLukija(String polku) {
        this.pisteita = 0;
        
        File file = new File(polku);

        Scanner lukija = null;
        try {
            lukija = new Scanner(file);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GeoJsonLukija.class.getName()).log(Level.SEVERE, null, ex);
        }
        lukija.useDelimiter("\\Z");
        data = lukija.next();

    }

    public Polygoni[] lueJson() {
        JSONObject obj = new JSONObject(data);

        JSONArray arr = obj.getJSONArray("features");
        Polygoni[] polygonit = new Polygoni[arr.length()];

        for (int i = 0; i < arr.length(); i++) {
            polygonit[i] = this.luoPolygoni(arr.getJSONObject(i).getJSONObject("geometry").getJSONArray("coordinates").getJSONArray(0));
        }
        return polygonit;
    }

    public Polygoni luoPolygoni(JSONArray pisteet) {
        Polygoni uusi = new Polygoni(pisteet.length());

        for (int i = 0; i < pisteet.length(); i++) {
            uusi.lisaaPiste(pisteet.getJSONArray(i).getDouble(0), pisteet.getJSONArray(i).getDouble(1), this.pisteita);
            this.pisteita++;
        }

        return uusi;
    }

    public int getPisteita() {
        return pisteita;
    }

}
