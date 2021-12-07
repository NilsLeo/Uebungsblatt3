package de.htwberlin.dao;

import de.htwberlin.object.Buchung;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * beschreibt die Schnittstelle zu einer Buchung
 * @author Patrick Dohmeier
 **/

public interface BuchungDao {

	/**
	 * aktualisiert eine Buchung
	 * @param buchung das Objekt Buchung, welches gespeichert werden soll
	 **/
	public void updateBuchung(Buchung buchung);

	/**
	 * findet eine Buchung
	 * @param Buchungid die Buchungid
	 * @return das Objekt Buchung, welches gesucht wird
	 */
	public Buchung findBuchung(int Buchungid);
	/**
	 * findet eine Buchung
	 * @param kennzeichen das kennzeichen
	 * @return das Objekt Buchung, welches gesucht wird
	 */
	public Buchung findBuchung(String kennzeichen);
	/**
	 * findet eine Buchung
	 * @param kennzeichen das kennzeichen
	 * @param abschnitts_id die id des Mautabschnitts
	 * @param b_id die id des Buchungsstatus
	 * @return das Objekt Buchung, welches gesucht wird
	 */
	public Buchung findBuchung(String kennzeichen, int abschnitts_id, int b_id);

	/**
	 * erzeugt eine neue Buchung
	 * @param Buchungsnummer die Buchungsnummer
	 * @return gibt eine neu Buchung zurück
	 */
	public Buchung createBuchung(int Buchungsnummer);
}
