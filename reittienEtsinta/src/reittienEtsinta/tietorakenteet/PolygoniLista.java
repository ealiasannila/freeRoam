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
public class PolygoniLista {
    private Polygoni[] lista;
    private int koko;
    
    public PolygoniLista(int alkukoko) {
        this.lista = new Polygoni[alkukoko]; 
        this.koko = 0;
    }
    
    public void lisaa(Polygoni polygoni){
        this.lista[koko] = polygoni;
        koko++;
        if(koko==this.lista.length){
            this.kasvata();
        }
    }
    
    private void kasvata(){
        Polygoni[] uusiLista = new Polygoni[this.lista.length*2];
        for (int i = 0; i < this.lista.length; i++) {
            uusiLista[i] = this.lista[i];
        }
        this.lista = uusiLista;
    }
    
    public Polygoni ota(int i){
        return this.lista[i];
    }
    
    public int koko(){
        return koko;
    }
    
    
    
    
}
