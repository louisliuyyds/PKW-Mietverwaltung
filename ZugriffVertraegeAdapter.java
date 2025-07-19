package adapter;

import datenbank.ZugriffVertraege;
import datenbank.ZugriffFuhrpark;
import defaults.Mietvertrag;
import service.MietvertragService;
import adapter.ZugriffInterface;
import defaults.Fahrzeug;

import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ZugriffVertraegeAdapter implements ZugriffInterface<Mietvertrag> {

    private final ZugriffVertraege vertragsDAO = new ZugriffVertraege();
    private final MietvertragService mietvertragService = new MietvertragService(vertragsDAO, new ZugriffFuhrpark());

    @Override
    public List<Mietvertrag> getAlleDaten() {
        return vertragsDAO.getAlleVertraege();
          }

    @Override
    public void update(Mietvertrag vertrag) {
        vertragsDAO.updateVertrag(vertrag, vertrag.getExtrasId());
    }

    @Override
    public void delete(int id) {
        vertragsDAO.deleteVertrag(id);
    }

    @Override
    public void add(Mietvertrag vertrag) {
        mietvertragService.createMietvertrag(vertrag);
    }

    @Override
    public String[] getSpaltennamen() {
        return new String[] {
            "ID", "Startdatum", "Enddatum", "User-ID", "Fahrzeug-ID", "Extras-ID", "Gesamtpreis"
        };
    }

    @Override
    public Mietvertrag getByID(int id) {
        return vertragsDAO.getVertragById(id);
    }

    @Override
    public List<Mietvertrag> getByName(String vorname, String name) {
        return new ArrayList<>();
    }

    public Mietvertrag fromMap(Map<String, String> daten) {
        try {
            int id = daten.containsKey("ID") ? Integer.parseInt(daten.get("ID")) : 0;
            int userId = Integer.parseInt(daten.get("User-ID"));
            int fahrzeugId = Integer.parseInt(daten.get("Fahrzeug-ID"));
            int extrasId = Integer.parseInt(daten.get("Extras-ID"));

            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
            Date start = new Date(sdf.parse(daten.get("Startdatum")).getTime());
            Date end = new Date(sdf.parse(daten.get("Enddatum")).getTime());

            double gesamtpreis = 0.0; // Ignorieren, wird automatisch berechnet

            Mietvertrag vertrag = new Mietvertrag(id, userId, fahrzeugId, extrasId, start.toLocalDate(), end.toLocalDate(), gesamtpreis);

            // Fahrzeugobjekt ergänzen (essentiell!)
            Fahrzeug fahrzeug = new ZugriffFuhrpark().getFahrzeugById(fahrzeugId);
            vertrag.setFahrzeug(fahrzeug);

            // Extrasliste ggf. leer lassen
            vertrag.setExtrasList(new ArrayList<>());

            return vertrag;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public List<Mietvertrag> getVertraegeByUser(int userId) {
        return vertragsDAO.getVertraegeByUser(userId);
    }
} 
