# Huffmanův kód
## Motivace a popis problému
Úkolem je vytvořit program implementující sadu metod, který 
dokáže komprimovat a dekomprimovat vstupní soubory Huffmanovým kódem. 
Typickým vstupním souborem je textový soubor. Výsledkem je soubor binární.

Program umí vyhledat soubory ve filesystému pomocí regulárního výrazu a následně
zkompromovat. Po komprimaci vyhodnotí ušetřenou velikost a čas komprimace. 

### Co to je Huffmanův kód?
_Huffmanův kód je princip komprese dat, který využívá statistických vlastností
ukládání dat. V typických souborech jsou typické hodnody (např. znaky) zastoupeny
s velmi nerovnoměrnou četností. Například mezery v typickém textu, naopak znak __ř__
bude málo frekventovaný._

_Huffmanův kód zpravuje analýzu četnosti výskytu jednotlivých znaků a podle četnosti
přidělí jednotlivým znakům kódy. Kódy mají různou délku, typicky 1 až desítky bitů.
Často se vyskytující znaky dostanou kódy kratší, málo časté znaky dostanou kód další.
To v důsledku vede k úspoře místa._

## Řešení
Program po spuštění disponuje nabídkou funkcí:
 - komprimace
   - název souboru (s podporou REGEX)
 - dekomprimace
   - název souboru (s podporou REGEX)
 - historická statistika

Statistika spočívá v zobrazení uložených záznamů a výpisu efektivity použití programu.

## Testování
K testování poslouží knihovna JUnit verze 5.