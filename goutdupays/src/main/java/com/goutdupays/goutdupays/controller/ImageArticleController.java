package com.goutdupays.goutdupays.controller;

import com.goutdupays.goutdupays.dto.ImageArticleDto;
import com.goutdupays.goutdupays.modele.ImageArticle;
import com.goutdupays.goutdupays.service.ImageArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/images")
public class ImageArticleController {

    @Autowired
    private ImageArticleService imageArticleService;

    @PostMapping("/create")
    public ResponseEntity<ImageArticleDto> uploadImageArticle(@RequestParam("file") MultipartFile file,
                                                              @RequestParam("description") String description,
                                                              @RequestParam("articleId") Long articleId) {
        try {
            ImageArticle imageArticle = imageArticleService.createImageArticle(file, description, articleId);
            return ResponseEntity.ok(new ImageArticleDto(imageArticle));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @GetMapping("/read")
    @PreAuthorize("isAuthenticated()")
    public List<ImageArticleDto> getAllImageArticles() {
        return imageArticleService.getAllImageArticles().stream()
                .map(ImageArticleDto::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<ImageArticleDto> getImageArticleById(@PathVariable Long id) {
        return imageArticleService.getImageArticleById(id)
                .map(imageArticle -> ResponseEntity.ok(new ImageArticleDto(imageArticle)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ImageArticleDto> updateImageArticle(@PathVariable Long id,
                                                              @RequestParam("file") MultipartFile file,
                                                              @RequestParam("description") String description) {
        try {
            ImageArticle updatedImageArticle = imageArticleService.updateImageArticle(id, file, description);
            return ResponseEntity.ok(new ImageArticleDto(updatedImageArticle));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteImageArticle(@PathVariable Long id) {
        imageArticleService.deleteImageArticle(id);
        return ResponseEntity.ok("Image deleted successfully");
    }
}
