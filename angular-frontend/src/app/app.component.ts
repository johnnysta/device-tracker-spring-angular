import {Component, OnInit} from '@angular/core';
import {AuthenticatedUserModel} from "./models/authenticated-user.model";
import {AccountService} from "./services/account.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'angular-frontend';
  loggedInUser: AuthenticatedUserModel | null = null;


  constructor(private accountService: AccountService,
              private router: Router) {
  }

  ngOnInit(): void {
    this.getUserInfo();
  }

  getUserInfo(): void {
    this.accountService.getUserInfo().subscribe({
      next: (value) => {
        this.loggedInUser = value;
        this.loggedInUser = {
          ...value,
          isLoggedIn(): boolean {
            return true;
          }
        }
        this.accountService.loggedInUser.next(this.loggedInUser);
      },
      error:
        err => {
          this.loggedInUser = null;
          this.accountService.loggedInUser.next(this.accountService.INITIAL_USER_STATE);
          this.router.navigate(['home'])
        }
    });
  }


}
