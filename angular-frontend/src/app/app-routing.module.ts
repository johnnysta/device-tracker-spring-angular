import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ListUsersComponent} from "./components/list-users/list-users.component";
import {RegisterUserComponent} from "./components/register-user/register-user.component";
import {RegisterDeviceComponent} from "./components/register-device/register-device.component";
import {ListDevicesComponent} from "./components/list-devices/list-devices.component";
import {LoginComponent} from "./components/login/login.component";
import {HomeComponent} from "./components/home/home.component";

const routes: Routes = [
  {path: "users", component: ListUsersComponent},
  {path: "user-registration", component: RegisterUserComponent},
  {path: "login", component: LoginComponent},
  {path: "devices", component: ListDevicesComponent},
  {path: "device-registration", component: RegisterDeviceComponent},
  {path: "home", component: HomeComponent},
  {path: "**", redirectTo: "/home"},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
