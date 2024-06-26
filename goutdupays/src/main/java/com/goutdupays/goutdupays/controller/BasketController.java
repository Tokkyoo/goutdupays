package com.goutdupays.goutdupays.controller;

import com.goutdupays.goutdupays.modele.Article;
import com.goutdupays.goutdupays.modele.Basket;
import com.goutdupays.goutdupays.modele.BasketArticle;
import com.goutdupays.goutdupays.repository.ArticleRepository;
import com.goutdupays.goutdupays.repository.BasketArticleRepository;
import com.goutdupays.goutdupays.repository.BasketRepository;
import com.goutdupays.goutdupays.service.BasketService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")
public class BasketController {

    @Autowired
    BasketRepository basketRepository;

    @Autowired
    BasketService basketService;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private BasketArticleRepository basketArticleRepository;

    @GetMapping("/basket/{userId}/user")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Basket> getBasketByUserId(@PathVariable(value = "userId") Long userId) {
        Basket basket = basketRepository.findByUserId(userId);
        return new ResponseEntity<>(basket, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('USER')")
    public Basket getBasket(@PathVariable Long userId) {
        Basket basket = basketRepository.findByUserId(userId);
        // Ajouter les détails des articles au panier
        Set<BasketArticle> basketArticlesWithDetails = basket.getBasketArticles().stream().map(basketArticle -> {
            Article article = articleRepository.findById(basketArticle.getId().getArticleId()).orElse(null);
            basketArticle.setArticle(article);
            return basketArticle;
        }).collect(Collectors.toSet());
        basket.setBasketArticles(basketArticlesWithDetails);
        return basket;
    }

    @GetMapping("/basket/{basketId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Basket> getBasketById(@PathVariable(value = "basketId") Long basketId) {
        Optional<Basket> basketOptional = basketRepository.findById(basketId);
        if (basketOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Basket basket = basketOptional.get();
        return new ResponseEntity<>(basket, HttpStatus.OK);
    }

    @PostMapping("/basket/{userId}/addArticle")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Basket> addArticleToBasket(@PathVariable Long userId, @RequestBody AddArticleRequest addArticleRequest) {
        Basket basket = basketRepository.findByUserId(userId);


        Article article = articleRepository.findById(addArticleRequest.getArticleId()).orElse(null);
        if (article == null) {
            return ResponseEntity.notFound().build();
        }

        BasketArticle existingBasketArticle = basketArticleRepository.findByBasketAndArticle(basket, article);
        if (existingBasketArticle != null) {
            existingBasketArticle.setQuantity(existingBasketArticle.getQuantity() + addArticleRequest.getQuantity());
        } else {
            BasketArticle newBasketArticle = new BasketArticle();
            newBasketArticle.setBasket(basket);
            newBasketArticle.setArticle(article);
            newBasketArticle.setQuantity(addArticleRequest.getQuantity());
            basket.getBasketArticles().add(newBasketArticle);
        }

        basket.setQuantity(basket.getQuantity() + addArticleRequest.getQuantity());

        Basket updatedBasket = basketRepository.save(basket);
        return ResponseEntity.ok(updatedBasket);
    }


    @DeleteMapping("/basket/{basketId}")
    public ResponseEntity<?> deleteBasket(@PathVariable Long basketId) {
        if (!basketRepository.existsById(basketId)) {
            return ResponseEntity.notFound().build();
        }
        basketRepository.deleteById(basketId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/basket/{userId}/removeArticle/{articleId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Basket> removeArticleFromBasket(@PathVariable Long userId, @PathVariable Long articleId) {
        Basket basket = basketRepository.findByUserId(userId);

        Article article = articleRepository.findById(articleId).orElse(null);
        if (article == null) {
            return ResponseEntity.notFound().build();
        }

        BasketArticle existingBasketArticle = basketArticleRepository.findByBasketAndArticle(basket, article);
        if (existingBasketArticle != null) {
            basket.getBasketArticles().remove(existingBasketArticle);
            basket.setQuantity(basket.getQuantity() - existingBasketArticle.getQuantity());
            basketArticleRepository.delete(existingBasketArticle);

            Basket updatedBasket = basketRepository.save(basket);
            return ResponseEntity.ok(updatedBasket);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    public static class AddArticleRequest {
        private Long articleId;
        private int quantity;

        public Long getArticleId() {
            return articleId;
        }

        public void setArticleId(Long articleId) {
            this.articleId = articleId;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public static class RemoveArticleRequest {
            private Long articleId;
            private int quantity;

            public Long getArticleId() {
                return articleId;
            }

            public void setArticleId(Long articleId) {
                this.articleId = articleId;
            }

            public int getQuantity() {
                return quantity;
            }

            public void setQuantity(int quantity) {
                this.quantity = quantity;
            }
        }
    }
}
