/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kaatopaikka;

/**
 * Yhtä verkon solmua esittävä luokka.
 * Käytetään solmuun liittyvän tiedon tallentamiseen.
 * @author elias
 */
public class VerkkoSolmu {


    private int x;
    private int y;
    private int polkuX;
    private int polkuY;
    private double alkuun;
    private double loppuun;
    private int kekoI;
    private int maasto;


    public VerkkoSolmu(int x, int y, int maasto, double loppuun) {
        this.x = x;
        this.y = y;
        this.polkuX = -1;
        this.polkuY = -1;
        this.alkuun = Double.MAX_VALUE;
        this.maasto = maasto;
        this.loppuun = loppuun;
        
    }
    
    
    public int getMaasto() {
        return maasto;
    }

    public void setMaasto(int maasto) {
        this.maasto = maasto;
    }

    public int getKekoI() {
        return kekoI;
    }

    public void setKekoI(int kekoI) {
        this.kekoI = kekoI;
    }

    
    /**
     * palauttaa arvion tämän solmun kautta kulkevan reitin pituudesta.
     * @return 
     */
    public double getArvio() {
        if (this.alkuun == Double.MAX_VALUE) {
            return this.alkuun;
        }
        return this.alkuun + this.loppuun;
    }


    public double getAlkuun() {
        return alkuun;
    }

    public void setAlkuun(double alkuun) {
        this.alkuun = alkuun;
    }

    public double getLoppuun() {
        return loppuun;
    }

    public void setLoppuun(double loppuun) {
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

    public int getPolkuX() {
        return polkuX;
    }

    public void setPolkuX(int polkuX) {
        this.polkuX = polkuX;
    }

    public int getPolkuY() {
        return polkuY;
    }

    public void setPolkuY(int polkuY) {
        this.polkuY = polkuY;
    }
    
    
    
    public String toString() {
        return "" + this.getAlkuun() ;
    }

}
