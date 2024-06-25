package com.goutdupays.goutdupays.repository;

import com.goutdupays.goutdupays.modele.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    @Query("SELECT a FROM Article a LEFT JOIN FETCH a.images WHERE a.id = :id")
    Optional<Article> findByIdWithImages(@Param("id") Long id);

    @Query("SELECT DISTINCT a FROM Article a LEFT JOIN FETCH a.images")
    List<Article> findAllWithImages();

    List<Article> findByNameContaining(String name);

    List<Article> findByUtilisateurId(Long utilisateurId);
}
