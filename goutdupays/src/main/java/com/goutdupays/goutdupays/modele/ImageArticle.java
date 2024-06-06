package com.goutdupays.goutdupays.modele;

import jakarta.persistence.*;

@Entity
@Table(name = "imagesArticle")
public class ImageArticle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nom")
    private String nom;

    @Column(name = "chemin_image")
    private String cheminImage;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")  // This column will store the foreign key to the articles table
    private Article article;

    // Constructors, getters, and setters
    public ImageArticle() {
    }

    public ImageArticle(String nom, String cheminImage, String description) {
        this.nom = nom;
        this.cheminImage = cheminImage;
        this.description = description;
    }

    // Standard getters and setters
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

    public String getCheminImage() {
        return cheminImage;
    }

    public void setCheminImage(String cheminImage) {
        this.cheminImage = cheminImage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }
}
