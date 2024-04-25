package com.goutdupays.goutdupays.service;

import com.goutdupays.goutdupays.modele.Categorie;
import com.goutdupays.goutdupays.repository.CategorieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategorieService {

    @Autowired
    private CategorieRepository categorieRepository;

    public List<Categorie> readAll() {
        return categorieRepository.findAll();
    }

    public Categorie readById(Long id) {
        Optional<Categorie> categorie = categorieRepository.findById(id);
        if (categorie.isPresent()) {
            return categorie.get();
        } else {
            throw new RuntimeException("Category not found for id :: " + id);
        }
    }

    public Categorie save(Categorie categorie) {
        return categorieRepository.save(categorie);
    }

    public void delete(Long id) {
        categorieRepository.deleteById(id);
    }
}
