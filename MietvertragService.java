package service;

import datenbank.ZugriffVertraege;
import datenbank.ZugriffFuhrpark;
import defaults.Mietvertrag;
import defaults.Fahrzeug;
import defaults.User;

import javax.swing.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class MietvertragService {

    private final ZugriffVertraege vertragsZugriff;
    private final ZugriffFuhrpark fahrzeugZugriff;

    public MietvertragService(ZugriffVertraege vertragsZugriff, ZugriffFuhrpark fahrzeugZugriff) {
        this.vertragsZugriff = vertragsZugriff;
        this.fahrzeugZugriff = fahrzeugZugriff;
    }

    public boolean createMietvertrag(Mietvertrag vertrag) {
        if (!pruefeStartdatum(vertrag.getStartdatum())) {
            zeigeFehlermeldung("Startdatum darf nicht in der Vergangenheit liegen.");
            return false;
        }

        if (!pruefeMaxDauer(vertrag.getStartdatum(), vertrag.getEnddatum())) {
            zeigeFehlermeldung("Die maximale Mietdauer beträgt 14 Tage.");
            return false;
        }

        if (!pruefeKeineUeberschneidung(vertrag.getUserId(), vertrag.getStartdatum(), vertrag.getEnddatum())) {
            zeigeFehlermeldung("Ein Kunde kann nur ein Fahrzeug gleichzeitig mieten.");
            return false;
        }

        Fahrzeug fahrzeug = fahrzeugZugriff.getFahrzeugById(vertrag.getFahrzeugId());
        if (fahrzeug == null || !fahrzeug.isVerfuegbar()) {
            zeigeFehlermeldung("Das Fahrzeug ist nicht verfügbar.");
            return false;
        }
        if (!pruefeFahrzeugVerfuegbar(vertrag.getFahrzeugId(), vertrag.getStartdatum(), vertrag.getEnddatum())) {
            zeigeFehlermeldung("Fahrzeug ist im gewählten Zeitraum bereits vermietet.");
            return false;
        }


        // Fahrzeug blockieren
        //fahrzeug.setVerfuegbar(false);
        //fahrzeugZugriff.updateFahrzeug(fahrzeug);

        // Mietvertrag speichern
        vertragsZugriff.addVertrag(vertrag, vertrag.getExtrasId());
        return true;
    }

    private boolean pruefeStartdatum(LocalDate startdatum) {
        return !startdatum.isBefore(LocalDate.now());
    }

    private boolean pruefeMaxDauer(LocalDate start, LocalDate ende) {
        long tage = ChronoUnit.DAYS.between(start, ende);
        return tage <= 14;
    }

    private boolean pruefeKeineUeberschneidung(int userId, LocalDate start, LocalDate ende) {
        List<Mietvertrag> vertraege = vertragsZugriff.getVertraegeByUser(userId);
        for (Mietvertrag mv : vertraege) {
            if (!(ende.isBefore(mv.getStartdatum()) || start.isAfter(mv.getEnddatum()))) {
                return false;
            }
        }
        return true;
    }
    private boolean pruefeFahrzeugVerfuegbar(int fahrzeugId, LocalDate start, LocalDate ende) {
        List<Mietvertrag> vertraege = vertragsZugriff.getVertraegeByFahrzeug(fahrzeugId);
        for (Mietvertrag mv : vertraege) {
            if (!(ende.isBefore(mv.getStartdatum()) || start.isAfter(mv.getEnddatum()))) {
                return false; // Überschneidung gefunden
            }
        }
        return true;
    }


    private void zeigeFehlermeldung(String text) {
        JOptionPane.showMessageDialog(null, text, "Ungültiger Mietvertrag", JOptionPane.ERROR_MESSAGE);
    }
} 
