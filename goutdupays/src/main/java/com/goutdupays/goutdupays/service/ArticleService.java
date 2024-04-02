package com.goutdupays.goutdupays.service;

import com.goutdupays.goutdupays.modele.Article;

import java.util.List;
public interface ArticleService {

    Article create(Article article);

    List<Article> read();

    Article readById(Long id);

    Article update(Long id, Article article);

    String delete(Long id);
}

