package datenbank;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import defaults.Extras;
import defaults.Fahrzeug;
import defaults.Mietvertrag;

public class ZugriffVertraege {

    public void addVertrag(Mietvertrag vertrag, int extrasId) {
        String sql = "INSERT INTO mietvertraege (startdatum, enddatum, id_users, id_fuhrparks, id_extra, gesamtpreis) VALUES ( ?, ?, ?, ?, ?, ?)";
        try (Connection conn = Supabaseverbindung.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

           
            stmt.setDate(1, Date.valueOf(vertrag.getStartdatum()));
            stmt.setDate(2, Date.valueOf(vertrag.getEnddatum()));
            stmt.setInt(3, vertrag.getUserId());
            stmt.setInt(4, vertrag.getFahrzeugId());
            stmt.setInt(5, extrasId);
            stmt.setDouble(6, vertrag.getGesamtpreis());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteVertrag(int id) {
        String sql = "DELETE FROM mietvertraege WHERE id = ?";
        try (Connection conn = Supabaseverbindung.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateVertrag(Mietvertrag vertrag, int extrasId) {
        String sql = "UPDATE mietvertraege SET startdatum = ?, enddatum = ?, id_users = ?, id_fuhrparks = ?, id_extra = ?, gesamtpreis = ? WHERE id = ?";
        try (Connection conn = Supabaseverbindung.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, Date.valueOf(vertrag.getStartdatum()));
            stmt.setDate(2, Date.valueOf(vertrag.getEnddatum()));
            stmt.setInt(3, vertrag.getUserId());
            stmt.setInt(4, vertrag.getFahrzeugId());
            stmt.setInt(5, extrasId);
            stmt.setDouble(6, vertrag.getGesamtpreis());
            stmt.setInt(7, vertrag.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Mietvertrag getVertragById(int id) {
        String sql = "SELECT * FROM mietvertraege WHERE id = ?";
        try (Connection conn = Supabaseverbindung.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToVertrag(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Mietvertrag> getAlleVertraege() {
        List<Mietvertrag> vertraege = new ArrayList<>();
        String sql = "SELECT * FROM mietvertraege";
        try (Connection conn = Supabaseverbindung.connect();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                vertraege.add(mapResultSetToVertrag(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vertraege;
    }

    private Mietvertrag mapResultSetToVertrag(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        int userId = rs.getInt("id_users");
        int fahrzeugId = rs.getInt("id_fuhrparks");
        int extrasId = rs.getInt("id_extra");
        LocalDate start = rs.getDate("startdatum").toLocalDate();
        LocalDate end = rs.getDate("enddatum").toLocalDate();
        double preis = rs.getDouble("gesamtpreis");

        Mietvertrag vertrag = new Mietvertrag(id, userId, fahrzeugId, extrasId, start, end, preis);
       

        // Optional: vollständige Objekte nachladen
        Fahrzeug fahrzeug = new ZugriffFuhrpark().getFahrzeugObjekt(fahrzeugId);
        if (fahrzeug != null) {
            vertrag.setFahrzeug(fahrzeug);
        }


        return vertrag;
    }
    
    public List<Mietvertrag> getVertraegeByUser(int userId) {
        List<Mietvertrag> liste = new ArrayList<>();
        String sql = "SELECT * FROM mietvertraege WHERE id_users = ?";

        try (Connection conn = Supabaseverbindung.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);

            try (ResultSet rs = stmt.executeQuery()) {
                ZugriffFuhrpark fahrzeugDAO = new ZugriffFuhrpark();

                while (rs.next()) {
                    int id = rs.getInt("id");
                    LocalDate startdatum = rs.getDate("startdatum").toLocalDate();
                    LocalDate enddatum = rs.getDate("enddatum").toLocalDate();
                    int idUser = rs.getInt("id_users");
                    int idFahrzeug = rs.getInt("id_fuhrparks");
                    int idExtras = rs.getInt("id_extra");
                    double gesamtpreis = rs.getDouble("gesamtpreis");

                    Mietvertrag mv = new Mietvertrag(id, idUser, idFahrzeug, idExtras, startdatum, enddatum, gesamtpreis);
                    Fahrzeug fahrzeug = fahrzeugDAO.getFahrzeugById(idFahrzeug);
                    mv.setFahrzeug(fahrzeug);

                    liste.add(mv);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return liste;
    }

    public List<Mietvertrag> getVertraegeByFahrzeug(int fahrzeugId){
        List<Mietvertrag> liste = new ArrayList<>();
        String sql = "SELECT * FROM mietvertraege WHERE id_fuhrparks = ?";

        try (Connection conn = Supabaseverbindung.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, fahrzeugId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                int userId = rs.getInt("id_users");
                int extrasId = rs.getInt("id_extra");
                LocalDate start = rs.getDate("startdatum").toLocalDate();
                LocalDate end = rs.getDate("enddatum").toLocalDate();
                double preis = rs.getDouble("gesamtpreis");

                Mietvertrag mv = new Mietvertrag(id, userId, fahrzeugId, extrasId, start, end, preis);
                liste.add(mv);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return liste;
    }
}