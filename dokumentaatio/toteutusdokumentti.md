#Toteutusdokumentti reittien etsintä harjoitustyöstä.

## Ohjelman yleisrakenne:

####huom. paketissa raster olevat luokat eivät oikeastaan enään tee mitään ja alkavat olla melko turhia.

- luokkakkavio tähän.

Ohjelman toiminta jakautuu viiteen osaan:

1. Syötteenä annettujen polygonien lukeminen. 
2. Syötteenä annettujen reittien analysointi ja eri maastotyyppien vauhtien määrittely reittien pohjalta.
3. Verkon generointi polygon aineistosta, ja kaarien painotus maastotyypeille määräytyneiden vauhtien mukaan.
4. Nopeimman reitin etsintä generoidussa verkossa.
5. Etsityn reitin kirjoittaminen tiedostoon jatkokäyttöä varten.

### 1. Polygonien lukeminen
Luokka GeoJson Lukija lukee geojson muodossa tallennetut polygonit ja muodostaa niistä Polygoni luokan olioita. 
Polygoneja voi olla kahta tyyppiä: aluemaisia ja viivmaisia. AluePolygoni tarjoaa viivamaiseen Polygoni luokkaan nähden lisäksi metodin sen tarkistamiseen onko piste polygonin sisällä. Tätä tarvitaan seuraavassa vaiheessa.

### 2. Syötteenä annettujen reittien analysointi.
Luokassa Maastokirjasto pidetään kirjaa siitä minkälaista vauhtia minkäkin tyyppisen polygonin alueella pääsee kulkemaan. Lisäksi pidetään yllä ylimääräistä maastoa, joka kuvaa kaikkea sitä, mikä ei kuulu minkään polygonin vaikutuspiirin. Maastokirjasto tarjoaa metodin jolla kirjastoon voidaan lisätä reitti, ja silloin jokaisen reitin pisteen kohdalla tarkistetaan onko se aluemaisen polygonin sisällä, tai viivmaisen polygonin lähellä. Jos näin on tallennetaan pisteen ja seuraavan pisteen välillä kuljettu vauhti kirjastoon polygonin maaston mukaan. Kun kirjastosta haetaan maaston vauhtia, palauttaa se keskiarvon kaikista maastoon tallennetuista vauhdeista.

### 3. Verkon generointi
Luokka Verkontekijä vastaa verkon generoinnista. Yleisperiaate on se, että solmut muodostuvat syötteenä annettujen polygonien solmuista, ja kaaria yritetään muodostaa jokaisesta solmusta jokaiseen solmuun. Kaarta ei kuitenkaan muodosteta, jos se leikkaa jonkin polygonin reunan. Koska kaarien muodostamisyrityksiä tulisi todella paljon jaetaan polygonit naapurustoihin niiden bounding boxin keskipisteen sijainnin mukaan. Kaaria pyritään muodostamaan vain samassa naapurustossa, sekä naapurustoa ympäröivissä naapurustoissa sijaitsevien polygonien solmujen kanssa. Myöskään kaaren ja polygonin leikkausta ei tarkisteta kuin lähinaapurustoissa. Jos kaari ei leikkaa mitään polygonia lisätään se pisteiden välille. Kaaren painoksi tulee aika-arvio, eli solmujen etäisyys (m)/vauhdilla (m/s).

### 4. Nopeimman reitin etsintä verkossa
Reitin etsintä tapahtuu luokssa Verkko A* algoritmia käyttäen. A* toteutus käyttää minimikekoa ja heuristiikkafunktiona pisteen etäisyyttää maalisolmuun * minimivauhti. Verkko käyttää VIERUSLISTOJA/MATRIISIA?.

### 5. Etsityn reitin kirjoittaminen tiedostoon.
Verkko tarjoaa metodin lyhyimmän reitin palauttamiseen Reitti oliona. Luokka GeoJsonKirjoittaja tarjoaa metodit reitin kirjoittamiseen geojson tiedostoon, jolloin sen jatkokäyttö esimerkiksi paikkatieto-ohjelmissa on helppoa.



