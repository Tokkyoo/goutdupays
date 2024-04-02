package com.goutdupays.goutdupays.repository;

import com.goutdupays.goutdupays.modele.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}
