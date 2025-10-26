import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { Product } from '../../../models/product.model';
import { environment } from '../../../../environments/environment.prod';
import { ActivatedRoute, Router } from '@angular/router';
import { ProductService } from '../../../services/product.service';
import Swal from 'sweetalert2';
import { CartService } from '../../../services/cart.service';
import { AuthenticationService } from '../../../services/authentication.service';

@Component({
  selector: 'app-product-detail',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './product-detail.component.html',
  styleUrl: './product-detail.component.css'
})
export class ProductDetailComponent {
  productId!: number;
  product: Product | null = null;
  apiUrl = environment.apiUrl;

  constructor(
    private route: ActivatedRoute,
    private productService: ProductService,
    private router: Router,
    private cartService: CartService,
    private authService: AuthenticationService
  ) { }

  ngOnInit(): void {
    this.productId = Number(this.route.snapshot.paramMap.get('id'));
    this.loadProduct();
  }

  loadProduct(): void {
    this.productService.getProductById(this.productId).subscribe({
      next: (data) => {
        this.product = data;
        if (this.product.image) {
          this.product.image += `?t=${new Date().getTime()}`;
        }
      },
      error: (err) => {
        console.log('Error al obtener producto', err);
        Swal.fire('Error', 'No se pudo cargar el producto', 'error');
        this.router.navigate(['/common-user/my-products']);
      }
    })
  }
  buyProduct(id: number): void {
    const userDpi = this.authService.getCurrentUser()?.dpi;
    if (!userDpi) {
      Swal.fire('Error', 'Usuario no autenticado', 'error');
      return;
    }

    if (this.product && this.product.id === id) {
      this.cartService.addToCart(userDpi, id, 1).subscribe({
        next: (res) => Swal.fire('Carrito', res, 'success'),
        error: () => Swal.fire('Error', 'No se pudo agregar el producto', 'error')
      });
    } else {
      Swal.fire('Error', 'Producto no encontrado', 'error');
    }
  }

}
