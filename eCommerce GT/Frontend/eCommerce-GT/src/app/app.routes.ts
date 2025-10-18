import { Routes } from '@angular/router';
import { MainPageComponent } from './components/main-page/main-page.component';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { CommonUserComponent } from './components/common-user/common-user.component';

export const routes: Routes = [
    { path: '', title: 'eCommerce-GT', component: MainPageComponent },
    { path: 'home', title: 'eCommerce-GT', component: MainPageComponent },
    { path: 'login', title: 'Iniciar sesión', component: LoginComponent },
    { path: 'register', title: 'Registrarase', component: RegisterComponent },
    { path: 'common-user', title: 'eCommerce-GT', component: CommonUserComponent }
];
