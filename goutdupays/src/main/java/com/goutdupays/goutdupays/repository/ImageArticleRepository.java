package com.goutdupays.goutdupays.repository;

import com.goutdupays.goutdupays.modele.ImageArticle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageArticleRepository extends JpaRepository<ImageArticle, Long> {
    // Additional query methods can be defined here
}