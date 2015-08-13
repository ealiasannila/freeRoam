/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package raster;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 * Lukee maastoa esittävää kuvatiedostoa, ja piirtää toiseen kuvaan reitin
 *
 * @author elias
 */
public class KuvanLukija {

    private File kuvatiedosto;
    private BufferedImage kuva;
    private BufferedImage tuloste;

    /**
     * avaa luettavan kuvan, sekä kuvan johon reitti piirretään
     *
     * @param polku
     * @param tulostePolku
     */
    public KuvanLukija(String polku, String tulostePolku) {
        kuvatiedosto = new File(polku);
        kuva = null;
        try {
            kuva = ImageIO.read(kuvatiedosto);
        } catch (IOException ex) {
            System.out.println("kuvaa ei löydy");
        }
        try {
            tuloste = ImageIO.read(kuvatiedosto);
        } catch (IOException ex) {
            System.out.println("tuloste ei löydy");
        }

    }

    /**
     * palauttaa tietyssä pikselissä olevan maastotyypin (=väriarvon)
     *
     * @param x
     * @param y
     * @return
     */
    public int getMaasto(int x, int y) {
        return kuva.getRGB(x, y);

    }

    /**
     * piirtää reitin kuvaan
     *
     * @param reitti
     */
    public void piirraReitti(Reitti reitti) {

        int rgb = Color.BLACK.getRGB();

        for (int i = 0; i < reitti.getX().length; i++) {
            this.tuloste.setRGB(reitti.getX()[i], reitti.getY()[i], rgb);
        }
        try {
            ImageIO.write(tuloste, "png", new File("aineisto/out.png"));
        } catch (IOException ex) {
            System.out.println("kirjoitus ei onnistunut");
        }
    }

}
