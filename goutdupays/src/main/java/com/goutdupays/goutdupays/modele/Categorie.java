package com.goutdupays.goutdupays.modele;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "Categorie")
@Getter
@Setter
@NoArgsConstructor
public class Categorie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String nom;

    // Relation OneToMany avec Article
    @OneToMany(mappedBy = "categorie", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Article> articles;

    public Categorie(String nom) {
        this.nom = nom;
    }
}
