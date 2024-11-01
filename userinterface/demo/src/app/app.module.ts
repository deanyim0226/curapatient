import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HttpClientModule } from '@angular/common/http';
import { HomePageComponent } from './pages/home-page/home-page.component';
import { FormsModule } from '@angular/forms';
import { HeaderComponent } from './header/header.component';
import { EmployeePageComponent } from './pages/employee-page/employee-page.component';
import { RegisterPageComponent } from './pages/register-page/register-page.component';
import { LoginPageComponent } from './pages/login-page/login-page.component';
/*

*/
@NgModule({
  declarations: [ //put all the components used in the app
    AppComponent,
    HomePageComponent,
    HeaderComponent, 
    EmployeePageComponent, RegisterPageComponent, LoginPageComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule, // to use ngModel for two-way binding
    HttpClientModule // to use HTTP request 
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
