/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reittienEtsinta.tietorakenteet;

/**
 *
 * @author elias
 */
public class VerkkoSolmu {


    private int x;
    private int y;
    private int id;
    private int polku;
    private long alkuun;
    private long loppuun;
    private int kekoI;

    public VerkkoSolmu(int id, int polku, long alkuun, int x, int y) {
        this.id = id;
        this.polku = polku;
        this.alkuun = alkuun;
        this.loppuun = loppuun;
        this.x = x;
        this.y = y;
    }

    public int getKekoI() {
        return kekoI;
    }

    public void setKekoI(int kekoI) {
        this.kekoI = kekoI;
    }

    
    public long getArvio() {
        if (this.alkuun == Long.MAX_VALUE) {
            return this.alkuun;
        }
        return this.alkuun + this.loppuun;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPolku() {
        return polku;
    }

    public void setPolku(int polku) {
        this.polku = polku;
    }

    public long getAlkuun() {
        return alkuun;
    }

    public void setAlkuun(long alkuun) {
        this.alkuun = alkuun;
    }

    public long getLoppuun() {
        return loppuun;
    }

    public void setLoppuun(long loppuun) {
        this.loppuun = loppuun;
    }

    
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
    
    
    
    public String toString() {
        return "A:" + this.getAlkuun() + " L: " + this.loppuun + " Id: " + this.getId() + "\n";
    }

}
