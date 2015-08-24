#Testausdokumentti

Ohjelman yksittäisten luokkien toimintaa on testattu JUnit yksikkötesteillä ohjelman kehityksen ajan, minkä lisäksi olen toteuttanut suorituskykytestausta.

## Yksikkötestit
Kaikkia luokkia tiedostonkäsittelyä ja käyttöliittymää lukuunottamatta on testattu JUnit testeillä. Tiedoston käsittelyluokkien ja käyttöliittymän testaus on toteutettu käsin.

##Suorituskykytestaus
Testasin ohjelman suoritusaikoja erikokoisilla syötteillä, sekä etsin karkean arvion maksimikokoisesta syötteestä, minkä ohjelma pystyy käsittelemään. 

###Verkon generointi:

Testasin verkon generointia pääkaupunkiseudun rakennuksista ja tiestöstä koostuvalla aineistolla. Kasvatin verkon kokoa, kunnes sen luonti ei enään onnistunut.

Kaikissa testeissä Verkontekijäluokan naapuroston määriä ohjaileva vakio oli 300, ja testeillä saatu aika 10 testin keskiarvo. Verkontekijä muodostaa siis n*n naapurustoa 

Tulokset: **muuta taulukoksi**

polygonit luettu, 2 polygonia
Solmuja: 13
n: 1
Verkon generointiin kului aikaa: 2ms.

polygonit luettu, 17 polygonia
Solmuja: 104
n: 1
Verkon generointiin kului aikaa: 5ms.

polygonit luettu, 137 polygonia
Solmuja: 1001
n: 3
Verkon generointiin kului aikaa: 1240ms.

polygonit luettu, 1545 polygonia
Solmuja: 10006
n: 33

Verkon generointiin kului aikaa: 4422ms.
polygonit luettu, 2903 polygonia
Solmuja: 20005
n: 66
out of heap error

Verkon kokoa rajoittavaksi tekijäksi muodostui siis tilavaativuus. 

###Reittien etsintä:
Reittien etsintä testauksessa testasin reitin etsintää ensin samalla pääkaupunkiseutua esittävällä verkolla ja sen osilla. Näissä verkoissa kaikkien kaarien vauhti oli sama. Testasin reitin etsintää kussakin verkossa kymmenestä satunnaisesta solmusta kymmeneen satunnaiseen solmuun. Ajat ovat näiden testien keskiarvoja.

Tulokset: **muuta taulukoksi**

polygonit luettu, 2 polygonia
Solmuja: 13
Reitin etsintään kului aikaa: 0ms.

polygonit luettu, 17 polygonia
Solmuja: 104
Reitin etsintään kului aikaa: 1ms.

polygonit luettu, 137 polygonia
Solmuja: 1001
Reitin etsintään kului aikaa: 4ms.

polygonit luettu, 1545 polygonia
Solmuja: 10006
Reitin etsintään kului aikaa: 256ms.


Testasin myös maastojen nopeuserojen vaikutusta reitin löytymiseen. Käytin testauksessa matinkylän koealuetta ja etsin reittiä aina samojen solmujen välillä. Valitsin solmut siten että reitti kulkee koko alueen lävitse

Tasavauhdit
Reitin etsintään kului aikaa: 87ms.

X10
Reitin etsintään kului aikaa: 99ms.

X100
Reitin etsintään kului aikaa: 87ms.

X1000
Reitin etsintään kului aikaa: 75ms.

Nopeuseroilla ei siis vaikuta olevan hirveän suurta vaikutusta.
