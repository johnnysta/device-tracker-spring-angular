import {Component, EventEmitter, Input, Output} from '@angular/core';
import {AuthenticatedUserModel} from "../../models/authenticated-user.model";
import {AccountService} from "../../services/account.service";

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent {

  @Input() public user?: AuthenticatedUserModel | null=null;
  @Output() public logoutEmitter: EventEmitter<void> = new EventEmitter<void>();


  constructor(private accountService: AccountService) {
  }


  public logout(): void {
    this.accountService.logout().subscribe({
      complete: () => {
        this.logoutEmitter.emit();
      },
    });
  }

}
