import {Component, Input} from '@angular/core';
import {AuthenticatedUserModel} from "../../models/authenticated-user.model";

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent {

  @Input() public user?: AuthenticatedUserModel | null=null;

}
