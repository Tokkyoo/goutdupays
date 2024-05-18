package com.goutdupays.goutdupays.repository;

import com.goutdupays.goutdupays.modele.Article;
import com.goutdupays.goutdupays.modele.Basket;
import com.goutdupays.goutdupays.modele.BasketArticle;
import com.goutdupays.goutdupays.modele.BasketArticleKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BasketArticleRepository extends JpaRepository<BasketArticle, BasketArticleKey> {
    BasketArticle findByBasketAndArticle(Basket basket, Article article);
}