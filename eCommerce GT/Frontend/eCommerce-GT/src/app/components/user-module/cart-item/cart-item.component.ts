import { Component, OnInit } from '@angular/core';
import { CartService } from '../../../services/cart.service';
import { CartProduct } from '../../../models/cart.model';
import { AuthenticationService } from '../../../services/authentication.service';
import Swal from 'sweetalert2';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { environment } from '../../../../environments/environment.prod';
import { Router } from '@angular/router';

@Component({
  selector: 'app-cart-item',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './cart-item.component.html',
  styleUrl: './cart-item.component.css'
})
export class CartItemComponent implements OnInit {
  cartItems: CartProduct[] = [];
  idart!: number;
  filteredCartItems: CartProduct[] = [];
  userDpi: string = '';
  searchTerm: string = '';
  apiUrl = environment.apiUrl;

  constructor(
    private cartService: CartService,
    private authService: AuthenticationService,
    private router: Router
  ) {
    this.userDpi = this.authService.getCurrentUser()?.dpi ?? '';

  }

  ngOnInit(): void {
    this.loadCart();
  }

  loadCart(): void {
    this.cartService.getCart(this.userDpi).subscribe({
      next: (data: CartProduct[]) => {
        this.cartItems = data.map(item => ({
          ...item,
          product: {
            id: item.product.id,
            name: item.product.name,
            description: item.product.description,
            price: item.product.price,
            stock: item.product.stock,
            condition: item.product.condition,
            status: item.product.status,
            category: item.product.category,
            image: item.product.image,
            averageRating: item.product.averageRating,
            ratings: item.product.ratings
          }
        }));
        this.filteredCartItems = [...this.cartItems];

        if (this.cartItems.length > 0) {
          this.idart = this.cartItems[0].idCart;
        } else {
          Swal.fire('Error', 'No se encontró el idCart en los productos del carrito', 'error');
        }
      },
      error: (err) => console.log(err)
    });
  }

  removeItem(productId: number): void {
    this.cartService.removeFromCart(this.userDpi, productId).subscribe({
      next: () => {
        Swal.fire('Carrito', 'Producto eliminado', 'success');
        this.loadCart();
      },
      error: (err) => console.log(err)
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
    if (this.idart) {
      this.router.navigate(['common-user/checkout', this.idart]);
    } else {
      Swal.fire('Carrito vacío', 'No hay productos en el carrito', 'warning');
    }
  }

  getTotal(): number {
    return this.cartItems.reduce((sum, item) => sum + item.product.price * item.quantity, 0);
  }

  increaseQuantity(item: CartProduct) {
    item.quantity++;
    this.cartService.addToCart(this.userDpi, item.product.id, 1).subscribe();
  }

  decreaseQuantity(item: CartProduct) {
    if (item.quantity > 1) {
      item.quantity--;
      this.cartService.addToCart(this.userDpi, item.product.id, -1).subscribe();
    }
  }

  updateQuantity(item: CartProduct) {
    if (item.quantity < 1) {
      item.quantity = 1;
    }

    this.cartService.updateQuantity(this.userDpi, item.product.id, item.quantity).subscribe({
      next: () => this.loadCart(),
      error: () => Swal.fire('Error', 'No se pudo actualizar la cantidad', 'error')
    });
  }

  applyCartFilters() {
    this.filteredCartItems = this.cartItems.filter(item =>
      item.product.name.toLowerCase().includes(this.searchTerm.toLowerCase())
    );
  }
}