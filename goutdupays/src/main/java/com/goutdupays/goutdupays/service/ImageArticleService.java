package com.goutdupays.goutdupays.service;

import com.goutdupays.goutdupays.modele.Article;
import com.goutdupays.goutdupays.modele.ImageArticle;
import com.goutdupays.goutdupays.repository.ArticleRepository;
import com.goutdupays.goutdupays.repository.ImageArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

        String fileName = fileStorageService.storeFile(file);
        String filePath = "/uploads/" + fileName; // Generate the accessible URL

        ImageArticle imageArticle = new ImageArticle();
        imageArticle.setCheminImage(filePath);
        imageArticle.setDescription(description);
        imageArticle.setArticle(article);
        return imageArticleRepository.save(imageArticle);
    }

    public List<ImageArticle> getAllImageArticles() {
        return imageArticleRepository.findAll().stream()
                .map(this::normalizeImagePath)
                .collect(Collectors.toList());
    }

    public Optional<ImageArticle> getImageArticleById(Long id) {
        return imageArticleRepository.findById(id).map(this::normalizeImagePath);
    }

    @Transactional
    public ImageArticle updateImageArticle(Long id, MultipartFile file, String description) {
        return imageArticleRepository.findById(id).map(imageArticle -> {
            try {
                String fileName = fileStorageService.storeFile(file);
                String filePath = "/uploads/" + fileName; // Generate the accessible URL
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

    private ImageArticle normalizeImagePath(ImageArticle imageArticle) {
        String cheminImage = imageArticle.getCheminImage();
        if (cheminImage != null && (cheminImage.startsWith("C:\\") || cheminImage.startsWith("/"))) {
            String fileName = Paths.get(cheminImage).getFileName().toString();
            imageArticle.setCheminImage("/uploads/" + fileName);
        }
        return imageArticle;
    }
}