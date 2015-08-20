/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reittienEtsinta;

/**
 * Tarjoaa useammissa luokissa tarjottavia apumetodeja
 *
 * @author elias
 */
public class Apumetodit {

    /**
     * laskee kahden pisteen välisen etäisyyden Pythagoraan lauseella
     *
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return
     */
    public static double pisteidenEtaisyys(double x1, double y1, double x2, double y2) {
        return Math.sqrt(pisteidenEtaisyysToiseen(x1, y1, x2, y2));

    }

    public static double pisteidenEtaisyysToiseen(double x1, double y1, double x2, double y2) {
        return Math.pow(Math.abs(y1 - y2), 2)
                + Math.pow(Math.abs(x1 - x2), 2);
    }

    /**
     * testaa ovatko kaksi pistetä samat
     *
     * @param lat1
     * @param lon1
     * @param lat2
     * @param lon2
     * @return
     */
    public static boolean pisteSama(double lat1, double lon1, double lat2, double lon2) {
        return (Math.abs(lat1 - lat2) < 0.01) && (Math.abs(lon1 - lon2) < 0.01);
    }

}
