import { Component, OnInit } from '@angular/core';
import { Product } from '../../../models/product.model';
import { environment } from '../../../../environments/environment.prod';
import { ProductService } from '../../../services/product.service';
import Swal from 'sweetalert2';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-product-approval',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './product-approval.component.html',
  styleUrl: './product-approval.component.css'
})
export class ProductApprovalComponent implements OnInit {
  pendingProducts: Product[] = [];
  apiUrl = environment.apiUrl;

  constructor(private productService: ProductService) { }

  ngOnInit(): void {
    this.loadPendingProducts();
  }

  loadPendingProducts(): void {
    this.productService.getPendingProducts().subscribe({
      next: (products: Product[]) => {
        this.pendingProducts = products;
      },
      error: (err) => console.log('Error cargando productos pendientes', err)
    });

  }

  approveProduct(productId: number): void {
    Swal.fire({
      title: '¿Aprobar producto?',
      icon: 'question',
      showCancelButton: true,
      confirmButtonText: 'Sí, aprobar',
      cancelButtonText: 'Cancelar',
      confirmButtonColor: '#fa8541',
      cancelButtonColor: '#d33'
    }).then(result => {
      if (result.isConfirmed) {
        this.productService.approveProduct(productId).subscribe({
          next: () => {
            Swal.fire('Aprobado', 'El producto ha sido aprobado.', 'success');
            this.loadPendingProducts();
          },
          error: (err) => {
            console.error(err);
            Swal.fire('Error', 'No se pudo aprobar el producto.', 'error');
          }
        });
      }
    });
  }

  rejectProduct(productId: number): void {
    Swal.fire({
      title: '¿Rechazar producto?',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Sí, rechazar',
      cancelButtonText: 'Cancelar',
      confirmButtonColor: '#fa8541',
      cancelButtonColor: '#d33'
    }).then(result => {
      if (result.isConfirmed) {
        this.productService.rejectProduct(productId).subscribe({
          next: () => {
            Swal.fire('Rechazado', 'El producto ha sido rechazado.', 'success');
            this.loadPendingProducts();
          },
          error: (err) => {
            console.error(err);
            Swal.fire('Error', 'No se pudo rechazar el producto.', 'error');
          }
        });
      }
    });
  }
}
