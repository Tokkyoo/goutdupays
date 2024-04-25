package com.goutdupays.goutdupays.dto;

import com.goutdupays.goutdupays.modele.Article;

public class ArticleDto {
    private Long id;
    private String name;
    private String description;
    private CategorieDto categorie;

    public ArticleDto() {}

    public ArticleDto(Article article) {
        this.id = article.getId();
        this.name = article.getName();
        this.description = article.getDescription();
        if (article.getCategorie() != null) {
            this.categorie = new CategorieDto(article.getCategorie());
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
}
