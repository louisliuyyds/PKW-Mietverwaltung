package datenbank;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import defaults.Fahrzeug;

public class ZugriffFuhrpark {

    public void addFahrzeug(Fahrzeug fahrzeug) {
        String sql = "INSERT INTO fuhrpark (kategorie, marke, modell, kennzeichen, getriebe, anzahl_sitze, treibstoff, preis, verfuegbarkeit) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = Supabaseverbindung.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, fahrzeug.getKategorie());
            stmt.setString(2, fahrzeug.getMarke());
            stmt.setString(3, fahrzeug.getModell());
            stmt.setString(4, fahrzeug.getKennzeichen());
            stmt.setString(5, fahrzeug.getGetriebe());
            stmt.setInt(6, fahrzeug.getAnzahlSitze());
            stmt.setString(7, fahrzeug.getTreibstoff());
            stmt.setDouble(8, fahrzeug.getPreis());
            stmt.setBoolean(9, fahrzeug.isVerfuegbar());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteFahrzeug(int id) {
        String sql = "DELETE FROM fuhrpark WHERE id_fuhrpark = ?";
        try (Connection conn = Supabaseverbindung.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateFahrzeug(Fahrzeug fahrzeug) {
        String sql = "UPDATE fuhrpark SET kategorie = ?, marke = ?, modell = ?, kennzeichen = ?, getriebe = ?, anzahl_sitze = ?, treibstoff = ?, preis = ?, verfuegbarkeit = ? WHERE id_fuhrpark = ?";
        try (Connection conn = Supabaseverbindung.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, fahrzeug.getKategorie());
            stmt.setString(2, fahrzeug.getMarke());
            stmt.setString(3, fahrzeug.getModell());
            stmt.setString(4, fahrzeug.getKennzeichen());
            stmt.setString(5, fahrzeug.getGetriebe());
            stmt.setInt(6, fahrzeug.getAnzahlSitze());
            stmt.setString(7, fahrzeug.getTreibstoff());
            stmt.setDouble(8, fahrzeug.getPreis());
            stmt.setBoolean(9, fahrzeug.isVerfuegbar());
            stmt.setInt(10, fahrzeug.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Fahrzeug> getAlleFahrzeuge() {
        List<Fahrzeug> fahrzeuge = new ArrayList<>();
        String sql = "SELECT * FROM fuhrpark";
        try (Connection conn = Supabaseverbindung.connect();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                fahrzeuge.add(mapResultSetToFahrzeug(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fahrzeuge;
    }

    public Fahrzeug getFahrzeugById(int id) {
        String sql = "SELECT * FROM fuhrpark WHERE id_fuhrpark = ?";
        try (Connection conn = Supabaseverbindung.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToFahrzeug(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Fahrzeug> getFahrzeugeByName(String marke, String modell) {
        List<Fahrzeug> fahrzeuge = new ArrayList<>();
        String sql = "SELECT * FROM fuhrpark WHERE LOWER(marke) LIKE ? AND LOWER(modell) LIKE ?";

        try (Connection conn = Supabaseverbindung.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + marke.toLowerCase() + "%");
            stmt.setString(2, "%" + modell.toLowerCase() + "%");

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                fahrzeuge.add(mapResultSetToFahrzeug(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fahrzeuge;
    }

    public Fahrzeug getFahrzeugObjekt(int id) {
        return getFahrzeugById(id);
    }
    public List<Fahrzeug> getVerfuegbareFahrzeuge(LocalDate start, LocalDate ende) {
        List<Fahrzeug> verfuegbareFahrzeuge = new ArrayList<>();

        String sql = "SELECT id_fuhrpark FROM fuhrpark " +
                     "WHERE id_fuhrpark NOT IN (" +
                     "  SELECT id_fuhrpark FROM mietvertraege " +
                     "  WHERE NOT (enddatum < ? OR startdatum > ?)" +
                     ")";

        try (Connection conn = Supabaseverbindung.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, Date.valueOf(start));
            stmt.setDate(2, Date.valueOf(ende));

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id_fuhrpark");
                Fahrzeug fahrzeug = getFahrzeugById(id);
                if (fahrzeug != null) {
                    verfuegbareFahrzeuge.add(fahrzeug);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return verfuegbareFahrzeuge;
    }


    private Fahrzeug mapResultSetToFahrzeug(ResultSet rs) throws SQLException {
        return new Fahrzeug(
            rs.getInt("id_fuhrpark"),
            rs.getString("kategorie"),
            rs.getString("marke"),
            rs.getString("modell"),
            rs.getString("kennzeichen"),
            rs.getString("getriebe"),
            rs.getInt("anzahl_sitze"),
            rs.getString("treibstoff"),
            rs.getDouble("preis"),
            rs.getBoolean("verfuegbarkeit")
        );
    }
} 
