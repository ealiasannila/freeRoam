#Testausdokumentti

Ohjelman yksittäisten luokkien toimintaa on testattu JUnit yksikkötesteillä ohjelman kehityksen ajan, minkä lisäksi olen toteuttanut suorituskykytestausta. Kaikki testit sijaitsevat testipakkauksissa ja ne voidaan toistaa ajamalla testit. Yksikkötestit joko menevät läpi tai ei. Suorituskykytestaus tulostaa konsoliin tiedot suoritukseen kuluneista ajoista.

Mielenkiintoinen havainto suorituskykytestaukseen liittyen oli vieruslistan tehokkuus vierusmatriisiin nähden. Olin siis toteuttanut ohjelman alunperin vierusmatriisilla, jolloin suurin verkko jonka ohjelma pystyi generoimaan sisälsi noin 10000 solmua. 20000 solmun verkon generointi johti out of heap virheeseen. Vieruslistaan vaihdettuani onnistui viela (XXX) kokoisen verkon generointi ja myös reitin etsintä nopeutui merkittävästi. 10000 solmun verkossa reitin etsintään kului keskimäärin 256ms kun käytin vierusmatriisia, kun taas vieruslistoja käyttävällä verkolla vastaava aika oli 17ms.

## Yksikkötestit
Kaikkia luokkia tiedostonkäsittelyä ja käyttöliittymää lukuunottamatta on testattu JUnit testeillä. Tiedoston käsittelyluokkien ja käyttöliittymän testaus on toteutettu käsin.

##Suorituskykytestaus
Testasin ohjelman suoritusaikoja erikokoisilla syötteillä, sekä etsin karkean arvion maksimikokoisesta syötteestä, minkä ohjelma pystyy käsittelemään. 

###Verkon generointi:

Testasin verkon generointia pääkaupunkiseudun rakennuksista ja tiestöstä koostuvalla aineistolla. Kasvatin verkon kokoa, kunnes sen luonti ei enään onnistunut.

Kaikissa testeissä Verkontekijäluokan naapuroston määriä ohjaileva vakio oli 300, ja testeillä saatu aika 10 testin keskiarvo paitsi yli 100000 solmua sisältävillä verkoilla, joilla verkon generointi tehtiin vain kerran. Verkontekijä muodostaa siis n*n naapurustoa 

Tulokset: **muuta taulukoksi**


polygonit luettu, 17 polygonia
Solmuja: 104
n: 1
Verkon generointiin kului aikaa: 58ms.
polygonit luettu, 13942 polygonia
Solmuja: 100001
n: 333
Verkon generointiin kului aikaa: 13121ms.
polygonit luettu, 2 polygonia
Solmuja: 13
n: 1
Verkon generointiin kului aikaa: 0ms.
polygonit luettu, 137 polygonia
Solmuja: 1001
n: 3
Verkon generointiin kului aikaa: 1516ms.
polygonit luettu, 1545 polygonia
Solmuja: 10006
n: 33
Verkon generointiin kului aikaa: 6983ms.
polygonit luettu, 48658 polygonia
Solmuja: 323495
n: 1078
Verkon generointiin kului aikaa: 7250ms.


###Reittien etsintä:
Reittien etsintä testauksessa testasin reitin etsintää ensin samalla pääkaupunkiseutua esittävällä verkolla ja sen osilla. Näissä verkoissa kaikkien kaarien vauhti oli sama. Testasin reitin etsintää kussakin verkossa kymmenestä satunnaisesta solmusta kymmeneen satunnaiseen solmuun. Ajat ovat näiden testien keskiarvoja.

Tulokset: **muuta taulukoksi**
polygonit luettu, 813 polygonia
Solmuja: 5402
n: 18
Reitin etsintään kului aikaa: 7ms.

polygonit luettu, 17 polygonia
Solmuja: 104
n: 1
Reitin etsintään kului aikaa: 1ms.

polygonit luettu, 137 polygonia
Solmuja: 1001
n: 3
Reitin etsintään kului aikaa: 2ms.

polygonit luettu, 1545 polygonia
Solmuja: 10006
n: 33
Reitin etsintään kului aikaa: 14ms.

polygonit luettu, 13942 polygonia
Solmuja: 100001
n: 333
Reitin etsintään kului aikaa: 97ms.

polygonit luettu, 2 polygonia
Solmuja: 13
n: 1
Reitin etsintään kului aikaa: 0ms.



Testasin myös maastojen nopeuserojen vaikutusta reitin löytymiseen. Käytin testauksessa matinkylän koealuetta ja etsin reittiä aina samojen solmujen välillä. Valitsin solmut siten että reitti kulkee koko alueen lävitse

Tasavauhdit
polygonit luettu, 813 polygonia
Solmuja: 5402
n: 18
Reitin etsintään kului aikaa: 11ms.

X1000
polygonit luettu, 813 polygonia
Solmuja: 5402
n: 18
Reitin etsintään kului aikaa: 9ms.

X100
polygonit luettu, 813 polygonia
Solmuja: 5402
n: 18
Reitin etsintään kului aikaa: 9ms.

X10
polygonit luettu, 813 polygonia
Solmuja: 5402
n: 18
Reitin etsintään kului aikaa: 9ms.

Nopeuseroilla ei siis vaikuta olevan hirveän suurta vaikutusta.


##fillaritestit
reitti 1.
koord 374384,6671020 374869,6670602 aineisto/matinkyla
Muodostetaan reitti solmusta 445 solmuun 4605
Aika arvio reitille: 0h:3m:35s
Toteutunut: 0h:3m:48s

reitti2.
koord 375208,6670444 374384,6671020 aineisto/matinkyla
Muodostetaan reitti solmusta 3371 solmuun 445
Puretaan reitti
Aika arvio reitille: 0h:5m:30s
Toteutunut: 0h:5m:51s

