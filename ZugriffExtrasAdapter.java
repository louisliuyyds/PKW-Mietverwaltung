package adapter;

import datenbank.ZugriffExtras;
import defaults.Extras;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ZugriffExtrasAdapter implements ZugriffInterface<Extras> {

    private final ZugriffExtras zugriff = new ZugriffExtras();

    @Override
    public List<Extras> getAlleDaten() {
        return zugriff.getAlleExtras();
    }

    @Override
    public void update(Extras extras) {
        zugriff.updateExtras(extras);
    }

    @Override
    public void delete(int id) {
        zugriff.deleteExtras(id);
    }

    @Override
    public void add(Extras extras) {
        zugriff.addExtras(extras);
    }

    @Override
    public String[] getSpaltennamen() {
        return new String[]{"ID", "Kategorie", "Beschreibung", "Preis"};
    }

    @Override
    public Extras getByID(int id) {
        List<Extras> alle = zugriff.getAlleExtras();
        for (Extras e : alle) {
            if (e.getId() == id) return e;
        }
        return null;
    }

    @Override
    public List<Extras> getByName(String kategorie, String beschreibung) {
        return zugriff.getExtrasByKategorieUndBezeichnung(kategorie, beschreibung);
    }

    public Extras fromMap(Map<String, String> daten) {
        try {
            int id = daten.containsKey("ID") ? Integer.parseInt(daten.get("ID")) : 0;
            String bezeichnung = daten.get("Beschreibung");
            double preis = Double.parseDouble(daten.get("Preis"));
            return new Extras(id, bezeichnung, preis);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
} 