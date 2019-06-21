package com.spirittesting.speisekarte.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Speise.
 */
@Document(collection = "speise")
public class Speise implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("bezeichnung")
    private String bezeichnung;

    @NotNull
    @Field("preis")
    private Float preis;

    @Field("beschreibung")
    private String beschreibung;

    @Field("kundenid")
    private String kundenid;

    @Field("speisekartenid")
    private String speisekartenid;

    @DBRef
    @Field("kunde")
    @com.fasterxml.jackson.annotation.JsonBackReference
    private Id kunde;

    @DBRef
    @Field("speisekarte")
    @JsonIgnoreProperties("")
    private Speisekarte speisekarte;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBezeichnung() {
        return bezeichnung;
    }

    public Speise bezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
        return this;
    }

    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    public Float getPreis() {
        return preis;
    }

    public Speise preis(Float preis) {
        this.preis = preis;
        return this;
    }

    public void setPreis(Float preis) {
        this.preis = preis;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public Speise beschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
        return this;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public String getKundenid() {
        return kundenid;
    }

    public Speise kundenid(String kundenid) {
        this.kundenid = kundenid;
        return this;
    }

    public void setKundenid(String kundenid) {
        this.kundenid = kundenid;
    }

    public String getSpeisekartenid() {
        return speisekartenid;
    }

    public Speise speisekartenid(String speisekartenid) {
        this.speisekartenid = speisekartenid;
        return this;
    }

    public void setSpeisekartenid(String speisekartenid) {
        this.speisekartenid = speisekartenid;
    }

    public Id getKunde() {
        return kunde;
    }

    public Speise kunde(Id id) {
        this.kunde = id;
        return this;
    }

    public void setKunde(Id id) {
        this.kunde = id;
    }

    public Speisekarte getSpeisekarte() {
        return speisekarte;
    }

    public Speise speisekarte(Speisekarte speisekarte) {
        this.speisekarte = speisekarte;
        return this;
    }

    public void setSpeisekarte(Speisekarte speisekarte) {
        this.speisekarte = speisekarte;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Speise speise = (Speise) o;
        if (speise.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), speise.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Speise{" +
            "id=" + getId() +
            ", bezeichnung='" + getBezeichnung() + "'" +
            ", preis=" + getPreis() +
            ", beschreibung='" + getBeschreibung() + "'" +
            ", kundenid='" + getKundenid() + "'" +
            ", speisekartenid='" + getSpeisekartenid() + "'" +
            "}";
    }
}
