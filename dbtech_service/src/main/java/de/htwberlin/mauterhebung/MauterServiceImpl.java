package de.htwberlin.mauterhebung;


import java.sql.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import de.htwberlin.dao.BuchungDao;
import de.htwberlin.dao.BuchungDaoImpl;
import de.htwberlin.dao.MautkategorieDao;
import de.htwberlin.dao.MautkategorieDaoImpl;
import de.htwberlin.object.Buchung;
import de.htwberlin.object.Mautkategorie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import de.htwberlin.exceptions.AlreadyCruisedException;
import de.htwberlin.exceptions.DataException;
import de.htwberlin.exceptions.InvalidVehicleDataException;
import de.htwberlin.exceptions.UnkownVehicleException;

/**
 * Die Klasse realisiert den AusleiheService.
 *
 * @author Patrick Dohmeier, Gruppe 07
 */

public class MauterServiceImpl implements IMauterhebung {

    private static final Logger L = LoggerFactory.getLogger(MauterServiceImpl.class);
    private Connection connection;

    @Override
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    private Connection getConnection() {
        if (connection == null) {
            throw new DataException("Connection not set");
        }
        return connection;
    }

    @Override
    public float berechneMaut(int mautAbschnitt, int achszahl, String kennzeichen)
            throws UnkownVehicleException, InvalidVehicleDataException, AlreadyCruisedException {

        // Prueft, ob das Fahrzeug registriert ist, sprich ob es "aktiv" ist, ein
        // Fahrzeuggeraet verbaut hat oder im manuellen Verfahren eine offene
        // Buchung des Fahrzeugs vorliegt
        if (!isVehicleRegistered(kennzeichen)) {
            throw new UnkownVehicleException("Das Fahrzeug ist nicht bekannt!-> Mautpreller");
        }

        if (isVehicleRegistered(kennzeichen) && automaticProcedure(kennzeichen)) {
            if (!compareAxlesAutomatic(kennzeichen, achszahl)) {
                throw new InvalidVehicleDataException("die Achszahl AUtomatic");
            }
        }
        if (isVehicleRegistered(kennzeichen) && !automaticProcedure(kennzeichen)) {
            if (!compareNoOfAxlesManuel(kennzeichen, achszahl)) {
                throw new InvalidVehicleDataException("die Achszahl Manuel");
            }
        }
        if (isVehicleRegistered(kennzeichen) && !automaticProcedure(kennzeichen)) {
            if (openManualProcedure(kennzeichen, mautAbschnitt)) {
                BuchungDao b_dao = new BuchungDaoImpl(getConnection());
                Buchung b = b_dao.findBuchung(kennzeichen, mautAbschnitt, 1);
                b.setB_id(3);
                java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
                b.setBefahrungsdatum(date);
                b_dao.updateBuchung(b);
            } else {
                throw new AlreadyCruisedException("Es liegt eine Doppelbefahrung vor");
            }
        }
        float mautsatzJeKm = berechneMautsatzJeKm(kennzeichen);
        float mautAbschnittslänge = berechneMautAbschnittslänge(mautAbschnitt);
        float input = mautsatzJeKm * mautAbschnittslänge;
        float berechneteMaut = (float) (Math.round(input * 100.0) / 100.0);
        //System.out.println(berechneteMaut);
        return berechneteMaut;
    }

