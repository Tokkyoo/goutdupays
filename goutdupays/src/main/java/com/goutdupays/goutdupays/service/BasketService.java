package com.goutdupays.goutdupays.service;

import com.goutdupays.goutdupays.modele.Article;
import com.goutdupays.goutdupays.modele.Basket;

import java.util.List;

public interface BasketService {

    Basket create(Basket basket);

    List<Basket> read();

    Basket readById(Long id);

    Basket update(Long id, Basket basket);

    String delete(Long id);
}
