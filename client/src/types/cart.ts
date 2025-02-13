import type {Product} from "@/types/product.ts";

export interface CartItem {
  id?: number;
  product: Product;
  quantity: number;
  cart?: {
    id: number;
  }
}
