import {defineStore} from "pinia";
import type {CartItem} from "@/types/cart.ts";
import type {Product} from "@/types/product.ts";
import axios from "axios";


const API_BASE = 'http://localhost:8080/api/cart';

export const useCartStore = defineStore('cart', {
  state: ()=> ({
    cartId: null as number | null,
    items: [] as CartItem[],
  }),
  getters: {
    totalItems(state): number {
      return state.items.reduce((sum, item) => sum + item.quantity, 0);
    },
    totalPrice(state): number {
      return state.items.reduce((total, item) => total + item.product.price * item.quantity, 0);
    }
  },
  actions: {
    async fetchCart() {
      console.log(this.items)
      console.log(this.cartId)
      if (!this.cartId) {
        this.items = [];
        return;
      }
      try {
        const response = await axios.get(`${API_BASE}/${this.cartId}`);
        console.log("Réponse", response)
        this.items = response.data.items;
      } catch (error) {
        console.error("Erreur lors du chargement du panier :", error);
      }
    },
    async addProduct(product: Product, quantity: number = 1) {
      try {
        const response = await axios.post(`${API_BASE}/items`, null, {
          params: { productId: product.id, quantity }
        });
        console.log("P à l'ajout: ", response.data);
        const cartItem: CartItem = response.data;
        console.log("Le panier à l'ajout du produit: ", cartItem.cart);

        if (cartItem.cart && cartItem.cart.id) {
          this.cartId = cartItem.cart.id;
        }
        const existingItem = this.items.find(item => item.product.id === product.id);
        if (existingItem) {
          console.log("Exit: ", existingItem)
          existingItem.quantity = cartItem.quantity;
        } else {
          console.log("Items: ", this.items)
          this.items.push(cartItem);

          return cartItem
        }
      } catch (error) {
        console.error("Erreur lors de l'ajout du produit au panier :", error);
        throw error;
      }
    }
,
    async removeProduct(productId: number) {
      try {
        const item = this.items.find(item => item.product.id === productId);
        if (!item) return;
        await axios.delete(`${API_BASE}/items/${item.id}`);
        this.items = this.items.filter(item => item.product.id !== productId);
      } catch (error) {
        console.error("Erreur lors de la suppression du produit du panier :", error);
        throw error;
      }
    },

    clearCart() {
      this.items = [];
    }
  },
  persist: true
})
