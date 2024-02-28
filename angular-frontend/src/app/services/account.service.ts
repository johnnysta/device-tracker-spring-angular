import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {BehaviorSubject, Observable} from "rxjs";
import {AccountListItemModel} from "../models/account-list-item.model";
import {AccountRegistrationDataModel} from "../models/account-registration-data.model";
import {environment} from "../../environments/environment";
import {AuthenticatedUserModel} from "../models/authenticated-user.model";

const BASE_URL: string = environment.serverUrl + '/api/accounts';

@Injectable({
  providedIn: 'root'
})
export class AccountService {


  readonly INITIAL_USER_STATE: AuthenticatedUserModel = {
    userName: '',
    email: '',
    isLoggedIn: false,
    id: -1
  };


  loggedInUser: BehaviorSubject<AuthenticatedUserModel> = new BehaviorSubject<AuthenticatedUserModel>(this.INITIAL_USER_STATE);

  constructor(private http: HttpClient) {
  }

  getAllAccounts(): Observable<AccountListItemModel[]> {
    return this.http.get<AccountListItemModel[]>(BASE_URL);
  }


  registerUser(registrationData: AccountRegistrationDataModel) {
    return this.http.post<AccountRegistrationDataModel>(BASE_URL, registrationData);
  }

  logInWithGithub() {
    window.location.href = environment.serverUrl + '/oauth2/authorization/github';
  }

  logInWithGoogle() {
    window.location.href = environment.serverUrl + '/oauth2/authorization/google';
  }

  getUserInfo(): Observable<AuthenticatedUserModel> {
    return this.http.get<AuthenticatedUserModel>(BASE_URL + '/userInfo');
  }

  logout(): Observable<void> {
    return this.http.get<void>(BASE_URL + '/logout');
  }
}
