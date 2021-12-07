package de.htwberlin.object;

import java.sql.Date;

/**
 * Die Klasse beinhaltet die Attribute einer Buchung
 * @author Gruppe 07
 **/

public class Buchung {

	public int buchung_id;

	public int b_id;

	public int abschnitts_id;

	public int kategorie_id;

	public String kennzeichen;

	public Date buchungsdatum;

	public Date befahrungsdatum;

	public float kosten;

	/**
	 * greift auf die Buchung_id zu
	 * @return buchung_id die Buchung_id
	 */
	public int getBuchung_id() {
		return buchung_id;
	}

	/**
	 * bestimmt die Buchung_id
	 * @param buchung_id die Buchung_id
	 */
	public void setBuchung_id(int buchung_id) {
		this.buchung_id = buchung_id;
	}
	/**
	 * greift auf die id des Buchungstatus zu
	 * @return b_id die id des Buchungsstatus
	 */
	public int getB_id() {
		return b_id;
	}

	/**
	 * bestimmt die id des Buchungsstatus
	 * @param b_id b_id die id des Buchungsstatus
	 */
	public void setB_id(int b_id) {
		this.b_id = b_id;
	}
	/**
	 * greift auf die id des Mautabschnitts zu
	 * @return abschnitts_id die id des Mautabschnitts
	 */
	public int getAbschnitts_id() {
		return abschnitts_id;
	}

	/**
	 * bestimmt die id des Mautabschnitts
	 * @param abschnitts_id abschnitts_id die id des Mautabschnitts
	 */
	public void setAbschnitts_id(int abschnitts_id) {
		this.abschnitts_id = abschnitts_id;
	}
	/**
	 * greift auf die id der Mautkategorie zu
	 * @return kategorie_id die id der Mautkategorie
	 */
	public int getKategorie_id() {
		return kategorie_id;
	}

	/**
	 * bestimmt die id der Mautkategorie
	 * @param kategorie_id
	 */
	public void setKategorie_id(int kategorie_id) {
		this.kategorie_id = kategorie_id;
	}
	/**
	 * greift auf das Kennzeichen zu
	 * @return kennzeichen das Kennzeichen
	 */
	public String getKennzeichen() {
		return kennzeichen;
	}

	/**
	 * bestimmt das Kennzeichen
	 * @param kennzeichen das Kennzeichen
	 */
	public void setKennzeichen(String kennzeichen) {
		this.kennzeichen = kennzeichen;
	}
	/**
	 * greift auf das Buchungsdatum zu
	 * @return buchungsdatum das Buchungsdatum
	 */
	public Date getBuchungsdatum() {
		return buchungsdatum;
	}

	/**
	 * bestimmt das Buchungsdatum
	 * @param buchungsdatum das Buchungsdatum
	 */
	public void setBuchungsdatum(Date buchungsdatum) {
		this.buchungsdatum = buchungsdatum;
	}
	/**
	 * greift auf das Befahrungsdatum zu
	 * @return befahrungsdatum das Befahrungsdatum
	 */
	public Date getBefahrungsdatum() {
		return befahrungsdatum;
	}

	/**
	 * bestimmt das Befahrungsdatum
	 * @param befahrungsdatum das Befahrungsdatum
	 */
	public void setBefahrungsdatum(Date befahrungsdatum) {
		this.befahrungsdatum = befahrungsdatum;
	}
	/**
	 * greift auf die Kosten der Buchung zu
	 * @return kosten die Kosten der Buchung
	 */
	public float getKosten() {
		return kosten;
	}

	/**
	 * bestimmt die Kosten der buchung
	 * @param kosten die Kosten der Buchung
	 */
	public void setKosten(float kosten) {
		this.kosten = kosten;
	}





}
