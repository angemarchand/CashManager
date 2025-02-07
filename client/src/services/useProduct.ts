import productService from "@/services/productService.ts";
import type { Product } from "@/types/product";
import {ref} from "vue";


export function useProduct() {
  const products = ref<Product[]>([])
  const error = ref<string | null>(null)

  const fetchProducts = async () => {
    try {
      products.value = await productService.getProducts()
      error.value = null
    }catch (err:any) {
      error.value = err.response?.data?.message || 'Erreur lors du chargement des produits'
    }
  }

  return { products, error, fetchProducts }
}
