import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {ListUsersComponent} from "./components/list-users/list-users.component";
import {RegisterUserComponent} from "./components/register-user/register-user.component";
import {RegisterDeviceComponent} from "./components/register-device/register-device.component";

const routes: Routes = [
  {path: "users", component:ListUsersComponent},
  {path: "user-registration", component:RegisterUserComponent},
  // {path: "devices", component:ListDevicesComponent},
  {path: "device-registration", component:RegisterDeviceComponent},
  {path: "**", redirectTo:"/registration"}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
