import { Component, OnInit } from '@angular/core';
import { CartProduct, CartService } from '../../../services/cart.service';
import { AuthenticationService } from '../../../services/authentication.service';
import Swal from 'sweetalert2';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-cart-item',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './cart-item.component.html',
  styleUrl: './cart-item.component.css'
})
export class CartItemComponent implements OnInit{
  cartItems: CartProduct[] = [];
  userDpi: string = '';
  
  constructor(private cartService: CartService, private authService: AuthenticationService) {
    this.userDpi = this.authService.getCurrentUser()?.dpi?? '';
  
  }

  ngOnInit(): void {
      this.loadCart();
  }

  loadCart(): void {
    this.cartService.getCart(this.userDpi).subscribe({
      next: (data) => this.cartItems = data,
      error: (err) => console.log(err)
    });
  }

  removeItem(productId: number): void {
    this.cartService.removeFromCart(this.userDpi, productId).subscribe({
      next: () => {
        Swal.fire('Carrito', 'Producto eliminado', 'success');
        this.loadCart();
      }
    });
  }

  clearCart(): void {
    this.cartService.clearCart(this.userDpi).subscribe({
      next: () => {
        Swal.fire('Carrito', 'Carrito vaciado', 'success');
        this.loadCart();
      }
    });
  }

  checkout(): void {
    this.cartService.checkout(this.userDpi).subscribe({
      next: () => {
        Swal.fire('Compra', 'Compra realizada exitosamente', 'success');
        this.loadCart();
      }
    });
  }

  getTotal(): number {
    return this.cartItems.reduce((sum, item) => sum + item.product.price * item.quantity, 0);
  }
}