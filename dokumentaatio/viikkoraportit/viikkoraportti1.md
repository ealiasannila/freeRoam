#Viikkoraportti 1

Ensimmäisellä viikolla alustin projektilleni github repon, rekisteröidyin labtooliin ja suunnittelin projektini kulkua.

Tärkein ensimmäisen viikon tehtävä oli varmastikkin aiheen valinta. Päädyin valitsemaan nopeimpien polkujen etsinnän siten, että verkon painotus tapahtuu analysoimalla toteutuneita matkoja. Tausta-ajatuksena tässä oli vapaamuotoisessa maastossa toimiva reittien etsintä, jossa käyttäjä voisi esim. tallentaa gps:n avulla metsässä kuljettuja reittejä, ja ohjelma painottaisi verkon eri osat sen mukaan, kuinka nopeasti toteutuneella polulla on kuljettu esim. metsässä vs. tiellä ja sen jälkeen antaa ehdotuksen nopeimmasta reitistä jollakin toisella reitillä.

Koska aihe on ainakin itselleni varsin haastellinen päätin aloittaa perus lyhyimmän polun etsinnästä verkossa (A*) ja siihen liittyvän minimikeon toteuttamisella, missä pääsinkin jo hyvään vauhtiin. Kun tämä osa ohjelmasta on saatu toteutettua, ajattelin toteuttaa eri maastotyyppien nopeuksien laskemisen toteutuneiden polkujen perusteella, mikä ei myöskään varmaankaan tuota suurempia ongelmia.

Tämän jälkeen verkon kaarien painottaminen perustuen eri maastotyyppeihin ei pitäisi olla vaikeaa, jos kaariin on tallennettu tieto minkä tyyppisen maaston läpi ne kulkevat. Toimivan maastonavigoinnin kannalta vaiken osuus onkin varmaankin itse verkon generoiminen, esim kuvatiedostosta. Ajattelinkin että lähtökohtaisesti tyydyn siihen, että luon verkon käsin, ja jos saan ohjelman muut osat toimimaan ja aikaa jää, perehdyn viimeisenä verkon luomiseen.

Tähän mennessä olen siis saanut kirjoitettua A* algoritmin sekä minimikeon toteutuksen, ja seuraavaksi pitäisi muokata A* toteutukseni siten että se käyttäisi minimikekoani. En ole vielä ihan varma miten saan tallennettua solmut kekoon etäysyysarvion ja etäisyyden alkuun perusteella, mutta kuitenkin saan keosta ulos solmun numeron etäisyyden sijaan. Tuskin kuitenkaan muodostuu suureksi ongelmaksi.

Tämän jäleen reittien etsintä alkaisi olla nähdäkseni kunnossa, ja voin siirtyä toteutuneiden polkujen analysointiin.
