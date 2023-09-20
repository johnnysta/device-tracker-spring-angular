import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {ListUsersComponent} from "./components/list-users/list-users.component";
import {RegisterUserComponent} from "./components/register-user/register-user.component";

const routes: Routes = [
  {path: "users", component:ListUsersComponent},
  {path: "registration", component:RegisterUserComponent},
  {path: "**", redirectTo:"/registration"}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
