package com.example.server.repository;

import com.example.server.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
    // Vous pouvez ajouter des méthodes spécifiques, par exemple pour retrouver un panier par userId
}
