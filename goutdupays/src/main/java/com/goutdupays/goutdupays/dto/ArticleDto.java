package com.goutdupays.goutdupays.dto;

import com.goutdupays.goutdupays.modele.Article;

public class ArticleDto {
    private Long id;
    private String name;
    private String description;

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    private Double price;
    private CategorieDto categorie;
    private String username; // Nouveau champ pour le nom d'utilisateur

    public ArticleDto() {}

    public ArticleDto(Article article) {
        this.id = article.getId();
        this.name = article.getName();
        this.description = article.getDescription();
        this.price = article.getPrice();
        if (article.getCategorie() != null) {
            this.categorie = new CategorieDto(article.getCategorie());
        }
        if (article.getUtilisateur() != null) {
            this.username = article.getUtilisateur().getUsername(); // Assigner le nom d'utilisateur
        }
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CategorieDto getCategorie() {
        return categorie;
    }

    public void setCategorie(CategorieDto categorie) {
        this.categorie = categorie;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
