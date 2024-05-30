package com.goutdupays.goutdupays.controller;

import com.goutdupays.goutdupays.dto.ArticleDto;
import com.goutdupays.goutdupays.modele.Article;
import com.goutdupays.goutdupays.modele.Categorie;
import com.goutdupays.goutdupays.modele.User;
import com.goutdupays.goutdupays.repository.ArticleRepository;
import com.goutdupays.goutdupays.repository.CategorieRepository;
import com.goutdupays.goutdupays.repository.UserRepository;
import com.goutdupays.goutdupays.service.ArticleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/article")
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private CategorieRepository categorieRepository;

    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('USER', 'MODERATOR')")
    public ArticleDto create(@RequestBody ArticleDto articleDto) {
        User user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Categorie categorie = categorieRepository.findById(articleDto.getCategorie().getId())
                .orElseThrow(() -> new RuntimeException("Categorie not found"));

        Article article = new Article();
        article.setName(articleDto.getName());
        article.setDescription(articleDto.getDescription());
        article.setPrice(articleDto.getPrice());
        article.setUtilisateur(user);
        article.setCategorie(categorie);

        Article savedArticle = articleRepository.save(article);
        return new ArticleDto(savedArticle);
    }


    @GetMapping("/read")
    @PreAuthorize("isAuthenticated()")
    public List<ArticleDto> read() {
        List<Article> articles = articleService.read();
        return articles.stream()
                .map(ArticleDto::new) // Ici, le constructeur mis à jour est utilisé
                .collect(Collectors.toList());
    }

    @GetMapping("/read/{id}")
    @PreAuthorize("hasAnyRole('USER', 'MODERATOR')")
    public ArticleDto readById(@PathVariable Long id) {
        Article article = articleService.readById(id);
        return new ArticleDto(article); // Ici aussi
    }

    // Mettre à jour l'article et retourner le DTO mis à jour
    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyRole('USER', 'MODERATOR')")
    public ArticleDto update(@PathVariable Long id, @RequestBody ArticleDto articleDtoDetails) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Article not found for this id :: " + id));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!article.getUtilisateur().getUsername().equals(authentication.getName())) {
            throw new RuntimeException("Not authorized to update this article");
        }

        article.setName(articleDtoDetails.getName());
        article.setDescription(articleDtoDetails.getDescription());

        // Update category if it's provided and different
        if (articleDtoDetails.getCategorie() != null || !article.getCategorie().getId().equals(articleDtoDetails.getCategorie().getId())) {
            Categorie newCategorie = categorieRepository.findById(articleDtoDetails.getCategorie().getId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            article.setCategorie(newCategorie);
        }

        Article updatedArticle = articleRepository.save(article);
        return new ArticleDto(updatedArticle);
    }

    // Supprimer l'article et renvoyer une réponse appropriée
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyRole('USER', 'MODERATOR')")
    public String delete(@PathVariable Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Article not found for this id :: " + id));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!article.getUtilisateur().getUsername().equals(authentication.getName())) {
            throw new RuntimeException("Not authorized to delete this article");
        }

        articleRepository.deleteById(id);
        return "Article deleted successfully";
    }
}
