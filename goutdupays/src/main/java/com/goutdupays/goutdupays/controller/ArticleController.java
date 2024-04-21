package com.goutdupays.goutdupays.controller;

import com.goutdupays.goutdupays.dto.ArticleDto;
import com.goutdupays.goutdupays.modele.Article;
import com.goutdupays.goutdupays.modele.User;
import com.goutdupays.goutdupays.repository.ArticleRepository;
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
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('USER', 'MODERATOR')")
    public Article create(@RequestBody Article article) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Current user: " + authentication.getName());
        System.out.println("Authorities: " + authentication.getAuthorities());

        User user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + authentication.getName()));

        article.setUtilisateur(user);
        return articleRepository.save(article);
    }


    @GetMapping("/read")
    @PreAuthorize("isAuthenticated()")
    public List<ArticleDto> read() {
        List<Article> articles = articleService.read();
        return articles.stream()
                .map(ArticleDto::new)
                .collect(Collectors.toList());
    }

    // Retourner le DTO au lieu de l'entité
    @GetMapping("/read/{id}")
    @PreAuthorize("hasAnyRole('USER', 'MODERATOR')")
    public ArticleDto readById(@PathVariable Long id) {
        Article article = articleService.readById(id);
        return new ArticleDto(article);
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
        // Mettre à jour d'autres champs selon les besoins

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
