import { Rating } from "./rating.model";

export interface Category {
  idCategory: number;
  categoryName: string;
  description: string;
}

export interface Product {
  id: number;
  name: string;
  description: string;
  price: number;
  stock: number;
  condition: boolean;
  status?: string;
  category: Category;
  image: string;
  averageRating?: number;
  ratings?: Rating[];
  sellerName?: string;
  createdAt?: string;
}
