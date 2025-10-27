import { Product } from "./product.model";

export interface CartModel {
  idCart: number;
  products: CartProduct[];
  total: number;
  createdAt: string;
  userDpi: string;
}

export interface ShoppingCart {
  idCart: number;
  userDpi: string;
  status: boolean;
  createdAt: string;
  updateAt: string;
}

export interface CartProduct {
  idCart: number;
  product: Product;
  quantity: number;
  price: number;
  cart: ShoppingCart;
}