package de.htwberlin.dao;

import de.htwberlin.exceptions.DataException;
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
public class MautkategorieDaoImpl implements MautkategorieDao {

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
        PreparedStatement ps = null;
        int i = 0;
        String query = "Update MAUTKATEGORIE set KATEGORIE_ID=?, ACHSZAHL=?,SSKL_ID=?,KAT_BEZEICHNUNG=?,MAUTSATZ_JE_KM=?  ";

        try {
            System.out.println("Kategorie_ID = " + mautkategorie.getKategorie_id());
            System.out.println("Achszahl = " + mautkategorie.getAchszahl());
            System.out.println("Mautsatz = " + mautkategorie.getMautsatz_je_km());
            ps = getConnection().prepareStatement(query);
            ps.setInt(1, mautkategorie.getKategorie_id());
            ps.setString(2, mautkategorie.getAchszahl());
            ps.setInt(3, mautkategorie.getSskl_id());
            ps.setString(4, mautkategorie.getKat_bezeichnung());
            ps.setFloat(4, mautkategorie.getMautsatz_je_km());
            i = ps.executeUpdate();
            System.out.println("ES wurde(n) " + i + " Datensätze aktualisiert");

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public Mautkategorie findMautkategorie(int kategorie_id) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "select * from MAUTKATEGORIE where KATEGORIE_ID = ?";
        Mautkategorie m = null;
        try {
            ps = getConnection().prepareStatement(query);
            ps.setInt(1, kategorie_id);

            rs = ps.executeQuery();

            if (rs.next()) {
                m = new Mautkategorie();
                m.setKategorie_id(kategorie_id);
                m.setAchszahl(rs.getString("ACHSZAHL"));
                m.setKat_bezeichnung(rs.getString("KAT_BEZEICHNUNG"));
                m.setSskl_id(rs.getInt("SSKL_ID"));
                m.setMautsatz_je_km(rs.getFloat("MAUTSATZ_JE_KM"));

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return m;
    }


    @Override
    public Mautkategorie createMautkategorie(int kategorie_id, String achszahl, String kat_bezeichnung, int sskl_id, float mautsatz_je_km) {
        Mautkategorie m = new Mautkategorie();
        m.setKategorie_id(kategorie_id);
        m.setAchszahl(achszahl);
        m.setKat_bezeichnung(kat_bezeichnung);
        m.setSskl_id(sskl_id);
        m.setMautsatz_je_km(mautsatz_je_km);
        return m;
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
