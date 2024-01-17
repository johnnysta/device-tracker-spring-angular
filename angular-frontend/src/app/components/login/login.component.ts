import { Component } from '@angular/core';
import {AccountService} from "../../services/account.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  constructor(private accountService: AccountService) {
  }

  logInWithGitHub() {
    this.accountService.logInWithGithub();
  }

  logInWithGoogle() {
    this.accountService.logInWithGoogle();
  }
}
