import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ProductService } from '../../../services/product.service';
import { AuthenticationService } from '../../../services/authentication.service';
import { Product } from '../../../models/product.model';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-my-products',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './my-products.component.html',
  styleUrl: './my-products.component.css'
})

export class MyProductsComponent implements OnInit {
  products: Product[] = [];
  userDpi: string = '';
  filteredProducts: Product[] = [];
  searchTerm: string = '';
  priceOrder: string = '';
  selectedCategory: string = '';

  constructor(
    private productService: ProductService,
    private authenticationService: AuthenticationService
  ) { }

  ngOnInit(): void {
    const currentUser = this.authenticationService.getCurrentUser();
    this.userDpi = currentUser?.dpi || '';
    console.log('entrando', this.userDpi)
    if (this.userDpi) {
      this.productService.getProductByUser(this.userDpi).subscribe({
        next: (data) => {
          this.products = data;
        },

        error: (err) => {
          console.error('Error al obtener productos del usuario:', err);
        }
      });
    }
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
}