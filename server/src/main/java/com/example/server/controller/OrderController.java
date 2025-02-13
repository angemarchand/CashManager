package com.example.server.controller;

import com.example.server.dto.OrderRequest;
import com.example.server.entity.Cart;
import com.example.server.entity.Order;
import com.example.server.entity.OrderItem;
import com.example.server.repository.CartRepository;
import com.example.server.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;

    @Autowired
    public OrderController(OrderRepository orderRepository, CartRepository cartRepository) {
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
    }

    @PostMapping
    public ResponseEntity<?> placeOrder(@RequestBody OrderRequest orderRequest) {
        // Récupérer le panier à partir de son id
        Cart cart = cartRepository.findById(orderRequest.getCartId()).orElse(null);
        if (cart == null || cart.getItems().isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Panier vide ou non trouvé.");
        }

        // Calculer le total de la commande
        BigDecimal total = cart.getItems().stream()
                .map(item -> item.getProduct().getPrice().multiply(new BigDecimal(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Créer une commande en mappant chaque CartItem en OrderItem
        Set<OrderItem> orderItems = cart.getItems().stream().map(cartItem ->
                OrderItem.builder()
                        .product(cartItem.getProduct())
                        .quantity(cartItem.getQuantity())
                        .priceAtPurchase(cartItem.getProduct().getPrice())
                        .build()
        ).collect(Collectors.toSet());

        Order order = Order.builder()
                //.userId(...), // TODO pour associer la commande à un utilisateur
                .totalPrice(total)
                .items(orderItems)
                .build();

        // Pour chaque OrderItem, fixer l'association avec la commande
        orderItems.forEach(item -> item.setOrder(order));

        Order savedOrder = orderRepository.save(order);

        // Vider le panier après la commande (optionnel)
        cart.getItems().clear();
        cartRepository.save(cart);

        return ResponseEntity.ok(savedOrder);
    }
}
