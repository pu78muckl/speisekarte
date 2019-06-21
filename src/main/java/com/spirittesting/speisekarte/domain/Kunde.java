package com.spirittesting.speisekarte.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * A Kunde.
 */
@Document(collection = "kunde")
public class Kunde implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9]*$")
    @Field("name")
    private String name;

    @NotNull
    @Field("vorname")
    private String vorname;

    @Field("speisekarte")
    private List<Speisekarte> speisenkarten = new LinkedList<>();

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

    public Kunde name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVorname() {
        return vorname;
    }

    public Kunde vorname(String vorname) {
        this.vorname = vorname;
        return this;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
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
        Kunde kunde = (Kunde) o;
        if (kunde.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), kunde.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Kunde{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", vorname='" + getVorname() + "'" +
            "}";
    }

    public void setSpeisekarte(Speisekarte speisekarte) {
        this.speisenkarten.add(speisekarte);
    }
}
