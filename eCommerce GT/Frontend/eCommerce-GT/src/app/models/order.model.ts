export interface OrderProduct {
  productId?: number;
  name: string;
  image?: string;
  price: number;
  quantity: number;
  categoryName?: string;
}

export interface OrderResponse {
  orderId: number;
  totalAmount: number;
  delivered: boolean;
  createdAt: string;
  deliveryDate: string;
  products: OrderProduct[];
  userDpi?: string;
}