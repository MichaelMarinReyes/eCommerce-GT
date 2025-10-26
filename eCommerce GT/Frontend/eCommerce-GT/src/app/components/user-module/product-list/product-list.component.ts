import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ProductService } from '../../../services/product.service';
import { Product } from '../../../models/product.model';
import { AuthenticationService } from '../../../services/authentication.service';
import { environment } from '../../../../environments/environment.prod';

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
    private authService: AuthenticationService
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
}
