package com.goutdupays.goutdupays.controller;

import com.goutdupays.goutdupays.modele.Article;
import com.goutdupays.goutdupays.service.ArticleService;

import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/article")
@AllArgsConstructor
public class ArticleController {
    private final ArticleService articleService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('USER')")
    public Article create(@RequestBody Article article)
    {
        return articleService.create(article);

    }

    @GetMapping("/read")
    @PreAuthorize("isAuthenticated()")
    public List<Article> read()
    {
        return articleService.read();
    }

    @GetMapping("/read/{id}")
    @PreAuthorize("hasRole('USER')")
    public Article readById(@PathVariable Long id)
    {

        return articleService.readById(id);
    }
    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('USER')")
    public Article update(@PathVariable Long id,@RequestBody Article article)
    {
        return articleService.update(id, article);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('USER')")
    public String delete(@PathVariable Long id) {
        return articleService.delete(id);
    }
}
