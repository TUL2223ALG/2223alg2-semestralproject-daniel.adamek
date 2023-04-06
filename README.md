# Spoje v městské hromadné dopravě
## Motivace a popis problému
Úkolem je vytvořit program, který bude vyhledávat možná spojení městské
hromadné dopravy ze stanice na stanici a vypisovat dobu trvání.
K sadě jsem si vybral otevřená data Prazské integrované dopravy.

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
