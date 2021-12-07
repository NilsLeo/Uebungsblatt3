package de.htwberlin.dao;

import de.htwberlin.exceptions.DataException;
import de.htwberlin.object.Buchung;
import de.htwberlin.object.Mautkategorie;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * Die Klasse zeigt die Implementierung des MautkategorieDao an einem Beispiel
 *
 * @author Gruppe 07
 **/
public class MautkategorieDaoImpl  implements MautkategorieDao {

        private Connection connection;

        public MautkategorieDaoImpl(Connection connection) {
            this.connection = connection;

        }

        private Connection getConnection() {
            if (connection == null) {
                throw new DataException("Connection not set");
            }
            return connection;
        }

    @Override
    public void updateMautkategorie(Mautkategorie mautkategorie) {


    }

    @Override
    public Mautkategorie findMautkategorie(int Mautkategorieid) {
        return null;
    }


    @Override
    public Mautkategorie createMautkategorie(int Mautkategorienummer) {
        return null;
    }

    @Override
        public Mautkategorie findMautkategorie(int sskl_id, String achszahl) {
            PreparedStatement ps = null;
            ResultSet rs = null;
            String query = "select * from Mautkategorie where achszahl= ? and sskl_id = ?";
            Mautkategorie m = null;
            try {
                ps = getConnection().prepareStatement(query);
                ps.setString(1, achszahl);
                ps.setInt(2, sskl_id);
                rs = ps.executeQuery();

                if (rs.next()) {
                    m = new Mautkategorie();
                    m.setKategorie_id(rs.getInt("KATEGORIE_ID"));
                    m.setSskl_id(rs.getInt("SSKL_ID"));
                    m.setKat_bezeichnung(rs.getString("KAT_BEZEICHNUNG"));
                    m.setAchszahl(rs.getString("ACHSZAHL"));
                    m.setMautsatz_je_km(rs.getFloat("MAUTSATZ_JE_KM"));
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
            return m;
        }
}
