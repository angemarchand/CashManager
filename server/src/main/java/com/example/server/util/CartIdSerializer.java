package com.example.server.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.example.server.entity.Cart;
import java.io.IOException;


public class CartIdSerializer extends JsonSerializer<Cart> {
    @Override
    public void serialize(Cart cart, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (cart == null) {
            gen.writeNull();
        } else {
            gen.writeStartObject();
            gen.writeNumberField("id", cart.getId());
            gen.writeEndObject();
        }
    }
}