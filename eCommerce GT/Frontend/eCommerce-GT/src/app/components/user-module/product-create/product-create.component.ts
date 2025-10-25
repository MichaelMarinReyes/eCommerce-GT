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
    productName: '',
    description: '',
    price: 0,
    stock: 0,
    condition: true,
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

  onFileSelected(event: any): void {
    const file = event.target.files[0];
    if (!file) return;

    if (file.size > 2 * 1024 * 1024) {
      alert('La imagen supera el tamaño máximo permitido (2MB)');
      event.target.value = '';
      return;
    }

    const reader = new FileReader();
    reader.onload = () => {
      this.product.image = reader.result as string; // Guardar en base64
    };
    reader.readAsDataURL(file);
  }

onSubmit(): void {

  if (!this.product.productName.trim()) {
    alert('El nombre del producto es obligatorio');
    return;
  }

  if (!this.product.description.trim()) {
    alert('La descripción del producto es obligatoria');
    return;
  }

  if (!this.product.image) {
    alert('Debes seleccionar una imagen del producto');
    return;
  }

  if (!this.product.price || this.product.price <= 0) {
    alert('El precio debe ser mayor a 0');
    return;
  }

  if (!this.product.stock || this.product.stock < 1) {
    alert('El stock debe ser al menos 1');
    return;
  }

  if (!this.product.category || this.product.category.idCategory === 0) {
    alert('Debes seleccionar una categoría válida');
    return;
  }

  this.productService.createProduct(this.product, this.userDpi).subscribe({
    next: (response) => {
      console.log('Producto creado:', response);
      Swal.fire({
        icon: 'success',
        title: 'Producto creado',
        text: 'El producto se ha creado correctamente'
      });
      this.product = {
        id: 0,
        productName: '',
        description: '',
        price: 0,
        stock: 0,
        condition: true,
        image: '',
        category: { idCategory: 0, categoryName: '', description: '' }
      };
    },
    error: (error) => {
      console.error('Error al crear el producto:', error);
      alert('No se pudo crear el producto. Intenta nuevamente');
    }
  });
}

}
