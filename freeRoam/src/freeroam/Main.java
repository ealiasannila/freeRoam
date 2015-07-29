/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package freeroam;

import freeroam.tietorakenteet.MinimiKeko;
import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author elias
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
/*
         Verkko verkko = new Verkko(new int[]{0, 1, 0}, new int[]{1, 2, 2}, new int[]{5, 3, 1}, 3, new int[]{0, 0, 0}, new int[]{0, 0, 0});
         verkko.aStar(0, 1);
         System.out.println(Arrays.toString(verkko.alkuun));
         */
        MinimiKeko minkek = new MinimiKeko(100);

        Random random = new Random();
        
        for (int i = 0; i < 10; i++) {
            minkek.lisaa(random.nextInt(100));
        }
        
        System.out.println("------");
        while (!minkek.tyhja()) {
            System.out.println(minkek.otaPienin());
        }

    }

}
/*
 0---5---1
 \     /
 1   3
 \ /
 2
 */
