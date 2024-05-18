package com.goutdupays.goutdupays.modele;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class BasketArticle {

    @EmbeddedId
    private BasketArticleKey id;

    @ManyToOne
    @MapsId("basketId")
    @JoinColumn(name = "basket_id")
    @JsonBackReference
    private Basket basket;

    @ManyToOne
    @MapsId("articleId")
    @JoinColumn(name = "article_id")
    @JsonBackReference
    private Article article;

    private int quantity;


    public BasketArticle() {}

    public BasketArticle(Basket basket, Article article, int quantity) {
        this.basket = basket;
        this.article = article;
        this.quantity = quantity;
        this.id = new BasketArticleKey(basket.getId(), article.getId());
    }




}

