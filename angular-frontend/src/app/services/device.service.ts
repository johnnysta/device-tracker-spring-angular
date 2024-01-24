import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {DeviceRegistrationInitDataModel} from "../models/device-registration-init-data.model";
import {DeviceRegistrationDataModel} from "../models/device-registration-data.model";
import {DeviceListItemModel} from "../models/device-list-item.model";

const BASE_URL = "http://localhost:8080/api/devices"

@Injectable({
  providedIn: 'root'
})
export class DeviceService {


  constructor(private http: HttpClient) {
  }

  getDeviceFormInitData(): Observable<DeviceRegistrationInitDataModel> {
    return this.http.get<DeviceRegistrationInitDataModel>(BASE_URL + "/init-data");
  }

  sendDeviceRegistration(deviceData: DeviceRegistrationDataModel) {
    return this.http.post<DeviceRegistrationDataModel>(BASE_URL, deviceData);
  }

  getDevicesByUserId(id: number): Observable<DeviceListItemModel[]> {
    return this.http.get<DeviceListItemModel[]>(BASE_URL + "/devices_by_user/" + id);
  }
}
