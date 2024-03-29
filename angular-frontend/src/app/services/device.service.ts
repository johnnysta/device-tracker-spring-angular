import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {DeviceRegistrationInitDataModel} from "../models/device-registration-init-data.model";
import {DeviceListItemModel} from "../models/device-list-item.model";
import {DeviceTrackStatusChangeModel} from "../models/device-track-status-change.model";
import {DeviceDetailsDataModel} from "../models/device-details-data.model";
import {environment} from "../../environments/environment";
import {DeviceTrackingSettingsDataModel} from "../models/device-tracking-settings-data.model";

const BASE_URL: string = environment.serverUrl + '/api/devices';


@Injectable({
  providedIn: 'root'
})
export class DeviceService {


  constructor(private http: HttpClient) {
  }

  getDeviceFormInitData(): Observable<DeviceRegistrationInitDataModel> {
    return this.http.get<DeviceRegistrationInitDataModel>(BASE_URL + "/init-data");
  }

  sendDeviceRegistration(deviceData: DeviceDetailsDataModel) {
    return this.http.post<DeviceDetailsDataModel>(BASE_URL, deviceData);
  }

  getDevicesByUserId(id: number): Observable<DeviceListItemModel[]> {
    return this.http.get<DeviceListItemModel[]>(BASE_URL + "/devices_by_user/" + id);
  }

  setTrackedStatus(deviceTrackStatusChange: DeviceTrackStatusChangeModel) {
    return this.http.post<DeviceTrackStatusChangeModel>(BASE_URL + "/device_change_track_status", deviceTrackStatusChange)

  }

  deleteDeviceById(i: number) {
    return this.http.delete(BASE_URL + "/delete/" + i);
  }

  getDeviceById(id: number) {
    return this.http.get<DeviceDetailsDataModel>(BASE_URL + "/device_by_id/" + id);
  }

  sendDeviceUpdate(deviceData: DeviceDetailsDataModel) {
    console.log("DeviceID: " + deviceData.deviceId)
    return this.http.put<DeviceDetailsDataModel>(BASE_URL + "/" + deviceData.deviceId, deviceData);

  }

  getSettingsByDeviceId(deviceId: number) {
    return this.http.get<DeviceTrackingSettingsDataModel>(BASE_URL+ "/trackingSettings/" + deviceId);
  }


  setSettingsByDeviceId(deviceId: number, deviceTrackingSettingsDataModel: DeviceTrackingSettingsDataModel ) {
    console.log("In setSettingsByDeviceId");
    console.log(deviceTrackingSettingsDataModel.isGeofenceActive);
    console.log(deviceTrackingSettingsDataModel.geofenceCenterLongitude);
    return this.http.put<DeviceTrackingSettingsDataModel>(BASE_URL+ "/trackingSettings/" + deviceId, deviceTrackingSettingsDataModel);
  }


  sendDeviceSettingsUpdate(currentDeviceId: string | null, deviceSettingsData: DeviceTrackingSettingsDataModel) {


  }
}
