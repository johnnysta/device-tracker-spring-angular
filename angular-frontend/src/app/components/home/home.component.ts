import {Component} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {AuthenticatedUserModel} from "../../models/authenticated-user.model";
import {AccountService} from "../../services/account.service";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent {

  loggedInUser!: AuthenticatedUserModel;

  constructor(private accountService: AccountService, private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.accountService.loggedInUser.subscribe
    ({next: value => this.loggedInUser = value});

    this.route.queryParams.subscribe({
      next: (params) => {
        const userId = params['id'];
        const provider = params['provider'];
        console.log('userId: ', userId);
        console.log('provider: ', provider);
      },
    });
  }
}
