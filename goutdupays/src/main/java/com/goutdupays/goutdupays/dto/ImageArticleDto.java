package com.goutdupays.goutdupays.dto;

import com.goutdupays.goutdupays.modele.ImageArticle;

public class ImageArticleDto {
    private Long id;
    private String url;

    public ImageArticleDto() {}

    public ImageArticleDto(ImageArticle image) {
        this.id = image.getId();
        this.url = image.getCheminImage();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCheminImage() { return url; }
    public void setUrl(String url) { this.url = url; }
}
