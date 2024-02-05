import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {ReactiveFormsModule} from "@angular/forms";
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import { NavbarComponent } from './components/navbar/navbar.component';
import { ListUsersComponent } from './components/list-users/list-users.component';
import { RegisterUserComponent } from './components/register-user/register-user.component';
import { RegisterUpdateDeviceComponent } from './components/register-device/register-update-device.component';
import { ListDevicesComponent } from './components/list-devices/list-devices.component';
import { LoginComponent } from './components/login/login.component';
import { HomeComponent } from './components/home/home.component';
import {HttpRequestInterceptor} from "./utils/interceptors/httpRequestInterceptor";
import {CookieService} from 'ngx-cookie-service';
import { TestComponent } from './components/test/test.component';
import { ShowDeviceOnMapComponent } from './components/show-device-on-map/show-device-on-map.component';

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    ListUsersComponent,
    RegisterUserComponent,
    RegisterUpdateDeviceComponent,
    ListDevicesComponent,
    LoginComponent,
    HomeComponent,
    TestComponent,
    ShowDeviceOnMapComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule
  ],
  providers: [
    CookieService,
    {provide: HTTP_INTERCEPTORS, useClass: HttpRequestInterceptor, multi: true},

  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
