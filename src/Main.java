import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<Fuvar> fuvarok = new ArrayList<>();


        try (BufferedReader br = new BufferedReader(new FileReader("fuvar.csv"))) {
            String sor = br.readLine();

            while ((sor = br.readLine()) != null) {
                String[] adatok = sor.split(";");
                Fuvar fuvar = new Fuvar(
                        Integer.parseInt(adatok[0]),
                        adatok[1],
                        Integer.parseInt(adatok[2]),
                        Double.parseDouble(adatok[3].replace(",", ".")),
                        Double.parseDouble(adatok[4].replace(",", ".")),
                        Double.parseDouble(adatok[5].replace(",", ".")),
                        adatok[6]
                );
                fuvarok.add(fuvar);
            }
        } catch (IOException e) {
            System.err.println("Hiba történt a fájl beolvasása során: " + e.getMessage());
        }


        int taxiAzonosito = 6185;

        long fuvarSzam = fuvarok.stream()
                .filter(fuvar -> fuvar.getTaxiId() == taxiAzonosito)
                .count();

        double osszBevetel = fuvarok.stream()
                .filter(fuvar -> fuvar.getTaxiId() == taxiAzonosito)
                .mapToDouble(fuvar -> fuvar.getViteldij() + fuvar.getBorravalo())
                .sum();

        double utazasSum = fuvarok.stream()
                .mapToDouble(Fuvar::getMegtettTavolsag)
                .sum();


        Fuvar leghosszabb = fuvarok.stream()
                .max(Comparator.comparingDouble(Fuvar::getMegtettTavolsag))
                .orElse(null);

        Fuvar legbokezubb = fuvarok.stream()
                .max(Comparator.comparingDouble(fuvar -> fuvar.getBorravalo() / fuvar.getViteldij()))
                .orElse(null);

        double sumtav = fuvarok.stream()
                .filter(fuvar -> fuvar.getTaxiId() == 4261)
                .mapToDouble(Fuvar::getMegtettTavolsag)
                .sum();

        long hibasFuvarokSzama = fuvarok.stream()
                .filter(fuvar -> fuvar.getIdotartam() > 0 && fuvar.getViteldij() > 0 && fuvar.getMegtettTavolsag() == 0)
                .count();

        double osszesIdotartam = fuvarok.stream()
                .filter(fuvar -> fuvar.getIdotartam() > 0 && fuvar.getViteldij() > 0 && fuvar.getMegtettTavolsag() == 0)
                .mapToInt(Fuvar::getIdotartam)
                .count();

        double hibasBevetel = fuvarok.stream()
                .filter(fuvar -> fuvar.getIdotartam() > 0 && fuvar.getViteldij() > 0 && fuvar.getMegtettTavolsag() == 0)
                .mapToDouble(fuvar -> fuvar.getViteldij() + fuvar.getBorravalo())
                .sum();

        boolean vane = fuvarok.stream()
                .anyMatch(fuvar -> fuvar.getTaxiId() == 1452);

        List<Fuvar> legrovidebbUtak = fuvarok.stream()
                .filter(fuvar -> fuvar.getIdotartam() > 0)
                .sorted(Comparator.comparingInt(Fuvar::getIdotartam))
                .limit(3)
                .collect(Collectors.toList());

        LocalDateTime dec24 = LocalDateTime.of(2016, 12, 24, 0, 0, 0, 0);
        LocalDateTime nextDay = dec24.plusDays(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        long decfuvarokSzama = fuvarok.stream()
                .filter(fuvar -> {
                    String indulas = fuvar.getIndulasIdopontja();
                    LocalDateTime indulasDateBen = LocalDateTime.parse(indulas, formatter);
                    return indulasDateBen.isAfter(dec24.minusSeconds(1)) && indulasDateBen.isBefore(nextDay);
                })
                .count();

        LocalDateTime dec31 = LocalDateTime.of(2016, 12, 31, 0, 0, 0, 0); // 2016-12-31 00:00:00
        long osszesFuvar = fuvarok.stream()
                .filter(fuvar -> {
                    String indulasString = fuvar.getIndulasIdopontja();
                    LocalDateTime indulas = LocalDateTime.parse(indulasString, formatter); // String konvertálása LocalDateTime-má
                    return indulas.isAfter(dec31.minusSeconds(1)) && indulas.isBefore(nextDay);
                })
                .count();

        long borravalotAdottFuvar = fuvarok.stream()
                .filter(fuvar -> {
                    String indulasString = fuvar.getIndulasIdopontja();
                    LocalDateTime indulas = LocalDateTime.parse(indulasString, formatter);
                    return indulas.isAfter(dec31.minusSeconds(1)) && indulas.isBefore(nextDay) && fuvar.getBorravalo() > 0;
                })
                .count();

        double arany = (double) borravalotAdottFuvar / osszesFuvar;

        // Eredmények kiírása
            // 1 Utazások száma
        long utazasDb = fuvarok.stream().count();
        System.out.println("Az utazások száma: " + utazasDb);

            // 2 6185-ös taxis
        System.out.println("A 6185-ös taxis bevétele: " + osszBevetel + " dollár");
        System.out.println("Fuvarok száma: " + fuvarSzam);

            // 3 Összesen hány mérföldet tettek meg a taxisok
        System.out.println("Az összes megtett mérföld: " + utazasSum);

            // 4 Leghosszabb fuvar
        System.out.println("A leghosszabb fuvar adatai: " + leghosszabb.toString());

            // 5 Legbőkezűbb
        System.out.println("A legbőkezűbb borravalójú fuvar adatai: " + legbokezubb.toString());

            // 6 4261-es taxis megtett távolsága
        System.out.println(String.format("A 4261-es azonosítójú taxis összesen %.2f km-t tett meg", sumtav*1,6));

            // 7 Hibás fuvarok
        System.out.println("A hibás fuvarok száma: " + hibasFuvarokSzama);
        System.out.println("A hibás fuvarok összes időtartama: " + osszesIdotartam + " másodperc");
        System.out.println("A hibás fuvarok teljes bevétele: " + hibasBevetel + " dollár");

            // 8 Van e 1452-es taxi
        if (vane) {
            System.out.println("A 1452-es azonosítójú taxi szerepel az adatok között.");
        } else {
            System.out.println("A 1452-es azonosítójú taxi nem szerepel az adatok között.");
        }

            // 9 Legrovidebb utak(3)
        System.out.println("A 3 legkisebb időtartamú utazás adatai:");
        legrovidebbUtak.forEach(fuvar -> {
            System.out.println("Taxi ID: " + fuvar.getTaxiId());
            System.out.println("Indulás időpontja: " + fuvar.getIndulasIdopontja());
            System.out.println("Időtartam: " + fuvar.getIdotartam() + " másodperc");
            System.out.println("Megtett távolság: " + fuvar.getMegtettTavolsag() + " mérföld");
            System.out.println("Viteldíj: " + fuvar.getViteldij() + " dollár");
            System.out.println("Borravaló: " + fuvar.getBorravalo() + " dollár");
            System.out.println("Fizetés módja: " + fuvar.getFizetesModja());
            System.out.println("----------------------");
        });

            // 10 Dec 24
        System.out.println("December 24-én történt fuvarok száma: " + decfuvarokSzama);

            // 11 Dec 31
        System.out.println("December 31-én fuvarok aránya, amelyeknél borravaló is volt: " + arany * 100 + "%");

    }
}
