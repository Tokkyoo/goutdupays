package com.goutdupays.goutdupays.service;

import com.goutdupays.goutdupays.modele.Basket;
import com.goutdupays.goutdupays.repository.BasketRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BasketServiceImpl implements BasketService {

    private final BasketRepository basketRepository;

    @Override
    public Basket create(Basket basket) {
        return basketRepository.save(basket);
    }

    @Override
    public List<Basket> read() {
        return basketRepository.findAll();
    }

    @Override
    public Basket update(Long id, Basket basket) {
        return basketRepository.findById(id)
                .map(b -> {
                    // Vérifiez si les champs ne sont pas null dans l'objet basket avant de les mettre à jour
                    if (basket.getQuantity() != null) {
                        b.setQuantity(basket.getQuantity());
                    }
                    // Ajoutez d'autres mises à jour selon vos besoins
                    // Exemple : b.setArticles(basket.getArticles());

                    return basketRepository.save(b);
                })
                .orElseThrow(() -> new RuntimeException("Panier non trouvé"));
    }


    @Override
    public Basket readById(Long id) {
        return basketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Panier non trouvé"));
    }

    @Override
    public String delete(Long id) {
        basketRepository.deleteById(id);
        return ("Panier supprimé");
    }
}
