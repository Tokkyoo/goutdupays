package com.goutdupays.goutdupays.controller;

import com.goutdupays.goutdupays.dto.CategorieDto;
import com.goutdupays.goutdupays.modele.Categorie;
import com.goutdupays.goutdupays.repository.CategorieRepository;
import com.goutdupays.goutdupays.service.CategorieService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/categorie")
public class CategorieController {

    @Autowired
    private CategorieService categorieService;

    @Autowired
    private CategorieRepository categorieRepository;

    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public CategorieDto create(@RequestBody CategorieDto categorieDto) {
        Categorie categorie = new Categorie(categorieDto.getNom());
        categorie = categorieRepository.save(categorie);
        return new CategorieDto(categorie);
    }

    @GetMapping("/read")
    @PreAuthorize("isAuthenticated()")
    public List<CategorieDto> read() {
        List<Categorie> categories = categorieService.readAll();
        return categories.stream()
                .map(CategorieDto::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/read/{id}")
    @PreAuthorize("isAuthenticated()")
    public CategorieDto readById(@PathVariable Long id) {
        Categorie categorie = categorieService.readById(id);
        return new CategorieDto(categorie);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("isAuthenticated()")
    public CategorieDto update(@PathVariable Long id, @RequestBody CategorieDto categorieDto) {
        Categorie categorie = categorieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found for this id :: " + id));
        categorie.setNom(categorieDto.getNom());
        categorie = categorieRepository.save(categorie);
        return new CategorieDto(categorie);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public String delete(@PathVariable Long id) {
        Categorie categorie = categorieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found for this id :: " + id));
        categorieRepository.deleteById(id);
        return "Category deleted successfully";
    }
}
