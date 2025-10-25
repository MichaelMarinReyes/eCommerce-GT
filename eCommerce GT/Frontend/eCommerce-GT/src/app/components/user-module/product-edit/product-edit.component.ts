import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ProductService } from '../../../services/product.service';
import { Product } from '../../../models/product.model';

@Component({
  selector: 'app-product-edit',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './product-edit.component.html',
  styleUrls: ['./product-edit.component.css'] // 🔹 Ojo: debe ser 'styleUrls' (en plural)
})
export class ProductEditComponent implements OnInit {
  productId!: number;
  product: Product = {
    id: 0,
    name: '',
    description: '',
    price: 0,
    stock: 0,
    condition: false,
    category: { idCategory: 0, categoryName: '', description: '' },
    image: ''
  };

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private productService: ProductService
  ) { }

  ngOnInit(): void {
    this.productId = Number(this.route.snapshot.paramMap.get('id'));
    this.loadProduct();
  }

  loadProduct(): void {
    this.productService.getProductById(this.productId).subscribe({
      next: (data) => {
        this.product = data;
      },
      error: (err) => {
        console.error('Error al obtener el producto', err);
        alert('No se pudo cargar el producto');
      }
    });
  }

  updateProduct(): void {
    this.productService.updateProduct(this.productId, this.product).subscribe({
      next: () => {
        alert('Producto actualizado con éxito');
        this.router.navigate(['/mis-productos']);
      },
      error: (err) => {
        console.error('Error al actualizar el producto', err);
        alert('No se pudo actualizar el producto');
      }
    });
  }
}