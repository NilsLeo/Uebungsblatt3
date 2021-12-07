package de.htwberlin.dao;

import de.htwberlin.object.Mautkategorie;

/**
 * beschreibt die Schnittstelle zu einer Mautkategorie
 *
 * @author Gruppe 07
 **/
public interface MautkategorieDao {

    /**
     * aktualisiert eine Mautkategorie
     *
     * @param mautkategorie das Objekt Mautkategorie, welches gespeichert werden soll
     **/
    public void updateMautkategorie(Mautkategorie mautkategorie);

    /**
     * findet eine Mautkategorie
     *
     * @param Mautkategorieid die
     * @return das Objekt Mautkategorie, welches gesucht wird
     */
    public Mautkategorie findMautkategorie(int Mautkategorieid);

    public Mautkategorie findMautkategorie(int sskl_id, String achszahl);

    /**
     * erzeugt eine neue Mautkategorie
     *
     * @param kategorie_id    die Mautkategorienummer
     * @param achszahl        die erlaubte Achszahl der Kategorie
     * @param kat_bezeichnung die Bezeichnung
     * @param sskl_id         die Id welche zur identifikation der sskl genutz wird
     * @param mautsatz_je_km  der Mautsatz der pro Kilometer berechnet wird
     * @return gibt eine neu Mautkategorie zurück
     */
    public Mautkategorie createMautkategorie(int kategorie_id, String achszahl, String kat_bezeichnung, int sskl_id, float mautsatz_je_km);
}

