package com.goutdupays.goutdupays.repository;

import com.goutdupays.goutdupays.modele.Basket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BasketRepository  extends JpaRepository<Basket, Long> {

    Basket findByUserId(Long id);
}
