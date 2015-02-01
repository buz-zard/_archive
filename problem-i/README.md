---

### Dalykinė sritis:

* People and their relatives.

### Internetinėje vartotojo sąsajoje rodomos dvi pagrindinės formos:


* žmonių sarašas – lentelė su trimis stulpeliais: vardas, pavardė ir gimimo data
* pasirinkto asmens informacijos redagavimo forma, kurioje taip pat rodomas automatiškai
surastų giminaičių sąrašas


### Required technologies:

* Java 1.6+
* Project building/packaging using Maven 3
* Trijų lygių architektūra (panaudojant Spring MVC karkasą):
  * vaizdavimo lygmenyje naudoti jsp .
  * verslo logikos lymenyje („Domain“, „Business login“, „Service), pvz. giminaičių paieškai, neprivalomi jokie programiniai karkasai.
  * duomenų prieigos lygmenyje reikia panaudoti Hibernate karkasą DAO esybes aprašant per anotacijas (*. hbm.xml failais naudotis griežtai draudžiama ).
* Automatic JUnit tests.

---
