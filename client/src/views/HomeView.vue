<template>
  <div class="my-10">
    <h1 class="text-center">Welcome to Easy Shop!</h1>
  </div>
  <v-container fluid>
    <v-row dense>
      <v-col
        v-for="product in paginatedProducts"
        :key="product.id"
        cols="12" sm="6" md="4"
      >
        <ProductCard :product="product" />
      </v-col>
    </v-row>
    <v-row justify="center" class="my-4">
      <v-pagination
        v-model="page"
        :length="pageCount"
        total-visible="5"
      />
    </v-row>
    <v-sheet color="surface-variant-alt"></v-sheet>
  </v-container>
</template>
<script setup lang="ts">
import {computed, onMounted, ref} from 'vue'
import {useProduct} from "@/services/useProduct.ts";
import ProductCard from "@/components/ProductCard.vue";

const { products, error, fetchProducts } = useProduct()

onMounted(() => {
  fetchProducts()
})

const page = ref(1)
const itemsPerPage = 2

const pageCount = computed(() => {
  return Math.ceil(products.value.length / itemsPerPage)
})

const paginatedProducts = computed(() => {
  const start = (page.value - 1) * itemsPerPage;
  return products.value.slice(start, start + itemsPerPage);
})
</script>
