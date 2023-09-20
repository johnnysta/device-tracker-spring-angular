import {Component, OnDestroy, OnInit} from '@angular/core';
import {AccountService} from "../../services/account.service";
import {AccountListItemModel} from "../../models/account-list-item.model";
import {Subscription} from "rxjs";

@Component({
  selector: 'app-list-users',
  templateUrl: './list-users.component.html',
  styleUrls: ['./list-users.component.css']
})
export class ListUsersComponent implements OnInit, OnDestroy {

  listData!: AccountListItemModel[];
  subscriptionAccounts!: Subscription;

  constructor(private accountService: AccountService) {
  }

  ngOnInit(): void {
    this.subscriptionAccounts = this.accountService.getAllAccounts().subscribe({
      next: data => {
        this.listData = data
      },
      error: err => {
        console.log(err)
      },
      complete: () => {
        console.log("account data: " + this.listData)
      }
    })

  }

  ngOnDestroy(): void {
    this.subscriptionAccounts.unsubscribe();
  }

}
