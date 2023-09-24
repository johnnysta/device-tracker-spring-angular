import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {AccountListItemModel} from "../models/account-list-item.model";
import {AccountRegistrationDataModel} from "../models/account-registration-data.model";

const BASE_URL = "http://localhost:8080/api/accounts"

@Injectable({
  providedIn: 'root'
})
export class AccountService {

  constructor(private http: HttpClient) { }

  getAllAccounts(): Observable<AccountListItemModel[]> {
    return this.http.get<AccountListItemModel[]>(BASE_URL);
  }


  registerUser(registrationData: AccountRegistrationDataModel) {
    return this.http.post<AccountRegistrationDataModel>(BASE_URL, registrationData);
  }
}
