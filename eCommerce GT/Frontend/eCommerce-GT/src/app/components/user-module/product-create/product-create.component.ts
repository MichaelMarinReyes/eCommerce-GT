import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProductService } from '../../../services/product.service';
import { Product } from '../../../models/product.model';
import { FormsModule } from '@angular/forms';
import { AuthenticationService } from '../../../services/authentication.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-product-create',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './product-create.component.html',
  styleUrls: ['./product-create.component.css']
})
export class ProductCreateComponent {
  userDpi: string = '';
  product: Product = {
    id: 0,
    name: '',
    description: '',
    price: 0,
    stock: 0,
    condition: true,
    status: '',
    image: '',
    category: { idCategory: 0, categoryName: '', description: '' }
  };

  categories = [
    { idCategory: 1, categoryName: 'Académico', description: 'Artículos académicos' },
    { idCategory: 2, categoryName: 'Decoración', description: 'Artículos decorativos' },
    { idCategory: 3, categoryName: 'Hogar', description: 'Artículos para el hogar' },
    { idCategory: 4, categoryName: 'Tecnología', description: 'Productos tecnológicos' },
    { idCategory: 5, categoryName: 'Otros', description: 'Otros productos' },
  ];

  constructor(private productService: ProductService, private authenticationService: AuthenticationService) {
    const user = this.authenticationService.getCurrentUser();
    this.userDpi = user.dpi || '';
  }

  selectedFile: File | null = null;

  onFileSelected(event: any): void {
    const file = event.target.files[0];
    if (!file) return;

    const validExtension = file.name.toLowerCase().endsWith('.jpg');
    if (!validExtension) {
      alert('Solo se permiten imágenes en formato JPG.');
      event.target.value = '';
      return;
    }

    if (file.size > 5 * 1024 * 1024) {
      alert('La imagen supera el tamaño máximo permitido (5MB)');
      event.target.value = '';
      return;
    }

    this.selectedFile = file;

    const reader = new FileReader();
    reader.onload = () => {
      this.product.image = reader.result as string;
    };
    reader.readAsDataURL(file);
  }


  onSubmit(): void {
    if (!this.validateProduct()) return;

    const formData = new FormData();
    formData.append('productName', this.product.name);
    formData.append('description', this.product.description);
    formData.append('price', this.product.price.toString());
    formData.append('stock', this.product.stock.toString());
    formData.append('condition', this.product.condition.toString());

    if (this.selectedFile) {
      formData.append('image', this.selectedFile, this.selectedFile.name);
    }

    this.productService.createProduct(formData, this.userDpi).subscribe({
      next: (response) => {
        Swal.fire('Producto creado', 'El producto se ha creado correctamente', 'success');
        this.resetForm();
      },
      error: (error) => {
        console.error('Error al crear el producto:', error);
        Swal.fire('Error', 'No se pudo crear el producto. Intenta nuevamente', 'error');
      }
    });
  }

  resetForm(): void {
    this.product = {
      id: 0,
      name: '',
      description: '',
      price: 0,
      stock: 0,
      condition: true,
      status: '',
      image: '',
      category: { idCategory: 0, categoryName: '', description: '' }
    };
    this.selectedFile = null;

    const fileInput = document.getElementById('productImage') as HTMLInputElement;
    if (fileInput) {
      fileInput.value = '';
    }
  }

  validateProduct(): boolean {
    if (!this.product.name.trim()) {
      alert('El nombre del producto es obligatorio');
      return false;
    }

    if (!this.product.description.trim()) {
      alert('La descripción del producto es obligatoria');
      return false;
    }

    if (!this.selectedFile) {
      alert('Debes seleccionar una imagen del producto');
      return false;
    }

    if (!this.product.price || this.product.price <= 0) {
      alert('El precio debe ser mayor a 0');
      return false;
    }

    if (!this.product.stock || this.product.stock < 1) {
      alert('El stock debe ser al menos 1');
      return false;
    }

    if (!this.product.category || this.product.category.idCategory === 0) {
      alert('Debes seleccionar una categoría válida');
      return false;
    }

    if (this.selectedFile) {
      const validExtension = this.selectedFile.name.toLowerCase().endsWith('.jpg');
      if (!validExtension) {
        alert('Solo se permiten imágenes en formato JPG.');
        return false;
      }

      if (this.selectedFile.size > 5 * 1024 * 1024) {
        alert('La imagen supera el tamaño máximo permitido (5MB)');
        return false;
      }
    }

    return true;
  }
}
