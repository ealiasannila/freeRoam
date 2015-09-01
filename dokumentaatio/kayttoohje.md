#Käyttöohje

##Syötteet:
Ohjelmalle annetaan käynnistäessä argumentteina kansiot joissa on geojson tiedostoja joissa on määritelty toteutuneet reitit, sekä eri maastotyypit.

    java reittienetsinta <kansio jossa maastotyypit> <kansio jossa toteutuneet reitit> 

Kun ohjelma käynnistetään luodaan maastokirjasto ja generoidaan verkko.

##Käyttö:
Kun ohjelma on käynnistetty pyydetään käyttäjää ilmoittamaan lähtö ja maalisolmut. Käyttäjä voi tehdä tämän seuraavilla komennoilla

    hae <lahtosolmu> <maalisolmu> <tiedostonimi> tämä hakee reitin kahden solmun välillä
    koord <lonLähtö> <latLähtö> <lonMaali> <latMaali> <tiedostonimi> tämä hakee lähtö ja määrnpää koordinaatteja lähimmät solmut ja laskee reitin näiden solmujen välillä.
    kaari <lahtosolmu> <maalisolmu> tämä tulostaa kaaren kahden solmun välillä. Ei tallenna mitään.

Kun lyhyin reitti on laskettu tallentaan se käyttäjän antamaan kansioon tiedostoihin reitti.geojson ja pisteet.geojson. Tämän jälkeen käyttäjä voi tehdä samassa verkossa uusia kyselyitä, ilman että verkkoa täytyy generoida uudestaan. Mikäli käyttäjä haluaa lisätä tai poistaa verkon generoinnissa käytettävää aineistoa tulee ohjelma käynnistää uudelleen. Ohjelma suljetaan komennolla lopeta.

##GeoJson tiedostot:
GeoJson on lähes kaikkien paikkatieto-ohjelmien tukema formaatti, jolla voidaan ilmaista paikkaan liittyvää tietoa json oliona. GeoJson olioon liittyy properties muuttujan sisältämää ominaisuustietoa, sekä Geometry muuttujan sisältämä sijaintitieto. Reittien etsintä ohjelma tekee reittiehdotuksen perusteella kaksi geojson tiedostoa, reitti.geojson ja pisteet.geojson. Reitti.geojson sisältää ehdotetun reitin viivana ilman attribuuttitietoja, kun taas pisteet.geojson sisältää pisteenä jokaisen verkon solmun, joka jää reitille. Pisteet.geojson tiedostoon on jokaiselle pisteelle talletettu atributtitietona pituus ja leveys koordinaatit, sekä aika-arvio sekunteina reitin alusta.


##Tulosten visualisointi:
Geojson tiedostoja voi visualisoida helposti paikkatieto-ohjelmassa (esim. QGIS löytyy laitoksen koneilta) Ohjelman käynnistyttyä voidaan geojson aineisto lisätä näkymään vasemmassa laidassa olevan add Vector layer painikkeen kautta.

##Mallitiedostot:
Kansiossa aineisto/matinkyla/polygonit sijaitsee testiaineisto eri maastotyypeille
Kansiossa aineisto/matinkyla/reitit sijaitsee testireitti

Kansiossa aineisto/pkseutu sijaitsee suurempi testiaineisto
