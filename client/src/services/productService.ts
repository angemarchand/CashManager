import axios, { type AxiosResponse } from "axios";
import type { Product } from "@/types/product";

const API_URL = 'http://localhost:8080/api/products';


export default {
  async getProducts (): Promise<Product[]> {
    const response = await axios.get(API_URL)

    return response.data
  }
}
