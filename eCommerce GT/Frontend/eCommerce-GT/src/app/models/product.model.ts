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
  image?: string;
  averageRating?: number;
  category: Category;
}
