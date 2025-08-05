package datenbank;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import defaults.Extras;

public class ZugriffExtras {

    public void addExtras(Extras extras) {
        String sql = "INSERT INTO extras (kategorie, beschreibung, preis) VALUES ( ?, ?, ?)";
        try (Connection conn = Supabaseverbindung.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            
            stmt.setString(1, extras.getKategorie());
            stmt.setString(2, extras.getBezeichnung()); // ggf. trennen in bezeichnung + beschreibung
            stmt.setDouble(3, extras.getPreis());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteExtras(int id) {
        String sql = "DELETE FROM extras WHERE id_extras = ?";
        try (Connection conn = Supabaseverbindung.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateExtras(Extras extras) {
        String sql = "UPDATE extras SET kategorie = ?, beschreibung = ?, preis = ? WHERE id_extras = ?";
        try (Connection conn = Supabaseverbindung.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, extras.getKategorie());
            stmt.setString(2, extras.getBezeichnung());
            stmt.setDouble(3, extras.getPreis());
            stmt.setInt(4, extras.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Extras> getAlleExtras() {
        List<Extras> extrasList = new ArrayList<>();
        String sql = "SELECT * FROM extras";
        try (Connection conn = Supabaseverbindung.connect();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                extrasList.add(mapResultSetToExtras(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return extrasList;
    }

    public List<Extras> getExtrasByKategorieUndBezeichnung(String kategorie, String beschreibung) {
        List<Extras> result = new ArrayList<>();
        String sql = "SELECT * FROM extras WHERE kategorie = ? AND beschreibung = ?";

        try (Connection conn = Supabaseverbindung.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, kategorie);
            stmt.setString(2, beschreibung);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                result.add(mapResultSetToExtras(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    private Extras mapResultSetToExtras(ResultSet rs) throws SQLException {
        return new Extras(
            rs.getInt("id_extras"),
            rs.getString("kategorie"),
            rs.getString("beschreibung"),
            rs.getDouble("preis")
        );
    }
} 
