#Testausdokumentti

Ohjelman yksittäisten luokkien toimintaa on testattu JUnit yksikkötesteillä ohjelman kehityksen ajan, minkä lisäksi olen toteuttanut suorituskykytestausta. Kaikki testit sijaitsevat testipakkauksissa ja ne voidaan toistaa ajamalla testit. Yksikkötestit joko menevät läpi tai ei. Suorituskykytestaus tulostaa konsoliin tiedot suoritukseen kuluneista ajoista.

Mielenkiintoinen havainto suorituskykytestaukseen liittyen oli vieruslistan tehokkuus vierusmatriisiin nähden. Olin siis toteuttanut ohjelman alunperin vierusmatriisilla, jolloin suurin verkko jonka ohjelma pystyi generoimaan sisälsi noin 10000 solmua. 20000 solmun verkon generointi johti out of heap virheeseen. Vieruslistaan vaihdettuani onnistui viela 323495 kokoisen verkon generointi ja myös reitin etsintä nopeutui merkittävästi. (isompia verkkoja varten tarvitsisin toisen testiaineiston) 10000 solmun verkossa reitin etsintään kului keskimäärin 256ms kun käytin vierusmatriisia, kun taas vieruslistoja käyttävällä verkolla vastaava aika oli 17ms.

## Yksikkötestit
Kaikkia luokkia tiedostonkäsittelyä ja käyttöliittymää lukuunottamatta on testattu JUnit testeillä. Tiedoston käsittelyluokkien ja käyttöliittymän testaus on toteutettu käsin.

##Suorituskykytestaus
Testasin ohjelman suoritusaikoja erikokoisilla syötteillä, sekä etsin karkean arvion maksimikokoisesta syötteestä, minkä ohjelma pystyy käsittelemään. 

###Verkon generointi:

Testasin verkon generointia pääkaupunkiseudun rakennuksista ja tiestöstä koostuvalla aineistolla. Kasvatin verkon kokoa, kunnes sen luonti ei enään onnistunut.

Kaikissa testeissä Verkontekijäluokan naapuroston määriä ohjaileva vakio oli 300, ja testeillä saatu aika 10 testin keskiarvo paitsi yli 100000 solmua sisältävillä verkoilla, joilla verkon generointi tehtiin vain kerran. Verkontekijä muodostaa siis n*n naapurustoa 

Tulokset: 

![verkon generointi] (verkon_generointi_testaus_diagrammi.png)

| Solmuja	| n    	| Verkon generointiin kulunut aika ms kotikone	| aika ms laitoksella | 
| ----------|:-----:| ---------------------------------------------:| -------------------:|
|13			|1		|0		 										|0
|104		|1		|24		 										|11
|1001 		|3		|1468	 										|863
|10006 		|33		|8917	 										|1800
|100001		|333	|13121	    									|2564
|323495		|1078	|7250    										| - CG overhead - 


###Reittien etsintä:
Reittien etsintä testauksessa testasin reitin etsintää ensin samalla pääkaupunkiseutua esittävällä verkolla ja sen osilla. Näissä verkoissa kaikkien kaarien vauhti oli sama. Testasin reitin etsintää kussakin verkossa kymmenestä satunnaisesta solmusta kymmeneen satunnaiseen solmuun. Ajat ovat näiden testien keskiarvoja.

Tulokset: 

![reittien etsintä] (reittien_etsinta_testaus_diagrammi.png)

| Solmuja	| n    	| Reitin etsintään kulunut aika ms kotikone	| aika ms laitoksella | 
| ----------|:-----:| -----------------------------------------:| -------------------:|
|13			|1		|0		 									|0
|104		|1		|1		 									|0
|1001 		|3		|3		 									|0
|10006 		|33		|13		 									|10
|100001		|333	|78		    								|42
|323495		|1078	|140    									|35


Testasin myös maastojen nopeuserojen vaikutusta reitin löytymiseen. Käytin testauksessa matinkylän koealuetta ja etsin reittiä aina samojen solmujen välillä. Valitsin solmut siten että reitti kulkee koko alueen lävitse

| Nopeusero	| Reitin etsintään kulunut aika ms	|
| ----------| ---------------------------------:|
|tasavauhdit|11		 							|
|x10		|9		 							|
|x100		|9		 							|
|x1000 		|9		 							|

Nopeuseroilla ei siis vaikuta olevan hirveän suurta vaikutusta.


##Ohjelman antamien aika-arvioiden paikkansapitävyyden testaus
testasin ohjelman reittiehdotuksille antamia aika-arvioita kulkemalla ehdotetun reitin ja katsomalla sekunttikellolla kauanko siihen meni. Referenssireitti, jonka pohjalta reittejä ehdotettiin oli kuljettu polkupyörällä, joten kuljin myös reittiehdotukset fillarilla.

###reitti 1.
![reitti1](reitti1.png)

*reitti1 Matinkylässä*

    koord 374384,6671020 374869,6670602 aineisto/matinkyla
    Muodostetaan reitti solmusta 445 solmuun 4605
**Aika arvio reitille: 0h:3m:35s**

**Toteutunut: 0h:3m:48s**

###reitti2.

![reitti2](reitti1.png)

*reitti2 Matinkylässä*

    koord 375208,6670444 374384,6671020 aineisto/matinkyla
    Muodostetaan reitti solmusta 3371 solmuun 445
**Aika arvio reitille: 0h:5m:30s**

**Toteutunut: 0h:5m:51s**

###Päätelmät:
Aika-arviot ovat aavistuksen optimistisia. Syitä:
* joudun suunnistamaan kun seuraan reittiä
* en ollut yhtä virkeällä poljentapäällä


