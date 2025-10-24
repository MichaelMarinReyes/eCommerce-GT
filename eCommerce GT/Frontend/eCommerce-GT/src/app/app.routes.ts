import { Routes } from '@angular/router';
import { MainPageComponent } from './components/main-page/main-page.component';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { CommonUserComponent } from './components/user-module/common-user/common-user.component';
import { ProductListComponent } from './components/user-module/product-list/product-list.component';
import { ProductCreateComponent } from './components/user-module/product-create/product-create.component';
import { AuthGuard } from './guards/auth.guard';
import { MyProductsComponent } from './components/user-module/my-products/my-products.component';

export const routes: Routes = [
    { path: '', title: 'eCommerce-GT', component: MainPageComponent },
    { path: 'home', title: 'eCommerce-GT', component: MainPageComponent },
    { path: 'login', title: 'Iniciar sesión', component: LoginComponent },
    { path: 'register', title: 'Registrarase', component: RegisterComponent },
    {
        path: 'common-user',
        title: 'eCommerce-GT',
        component: CommonUserComponent,
        canActivate: [AuthGuard],
        children: [
            { path: '', redirectTo: 'product-list', pathMatch: 'full' },
            { path: 'product-list', title: 'Productos', component: ProductListComponent },
            { path: 'product-create', title: 'Vender producto', component: ProductCreateComponent },
            { path: 'my-products', title: 'Mis productos', component: MyProductsComponent }
        ]
    },
    {
        path: 'product-create',
        title: 'Vender Producto',
        component: ProductCreateComponent,
        canActivate: [AuthGuard]
    }
];
