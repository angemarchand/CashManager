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
      <v-btn class="bg-accent" rounded >
        Ajouter au panier
      </v-btn>
    </v-card-actions>
  </v-card>
</template>

<script setup lang="ts">
import type { Product } from '@/types/product';

const props = defineProps<{ product: Product }>()


const formatCurrency = (value: number): string => {
  return new Intl.NumberFormat('fr-FR', { style: 'currency', currency: 'EUR' }).format(value)
}

</script>