    /**
     * berechnet die mautabschnittslänge
     * @author Gruppe 07
     * @param mautAbschnitt der Mautabschnitt
     * @return mautAbschnittslänge die mautabschnittslänge in km
     **/
    private float berechneMautAbschnittslänge(int mautAbschnitt) {
        String strmautAbschnittslänge;
        float mautAbschnittslänge = 0;
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        try {
            String queryString = "SELECT LAENGE FROM MAUTABSCHNITT WHERE ABSCHNITTS_ID = ?";
            preparedStatement = getConnection().prepareStatement(queryString);
            preparedStatement.setInt(1, mautAbschnitt);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                DecimalFormat df = new DecimalFormat("#.#");

                strmautAbschnittslänge = df.format(resultSet.getFloat("LAENGE") / 1000).replaceAll(",", ".");
                mautAbschnittslänge = Float.parseFloat(strmautAbschnittslänge);
            }
            // System.out.println("mautAbschnittslänge: " + mautAbschnittslänge);
            return mautAbschnittslänge;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * berechnet den Mautsatz je km
     * @author Gruppe 07
     * @param kennzeichen der Mautabschnitt
     * @return mautsatzJeKm der Mautsatz je Km in Euro
     **/

    private float berechneMautsatzJeKm(String kennzeichen) {
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        PreparedStatement pst;
        ResultSet rs;
        int achszahl = 0;
        int sskl_id = 0;
        float input;
        float mautsatzJeKm = 0;
        String strMautsatzJeKm;
        try {
            String queryString = "SELECT ACHSEN, SSKL_ID FROM FAHRZEUG WHERE KENNZEICHEN = ?";
            preparedStatement = getConnection().prepareStatement(queryString);
            preparedStatement.setString(1, kennzeichen);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                achszahl = resultSet.getInt("ACHSEN");
                sskl_id = resultSet.getInt("SSKL_ID");
            MautkategorieDao m_dao = new MautkategorieDaoImpl(getConnection());
            Mautkategorie m = m_dao.findMautkategorie(sskl_id, ">= " + String.valueOf(achszahl));
            input = m.getMautsatz_je_km() / 100;
                DecimalFormat df = new DecimalFormat("#.###");
            strMautsatzJeKm = df.format(input).replaceAll(",", ".");
            mautsatzJeKm = Float.parseFloat(strMautsatzJeKm);
        }
        System.out.println("mautsatzJeKm: " + mautsatzJeKm);
        return mautsatzJeKm;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    /**
     * Überprüft, ob es sich um ein manuelles Verfahren mit offenem Buchungsstatus handelt
     * @autor Gruppe 07
     * @param kennzeichen   das Kennzeichen des Fahrzeugs
     * @param mautAbschnitt der mautAbschnitt
     * @return true, wenn das Verfahren offen ist || false, wenn nicht
     **/
    private boolean openManualProcedure(String kennzeichen, int mautAbschnitt) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            String queryString = "SELECT bs.STATUS FROM BUCHUNGSTATUS bs,BUCHUNG b WHERE b.B_ID=bs.B_ID AND b.KENNZEICHEN=? AND b.ABSCHNITTS_ID=? AND bs.STATUS LIKE 'offen'";
            preparedStatement = getConnection().prepareStatement(queryString);
            preparedStatement.setString(1, kennzeichen);
            preparedStatement.setInt(2, mautAbschnitt);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private int findbuchungsid(String kennzeichen, int mautAbschnitt) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            String queryString = "SELECT b.BUCHUNG_ID FROM BUCHUNGSTATUS bs,BUCHUNG b WHERE b.B_ID=bs.B_ID AND b.KENNZEICHEN=? AND b.ABSCHNITTS_ID=? AND bs.STATUS LIKE 'offen'";
            preparedStatement = getConnection().prepareStatement(queryString);
            preparedStatement.setString(1, kennzeichen);
            preparedStatement.setInt(2, mautAbschnitt);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                System.out.println(resultSet.getInt(1));
                return resultSet.getInt(1);
            } else return 1;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Überprüft, ob es sich um ein automatisches Verfahren handelt
     * @author Gruppe 07
     * @param kennzeichen das Kennzeichen des Fahrzeugs
     * @return true, wenn es sich um ein automatisches Verfahren handelt || false, wenn nicht
     **/
    private boolean automaticProcedure(String kennzeichen) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            String queryString = "SELECT fg.FZG_ID AS ANZAHL FROM FAHRZEUG f,FAHRZEUGGERAT fg WHERE f.FZ_ID = fg.FZ_ID AND " +
                    "KENNZEICHEN = ? AND fg.AUSBAUDATUM IS NULL";
            preparedStatement = getConnection().prepareStatement(queryString);
            preparedStatement.setString(1, kennzeichen);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * prueft, ob das Fahrzeug bereits registriert und aktiv ist oder eine
     * manuelle offene Buchung fuer das Fahrzeug vorliegt
     * @author Gruppe 07
     * @param kennzeichen , das Kennzeichen des Fahrzeugs
     * @return true wenn das Fahrzeug registiert ist || false wenn nicht
     **/
    public boolean isVehicleRegistered(String kennzeichen) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT SUM( ANZAHL ) AS ANZAHL FROM (SELECT COUNT(F.KENNZEICHEN) AS ANZAHL FROM FAHRZEUG F"
                    + " JOIN FAHRZEUGGERAT FZG ON F.FZ_ID = FZG.FZ_ID AND F.ABMELDEDATUM IS NULL AND FZG.STATUS = 'active' "
                    + " AND  F.KENNZEICHEN =  ?  UNION ALL SELECT COUNT(KENNZEICHEN) AS ANZAHL FROM BUCHUNG WHERE"
                    + " KENNZEICHEN = ?  AND B_ID = 1)";
            preparedStatement = getConnection().prepareStatement(queryString);
            preparedStatement.setString(1, kennzeichen);
            preparedStatement.setString(2, kennzeichen);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getLong("ANZAHL") > 0;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    /**
     * Überprüft beim manuellen Verfahren, ob das Fahrzeug mit der korrekten Achszahl unterwegs ist
     * @author Gruppe 07
     * @param kennzeichen das Kennzeichen des Fahrzeugs
     * @param achszahl    die achszahl
     * @return true, wenn das Fahrzeug mit der korrekten Achszahl unterwegs ist || false, wenn nicht
     **/
    private boolean compareNoOfAxlesManuel(String kennzeichen, int achszahl) {
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        try {
            String queryString = "SELECT ACHSZAHL FROM BUCHUNG " +
                    "INNER JOIN MAUTKATEGORIE ON BUCHUNG.KATEGORIE_ID = MAUTKATEGORIE.KATEGORIE_ID WHERE KENNZEICHEN = ? AND B_ID =1";
            preparedStatement = getConnection().prepareStatement(queryString);
            preparedStatement.setString(1, kennzeichen);
            resultSet = preparedStatement.executeQuery();
            List<String> achszahlList = new ArrayList<String>();
            if (resultSet.next()) {
                String zahl = resultSet.getString("ACHSZAHL");
                if (zahl.contains(">=")) if (achszahl >= Integer.parseInt(zahl.replaceAll("[^0-9]", "")))
                    return true;
                else return false;
                if (zahl.contains("=")) if (achszahl == Integer.parseInt(zahl.replaceAll("[^0-9]", "")))
                    return true;
                else return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Überprüft beim automatischen Verfahren, ob das Fahrzeug mit der korrekten Achszahl unterwegs ist
     * @author Gruppe 07
     * @param kennzeichen das Kennzeichen des Fahrzeugs
     * @param achszahl
     * @return true, wenn das Fahrzeug mit der korrekten Achszahl unterwegs ist|| false wenn nicht
     **/
    private boolean compareAxlesAutomatic(String kennzeichen, int achszahl) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            String queryString = "SELECT ACHSEN FROM FAHRZEUG WHERE KENNZEICHEN = ?";
            preparedStatement = getConnection().prepareStatement(queryString);
            preparedStatement.setString(1, kennzeichen);
            resultSet = preparedStatement.executeQuery();
            return resultSet.next() && resultSet.getInt(1) == achszahl;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
