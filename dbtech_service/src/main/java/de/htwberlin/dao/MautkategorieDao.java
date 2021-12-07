package de.htwberlin.dao;

import de.htwberlin.object.Mautkategorie;
/**
 * beschreibt die Schnittstelle zu einer Mautkategorie
 * @author Gruppe 07
 **/
public interface MautkategorieDao {

        /**
         * aktualisiert eine Mautkategorie
         * @param mautkategorie das Objekt Mautkategorie, welches gespeichert werden soll
         **/
        public void updateMautkategorie(Mautkategorie mautkategorie);

        /**
         * findet eine Mautkategorie
         * @param Mautkategorieid die
         * @return das Objekt Mautkategorie, welches gesucht wird
         */
        public Mautkategorie findMautkategorie(int Mautkategorieid);

        public Mautkategorie findMautkategorie(int sskl_id, String achszahl);

        /**
         * erzeugt eine neue Mautkategorie
         * @param Mautkategorienummer die Mautkategorienummer
         * @return gibt eine neu Mautkategorie zurück
         */
        public Mautkategorie createMautkategorie(int Mautkategorienummer);
    }

