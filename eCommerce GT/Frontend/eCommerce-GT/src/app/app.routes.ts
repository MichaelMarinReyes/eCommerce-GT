import { Routes } from '@angular/router';
import { MainPageComponent } from './components/main-page/main-page.component';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { CommonUserComponent } from './components/user-module/common-user/common-user.component';
import { ProductListComponent } from './components/user-module/product-list/product-list.component';
import { ProductCreateComponent } from './components/user-module/product-create/product-create.component';
import { AuthGuard } from './guards/auth.guard';
import { MyProductsComponent } from './components/user-module/my-products/my-products.component';
import { ProductEditComponent } from './components/user-module/product-edit/product-edit.component';
import { ProductDetailComponent } from './components/user-module/product-detail/product-detail.component';
import { CartItemComponent } from './components/user-module/cart-item/cart-item.component';
import { CheckoutComponent } from './components/user-module/checkout/checkout.component';
import { OrderListComponent } from './components/user-module/order-list/order-list.component';
import { ModeratorComponent } from './components/moderator-module/moderator/moderator.component';
import { AdminComponent } from './components/admin-module/admin/admin.component';
import { SanctionCreateComponent } from './components/moderator-module/sanction-create/sanction-create.component';
import { SanctionsListComponent } from './components/moderator-module/sanctions-list/sanctions-list.component';
import { LogisticsComponent } from './components/logistics-module/logistics/logistics.component';
import { ProductApprovalComponent } from './components/moderator-module/product-approval/product-approval.component';
import { OrderDeliveryComponent } from './components/logistics-module/order-delivery/order-delivery.component';

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
        data: { role: 'USUARIO COMÚN' },
        children: [
            { path: '', redirectTo: 'product-list', pathMatch: 'full' },
            { path: 'product-list', title: 'Productos', component: ProductListComponent },
            { path: 'product-create', title: 'Vender producto', component: ProductCreateComponent },
            { path: 'my-products', title: 'Mis productos', component: MyProductsComponent },
            { path: 'product-edit/:id', title: 'Editar producto', component: ProductEditComponent },
            { path: 'product-detail/:id', title: 'Detalle del producto', component: ProductDetailComponent },
            { path: 'cart', title: 'Carrito de compras', component: CartItemComponent },
            { path: 'checkout/:idCart', title: 'Pagar', component: CheckoutComponent },
            { path: 'orders', title: 'Ordenes', component: OrderListComponent }
        ]
    },
    {
        path: 'admin',
        title: 'eCommerce-GT',
        component: AdminComponent,
        canActivate: [AuthGuard],
        data: { role: 'ADMINISTRADOR' },
        children: [
            { path: '', redirectTo: 'product-approval', pathMatch: 'full' }

        ]
    },
    {
        path: 'moderator',
        title: 'eCommerce-GT',
        component: ModeratorComponent,
        canActivate: [AuthGuard],
        data: { role: 'MODERADOR' },
        children: [
            { path: '', redirectTo: 'product-approval', pathMatch: 'full' },
            { path: 'product-approval', title: 'Productos', component: ProductApprovalComponent },
            { path: 'sanction-create', title: 'Crear sanción', component: SanctionCreateComponent },
            { path: 'all-sanctions', title: 'Lista de sanciones', component: SanctionsListComponent }
        ]
    },
    {
        path: 'logistics',
        title: 'eCommerce-GT',
        component: LogisticsComponent,
        canActivate: [AuthGuard],
        data: { role: 'LOGÍSTICA' },
        children: [
            { path: '', redirectTo: 'order-delivery', pathMatch: 'full' },
            { path: 'order-delivery', title: 'Entrega de pedido', component: OrderDeliveryComponent },
            { path: 'order-list', title: 'Lista de pedidos', component: OrderListComponent }
        ]
    }
];