import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {TrackingDataListItemModel} from "../models/tracking-data-list-item.model";
import {environment} from "../../environments/environment";

const BASE_URL: string = environment.serverUrl + '/api/tracking';

@Injectable({
  providedIn: 'root'
})


export class TrackingService {

  constructor(private http: HttpClient) {
  }

  getTrackingData(deviceId: number): Observable<TrackingDataListItemModel[]> {
    return this.http.get<TrackingDataListItemModel[]>(BASE_URL + "/" + deviceId);
  }
}
