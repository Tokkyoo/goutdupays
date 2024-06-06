package com.goutdupays.goutdupays.dto;

import com.goutdupays.goutdupays.modele.Article;
import java.util.List;
import java.util.stream.Collectors;

public class ArticleDto {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private CategorieDto categorie;
    private String username;
    private List<ImageArticleDto> images;

    public ArticleDto() {
    }

    public ArticleDto(Article article) {
        this.id = article.getId();
        this.name = article.getName();
        this.description = article.getDescription();
        this.price = article.getPrice();
        if (article.getCategorie() != null) {
            this.categorie = new CategorieDto(article.getCategorie());
        }
        if (article.getUtilisateur() != null) {
            this.username = article.getUtilisateur().getUsername();
        }
        if (article.getImages() != null) {
            this.images = article.getImages().stream()
                    .map(ImageArticleDto::new)
                    .collect(Collectors.toList());
        }
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return this.price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public CategorieDto getCategorie() {
        return this.categorie;
    }

    public void setCategorie(CategorieDto categorie) {
        this.categorie = categorie;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<ImageArticleDto> getImages() {
        return this.images;
    }

    public void setImages(List<ImageArticleDto> images) {
        this.images = images;
    }
}
