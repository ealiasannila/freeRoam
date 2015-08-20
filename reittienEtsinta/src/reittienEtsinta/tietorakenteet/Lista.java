
package reittienEtsinta.tietorakenteet;

/**
 * Listatietorakenne johon voidaan tallettaa olioita. 
 * Käyttää sisäiseen toteutukseen taulukkoa, jota kasvatetaan tarpeen mukaan
 * @author elias
 */
public class Lista<A> {
    private Object[] lista;
    private int koko;
    
    public Lista(int alkukoko) {
        this.lista = new Object[alkukoko]; 
        this.koko = 0;
    }
    
    /**
     * lisää uuden alkion listalle
     * @param alkio 
     */
    public void lisaa(A alkio){
        this.lista[koko] = alkio;
        koko++;
        if(koko==this.lista.length){
            this.kasvata();
        }
    }
    
    /**
     * tuplaa taulukon koon
     */
    private void kasvata(){
        Object[] uusiLista = new Object[this.lista.length*2];
        for (int i = 0; i < this.lista.length; i++) {
            uusiLista[i] = this.lista[i];
        }
        this.lista = uusiLista;
    }
    
    /**
     * palauttaa indeksissä i olevan alkion
     * @param i
     * @return 
     */
    public A ota(int i){
        return (A) this.lista[i];
    }
    
    
    /**
     * palautta listan alkioiden määrän
     * @return 
     */
    public int koko(){
        return koko;
    }
    
    
    
    
    
    
}
