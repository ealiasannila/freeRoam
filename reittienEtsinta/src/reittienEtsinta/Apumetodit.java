/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reittienEtsinta;

/**
 * Tarjoaa useammissa luokissa tarjottavia apumetodeja
 * @author elias
 */
public class Apumetodit {

    
    /**
     * laskee kahden pisteen välisen etäisyyden Pythagoraan lauseella
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return 
     */
    public static double pisteidenEtaisyys(int x1, int y1, int x2, int y2) {
        return Math.sqrt(
                Math.pow(Math.abs(y1 - y2), 2)
                + Math.pow(Math.abs(x1 - x2), 2));

    }
}
