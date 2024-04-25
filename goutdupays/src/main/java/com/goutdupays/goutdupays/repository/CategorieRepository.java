package com.goutdupays.goutdupays.repository;

import com.goutdupays.goutdupays.modele.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategorieRepository extends JpaRepository<Categorie, Long> {
    // Ici, vous pouvez ajouter d'autres méthodes spécifiques aux requêtes, si nécessaire
}
