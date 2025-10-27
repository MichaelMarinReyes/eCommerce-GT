import { Component, OnInit } from '@angular/core';
import { CartService } from '../../../services/cart.service';
import { ActivatedRoute, Router } from '@angular/router';
import { CartProduct } from '../../../models/cart.model';
import Swal from 'sweetalert2';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AuthenticationService } from '../../../services/authentication.service';
import { OrderService } from '../../../services/order.service';

@Component({
  selector: 'app-checkout',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './checkout.component.html',
  styleUrl: './checkout.component.css'
})
export class CheckoutComponent implements OnInit {
  userDpi: string = '';
  cartItems: CartProduct[] = [];
  cartId!: number;
  payment = {
    cardName: '',
    cardNumber: '',
    expiry: '',
    cvv: ''
  };

  constructor(
    private cartService: CartService,
    private route: ActivatedRoute,
    private authService: AuthenticationService,
    private router: Router,
    private orderService: OrderService
  ) {
    this.userDpi = this.authService.getCurrentUser()?.dpi ?? '';
  }

  ngOnInit(): void {
    this.cartId = Number(this.route.snapshot.paramMap.get('idCart'));
    this.loadCart();
  }

  loadCart(): void {
    this.cartService.getCartById(this.cartId, this.userDpi).subscribe({
      next: (data) => {
        console.log(data)
        this.cartItems = data.products;
      },
      error: (err: any) => console.log(err)
    });
  }

  getTotal(): number {
    return this.cartItems.reduce((sum, item) => sum + item.product.price * item.quantity, 0);
  }

  processPayment(form: any): void {
    if (form.invalid) {
      Swal.fire('Error', 'Por favor complete correctamente todos los campos de pago', 'error');
      return;
    }

    this.orderService.checkout(this.cartId, this.userDpi).subscribe({
      next: (res: any) => {
        Swal.fire('Éxito', 'Pago realizado y pedido generado', 'success');
        this.router.navigate(['/common-user/orders']); // redirigir a historial de pedidos
      },
      error: (err: any) => {
        console.log(err);
        Swal.fire('Error', 'No se pudo procesar el pago', 'error');
      }
    });
  }

}