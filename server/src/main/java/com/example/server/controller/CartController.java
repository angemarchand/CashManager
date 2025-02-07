package com.example.server.controller;

import com.example.server.entity.Cart;
import com.example.server.entity.CartItem;
import com.example.server.entity.Product;
import com.example.server.repository.CartRepository;
import com.example.server.repository.CartItemRepository;
import com.example.server.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.Optional;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    @Autowired
    public CartController(CartRepository cartRepository, CartItemRepository cartItemRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
    }

    @GetMapping("/{cartId}")
    public ResponseEntity<Cart> getCart(@PathVariable Long cartId) {
        Optional<Cart> cartOpt = cartRepository.findById(cartId);
        return cartOpt.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Ajouter un article au panier avec pour paramètres productId, quantity
    @PostMapping("/items")
    public ResponseEntity<?> addCartItem(
            @RequestParam Long productId,
            @RequestParam Integer quantity) {

        Optional<Product> productOpt = productRepository.findById(productId);
        if (!productOpt.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Produit non trouvé");
        }
        Product product = productOpt.get();

        if (product.getStock() < quantity) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Stock insuffisant pour le produit : " + product.getName());
        }


        Cart cart = new Cart();
        cart = cartRepository.save(cart);

        CartItem cartItem = CartItem.builder()
                .cart(cart)
                .product(product)
                .quantity(quantity)
                .build();

        cart.getItems().add(cartItem);
        CartItem savedCartItem = cartItemRepository.save(cartItem);

        cartRepository.saveAndFlush(cart);

        return ResponseEntity.ok(savedCartItem);
    }



    @DeleteMapping("/items/{itemId}")
    public ResponseEntity<Void> removeCartItem(@PathVariable Long itemId) {
        Optional<CartItem> itemOpt = cartItemRepository.findById(itemId);
        if (itemOpt.isPresent()) {
            cartItemRepository.delete(itemOpt.get());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
