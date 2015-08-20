#Viikkoraportti 4

Viimeviikolla tehty muutos polygonpohjaiseen verkon generointiin on tällä viikolla osoittautunut hyväksi päätökseksi ja ohjelman toteutus on edennyt hyvää tahtia. Ohjelma pystyy nyt myös hallitsemaan huomattavasti suurempia alueita, kuin rasteri pohjainen versio mihin olen varsin tyytyväinen. En ole vielä ehtinyt testata kuinka suurta aineistoa ohjelma pystyy käsittelemään.

Erityistä iloa tällä viikolla on aihettanut se, että ohjelma alkaa toimia sen verran hyvin, että se ehdottaa varsin järkeviä reittejä. Kävin tallentamassa referenssireitin matinkylässä polkupyörällä, ja reitit, joita ohjelma ehdotta referenssireitin perusteella, vaikuttavat järkeviltä. Myös reittiin liittyvä aika-arvio vaikuttaa uskottavalta. En ole vielä tosin ehtinyt testaamaan kuinka hyvin se pitää paikkaansa.

Kysymyksiä:
* On varmaankin ok käyttää valmista kirjastoa JSON tiedostojen lukemiseen ja kirjoittamiseen niinkuin tällä hetkellä on?

Mitä tällä viikolla on saatu aikaiseksi:
* Viivamaisten polygonien tuki, nyt ohjelma tukee myös viivmaista aineistoa esim. teitä. Toteutunutta reittiä analysoidessa reittipisteen katsotaan olevan viivalla, jos se on alle 4m päässä polygonista.
* Polygonien reunoja pitkin liikkuminen. Nyt kaaret muodostetaan siten, että jos kaari kulkee polygonin reunaa pitkin annetaan sen vauhdiksi, joko polygonin vauhti, tai tuntemattoman alueen vauhti, riippuen siitä kumpi on suurempi.
* Luovuin GPX formaatissa olevien reittien lukemisesta, kun tajusin että GeoJson olion luominen GPX tiedostosta on erittäin helppoa. Nyt ohjelma siis käyttää ainoastaan geojson muotoista dataa, jonka kanssa työskentely on mielyttävämpää kuin GPX datan.
* Toteutin ohjelman tarvitseman lista tietorakenteen. Toteutukseni käyttää taulukkoa jota kasvatetaan tarpeen mukaan. Kaikki tarvittavat tietorakenteet ja algoritmit on nyt siis toteutettu.
* Ohjelmasta on saatu kitkettyä joitakin sitkeitä bugeja. Ehkäpä hankalimmin löydettävä bugi liittyi siihen, että a*:rin käyttämä heuristiikkafuntio antoi liian suuria arvoja, jos todellisella polulla oli kaaria, joiden vauhti oli alle 1.
* Toteutin komentorivipohjaisen käyttöliittymän. Nyt verkko generoidaan vain ohjelmaa käynnistettäessä, minkä jälkeen voidaan tehdä lyhyimmän reitin kyselyitä ilman, että verkkoa täytyy luoda uudestaan.
* Verkontekijän toimintaa on saatua selkeytettyä huomattavasti ja generoidut verkot alkavat olla varsin järkeviä.
* Kirjoitettu lisää testejä. Nyt kaikilla luokilla tiedostonkäsittelyä ja käyttöliittymää lukuunottamatta on edes jossain määrin kattavat JUnit testit.
* Koodia on siistitty ja toteutetut tietorakenteet (pino, minimikeko, lista) ovat nyt monikäyttöisempiä kuin ennen.
* Ohjelma antaa nyt ulos reitin lisäksi jokaiselle reitin pisteelle aika-arvion reitin alusta.
* Dokumentaatioon on saatu aluille käyttöohje, sekä toteutusdokumentti.


Mitä seuraavaksi: 
* Suorituskykytestauksen aloittaminen.
 - testataan verkon generointia ja reitin hakua erikokoisilla aineistoilla. Selvitetään kuinka suuren aineiston ohjelma pystyy käsittelemään.
 - testataan maastokirjaston laajuuden, ja eri maastojen nopeuserojen vaikutusta reitin etsintään.
* Aika- ja tilavaativuus analyysi.
* Testataan ohjelman antamien ehdotusten paikkaansapitävyyttä todellisessa maailmassa. Suoritetaan polkupyörän ja sekunttikellon avulla.

