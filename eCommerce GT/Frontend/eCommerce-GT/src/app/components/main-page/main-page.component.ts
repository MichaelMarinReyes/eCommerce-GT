import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, NavigationEnd, RouterLink } from '@angular/router';
import { filter } from 'rxjs';
import { ProductService } from '../../services/product.service';
import { Product } from '../../models/product.model';

@Component({
  selector: 'main-page',
  standalone: true,
  imports: [RouterLink, CommonModule, FormsModule],
  templateUrl: './main-page.component.html',
  styleUrl: './main-page.component.css'
})
export class MainPageComponent {
  title: string = 'eCommerce-Gt';
  currentRoute: string = '';
  products: Product[] = [];
  filteredProducts: Product[] = [];
  searchTerm: string = '';
  priceOrder: string = '';
  selectedCategory: string = '';

  constructor(private router: Router, private productService: ProductService) {
    this.router.events.pipe(
      filter(event => event instanceof NavigationEnd)
    ).subscribe((event: NavigationEnd) => {
      this.currentRoute = event.urlAfterRedirects;
    });
  }

  ngOnInit(): void {
    this.productService.getProducts().subscribe({
      next: (data) => {
        this.products = data;
        console.log('Productos cargados:', this.products);
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
        product.productName.toLowerCase().includes(this.searchTerm.toLowerCase())
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
