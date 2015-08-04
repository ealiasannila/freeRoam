/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reittienEtsinta;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import reittienEtsinta.toteutuneetReitit.Reitti;

/**
 *
 * @author elias
 */
public class KuvanLukija {

    private File kuvatiedosto;
    private BufferedImage kuva;
    private File tulosteTiedosto;
    private BufferedImage tuloste;

    public KuvanLukija(String polku, String tulostePolku) {
        kuvatiedosto = new File(polku);
        kuva = null;
        try {
            kuva = ImageIO.read(kuvatiedosto);
        } catch (IOException ex) {
            System.out.println("kuvaa ei löydy");
        }
        tulosteTiedosto = new File(tulostePolku);
        tuloste = null;
        try {
            tuloste = ImageIO.read(tulosteTiedosto);
        } catch (IOException ex) {
            System.out.println("tuloste ei löydy");
        }

    }

    public int getMaasto(int x, int y) {
        return kuva.getRGB(x, y);

    }

    public void piirraReitti(Reitti reitti) {

        
        int rgb = Color.BLACK.getRGB();

        

        for (int i = 0; i < reitti.getX().length; i++) {
            this.tuloste.setRGB(reitti.getX()[i], reitti.getY()[i], rgb);
        }
        try {
            ImageIO.write(tuloste, "jpg", this.tulosteTiedosto);
        } catch (IOException ex) {
            System.out.println("kirjoitus ei onnistunut");
        }
    }

}
