import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ProductService } from '../../../services/product.service';
import { Product } from '../../../models/product.model';
import { AuthenticationService } from '../../../services/authentication.service';
import { environment } from '../../../../environments/environment.prod';
import { Router } from '@angular/router';
import { CartService } from '../../../services/cart.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-product-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.css']
})
export class ProductListComponent implements OnInit {
  apiUrl = environment.apiUrl;
  products: Product[] = [];
  filteredProducts: Product[] = [];
  searchTerm: string = '';
  priceOrder: string = '';
  selectedCategory: string = '';

  constructor(
    private productService: ProductService,
    private authService: AuthenticationService,
    private router: Router,
    private cartService: CartService
  ) { }

  ngOnInit(): void {
    const dpi = this.authService.getCurrentUser().dpi;

    if (!dpi) {
      return;
    }

    this.productService.getProductExcludeById(dpi).subscribe({
      next: (data) => {
        this.products = data;
        this.filteredProducts = data;
      },
      error: (err) => {
        console.error('Error al cargar productos:', err);
      }
    });
  }

  applyFilters(): void {
    let filtered = [...this.products];

    if (this.searchTerm.trim() !== '') {
      filtered = filtered.filter(product =>
        product.name.toLowerCase().includes(this.searchTerm.toLowerCase())
      );
    }

    if (this.priceOrder === 'asc') {
      filtered.sort((a, b) => a.price - b.price);
    } else if (this.priceOrder === 'desc') {
      filtered.sort((a, b) => b.price - a.price);
    }

    if (this.selectedCategory) {
      filtered = filtered.filter(p =>
        p.category?.categoryName === this.selectedCategory
      );
    }
    this.filteredProducts = filtered;
  }

  goToDetail(id: number): void {
    this.router.navigate(['/common-user/product-detail/', id]);
  }

  buyProduct(product: Product): void {
    const userDpi = this.authService.getCurrentUser()?.dpi;
    if (!userDpi) {
      Swal.fire('Error', 'Usuario no autenticado', 'error');
      return;
    }

    this.cartService.addToCart(userDpi, product.id, 1).subscribe({
      next: (cartItem) => {
        Swal.fire({
          title: 'Producto agregado al carrito',
          html: `
          <strong>${cartItem.product.name}</strong> <br>
          Cantidad: ${cartItem.quatity} <br>
          Precio: Q ${cartItem.price}
        `,
          icon: 'success'
        });
      },
      error: () => Swal.fire('Error', 'No se pudo agregar el producto', 'error')
    });
  }


}
