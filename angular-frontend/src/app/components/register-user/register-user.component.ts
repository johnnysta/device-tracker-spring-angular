import { Component } from '@angular/core';
import {FormBuilder} from "@angular/forms";
import {AccountService} from "../../services/account.service";
import {AccountRegistrationDataModel} from "../../models/account-registration-data.model";

@Component({
  selector: 'app-register-user',
  templateUrl: './register-user.component.html',
  styleUrls: ['./register-user.component.css']
})
export class RegisterUserComponent {
  registrationForm: any;
  registrationData!: AccountRegistrationDataModel;

  constructor(private formBuilder: FormBuilder,
              private accountService: AccountService) {
    this.registrationForm = this.formBuilder.group({
      user_email: [''],
      user_name: [''],
      user_password: [''],
      confirm_user_password: ['']
    })

  }

  submitData() {
    this.registrationData = this.registrationForm.value;
    console.log(this.registrationData);
    this.accountService.registerUser(this.registrationData).subscribe({
      next: value => {},
      error: err => console.error(err),
      complete: () => this.registrationForm.reset()
    });
  }
}
