package com.goutdupays.goutdupays.service;

import com.goutdupays.goutdupays.modele.Article;
import com.goutdupays.goutdupays.modele.ImageArticle;
import com.goutdupays.goutdupays.repository.ArticleRepository;
import com.goutdupays.goutdupays.repository.ImageArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ImageArticleService {

    @Autowired
    private ImageArticleRepository imageArticleRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private FileStorageService fileStorageService;

    public ImageArticle createImageArticle(MultipartFile file, String description, Long articleId) throws IOException {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new RuntimeException("Article not found with id: " + articleId));

        String filePath = fileStorageService.storeFile(file);
        ImageArticle imageArticle = new ImageArticle();
        imageArticle.setCheminImage(filePath);
        imageArticle.setDescription(description);
        imageArticle.setArticle(article);
        return imageArticleRepository.save(imageArticle);
    }

    public List<ImageArticle> getAllImageArticles() {
        return imageArticleRepository.findAll();
    }

    public Optional<ImageArticle> getImageArticleById(Long id) {
        return imageArticleRepository.findById(id);
    }

    @Transactional
    public ImageArticle updateImageArticle(Long id, MultipartFile file, String description) {
        return imageArticleRepository.findById(id).map(imageArticle -> {
            try {
                String filePath = fileStorageService.storeFile(file);
                imageArticle.setCheminImage(filePath);
                imageArticle.setDescription(description);
            } catch (Exception e) {
                throw new RuntimeException("Failed to store file", e);
            }
            return imageArticleRepository.save(imageArticle);
        }).orElseThrow(() -> new RuntimeException("Image not found with id " + id));
    }

    public void deleteImageArticle(Long id) {
        imageArticleRepository.deleteById(id);
    }
}
