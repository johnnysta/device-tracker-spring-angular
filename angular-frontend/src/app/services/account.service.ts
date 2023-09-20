import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {AccountListItemModel} from "../models/account-list-item.model";

const BASE_URL = "http://localhost:8080/api/accounts"

@Injectable({
  providedIn: 'root'
})
export class AccountService {

  constructor(private http: HttpClient) { }

  getAllAccounts(): Observable<AccountListItemModel[]> {
    return this.http.get<AccountListItemModel[]>(BASE_URL);
  }


}
