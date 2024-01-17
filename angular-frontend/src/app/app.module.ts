import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {ReactiveFormsModule} from "@angular/forms";
import {HttpClientModule} from "@angular/common/http";
import { NavbarComponent } from './components/navbar/navbar.component';
import { ListUsersComponent } from './components/list-users/list-users.component';
import { RegisterUserComponent } from './components/register-user/register-user.component';
import { RegisterDeviceComponent } from './components/register-device/register-device.component';
import { ListDevicesComponent } from './components/list-devices/list-devices.component';
import { GithubLoginComponent } from './components/login/github-login/github-login.component';
import { GoogleLoginComponent } from './components/login/google-login/google-login.component';
import { LoginComponent } from './components/login/login.component';
import { HomeComponent } from './components/home/home.component';

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    ListUsersComponent,
    RegisterUserComponent,
    RegisterDeviceComponent,
    ListDevicesComponent,
    GithubLoginComponent,
    GoogleLoginComponent,
    LoginComponent,
    HomeComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
