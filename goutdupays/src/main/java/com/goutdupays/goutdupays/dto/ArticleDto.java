package com.goutdupays.goutdupays.dto;

import com.goutdupays.goutdupays.modele.Article;

public class ArticleDto {
    private Long id;
    private String name;
    private String description;

    // Constructeur sans arguments nécessaire pour la désérialisation Jackson
    public ArticleDto() {}

    // Constructeur pour convertir une entité en DTO
    public ArticleDto(Article article) {
        this.id = article.getId();
        this.name = article.getName();
        this.description = article.getDescription();
    }

    // Getters et Setters
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
}
