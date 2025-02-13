<template>
  <v-card class="mx-auto" max-width="400" outlined>
    <v-img :src="product.productUrl" height="100px">
      <v-card-title class="white--text font-bold">{{ product.name }}</v-card-title>
    </v-img>

    <v-card-subtitle class="mt-2 font-bold">
      {{ formatCurrency(product.price) }}
    </v-card-subtitle>

    <v-card-actions>
      <v-spacer></v-spacer>
      <v-btn class="bg-accent" rounded @click="handleAddToCart">
        Add to cart
      </v-btn>
    </v-card-actions>
  </v-card>
</template>

<script setup lang="ts">
import type { Product } from '@/types/product';
import {useCartStore} from "@/stores/cart.ts";
import { useRouter } from 'vue-router';

const props = defineProps<{ product: Product }>()
const cartStore = useCartStore()
const router = useRouter()


const formatCurrency = (value: number): string => {
  return new Intl.NumberFormat('fr-FR', { style: 'currency', currency: 'EUR' }).format(value)
}



const handleAddToCart = async () => {
  try {
    await cartStore.addProduct(props.product, 1);
    console.log("Product added to cart:", props.product);
    await router.push({path: '/cart'})
  } catch (error) {
    console.error("Error adding product to cart:", error);
  }
};

</script>
