# ChirpTrip

Lennureisijale lennu planeerimise ja lennukis istekohtade soovitamise veebirakendus.

CGI suvepraktika proovitöö

## Kasutamine

Rakenduse käivitamiseks on soovituslik kasutada Docker-it.

```bash
git clone https://github.com/OskarUnn/chirptrip.git
cd chirptrip
docker compose up
```

Seejärel saab rakenduse avada veebilehitsejas aadressil [localhost](http://localhost)

## Autori kommentaarid

Rakenduse tegemiseks kulus 16h

### Lendude andmed

Rakendus koostab esmakäivitusel võlts andmed 1000 lennu kohta valides Openflights andmestikust kaks suvalist lennujaama Euroopa piirkonnast ja valib sellele lennule suvalise väljumisaja järgmise nädala jooksul. Igale lennule genereeritakse ka suvaline hõivatud istmete plaan nii et umbes pooled istmed on hõivatud lennukis. Lennu hind sõltub lennu kaugusest ja natukesest suvalisusest.

Alguses oli autoril soov laadida andmed reaalajas töötavast lendude veebiteenusest, aga ta ei leidnud sellist mis oleks tasuta ja ei vajaks API võtit.

### Lendude filtreerimine

Lende saab filtreerida kolmel kriteeriumil:
1. **Alguskoht**- lennu alguskoht, olgu selleks kas linn, riik või [rahvusvaheline lennujaama kood](https://en.wikipedia.org/wiki/IATA_airport_code), alguskoha valimisel ei näidata seda otsingu tulemuste nimekirjas
2. **Sihtkoht**- lennu sihtkoht, olgu selleks kas linn, riik või [rahvusvaheline lennujaama kood](https://en.wikipedia.org/wiki/IATA_airport_code)
3. **Hind**- maksimaalne istekoha hind eurodes

### Millega jäin hätta?

- Andmebaasile päringu tegemine koos ajatsooni teadlikke andmetüüpidega- seda ma lõpuks tööle ei saanud ja jätsin tegemata

### Mis jäi lahendamata?

- **Istekohtade arvu valimine**- seda ei jõudnud implementeerida, selle asemel luban kasutajal järjest valida soovitud istekohti
- **Istekohtade soovitamine**- oleksin selle lahendanud nii, et kasutaja saaks valida nimekirjast millised kohad talle meeldivad (rohkem jalaruumi jne) ning peale nupuvajutust valinud kasutajale vastavalt tingimustele parimad kohad, see kõik oleks toimunud frontendi osa peal

### Muud teadaolevad vead ja puudused

- Rakendus ei kontrolli, et lend, millele piletid osteti, ei ole juba väljunud.
- Veebileht näeb päris kole välja
- Lendude päringud peaksid olema lehtedeks jaotatud (saata korraga kuni 50 lennu infot, mitte kõik 1000 korraga)

### Väliste allikate kasutamine

Spring Booti ja muude Java teekide kasutamiseks oli autorile suureks kasuks artikklid veebilehel [Baeldung](https://www.baeldung.com/).

Kõik backendi kood on autori enda kirjutatud, vä kui on kommentaariga viidatud allikale, kust see on saadud.

Frontendi loomiseks kasutas autor Cursor IDE-t.
