<template>
  <v-container>
    <h1 class="text-center my-4">Checkout</h1>
    <v-row v-if="cartItems.length > 0" dense>
      <v-col
        v-for="item in cartItems"
        :key="item.product.id"
        cols="12"
        md="6"
      >
        <v-card outlined>
          <v-img :src="item.product.productUrl" height="200px">
            <v-card-title class="white--text">{{ item.product.name }}</v-card-title>
          </v-img>
          <v-card-subtitle class="mt-2">
            Price per unit: {{ formatCurrency(item.product.price) }}
          </v-card-subtitle>
          <v-card-text>
            Quantity: {{ item.quantity }}<br>
            Total: {{ formatCurrency(item.product.price * item.quantity) }}
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>
    <v-row v-else>
      <v-col class="text-center">
        <p>Your shopping cart is empty.</p>
      </v-col>
    </v-row>
    <v-row v-if="cartItems.length > 0" class="mt-4">
      <v-col cols="12" md="4">
        <v-card outlined>
          <PaymentMethodForm />
        </v-card>
      </v-col>
    </v-row>
    <v-row v-if="cartItems.length > 0" class="mt-6" justify="end">
      <v-col cols="12" md="4">
        <v-card outlined>
          <v-card-title>Grand Total</v-card-title>
          <v-card-text class="text-h5">
            {{ formatCurrency(totalPrice) }}
          </v-card-text>
          <v-card-actions>
            <v-btn class="bg-primary" rounded block @click="placeOrder" size="large" >Place Order</v-btn>
          </v-card-actions>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import axios from 'axios'
import { useCartStore } from '@/stores/cart'
import { useRouter } from 'vue-router'
import PaymentMethodForm from "@/components/PaymentMethodForm.vue";

const cartStore = useCartStore()
const router = useRouter()

const cartItems = computed(() => cartStore.items)
const totalPrice = computed(() => cartStore.totalPrice)

const formatCurrency = (value: number): string => {
  return new Intl.NumberFormat('fr-FR', { style: 'currency', currency: 'EUR' }).format(value)
}


const placeOrder = async () => {
  if (!cartStore.cartId) {
    alert("Your cart is empty or not saved.");
    return;
  }
  try {
    const response = await axios.post('http://localhost:8080/api/orders', {
      cartId: cartStore.cartId
    });
    console.log("Order placed: ", response.data);
    cartStore.clearCart();
    await router.push('/order-confirmation');
  } catch (error) {
    console.error("Error placing order:", error);
    alert("Error placing order.");
  }
}
</script>
