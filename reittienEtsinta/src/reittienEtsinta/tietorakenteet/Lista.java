
package reittienEtsinta.tietorakenteet;

/**
 * Listatietorakenne johon voidaan tallettaa Polygon olioita. 
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
    
    public void lisaa(A alkio){
        this.lista[koko] = alkio;
        koko++;
        if(koko==this.lista.length){
            this.kasvata();
        }
    }
    
    private void kasvata(){
        Object[] uusiLista = new Object[this.lista.length*2];
        for (int i = 0; i < this.lista.length; i++) {
            uusiLista[i] = this.lista[i];
        }
        this.lista = uusiLista;
    }
    
    public A ota(int i){
        return (A) this.lista[i];
    }
    
    public int koko(){
        return koko;
    }
    
    
    
    
    
    
}
