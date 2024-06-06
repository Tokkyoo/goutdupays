package com.goutdupays.goutdupays.service;

import com.goutdupays.goutdupays.modele.Article;
import com.goutdupays.goutdupays.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;

    @Autowired
    public ArticleServiceImpl(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Override
    public Article create(Article article) {
        return articleRepository.save(article);
    }

    @Override
    public List<Article> read() {
        return articleRepository.findAllWithImages();
    }

    @Override
    public Article readById(Long id) {
        return articleRepository.findByIdWithImages(id)
                .orElseThrow(() -> new RuntimeException("Article not found"));
    }

    @Override
    public Article update(Long id, Article updatedArticle) {
        return articleRepository.findById(id)
                .map(article -> {
                    article.setName(updatedArticle.getName());
                    article.setDescription(updatedArticle.getDescription());
                    article.setCategorie(updatedArticle.getCategorie());
                    article.setImages(updatedArticle.getImages());
                    return articleRepository.save(article);
                }).orElseThrow(() -> new RuntimeException("Article not found for this id :: " + id));
    }

    @Override
    public String delete(Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Article not found for this id :: " + id));
        articleRepository.delete(article);
        return "Article deleted successfully";
    }
}
