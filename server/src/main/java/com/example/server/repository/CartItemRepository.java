package com.example.server.repository;

import com.example.server.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    // Vous pouvez ajouter des méthodes pour retrouver les items d'un panier, etc.
}
