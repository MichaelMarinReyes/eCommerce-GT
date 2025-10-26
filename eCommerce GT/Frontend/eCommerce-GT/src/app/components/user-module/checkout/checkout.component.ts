import { Component, OnInit } from '@angular/core';
import { CartService } from '../../../services/cart.service';
import { ActivatedRoute } from '@angular/router';
import { CartProduct } from '../../../models/cart.model';
import Swal from 'sweetalert2';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AuthenticationService } from '../../../services/authentication.service';

@Component({
  selector: 'app-checkout',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './checkout.component.html',
  styleUrl: './checkout.component.css'
})
export class CheckoutComponent implements OnInit {
  idCart: number = 0;
  userDpi: string = '';
  cartItems: CartProduct[] = [];

  payment = {
    cardName: '',
    cardNumber: '',
    expiry: '',
    cvv: ''
  };

  constructor(
    private cartService: CartService,
    private route: ActivatedRoute,
    private authService: AuthenticationService
  ) { }

  ngOnInit(): void {
    this.idCart = Number(this.route.snapshot.paramMap.get('idCart'));
    this.userDpi = this.authService.getCurrentUser()?.dpi ?? '';
    if (this.idCart && this.userDpi) {
      this.loadCart();
    } else {
      Swal.fire('Error', 'No se pudo obtener la información del carrito', 'error');
    }
  }

  loadCart(): void {
    this.cartService.getCartById(this.idCart, this.userDpi).subscribe({
      next: (data) => {
        this.cartItems = data.products;
        console.log("Carrito cargado:", JSON.stringify(this.cartItems, null, 2));
      },
      error: (err) => {
        console.error(err);
        Swal.fire('Error', 'No se pudo cargar el carrito', 'error');
      }
    });
  }

  getTotal(): number {
    return this.cartItems.reduce((sum, item) => sum + item.product.price * item.quantity, 0);
  }

  processPayment(form: any): void {
    if (form.valid) {
      Swal.fire('Pago exitoso', 'Tu compra ha sido procesada correctamente', 'success');
      // Aquí puedes llamar un endpoint que cambie el estado del carrito a “pagado”
    } else {
      Swal.fire('Formulario inválido', 'Por favor completa todos los campos correctamente', 'warning');
    }
  }
}