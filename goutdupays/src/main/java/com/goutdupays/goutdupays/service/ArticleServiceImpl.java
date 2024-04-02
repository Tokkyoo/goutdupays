package com.goutdupays.goutdupays.service;


import com.goutdupays.goutdupays.modele.Article;
import com.goutdupays.goutdupays.repository.ArticleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ArticleServiceImpl implements ArticleService{

    private final ArticleRepository articleRepository;
    @Override
    public Article create(Article article) {
        return articleRepository.save(article);
    }

    @Override
    public List<Article> read() {
        return articleRepository.findAll();
    }

    @Override
    public Article update(Long id, Article article) {
        return articleRepository.findById(id)
                .map(p-> {
                    p.setPrice(article.getPrice());
                    p.setName(article.getName());
                    p.setDescription(article.getDescription());
                    p.setQuantity(article.getQuantity());
                    return articleRepository.save(p);
                }).orElseThrow(() -> new RuntimeException("Article non trouvé"));
    }

    @Override
    public Article readById(Long id) {
        return articleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Article non trouvé"));
    }

    @Override
    public String delete(Long id) {
        articleRepository.deleteById(id);
        return ("Article supprimé");
    }
}
