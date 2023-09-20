import { Component } from '@angular/core';
import {FormBuilder} from "@angular/forms";
import {AccountService} from "../../services/account.service";

@Component({
  selector: 'app-register-user',
  templateUrl: './register-user.component.html',
  styleUrls: ['./register-user.component.css']
})
export class RegisterUserComponent {
  registrationForm: any;

  constructor(private formBuilder: FormBuilder,
              private accountService: AccountService) {
    this.registrationForm = this.formBuilder.group({
      email: [''],
      username: [''],
      password: ['']
    })

  }


  submitData() {

  }
}
