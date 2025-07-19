package adapter;

import datenbank.ZugriffFuhrpark;
import defaults.Fahrzeug;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ZugriffFahrzeugAdapter implements ZugriffInterface<Fahrzeug> {

    private final ZugriffFuhrpark zugriff = new ZugriffFuhrpark();

    @Override
    public List<Fahrzeug> getAlleDaten() {
        return zugriff.getAlleFahrzeuge();
    }

    @Override
    public void update(Fahrzeug fahrzeug) {
        zugriff.updateFahrzeug(fahrzeug);
    }

    @Override
    public void delete(int id) {
        zugriff.deleteFahrzeug(id);
    }

    @Override
    public void add(Fahrzeug fahrzeug) {
        zugriff.addFahrzeug(fahrzeug);
    }

    @Override
    public String[] getSpaltennamen() {
        return new String[]{
            "ID", "Kategorie", "Marke", "Modell", "Kennzeichen", "Getriebe", "Anzahl Sitze", "Treibstoff", "Preis", "Verfügbarkeit"
        };
    }

    @Override
    public Fahrzeug getByID(int id) {
        return zugriff.getFahrzeugById(id);
    }

    @Override
    public List<Fahrzeug> getByName(String marke, String modell) {
        return zugriff.getFahrzeugeByName(marke, modell);
    }

    public Fahrzeug fromMap(Map<String, String> daten) {
        try {
            int id = daten.containsKey("ID") ? Integer.parseInt(daten.get("ID")) : 0;
            String kategorie = daten.get("Kategorie");
            String marke = daten.get("Marke");
            String modell = daten.get("Modell");
            String kennzeichen = daten.get("Kennzeichen");
            String getriebe = daten.get("Getriebe");
            int sitze = Integer.parseInt(daten.get("Anzahl Sitze"));
            String treibstoff = daten.get("Treibstoff");
            double preis = Double.parseDouble(daten.get("Preis"));
            boolean verfuegbar = daten.get("Verfügbarkeit").equalsIgnoreCase("true") || daten.get("Verfügbarkeit").equalsIgnoreCase("ja");

            return new Fahrzeug(id, kategorie, marke, modell, kennzeichen, getriebe, sitze, treibstoff, preis, verfuegbar);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
} 
