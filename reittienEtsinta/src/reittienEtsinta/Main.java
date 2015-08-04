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
         
        
        
        Verkko verkko = new Verkko(new int[]{1, 2, 4, 5, 1, 3, 1}, new int[]{2, 3, 5, 6, 6, 0, 4}, new int[]{4, 3, 1, 3,4,1,1}, 7, new int[]{3, 0, 4, 4, 3, 3, 0}, new int[]{2, 0, 0, 3, 3, 4, 4});
        verkko.aStar(1, 0);
        System.out.println(Arrays.toString(verkko.getSolmut()));

        
        /*
        
         Verkko verkko = new Verkko(new int[]{0,0, 1, 2,2, 3}, new int[]{1,2, 4, 3,4, 4}, new int[]{5, 3, 4, 7, 2, 1}, 5);
         verkko.aStar(1, 3);
         System.out.println(verkko.getSolmut()[0].getAlkuun());
         
         System.out.println(Arrays.toString(verkko.getSolmut()));
         */
        /* MINIMIKEKOTESTI
         MinimiKeko minkek = new MinimiKeko(100);

         Random random = new Random();
        
         for (int i = 0; i < 10; i++) {
         minkek.lisaa(new VerkkoSolmu(i, -1, random.nextInt(100), random.nextInt(100)));
         }
        
         System.out.println("------");
         while (!minkek.tyhja()) {
         System.out.println(minkek.otaPienin().getId());
         }
        
         */
    }

}
