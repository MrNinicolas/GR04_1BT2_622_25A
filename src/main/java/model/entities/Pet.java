package model.entities;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;

@Entity
public class Pet implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPet")
    private int idPet;

    @Column(name = "name")
    private String name;

    @Column(name = "species")
    private String species;

    @Column(name = "breed")
    private String breed;

    @Column(name = "color")
    private String color;

    @Column(name = "ownerName")
    private String ownerName;

    public Pet() {
    }

    public Pet(int idPet, String name, String species, String breed, String color, String ownerName) {
        this.idPet = idPet;
        this.name = name;
        this.species = species;
        this.breed = breed;
        this.color = color;
        this.ownerName = ownerName;
    }

    public int getIdPet() {
        return idPet;
    }

    public void setIdPet(int idPet) {
        this.idPet = idPet;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    @Override
    public String toString() {
        return "Pet {\n" +
                "  idPet      : " + idPet + ",\n" +
                "  name       : '" + name + "',\n" +
                "  species    : '" + species + "',\n" +
                "  breed      : '" + breed + "',\n" +
                "  color      : '" + color + "',\n" +
                "  ownerName  : '" + ownerName + "',\n" +
                "}";
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}