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
  category: Category;
  image: string;
  averageRating?: number;
}
