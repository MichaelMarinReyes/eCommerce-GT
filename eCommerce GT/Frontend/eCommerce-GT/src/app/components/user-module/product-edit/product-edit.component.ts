import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ProductService } from '../../../services/product.service';
import { Product } from '../../../models/product.model';
import Swal from 'sweetalert2';
import { AuthenticationService } from '../../../services/authentication.service';
import { environment } from '../../../../environments/environment.prod';

@Component({
  selector: 'app-product-edit',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './product-edit.component.html',
  styleUrls: ['./product-edit.component.css']
})
export class ProductEditComponent implements OnInit {
  productId!: number;
  selectedFile: File | null = null;
  previewImage: string | null = null;
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

  categories = [
    { idCategory: 1, categoryName: 'Académico', description: 'Artículos académicos' },
    { idCategory: 2, categoryName: 'Decoración', description: 'Artículos decorativos' },
    { idCategory: 3, categoryName: 'Hogar', description: 'Artículos para el hogar' },
    { idCategory: 4, categoryName: 'Tecnología', description: 'Productos tecnológicos' },
    { idCategory: 5, categoryName: 'Otros', description: 'Otros productos' },
  ];

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private productService: ProductService,
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
          this.previewImage = `${environment.apiUrl}/uploads/images/${this.product.image}`;
        }
      },
      error: (err) => {
        console.error('Error al obtener el producto', err);
        Swal.fire('Error', 'No se pudo cargar el producto', 'error');
      }
    });
  }

  onFileSelected(event: any): void {
    if (event.target.files.length > 0) {
      const file = event.target.files[0];
      const validExtension = file.name.toLowerCase().endsWith('.jpg');
      if (!validExtension) {
        Swal.fire('Error', 'Solo se permiten imágenes en formato JPG.', 'error');
        return;
      }
      if (file.size > 5 * 1024 * 1024) {
        Swal.fire('Error', 'La imagen supera el tamaño máximo permitido (5MB)', 'error');
        return;
      }

      this.selectedFile = file;

      const reader = new FileReader();
      reader.onload = (e: any) => this.previewImage = e.target.result;
      reader.readAsDataURL(file);
    }
  }

  updateProduct(): void {
    if (!this.validateProduct()) return;

    const formData = new FormData();
    formData.append('productName', this.product.name);
    formData.append('description', this.product.description);
    formData.append('price', this.product.price.toString());
    formData.append('stock', this.product.stock.toString());
    formData.append('condition', this.product.condition.toString());
    formData.append('categoryName', this.product.category.categoryName);

    if (this.selectedFile) {
      formData.append('image', this.selectedFile, this.selectedFile.name);
    }

    const dpi = this.authService.getCurrentUser().dpi;
    if (!dpi) {
      Swal.fire('Error', 'No se pudo obtener el DPI del usuario', 'error');
      return;
    }

    this.productService.updateProduct(this.productId, formData, dpi).subscribe({
      next: () => {
        Swal.fire('Éxito', 'Producto actualizado correctamente', 'success');
        this.router.navigate(['/common-user/my-products']);
      },
      error: (err) => {
        console.error('Error al actualizar el producto', err);
        const msg = err.error?.message || 'No se pudo actualizar el producto';
        Swal.fire('Error', msg, 'error');
      }
    });
  }


  validateProduct(): boolean {
    if (!this.product.name.trim()) {
      Swal.fire('Error', 'El nombre del producto es obligatorio', 'error');
      return false;
    }

    if (!this.product.description.trim()) {
      Swal.fire('Error', 'La descripción del producto es obligatoria', 'error');
      return false;
    }

    if (!this.product.category || !this.product.category.categoryName) {
      Swal.fire('Error', 'Debes seleccionar una categoría válida', 'error');
      return false;
    }

    if (!this.product.price || this.product.price <= 0) {
      Swal.fire('Error', 'El precio debe ser mayor a 0', 'error');
      return false;
    }

    if (!this.product.stock || this.product.stock < 1) {
      Swal.fire('Error', 'El stock debe ser al menos 1', 'error');
      return false;
    }

    if (this.selectedFile) {
      const validExtension = this.selectedFile.name.toLowerCase().endsWith('.jpg');
      if (!validExtension) {
        Swal.fire('Error', 'Solo se permiten imágenes en formato JPG.', 'error');
        return false;
      }

      if (this.selectedFile.size > 5 * 1024 * 1024) {
        Swal.fire('Error', 'La imagen supera el tamaño máximo permitido (5MB)', 'error');
        return false;
      }
    }

    return true;
  }

  compareCategories(c1: any, c2: any): boolean {
    return c1 && c2 ? c1.idCategory === c2.idCategory : c1 === c2;
  }

}