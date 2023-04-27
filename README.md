# Spoje v městské hromadné dopravě
## Motivace a popis problému
Úkolem je vytvořit program, který bude vyhledávat možná spojení městské
hromadné dopravy ze stanice na stanici a vypisovat dobu trvání.
K sadě jsem si vybral otevřená data Prazské integrované dopravy.

Osobní motivací je, abych implementoval úlohu, kterou již mám v C++ vyřešenou
pomocí algoritmu BFS (vyhledávání do šířky), zkusil si kód napsat v Javě,
pokusit se naprogramovat jiné prohledávací algoritmy (DFS, Dijskra) a porovnat
efektivitu. Zároveň chci mé řešení rozšířit pouze ze stanic metra i na tramvaje,
autobusy, trolejbusy, vlaky, přívozy a lanové pozemní dráhy. 

## Řešení
Program načte ze souboru názvy stanic MHD a délku přesunu mezi nimi, následně
vytvoří neorientovaný graf, nad kterým bude vyhledávat cesty pomocí algoritmu
Vyhledávání do šířky.

Program po spuštění disponuje nabídkou funkcí:
 - vyhledávání
   - název stanice Z (s podporou REGEX)
   - název stanice DO (s podporou REGEX)
 - historie vyhledávání

## Testování
K testování poslouží knihovna JUnit verze 5.

__Status JUnit5 testů__ [![JUnit5 Tests](https://github.com/TUL2223ALG/2223alg2-semestralproject-daniel.adamek/actions/workflows/maven.yml/badge.svg)](https://github.com/TUL2223ALG/2223alg2-semestralproject-daniel.adamek/actions/workflows/maven.yml)
