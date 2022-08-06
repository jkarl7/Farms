AS CGI Eesti proovitöö.

Autor: Karl Jürgenson

---------- Instrukstioonid ----------

Eeldused projekti käivitamiseks:
Projekt kasutab Java SDK 11 (see peab arvutis olemas olema)
Projekt võtab sisse sellises formaadis .csv faile nagu on maaalad.csv
-------------------------------------
Projektis on olemas juba gradle wrapper (st ei pea arvutis eraldi gradlet installima). Versioon: 7.5
Projekt kasutab Spring Boot versioon: 2.7.2

* Tõmmata projekt alla Git-st (git clone https://github.com/jkarl7/Farms.git) või paigaldada manustesse lisatud ZIP fail kuskile kõvakettale

1) Projekti käivitamine läbi IDE (nt Intellj IDEA)

- Panna projekti juurkausta maaalad.csv (see on default failinimi, kust andmeid loetakse. Samuti on lihtsam kui .csv fail asub projekti juurkaustas, muidu peab täpsemat Pathi failini ette määrama)
    - Kui on soov mingist muu nimega failist lugeda, siis tuleb "Run configurations" alt "Program arguments" lahtrisse määrata soovitud failinimi <failinimi>.csv
    - Run
    - Projekti juurkausta tekib tulemused.csv

2) Projekti käivitamine läbi JAR faili
* Minna projekti juurkausta ja käivitada gradlew build
* Navigeerida projekti juurkaustast build/libs kausta
* Siia tasub lihtsuse mõttes panna maaalad.csv
* Käivitada command linelt java -jar Farms-0.0.1-SNAPSHOT.jar (NB! Kui on mingi muu nimega fail, siis kujul java -jar Farms-0.0.1-SNAPSHOT.jar <failinimi>.csv)
* Samasse kausta, kus asub JAR fail tekib tulemused.csv