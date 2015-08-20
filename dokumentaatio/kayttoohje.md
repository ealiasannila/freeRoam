#Käyttöohje

##Syötteet:
Ohjelmalle annetaan käynnistäessä argumentteina kansiot joissa on geojson tiedostoja joissa on määritelty toteutuneet reitit, sekä eri maastotyypit.

    java reittienetsinta <kansio jossa maastotyypit> <kansio jossa toteutuneet reitit> 

Kun ohjelma käynnistetään luodaan maastokirjasto ja generoidaan verkko.

##Käyttö
Kun ohjelma on käynnistetty pyydetään käyttäjää ilmoittamaan lähtö ja maalisolmut. Käyttäjä voi tehdä tämän seuraavilla komennoilla

    hae <lahtosolmu> <maalisolmu> <tiedostonimi> tämä hakee reitin kahden solmun välillä
    koord <lonLähtö> <latLähtö> <lonMaali> <latMaali> <tiedostonimi> tämä hakee lähtö ja määrnpää koordinaatteja lähimmät solmut ja laskee reitin näiden solmujen välillä.

Kun lyhyin reitti on laskettu tallentaan se käyttäjän antamaan tiedostoon. Tämän jälkeen käyttäjä voi tehdä samassa verkossa uusia kyselyitä, ilman että verkkoa täytyy generoida uudestaan. Mikäli käyttäjä haluaa lisätä tai poistaa verkon generoinnissa käytettävää aineistoa tulee ohjelma käynnistää uudelleen. Ohjelma suljetaan komennolla lopeta.

##Mallitiedostot
Kansiossa aineisto/matinkyla/polygonit sijaitsee testiaineisto eri maastotyypeille
Kansiossa aineisto/matinkyla/reitit sijaitsee testireitti
