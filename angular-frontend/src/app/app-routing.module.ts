import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ListUsersComponent} from "./components/list-users/list-users.component";
import {RegisterUserComponent} from "./components/register-user/register-user.component";
import {RegisterUpdateDeviceComponent} from "./components/register-device/register-update-device.component";
import {ListDevicesComponent} from "./components/list-devices/list-devices.component";
import {LoginComponent} from "./components/login/login.component";
import {HomeComponent} from "./components/home/home.component";
import {TestComponent} from "./components/test/test.component";
import {ShowDeviceOnMapComponent} from "./components/show-device-on-map/show-device-on-map.component";

const routes: Routes = [
  {path: "users", component: ListUsersComponent},
  {path: "user-registration", component: RegisterUserComponent},
  {path: "login", component: LoginComponent},
  {path: "devices", component: ListDevicesComponent},
  {path: "device-registration-update/:id", component: RegisterUpdateDeviceComponent},
  {path: "device-registration-update", component: RegisterUpdateDeviceComponent},
  {path: "show-device-on-map/:id", component: ShowDeviceOnMapComponent},
  {path: "home", component: HomeComponent},
  {path: "test", component: TestComponent},
  {path: "**", redirectTo: "/home"},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
