package com.goutdupays.goutdupays.modele;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;
@Getter
@Setter

@Embeddable
public class BasketArticleKey implements Serializable {

    @Column(name = "basket_id")
    private Long basketId;

    @Column(name = "article_id")
    private Long articleId;

    // standard constructors, getters, and setters

    public BasketArticleKey() {}

    public BasketArticleKey(Long basketId, Long articleId) {
        this.basketId = basketId;
        this.articleId = articleId;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BasketArticleKey that = (BasketArticleKey) o;
        return Objects.equals(basketId, that.basketId) && Objects.equals(articleId, that.articleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(basketId, articleId);
    }
}

