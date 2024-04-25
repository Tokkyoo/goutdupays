package com.tpjavaspring.tpjavaspring.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
public class Candidat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "name cannot be empty")
    @NotNull(message = " name cannot not be null")
    private String nom;
    @NotBlank(message = "first name cannot be empty")
    @NotNull(message = " first name cannot not be null")
    private String prenom;


    @NotNull(message = "birthday cannot be null")
    @Temporal(value = TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat()
    private LocalDate dateNaissance;
    @NotBlank(message = "address cannot be empty")
    @NotNull(message = "address cannot be null")
    private String adresse;
    @NotBlank(message = "city cannot be empty")
    @NotNull(message = "city cannot be null")
    private String ville;
    @NotBlank(message = "postal code cannot be empty")
    @NotNull(message = " postal cannot be null")
    private String codePostal;

    public Candidat(String nom, String prenom, LocalDate dateNaissance, String adresse, String ville, String codePostal) {

        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.adresse = adresse;
        this.ville = ville;
        this.codePostal = codePostal;
    }

    public Candidat() {

    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }
}

