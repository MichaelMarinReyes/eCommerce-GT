import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ProductService } from '../../../services/product.service';
import { AuthenticationService } from '../../../services/authentication.service';
import { Product } from '../../../models/product.model';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-my-products',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterOutlet],
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
    private authenticationService: AuthenticationService,
    private router: Router,
    private route: ActivatedRoute
  ) { }

ngOnInit(): void {
  const currentUser = this.authenticationService.getCurrentUser();
  this.userDpi = currentUser?.dpi || '';
  if (this.userDpi) {
    this.productService.getProductByUser(this.userDpi).subscribe({
      next: (data) => {
        this.products = data.map(p => ({
          ...p,
          status: p.status?.toString() || ''
        }));
                console.log('productos del usuario', this.products);
        this.filteredProducts = [...this.products];
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

  editProduct(id: number) {
    this.router.navigate(['common-user/product-edit/', id]);
  }
}