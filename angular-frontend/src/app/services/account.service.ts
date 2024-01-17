import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {AccountListItemModel} from "../models/account-list-item.model";
import {AccountRegistrationDataModel} from "../models/account-registration-data.model";
import {environment} from "../../environments/environment";
import {AuthenticatedUserModel} from "../models/authenticated-user.model";

@Injectable({
  providedIn: 'root'
})
export class AccountService {

  private BASE_URL: string = environment.serverUrl + '/api/accounts';

  constructor(private http: HttpClient) {
  }

  getAllAccounts(): Observable<AccountListItemModel[]> {
    return this.http.get<AccountListItemModel[]>(this.BASE_URL);
  }


  registerUser(registrationData: AccountRegistrationDataModel) {
    return this.http.post<AccountRegistrationDataModel>(this.BASE_URL, registrationData);
  }

  logInWithGithub() {
    window.location.href = environment.serverUrl + '/oauth2/authorization/github';
  }

  logInWithGoogle() {
    window.location.href = environment.serverUrl + '/oauth2/authorization/google';


  }

  getUserInfo(): Observable<AuthenticatedUserModel> {
    return this.http.get<AuthenticatedUserModel>(this.BASE_URL + 'urerInfo');
  }
}
