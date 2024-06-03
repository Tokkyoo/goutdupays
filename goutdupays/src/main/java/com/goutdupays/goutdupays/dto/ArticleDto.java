package com.goutdupays.goutdupays.dto;

import com.goutdupays.goutdupays.modele.Article;

import java.util.List;
import java.util.stream.Collectors;

public class ArticleDto {
    private Long id;
    private String name;
    private String description;
    private CategorieDto categorie;
    private List<ImageArticleDto> images;

    public ArticleDto() {}

    public ArticleDto(Article article) {
        this.id = article.getId();
        this.name = article.getName();
        this.description = article.getDescription();
        if (article.getCategorie() != null) {
            this.categorie = new CategorieDto(article.getCategorie());
        }
        if (article.getImages() != null) {
            this.images = article.getImages().stream()
                    .map(ImageArticleDto::new)
                    .collect(Collectors.toList());
        }
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public CategorieDto getCategorie() { return categorie; }
    public void setCategorie(CategorieDto categorie) { this.categorie = categorie; }
    public List<ImageArticleDto> getImages() { return images; }
    public void setImages(List<ImageArticleDto> images) { this.images = images; }
}
