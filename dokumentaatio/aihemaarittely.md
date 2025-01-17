#Aihe:
Toteutetaan nopeimman reitin etsintä vapaamuotoisella areenalla perustuen aiempiin toteutuneisiin matkoihin.

#Syöte:
Ohjelma saa syötteenä luokitellun aineiston esimerkiksi kartan, jossa eri tyyppiset alueet on kuvattu eri väreillä, sekä listan toteutuneita polkuja, jotka ovat koordinaatteja ja kellonaikoja.

#Ohjelman toiminta:

##Areenan alustus:
* jaetaan toteutuneet polut osiin, sen perusteella minkä alueen sisään ne jäävät.
* lasketaan polun pituuden sekä ensimmäisen ja viimeisen alueen sisällä olevan pisteen aikaleimojen perusteella vauhti polun osalla.
* lasketaan kaikkien eri polkujen osien, jotka ovat samantyyppisellä alueella, vauhtien perusteella keskimääräien vauhti kyseisellä tyypillä.
* luodaan areenasta painotettu verkko, jossa kaaripainoina vauhti.

##Lyhyimmän reitin etsintä:
* lyhyimmän reitin etsintä verkossa, esim. A*.

#Tarvittavat tietorakenteet:
* Areenan verkko vieruslistana, tai matriisina.
* jos listana listatietorakenteen toteutus
* Hajautustaulu eri maastotyyppien nopeuksien tallettamiseen? Ehkä pelkkä taulukko jossa indexit tyyppejä? Saadaanko etua jos hajautustalu ja avaimena esim rgb arvo? Tyyppejä niin vähän että pelkkä taulukkokin voi käyttää indeksinä rgb...
* Minimikeko lyhyimmän polun etsintään.

#Ongelmia:
* jos verkko koostuu pikseleistä (jokaisella pikselillä 8 naapuria), miten toteutetaan vinottain liikkuessa kompensointi pidemmälle matkalle? Silti tulos menee väärin, koska mahdollisia liikkumisuuntia vain 8...
* jos ei, mitä muita vaihtoehtoja? verkon solmujen sijoittelu suurella spatiaalisella resoluutiolla, mutta suuremmalla kaarien määrällä? (eli esim suorat kaaret naapurien naapureihin, jolloin kulkusuuntia olisi jo 16)
* Onko ongelma jos toteutuneiden polkujen pituus lasketaan euklidisesti, mutta verkossa voi liikkua vain kahdeksaan "ilmansuuntaan"?
* kuinka suurilla verkoilla ja fyysisillä alueilla reittejä pystyy edes laskemaan järkevässä ajassa?
* 5m spatiaalisella resoluutiolla neliökilometrin areenalla 40 000 solmua ja n.160 000/320 000 kaarta?

* yhden tyyppisen alueen sisällä ei ikänä kannata tehdä mutkaa. Riittäisikö siis tallentaa vain alueiden reunat ja tallentaa aluiden sisäiset etäisyydet kaariin, jotka kulkisivat jokaisesta alueen reunasolmusta kaikkiin alueen reunasolmuihin. 
* Ongelmana alueet joiden muoto sellainen, että suora reitti reunasolmusta toiseen kulkee alueen ulkopuolella. -> pitäisi jotenkin kaaren lisäyksessä tarkistaa kulkeeko kaari alueen ulkopuolella. 
* seurauksena verkko jossa paljon kaaria suhteessa solmujen määrään.
* luultavasti vaikeuttaisi huomattavasti esim. korkeusvaihteluiden huomioimisen toteuttamista.


#Jatkokehitettävää: (tuskin toteutuu koska aikarajoiteet...)
* lyhyimmän reitin esittäminen käyttäjälle visuaalisesti
* alku ja loppupisteen valinta visuaalisesti


* vaihtoehtoiset kulkuvälineet (kävellen , polkupyörällä...)
* vapaan kokoiset areenat (alustetaan vain osa areenaa sen perusteella, missä alku ja loppupiste sijaitsee)
* maastotyyppi kirjasto, tallennetaan eri maastotyypeille tähänasti toteutuneiden reittien perusteella laskettu vauhti tiedostoon, jotta kun halutaan siirtyä uudelle alueelle, voidaan verkolle laskea painot ilman toteutuneita reittejä alueella.

* kulkusuunnasta riippuvat nopeudet (esim ylä- vs alamäki)
* maastojen haastavuuden kompensointi. Esim jos laskennallisesti lyhin reitti on suora viiva metsän läpi, ei se todellisuudessa välttämättä ole nopein reitti, sillä metsässä on vaikea kävellä suoraan. Toisaalta tietä pitkin kävellessä toteutuneen ja laskennallisen polun pituudet kohtaavat varmaankin varsin hyvin. -> jonkin lainen oppiminen perustuen siihen että ehdotettua reittiä käydään testaamassa.
