package com.spirittesting.speisekarte.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Speisekarte.
 */
@Document(collection = "speisekarte")
public class Speisekarte implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("name")
    private String name;

    @Field("kunde")
    private String kunde;

    @DBRef
    @Field("speise")
    @JsonIgnoreProperties("")
    private Speise speise;

    @DBRef
    @Field("kundeid")
    private Set<Kunde> kundeids = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Speisekarte name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKunde() {
        return kunde;
    }

    public Speisekarte kunde(String kunde) {
        this.kunde = kunde;
        return this;
    }

    public void setKunde(String kunde) {
        this.kunde = kunde;
    }

    public Speise getSpeise() {
        return speise;
    }

    public Speisekarte speise(Speise speise) {
        this.speise = speise;
        return this;
    }

    public void setSpeise(Speise speise) {
        this.speise = speise;
    }

    public Set<Kunde> getKundeids() {
        return kundeids;
    }

    public Speisekarte kundeids(Set<Kunde> kundes) {
        this.kundeids = kundes;
        return this;
    }

    public Speisekarte addKundeid(Kunde kunde) {
        this.kundeids.add(kunde);
        kunde.setSpeisekarte(this);
        return this;
    }

    public Speisekarte removeKundeid(Kunde kunde) {
        this.kundeids.remove(kunde);
        kunde.setSpeisekarte(null);
        return this;
    }

    public void setKundeids(Set<Kunde> kundes) {
        this.kundeids = kundes;
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
        Speisekarte speisekarte = (Speisekarte) o;
        if (speisekarte.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), speisekarte.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Speisekarte{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", kunde='" + getKunde() + "'" +
            "}";
    }
}
