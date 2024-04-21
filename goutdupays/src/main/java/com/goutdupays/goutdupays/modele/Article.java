package com.goutdupays.goutdupays.modele;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Article")
@Getter
@Setter
@NoArgsConstructor
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String name;

    @Column(length = 150)
    private String description;

    private Double price;
    private Integer quantity;

    // Relation avec Utilisateur
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "utilisateur_id")
    private User utilisateur;

    // Relation avec Catégorie
    //@ManyToOne(fetch = FetchType.LAZY)
    //@JoinColumn(name = "categorie_id")
    //private Categorie categorie;

    // Relation un-à-un avec Image
    //@OneToOne(fetch = FetchType.LAZY)
    //@JoinColumn(name = "image_id")
    //private Image image;
}
