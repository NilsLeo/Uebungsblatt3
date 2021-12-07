package de.htwberlin.object;
/**
 * Die Klasse beinhaltet die Attribute einer Mautkategorie
 * @author Gruppe 07
 **/
public class Mautkategorie {

    int kategorie_id;
    int sskl_id;
    String kat_bezeichnung;
    String achszahl;
    float mautsatz_je_km;

    /**
     * greift auf die id der Mautkategorie zu
     * @return kategorie_id die id der Mautkategorie
     */
    public int getKategorie_id() {
        return kategorie_id;
    }

    /**
     * bestimmt die id der Mautkategorie
     * @param kategorie_id die id der Mautkategorie
     */
    public void setKategorie_id(int kategorie_id) {
        this.kategorie_id = kategorie_id;
    }

    /**
     * greift auf die id der Schadstoffklasse zu
     * @return sskl_id die id der Schadstoffklasse
     */
    public int getSskl_id() {
        return sskl_id;
    }

    /**
     * bestimmt die id der Schadstoffklasse
     * @param sskl_id die id der Schadstoffklasse
     */
    public void setSskl_id(int sskl_id) {
        this.sskl_id = sskl_id;
    }

    /**
     * greift auf die Bezeichnung der Mautkategorie zu
     * @return die Bezeichnung der Mautkategorie
     */
    public String getKat_bezeichnung() {
        return kat_bezeichnung;
    }

    /**
     * bestimmt die Bezeichnung der Mautkategorie
     * @param kat_bezeichnung die Bezeichnung der Mautkategorie
     */
    public void setKat_bezeichnung(String kat_bezeichnung) {
        this.kat_bezeichnung = kat_bezeichnung;
    }

    /**
     * greift auf die Achszahl zu
     * @return achszahl die Achszahl
     */
    public String getAchszahl() {
        return achszahl;
    }

    /**
     * bestimmt die Achszahl
     * @param achszahl die Achszahl
     */
    public void setAchszahl(String achszahl) {
        this.achszahl = achszahl;
    }

    /**
     * greift auf den Mautsatz je KM in cent zu
     * @return mautsatz_je_km der Mautsatz je KM in cent
     */
    public float getMautsatz_je_km() {
        return mautsatz_je_km;
    }

    /**
     * bestimmt den Mautsatz je KM in cent
     * @param mautsatz_je_km der Mautsatz je KM in cent
     */
    public void setMautsatz_je_km(float mautsatz_je_km) {
        this.mautsatz_je_km = mautsatz_je_km;
    }
}


